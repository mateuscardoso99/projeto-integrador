package com.example.api.job;

import java.util.Map;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.orm.jpa.JpaTransactionManager;

import com.example.api.models.Categoria;
import com.example.api.models.Questao;
import com.example.api.models.Resposta;
import com.example.api.repository.CategoriaRepository;
import com.example.api.repository.QuestaoRepository;
import com.example.api.repository.RespostaRepository;

import jakarta.annotation.PostConstruct;

@Configuration
public class CSVReadToDatabase{

    public static final Logger logger = LoggerFactory.getLogger(CSVReadToDatabase.class);

    private Map<String, Categoria> categorias = new HashMap<>();
    private Questao ultimaQuestaoCadastrada;

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JpaTransactionManager transactionManager;
    @Autowired
    private ItemReader<CSVData> itemReader;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private QuestaoRepository questaoRepository;
    @Autowired
    private RespostaRepository respostaRepository;

    @PostConstruct //permite então executar alguma ação logo após a inicialização do Spring, pois chamar um repository dentro do construtor dá erro pois ele não foi inicializado ainda.
    public void getCategoriaQuestoes(){
       //this.categorias = this.categoriaRepository.findAll().stream().collect(Collectors.toMap(Categoria::getCodigo, c -> c));
    }

    @Bean
    public Job insertIntoDbFromCsvJob() {
        var name = "CSVData Import Job";
        var builder = new JobBuilder(name, jobRepository);
        return builder
                .start(step1())
                .incrementer(new RunIdIncrementer())//cria um id pro job, pra poder executar varias vezes, senão só vai ser executado uma vez por causa do controle das tabelas no bd
                .build();
    }

    @Bean
    public Step step1() {
        var name = "INSERT CSV RECORDS To DB Step";
        return new StepBuilder(name, jobRepository)
            .<CSVData, CSVData>chunk(10, transactionManager)
            .reader(itemReader)
            .processor(processor())
            .writer(writer())
            .build();
    }

    @Bean
    public ItemProcessor<CSVData, CSVData> processor() {
        return item -> item;
    }

    @Bean
    public ItemWriter<CSVData> writer() {
       
        return items -> {
            for (CSVData item : items) {
                // Write item to the destination

                if(item.getIs_questao()){
                    Questao questao = new Questao();
                    questao.setDescricao(item.getDescricao());

                    if(this.categorias.containsKey(item.getCod_categoria())){
                        questao.setCategoria(categorias.get(item.getCod_categoria()));
                    }
                    else{
                        Categoria c = this.categoriaRepository.findByCodigo(item.getCod_categoria()).get();
                        questao.setCategoria(c);
                        categorias.put(item.getCod_categoria(), c);
                    }

                    ultimaQuestaoCadastrada = this.questaoRepository.save(questao);

                    logger.info("Questão cadastrada: {}", item.getDescricao());
                }
                else if(item.getIs_resposta()){
                    Resposta resposta = new Resposta();
                    resposta.setDescricao(item.getDescricao());
                    resposta.setCerta(item.getIs_certa());

                    Questao questao = this.questaoRepository.findById(ultimaQuestaoCadastrada.getId()).get();
                    resposta.setQuestao(questao);

                    this.respostaRepository.save(resposta);

                    logger.info("Resposta cadastrada: {}", item.getDescricao());
                }
            }
        };
    }


    @Bean
    public FlatFileItemReader<CSVData> flatFileItemReader(@Value("classpath:/questoes.csv") Resource inputFile){
        FlatFileItemReader<CSVData> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setName("DEVAL");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setResource(inputFile);
        flatFileItemReader.setLineMapper(linMappe());
        return flatFileItemReader;
    }
    @Bean
    public LineMapper<CSVData> linMappe() {
        DefaultLineMapper<CSVData> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(";");
        lineTokenizer.setNames("descricao","cod_categoria", "is_questao","is_resposta","is_certa");
        lineTokenizer.setStrict(false); // Set strict property to false
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setTargetType(CSVData.class);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
        return defaultLineMapper;
    }
}

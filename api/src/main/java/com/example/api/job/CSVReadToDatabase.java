package com.example.api.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

public class CSVReadToDatabase {
    
    @Value("resources/questoes.csv")
    private Resource resource;

    private final JobRepository jobRepository;

    public CSVReadToDatabase(JobRepository jobRepository){
        this.jobRepository = jobRepository;
    }

    @Bean
    public DelimitedLineTokenizer tokenizer() {
        var tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(";");
        tokenizer.setNames("descricao", "categotia");
        return tokenizer;
    }

    @Bean(name="insertIntoDbFromCsvJob")
    public Job insertIntoDbFromCsvJob(Step step1, Step step2) {
        var name = "Persons Import Job";
        var builder = new JobBuilder(name, jobRepository);
        return builder.start(step1).build();
    }
}

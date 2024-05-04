package com.example.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.api.dto.QuestaoDTO;
import com.example.api.exception.DataNotFoundException;
import com.example.api.models.Questao;
import com.example.api.models.Resposta;
import com.example.api.repository.CategoriaRepository;
import com.example.api.repository.QuestaoRepository;
import com.example.api.repository.RespostaRepository;
import com.example.api.request.CadastroQuestao;
import com.example.api.request.CadastroResposta;

@Service
public class QuestaoService {
    private final QuestaoRepository questaoRepository;
    private final CategoriaRepository categoriaRepository;
    private final RespostaRepository respostaRepository;

    public QuestaoService(QuestaoRepository questaoRepository, CategoriaRepository categoriaRepository, RespostaRepository respostaRepository){
        this.questaoRepository = questaoRepository;
        this.categoriaRepository = categoriaRepository;
        this.respostaRepository = respostaRepository;
    }

    public List<QuestaoDTO> getAll(){
        List<QuestaoDTO> questoes = new ArrayList<>();
        questoes.addAll(this.questaoRepository.findAll().stream().map(q -> new QuestaoDTO().convert(q)).toList());
        return questoes;
    }

    public QuestaoDTO find(Long id) throws Exception{
        Optional<Questao> q = this.questaoRepository.findById(id);
        if(q.isPresent()){
            return new QuestaoDTO().convert(q.get());
        }
        throw new DataNotFoundException("questão não encontrada");
    }

    public QuestaoDTO save(CadastroQuestao cadastroQuestao) throws Exception{
        Questao questao = new Questao();
        questao.setCategoria(this.categoriaRepository.findById(cadastroQuestao.idCategoria()).orElseThrow(() -> new DataNotFoundException("categoria não encontrada")));
        questao.setDescricao(cadastroQuestao.descricao());
        questao.setCodigo(cadastroQuestao.codigo());

        validateQtdRespostasCertas(cadastroQuestao);

        List<Resposta> respostas = this.toRespostas(questao, cadastroQuestao.respostas());

        questao.setRespostas(respostas);
        questao = this.questaoRepository.save(questao);
        return new QuestaoDTO().convert(questao);
    }

    //por padrão só faz rollback se for uma exceção de tempo execução, então precisa específicar as excecções que devem tbm gerar rollback
    @Transactional(rollbackFor = DataNotFoundException.class)
    public QuestaoDTO update(Long id, CadastroQuestao cadastroQuestao) throws Exception{
        Questao questao = questaoRepository.findById(id).orElseThrow(() -> new DataNotFoundException("questão não encontrada"));
        questao.setCategoria(this.categoriaRepository.findById(cadastroQuestao.idCategoria()).orElseThrow(() -> new DataNotFoundException("categoria não encontrada")));
        questao.setDescricao(cadastroQuestao.descricao());
        questao.setCodigo(cadastroQuestao.codigo());

        validateQtdRespostasCertas(cadastroQuestao);

        List<Long> idsRespostasEnviadas = cadastroQuestao.respostas().stream().map(CadastroResposta::id).collect(Collectors.toList());

        List<Resposta> respostasExcluidas = questao.getRespostas().stream().filter(resp -> !idsRespostasEnviadas.contains(resp.getId())).collect(Collectors.toList());

        respostasExcluidas.forEach(resp -> {
            resp.setAtivo(false);
        });

        respostaRepository.saveAll(respostasExcluidas);

        for(CadastroResposta cadastroResposta : cadastroQuestao.respostas()){
            if(cadastroResposta.id() == null){
                Resposta resposta = new Resposta();
                resposta.setCerta(cadastroResposta.certa());
                resposta.setDescricao(cadastroResposta.descricao());
                resposta.setQuestao(questao);
                questao.getRespostas().add(resposta);
            }
            else{
                Resposta resposta = this.respostaRepository.findByQuestao(cadastroResposta.id(),questao.getId()).orElseThrow(() -> new DataNotFoundException("questão não encontrada para a resposta"));
                resposta.setDescricao(cadastroResposta.descricao());
                resposta.setCerta(cadastroResposta.certa());
            }
        }

        //questao.setRespostas(this.toRespostas(questao, cadastroQuestao.respostas()));


        questaoRepository.save(questao);
        return new QuestaoDTO().convert(questao);
    }

    public void delete(Long id) throws DataNotFoundException{
        Questao questao = questaoRepository.findById(id).orElseThrow(() -> new DataNotFoundException("questão não encontrada"));
        questao.getRespostas().forEach(r -> {
            r.setAtivo(false);
        });
        questao.setAtivo(false);
        this.questaoRepository.save(questao);
    }

    private List<Resposta> toRespostas(Questao questao, List<CadastroResposta> cadastroResposta){
        List<Resposta> respostas = new ArrayList<>();
        for(CadastroResposta cad : cadastroResposta){
            Resposta resp = new Resposta();
            resp.setCerta(cad.certa());
            resp.setDescricao(cad.descricao());
            resp.setQuestao(questao);
            respostas.add(resp);
        }
        return respostas;
    }

    private void validateQtdRespostasCertas(CadastroQuestao cadastroQuestao) throws BadRequestException{
        long qtdCertas = cadastroQuestao.respostas().stream().filter(resp -> Boolean.TRUE.equals(resp.certa())).count();

        if(qtdCertas == 0 || qtdCertas > 1)
            throw new BadRequestException("Deve haver 1 resposta certa");
    }
}

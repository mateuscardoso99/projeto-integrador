package com.example.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.api.dto.QuestaoDTO;
import com.example.api.exception.DataNotFoundException;
import com.example.api.models.Questao;
import com.example.api.models.Resposta;
import com.example.api.repository.CategoriaRepository;
import com.example.api.repository.QuestaoRepository;
import com.example.api.request.CadastroQuestao;
import com.example.api.request.CadastroResposta;

@Service
public class QuestaoService {
    private final QuestaoRepository questaoRepository;
    private final CategoriaRepository categoriaRepository;

    public QuestaoService(QuestaoRepository questaoRepository, CategoriaRepository categoriaRepository){
        this.questaoRepository = questaoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<QuestaoDTO> getAll(){
        List<QuestaoDTO> questoes = new ArrayList<>();
        questoes.addAll(this.questaoRepository.findAll().stream().map(q -> new QuestaoDTO().convert(q)).toList());
        return questoes;
    }

    public QuestaoDTO find(Long id) throws Exception{
        return Optional.ofNullable(this.questaoRepository.findById(id)).map(r -> new QuestaoDTO().convert(r.get())).orElseThrow(() -> new DataNotFoundException("questão não encontrada"));
    }

    public QuestaoDTO save(CadastroQuestao cadastroQuestao) throws Exception{
        Questao questao = new Questao();
        questao.setAtivo(Boolean.TRUE);
        questao.setCategoria(this.categoriaRepository.findById(cadastroQuestao.idCategoria()).orElseThrow(() -> new DataNotFoundException("categoria não encontrada")));
        questao.setDescricao(cadastroQuestao.descricao());

        List<Resposta> respostas = this.toRespostas(questao, cadastroQuestao.respostas());

        questao.setRespostas(respostas);
        questao = this.questaoRepository.save(questao);
        return new QuestaoDTO().convert(questao);
    }

    public QuestaoDTO update(Long id, CadastroQuestao cadastroQuestao) throws DataNotFoundException{
        Questao questao = questaoRepository.findById(id).orElseThrow(() -> new DataNotFoundException("questão não encontrada"));
        questao.setAtivo(cadastroQuestao.ativo());
        questao.setDescricao(cadastroQuestao.descricao());
        questao.setRespostas(this.toRespostas(questao, cadastroQuestao.respostas()));
        questaoRepository.save(questao);
        return new QuestaoDTO().convert(questao);
    }

    public void delete(Long id) throws DataNotFoundException{
        Questao questao = questaoRepository.findById(id).orElseThrow(() -> new DataNotFoundException("questão não encontrada"));
        this.questaoRepository.delete(questao);
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

}

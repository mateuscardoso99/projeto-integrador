package com.example.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.api.dto.QuestaoDTO;
import com.example.api.models.Questao;
import com.example.api.repository.CategoriaRepository;
import com.example.api.repository.QuestaoRepository;
import com.example.api.request.CadastroQuestao;

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

    public QuestaoDTO find(Long id){
        return Optional.ofNullable(this.questaoRepository.findById(id)).map(r -> new QuestaoDTO().convert(r.get())).orElseThrow(() -> new IllegalArgumentException("não encontrado"));
    }

    public QuestaoDTO save(CadastroQuestao cadastroQuestao){
        Questao questao = new Questao();
        questao.setAtivo(Boolean.TRUE);
        questao.setCategoria(this.categoriaRepository.findById(cadastroQuestao.idCategoria()).orElseThrow(() -> new IllegalArgumentException("null")));
        questao.setDescricao(cadastroQuestao.descricao());
        //questao.setRespostas(cadastroQuestao.respostas());
        questao = this.questaoRepository.save(questao);
        return new QuestaoDTO().convert(questao);
    }
}

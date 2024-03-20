package com.example.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.QuestaoDTO;
import com.example.api.repository.QuestaoRepository;
import com.example.api.request.CadastroQuestao;
import com.example.api.service.QuestaoService;

@RestController
@RequestMapping(value = "questao")
public class CadastroQuestaoController {
    private final QuestaoService questaoService;

    public CadastroQuestaoController(QuestaoService questaoService){
        this.questaoService = questaoService;
    }

    @GetMapping(value = "/")
    public List<QuestaoDTO> findAll(){
        return this.questaoService.getAll();
    }

    @PostMapping(value = "/")
    public void cadastro(@RequestBody CadastroQuestao categoria){

    }

    @PutMapping(value = "/{id}")
    public void update(@RequestBody CadastroQuestao categoria){

    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id){

    }
}

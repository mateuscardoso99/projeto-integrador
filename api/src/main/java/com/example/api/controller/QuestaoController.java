package com.example.api.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.QuestaoDTO;
import com.example.api.request.CadastroQuestao;
import com.example.api.service.QuestaoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "questao")
public class QuestaoController {
    private final QuestaoService questaoService;
    private final Logger logger = Logger.getLogger(QuestaoController.class.getName());

    public QuestaoController(QuestaoService questaoService){
        this.questaoService = questaoService;
    }

    @GetMapping
    public ResponseEntity<List<QuestaoDTO>> findAll(){
        return ResponseEntity.ok(this.questaoService.getAll());
    }

    @GetMapping(value = "/{id}")
    public QuestaoDTO find(@PathVariable Long id) throws Exception{
        return this.questaoService.find(id);
    }

    @GetMapping(value = "/count")
    public ResponseEntity<Long> count(){
        return new ResponseEntity<Long>(this.questaoService.count(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> cadastro(@RequestBody @Valid CadastroQuestao questao) throws Exception{
        try{
            QuestaoDTO questaoDTO = this.questaoService.save(questao);
            return new ResponseEntity<>(questaoDTO, HttpStatus.CREATED);
        }catch(Exception ex){
            logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            throw ex;
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid CadastroQuestao questao, @PathVariable Long id) throws Exception{
        try{
            QuestaoDTO questaoDTO = this.questaoService.update(id, questao);
            return new ResponseEntity<>(questaoDTO, HttpStatus.OK);
        }catch(Exception ex){
            logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            throw ex;
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception{
        this.questaoService.delete(id);
        return ResponseEntity.ok().build();
    }
}

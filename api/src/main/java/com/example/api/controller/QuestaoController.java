package com.example.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.QuestaoDTO;
import com.example.api.models.Questao;
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
    public ResponseEntity<List<QuestaoDTO>> getAtivos(){
        return ResponseEntity.ok(this.questaoService.getAtivos());
    }

    @GetMapping(value = "/{id}")
    public QuestaoDTO find(@PathVariable Long id) throws Exception{
        return this.questaoService.find(id);
    }

    @GetMapping(value = "/count")
    public ResponseEntity<Long> count(){
        return new ResponseEntity<Long>(this.questaoService.count(), HttpStatus.OK);
    }

    @GetMapping(value = "/categoria/{idCategoria}")
    public ResponseEntity<Map<String, Object>> findByCategoria(
            @PathVariable Long idCategoria, 
            @RequestParam(defaultValue= "0", required = false) Integer pageNumber,
            @RequestParam(defaultValue= "10", required = false) Integer pageSize,
            @RequestParam(name = "filtro", required = false) String nomeQuestao
    ){
        Page<Questao> page = this.questaoService.getByCategoria(idCategoria, nomeQuestao, PageRequest.of(pageNumber, pageSize, Sort.by("id")));
        Map<String, Object> response = new HashMap<>();
        response.put("questoes", page.getContent().stream().map(q -> new QuestaoDTO().convert(q)).collect(Collectors.toList()));
        response.put("totalPages", page.getTotalPages());
        response.put("totalQuestoes", page.getTotalElements());
        response.put("paginaAtual", page.getNumber());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = {"","/"})
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

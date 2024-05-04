package com.example.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.exception.DataNotFoundException;
import com.example.api.service.QuestaoService;

@RestController
@RequestMapping(value = "batch")
public class BatchController {
    private final QuestaoService questaoService;

    public BatchController(QuestaoService questaoService){
        this.questaoService = questaoService;
    }

    @GetMapping
    public ResponseEntity<String> run() throws DataNotFoundException{
        this.questaoService.carregarQuestoes();
        return ResponseEntity.ok("Batch Job started with JobExecutionId"); 
    }
}

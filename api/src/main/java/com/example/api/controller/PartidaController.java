package com.example.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.PartidaDTO;
import com.example.api.request.EncerramentoPartida;
import com.example.api.service.PartidaService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/partida")
public class PartidaController {
    private final PartidaService partidaService;

    public PartidaController(PartidaService partidaService){
        this.partidaService = partidaService;
    }

    @PostMapping("/{idCategoria}")
    public ResponseEntity<?> iniciarPartida(@PathVariable Long idCategoria) {
        try{
            PartidaDTO partidaDTO = this.partidaService.iniciarPartida(idCategoria);       
            return new ResponseEntity<>(partidaDTO, HttpStatus.CREATED);
        }catch(Exception ex){
            return new ResponseEntity<>("erro", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPartida(@PathVariable Long idPartida) {
        try{
            PartidaDTO partidaDTO = this.partidaService.getPartida(idPartida);
            return ResponseEntity.ok(partidaDTO);
        }catch(Exception ex){
            return new ResponseEntity<>("erro", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/encerrar-partida/{id}")
    public ResponseEntity<PartidaDTO> encerrarPartida(@RequestBody @Valid EncerramentoPartida encerramentoPartida, @PathVariable Long id) throws Exception{
        PartidaDTO partidaDTO = this.partidaService.encerrarPartida(encerramentoPartida);
        return ResponseEntity.ok(partidaDTO);
    }
    
    
}

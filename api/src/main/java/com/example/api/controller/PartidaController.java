package com.example.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.PartidaDTO;
import com.example.api.request.EncerramentoPartida;
import com.example.api.service.PartidaService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.logging.Level;
import java.util.logging.Logger;

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
    private static final Logger LOGGER = Logger.getLogger(PartidaController.class.getName());

    public PartidaController(PartidaService partidaService){
        this.partidaService = partidaService;
    }

    @PostMapping("/{idCategoria}")
    public ResponseEntity<?> iniciarPartida(@PathVariable Long idCategoria, HttpServletRequest request) throws Exception{
        try{
            PartidaDTO partidaDTO = this.partidaService.iniciarPartida(idCategoria, request);       
            return new ResponseEntity<>(partidaDTO, HttpStatus.CREATED);
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            throw ex;
        }
    }

    @GetMapping("/{idPartida}")
    public ResponseEntity<?> getPartida(@PathVariable Long idPartida, HttpServletRequest request) throws Exception{
        try{
            PartidaDTO partidaDTO = this.partidaService.getPartida(idPartida, request);
            return ResponseEntity.ok(partidaDTO);
        }catch(Exception ex){
            throw ex;
        }
    }
    
    @PostMapping("/encerrar-partida/{id}")
    public ResponseEntity<PartidaDTO> encerrarPartida(@RequestBody @Valid EncerramentoPartida encerramentoPartida, @PathVariable Long id, HttpServletRequest request) throws Exception{
        PartidaDTO partidaDTO = this.partidaService.encerrarPartida(encerramentoPartida, request);
        return ResponseEntity.ok(partidaDTO);
    }
    
    
}

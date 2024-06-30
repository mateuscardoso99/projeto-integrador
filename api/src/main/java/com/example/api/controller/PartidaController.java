package com.example.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.PartidaDTO;
import com.example.api.dto.RankingDTO;
import com.example.api.dto.ResultadoPartidaDTO;
import com.example.api.dto.CategoriaDTO;
import com.example.api.request.EncerramentoPartida;
import com.example.api.service.CategoriaService;
import com.example.api.service.PartidaService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Collection;

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
    private final CategoriaService categoriaService;
    private static final Logger LOGGER = Logger.getLogger(PartidaController.class.getName());

    public PartidaController(PartidaService partidaService, CategoriaService categoriaService){
        this.partidaService = partidaService;
        this.categoriaService = categoriaService;
    }

    @GetMapping("/categorias")
    public ResponseEntity<Collection<CategoriaDTO>> getCategorias() {
        return new ResponseEntity<>(this.categoriaService.findCategoriaMinimoDezQuestoesCadastradas(), HttpStatus.OK);
    }

    @GetMapping("/ranking/{idCategoria}")
    public ResponseEntity<Collection<RankingDTO>> ranking(@PathVariable Long idCategoria) {
        return new ResponseEntity<>(this.partidaService.getRanking(idCategoria), HttpStatus.OK);
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
            PartidaDTO partidaDTO = this.partidaService.getPartida(idPartida, request, false);
            return ResponseEntity.ok(partidaDTO);
        }catch(Exception ex){
            throw ex;
        }
    }
    
    @PostMapping("/encerrar")
    public ResponseEntity<ResultadoPartidaDTO> encerrarPartida(@RequestBody @Valid EncerramentoPartida encerramentoPartida, HttpServletRequest request) throws Exception{
        ResultadoPartidaDTO partidaDTO = this.partidaService.encerrarPartida(encerramentoPartida, request);
        return ResponseEntity.ok(partidaDTO);
    }
    
    @GetMapping("/resultado/{idPartida}")
    public ResponseEntity<?> resultadoPartida(@PathVariable Long idPartida, HttpServletRequest request) throws Exception{
        try{
            PartidaDTO partidaDTO = this.partidaService.getPartida(idPartida, request, true);
            ResultadoPartidaDTO resultado = new ResultadoPartidaDTO();
            resultado.setTotalAcertos(this.partidaService.countAcertosPartida(partidaDTO.getId(), request));
            resultado.setPartidaDTO(partidaDTO);
            return ResponseEntity.ok(resultado);
        }catch(Exception ex){
            throw ex;
        }
    }
}

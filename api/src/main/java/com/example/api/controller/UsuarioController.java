package com.example.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.UsuarioDTO;
import com.example.api.request.CadastroUsuario;
import com.example.api.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "cadastro")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<UsuarioDTO> cadastro(@RequestBody @Valid CadastroUsuario usuario){
        UsuarioDTO u = this.usuarioService.save(usuario);
        return new ResponseEntity<>(u, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<UsuarioDTO> update(@RequestBody @Valid CadastroUsuario usuario){
        UsuarioDTO u = this.usuarioService.save(usuario);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> delete(){
        this.usuarioService.delete();
        return new ResponseEntity<>("apagado com sucesso", HttpStatus.OK);
    }
}

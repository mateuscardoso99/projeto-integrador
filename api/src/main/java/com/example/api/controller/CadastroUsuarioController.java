package com.example.api.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.repository.UsuarioRepository;
import com.example.api.request.CadastroUsuario;

@RestController
@RequestMapping(value = "cadastro")
public class CadastroUsuarioController {
    private final UsuarioRepository usuarioRepository;

    public CadastroUsuarioController(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping(value = "/")
    public void cadastro(@RequestBody CadastroUsuario categoria){

    }

    @PutMapping(value = "/{id}")
    public void update(@RequestBody CadastroUsuario categoria){

    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id){

    }
}

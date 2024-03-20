package com.example.api.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.repository.RespostaRepository;
import com.example.api.request.CadastroResposta;
import com.example.api.request.CadastroUsuario;

@RestController
@RequestMapping(value = "resposta")
public class CadastroRespostaController {
    private final RespostaRepository respostaRepository;

    public CadastroRespostaController(RespostaRepository respostaRepository){
        this.respostaRepository = respostaRepository;
    }

    @PostMapping(value = "/")
    public void cadastro(@RequestBody CadastroResposta categoria){

    }

    @PutMapping(value = "/{id}")
    public void update(@RequestBody CadastroResposta categoria){

    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id){

    }
}

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

import com.example.api.dto.CategoriaDTO;
import com.example.api.request.CadastroCategoria;
import com.example.api.service.CategoriaService;

@RestController
@RequestMapping(value = "categoria")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService){
        this.categoriaService = categoriaService;
    }
    
    @GetMapping(value = "/")
    public List<CategoriaDTO> getAll(){
        return this.categoriaService.getAll();
    }

    @GetMapping(value = "/{id}")
    public void find(@PathVariable Long id){

    }

    @PostMapping(value = "/")
    public void save(@RequestBody CadastroCategoria categoria){

    }

    @PutMapping(value = "/{id}")
    public void update(@RequestBody CadastroCategoria categoria){

    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id){

    }
}

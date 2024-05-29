package com.example.api.controller;

import java.util.List;

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

import com.example.api.dto.CategoriaDTO;
import com.example.api.exception.DataNotFoundException;
import com.example.api.request.CadastroCategoria;
import com.example.api.service.CategoriaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "categoria")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService){
        this.categoriaService = categoriaService;
    }
    
    @GetMapping(value = {"/", ""})
    public List<CategoriaDTO> getAtivos(){
        return this.categoriaService.getAtivos();
    }

    @GetMapping(value = "/count")
    public ResponseEntity<Long> count(){
        return new ResponseEntity<Long>(this.categoriaService.count(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public CategoriaDTO find(@PathVariable Long id) throws DataNotFoundException{
        return this.categoriaService.findById(id);
    }

    @PostMapping(value = {"/", ""})
    public CategoriaDTO save(@RequestBody @Valid CadastroCategoria categoria){
        return this.categoriaService.save(categoria);
    }

    @PutMapping(value = "/{id}")
    public CategoriaDTO update(@RequestBody @Valid CadastroCategoria categoria, @PathVariable Long id) throws DataNotFoundException{
        return this.categoriaService.update(id, categoria);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) throws Exception{
        this.categoriaService.delete(id);
    }
}

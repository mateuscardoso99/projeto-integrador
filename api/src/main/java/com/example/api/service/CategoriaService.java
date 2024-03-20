package com.example.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.api.dto.CategoriaDTO;
import com.example.api.repository.CategoriaRepository;

@Service
public class CategoriaService {
    private CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaDTO> getAll(){
        return this.categoriaRepository.findAll().stream().map(CategoriaDTO::convert).toList();
    }

    public CategoriaDTO findById(Long id){
        return Optional.ofNullable(this.categoriaRepository.findById(id)).map(cat -> CategoriaDTO.convert(cat.get())).orElseThrow(() -> new IllegalArgumentException("null"));
    }
}

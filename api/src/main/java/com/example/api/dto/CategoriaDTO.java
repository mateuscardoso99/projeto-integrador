package com.example.api.dto;

import com.example.api.models.Categoria;

public class CategoriaDTO {
    private Long id;
    private String nome;
    private Boolean ativo;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Boolean getAtivo() {
        return ativo;
    }
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public static CategoriaDTO convert(Categoria cat){
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setId(cat.getId());
        categoriaDTO.setAtivo(cat.getAtivo());
        categoriaDTO.setNome(cat.getNome());
        return categoriaDTO;
    }
}

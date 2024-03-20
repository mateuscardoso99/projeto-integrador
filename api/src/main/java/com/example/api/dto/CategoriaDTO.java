package com.example.api.dto;

import com.example.api.models.Categoria;

public class CategoriaDTO {
    private Long id;
    private String nome;
    private String codigo;
    
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
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public static CategoriaDTO convert(Categoria cat){
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setCodigo(cat.getCodigo());
        categoriaDTO.setId(cat.getId());
        categoriaDTO.setNome(cat.getNome());
        return categoriaDTO;
    }
}

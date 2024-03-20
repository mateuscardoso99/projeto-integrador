package com.example.api.dto;

import java.util.List;

import com.example.api.models.Questao;

public class QuestaoDTO {
    private Long id;
    private String descricao;
    private Boolean ativo;
    private CategoriaDTO categoria;
    private List<RespostaDTO> respostas;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Boolean getAtivo() {
        return ativo;
    }
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
    public CategoriaDTO getCategoria() {
        return categoria;
    }
    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }
    public List<RespostaDTO> getRespostas() {
        return respostas;
    }
    public void setRespostas(List<RespostaDTO> respostas) {
        this.respostas = respostas;
    }

    public QuestaoDTO convert(Questao questao){
        this.ativo = questao.getAtivo();
        this.categoria = CategoriaDTO.convert(questao.getCategoria());
        this.descricao = questao.getDescricao();
        this.id = questao.getId();
        return this;
    }
    
}

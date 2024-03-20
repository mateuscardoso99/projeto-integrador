package com.example.api.dto;

public class RespostaDTO {
    private Long id;
    private String descricao;
    private Boolean certa;
    
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
    public Boolean getCerta() {
        return certa;
    }
    public void setCerta(Boolean certa) {
        this.certa = certa;
    }
    
    
}

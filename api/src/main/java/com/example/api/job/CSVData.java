package com.example.api.job;

public class CSVData {
    private String descricao;
    private String categoria;
    private Boolean is_questao;
    private Boolean is_resposta;
    private Boolean is_certa;
    
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public Boolean getIs_questao() {
        return is_questao;
    }
    public void setIs_questao(Boolean is_questao) {
        this.is_questao = is_questao;
    }
    public Boolean getIs_resposta() {
        return is_resposta;
    }
    public void setIs_resposta(Boolean is_resposta) {
        this.is_resposta = is_resposta;
    }
    public Boolean getIs_certa() {
        return is_certa;
    }
    public void setIs_certa(Boolean is_certa) {
        this.is_certa = is_certa;
    }

    
}

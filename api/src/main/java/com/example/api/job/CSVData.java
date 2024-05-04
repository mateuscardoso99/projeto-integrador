package com.example.api.job;

public class CSVData {
    private String descricao;
    private String cod_categoria;
    private String cod_questao;
    private Boolean is_categoria;
    private Boolean is_certa;

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getCod_categoria() {
        return cod_categoria;
    }
    public void setCod_categoria(String cod_categoria) {
        this.cod_categoria = cod_categoria;
    }
    public Boolean getIs_categoria() {
        return is_categoria;
    }
    public void setIs_categoria(Boolean is_categoria) {
        this.is_categoria = is_categoria;
    }
    public Boolean getIs_certa() {
        return is_certa;
    }
    public void setIs_certa(Boolean is_certa) {
        this.is_certa = is_certa;
    }
    public String getCod_questao() {
        return cod_questao;
    }
    public void setCod_questao(String cod_questao) {
        this.cod_questao = cod_questao;
    }    
}

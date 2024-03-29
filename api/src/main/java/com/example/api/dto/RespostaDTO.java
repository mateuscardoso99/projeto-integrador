package com.example.api.dto;

import com.example.api.models.Resposta;

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
    
    public static RespostaDTO convert(Resposta resposta){
        RespostaDTO respostaDTO = new RespostaDTO();
        respostaDTO.setCerta(resposta.getCerta());
        respostaDTO.setDescricao(resposta.getDescricao());
        respostaDTO.setId(resposta.getId());
        return respostaDTO;
    }
}

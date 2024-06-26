package com.example.api.dto;

import java.io.Serializable;

public class RankingDTO implements Serializable{
    private Long partida;
    private String categoria;
    private Long acertos;
    private String usuario;

    public Long getPartida() {
        return partida;
    }
    public void setPartida(Long partida) {
        this.partida = partida;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public Long getAcertos() {
        return acertos;
    }
    public void setAcertos(Long acertos) {
        this.acertos = acertos;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    } 
}

package com.example.api.dto;

import java.io.Serializable;

public class RankingDTO implements Serializable{
    private Long partida;
    private String categoria;
    private Long acertos;
    private Long idusuario;
    private String nomeusuario;

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
    public Long getIdusuario() {
        return idusuario;
    }
    public void setIdusuario(Long idusuario) {
        this.idusuario = idusuario;
    }
    public String getNomeusuario() {
        return nomeusuario;
    }
    public void setUsuario(String nomeusuario) {
        this.nomeusuario = nomeusuario;
    } 
}

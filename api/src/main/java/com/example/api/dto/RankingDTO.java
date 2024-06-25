package com.example.api.dto;

public class RankingDTO {
    private Long partida;
    private String categoria;
    private Long acertos;
    private String usuario;

    public RankingDTO(Long partida, String categoria, Long acertos, String usuario) {
        this.partida = partida;
        this.categoria = categoria;
        this.acertos = acertos;
        this.usuario = usuario;
    }

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

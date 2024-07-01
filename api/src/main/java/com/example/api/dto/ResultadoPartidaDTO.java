package com.example.api.dto;

public class ResultadoPartidaDTO {
    private Long totalAcertos;
    private PartidaDTO partida;

    public Long getTotalAcertos() {
        return totalAcertos;
    }
    public void setTotalAcertos(Long totalAcertos) {
        this.totalAcertos = totalAcertos;
    }
    public PartidaDTO getPartida() {
        return partida;
    }
    public void setPartidaDTO(PartidaDTO partidaDTO) {
        this.partida = partidaDTO;
    }
}

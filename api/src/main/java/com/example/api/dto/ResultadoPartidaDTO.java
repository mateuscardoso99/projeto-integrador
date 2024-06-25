package com.example.api.dto;

public class ResultadoPartidaDTO {
    private Long totalAcertos;
    private PartidaDTO partidaDTO;

    public Long getTotalAcertos() {
        return totalAcertos;
    }
    public void setTotalAcertos(Long totalAcertos) {
        this.totalAcertos = totalAcertos;
    }
    public PartidaDTO getPartidaDTO() {
        return partidaDTO;
    }
    public void setPartidaDTO(PartidaDTO partidaDTO) {
        this.partidaDTO = partidaDTO;
    }
}

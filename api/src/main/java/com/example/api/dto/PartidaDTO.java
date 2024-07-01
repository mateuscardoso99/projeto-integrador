package com.example.api.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.LinkedList;

import com.example.api.models.Partida;

public class PartidaDTO {
    private Long id;
    private LocalDateTime horaInicio;
    private Boolean encerrado;
    private CategoriaDTO categoria;
    private List<PartidaRespostaDTO> partidaQuestoes = new LinkedList<>();
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }
    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }
    public Boolean getEncerrado() {
        return encerrado;
    }
    public void setEncerrado(Boolean encerrado) {
        this.encerrado = encerrado;
    }
    public CategoriaDTO getCategoria() {
        return categoria;
    }
    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }
    public List<PartidaRespostaDTO> getPartidaQuestoes() {
        return partidaQuestoes;
    }
    public void setPartidaQuestoes(List<PartidaRespostaDTO> partidaQuestoes) {
        this.partidaQuestoes = partidaQuestoes;
    }

    public static PartidaDTO convert(Partida partida, boolean showRespostaCerta){
        PartidaDTO partidaDTO = new PartidaDTO();
        partidaDTO.setCategoria(CategoriaDTO.convert(partida.getCategoria()));
        partidaDTO.setHoraInicio(partida.getHoraInicio());
        partidaDTO.setEncerrado(partida.getEncerrado());
        partidaDTO.setId(partida.getId());

        partida.getPartidaRespostas().forEach(p -> {
            partidaDTO.getPartidaQuestoes().add(PartidaRespostaDTO.convert(p, showRespostaCerta));
        });
        return partidaDTO;
    }
}

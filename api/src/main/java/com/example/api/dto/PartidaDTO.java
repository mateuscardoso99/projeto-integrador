package com.example.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.api.models.Partida;

public class PartidaDTO {
    private Long id;
    private LocalDateTime horaInicio;
    private CategoriaDTO categoria;
    private List<PartidaRespostaDTO> respostas;
    
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
    public CategoriaDTO getCategoria() {
        return categoria;
    }
    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }
    public List<PartidaRespostaDTO> getRespostas() {
        return respostas;
    }
    public void setRespostas(List<PartidaRespostaDTO> respostas) {
        this.respostas = respostas;
    }

    public static PartidaDTO convert(Partida partida){
        PartidaDTO partidaDTO = new PartidaDTO();
        partidaDTO.setCategoria(CategoriaDTO.convert(partida.getCategoria()));
        partidaDTO.setHoraInicio(partida.getHoraInicio());
        partidaDTO.setId(partida.getId());
        partidaDTO.setRespostas(null);
        return partidaDTO;
    }
}

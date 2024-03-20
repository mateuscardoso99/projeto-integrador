package com.example.api.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record EncerramentoPartida(
    @Positive
    Long idPartida,

    @NotBlank(message = "respostas são obrigatórias")
    List<RespostasPartida> respostasPartidas
){}

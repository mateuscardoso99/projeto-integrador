package com.example.api.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record EncerramentoPartida(
    @Positive
    Long idPartida,

    @NotNull(message = "respostas devem ser enviadas")
    @Size(min = 10, max = 10, message = "devem ser enviadas 10 respostas")
    @Valid
    List<RespostasPartida> respostas
){}

package com.example.api.request;

import jakarta.validation.constraints.Positive;

public record RespostasPartida(
    @Positive
    Long idQuestao,

    @Positive
    Long idResposta
){}

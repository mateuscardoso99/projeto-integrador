package com.example.api.request;

import jakarta.validation.constraints.Positive;

public record EncerramentoPartida(
    @Positive
    Long idPartida,

    @Positive
    Long idQuestao,

    @Positive
    Long idResposta

){}

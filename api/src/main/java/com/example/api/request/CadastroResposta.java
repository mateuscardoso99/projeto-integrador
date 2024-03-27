package com.example.api.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CadastroResposta(
    @NotNull(message = "informe a descrição")
    String descricao,

    @Positive(message = "parametro inválido")
    Long idQuestao,

    @NotNull(message = "informe se é a resposta certa ou N")
    Boolean certa  
){}

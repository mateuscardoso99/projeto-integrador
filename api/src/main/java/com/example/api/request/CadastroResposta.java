package com.example.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CadastroResposta(
    @NotBlank(message = "informe a descrição")
    String descricao,

    @Positive(message = "parametro inválido")
    Long idQuestao,

    @NotBlank(message = "informe se é a resposta certa ou N")
    Boolean certa  
){}

package com.example.api.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CadastroResposta(
    Long id,
    
    @NotNull(message = "informe a descrição")
    String descricao,

    @Positive(message = "parametro inválido")
    Long idQuestao,

    @NotNull(message = "informe se é a resposta certa ou não")
    Boolean certa  
){}

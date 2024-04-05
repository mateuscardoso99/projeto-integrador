package com.example.api.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CadastroQuestao (
    @NotBlank(message = "informe a descrição")
    String descricao,

    @Positive
    Long idCategoria,
    
    Boolean ativo,

    @NotBlank(message = "informe as respostas")
    @Size(min = 5, max = 5, message = "questão deve ter 5 respostas")
    List<CadastroResposta> respostas
){}

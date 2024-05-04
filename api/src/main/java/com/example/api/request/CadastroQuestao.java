package com.example.api.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CadastroQuestao (
    @NotBlank(message = "informe a descrição")
    String descricao,

    @NotBlank(message = "informe o código")
    String codigo,

    @Positive
    Long idCategoria,
    
    Boolean ativo,

    @Size(min = 5, max = 5, message = "questão deve ter 5 respostas")
    @Valid
    List<CadastroResposta> respostas
){}

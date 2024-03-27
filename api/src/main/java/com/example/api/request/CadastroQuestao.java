package com.example.api.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CadastroQuestao (
    @NotBlank(message = "informe a descrição")
    String descricao,

    @Positive
    Long idCategoria,
    
    Boolean ativo,

    @Valid
    List<CadastroResposta> respostas
){}

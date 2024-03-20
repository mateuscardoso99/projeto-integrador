package com.example.api.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CadastroQuestao (
    @NotBlank(message = "informe a descrição")
    String descricao,

    @Positive
    Long idCategoria,

    @NotBlank(message = "informe as respostas")
    List<CadastroResposta> respostas
){}

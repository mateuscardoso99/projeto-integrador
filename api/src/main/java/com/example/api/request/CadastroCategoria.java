package com.example.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroCategoria (
    @NotBlank(message = "informe nome")
    @Size(max = 50)
    String nome,

    @NotBlank(message = "informe o codigo")
    @Size(max = 20)
    String codigo
){}

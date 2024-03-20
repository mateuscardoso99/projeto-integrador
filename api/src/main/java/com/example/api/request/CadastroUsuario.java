package com.example.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroUsuario(
    @Email
    @NotBlank(message = "email é obrigatório")
    @Size(max = 50)
    String email,

    @NotBlank(message = "senha é obrigatória")
    String senha
){}

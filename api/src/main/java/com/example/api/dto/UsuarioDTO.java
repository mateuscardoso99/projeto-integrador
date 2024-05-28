package com.example.api.dto;

import com.example.api.models.Usuario;

public class UsuarioDTO {
    private Long id;
    private String email;
    private String nome;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }    
    
    public static UsuarioDTO convert(Usuario usuario){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNome(usuario.getNome());
        return usuarioDTO;
    }
}

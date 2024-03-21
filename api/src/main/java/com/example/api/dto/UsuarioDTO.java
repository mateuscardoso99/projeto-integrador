package com.example.api.dto;

import com.example.api.models.Usuario;

public class UsuarioDTO {
    private Long id;
    private String email;

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
    
    public static UsuarioDTO convert(Usuario usuario){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setId(usuario.getId());
        return usuarioDTO;
    }
}

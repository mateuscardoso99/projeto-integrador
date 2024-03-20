package com.example.api.dto;

import com.example.api.models.Usuario;

public class UsuarioDTO {
    private String token;
    private String email;
    
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public static UsuarioDTO convert(Usuario usuario, String token){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setToken(token);
        return usuarioDTO;
    }
}

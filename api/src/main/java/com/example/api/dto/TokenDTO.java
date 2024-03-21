package com.example.api.dto;

public class TokenDTO{
    private String token;
    private UsuarioDTO usuarioDTO;

    public TokenDTO(String token, UsuarioDTO usuarioDTO){
        this.token = token;
        this.usuarioDTO = usuarioDTO;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public UsuarioDTO getUsuarioDTO() {
        return usuarioDTO;
    }
    
    public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
        this.usuarioDTO = usuarioDTO;
    }
}

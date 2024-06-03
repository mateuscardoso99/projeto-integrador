package com.example.api.dto;

import com.example.api.models.Usuario;

public class UsuarioDTO {
    private Long id;
    private String email;
    private String nome;
    private Boolean isAdmin;

    public UsuarioDTO(Usuario usuario){
        this.setEmail(usuario.getEmail());
        this.setId(usuario.getId());
        this.setNome(usuario.getNome());
        this.setIsAdmin(usuario.getAdmin());
    }

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
    public Boolean isAdmin(){
        return this.isAdmin;
    }
    public void setIsAdmin(Boolean isAdmin){
        this.isAdmin = isAdmin;
    }
}

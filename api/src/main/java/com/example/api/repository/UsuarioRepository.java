package com.example.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.api.models.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
    
}

package com.example.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.api.models.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
    @Query("SELECT u FROM Usuario WHERE u.email = ?1 AND u.ativo = true")
    Optional<Usuario> findByEmail(String email);
}

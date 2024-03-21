package com.example.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.api.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    @Query("SELECT u FROM Usuario u WHERE u.email = ?1 AND u.ativo = true")
    Optional<Usuario> findByEmail(String email);
}

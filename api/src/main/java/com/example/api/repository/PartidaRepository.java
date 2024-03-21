package com.example.api.repository;

import java.util.Optional;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.api.models.Partida;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long>{
    @Query("SELECT p FROM Partida p WHERE p.id = ?1 AND p.usuario.id = ?2")
    Optional<Partida> findPartida(Long idPartida, Long idUsuario);

    @Query("SELECT p FROM Partida p WHERE p.usuario.id = ?1")
    Collection<Partida> findPartidasUsuario(Long idUsuario);
}

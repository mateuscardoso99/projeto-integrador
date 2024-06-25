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

    @Query(
        value = "select sum(case when r.certa = true then 1 ELSE 0 end) as acertos from partida p join usuario u on u.id = p.usuario_id join partida_respostas pr on pr.partida_id = p.id join resposta r on r.id = pr.resposta_id where p.id = ?1 and u.id = ?2",
        nativeQuery = true
    )
    Long countAcertosPartida(Long idPartida, Long idUsuario);
}

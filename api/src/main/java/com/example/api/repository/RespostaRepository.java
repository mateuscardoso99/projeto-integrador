package com.example.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.api.models.Resposta;

@Repository
public interface RespostaRepository extends JpaRepository<Resposta,Long>{
    @Query("SELECT r FROM Resposta r WHERE r.id = ?1 AND r.questao.id = ?2 AND r.ativo = TRUE")
    Optional<Resposta> findByQuestao(Long idResposta, Long idQuestao);
}

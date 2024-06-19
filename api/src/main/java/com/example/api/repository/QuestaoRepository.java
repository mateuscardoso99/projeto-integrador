package com.example.api.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.api.models.Questao;

@Repository
public interface QuestaoRepository extends JpaRepository<Questao, Long>{
    @Query("SELECT q FROM Questao q WHERE q.categoria.id = ?1 AND q.ativo = true")
    Page<Questao> findByCategoria(Long idCategoria, Pageable pageable);

    @Query("SELECT q FROM Questao q WHERE q.categoria.id = ?1 AND LOWER(q.descricao) LIKE LOWER(CONCAT('%',?2,'%')) AND q.ativo = true")
    Page<Questao> findByCategoriaAndNome(Long idCategoria, String nomeQuestao, Pageable pageable);

    @Query("SELECT q FROM Questao q WHERE q.ativo = true")
    Collection<Questao> findAtivos();
}

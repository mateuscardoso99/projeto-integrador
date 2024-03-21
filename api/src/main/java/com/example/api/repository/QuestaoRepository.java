package com.example.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.api.models.Questao;

@Repository
public interface QuestaoRepository extends JpaRepository<Questao, Long>{
    
}

package com.example.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.models.Questao;

public interface QuestaoRepository extends JpaRepository<Questao, Long>{
    
}

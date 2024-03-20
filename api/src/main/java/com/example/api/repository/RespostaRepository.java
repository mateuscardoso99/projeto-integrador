package com.example.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.models.Resposta;

public interface RespostaRepository extends JpaRepository<Resposta,Long>{
    
}

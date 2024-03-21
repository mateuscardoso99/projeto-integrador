package com.example.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.api.models.Resposta;

@Repository
public interface RespostaRepository extends JpaRepository<Resposta,Long>{
    
}

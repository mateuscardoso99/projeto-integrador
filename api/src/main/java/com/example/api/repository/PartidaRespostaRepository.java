package com.example.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.api.models.PartidaRespostas;

@Repository
public interface PartidaRespostaRepository extends CrudRepository<PartidaRespostas, Long>{
    
}

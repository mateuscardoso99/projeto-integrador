package com.example.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.api.models.Partida;

public interface PartidaRepository extends CrudRepository<Partida, Long>{
    
}

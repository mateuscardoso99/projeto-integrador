package com.example.api.repository;

import java.util.Optional;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.api.models.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    @Query("SELECT c FROM Categoria c WHERE c.ativo = true")
    Collection<Categoria> findAtivos();

    Optional<Categoria> findByNome(String nome);
    
}

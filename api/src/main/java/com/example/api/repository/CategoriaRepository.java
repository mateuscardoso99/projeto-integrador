package com.example.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.api.models.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    Optional<Categoria> findByCodigo(String codigo);
}

package com.example.api.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.api.dto.CategoriaDTO;
import com.example.api.exception.DataNotFoundException;
import com.example.api.exception.ErrorRuntimeException;
import com.example.api.models.Categoria;
import com.example.api.repository.CategoriaRepository;
import com.example.api.request.CadastroCategoria;

@Service
public class CategoriaService {
    private CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaDTO> getAll(){
        return this.categoriaRepository.findAll().stream().map(CategoriaDTO::convert).toList();
    }

    public List<CategoriaDTO> getAtivos(){
        return this.categoriaRepository.findAtivos().stream().map(CategoriaDTO::convert).toList();
    }

    public CategoriaDTO findById(Long id) throws DataNotFoundException{
        Optional<Categoria> c = this.categoriaRepository.findById(id);
        if(c.isPresent()){
            return CategoriaDTO.convert(c.get());
        }
        throw new DataNotFoundException("categoria não encontrada");
    }

    public CategoriaDTO findByNome(String nome){
        Optional<Categoria> c = this.categoriaRepository.findByNome(nome);
        return c.isPresent() ? CategoriaDTO.convert(c.get()) : null;
    }

    public CategoriaDTO save(CadastroCategoria cadastroCategoria){
        Optional.ofNullable(this.findByNome(cadastroCategoria.nome())).ifPresent(s -> { 
            throw new ErrorRuntimeException("categoria já existe"); 
        });
        var categoria = new Categoria();
        categoria.setNome(cadastroCategoria.nome());
        categoria = categoriaRepository.save(categoria);
        return CategoriaDTO.convert(categoria);
    }

    public void delete(Long id) throws DataNotFoundException{
        Categoria categoria = this.categoriaRepository.findById(id).orElseThrow(() -> new DataNotFoundException("categoria não encontrada"));
        categoria.setAtivo(false);
        this.categoriaRepository.save(categoria);
    }

    public CategoriaDTO update(Long id, CadastroCategoria cadastroCategoria) throws DataNotFoundException{
        Categoria categoria = this.categoriaRepository.findById(id).orElseThrow(() -> new DataNotFoundException("categoria não encontrada"));
        categoria.setNome(cadastroCategoria.nome());
        categoriaRepository.save(categoria);
        return CategoriaDTO.convert(categoria);
    }

    public Long count(){
        return this.categoriaRepository.count();
    }

    public Collection<CategoriaDTO> findCategoriaMinimoDezQuestoesCadastradas(){
        return this.categoriaRepository.findCategoriaMinimoDezQuestoesCadastradas().stream().map(CategoriaDTO::convert).toList();
    }
}

package com.example.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.api.dto.CategoriaDTO;
import com.example.api.exception.DataNotFoundException;
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

    public CategoriaDTO save(CadastroCategoria cadastroCategoria){
        Categoria categoria = new Categoria();
        categoria.setNome(cadastroCategoria.nome());
        categoria.setCodigo(cadastroCategoria.codigo());
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
        categoria.setCodigo(cadastroCategoria.codigo());
        categoria.setNome(cadastroCategoria.nome());
        categoriaRepository.save(categoria);
        return CategoriaDTO.convert(categoria);
    }

    public Long count(){
        return this.categoriaRepository.count();
    }
}

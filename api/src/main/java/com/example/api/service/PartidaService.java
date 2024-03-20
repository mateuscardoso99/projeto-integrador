package com.example.api.service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.api.dto.PartidaDTO;
import com.example.api.exception.DataNotFoundException;
import com.example.api.models.Categoria;
import com.example.api.models.Partida;
import com.example.api.models.PartidaRespostas;
import com.example.api.models.Usuario;
import com.example.api.repository.CategoriaRepository;
import com.example.api.repository.PartidaRepository;
import com.example.api.repository.QuestaoRepository;
import com.example.api.repository.RespostaRepository;
import com.example.api.request.EncerramentoPartida;
import com.example.api.request.RespostasPartida;

@Service
public class PartidaService {
    private final PartidaRepository partidaRepository;
    private final CategoriaRepository categoriaRepository;
        private final QuestaoRepository questaoRepository;
    private final RespostaRepository respostaRepository;

    public PartidaService(PartidaRepository partidaRepository, CategoriaRepository categoriaRepository, QuestaoRepository questaoRepository, RespostaRepository respostaRepository){
        this.partidaRepository = partidaRepository;
        this.categoriaRepository = categoriaRepository;
        this.questaoRepository = questaoRepository;
        this.respostaRepository = respostaRepository;
    }

    public PartidaDTO getPartida(Long id) throws DataNotFoundException{
        Partida partida = this.partidaRepository.findById(id).orElseThrow(() -> new DataNotFoundException("n encontrado"));
        return PartidaDTO.convert(partida);
    }

    public PartidaDTO iniciarPartida(Long idCategoria) throws DataNotFoundException{
        Categoria categoria = this.categoriaRepository.findById(idCategoria).orElseThrow(() -> new DataNotFoundException("n encontrado"));
        Partida partida = new Partida();
        partida.setHoraInicio(LocalDateTime.now());
        partida.setUsuario(new Usuario());
        partida.setCategoria(categoria);
        partida = this.partidaRepository.save(partida);
        return PartidaDTO.convert(partida);
    }

    public PartidaDTO encerrarPartida(EncerramentoPartida encerramentoPartida) throws Exception{
        Partida partida = this.partidaRepository.findById(encerramentoPartida.idPartida()).orElseThrow(() -> new DataNotFoundException("partida não encontrada"));
        
        List<PartidaRespostas> respostasDaPartida = new LinkedList<>();

        for(RespostasPartida resp : encerramentoPartida.respostasPartidas()){
            PartidaRespostas partidaRespostas = new PartidaRespostas();
            partidaRespostas.setPartida(partida);
            partidaRespostas.setQuestao(this.questaoRepository.findById(resp.idQuestao()).orElseThrow(() -> new DataNotFoundException("questão não encontrada")));
            partidaRespostas.setResposta(this.respostaRepository.findById(resp.idResposta()).orElseThrow(() -> new DataNotFoundException("resposta não encontrada")));
            respostasDaPartida.add(partidaRespostas);
        }

        partida.setPartidaRespostas(respostasDaPartida);
        partida = partidaRepository.save(partida);
        return PartidaDTO.convert(partida);
    }
}

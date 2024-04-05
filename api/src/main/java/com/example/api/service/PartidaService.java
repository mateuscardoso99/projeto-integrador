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
import com.example.api.security.GetUserFromJwt;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class PartidaService {
    private final PartidaRepository partidaRepository;
    private final CategoriaRepository categoriaRepository;
    private final QuestaoRepository questaoRepository;
    private final RespostaRepository respostaRepository;
    private final GetUserFromJwt userFromJwt;

    public PartidaService(
        PartidaRepository partidaRepository, 
        CategoriaRepository categoriaRepository, 
        QuestaoRepository questaoRepository, 
        RespostaRepository respostaRepository,
        GetUserFromJwt userFromJwt
    ){
        this.partidaRepository = partidaRepository;
        this.categoriaRepository = categoriaRepository;
        this.questaoRepository = questaoRepository;
        this.respostaRepository = respostaRepository;
        this.userFromJwt = userFromJwt;
    }

    public PartidaDTO getPartida(Long id, HttpServletRequest request) throws DataNotFoundException{
        Usuario usuario = this.userFromJwt.load(request);
        Partida partida = this.partidaRepository.findPartida(id, usuario.getId()).orElseThrow(() -> new DataNotFoundException("partida não encontrada"));
        return PartidaDTO.convert(partida);
    }

    public PartidaDTO iniciarPartida(Long idCategoria, HttpServletRequest request) throws DataNotFoundException{
        Categoria categoria = this.categoriaRepository.findById(idCategoria).orElseThrow(() -> new DataNotFoundException("categoria não encontrada"));
        Partida partida = new Partida();
        partida.setHoraInicio(LocalDateTime.now());
        partida.setUsuario(this.userFromJwt.load(request));
        partida.setCategoria(categoria);
        partida = this.partidaRepository.save(partida);
        return PartidaDTO.convert(partida);
    }

    public PartidaDTO encerrarPartida(EncerramentoPartida encerramentoPartida, HttpServletRequest request) throws Exception{
        Usuario usuario = this.userFromJwt.load(request);
        Partida partida = this.partidaRepository.findPartida(encerramentoPartida.idPartida(), usuario.getId()).orElseThrow(() -> new DataNotFoundException("partida não encontrada"));
        
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

package com.example.api.service;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.api.dto.PartidaDTO;
import com.example.api.dto.RankingDTO;
import com.example.api.dto.ResultadoPartidaDTO;
import com.example.api.exception.DataNotFoundException;
import com.example.api.models.Categoria;
import com.example.api.models.Partida;
import com.example.api.models.Questao;
import com.example.api.models.Resposta;
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

    public PartidaDTO getResultadosPartida(Long id, HttpServletRequest request, boolean showRespostaCerta) throws Exception{
        Partida partida = this.findPartida(id, request);
        if(!partida.getEncerrado()){
            throw new BadRequestException("partida ainda não foi encerrada");
        }
        return PartidaDTO.convert(partida, showRespostaCerta);
    }

    public PartidaDTO getPartidaEmAndamento(Long id, HttpServletRequest request, boolean showRespostaCerta) throws Exception{
        Partida partida = this.findPartida(id, request);
        if(partida.getEncerrado()){
            throw new BadRequestException("partida já foi encerrada");
        }
        verificaPartidaExpirada(partida);
        return PartidaDTO.convert(partida, showRespostaCerta);
    }

    private Partida findPartida(Long id, HttpServletRequest request) throws Exception{
        Usuario usuario = this.userFromJwt.load(request);
        Partida partida = this.partidaRepository.findPartida(id, usuario.getId()).orElseThrow(() -> new DataNotFoundException("partida não encontrada"));
        return partida;
    }

    public Collection<PartidaDTO> findPartidasUsuario(HttpServletRequest request){
        Usuario usuario = this.userFromJwt.load(request);
        return partidaRepository.findPartidasUsuario(usuario.getId()).stream().map(p -> PartidaDTO.convert(p, false)).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = {Exception.class, DataNotFoundException.class, BadRequestException.class})
    public PartidaDTO iniciarPartida(Long idCategoria, HttpServletRequest request) throws DataNotFoundException, BadRequestException{
        Categoria categoria = this.categoriaRepository.findById(idCategoria).orElseThrow(() -> new DataNotFoundException("categoria não encontrada"));
        
        Collection<Questao> questoes = this.questaoRepository.findAleatoriasPartida(idCategoria);

        if(questoes.size() < 10)
            throw new BadRequestException("categoria não possui questões suficientes para uma partida");

        Partida partida = new Partida();
        partida.setHoraInicio(LocalDateTime.now());
        partida.setUsuario(this.userFromJwt.load(request));
        partida.setCategoria(categoria);

        List<PartidaRespostas> partidaRespostas = new LinkedList<>();

        for(Questao q : questoes){
            PartidaRespostas p = new PartidaRespostas();
            p.setPartida(partida);
            p.setQuestao(q);
            p.setResposta(null);
            partidaRespostas.add(p);
        };

        partida.setPartidaRespostas(partidaRespostas);
        
        partida = this.partidaRepository.save(partida);
        return PartidaDTO.convert(partida, false);
    }

    @Transactional(rollbackFor = {Exception.class, DataNotFoundException.class})
    public ResultadoPartidaDTO encerrarPartida(EncerramentoPartida encerramentoPartida, HttpServletRequest request) throws Exception{
        Usuario usuario = this.userFromJwt.load(request);
        Partida partida = this.partidaRepository.findPartida(encerramentoPartida.idPartida(), usuario.getId()).orElseThrow(() -> new DataNotFoundException("partida não encontrada"));
        
        if(partida.getEncerrado()){
            throw new BadRequestException("Partida já encerrada");
        }

        verificaPartidaExpirada(partida);

        Map<Long, Long> map = encerramentoPartida.respostas().stream().collect(Collectors.toMap(RespostasPartida::idQuestao, RespostasPartida::idResposta));

        for(PartidaRespostas p : partida.getPartidaRespostas()){
            Long idRespostaRecebida = map.get(p.getQuestao().getId());

            //if(idRespostaRecebida == null)
            //    throw new DataNotFoundException("resposta para a questão " + "\"" + p.getQuestao().getDescricao() + "\"" + " não enviada");

            //aceitar envio de resposta null, significa que não respondeu 
            if(idRespostaRecebida != null){
                Resposta resposta = this.respostaRepository.findByQuestao(idRespostaRecebida, p.getQuestao().getId()).orElseThrow(() -> new DataNotFoundException("resposta para a questão " + "\"" + p.getQuestao().getDescricao() + "\"" + " não encontrada"));
                p.setResposta(resposta);
            }
        };

        partida.setEncerrado(true);
        partida = partidaRepository.save(partida);
        ResultadoPartidaDTO resultado = new ResultadoPartidaDTO();
        resultado.setTotalAcertos(this.partidaRepository.countAcertosPartida(partida.getId(), usuario.getId()));
        resultado.setPartidaDTO(PartidaDTO.convert(partida, false));
        return resultado;
    }

    public Collection<RankingDTO> getRanking(Long idCategoria){
        return this.partidaRepository.rankingAcertosByCategoriaPorUsuario(idCategoria);
    }

    public Long countAcertosPartida(Long idPartida, HttpServletRequest request){
        Usuario usuario = this.userFromJwt.load(request);
        return this.partidaRepository.countAcertosPartida(idPartida, usuario.getId());
    }

    private static void verificaPartidaExpirada(Partida partida) throws Exception{
        Duration duration = Duration.between(partida.getHoraInicio(), LocalDateTime.now());
        if((duration.getSeconds() / 60) > 21){
            throw new BadRequestException("Partida expirada, passou de 20 minutos");
        }
    }
}

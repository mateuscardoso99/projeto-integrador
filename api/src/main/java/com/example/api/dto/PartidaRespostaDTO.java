package com.example.api.dto;

import com.example.api.models.PartidaRespostas;

public class PartidaRespostaDTO {
    private Long id;
    private QuestaoDTO questao;
    private RespostaDTO respostaEnviada;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public QuestaoDTO getQuestao() {
        return questao;
    }
    public void setQuestao(QuestaoDTO questao) {
        this.questao = questao;
    }
    public RespostaDTO getRespostaEnviada() {
        return respostaEnviada;
    }
    public void setRespostaEnviada(RespostaDTO respostaEnviada) {
        this.respostaEnviada = respostaEnviada;
    }    

    public static PartidaRespostaDTO convert(PartidaRespostas partidaResposta, boolean showRespostaCerta){
        PartidaRespostaDTO dto = new PartidaRespostaDTO();
        dto.id = partidaResposta.getId();
        dto.questao = new QuestaoDTO().convert(partidaResposta.getQuestao(), showRespostaCerta);
        
        if(partidaResposta.getResposta() != null){
            dto.respostaEnviada = RespostaDTO.convert(partidaResposta.getResposta(), showRespostaCerta);
        }
        
        return dto;
    }
}

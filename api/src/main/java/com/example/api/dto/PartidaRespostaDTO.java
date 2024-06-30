package com.example.api.dto;

import com.example.api.models.PartidaRespostas;

public class PartidaRespostaDTO {
    private Long id;
    private QuestaoDTO questao;
    private RespostaDTO resposta;
    
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
    public RespostaDTO getResposta() {
        return resposta;
    }
    public void setResposta(RespostaDTO resposta) {
        this.resposta = resposta;
    }

    public static PartidaRespostaDTO convert(PartidaRespostas partidaResposta, boolean showRespostas){
        PartidaRespostaDTO dto = new PartidaRespostaDTO();
        dto.id = partidaResposta.getId();
        dto.questao = new QuestaoDTO().convert(partidaResposta.getQuestao(), showRespostas);
        return dto;
    }
}

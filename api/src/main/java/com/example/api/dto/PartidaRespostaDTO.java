package com.example.api.dto;

import com.example.api.models.PartidaRespostas;

public class PartidaRespostaDTO {
    private Long id;
    private QuestaoDTO questao;
    
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

    public static PartidaRespostaDTO convert(PartidaRespostas partidaResposta, boolean showRespostaCerta){
        PartidaRespostaDTO dto = new PartidaRespostaDTO();
        dto.id = partidaResposta.getId();
        dto.questao = new QuestaoDTO().convert(partidaResposta.getQuestao(), showRespostaCerta);
        return dto;
    }
}

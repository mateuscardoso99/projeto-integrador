package com.example.api.dto;

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

    
}

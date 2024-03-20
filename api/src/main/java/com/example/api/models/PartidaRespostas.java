package com.example.api.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "partida_respostas")
public class PartidaRespostas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "partida_id", nullable = false)
    private Partida partida;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "questao_id", nullable = false)
    private Questao questao;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resposta_id", nullable = false)
    private Resposta resposta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public Questao getQuestao() {
        return questao;
    }

    public void setQuestao(Questao questao) {
        this.questao = questao;
    }

    public Resposta getResposta() {
        return resposta;
    }

    public void setResposta(Resposta resposta) {
        this.resposta = resposta;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((partida == null) ? 0 : partida.hashCode());
        result = prime * result + ((questao == null) ? 0 : questao.hashCode());
        result = prime * result + ((resposta == null) ? 0 : resposta.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PartidaRespostas other = (PartidaRespostas) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (partida == null) {
            if (other.partida != null)
                return false;
        } else if (!partida.equals(other.partida))
            return false;
        if (questao == null) {
            if (other.questao != null)
                return false;
        } else if (!questao.equals(other.questao))
            return false;
        if (resposta == null) {
            if (other.resposta != null)
                return false;
        } else if (!resposta.equals(other.resposta))
            return false;
        return true;
    }

    
}

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
}

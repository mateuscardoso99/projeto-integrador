package com.example.api.models;

import java.time.LocalDateTime;
import java.util.Collection;

import com.example.api.dto.RankingDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;

/**
 * CASCADE TYPE:
    PERSIST: Ele propaga a operação de persistir um objeto Pai para um objeto Filho, assim quando salvar a Entidade Cliente, também será salvo todas as Entidades Telefone associadas.
    MERGE: Ele propaga a operação de atualização de um objeto Pai para um objeto Filho, assim quando atualizadas as informações da Entidade Cliente, também será atualizado no banco de dados todas as informações das Entidades Telefone associadas.
    REMOVE: Ele propaga a operação de remoção de um objeto Pai para um objeto Filho, assim quando remover a Entidade Cliente, também será removida todas as entidades Telefone associadas.
    REFRESH: Ele propaga a operação de recarregar de um objeto Pai para um objeto Filho, assim, quando houver atualização no banco de dados na Entidade Cliente, todas as entidades Telefone associadas serão recarregadas do banco de dados.
    ALL: Corresponde a todas as operações acima (MERGE, PERSIST, REFRESH e REMOVE).
    DETACH: "A operação de desanexação remove a entidade do contexto persistente. Quando usamos CascaseType.DETACH, a entidade filha também é removida do contexto persistente".
 */

@NamedNativeQuery(name = "Partida.rankingAcertosByCategoriaPorUsuario",
                  query = "select p.id as partida, c.nome as categoria, sum(case when r.certa = true then 1 ELSE 0 end) as acertos , u.nome as usuario from partida p join usuario u on u.id = p.usuario_id join categoria c on c.id = p.categoria_id left join partida_respostas pr on pr.partida_id = p.id left join resposta r on r.id = pr.resposta_id where c.id = :categoria_id group by p.id, c.nome, u.nome order by acertos desc limit 20",
                  resultSetMapping = "Mapping.RankingDTO")
@SqlResultSetMapping(name = "Mapping.RankingDTO",
                     classes = @ConstructorResult(targetClass = RankingDTO.class,
                                                  columns = {@ColumnResult(name = "partida", type = Long.class),
                                                             @ColumnResult(name = "categoria", type = String.class),
                                                             @ColumnResult(name = "acertos", type = Long.class),
                                                             @ColumnResult(name = "usuario", type = String.class)}))
@Entity
@Table(name = "partida")
public class Partida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hora_inicio")
    private LocalDateTime horaInicio;

    @Column(name = "encerrado", columnDefinition = "boolean default false")
    private Boolean encerrado;

    @ManyToOne(cascade = CascadeType.ALL)
    private Categoria categoria;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partida", fetch = FetchType.LAZY)
    private Collection<PartidaRespostas> partidaRespostas;

    public Partida(){
        this.encerrado = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }
    
    public Boolean getEncerrado() {
        return encerrado;
    }

    public void setEncerrado(Boolean encerrado) {
        this.encerrado = encerrado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Collection<PartidaRespostas> getPartidaRespostas() {
        return partidaRespostas;
    }

    public void setPartidaRespostas(Collection<PartidaRespostas> partidaRespostas) {
        this.partidaRespostas = partidaRespostas;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((horaInicio == null) ? 0 : horaInicio.hashCode());
        result = prime * result + ((encerrado == null) ? 0 : encerrado.hashCode());
        result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
        result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
        result = prime * result + ((partidaRespostas == null) ? 0 : partidaRespostas.hashCode());
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
        Partida other = (Partida) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (horaInicio == null) {
            if (other.horaInicio != null)
                return false;
        } else if (!horaInicio.equals(other.horaInicio))
            return false;
        if (encerrado == null) {
            if (other.encerrado != null)
                return false;
        } else if (!encerrado.equals(other.encerrado))
            return false;
        if (categoria == null) {
            if (other.categoria != null)
                return false;
        } else if (!categoria.equals(other.categoria))
            return false;
        if (usuario == null) {
            if (other.usuario != null)
                return false;
        } else if (!usuario.equals(other.usuario))
            return false;
        if (partidaRespostas == null) {
            if (other.partidaRespostas != null)
                return false;
        } else if (!partidaRespostas.equals(other.partidaRespostas))
            return false;
        return true;
    }

    
    
}

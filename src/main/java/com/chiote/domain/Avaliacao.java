package com.chiote.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Avaliacao.
 */
@Entity
@Table(name = "avaliacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Avaliacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "data_cricao", nullable = false)
    private ZonedDateTime dataCricao;

    @Column(name = "data_atualizacao")
    private ZonedDateTime dataAtualizacao;

    @ManyToOne
    @NotNull
    private User avaliado;

    @ManyToOne
    @NotNull
    private AvaliacaoModelo avaliacaoModelo;

    @OneToMany(mappedBy = "avaliacao")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Resposta> respostas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDataCricao() {
        return dataCricao;
    }

    public Avaliacao dataCricao(ZonedDateTime dataCricao) {
        this.dataCricao = dataCricao;
        return this;
    }

    public void setDataCricao(ZonedDateTime dataCricao) {
        this.dataCricao = dataCricao;
    }

    public ZonedDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public Avaliacao dataAtualizacao(ZonedDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
        return this;
    }

    public void setDataAtualizacao(ZonedDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public User getAvaliado() {
        return avaliado;
    }

    public Avaliacao avaliado(User user) {
        this.avaliado = user;
        return this;
    }

    public void setAvaliado(User user) {
        this.avaliado = user;
    }

    public AvaliacaoModelo getAvaliacaoModelo() {
        return avaliacaoModelo;
    }

    public Avaliacao avaliacaoModelo(AvaliacaoModelo avaliacaoModelo) {
        this.avaliacaoModelo = avaliacaoModelo;
        return this;
    }

    public void setAvaliacaoModelo(AvaliacaoModelo avaliacaoModelo) {
        this.avaliacaoModelo = avaliacaoModelo;
    }

    public Set<Resposta> getRespostas() {
        return respostas;
    }

    public Avaliacao respostas(Set<Resposta> respostas) {
        this.respostas = respostas;
        return this;
    }

    public Avaliacao addResposta(Resposta resposta) {
        respostas.add(resposta);
        resposta.setAvaliacao(this);
        return this;
    }

    public Avaliacao removeResposta(Resposta resposta) {
        respostas.remove(resposta);
        resposta.setAvaliacao(null);
        return this;
    }

    public void setRespostas(Set<Resposta> respostas) {
        this.respostas = respostas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Avaliacao avaliacao = (Avaliacao) o;
        if (avaliacao.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, avaliacao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Avaliacao{" +
            "id=" + id +
            ", dataCricao='" + dataCricao + "'" +
            ", dataAtualizacao='" + dataAtualizacao + "'" +
            '}';
    }
}

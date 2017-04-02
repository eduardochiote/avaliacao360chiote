package com.chiote.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AvaliacaoControle.
 */
@Entity
@Table(name = "avaliacao_controle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AvaliacaoControle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "situacao", nullable = false)
    private Boolean situacao;

    @ManyToOne
    @NotNull
    private User avaliador;

    @ManyToOne
    @NotNull
    private Avaliado avaliado;

    @OneToMany(mappedBy = "nome")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AvaliacaoModelo> avaliacaoModelos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isSituacao() {
        return situacao;
    }

    public AvaliacaoControle situacao(Boolean situacao) {
        this.situacao = situacao;
        return this;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    public User getAvaliador() {
        return avaliador;
    }

    public AvaliacaoControle avaliador(User user) {
        this.avaliador = user;
        return this;
    }

    public void setAvaliador(User user) {
        this.avaliador = user;
    }

    public Avaliado getAvaliado() {
        return avaliado;
    }

    public AvaliacaoControle avaliado(Avaliado avaliado) {
        this.avaliado = avaliado;
        return this;
    }

    public void setAvaliado(Avaliado avaliado) {
        this.avaliado = avaliado;
    }

    public Set<AvaliacaoModelo> getAvaliacaoModelos() {
        return avaliacaoModelos;
    }

    public AvaliacaoControle avaliacaoModelos(Set<AvaliacaoModelo> avaliacaoModelos) {
        this.avaliacaoModelos = avaliacaoModelos;
        return this;
    }

    public AvaliacaoControle addAvaliacaoModelo(AvaliacaoModelo avaliacaoModelo) {
        avaliacaoModelos.add(avaliacaoModelo);
        avaliacaoModelo.setNome(this);
        return this;
    }

    public AvaliacaoControle removeAvaliacaoModelo(AvaliacaoModelo avaliacaoModelo) {
        avaliacaoModelos.remove(avaliacaoModelo);
        avaliacaoModelo.setNome(null);
        return this;
    }

    public void setAvaliacaoModelos(Set<AvaliacaoModelo> avaliacaoModelos) {
        this.avaliacaoModelos = avaliacaoModelos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AvaliacaoControle avaliacaoControle = (AvaliacaoControle) o;
        if (avaliacaoControle.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, avaliacaoControle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AvaliacaoControle{" +
            "id=" + id +
            ", situacao='" + situacao + "'" +
            '}';
    }
}

package com.chiote.domain;

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
 * A Equipe.
 */
@Entity
@Table(name = "equipe")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Equipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "nome", length = 20, nullable = false)
    private String nome;

    @Size(max = 200)
    @Column(name = "descricao", length = 200)
    private String descricao;

    @Column(name = "ativo")
    private Boolean ativo;

    @NotNull
    @Column(name = "data_criacao", nullable = false)
    private ZonedDateTime dataCriacao;

    @ManyToOne
    @NotNull
    private User lider;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "equipe_membros",
               joinColumns = @JoinColumn(name="equipes_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="membros_id", referencedColumnName="ID"))
    private Set<User> membros = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Equipe nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Equipe descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public Equipe ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public ZonedDateTime getDataCriacao() {
        return dataCriacao;
    }

    public Equipe dataCriacao(ZonedDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
        return this;
    }

    public void setDataCriacao(ZonedDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public User getLider() {
        return lider;
    }

    public Equipe lider(User user) {
        this.lider = user;
        return this;
    }

    public void setLider(User user) {
        this.lider = user;
    }

    public Set<User> getMembros() {
        return membros;
    }

    public Equipe membros(Set<User> users) {
        this.membros = users;
        return this;
    }

    public Equipe addMembros(User user) {
        membros.add(user);
        return this;
    }

    public Equipe removeMembros(User user) {
        membros.remove(user);
        return this;
    }

    public void setMembros(Set<User> users) {
        this.membros = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Equipe equipe = (Equipe) o;
        if (equipe.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, equipe.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Equipe{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", descricao='" + descricao + "'" +
            ", ativo='" + ativo + "'" +
            ", dataCriacao='" + dataCriacao + "'" +
            '}';
    }
}

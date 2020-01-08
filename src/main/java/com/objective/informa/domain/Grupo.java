package com.objective.informa.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * A Grupo.
 */
@Entity
@Table(name = "grupo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Grupo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Version
    @Column(name = "versao")
    private Long versao;

    @Column(name = "criacao")
    private ZonedDateTime criacao;

    @Column(name = "ultima_edicao")
    private ZonedDateTime ultimaEdicao;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "formal")
    private Boolean formal;

    @Column(name = "opcional")
    private Boolean opcional;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersao() {
        return versao;
    }

    public Grupo versao(Long versao) {
        this.versao = versao;
        return this;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public ZonedDateTime getCriacao() {
        return criacao;
    }

    public Grupo criacao(ZonedDateTime criacao) {
        this.criacao = criacao;
        return this;
    }

    public void setCriacao(ZonedDateTime criacao) {
        this.criacao = criacao;
    }

    public ZonedDateTime getUltimaEdicao() {
        return ultimaEdicao;
    }

    public Grupo ultimaEdicao(ZonedDateTime ultimaEdicao) {
        this.ultimaEdicao = ultimaEdicao;
        return this;
    }

    public void setUltimaEdicao(ZonedDateTime ultimaEdicao) {
        this.ultimaEdicao = ultimaEdicao;
    }

    public String getNome() {
        return nome;
    }

    public Grupo nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Grupo descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isFormal() {
        return formal;
    }

    public Grupo formal(Boolean formal) {
        this.formal = formal;
        return this;
    }

    public void setFormal(Boolean formal) {
        this.formal = formal;
    }

    public Boolean isOpcional() {
        return opcional;
    }

    public Grupo opcional(Boolean opcional) {
        this.opcional = opcional;
        return this;
    }

    public void setOpcional(Boolean opcional) {
        this.opcional = opcional;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Grupo)) {
            return false;
        }
        return id != null && id.equals(((Grupo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Grupo{" +
            "id=" + getId() +
            ", versao=" + getVersao() +
            ", criacao='" + getCriacao() + "'" +
            ", ultimaEdicao='" + getUltimaEdicao() + "'" +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", formal='" + isFormal() + "'" +
            ", opcional='" + isOpcional() + "'" +
            "}";
    }
}

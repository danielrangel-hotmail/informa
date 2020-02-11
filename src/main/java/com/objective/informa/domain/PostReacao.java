package com.objective.informa.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A PostReacao.
 */
@Entity
@Table(name = "post_reacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PostReacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "criacao")
    private ZonedDateTime criacao;

    @Column(name = "ultima_edicao")
    private ZonedDateTime ultimaEdicao;

    @Column(name = "versao")
    private Long versao;

    @Column(name = "reacao")
    private String reacao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("postReacaos")
    private PerfilUsuario perfil;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("reacoes")
    private Post post;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCriacao() {
        return criacao;
    }

    public PostReacao criacao(ZonedDateTime criacao) {
        this.criacao = criacao;
        return this;
    }

    public void setCriacao(ZonedDateTime criacao) {
        this.criacao = criacao;
    }

    public ZonedDateTime getUltimaEdicao() {
        return ultimaEdicao;
    }

    public PostReacao ultimaEdicao(ZonedDateTime ultimaEdicao) {
        this.ultimaEdicao = ultimaEdicao;
        return this;
    }

    public void setUltimaEdicao(ZonedDateTime ultimaEdicao) {
        this.ultimaEdicao = ultimaEdicao;
    }

    public Long getVersao() {
        return versao;
    }

    public PostReacao versao(Long versao) {
        this.versao = versao;
        return this;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public String getReacao() {
        return reacao;
    }

    public PostReacao reacao(String reacao) {
        this.reacao = reacao;
        return this;
    }

    public void setReacao(String reacao) {
        this.reacao = reacao;
    }

    public PerfilUsuario getPerfil() {
        return perfil;
    }

    public PostReacao perfil(PerfilUsuario perfilUsuario) {
        this.perfil = perfilUsuario;
        return this;
    }

    public void setPerfil(PerfilUsuario perfilUsuario) {
        this.perfil = perfilUsuario;
    }

    public Post getPost() {
        return post;
    }

    public PostReacao post(Post post) {
        this.post = post;
        return this;
    }

    public void setPost(Post post) {
        this.post = post;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostReacao)) {
            return false;
        }
        return id != null && id.equals(((PostReacao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PostReacao{" +
            "id=" + getId() +
            ", criacao='" + getCriacao() + "'" +
            ", ultimaEdicao='" + getUltimaEdicao() + "'" +
            ", versao=" + getVersao() +
            ", reacao='" + getReacao() + "'" +
            "}";
    }
}

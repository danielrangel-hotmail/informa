package com.objective.informa.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A PerfilGrupo.
 */
@Entity
@Table(name = "perfil_grupo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PerfilGrupo implements Serializable {

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

    @Column(name = "favorito")
    private Boolean favorito;

    @Column(name = "notifica")
    private Boolean notifica;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("grupos")
    private PerfilUsuario perfil;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("usuarios")
    private Grupo grupo;

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

    public PerfilGrupo criacao(ZonedDateTime criacao) {
        this.criacao = criacao;
        return this;
    }

    public void setCriacao(ZonedDateTime criacao) {
        this.criacao = criacao;
    }

    public ZonedDateTime getUltimaEdicao() {
        return ultimaEdicao;
    }

    public PerfilGrupo ultimaEdicao(ZonedDateTime ultimaEdicao) {
        this.ultimaEdicao = ultimaEdicao;
        return this;
    }

    public void setUltimaEdicao(ZonedDateTime ultimaEdicao) {
        this.ultimaEdicao = ultimaEdicao;
    }

    public Long getVersao() {
        return versao;
    }

    public PerfilGrupo versao(Long versao) {
        this.versao = versao;
        return this;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public Boolean isFavorito() {
        return favorito;
    }

    public PerfilGrupo favorito(Boolean favorito) {
        this.favorito = favorito;
        return this;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }

    public Boolean isNotifica() {
        return notifica;
    }

    public PerfilGrupo notifica(Boolean notifica) {
        this.notifica = notifica;
        return this;
    }

    public void setNotifica(Boolean notifica) {
        this.notifica = notifica;
    }

    public PerfilUsuario getPerfil() {
        return perfil;
    }

    public PerfilGrupo perfil(PerfilUsuario perfilUsuario) {
        this.perfil = perfilUsuario;
        return this;
    }

    public void setPerfil(PerfilUsuario perfilUsuario) {
        this.perfil = perfilUsuario;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public PerfilGrupo grupo(Grupo grupo) {
        this.grupo = grupo;
        return this;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerfilGrupo)) {
            return false;
        }
        return id != null && id.equals(((PerfilGrupo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PerfilGrupo{" +
            "id=" + getId() +
            ", criacao='" + getCriacao() + "'" +
            ", ultimaEdicao='" + getUltimaEdicao() + "'" +
            ", versao=" + getVersao() +
            ", favorito='" + isFavorito() + "'" +
            ", notifica='" + isNotifica() + "'" +
            "}";
    }
}

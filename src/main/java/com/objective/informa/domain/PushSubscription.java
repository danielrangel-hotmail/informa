package com.objective.informa.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A PushSubscription.
 */
@Entity
@Table(name = "push_subscription")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PushSubscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "versao")
    private Long versao;

    @Column(name = "criacao")
    private ZonedDateTime criacao;

    @Column(name = "endpoint")
    private String endpoint;

    @Column(name = "key")
    private String key;

    @Column(name = "auth")
    private String auth;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("subscriptions")
    private PerfilUsuario perfil;

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

    public PushSubscription versao(Long versao) {
        this.versao = versao;
        return this;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public ZonedDateTime getCriacao() {
        return criacao;
    }

    public PushSubscription criacao(ZonedDateTime criacao) {
        this.criacao = criacao;
        return this;
    }

    public void setCriacao(ZonedDateTime criacao) {
        this.criacao = criacao;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public PushSubscription endpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getKey() {
        return key;
    }

    public PushSubscription key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAuth() {
        return auth;
    }

    public PushSubscription auth(String auth) {
        this.auth = auth;
        return this;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public PerfilUsuario getPerfil() {
        return perfil;
    }

    public PushSubscription perfil(PerfilUsuario perfilUsuario) {
        this.perfil = perfilUsuario;
        return this;
    }

    public void setPerfil(PerfilUsuario perfilUsuario) {
        this.perfil = perfilUsuario;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PushSubscription)) {
            return false;
        }
        return id != null && id.equals(((PushSubscription) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PushSubscription{" +
            "id=" + getId() +
            ", versao=" + getVersao() +
            ", criacao='" + getCriacao() + "'" +
            ", endpoint='" + getEndpoint() + "'" +
            ", key='" + getKey() + "'" +
            ", auth='" + getAuth() + "'" +
            "}";
    }
}

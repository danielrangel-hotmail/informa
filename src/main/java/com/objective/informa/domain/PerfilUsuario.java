package com.objective.informa.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A PerfilUsuario.
 */
@Entity
@Table(name = "perfil_usuario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PerfilUsuario implements Serializable {

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

    @Column(name = "entrada_na_empresa")
    private LocalDate entradaNaEmpresa;

    @Column(name = "saida_da_empresa")
    private LocalDate saidaDaEmpresa;

    @Column(name = "nascimento")
    private LocalDate nascimento;

    @Column(name = "skype")
    private String skype;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User usuario;

    @OneToMany(mappedBy = "perfil")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PushSubscription> subscriptions = new HashSet<>();

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

    public PerfilUsuario criacao(ZonedDateTime criacao) {
        this.criacao = criacao;
        return this;
    }

    public void setCriacao(ZonedDateTime criacao) {
        this.criacao = criacao;
    }

    public ZonedDateTime getUltimaEdicao() {
        return ultimaEdicao;
    }

    public PerfilUsuario ultimaEdicao(ZonedDateTime ultimaEdicao) {
        this.ultimaEdicao = ultimaEdicao;
        return this;
    }

    public void setUltimaEdicao(ZonedDateTime ultimaEdicao) {
        this.ultimaEdicao = ultimaEdicao;
    }

    public Long getVersao() {
        return versao;
    }

    public PerfilUsuario versao(Long versao) {
        this.versao = versao;
        return this;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public LocalDate getEntradaNaEmpresa() {
        return entradaNaEmpresa;
    }

    public PerfilUsuario entradaNaEmpresa(LocalDate entradaNaEmpresa) {
        this.entradaNaEmpresa = entradaNaEmpresa;
        return this;
    }

    public void setEntradaNaEmpresa(LocalDate entradaNaEmpresa) {
        this.entradaNaEmpresa = entradaNaEmpresa;
    }

    public LocalDate getSaidaDaEmpresa() {
        return saidaDaEmpresa;
    }

    public PerfilUsuario saidaDaEmpresa(LocalDate saidaDaEmpresa) {
        this.saidaDaEmpresa = saidaDaEmpresa;
        return this;
    }

    public void setSaidaDaEmpresa(LocalDate saidaDaEmpresa) {
        this.saidaDaEmpresa = saidaDaEmpresa;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public PerfilUsuario nascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
        return this;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getSkype() {
        return skype;
    }

    public PerfilUsuario skype(String skype) {
        this.skype = skype;
        return this;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public User getUsuario() {
        return usuario;
    }

    public PerfilUsuario usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public Set<PushSubscription> getSubscriptions() {
        return subscriptions;
    }

    public PerfilUsuario subscriptions(Set<PushSubscription> pushSubscriptions) {
        this.subscriptions = pushSubscriptions;
        return this;
    }

    public PerfilUsuario addSubscriptions(PushSubscription pushSubscription) {
        this.subscriptions.add(pushSubscription);
        pushSubscription.setPerfil(this);
        return this;
    }

    public PerfilUsuario removeSubscriptions(PushSubscription pushSubscription) {
        this.subscriptions.remove(pushSubscription);
        pushSubscription.setPerfil(null);
        return this;
    }

    public void setSubscriptions(Set<PushSubscription> pushSubscriptions) {
        this.subscriptions = pushSubscriptions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerfilUsuario)) {
            return false;
        }
        return id != null && id.equals(((PerfilUsuario) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PerfilUsuario{" +
            "id=" + getId() +
            ", criacao='" + getCriacao() + "'" +
            ", ultimaEdicao='" + getUltimaEdicao() + "'" +
            ", versao=" + getVersao() +
            ", entradaNaEmpresa='" + getEntradaNaEmpresa() + "'" +
            ", saidaDaEmpresa='" + getSaidaDaEmpresa() + "'" +
            ", nascimento='" + getNascimento() + "'" +
            ", skype='" + getSkype() + "'" +
            "}";
    }
}

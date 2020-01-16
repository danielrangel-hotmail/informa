package com.objective.informa.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.objective.informa.domain.enumeration.LinkTipo;

/**
 * A LinkExterno.
 */
@Entity
@Table(name = "link_externo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LinkExterno implements Serializable {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private LinkTipo tipo;

    @Column(name = "link")
    private String link;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("linkExternos")
    private User usuario;

    @ManyToOne
    @JsonIgnoreProperties("linksExternos")
    private Post post;

    @ManyToOne
    @JsonIgnoreProperties("linksExternos")
    private Mensagem mensagem;

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

    public LinkExterno versao(Long versao) {
        this.versao = versao;
        return this;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public ZonedDateTime getCriacao() {
        return criacao;
    }

    public LinkExterno criacao(ZonedDateTime criacao) {
        this.criacao = criacao;
        return this;
    }

    public void setCriacao(ZonedDateTime criacao) {
        this.criacao = criacao;
    }

    public ZonedDateTime getUltimaEdicao() {
        return ultimaEdicao;
    }

    public LinkExterno ultimaEdicao(ZonedDateTime ultimaEdicao) {
        this.ultimaEdicao = ultimaEdicao;
        return this;
    }

    public void setUltimaEdicao(ZonedDateTime ultimaEdicao) {
        this.ultimaEdicao = ultimaEdicao;
    }

    public LinkTipo getTipo() {
        return tipo;
    }

    public LinkExterno tipo(LinkTipo tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(LinkTipo tipo) {
        this.tipo = tipo;
    }

    public String getLink() {
        return link;
    }

    public LinkExterno link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public User getUsuario() {
        return usuario;
    }

    public LinkExterno usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public Post getPost() {
        return post;
    }

    public LinkExterno post(Post post) {
        this.post = post;
        return this;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Mensagem getMensagem() {
        return mensagem;
    }

    public LinkExterno mensagem(Mensagem mensagem) {
        this.mensagem = mensagem;
        return this;
    }

    public void setMensagem(Mensagem mensagem) {
        this.mensagem = mensagem;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LinkExterno)) {
            return false;
        }
        return id != null && id.equals(((LinkExterno) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LinkExterno{" +
            "id=" + getId() +
            ", versao=" + getVersao() +
            ", criacao='" + getCriacao() + "'" +
            ", ultimaEdicao='" + getUltimaEdicao() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", link='" + getLink() + "'" +
            "}";
    }
}

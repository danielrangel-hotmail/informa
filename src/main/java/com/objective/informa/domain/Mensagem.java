package com.objective.informa.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Mensagem.
 */
@Entity
@Table(name = "mensagem")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mensagem implements Serializable {

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

    @Column(name = "conteudo")
    private String conteudo;

    @Column(name = "tem_conversa")
    private Boolean temConversa;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("mensagems")
    private User autor;

    @ManyToOne
    @JsonIgnoreProperties("mensagems")
    private Post post;

    @ManyToOne
    @JsonIgnoreProperties("mensagems")
    private Mensagem conversa;

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

    public Mensagem versao(Long versao) {
        this.versao = versao;
        return this;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public ZonedDateTime getCriacao() {
        return criacao;
    }

    public Mensagem criacao(ZonedDateTime criacao) {
        this.criacao = criacao;
        return this;
    }

    public void setCriacao(ZonedDateTime criacao) {
        this.criacao = criacao;
    }

    public ZonedDateTime getUltimaEdicao() {
        return ultimaEdicao;
    }

    public Mensagem ultimaEdicao(ZonedDateTime ultimaEdicao) {
        this.ultimaEdicao = ultimaEdicao;
        return this;
    }

    public void setUltimaEdicao(ZonedDateTime ultimaEdicao) {
        this.ultimaEdicao = ultimaEdicao;
    }

    public String getConteudo() {
        return conteudo;
    }

    public Mensagem conteudo(String conteudo) {
        this.conteudo = conteudo;
        return this;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Boolean isTemConversa() {
        return temConversa;
    }

    public Mensagem temConversa(Boolean temConversa) {
        this.temConversa = temConversa;
        return this;
    }

    public void setTemConversa(Boolean temConversa) {
        this.temConversa = temConversa;
    }

    public User getAutor() {
        return autor;
    }

    public Mensagem autor(User user) {
        this.autor = user;
        return this;
    }

    public void setAutor(User user) {
        this.autor = user;
    }

    public Post getPost() {
        return post;
    }

    public Mensagem post(Post post) {
        this.post = post;
        return this;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Mensagem getConversa() {
        return conversa;
    }

    public Mensagem conversa(Mensagem mensagem) {
        this.conversa = mensagem;
        return this;
    }

    public void setConversa(Mensagem mensagem) {
        this.conversa = mensagem;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mensagem)) {
            return false;
        }
        return id != null && id.equals(((Mensagem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Mensagem{" +
            "id=" + getId() +
            ", versao=" + getVersao() +
            ", criacao='" + getCriacao() + "'" +
            ", ultimaEdicao='" + getUltimaEdicao() + "'" +
            ", conteudo='" + getConteudo() + "'" +
            ", temConversa='" + isTemConversa() + "'" +
            "}";
    }
}

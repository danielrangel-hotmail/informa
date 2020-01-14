package com.objective.informa.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Arquivo.
 */
@Entity
@Table(name = "arquivo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Arquivo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "versao")
    private Long versao;

    @Column(name = "criacao")
    private ZonedDateTime criacao;

    @Column(name = "ultima_edicao")
    private ZonedDateTime ultimaEdicao;

    @Column(name = "nome")
    private String nome;

    @Column(name = "colecao")
    private String colecao;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "link")
    private String link;

    @Column(name = "upload_confirmado")
    private Boolean uploadConfirmado;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("arquivos")
    private User usuario;

    @ManyToOne
    @JsonIgnoreProperties("arquivos")
    private Post post;

    @ManyToOne
    @JsonIgnoreProperties("arquivos")
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

    public Arquivo versao(Long versao) {
        this.versao = versao;
        return this;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public ZonedDateTime getCriacao() {
        return criacao;
    }

    public Arquivo criacao(ZonedDateTime criacao) {
        this.criacao = criacao;
        return this;
    }

    public void setCriacao(ZonedDateTime criacao) {
        this.criacao = criacao;
    }

    public ZonedDateTime getUltimaEdicao() {
        return ultimaEdicao;
    }

    public Arquivo ultimaEdicao(ZonedDateTime ultimaEdicao) {
        this.ultimaEdicao = ultimaEdicao;
        return this;
    }

    public void setUltimaEdicao(ZonedDateTime ultimaEdicao) {
        this.ultimaEdicao = ultimaEdicao;
    }

    public String getNome() {
        return nome;
    }

    public Arquivo nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getColecao() {
        return colecao;
    }

    public Arquivo colecao(String colecao) {
        this.colecao = colecao;
        return this;
    }

    public void setColecao(String colecao) {
        this.colecao = colecao;
    }

    public String getTipo() {
        return tipo;
    }

    public Arquivo tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLink() {
        return link;
    }

    public Arquivo link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean isUploadConfirmado() {
        return uploadConfirmado;
    }

    public Arquivo uploadConfirmado(Boolean uploadConfirmado) {
        this.uploadConfirmado = uploadConfirmado;
        return this;
    }

    public void setUploadConfirmado(Boolean uploadConfirmado) {
        this.uploadConfirmado = uploadConfirmado;
    }

    public User getUsuario() {
        return usuario;
    }

    public Arquivo usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public Post getPost() {
        return post;
    }

    public Arquivo post(Post post) {
        this.post = post;
        return this;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Mensagem getMensagem() {
        return mensagem;
    }

    public Arquivo mensagem(Mensagem mensagem) {
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
        if (!(o instanceof Arquivo)) {
            return false;
        }
        return id != null && id.equals(((Arquivo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Arquivo{" +
            "id=" + getId() +
            ", versao=" + getVersao() +
            ", criacao='" + getCriacao() + "'" +
            ", ultimaEdicao='" + getUltimaEdicao() + "'" +
            ", nome='" + getNome() + "'" +
            ", colecao='" + getColecao() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", link='" + getLink() + "'" +
            ", uploadConfirmado='" + isUploadConfirmado() + "'" +
            "}";
    }
}

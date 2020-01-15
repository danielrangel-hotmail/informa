package com.objective.informa.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.objective.informa.domain.Arquivo} entity.
 */
public class ArquivoDTO implements Serializable {

    private Long id;

    private Long versao;

    private ZonedDateTime criacao;

    private ZonedDateTime ultimaEdicao;

    private String nome;

    private String colecao;

    private String tipo;

    private String link;

    private Boolean uploadConfirmado;

    private Long usuarioId;

    private Long postId;

    private Long mensagemId;

    private String s3PresignedURL;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public ZonedDateTime getCriacao() {
        return criacao;
    }

    public void setCriacao(ZonedDateTime criacao) {
        this.criacao = criacao;
    }

    public ZonedDateTime getUltimaEdicao() {
        return ultimaEdicao;
    }

    public void setUltimaEdicao(ZonedDateTime ultimaEdicao) {
        this.ultimaEdicao = ultimaEdicao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getColecao() {
        return colecao;
    }

    public void setColecao(String colecao) {
        this.colecao = colecao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean isUploadConfirmado() {
        return uploadConfirmado;
    }

    public void setUploadConfirmado(Boolean uploadConfirmado) {
        this.uploadConfirmado = uploadConfirmado;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long userId) {
        this.usuarioId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getMensagemId() {
        return mensagemId;
    }

    public void setMensagemId(Long mensagemId) {
        this.mensagemId = mensagemId;
    }

    public String getS3PresignedURL() {
        return s3PresignedURL;
    }
    public void setS3PresignedURL(String s3PresignedURL) {
        this.s3PresignedURL = s3PresignedURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ArquivoDTO arquivoDTO = (ArquivoDTO) o;
        if (arquivoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), arquivoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ArquivoDTO{" +
            "id=" + getId() +
            ", versao=" + getVersao() +
            ", criacao='" + getCriacao() + "'" +
            ", ultimaEdicao='" + getUltimaEdicao() + "'" +
            ", nome='" + getNome() + "'" +
            ", colecao='" + getColecao() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", link='" + getLink() + "'" +
            ", uploadConfirmado='" + isUploadConfirmado() + "'" +
            ", usuarioId=" + getUsuarioId() +
            ", postId=" + getPostId() +
            ", mensagemId=" + getMensagemId() +
            ", s3PresignedURL=" + getS3PresignedURL() +
            "}";
    }
}

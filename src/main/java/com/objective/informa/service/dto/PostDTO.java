package com.objective.informa.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import org.springframework.data.annotation.ReadOnlyProperty;

/**
 * A DTO for the {@link com.objective.informa.domain.Post} entity.
 */
public class PostDTO implements Serializable {

    private Long id;

    @ReadOnlyProperty
    private Long versao;

    @ReadOnlyProperty
    private ZonedDateTime criacao;

    @ReadOnlyProperty
    private ZonedDateTime ultimaEdicao;

    private String conteudo;

    private ZonedDateTime publicacao;

    private Long autorId;

    private Long grupoId;

    private String autorNome;

    private String grupoNome;

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

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public ZonedDateTime getPublicacao() {
        return publicacao;
    }

    public void setPublicacao(ZonedDateTime publicacao) {
        this.publicacao = publicacao;
    }

    public Long getAutorId() {
        return autorId;
    }

    public void setAutorId(Long userId) {
        this.autorId = userId;
    }

    public Long getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Long grupoId) {
        this.grupoId = grupoId;
    }

    public String getAutorNome() {
        return autorNome;
    }

    public void setAutorNome(String autorNome) {
        this.autorNome = autorNome;
    }

    public String getGrupoNome() {
        return grupoNome;
    }

    public void setGrupoNome(String grupoNome) {
        this.grupoNome = grupoNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PostDTO postDTO = (PostDTO) o;
        if (postDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), postDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PostDTO{" +
            "id=" + getId() +
            ", versao=" + getVersao() +
            ", criacao='" + getCriacao() + "'" +
            ", ultimaEdicao='" + getUltimaEdicao() + "'" +
            ", conteudo='" + getConteudo() + "'" +
            ", publicacao='" + getPublicacao() + "'" +
            ", autorId=" + getAutorId() +
            ", grupoId=" + getGrupoId() +
            "}";
    }
}

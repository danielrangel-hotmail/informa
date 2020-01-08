package com.objective.informa.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import org.springframework.data.annotation.ReadOnlyProperty;

/**
 * A DTO for the {@link com.objective.informa.domain.Grupo} entity.
 */
public class GrupoDTO implements Serializable {

    private Long id;

    @ReadOnlyProperty
    private Long versao;

    @ReadOnlyProperty
    private ZonedDateTime criacao;

    @ReadOnlyProperty
    private ZonedDateTime ultimaEdicao;

    private String nome;

    private String descricao;

    private Boolean formal;

    private Boolean opcional;


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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isFormal() {
        return formal;
    }

    public void setFormal(Boolean formal) {
        this.formal = formal;
    }

    public Boolean isOpcional() {
        return opcional;
    }

    public void setOpcional(Boolean opcional) {
        this.opcional = opcional;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GrupoDTO grupoDTO = (GrupoDTO) o;
        if (grupoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), grupoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GrupoDTO{" +
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

package com.objective.informa.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.objective.informa.domain.PerfilGrupo} entity.
 */
public class PerfilGrupoDTO implements Serializable {

    private Long id;

    private ZonedDateTime criacao;

    private ZonedDateTime ultimaEdicao;

    private Long versao;

    private Boolean favorito;

    private Boolean notifica;

    private Boolean moderador;


    private Long perfilId;

    private GrupoDTO grupo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public Boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }

    public Boolean isNotifica() {
        return notifica;
    }

    public void setNotifica(Boolean notifica) {
        this.notifica = notifica;
    }

    public Boolean isModerador() {
        return moderador;
    }

    public void setModerador(Boolean moderador) {
        this.moderador = moderador;
    }

    public Long getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Long perfilUsuarioId) {
        this.perfilId = perfilUsuarioId;
    }

    public GrupoDTO getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoDTO grupo) {
        this.grupo = grupo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PerfilGrupoDTO perfilGrupoDTO = (PerfilGrupoDTO) o;
        if (perfilGrupoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), perfilGrupoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PerfilGrupoDTO{" +
            "id=" + getId() +
            ", criacao='" + getCriacao() + "'" +
            ", ultimaEdicao='" + getUltimaEdicao() + "'" +
            ", versao=" + getVersao() +
            ", favorito='" + isFavorito() + "'" +
            ", notifica='" + isNotifica() + "'" +
            ", moderador='" + isModerador() + "'" +
            ", perfilId=" + getPerfilId() +
            ", grupoId=" + (getGrupo() != null ? getGrupo().getId() : "") +
            "}";
    }
}

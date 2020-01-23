package com.objective.informa.service.dto;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.objective.informa.domain.PerfilUsuario} entity.
 */
public class PerfilUsuarioDTO implements Serializable {

    private Long id;

    private ZonedDateTime criacao;

    private ZonedDateTime ultimaEdicao;

    private Long versao;

    private LocalDate entradaNaEmpresa;

    private LocalDate saidaDaEmpresa;

    private LocalDate nascimento;

    private String skype;


    private Long usuarioId;

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

    public LocalDate getEntradaNaEmpresa() {
        return entradaNaEmpresa;
    }

    public void setEntradaNaEmpresa(LocalDate entradaNaEmpresa) {
        this.entradaNaEmpresa = entradaNaEmpresa;
    }

    public LocalDate getSaidaDaEmpresa() {
        return saidaDaEmpresa;
    }

    public void setSaidaDaEmpresa(LocalDate saidaDaEmpresa) {
        this.saidaDaEmpresa = saidaDaEmpresa;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long userId) {
        this.usuarioId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PerfilUsuarioDTO perfilUsuarioDTO = (PerfilUsuarioDTO) o;
        if (perfilUsuarioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), perfilUsuarioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PerfilUsuarioDTO{" +
            "id=" + getId() +
            ", criacao='" + getCriacao() + "'" +
            ", ultimaEdicao='" + getUltimaEdicao() + "'" +
            ", versao=" + getVersao() +
            ", entradaNaEmpresa='" + getEntradaNaEmpresa() + "'" +
            ", saidaDaEmpresa='" + getSaidaDaEmpresa() + "'" +
            ", nascimento='" + getNascimento() + "'" +
            ", skype='" + getSkype() + "'" +
            ", usuarioId=" + getUsuarioId() +
            "}";
    }
}

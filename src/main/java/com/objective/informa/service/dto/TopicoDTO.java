package com.objective.informa.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.objective.informa.domain.Topico} entity.
 */
public class TopicoDTO implements Serializable {

    private Long id;

    private Long versao;

    private String nome;


    private Long autorId;

    private Long substitutoId;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getAutorId() {
        return autorId;
    }

    public void setAutorId(Long userId) {
        this.autorId = userId;
    }

    public Long getSubstitutoId() {
        return substitutoId;
    }

    public void setSubstitutoId(Long topicoId) {
        this.substitutoId = topicoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TopicoDTO topicoDTO = (TopicoDTO) o;
        if (topicoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), topicoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TopicoDTO{" +
            "id=" + getId() +
            ", versao=" + getVersao() +
            ", nome='" + getNome() + "'" +
            ", autorId=" + getAutorId() +
            ", substitutoId=" + getSubstitutoId() +
            "}";
    }
}

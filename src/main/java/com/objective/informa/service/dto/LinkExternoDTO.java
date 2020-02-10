package com.objective.informa.service.dto;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.objective.informa.domain.enumeration.LinkTipo;

/**
 * A DTO for the {@link com.objective.informa.domain.LinkExterno} entity.
 */
public class LinkExternoDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long versao;

    private ZonedDateTime criacao;

    private ZonedDateTime ultimaEdicao;

    private LinkTipo tipo;

    private String link;


    private Long usuarioId;

    private Long postId;

    private Long mensagemId;

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

    public LinkTipo getTipo() {
        return tipo;
    }

    public void setTipo(LinkTipo tipo) {
        this.tipo = tipo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LinkExternoDTO linkExternoDTO = (LinkExternoDTO) o;
        if (linkExternoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), linkExternoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LinkExternoDTO{" +
            "id=" + getId() +
            ", versao=" + getVersao() +
            ", criacao='" + getCriacao() + "'" +
            ", ultimaEdicao='" + getUltimaEdicao() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", link='" + getLink() + "'" +
            ", usuarioId=" + getUsuarioId() +
            ", postId=" + getPostId() +
            ", mensagemId=" + getMensagemId() +
            "}";
    }
}

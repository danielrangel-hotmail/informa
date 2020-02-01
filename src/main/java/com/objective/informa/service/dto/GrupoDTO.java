package com.objective.informa.service.dto;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Lob;

import org.springframework.data.annotation.ReadOnlyProperty;

import com.objective.informa.domain.User;

/**
 * A DTO for the {@link com.objective.informa.domain.Grupo} entity.
 */
public class GrupoDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

    @Lob
    private byte[] logo;

    private String logoContentType;
    
    private String cabecalhoSuperiorCor;

    private String cabecalhoInferiorCor;

    private String logoFundoCor;


    private Set<TopicoDTO> topicos = new HashSet<>();
    
    private Set<SimpleUserDTO> moderadores= new HashSet<>();

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

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public String getCabecalhoSuperiorCor() {
        return cabecalhoSuperiorCor;
    }

    public void setCabecalhoSuperiorCor(String cabecalhoSuperiorCor) {
        this.cabecalhoSuperiorCor = cabecalhoSuperiorCor;
    }

    public String getCabecalhoInferiorCor() {
        return cabecalhoInferiorCor;
    }

    public void setCabecalhoInferiorCor(String cabecalhoInferiorCor) {
        this.cabecalhoInferiorCor = cabecalhoInferiorCor;
    }

    public String getLogoFundoCor() {
        return logoFundoCor;
    }

    public void setLogoFundoCor(String logoFundoCor) {
        this.logoFundoCor = logoFundoCor;
    }

    public Set<TopicoDTO> getTopicos() {
        return topicos;
    }

    public void setTopicos(Set<TopicoDTO> topicos) {
        this.topicos = topicos;
    }

	public Set<SimpleUserDTO> getModeradores() {
		return moderadores;
	}

	public void setModeradores(Set<SimpleUserDTO> moderadores) {
		this.moderadores = moderadores;
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
            ", logo='" + getLogo() + "'" +
            ", cabecalhoSuperiorCor='" + getCabecalhoSuperiorCor() + "'" +
            ", cabecalhoInferiorCor='" + getCabecalhoInferiorCor() + "'" +
            ", logoFundoCor='" + getLogoFundoCor() + "'" +
            "}";
    }
}

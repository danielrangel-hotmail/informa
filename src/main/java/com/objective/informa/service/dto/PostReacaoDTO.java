package com.objective.informa.service.dto;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.objective.informa.domain.PostReacao} entity.
 */
public class PostReacaoDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private ZonedDateTime criacao;

    private ZonedDateTime ultimaEdicao;

    private Long versao;

    private String reacao;

    private Long perfilId;
    
    private String perfilNome;

    private Long postId;

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

    public String getReacao() {
        return reacao;
    }

    public void setReacao(String reacao) {
        this.reacao = reacao;
    }

    public Long getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Long perfilUsuarioId) {
        this.perfilId = perfilUsuarioId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    
    public String getPerfilNome() {
		return perfilNome;
	}

	public void setPerfilNome(String perfilNome) {
		this.perfilNome = perfilNome;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PostReacaoDTO postReacaoDTO = (PostReacaoDTO) o;
        if (postReacaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), postReacaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PostReacaoDTO{" +
            "id=" + getId() +
            ", criacao='" + getCriacao() + "'" +
            ", ultimaEdicao='" + getUltimaEdicao() + "'" +
            ", versao=" + getVersao() +
            ", reacao='" + getReacao() + "'" +
            ", perfilId=" + getPerfilId() +
            ", perfilNome=" + getPerfilNome() +
            ", postId=" + getPostId() +
            
            "}";
    }
}

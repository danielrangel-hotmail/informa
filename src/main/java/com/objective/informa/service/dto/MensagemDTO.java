package com.objective.informa.service.dto;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.objective.informa.domain.Mensagem} entity.
 */
public class MensagemDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long versao;

    private ZonedDateTime criacao;

    private ZonedDateTime ultimaEdicao;

    private String conteudo;

    private Boolean temConversa;

    private Long autorId;

    private Long postId;

    private Long conversaId;

    private String autorNome;

    private String autorEmail;

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

    public Boolean isTemConversa() {
        return temConversa;
    }

    public void setTemConversa(Boolean temConversa) {
        this.temConversa = temConversa;
    }

    public Long getAutorId() {
        return autorId;
    }

    public void setAutorId(Long userId) {
        this.autorId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getConversaId() {
        return conversaId;
    }

    public void setConversaId(Long mensagemId) {
        this.conversaId = mensagemId;
    }

    public String getAutorNome() {
        return autorNome;
    }

    public void setAutorNome(String autorNome) {
        this.autorNome = autorNome;
    }

    public String getAutorEmail() {
        return autorEmail;
    }

    public void setAutorEmail(String autorEmail) {
        this.autorEmail = autorEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MensagemDTO mensagemDTO = (MensagemDTO) o;
        if (mensagemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mensagemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MensagemDTO{" +
            "id=" + getId() +
            ", versao=" + getVersao() +
            ", criacao='" + getCriacao() + "'" +
            ", ultimaEdicao='" + getUltimaEdicao() + "'" +
            ", conteudo='" + getConteudo() + "'" +
            ", temConversa='" + isTemConversa() + "'" +
            ", autorId=" + getAutorId() +
            ", postId=" + getPostId() +
            ", conversaId=" + getConversaId() +
            "}";
    }
}

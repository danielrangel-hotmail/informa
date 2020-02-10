package com.objective.informa.service.dto;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.objective.informa.domain.PushSubscription} entity.
 */
public class PushSubscriptionDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long versao;

    private ZonedDateTime criacao;

    private String endpoint;

    private String key;

    private String auth;


    private Long perfilId;

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

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public Long getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Long perfilUsuarioId) {
        this.perfilId = perfilUsuarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PushSubscriptionDTO pushSubscriptionDTO = (PushSubscriptionDTO) o;
        if (pushSubscriptionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pushSubscriptionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PushSubscriptionDTO{" +
            "id=" + getId() +
            ", versao=" + getVersao() +
            ", criacao='" + getCriacao() + "'" +
            ", endpoint='" + getEndpoint() + "'" +
            ", key='" + getKey() + "'" +
            ", auth='" + getAuth() + "'" +
            ", perfilId=" + getPerfilId() +
            "}";
    }
}

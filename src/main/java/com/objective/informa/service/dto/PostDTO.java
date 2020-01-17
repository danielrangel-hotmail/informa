package com.objective.informa.service.dto;
import com.objective.informa.domain.Arquivo;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    private Boolean oficial;

    private List<ArquivoDTO> arquivos = new ArrayList<ArquivoDTO>();

    private List<LinkExternoDTO> linksExternos = new ArrayList<LinkExternoDTO>();

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

    public Boolean isOficial() {
        return oficial;
    }

    public void setOficial(Boolean oficial) {
        this.oficial = oficial;
    }

    public List<ArquivoDTO> getArquivos() {
        return arquivos;
    }

    public void setArquivos(List<ArquivoDTO> arquivos) {
        this.arquivos = arquivos;
    }

    public List<LinkExternoDTO> getLinksExternos() {
        return linksExternos;
    }

    public void setLinksExternos(
        List<LinkExternoDTO> linksExternos) {
        this.linksExternos = linksExternos;
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
            ", oficial=" + isOficial() +
            "}";
    }
}

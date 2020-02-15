package com.objective.informa.service.dto;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.ReadOnlyProperty;

/**
 * A DTO for the {@link com.objective.informa.domain.Post} entity.
 */
public class PostDTO implements Serializable {

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

    private String conteudo;

    private ZonedDateTime publicacao;

    private Long autorId;

    private Long grupoId;

    private String autorNome;

    private String autorEmail;

    private String grupoNome;

    private Boolean oficial;

    private Boolean removido;

    private Boolean arquivado;

    private ZonedDateTime momentoRemocao;

    private ZonedDateTime momentoArquivado;

    private Long removedorId;

    private Long arquivadorId;

    private Long numeroDeMensagens;
    
    private PostReacoesDTO reacoes;

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

    public String getAutorEmail() {
        return autorEmail;
    }

    public void setAutorEmail(String autorEmail) {
        this.autorEmail = autorEmail;
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

    public Long getNumeroDeMensagens() {
        return numeroDeMensagens;
    }

    public void setNumeroDeMensagens(Long numeroDeMensagens) {
        this.numeroDeMensagens = numeroDeMensagens;
    }

    

	public PostReacoesDTO getReacoes() {
		return reacoes;
	}

	public void setReacoes(PostReacoesDTO reacoes) {
		this.reacoes = reacoes;
	}

	
	public Boolean getRemovido() {
		return removido;
	}

	public void setRemovido(Boolean removido) {
		this.removido = removido;
	}

	public Boolean getArquivado() {
		return arquivado;
	}

	public void setArquivado(Boolean arquivado) {
		this.arquivado = arquivado;
	}

	public ZonedDateTime getMomentoRemocao() {
		return momentoRemocao;
	}

	public void setMomentoRemocao(ZonedDateTime momentoRemocao) {
		this.momentoRemocao = momentoRemocao;
	}

	public ZonedDateTime getMomentoArquivado() {
		return momentoArquivado;
	}

	public void setMomentoArquivado(ZonedDateTime momentoArquivado) {
		this.momentoArquivado = momentoArquivado;
	}

	public Long getRemovedorId() {
		return removedorId;
	}

	public void setRemovedorId(Long removedorId) {
		this.removedorId = removedorId;
	}

	public Long getArquivadorId() {
		return arquivadorId;
	}

	public void setArquivadorId(Long arquivadorId) {
		this.arquivadorId = arquivadorId;
	}

	public Boolean getOficial() {
		return oficial;
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
		return "PostDTO [id=" + id + ", versao=" + versao + ", criacao=" + criacao + ", ultimaEdicao=" + ultimaEdicao
				+ ", conteudo=" + conteudo + ", publicacao=" + publicacao + ", autorId=" + autorId + ", grupoId="
				+ grupoId + ", autorNome=" + autorNome + ", autorEmail=" + autorEmail + ", grupoNome=" + grupoNome
				+ ", oficial=" + oficial + ", removido=" + removido + ", arquivado=" + arquivado + ", momentoRemocao="
				+ momentoRemocao + ", momentoArquivado=" + momentoArquivado + ", removedorId=" + removedorId
				+ ", arquivadorId=" + arquivadorId + ", numeroDeMensagens=" + numeroDeMensagens + ", reacoes=" + reacoes
				+ ", arquivos=" + arquivos + ", linksExternos=" + linksExternos + "]";
	}


}


package com.objective.informa.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Post.
 */
@Entity
@Table(name = "post")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Version
    @Column(name = "versao")
    private Long versao;

    @Column(name = "criacao")
    private ZonedDateTime criacao;

    @Column(name = "ultima_edicao")
    private ZonedDateTime ultimaEdicao;

    @Column(name = "conteudo")
    private String conteudo;

    @Column(name = "oficial")
    private Boolean oficial;

    @Column(name = "removido")
    private Boolean removido;

    @Column(name = "arquivado")
    private Boolean arquivado;

    @Column(name = "publicacao")
    private ZonedDateTime publicacao;

    @Column(name = "momento_remocao")
    private ZonedDateTime momentoRemocao;

    @Column(name = "momento_arquivado")
    private ZonedDateTime momentoArquivado;

    @OneToMany(mappedBy = "post")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Arquivo> arquivos = new HashSet<>();

    @OneToMany(mappedBy = "post")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LinkExterno> linksExternos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("posts")
    private User autor;

    @ManyToOne(optional = true)
    @JsonIgnoreProperties("posts")
    private User removedor;

    @ManyToOne(optional = true)
    @JsonIgnoreProperties("posts")
    private User arquivador;

    @ManyToOne
    @JsonIgnoreProperties("posts")
    private Grupo grupo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersao() {
        return versao;
    }

    public Post versao(Long versao) {
        this.versao = versao;
        return this;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public ZonedDateTime getCriacao() {
        return criacao;
    }

    public Post criacao(ZonedDateTime criacao) {
        this.criacao = criacao;
        return this;
    }

    public void setCriacao(ZonedDateTime criacao) {
        this.criacao = criacao;
    }

    public ZonedDateTime getUltimaEdicao() {
        return ultimaEdicao;
    }

    public Post ultimaEdicao(ZonedDateTime ultimaEdicao) {
        this.ultimaEdicao = ultimaEdicao;
        return this;
    }

    public void setUltimaEdicao(ZonedDateTime ultimaEdicao) {
        this.ultimaEdicao = ultimaEdicao;
    }

    public String getConteudo() {
        return conteudo;
    }

    public Post conteudo(String conteudo) {
        this.conteudo = conteudo;
        return this;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Boolean isOficial() {
        return oficial;
    }

    public Post oficial(Boolean oficial) {
        this.oficial = oficial;
        return this;
    }

    public void setOficial(Boolean oficial) {
        this.oficial = oficial;
    }

    public ZonedDateTime getPublicacao() {
        return publicacao;
    }

    public Post publicacao(ZonedDateTime publicacao) {
        this.publicacao = publicacao;
        return this;
    }

    public void setPublicacao(ZonedDateTime publicacao) {
        this.publicacao = publicacao;
    }

    public Set<Arquivo> getArquivos() {
        return arquivos;
    }

    public Post arquivos(Set<Arquivo> arquivos) {
        this.arquivos = arquivos;
        return this;
    }

    public Post addArquivos(Arquivo arquivo) {
        this.arquivos.add(arquivo);
        arquivo.setPost(this);
        return this;
    }

    public Post removeArquivos(Arquivo arquivo) {
        this.arquivos.remove(arquivo);
        arquivo.setPost(null);
        return this;
    }

    public void setArquivos(Set<Arquivo> arquivos) {
        this.arquivos = arquivos;
    }

    public Set<LinkExterno> getLinksExternos() {
        return linksExternos;
    }

    public Post linksExternos(Set<LinkExterno> linkExternos) {
        this.linksExternos = linkExternos;
        return this;
    }

    public Post addLinksExternos(LinkExterno linkExterno) {
        this.linksExternos.add(linkExterno);
        linkExterno.setPost(this);
        return this;
    }

    public Post removeLinksExternos(LinkExterno linkExterno) {
        this.linksExternos.remove(linkExterno);
        linkExterno.setPost(null);
        return this;
    }

    public void setLinksExternos(Set<LinkExterno> linkExternos) {
        this.linksExternos = linkExternos;
    }

    public User getAutor() {
        return autor;
    }

    public Post autor(User user) {
        this.autor = user;
        return this;
    }

    public void setAutor(User user) {
        this.autor = user;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public Post grupo(Grupo grupo) {
        this.grupo = grupo;
        return this;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post)) {
            return false;
        }
        return id != null && id.equals(((Post) o).id);
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

	public User getRemovedor() {
		return removedor;
	}

	public void setRemovedor(User removedor) {
		this.removedor = removedor;
	}

	public User getArquivador() {
		return arquivador;
	}

	public void setArquivador(User arquivador) {
		this.arquivador = arquivador;
	}

	@Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		return "Post [id=" + id + ", versao=" + versao + ", criacao=" + criacao + ", ultimaEdicao=" + ultimaEdicao
				+ ", conteudo=" + conteudo + ", oficial=" + oficial + ", removido=" + removido + ", arquivado="
				+ arquivado + ", publicacao=" + publicacao + ", momentoRemocao=" + momentoRemocao
				+ ", momentoArquivado=" + momentoArquivado + ", autor=" + autor + ", removedor=" + removedor + ", arquivador=" + arquivador
				+ ", grupo=" + grupo + "]";
	}

    
    
}

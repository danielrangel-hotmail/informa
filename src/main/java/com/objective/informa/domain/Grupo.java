package com.objective.informa.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Grupo.
 */
@Entity
@Table(name = "grupo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Grupo implements Serializable {

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

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "formal")
    private Boolean formal;

    @Column(name = "opcional")
    private Boolean opcional;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @Column(name = "cabecalho_superior_cor")
    private String cabecalhoSuperiorCor;

    @Column(name = "cabecalho_inferior_cor")
    private String cabecalhoInferiorCor;

    @Column(name = "logo_fundo_cor")
    private String logoFundoCor;

    @OneToMany(mappedBy = "grupo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PerfilGrupo> usuarios = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "grupo_topicos",
               joinColumns = @JoinColumn(name = "grupo_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "topicos_id", referencedColumnName = "id"))
    private Set<Topico> topicos = new HashSet<>();

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

    public Grupo versao(Long versao) {
        this.versao = versao;
        return this;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public ZonedDateTime getCriacao() {
        return criacao;
    }

    public Grupo criacao(ZonedDateTime criacao) {
        this.criacao = criacao;
        return this;
    }

    public void setCriacao(ZonedDateTime criacao) {
        this.criacao = criacao;
    }

    public ZonedDateTime getUltimaEdicao() {
        return ultimaEdicao;
    }

    public Grupo ultimaEdicao(ZonedDateTime ultimaEdicao) {
        this.ultimaEdicao = ultimaEdicao;
        return this;
    }

    public void setUltimaEdicao(ZonedDateTime ultimaEdicao) {
        this.ultimaEdicao = ultimaEdicao;
    }

    public String getNome() {
        return nome;
    }

    public Grupo nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Grupo descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isFormal() {
        return formal;
    }

    public Grupo formal(Boolean formal) {
        this.formal = formal;
        return this;
    }

    public void setFormal(Boolean formal) {
        this.formal = formal;
    }

    public Boolean isOpcional() {
        return opcional;
    }

    public Grupo opcional(Boolean opcional) {
        this.opcional = opcional;
        return this;
    }

    public void setOpcional(Boolean opcional) {
        this.opcional = opcional;
    }

    public byte[] getLogo() {
        return logo;
    }

    public Grupo logo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public Grupo logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public String getCabecalhoSuperiorCor() {
        return cabecalhoSuperiorCor;
    }

    public Grupo cabecalhoSuperiorCor(String cabecalhoSuperiorCor) {
        this.cabecalhoSuperiorCor = cabecalhoSuperiorCor;
        return this;
    }

    public void setCabecalhoSuperiorCor(String cabecalhoSuperiorCor) {
        this.cabecalhoSuperiorCor = cabecalhoSuperiorCor;
    }

    public String getCabecalhoInferiorCor() {
        return cabecalhoInferiorCor;
    }

    public Grupo cabecalhoInferiorCor(String cabecalhoInferiorCor) {
        this.cabecalhoInferiorCor = cabecalhoInferiorCor;
        return this;
    }

    public void setCabecalhoInferiorCor(String cabecalhoInferiorCor) {
        this.cabecalhoInferiorCor = cabecalhoInferiorCor;
    }

    public String getLogoFundoCor() {
        return logoFundoCor;
    }

    public Grupo logoFundoCor(String logoFundoCor) {
        this.logoFundoCor = logoFundoCor;
        return this;
    }

    public void setLogoFundoCor(String logoFundoCor) {
        this.logoFundoCor = logoFundoCor;
    }

    public Set<PerfilGrupo> getUsuarios() {
        return usuarios;
    }

    public Grupo usuarios(Set<PerfilGrupo> perfilGrupos) {
        this.usuarios = perfilGrupos;
        return this;
    }

    public Grupo addUsuarios(PerfilGrupo perfilGrupo) {
        this.usuarios.add(perfilGrupo);
        perfilGrupo.setGrupo(this);
        return this;
    }

    public Grupo removeUsuarios(PerfilGrupo perfilGrupo) {
        this.usuarios.remove(perfilGrupo);
        perfilGrupo.setGrupo(null);
        return this;
    }

    public void setUsuarios(Set<PerfilGrupo> perfilGrupos) {
        this.usuarios = perfilGrupos;
    }

    public Set<Topico> getTopicos() {
        return topicos;
    }

    public Grupo topicos(Set<Topico> topicos) {
        this.topicos = topicos;
        return this;
    }

    public Grupo addTopicos(Topico topico) {
        this.topicos.add(topico);
        topico.getGrupos().add(this);
        return this;
    }

    public Grupo removeTopicos(Topico topico) {
        this.topicos.remove(topico);
        topico.getGrupos().remove(this);
        return this;
    }

    public void setTopicos(Set<Topico> topicos) {
        this.topicos = topicos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Grupo)) {
            return false;
        }
        return id != null && id.equals(((Grupo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Grupo{" +
            "id=" + getId() +
            ", versao=" + getVersao() +
            ", criacao='" + getCriacao() + "'" +
            ", ultimaEdicao='" + getUltimaEdicao() + "'" +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", formal='" + isFormal() + "'" +
            ", opcional='" + isOpcional() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            ", cabecalhoSuperiorCor='" + getCabecalhoSuperiorCor() + "'" +
            ", cabecalhoInferiorCor='" + getCabecalhoInferiorCor() + "'" +
            ", logoFundoCor='" + getLogoFundoCor() + "'" +
            "}";
    }
}

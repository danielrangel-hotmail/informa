package com.objective.informa.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Topico.
 */
@Entity
@Table(name = "topico")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Topico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "versao")
    private Long versao;

    @Column(name = "nome")
    private String nome;

    @OneToMany(mappedBy = "substituto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Topico> substituidos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("topicos")
    private User autor;

    @ManyToOne
    @JsonIgnoreProperties("substituidos")
    private Topico substituto;

    @ManyToMany(mappedBy = "topicos")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Grupo> grupos = new HashSet<>();

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

    public Topico versao(Long versao) {
        this.versao = versao;
        return this;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public String getNome() {
        return nome;
    }

    public Topico nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Topico> getSubstituidos() {
        return substituidos;
    }

    public Topico substituidos(Set<Topico> topicos) {
        this.substituidos = topicos;
        return this;
    }

    public Topico addSubstituidos(Topico topico) {
        this.substituidos.add(topico);
        topico.setSubstituto(this);
        return this;
    }

    public Topico removeSubstituidos(Topico topico) {
        this.substituidos.remove(topico);
        topico.setSubstituto(null);
        return this;
    }

    public void setSubstituidos(Set<Topico> topicos) {
        this.substituidos = topicos;
    }

    public User getAutor() {
        return autor;
    }

    public Topico autor(User user) {
        this.autor = user;
        return this;
    }

    public void setAutor(User user) {
        this.autor = user;
    }

    public Topico getSubstituto() {
        return substituto;
    }

    public Topico substituto(Topico topico) {
        this.substituto = topico;
        return this;
    }

    public void setSubstituto(Topico topico) {
        this.substituto = topico;
    }

    public Set<Grupo> getGrupos() {
        return grupos;
    }

    public Topico grupos(Set<Grupo> grupos) {
        this.grupos = grupos;
        return this;
    }

    public Topico addGrupos(Grupo grupo) {
        this.grupos.add(grupo);
        grupo.getTopicos().add(this);
        return this;
    }

    public Topico removeGrupos(Grupo grupo) {
        this.grupos.remove(grupo);
        grupo.getTopicos().remove(this);
        return this;
    }

    public void setGrupos(Set<Grupo> grupos) {
        this.grupos = grupos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Topico)) {
            return false;
        }
        return id != null && id.equals(((Topico) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Topico{" +
            "id=" + getId() +
            ", versao=" + getVersao() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}

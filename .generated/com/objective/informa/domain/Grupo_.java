package com.objective.informa.domain;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Grupo.class)
public abstract class Grupo_ {

	public static volatile SingularAttribute<Grupo, Boolean> formal;
	public static volatile SingularAttribute<Grupo, ZonedDateTime> ultimaEdicao;
	public static volatile SingularAttribute<Grupo, Boolean> opcional;
	public static volatile SingularAttribute<Grupo, String> nome;
	public static volatile SingularAttribute<Grupo, Long> id;
	public static volatile SingularAttribute<Grupo, ZonedDateTime> criacao;
	public static volatile SetAttribute<Grupo, PerfilGrupo> usuarios;
	public static volatile SingularAttribute<Grupo, Long> versao;
	public static volatile SingularAttribute<Grupo, String> descricao;

	public static final String FORMAL = "formal";
	public static final String ULTIMA_EDICAO = "ultimaEdicao";
	public static final String OPCIONAL = "opcional";
	public static final String NOME = "nome";
	public static final String ID = "id";
	public static final String CRIACAO = "criacao";
	public static final String USUARIOS = "usuarios";
	public static final String VERSAO = "versao";
	public static final String DESCRICAO = "descricao";

}


package com.objective.informa.domain;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PerfilGrupo.class)
public abstract class PerfilGrupo_ {

	public static volatile SingularAttribute<PerfilGrupo, ZonedDateTime> ultimaEdicao;
	public static volatile SingularAttribute<PerfilGrupo, Boolean> favorito;
	public static volatile SingularAttribute<PerfilGrupo, Grupo> grupo;
	public static volatile SingularAttribute<PerfilGrupo, Boolean> notifica;
	public static volatile SingularAttribute<PerfilGrupo, Long> id;
	public static volatile SingularAttribute<PerfilGrupo, ZonedDateTime> criacao;
	public static volatile SingularAttribute<PerfilGrupo, Long> versao;
	public static volatile SingularAttribute<PerfilGrupo, PerfilUsuario> perfil;

	public static final String ULTIMA_EDICAO = "ultimaEdicao";
	public static final String FAVORITO = "favorito";
	public static final String GRUPO = "grupo";
	public static final String NOTIFICA = "notifica";
	public static final String ID = "id";
	public static final String CRIACAO = "criacao";
	public static final String VERSAO = "versao";
	public static final String PERFIL = "perfil";

}


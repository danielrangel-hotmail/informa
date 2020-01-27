package com.objective.informa.domain;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PerfilUsuario.class)
public abstract class PerfilUsuario_ {

	public static volatile SingularAttribute<PerfilUsuario, LocalDate> entradaNaEmpresa;
	public static volatile SingularAttribute<PerfilUsuario, String> skype;
	public static volatile SingularAttribute<PerfilUsuario, LocalDate> nascimento;
	public static volatile SetAttribute<PerfilUsuario, PushSubscription> subscriptions;
	public static volatile SingularAttribute<PerfilUsuario, ZonedDateTime> ultimaEdicao;
	public static volatile SingularAttribute<PerfilUsuario, User> usuario;
	public static volatile SetAttribute<PerfilUsuario, PerfilGrupo> grupos;
	public static volatile SingularAttribute<PerfilUsuario, Long> id;
	public static volatile SingularAttribute<PerfilUsuario, ZonedDateTime> criacao;
	public static volatile SingularAttribute<PerfilUsuario, Long> versao;
	public static volatile SingularAttribute<PerfilUsuario, LocalDate> saidaDaEmpresa;

	public static final String ENTRADA_NA_EMPRESA = "entradaNaEmpresa";
	public static final String SKYPE = "skype";
	public static final String NASCIMENTO = "nascimento";
	public static final String SUBSCRIPTIONS = "subscriptions";
	public static final String ULTIMA_EDICAO = "ultimaEdicao";
	public static final String USUARIO = "usuario";
	public static final String GRUPOS = "grupos";
	public static final String ID = "id";
	public static final String CRIACAO = "criacao";
	public static final String VERSAO = "versao";
	public static final String SAIDA_DA_EMPRESA = "saidaDaEmpresa";

}


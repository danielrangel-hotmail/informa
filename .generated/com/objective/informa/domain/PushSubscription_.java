package com.objective.informa.domain;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PushSubscription.class)
public abstract class PushSubscription_ {

	public static volatile SingularAttribute<PushSubscription, String> endpoint;
	public static volatile SingularAttribute<PushSubscription, String> auth;
	public static volatile SingularAttribute<PushSubscription, Long> id;
	public static volatile SingularAttribute<PushSubscription, ZonedDateTime> criacao;
	public static volatile SingularAttribute<PushSubscription, Long> versao;
	public static volatile SingularAttribute<PushSubscription, String> key;
	public static volatile SingularAttribute<PushSubscription, PerfilUsuario> perfil;

	public static final String ENDPOINT = "endpoint";
	public static final String AUTH = "auth";
	public static final String ID = "id";
	public static final String CRIACAO = "criacao";
	public static final String VERSAO = "versao";
	public static final String KEY = "key";
	public static final String PERFIL = "perfil";

}


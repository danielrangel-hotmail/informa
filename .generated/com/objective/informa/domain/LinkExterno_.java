package com.objective.informa.domain;

import com.objective.informa.domain.enumeration.LinkTipo;
import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LinkExterno.class)
public abstract class LinkExterno_ {

	public static volatile SingularAttribute<LinkExterno, LinkTipo> tipo;
	public static volatile SingularAttribute<LinkExterno, Post> post;
	public static volatile SingularAttribute<LinkExterno, ZonedDateTime> ultimaEdicao;
	public static volatile SingularAttribute<LinkExterno, Mensagem> mensagem;
	public static volatile SingularAttribute<LinkExterno, String> link;
	public static volatile SingularAttribute<LinkExterno, User> usuario;
	public static volatile SingularAttribute<LinkExterno, Long> id;
	public static volatile SingularAttribute<LinkExterno, ZonedDateTime> criacao;
	public static volatile SingularAttribute<LinkExterno, Long> versao;

	public static final String TIPO = "tipo";
	public static final String POST = "post";
	public static final String ULTIMA_EDICAO = "ultimaEdicao";
	public static final String MENSAGEM = "mensagem";
	public static final String LINK = "link";
	public static final String USUARIO = "usuario";
	public static final String ID = "id";
	public static final String CRIACAO = "criacao";
	public static final String VERSAO = "versao";

}


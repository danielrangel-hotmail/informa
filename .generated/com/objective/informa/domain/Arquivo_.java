package com.objective.informa.domain;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Arquivo.class)
public abstract class Arquivo_ {

	public static volatile SingularAttribute<Arquivo, String> tipo;
	public static volatile SingularAttribute<Arquivo, Post> post;
	public static volatile SingularAttribute<Arquivo, ZonedDateTime> ultimaEdicao;
	public static volatile SingularAttribute<Arquivo, Mensagem> mensagem;
	public static volatile SingularAttribute<Arquivo, String> colecao;
	public static volatile SingularAttribute<Arquivo, String> link;
	public static volatile SingularAttribute<Arquivo, String> nome;
	public static volatile SingularAttribute<Arquivo, Boolean> uploadConfirmado;
	public static volatile SingularAttribute<Arquivo, User> usuario;
	public static volatile SingularAttribute<Arquivo, Long> id;
	public static volatile SingularAttribute<Arquivo, ZonedDateTime> criacao;
	public static volatile SingularAttribute<Arquivo, Long> versao;

	public static final String TIPO = "tipo";
	public static final String POST = "post";
	public static final String ULTIMA_EDICAO = "ultimaEdicao";
	public static final String MENSAGEM = "mensagem";
	public static final String COLECAO = "colecao";
	public static final String LINK = "link";
	public static final String NOME = "nome";
	public static final String UPLOAD_CONFIRMADO = "uploadConfirmado";
	public static final String USUARIO = "usuario";
	public static final String ID = "id";
	public static final String CRIACAO = "criacao";
	public static final String VERSAO = "versao";

}


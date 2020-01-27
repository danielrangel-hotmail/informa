package com.objective.informa.domain;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Mensagem.class)
public abstract class Mensagem_ {

	public static volatile SetAttribute<Mensagem, LinkExterno> linksExternos;
	public static volatile SingularAttribute<Mensagem, String> conteudo;
	public static volatile SingularAttribute<Mensagem, Post> post;
	public static volatile SingularAttribute<Mensagem, Mensagem> conversa;
	public static volatile SingularAttribute<Mensagem, ZonedDateTime> ultimaEdicao;
	public static volatile SingularAttribute<Mensagem, Boolean> temConversa;
	public static volatile SetAttribute<Mensagem, Arquivo> arquivos;
	public static volatile SingularAttribute<Mensagem, Long> id;
	public static volatile SingularAttribute<Mensagem, ZonedDateTime> criacao;
	public static volatile SingularAttribute<Mensagem, Long> versao;
	public static volatile SingularAttribute<Mensagem, User> autor;

	public static final String LINKS_EXTERNOS = "linksExternos";
	public static final String CONTEUDO = "conteudo";
	public static final String POST = "post";
	public static final String CONVERSA = "conversa";
	public static final String ULTIMA_EDICAO = "ultimaEdicao";
	public static final String TEM_CONVERSA = "temConversa";
	public static final String ARQUIVOS = "arquivos";
	public static final String ID = "id";
	public static final String CRIACAO = "criacao";
	public static final String VERSAO = "versao";
	public static final String AUTOR = "autor";

}


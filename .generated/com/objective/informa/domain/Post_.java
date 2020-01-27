package com.objective.informa.domain;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Post.class)
public abstract class Post_ {

	public static volatile SingularAttribute<Post, ZonedDateTime> publicacao;
	public static volatile SetAttribute<Post, LinkExterno> linksExternos;
	public static volatile SingularAttribute<Post, String> conteudo;
	public static volatile SingularAttribute<Post, ZonedDateTime> ultimaEdicao;
	public static volatile SetAttribute<Post, Arquivo> arquivos;
	public static volatile SingularAttribute<Post, Grupo> grupo;
	public static volatile SingularAttribute<Post, Long> id;
	public static volatile SingularAttribute<Post, Boolean> oficial;
	public static volatile SingularAttribute<Post, ZonedDateTime> criacao;
	public static volatile SingularAttribute<Post, Long> versao;
	public static volatile SingularAttribute<Post, User> autor;

	public static final String PUBLICACAO = "publicacao";
	public static final String LINKS_EXTERNOS = "linksExternos";
	public static final String CONTEUDO = "conteudo";
	public static final String ULTIMA_EDICAO = "ultimaEdicao";
	public static final String ARQUIVOS = "arquivos";
	public static final String GRUPO = "grupo";
	public static final String ID = "id";
	public static final String OFICIAL = "oficial";
	public static final String CRIACAO = "criacao";
	public static final String VERSAO = "versao";
	public static final String AUTOR = "autor";

}


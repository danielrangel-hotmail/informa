package com.objective.informa.service.dto;

import java.util.List;

public class PostReacoesDTO {

	private List<PostReacaoDTO> reacoes;
	private PostReacaoDTO reacaoLogado;
	
	public PostReacoesDTO(List<PostReacaoDTO> reacoes, PostReacaoDTO reacaoLogado) {
		super();
		this.reacoes = reacoes;
		this.reacaoLogado = reacaoLogado;
	}

	public List<PostReacaoDTO> getReacoes() {
		return reacoes;
	}

	public void setReacoes(List<PostReacaoDTO> reacoes) {
		this.reacoes = reacoes;
	}

	public PostReacaoDTO getReacaoLogado() {
		return reacaoLogado;
	}

	public void setReacaoLogado(PostReacaoDTO reacaoLogado) {
		this.reacaoLogado = reacaoLogado;
	}

	@Override
	public String toString() {
		return "PostReacoesDTO [reacoes=" + reacoes + ", reacaoLogado=" + reacaoLogado + "]";
	}
	
	
}

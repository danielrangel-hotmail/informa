package com.objective.informa.service.environments;

import org.springframework.stereotype.Service;

import com.objective.insistence.layer.environment.InsistenceEnvironment;

@Service
public class ZeradoEnvironment implements InsistenceEnvironment{

	@Override
	public void execute() {
		// Não faz nada mesmo
	}

	@Override
	public String getName() {
		return "Zerado";
	}

}

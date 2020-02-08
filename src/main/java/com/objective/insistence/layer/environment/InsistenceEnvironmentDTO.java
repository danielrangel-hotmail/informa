package com.objective.insistence.layer.environment;

public class InsistenceEnvironmentDTO {

	public static InsistenceEnvironmentDTO toDTO(InsistenceEnvironment i) {
		return new InsistenceEnvironmentDTO(i.getName());
	}
	
	private String name;

	public InsistenceEnvironmentDTO(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}

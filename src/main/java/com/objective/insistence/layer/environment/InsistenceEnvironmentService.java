package com.objective.insistence.layer.environment;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.objective.insistence.layer.jdbc.InsistenceLayerService;

@Service
public class InsistenceEnvironmentService {
	
	private final List<InsistenceEnvironment> insistenceEnvironments;
	private final Map<String, InsistenceEnvironment> insistenceEnvironmentsMap;
	private final InsistenceLayerService insistenceLayerService;

	public InsistenceEnvironmentService(List<InsistenceEnvironment> insistenceEnvironments, InsistenceLayerService insistenceLayerService) {
		super();
		this.insistenceLayerService = insistenceLayerService;
		this.insistenceEnvironments = insistenceEnvironments;
		this.insistenceEnvironmentsMap = insistenceEnvironments.stream().collect(Collectors.toMap(InsistenceEnvironment::getName, e -> e));
	}

	public void executeEnvironment(String environmentName) {
		InsistenceEnvironment insistenceEnvironment = insistenceEnvironmentsMap.get(environmentName);
		if (insistenceEnvironment  == null) throw new RuntimeException(String.format("Environment %s doesn't exists", environmentName));
		if (insistenceLayerService.isActive()) insistenceLayerService.removeAllSavepoints();
		insistenceLayerService.setNewSavePoint();
		insistenceEnvironment.execute();
		insistenceLayerService.setNewSavePoint();
	}

	public void resetEnvironments() {
		insistenceLayerService.removeAllSavepoints();
	}
	
	public List<InsistenceEnvironmentDTO> getInsistenceEnvironments() {
		return this.insistenceEnvironments.stream().
				map(InsistenceEnvironmentDTO::toDTO)
				.collect(Collectors.toList());
	}
}

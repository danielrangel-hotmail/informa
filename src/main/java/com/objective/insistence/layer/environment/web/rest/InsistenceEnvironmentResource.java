package com.objective.insistence.layer.environment.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.objective.insistence.layer.environment.InsistenceEnvironmentDTO;
import com.objective.insistence.layer.environment.InsistenceEnvironmentService;
import com.objective.insistence.layer.web.rest.InsistenceLayerResource;

@RestController
@RequestMapping("/api")
@Profile("dev")
public class InsistenceEnvironmentResource {

    private final Logger log = LoggerFactory.getLogger(InsistenceLayerResource.class);
    
    private final InsistenceEnvironmentService insistenceEnvironmentService;

	public InsistenceEnvironmentResource(InsistenceEnvironmentService insistenceEnvironmentService) {
		super();
		this.insistenceEnvironmentService = insistenceEnvironmentService;
	}
    
  @GetMapping("/insistence-environment/set/{name}")
  public ResponseEntity<InsistenceEnvironmentStatusDTO> setNewSavePoint(@PathVariable String name) {
      log.debug("REST request to run environment %s", name);
      this.insistenceEnvironmentService.executeEnvironment(name);
      return ResponseEntity.ok().headers(null).body(new InsistenceEnvironmentStatusDTO("OK"));
  }

  @GetMapping("/insistence-environment/resetAll")
  public ResponseEntity<InsistenceEnvironmentStatusDTO> resetAll() {
      log.debug("REST request to reset environments");
      this.insistenceEnvironmentService.resetEnvironments();
      return ResponseEntity.ok().body(new InsistenceEnvironmentStatusDTO("OK"));
  }

  @GetMapping("/insistence-environment")
  public List<InsistenceEnvironmentDTO> getAll() {
      log.debug("REST request to get environments");
      return this.insistenceEnvironmentService.getInsistenceEnvironments();
  }

}


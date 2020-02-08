package com.objective.insistence.layer.web.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.objective.insistence.layer.jdbc.InsistenceLayerService;

@RestController
@RequestMapping("/api")
@Profile("dev")
public class InsistenceLayerResource {

    private final Logger log = LoggerFactory.getLogger(InsistenceLayerResource.class);
    
    private final InsistenceLayerService insistenceLayerService;

	public InsistenceLayerResource(InsistenceLayerService insistenceLayerService) {
		super();
		this.insistenceLayerService = insistenceLayerService;
	}
    
    @GetMapping("/insistence-layer/new-save-point")
    public List<SavepointDTO> setNewSavePoint() {
        log.debug("REST request to new save point");
        insistenceLayerService.setNewSavePoint();
        return savepoints(); 
    }

    @GetMapping("/insistence-layer/reset-last-savepoint")
    public List<SavepointDTO> resetLastSavePoint() {
        log.debug("REST request to reset last savepoint");
        insistenceLayerService.resetLastSavePoint();
        return savepoints(); 
    }

    @GetMapping("/insistence-layer/remove-all-savepoints")
    public List<SavepointDTO> removeAllSavepoints() {
        log.debug("REST request to remove all savepoints");
        insistenceLayerService.removeAllSavepoints();        
        return savepoints(); 
    }

    
    private List<SavepointDTO> savepoints() {
    	return insistenceLayerService.getSavePointStack().stream()
    			.map(SavepointDTO::toDto)
    			.collect(Collectors.toList());
    }
    
}




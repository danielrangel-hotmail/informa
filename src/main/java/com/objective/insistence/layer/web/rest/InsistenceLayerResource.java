package com.objective.insistence.layer.web.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.objective.insistence.layer.jdbc.InsistenceLayerService;

@RestController
@RequestMapping("/api")
public class InsistenceLayerResource {

    private final Logger log = LoggerFactory.getLogger(InsistenceLayerResource.class);
    
    private final InsistenceLayerService insistenceLayerService;

	public InsistenceLayerResource(InsistenceLayerService insistenceLayerService) {
		super();
		this.insistenceLayerService = insistenceLayerService;
	}
    
    @GetMapping("/insistence-layer/new-save-point")
    public List<SavepointDTO> setNewSavePoint() {
        log.debug("REST request to get all Topicos");
        insistenceLayerService.setNewSavePoint();
        return savepoints(); 
    }

    @GetMapping("/insistence-layer/reset-last-savepoint")
    public List<SavepointDTO> resetLastSavePoint() {
        log.debug("REST request to get all Topicos");
        insistenceLayerService.resetLastSavePoint();
        return savepoints(); 
    }

    @GetMapping("/insistence-layer/remove-all-savepoints")
    public List<SavepointDTO> removeAllSavepoints() {
        log.debug("REST request to get all Topicos");
        insistenceLayerService.removeAllSavepoints();        
        return savepoints(); 
    }

    
    private List<SavepointDTO> savepoints() {
    	return insistenceLayerService.getSavePointStack().stream()
    			.map(SavepointDTO::toDto)
    			.collect(Collectors.toList());
    }
    
}




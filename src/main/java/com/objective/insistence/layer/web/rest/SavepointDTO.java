package com.objective.insistence.layer.web.rest;

import java.sql.SQLException;
import java.sql.Savepoint;


public class SavepointDTO {

	public String name;
	
	public static SavepointDTO toDto(Savepoint savepoint) {
		SavepointDTO savepointDTO = new SavepointDTO();
		try {
			savepointDTO.name = savepoint.getSavepointName();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return savepointDTO;
	}


}

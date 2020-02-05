package com.objective.insistence.layer.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;

import com.zaxxer.hikari.HikariConfig;

public class InsistenceLayerHikariDataSource extends com.zaxxer.hikari.HikariDataSource {

	private static Properties properties;
	static {
		properties = new Properties();
	      properties.setProperty("datasource.hikari.poolName", "Hikari");
	      properties.setProperty("datasource.hikari.auto-commit", "false");
	}
	

	@Autowired
	private InsistenceLayerService insistenceLayerService;

	@Override
	public Connection getConnection() throws SQLException {
		if (insistenceLayerService.isActive()) {
			return insistenceLayerService.getMasterConnection();
		}
		InsistenceLayerConnection newConnection = new InsistenceLayerConnection(super.getConnection(), insistenceLayerService);
		if (insistenceLayerService.needNewMasterConnection()) {
			insistenceLayerService.setMasterConnection(newConnection); 
		} else {
			insistenceLayerService.addConnection(newConnection);
		}
		return newConnection;
	}

	public InsistenceLayerHikariDataSource() {
		super();
		this.setPoolName("Hikari");
		this.setAutoCommit(false);
	}

	public InsistenceLayerHikariDataSource(HikariConfig configuration) {
		super(configuration);
	}

	
}

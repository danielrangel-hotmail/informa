package com.objective.insistence.layer.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

public class InsistenceLayerConnection extends ConnectionDelegate {

//    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

	@Autowired
	private final InsistenceLayerService insistenceLayerService;
	
	private Connection normalDelegate;

	public InsistenceLayerConnection(Connection delegate, InsistenceLayerService insistenceLayerService) {
		super(delegate);
		this.normalDelegate = delegate;
		this.insistenceLayerService = insistenceLayerService;
	}


	public void forceClose() throws SQLException {
		delegate.close();
	}

	public void forceRollback() throws SQLException {
		delegate.rollback();
	}
	
	
	@Override
	public void commit() throws SQLException {
		if (insistenceLayerService.isActive()) {
//			insistenceLayerService.setNewSavePoint();
		} else {
			delegate.commit();
		}
	}

	@Override
	public void rollback() throws SQLException {
		if (insistenceLayerService.isActive()) {
//			insistenceLayerService.resetLastSavePoint();
		} else {
			delegate.rollback();
		}

	}
	
	@Override
	public void close() throws SQLException {
		if (this == insistenceLayerService.getMasterConnection()) {
			return;
		}
		if (!insistenceLayerService.isActive()) {
			delegate.close();
		} 
	}


	public void delegateMasterConnection(InsistenceLayerConnection masterConnection) {
		this.delegate = masterConnection;
				
	}

	public void delegateNormalConnection() {
		this.delegate = normalDelegate;
				
	}
}
package com.objective.insistence.layer.jdbc;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class InsistenceLayerService {

	private List<InsistenceLayerConnection> connections = new ArrayList<InsistenceLayerConnection>();
	
	private InsistenceLayerConnection masterConnection;
	
	private Stack<Savepoint> savePointStack = new Stack<Savepoint>();
	
	private final Logger log = LoggerFactory.getLogger(InsistenceLayerService.class);
	

	public int count() {
		synchronized (this) {
			return savePointStack.size();
		}
	}

	public boolean isActive() {
		return !this.savePointStack.isEmpty();
	}
	
	public void setNewSavePoint() {
		String savepoint = "SAVE_POINT_" + savePointStack.size();
		 setNewSavePoint(savepoint);
	}
	
	public void setNewSavePoint(String savepoint) {
		synchronized (this) {
			if (!TransactionSynchronizationManager.isSynchronizationActive())
				TransactionSynchronizationManager.initSynchronization();
			try {
				masterConnection.setAutoCommit(false);
				Savepoint sp = masterConnection.setSavepoint(savepoint);
				if (savePointStack.isEmpty()) {
					this.connections.forEach(conn -> conn.delegateMasterConnection(masterConnection));
				}
				savePointStack.push(sp);

				String tag = "New save point : " + sp.getSavepointName();
				log.debug(tag);

			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void resetLastSavePoint() {
		synchronized (this) {
			if (savePointStack.isEmpty())
				throw new RuntimeException("no save point was found");

			if (savePointStack.size() == 1) { 
				removeAllSavepoints();
				return;
			}
			try {

				Savepoint sp = savePointStack.pop();
				String savepointName = sp.getSavepointName();
				masterConnection.rollback(sp);
				String tag = "Rollback save point : " + savepointName;
				log.debug(tag);

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}


	public void removeAllSavepoints() {
		synchronized (this) {
			if (savePointStack.isEmpty()) return;
			try {
				masterConnection.forceRollback();
				savePointStack.clear();
				this.connections.forEach(conn -> conn.delegateNormalConnection());
				String tag = "Rollback all save points";
				log.debug(tag);

			} catch (Exception e) {
				throw new RuntimeException(e);
			} 
		}
	}

	public Stack<Savepoint> getSavePointStack() {
		return savePointStack;
	}
	
	public void addConnection(InsistenceLayerConnection conn) {
		synchronized (this) {
			this.connections.add(conn);
			if (isActive()) {
				conn.delegateMasterConnection(masterConnection);
			}
		}
	}

	public InsistenceLayerConnection getMasterConnection() {
		return masterConnection;
	}

	public void setMasterConnection(InsistenceLayerConnection masterConnection) {
		this.masterConnection = masterConnection;
	}
	
	public boolean needNewMasterConnection() {
		try {
			return (this.masterConnection == null) || (this.masterConnection.isClosed());
		} catch (SQLException e) {
			return true;
		}
	}
	
}


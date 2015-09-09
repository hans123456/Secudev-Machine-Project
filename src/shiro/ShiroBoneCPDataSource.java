package shiro;

import com.jolbox.bonecp.BoneCPDataSource;

import models.ConnectionFactory;

public class ShiroBoneCPDataSource extends BoneCPDataSource {

	private static final long serialVersionUID = 1L;

	public ShiroBoneCPDataSource() {
		this.setDriverClass(ConnectionFactory.getDriverClass());
		this.setJdbcUrl(ConnectionFactory.getConnectionUrl());
		this.setUsername(ConnectionFactory.getUsername());
		this.setPassword(ConnectionFactory.getPassword());
		this.setMaxConnectionsPerPartition(10);
	}

}

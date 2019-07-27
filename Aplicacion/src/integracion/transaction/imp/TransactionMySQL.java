package integracion.transaction.imp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import integracion.transaction.Transaction;


public class TransactionMySQL implements Transaction {

	private Connection connection;
	
	public TransactionMySQL(){
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/fruteria_mr_pressman?" +
                    "user=pressman&password=1234");
		} catch (SQLException e) {
			connection = null;
		}
	}

	public void start() {
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	public void commit() {
		try {
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	public void rollback() {
		try {
			connection.rollback();
			connection.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	public Object getResource() {
		return connection;
	}
}
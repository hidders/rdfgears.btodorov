package nl.tudelft.wis.datamanagement.backend.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import nl.tudelft.wis.datamanagement.backend.Config;

public class MySQLQueryExecutor {

    public void execute(Iterable<String> statements) throws Exception {
	//TODO connection pool
	
	System.out.println("Executing statements");
	// This will load the MySQL driver, each DB has its own driver
	Class.forName("com.mysql.jdbc.Driver");
	// Setup the connection with the DB
	Connection connection = DriverManager.getConnection(Config.getDbURL(),
		Config.getDbUsername(), Config.getDbPassword());

	connection.setAutoCommit(false);

	try {
	    Statement statement = connection.createStatement();

	    for (String s : statements) {
		System.out.println(s);
		statement.addBatch(s);
	    }

	    statement.executeBatch();

	    connection.commit();
	} catch (SQLException e) {
	    e.printStackTrace();
	    connection.rollback();
	}finally{
	    connection.close();
	}
    }
}

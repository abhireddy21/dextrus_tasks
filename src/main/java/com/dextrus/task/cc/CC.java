package com.dextrus.task.cc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CC {

	public static Connection connectToDB() {
		Connection connection = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection("jdbc:sqlserver://3.88.173.201;encrypt=false", "dextrus", "Dextrus!1");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}

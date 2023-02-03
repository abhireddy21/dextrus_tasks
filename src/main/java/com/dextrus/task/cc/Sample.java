package com.dextrus.task.cc;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.dextrus.task.service.ConnectionService;

public class Sample {

	public void describetable() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection("jdbc:sqlserver://3.88.173.201;encrypt=false",
					"dextrus", "Dextrus!1");
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("use Northwind; select top 5 * from dbo.customers;");
			ResultSetMetaData data = resultSet.getMetaData();

			int cCount = data.getColumnCount();

			while (resultSet.next()) {
				for (int j = 1; j <= cCount; j++) {
					System.out.println(data.getColumnName(j) + "-->" + resultSet.getString(j));
				}
				System.out.println("--------------");
			}

			// }

		} catch (ClassNotFoundException |

				SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	public static void main(String[] args) {
//		Sample sample = new Sample();
//		sample.describetable();
//	}
}

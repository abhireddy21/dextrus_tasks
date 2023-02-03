package com.dextrus.task.cc;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.dextrus.task.service.ConnectionService;

public class Sample {

		public void describetable() {
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				Connection connection = DriverManager.getConnection("jdbc:sqlserver://3.88.173.201;encrypt=false","dextrus","Dextrus!1");
				PreparedStatement preparedStatement = connection.prepareStatement("use BSP; select * from dbo.Accounts");
				ResultSet resultset = preparedStatement.executeQuery();
				ResultSetMetaData data= resultset.getMetaData();
				int col = data.getColumnCount();
				for(int i = 1 ; i<=col;i++) {
				System.out.println(data.getColumnName(i)+"-->"+data.getColumnTypeName(i));
				}

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public static void main(String[] args) {
			Sample sample = new Sample();
			sample.describetable();
	}
}

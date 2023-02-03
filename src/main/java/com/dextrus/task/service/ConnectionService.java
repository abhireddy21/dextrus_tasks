package com.dextrus.task.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dextrus.task.cc.CC;
import com.dextrus.task.model.ConnectionRequest;
import com.dextrus.task.model.TableDescription;
import com.dextrus.task.model.TableProperties;

@Service
public class ConnectionService {

	// Task-1
	public String connection(ConnectionRequest connectionRequest) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(connectionRequest.getUrl(),
					connectionRequest.getUsername(), connectionRequest.getPassword());
			return "SUCCESS";
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "FAILED";
		}
	}

	// Task-2
	public ArrayList<String> connectGetCatalogs(ConnectionRequest connectionRequest) {
		ArrayList<String> al = new ArrayList<>();
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(connectionRequest.getUrl(),
					connectionRequest.getUsername(), connectionRequest.getPassword());
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery("select name from sys.databases;");
			while (resultset.next()) {
				al.add(resultset.getString(1));
			}
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return al;
	}

	// Task-3
	public ArrayList<String> connectGetListOfSchemas(ConnectionRequest connectionRequest) {
		ArrayList<String> al = new ArrayList<>();
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(connectionRequest.getUrl(),
					connectionRequest.getUsername(), connectionRequest.getPassword());
			Statement statement = connection.createStatement();
			ResultSet resultset = statement
					.executeQuery("select name from " + connectionRequest.getCatalog() + ".sys.schemas;");
			while (resultset.next()) {
				al.add(resultset.getString(1));
			}
		} catch (ClassNotFoundException | SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return al;
	}

	// Task-4
	public ArrayList<TableProperties> connectGetListOfTables(ConnectionRequest connectionRequest) {
		ArrayList<TableProperties> al = new ArrayList<>();
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(connectionRequest.getUrl(),
					connectionRequest.getUsername(), connectionRequest.getPassword());
			PreparedStatement preparedStatement = connection.prepareStatement("use " + connectionRequest.getCatalog()
					+ "; " + "select * from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA=?;");
			preparedStatement.setString(1, connectionRequest.getSchema());
			ResultSet resultset = preparedStatement.executeQuery();
			while (resultset.next()) {
				TableProperties tp = new TableProperties();
				tp.setTABLE_CATALOG(resultset.getString(1));
				tp.setTABLE_SCHEMA(resultset.getString(2));
				tp.setTABLE_NAME(resultset.getString(3));
				tp.setTABLE_TYPE(resultset.getString(4));
				al.add(tp);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return al;
	}

	// Task-5
	public ArrayList<TableDescription> connectGetDescOfTables(ConnectionRequest connectionRequest) {
		ArrayList<TableDescription> al = new ArrayList<>();
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(connectionRequest.getUrl(),
					connectionRequest.getUsername(), connectionRequest.getPassword());
			PreparedStatement preparedStatement = connection
					.prepareStatement("USE "+connectionRequest.getCatalog()+"; SELECT * FROM " +connectionRequest.getSchema()+ "." +connectionRequest.getTable());
			ResultSet resultset = preparedStatement.executeQuery();
			ResultSetMetaData data = resultset.getMetaData();
			int columnCount = data.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				TableDescription td = new TableDescription();
				td.setColumnName(data.getColumnName(i));
				td.setDataType(data.getColumnTypeName(i));
				td.setIsNullable(data.isNullable(i));
				td.setMaxlength(data.getColumnDisplaySize(i));
				td.setPrecision(data.getPrecision(i));
				al.add(td);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return al;
	}
	
	//Task-7
	public List<TableProperties> getTablesAndViewsByPattern(ConnectionRequest connectionRequest) {
		List<TableProperties> viewsAndTables = new ArrayList<>();
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(connectionRequest.getUrl(),
					connectionRequest.getUsername(), connectionRequest.getPassword());
			PreparedStatement statement = connection
					.prepareStatement("use " + connectionRequest.getCatalog() + ";" + " SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE ?");
			statement.setString(1, connectionRequest.getPattern());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				TableProperties table = new TableProperties();
				table.setTABLE_NAME(resultSet.getString(3));
				table.setTABLE_TYPE(resultSet.getString(4));
				table.setTABLE_CATALOG(resultSet.getString(1));
				table.setTABLE_SCHEMA(resultSet.getString(2));
				viewsAndTables.add(table);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return viewsAndTables;
	}
}

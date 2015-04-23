package database;

import java.sql.*;

public class JavaToMySQL {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "pass";
	static Connection conn = null;
	static Statement stmt = null;
	public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");

		//STEP 3: Open a connection
		System.out.println("Connecting to database...");
		conn = DriverManager.getConnection(DB_URL, USER, PASS);

		//STEP 4: Execute a query
		System.out.println("Creating database...");
		stmt = conn.createStatement();
		boolean hasDatabase = false;
		ResultSet resultSet = conn.getMetaData().getCatalogs();
		String sql;
		while (resultSet.next()) {

			String databaseName = resultSet.getString(1);
			if(databaseName.equals("noire_etoile")){
				hasDatabase=true;
			}
		}
		resultSet.close();
		if (!hasDatabase){
			sql = "CREATE DATABASE Noire_Etoile";
			stmt.executeUpdate(sql);
			System.out.println("Database created successfully...");


		}else System.out.println("Database already exists...");
		createTable("test",
				"(id INTEGER not NULL, PRIMARY KEY ( id ))");
		DatabaseMetaData meta = conn.getMetaData();
		ResultSet res = meta.getTables("noire_etoile", null, "test",null);
		if (res.next())System.out.println("table created");
		else System.out.println("no table created");
		
		

	}

	public static int createTable(String tableName,String properties) throws SQLException{
		String sql;
		stmt.executeUpdate("use noire_etoile");
		sql = "CREATE TABLE IF NOT EXISTS "+ tableName+ " " + properties;
		return stmt.executeUpdate(sql);
	}
}

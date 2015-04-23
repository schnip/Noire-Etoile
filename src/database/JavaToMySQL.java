package database;

import java.sql.*;

public class JavaToMySQL {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost/";

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "pass";
	public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		Connection conn = null;
		   Statement stmt = null;
		   Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL, USER, PASS);

		      //STEP 4: Execute a query
		      System.out.println("Creating database...");
		      stmt = conn.createStatement();
		      
		      String sql = "DROP DATABASE Noire_Etoile";
		      stmt.executeUpdate(sql);
		      
		      sql = "CREATE DATABASE Noire_Etoile";
		      stmt.executeUpdate(sql);
		      System.out.println("Database created successfully...");
	
	}
}

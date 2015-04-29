package database;

import java.sql.*;

public class BlackDatabase {
	private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private final String DB_URL = "jdbc:mysql://localhost/";

	//  Database credentials
	private final String USER = "user";
	private final String PASS = "pass";
	private Connection conn = null;
	private Statement stmt = null;
	private CallableStatement getPlanetStmt = null;

	public BlackDatabase() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			getPlanetStmt = conn.prepareCall("{call log_from_planet(?)}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ResultSet getPlanetInfo(String planetName){
		ResultSet r = null;
		try {
			getPlanetStmt.setString(1, planetName);
			boolean hadResults = getPlanetStmt.execute();
			while (hadResults) {
				r = getPlanetStmt.getResultSet();
				hadResults = getPlanetStmt.getMoreResults();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

}
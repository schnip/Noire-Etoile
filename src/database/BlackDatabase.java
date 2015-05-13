package database;

import java.sql.*;

public class BlackDatabase implements DBInterface{
	private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private final String DB_URL = "jdbc:mysql://localhost/noire_etoile";

	//  Database credentials
	private final String USER = "user";
	private final String PASS = "pass";
	private Connection conn = null;
	private CallableStatement getPlanetNewsStmt;
	private CallableStatement getSystemNewsStmt;
	private CallableStatement getInventoryStmt;
	private CallableStatement getGalaxyNewsStmt;
	private CallableStatement getSystemPlanetsStmt;
	private CallableStatement getSystemsStmt;
	private CallableStatement setPlayerPlanetStmt;
	private CallableStatement getPlayerPlanetStmt;
	private CallableStatement createNewPlayerStmt;
	private CallableStatement getAllVendorsStmt;
	private CallableStatement getVendorGoodsStmt;
	
	public BlackDatabase() {
		try {
			Class.forName(JDBC_DRIVER);

			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			getPlanetNewsStmt = conn.prepareCall("{call log_from_planet(?)}");
			getSystemNewsStmt = conn.prepareCall("{call log_from_star_system(?)}");
			getInventoryStmt = conn.prepareCall("{call persons_inventory(?)}");
			getGalaxyNewsStmt = conn.prepareCall("{call log_from_Galaxy()}");
			getSystemPlanetsStmt = conn.prepareCall("{call planets_given_system(?)}");
			getSystemsStmt = conn.prepareCall("{call all_systems_in_galaxy()}");
			setPlayerPlanetStmt = conn.prepareCall("{call travel_to_planet(?,?)}");
			getPlayerPlanetStmt = conn.prepareCall("{call player_planet(?)}");
			createNewPlayerStmt = conn.prepareCall("{call create_new_player(?,?,?)}");
			getAllVendorsStmt = conn.prepareCall("{call get_vendors(?,?)}");
			getVendorGoodsStmt = conn.prepareCall("{call get_goods_vendor(?)}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("breaking in Black Database init:");
			e.printStackTrace();
		}
	}

	@Override
	public ResultSet getPlanetNews(String planetName){
		ResultSet r = null;
		try {
			getPlanetNewsStmt.setString(1, planetName);
			boolean hadResults = getPlanetNewsStmt.execute();
			while (hadResults) {
				r = getPlanetNewsStmt.getResultSet();
				//hadResults = getPlanetStmt.getMoreResults();
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public ResultSet getSystemNews(String SystemName){
		ResultSet r = null;
		try {
			getSystemNewsStmt.setString(1, SystemName);
			boolean hadResults = getSystemNewsStmt.execute();
			while (hadResults) {
				r = getSystemNewsStmt.getResultSet();
				//hadResults = getPlanetStmt.getMoreResults();
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public ResultSet getPersonInventory(String personName) {
		ResultSet r = null;
		try {
			getInventoryStmt.setString(1, personName);
			boolean hadResults = getInventoryStmt.execute();
			while (hadResults) {
				r = getInventoryStmt.getResultSet();
				//hadResults = getPlanetStmt.getMoreResults();
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public ResultSet getGalaxyNews() {
		ResultSet r = null;
		try {
			boolean hadResults = getGalaxyNewsStmt.execute();
			while (hadResults) {
				r = getGalaxyNewsStmt.getResultSet();
				//hadResults = getPlanetStmt.getMoreResults();
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public ResultSet getSystemPlanets(String systemName) {
		ResultSet r = null;
		try {
			getSystemPlanetsStmt.setString(1, systemName);
			boolean hadResults = getSystemPlanetsStmt.execute();
			while (hadResults) {
				r = getSystemPlanetsStmt.getResultSet();
				//hadResults = getPlanetStmt.getMoreResults();
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public ResultSet getSystems() {
		ResultSet r = null;
		try {
			boolean hadResults = getSystemsStmt.execute();
			while (hadResults) {
				r = getSystemsStmt.getResultSet();
				//hadResults = getPlanetStmt.getMoreResults();
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public Boolean setPlayerPlanet(String planetName, String playerName) {
		boolean r = false;
		try {
			setPlayerPlanetStmt.setString(1, planetName);
			setPlayerPlanetStmt.setString(2, playerName);
			r = setPlayerPlanetStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public ResultSet getVendors(String planetName, String playerName) {
		ResultSet r = null;
		try {
			getAllVendorsStmt.setString(1, planetName);
			getAllVendorsStmt.setString(2, playerName);
			boolean hadResults = getAllVendorsStmt.execute();
			while (hadResults) {
				r = getAllVendorsStmt.getResultSet();
				//hadResults = getPlanetStmt.getMoreResults();
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public Boolean makeTrade(String buyerName, String vendorName, int BgoodID,
			int Bquantity, int VgoodID, int Vquantity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getGoods(String playerName) {
		ResultSet r = null;
		try {
			getVendorGoodsStmt.setString(1, playerName);
			boolean hadResults = getVendorGoodsStmt.execute();
			while (hadResults) {
				r = getVendorGoodsStmt.getResultSet();
				//hadResults = getPlanetStmt.getMoreResults();
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public Boolean createPlayer(String playerName, String playerPlanet,
			int maxWeight, String shipName, int startCredits) {
		boolean r = false;
		try {
			createNewPlayerStmt.setString(1, playerName);
			createNewPlayerStmt.setString(2, playerPlanet);
			createNewPlayerStmt.setInt(3, maxWeight);
			r = createNewPlayerStmt.execute();
			r=true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	
	@Override
	public String getPlayerPlanet(String playerName) {
		ResultSet r = null;
		String pname = null;
		try {
			getPlayerPlanetStmt.setString(1, playerName);
			boolean hadResults = getPlayerPlanetStmt.execute();
			while (hadResults) {
				r = getPlayerPlanetStmt.getResultSet();
				//hadResults = getPlanetStmt.getMoreResults();
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (r == null) {
			return null;
		}
		try{
			r.first();
			pname = r.getString("planet");
		} catch(Exception exp){System.out.println("this is bad... :");exp.printStackTrace();}
		return pname;
	}

}
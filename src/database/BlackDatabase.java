package database;

import java.sql.*;

public class BlackDatabase implements DBInterface{
	private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private final String DB_URL = "jdbc:mysql://localhost/noire_etoile?noAccessToProcedureBodies=true";

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
	private CallableStatement makeTradeStmt;
	private CallableStatement getMoneyStmt;
	private CallableStatement getUsedWeightStmt;
	private CallableStatement getTotalWeightStmt;
	private CallableStatement getPlanetSystemStmt;
	private CallableStatement getPlayerShipStmt;
	private CallableStatement playerExistsStmt;
	private CallableStatement dropPlayerStmt;
	
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
			createNewPlayerStmt = conn.prepareCall("{call create_new_player(?,?,?,?)}");
			getAllVendorsStmt = conn.prepareCall("{call get_vendors(?,?)}");
			getVendorGoodsStmt = conn.prepareCall("{call get_goods_vendor(?)}");
			makeTradeStmt = conn.prepareCall("{call make_trade(?,?,?,?)}");
			getMoneyStmt = conn.prepareCall("{call get_player_money(?)}");
			getUsedWeightStmt = conn.prepareCall("{call get_used_weight(?)}");
			getTotalWeightStmt = conn.prepareCall("{call get_total_weight(?)}");
			getPlanetSystemStmt = conn.prepareCall("{call system_given_planet(?)}");
			getPlayerShipStmt = conn.prepareCall("{call get_player_ship(?)}");
			playerExistsStmt = conn.prepareCall("{? = call player_exists(?)}");
			dropPlayerStmt = conn.prepareCall("{call drop_player(?)}");
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
	public Boolean makeTrade(String buyerName, String vendorName, String BgoodName,
			int Bquantity) {
			boolean r = false;
			try {
				makeTradeStmt.setString(1, buyerName);
				makeTradeStmt.setString(2, vendorName);
				makeTradeStmt.setString(3, BgoodName);
				makeTradeStmt.setInt(4, Bquantity);
				r = makeTradeStmt.execute();
				r=true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return r;
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
			createNewPlayerStmt.setString(4, shipName);
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

	@Override
	public String getPlanetSystem(String planetName) {
		ResultSet r = null;
		String pname = null;
		try {
			getPlanetSystemStmt.setString(1, planetName);
			boolean hadResults = getPlanetSystemStmt.execute();
			while (hadResults) {
				r = getPlanetSystemStmt.getResultSet();
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
			pname = r.getString("star_system");
		} catch(Exception exp){System.out.println("this is bad... :");exp.printStackTrace();}
		return pname;
	}

	@Override
	public String getPlayerShip(String playerName) {
		ResultSet r = null;
		String pname = null;
		try {
			getPlayerShipStmt.setString(1, playerName);
			boolean hadResults = getPlayerShipStmt.execute();
			while (hadResults) {
				r = getPlayerShipStmt.getResultSet();
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
			pname = r.getString("shipName");
		} catch(Exception exp){System.out.println("this is bad... :");exp.printStackTrace();}
		return pname;
	}

	@Override
	public int getPlayerCredits(String playerName) {
		ResultSet r = null;
		int money = -1;
		try {
			getMoneyStmt.setString(1, playerName);
			boolean hadResults = getMoneyStmt.execute();
			while (hadResults) {
				r = getMoneyStmt.getResultSet();
				//hadResults = getPlanetStmt.getMoreResults();
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (r == null) {
			return -1;
		}
		try{
			r.first(); 
			money = r.getInt("quantity");
		} catch(Exception exp){System.out.println("this is bad... :");exp.printStackTrace();}
		return money;
	}

	@Override
	public int getPlayerTotalWeight(String playerName) {
		ResultSet r = null;
		int money = -1;
		try {
			getTotalWeightStmt.setString(1, playerName);
			boolean hadResults = getTotalWeightStmt.execute();
			while (hadResults) {
				r = getTotalWeightStmt.getResultSet();
				//hadResults = getPlanetStmt.getMoreResults();
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (r == null) {
			return -1;
		}
		try{
			r.first();
			money = r.getInt("max_weight");
		} catch(Exception exp){System.out.println("this is bad... :");exp.printStackTrace();}
		return money;
	}

	@Override
	public int getPlayerRemainingWeight(String playerName) {
		ResultSet r = null;
		int money = -1;
		try {
			getUsedWeightStmt.setString(1, playerName);
			boolean hadResults = getUsedWeightStmt.execute();
			while (hadResults) {
				r = getUsedWeightStmt.getResultSet();
				//hadResults = getPlanetStmt.getMoreResults();
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (r == null) {
			return getPlayerTotalWeight(playerName);
		}
		try{
			r.first();
			money = r.getInt("used_weight");
		} catch(Exception exp){System.out.println("this is bad... :");exp.printStackTrace();}
		return getPlayerTotalWeight(playerName)-money;
	}

	@Override
	public ResultSet getAllGoods() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void giveGood(String player, String good, int quantity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean playerExists(String playerName) {
		boolean outputValue = false;
		try {
			playerExistsStmt.registerOutParameter(1,java.sql.Types.BOOLEAN);
			playerExistsStmt.setString(2,playerName);
			playerExistsStmt.execute();
			outputValue = playerExistsStmt.getBoolean(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outputValue;
	}
	@Override
	public void dropCharacter(String playerName) {
		try {
			dropPlayerStmt.setString(1, playerName);
			boolean hadResults = dropPlayerStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
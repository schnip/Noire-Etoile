package database;

import java.sql.ResultSet;

public interface DBInterface {
	public ResultSet getPlanetNews(String planetName);
	public ResultSet getSystemNews(String systemName);
	public ResultSet getPersonInventory(String personName);
	public ResultSet getGalaxyNews();
	public ResultSet getSystemPlanets(String systemName);
	public ResultSet getSystems();
	public Boolean setPlayerPlanet(String planetName, String playerName);
	public ResultSet getVendors(String planetName, String playerName);
	public Boolean makeTrade(String buyerName, String vendorName, String goodName, int Bquantity);
	public ResultSet getGoods(String playerName);
	public String getPlayerPlanet(String playerName);
	public Boolean createPlayer(String playerName, String playerPlanet, int maxWeight, String shipName, int startCredits);
	public String getPlanetSystem(String planetName);
	public String getPlayerShip(String playerName);
	public int getPlayerCredits(String playerName);
	public int getPlayerTotalWeight(String playerName);
	public int getPlayerRemainingWeight(String playerName);
	public ResultSet getAllGoods();
	public void giveGood(String player, String good, int quantity);
	public boolean playerExists(String playerName);
	public void dropCharacter(String playerName);
	public int getLegality(String good);
	public int getPoliceLevel(String planet);
	public int getPoliceLevelStarSystem(String planet);
	public int getDangerLevel(String player_planet);
}

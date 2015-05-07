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
}

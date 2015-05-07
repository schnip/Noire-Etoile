package ui;

import java.sql.ResultSet;

import database.BlackDatabase;
import database.DBInterface;


public class Launcher {

	private static String player_name;
	private static String player_ship;
	private static String player_planet;
	private static String player_system;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] x = getArrayFilledWithBlanks(23);
		Engine e = new Engine();
		DBInterface bd = new BlackDatabase();
		String ret;
		x[0] = "Hello World!";
		x[1] = "This is the main window in which you will interact with Noire Etoile";
		x[3] = "This is the main menu";
		x[5] = "    1) Connect to the local database";
		x[6] = "    2) New game";
		x[22] = "Enter a string and press enter (q to exit)";
		ret = e.render(x);
		while(!ret.equals("q")) {
			//x[3] = "The string you entered was: " + ret;
			if (ret.equals("2")) {
				createCharater(e, bd);
			}
			ret = e.render(x);
		}
		e.close();
	}
	
	public static String[] getArrayFilledWithBlanks(int len) {
		String[] x = new String[len];
		for (int i = 0; i<len; i++) {
			x[i] = "";
		}
		return x;
	}

	private static void createCharater(Engine e, DBInterface bd) {
		String[] x = getArrayFilledWithBlanks(23);
		String ret;
		x[0] = "Welcome to Noire Etoile! I am your ship's onboard computer!";
		x[1] = "Please forgive my dataloss, I seem to have forgotten your name in my most recent";
		x[2] = "software upgrade. It was what?";
		x[22] = "(Enter your player's name and press enter)";
		ret = e.render(x);
		player_name = ret;
		x[4] = "Ah yes, Captain " + player_name + "!";
		x[6] = "Now that your new ship has been completed, what would you like to name her?";
		x[22] = "(Enter your ship's name and press enter)";
		ret = e.render(x);
		player_ship = ret;
		x[8] = "Very well, the " + player_ship + " it is!";
		x[10] = "When you are ready, we'll go to orbit!";
		x[22] = "(Press enter when you are ready to go to orbit)";
		ret = e.render(x);
		player_planet = "Undefined";
		player_system = "Undefined";
		orbitX(e, bd);
	}

	private static void orbitX(Engine e, DBInterface bd) {
		String[] x = getArrayFilledWithBlanks(23);
		String ret;
		x[0] = "Orbit Screen";
		x[1] = "You are currently in orbit around " + player_planet + " in the " + player_system + " system.";
		x[3] = "    1) System map";
		x[4] = "    2) Spacedock";
		x[5] = "    3) Land and meet with people on planet";
		x[6] = "    4) View ship cargo and status";
		x[7] = "    5) Read news";
		x[8] = "    6) Settings";
		x[9] = "    7) Quit";
		x[22] = "(Type a number and press enter)";
		while(true) {
		ret = e.render(x);
		switch (ret) {
			case "1":
				systemX(e, bd, false);
				break;
			case "2":
				spacedockX(e, bd);
				break;
			case "3":
				landingX(e, bd);
				break;
			case "4":
				cargoX(e, bd);
				break;
			case "5":
				newsX(e, bd);
				break;
			case "6":
				settingsX(e, bd);
				orbitX(e, bd);
				break;
			case "7":
				return;
			default:
				break;
		}
		}
	}

	private static void settingsX(Engine e, DBInterface bd) {
		String[] x = getArrayFilledWithBlanks(23);
		String ret;
		x[0] = "Settings Screen";	
		x[3] = "    No settings available yet";
		x[5] = "    1) Return to previous menu";
		x[22] = "(Type a number and press enter)";
		ret = e.render(x);
		switch (ret) {
			case "1":
				break;
			default:
				settingsX(e, bd);
				break;
		}
	}

	private static void newsX(Engine e, DBInterface bd) {
		String[] x = getArrayFilledWithBlanks(23);
		String ret;
		x[0] = "News Screen";
		x[1] = "You are on " + player_planet + " in the " + player_system + " system";
		x[22] = "(l for local news, s for system news, g for galactic news, b to go back)";
		while(true) {
			ret = e.render(x);
			switch (ret) {
				case "l":
					ResultSet rs = bd.getPlanetNews("Planet Eric");
					if (rs == null) {
						break;
					}
					try{
					rs.first();
					for (int i = 0; i < 10; i++) {
						x[i+2] = "  " + rs.getInt("star_date") + " " + rs.getString("log_text");
						if (rs.isLast()) {
							break;
						}
						rs.next();
					}
					} catch(Exception exp){System.out.println("this is bad... :");exp.printStackTrace();}
					break;
				case "s":
					// TODO get system-wide news here
					break;
				case "g":
					// TODO get galaxy-wide news here
					break;
				case "b":
					return;
				default:
					break;
			}
		}
	}

	private static void cargoX(Engine e, DBInterface bd) {
		String[] x = getArrayFilledWithBlanks(23);
		String ret;
		x[0] = "Inventory Screen";	
		ret = e.render(x);		
	}

	private static void landingX(Engine e, DBInterface bd) {
		String[] x = getArrayFilledWithBlanks(23);
		String[] p = getArrayFilledWithBlanks(10);
		String ret;
		x[0] = "On Planet Screen";
		try {
			ResultSet rs = bd.getVendors(player_planet, player_name);
			rs.first();
			for (int i = 1; i < 11; i++) {
				x[i+5] = "    " + i + ") " + rs.getString("name");
				p[i-1] = rs.getString("name");
				if (rs.isLast()) {
					break;
				}
				rs.next();
			}
		} catch(Exception exp){System.out.println("this is bad... :");exp.printStackTrace();}
		ret = e.render(x);
		
	}

	private static void spacedockX(Engine e, DBInterface bd) {
		String[] x = getArrayFilledWithBlanks(23);
		String ret;
		x[0] = "Spacedock Screen";	
		ret = e.render(x);
		
	}

	private static void systemX(Engine e, DBInterface bd, boolean must) {
		String[] x = getArrayFilledWithBlanks(23);
		String[] p = getArrayFilledWithBlanks(10);
		String ret;
		x[0] = "System Screen";	
		x[1] = "You are currently on " + player_planet + " in the " + player_system + " system";
		if (!must)
			x[3] = "    g) Galactic map";
		x[4] = "    r) Return to orbit screen";
		try {
			ResultSet rs = bd.getSystemPlanets(player_system);
			rs.first();
			for (int i = 1; i < 11; i++) {
				x[i+5] = "    " + i + ") " + rs.getString("name");
				p[i-1] = rs.getString("name");
				if (rs.isLast()) {
					break;
				}
				rs.next();
			}
		} catch(Exception exp){System.out.println("this is bad... :");exp.printStackTrace();}
		// TODO bring planets there to be selected between
		while(true) {
		ret = e.render(x);
		switch (ret) {
			case "g":
				boolean change = galaxyX(e, bd);
				systemX(e, bd, true);
				return;
			case "r":
				if (must)
					break;
				return;
			default:
				int choice = Integer.parseInt(ret);
				if (choice<0 && choice<11) {
					player_planet = p[choice-1];
					bd.setPlayerPlanet(player_planet, player_name);
				}
				break;
		}
		}
	}

	private static boolean galaxyX(Engine e, DBInterface bd) {
		String[] x = getArrayFilledWithBlanks(23);
		String[] p = getArrayFilledWithBlanks(10);
		String ret;
		x[0] = "Galaxy Screen";
		x[1] = "    You are currently in the " + player_system + " system";
		x[3] = "    r) Return to system screen";
		// TODO bring system there to be selected between
		try {
			ResultSet rs = bd.getSystems();
			rs.first();
			for (int i = 1; i < 11; i++) {
				x[i+5] = "    " + i + ") " + rs.getString("name");
				p[i-1] = rs.getString("name");
				if (rs.isLast()) {
					break;
				}
				rs.next();
			}
		} catch(Exception exp){System.out.println("this is bad... :");exp.printStackTrace();}
		while(true) {
			ret = e.render(x);
			switch (ret) {
				case "r":
					return false;
				default:
					int choice = Integer.parseInt(ret);
					if (choice<0 && choice<11) {
						player_system = p[choice-1];
						return true;
					}
					break;
			}
		}
	}
}

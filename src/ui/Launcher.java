package ui;

import java.sql.ResultSet;
import java.util.ArrayList;

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
		x[7] = "    3) Load game";
		x[22] = "Enter a string and press enter (q to exit)";
		ret = e.render(x);
		while(!ret.equals("q")) {
			//x[3] = "The string you entered was: " + ret;
			if (ret.equals("2")) {
				createCharater(e, bd);
			}
			if (ret.equals("3")) {
				// TODO things
				loadGame(e, bd);
			}
			ret = e.render(x);
		}
		e.close();
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
				orbitX(e, bd);
				break;
		}
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
		player_planet = "Planet Eric";
		player_system = "Easter Egg Nebula";
		if (bd.createPlayer(player_name, player_planet, 1000, player_ship, 1000)) {
			orbitX(e, bd);
		} else {
			createCharater(e, bd);
		}
	}

	private static void orbitX(Engine e, DBInterface bd) {
		String[] x = getArrayFilledWithBlanks(23);
		String ret;
		player_planet = bd.getPlayerPlanet(player_name);
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
				orbitX(e, bd);
				return;
			case "2":
				spacedockX(e, bd);
				orbitX(e, bd);
				return;
			case "3":
				landingX(e, bd);
				orbitX(e, bd);
				return;
			case "4":
				cargoX(e, bd);
				orbitX(e, bd);
				return;
			case "5":
				newsX(e, bd);
				orbitX(e, bd);
				return;
			case "6":
				settingsX(e, bd);
				orbitX(e, bd);
				return;
			case "7":
				return;
			default:
				break;
		}
		}
	}

	private static void loadGame(Engine e, DBInterface bd) {
		String[] x = getArrayFilledWithBlanks(23);
		String ret;
		x[0] = "Load Game";	
		x[3] = "    Enter the name of the character you want to load";
		x[4] = "    ";
		x[22] = "(Type a name and press enter)";
		while(true) {
			ret = e.render(x);
			String get;
			get = bd.getPlayerPlanet(ret);
			if (get == null) {
				continue;
			} else {
				player_name = ret;
				player_planet = get;
				player_system = bd.getPlanetSystem(get);
				player_ship = bd.getPlayerShip(ret);
				orbitX(e, bd);
				return;
			}
		}
	}

	private static void newsX(Engine e, DBInterface bd) {
		String[] x = getArrayFilledWithBlanks(23);
		String ret;
		player_planet = bd.getPlayerPlanet(player_name);
		
		x[0] = "News Screen";
		x[1] = "You are on " + player_planet + " in the " + player_system + " system";
		x[22] = "(l for local news, s for system news, g for galactic news, b to go back)";
		while(true) {
			ret = e.render(x);
			switch (ret) {
				case "l":
					ResultSet rs = bd.getPlanetNews("player_planet");
					if (rs == null) {
						break;
					}
					try{
					if (rs == null)
						break;
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
					rs = bd.getSystemNews("player_system");
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
				case "g":
					rs = bd.getGalaxyNews();
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
		try {
			ResultSet rs = bd.getGoods(player_name);
			if(rs!= null){
			rs.first();
			for (int i = 1; i < 11; i++) {
				x[i+5] = "  " + rs.getInt("quantity") + " " + rs.getString("name");
				if (rs.isLast()) {
					break;
				}
				rs.next();
			}
			}
		} catch(Exception exp){System.out.println("this is bad... :");exp.printStackTrace();}
		ret = e.render(x);		
	}

	private static void landingX(Engine e, DBInterface bd) {
		String[] x = getArrayFilledWithBlanks(23);
		String[] p = getArrayFilledWithBlanks(10);
		String ret;
		x[0] = "On Planet Screen";
		x[1] = "  Choose a vendor to interact with";
		x[20] = "    r) Return to previous screen";
		try {
			ResultSet rs = bd.getVendors(player_planet, player_name);
			if(rs!= null){
			rs.first();
			for (int i = 1; i < 11; i++) {
				x[i+5] = "    " + i + ") " + rs.getString("name");
				p[i-1] = rs.getString("name");
				if (rs.isLast()) {
					break;
				}
				rs.next();
			}
			}
		} catch(Exception exp){System.out.println("this is bad... :");exp.printStackTrace();}
		while (true) {
			ret = e.render(x);
			if (ret.equals("r")) {
				orbitX(e, bd);
				return;
			}
			int choice = Integer.parseInt(ret);
			if (choice>0 && choice<11) {
				vendorX(e, bd, p[choice-1]);
			}
		}		
	}
	
	private static void vendorX(Engine e, DBInterface bd, String v) {
		String[] x = getArrayFilledWithBlanks(23);
		String[] p = getArrayFilledWithBlanks(10);
		String ret;
		ArrayList<String> gdArray = new ArrayList<String>();
		x[0] = "On Vendor Screen";
		x[1] = "  Choose a good to buy";
		x[20] = "    r) Return to previous screen";
		try {
			ResultSet rs = bd.getGoods(v);
			if(rs!= null){
			rs.first();
			for (int i = 1; i < 11; i++) {
				gdArray.add(rs.getString("goodName"));
				x[i+5] = "    " + i + ") " + rs.getString("goodName");
				p[i-1] = rs.getString("goodName");
				if (rs.isLast()) {
					break;
				}
				rs.next();
			}
			}
		} catch(Exception exp){System.out.println("this is bad... :");exp.printStackTrace();}
		while (true) {
			ret = e.render(x);
			if (ret.equals("r")) {
				landingX(e, bd);
				return;
			}
			int choice = Integer.parseInt(ret);
			if (choice>0 && choice<11) {
				//bd.makeTrade(player_name, v, p[choice-1],1);
				amountX(e, bd, v, p[choice-1]);
			}
		}		
	}

	private static void amountX(Engine e, DBInterface bd, String v,
			String good) {
		String[] x = getArrayFilledWithBlanks(23);
		String[] p = getArrayFilledWithBlanks(10);
		String ret;
		int gw = 0, gc = 0, pw = 0, pc = 0, vq = 0;
		x[0] = "Choose an amount to buy";
		x[20] = "    r) Return to previous screen";
		try {
			ResultSet rs;
			pc = bd.getPlayerCredits(player_name);
			pw = bd.getPlayerRemainingWeight(player_name);
			x[3] = "    You currently have " + pc + " credits";
			x[4] = "    You currently have " + pw + "/" + bd.getPlayerTotalWeight(player_name) + " weight remaining on your ship";
			rs = bd.getGoods(v);
			if(rs!= null){
				rs.first();
			} else {
				System.out.println("Ahhhhh things are breeeaking");
				return;
			}
			gc = rs.getInt("good_value");
			gw = rs.getInt("weight");
			x[6] = "    " + good + " costs " + gc + " per unit from " + v;
			x[7] = "    " + good + " weights " + gw;
			vq = rs.getInt("quantity");
		} catch(Exception exp){System.out.println("this is bad... :");exp.printStackTrace();}
		while (true) {
			ret = e.render(x);
			boolean noflag = true;
			x[9] = "";
			if (ret.equals("r")) {
				vendorX(e, bd, v);
				return;
			}
			int choice = Integer.parseInt(ret);
			if (choice * gw < pw) {
				x[9] = "    Not enough space";
				noflag = false;
			}
			if (choice > vq) {
				x[10] = "    Vendor does not have that many items";
				noflag = false;
			}
			if (choice * gc > pc) {
				x[11] = "    You do not have enough credits";
				noflag = false;
			}
			if (noflag) {
				// Actually make the trade
				bd.makeTrade(player_name, v, good, choice);
				return;
			}
		}				
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
				systemX(e, bd, change);
				return;
			case "r":
				if (must)
					break;
				return;
			default:
				int choice = Integer.parseInt(ret);
				//System.out.println("player_planet = " + player_planet);
				if (choice>0 && choice<11) {
					player_planet = p[choice-1];
					//System.out.println("In choice to set planet. player_planet = "+player_planet);
					bd.setPlayerPlanet(player_planet, player_name);
				}
				return;
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
			if(rs!=null){
			rs.first();
			for (int i = 1; i < 11; i++) {
				x[i+5] = "    " + i + ") " + rs.getString("name");
				p[i-1] = rs.getString("name");
				if (rs.isLast()) {
					break;
				}
				rs.next();
			}
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
						if (player_system.equals(p[choice-1])) {
							return false;
						}
						player_system = p[choice-1];
						return true;
					}
					break;
			}
		}
	}
}

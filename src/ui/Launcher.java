package ui;

import database.BlackDatabase;


public class Launcher {

	private static String player_name;
	private static String player_ship;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] x = getArrayFilledWithBlanks(23);
		Engine e = new Engine();
		BlackDatabase bd = new BlackDatabase();
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

	private static void createCharater(Engine e, BlackDatabase bd) {
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
		orbitX(e, bd);
	}

	private static void orbitX(Engine e, BlackDatabase bd) {
		String[] x = getArrayFilledWithBlanks(23);
		String ret;
		x[0] = "Orbit Screen";
		x[1] = "You are currently in orbit around "; // TODO gather planet name and put it here
		x[3] = "    1) System map";
		x[4] = "    2) Spacedock";
		x[5] = "    3) Land and meet with people on planet";
		x[6] = "    4) View ship cargo and status";
		x[7] = "    5) Read news";
		x[8] = "    6) Settings";
		x[9] = "    7) Quit";
		x[22] = "(Type a number and press enter)";
		ret = e.render(x);
		switch (ret) {
			case "1":
				systemX(e, bd);
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
				break;
			default:
				orbitX(e, bd);
				break;
		}
	}

	private static void settingsX(Engine e, BlackDatabase bd) {
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

	private static void newsX(Engine e, BlackDatabase bd) {
		String[] x = getArrayFilledWithBlanks(23);
		String ret;
		x[0] = "News Screen";	
		x[22] = "(l for local news, s for system news, g for galactic news)";
		ret = e.render(x);
		
	}

	private static void cargoX(Engine e, BlackDatabase bd) {
		String[] x = getArrayFilledWithBlanks(23);
		String ret;
		x[0] = "Inventory Screen";	
		ret = e.render(x);		
	}

	private static void landingX(Engine e, BlackDatabase bd) {
		String[] x = getArrayFilledWithBlanks(23);
		String ret;
		x[0] = "On Planet Screen";	
		ret = e.render(x);
		
	}

	private static void spacedockX(Engine e, BlackDatabase bd) {
		String[] x = getArrayFilledWithBlanks(23);
		String ret;
		x[0] = "Spacedock Screen";	
		ret = e.render(x);
		
	}

	private static void systemX(Engine e, BlackDatabase bd) {
		String[] x = getArrayFilledWithBlanks(23);
		String ret;
		x[0] = "System Screen";	
		x[1] = "You are currently in the TODO system"; // TODO gather system name and put it here
		x[3] = "    1) Galactic map";
		x[4] = "    2) Return to orbit screen";
		ret = e.render(x);
		switch (ret) {
			case "1":
				galaxyX(e, bd);
				break;
			case "2":
				break;
			default:
				systemX(e, bd);
				break;
		}
	}

	private static void galaxyX(Engine e, BlackDatabase bd) {
		String[] x = getArrayFilledWithBlanks(23);
		String ret;
		x[0] = "Galaxy Screen";	
		ret = e.render(x);
		
	}
}

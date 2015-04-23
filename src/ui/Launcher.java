package ui;


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
		String ret;
		x[0] = "Hello World!";
		x[1] = "This is the main window in which you will interact with Noire Etoile";
		x[3] = "This is the main menu";
		x[5] = "    1) Connect to the local database";
		x[6] = "    2) New game";
		x[22] = "Enter a string and press enter (q to exit)";
		ret = e.render(x);
		while(!ret.equals("q")) {
			x[3] = "The string you entered was: " + ret;
			if (ret.equals("2")) {
				createCharater(e);
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

	public static void createCharater(Engine e) {
		String[] x = getArrayFilledWithBlanks(23);
		String ret;
		x[0] = "Welcome to Noire Etoile! I am your ship's onboard computer!";
		x[1] = "Please forgive my dataloss, I seem to have forgotten your name in my most recent";
		x[2] = "software upgrade. It was what?";
		x[22] = "(Enter your player's name and press enter)";
		ret = e.render(x);
		player_name = ret;
		x[4] = "Ah yes, Captain " + ret + "!";
		x[6] = "Now that your new ship has been completed, what would you like to name her?";
		x[22] = "(Enter your ship's name and press enter)";
		ret = e.render(x);
		player_ship = ret;
		x[8] = "Very well, the " + ret + " it is!";
		x[10] = "When you are ready, we'll go to orbit!";
		x[22] = "(Press enter when you are ready to go to orbit)";
		ret = e.render(x);
	}
}

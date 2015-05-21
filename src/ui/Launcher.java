package ui;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

import database.BlackDatabase;
import database.DBInterface;


public class Launcher {

	private static String player_name;
	private static String player_ship;
	private static String player_planet;
	private static String player_system;

	public static void main(String[] args) {
		String[] x = getArrayFilledWithBlanks(23);
		Engine e = new Engine();
		DBInterface bd = new BlackDatabase();
		String ret;
		x[1] = "Main Menu";
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
				loadGame(e, bd);
			}
			ret = e.render(x);
		}
		e.close();
		System.exit(0);
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
		x[22] = "(Enter your player's name (minimum length 2) and press enter)";
		ret = e.render(x);
		if(ret.length()<2) {createCharater(e,bd); return;}
		int offset = 0;
		if(ret==""){ createCharater(e,bd); return;}
		if(bd.playerExists(ret)){
			String pname = ret;
			while(true){
				offset=2;
				x[2+offset] ="It seems I have been able to find your old data. Would you like to continue";
				x[3+offset] = "where you left off or create a new charater?";
				x[22] = "(Enter l to load previous game, n to create a new character, or o to overwrite)";
				ret = e.render(x);
				if (ret.toLowerCase().equals("n")){
					x[20] ="";
					createCharater(e,bd); return;
				}
				else if (ret.toLowerCase().equals( "l")){
					x[20] ="";
					String get;
					get = bd.getPlayerPlanet(pname);
					if (get == null) {
						get = "Planet Eric";
					}
					player_name = pname;
					player_planet = get;
					player_system = bd.getPlanetSystem(get);
					player_ship = bd.getPlayerShip(pname);
					orbitX(e, bd);
					return;
				}
				else if (ret.toLowerCase().equals( "o")){
					x[20] ="";
					ret = pname;
					bd.dropCharacter(ret);
					break;
				}
				else{
					x[20] = "Please enter (y/n) as a valid entry.";
					continue;
				}
			}
		}
		player_name = ret;
		x[4+offset] = "Ah yes, Captain " + player_name + "!";
		x[5+offset] = "Now that your new ship has been completed, what would you like to name her?";
		x[22] = "(Enter your ship's name and press enter)";
		ret = e.render(x);
		player_ship = ret;
		x[7+offset] = "Very well, the " + player_ship + " it is!";
		x[8+offset] = "When you are ready, we'll go to orbit!";
		x[22] = "(Press enter when you are ready to go to orbit)";
		ret = e.render(x);
		player_planet = "Planet Eric";
		player_system = "Easter Egg Nebula";
		if (bd.createPlayer(player_name, player_planet, 5000, player_ship, 1000)) {
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
				if (systemX(e, bd, false)) {
					if (e.isEvent(bd, player_planet)) {
						Event.runEvent(bd, player_planet, player_name, Event.PLANET_MOVE, e);
					}
				}
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
				String[] main=null;
				main(main);
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
		x[20] = "    r) Return to previous screen";
		x[22] = "(Type a name (minimum length 2) and press enter)";
		while(true) {
			ret = e.render(x);
			if(ret.equals("r")){return;}
			if(bd.playerExists(ret)){
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
			else{
				x[18]= "Character "+ ret + " does not exist. ";
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
				ResultSet rs = bd.getPlanetNews(player_planet);
				if (rs == null) {
					break;
				}
				x = getArrayFilledWithBlanks(23);
				x[0] = "News Screen";
				x[1] = "You are on " + player_planet + " in the " + player_system + " system";
				x[22] = "(l for local news, s for system news, g for galactic news, b to go back)";
				try{
					rs.first();
					if(!rs.next()){x[3]="There is no news for this planet";break;}
					rs.previous();
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
				x = getArrayFilledWithBlanks(23);
				x[0] = "News Screen";
				x[1] = "You are on " + player_planet + " in the " + player_system + " system";
				x[22] = "(l for local news, s for system news, g for galactic news, b to go back)";
				try{
					rs.first();
					if(!rs.next()){x[3]="There is no news for this system";break;}
					rs.previous();
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
				x = getArrayFilledWithBlanks(23);
				x[0] = "News Screen";
				x[1] = "You are on " + player_planet + " in the " + player_system + " system";
				x[22] = "(l for local news, s for system news, g for galactic news, b to go back)";
				try{
					rs.first();
					if(!rs.next()){x[3]="There is no news for the Galaxy";break;}
					rs.previous();
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
		x[0] = "Inventory Screen";
		try {
			ResultSet rs = bd.getGoods(player_name);
			if(rs!= null){
				rs.first();
				for (int i = 1; i < 11; i++) {
					x[i+5] = "  " + rs.getInt("quantity") + " " + rs.getString("goodName");
					if (rs.isLast()) {
						break;
					}
					rs.next();
				}
			}
		} catch(Exception exp){System.out.println("this is bad... :");exp.printStackTrace();}
		e.render(x);		
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
				if(!rs.first()){x[3]="There are no other people on this planet";
				e.render(x);
				orbitX(e,bd); return;}
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
			else if(ret== ""){
				continue;
			}
			else{
				try {
					int choice = Integer.parseInt(ret);
					if (choice>0 && choice<11) {
						vendorX(e, bd, p[choice-1]);
					}
				} catch (NumberFormatException exc) {
					x[18] = "   Please enter a valid entry";
					continue;
				}

			}
		}		
	}

	private static void vendorX(Engine e, DBInterface bd, String v) {
		String[] x = getArrayFilledWithBlanks(23);
		String[] p = getArrayFilledWithBlanks(10);
		String ret;
		boolean flag = true;
		ArrayList<String> gdArray = new ArrayList<String>();
		x[0] = "On Vendor Screen";
		x[1] = "  Choose a good to buy";
		x[20] = "    r) Return to previous screen";
		x[19] = "    c) Call police on "+v+" for selling illegal products";
		x[3] = "    Item Name        Quantity Availible        Price";
		try {
			ResultSet rs = bd.getGoods(v);
			if(rs!= null){
				if(!rs.first()){
					x[3]="There are no goods for this person";
					x[19]="";
					flag = false;
					ret = e.render(x);
					landingX(e,bd);return;}
				for (int i = 1; i < 11; i++) {
					gdArray.add(rs.getString("goodName"));
					x[i+5] = "    " + i + ") " + rs.getString("goodName")+
							"         " + rs.getString("quantity")+"        " + rs.getString("good_value");
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
			if(ret.equals("c")&&flag){
				callPolice(e,bd,v,gdArray);
				return;
			}
			try {
				int choice = Integer.parseInt(ret);
				if (choice>0 && choice<11) {
					//bd.makeTrade(player_name, v, p[choice-1],1);
					System.out.println(p[choice-1]);

					amountX(e, bd, v, p[choice-1]);
				}}
			catch(Exception exc){
				x[18] = "   Please enter a valid entry";
				continue;
			}
		}		
	}

	private static void callPolice(Engine e, DBInterface bd,String v, ArrayList<String> gdArray){
		String[] x = getArrayFilledWithBlanks(23);
		String ret;
		int plvl = bd.getPoliceLevel(player_planet);
		String pL="";
		if(plvl>75)pL="High";
		else if (plvl>30)pL="Average";
		else pL ="Low";
		int dlvl = bd.getDangerLevel(player_planet);
		String dL="";
		if(dlvl>75)dL="High";
		else if (dlvl>30)dL="Average";
		else dL ="Low";
		x[0] = "Are you sure that you want to call the Police on "+v+"?";
		x[1] = "Police Level: " + pL + "        Danger Level: "+dL;
		x[20] = "    r) Return to previous screen";
		x[19] = "    y) Make the Call";
		ret = e.render(x);
		if(ret.equals("")){
			callPolice(e,bd,v,gdArray);
			return;
		}
		else if(ret.equals("r")){
			vendorX(e,bd,v);
			return;
		}
		else if(ret.equals("y")){
			x[3]="You make the call...";
			x[20]="(Press enter to continue)";
			x[19]="";
			ret = e.render(x);
			if(policeCome(plvl)){
				x[5]="The police come to investigate...";
				boolean flag = false;
				int i =0;
				try {
					for(i=0;i < gdArray.size(); i++) {
						if(bd.getLegality(gdArray.get(i))==0){
							flag = true;
							break;
						}
					}
				} catch(Exception exp){System.out.println("this is bad... :");exp.printStackTrace();}
				if(flag){
					x[7]="The Police come back carrying "+gdArray.get(i);
					x[8]= "They proceed to cuff the shop owner";
					ret = e.render(x);
					x[9]="\"Thanks for the help you scum\" one of them says as they throw you a wad of cash.";
					ret = e.render(x);
					x[11] = "    You now have " + 5000 + " addtional " + "Money";
					x[22] = "(press enter to continue)";
					bd.giveGood(player_name, "Money", 5000);
					ret = e.render(x);
					return;
				}
				else{
					x[7]="The Police don't find any illegal goods in "+v+"'s shop";
					ret = e.render(x);
					x[9]="As you turn to leave you hear a noise behind you...";
					x[10]="You turn to see the shop owner as he lands a strike on your head...";
					ret = e.render(x);
					x[12]="The shop owner stands on the inside of the airlock and asks in the intercom";
					x[13]="Say your last words, Punk!";
					x[20]="(Type your punny excuse for bringing the cops into things and press enter)";
					ret = e.render(x);
					x[9]="The shop owner ignores your comments and sends you out the airlock. GAME OVER.";
					ret = e.render(x);
					bd.dropCharacter(player_name);
					e.close();
					System.exit(0);
				}
			}
			if(thugsCome(dlvl)){
				x[5]="The police do not come. A shame...";
				ret = e.render(x);
				x[7]="You hear something coming from a hallway in the airlock";
				ret = e.render(x);
				x[9]="A group of thugs slam you against the nearest wall";
				x[20]="(Type your punny excuse for bringing the cops into things and press enter)";
				ret = e.render(x);
				x[9]="The thugs ignore your comments and throw you out the airlock. GAME OVER.";
				ret = e.render(x);
				bd.dropCharacter(player_name);
				e.close();
				System.exit(0);
			}
			else{

				x[5]="The police do not come. A shame...";
				ret = e.render(x);
				return;
			}
		}else{
			x[18] = "Please enter a valid entry";
			callPolice(e,bd,v,gdArray);
			return;
		}
	}

	public static boolean policeCome(int plvl) {
		Random r = new Random();
		if (r.nextInt((105-plvl)) <= 50) {
			return true;
		}
		return false;
	}

	public static boolean thugsCome(int dlvl) {
		Random r = new Random();
		if (r.nextInt((105-dlvl)) <= 50) {
			return true;
		}
		return false;
	}

	private static void amountX(Engine e, DBInterface bd, String v,
			String good) {
		String[] x = getArrayFilledWithBlanks(23);
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
			while (!rs.getString("goodName").equals(good)){
				if(!rs.next()){x[3]="There are no goods";break;}
				rs.previous();
				if (rs.isLast()) {
					break;
				}
				rs.next();}
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
			x[10] = "";
			x[11] = "";
			if (ret.equals("r")) {
				vendorX(e, bd, v);
				return;
			}
			int choice;
			try {
				choice = Integer.parseInt(ret);
			}
			catch(Exception exc){
				x[18] = "   Please enter a valid entry";
				continue;
			}
			System.out.println(pw + ","+gw*choice);
			if (choice * gw > pw) {
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
				traderecieptX(e, bd, v, good, choice, gc);
				return;
			}	
		}				
	}

	private static void spacedockX(Engine e, DBInterface bd) {
		String[] x = getArrayFilledWithBlanks(23);
		x[0] = "Spacedock Screen";
		x[2] = "This area currently under construction.";	
		x[18] = "(press enter to return to previous screen)";
		e.render(x);

	}

	private static boolean systemX(Engine e, DBInterface bd, boolean must) {
		String[] x = getArrayFilledWithBlanks(23);
		String[] p = getArrayFilledWithBlanks(10);
		String ret;
		x[0] = "System Screen";	
		x[1] = "You are currently on " + player_planet + " in the " + player_system + " system";
		x[22] = "(Select option and press enter)";
		if (!must)
			x[3] = "    g) Galactic map";
		x[4] = "    r) Return to orbit screen";
		try {
			ResultSet rs = bd.getSystemPlanets(player_system);
			if(!rs.first()){x[3]="There are no planets in this system";}
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
			case "g":
				boolean change = galaxyX(e, bd);
				return systemX(e, bd, change);
			case "r":
				if (must)
					break;
				return false;
			default:
				try {
					int choice = Integer.parseInt(ret);
					if (choice>0 && choice<11) {
						player_planet = p[choice-1];
						//System.out.println("In choice to set planet. player_planet = "+player_planet);
						bd.setPlayerPlanet(player_planet, player_name);
					}
				} catch (NumberFormatException exc) {
					x[18] = "   Please enter a valid entry";
					continue;
				}
				return true;
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
		try {
			ResultSet rs = bd.getSystems();
			if(rs!=null){

				if(!rs.first()){x[3]="There are no systems in the galaxy";}
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
				try {
					int choice = Integer.parseInt(ret);
					if (choice>0 && choice<11) {
						if (player_system.equals(p[choice-1])) {
							return false;
						}
						player_system = p[choice-1];
						player_planet = "nothing";
						return true;
					}
				} catch (NumberFormatException exc) {
					x[18] = "   Please enter a valid entry";
					continue;
				}
				break;
			}
		}
	}

	@SuppressWarnings("unused")
	private static void emptyX(Engine e, DBInterface bd) {
		String[] x = getArrayFilledWithBlanks(23);
		String ret;
		x[0] = "System Screen";	
		x[1] = "You are currently on " + player_planet + " in the " + player_system + " system";
		x[22] = "(Select option and press enter)";
		while(true) {
			ret = e.render(x);
			if (ret.equals("r")) {
				break;
			}
		}
	}

	private static void traderecieptX(Engine e, DBInterface bd, String vendor, String good, int quantity, int unitcost) {
		String[] x = getArrayFilledWithBlanks(23);
		x[0] = "Reciept of the Trade of Goods";	
		x[1] = "You are currently on " + player_planet + " in the " + player_system + " system";
		x[3] = "You have successfully traded "  + quantity + " of " + good + " for " + unitcost + " credits";
		x[5] = vendor + " thanks you for your buisness";
		x[22] = "(Press enter)";
		e.render(x);
	}
}

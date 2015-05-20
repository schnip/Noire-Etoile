package ui;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

import database.DBInterface;

public class Event {
	
	public static final int PLANET_MOVE = 0;
	public static final int ON_SURFACE = 1;
	public static final int SPACEDOCK = 2;
	public static final int MAX_SCRAP_QUANTITY = 10;
	public static final int POLICE_RATE = 2;
	public static final int POLICE_THRESHOLD = 50;
	public static final int POLICE_LEANIENCY = 50;
	
	private static DBInterface bd;
	private static String planet;
	private static String player;
	private static int type;
	private static Engine e;
	private static Random r;
	
	public static void runEvent(DBInterface bd, String planet, String player, int type, Engine e) {
		Event.bd = bd;
		Event.planet = planet;
		Event.player = player;
		Event.type = type;
		Event.e = e;
		Event.r = new Random();

		if (r.nextInt(POLICE_RATE) == 0) {
			police();
			return;
		}
		
		
		// This is the default
		salvage();
	}

	private static void salvage() {
		String good = getRandomGood();
		int qty = r.nextInt(MAX_SCRAP_QUANTITY) + 1;
		if (good == null) {
			return;
		}
		String[] x = Launcher.getArrayFilledWithBlanks(23);
		String ret;
		x[4] = "    While on your journey, you encountered some scrap that you could salvage";
		x[6] = "    You now have " + qty + " addtional " + good;
		x[22] = "(press enter to continue)";
		bd.giveGood(player, good, qty);
		ret = e.render(x);
	}

	private static void police() {
		String[] x = Launcher.getArrayFilledWithBlanks(23);
		String ret;
		x[4] = "    A police cruiser flies by on its runs to intercept illegal goods";
		x[22] = "(press enter to continue)";
		ret = e.render(x);
		if (POLICE_THRESHOLD <= (r.nextInt(POLICE_LEANIENCY) + bd.getPoliceLevel(planet))) {
			x[6] = "    The police stop and board your ship to look for illegal goods";
			ret = e.render(x);
			try {
				ResultSet rs = bd.getGoods(player);
				rs.first();
				while (true) {
					if (rs.isLast()) {
						break;
					}
					if (0 == bd.getLegality(rs.getString("goodName"))) {
						x[8] = "    The police officers come back to you carrying your " + rs.getString("goodName");
						ret = e.render(x);
						x[10] = "    They ask for an explanation.";
						x[22] = "(type your response and press enter)";
						ret = e.render(x);
						x[12] = "    They disregard your response and throw you out the airlock";
						x[14] = "    GAME OVER";
						x[22] = "(press enter to release your final breath)";
						ret = e.render(x);
						bd.dropCharacter(player);
						e.close();
						System.exit(0);
					}
					rs.next();
				}
				x[8] = "    The search comes back clean and the police continue on their way";
				ret = e.render(x);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			x[6] = "    The cruiser passes uneventfully by without boarding your ship";
			ret = e.render(x);
			return;
		}
	}

	private static String getRandomGood() {
		ArrayList<String> goods = new ArrayList<String>();
		try {
			ResultSet rs = bd.getAllGoods();
			if (rs == null) {
				return null;
			}
			rs.first();
			while (!rs.isLast()) {
				goods.add(rs.getString("goodName"));
				rs.next();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (goods.isEmpty()) {
			return null;
		}
		return goods.get(r.nextInt(goods.size()));
	}
	
}

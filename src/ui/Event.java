package ui;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

import database.DBInterface;

public class Event {
	
	public static final int PLANET_MOVE = 0;
	public static final int ON_SURFACE = 1;
	public static final int SPACEDOCK = 2;
	
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
		
		
		// This is the default
		salvage();
	}

	private static void salvage() {
		String good = getRandomGood();
		if (good == null) {
			return;
		}
		String[] x = Launcher.getArrayFilledWithBlanks(23);
		String ret;
		x[0] = "System Screen";	
		x[22] = "(Select option and press enter)";
		while(true) {
			ret = e.render(x);
			if (ret.equals("r")) {
				break;
			}
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

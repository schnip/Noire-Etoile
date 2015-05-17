package ui;

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
	private static Random r;
	
	public static void runEvent(DBInterface bd, String planet, String player, int type) {
		Event.bd = bd;
		Event.planet = planet;
		Event.player = player;
		Event.type = type;
		Event.r = new Random();
		
		
		// This is the default
		salvage();
	}

	private static void salvage() {
		// TODO Auto-generated method stub
		
	}
	
}

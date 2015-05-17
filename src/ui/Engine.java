package ui;

import java.util.Random;
import java.util.Scanner;

import database.DBInterface;


public class Engine {

	private Scanner in;
	private boolean isWindows;
	private static final int WINDOW_WIDTH = 80;
	
	public Engine() {
		in = new Scanner(System.in);
		isWindows = System.getProperty("os.name").contains("Windows");
	}
	
	public void close() {
		in.close();
	}
	
	public String render(String[] x) {
		if (isWindows)
			System.out.println("");
		for (int i = 0;i<23;i++) {
			System.out.println(x[i].substring(0, Math.min(x[i].length(), WINDOW_WIDTH)));
		}
		return this.in.nextLine();
	}
	
	public boolean isEvent(DBInterface bd, String planet) {
		Random r = new Random();
		if (r.nextInt(5) == 0) {
			return true;
		}
		return false;
	}
	
	public void runEvent(DBInterface bd, String planet, String player, int type) {
		Random r = new Random();
	}

}
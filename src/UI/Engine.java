package UI;

import java.util.Scanner;

public class Engine {

	private Scanner in;
	private boolean isWindows;
	
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
			System.out.println(x[i]);
		}
		return this.in.nextLine();
	}

}
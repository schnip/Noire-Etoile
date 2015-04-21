package UI;

import java.util.Scanner;

public class Engine {

	private Scanner in;
	
	public Engine() {
		in = new Scanner(System.in);
	}
	
	public void close() {
		in.close();
	}
	
	public String render(String[] x) {
		for (int i = 0;i<23;i++) {
			System.out.println(x[i]);
		}
		return this.in.nextLine();
	}

}
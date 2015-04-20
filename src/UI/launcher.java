package UI;

import java.util.Scanner;

public class launcher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello World");
		Scanner in = new Scanner(System.in);
		for (int j = 0;j<24;j++) {
			System.out.println("");
		}
		System.out.println("Enter some text and hit enter");
		String s = in.nextLine();
		System.out.println("You entered: " + s);
	}

}

package UI;


public class Launcher {

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
		x[22] = "Enter a string and press enter (q to exit)";
		ret = e.render(x);
		while(!ret.equals("q")) {
			x[3] = "The string you entered was: " + ret;
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
}

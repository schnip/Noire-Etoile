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
		x[22] = "Enter a string and press enter (q to exit)";
		ret = e.render(x);
		while(true) {
			if (ret == "q") {
				break;
			}
			x[3] = "The string you entered was: " + ret;
			ret = e.render(x);
		}
	}
	
	public static String[] getArrayFilledWithBlanks(int len) {
		String[] x = new String[len];
		for (int i = 0; i<len; i++) {
			x[i] = "";
		}
		return x;
	}
}

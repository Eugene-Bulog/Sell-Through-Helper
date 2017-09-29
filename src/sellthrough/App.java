package sellthrough;

import java.io.IOException;

public class App {
	
	public static void main (String[] args) {
		try {
			new Reader().readSheet(args[0]);
		} catch (IOException e) {
			System.out.println("Error loading sell through sheet");
		}
	}
}

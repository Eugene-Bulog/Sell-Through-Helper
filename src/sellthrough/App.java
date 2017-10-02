package sellthrough;

import java.io.IOException;

public class App {
	
	public static void main (String[] args) {
		try {
			new Reader(args[0], args[1]).readWeeklySheet();
		} catch (IOException e) {
			System.out.println("Error loading sell through sheet");
		}
	}
}

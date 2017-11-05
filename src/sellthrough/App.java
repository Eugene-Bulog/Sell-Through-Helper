package sellthrough;

import java.io.IOException;

public class App {
	
	public static void main (String[] args) {
		try {
			if (args[0].endsWith("txt")) {
				new Reader(args[0], args[1]).readWeeklySheet();
			}
			else {
				System.out.println("Please convert spreadsheet to .txt (tab separated) before dropping into the program.");
				throw new IOException();
			}
		} catch (IOException e) {
			System.out.println("Error loading sell through sheet, processing cancelled.");
		}
	}
}

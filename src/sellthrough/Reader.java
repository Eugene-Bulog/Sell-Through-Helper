package sellthrough;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
/**
 * Class for reading the sell through sheet and creating the SKU objects for each row.
 * @author Eugene Bulog
 */
	
	// Strings containing the paths to the sheets
	private String _weeklyPath;
	
	/**
	 * Constructor for Reader class
	 * @param weeklyPath The path to the weekly sell through report recieved from reseller
	 */
	public Reader(String weeklyPath) {
		_weeklyPath = weeklyPath;
	}
	
	
	/**
	 * Void for reading the sell through spreadsheet and saving the data for each product
	 * to this object's fields
	 * @throws IOException 
	 */
	public void readWeeklySheet() throws IOException {
		
		// Create file reader to read at path and buffered reader to read file reader output
		FileReader fReader = new FileReader(_weeklyPath);
		BufferedReader bReader = new BufferedReader(fReader);
		
		int i = 0;
		String line = new String();
		String[][] splitLine = new String[999][30];
		
		// Reads and saves each line of the sheet
		while ((line = bReader.readLine()) != null) {

			//Splits around commas
			String[] split = line.split(",");

			// Fills output array with result of split (any non filled spaces remain null)
			for(int pos = 0; pos < split.length; pos++) {
				splitLine[i][pos] = split[pos];
			}
			
			i++;
		}

		// flush buffered & file readers
		bReader.close();
		fReader.close();

		// Create the objects for each line's data
		saveSKU(splitLine);
			
	}
	
	
	/**
	 * Creates and stores a SKU object for each product in the read file
	 * @param lines The lines read from the file
	 */
	private void saveSKU(String[][] lines) {
		
		int i = 0;
		String col;
		
		int codeCol = 0;
		int descCol = 0;
		int wk1Col = 0;
		int sohCol = 0;
		
		// Locates and stores the column numbers containing relevant data
		while ((col = lines[0][i]) != null) {
			if (col.contains("ItemCode")) {
				codeCol = i;
			}
			else if (col.contains("ItemName")) {
				descCol = i;
			}
			else if (col.contains("WK1")) {
				wk1Col = i;
			}
			else if (col.contains("SOH")) {
				sohCol = i;
			}
			i++;
		}
		
		
		SKU[] list = new SKU[999]; // placeholder
		i = 1;
		// Creates an object for each SKU
		while ((col = lines[i][0]) != null) {
			list[i - 1] = new SKU(lines[i][codeCol], lines[i][descCol], 
					Integer.parseInt(lines[i][wk1Col]), Integer.parseInt(lines[i][sohCol]));
			i++;
			
		}
		
	}
	
}

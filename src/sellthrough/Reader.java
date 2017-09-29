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
	
	// Ints relating to the column numbers of the important data
	private int _sohCol;
	private int _wk1Col;
	private int _codeCol;
	private int _descCol;
	
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

		// Locates and stores the column numbers containing relevant data
		i = 0;
		String col;
		while ((col = splitLine[0][i]) != null) {
			if (col.contains("ItemCode")) {
				_codeCol = i;
			}
			else if (col.contains("ItemName")) {
				_descCol = i;
			}
			else if (col.contains("WK1")) {
				_wk1Col = i;
			}
			else if (col.contains("SOH")) {
				_sohCol = i;
			}
			i++;
		}
		
			
	}
	
	
}

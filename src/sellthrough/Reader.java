package sellthrough;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
/**
 * Class for reading the sell through sheet and creating the SKU objects for each row.
 * @author Eugene Bulog
 */
	
	/**
	 * Void for reading the sell through spreadsheet and saving the input stream
	 * to this object's fields
	 * @throws IOException 
	 */
	public void readSheet(String path) throws IOException {
		
		// Create file reader to read at path and buffered reader to read file reader output
		FileReader fReader = new FileReader(path);
		BufferedReader bReader = new BufferedReader(fReader);
		
		String line[] = new String[999];
		int i = 0;
		
		while ((line[i] = bReader.readLine()) != null) {
			System.out.println(line[i]);
			i++;
		}

		// flush buffered & file readers
		bReader.close();
		fReader.close();

			
	}
}

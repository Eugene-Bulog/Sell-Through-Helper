package sellthrough;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Writer {
	/**
	 * Writer class which handles writing to an xlsx file, and sorts accordingly
	 * @author Eugene Bulog
	 */
	
	
	private List<SKU> _skuList = new ArrayList<SKU>();
	private String _outPath; // Path to find the output xlsx
	private File _outFile;
	private FileInputStream _fis;
	private int _latestCol = 4;
	private XSSFWorkbook _outBook;
	private String _date = "placeholder";
	
	/**
	 * Constructor for Writer class
	 * @param skuList
	 */
	public Writer(List<SKU> skuList, String outPath) {
		_skuList = skuList;
		_outPath = outPath;
		
		// Set up file
		_outFile = new File(_outPath);
	}
	
	
	/**
	 * Writes the sales figures to the external xlsx specified in run configuration.
	 * All writing methods must be called after _outBook is defined in this method, within
	 * the try/catch.
	 */
	public void writeOut() {
		
		
		try {
			_fis = new FileInputStream(_outFile);
			
			// set up workbook and sheet
			_outBook = new XSSFWorkbook(_fis);
			XSSFSheet summarySheet = _outBook.getSheetAt(0);
			
			// Finds column for this week, and titles it
			_latestCol = summarySheet.getRow(0).getLastCellNum() + 1;
			summarySheet.getRow(0).createCell(_latestCol).setCellValue(_date);
			
			// saves values for each sku
			Scanner scanner = new Scanner(System.in);
			
			for (int i = 0; i < _skuList.size(); i++) {
				
				// Check if this sku is already saved or not
				if (!updateSKU(_skuList.get(i),0)) {
					// If it isn't saved, save it to summary
					addSkuToSheet(_skuList.get(i),0);
					
					
					// Create or find the non-summary sheet this is not on
					System.out.println("What category does " + _skuList.get(i).getDescription() + " go in?");
					String category = scanner.nextLine();
					
					
					int findSheet = _outBook.getSheetIndex(category);
					
					if (findSheet == -1) {
						findSheet = setUpSheet(category);
					}
					
					addSkuToSheet(_skuList.get(i),findSheet);
					
				}
				else {
					// if it exists, find the other sheet it's on, and update it there too
					for (int j = 1; j < _outBook.getNumberOfSheets(); j++) {
						if (updateSKU(_skuList.get(i),j)) {
							break;
						}
					}
				}
			}
			
			scanner.close();
			
			// write and close
			FileOutputStream outStream = new FileOutputStream(_outFile);
			_outBook.write(outStream);
			_outBook.close();
			_fis.close();
			outStream.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Sets up a NEW sheet with the specified name. Adds initial column titles.
	 * Method caller's responsibility to check sheet doesn't already exist
	 * @param sheetName The name of the sheet to set up
	 * @return The index of the new sheet
	 */
	private int setUpSheet(String sheetName) {
		
			// Create sheet
			XSSFSheet sheet = _outBook.createSheet();
			int sheetIndex = _outBook.getSheetIndex(sheet);
			_outBook.setSheetName(sheetIndex, sheetName);
				
			
			// Set up title rows
			Row titleRow = sheet.createRow(0);
			titleRow.createCell(0).setCellValue("SKU");
			titleRow.createCell(1).setCellValue("Product Description");
			titleRow.createCell(2).setCellValue("SOH");
			titleRow.createCell(_latestCol).setCellValue(_date);
			
			return sheetIndex;
		
	}
	
	/**
	 * Appends the given SKU to the specified sheet
	 * @param sku The SKU to add
	 * @param sheetIndex The index of the sheet to add it to
	 */
	private void addSkuToSheet(SKU sku, int sheetIndex) {
		
			XSSFSheet sheet = _outBook.getSheetAt(sheetIndex);
			Row row = sheet.createRow(sheet.getLastRowNum() + 1);
			
			// Appends SKU to sheet
			row.createCell(0).setCellValue(sku.getCode());
			row.createCell(1).setCellValue(sku.getDescription());
			row.createCell(2).setCellValue(sku.getSOH());
			row.createCell(_latestCol).setCellValue(sku.getWk1Sold());

		
	}
	
	/**
	 * Updates the sales figures for the sku in the specified sheet if it can be found.
	 * @param sku The sku to update
	 * @param sheetIndex The index of the sheet to update the sku on
	 * @return True if the sku was found and updated, otherwise false
	 */
	private boolean updateSKU(SKU sku, int sheetIndex) {
		XSSFSheet sheet = _outBook.getSheetAt(sheetIndex);
		
		// Sets date column
		if (sheet.getRow(0) != null) {
			sheet.getRow(0).createCell(_latestCol).setCellValue(_date);
		}
		
		
		for (Row row : sheet) {
			if (row.getCell(0).getStringCellValue().equals(sku.getCode())) {
				row.createCell(_latestCol).setCellValue(sku.getWk1Sold());
				row.createCell(2).setCellValue(sku.getSOH());
				return true;
			}
		}
		

		
		
		return false;
	}
	
}

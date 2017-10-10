package sellthrough;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	 * Writes the sales figures to the external xlsx specified in run configuration
	 */
	public void writeOut() {
		
		
		
		try {
			_fis = new FileInputStream(_outFile);
			
			// set up workbook and sheet
			XSSFWorkbook outBook = new XSSFWorkbook(_fis);
			XSSFSheet summarySheet = outBook.getSheetAt(0);
			
			
			
			
			
			// saves values for each sku
			for (int i = 1; i < _skuList.size(); i++) {
				Row row = summarySheet.getRow(i);
				if (row == null) {
					row = summarySheet.createRow(i);
				}
				row.createCell(0).setCellValue(_skuList.get(i - 1).getCode());
				row.createCell(1).setCellValue(_skuList.get(i - 1).getDescription());
				row.createCell(2).setCellValue(_skuList.get(i - 1).getSOH());
				row.createCell(4).setCellValue(_skuList.get(i - 1).getWk1Sold());
			}
			
			// write and close
			FileOutputStream outStream = new FileOutputStream(_outFile);
			outBook.write(outStream);
			outBook.close();
			_fis.close();
			outStream.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Sets up a NEW sheet with the specified name. Adds initial column titles
	 * @param sheetName The name of the sheet to set up
	 */
	private void setUpSheet(String sheetName) {
		
		try {
			
			_fis = new FileInputStream(_outFile);
				
			// set up workbook and sheet
			XSSFWorkbook outBook = new XSSFWorkbook(_fis);
			XSSFSheet sheet = outBook.createSheet();
			outBook.setSheetName(outBook.getSheetIndex(sheet), sheetName);
				
			
			// Set up title rows
			Row titleRow = sheet.createRow(0);
			titleRow.createCell(0).setCellValue("SKU");
			titleRow.createCell(1).setCellValue("Product Description");
			titleRow.createCell(2).setCellValue("SOH");
			
			// write and close
			FileOutputStream outStream = new FileOutputStream(_outFile);
			outBook.write(outStream);
			outBook.close();
			_fis.close();
			outStream.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}

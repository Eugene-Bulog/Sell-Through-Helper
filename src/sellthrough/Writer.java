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
	
	
	/**
	 * Constructor for Writer class
	 * @param skuList
	 */
	public Writer(List<SKU> skuList, String outPath) {
		_skuList = skuList;
		_outPath = outPath;
	}
	
	
	/**
	 * Writes the sales figures to the external xlsx specified in run configuration
	 */
	public void writeOut() {
		
		// Set up file and input stream
		File outFile = new File(_outPath);
		FileInputStream fis;
		try {
			fis = new FileInputStream(outFile);
			
			// set up workbook and sheet
			XSSFWorkbook outBook = new XSSFWorkbook(fis);
			XSSFSheet summarySheet = outBook.getSheetAt(0);
			
			// saves values for each sku
			for (int i = 0; i < _skuList.size(); i++) {
				Row row = summarySheet.createRow(i);
				row.createCell(0).setCellValue(_skuList.get(i).getCode());
				row.createCell(1).setCellValue(_skuList.get(i).getDescription());
				row.createCell(2).setCellValue(_skuList.get(i).getSOH());
				row.createCell(3).setCellValue(_skuList.get(i).getWk1Sold());
			}
			
			// write and close
			FileOutputStream outstream = new FileOutputStream(outFile);
			outBook.write(outstream);
			outBook.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}

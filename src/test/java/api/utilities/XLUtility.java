package api.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLUtility {

	public FileInputStream fis;
	public FileOutputStream fos;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;	
	public CellStyle style;
	String path;
	
	public XLUtility(String path) {
		this.path = path;
	}
	
	public int getRowCount(String sheetName) throws IOException {
		fis = new FileInputStream(path);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum();
		workbook.close();
		fis.close();
		return rowCount;
	}
	
	
	public int getCellCount(String sheetName, int rowNum) throws IOException {
		fis = new FileInputStream(path);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);
		row=sheet.getRow(rowNum);
		int cellCount = row.getLastCellNum();
		workbook.close();
		fis.close();
		return cellCount;
	}
	
	public String getCellData(String sheetName, int rowNum, int column) throws IOException {
		fis = new FileInputStream(path);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		cell = row.getCell(column);
		
		DataFormatter formater = new DataFormatter();
		String data;
		try{
		data = formater.formatCellValue(cell);		//returns formatted value of cell as a string
		}
		catch(Exception e) {
			data="";			//initialized variable data
			e.printStackTrace();
		}
		
		workbook.close();
		fis.close();
		return data;
	}
	
	
	public void setCellData(String sheetName, int rowNum, int cellNum, String data) throws IOException {
		File xlFile =  new File(path);
		
		if(! xlFile.exists()) { 					//if file not exist then create new file
			workbook =  new XSSFWorkbook();
			fos = new FileOutputStream(path);
			workbook.write(fos);
		}
		
		fis = new FileInputStream(path);
		workbook =  new XSSFWorkbook(fis);
		
		if(workbook.getSheetIndex(sheetName)==-1) 		//if sheet not exists then create new Sheet
			workbook.createSheet(sheetName);
			sheet = workbook.getSheet(sheetName);
		
		
		if(sheet.getRow(rowNum)==null) 				//if row not exist then create new Row
			sheet.createRow(rowNum);
			sheet.getRow(rowNum);
		
		
		cell = row.createCell(cellNum);
		cell.setCellValue(data);
		fos = new FileOutputStream(path);
		workbook.write(fos);
		workbook.close();
		fis.close();
		fos.close();
			
	}
	
	

	
	
}

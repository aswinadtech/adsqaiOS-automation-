package com.twc.ios.app.excel;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.twc.ios.app.general.Driver;
import com.twc.ios.app.general.PropertyFile;




public class Excelread_Validation extends Driver {
	
	public static void main(String[] args) throws Exception{
		excelreadvalidation("Paths");
	}

	public static String[][] excelreadvalidation(String Type) throws Exception {
		
		Driver.property();
		PropertyFile.property();
		System.out.println("File path"+properties.getProperty("ExcelFilePath"));
		File f_validation= new File(properties.getProperty("ExcelFilePath"));
		
				//("/Users/aparna/Documents/Naresh/com.weather.customparam/ExcelFile/Weather_smoke.xls");
				//(properties.getProperty("Excel_validation_Path"));
		FileInputStream fis_validation = new FileInputStream(f_validation);
		HSSFWorkbook wb_validation = new HSSFWorkbook(fis_validation);
		HSSFSheet ws = wb_validation.getSheet(Type);

		int rownum = ws.getLastRowNum() + 1;
		int colnum = ws.getRow(0).getLastCellNum();
		String data[][] = new String[rownum][colnum];

		for (int i = 0; i < rownum; i++) {
		    HSSFRow row = ws.getRow(i);

		    for (int j = 0; j < colnum; j++) {
			HSSFCell cell = row.getCell(j);
			String value = CellToString.ctos(cell);
			data[i][j] = value;
			System.out.println("Values are :" + value + " : data[" + i + "][" + j + "]");

		    }
		}
		// wb.close();
		return data;

	    }

}

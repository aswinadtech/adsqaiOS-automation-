package com.twc.ios.app.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.twc.ios.app.general.Driver;

public class Write_result extends Driver{
	public void WriteResult( String sheetName, String tmp_val, int i, int resultColIndex) {
		String Path =null;
		
		if(sheetName.equals("Paths")||sheetName.equals("Capabilities")){
			Path =properties.getProperty("ExcelFilePath");
		}else if(sheetName.equals("Ad Slots_iPhone")){
			Path =properties.getProperty("ExcelFilePath_AdUnits");
		}else {
			Path =properties.getProperty("ExcelFilePath_CustParam_Result");
		
		}
		try {

			FileInputStream file = new FileInputStream(Path);
			HSSFWorkbook workbook = new HSSFWorkbook(file);

			HSSFSheet sheet = workbook.getSheet(sheetName);

			Cell searchText3 = sheet.getRow(i).getCell(resultColIndex);
			searchText3.setCellValue(tmp_val);
			

			//			    Cell searchText4 = sheet.getRow(rowIndex).getCell(valueColIndex);
			//			    searchText4.setCellValue(Val);

			file.close();

			FileOutputStream outFile = new FileOutputStream(Path);
			workbook.write(outFile);
			outFile.close();

		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	
	
	
	
	public void WriteResultAllParams( String sheetName, String Param_Type, String tmp_val, int i,int ValueColIndex, int resultColIndex) {
		String Path =null;
		
			Path =properties.getProperty("ExcelFilePath_CustParam_Result");
		

		try {

			FileInputStream file = new FileInputStream(Path);
			HSSFWorkbook workbook = new HSSFWorkbook(file);

			HSSFSheet sheet = workbook.getSheet(sheetName);

			Cell searchText2 = sheet.getRow(i).getCell(ValueColIndex);
			searchText2.setCellValue(Param_Type);
			
			Cell searchText3 = sheet.getRow(i).getCell(resultColIndex);
			searchText3.setCellValue(tmp_val);

			

			file.close();

			FileOutputStream outFile = new FileOutputStream(Path);
			workbook.write(outFile);
			outFile.close();

		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}


}

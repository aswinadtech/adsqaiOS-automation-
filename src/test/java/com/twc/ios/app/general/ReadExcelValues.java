package com.twc.ios.app.general;

import com.twc.ios.app.excel.ExcelData;

public class ReadExcelValues {
	 public static String[][] data =null;
	 public static String[][] nextdata =null;
	public static void excelValues(String Excelname,String Sheetname) throws Exception{
		 data = new String[10][10];
		ExcelData er = new ExcelData();
		data = er.excelread(Excelname, Sheetname);
        //System.out.println("data is :"+ data);
	}
	
//	public static void excelValuesnextSheet(String nextSheetname) throws Exception{
//		nextdata = new String[10][10];
//		ExcelData er = new ExcelData();
//		nextdata = er.excelread(nextSheetname, nextSheetname);
//       //System.out.println("data is :"+ data);
//	}

}

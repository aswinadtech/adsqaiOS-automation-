package com.twc.ios.app.excel;

public class PopulateData {

    public static void main(String[] args) throws Exception {
	String[][] data = new String[22][10];
//	ExcelData er = new ExcelData();
//	er.excelread();
	
	Excelread_Validation exv= new Excelread_Validation();
	data = exv.excelreadvalidation("tmp");
	
	
	for (int k=1;k<=18;k++){
		System.out.println("J value is ::"+k);
		System.out.println("data is ::"+data[k][1]);
	}
    }
}

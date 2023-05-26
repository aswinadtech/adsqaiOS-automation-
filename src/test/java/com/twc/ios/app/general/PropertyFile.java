package com.twc.ios.app.general;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyFile {

	protected static FileInputStream fileInput;
	protected static Properties properties = new Properties();

	public static void property() throws IOException
	{
		//File file = new File("/Users/jenkins/Documents/projects/ads-ios-Regression/DataFile.Properties");
		File file = new File(System.getProperty("user.dir") + "/DataFile.Properties");
		try {
			fileInput = new FileInputStream(file);
			properties.load(fileInput);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}		

	}
}

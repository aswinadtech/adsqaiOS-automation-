package com.twc.ios.app.general;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.poi.util.SystemOutLogger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.twc.ios.app.charlesfunctions.CharlesProxy;
import com.twc.ios.app.excel.ExcelData;
import com.twc.ios.app.functions.Functions;
import com.twc.ios.app.pages.AirQualityCardScreen;
import com.twc.ios.app.pages.AlertCenterScreen;
import com.twc.ios.app.pages.AndroidAirQualityCardScreen;
import com.twc.ios.app.pages.AndroidAlertCenterScreen;
import com.twc.ios.app.pages.AndroidDailyCardScreen;
import com.twc.ios.app.pages.AndroidDailyNavTab;
import com.twc.ios.app.pages.AndroidHomeNavTab;
import com.twc.ios.app.pages.AndroidHourlyCardScreen;
import com.twc.ios.app.pages.AndroidHourlyNavTab;
import com.twc.ios.app.pages.AndroidNewsCardScreen;
import com.twc.ios.app.pages.AndroidRadarCardScreen;
import com.twc.ios.app.pages.AndroidRadarNavTab;
import com.twc.ios.app.pages.AndroidTodayCardScreen;
import com.twc.ios.app.pages.AndroidVideoCardScreen;
import com.twc.ios.app.pages.AndroidVideoNavTab;
import com.twc.ios.app.pages.CurrentConditionsCardScreen;
import com.twc.ios.app.pages.DailyCardScreen;
import com.twc.ios.app.pages.DailyNavTab;
import com.twc.ios.app.pages.HomeNavTab;
import com.twc.ios.app.pages.HourlyNavTab;
import com.twc.ios.app.pages.LifeStyleCardScreen;
import com.twc.ios.app.pages.NewsCardScreen;
import com.twc.ios.app.pages.PlanningCardScreen;
import com.twc.ios.app.pages.RadarCardScreen;
import com.twc.ios.app.pages.RadarNavTab;
import com.twc.ios.app.pages.SeasonalHubCardScreen;
import com.twc.ios.app.pages.TodayCardScreen;
import com.twc.ios.app.pages.VideoCardScreen;
import com.twc.ios.app.pages.VideoNavTab;
import com.twc.ios.app.pages.WatsonCardScreen;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.functions.ExpectedCondition;
import io.appium.java_client.ios.IOSDriver;

public class Utils extends Functions {

	public static File outfile = null;
	public static List<String> customParamsList = new ArrayList<String>();
	public static List<String> listOf_b_Params = new ArrayList<String>();
	public static List<String> listOf_criteo_Params = new ArrayList<String>();
	public static int aaxbidErrorCount = 0;
	public static int criteoparamErrorCount = 0;
	public static int aaxcallsSize = 0;
	public static boolean isaaxgampadcallexists = false;
	public static int criteocallsSize = 0;
	public static int aaxcallsResponseSize = 0;
	public static int criteocallsResponseSize = 0;
	public static int criteogampadcallcount = 0;
	public static String previous_IPAddress = null;
	public static String current_IPAddress = null;
	public static String current_WifiName = "Test";
	public static String iOSVersion = null;
	public static String iPhoneUDID = null;
	public static LinkedHashMap<String, String> feedCardsMap = new LinkedHashMap<String, String>();
	public static LinkedHashMap<String, String> feedAdCardsMap = new LinkedHashMap<String, String>();
	public static int feedCardCurrentPosition = 3;
	public static int aaxHealthArticlesCheckCount = 0;
	public static int criteoHealthArticlesParamCpmCheckStartCount = 0;
	public static int criteoHealthArticlesParamSizeNUrlCheckStartCount = 0;
	public static String healthcriteoArticleCheckHappenedSheet = "Test";
	public static String healthaaxArticleCheckHappenedSheet = "Test";
	public static String[] initialCardNameCheckList = { "anchor_tempLabel", "anchor_weatherImage",
			"anchor_feelsLikeLabel", "anchor_hiLowLabel", "TodayTab", "HourlyTab", "DailyTab", "Morning", "Afternoon",
			"Evening", "Overnight" };
	public static LinkedHashMap<String, String> wfxParameters = new LinkedHashMap<String, String>();
	public static String placeId = null;
	public static String hlzip = null;

	public enum CardNames {
		video, today, news, aq, maps, daily
	}
	
	/**
	 * Get Current Wifi Name and Write to properties file
	 * This method works only if the device is connected to system
	 * Call this method after device is launched, to get the ios Version from Capabilities incase of failure
	 * @throws Exception
	 */
	public static void getiOSVersion() throws Exception {
		// TODO Auto-generated method stub
		Process p = null;
		try {
			File bashFile = new File(System.getProperty("user.dir") + "/getOSName.sh");
			String[] cmd = { "/bin/sh", bashFile.getName() };

			p = Runtime.getRuntime().exec(cmd);

			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream())); 
			//System.out.println(reader.lines().count());
			
			String s;
					
			while ((s = reader.readLine()) != null) {
				System.out.println("Curent iOS Version is: " + s);
				logStep("Curent iOS Version is: " + s);
				iOSVersion = s;
							
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("There is an exception while finding iOS Version, hence reading from Capabilities");
			logStep("There is an exception while finding iOS Version, hence reading from Capabilities");
			e.printStackTrace();
			iOSVersion = (String) Ad.getCapabilities().getCapability("platformVersion");
		}
		p.destroy();
			
	}
	
	/**
	 * Get Current iPhone Unique DeviceID
	 * This method works only if the device is connected to system
	 * Call this method before launched, gets the UDID from capabilities incase of failure
	 * @throws Exception
	 */
	public static void getiPhoneUDID() throws Exception {
		// TODO Auto-generated method stub
		Process p = null;
		try {
			File bashFile = new File(System.getProperty("user.dir") + "/getiPhoneUDID.sh");
			String[] cmd = { "/bin/sh", bashFile.getName() };

			p = Runtime.getRuntime().exec(cmd);

			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream())); 
			//System.out.println(reader.lines().count());
			
			String s;
					
			while ((s = reader.readLine()) != null) {
				System.out.println("Curent iPhone UDID is: " + s);
				logStep("Curent iPhone UDID is: " + s);
				iPhoneUDID = s.trim();
							
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("There is an exception while finding UDID of connected device, hence reading from Capabilities");
			logStep("There is an exception while finding UDID of connected device, hence reading from Capabilities");
			e.printStackTrace();
			//iPhoneUDID = (String) Ad.getCapabilities().getCapability("platformVersion");
		}
		p.destroy();
		
	}

	/**
	 * Get Current Wifi Name and Write to properties file
	 * @throws Exception
	 */
	public static void getCurrentSSIDName() throws Exception {
		// TODO Auto-generated method stub
		Process p;
		try {
			File bashFile = new File(System.getProperty("user.dir") + "/getSSIDName.sh");
			String[] cmd = { "/bin/sh", bashFile.getName() };

			p = Runtime.getRuntime().exec(cmd);

			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			String s;
			while ((s = reader.readLine()) != null) {
				System.out.println("Current Wifi Name is : " + s);
				logStep("Current Wifi Name is : " + s);
				current_WifiName = s;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("There is an exception while finding current Wifi Name");
			e.printStackTrace();
		}

		FileOutputStream fos = new FileOutputStream(
				System.getProperty("user.dir") + properties.getProperty("dataFilePath"));
		properties.setProperty("wifiName", current_WifiName);
		properties.store(fos, "Current SSID Stored");
		fos.close();
		
	}
	
	/**
	 * Get Current MAC IP Address and Set IPhone Proxy based on input parameter
	 * @param enableProxy
	 * @throws Exception
	 */
	public static void getCurrentMacIPAddressAndSetiPhoneProxy(boolean enableProxy) throws Exception {
		// TODO Auto-generated method stub
		getCurrentSSIDName();
		Process p;
		try {
			File bashFile = new File(System.getProperty("user.dir") + "/getIpAddress.sh");
			String[] cmd = { "/bin/sh", bashFile.getName() };

			p = Runtime.getRuntime().exec(cmd);

			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String s;
			while ((s = reader.readLine()) != null) {
				System.out.println("Current IP Address is : " + s);
				logStep("Current IP Address is : " + s);
				current_IPAddress = s;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("There is an exception while finding current MAC IP Address");
			e.printStackTrace();
		}

		// System.out.println("Buid Name is : " + BuildName);
		if (!enableProxy) {
			System.out.println("Since enableProxy set to false, iPhone Proxy setting to Off");
			logStep("Since enableProxy set to false, iPhone Proxy setting to Off");
			launchiOSSettings();
			enableDeviceProxy(properties.getProperty("wifiName"), enableProxy);
		} else {
			FileOutputStream fos = new FileOutputStream(
					System.getProperty("user.dir") + properties.getProperty("dataFilePath"));
			properties.setProperty("current_IPAddress", current_IPAddress);
			properties.store(fos, "Current MAC Address to update proxy settings in iPhone");
			fos.close();
			previous_IPAddress = properties.getProperty("previous_IPAddress");
			current_IPAddress = properties.getProperty("current_IPAddress");
			System.out.println("Previous IP Address Value :" + previous_IPAddress + "-----"
					+ "Current IP Address Value :" + current_IPAddress);
			logStep("Previous IP Address Value :" + previous_IPAddress + "-----" + "Current IP Address Value :"
					+ current_IPAddress);

			if (current_IPAddress.equals(previous_IPAddress)) {
				System.out
						.println("Previous and  Current IP Address are same no need to updte proxy settings of iPhone");
				logStep("Previous and  Current IP Address are same no need to updte proxy settings of iPhone");

			} else {
				System.out.println(
						"Previous and  Current IP Address are different, hence updating proxy settings of iPhone");
				logStep("Previous and  Current IP Address are different, hence updating proxy settings of iPhone");
				try {
					launchiOSSettings();
					enableDeviceProxy(current_IPAddress, properties.getProperty("wifiName"), enableProxy);
				} catch (Exception e) {
					System.out.println("An exception while updating proxy details in iPhone");
					logStep("An exception while updating proxy details in iPhone");
				}
				try {
					System.out.println("Updating previous IP details in properties file");
					logStep("Updating previous IP details in properties file");
					Utils.set_PreviousIPAddress();
				} catch (Exception e) {
					System.out.println("An exception while updating previous IP details in properties file");
					logStep("An exception while updating previous IP details in properties file");
				}

			}
		}

	}

	/**
	 * Get Current MAC IP Address and Set IPhone Proxy based on input parameters
	 * @param enableProxy
	 * @param ignoreIPCheck
	 * @throws Exception
	 */
	public static void getCurrentMacIPAddressAndSetiPhoneProxy(boolean enableProxy, boolean ignoreIPCheck)
			throws Exception {
		// TODO Auto-generated method stub
		getCurrentSSIDName();
		Process p;
		try {
			File bashFile = new File(System.getProperty("user.dir") + "/getIpAddress.sh");
			String[] cmd = { "/bin/sh", bashFile.getName() };

			p = Runtime.getRuntime().exec(cmd);

			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String s;
			while ((s = reader.readLine()) != null) {
				System.out.println("Current IP Address is : " + s);
				logStep("Current IP Address is : " + s);
				current_IPAddress = s;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("There is an exception while finding current MAC IP Address");
			logStep("There is an exception while finding current MAC IP Address");
			e.printStackTrace();
		}

		// System.out.println("Buid Name is : " + BuildName);
		if (!enableProxy) {
			System.out.println("Since enableProxy set to false, iPhone Proxy setting to Off");
			logStep("Since enableProxy set to false, iPhone Proxy setting to Off");
			launchiOSSettings();
			enableDeviceProxy(properties.getProperty("wifiName"), enableProxy);
		} else {
			FileOutputStream fos = new FileOutputStream(
					System.getProperty("user.dir") + properties.getProperty("dataFilePath"));
			properties.setProperty("current_IPAddress", current_IPAddress);
			properties.store(fos, "Current MAC Address to update proxy settings in iPhone");
			fos.close();
			previous_IPAddress = properties.getProperty("previous_IPAddress");
			current_IPAddress = properties.getProperty("current_IPAddress");
			System.out.println("Previous IP Address Value :" + previous_IPAddress + "-----"
					+ "Current IP Address Value :" + current_IPAddress);
			logStep("Previous IP Address Value :" + previous_IPAddress + "-----" + "Current IP Address Value :"
					+ current_IPAddress);
			if (ignoreIPCheck) {
				System.out.println("Since IP Check is ignored, updating proxy settings of iPhone");
				logStep("Since IP Check is ignored, updating proxy settings of iPhone");
				try {
					launchiOSSettings();
					enableDeviceProxy(current_IPAddress, properties.getProperty("wifiName"), enableProxy);
				} catch (Exception e) {
					System.out.println("An exception while updating proxy details in iPhone");
					logStep("An exception while updating proxy details in iPhone");
				}
				try {
					System.out.println("Updating previous IP details in properties file");
					logStep("Updating previous IP details in properties file");
					Utils.set_PreviousIPAddress();
				} catch (Exception e) {
					System.out.println("An exception while updating previous IP details in properties file");
					logStep("An exception while updating previous IP details in properties file");
				}
			} else {
				if (current_IPAddress.equals(previous_IPAddress)) {
					System.out.println(
							"Previous and  Current IP Address are same no need to updte proxy settings of iPhone");
					logStep("Previous and  Current IP Address are same no need to updte proxy settings of iPhone");

				} else {
					System.out.println(
							"Previous and  Current IP Address are different, hence updating proxy settings of iPhone");
					logStep("Previous and  Current IP Address are different, hence updating proxy settings of iPhone");
					try {
						launchiOSSettings();
						enableDeviceProxy(current_IPAddress, properties.getProperty("wifiName"), enableProxy);
					} catch (Exception e) {
						System.out.println("An exception while updating proxy details in iPhone");
						logStep("An exception while updating proxy details in iPhone");
					}
					try {
						System.out.println("Updating previous IP details in properties file");
						logStep("Updating previous IP details in properties file");
						Utils.set_PreviousIPAddress();
					} catch (Exception e) {
						System.out.println("An exception while updating previous IP details in properties file");
						logStep("An exception while updating previous IP details in properties file");
					}
				}
			}

		}

	}
	
	/**
	 * Get Current MAC IP Address and Set Android Proxy based on input parameters
	 * @param enableProxy
	 * @param ignoreIPCheck
	 * @throws Exception
	 */
	public static void getCurrentMacIPAddressAndSetandroidProxy(boolean enableProxy, boolean ignoreIPCheck)
			throws Exception {
		// TODO Auto-generated method stub
		getCurrentSSIDName();
		Process p;
		try {
			File bashFile = new File(System.getProperty("user.dir") + "/getIpAddress.sh");
			String[] cmd = { "/bin/sh", bashFile.getName() };

			p = Runtime.getRuntime().exec(cmd);

			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String s;
			while ((s = reader.readLine()) != null) {
				System.out.println("Current IP Address is : " + s);
				logStep("Current IP Address is : " + s);
				current_IPAddress = s;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("There is an exception while finding current MAC IP Address");
			logStep("There is an exception while finding current MAC IP Address");
			e.printStackTrace();
		}

		// System.out.println("Buid Name is : " + BuildName);
		if (!enableProxy) {
			System.out.println("Since enableProxy set to false, android Proxy setting to Off");
			logStep("Since enableProxy set to false, android Proxy setting to Off");
			launchAndroidSettingsApp();
			//enableDeviceProxy(properties.getProperty("wifiName"), enableProxy);
			Utils.settingProxyOff("None");
			//Utils.settingProxyEnable("Manual","192.168.0.133", "8111");
		} else {
			FileOutputStream fos = new FileOutputStream(
					System.getProperty("user.dir") + properties.getProperty("dataFilePath"));
			properties.setProperty("current_IPAddress", current_IPAddress);
			properties.store(fos, "Current MAC Address to update proxy settings in iPhone");
			fos.close();
			previous_IPAddress = properties.getProperty("previous_IPAddress");
			current_IPAddress = properties.getProperty("current_IPAddress");
			System.out.println("Previous IP Address Value :" + previous_IPAddress + "-----"
					+ "Current IP Address Value :" + current_IPAddress);
			logStep("Previous IP Address Value :" + previous_IPAddress + "-----" + "Current IP Address Value :"
					+ current_IPAddress);
			if (ignoreIPCheck) {
				System.out.println("Since IP Check is ignored, updating proxy settings of android");
				logStep("Since IP Check is ignored, updating proxy settings of android");
				try {
					launchAndroidSettingsApp();
					//enableDeviceProxy(current_IPAddress, properties.getProperty("wifiName"), enableProxy);
					Utils.settingProxyEnable("Manual",current_IPAddress, "8111");
				} catch (Exception e) {
					System.out.println("An exception while updating proxy details in android");
					logStep("An exception while updating proxy details in android");
				}
				try {
					System.out.println("Updating previous IP details in properties file");
					logStep("Updating previous IP details in properties file");
					Utils.set_PreviousIPAddress();
				} catch (Exception e) {
					System.out.println("An exception while updating previous IP details in properties file");
					logStep("An exception while updating previous IP details in properties file");
				}
			} else {
				if (current_IPAddress.equals(previous_IPAddress)) {
					System.out.println(
							"Previous and  Current IP Address are same no need to updte proxy settings of iPhone");
					logStep("Previous and  Current IP Address are same no need to updte proxy settings of iPhone");

				} else {
					System.out.println(
							"Previous and  Current IP Address are different, hence updating proxy settings of iPhone");
					logStep("Previous and  Current IP Address are different, hence updating proxy settings of iPhone");
					try {
						launchAndroidSettingsApp();
						Utils.settingProxyEnable("Manual",current_IPAddress, "8111");
					} catch (Exception e) {
						System.out.println("An exception while updating proxy details in android");
						logStep("An exception while updating proxy details in android");
					}
					try {
						System.out.println("Updating previous IP details in properties file");
						logStep("Updating previous IP details in properties file");
						Utils.set_PreviousIPAddress();
					} catch (Exception e) {
						System.out.println("An exception while updating previous IP details in properties file");
						logStep("An exception while updating previous IP details in properties file");
					}
				}
			}

		}

	}

	/**
	 * Write MAC IP Address to Properties file
	 * @throws Exception
	 */
	public static void set_PreviousIPAddress() throws Exception {
		FileOutputStream fos = new FileOutputStream(
				System.getProperty("user.dir") + properties.getProperty("dataFilePath"));

		properties.setProperty("previous_IPAddress", current_IPAddress);
		properties.store(fos, "Previous IP Address stotred");
		fos.close();
	}

	/**
	 * This method generates an xml file for the charles session file of extension
	 * .chlsx
	 */
	public static boolean createXMLFileForCharlesSessionFile() throws Exception {
		FileInputStream instream = null;
		FileOutputStream outstream = null;
		ReadExcelValues.excelValues("Smoke", "Paths");
		// String path = System.getProperty("user.dir") + "/CapturedSessionFile/";
		// Read the file name from the folder
		File folder = new File(System.getProperty("user.dir") + ReadExcelValues.data[4][Cap]);
		// File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		String fileName = null;
		for (File file : listOfFiles) {
			if (file.isFile()) {
				fileName = file.getName();
				System.out.println("File Name is : " + fileName);
				logStep("File Name is : " + fileName);
			}
		}
		try {

			File infile = new File(System.getProperty("user.dir") + ReadExcelValues.data[4][Cap] + fileName);

			outfile = new File(System.getProperty("user.dir") + "/myoutputFile.xml");

			instream = new FileInputStream(infile);
			outstream = new FileOutputStream(outfile);

			byte[] buffer = new byte[1024];

			int length;
			/*
			 * copying the contents from input stream to output stream using read and write
			 * methods
			 */
			while ((length = instream.read(buffer)) > 0) {
				outstream.write(buffer, 0, length);
			}

			// Closing the input/output file streams
			instream.close();
			outstream.close();

			System.out.println("Successfully Generated XML file from Charles session file!!");
			logStep("Successfully Generated XML file from Charles session file!!");
			return true;
		} catch (Exception e) {
			System.out.println("Failed to Generate XML file from Charles session file");
			logStep("Failed to Generate XML file from Charles session file");
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Verifies whether corresponding gampad call exists or not by reading expected
	 * value from sheet
	 * 
	 * @param excelName
	 * @param sheetName
	 * @throws Exception
	 */
	public static void verifyPubadCal(String excelName, String sheetName) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);

		// Read the content form file
		File fXmlFile = new File(outfile.getName());
		// File fXmlFile = outfile;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		// NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		// Getting custom_params amzn_b values
		// List<String> customParamsList = new ArrayList<String>();

		String iuId = null;
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		if (sheetName.equalsIgnoreCase("PreRollVideo")) {
			iuId = videoIUValue;
		} else if (sheetName.equalsIgnoreCase("IDD")) {
			String today = dailyDetailsDayOfWeek.concat("1");
			iuId = ReadExcelValues.data[18][Cap];
			iuId = iuId.concat("_") + today;
		} else {
			iuId = ReadExcelValues.data[18][Cap];
		}
		boolean iuExists = false;
		String queryIU = null;

		/*for (String qry : getQueryList) {
			if (qry.contains(iuId)) {

				iuExists = true;

				break;

			}
		}*/
		
		for (String qry : getQueryList) {
			if (qry.contains(iuId)) {
				if (sheetName.equalsIgnoreCase("Hourly")) {
					queryIU = return_iu_value_from_query_parameter_of_Feedcall(qry);
					if (queryIU.equalsIgnoreCase(iuId)) {
						iuExists = true;
						break;
					}
				} else {
					iuExists = true;
					break;
				}

			}
		}
		if (nextGenIMadDisplayed && sheetName.equalsIgnoreCase("Pulltorefresh")) {
			/*
			 * There may be chances that gampad call might not generated.. for ex: when IM
			 * ad displayed on home screen, then homescreen call doesn't generate
			 * 
			 */
			System.out.println("Since IM Ad displayed on App Launch, Homescreen ad call validation is skipped");
			logStep("Since IM Ad displayed on App Launch, Homescreen ad call validation is skipped");
		} else {
			if (iuExists) {
				System.out.println(iuId + " ad call is present");
				logStep(iuId + " ad call is present");
				System.out.println(iuId + " ad call validation is successful");
				logStep(iuId + " ad call validation is successful");
			} else {
				System.out.println(iuId + " ad call is not present");
				logStep(iuId + " ad call is not present");
				System.out.println(iuId + " ad call validation is failed");
				logStep(iuId + " ad call validation is failed");
				Assert.fail(iuId + " ad call is not present");

			}
		}

	}
	
	public static void verifyPubadCal(String excelName, String sheetName, boolean expected) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);

		// Read the content form file
		File fXmlFile = new File(outfile.getName());
		// File fXmlFile = outfile;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		// NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		// Getting custom_params amzn_b values
		// List<String> customParamsList = new ArrayList<String>();

		String iuId = null;
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		if (sheetName.equalsIgnoreCase("PreRollVideo")) {
			iuId = videoIUValue;
		} else if (sheetName.equalsIgnoreCase("IDD")) {
			String today = dailyDetailsDayOfWeek.concat("1");
			iuId = ReadExcelValues.data[18][Cap];
			iuId = iuId.concat("_") + today;
		} else {
			iuId = ReadExcelValues.data[18][Cap];
		}
		boolean iuExists = false;
		String queryIU = null;

		/*for (String qry : getQueryList) {
			if (qry.contains(iuId)) {

				iuExists = true;

				break;

			}
			
		}*/
		
		for (String qry : getQueryList) {
			if (qry.contains(iuId)) {
				if (sheetName.equalsIgnoreCase("Hourly")) {
					queryIU = return_iu_value_from_query_parameter_of_Feedcall(qry);
					if (queryIU.equalsIgnoreCase(iuId)) {
						iuExists = true;
						break;
					}
				} else {
					iuExists = true;
					break;
				}

			}
		}
		
		if (nextGenIMadDisplayed && sheetName.equalsIgnoreCase("Pulltorefresh")) {
			/*
			 * There may be chances that gampad call might not generated.. for ex: when IM
			 * ad displayed on home screen, then homescreen call doesn't generate
			 * 
			 */
			System.out.println("Since IM Ad displayed on App Launch, Homescreen ad call validation is skipped");
			logStep("Since IM Ad displayed on App Launch, Homescreen ad call validation is skipped");
		} else {
			if (expected == iuExists) {
				if (iuExists) {
					System.out.println(iuId + " ad call is present");
					logStep(iuId + " ad call is present");
					System.out.println(iuId + " ad call validation is successful");
					logStep(iuId + " ad call validation is successful");
				} else {
					System.out.println(iuId + " ad call is not present");
					logStep(iuId + " ad call is not present");
					System.out.println(iuId + " ad call validation is successful");
					logStep(iuId + " ad call validation is successful");
				}
				
			} else {
				System.out.println(iuId + " ad call validation is failed");
				logStep(iuId + " ad call validation is failed");
				if (expected) {
					System.out.println(iuId + " :ad Call expected to present but it not exists");
					logStep(iuId + " :ad Call expected to present but it not exists");
					Assert.fail(iuId + " :ad Call expected to present but it not exists");
				} else {
					System.out.println(iuId + " :ad Call is not expected to present but it exists");
					logStep(iuId + " :ad Call is not expected to present but it exists");
					Assert.fail(iuId + " :ad Call is not expected to present but it exists");
				}
			}
		}

	}

	/**
	 * Validates given iu value exists in Charles session file or not
	 * @param expiuId
	 * @throws Exception
	 */
	public static void verifyPubadCal(String expiuId) throws Exception {

		// Read the content form file
		File fXmlFile = new File(outfile.getName());
		// File fXmlFile = outfile;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		// NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		// Getting custom_params amzn_b values
		// List<String> customParamsList = new ArrayList<String>();

		// String iuId =
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		// String iuId = "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fcard%2Fdaily";
		String iuId = expiuId;
		boolean iuExists = false;

		for (String qry : getQueryList) {
			if (qry.contains(iuId)) {

				iuExists = true;

				break;

			}
		}
		if (iuExists) {
			System.out.println(iuId + " ad call is present");
			logStep(iuId + " ad call is present");
			System.out.println(iuId + " ad call validation is successful");
			logStep(iuId + " ad call validation is successful");
		} else {
			System.out.println(iuId + " ad call is not present");
			logStep(iuId + " ad call is not present");
			System.out.println(iuId + " ad call validation is failed");
			logStep(iuId + " ad call validation is failed");
			Assert.fail(iuId + " ad call is not present");

		}

	}
	
	/**
	 * Verifies whether IM Ad displayed on Homescreen and returns true/false
	 * @return
	 */
	public static boolean isNextGenIMAdDisplayed() {
		boolean flag = false;
		if (TestBase.isElementExists(By.xpath("//XCUIElementTypeOther[@name='nextgen-integrated-marquee-card-containerView']"))) {
			nextGenIMadDisplayed = true;
			System.out.println("****** NextGen IM Ad displayed on homescreen");
			logStep("****** NextGen IM Ad displayed on homescreen");
			flag = true;
		} else {
			nextGenIMadDisplayed = false;
			flag = false;
		}
		return flag;
	}
	
	/**
	 * Verifies whether IM Ad displayed on Homescreen and returns true/false
	 * @return
	 */
	public static boolean isNextGenIMAdDisplayedAndroid() {
		boolean flag = false;
		if (TestBase.isElementExists(By.xpath("//android.widget.FrameLayout[@resource-id='com.weather.Weather:id/card_integrated_ad_root']"))) {
			nextGenIMadDisplayed = true;
			System.out.println("****** NextGen IM Ad displayed on homescreen");
			logStep("****** NextGen IM Ad displayed on homescreen");
			flag = true;
		} else {
			nextGenIMadDisplayed = false;
			flag = false;
		}
		return flag;
	}

	/**
	 * Checks whether the interstitial ad call exists or not and return boolean value accordingly
	 * @param excelName
	 * @param sheetName
	 * @return
	 * @throws Exception
	 */
	public static boolean isInterStitialAdCalExists(String excelName, String sheetName) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		boolean interStitialAdcallPresent = false;
		// Read the content form file
		File fXmlFile = new File(outfile.getName());
		// File fXmlFile = outfile;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		// NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		// Getting custom_params amzn_b values
		// List<String> customParamsList = new ArrayList<String>();

		// String iuId =
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		// String iuId = "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fcard%2Fdaily";
		String iuId = ReadExcelValues.data[17][Cap];
		boolean iuExists = false;

		for (String qry : getQueryList) {
			if (qry.contains(iuId)) {

				iuExists = true;

				break;

			}
		}
		if (iuExists) {
			System.out.println(iuId + " ad call is present");
			logStep(iuId + " ad call is present");
			interStitialAdcallPresent = true;
		} else {
			System.out.println(iuId + " ad call is not present");
			logStep(iuId + " ad call is not present");
			// Assert.fail(iuId + " ad call is not present");
			interStitialAdcallPresent = false;
		}

		return interStitialAdcallPresent;
	}
	
	/**
	 * Verifies whether interstitial call has response
	 * @param excelName
	 * @param sheetName
	 * @return
	 * @throws Exception
	 */
	public static boolean isInterstitialCall_hasResponse(String excelName, String sheetName) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

// Getting custom_params amzn_b values
		List<String> customParamsList = new ArrayList<String>();

		String iuId = null;

		// String iuId =
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		iuId = ReadExcelValues.data[17][Cap];

		boolean iuExists = false;
		for (String qry : getQueryList) {
			if (qry.contains(iuId)) {
				iuExists = true;
				break;
			}
		}
		boolean flag = false;
		boolean resflag = false;
		if (iuExists) {
			System.out.println(iuId + " ad call is present");
			logStep(iuId + " ad call is present");
			outerloop: for (int p = 0; p < nodeList.getLength(); p++) {
				// System.out.println("Total transactions: "+nodeList.getLength());
				if (nodeList.item(p) instanceof Node) {
					Node node = nodeList.item(p);
					if (node.hasChildNodes()) {
						NodeList nl = node.getChildNodes();
						for (int j = 0; j < nl.getLength(); j++) {
							// System.out.println("node1 length is: "+nl.getLength());
							Node innernode = nl.item(j);
							if (innernode != null) {
								// System.out.println("Innernode name is: "+innernode.getNodeName());
								if (innernode.getNodeName().equals("request")) {
									if (innernode.hasChildNodes()) {
										NodeList n2 = innernode.getChildNodes();
										for (int k = 0; k < n2.getLength(); k++) {
											// System.out.println("node2 length is: "+n2.getLength());
											Node innernode2 = n2.item(k);
											if (innernode2 != null) {
												// System.out.println("Innernode2 name is: "+innernode2.getNodeName());
												if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
													Element eElement = (Element) innernode2;
													// System.out.println("Innernode2 element name is:
													// "+eElement.getNodeName());
													if (eElement.getNodeName().equals("headers")) {
														if (innernode2.hasChildNodes()) {
															NodeList n3 = innernode2.getChildNodes();
															for (int q = 0; q < n3.getLength(); q++) {
																// System.out.println("node3 length is:
																// "+n3.getLength());
																Node innernode3 = n3.item(q);
																if (innernode3 != null) {
																	// System.out.println("Innernode3 name is:
																	// "+innernode3.getNodeName());
																	if (innernode3.getNodeType() == Node.ELEMENT_NODE) {
																		Element eElement1 = (Element) innernode3;
																		// System.out.println("Innernode3 element name
																		// is: "+eElement1.getNodeName());
																		if (eElement1.getNodeName().equals("header") || eElement1.getNodeName().equals("first-line")) {
																			String content = eElement1.getTextContent();
																			// System.out.println("request
																			// body"+content);

																			if (content.contains(iuId)) {
																				flag = true;
																				// System.out.println("request
																				// body"+content);
																				// istofRequestBodies.add(content);
																				// System.out.println("request body
																				// found "+content);
																				// break;
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}

								if (flag) {
									// System.out.println("Exiting after found true ");
									// System.out.println("checking innernode name is: "+innernode.getNodeName());
									if (innernode.getNodeName().equals("response")) {
										// System.out.println(innernode.getNodeName());
										if (innernode.hasChildNodes()) {
											NodeList n2 = innernode.getChildNodes();
											for (int k = 0; k < n2.getLength(); k++) {
												Node innernode2 = n2.item(k);
												if (innernode2 != null) {
													if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
														Element eElement = (Element) innernode2;
														if (eElement.getNodeName().equals("body")) {
															String content = eElement.getTextContent();
															// System.out.println("response body "+content);
															if (content.contains(ReadExcelValues.data[13][Cap])) {
																resflag = true;
																break outerloop;

															}
														}
													}
												}
											}
										}
									}

								}
								// break;
							}
						}
					}
				}
				flag = false;
			}

		} else {
			System.out.println(iuId + " ad call is not present");
			logStep(iuId + " ad call is not present");

		}

		// return flag;

		// Get Pubad call from

		if (resflag) {
			System.out.println("Interstitial call has response, hence Interstitial Ad to be displayed");
			logStep("Interstitial call has response, hence Interstitial Ad to be displayed");
			return resflag;
		} else {
			System.out.println("Interstitial call doesnt have response, hence Interstitial Ad not to be displayed");
			logStep("Interstitial call doesnt have response, hence Interstitial Ad not to be displayed");
			return resflag;
		}

	}
	
	/**
	 * This method mainly used before clearing charles session to check  for  interstitial call.
	 * @param excelName
	 * @param sheetName
	 * @throws Exception 
	 */
	public static void verifytinterstitialAdcallBeforeClearSession(String excelName, String sheetName) throws Exception {
		CharlesProxy.proxy.stopRecording();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		CharlesProxy.proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		if (isInterStitialAdCalExists(excelName, sheetName) && isInterstitialCall_hasResponse(excelName, sheetName)) {
			interStitialAdcallSuccessful = true;
		}
		CharlesProxy.proxy.startRecording();
	}

	/**
	 * Verifies amazon slot id of given card name(Sheet name) present in Charles session file or not
	 * @param excelName
	 * @param sheetName
	 * @throws Exception
	 */
	public static void verifyAAX_SlotId(String excelName, String sheetName) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);

		// Read the content form file
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");

		// Read JSONs and get b value
		// List<String> jsonBValuesList = new ArrayList<String>();

		// String slotId = "153f5936-781f-4586-8fdb-040ce298944a";

		// String slotId = "c4dd8ec4-e40c-4a63-ae81-8f756793ac5e";
		String slotId = ReadExcelValues.data[21][Cap];

		boolean flag = false;
		// List<String> istofRequestBodies = new ArrayList<String>();
		// List<String> istofResponseBodies = new ArrayList<String>();
		// List<String> listOf_b_Params = new ArrayList<String>();

		nodeList: for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i) instanceof Node) {
				Node node = nodeList.item(i);
				if (node.hasChildNodes()) {
					NodeList nl = node.getChildNodes();
					NodeList: for (int j = 0; j < nl.getLength(); j++) {
						Node innernode = nl.item(j);
						if (innernode != null) {
							if (innernode.getNodeName().equals("request")) {
								if (innernode.hasChildNodes()) {
									NodeList n2 = innernode.getChildNodes();
									for (int k = 0; k < n2.getLength(); k++) {
										Node innernode2 = n2.item(k);
										if (innernode2 != null) {
											if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
												Element eElement = (Element) innernode2;
												if (eElement.getNodeName().equals("body")) {
													String content = eElement.getTextContent();
													if (content.contains(slotId)) {
														flag = true;
														// istofRequestBodies.add(content);

														break nodeList;

														// System.out.println("request body "+content);
													}
												}
											}
										}
									}

								}
							}

						}
					}

				}
			}

		}
		if (flag) {
			System.out.println("slot id: " + slotId + " is present");
			logStep("slot id: " + slotId + " is present");
			System.out.println(sheetName + " : AAX slot id Verification is successful");
			logStep(sheetName + " : AAX slot id Verification is successful");

		} else {
			System.out.println("slot id: " + slotId + " is not present");
			logStep("slot id: " + slotId + " is not present");
			System.out.println(sheetName + " :AAX slot id Verification is failed");
			logStep(sheetName + " :AAX slot id Verification is failed");
			Assert.fail("slot id: " + slotId + " is not present");
		}

	}

	/**
	 * Verifies amazon slot id of given card name(Sheet name) present in Charles session file or not and returns boolean value as per expected value
	 * @param excelName
	 * @param sheetName
	 * @param expected
	 * @throws Exception
	 */
	public static void verifyAAX_SlotId(String excelName, String sheetName, boolean expected) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);

		// Read the content form file
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");

		// Read JSONs and get b value
		// List<String> jsonBValuesList = new ArrayList<String>();

		// String slotId = "153f5936-781f-4586-8fdb-040ce298944a";

		// String slotId = "c4dd8ec4-e40c-4a63-ae81-8f756793ac5e";
		String slotId = ReadExcelValues.data[21][Cap];

		boolean flag = false;
		// List<String> istofRequestBodies = new ArrayList<String>();
		// List<String> istofResponseBodies = new ArrayList<String>();
		// List<String> listOf_b_Params = new ArrayList<String>();

		nodeList: for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i) instanceof Node) {
				Node node = nodeList.item(i);
				if (node.hasChildNodes()) {
					NodeList nl = node.getChildNodes();
					NodeList: for (int j = 0; j < nl.getLength(); j++) {
						Node innernode = nl.item(j);
						if (innernode != null) {
							if (innernode.getNodeName().equals("request")) {
								if (innernode.hasChildNodes()) {
									NodeList n2 = innernode.getChildNodes();
									for (int k = 0; k < n2.getLength(); k++) {
										Node innernode2 = n2.item(k);
										if (innernode2 != null) {
											if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
												Element eElement = (Element) innernode2;
												if (eElement.getNodeName().equals("body")) {
													String content = eElement.getTextContent();
													if (content.contains(slotId)) {
														flag = true;
														// istofRequestBodies.add(content);

														break nodeList;

														// System.out.println("request body "+content);
													}
												}
											}
										}
									}

								}
							}

						}
					}

				}
			}

		}
		if (flag) {
			System.out.println("slot id: " + slotId + " is present");
			logStep("slot id: " + slotId + " is present");

		} else {
			System.out.println("slot id: " + slotId + " is not present");
			logStep("slot id: " + slotId + " is not present");
			// Assert.fail("slot id: " + slotId + " is not present");
		}
		/*
		 * if (flag != expected) { System.out.println(sheetName +
		 * " :AAX slot id Verification is failed"); logStep(sheetName +
		 * " :AAX slot id Verification is failed"); Assert.fail(sheetName +
		 * " :AAX slot id Verification is failed"); }
		 */

		if (expected == flag) {
			System.out.println(sheetName + " : AAX slot id Verification is successful");
			logStep(sheetName + " : AAX slot id Verification is successful");

		} else {
			System.out.println(sheetName + " :AAX slot id Verification is failed");
			logStep(sheetName + " :AAX slot id Verification is failed");

			if (expected) {
				System.out.println(sheetName + " :AAX slot id expected to present but it not exists");
				logStep(sheetName + " :AAX slot id expected to present but it not exists");
				Assert.fail(sheetName + " :AAX slot id expected to present but it not exists");
			} else {
				System.out.println(sheetName + " :AAX slot id is not expected to present but it exists");
				logStep(sheetName + " :AAX slot id is not expected to present but it exists");
				Assert.fail(sheetName + " :AAX slot id is not expected to present but it exists");
			}
		}

	}

	/**
	 * Evaluate Charles session
	 * @param document
	 * @param xpathExpression
	 * @return
	 * @throws Exception
	 */
	public static List<String> evaluateXPath(Document document, String xpathExpression) throws Exception {
		// Create XPathFactory object
		XPathFactory xpathFactory = XPathFactory.newInstance();
		// Create XPath object
		XPath xpath = xpathFactory.newXPath();
		List<String> values = new ArrayList<String>();
		try {
			// Create XPathExpression object
			XPathExpression expr = xpath.compile(xpathExpression);
			NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				values.add(nodes.item(i).getNodeValue());
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return values;
	}

	/**
	 * Returns Custom Parameter value of given gampad call
	 * @param qryValue
	 * @param cust_param
	 * @return
	 */
	private static String getCustomParamBy_iu_value(String qryValue, String cust_param) {
		List<String> listOfUisQrys = new ArrayList<String>();
		String cust_params = "";
		String[] key = null;
		// if (qryValue != null && qryValue.contains("cust_params")) {
		if (qryValue != null && qryValue.contains("cust_params")) {
			cust_params = qryValue.substring(qryValue.indexOf("cust_params"));
			cust_params = cust_params.replace("%26", "&");
			cust_params = cust_params.replace("%2C", ",");
			cust_params = cust_params.replace("%3D", "=");
		}
		if (cust_params.indexOf("&" + cust_param + "=") > 0 || cust_params.indexOf("=" + cust_param + "=") > 0) {
			try {
				cust_params = cust_params.substring(cust_params.indexOf("&" + cust_param + "="));
				cust_params = cust_params.substring(cust_params.indexOf(cust_param));
			} catch (Exception e) {
				cust_params = cust_params.substring(cust_params.indexOf("=" + cust_param + "="));
				cust_params = cust_params.substring(cust_params.indexOf(cust_param));
			}
			// cust_params = cust_params.substring(cust_params.indexOf(cust_param));
			String b[] = cust_params.split("&");
			cust_params = b[0];
			key = cust_params.split("=");
			cust_params = key[1];
		} else {
			cust_params = "";
		}
		if (cust_param.equalsIgnoreCase("ct")) {
			cust_params = cust_params.replaceAll("_", " ");
		}

		return cust_params;
	}
	
	/**
	 * Returns query param value
	 * @param qryValue
	 * @param queryParam
	 * @return
	 */
	private static String getQueryParamValue(String qryValue, String queryParam) {
		
		String cust_params = "";
		String[] key = null;
		// if (qryValue != null && qryValue.contains("cust_params")) {
		if (qryValue != null && qryValue.contains(queryParam)) {
			cust_params = qryValue.substring(qryValue.indexOf(queryParam));
		}
		if (cust_params.indexOf(queryParam+ "=" ) == 0 || cust_params.indexOf("&" + queryParam + "=") > 0 || cust_params.indexOf("=" + queryParam + "=") > 0) {
			
			try {
				cust_params = cust_params.substring(cust_params.indexOf("&" + queryParam + "="));
				cust_params = cust_params.substring(cust_params.indexOf(queryParam));
			} catch (Exception e) {
				try {
					cust_params = cust_params.substring(cust_params.indexOf("=" + queryParam + "="));
					cust_params = cust_params.substring(cust_params.indexOf(queryParam));
				} catch (Exception e1) {
					cust_params = cust_params.substring(cust_params.indexOf(queryParam + "="));
				}
			}
			String b[] = cust_params.split("&");
			cust_params = b[0];
			key = cust_params.split("=");
			cust_params = key[1];
		} else {
			cust_params = "";
		}
		
		return cust_params;
	}

	/**
	 * Returns Parameter (which is not a custom parameter) of given gampad call
	 * @param qryValue
	 * @param cust_param
	 * @return
	 */
	private static String getNonCustomParamBy_iu_value(String qryValue, String cust_param) {
		List<String> listOfUisQrys = new ArrayList<String>();
		String cust_params = "";
		String[] key = null;
		// if (qryValue != null && qryValue.contains("cust_params")) {
		if (qryValue != null && qryValue.contains(cust_param)) {
			cust_params = qryValue.substring(qryValue.indexOf(cust_param));
			cust_params = cust_params.replace("%26", "&");
			cust_params = cust_params.replace("%2C", ",");
			cust_params = cust_params.replace("%3D", "=");
			cust_params = cust_params.replace("%2F", "/");
			cust_params = cust_params.replace("%3A", ":");
			cust_params = cust_params.replace("%3F", "?");
		}
		if (cust_params.indexOf(cust_param) >= 0) {
			try {
				cust_params = cust_params.substring(cust_params.indexOf(cust_param + "="));
				cust_params = cust_params.substring(cust_params.indexOf(cust_param));
			} catch (Exception e) {
				cust_params = cust_params.substring(cust_params.indexOf("&" + cust_param + "="));
				cust_params = cust_params.substring(cust_params.indexOf(cust_param));
			}
			// cust_params = cust_params.substring(cust_params.indexOf(cust_param));
			String b[] = cust_params.split("&");
			cust_params = b[0];
			key = cust_params.split("=");
			cust_params = key[1];
		} else {
			cust_params = "";
		}
		return cust_params;
	}

	/**
	 * Returns b param value from aax call response
	 * @param qryValue
	 * @return
	 */
	private static String get_b_value_inJsonResponseBody(String qryValue) {
		String b_paramValue = "";
		JSONParser parser = new JSONParser();
		try {
			JSONObject json = (JSONObject) parser.parse(qryValue);
			Object obj = checkKey(json, "b");
			if (obj != null) {
				b_paramValue = obj.toString();
			} else {
				// inorder to not to disturb the existing method structure, when there is no
				// bidding happens i.e. response contains error, returning error explicitly
				b_paramValue = "error";
			}
		} catch (ParseException e) {
			// inorder to not to disturb the existing method structure, when there is no
			// bidding happens i.e. response contains error, returning error explicitly
			b_paramValue = "error";
			e.printStackTrace();
		}

		return b_paramValue;
	}

	/**
	 * Get Parameter value from Json Body
	 * @param qryValue
	 * @param param
	 * @return
	 */
	private static String get_Param_Value_inJsonBody(String qryValue, String param) {
		String b_paramValue = "";
		JSONParser parser = new JSONParser();
		try {
			JSONObject json = (JSONObject) parser.parse(qryValue);
			Object obj = checkKey(json, param);
			if (obj != null) {
				b_paramValue = obj.toString();
			} else {
				// inorder to not to disturb the existing method structure, when there is no
				// bidding happens i.e. response contains error, returning error explicitly
				b_paramValue = "error";
			}
		} catch (ParseException e) {
			// inorder to not to disturb the existing method structure, when there is no
			// bidding happens i.e. response contains error, returning error explicitly
			b_paramValue = "error";
			e.printStackTrace();
		}

		return b_paramValue;
	}

	/**
	 * To get the value for "b". Here key is -> 'b'
	 * 
	 * @param object
	 * @param searchedKey
	 * @return
	 */
	public static Object checkKey(JSONObject object, String searchedKey) {
		boolean exists = object.containsKey(searchedKey);
		Object obj = null;
		if (exists) {
			obj = object.get(searchedKey);
		}
		if (!exists) {
			Set<String> keys = object.keySet();
			for (String key : keys) {
				if (object.get(key) instanceof JSONObject) {
					obj = checkKey((JSONObject) object.get(key), searchedKey);
				}
			}
		}
		return obj;
	}

	/**
	 * Verifies whether given api call presents in charles session or not
	 * @param excelName
	 * @param sheetName
	 * @throws Exception
	 */
	public static void verifyAPICal(String excelName, String sheetName) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);

		// Read the content form file
		File fXmlFile = new File(outfile.getName());
		// File fXmlFile = outfile;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		// NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@host";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		// Getting custom_params amzn_b values
		// List<String> customParamsList = new ArrayList<String>();

		// String iuId =
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		// String iuId = "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fcard%2Fdaily";
		String adURL = ReadExcelValues.data[2][Cap];
		boolean iuExists = false;

		for (String qry : getQueryList) {
			if (qry.contains(adURL)) {

				iuExists = true;

				break;

			}
		}
		if (iuExists) {
			System.out.println(adURL + " ad call is present");
			logStep(adURL + " ad call is present");
			System.out.println(sheetName + " :API Call Verification is successful");
			logStep(sheetName + " :API Call Verification is successful");
		} else {
			System.out.println(adURL + " ad call is not present");
			logStep(adURL + " ad call is not present");
			System.out.println(sheetName + " :API Call Verification is failed");
			logStep(sheetName + " :API Call Verification is failed");
			Assert.fail(adURL + " ad call is not present");

		}

	}

	/**
	 * Verifies api call in charles session file and returns boolean accordingly whether it is expected or not
	 * @param excelName
	 * @param sheetName
	 * @param expected
	 * @throws Exception
	 */
	public static void verifyAPICal(String excelName, String sheetName, boolean expected) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);

		// Read the content form file
		File fXmlFile = new File(outfile.getName());
		// File fXmlFile = outfile;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		// NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@host";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		// Getting custom_params amzn_b values
		// List<String> customParamsList = new ArrayList<String>();

		// String iuId =
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		// String iuId = "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fcard%2Fdaily";
		String adURL = ReadExcelValues.data[2][Cap];
		boolean iuExists = false;

		for (String qry : getQueryList) {
			if (qry.contains(adURL)) {

				iuExists = true;

				break;

			}
		}
		if (iuExists) {
			System.out.println(adURL + " ad call is present");
			logStep(adURL + " ad call is present");
		} else {
			System.out.println(adURL + " ad call is not present");
			logStep(adURL + " ad call is not present");

		}

		if (expected == iuExists) {
			System.out.println(sheetName + " :API Call Verification is successful");
			logStep(sheetName + " :API Call Verification is successful");

		} else {
			System.out.println(sheetName + " :API Call Verification is failed");
			logStep(sheetName + " :API Call Verification is failed");
			if (expected) {
				System.out.println(sheetName + " :API Call expected to present but it not exists");
				logStep(sheetName + " :API Call expected to present but it not exists");
				Assert.fail(sheetName + " :API Call expected to present but it not exists");
			} else {
				System.out.println(sheetName + " :API Call is not expected to present but it exists");
				logStep(sheetName + " :API Call is not expected to present but it exists");
				Assert.fail(sheetName + " :API Call is not expected to present but it exists");
			}
		}

	}

	/**
	 * Verifies Background asset call by response
	 * @param excelName
	 * @param sheetName
	 * @param adType
	 * @throws Exception
	 */
	public static void verifyBGAd_byCallResponse(String excelName, String sheetName, String adType) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String bgAssetCall = null;

		if (adType.equalsIgnoreCase("Static")) {
			String staticbgAssetCall = ReadExcelValues.data[8][Cap];
			bgAssetCall = staticbgAssetCall;
			/*
			 * if (sheetName.equalsIgnoreCase("IntegratedFeedCard")) { // as Integrated Feed
			 * Card Static BG Asset call is dynamic based on cnd and // dynght parameters,
			 * hence retrieving the parameters and framing asset call String cndValue =
			 * get_custom_param_val_of_gampad(excelName, sheetName, "cnd"); String
			 * dynghtValue = get_custom_param_val_of_gampad(excelName, sheetName, "dynght");
			 * bgAssetCall = staticbgAssetCall.concat("-").concat(cndValue).concat("-")
			 * .concat(dynghtValue.toLowerCase()).concat(".jpg"); }
			 */

		} else if (adType.equalsIgnoreCase("Video")) {
			String videobgAssetCall = ReadExcelValues.data[10][Cap];
			bgAssetCall = videobgAssetCall;

		}

		// Read the content form file
		File fXmlFile = new File(outfile.getName());
		// File fXmlFile = outfile;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		// NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@path";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		// Getting custom_params amzn_b values
		// List<String> customParamsList = new ArrayList<String>();

		// String iuId =
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		// String iuId = "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fcard%2Fdaily";
		// String adURL = readExcelValues.data[2][Cap];
		boolean iuExists = false;

		for (String qry : getQueryList) {
			if (qry.contains(bgAssetCall)) {

				iuExists = true;

				break;

			}
		}
		if (iuExists) {
			System.out.println("Charles response contains " + sheetName + " " + adType + " Asset Call: " + bgAssetCall);
			logStep("Charles response contains " + sheetName + " " + adType + " Asset Call: " + bgAssetCall);
			System.out.println(sheetName + " " + adType + " Asset Call: " + bgAssetCall + " validation is successful");
			logStep(sheetName + " " + adType + " Asset Call: " + bgAssetCall + " validation is successful");
		} else {
			System.out.println(
					"Charles response doesn't contains " + sheetName + " " + adType + " Asset Call: " + bgAssetCall);
			logStep("Charles response doesn't contains " + sheetName + " " + adType + " Asset Call: " + bgAssetCall);
			System.out.println(sheetName + " " + adType + " Asset Call: " + bgAssetCall + " validation is failed");
			logStep(sheetName + " " + adType + " Asset Call: " + bgAssetCall + " validation is failed");
			Assert.fail(
					"Charles response doesn't contains " + sheetName + " " + adType + " Asset Call: " + bgAssetCall);
		}

	}

	/**
	 * Verifies FG asset call by response
	 * @param excelName
	 * @param sheetName
	 * @param adType
	 * @throws Exception
	 */
	public static void verifyFGAd_byCallResponse(String excelName, String sheetName, String adType) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String fgAssetCall = null;

		if (adType.equalsIgnoreCase("Static")) {
			String staticfgAssetCall = ReadExcelValues.data[9][Cap];
			fgAssetCall = staticfgAssetCall;

		} else if (adType.equalsIgnoreCase("Video")) {
			String videofgAssetCall = ReadExcelValues.data[11][Cap];
			fgAssetCall = videofgAssetCall;

		}

		// Read the content form file
		File fXmlFile = new File(outfile.getName());
		// File fXmlFile = outfile;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		// NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@path";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		// Getting custom_params amzn_b values
		// List<String> customParamsList = new ArrayList<String>();

		// String iuId =
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		// String iuId = "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fcard%2Fdaily";
		// String adURL = readExcelValues.data[2][Cap];
		boolean iuExists = false;

		for (String qry : getQueryList) {
			if (qry.contains(fgAssetCall)) {

				iuExists = true;

				break;

			}
		}
		if (iuExists) {
			System.out.println("Charles response contains " + sheetName + " " + adType + " Asset Call: " + fgAssetCall);
			logStep("Charles response contains " + sheetName + " " + adType + " Asset Call: " + fgAssetCall);
			System.out.println(sheetName + " " + adType + " Asset Call: " + fgAssetCall + " validation is successful");
			logStep(sheetName + " " + adType + " Asset Call: " + fgAssetCall + " validation is successful");
		} else {
			System.out.println(
					"Charles response doesn't contains " + sheetName + " " + adType + " Asset Call" + fgAssetCall);
			logStep("Charles response doesn't contains " + sheetName + " " + adType + " Asset Call: " + fgAssetCall);
			System.out.println(sheetName + " " + adType + " Asset Call: " + fgAssetCall + " validation is failed");
			logStep(sheetName + " " + adType + " Asset Call: " + fgAssetCall + " validation is failed");
			Assert.fail(
					"Charles response doesn't contains " + sheetName + " " + adType + " Asset Call: " + fgAssetCall);
		}

	}

	/**
	 * Verifies whether IM or IF call has response
	 * @param excelName
	 * @param sheetName
	 * @return
	 * @throws Exception
	 */
	public static boolean isNextGenIMorIFCall_hasResponse(String excelName, String sheetName) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

// Getting custom_params amzn_b values
		List<String> customParamsList = new ArrayList<String>();

		String iuId = null;

		// String iuId =
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		if (sheetName.equalsIgnoreCase("IntegratedForecast")) {
			if (Ad instanceof IOSDriver<?>) {
				iuId = "iu=%2F7646%2Ftest_app_iphone_us%2Fdb_display%2Fcard%2Fdaily";
			} else if (Ad instanceof AndroidDriver<?>){
				iuId = "iu=%2F7646%2Ftest_app_android_us%2Fdb_display%2Fcard%2Fdaily";
			}
		} else if (sheetName.equalsIgnoreCase("NextGenIM")) {
			if (Ad instanceof IOSDriver<?>) {
				iuId = "iu=%2F7646%2Ftest_app_iphone_us%2Fdb_display%2Fhome_screen%2Fmarquee";
			} else if (Ad instanceof AndroidDriver<?>){
				iuId = "iu=%2F7646%2Ftest_app_android_us%2Fdb_display%2Fhome_screen%2Fmarquee";
			}
			
		} else {
			iuId = ReadExcelValues.data[18][Cap];
		}

		boolean iuExists = false;
		for (String qry : getQueryList) {
			if (qry.contains(iuId)) {
				iuExists = true;
				break;
			}
		}
		boolean flag = false;
		boolean resflag = false;
		if (iuExists) {
			System.out.println(iuId + " ad call is present");
			logStep(iuId + " ad call is present");
			outerloop: for (int p = 0; p < nodeList.getLength(); p++) {
				// System.out.println("Total transactions: "+nodeList.getLength());
				if (nodeList.item(p) instanceof Node) {
					Node node = nodeList.item(p);
					if (node.hasChildNodes()) {
						NodeList nl = node.getChildNodes();
						for (int j = 0; j < nl.getLength(); j++) {
							// System.out.println("node1 length is: "+nl.getLength());
							Node innernode = nl.item(j);
							if (innernode != null) {
								// System.out.println("Innernode name is: "+innernode.getNodeName());
								if (innernode.getNodeName().equals("request")) {
									if (innernode.hasChildNodes()) {
										NodeList n2 = innernode.getChildNodes();
										for (int k = 0; k < n2.getLength(); k++) {
											// System.out.println("node2 length is: "+n2.getLength());
											Node innernode2 = n2.item(k);
											if (innernode2 != null) {
												// System.out.println("Innernode2 name is: "+innernode2.getNodeName());
												if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
													Element eElement = (Element) innernode2;
													// System.out.println("Innernode2 element name is:
													// "+eElement.getNodeName());
													if (eElement.getNodeName().equals("headers")) {
														if (innernode2.hasChildNodes()) {
															NodeList n3 = innernode2.getChildNodes();
															for (int q = 0; q < n3.getLength(); q++) {
																// System.out.println("node3 length is:
																// "+n3.getLength());
																Node innernode3 = n3.item(q);
																if (innernode3 != null) {
																	// System.out.println("Innernode3 name is:
																	// "+innernode3.getNodeName());
																	if (innernode3.getNodeType() == Node.ELEMENT_NODE) {
																		Element eElement1 = (Element) innernode3;
																		// System.out.println("Innernode3 element name
																		// is: "+eElement1.getNodeName());
																		if (eElement1.getNodeName().equals("header") || eElement1.getNodeName().equals("first-line")) {
																			String content = eElement1.getTextContent();
																			// System.out.println("request body
																			// "+content);

																			if (content.contains(iuId)) {
																				flag = true;
																				// System.out.println("request body
																				// "+content);
																				// istofRequestBodies.add(content);
																				// System.out.println("request body
																				// found "+content);
																				// break;
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}

								if (flag) {
									// System.out.println("Exiting after found true ");
									// System.out.println("checking innernode name is: "+innernode.getNodeName());
									if (innernode.getNodeName().equals("response")) {
										// System.out.println(innernode.getNodeName());
										if (innernode.hasChildNodes()) {
											NodeList n2 = innernode.getChildNodes();
											for (int k = 0; k < n2.getLength(); k++) {
												Node innernode2 = n2.item(k);
												if (innernode2 != null) {
													if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
														Element eElement = (Element) innernode2;
														if (eElement.getNodeName().equals("body")) {
															String content = eElement.getTextContent();
															// System.out.println("response body "+content);
															if (content.contains(ReadExcelValues.data[13][Cap])) {
																resflag = true;
																break outerloop;

															}
														}
													}
												}
											}
										}
									}

								}
								// break;
							}
						}
					}
				}
				flag = false;
			}

		} else {
			System.out.println(iuId + " ad call is not present");
			logStep(iuId + " ad call is not present");

		}

		// return flag;

		// Get Pubad call from

		if (resflag) {
			System.out.println(sheetName + " call has response, hence " + sheetName + " Ad to be displayed");
			logStep(sheetName + " call has response, hence " + sheetName + " Ad to be displayed");
			return resflag;
		} else {
			System.out
					.println(sheetName + " call doesnt have response, hence " + sheetName + " Ad not to be displayed");
			logStep(sheetName + " call doesnt have response, hence " + sheetName + " Ad not to be displayed");
			return resflag;
		}

	}

	/**
	 * This method verifies the corresponding gampad call ad size by reading
	 * expected value from the sheet
	 * 
	 * @param excelName
	 * @param sheetName
	 * @throws Exception
	 */
	public static void verify_Ad_Size(String excelName, String sheetName) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String expSize = ReadExcelValues.data[12][Cap];

		ReadExcelValues.excelValues(excelName, sheetName);

		// Read the content form file
		File fXmlFile = new File(outfile.getName());
		// File fXmlFile = outfile;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		// NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		// Getting custom_params amzn_b values
		// List<String> customParamsList = new ArrayList<String>();

		String iuId = null;
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		// String iuId = "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fcard%2Fdaily";
		// String iuId = readExcelValues.data[18][Cap];

		if (sheetName.contains("NextGenIM")) {
			if (Ad instanceof IOSDriver<?>) {
				iuId = "iu=%2F7646%2Ftest_app_iphone_us%2Fdb_display%2Fhome_screen%2Fmarquee";
			} else if (Ad instanceof AndroidDriver<?>){
				iuId = "iu=%2F7646%2Ftest_app_android_us%2Fdb_display%2Fhome_screen%2Fmarquee";
			}
		} else if (sheetName.equalsIgnoreCase("IDD")) {
			String today = dailyDetailsDayOfWeek.concat("1");
			iuId = ReadExcelValues.data[18][Cap];
			iuId = iuId.concat("_") + today;
		} else {
			iuId = ReadExcelValues.data[18][Cap];
		}

		boolean iuExists = false;
		boolean sizeExists = false;
		String actualSize = null;

		for (String qry : getQueryList) {
			if (qry.contains(iuId)) {

				iuExists = true;
				String[] qrylist = qry.split("&");

				for (int i = 0; i < qrylist.length; i++) {
					if (qrylist[i].contains("sz=")) {
						actualSize = qrylist[i];
						break;
					}
				}

				/*
				 * if (qry.contains(expSize)) {
				 * 
				 * sizeExists = true;
				 * 
				 * break; break;
				 * 
				 * }
				 */
				if (expSize.contains(actualSize)) {
					sizeExists = true;
				}
				break;

			} else {
				if (sheetName.contains("Feed3")) {
					String iuId1 = "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Ffeed%2Fhpvar_3";
					if (qry.contains(iuId1)) {

						iuExists = true;
						String[] qrylist = qry.split("&");

						for (int i = 0; i < qrylist.length; i++) {
							if (qrylist[i].contains("sz=")) {
								actualSize = qrylist[i];
								break;
							}
						}

						if (expSize.contains(actualSize)) {
							sizeExists = true;
						}
						iuId = iuId1;
						break;

					}
				}
			}
		}

		/*
		 * if (iuExists) { System.out.println(iuId + " ad call is present");
		 * logStep(iuId + " ad call is present"); } else { System.out.println(iuId +
		 * " ad call is not present"); logStep(iuId + " ad call is not present");
		 * Assert.fail(iuId + " ad call is not present");
		 * 
		 * }
		 * 
		 */

		/*
		 * boolean sizeExists = false;
		 * 
		 * for (String qry : getQueryList) {
		 * 
		 * if (qry.contains(size)) {
		 * 
		 * sizeExists = true;
		 * 
		 * break;
		 * 
		 * } }
		 */

		/*
		 * if (sizeExists) { System.out.println("IM/IF Ad Size in Charles Request is: "
		 * + size.replaceAll("%7C", "|"));
		 * logStep("IM/IF Ad Size in Charles Request is: " + size.replaceAll("%7C",
		 * "|")); } else {
		 * System.out.println("Charles Request doesn't not contains IM/IF Ad Size : " +
		 * size.replaceAll("%7C", "|"));
		 * logStep("Charles Request doesn't not contains IM/IF Ad Size : " +
		 * size.replaceAll("%7C", "|"));
		 * Assert.fail("Charles Request doesn't not contains IM/IF Ad Size : " +
		 * size.replaceAll("%7C", "|"));
		 * 
		 * }
		 */

		if (iuExists && sizeExists) {
			System.out.println(sheetName + " Ad Size in Charles Request is: " + actualSize.replaceAll("%7C", "|")
					+ " is matched with expected Size: " + expSize.replaceAll("%7C", "|"));
			logStep(sheetName + " Ad Size in Charles Request is: " + actualSize.replaceAll("%7C", "|")
					+ " is matched with expected Size: " + expSize.replaceAll("%7C", "|"));
			System.out.println(sheetName + " ad size validation is successful");
			logStep(sheetName + " ad size validation is successful");
		} else {
			System.out.println(sheetName + " ad size validation is failed");
			logStep(sheetName + " ad size validation is failed");
			if (!iuExists) {
				System.out.println(iuId + " ad call is not present");
				logStep(iuId + " ad call is not present");
				Assert.fail(iuId + " ad call is not present");

			} else {
				System.out.println(iuId + " ad call is present & Size of ad: " + actualSize
						+ " is not matched with expected Size: " + expSize.replaceAll("%7C", "|"));
				logStep(iuId + " ad call is present & Size of ad: " + actualSize
						+ " is not matched with expected Size: " + expSize.replaceAll("%7C", "|"));
				Assert.fail(iuId + " ad call is present & Size of ad: " + actualSize
						+ " is not matched with expected Size: " + expSize.replaceAll("%7C", "|"));
			}

		}

	}

	/**
	 * Verifies whether IDD Call has response
	 * @param excelName
	 * @param sheetName
	 * @return
	 * @throws Exception
	 */
	public static boolean isIDDCall_hasResponse(String excelName, String sheetName) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		File fXmlFile = new File(outfile.getName());
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

// Getting custom_params amzn_b values
		List<String> customParamsList = new ArrayList<String>();

		String iuId = null;

		String sdir = "dir: 'https://s.w-x.co/cl/'";
		String sdirClient = "dirClient: 'wxcl/'";
		String sdirFolder = "dirFolder: 'prototype/idd/'";
		String simgID = "imgID: 'example-static/500x600-bg-guides-green.jpg'";

		// String iuId =
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		if (sheetName.equalsIgnoreCase("IntegratedForecast")) {
			if (Ad instanceof IOSDriver<?>) {
				iuId = "iu=%2F7646%2Ftest_app_iphone_us%2Fdb_display%2Fcard%2Fdaily";
			} else if (Ad instanceof AndroidDriver<?>){
				iuId = "iu=%2F7646%2Ftest_app_android_us%2Fdb_display%2Fcard%2Fdaily";
			}
		} else if (sheetName.equalsIgnoreCase("NextGenIM")) {
			if (Ad instanceof IOSDriver<?>) {
				iuId = "iu=%2F7646%2Ftest_app_iphone_us%2Fdb_display%2Fhome_screen%2Fmarquee";
			} else if (Ad instanceof AndroidDriver<?>){
				iuId = "iu=%2F7646%2Ftest_app_android_us%2Fdb_display%2Fhome_screen%2Fmarquee";
			}
		} else if (sheetName.equalsIgnoreCase("IDD")) {
			String today = dailyDetailsDayOfWeek.concat("1");
			iuId = ReadExcelValues.data[18][Cap];
			iuId = iuId.concat("_") + today;
			// iuId = "iu=%2F7646%2Ftest_app_iphone_us%2Fdb_display%2Fdetails%2F10day_fri1";

		} else {
			iuId = ReadExcelValues.data[18][Cap];
		}

		boolean iuExists = false;
		for (String qry : getQueryList) {
			if (qry.contains(iuId)) {
				iuExists = true;
				break;
			}
		}
		boolean flag = false;
		boolean resflag = false;
		if (iuExists) {
			System.out.println(iuId + " ad call is present");
			logStep(iuId + " ad call is present");
			outerloop: for (int p = 0; p < nodeList.getLength(); p++) {
				// System.out.println("Total transactions: "+nodeList.getLength());
				if (nodeList.item(p) instanceof Node) {
					Node node = nodeList.item(p);
					if (node.hasChildNodes()) {
						NodeList nl = node.getChildNodes();
						for (int j = 0; j < nl.getLength(); j++) {
							// System.out.println("node1 length is: "+nl.getLength());
							Node innernode = nl.item(j);
							if (innernode != null) {
								// System.out.println("Innernode name is: "+innernode.getNodeName());
								if (innernode.getNodeName().equals("request")) {
									if (innernode.hasChildNodes()) {
										NodeList n2 = innernode.getChildNodes();
										for (int k = 0; k < n2.getLength(); k++) {
											// System.out.println("node2 length is: "+n2.getLength());
											Node innernode2 = n2.item(k);
											if (innernode2 != null) {
												// System.out.println("Innernode2 name is: "+innernode2.getNodeName());
												if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
													Element eElement = (Element) innernode2;
													// System.out.println("Innernode2 element name is:
													// "+eElement.getNodeName());
													if (eElement.getNodeName().equals("headers")) {
														if (innernode2.hasChildNodes()) {
															NodeList n3 = innernode2.getChildNodes();
															for (int q = 0; q < n3.getLength(); q++) {
																// System.out.println("node3 length is:
																// "+n3.getLength());
																Node innernode3 = n3.item(q);
																if (innernode3 != null) {
																	// System.out.println("Innernode3 name is:
																	// "+innernode3.getNodeName());
																	if (innernode3.getNodeType() == Node.ELEMENT_NODE) {
																		Element eElement1 = (Element) innernode3;
																		// System.out.println("Innernode3 element name
																		// is: "+eElement1.getNodeName());
																		if (eElement1.getNodeName().equals("header") || eElement1.getNodeName().equals("first-line")) {
																			String content = eElement1.getTextContent();
																			// System.out.println("request body
																			// "+content);

																			if (content.contains(iuId)) {
																				flag = true;
																				// System.out.println("request body
																				// "+content);
																				// istofRequestBodies.add(content);
																				// System.out.println("request body
																				// found "+content);
																				// break;
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}

								if (flag) {
									// System.out.println("Exiting after found true ");
									// System.out.println("checking innernode name is: "+innernode.getNodeName());
									if (innernode.getNodeName().equals("response")) {
										// System.out.println(innernode.getNodeName());
										if (innernode.hasChildNodes()) {
											NodeList n2 = innernode.getChildNodes();
											for (int k = 0; k < n2.getLength(); k++) {
												Node innernode2 = n2.item(k);
												if (innernode2 != null) {
													if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
														Element eElement = (Element) innernode2;
														if (eElement.getNodeName().equals("body")) {
															String content = eElement.getTextContent();
															// System.out.println("response body "+content);
															if (content.contains(sdir)) {
																System.out.println(sdir);
																if (content.contains(sdirClient)) {
																	System.out.println(sdirClient);
																	if (content.contains(sdirFolder)) {
																		System.out.println(sdirFolder);
																		if (content.contains(simgID)) {
																			System.out.println(simgID);
																			// System.out.println("response body
																			// "+content);
																			resflag = true;
																			break outerloop;
																		}
																	}
																}

															}
														}
													}
												}
											}
										}
									}

								}
								// break;
							}
						}
					}
				}
				flag = false;
			}

		} else {
			System.out.println(iuId + " ad call is not present");
			logStep(iuId + " ad call is not present");

		}

		// return flag;

		// Get Pubad call from

		if (resflag) {
			System.out
					.println(sheetName + " call response contains IM URL, hence " + sheetName + " Ad to be displayed");
			logStep(sheetName + " call response contains IM URL, hence " + sheetName + " Ad to be displayed");
			return resflag;
		} else {
			System.out.println(
					sheetName + " call response doesnt have IM URL, hence " + sheetName + " Ad not to be displayed");
			logStep(sheetName + " call response doesnt have IM URL, hence " + sheetName + " Ad not to be displayed");
			return resflag;
		}

	}

	/**
	 * Verifies gampad call by text in response
	 * @param excelName
	 * @param sheetName
	 * @throws Exception
	 */
	public static void Verify_Gampad_Call_ByResponseText(String excelName, String sheetName) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String expectedValue = ReadExcelValues.data[13][Cap];
		File fXmlFile = new File(outfile.getName());
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

// Getting custom_params amzn_b values
		List<String> customParamsList = new ArrayList<String>();

		String iuId = null;
		// String iuId =
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		if (sheetName.equalsIgnoreCase("IntegratedForecast")) {
			if (Ad instanceof IOSDriver<?>) {
				iuId = "iu=%2F7646%2Ftest_app_iphone_us%2Fdb_display%2Fcard%2Fdaily";
			} else if (Ad instanceof AndroidDriver<?>){
				iuId = "iu=%2F7646%2Ftest_app_android_us%2Fdb_display%2Fcard%2Fdaily";
			}
		} else if (sheetName.equalsIgnoreCase("NextGenIM")) {
			if (Ad instanceof IOSDriver<?>) {
				iuId = "iu=%2F7646%2Ftest_app_iphone_us%2Fdb_display%2Fhome_screen%2Fmarquee";
			} else if (Ad instanceof AndroidDriver<?>){
				iuId = "iu=%2F7646%2Ftest_app_android_us%2Fdb_display%2Fhome_screen%2Fmarquee";
			}
		} else if (sheetName.equalsIgnoreCase("IDD")) {
			String today = dailyDetailsDayOfWeek.concat("1");
			iuId = ReadExcelValues.data[18][Cap];
			iuId = iuId.concat("_") + today;
			// iuId = "iu=%2F7646%2Ftest_app_iphone_us%2Fdb_display%2Fdetails%2F10day_fri1";

		} else {
			iuId = ReadExcelValues.data[18][Cap];
		}

		boolean iuExists = false;
		for (String qry : getQueryList) {
			if (qry.contains(iuId)) {
				iuExists = true;
				break;
			}
		}
		boolean flag = false;
		boolean resflag = false;
		if (iuExists) {
			System.out.println(iuId + " ad call is present");
			logStep(iuId + " ad call is present");
			outerloop: for (int p = 0; p < nodeList.getLength(); p++) {
				// System.out.println("Total transactions: "+nodeList.getLength());
				if (nodeList.item(p) instanceof Node) {
					Node node = nodeList.item(p);
					if (node.hasChildNodes()) {
						NodeList nl = node.getChildNodes();
						for (int j = 0; j < nl.getLength(); j++) {
							// System.out.println("node1 length is: "+nl.getLength());
							Node innernode = nl.item(j);
							if (innernode != null) {
								// System.out.println("Innernode name is: "+innernode.getNodeName());
								if (innernode.getNodeName().equals("request")) {
									if (innernode.hasChildNodes()) {
										NodeList n2 = innernode.getChildNodes();
										for (int k = 0; k < n2.getLength(); k++) {
											// System.out.println("node2 length is: "+n2.getLength());
											Node innernode2 = n2.item(k);
											if (innernode2 != null) {
												// System.out.println("Innernode2 name is: "+innernode2.getNodeName());
												if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
													Element eElement = (Element) innernode2;
													// System.out.println("Innernode2 element name is:
													// "+eElement.getNodeName());
													if (eElement.getNodeName().equals("headers")) {
														if (innernode2.hasChildNodes()) {
															NodeList n3 = innernode2.getChildNodes();
															for (int q = 0; q < n3.getLength(); q++) {
																// System.out.println("node3 length is:
																// "+n3.getLength());
																Node innernode3 = n3.item(q);
																if (innernode3 != null) {
																	// System.out.println("Innernode3 name is:
																	// "+innernode3.getNodeName());
																	if (innernode3.getNodeType() == Node.ELEMENT_NODE) {
																		Element eElement1 = (Element) innernode3;
																		// System.out.println("Innernode3 element name
																		// is: "+eElement1.getNodeName());
																		if (eElement1.getNodeName().equals("header") || eElement1.getNodeName().equals("first-line")) {
																			String content = eElement1.getTextContent();
																			// System.out.println("request body
																			// "+content);

																			if (content.contains(iuId)) {
																				flag = true;
																				// System.out.println("request body
																				// "+content);
																				// istofRequestBodies.add(content);
																				// System.out.println("request body
																				// found "+content);
																				// break;
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}

								if (flag) {
									// System.out.println("Exiting after found true ");
									// System.out.println("checking innernode name is: "+innernode.getNodeName());
									if (innernode.getNodeName().equals("response")) {
										// System.out.println(innernode.getNodeName());
										if (innernode.hasChildNodes()) {
											NodeList n2 = innernode.getChildNodes();
											for (int k = 0; k < n2.getLength(); k++) {
												Node innernode2 = n2.item(k);
												if (innernode2 != null) {
													if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
														Element eElement = (Element) innernode2;
														if (eElement.getNodeName().equals("body")) {
															String content = eElement.getTextContent();
															// System.out.println("response body "+content);
															if (content.contains(expectedValue)) {
																System.out.println(expectedValue);
																resflag = true;
																break outerloop;

															}
														}
													}
												}
											}
										}
									}

								}
								// break;
							}
						}
					}
				}
				flag = false;
			}

			if (resflag) {
				System.out.println(sheetName + " call response contains " + expectedValue);
				logStep(sheetName + " call response contains " + expectedValue);
				System.out.println(sheetName + " call response validation is successful");
				logStep(sheetName + " call response validation is successful");

			} else {
				System.out.println(sheetName + " call response doesn't contains " + expectedValue);
				logStep(sheetName + " call response doesn't contains " + expectedValue);
				System.out.println(sheetName + " call response validation is failed");
				logStep(sheetName + " call response validation is failed");
				Assert.fail(sheetName + " call response doesn't contains " + expectedValue);
			}

		} else {
			System.out.println(iuId + " ad call is not present");
			logStep(iuId + " ad call is not present");
			System.out.println(sheetName + " call response validation is failed");
			logStep(sheetName + " call response validation is failed");
			Assert.fail(iuId + " ad call is not present hence response validation is failed");
		}

	}

	/**
	 * Verifies Marque ad call by response
	 * @param Excelname
	 * @param sheetName
	 * @throws Exception
	 */
	public static void verifyMarqueeAd_byCallResponse(String Excelname, String sheetName) throws Exception {

		boolean iMCallResponse = Utils.isNextGenIMorIFCall_hasResponse(Excelname, sheetName);
		String cardName = "homescreen";
		MobileElement adele = null;
		MobileElement nextGenAdImage  = null;

		TestBase.waitForMilliSeconds(10000);
		if (iMCallResponse == true) {
			try {
				if (Ad instanceof IOSDriver<?>) {
					adele = Ad.findElementByName("nextgen-integrated-marquee-card-containerView");
				} else if (Ad instanceof AndroidDriver<?>){
					adele = Ad.findElementById("com.weather.Weather:id/card_integrated_ad_root");
				}
				
				/*
				 * nextGenAdImage = Ad.findElementByXPath(
				 * "//XCUIElementTypeOther[@name=\"nextgen-integrated-marquee-card-containerView\"]//XCUIElementTypeImage"
				 * );
				 */
				if (adele.isDisplayed()) {
					// logStep("Nextgen IM Ad presented on the page "+cardName+" :--- Sizes are
					// "+adele.getSize());
					// System.out.println("Nextgen IM Ad presented on the page "+cardName+" :---
					// Sizes are "+adele.getSize());

					/*
					 * logStep("Nextgen IM Ad presented on the page " + cardName +
					 * " :--- Sizes are " + nextGenAdImage.getSize());
					 * System.out.println("Nextgen IM Ad presented on the page " + cardName +
					 * " :--- Sizes are " + nextGenAdImage.getSize());
					 */
					attachScreen();

				}

			} catch (Exception e) {
				System.out.println("Ad Not presented on the " + cardName + " screen though Marquee call response true");
				logStep("Ad Not presented on the " + cardName + " screen though Marquee call response true");
				attachScreen();
				Assert.fail("Ad Not presented on the " + cardName + " screen though Marquee call response true");
			}

		} else {
			try {
				if (Ad instanceof IOSDriver<?>) {
					adele = Ad.findElementByName("nextgen-integrated-marquee-card-containerView");
				} else if (Ad instanceof AndroidDriver<?>){
					adele = Ad.findElementById("com.weather.Weather:id/card_integrated_ad_root");
				}
				/*
				 * nextGenAdImage = Ad.findElementByXPath(
				 * "//XCUIElementTypeOther[@name=\"nextgen-integrated-marquee-card-containerView\"]//XCUIElementTypeImage"
				 * );
				 */
				if (adele.isDisplayed()) {
					/*
					 * logStep("Nextgen IM Ad presented on the page " + cardName +
					 * " when response is false :--- Sizes are " + nextGenAdImage.getSize());
					 * System.out.println("Nextgen IM Ad presented on the page " + cardName +
					 * " when response is false :--- Sizes are " + nextGenAdImage.getSize());
					 */
					attachScreen();
					Assert.fail("Nextgen IM Ad presented on the page " + cardName + " when response is false");
				}

			} catch (Exception e) {
				System.out.println("Ad Not presented on the " + cardName + " screen since Marquee call response false");
				logStep("Ad Not presented on the " + cardName + " screen since Marquee call response false");
				attachScreen();

			}
		}

	}

	
	/**
	 * Verifies IFC ad by call response
	 * @param Excelname
	 * @param sheetName
	 * @throws Exception
	 */
	public static void verifyIntegratedFeedCardAd_byCallResponse(String Excelname, String sheetName) throws Exception {

		boolean iMCallResponse = Utils.isNextGenIMorIFCall_hasResponse(Excelname, sheetName);
		String cardName = "homescreen";
		MobileElement adele = null;
		MobileElement nextGenAdImage = null;

		TestBase.waitForMilliSeconds(10000);
		if (iMCallResponse == true) {
			try {
				
				if (Ad instanceof IOSDriver<?>) {
					adele = Ad.findElementByName("integrated-ad-card-containerView");
				} else if (Ad instanceof AndroidDriver<?>){
					adele = Ad.findElementByXPath("(//android.widget.FrameLayout[@resource-id=\"com.weather.Weather:id/ad_view_holder\"]//android.webkit.WebView)[1]");
				}
				/*
				 * nextGenAdImage = Ad.findElementByXPath(
				 * "//XCUIElementTypeOther[@name=\"nextgen-integrated-marquee-card-containerView\"]//XCUIElementTypeImage"
				 * );
				 */
				if (adele.isDisplayed()) {
					// logStep("Nextgen IM Ad presented on the page "+cardName+" :--- Sizes are
					// "+adele.getSize());
					// System.out.println("Nextgen IM Ad presented on the page "+cardName+" :---
					// Sizes are "+adele.getSize());

					/*
					 * logStep("Nextgen IM Ad presented on the page " + cardName +
					 * " :--- Sizes are " + nextGenAdImage.getSize());
					 * System.out.println("Nextgen IM Ad presented on the page " + cardName +
					 * " :--- Sizes are " + nextGenAdImage.getSize());
					 */
					attachScreen();

				}

			} catch (Exception e) {
				System.out.println("Ad Not presented on the " + cardName + " screen though Marquee call response true");
				logStep("Ad Not presented on the " + cardName + " screen though Marquee call response true");
				attachScreen();
				Assert.fail("Ad Not presented on the " + cardName + " screen though Marquee call response true");
			}

		} else {
			try {
				if (Ad instanceof IOSDriver<?>) {
					adele = Ad.findElementByName("integrated-ad-card-containerView");
				} else if (Ad instanceof AndroidDriver<?>){
					adele = Ad.findElementByXPath("(//android.widget.FrameLayout[@resource-id=\"com.weather.Weather:id/ad_view_holder\"]//android.webkit.WebView)[1]");
				}
				/*
				 * nextGenAdImage = Ad.findElementByXPath(
				 * "//XCUIElementTypeOther[@name=\"nextgen-integrated-marquee-card-containerView\"]//XCUIElementTypeImage"
				 * );
				 */
				if (adele.isDisplayed()) {
					/*
					 * logStep("Nextgen IM Ad presented on the page " + cardName +
					 * " when response is false :--- Sizes are " + nextGenAdImage.getSize());
					 * System.out.println("Nextgen IM Ad presented on the page " + cardName +
					 * " when response is false :--- Sizes are " + nextGenAdImage.getSize());
					 */
					attachScreen();
					Assert.fail("Nextgen IM Ad presented on the page " + cardName + " when response is false");
				}

			} catch (Exception e) {
				System.out.println("Ad Not presented on the " + cardName + " screen since Marquee call response false");
				logStep("Ad Not presented on the " + cardName + " screen since Marquee call response false");
				attachScreen();

			}
		}

	}

	/**
	 * Verifies feed calls like feed1, feed2,....etc
	 * @param excelName
	 * @param sheetName
	 * @throws Exception
	 */
	public static void Verify_feedcalls(String excelName, String sheetName) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);

		// Read the content form file
		File fXmlFile = new File(outfile.getName());
		// File fXmlFile = outfile;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		// NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		// Getting custom_params amzn_b values
		// List<String> customParamsList = new ArrayList<String>();

		// String iuId =
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		// String iuId = "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fcard%2Fdaily";

		String[] feedCards = ReadExcelValues.data[7][Cap].toString().split(",");

		int feedCount = feedCards.length;
		String iuId = null;
		String cardsNotPresent = "";
		int failCount = 0;

		for (int i = 0; i < feedCount; i++) {
			iuId = ReadExcelValues.data[5][Cap] + feedCards[i];
			if (i == 0) {
				iuId = ReadExcelValues.data[1][Cap] + feedCards[i];
				boolean iuExists = false;

				for (String qry : getQueryList) {
					if (qry.contains(iuId)) {

						iuExists = true;

						break;

					}
				}
				if (iuExists) {
					System.out.println(feedCards[i] + " ad call is present");
					logStep(feedCards[i] + " ad call is present");
				} else {
					System.out.println(feedCards[i] + " ad call is not present");
					logStep(feedCards[i] + " ad call is not present");
					failCount++;
					// Assert.fail(feedCards[i] + " ad call is not present");
					cardsNotPresent = cardsNotPresent.concat(feedCards[i] + ", ");

				}
			} else {

				boolean iuExists = false;

				for (String qry : getQueryList) {
					if (qry.contains(iuId)) {

						iuExists = true;

						break;

					}
				}
				if (iuExists) {
					System.out.println(feedCards[i] + " ad call is present");
					logStep(feedCards[i] + " ad call is present");
				} else {
					System.out.println(feedCards[i] + " ad call is not present");
					logStep(feedCards[i] + " ad call is not present");
					failCount++;
					// Assert.fail(feedCards[i] + " ad call is not present");
					cardsNotPresent = cardsNotPresent.concat(feedCards[i] + ", ");

				}
			}
		}
		if (failCount > 0) {
			System.out.println(cardsNotPresent + " ad call is not present");
			logStep(cardsNotPresent + " ad call is not present");
			System.out.println("Feed Calls validation is failed");
			logStep("Feed Calls validation is failed");
			Exception = cardsNotPresent + " ad call is not present";
			Assert.fail(Exception);
		}

	}

	/**
	 * Verify Feed calls of new feed ads IOSFLAG-3229
	 * 
	 * @param excelName
	 * @param sheetName
	 * @throws Exception
	 */
	public static void Verify_newfeedAdcalls(String excelName, String sheetName) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);

		// Read the content form file
		File fXmlFile = new File(outfile.getName());
		// File fXmlFile = outfile;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		// NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		// Getting custom_params amzn_b values
		// List<String> customParamsList = new ArrayList<String>();

		// String iuId =
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		// String iuId = "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fcard%2Fdaily";

		String[] feedCards = ReadExcelValues.data[7][Cap].toString().split(",");

		int feedCount = feedCards.length;
		// String iuId = null;
		boolean iuExists = false;
		String cardsNotPresent = "";
		int failCount = 0;

		for (int i = 0; i < feedCount; i++) {

			if (i == 0) {
				/*
				 * if (!nextGenIMadDisplayed) { String iuId = readExcelValues.data[1][Cap] +
				 * feedCards[i]; System.out.println("iu  value is: " + iuId);
				 * logStep("iu  value is: " + iuId); iuExists = false;
				 * 
				 * for (String qry : getQueryList) { if (qry.contains(iuId)) {
				 * 
				 * iuExists = true;
				 * 
				 * break;
				 * 
				 * } } if (iuExists) { System.out.println(feedCards[i] + " ad call is present");
				 * logStep(feedCards[i] + " ad call is present"); } else {
				 * System.out.println(feedCards[i] + " ad call is not present");
				 * logStep(feedCards[i] + " ad call is not present"); failCount++; //
				 * Assert.fail(feedCards[i] + " ad call is not present"); cardsNotPresent =
				 * cardsNotPresent.concat(feedCards[i] + ", ");
				 * 
				 * } }
				 */

			} else {
				for (int j = 1; j <= feedAdCount; j++) {
					String iuId = ReadExcelValues.data[5][Cap] + feedCards[i] + j;
					System.out.println("iu  value is: " + iuId);
					logStep("iu  value is: " + iuId);
					iuExists = false;

					for (String qry : getQueryList) {
						if (qry.contains(iuId)) {

							iuExists = true;

							break;

						} else {
							if (j == 3) {
								String currentFeedAd_Card1 = "hpvar_3";
								String iuId1 = ReadExcelValues.data[5][Cap] + currentFeedAd_Card1;
								if (qry.contains(iuId1)) {
									iuExists = true;
									iuId = iuId1;
									break;
								}
							}
						}
					}
					if (iuExists) {
						System.out.println(feedCards[i] + j + " ad call is present");
						logStep(feedCards[i] + j + " ad call is present");
					} else {
						System.out.println(feedCards[i] + j + " ad call is not present");
						logStep(feedCards[i] + j + " ad call is not present");
						failCount++;
						// Assert.fail(feedCards[i] + " ad call is not present");
						cardsNotPresent = cardsNotPresent.concat(feedCards[i] + j + ", ");

					}
				}

			}
		}
		if (failCount > 0) {
			System.out.println(cardsNotPresent + " ad call is not present");
			logStep(cardsNotPresent + " ad call is not present");
			Exception = cardsNotPresent + " ad call is not present";
			System.out.println("New Feed Calls validation is failed");
			logStep("New Feed Calls validation is failed");
			Assert.fail(Exception);
		}

	}

	/**
	 * Verifies Feed ads size
	 * @param excelName
	 * @param sheetName
	 * @throws Exception
	 */
	public static void verify_newfeeds_Ad_Size(String excelName, String sheetName) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String size = ReadExcelValues.data[10][Cap];

		ReadExcelValues.excelValues(excelName, sheetName);

		// Read the content form file
		File fXmlFile = new File(outfile.getName());
		// File fXmlFile = outfile;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		// NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		// Getting custom_params amzn_b values
		// List<String> customParamsList = new ArrayList<String>();

		// String iuId =
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		// String iuId = "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fcard%2Fdaily";
		// String iuId = readExcelValues.data[18][Cap];

		boolean iuExists = false;
		boolean sizeExists = false;

		int feedAdCountConsidered = feedAdCount - 4;
		int feedCardNo = 5;
		String feedCardsSizeNotMatched = "";
		int failCount = 0;
		if (feedAdCountConsidered > 0) {
			for (int p = 0; p < feedAdCountConsidered; p++) {
				String iuId = ReadExcelValues.data[5][Cap] + "feed_" + feedCardNo;
				iuExists = false;
				sizeExists = false;

				for (String qry : getQueryList) {
					if (qry.contains(iuId)) {

						iuExists = true;

						if (qry.contains(size)) {

							sizeExists = true;

							break;

						}

					}
				}
				if (iuExists && sizeExists) {
					System.out.println(iuId + " ad call is present & Size of ad matched with expected Size: " + size);
					logStep(iuId + " ad call is present & Size of ad matched with expected Size: " + size);
				} else {
					System.out.println("New feed feed_" + feedCardNo + "ad size validation is failed");
					logStep("New feed feed_" + feedCardNo + "ad size validation is failed");
					failCount++;
					// Assert.fail(feedCards[i] + " ad call is not present");
					feedCardsSizeNotMatched = feedCardsSizeNotMatched.concat("feed_" + feedCardNo + ", ");
					if (!iuExists) {
						System.out.println(iuId + " ad call is not present");
						logStep(iuId + " ad call is not present");

					} else {
						System.out.println(
								iuId + " ad call is present & Size of ad is not matched with expected Size: " + size);
						logStep(iuId + " ad call is present & Size of ad is not matched with expected Size: " + size);
					}

				}
				feedCardNo++;

			}
		} else {
			System.out.println("There are no more than " + feedCardNo + " feed ad cards");
			logStep("There are no more than " + feedCardNo + " feed ad cards");
			System.out.println("Ads Size validation is failed");
			logStep("Ads Size validation is failed");
			Assert.fail("There are no more than " + feedCardNo + " feed ad cards");
		}

		if (failCount > 0) {
			System.out.println(feedCardsSizeNotMatched + " Ads Size validation is failed");
			logStep(feedCardsSizeNotMatched + " Ads Size validation is failed");
			Exception = feedCardsSizeNotMatched + " Ads Size validation is failed";
			Assert.fail(Exception);
		}

	}

	/**
	 * Till Feed_4 calls are preloaded, from feed_5 onwards amazon calls are
	 * generated after swiping through corresponding card.
	 */
	public static void verifyFeedAds_AAX_SlotIds(String excelName, String sheetName) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);

		// Read the content form file
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");

		// Read JSONs and get b value
		// List<String> jsonBValuesList = new ArrayList<String>();

		// String slotId = "153f5936-781f-4586-8fdb-040ce298944a";

		// String slotId = "c4dd8ec4-e40c-4a63-ae81-8f756793ac5e";
		String[] feedCardsUUIDs = ReadExcelValues.data[9][Cap].toString().split(",");

		int uuidCount = feedCardsUUIDs.length;
		int feedAdCountConsidered = feedAdCount - 4;
		int feedCardNo = 5;
		String feedCardsUUIDNotPresent = "";
		int failCount = 0;
		// for(int p = 0; p < uuidCount; p++) {
		for (int p = 0; p < feedAdCountConsidered; p++) {
			String slotId = feedCardsUUIDs[p];

			boolean flag = false;
			// List<String> istofRequestBodies = new ArrayList<String>();
			// List<String> istofResponseBodies = new ArrayList<String>();
			// List<String> listOf_b_Params = new ArrayList<String>();

			nodeList: for (int i = 0; i < nodeList.getLength(); i++) {
				if (nodeList.item(i) instanceof Node) {
					Node node = nodeList.item(i);
					if (node.hasChildNodes()) {
						NodeList nl = node.getChildNodes();
						NodeList: for (int j = 0; j < nl.getLength(); j++) {
							Node innernode = nl.item(j);
							if (innernode != null) {
								if (innernode.getNodeName().equals("request")) {
									if (innernode.hasChildNodes()) {
										NodeList n2 = innernode.getChildNodes();
										for (int k = 0; k < n2.getLength(); k++) {
											Node innernode2 = n2.item(k);
											if (innernode2 != null) {
												if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
													Element eElement = (Element) innernode2;
													if (eElement.getNodeName().equals("body")) {
														String content = eElement.getTextContent();
														if (content.contains(slotId)) {
															flag = true;
															// istofRequestBodies.add(content);

															break nodeList;

															// System.out.println("request body "+content);
														}
													}
												}
											}
										}

									}
								}

							}
						}

					}
				}

			}
			if (flag) {
				System.out.println("feed_" + feedCardNo + " slot id: " + slotId + " is present");
				logStep("feed_" + feedCardNo + " slot id: " + slotId + " is present");

			} else {
				System.out.println("slot id: " + slotId + " is not present");
				logStep("slot id: " + slotId + " is not present");
				// Assert.fail("slot id: " + slotId + " is not present");
				System.out.println("feed_" + feedCardNo + " slot id not present");
				logStep("feed_" + feedCardNo + " slot id not present");
				failCount++;
				// Assert.fail(feedCards[i] + " ad call is not present");
				feedCardsUUIDNotPresent = feedCardsUUIDNotPresent.concat("feed_" + feedCardNo + ", ");
			}
			feedCardNo++;
		}

		if (failCount > 0) {
			System.out.println(feedCardsUUIDNotPresent + " UUID is not present");
			logStep(feedCardsUUIDNotPresent + " UUID is not present");
			Exception = feedCardsUUIDNotPresent + " UUID is not present";
			Assert.fail(Exception);
		}

	}

	/**
	 * verifies rdp value in gampad call
	 * @param excelName
	 * @param sheetName
	 * @param expected
	 * @throws Exception
	 */
	public static void validate_rdp_val_in_gampad_url(String excelName, String sheetName, boolean expected)
			throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);

		// Read the content form file
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		// Getting custom_params amzn_b values
		List<String> customParamsList = new ArrayList<String>();

		String iuId = null;
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		if (sheetName.equalsIgnoreCase("PreRollVideo")) {
			iuId = videoIUValue;
		} else {
			iuId = ReadExcelValues.data[18][Cap];
		}

		boolean rdpExists = false;
		boolean iuExists = false;
		for (String qry : getQueryList) {
			if (qry.contains(iuId)) {
				iuExists = true;
				if (qry.contains("rdp=1")) {
					rdpExists = true;
					// if (!"".equals(tempCustmParam))
					// customParamsList.add(getCustomParamsBy_iu_value(qry));
					break;
				}

			}

		}

		if (expected) {
			System.out.println("rdp keyword expected to present in URL");
			logStep("rdp keyword expected to present in URL");
		} else {
			System.out.println("rdp keyword not expected to present in URL");
			logStep("rdp keyword not expected to present in URL");

		}
		if (nextGenIMadDisplayed && sheetName.equalsIgnoreCase("Pulltorefresh")) {
			System.out.println("Since IM Ad displayed on App Launch, Homescreen call validation is skipped");
			logStep("Since IM Ad displayed on App Launch, Homescreen call validation is skipped");
		} else if (iuExists) {
			if (rdpExists == expected) {
				System.out.println("rdp keyword validation is successful");
				logStep("rdp keyword validation is successful");
			} else {
				System.out.println("rdp keyword validation is failed");
				logStep("rdp keyword validation is failed");
				if (expected) {
					System.out.println("rdp keyword expected to present in URL but it not exist");
					logStep("rdp keyword expected to present in URL but it not exist");
					Assert.fail("rdp keyword expected to present in URL but it not exist");
				} else {
					System.out.println("rdp keyword not expected to present in URL but it exists");
					logStep("rdp keyword not expected to present in URL but it exists");
					Assert.fail("rdp keyword not expected to present in URL but it exists");
				}

			}
		} else {
			System.out.println(iuId + " ad call is not present");
			logStep(iuId + " ad call is not present");
			System.out.println("rdp keyword validation is failed");
			logStep("rdp keyword validation is failed");
			Assert.fail(iuId + " ad call is not present");
		}

	}

	/**
	 * verifies npa valuee in gampad call
	 * @param excelName
	 * @param sheetName
	 * @param expected
	 * @throws Exception
	 */
	public static void validate_npa_val_in_gampad_url(String excelName, String sheetName, boolean expected)
			throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);

		// Read the content form file
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		// Getting custom_params amzn_b values
		List<String> customParamsList = new ArrayList<String>();

		String iuId = null;
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		if (sheetName.equalsIgnoreCase("PreRollVideo")) {
			iuId = videoIUValue;
		} else {
			iuId = ReadExcelValues.data[18][Cap];
		}
		boolean rdpExists = false;
		boolean iuExists = false;
		for (String qry : getQueryList) {
			if (qry.contains(iuId)) {
				iuExists = true;
				if (qry.contains("npa=1")) {
					rdpExists = true;
					// if (!"".equals(tempCustmParam))
					// customParamsList.add(getCustomParamsBy_iu_value(qry));
					break;
				}

			}

		}

		if (expected) {
			System.out.println("npa keyword expected to present in URL");
			logStep("npa keyword expected to present in URL");
		} else {
			System.out.println("npa keyword not expected to present in URL");
			logStep("npa keyword not expected to present in URL");

		}
		if (nextGenIMadDisplayed && sheetName.equalsIgnoreCase("Pulltorefresh")) {
			System.out.println("Since IM Ad displayed on App Launch, Homescreen call validation is skipped");
			logStep("Since IM Ad displayed on App Launch, Homescreen call validation is skipped");
		} else if (iuExists) {
			if (rdpExists == expected) {
				System.out.println("npa keyword validation is successful");
				logStep("npa keyword validation is successful");
			} else {
				System.out.println("npa keyword validation is failed");
				logStep("npa keyword validation is failed");
				if (expected) {
					System.out.println("npa keyword expected to present in URL but it not exist");
					logStep("npa keyword expected to present in URL but it not exist");
					Assert.fail("npa keyword expected to present in URL but it not exist");
				} else {
					System.out.println("npa keyword not expected to present in URL but it exists");
					logStep("npa keyword not expected to present in URL but it exists");
					Assert.fail("npa keyword not expected to present in URL but it exists");
				}

			}
		} else {
			System.out.println(iuId + " ad call is not present");
			logStep(iuId + " ad call is not present");
			System.out.println("npa keyword validation is failed");
			logStep("npa keyword validation is failed");
			Assert.fail(iuId + " ad call is not present");
		}

	}
	
	/**
	 * verifies non custom parameter valuee in gampad call
	 * @param excelName
	 * @param sheetName
	 * @param param
	 * @param value
	 * @throws Exception
	 */
	public static void validate_non_custom_param_val_in_gampad_url(String excelName, String sheetName, String param, String value)
			throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);

		// Read the content form file
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		// Getting custom_params amzn_b values
		List<String> customParamsList = new ArrayList<String>();

		String iuId = null;
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		if (sheetName.equalsIgnoreCase("PreRollVideo")) {
			iuId = videoIUValue;
		} else {
			iuId = ReadExcelValues.data[18][Cap];
		}
		if (param.equalsIgnoreCase("content_url")) {
			value = value.replace(":", "%3A").replace("/", "%2F");
		}
		boolean rdpExists = false;
		boolean iuExists = false;
		for (String qry : getQueryList) {
			if (qry.contains(iuId)) {
				iuExists = true;
				if (qry.contains(param+"="+value)) {
					rdpExists = true;
					// if (!"".equals(tempCustmParam))
					// customParamsList.add(getCustomParamsBy_iu_value(qry));
					break;
				}

			}

		}
		
		if (nextGenIMadDisplayed && sheetName.equalsIgnoreCase("Pulltorefresh")) {
			System.out.println("Since IM Ad displayed on App Launch, Homescreen call validation is skipped");
			logStep("Since IM Ad displayed on App Launch, Homescreen call validation is skipped");
		} else if (iuExists) {
			if (rdpExists ) {
				System.out.println(param+"="+value +" is found in "+sheetName+" gampad call, hence : "+param+" parameter validation is successful");
				logStep(param+"="+value +" is found in "+sheetName+" gampad call, hence : "+param+" parameter validation is successful");
			} else {
				System.out.println(param+"="+value +" is not found in "+sheetName+" gampad call, hence : "+param+" parameter validation is failed");
				logStep(param+"="+value +" is not found in "+sheetName+" gampad call, hence : "+param+" parameter validation is failed");
				Assert.fail(param+"="+value +" is not found in "+sheetName+" gampad call, hence : "+param+" parameter validation is failed");
			}
		} else {
			System.out.println(iuId + " ad call is not present");
			logStep(iuId + " ad call is not present");
			System.out.println(param+"="+value +" parameter validation is failed");
			logStep(param+"="+value +" parameter validation is failed");
			Assert.fail(iuId + " ad call is not present");
		}

	}

	/**
	 * get b values from amazon aax calls of XML File and store to list
	 * 
	 * @param slotID
	 * @param clearList
	 * @throws Exception
	 */
	public static void get_amazon_bid_values_from_aaxCalls(String slotID, boolean clearList) throws Exception {

		// readExcelValues.excelValues(excelName, sheetName);

		// Read the content form file
		File fXmlFile = new File(outfile.getName());
		if (clearList) {
			listOf_b_Params.clear();
			aaxcallsSize = 0;
		}

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");

		// Read JSONs and get b value
		// List<String> jsonBValuesList = new ArrayList<String>();

		// String slotId = readExcelValues.data[21][Cap];

		// String slotId = "c4dd8ec4-e40c-4a63-ae81-8f756793ac5e";
		//"slot":"9be28769-4207-4d51-8063-dc8e645383b2"
		slotID = "\"slot\":\""+slotID+"\""; 

		boolean flag = false;
		List<String> istofRequestBodies = new ArrayList<String>();
		List<String> istofResponseBodies = new ArrayList<String>();
		// List<String> listOf_b_Params = new ArrayList<String>();

		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i) instanceof Node) {
				Node node = nodeList.item(i);
				if (node.hasChildNodes()) {
					NodeList nl = node.getChildNodes();
					for (int j = 0; j < nl.getLength(); j++) {
						Node innernode = nl.item(j);
						if (innernode != null) {
							if (innernode.getNodeName().equals("request")) {
								if (innernode.hasChildNodes()) {
									NodeList n2 = innernode.getChildNodes();
									for (int k = 0; k < n2.getLength(); k++) {
										Node innernode2 = n2.item(k);
										if (innernode2 != null) {
											if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
												Element eElement = (Element) innernode2;
												if (eElement.getNodeName().equals("body")) {
													String content = eElement.getTextContent();
													if (content.contains(slotID)) {
														flag = true;
														aaxcallsSize++;
														istofRequestBodies.add(content);
														 //System.out.println("request body "+content);
													}
												}
											}
										}
									}
								}
							}

							if (flag) {
								if (innernode.getNodeName().equals("response")) {
									// System.out.println(innernode.getNodeName());
									if (innernode.hasChildNodes()) {
										NodeList n2 = innernode.getChildNodes();
										for (int k = 0; k < n2.getLength(); k++) {
											Node innernode2 = n2.item(k);
											if (innernode2 != null) {
												if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
													Element eElement = (Element) innernode2;
													if (eElement.getNodeName().equals("body")) {
														String content = eElement.getTextContent();
														istofResponseBodies.add(content);
														String tempBparam = get_b_value_inJsonResponseBody(content);
														if (!"".contentEquals(tempBparam)) {
															listOf_b_Params.add(tempBparam);
														}
														// System.out.println("response body "+content);
													}
												}
											}
										}
									}
								}

							}

						}
					}
				}
			}
			flag = false;
		}
		System.out.println(listOf_b_Params);
		logStep(listOf_b_Params.toString());

		aaxcallsResponseSize = listOf_b_Params.size();
		aaxbidErrorCount = 0;
		for (String b : listOf_b_Params) {
			System.out.println(" b values from JSON-----------> " + b);
			if (b.contentEquals("error")) {
				aaxbidErrorCount++;
			}
		}
		System.out.println("aaxcalls Size is: " + aaxcallsSize);
		System.out.println("aaxcallsResponse Size is: " + aaxcallsResponseSize);
		System.out.println("aaxbidErrorCount size is: " + aaxbidErrorCount);

	}

	/**
	 * This retrives amazon bid values of specific call from amazon calls and add to
	 * list
	 * 
	 * @param excelName
	 * @param sheetName
	 * @param clearList
	 * @throws Exception
	 */
	public static void load_amazon_bid_values_from_aaxCalls(String excelName, String sheetName, boolean clearList)
			throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String slotID = ReadExcelValues.data[21][Cap];
		get_amazon_bid_values_from_aaxCalls(slotID, clearList);
	}

	/**
	 * Get b value from gampad calls of XML File and store to list
	 * 
	 * @param excelName
	 * @param sheetName
	 * @param feedCall
	 * @param cust_param
	 * @throws Exception
	 */
	public static void get_amazon_bid_values_from_gampadCalls(String excelName, String sheetName, String feedCall,
			String cust_param) throws Exception {
		// readExcelValues.excelValues(excelName, sheetName);

		// Read the content form file
		File fXmlFile = new File(outfile.getName());
		customParamsList.clear();
		isaaxgampadcallexists = false;
		int gampadcallcount = 0;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		// NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		// Getting custom_params amzn_b values
		// List<String> customParamsList = new ArrayList<String>();

		// String iuId =
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		// String iuId = "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fcard%2Fdaily";
		String queryIU = null;
		for (String qry : getQueryList) {
			if (qry.contains(feedCall)) {
				if (sheetName.equalsIgnoreCase("Hourly")) {
					queryIU = return_iu_value_from_query_parameter_of_Feedcall(qry);
					if (queryIU.equalsIgnoreCase(feedCall)) {
						gampadcallcount++;
						isaaxgampadcallexists = true;
						String tempCustmParam = getCustomParamBy_iu_value(qry, cust_param);
						if (!"".equals(tempCustmParam)) {
							customParamsList.add(getCustomParamBy_iu_value(qry, cust_param));
						} else {
							customParamsList.add("-1");
						}
					}
				} else {
					gampadcallcount++;
					isaaxgampadcallexists = true;
					String tempCustmParam = getCustomParamBy_iu_value(qry, cust_param);
					if (!"".equals(tempCustmParam)) {
						customParamsList.add(getCustomParamBy_iu_value(qry, cust_param));
					} else {
						customParamsList.add("-1");
					}
				}

			}
		}

		if (!isaaxgampadcallexists) {
			System.out.println("Corresponding gampad call " + feedCall + " is not generated..");
			logStep("Corresponding gampad call " + feedCall + " is not generated..");
		} else {
			System.out.println("No of times the gampad call found is: " + gampadcallcount);
			logStep("No of times the gampad call found is: " + gampadcallcount);
			System.out.println(customParamsList);
			logStep(customParamsList.toString());
		}

	}

	/**
	 * This retrives amazon bid values from aax calls and gampad calls of
	 * corresponding add calls and verifies any one matching.
	 * 
	 * @param excelName
	 * @param sheetName
	 * @param clearList
	 * @throws Exception
	 */
	public static void validate_aax_bid_value_with_gampad_bid_value(String excelName, String sheetName,
			boolean clearList) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String slotID = ReadExcelValues.data[21][Cap];
		// String feedCall = readExcelValues.data[18][Cap];
		String feedCall = null;
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		if (sheetName.equalsIgnoreCase("PreRollVideo")) {
			feedCall = videoIUValue;
		} else if (sheetName.equalsIgnoreCase("News(details)")) {
			feedCall = return_iu_value_of_Feedcall(excelName, sheetName);
		} else {
			feedCall = ReadExcelValues.data[18][Cap];
		}

		boolean testpass = false;
		int failCount = 0;
		int successCount = 0;
		int amazonBiddingFailCount = 0;
		int amazonBiddingSuccessCount = 0;
		int maxIterations = 0;
		String cust_param = "amzn_b";

		if (sheetName.contains("PreRollVideo")) {
			cust_param = "amzn_vid";
		}

		get_amazon_bid_values_from_aaxCalls(slotID, clearList);

		if (aaxcallsSize == 0) {
			System.out.println("amazon aax " + sheetName
					+ " call is not generated in current session, so skipping the bid value verification");
			logStep("amazon aax " + sheetName
					+ " call is not generated in current session, so skipping the bid value verification");

		} else if (aaxbidErrorCount == aaxcallsResponseSize) {
			System.out.println("amazon aax " + sheetName
					+ " call response contains error i.e. bidding is not happened in current session, so skipping the bid value verification");
			logStep("amazon aax " + sheetName
					+ " call response contains error i.e. bidding is not happened in current session, so skipping the bid value verification");

		} else if (nextGenIMadDisplayed && sheetName.equalsIgnoreCase("Pulltorefresh")) {
			/*
			 * There may be chances that gampad call might not generated.. for ex: when IM
			 * ad displayed on home screen, then homescreen today call doesnt generate
			 * 
			 */
			System.out.println("Since IM Ad displayed on App Launch, Homescreen call bid id validation is skipped");
			logStep("Since IM Ad displayed on App Launch, Homescreen call bid id validation is skipped");
		} else {
			get_amazon_bid_values_from_gampadCalls(excelName, sheetName, feedCall, cust_param);
			/*
			 * below checks whether the gampad call exists or not before validating for
			 * amazon bid value..
			 */
			if (!isaaxgampadcallexists) {
				System.out.println("Ad Call :" + feedCall + " not found in charles session, hence Custom Parameter: "
						+ cust_param + " validation is failed");
				logStep("Ad Call :" + feedCall + " not found in charles session, hence Custom Parameter: " + cust_param
						+ " validation is failed");
				Assert.fail("Ad Call :" + feedCall + " not found in charles session, hence Custom Parameter: "
						+ cust_param + " validation is failed");
			} else if (customParamsList.size() == 0) {
				System.out.println("Ad Call :" + feedCall + " not contains the Custom Parameter: " + cust_param
						+ ", hence Custom Parameter: " + cust_param + " validation is failed");
				logStep("Ad Call :" + feedCall + " not contains the Custom Parameter: " + cust_param
						+ ", hence Custom Parameter: " + cust_param + " validation is failed");
				Assert.fail("Ad Call :" + feedCall + " not contains the Custom Parameter: " + cust_param
						+ ", hence Custom Parameter: " + cust_param + " validation is failed");
			} else {

				if (listOf_b_Params.size() > customParamsList.size()) {
					maxIterations = customParamsList.size();
				} else {
					maxIterations = listOf_b_Params.size();
				}
				if (sheetName.equalsIgnoreCase("Health(goRun)") || sheetName.equalsIgnoreCase("Health(boatAndBeach)")) {
					int iterations = 0;

					if (maxIterations == 1) {
						iterations = 2;
					} else {
						iterations = maxIterations;
					}
					for (int i = 0; i < iterations / 2; i++) {
						if (listOf_b_Params.get(i).equalsIgnoreCase("error")) {
							amazonBiddingFailCount++;
							if (listOf_b_Params.size() == 1) {
								System.out.println(
										"It looks that the only Occurance of Amazon Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
								logStep("It looks that the only Occurance of Amazon Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
							} else {
								System.out.println("It looks that: " + i
										+ " Occurance of Amazon Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
								logStep("It looks that: " + i
										+ " Occurance of Amazon Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
							}

						} else {
							amazonBiddingSuccessCount++;
							if (listOf_b_Params.get(i).equalsIgnoreCase(customParamsList.get(i + 1))) {
								successCount++;
								/*
								 * System.out.println("amazon aax " + sheetName +
								 * " call bid value is matched with corresponding gampad call bid value");
								 * logStep("amazon aax " + sheetName +
								 * " call bid value is matched with corresponding gampad call bid value");
								 */

								System.out.println(i + " Occurance of Amazon call bid value: " + listOf_b_Params.get(i)
										+ " is matched with " + (i + 1) + " Occurance of corresponding " + sheetName
										+ " gampad call " + cust_param + " value: " + customParamsList.get(i + 1));
								logStep(i + " Occurance of Amazon call bid value: " + listOf_b_Params.get(i)
										+ " is matched with " + (i + 1) + " Occurance of corresponding " + sheetName
										+ " gampad call " + cust_param + " value: " + customParamsList.get(i + 1));
								// System.out.println("amazon aax " + sheetName + " call bid value validation is
								// successful");
								// logStep("amazon aax " + sheetName + " call bid value vaidation is
								// successful");
								// break;

							} else {
								if (customParamsList.get(i + 1).equalsIgnoreCase("-1")) {
									System.out.println(i + 1 + " Occurance of corresponding " + sheetName
											+ " gampad call: " + feedCall + " not having parameter " + cust_param);
									logStep(i + 1 + " Occurance of corresponding " + sheetName + " gampad call: "
											+ feedCall + " not having parameter " + cust_param);
									failCount++;
								} else {
									System.out.println(i + " Occurance of Amazon call " + cust_param + " value: "
											+ listOf_b_Params.get(i) + " is not matched with " + i + 1
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i + 1));
									logStep(i + " Occurance of Amazon call " + cust_param + " value: "
											+ listOf_b_Params.get(i) + " is not matched with " + i + 1
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i + 1));
									failCount++;
								}

							}

						}

					}

				} else if (sheetName.equalsIgnoreCase("Health(coldAndFluArticles)")
						|| sheetName.equalsIgnoreCase("Health(allergyArticles)")) {

					int j;
					if (aaxHealthArticlesCheckCount == 0) {
						j = 0;
					} else {
						j = aaxHealthArticlesCheckCount;
					}
					for (int i = 0; i < maxIterations; i++, j++) {
						aaxHealthArticlesCheckCount++;
						if (listOf_b_Params.get(j).equalsIgnoreCase("error")) {
							amazonBiddingFailCount++;
							if (listOf_b_Params.size() == 1) {
								System.out.println(
										"It looks that the only Occurance of Amazon Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
								logStep("It looks that the only Occurance of Amazon Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
							} else {
								System.out.println("It looks that: " + j
										+ " Occurance of Amazon Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
								logStep("It looks that: " + j
										+ " Occurance of Amazon Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
							}

						} else {
							amazonBiddingSuccessCount++;
							if (listOf_b_Params.get(j).equalsIgnoreCase(customParamsList.get(i))) {
								successCount++;
								/*
								 * System.out.println("amazon aax " + sheetName +
								 * " call bid value is matched with corresponding gampad call bid value");
								 * logStep("amazon aax " + sheetName +
								 * " call bid value is matched with corresponding gampad call bid value");
								 */

								System.out.println(j + " Occurance of Amazon call bid value: " + listOf_b_Params.get(j)
										+ " is matched with " + i + " Occurance of corresponding " + sheetName
										+ " gampad call " + cust_param + " value: " + customParamsList.get(i));
								logStep(j + " Occurance of Amazon call bid value: " + listOf_b_Params.get(j)
										+ " is matched with " + i + " Occurance of corresponding " + sheetName
										+ " gampad call " + cust_param + " value: " + customParamsList.get(i));
								// System.out.println("amazon aax " + sheetName + " call bid value validation is
								// successful");
								// logStep("amazon aax " + sheetName + " call bid value vaidation is
								// successful");
								// break;

							} else {
								if (customParamsList.get(i).equalsIgnoreCase("-1")) {
									System.out.println(i + " Occurance of corresponding " + sheetName + " gampad call: "
											+ feedCall + " not having parameter " + cust_param);
									logStep(i + " Occurance of corresponding " + sheetName + " gampad call: " + feedCall
											+ " not having parameter " + cust_param);
									failCount++;
								} else {
									System.out.println(j + " Occurance of Amazon call " + cust_param + " value: "
											+ listOf_b_Params.get(j) + " is not matched with " + i
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i));
									logStep(j + " Occurance of Amazon call " + cust_param + " value: "
											+ listOf_b_Params.get(j) + " is not matched with " + i
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i));
									failCount++;
								}

							}
						}

					}

				} else if (sheetName.equalsIgnoreCase("Hourly1") || 
						sheetName.equalsIgnoreCase("Hourly2") || 
						sheetName.equalsIgnoreCase("Hourly3")){
					/**
					 * At present through automation, hourly big ad calls are generated at once or hourly 1 call once and hourly 2 & 3 are generated at once,
					 * hence separated logic written forr Hourly big ads...
					 */
					for (int i = 0; i < maxIterations; i++) {
						if (listOf_b_Params.get(i).equalsIgnoreCase("error")) {
							amazonBiddingFailCount++;
							if (listOf_b_Params.size() == 1) {
								System.out.println(
										"It looks that the only Occurance of Amazon Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
								logStep("It looks that the only Occurance of Amazon Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
							} else {
								System.out.println("It looks that: " + i
										+ " Occurance of Amazon Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
								logStep("It looks that: " + i
										+ " Occurance of Amazon Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
							}

						} else {
							amazonBiddingSuccessCount++;
							//if (listOf_b_Params.get(i).equalsIgnoreCase(customParamsList.get(i))) {
							if (listOf_b_Params.contains(customParamsList.get(i))) {
								successCount++;
								
								/*System.out.println(i + " Occurance of Amazon call bid value: " + listOf_b_Params.get(i)
										+ " is matched with " + i + " Occurance of corresponding " + sheetName
										+ " gampad call " + cust_param + " value: " + customParamsList.get(i));
								logStep(i + " Occurance of Amazon call bid value: " + listOf_b_Params.get(i)
										+ " is matched with " + i + " Occurance of corresponding " + sheetName
										+ " gampad call " + cust_param + " value: " + customParamsList.get(i));*/
								
								System.out.println(i + " Occurance of corresponding " + sheetName
								+ " gampad call " + cust_param + " value: " + customParamsList.get(i) + " exists in "+listOf_b_Params);
								
								logStep(i + " Occurance of corresponding " + sheetName
								+ " gampad call " + cust_param + " value: " + customParamsList.get(i) + " exists in "+listOf_b_Params);
								
							} else {
								if (customParamsList.get(i).equalsIgnoreCase("-1")) {
									System.out.println(i + " Occurance of corresponding " + sheetName + " gampad call: "
											+ feedCall + " not having parameter " + cust_param);
									logStep(i + " Occurance of corresponding " + sheetName + " gampad call: " + feedCall
											+ " not having parameter " + cust_param);
									failCount++;
								} else {
									/*System.out.println(i + " Occurance of Amazon call " + cust_param + " value: "
											+ listOf_b_Params.get(i) + " is not matched with " + i
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i));
									logStep(i + " Occurance of Amazon call " + cust_param + " value: "
											+ listOf_b_Params.get(i) + " is not matched with " + i
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i));*/
									System.out.println(i+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i) +" not exists in "+listOf_b_Params);
									logStep(i+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i) +" not exists in "+listOf_b_Params);
									failCount++;
								}

							}
						}

					}
				} else {

					for (int i = 0; i < maxIterations; i++) {
						if (listOf_b_Params.get(i).equalsIgnoreCase("error")) {
							amazonBiddingFailCount++;
							if (listOf_b_Params.size() == 1) {
								System.out.println(
										"It looks that the only Occurance of Amazon Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
								logStep("It looks that the only Occurance of Amazon Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
							} else {
								System.out.println("It looks that: " + i
										+ " Occurance of Amazon Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
								logStep("It looks that: " + i
										+ " Occurance of Amazon Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
							}

						} else {
							amazonBiddingSuccessCount++;
							if (listOf_b_Params.get(i).equalsIgnoreCase(customParamsList.get(i))) {
								successCount++;
								/*
								 * System.out.println("amazon aax " + sheetName +
								 * " call bid value is matched with corresponding gampad call bid value");
								 * logStep("amazon aax " + sheetName +
								 * " call bid value is matched with corresponding gampad call bid value");
								 */

								System.out.println(i + " Occurance of Amazon call bid value: " + listOf_b_Params.get(i)
										+ " is matched with " + i + " Occurance of corresponding " + sheetName
										+ " gampad call " + cust_param + " value: " + customParamsList.get(i));
								logStep(i + " Occurance of Amazon call bid value: " + listOf_b_Params.get(i)
										+ " is matched with " + i + " Occurance of corresponding " + sheetName
										+ " gampad call " + cust_param + " value: " + customParamsList.get(i));
								// System.out.println("amazon aax " + sheetName + " call bid value validation is
								// successful");
								// logStep("amazon aax " + sheetName + " call bid value vaidation is
								// successful");
								// break;

							} else {
								if (customParamsList.get(i).equalsIgnoreCase("-1")) {
									System.out.println(i + " Occurance of corresponding " + sheetName + " gampad call: "
											+ feedCall + " not having parameter " + cust_param);
									logStep(i + " Occurance of corresponding " + sheetName + " gampad call: " + feedCall
											+ " not having parameter " + cust_param);
									failCount++;
								} else {
									System.out.println(i + " Occurance of Amazon call " + cust_param + " value: "
											+ listOf_b_Params.get(i) + " is not matched with " + i
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i));
									logStep(i + " Occurance of Amazon call " + cust_param + " value: "
											+ listOf_b_Params.get(i) + " is not matched with " + i
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i));
									failCount++;
								}

							}
						}

					}
				}

			}

		}

		if (amazonBiddingFailCount > amazonBiddingSuccessCount) {

			if (maxIterations == 1) {
				if (failCount == 0) {
					System.out.println(
							"It looks that only Occurance of gampad call doesn't have amazon bid parameter due to corresponding instance of amazon bidding unsucecssful");
					logStep("It looks that only Occurance of gampad call doesn't have amazon bid parameter due to corresponding instance of amazon bidding unsucecssful");
				} else {
					System.out
							.println("It looks that the only Occurance of gampad call amazon bid validation is failed");
					logStep("It looks that the only Occurance of gampad call amazon bid validation is failed");
					Assert.fail("It looks that the only Occurance of gampad call amazon bid validation is failed");
				}

			} else {
				System.out.println("Morethan 50% Of Amazon call bidding is not successful for " + sheetName
						+ " aax call " + " ,refer to charles session file for more details ");
				logStep("Morethan 50% Of Amazon call bidding is not successful for " + sheetName + " aax call "
						+ " ,refer to charles session file for more details ");
				System.out.println("Amazon call bid validation with " + sheetName + " gampad call is failed");
				logStep("Amazon call bid validation with " + sheetName + " gampad call is failed");
				Assert.fail("Morethan 50% Of Amazon call bidding is not successful for " + sheetName + " aax call "
						+ " ,refer to charles session file for more details ");
			}

		} else if (failCount > successCount) {
			if (maxIterations == 1) {
				System.out.println("It looks that the only Occurance of gampad call amazon bid validation is failed");
				logStep("It looks that the only Occurance of gampad call amazon bid validation is failed");
				Assert.fail("It looks that the only Occurance of gampad call amazon bid validation is failed");
			} else {
				System.out.println("Morethan 50% Of Amazon call bid values  not matched with corresponding " + sheetName
						+ " gampad call " + cust_param + " values");
				logStep("Morethan 50% Of Amazon call bid values  not matched with corresponding " + sheetName
						+ " gampad call " + cust_param + " values");
				System.out.println("Amazon call bid values validation with " + sheetName + " gampad call is failed");
				logStep("Amazon call bid values validation with " + sheetName + " gampad call is failed");
				Assert.fail("Morethan 50% Of Amazon call bid values  not matched with corresponding " + sheetName
						+ " gampad call " + cust_param + " values");
			}

		}
	}
	
	/**
	 * This retrives amazon bid values from non preload aax calls and gampad calls of
	 * corresponding add calls and verifies any one matching.
	 * @param excelName
	 * @param sheetName
	 * @param clearList
	 * @param isNonPreload
	 * @throws Exception
	 */
	public static void validate_aax_bid_value_with_gampad_bid_value(String excelName, String sheetName,
			boolean clearList, boolean isNonPreload) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String slotID = ReadExcelValues.data[21][Cap];
		// String feedCall = readExcelValues.data[18][Cap];
		String feedCall = null;
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		if (sheetName.equalsIgnoreCase("PreRollVideo")) {
			feedCall = videoIUValue;
		} else if (sheetName.equalsIgnoreCase("News(details)")) {
			feedCall = return_iu_value_of_Feedcall(excelName, sheetName);
		} else {
			feedCall = ReadExcelValues.data[18][Cap];
		}

		boolean testpass = false;
		int failCount = 0;
		int successCount = 0;
		int amazonBiddingFailCount = 0;
		int amazonBiddingSuccessCount = 0;
		int maxIterations = 0;
		String cust_param = "amzn_b";

		if (sheetName.contains("PreRollVideo")) {
			cust_param = "amzn_vid";
		}

		get_amazon_bid_values_from_aaxCalls(slotID, clearList);

		if (aaxcallsSize == 0) {
			System.out.println("amazon aax " + sheetName
					+ " call is not generated in current session, so skipping the bid value verification");
			logStep("amazon aax " + sheetName
					+ " call is not generated in current session, so skipping the bid value verification");

		} else if (aaxbidErrorCount == aaxcallsResponseSize) {
			System.out.println("amazon aax " + sheetName
					+ " call response contains error i.e. bidding is not happened in current session, so skipping the bid value verification");
			logStep("amazon aax " + sheetName
					+ " call response contains error i.e. bidding is not happened in current session, so skipping the bid value verification");

		} else if (nextGenIMadDisplayed && sheetName.equalsIgnoreCase("Pulltorefresh")) {
			/*
			 * There may be chances that gampad call might not generated.. for ex: when IM
			 * ad displayed on home screen, then homescreen today call doesnt generate
			 * 
			 */
			System.out.println("Since IM Ad displayed on App Launch, Homescreen call bid id validation is skipped");
			logStep("Since IM Ad displayed on App Launch, Homescreen call bid id validation is skipped");
		} else {
			get_amazon_bid_values_from_gampadCalls(excelName, sheetName, feedCall, cust_param);
			/*
			 * below checks whether the gampad call exists or not before validating for
			 * amazon bid value..
			 */
			if (!isaaxgampadcallexists) {
				System.out.println("Ad Call :" + feedCall + " not found in charles session, hence Custom Parameter: "
						+ cust_param + " validation is failed");
				logStep("Ad Call :" + feedCall + " not found in charles session, hence Custom Parameter: " + cust_param
						+ " validation is failed");
				Assert.fail("Ad Call :" + feedCall + " not found in charles session, hence Custom Parameter: "
						+ cust_param + " validation is failed");
			} else if (customParamsList.size() == 0) {
				System.out.println("Ad Call :" + feedCall + " not contains the Custom Parameter: " + cust_param
						+ ", hence Custom Parameter: " + cust_param + " validation is failed");
				logStep("Ad Call :" + feedCall + " not contains the Custom Parameter: " + cust_param
						+ ", hence Custom Parameter: " + cust_param + " validation is failed");
				Assert.fail("Ad Call :" + feedCall + " not contains the Custom Parameter: " + cust_param
						+ ", hence Custom Parameter: " + cust_param + " validation is failed");
			} else {

				if (listOf_b_Params.size() > customParamsList.size()) {
					maxIterations = customParamsList.size();
				} else {
					maxIterations = listOf_b_Params.size();
				}
				if (isNonPreload) {
					if (customParamsList.size() == 1) {
						System.out.println(
								sheetName+" is non preload aax call and it requires morethan one gampad call for amazon bid validation hence failing the case, verify manually");
						logStep(sheetName+" is non preload aax call and it requires morethan one gampad call for amazon bid validation hence failing the case, verify manually");
						Assert.fail(
								sheetName+" is non preload aax call and it requires morethan one gampad call for amazon bid validation hence failing the case, verify manually");
					}
				}
				if (sheetName.equalsIgnoreCase("Health(goRun)") || sheetName.equalsIgnoreCase("Health(boatAndBeach)")) {
					int iterations = 0;
					int index = 0;
					if (maxIterations == 1) {
						iterations = 2;
						
					} else {
						iterations = maxIterations;
					}
					
					if (maxIterations%2 !=0 ) {
						maxIterations++;
					}
					//for (int i = 0; i < iterations / 2; i++) {
					for (int i = 0; i < maxIterations / 2; i++) {
						if (listOf_b_Params.get(i).equalsIgnoreCase("error")) {
							amazonBiddingFailCount++;
							if (listOf_b_Params.size() == 1) {
								System.out.println(
										"It looks that the only Occurance of Amazon Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
								logStep("It looks that the only Occurance of Amazon Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
							} else {
								System.out.println("It looks that: " + i
										+ " Occurance of Amazon Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
								logStep("It looks that: " + i
										+ " Occurance of Amazon Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
							}

						} else {
							amazonBiddingSuccessCount++;
							index++;
							if (listOf_b_Params.get(i).equalsIgnoreCase(customParamsList.get(i + index+ 2))) {
								successCount++;
								/*
								 * System.out.println("amazon aax " + sheetName +
								 * " call bid value is matched with corresponding gampad call bid value");
								 * logStep("amazon aax " + sheetName +
								 * " call bid value is matched with corresponding gampad call bid value");
								 */

								System.out.println(i + " Occurance of Amazon call bid value: " + listOf_b_Params.get(i)
										+ " is matched with " + (i + 1) + " Occurance of corresponding " + sheetName
										+ " gampad call " + cust_param + " value: " + customParamsList.get(i + index+ 2));
								logStep(i + " Occurance of Amazon call bid value: " + listOf_b_Params.get(i)
										+ " is matched with " + (i + 1) + " Occurance of corresponding " + sheetName
										+ " gampad call " + cust_param + " value: " + customParamsList.get(i + index+ 2));
								// System.out.println("amazon aax " + sheetName + " call bid value validation is
								// successful");
								// logStep("amazon aax " + sheetName + " call bid value vaidation is
								// successful");
								// break;

							} else {
								if (customParamsList.get(i + index+ 2).equalsIgnoreCase("-1")) {
									System.out.println(i + 1 + " Occurance of corresponding " + sheetName
											+ " gampad call: " + feedCall + " not having parameter " + cust_param);
									logStep(i + 1 + " Occurance of corresponding " + sheetName + " gampad call: "
											+ feedCall + " not having parameter " + cust_param);
									failCount++;
								} else {
									System.out.println(i + " Occurance of Amazon call " + cust_param + " value: "
											+ listOf_b_Params.get(i) + " is not matched with " + i + 1
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i + index+ 2));
									logStep(i + " Occurance of Amazon call " + cust_param + " value: "
											+ listOf_b_Params.get(i) + " is not matched with " + i + 1
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i + index+ 2));
									failCount++;
								}

							}

						}

					}

				} else if (sheetName.equalsIgnoreCase("Health(coldAndFluArticles)")
						|| sheetName.equalsIgnoreCase("Health(allergyArticles)")) {
					
				/*	int j;
					if (aaxHealthArticlesCheckCount == 0) {
						j = 0;
					} else {
						j = aaxHealthArticlesCheckCount;
					}
					for (int i = 0; i < maxIterations ; i++, j++) {
						i++;
						if (!(j < listOf_b_Params.size())) {
							System.out.println(
									"Articles Bid id's have been used already, nothing more leftout to validate for: "+sheetName +"verify manually");
							logStep("Articles Bid id's have been used already, nothing more leftout to validate for: "+sheetName +"verify manually");
							Assert.fail(
									"Articles Bid id's have been used already, nothing more leftout to validate for: "+sheetName +"verify manually");
						}
						aaxHealthArticlesCheckCount++;
						if (listOf_b_Params.get(j).equalsIgnoreCase("error")) {
							amazonBiddingFailCount++;
							if (listOf_b_Params.size() == 1) {
								System.out.println(
										"It looks that the only Occurance of Amazon Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
								logStep("It looks that the only Occurance of Amazon Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
							} else {
								System.out.println("It looks that: " + j
										+ " Occurance of Amazon Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
								logStep("It looks that: " + j
										+ " Occurance of Amazon Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
							}

						} else {
							amazonBiddingSuccessCount++;
							if (listOf_b_Params.get(j).equalsIgnoreCase(customParamsList.get(i))) {
								successCount++;
								
								System.out.println(j + " Occurance of Amazon call bid value: " + listOf_b_Params.get(j)
										+ " is matched with " + i + " Occurance of corresponding " + sheetName
										+ " gampad call " + cust_param + " value: " + customParamsList.get(i));
								logStep(j + " Occurance of Amazon call bid value: " + listOf_b_Params.get(j)
										+ " is matched with " + i + " Occurance of corresponding " + sheetName
										+ " gampad call " + cust_param + " value: " + customParamsList.get(i));
								// System.out.println("amazon aax " + sheetName + " call bid value validation is
								// successful");
								// logStep("amazon aax " + sheetName + " call bid value vaidation is
								// successful");
								// break;

							} else {
								if (customParamsList.get(i).equalsIgnoreCase("-1")) {
									System.out.println(i + " Occurance of corresponding " + sheetName + " gampad call: "
											+ feedCall + " not having parameter " + cust_param);
									logStep(i + " Occurance of corresponding " + sheetName + " gampad call: " + feedCall
											+ " not having parameter " + cust_param);
									failCount++;
								} else {
									System.out.println(j + " Occurance of Amazon call " + cust_param + " value: "
											+ listOf_b_Params.get(j) + " is not matched with " + i
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i));
									logStep(j + " Occurance of Amazon call " + cust_param + " value: "
											+ listOf_b_Params.get(j) + " is not matched with " + i
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i));
									failCount++;
								}

							}
						}

					}*/
					
					/**
					 * Since in a single session both the articles uses same bid id hence for time being commented above logic, and added below one
					 */
					for (int i = 0, j = 0; i < maxIterations && j < maxIterations; i++,j++) {
						if (isNonPreload) {
							i++;
						}
						if (listOf_b_Params.get(j).equalsIgnoreCase("error")) {
							amazonBiddingFailCount++;
							if (listOf_b_Params.size() == 1) {
								System.out.println(
										"It looks that the only Occurance of Amazon Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
								logStep("It looks that the only Occurance of Amazon Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
							} else {
								System.out.println("It looks that: " + j
										+ " Occurance of Amazon Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
								logStep("It looks that: " + j
										+ " Occurance of Amazon Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
							}

						} else {
							amazonBiddingSuccessCount++;
							//if (listOf_b_Params.get(i).equalsIgnoreCase(customParamsList.get(i))) {
							if (listOf_b_Params.contains(customParamsList.get(i))) {
								successCount++;
																
								System.out.println(i + " Occurance of corresponding " + sheetName
								+ " gampad call " + cust_param + " value: " + customParamsList.get(i) + " exists in "+listOf_b_Params);
								
								logStep(i + " Occurance of corresponding " + sheetName
								+ " gampad call " + cust_param + " value: " + customParamsList.get(i) + " exists in "+listOf_b_Params);
								
							} else {
								if (customParamsList.get(i).equalsIgnoreCase("-1")) {
									System.out.println(i + " Occurance of corresponding " + sheetName + " gampad call: "
											+ feedCall + " not having parameter " + cust_param);
									logStep(i + " Occurance of corresponding " + sheetName + " gampad call: " + feedCall
											+ " not having parameter " + cust_param);
									failCount++;
								} else {
									System.out.println(i+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i) +" not exists in "+listOf_b_Params);
									logStep(i+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i) +" not exists in "+listOf_b_Params);
									failCount++;
								}

							}
						}

					}

				} else {
					int preloadCheck =  0;
					for (int i = 0, j = 0; i < maxIterations && j < maxIterations; i++,j++) {
						if (isNonPreload && preloadCheck == 0) {
							//for non preload calls, gampad calls index to start from 1
							i=1;
							preloadCheck++;
						}
						if (listOf_b_Params.get(j).equalsIgnoreCase("error")) {
							amazonBiddingFailCount++;
							if (listOf_b_Params.size() == 1) {
								System.out.println(
										"It looks that the only Occurance of Amazon Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
								logStep("It looks that the only Occurance of Amazon Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
							} else {
								System.out.println("It looks that: " + j
										+ " Occurance of Amazon Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
								logStep("It looks that: " + j
										+ " Occurance of Amazon Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
							}

						} else {
							amazonBiddingSuccessCount++;
							if (listOf_b_Params.get(j).equalsIgnoreCase(customParamsList.get(i))) {
								successCount++;
								/*
								 * System.out.println("amazon aax " + sheetName +
								 * " call bid value is matched with corresponding gampad call bid value");
								 * logStep("amazon aax " + sheetName +
								 * " call bid value is matched with corresponding gampad call bid value");
								 */

								System.out.println(j + " Occurance of Amazon call bid value: " + listOf_b_Params.get(j)
										+ " is matched with " + i + " Occurance of corresponding " + sheetName
										+ " gampad call " + cust_param + " value: " + customParamsList.get(i));
								logStep(j + " Occurance of Amazon call bid value: " + listOf_b_Params.get(j)
										+ " is matched with " + i + " Occurance of corresponding " + sheetName
										+ " gampad call " + cust_param + " value: " + customParamsList.get(i));
								// System.out.println("amazon aax " + sheetName + " call bid value validation is
								// successful");
								// logStep("amazon aax " + sheetName + " call bid value vaidation is
								// successful");
								// break;

							} else {
								if (customParamsList.get(i).equalsIgnoreCase("-1")) {
									System.out.println(i + " Occurance of corresponding " + sheetName + " gampad call: "
											+ feedCall + " not having parameter " + cust_param);
									logStep(i + " Occurance of corresponding " + sheetName + " gampad call: " + feedCall
											+ " not having parameter " + cust_param);
									failCount++;
								} else {
									System.out.println(j + " Occurance of Amazon call " + cust_param + " value: "
											+ listOf_b_Params.get(j) + " is not matched with " + i
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i));
									logStep(j + " Occurance of Amazon call " + cust_param + " value: "
											+ listOf_b_Params.get(j) + " is not matched with " + i
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i));
									failCount++;
								}

							}
						}

					}
				}

			}

		}

		if (amazonBiddingFailCount > amazonBiddingSuccessCount) {

			if (maxIterations == 1) {
				if (failCount == 0) {
					System.out.println(
							"It looks that only Occurance of gampad call doesn't have amazon bid parameter due to corresponding instance of amazon bidding unsucecssful");
					logStep("It looks that only Occurance of gampad call doesn't have amazon bid parameter due to corresponding instance of amazon bidding unsucecssful");
				} else {
					System.out
							.println("It looks that the only Occurance of gampad call amazon bid validation is failed");
					logStep("It looks that the only Occurance of gampad call amazon bid validation is failed");
					Assert.fail("It looks that the only Occurance of gampad call amazon bid validation is failed");
				}

			} else {
				System.out.println("Morethan 50% Of Amazon call bidding is not successful for " + sheetName
						+ " aax call " + " ,refer to charles session file for more details ");
				logStep("Morethan 50% Of Amazon call bidding is not successful for " + sheetName + " aax call "
						+ " ,refer to charles session file for more details ");
				System.out.println("Amazon call bid validation with " + sheetName + " gampad call is failed");
				logStep("Amazon call bid validation with " + sheetName + " gampad call is failed");
				Assert.fail("Morethan 50% Of Amazon call bidding is not successful for " + sheetName + " aax call "
						+ " ,refer to charles session file for more details ");
			}

		} else if (failCount > successCount) {
			if (maxIterations == 1) {
				System.out.println("It looks that the only Occurance of gampad call amazon bid validation is failed");
				logStep("It looks that the only Occurance of gampad call amazon bid validation is failed");
				Assert.fail("It looks that the only Occurance of gampad call amazon bid validation is failed");
			} else {
				System.out.println("Morethan 50% Of Amazon call bid values  not matched with corresponding " + sheetName
						+ " gampad call " + cust_param + " values");
				logStep("Morethan 50% Of Amazon call bid values  not matched with corresponding " + sheetName
						+ " gampad call " + cust_param + " values");
				System.out.println("Amazon call bid values validation with " + sheetName + " gampad call is failed");
				logStep("Amazon call bid values validation with " + sheetName + " gampad call is failed");
				Assert.fail("Morethan 50% Of Amazon call bid values  not matched with corresponding " + sheetName
						+ " gampad call " + cust_param + " values");
			}

		}
	}

	/**
	 * Gets Number Of Occurances Of API call with given host and path
	 * @param host
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static int getNoOfOccurancesOfAPICallWithHostandPath(String host, String path) throws Exception {
		// readExcelValues.excelValues(excelName, sheetName);
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@host";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

// Getting custom_params amzn_b values
		List<String> customParamsList = new ArrayList<String>();
		
		int count = 0;
		// String iuId = null;

		boolean iuExists = false;
		for (String qry : getQueryList) {
			if (qry.contains(host)) {
				iuExists = true;
				break;
			}
		}
		boolean hflag = false;
		boolean pflag = false;
		boolean resflag = false;

		if (iuExists) {
			System.out.println(host + "  call is present");
			logStep(host + "  call is present");
			outerloop: for (int p = 0; p < nodeList.getLength(); p++) {
				// System.out.println("Total transactions: "+nodeList.getLength());
				if (nodeList.item(p) instanceof Node) {
					Node node = nodeList.item(p);
					if (node.hasChildNodes()) {
						NodeList nl = node.getChildNodes();
						for (int j = 0; j < nl.getLength(); j++) {
							// System.out.println("node1 length is: "+nl.getLength());
							Node innernode = nl.item(j);
							if (innernode != null) {
								// System.out.println("Innernode name is: "+innernode.getNodeName());
								if (innernode.getNodeName().equals("request")) {
									//System.out.println("..............request.....................");
									hflag = false;
									pflag = false;
									if (innernode.hasChildNodes()) {
										NodeList n2 = innernode.getChildNodes();
										for (int k = 0; k < n2.getLength(); k++) {
											// System.out.println("node2 length is: "+n2.getLength());
											Node innernode2 = n2.item(k);
											if (innernode2 != null) {
												// System.out.println("Innernode2 name is: "+innernode2.getNodeName());
												if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
													Element eElement = (Element) innernode2;
													// System.out.println("Innernode2 element name is:
													// "+eElement.getNodeName());
													if (eElement.getNodeName().equals("headers")) {
														if (innernode2.hasChildNodes()) {
															NodeList n3 = innernode2.getChildNodes();
															for (int q = 0; q < n3.getLength(); q++) {
																// System.out.println("node3 length is:
																// "+n3.getLength());
																Node innernode3 = n3.item(q);
																if (innernode3 != null) {
																	// System.out.println("Innernode3 name is:
																	// "+innernode3.getNodeName());
																	if (innernode3.getNodeType() == Node.ELEMENT_NODE) {
																		Element eElement1 = (Element) innernode3;
																		
																		// System.out.println("Innernode3 element name
																		// is: "+eElement1.getNodeName());
																		if (eElement1.getNodeName().equals("header")) {
																			String content = eElement1.getTextContent();
																			//System.out.println("request body header..."+content);

																			if (content.contains(host)) {
																				hflag = true;
																				//System.out.println("request body host found "+ content);
																				
																			} else if (content.contains(path)) {
																				pflag = true;
																				//System.out.println("request body path found "+ content);
																				
																			}
																		}

																		// this condition especially for android since
																		// its file has path value under first-line
																		// element
																		if (eElement1.getNodeName()
																				.equals("first-line")) {
																			String content = eElement1.getTextContent();
																			//System.out.println("request body first line "+content);

																			if (content.contains(path)) {
																				pflag = true;
																				//System.out.println("request body path found " +  content);
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}

								/*
								 * if (flag) { // System.out.println("Exiting after found true "); //
								 * System.out.println("checking innernode name is: "+innernode.getNodeName());
								 * if (innernode.getNodeName().equals("response")) { //
								 * System.out.println(innernode.getNodeName()); if (innernode.hasChildNodes()) {
								 * NodeList n2 = innernode.getChildNodes(); for (int k = 0; k < n2.getLength();
								 * k++) { Node innernode2 = n2.item(k); if (innernode2 != null) { if
								 * (innernode2.getNodeType() == Node.ELEMENT_NODE) { Element eElement =
								 * (Element) innernode2; if (eElement.getNodeName().equals("body")) { String
								 * content = eElement.getTextContent(); //
								 * System.out.println("response body "+content); if
								 * (content.contains(readExcelValues.data[13][Cap])) { resflag = true; break
								 * outerloop;
								 * 
								 * } } } } } } }
								 * 
								 * }
								 */
								if (hflag && pflag) {
									resflag = true;
									//break outerloop;
									count++;
									hflag = false;
									pflag = false;
								}
							}
						}
					}
				}
				// flag = false;
			}

		} else {
			System.out.println(host + " ad call is not present");
			logStep(host + " ad call is not present");

		}

		return count;
	}
	
	/**
	 * Verifies API call with given host and path
	 * @param host
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyAPICallWithHostandPath(String host, String path) throws Exception {
		// readExcelValues.excelValues(excelName, sheetName);
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@host";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

// Getting custom_params amzn_b values
		List<String> customParamsList = new ArrayList<String>();

		// String iuId = null;

		boolean iuExists = false;
		for (String qry : getQueryList) {
			if (qry.contains(host)) {
				iuExists = true;
				break;
			}
		}
		boolean hflag = false;
		boolean pflag = false;
		boolean resflag = false;

		if (iuExists) {
			System.out.println(host + "  call is present");
			logStep(host + "  call is present");
			outerloop: for (int p = 0; p < nodeList.getLength(); p++) {
				// System.out.println("Total transactions: "+nodeList.getLength());
				if (nodeList.item(p) instanceof Node) {
					Node node = nodeList.item(p);
					if (node.hasChildNodes()) {
						NodeList nl = node.getChildNodes();
						for (int j = 0; j < nl.getLength(); j++) {
							// System.out.println("node1 length is: "+nl.getLength());
							Node innernode = nl.item(j);
							if (innernode != null) {
								// System.out.println("Innernode name is: "+innernode.getNodeName());
								if (innernode.getNodeName().equals("request")) {
									//System.out.println("...................................");
									hflag = false;
									pflag = false;
									if (innernode.hasChildNodes()) {
										NodeList n2 = innernode.getChildNodes();
										for (int k = 0; k < n2.getLength(); k++) {
											// System.out.println("node2 length is: "+n2.getLength());
											Node innernode2 = n2.item(k);
											if (innernode2 != null) {
												// System.out.println("Innernode2 name is: "+innernode2.getNodeName());
												if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
													Element eElement = (Element) innernode2;
													// System.out.println("Innernode2 element name is:
													// "+eElement.getNodeName());
													if (eElement.getNodeName().equals("headers")) {
														if (innernode2.hasChildNodes()) {
															NodeList n3 = innernode2.getChildNodes();
															for (int q = 0; q < n3.getLength(); q++) {
																// System.out.println("node3 length is:
																// "+n3.getLength());
																Node innernode3 = n3.item(q);
																if (innernode3 != null) {
																	// System.out.println("Innernode3 name is:
																	// "+innernode3.getNodeName());
																	if (innernode3.getNodeType() == Node.ELEMENT_NODE) {
																		Element eElement1 = (Element) innernode3;
																		
																		// System.out.println("Innernode3 element name
																		// is: "+eElement1.getNodeName());
																		if (eElement1.getNodeName().equals("header")) {
																			String content = eElement1.getTextContent();
																			//System.out.println("request body "+content);

																			if (content.contains(host)) {
																				hflag = true;
																				//System.out.println("request body found "+ content);
																				
																			} else if (content.contains(path)) {
																				pflag = true;
																				//System.out.println("request body found "+ content);
																				
																			}
																		}

																		// this condition especially for android since
																		// its file has path value under first-line
																		// element
																		if (eElement1.getNodeName()
																				.equals("first-line")) {
																			String content = eElement1.getTextContent();
																			// System.out.println("request body
																			// "+content);

																			if (content.contains(path)) {
																				pflag = true;
																				// System.out.println("request body
																				// found "
																				// +  content);
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}

								/*
								 * if (flag) { // System.out.println("Exiting after found true "); //
								 * System.out.println("checking innernode name is: "+innernode.getNodeName());
								 * if (innernode.getNodeName().equals("response")) { //
								 * System.out.println(innernode.getNodeName()); if (innernode.hasChildNodes()) {
								 * NodeList n2 = innernode.getChildNodes(); for (int k = 0; k < n2.getLength();
								 * k++) { Node innernode2 = n2.item(k); if (innernode2 != null) { if
								 * (innernode2.getNodeType() == Node.ELEMENT_NODE) { Element eElement =
								 * (Element) innernode2; if (eElement.getNodeName().equals("body")) { String
								 * content = eElement.getTextContent(); //
								 * System.out.println("response body "+content); if
								 * (content.contains(readExcelValues.data[13][Cap])) { resflag = true; break
								 * outerloop;
								 * 
								 * } } } } } } }
								 * 
								 * }
								 */
								if (hflag && pflag) {
									resflag = true;
									break outerloop;
								}
							}
						}
					}
				}
				// flag = false;
			}

		} else {
			System.out.println(host + " ad call is not present");
			logStep(host + " ad call is not present");

		}

		return resflag;
	}
	
	/**
	 * Verifies API call with given host and path1 and path2
	 * @param host
	 * @param path1
	 * @param path2
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyAPICallWithHostandPath(String host, String path1, String path2) throws Exception {
		// readExcelValues.excelValues(excelName, sheetName);
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@host";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

// Getting custom_params amzn_b values
		List<String> customParamsList = new ArrayList<String>();

		// String iuId = null;

		boolean iuExists = false;
		for (String qry : getQueryList) {
			if (qry.contains(host)) {
				iuExists = true;
				break;
			}
		}
		boolean hflag = false;
		boolean pflag = false;
		boolean p1flag = false;
		boolean resflag = false;

		if (iuExists) {
			System.out.println(host + "  call is present");
			logStep(host + "  call is present");
			outerloop: for (int p = 0; p < nodeList.getLength(); p++) {
				// System.out.println("Total transactions: "+nodeList.getLength());
				if (nodeList.item(p) instanceof Node) {
					Node node = nodeList.item(p);
					if (node.hasChildNodes()) {
						NodeList nl = node.getChildNodes();
						for (int j = 0; j < nl.getLength(); j++) {
							// System.out.println("node1 length is: "+nl.getLength());
							Node innernode = nl.item(j);
							if (innernode != null) {
								// System.out.println("Innernode name is: "+innernode.getNodeName());
								if (innernode.getNodeName().equals("request")) {
									//System.out.println("...................................");
									hflag = false;
									pflag = false;
									if (innernode.hasChildNodes()) {
										NodeList n2 = innernode.getChildNodes();
										for (int k = 0; k < n2.getLength(); k++) {
											// System.out.println("node2 length is: "+n2.getLength());
											Node innernode2 = n2.item(k);
											if (innernode2 != null) {
												// System.out.println("Innernode2 name is: "+innernode2.getNodeName());
												if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
													Element eElement = (Element) innernode2;
													// System.out.println("Innernode2 element name is:
													// "+eElement.getNodeName());
													if (eElement.getNodeName().equals("headers")) {
														if (innernode2.hasChildNodes()) {
															NodeList n3 = innernode2.getChildNodes();
															for (int q = 0; q < n3.getLength(); q++) {
																// System.out.println("node3 length is:
																// "+n3.getLength());
																Node innernode3 = n3.item(q);
																if (innernode3 != null) {
																	// System.out.println("Innernode3 name is:
																	// "+innernode3.getNodeName());
																	if (innernode3.getNodeType() == Node.ELEMENT_NODE) {
																		Element eElement1 = (Element) innernode3;
																		
																		// System.out.println("Innernode3 element name
																		// is: "+eElement1.getNodeName());
																		if (eElement1.getNodeName().equals("header")) {
																			String content = eElement1.getTextContent();
																			//System.out.println("request body "+content);

																			if (content.contains(host)) {
																				hflag = true;
																				//System.out.println("request body found "+ content);
																				
																			} else if (content.contains(path1)) {
																				if (content.contains(path2)) {
																					pflag = true;
																				}
																				//System.out.println("request body found "+ content);
																				
																			}
																		}

																		// this condition especially for android since
																		// its file has path value under first-line
																		// element
																		if (eElement1.getNodeName()
																				.equals("first-line")) {
																			String content = eElement1.getTextContent();
																			// System.out.println("request body
																			// "+content);

																			if (content.contains(path1)) {
																				if (content.contains(path2)) {
																					pflag = true;
																				}
																				
																				// System.out.println("request body
																				// found "
																				// +  content);
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}

								/*
								 * if (flag) { // System.out.println("Exiting after found true "); //
								 * System.out.println("checking innernode name is: "+innernode.getNodeName());
								 * if (innernode.getNodeName().equals("response")) { //
								 * System.out.println(innernode.getNodeName()); if (innernode.hasChildNodes()) {
								 * NodeList n2 = innernode.getChildNodes(); for (int k = 0; k < n2.getLength();
								 * k++) { Node innernode2 = n2.item(k); if (innernode2 != null) { if
								 * (innernode2.getNodeType() == Node.ELEMENT_NODE) { Element eElement =
								 * (Element) innernode2; if (eElement.getNodeName().equals("body")) { String
								 * content = eElement.getTextContent(); //
								 * System.out.println("response body "+content); if
								 * (content.contains(readExcelValues.data[13][Cap])) { resflag = true; break
								 * outerloop;
								 * 
								 * } } } } } } }
								 * 
								 * }
								 */
								if (hflag && pflag) {
									resflag = true;
									break outerloop;
								}
							}
						}
					}
				}
				// flag = false;
			}

		} else {
			System.out.println(host + " ad call is not present");
			logStep(host + " ad call is not present");

		}

		return resflag;
	}
	
	/**
	 * Verifies API call with given host
	 * @param host
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyAPICallWithHost(String host) throws Exception {
		// readExcelValues.excelValues(excelName, sheetName);
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@host";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

// Getting custom_params amzn_b values
		List<String> customParamsList = new ArrayList<String>();

		// String iuId = null;

		boolean iuExists = false;
		for (String qry : getQueryList) {
			if (qry.contains(host)) {
				iuExists = true;
				break;
			}
		}
		boolean hflag = false;
		boolean pflag = false;
		boolean resflag = false;

		if (iuExists) {
			System.out.println(host + "  call is present");
			logStep(host + "  call is present");
		} else {
			System.out.println(host + " ad call is not present");
			logStep(host + " ad call is not present");

		}

		return iuExists;
	}
	
		
	public static boolean getTheQueryParamValueOfGivenHost(String host) throws Exception {
		// readExcelValues.excelValues(excelName, sheetName);
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@host";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

// Getting custom_params amzn_b values
		List<String> customParamsList = new ArrayList<String>();

		// String iuId = null;

		boolean iuExists = false;
		for (String qry : getQueryList) {
			if (qry.contains(host)) {
				iuExists = true;
				
	//			getQueryParamValue(String qryValue, String queryParam)
				
				
				break;
			}
		}
		boolean hflag = false;
		boolean pflag = false;
		boolean resflag = false;

		if (iuExists) {
			System.out.println(host + "  call is present");
			logStep(host + "  call is present");
		} else {
			System.out.println(host + " ad call is not present");
			logStep(host + " ad call is not present");

		}

		return iuExists;
	}
	
	public static String getTheQueryParamValueOfGivenHostAndPath(String host, String path, String queryParam) throws Exception {
		// readExcelValues.excelValues(excelName, sheetName);
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@host";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

// Getting custom_params amzn_b values
		List<String> customParamsList = new ArrayList<String>();

		// String iuId = null;

		boolean iuExists = false;
		for (String qry : getQueryList) {
			if (qry.contains(host)) {
				iuExists = true;
				
	//			getQueryParamValue(String qryValue, String queryParam)
				
				
				break;
			}
		}
		
		boolean resflag = false;
		String queryParamValue = null;
		
		if (iuExists) {
			System.out.println(host + "  call is present");
			logStep(host + "  call is present");
			outerloop: for (int p = 0; p < nodeList.getLength(); p++) {
				// System.out.println("Total transactions: "+nodeList.getLength());
				if (nodeList.item(p) instanceof Node) {
					Node node = nodeList.item(p);
					String currentNode = node.getNodeName();
					NamedNodeMap node1 = node.getAttributes();
					Node hostNameNode = node1.getNamedItem("host");
					String hostName = hostNameNode.getNodeValue();
					//System.out.println("CCurrent Node Name is: "+hostNameNode.getNodeValue());
					if(hostName.equalsIgnoreCase(host)) {
						Node pathNameNode = node1.getNamedItem("path");
						String pathName = pathNameNode.getNodeValue();
						if(pathName.equalsIgnoreCase(path)) {
							Node queryNode = node1.getNamedItem("query");
							String queryString = queryNode.getNodeValue();
							queryParamValue = getQueryParamValue(queryString, queryParam);
							resflag = true;
							break;
							
						}
					}
								
				}
				// flag = false;
			}

		} else {
			System.out.println(host + " call is not present");
			logStep(host + " call is not present");

		}

		return queryParamValue;
	}
	
	/**
	 * Verifies PreRoll Video Beacon in Charles Session
	 * @param host
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyPreRollVideoBeaconValueInCharlesSession(String host, String path, String beaconParam, String beaconValue) throws Exception {
		// readExcelValues.excelValues(excelName, sheetName);
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@host";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

// Getting custom_params amzn_b values
		List<String> customParamsList = new ArrayList<String>();

		// String iuId = null;

		boolean iuExists = false;
		for (String qry : getQueryList) {
			if (qry.contains(host)) {
				iuExists = true;
				break;
			}
		}
		boolean hflag = false;
		boolean pflag = false;
		boolean resflag = false;
		String needBeaconSessionId = null;
		int iteration = 0;

		if (iuExists) {
			System.out.println(host + "  call is present");
			logStep(host + "  call is present");
			outerloop: for (int p = 0; p < nodeList.getLength(); p++) {
				// System.out.println("Total transactions: "+nodeList.getLength());
				if (nodeList.item(p) instanceof Node) {
					Node node = nodeList.item(p);
					String currentNode = node.getNodeName();
					NamedNodeMap node1 = node.getAttributes();
					Node hostNameNode = node1.getNamedItem("host");
					String hostName = hostNameNode.getNodeValue();
					//System.out.println("CCurrent Node Name is: "+hostNameNode.getNodeValue());
					if(hostName.equalsIgnoreCase(host)) {
						Node pathNameNode = node1.getNamedItem("path");
						String pathName = pathNameNode.getNodeValue();
						if(pathName.equalsIgnoreCase(path)) {
							Node queryNode = node1.getNamedItem("query");
							String queryString = queryNode.getNodeValue();
							String adBeaconSessionId = getQueryParamValue(queryString, "adSessionId");
							if(adBeaconSessionId.equalsIgnoreCase("")) {
								continue;
							}
							iteration++;
							System.out.println("Beacon Session id: "+adBeaconSessionId);
							if(iteration == 1) {
								needBeaconSessionId  = adBeaconSessionId;
							}
							String adBeaconType = getQueryParamValue(queryString, beaconParam);
							
							if (adBeaconType.equalsIgnoreCase(beaconValue) && adBeaconSessionId.equalsIgnoreCase(needBeaconSessionId) ) {
								System.out.println("beacon value found");
								resflag = true;
								break;
							}
								
							
						}
					}
								
				}
				// flag = false;
			}

		} else {
			System.out.println(host + " call is not present");
			logStep(host + " call is not present");

		}

		return resflag;
	}

	/**
	 * Wait for minute
	 */
	public static void waitForMinute() {
		long start = System.nanoTime();
		// ...
		long finish = System.nanoTime();
		long timeElapsed = finish - start;

		// long start = System.currentTimeMillis();
		// ...
		// long finish = System.currentTimeMillis();
		// long timeElapsed = finish - start;

		// float sec = (end - start) / 1000F; System.out.println(sec + " seconds");
	}

	/**
	 * Verifies Interstitial ad on given card
	 * @param excelName
	 * @param sheetName
	 * @param cardName
	 * @return
	 * @throws Exception
	 */
	public static String verifyInterstitialAd(String excelName, String sheetName, String cardName,
			String interStitialType) throws Exception {
		/*HomeNavTab hmTab = new HomeNavTab(Ad);
		HourlyNavTab hrTab = new HourlyNavTab(Ad);
		DailyNavTab dTab = new DailyNavTab(Ad);
		RadarNavTab rTab = new RadarNavTab(Ad);
		VideoNavTab vTab = new VideoNavTab(Ad);
		PlanningCardScreen pScreen = new PlanningCardScreen(Ad);
		VideoCardScreen vScreen = new VideoCardScreen(Ad);
		TodayCardScreen tScreen = new TodayCardScreen(Ad);
		AirQualityCardScreen aqScreen = new AirQualityCardScreen(Ad);
		RadarCardScreen rScreen = new RadarCardScreen(Ad);
		DailyCardScreen dScreen = new DailyCardScreen(Ad);
		NewsCardScreen nScreen = new NewsCardScreen(Ad);*/
		Object hmTab = null;
		Object hrTab = null;
		Object dTab = null;
		Object rTab = null;
		Object vTab = null;
		Object pScreen = null;
		Object hrScreen = null;
		Object vScreen = null;
		Object tScreen = null;
		Object aqScreen = null;
		Object rScreen = null;
		Object dScreen = null;
		Object nScreen = null;
		
		if (Ad instanceof IOSDriver<?>) {
			hmTab = new HomeNavTab(Ad);
			hrTab = new HourlyNavTab(Ad);
			dTab = new DailyNavTab(Ad);
			rTab = new RadarNavTab(Ad);
			vTab = new VideoNavTab(Ad);
			pScreen = new PlanningCardScreen(Ad);
			vScreen = new VideoCardScreen(Ad);
			tScreen = new TodayCardScreen(Ad);
			aqScreen = new AirQualityCardScreen(Ad);
			rScreen = new RadarCardScreen(Ad);
			dScreen = new DailyCardScreen(Ad);
			nScreen = new NewsCardScreen(Ad);
		} else if (Ad instanceof AndroidDriver<?>) {
			hmTab = new AndroidHomeNavTab(Ad);
			hrTab = new AndroidHourlyNavTab(Ad);
			dTab = new AndroidDailyNavTab(Ad);
			rTab = new AndroidRadarNavTab(Ad);
			vTab = new AndroidVideoNavTab(Ad);
			hrScreen = new AndroidHourlyCardScreen(Ad);
			vScreen = new AndroidVideoCardScreen(Ad);
			tScreen = new AndroidTodayCardScreen(Ad);
			aqScreen = new AndroidAirQualityCardScreen(Ad);
			rScreen = new AndroidRadarCardScreen(Ad);
			dScreen = new AndroidDailyCardScreen(Ad);
			nScreen = new AndroidNewsCardScreen(Ad);
		}
		/*
		 * Maps: exit Video: Exit Hourly : Entry Daily : Exit
		 */
		Calendar cal = null;
		Date date = null;
		DateFormat dateFormat = null;
		String formattedDate = null;
		String returnMessage = null;
		MobileElement elementToClick = null;

		TestBase.waitForMilliSeconds(2000);
		Functions.archive_folder("Charles");
		CharlesProxy.proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		if (Utils.isInterStitialAdCalExists(excelName, sheetName)) {

			if (Utils.isInterstitialCall_hasResponse(excelName, sheetName)) {
				TestBase.waitForMilliSeconds(2000);
				if (!cardName.contains("Tab")) {
					if (cardName.contains("banner")) {
						// navigate to Planning Card
						// Utils.navigateToPlanningCard();
						Utils.navigateTofeedCard("planning-containerView", false, false);
					} else {
						// navigate to feed card
						
						if (Ad instanceof IOSDriver<?>) {
							Utils.navigateTofeedCard(cardName, false, false);
						} else if (Ad instanceof AndroidDriver<?>) {
							if (cardName.equalsIgnoreCase("hourly")) {
								((AndroidHourlyCardScreen) hrScreen).scrollToHourlyCard();
							} else if (cardName.equalsIgnoreCase("daily")) {
								((AndroidDailyCardScreen) dScreen).scrollToDailyCard();
							} else if (cardName.equalsIgnoreCase("today")) {
								((AndroidTodayCardScreen) tScreen).scrollToTodayCard();
							}
						}
					}

				}
				/**
				 * following condition is added for Map Card.. When Rain card is displayed, then Map card doesn't appear
				 * Hence need to skip the interstitial validation that time
				 * 
				 */
				if (cardName.equalsIgnoreCase("radar.largead") && rainCardDisplayed) {
					System.out.println("Since Rain Card displayed, Map Card will not appear, hence no need to perform further checks");
					logStep("Since Rain Card displayed, Map Card will not appear, hence no need to perform further checks");
					interStitialDisplayed = false;
				} else {
					if (cardName.equalsIgnoreCase("video")) {
						if (Ad instanceof IOSDriver<?>) {
							((VideoCardScreen) vScreen).navigateToVideoCardContentPageAndDontHandleInterstitials();
						} else if (Ad instanceof AndroidDriver<?>) {
							((AndroidVideoCardScreen) vScreen).navigateToVideoCardContentPageAndDontHandleInterstitials();
						}
						

					} else if (cardName.equalsIgnoreCase("today")) {
						if (Ad instanceof IOSDriver<?>) {
							((TodayCardScreen) tScreen).navigateToTodayCardContentPageAndDontHandleInterstitials();
						} else if (Ad instanceof AndroidDriver<?>) {
							((AndroidTodayCardScreen) tScreen).navigateToTodayCardContentPageAndDontHandleInterstitials();
						}
						

					} else if (cardName.equalsIgnoreCase("aq")) {
						if (Ad instanceof IOSDriver<?>) {
							((AirQualityCardScreen) aqScreen).navigateToAirQualityCardContentPageAndDontHandleInterstitials();
						} else if (Ad instanceof AndroidDriver<?>) {
							((AndroidAirQualityCardScreen) aqScreen).navigateToAirQualityCardContentPageAndDontHandleInterstitials();
						}	
						

					} else if (cardName.equalsIgnoreCase("radar.largead")) {
						if (Ad instanceof IOSDriver<?>) {
							((RadarCardScreen) rScreen).navigateToRadarCardContentPageAndDontHandleInterstitials();
						} else if (Ad instanceof AndroidDriver<?>) {
							((AndroidRadarCardScreen) rScreen).navigateToRadarCardContentPageAndDontHandleInterstitials();
						}
						

					} else if (cardName.equalsIgnoreCase("daily")) {
						if (Ad instanceof IOSDriver<?>) {
							((DailyCardScreen) dScreen).navigateToDailyCardContentPageAndDontHandleInterstitials();
						} else if (Ad instanceof AndroidDriver<?>) {
							((AndroidDailyCardScreen) dScreen).navigateToDailyCardContentPageAndDontHandleInterstitials();
						}
						

					} else if (cardName.equalsIgnoreCase("news")) {
						if (Ad instanceof IOSDriver<?>) {
							((NewsCardScreen) nScreen).navigateToNewsCardContentPageAndDontHandleInterstitials();
						} else if (Ad instanceof AndroidDriver<?>) {
							((AndroidNewsCardScreen) nScreen).navigateToNewsCardContentPageAndDontHandleInterstitials();
						}
						

					} else if (cardName.equalsIgnoreCase("hourlyTab")) {
						if (Ad instanceof IOSDriver<?>) {
							((HourlyNavTab) hrTab).navigateToHourlyTab();
						} else if (Ad instanceof AndroidDriver<?>) {
							((AndroidHourlyNavTab) hrTab).navigateToHourlyTab();
						}
						

					} else if (cardName.equalsIgnoreCase("dailyTab")) {
						if (Ad instanceof IOSDriver<?>) {
							((DailyNavTab) dTab).navigateToDailyTab();
						} else if (Ad instanceof AndroidDriver<?>) {
							((AndroidDailyNavTab) dTab).navigateToDailyTab();
						}
						

					} else if (cardName.equalsIgnoreCase("videoTab")) {
						if (Ad instanceof IOSDriver<?>) {
							((VideoNavTab) vTab).navigateToVideoTab();
						} else if (Ad instanceof AndroidDriver<?>) {
							((AndroidVideoNavTab) vTab).navigateToVideoTab();
						}
						

					} else if (cardName.equalsIgnoreCase("mapTab")) {
						if (Ad instanceof IOSDriver<?>) {
							((RadarNavTab) rTab).navigateToRadarTab();
						} else if (Ad instanceof AndroidDriver<?>) {
							((AndroidRadarNavTab) rTab).navigateToRadarTab();
						}
						

					} else if (cardName.equalsIgnoreCase("hourlybanner")) {
						if (Ad instanceof IOSDriver<?>) {
							((PlanningCardScreen) pScreen).navigateToHourlyDetailsFromPlanningCardAndDontHandleInterstitials();
						} else if (Ad instanceof AndroidDriver<?>) {
							
						}
						

					} else if (cardName.equalsIgnoreCase("dailybanner")) {
						if (Ad instanceof IOSDriver<?>) {
							((PlanningCardScreen) pScreen).navigateToDailyDetailsFromPlanningCardAndDontHandleInterstitials();
						} else if (Ad instanceof AndroidDriver<?>) {
							
						}
						

					}
					
					TestBase.waitForMilliSeconds(2000);
					/*
					 * Entry Interstitial be displayed upon navigating to any footer nav tab or any
					 * content page Exit Interstitial only be displayed upon click on Back button on
					 * any content page or home button on any nav tab bar
					 */
					if (interStitialType.equalsIgnoreCase("Exit")) {
						if (cardName.contains("Tab")) {
							if (Ad instanceof IOSDriver<?>) {
								((HomeNavTab) hmTab).navigateToHomeTab_toGetInterStitialAd();
							} else if (Ad instanceof AndroidDriver<?>) {
								((AndroidHomeNavTab) hmTab).navigateToHomeTab_toGetInterStitialAd();
							}
						} else {
							
							if (Ad instanceof IOSDriver<?>) {
								navigateBackToFeedCard();
							} else if (Ad instanceof AndroidDriver<?>) {
								navigateBackToFeedCardAndroid();						
							}
						}
					}
					/*
					 * if(interStitialType.equalsIgnoreCase("Entry")) {
					 * 
					 * Clearing session for Entry interstitial as call wont generate
					 * automatically...
					 * 
					 * CharlesProxy.proxy.clearCharlesSession(); }
					 */

					cal = Calendar.getInstance();
					date = cal.getTime();
					dateFormat = new SimpleDateFormat("HH:mm:ss");
					formattedDate = dateFormat.format(date);
					System.out.println("Current time of the day using Calendar - 24 hour format: " + formattedDate);
					// logStep("Current time of the day using Calendar - 24 hour format: " +
					// formattedDate);

					long start = System.nanoTime();
					long finish = 0;
					long interstitialFqCapEndTime = 0L;
					long timeElapsed = 0;
					long interstitialFqtimeElapsed = 0L;
					long convert = 0;
					long interstitialFqtimeconvert = 0L;
					if (Ad instanceof IOSDriver<?>) {
						handle_Interstitial_Ad();
					} else if (Ad instanceof AndroidDriver<?>) {
						handle_Interstitial_AdAndroid();						
					}
					if (interStitialDisplayed) {
						if (interStitialType.equalsIgnoreCase("Entry")) {
							if (!cardName.contains("Tab")) {
								if (Ad instanceof IOSDriver<?>) {
									navigateBackToFeedCard();
								} else if (Ad instanceof AndroidDriver<?>) {
									navigateBackToFeedCardAndroid();						
								}
							}
						}
						System.out.println("Interstitial Ad Displayed");
						logStep("Interstitial Ad Displayed");
						interStitialDisplayed = false;
						TestBase.waitForMilliSeconds(10000);
						finish = System.nanoTime();
						interstitialFqCapEndTime = System.nanoTime();
						// System.out.println("interstitialFqCapStrtTime: "+interstitialFqCapStrtTime);
						// System.out.println("interstitialFqCapEndTime: "+interstitialFqCapEndTime);
						timeElapsed = finish - start;
						interstitialFqtimeElapsed = interstitialFqCapEndTime - interstitialFqCapStrtTime;
						convert = TimeUnit.SECONDS.convert(timeElapsed, TimeUnit.NANOSECONDS);
						interstitialFqtimeconvert = TimeUnit.SECONDS.convert(interstitialFqtimeElapsed,
								TimeUnit.NANOSECONDS);
						// System.out.println("Elapsed time is: " + convert);
						System.out.println("Interstitial Frequency Cap Elapsed time is: " + interstitialFqtimeconvert);
						// logStep("Elapsed time is: " + convert);
						cal = Calendar.getInstance();
						date = cal.getTime();
						dateFormat = new SimpleDateFormat("HH:mm:ss");
						formattedDate = dateFormat.format(date);
						// System.out.println("Current time of the day using Calendar - 24 hour format:
						// " + formattedDate);
						// logStep("Current time of the day using Calendar - 24 hour format: " +
						// formattedDate);
						// if (convert >= 65) {
						if (interstitialFqtimeconvert >= 65) {
							/*
							 * clearing may be need to move above for entry interstitials if call generates
							 * automatically after a minute. Currently interstitials are not enabled to test
							 * entry feature.
							 */
							CharlesProxy.proxy.clearCharlesSession();

							// Since interstitial call doesnt generate automtically after the frequency cap
							// and it generates when refresh happened.
							// hence pull to refresh is performed after navigating to home tab
							if (Ad instanceof IOSDriver<?>) {
								((HomeNavTab) hmTab).navigateToHomeTab_toGetInterStitialAd();
								Functions.scroll_Up();
								Functions.scroll_Up();
							} else if (Ad instanceof AndroidDriver<?>) {
								//((AndroidHomeNavTab) hmTab).navigateToHomeTab_toGetInterStitialAd();
								Functions.close_launchAppAndroid();
							}
							
							TestBase.waitForMilliSeconds(5000);

							Functions.archive_folder("Charles");
							CharlesProxy.proxy.getXml();
							Utils.createXMLFileForCharlesSessionFile();
							if (Utils.isInterStitialAdCalExists(excelName, sheetName)) {
								if (Utils.isInterstitialCall_hasResponse(excelName, sheetName)) {
									TestBase.waitForMilliSeconds(2000);

									if (!cardName.contains("Tab")) {
										if (cardName.contains("banner")) {
											// navigate to Planning Card
											// Utils.navigateToPlanningCard();
											Utils.navigateTofeedCard("planning-containerView", false, false);
										} else {
											// navigate to feed card
											
											if (Ad instanceof IOSDriver<?>) {
												Utils.navigateTofeedCard(cardName, false, false);
											} else if (Ad instanceof AndroidDriver<?>) {
												if (cardName.equalsIgnoreCase("hourly")) {
													((AndroidHourlyCardScreen) hrScreen).scrollToHourlyCard();
												} else if (cardName.equalsIgnoreCase("daily")) {
													((AndroidDailyCardScreen) dScreen).scrollToDailyCard();
												} else if (cardName.equalsIgnoreCase("today")) {
													((AndroidTodayCardScreen) tScreen).scrollToTodayCard();
												}
											}
										}

									}

									// System.out.println("Checking for element displayed: " +
									// elementToClick.isDisplayed());
									// System.out.println("Checking for element enabled: " +
									// elementToClick.isEnabled());
									// WebElement element =
									// mywait.until(ExpectedConditions.elementToBeClickable(elementToClick));
									// if (!elementToClick.isEnabled()) {
									System.out.println("Current Context is: " + Ad.getContext());
									Ad.context(Ad.getContext());
									/*
									 * System.out.println("Element Coordinates: "+elementToClick.getCoordinates());
									 * Dimension d = elementToClick.getSize(); int x = d.getWidth(); int y =
									 * d.getHeight(); System.out.println("X Position of element is: "+x);
									 * System.out.println("Y Position of element is: "+y);
									 */
									/*
									 * Since Nav Tab elements are loosing references, hence reassigning them
									 */
									if (cardName.equalsIgnoreCase("hourlyTab")) {
										if (Ad instanceof IOSDriver<?>) {
											((HourlyNavTab) hrTab).navigateToHourlyTab();
										} else if (Ad instanceof AndroidDriver<?>) {
											((AndroidHourlyNavTab) hrTab).navigateToHourlyTab();
										}
										

									} else if (cardName.equalsIgnoreCase("dailyTab")) {
										if (Ad instanceof IOSDriver<?>) {
											((DailyNavTab) dTab).navigateToDailyTab();
										} else if (Ad instanceof AndroidDriver<?>) {
											((AndroidDailyNavTab) dTab).navigateToDailyTab();
										}
										

									} else if (cardName.equalsIgnoreCase("videoTab")) {
										if (Ad instanceof IOSDriver<?>) {
											((VideoNavTab) vTab).navigateToVideoTab();
										} else if (Ad instanceof AndroidDriver<?>) {
											((AndroidVideoNavTab) vTab).navigateToVideoTab();
										}
										

									} else if (cardName.equalsIgnoreCase("mapTab")) {
										if (Ad instanceof IOSDriver<?>) {
											((RadarNavTab) rTab).navigateToRadarTab();
										} else if (Ad instanceof AndroidDriver<?>) {
											((AndroidRadarNavTab) rTab).navigateToRadarTab();
										}
										

									} else if (cardName.equalsIgnoreCase("hourlybanner")) {
										if (Ad instanceof IOSDriver<?>) {
											((PlanningCardScreen) pScreen).navigateToHourlyDetailsFromPlanningCardAndDontHandleInterstitials();
										} else if (Ad instanceof AndroidDriver<?>) {
											
										}
										

									} else if (cardName.equalsIgnoreCase("dailybanner")) {
										if (Ad instanceof IOSDriver<?>) {
											((PlanningCardScreen) pScreen).navigateToDailyDetailsFromPlanningCardAndDontHandleInterstitials();
										} else if (Ad instanceof AndroidDriver<?>) {
											
										}
										

									} else if (cardName.equalsIgnoreCase("radar.largead")) {
										if (Ad instanceof IOSDriver<?>) {
											((RadarCardScreen) rScreen).navigateToRadarCardContentPageAndDontHandleInterstitials();
										} else if (Ad instanceof AndroidDriver<?>) {
											((AndroidRadarCardScreen) rScreen).navigateToRadarCardContentPageAndDontHandleInterstitials();
										}
										

									} else if (cardName.equalsIgnoreCase("daily")) {
										if (Ad instanceof IOSDriver<?>) {
											((DailyCardScreen) dScreen).navigateToDailyCardContentPageAndDontHandleInterstitials();
										} else if (Ad instanceof AndroidDriver<?>) {
											((AndroidDailyCardScreen) dScreen).navigateToDailyCardContentPageAndDontHandleInterstitials();
										}
										

									} else if (cardName.equalsIgnoreCase("today")) {
										if (Ad instanceof IOSDriver<?>) {
											((TodayCardScreen) tScreen).navigateToTodayCardContentPageAndDontHandleInterstitials();
										} else if (Ad instanceof AndroidDriver<?>) {
											((AndroidTodayCardScreen) tScreen).navigateToTodayCardContentPageAndDontHandleInterstitials();
										}
										

									}
									// }

									TestBase.waitForMilliSeconds(2000);
									/*
									 * For feed cards freq cap check doesnt work as interstitial call doesnt auto
									 * generate after frequency cap...
									 */

									if (interStitialType.equalsIgnoreCase("Exit")) {
										if (cardName.contains("Tab")) {
											if (Ad instanceof IOSDriver<?>) {
												((HomeNavTab) hmTab).navigateToHomeTab_toGetInterStitialAd();
											} else if (Ad instanceof AndroidDriver<?>) {
												((AndroidHomeNavTab) hmTab).navigateToHomeTab_toGetInterStitialAd();
											}
										} else {
											if (Ad instanceof IOSDriver<?>) {
												navigateBackToFeedCard();
											} else if (Ad instanceof AndroidDriver<?>) {
												navigateBackToFeedCardAndroid();						
											}
										}
									}
									if (Ad instanceof IOSDriver<?>) {
										handle_Interstitial_Ad();
									} else if (Ad instanceof AndroidDriver<?>) {
										handle_Interstitial_AdAndroid();						
									}
									if (interStitialDisplayed) {
										if (interStitialType.equalsIgnoreCase("Entry")) {
											if (!cardName.contains("Tab")) {
												if (Ad instanceof IOSDriver<?>) {
													navigateBackToFeedCard();
												} else if (Ad instanceof AndroidDriver<?>) {
													navigateBackToFeedCardAndroid();						
												}
											}
										}
										System.out.println("Interstitial Ad Displayed after 1 min");
										logStep("Interstitial Ad Displayed after 1 min");
										interStitialDisplayed = false;
									} else {
										System.out.println(
												"Interstitial Ad Not Displayed after 1 min, no need to perform further checks");
										logStep("Interstitial Ad Not Displayed after 1 min, no need to perform further checks");
										returnMessage = "Interstitial Ad Not Displayed after 1 min, no need to perform further checks";
										interStitialDisplayed = false;

										if (interStitialType.equalsIgnoreCase("Entry")) {
											if (!cardName.contains("Tab")) {
												if (Ad instanceof IOSDriver<?>) {
													navigateBackToFeedCard();
												} else if (Ad instanceof AndroidDriver<?>) {
													navigateBackToFeedCardAndroid();						
												}
											}
										}

									}
								} else {
									System.out.println(
											"Interstitial Ad call generated after 1 min doesnt have response, no need to perform further checks");
									logStep("Interstitial Ad call generated after 1 min doesnt have response, no need to perform further checks");
									// returnMessage = "Interstitial Ad call generated after 1 min doesnt have
									// response, no need to perform further checks";
									interStitialDisplayed = false;

									// remove below
									if (cardName.contains("Tab")) {
										if (Ad instanceof IOSDriver<?>) {
											((HomeNavTab) hmTab).navigateToHomeTab_toGetInterStitialAd();
										} else if (Ad instanceof AndroidDriver<?>) {
											((AndroidHomeNavTab) hmTab).navigateToHomeTab_toGetInterStitialAd();
										}
									} else {
										if (Ad instanceof IOSDriver<?>) {
											navigateBackToFeedCard();
										} else if (Ad instanceof AndroidDriver<?>) {
											navigateBackToFeedCardAndroid();						
										}
									}
								}

							} else {
								// need to write else code....think and write...
								System.out.println(
										"Interstitial Ad call not generated after 1 min, no need to perform further checks");
								logStep("Interstitial Ad call not generated after 1 min, no need to perform further checks");
								returnMessage = "Interstitial Ad call not generated after 1 min, no need to perform further checks";
								interStitialDisplayed = false;

								// remove below
								if (cardName.contains("Tab")) {
									if (Ad instanceof IOSDriver<?>) {
										((HomeNavTab) hmTab).navigateToHomeTab_toGetInterStitialAd();
									} else if (Ad instanceof AndroidDriver<?>) {
										((AndroidHomeNavTab) hmTab).navigateToHomeTab_toGetInterStitialAd();
									}
								} else {
									if (Ad instanceof IOSDriver<?>) {
										navigateBackToFeedCard();
									} else if (Ad instanceof AndroidDriver<?>) {
										navigateBackToFeedCardAndroid();						
									}
								}
							}

							interStitialDisplayed = false;
						} else {
							System.out.println("Will be going to forloop due to elapsed time lessthan 60 s ");
							logStep("Will be going to forloop due to elapsed time lessthan 60 s ");
							for (int i = 0; i <= 65; i++) {
								TestBase.waitForMilliSeconds(1000);
								finish = System.nanoTime();
								interstitialFqCapEndTime = System.nanoTime();

								timeElapsed = finish - start;
								interstitialFqtimeElapsed = interstitialFqCapEndTime - interstitialFqCapStrtTime;

								convert = TimeUnit.SECONDS.convert(timeElapsed, TimeUnit.NANOSECONDS);
								interstitialFqtimeconvert = TimeUnit.SECONDS.convert(interstitialFqtimeElapsed,
										TimeUnit.NANOSECONDS);
								// System.out.println("Elapsed time is: " + convert);
								System.out.println(
										"Interstitial Frequency Cap Elapsed time is: " + interstitialFqtimeconvert);
								cal = Calendar.getInstance();
								date = cal.getTime();
								dateFormat = new SimpleDateFormat("HH:mm:ss");
								formattedDate = dateFormat.format(date);
								// System.out.println("Current time of the day using Calendar - 24 hour format:
								// " + formattedDate);
								// logStep("Current time of the day using Calendar - 24 hour format: " +
								// formattedDate);
								// if (convert >= 65) {
								if (interstitialFqtimeconvert >= 65) {
									CharlesProxy.proxy.clearCharlesSession();

									// Since interstitial call doesnt generate automtically after the frequency cap
									// and it generates when refresh happened.
									// hence pull to refresh is performed after navigating to home tab
									if (Ad instanceof IOSDriver<?>) {
										((HomeNavTab) hmTab).navigateToHomeTab_toGetInterStitialAd();
										Functions.scroll_Up();
										Functions.scroll_Up();
									} else if (Ad instanceof AndroidDriver<?>) {
										//((AndroidHomeNavTab) hmTab).navigateToHomeTab_toGetInterStitialAd();
										Functions.close_launchAppAndroid();
									}
									
									TestBase.waitForMilliSeconds(5000);

									Functions.archive_folder("Charles");
									CharlesProxy.proxy.getXml();
									Utils.createXMLFileForCharlesSessionFile();

									if (Utils.isInterStitialAdCalExists(excelName, sheetName)) {
										if (Utils.isInterstitialCall_hasResponse(excelName, sheetName)) {
											TestBase.waitForMilliSeconds(2000);

											if (!cardName.contains("Tab")) {
												if (cardName.contains("banner")) {
													// navigate to Planning Card
													// Utils.navigateToPlanningCard();
													Utils.navigateTofeedCard("planning-containerView", false, false);
												} else {
													// navigate to feed card
													
													if (Ad instanceof IOSDriver<?>) {
														Utils.navigateTofeedCard(cardName, false, false);
													} else if (Ad instanceof AndroidDriver<?>) {
														if (cardName.equalsIgnoreCase("hourly")) {
															((AndroidHourlyCardScreen) hrScreen).scrollToHourlyCard();
														} else if (cardName.equalsIgnoreCase("daily")) {
															((AndroidDailyCardScreen) dScreen).scrollToDailyCard();
														} else if (cardName.equalsIgnoreCase("today")) {
															((AndroidTodayCardScreen) tScreen).scrollToTodayCard();
														}
													}
												}

											}

											// System.out.println("Checking for element displayed: " +
											// elementToClick.isDisplayed());
											// System.out.println("Checking for element enabled: " +
											// elementToClick.isEnabled());
											// WebElement element =
											// mywait.until(ExpectedConditions.elementToBeClickable(elementToClick));
											// if (!elementToClick.isEnabled()) {
											System.out.println("Current Context is: " + Ad.getContext());
											Ad.context(Ad.getContext());
											/*
											 * System.out.println("Element Coordinates: "+elementToClick.getCoordinates( ));
											 * Dimension d = elementToClick.getSize(); int x = d.getWidth(); int y =
											 * d.getHeight(); System.out.println("X Position of element is: "+x);
											 * System.out.println("Y Position of element is: "+y);
											 */
											/*
											 * Since Nav Tab elements are loosing references, hence reassigning them
											 */
											if (cardName.equalsIgnoreCase("hourlyTab")) {
												if (Ad instanceof IOSDriver<?>) {
													((HourlyNavTab) hrTab).navigateToHourlyTab();
												} else if (Ad instanceof AndroidDriver<?>) {
													((AndroidHourlyNavTab) hrTab).navigateToHourlyTab();
												}
												

											} else if (cardName.equalsIgnoreCase("dailyTab")) {
												if (Ad instanceof IOSDriver<?>) {
													((DailyNavTab) dTab).navigateToDailyTab();
												} else if (Ad instanceof AndroidDriver<?>) {
													((AndroidDailyNavTab) dTab).navigateToDailyTab();
												}
												

											} else if (cardName.equalsIgnoreCase("videoTab")) {
												if (Ad instanceof IOSDriver<?>) {
													((VideoNavTab) vTab).navigateToVideoTab();
												} else if (Ad instanceof AndroidDriver<?>) {
													((AndroidVideoNavTab) vTab).navigateToVideoTab();
												}
												

											} else if (cardName.equalsIgnoreCase("mapTab")) {
												if (Ad instanceof IOSDriver<?>) {
													((RadarNavTab) rTab).navigateToRadarTab();
												} else if (Ad instanceof AndroidDriver<?>) {
													((AndroidRadarNavTab) rTab).navigateToRadarTab();
												}
												

											} else if (cardName.equalsIgnoreCase("hourlybanner")) {
												if (Ad instanceof IOSDriver<?>) {
													((PlanningCardScreen) pScreen).navigateToHourlyDetailsFromPlanningCardAndDontHandleInterstitials();
												} else if (Ad instanceof AndroidDriver<?>) {
													
												}
												

											} else if (cardName.equalsIgnoreCase("dailybanner")) {
												if (Ad instanceof IOSDriver<?>) {
													((PlanningCardScreen) pScreen).navigateToDailyDetailsFromPlanningCardAndDontHandleInterstitials();
												} else if (Ad instanceof AndroidDriver<?>) {
													
												}
												

											} else if (cardName.equalsIgnoreCase("radar.largead")) {
												if (Ad instanceof IOSDriver<?>) {
													((RadarCardScreen) rScreen).navigateToRadarCardContentPageAndDontHandleInterstitials();
												} else if (Ad instanceof AndroidDriver<?>) {
													((AndroidRadarCardScreen) rScreen).navigateToRadarCardContentPageAndDontHandleInterstitials();
												}
												

											} else if (cardName.equalsIgnoreCase("daily")) {
												if (Ad instanceof IOSDriver<?>) {
													((DailyCardScreen) dScreen).navigateToDailyCardContentPageAndDontHandleInterstitials();
												} else if (Ad instanceof AndroidDriver<?>) {
													((AndroidDailyCardScreen) dScreen).navigateToDailyCardContentPageAndDontHandleInterstitials();
												}
												

											} else if (cardName.equalsIgnoreCase("today")) {
												if (Ad instanceof IOSDriver<?>) {
													((TodayCardScreen) tScreen).navigateToTodayCardContentPageAndDontHandleInterstitials();
												} else if (Ad instanceof AndroidDriver<?>) {
													((AndroidTodayCardScreen) tScreen).navigateToTodayCardContentPageAndDontHandleInterstitials();
												}
												

											}
											// }
											/*
											 * try { elementToClick.click(); TestBase.waitForMilliSeconds(2000);
											 * System.out.println("Clicked on Respective Nav Tab/Feed Card element");
											 * logStep("Clicked on Respective Nav Tab/Feed Card element"); attachScreen(); }
											 * catch (Exception e) { System.out.println(
											 * "An exception while clicking on element hence navigating to hometab and click again"
											 * );
											 * logStep("An exception while clicking on element hence navigating to hometab and click again"
											 * ); hmTab.navigateToHomeTab_toGetInterStitialAd(); if
											 * (!cardName.contains("Tab")) { if (cardName.contains("banner")) { // Since
											 * interstitial call doesnt generate automtically after the // frequency cap //
											 * and it generates when refresh happened. // hence pull to refresh is performed
											 * after navigating to home tab hmTab.navigateToHomeTab_toGetInterStitialAd();
											 * // navigate to Planning Card // Utils.navigateToPlanningCard();
											 * Utils.navigateTofeedCard("planning-containerView"); if
											 * (cardName.equalsIgnoreCase("hourlybanner")) {
											 * pScreen.navigateToHourlyDetailsFromPlanningCardAndDontHandleInterstitials();
											 * 
											 * } else if (cardName.equalsIgnoreCase("dailybanner")) {
											 * pScreen.navigateToDailyDetailsFromPlanningCardAndDontHandleInterstitials();
											 * 
											 * } } else { // Since interstitial call doesnt generate automtically after the
											 * // frequency cap // and it generates when refresh happened. // hence pull to
											 * refresh is performed after navigating to home tab
											 * hmTab.navigateToHomeTab_toGetInterStitialAd(); // navigate to Feed Card
											 * Utils.navigateTofeedCard(cardName); if
											 * (cardName.equalsIgnoreCase("radar.largead")) {
											 * rScreen.navigateToRadarCardContentPageAndDontHandleInterstitials();
											 * 
											 * } else if (cardName.equalsIgnoreCase("daily")) {
											 * dScreen.navigateToDailyCardContentPageAndDontHandleInterstitials(); //
											 * elementToClick = // Ad.findElement(By.name("dailyCollectionViewCell_0"));
											 * 
											 * } } } elementToClick.click();
											 * System.out.println("Clicked on Respective Nav Tab/Feed Card element");
											 * logStep("Clicked on Respective Nav Tab/Feed Card element"); attachScreen(); }
											 */

											TestBase.waitForMilliSeconds(2000);
											if (interStitialType.equalsIgnoreCase("Exit")) {
												if (cardName.contains("Tab")) {
													if (Ad instanceof IOSDriver<?>) {
														((HomeNavTab) hmTab).navigateToHomeTab_toGetInterStitialAd();
													} else if (Ad instanceof AndroidDriver<?>) {
														((AndroidHomeNavTab) hmTab).navigateToHomeTab_toGetInterStitialAd();
													}
												} else {
													if (Ad instanceof IOSDriver<?>) {
														navigateBackToFeedCard();
													} else if (Ad instanceof AndroidDriver<?>) {
														navigateBackToFeedCardAndroid();						
													}
												}
											}
											if (Ad instanceof IOSDriver<?>) {
												handle_Interstitial_Ad();
											} else if (Ad instanceof AndroidDriver<?>) {
												handle_Interstitial_AdAndroid();						
											}
											if (interStitialDisplayed) {
												if (interStitialType.equalsIgnoreCase("Entry")) {
													if (cardName.contains("banner")) {
														if (Ad instanceof IOSDriver<?>) {
															navigateBackToFeedCard();
														} else if (Ad instanceof AndroidDriver<?>) {
															navigateBackToFeedCardAndroid();						
														}
													}
												}
												System.out.println("Interstitial Ad Displayed after 1 min");
												logStep("Interstitial Ad Displayed after 1 min");
												interStitialDisplayed = false;
											} else {
												System.out.println(
														"Interstitial Ad Not Displayed after 1 min, no need to perform further checks");
												logStep("Interstitial Ad Not Displayed after 1 min, no need to perform further checks");
												returnMessage = "Interstitial Ad Not Displayed after 1 min, no need to perform further checks";
												interStitialDisplayed = false;

												if (interStitialType.equalsIgnoreCase("Entry")) {
													if (cardName.contains("banner")) {
														if (Ad instanceof IOSDriver<?>) {
															navigateBackToFeedCard();
														} else if (Ad instanceof AndroidDriver<?>) {
															navigateBackToFeedCardAndroid();						
														}
													}
												}

											}
											break;
										} else {
											// need to write else code....think and write...
											System.out.println(
													"Interstitial Ad call generated after 1 min doesnt have response, no need to perform further checks");
											logStep("Interstitial Ad call generated after 1 min doesnt have response, no need to perform further checks");
											// returnMessage = "Interstitial Ad call generated after 1 min doesnt have
											// response, no need to perform further checks";
											interStitialDisplayed = false;

											// remove below
											/*
											 * if (cardName.contains("Tab")) { navigateToHomeTab_toGetInterStitialAd(); }
											 * else { navigateBackToFeedCard(); }
											 */
										}
									} else {
										// need to write else code....think and write...
										System.out.println(
												"Interstitial Ad call not generated after 1 min, no need to perform further checks");
										logStep("Interstitial Ad call not generated after 1 min, no need to perform further checks");
										returnMessage = "Interstitial Ad call not generated after 1 min, no need to perform further checks";
										interStitialDisplayed = false;

										// remove below
										/*
										 * if (cardName.contains("Tab")) { navigateToHomeTab_toGetInterStitialAd(); }
										 * else { navigateBackToFeedCard(); }
										 */
									}
									interStitialDisplayed = false;
									break;
								}
							}
						}

					} else {
						System.out.println("Interstitial Ad Not Displayed, no need to perform further checks");
						logStep("Interstitial Ad Not Displayed, no need to perform further checks");
						returnMessage = "Interstitial Ad Not Displayed, no need to perform further checks";
						interStitialDisplayed = false;

						if (interStitialType.equalsIgnoreCase("Entry")) {
							if (!cardName.contains("Tab")) {
								if (Ad instanceof IOSDriver<?>) {
									navigateBackToFeedCard();
								} else if (Ad instanceof AndroidDriver<?>) {
									navigateBackToFeedCardAndroid();						
								}
							}
						}

					}
				}

				
			} else {
				System.out.println("Interstitial Ad call doesnt have response, no need to perform further checks");
				logStep("Interstitial Ad call doesnt have response, no need to perform further checks");
				// returnMessage = "Interstitial Ad call doesnt have response, no need to
				// perform further checks";
				interStitialDisplayed = false;

				// this piece to be removed
				/*
				 * if (cardName.contains("Tab")) { navigateToHomeTab_toGetInterStitialAd(); }
				 * else { navigateBackToFeedCard(); }
				 */
			}

		} else {
			System.out.println("Interstitial Ad call not generated, no need to perform further checks");
			logStep("Interstitial Ad call not generated, no need to perform further checks");
			returnMessage = "Interstitial Ad call not generated, no need to perform further checks";
			interStitialDisplayed = false;

			// this piece to be removed
			/*
			 * if (cardName.contains("Tab")) { navigateToHomeTab_toGetInterStitialAd(); }
			 * else { navigateBackToFeedCard(); }
			 */

		}
		return returnMessage;

	}

	/**
	 * Asserts Interstitial ad
	 * @param excelName
	 * @param sheetName
	 * @param cardName
	 * @param interStitialType
	 * @throws Exception
	 */
	public static void assertinterStitialAd(String excelName, String sheetName, String cardName,
			String interStitialType) throws Exception {
		String interStitialMessage = verifyInterstitialAd(excelName, sheetName, cardName, interStitialType);
		if (interStitialMessage != null) {
			System.out.println("Interstitial Ad Assertion is failed");
			logStep("Interstitial Ad Assertion is failed");
			Assert.fail(interStitialMessage);
		} else {
			System.out.println("Interstitial Ad Assertion is successful");
			logStep("Interstitial Ad Assertion is successful");
		}
	}

	/**
	 * Navigate to All Feed Cards and its content pages by choice
	 * 
	 * @param includeDetailsPages
	 * @throws Exception
	 */
	public static void navigateToAllCardsOld(boolean includeDetailsPages, boolean navigateTwiceToDetailsPages) throws Exception {
		HomeNavTab hmTab = new HomeNavTab(Ad);
		HourlyNavTab hrTab = new HourlyNavTab(Ad);
		DailyNavTab dTab = new DailyNavTab(Ad);
		RadarNavTab rTab = new RadarNavTab(Ad);
		VideoNavTab vTab = new VideoNavTab(Ad);
		AlertCenterScreen alertScreen = new AlertCenterScreen(Ad);
		CurrentConditionsCardScreen cConditionsCardScreen = new CurrentConditionsCardScreen(Ad);
		LifeStyleCardScreen lStyleCardScreen = new LifeStyleCardScreen(Ad);
		SeasonalHubCardScreen sHubCardScreen = new SeasonalHubCardScreen(Ad);
		DailyCardScreen dCardScreen = new DailyCardScreen(Ad);
		RadarCardScreen rCardScreen = new RadarCardScreen(Ad);
		TodayCardScreen tCardScreen = new TodayCardScreen(Ad);
		VideoCardScreen vCardScreen = new VideoCardScreen(Ad);
		AirQualityCardScreen aqCardScreen = new AirQualityCardScreen(Ad);
		NewsCardScreen nCardScreen = new NewsCardScreen(Ad);
		WatsonCardScreen wCardScreen = new WatsonCardScreen(Ad);

		String cardName = "homescreen";
		String adcardname = "Test Card";
		String feedName = null;
		MobileElement adele;
		ArrayList<String> cardsList = new ArrayList<String>();
		MobileElement allCards = null;
		List<MobileElement> Cards = null;
		nextGenIMadDisplayed = false;
		rainCardDisplayed = false;
		int u = 1;
		feedAdCount = 0;
		int cardHeight = 0;
		ReadExcelValues.excelValues("Smoke", "General");
		// Since HomeTab button not functioning in 11.0+ builds when user on any feed
		// card, hence first clicking on daily tab before clicking on Home tab.
		// navigateToDailyTab();
		/*
		 * Navigating to all nav tabs in the begining as amzn_b parameter is not
		 * appending when navigating at end
		 */
		// clickonHomeTab
		hmTab.clickonHomeTab();
		TestBase.waitForMilliSeconds(2000);
		// navigate to Hourly tab
		//hrTab.navigateToHourlyTab();
		hrTab.navigateToHourlyTabAndHandleInterstitialAd();
		TestBase.waitForMilliSeconds(2000);
		// navigate to Daily tab
		dTab.navigateToDailyTab();
		TestBase.waitForMilliSeconds(2000);
		// navigate to Radar tab
		rTab.navigateToRadarTab();
		TestBase.waitForMilliSeconds(2000);
		// navigate to Video tab
		vTab.navigateToVideoTab();
		TestBase.waitForMilliSeconds(2000);
		// clickonHomeTab
		hmTab.clickonHomeTab();
		TestBase.waitForMilliSeconds(5000);

		System.out.println("User on Current Conditions Card and trying to scroll the app till end");
		logStep("User on Current Conditions Card and trying to scroll the app till end");
		// scroll_Down();
		// scroll_Down();

		try {
			cConditionsCardScreen.swipe_Up_CurrentConditionCard();
			TestBase.waitForMilliSeconds(2000);
		} catch (Exception e) {
			System.out.println("There is an error in swiping Current Conditions Card, it might not have displayed");
			logStep("There is an error in swiping Current Conditions Card, it might not have displayed");
		}

		for (int scrollend = 1; scrollend <= 50; scrollend++) {
			System.out.println("=================================================================");
			logStep("=================================================================");
			System.out.println("Current iteration is: " + scrollend);
			logStep("Current iteration is: " + scrollend);

			try {
				allCards = Ad.findElementByClassName("XCUIElementTypeCollectionView");
				Cards = allCards.findElementsByClassName("XCUIElementTypeCell");
				if (scrollend == 1) {

					for (int ccount = 0; ccount <= 1; ccount++) {
						boolean matchFound = false;

						try {
							// cardName =
							// Cards.get(ccount).findElement(By.className("XCUIElementTypeStaticText")).getAttribute("name");
							try {
								cardName = Cards.get(ccount)
										.findElement(By.xpath("//XCUIElementTypeOther[@name='nextgen-integrated-marquee-card-containerView']"))
										.getAttribute("name");
							} catch (Exception e) {
								try {
									cardName = Cards.get(ccount)
											.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"))
											.getAttribute("name");
								} catch (Exception e1) {
									cardName = Cards.get(ccount).findElement(By.xpath("(//XCUIElementTypeOther)[3]"))
											.getAttribute("name");
								}
							}
							
							globalcurrentCard = Cards.get(ccount);

							/*
							 * MobileElement currentCardElement = Cards.get(ccount)
							 * .findElementByAccessibilityId("current-condition-updated-card-containerView")
							 * ;
							 */
							MobileElement currentCardElement = cConditionsCardScreen
									.returnCurrentConditionCard(Cards.get(ccount));
							matchFound = true;
						} catch (Exception e) {

						}

						if (matchFound == true) {
							System.out.println("First feed card not yet derived.. continuing...");
							logStep("First feed card not yet derived.. continuing...");
						} else {
							System.out.println("First feed card derived...and Card Name is: " + cardName);
							logStep("First feed card derived...and Card Name is: " + cardName);
							break;
						}
					}

				} else {
					TestBase.waitForMilliSeconds(10000);// waiting for advertisements to load
					if (!nextGenIMadDisplayed) {
						try {
							cardName = Cards.get(0)
									.findElement(By.xpath("//XCUIElementTypeOther[@name='nextgen-integrated-marquee-card-containerView']"))
									.getAttribute("name");
							globalcurrentCard = Cards.get(0);
						} catch (Exception e) {
							try {
								// cardName =
								// Cards.get(0).findElement(By.className("XCUIElementTypeStaticText")).getAttribute("name");
								cardName = Cards.get(0)
										.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"))
										.getAttribute("name");
								globalcurrentCard = Cards.get(0);
							} catch (Exception ey) {
								try {
									try {
										// some times when big advertisement displayed, it doenst have name as
										// 'Advertisement' hence below implemented
										cardName = Cards.get(0)
												.findElement(By.xpath("//XCUIElementTypeOther[@name='ads-card-containerView']"))
												.getAttribute("name");
										globalcurrentCard = Cards.get(0);

									} catch (Exception ex) {
										try {
											// when Integrated Feed card is displayed, it doenst have name as
											// 'Advertisement' hence below implemented
											cardName = Cards.get(0)
													.findElement(By.xpath(
															"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']"))
													.getAttribute("name");
											globalcurrentCard = Cards.get(0);

										} catch (Exception ex1) {
											// sometimes cards like planning card, planning ad card,..etc doesnt have
											// XCUIElementTypeStaticText element,
											cardName = Cards.get(0).findElement(By.xpath("(//XCUIElementTypeOther)[3]"))
													.getAttribute("name");
											globalcurrentCard = Cards.get(0);

										}

									}
								} catch (Exception e1) {
									globalcurrentCard = Cards.get(0);
									cardName = "UnKnownCard";

								}

							}
							
						}
					} else {
						try {
							// cardName =
							// Cards.get(0).findElement(By.className("XCUIElementTypeStaticText")).getAttribute("name");
							cardName = Cards.get(0)
									.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"))
									.getAttribute("name");
							globalcurrentCard = Cards.get(0);
						} catch (Exception ey) {
							try {
								try {
									// some times when big advertisement displayed, it doenst have name as
									// 'Advertisement' hence below implemented
									cardName = Cards.get(0)
											.findElement(By.xpath("//XCUIElementTypeOther[@name='ads-card-containerView']"))
											.getAttribute("name");
									globalcurrentCard = Cards.get(0);

								} catch (Exception ex) {
									try {
										// when Integrated Feed card is displayed, it doenst have name as
										// 'Advertisement' hence below implemented
										cardName = Cards.get(0)
												.findElement(By.xpath(
														"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']"))
												.getAttribute("name");
										globalcurrentCard = Cards.get(0);

									} catch (Exception ex1) {
										// sometimes cards like planning card, planning ad card,..etc doesnt have
										// XCUIElementTypeStaticText element,
										cardName = Cards.get(0).findElement(By.xpath("(//XCUIElementTypeOther)[3]"))
												.getAttribute("name");
										globalcurrentCard = Cards.get(0);

									}

								}
							} catch (Exception e1) {
								globalcurrentCard = Cards.get(0);
								cardName = "UnKnownCard";

							}

						}
					}
						
					cardName = getDerivedCardName(Cards.get(0), cardName);

					if (adcardname.equals(cardName)) {
						if (!nextGenIMadDisplayed) {
							try {
								cardName = Cards.get(1)
										.findElement(By.xpath("//XCUIElementTypeOther[@name='nextgen-integrated-marquee-card-containerView']"))
										.getAttribute("name");
								globalcurrentCard = Cards.get(1);
								globalprevCard = Cards.get(0);
							} catch(Exception e) {
								try {
									// cardName =
									// Cards.get(1).findElement(By.className("XCUIElementTypeStaticText")).getAttribute("name");
									cardName = Cards.get(1)
											.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"))
											.getAttribute("name");
									globalcurrentCard = Cards.get(1);
									globalprevCard = Cards.get(0);
								} catch (Exception ey) {
									try {
										try {
											// some times when big advertisement displayed, it doenst have name as
											// 'Advertisement' hence below implemented
											cardName = Cards.get(1)
													.findElement(
															By.xpath("//XCUIElementTypeOther[@name='ads-card-containerView']"))
													.getAttribute("name");
											globalcurrentCard = Cards.get(1);
											globalprevCard = Cards.get(0);
										} catch (Exception ex) {
											try {
												// when Integrated Feed card is displayed, it doenst have name as
												// 'Advertisement' hence below implemented
												cardName = Cards.get(1).findElement(By.xpath(
														"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']"))
														.getAttribute("name");
												globalcurrentCard = Cards.get(1);
												globalprevCard = Cards.get(0);
											} catch (Exception ex1) {
												// sometimes cards like planning card, planning ad card,..etc doesnt have
												// XCUIElementTypeStaticText element,
												cardName = Cards.get(1).findElement(By.xpath("(//XCUIElementTypeOther)[3]"))
														.getAttribute("name");
												globalcurrentCard = Cards.get(1);
												globalprevCard = Cards.get(0);
											}
										}
									} catch (Exception e1) {
										globalcurrentCard = Cards.get(1);
										cardName = "UnKnownCard";

									}

								}
							}
						} else {


							try {
								// cardName =
								// Cards.get(1).findElement(By.className("XCUIElementTypeStaticText")).getAttribute("name");
								cardName = Cards.get(1)
										.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"))
										.getAttribute("name");
								globalcurrentCard = Cards.get(1);
								globalprevCard = Cards.get(0);
							} catch (Exception ey) {
								try {
									try {
										// some times when big advertisement displayed, it doenst have name as
										// 'Advertisement' hence below implemented
										cardName = Cards.get(1)
												.findElement(
														By.xpath("//XCUIElementTypeOther[@name='ads-card-containerView']"))
												.getAttribute("name");
										globalcurrentCard = Cards.get(1);
										globalprevCard = Cards.get(0);
									} catch (Exception ex) {
										try {
											// when Integrated Feed card is displayed, it doenst have name as
											// 'Advertisement' hence below implemented
											cardName = Cards.get(1).findElement(By.xpath(
													"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']"))
													.getAttribute("name");
											globalcurrentCard = Cards.get(1);
											globalprevCard = Cards.get(0);
										} catch (Exception ex1) {
											// sometimes cards like planning card, planning ad card,..etc doesnt have
											// XCUIElementTypeStaticText element,
											cardName = Cards.get(1).findElement(By.xpath("(//XCUIElementTypeOther)[3]"))
													.getAttribute("name");
											globalcurrentCard = Cards.get(1);
											globalprevCard = Cards.get(0);
										}
									}
								} catch (Exception e1) {
									globalcurrentCard = Cards.get(1);
									cardName = "UnKnownCard";

								}

							}
						
						
						}
					
					cardName = getDerivedCardName(Cards.get(1), cardName);

					} else {
						// logStep("Ad card is not same");
					}

				}

				feedCardCurrentPosition++;
				cardsList.add(cardName);

				if (cardName.equalsIgnoreCase("nextgen-integrated-marquee-card-containerView")) {
					nextGenIMadDisplayed = true;
				} else if (cardName.equalsIgnoreCase("Rain")) {
					rainCardDisplayed = true;
				}

				try {
					if (cardName.equalsIgnoreCase("labelDescription")) {
						feedCardsMap.put(cardName + "_" + scrollend, "p" + feedCardCurrentPosition);
					} else {
						feedCardsMap.put(cardName, "p" + feedCardCurrentPosition);
					}
				} catch (Exception e) {
					System.out.println("There is an exception while adding card " + cardName + " to feedCardsMap ");
					logStep("There is an exception while adding card " + cardName + " to feedCardsMap ");
				}
				System.out.println("Current Card is : " + cardName);
				logStep("Current Card is : " + cardName);
				attachScreen();

				adcardname = cardName;

				cardName = shortCardName(cardName);

			} catch (Exception e) {
				logStep("There is an exception while fetching current card details");
				System.out.println("There is an exception while fetching current card details");
				System.out.println(e.getMessage());
				e.printStackTrace();
				logStep(e.getMessage());
				attachScreen();

			}

			if (scrollend >= 15) {
				boolean isFooterCard = false;
				try {
					ReadExcelValues.excelValues("Smoke", "General");
					if (Ad.findElementByName(ReadExcelValues.data[1][Cap]).isDisplayed()) {
						attachScreen();
						System.out.println("User done scrolling, Printing last 3 cards when Copyright text displayed");
						logStep("User done scrolling till last page, Printing last 3 cards when Copyright text displayed");

						try {
							// cardName =
							// Cards.get(1).findElement(By.className("XCUIElementTypeStaticText")).getAttribute("name");
							cardName = Cards.get(1)
									.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"))
									.getAttribute("name");
						} catch (Exception e) {
							try {
								// some times when big advertisement displayed, it doenst have name as
								// 'Advertisement' hence below implemented
								cardName = Cards.get(1)
										.findElement(By.xpath("//XCUIElementTypeOther[@name='ads-card-containerView']"))
										.getAttribute("name");

							} catch (Exception ex) {
								try {
									// when Integrated Feed card is displayed, it doenst have name as
									// 'Advertisement' hence below implemented
									cardName = Cards.get(1)
											.findElement(By.xpath(
													"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']"))
											.getAttribute("name");
								} catch (Exception ex1) {
									// sometimes cards like planning card, planning ad card,..etc doesnt have
									// XCUIElementTypeStaticText element,
									cardName = Cards.get(1).findElement(By.xpath("(//XCUIElementTypeOther)[3]"))
											.getAttribute("name");
								}
							}
						}
						if (cardName.equalsIgnoreCase("Advertisement")) {
							try {
								feedName = Cards.get(1).findElement(By.xpath(
										"//XCUIElementTypeStaticText[@name='Advertisement']//parent::XCUIElementTypeOther"))
										.getAttribute("name");
								cardName = feedName.replaceAll("-adContainerView", "");
							} catch (Exception e) {
								// following is added to handle Integrated Feed Card while getting card name.
								try {
									feedName = Cards.get(1).findElement(By.xpath(
											"//XCUIElementTypeStaticText[@name='Advertisement']//parent::XCUIElementTypeOther/following-sibling::XCUIElementTypeOther"))
											.getAttribute("name");
									cardName = feedName.replaceAll("-adContainerView", "");
								} catch (Exception e1) {
									cardName = "Advertisement";
								}
							}

						} else if (cardName.equalsIgnoreCase("ads-card-containerView")) {
							try {
								feedName = Cards.get(1).findElement(By.xpath(
										"//XCUIElementTypeOther[@name='ads-card-containerView']/XCUIElementTypeOther"))
										.getAttribute("name");
								cardName = feedName.replaceAll("-adContainerView", "");
							} catch (Exception e) {
								cardName = "Advertisement";
							}
						} else if (cardName.equalsIgnoreCase("integrated-ad-card-containerView")) {
							try {
								feedName = Cards.get(1).findElement(By.xpath(
										"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']//XCUIElementTypeOther[contains(@name,'-adContainerView')]"))
										.getAttribute("name");
								cardName = feedName.replaceAll("-adContainerView", "");
							} catch (Exception e) {
								cardName = "Advertisement";
							}
						}
						if (cardName.contains("Copyright")) {
							isFooterCard = true;
						}
						System.out.println(" 1st  Card is : " + cardName);
						logStep("1st  Card is : " + cardName);

						if (!isFooterCard) {
							try {
								// cardName =
								// Cards.get(2).findElement(By.className("XCUIElementTypeStaticText")).getAttribute("name");
								cardName = Cards.get(2)
										.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"))
										.getAttribute("name");
							} catch (Exception e) {
								try {
									// some times when big advertisement displayed, it doenst have name as
									// 'Advertisement' hence below implemented
									cardName = Cards.get(2)
											.findElement(
													By.xpath("//XCUIElementTypeOther[@name='ads-card-containerView']"))
											.getAttribute("name");

								} catch (Exception ex) {
									try {
										// when Integrated Feed card is displayed, it doenst have name as
										// 'Advertisement' hence below implemented
										cardName = Cards.get(2).findElement(By.xpath(
												"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']"))
												.getAttribute("name");
									} catch (Exception ex1) {
										// sometimes cards like planning card, planning ad card,..etc doesnt have
										// XCUIElementTypeStaticText element,
										cardName = Cards.get(2).findElement(By.xpath("(//XCUIElementTypeOther)[3]"))
												.getAttribute("name");
									}
								}
							}

							if (cardName.equalsIgnoreCase("Advertisement")) {
								try {
									feedName = Cards.get(2).findElement(By.xpath(
											"//XCUIElementTypeStaticText[@name='Advertisement']//parent::XCUIElementTypeOther"))
											.getAttribute("name");
									cardName = feedName.replaceAll("-adContainerView", "");
								} catch (Exception e) {
									// following is added to handle Integrated Feed Card while getting card name.
									try {
										feedName = Cards.get(2).findElement(By.xpath(
												"//XCUIElementTypeStaticText[@name='Advertisement']//parent::XCUIElementTypeOther/following-sibling::XCUIElementTypeOther"))
												.getAttribute("name");
										cardName = feedName.replaceAll("-adContainerView", "");
									} catch (Exception e1) {
										cardName = "Advertisement";
									}
								}

							} else if (cardName.equalsIgnoreCase("ads-card-containerView")) {
								try {
									feedName = Cards.get(2).findElement(By.xpath(
											"//XCUIElementTypeOther[@name='ads-card-containerView']/XCUIElementTypeOther"))
											.getAttribute("name");
									cardName = feedName.replaceAll("-adContainerView", "");
								} catch (Exception e) {
									cardName = "Advertisement";
								}
							} else if (cardName.equalsIgnoreCase("integrated-ad-card-containerView")) {
								try {
									feedName = Cards.get(2).findElement(By.xpath(
											"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']//XCUIElementTypeOther[contains(@name,'-adContainerView')]"))
											.getAttribute("name");
									cardName = feedName.replaceAll("-adContainerView", "");
								} catch (Exception e) {
									cardName = "Advertisement";
								}
							}
							if (cardName.contains("Copyright")) {
								isFooterCard = true;
							}

							System.out.println(" 2nd  Card is : " + cardName);
							logStep("2nd  Card is : " + cardName);

						}

						if (!isFooterCard) {
							try {
								// cardName =
								// Cards.get(2).findElement(By.className("XCUIElementTypeStaticText")).getAttribute("name");
								cardName = Cards.get(3)
										.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"))
										.getAttribute("name");
							} catch (Exception e) {
								try {
									// some times when big advertisement displayed, it doenst have name as
									// 'Advertisement' hence below implemented
									cardName = Cards.get(3)
											.findElement(
													By.xpath("//XCUIElementTypeOther[@name='ads-card-containerView']"))
											.getAttribute("name");

								} catch (Exception ex) {
									try {
										// when Integrated Feed card is displayed, it doenst have name as
										// 'Advertisement' hence below implemented
										cardName = Cards.get(2).findElement(By.xpath(
												"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']"))
												.getAttribute("name");
									} catch (Exception ex1) {
										// sometimes cards like planning card, planning ad card,..etc doesnt have
										// XCUIElementTypeStaticText element,
										cardName = Cards.get(2).findElement(By.xpath("(//XCUIElementTypeOther)[3]"))
												.getAttribute("name");
									}
								}
							}
							if (cardName.equalsIgnoreCase("Advertisement")) {
								try {
									feedName = Cards.get(3).findElement(By.xpath(
											"//XCUIElementTypeStaticText[@name='Advertisement']//parent::XCUIElementTypeOther"))
											.getAttribute("name");
									cardName = feedName.replaceAll("-adContainerView", "");
								} catch (Exception e) {
									// following is added to handle Integrated Feed Card while getting card name.
									try {
										feedName = Cards.get(3).findElement(By.xpath(
												"//XCUIElementTypeStaticText[@name='Advertisement']//parent::XCUIElementTypeOther/following-sibling::XCUIElementTypeOther"))
												.getAttribute("name");
										cardName = feedName.replaceAll("-adContainerView", "");
									} catch (Exception e1) {
										cardName = "Advertisement";
									}
								}

							} else if (cardName.equalsIgnoreCase("ads-card-containerView")) {
								try {
									feedName = Cards.get(3).findElement(By.xpath(
											"//XCUIElementTypeOther[@name='ads-card-containerView']/XCUIElementTypeOther"))
											.getAttribute("name");
									cardName = feedName.replaceAll("-adContainerView", "");
								} catch (Exception e) {
									cardName = "Advertisement";
								}
							} else if (cardName.equalsIgnoreCase("integrated-ad-card-containerView")) {
								try {
									feedName = Cards.get(3).findElement(By.xpath(
											"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']//XCUIElementTypeOther[contains(@name,'-adContainerView')]"))
											.getAttribute("name");
									cardName = feedName.replaceAll("-adContainerView", "");
								} catch (Exception e) {
									cardName = "Advertisement";
								}

							}

							if (cardName.contains("Copyright")) {
								isFooterCard = true;
							}
							System.out.println(" 3rd  Card is : " + cardName);
							logStep("3rd  Card is : " + cardName);

						}

						break;
					}
				} catch (Exception e) {
					System.out.println("last page not found, Need to scrol till the end");
					logStep("last page not found, Need to scrol till the end");
				}
			}
			if (cardName.contains("weather.feed") || cardName.contains("Advertisement")) {
				feedAdCount++;
			}
			// Capturing the Height of the Card, as card looses the reference when it comes
			// back after navigating to content page.
			try {
				cardHeight = Functions.getFeedCardSize(globalcurrentCard);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("An Exception while fetching card height");
				logStep("An Exception while fetching card height");
			}

			if (includeDetailsPages && !cardName.contains("weather.feed")) {
				try {

					if (cardName.equalsIgnoreCase("lifestyle")) {
						lStyleCardScreen.navigateToLifeStyleCardIndexes();
						if (navigateTwiceToDetailsPages) {
							lStyleCardScreen.navigateToLifeStyleCardIndexes();
						}
					} else if (cardName.equalsIgnoreCase("seasonalhub")) {
						//sHubCardScreen.navigateToFirstIndexOfSeasonalHubCard();
						sHubCardScreen.navigateToSeasonalHubCardIndexes();
						if (navigateTwiceToDetailsPages) {
							//sHubCardScreen.navigateToFirstIndexOfSeasonalHubCard();
							sHubCardScreen.navigateToSeasonalHubCardIndexes();
						}
					} else if (cardName.equalsIgnoreCase("daily")) {
						dCardScreen.navigateToDailyCardContentPage();
						if (navigateTwiceToDetailsPages) {
							dCardScreen.navigateToDailyCardContentPage();
						}
					} else if (cardName.equalsIgnoreCase("hurricane-central")) {
						// navigate to hurricanecentral card content page
					} else if (cardName.equalsIgnoreCase("radar.largead")) {
						rCardScreen.navigateToRadarCardContentPage();
						if (navigateTwiceToDetailsPages) {
							rCardScreen.navigateToRadarCardContentPage();
						}
					} else if (cardName.equalsIgnoreCase("snapshot")) {
						// navigate to snapshot card content page
					} else if (cardName.equalsIgnoreCase("today")) {
						tCardScreen.navigateToTodayCardContentPage();
						if (navigateTwiceToDetailsPages) {
							tCardScreen.navigateToTodayCardContentPage();
						}
					} else if (cardName.equalsIgnoreCase("video")) {
						vCardScreen.navigateToVideoCardContentPage();
						if (navigateTwiceToDetailsPages) {
							vCardScreen.navigateToVideoCardContentPage();
						}

					} else if (cardName.equalsIgnoreCase("aq")) {
						aqCardScreen.navigateToAirQualityCardContentPage();
						if (navigateTwiceToDetailsPages) {
							aqCardScreen.navigateToAirQualityCardContentPage();
						}

					} else if (cardName.equalsIgnoreCase("news")) {
						nCardScreen.navigateToNewsCardContentPage();
						if (navigateTwiceToDetailsPages) {
							nCardScreen.navigateToNewsCardContentPage();
						}

					} else if (cardName.equalsIgnoreCase("watson-allergy")) {
						// wCardScreen.navigateToWatsonCardContentPage(includeDetailsPages);

					} else if (cardName.equalsIgnoreCase("watson-cold-and-flu")) {
						// wCardScreen.navigateToWatsonCardContentPage(includeDetailsPages);

					}

				} catch (Exception e) {

				}
			}

			try {
				System.out.println("Swiping on feed card: " + cardName);
				logStep("Swiping on feed card: " + cardName);
				getFeedCardSizeandSwipewithNoMargin(cardHeight);
				// swipe_Up();
				// scroll_Down();
			} catch (Exception e) {
				attachScreen();
				String[] strcleariProxy = { "/bin/bash", "-c", "killall iproxy xcodebuild XCTRunner" };
				Process proc = Runtime.getRuntime().exec(strcleariProxy);
				int rc = proc.waitFor();
				Assert.fail("Scrolling filed, need to execute test Case again	");
			}
		}
		System.out.println("Feed Cards Map is: " + feedCardsMap);
		logStep("Feed Cards Map is: " + feedCardsMap);
		System.out.println("Cards List: " + cardsList);
		logStep("Cards List: " + cardsList);
		System.out.println("Total no of Feed Cards counted are :" + feedAdCount);
		logStep("Total no of Feed Cards counted are :" + feedAdCount);

		// navigate to Daily tab, since Home tab doesnt function, navigating first to
		// daily tab. this can be removed once the issue resolved
		// navigateToDailyTab();

		hmTab.clickonHomeTab();
		TestBase.waitForMilliSeconds(2000);
		/*
		 * // navigate to Hourly tab navigateToHourlyTab(); TestBase.waitForMilliSeconds(2000);
		 * 
		 * // navigate to Daily tab navigateToDailyTab(); TestBase.waitForMilliSeconds(2000);
		 * 
		 * // navigate to Video tab navigateToVideoTab(); TestBase.waitForMilliSeconds(2000);
		 */

		// navigate to Alerts
		alertScreen.navigateToMyAlerts();
		if (navigateTwiceToDetailsPages) {	
			alertScreen.navigateToMyAlerts();
		}
		TestBase.waitForMilliSeconds(2000);

		hmTab.clickonHomeTab();
	}
	
	
	/**
	 * Navigate to All Feed Cards and its content pages by choice
	 * 
	 * @param includeDetailsPages
	 * @param navigateTwiceToDetailsPages
	 * @throws Exception
	 */
	public static void navigateToAllCards(boolean includeDetailsPages, boolean navigateTwiceToDetailsPages) throws Exception {
		HomeNavTab hmTab = new HomeNavTab(Ad);
		HourlyNavTab hrTab = new HourlyNavTab(Ad);
		DailyNavTab dTab = new DailyNavTab(Ad);
		RadarNavTab rTab = new RadarNavTab(Ad);
		VideoNavTab vTab = new VideoNavTab(Ad);
		AlertCenterScreen alertScreen = new AlertCenterScreen(Ad);
		
		ReadExcelValues.excelValues("Smoke", "General");
		By byFooterCard = MobileBy.name(ReadExcelValues.data[1][Cap]);
		
	
		hmTab.clickonHomeTab();
		TestBase.waitForMilliSeconds(2000);
		// navigate to Hourly tab
		//hrTab.navigateToHourlyTab();
		hrTab.navigateToHourlyTabAndHandleInterstitialAd();
		TestBase.waitForMilliSeconds(2000);
		// navigate to Daily tab
		dTab.navigateToDailyTab();
		TestBase.waitForMilliSeconds(2000);
		// navigate to Radar tab
		rTab.navigateToRadarTab();
		TestBase.waitForMilliSeconds(2000);
		// navigate to Video tab
		vTab.navigateToVideoTab();
		TestBase.waitForMilliSeconds(2000);
		// clickonHomeTab
		hmTab.clickonHomeTab();
		TestBase.waitForMilliSeconds(5000);

		System.out.println("User on Current Conditions Card and trying to scroll the app till end");
		logStep("User on Current Conditions Card and trying to scroll the app till end");
		
		//By byNextGenIMCard = MobileBy.xpath("//XCUIElementTypeOther[@name='nextgen-integrated-marquee-card-containerView']");
		//nextGenIMadDisplayed = isDisplayed(byNextGenIMCard);
		
		if (includeDetailsPages) {
			Functions.genericScrollTWC(byFooterCard, true, false, getOffsetYTop(), TOLERANCE_FROM_TOP, includeDetailsPages, navigateTwiceToDetailsPages);
		} else {
			Functions.genericScroll(byFooterCard, true, false, getOffsetYTop(), TOLERANCE_FROM_TOP);
		}
		
		
		hmTab.clickonHomeTab();
		TestBase.waitForMilliSeconds(2000);
		
		// navigate to Alerts
		alertScreen.navigateToMyAlerts();
		if (navigateTwiceToDetailsPages) {	
			alertScreen.navigateToMyAlerts();
		}
		TestBase.waitForMilliSeconds(2000);

		hmTab.clickonHomeTab();
	}
	
	/**
	 * Navigate to All Feed Cards and its content pages by choice
	 * 
	 * @param includeDetailsPages
	 * @param navigateTwiceToDetailsPages
	 * @throws Exception
	 */
	public static void navigateToAllCardsAndroid(boolean includeDetailsPages, boolean navigateTwiceToDetailsPages) throws Exception {
		
		//By byFooterCard = MobileBy.xpath("//android.widget.TextView[@text=\"© Copyright TWC Product and Technology, LLC 2011, 2022\"]");
		By byhourlyCard = MobileBy.xpath("//android.widget.FrameLayout[@resource-id=\"com.weather.Weather:id/hourly_forecast_card_view\"]");
		By bydailyCard = MobileBy.xpath("//android.widget.FrameLayout[@resource-id=\"com.weather.Weather:id/card_daily_forecast_view\"]");
		By bybNewsCard = MobileBy.xpath("//android.widget.FrameLayout[@resource-id=\"com.weather.Weather:id/card_breaking_news_view\"]");
		By byTodaysDetailsCard = MobileBy.xpath("//android.widget.TextView[@resource-id=\"com.weather.Weather:id/header_title\" and @text=\"Today's Details\"]");
		//By bySeasonalHubCard = MobileBy.xpath("//android.widget.TextView[@resource-id=\"com.weather.Weather:id/header_title\" and @text=\"Today's Details\"]");
		
		ReadExcelValues.excelValues("Smoke", "General");
		String footerText = ReadExcelValues.data[1][Cap];
			
		By byFooterCard = MobileBy.xpath("//android.widget.TextView[@text='"+footerText+"']");
		
		AndroidHomeNavTab hmTab = new AndroidHomeNavTab(Ad);
		AndroidHourlyNavTab hrTab = new AndroidHourlyNavTab(Ad);
		AndroidDailyNavTab dTab = new AndroidDailyNavTab(Ad);
		AndroidRadarNavTab rTab = new AndroidRadarNavTab(Ad);
		AndroidVideoNavTab vTab = new AndroidVideoNavTab(Ad);
		AndroidAlertCenterScreen alertScreen = new AndroidAlertCenterScreen(Ad);
		

		hmTab.clickonHomeTab();
		TestBase.waitForMilliSeconds(2000);
		// navigate to Hourly tab
		//hrTab.navigateToHourlyTab();
		hrTab.navigateToHourlyTabAndHandleInterstitialAd();
		TestBase.waitForMilliSeconds(2000);
		// navigate to Daily tab
		//dTab.navigateToDailyTab();
		dTab.navigateToDailyTabAndHandleInterstitialAd();
		TestBase.waitForMilliSeconds(2000);
		// navigate to Radar tab
		//rTab.navigateToRadarTab();
		rTab.navigateToRadarTabAndHandleInterstitialAd();
		TestBase.waitForMilliSeconds(2000);
		// navigate to Video tab
		//vTab.navigateToVideoTab();
		vTab.navigateToVideoTabAndHandleInterstitialAd();
		TestBase.waitForMilliSeconds(2000);
		// clickonHomeTab
		hmTab.clickonHomeTab();
		TestBase.waitForMilliSeconds(5000);

		System.out.println("User on Current Conditions Card and trying to scroll the app till end");
		logStep("User on Current Conditions Card and trying to scroll the app till end");
		
		//Functions.genericScroll(byFooterCard, true, true, getOffsetYTop(), TOLERANCE_FROM_TOP);
		
		if (includeDetailsPages) {
			Functions.genericScrollTWCAndroid(byFooterCard, true, false, getOffsetYTop(), TOLERANCE_FROM_TOP, includeDetailsPages, navigateTwiceToDetailsPages);
		} else {
			Functions.genericScroll(byFooterCard, true, false, getOffsetYTop(), TOLERANCE_FROM_TOP);
		}
		
		hmTab.clickonHomeTab();
		TestBase.waitForMilliSeconds(2000);
		
		// navigate to Alerts
		alertScreen.navigateToMyAlerts();
		if (navigateTwiceToDetailsPages) {	
			alertScreen.navigateToMyAlerts();
		}
		TestBase.waitForMilliSeconds(2000);

		hmTab.clickonHomeTab();
		
	}

	/**
	 * Navigate to Specific Feed Card by choice
	 * 
	 * @param feedCard
	 * @throws Exception
	 */
	public static void navigateTofeedCardOld(String feedCard) throws Exception {
		HomeNavTab hmTab = new HomeNavTab(Ad);
		CurrentConditionsCardScreen cConditionsCardScreen = new CurrentConditionsCardScreen(Ad);

		String cardName = "homescreen";
		String adcardname = "Test Card";
		String feedName = null;
		MobileElement adele;
		boolean cardFound = false;
		MobileElement allCards = null;
		List<MobileElement> Cards = null;
		ArrayList<String> cardsList = new ArrayList<String>();
		nextGenIMadDisplayed = false;
		rainCardDisplayed = false;
		int u = 1;

		ReadExcelValues.excelValues("Smoke", "General");

		// Since HomeTab button not functioning in 11.0+ builds when user on any feed
		// card, hence first clicking on daily tab before clicking on Home tab.
		// navigateToDailyTab();
		// clickonHomeTab();
		hmTab.clickonHomeTab();
		
		TestBase.waitForMilliSeconds(5000);

		System.out.println("User on Current Conditions Card and trying to scroll the app till end");
		logStep("User on Current Conditions Card and trying to scroll the app till end");
		// scroll_Down();
		// scroll_Down();

		try {
			cConditionsCardScreen.swipe_Up_CurrentConditionCard();
			TestBase.waitForMilliSeconds(2000);
		} catch (Exception e) {
			System.out.println("There is an error in swiping Current Conditions Card, it might not have displayed");
			logStep("There is an error in swiping Current Conditions Card, it might not have displayed");
		}

		for (int scrollend = 1; scrollend <= 50; scrollend++) {
			System.out.println("=================================================================");
			logStep("=================================================================");
			System.out.println("Current iteration is: " + scrollend);
			logStep("Current iteration is: " + scrollend);
			/*
			 * try{ if(Ad.findElementByName(readExcelValues.data[1][Cap]).isDisplayed()){
			 * attachScreen(); System.out.println("User done scrolling");
			 * logStep("User done scrolling till last page"); break; } } catch(Exception e){
			 * System.out.println("last page not found, Need to scrol till the end");
			 */
			try {
				allCards = Ad.findElementByClassName("XCUIElementTypeCollectionView");
				Cards = allCards.findElementsByClassName("XCUIElementTypeCell");

				if (scrollend == 1) {

					for (int ccount = 0; ccount <= 1; ccount++) {
						boolean matchFound = false;

						try {
							// cardName =
							// Cards.get(ccount).findElement(By.className("XCUIElementTypeStaticText")).getAttribute("name");
														
							try {
								cardName = Cards.get(ccount)
										.findElement(By.xpath("//XCUIElementTypeOther[@name='nextgen-integrated-marquee-card-containerView']"))
										.getAttribute("name");
							} catch (Exception e) {
								try {
									cardName = Cards.get(ccount)
											.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"))
											.getAttribute("name");
								} catch (Exception e1) {
									cardName = Cards.get(ccount).findElement(By.xpath("(//XCUIElementTypeOther)[3]"))
											.getAttribute("name");
								}
							}
							
							globalcurrentCard = Cards.get(ccount);

							/*
							 * MobileElement currentCardElement = Cards.get(ccount)
							 * .findElementByAccessibilityId("current-condition-updated-card-containerView")
							 * ;
							 */
							MobileElement currentCardElement = cConditionsCardScreen
									.returnCurrentConditionCard(Cards.get(ccount));
							matchFound = true;
						} catch (Exception e) {

						}

						if (matchFound == true) {
							System.out.println("First feed card not yet derived.. continuing...");
							logStep("First feed card not yet derived.. continuing...");
						} else {
							cardName = getDerivedCardName(Cards.get(ccount), cardName);
							System.out.println("First feed card derived...and Card Name is: " + cardName);
							logStep("First feed card derived...and Card Name is: " + cardName);
							break;
						}
					}

				} else {
					TestBase.waitForMilliSeconds(10000);// waiting for advertisements to load
					try {
						cardName = Cards.get(0)
								.findElement(By.xpath("//XCUIElementTypeOther[@name='nextgen-integrated-marquee-card-containerView']"))
								.getAttribute("name");
						globalcurrentCard = Cards.get(0);
					} catch(Exception e) {
						try {
							// cardName =
							// Cards.get(0).findElement(By.className("XCUIElementTypeStaticText")).getAttribute("name");
							cardName = Cards.get(0)
									.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"))
									.getAttribute("name");
							globalcurrentCard = Cards.get(0);
						} catch (Exception ey) {
							try {
								try {
									// some times when big advertisement displayed, it doenst have name as
									// 'Advertisement' hence below implemented
									cardName = Cards.get(0)
											.findElement(By.xpath("//XCUIElementTypeOther[@name='ads-card-containerView']"))
											.getAttribute("name");
									globalcurrentCard = Cards.get(0);

								} catch (Exception ex) {
									try {
										// when Integrated Feed card is displayed, it doenst have name as
										// 'Advertisement' hence below implemented
										cardName = Cards.get(0)
												.findElement(By.xpath(
														"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']"))
												.getAttribute("name");
										globalcurrentCard = Cards.get(0);

									} catch (Exception ex1) {
										// sometimes cards like planning card, planning ad card,..etc doesnt have
										// XCUIElementTypeStaticText element,
										cardName = Cards.get(0).findElement(By.xpath("(//XCUIElementTypeOther)[3]"))
												.getAttribute("name");
										globalcurrentCard = Cards.get(0);

									}

								}
							} catch (Exception e1) {
								globalcurrentCard = Cards.get(0);
								cardName = "UnKnownCard";

							}

						}
						
					}
								
					cardName = getDerivedCardName(Cards.get(0), cardName);

					if (adcardname.equals(cardName)) {
						try {
							cardName = Cards.get(1)
									.findElement(By.xpath("//XCUIElementTypeOther[@name='nextgen-integrated-marquee-card-containerView']"))
									.getAttribute("name");
							globalcurrentCard = Cards.get(1);
							globalprevCard = Cards.get(0);
						} catch (Exception e) {
							try {
								// cardName =
								// Cards.get(1).findElement(By.className("XCUIElementTypeStaticText")).getAttribute("name");
								cardName = Cards.get(1)
										.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"))
										.getAttribute("name");
								globalcurrentCard = Cards.get(1);
								globalprevCard = Cards.get(0);
							} catch (Exception ey) {
								try {
									try {
										// some times when big advertisement displayed, it doenst have name as
										// 'Advertisement' hence below implemented
										cardName = Cards.get(1)
												.findElement(
														By.xpath("//XCUIElementTypeOther[@name='ads-card-containerView']"))
												.getAttribute("name");
										globalcurrentCard = Cards.get(1);
										globalprevCard = Cards.get(0);
									} catch (Exception ex) {
										try {
											// when Integrated Feed card is displayed, it doenst have name as
											// 'Advertisement' hence below implemented
											cardName = Cards.get(1).findElement(By.xpath(
													"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']"))
													.getAttribute("name");
											globalcurrentCard = Cards.get(1);
											globalprevCard = Cards.get(0);
										} catch (Exception ex1) {
											// sometimes cards like planning card, planning ad card,..etc doesnt have
											// XCUIElementTypeStaticText element,
											cardName = Cards.get(1).findElement(By.xpath("(//XCUIElementTypeOther)[3]"))
													.getAttribute("name");
											globalcurrentCard = Cards.get(1);
											globalprevCard = Cards.get(0);
										}

									}
								} catch (Exception e1) {
									globalcurrentCard = Cards.get(1);
									cardName = "UnKnownCard";

								}

							}
							
						}
						
					cardName = getDerivedCardName(Cards.get(1), cardName);

					} else {
						// logStep("Ad card is not same");
					}

				}
				
				if (cardName.equalsIgnoreCase("nextgen-integrated-marquee-card-containerView")) {
					nextGenIMadDisplayed = true;
				} else if (cardName.equalsIgnoreCase("Rain")) {
					rainCardDisplayed = true;
				}

				cardsList.add(cardName);

				System.out.println("Current Card is : " + cardName);
				logStep("Current Card is : " + cardName);
				attachScreen();
				adcardname = cardName;

				cardName = shortCardName(cardName);

			} catch (Exception e2) {
				logStep(cardName + " Card not available");
				logStep(e2.getMessage());
				attachScreen();
			}
			
			try {

				if (cardName.equalsIgnoreCase("Rain") && feedCard.equalsIgnoreCase("radar.largead")) {
					
					System.out.println("Since Rain Card appears, Map Card will not be displayed, hence skipping the navigation and further validation");
					logStep("Since Rain Card appears, Map Card will not be displayed, hence skipping the navigation and further validation");

					break;
				}
			} catch (Exception e) {

			}

			try {

				if (cardName.equalsIgnoreCase(feedCard)) {
					cardFound = true;
					System.out.println("Navigated to card: " + feedCard);
					logStep("Navigated to card : " + cardName);

					if (cardName.equalsIgnoreCase("lifestyle")) {

					} else if (cardName.equalsIgnoreCase("seasonalhub")) {

					} else if (cardName.equalsIgnoreCase("daily")) {

					} else if (cardName.equalsIgnoreCase("hurricane-central")) {

					} else if (cardName.equalsIgnoreCase("radar.largead")) {

					} else if (cardName.equalsIgnoreCase("today")) {

					} else if (cardName.equalsIgnoreCase("video")) {

					} else if (cardName.equalsIgnoreCase("aq")) {

					} else if (cardName.equalsIgnoreCase("news")) {

					} else if (cardName.equalsIgnoreCase("privacy")) {

					}

					break;
				}
			} catch (Exception e) {

			}

			if (scrollend >= 15) {
				boolean isFooterCard = false;
				try {
					if (Ad.findElementByName(ReadExcelValues.data[1][Cap]).isDisplayed()) {
						attachScreen();
						System.out.println("User done scrolling, Printing last 3 cards when Copyright text displayed");
						logStep("User done scrolling till last page, Printing last 3 cards when Copyright text displayed");

						try {
							// cardName =
							// Cards.get(1).findElement(By.className("XCUIElementTypeStaticText")).getAttribute("name");
							cardName = Cards.get(1)
									.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"))
									.getAttribute("name");
						} catch (Exception e) {
							try {
								// some times when big advertisement displayed, it doenst have name as
								// 'Advertisement' hence below implemented
								cardName = Cards.get(1)
										.findElement(By.xpath("//XCUIElementTypeOther[@name='ads-card-containerView']"))
										.getAttribute("name");

							} catch (Exception ex) {
								try {
									// when Integrated Feed card is displayed, it doenst have name as
									// 'Advertisement' hence below implemented
									cardName = Cards.get(1)
											.findElement(By.xpath(
													"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']"))
											.getAttribute("name");
								} catch (Exception ex1) {
									// sometimes cards like planning card, planning ad card,..etc doesnt have
									// XCUIElementTypeStaticText element,
									cardName = Cards.get(1).findElement(By.xpath("(//XCUIElementTypeOther)[3]"))
											.getAttribute("name");
								}
							}
						}
						if (cardName.equalsIgnoreCase("Advertisement")) {
							try {
								feedName = Cards.get(1).findElement(By.xpath(
										"//XCUIElementTypeStaticText[@name='Advertisement']//parent::XCUIElementTypeOther"))
										.getAttribute("name");
								cardName = feedName.replaceAll("-adContainerView", "");
							} catch (Exception e) {
								// following is added to handle Integrated Feed Card while getting card name.
								try {
									feedName = Cards.get(1).findElement(By.xpath(
											"//XCUIElementTypeStaticText[@name='Advertisement']//parent::XCUIElementTypeOther/following-sibling::XCUIElementTypeOther"))
											.getAttribute("name");
									cardName = feedName.replaceAll("-adContainerView", "");
								} catch (Exception e1) {
									cardName = "Advertisement";
								}
							}

						} else if (cardName.equalsIgnoreCase("ads-card-containerView")) {
							try {
								feedName = Cards.get(1).findElement(By.xpath(
										"//XCUIElementTypeOther[@name='ads-card-containerView']/XCUIElementTypeOther"))
										.getAttribute("name");
								cardName = feedName.replaceAll("-adContainerView", "");
							} catch (Exception e) {
								cardName = "Advertisement";
							}
						} else if (cardName.equalsIgnoreCase("integrated-ad-card-containerView")) {
							try {
								feedName = Cards.get(1).findElement(By.xpath(
										"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']//XCUIElementTypeOther[contains(@name,'-adContainerView')]"))
										.getAttribute("name");
								cardName = feedName.replaceAll("-adContainerView", "");
							} catch (Exception e) {
								cardName = "Advertisement";
							}
						}
						if (cardName.contains("Copyright")) {
							isFooterCard = true;
						}
						System.out.println(" 1st  Card is : " + cardName);
						logStep("1st  Card is : " + cardName);
						cardName = shortCardName(cardName);
						if (cardName.equalsIgnoreCase(feedCard)) {
							cardFound = true;
						}

						if (!isFooterCard) {
							try {
								// cardName =
								// Cards.get(2).findElement(By.className("XCUIElementTypeStaticText")).getAttribute("name");
								cardName = Cards.get(2)
										.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"))
										.getAttribute("name");
							} catch (Exception e) {
								try {
									// some times when big advertisement displayed, it doenst have name as
									// 'Advertisement' hence below implemented
									cardName = Cards.get(2)
											.findElement(
													By.xpath("//XCUIElementTypeOther[@name='ads-card-containerView']"))
											.getAttribute("name");

								} catch (Exception ex) {
									try {
										// when Integrated Feed card is displayed, it doenst have name as
										// 'Advertisement' hence below implemented
										cardName = Cards.get(2).findElement(By.xpath(
												"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']"))
												.getAttribute("name");
									} catch (Exception ex1) {
										// sometimes cards like planning card, planning ad card,..etc doesnt have
										// XCUIElementTypeStaticText element,
										cardName = Cards.get(2).findElement(By.xpath("(//XCUIElementTypeOther)[3]"))
												.getAttribute("name");
									}
								}
							}

							if (cardName.equalsIgnoreCase("Advertisement")) {
								try {
									feedName = Cards.get(2).findElement(By.xpath(
											"//XCUIElementTypeStaticText[@name='Advertisement']//parent::XCUIElementTypeOther"))
											.getAttribute("name");
									cardName = feedName.replaceAll("-adContainerView", "");
								} catch (Exception e) {
									// following is added to handle Integrated Feed Card while getting card name.
									try {
										feedName = Cards.get(2).findElement(By.xpath(
												"//XCUIElementTypeStaticText[@name='Advertisement']//parent::XCUIElementTypeOther/following-sibling::XCUIElementTypeOther"))
												.getAttribute("name");
										cardName = feedName.replaceAll("-adContainerView", "");
									} catch (Exception e1) {
										cardName = "Advertisement";
									}
								}

							} else if (cardName.equalsIgnoreCase("ads-card-containerView")) {
								try {
									feedName = Cards.get(2).findElement(By.xpath(
											"//XCUIElementTypeOther[@name='ads-card-containerView']/XCUIElementTypeOther"))
											.getAttribute("name");
									cardName = feedName.replaceAll("-adContainerView", "");
								} catch (Exception e) {
									cardName = "Advertisement";
								}
							} else if (cardName.equalsIgnoreCase("integrated-ad-card-containerView")) {
								try {
									feedName = Cards.get(2).findElement(By.xpath(
											"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']//XCUIElementTypeOther[contains(@name,'-adContainerView')]"))
											.getAttribute("name");
									cardName = feedName.replaceAll("-adContainerView", "");
								} catch (Exception e) {
									cardName = "Advertisement";
								}
							}
							if (cardName.contains("Copyright")) {
								isFooterCard = true;
							}

							System.out.println(" 2nd  Card is : " + cardName);
							logStep("2nd  Card is : " + cardName);
							cardName = shortCardName(cardName);

							if (cardName.equalsIgnoreCase(feedCard)) {
								cardFound = true;
							}
						}

						if (!isFooterCard) {
							try {
								// cardName =
								// Cards.get(2).findElement(By.className("XCUIElementTypeStaticText")).getAttribute("name");
								cardName = Cards.get(3)
										.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"))
										.getAttribute("name");
							} catch (Exception e) {
								try {
									// some times when big advertisement displayed, it doenst have name as
									// 'Advertisement' hence below implemented
									cardName = Cards.get(3)
											.findElement(
													By.xpath("//XCUIElementTypeOther[@name='ads-card-containerView']"))
											.getAttribute("name");

								} catch (Exception ex) {
									try {
										// when Integrated Feed card is displayed, it doenst have name as
										// 'Advertisement' hence below implemented
										cardName = Cards.get(3).findElement(By.xpath(
												"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']"))
												.getAttribute("name");
									} catch (Exception ex1) {
										// sometimes cards like planning card, planning ad card,..etc doesnt have
										// XCUIElementTypeStaticText element,
										cardName = Cards.get(3).findElement(By.xpath("(//XCUIElementTypeOther)[3]"))
												.getAttribute("name");
									}
								}
							}
							if (cardName.equalsIgnoreCase("Advertisement")) {
								try {
									feedName = Cards.get(3).findElement(By.xpath(
											"//XCUIElementTypeStaticText[@name='Advertisement']//parent::XCUIElementTypeOther"))
											.getAttribute("name");
									cardName = feedName.replaceAll("-adContainerView", "");
								} catch (Exception e) {
									// following is added to handle Integrated Feed Card while getting card name.
									try {
										feedName = Cards.get(3).findElement(By.xpath(
												"//XCUIElementTypeStaticText[@name='Advertisement']//parent::XCUIElementTypeOther/following-sibling::XCUIElementTypeOther"))
												.getAttribute("name");
										cardName = feedName.replaceAll("-adContainerView", "");
									} catch (Exception e1) {
										cardName = "Advertisement";
									}
								}

							} else if (cardName.equalsIgnoreCase("ads-card-containerView")) {
								try {
									feedName = Cards.get(3).findElement(By.xpath(
											"//XCUIElementTypeOther[@name='ads-card-containerView']/XCUIElementTypeOther"))
											.getAttribute("name");
									cardName = feedName.replaceAll("-adContainerView", "");
								} catch (Exception e) {
									cardName = "Advertisement";
								}
							} else if (cardName.equalsIgnoreCase("integrated-ad-card-containerView")) {
								try {
									feedName = Cards.get(3).findElement(By.xpath(
											"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']//XCUIElementTypeOther[contains(@name,'-adContainerView')]"))
											.getAttribute("name");
									cardName = feedName.replaceAll("-adContainerView", "");
								} catch (Exception e) {
									cardName = "Advertisement";
								}

							}

							if (cardName.contains("Copyright")) {
								isFooterCard = true;
							}
							System.out.println(" 3rd  Card is : " + cardName);
							logStep("3rd  Card is : " + cardName);
							cardName = shortCardName(cardName);
							if (cardName.equalsIgnoreCase(feedCard)) {
								cardFound = true;
							}
						}

						break;
					}
				} catch (Exception e) {
					System.out.println("last page not found, Need to scrol till the end");
					logStep("last page not found, Need to scrol till the end");
				}
			}
			try {
				System.out.println("Swiping on feed card: " + cardName);
				logStep("Swiping on feed card: " + cardName);
				getFeedCardSizeandSwipewithNoMargin();

			} catch (Exception e) {
				attachScreen();
				String[] strcleariProxy = { "/bin/bash", "-c", "killall iproxy xcodebuild XCTRunner" };
				Process proc = Runtime.getRuntime().exec(strcleariProxy);
				int rc = proc.waitFor();
				Assert.fail("Scrolling filed, need to execute test Case again");
			}

		}
		System.out.println("Cards List: " + cardsList);
		logStep("Cards List: " + cardsList);
		if (cardFound == false) {
			if (cardName.equalsIgnoreCase("Rain") && feedCard.equalsIgnoreCase("radar.largead")) {
				System.out.println("Since Rain Card appears, Map Card will not be displayed, hence skipping the navigation and further validation");
				logStep("Since Rain Card appears, Map Card will not be displayed, hence skipping the navigation and further validation");
			} else {
				System.out.println("Corresponding card: " + feedCard + " is not found, hence navigation is failed");
				logStep("Corresponding card: " + feedCard + " is not found, hence navigation is failed");
				Assert.fail("Corresponding card: " + feedCard + " is not found");
			}
			
		}

	}
	
	/**
	 * Navigate to Specified Card of Given Name, by swiping through each card at once
	 * @param feedCard
	 * @param includeDetailsPages
	 * @param navigateTwiceToDetailsPages
	 * @throws java.lang.Exception
	 */
	public static void navigateTofeedCard(String feedCardName, boolean includeDetailsPages, boolean navigateTwiceToDetailsPages) throws java.lang.Exception {
		Functions.genericScrollTWC(feedCardName, true, true, getOffsetYTop(), TOLERANCE_FROM_TOP, includeDetailsPages, navigateTwiceToDetailsPages);
	}

	/**
	 * Load Feed Cards to Map
	 */
	public static void loadFeedAdCardsToMap() {
		for (Map.Entry m : feedCardsMap.entrySet()) {
			// System.out.println(m.getKey()+" : "+m.getValue());
			String currentKey = m.getKey().toString();
			if (currentKey.contains("feed")) {
				String[] cKeySplit = currentKey.split("\\.");
				String feed = cKeySplit[1];
				String feedPos = m.getValue().toString();
				System.out.println("Feed Ad Card Name: " + feed);
				logStep("Feed Ad Card Name: " + feed);
				System.out.println("Feed Ad Card pos value is: " + feedPos);
				logStep("Feed Ad Card pos value is: " + feedPos);
				feedAdCardsMap.put(feed, feedPos);
			}
		}
	}

	/**
	 * Verify Specific custom parameter value of all existed new feed ads (i.e.
	 * feed1, feed2,....etc)
	 * 
	 * @param excelName
	 * @param sheetName
	 * @param cust_param
	 * @throws Exception
	 */
	public static void validate_custom_param_val_of_newFeedAds(String excelName, String sheetName, String cust_param)
			throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);

		// Read the content form file
		File fXmlFile = new File(outfile.getName());
		// File fXmlFile = outfile;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		// NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		// Getting custom_params amzn_b values
		// List<String> customParamsList = new ArrayList<String>();

		// String iuId =
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		// String iuId = "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fcard%2Fdaily";

		// String iuId = null;
		boolean iuExists = false;
		String cardsNotPresent = "";
		int failCount = 0;
		String cardsCustParamNotMatched = "";
		int custParamFailCount = 0;
		String tempCustmParam = null;
		loadFeedAdCardsToMap();
		for (Map.Entry m : feedAdCardsMap.entrySet()) {
			String feedCardNo = m.getKey().toString().trim().substring(4);
			String currentFeedAd_Card = "feed_" + feedCardNo;
			String iuId = ReadExcelValues.data[5][Cap] + currentFeedAd_Card;
			String expCustParam_val = m.getValue().toString();
			tempCustmParam = null;
			iuExists = false;
			for (String qry : getQueryList) {
				if (qry.contains(iuId)) {
					tempCustmParam = getCustomParamBy_iu_value(qry, cust_param);
					// if (!"".equals(tempCustmParam))
					// customParamsList.add(getCustomParamsBy_iu_value(qry));
					iuExists = true;
					break;
				} else {
					if (feedCardNo.contentEquals("3")) {
						String currentFeedAd_Card1 = "hpvar_3";
						String iuId1 = ReadExcelValues.data[5][Cap] + currentFeedAd_Card1;
						if (qry.contains(iuId1)) {
							tempCustmParam = getCustomParamBy_iu_value(qry, cust_param);
							// if (!"".equals(tempCustmParam))
							// customParamsList.add(getCustomParamsBy_iu_value(qry));
							iuExists = true;
							iuId = iuId1;
							currentFeedAd_Card = currentFeedAd_Card1;
							break;
						}
					}
				}

			}
			if (iuExists) {
				// System.out.println(currentFeedAd_Card + " ad call is present");
				// logStep(currentFeedAd_Card + " ad call is present");
				System.out.println(cust_param + " value of from gampad call  of : " + iuId + " is " + tempCustmParam);

				if (tempCustmParam.equalsIgnoreCase(expCustParam_val)) {
					System.out.println(currentFeedAd_Card + " custom Parameter :" + cust_param + " value: "
							+ tempCustmParam + " is matched with the expected value " + expCustParam_val);
					logStep(currentFeedAd_Card + " custom Parameter :" + cust_param + " value: " + tempCustmParam
							+ " is matched with the expected value " + expCustParam_val);
				} else {
					System.out.println(currentFeedAd_Card + " custom Parameter :" + cust_param + " value: "
							+ tempCustmParam + " is not matched with the expected value " + expCustParam_val);
					logStep(currentFeedAd_Card + " custom Parameter :" + cust_param + " value: " + tempCustmParam
							+ " is not matched with the expected value " + expCustParam_val);
					custParamFailCount++;
					cardsCustParamNotMatched = cardsCustParamNotMatched.concat(currentFeedAd_Card + ", ");

					// Assert.fail("Custom Parameter :" + cust_param + " value: " + tempCustmParam
					// + " is not matched with the expected value " + expCustParam_val);
				}

			} else {
				System.out.println(currentFeedAd_Card + " ad call is not present");
				logStep(currentFeedAd_Card + " ad call is not present");
				failCount++;
				// Assert.fail(feedCards[i] + " ad call is not present");
				cardsNotPresent = cardsNotPresent.concat(currentFeedAd_Card + ", ");

			}
		}

		if (failCount > 0) {
			Exception = null;
			System.out.println(cardsNotPresent + " ad call is not present");
			logStep(cardsNotPresent + " ad call is not present");
			Exception = cardsNotPresent + " ad call is not present";
			// Assert.fail(Exception);
		}
		if (custParamFailCount > 0) {
			Exception = null;
			System.out.println(
					cardsCustParamNotMatched + " cards " + cust_param + " value is not matched with expected values");
			logStep(cardsCustParamNotMatched + " cards " + cust_param + " value is not matched with expected values");
			Exception = cardsCustParamNotMatched + " cards " + cust_param
					+ " value is not matched with expected values";
			Assert.fail(Exception);
		}

	}

	/**
	 * This method returns the parameter value from the corresponding API response
	 * 
	 * @param cust_param
	 * @return
	 * @throws Exception
	 */
	public static String get_param_value_from_APICalls(String cust_param) throws Exception {

		// readExcelValues.excelValues(excelName, sheetName);

		// Read the content form file
		File fXmlFile = new File(outfile.getName());
		listOf_b_Params.clear();

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		ReadExcelValues.excelValues("Cust_Param", "PramNaming");
		int custParamCount = ExcelData.rowcount;
		// Read JSONs and get b value
		// List<String> jsonBValuesList = new ArrayList<String>();

		// String slotId = readExcelValues.data[21][Cap];

		// String slotId = "c4dd8ec4-e40c-4a63-ae81-8f756793ac5e";

		boolean flag = false;
		boolean hflag = false;
		String ApiParamValue = null;
		List<String> istofRequestBodies = new ArrayList<String>();
		List<String> istofResponseBodies = new ArrayList<String>();
		// List<String> listOf_b_Params = new ArrayList<String>();

		outerloop: for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i) instanceof Node) {
				Node node = nodeList.item(i);
				if (node.hasChildNodes()) {
					NodeList nl = node.getChildNodes();
					for (int j = 0; j < nl.getLength(); j++) {
						Node innernode = nl.item(j);
						if (innernode != null) {

							if (innernode.getNodeName().equals("request")) {
								if (innernode.hasChildNodes()) {
									NodeList n2 = innernode.getChildNodes();
									for (int k = 0; k < n2.getLength(); k++) {
										Node innernode2 = n2.item(k);
										if (innernode2 != null) {
											if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
												Element eElement = (Element) innernode2;
												if (eElement.getNodeName().equals("headers")) {
													if (innernode2.hasChildNodes()) {
														NodeList n3 = innernode2.getChildNodes();
														for (int q = 0; q < n3.getLength(); q++) {
															// System.out.println("node3 length is:
															// "+n3.getLength());
															Node innernode3 = n3.item(q);
															if (innernode3 != null) {
																// System.out.println("Innernode3 name is:
																// "+innernode3.getNodeName());
																if (innernode3.getNodeType() == Node.ELEMENT_NODE) {
																	Element eElement1 = (Element) innernode3;
																	// System.out.println("Innernode3 element name
																	// is: "+eElement1.getNodeName());
																	if (eElement1.getNodeName().equals("header")) {
																		String content = eElement1.getTextContent();
																		// System.out.println("request body
																		// "+content);

																		for (int paramtype = 1; paramtype <= custParamCount; paramtype++) {
																			if (cust_param.equals(
																					ReadExcelValues.data[paramtype][0])
																					&& content.contains(
																							ReadExcelValues.data[paramtype][3])) {
																				flag = true;
																				break;
																			}
																		}
																	}

																	// this condition especially for android since its
																	// file has path value under first-line element
																	if (eElement1.getNodeName().equals("first-line")) {
																		String content = eElement1.getTextContent();
																		// System.out.println("request body
																		// "+content);

																		for (int paramtype = 1; paramtype <= custParamCount; paramtype++) {
																			if (cust_param.equals(
																					ReadExcelValues.data[paramtype][0])
																					&& content.contains(
																							ReadExcelValues.data[paramtype][3])) {
																				flag = true;
																				break;
																			}
																		}
																	}

																}
															}
														}
													}
												}

											}
										}
									}
								}
							}

							if (flag) {
								if (innernode.getNodeName().equals("response")) {
									// System.out.println(innernode.getNodeName());
									if (innernode.hasChildNodes()) {
										NodeList n2 = innernode.getChildNodes();
										for (int k = 0; k < n2.getLength(); k++) {
											Node innernode2 = n2.item(k);
											if (innernode2 != null) {
												if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
													Element eElement = (Element) innernode2;
													if (eElement.getNodeName().equals("body")) {
														String content = eElement.getTextContent();
														String[] JsonValues = null;
														String JsonParam = null;

														for (int paramtype = 1; paramtype <= custParamCount; paramtype++) {
															if (cust_param.equals(ReadExcelValues.data[paramtype][0])) {
																if (ReadExcelValues.data[paramtype][2].contains(",")) {
																	JsonValues = ReadExcelValues.data[paramtype][2]
																			.split(",");
																	JsonParam = JsonValues[0].trim();

																} /*
																	 * else
																	 * if(readExcelValues.data[paramtype][2].contains(
																	 * "direct")) { //mainTag = (JSONObject) obj; }
																	 */else {
																	JsonParam = ReadExcelValues.data[paramtype][2]
																			.trim();
																	// mainTag = (JSONObject) jsonObject.get(JsonParam);
																}
																break;
															}
														}

														if (content.contains(JsonParam)) {
															// content.replaceAll(":null", ":nl");
															// flag = true;
															// istofRequestBodies.add(content);
															// System.out.println("request body " + content);
															// ApiParamValue = getValueFromJsonResponseBody(content,
															// jsonParam,jsonNode);
															ApiParamValue = get_Expected_Value_From_APIResponseBody(
																	"Cust_Param", "PramNaming", cust_param, content);
															break outerloop;

															/*
															 * JSONParser parser = new JSONParser();
															 * //System.out.println("adreq1 is : "+adreq1.toString());
															 * Object obj = parser.parse(new String(content)); String
															 * JsonParam="v3-wx-observations-current"; JSONObject
															 * jsonObject = (JSONObject) obj; JSONObject mainTag =
															 * (JSONObject) jsonObject.get(JsonParam);
															 * //System.out.println("obj : "+obj);
															 * 
															 * String ApiParamValue= mainTag.get("iconCode").toString();
															 * System.out.println("value is "+ApiParamValue);
															 */

														}
														/*
														 * istofResponseBodies.add(content); String tempBparam =
														 * get_b_value_inJsonResponseBody(content); if
														 * (!"".contentEquals(tempBparam)) {
														 * listOf_b_Params.add(tempBparam); }
														 */
														// System.out.println("response body "+content);
													}
												}
											}
										}
									}
								}
							}

						}
					}
				}
			}
			flag = false;
		}

		return ApiParamValue;
	}

	/**
	 * This method returns the parameter value from the corresponding API response
	 * based on given zipcode.
	 * 
	 * @param cust_param
	 * @param zipCode
	 * @return
	 * @throws Exception
	 */
	public static String get_param_value_from_APICalls(String cust_param, String zipCode) throws Exception {

		// readExcelValues.excelValues(excelName, sheetName);

		// Read the content form file
		File fXmlFile = new File(outfile.getName());
		listOf_b_Params.clear();

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		ReadExcelValues.excelValues("Cust_Param", "PramNaming");
		int custParamCount = ExcelData.rowcount;
		// Read JSONs and get b value
		// List<String> jsonBValuesList = new ArrayList<String>();

		// String slotId = readExcelValues.data[21][Cap];

		// String slotId = "c4dd8ec4-e40c-4a63-ae81-8f756793ac5e";

		boolean flag = false;
		boolean hflag = false;
		String ApiParamValue = null;
		List<String> istofRequestBodies = new ArrayList<String>();
		List<String> istofResponseBodies = new ArrayList<String>();
		// List<String> listOf_b_Params = new ArrayList<String>();

		outerloop: for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i) instanceof Node) {
				Node node = nodeList.item(i);
				if (node.hasChildNodes()) {
					NodeList nl = node.getChildNodes();
					for (int j = 0; j < nl.getLength(); j++) {
						Node innernode = nl.item(j);
						if (innernode != null) {

							if (innernode.getNodeName().equals("request")) {
								if (innernode.hasChildNodes()) {
									NodeList n2 = innernode.getChildNodes();
									for (int k = 0; k < n2.getLength(); k++) {
										Node innernode2 = n2.item(k);
										if (innernode2 != null) {
											if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
												Element eElement = (Element) innernode2;
												if (eElement.getNodeName().equals("headers")) {
													if (innernode2.hasChildNodes()) {
														NodeList n3 = innernode2.getChildNodes();
														for (int q = 0; q < n3.getLength(); q++) {
															// System.out.println("node3 length is:
															// "+n3.getLength());
															Node innernode3 = n3.item(q);
															if (innernode3 != null) {
																// System.out.println("Innernode3 name is:
																// "+innernode3.getNodeName());
																if (innernode3.getNodeType() == Node.ELEMENT_NODE) {
																	Element eElement1 = (Element) innernode3;
																	// System.out.println("Innernode3 element name
																	// is: "+eElement1.getNodeName());
																	if (eElement1.getNodeName().equals("header")) {
																		String content = eElement1.getTextContent();
																		// System.out.println("request body
																		// "+content);

																		for (int paramtype = 1; paramtype <= custParamCount; paramtype++) {
																			if (cust_param.equals(
																					ReadExcelValues.data[paramtype][0])
																					&& content.contains(
																							ReadExcelValues.data[paramtype][3])) {
																				flag = true;
																				break;
																			}
																		}
																	}

																	// this condition especially for android since its
																	// file has path value under first-line element
																	if (eElement1.getNodeName().equals("first-line")) {
																		String content = eElement1.getTextContent();
																		// System.out.println("request body
																		// "+content);

																		for (int paramtype = 1; paramtype <= custParamCount; paramtype++) {
																			if (cust_param.equals(
																					ReadExcelValues.data[paramtype][0])
																					&& content.contains(
																							ReadExcelValues.data[paramtype][3])) {
																				flag = true;
																				break;
																			}
																		}
																	}

																}
															}
														}
													}
												}

											}
										}
									}
								}
							}

							if (flag) {
								if (innernode.getNodeName().equals("response")) {
									// System.out.println(innernode.getNodeName());
									if (innernode.hasChildNodes()) {
										NodeList n2 = innernode.getChildNodes();
										for (int k = 0; k < n2.getLength(); k++) {
											Node innernode2 = n2.item(k);
											if (innernode2 != null) {
												if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
													Element eElement = (Element) innernode2;
													if (eElement.getNodeName().equals("body")) {
														String content = eElement.getTextContent();
														String[] JsonValues = null;
														String JsonParam = null;

														for (int paramtype = 1; paramtype <= custParamCount; paramtype++) {
															if (cust_param.equals(ReadExcelValues.data[paramtype][0])) {
																if (ReadExcelValues.data[paramtype][2].contains(",")) {
																	JsonValues = ReadExcelValues.data[paramtype][2]
																			.split(",");
																	JsonParam = JsonValues[0].trim();

																} /*
																	 * else
																	 * if(readExcelValues.data[paramtype][2].contains(
																	 * "direct")) { //mainTag = (JSONObject) obj; }
																	 */else {
																	JsonParam = ReadExcelValues.data[paramtype][2]
																			.trim();
																	// mainTag = (JSONObject) jsonObject.get(JsonParam);
																}
																break;
															}
														}

														if (content.contains(JsonParam)) {
															// content.replaceAll(":null", ":nl");
															// flag = true;
															// istofRequestBodies.add(content);
															// System.out.println("request body " + content);
															// ApiParamValue = getValueFromJsonResponseBody(content,
															// jsonParam,jsonNode);
															ApiParamValue = get_Expected_Value_From_APIResponseBody(
																	"Cust_Param", "PramNaming", zipCode, cust_param,
																	content);
															break outerloop;

															/*
															 * JSONParser parser = new JSONParser();
															 * //System.out.println("adreq1 is : "+adreq1.toString());
															 * Object obj = parser.parse(new String(content)); String
															 * JsonParam="v3-wx-observations-current"; JSONObject
															 * jsonObject = (JSONObject) obj; JSONObject mainTag =
															 * (JSONObject) jsonObject.get(JsonParam);
															 * //System.out.println("obj : "+obj);
															 * 
															 * String ApiParamValue= mainTag.get("iconCode").toString();
															 * System.out.println("value is "+ApiParamValue);
															 */

														}
														/*
														 * istofResponseBodies.add(content); String tempBparam =
														 * get_b_value_inJsonResponseBody(content); if
														 * (!"".contentEquals(tempBparam)) {
														 * listOf_b_Params.add(tempBparam); }
														 */
														// System.out.println("response body "+content);
													}
												}
											}
										}
									}
								}
							}

						}
					}
				}
			}
			flag = false;
		}

		return ApiParamValue;
	}
	
	/**
	 * This method returns the parameter value from the corresponding API response
	 * based on given zipcode.
	 * 
	 * @param cust_param
	 * @param zipCode
	 * @return
	 * @throws Exception
	 */
	public static String get_param_value_from_APICalls_V2(String cust_param, String zipCode, String constant, String countryCode) throws Exception {

		// readExcelValues.excelValues(excelName, sheetName);

		// Read the content form file
		File fXmlFile = new File(outfile.getName());
		listOf_b_Params.clear();

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		ReadExcelValues.excelValues("Cust_Param", "PramNaming");
		int custParamCount = ExcelData.rowcount;
		// Read JSONs and get b value
		// List<String> jsonBValuesList = new ArrayList<String>();

		// String slotId = readExcelValues.data[21][Cap];

		// String slotId = "c4dd8ec4-e40c-4a63-ae81-8f756793ac5e";

		boolean flag = false;
		boolean hflag = false;
		String ApiParamValue = null;
		List<String> istofRequestBodies = new ArrayList<String>();
		List<String> istofResponseBodies = new ArrayList<String>();
		// List<String> listOf_b_Params = new ArrayList<String>();

		outerloop: for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i) instanceof Node) {
				Node node = nodeList.item(i);
				if (node.hasChildNodes()) {
					NodeList nl = node.getChildNodes();
					for (int j = 0; j < nl.getLength(); j++) {
						Node innernode = nl.item(j);
						if (innernode != null) {

							if (innernode.getNodeName().equals("request")) {
								if (innernode.hasChildNodes()) {
									NodeList n2 = innernode.getChildNodes();
									for (int k = 0; k < n2.getLength(); k++) {
										Node innernode2 = n2.item(k);
										if (innernode2 != null) {
											if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
												Element eElement = (Element) innernode2;
												if (eElement.getNodeName().equals("headers")) {
													if (innernode2.hasChildNodes()) {
														NodeList n3 = innernode2.getChildNodes();
														for (int q = 0; q < n3.getLength(); q++) {
															// System.out.println("node3 length is:
															// "+n3.getLength());
															Node innernode3 = n3.item(q);
															if (innernode3 != null) {
																// System.out.println("Innernode3 name is:
																// "+innernode3.getNodeName());
																if (innernode3.getNodeType() == Node.ELEMENT_NODE) {
																	Element eElement1 = (Element) innernode3;
																	// System.out.println("Innernode3 element name
																	// is: "+eElement1.getNodeName());
																	if (eElement1.getNodeName().equals("header")) {
																		String content = eElement1.getTextContent();
																		// System.out.println("request body
																		// "+content);

																		for (int paramtype = 1; paramtype <= custParamCount; paramtype++) {
																			if (cust_param.equals(
																					ReadExcelValues.data[paramtype][0])
																					&& content.contains(
																							ReadExcelValues.data[paramtype][3])) {
																				flag = true;
																				break;
																			}
																		}
																	}

																	// this condition especially for android since its
																	// file has path value under first-line element
																	if (eElement1.getNodeName().equals("first-line")) {
																		String content = eElement1.getTextContent();
																		// System.out.println("request body
																		// "+content);

																		for (int paramtype = 1; paramtype <= custParamCount; paramtype++) {
																			if (cust_param.equals(
																					ReadExcelValues.data[paramtype][0])
																					&& content.contains(
																							ReadExcelValues.data[paramtype][3])) {
																				flag = true;
																				break;
																			}
																		}
																	}

																}
															}
														}
													}
												}

											}
										}
									}
								}
							}

							if (flag) {
								if (innernode.getNodeName().equals("response")) {
									// System.out.println(innernode.getNodeName());
									if (innernode.hasChildNodes()) {
										NodeList n2 = innernode.getChildNodes();
										for (int k = 0; k < n2.getLength(); k++) {
											Node innernode2 = n2.item(k);
											if (innernode2 != null) {
												if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
													Element eElement = (Element) innernode2;
													if (eElement.getNodeName().equals("body")) {
														String content = eElement.getTextContent();
														String[] JsonValues = null;
														String JsonParam = null;

														for (int paramtype = 1; paramtype <= custParamCount; paramtype++) {
															if (cust_param.equals(ReadExcelValues.data[paramtype][0])) {
																if (ReadExcelValues.data[paramtype][2].contains(",")) {
																	JsonValues = ReadExcelValues.data[paramtype][2]
																			.split(",");
																	JsonParam = JsonValues[0].trim();

																} /*
																	 * else
																	 * if(readExcelValues.data[paramtype][2].contains(
																	 * "direct")) { //mainTag = (JSONObject) obj; }
																	 */else {
																	JsonParam = ReadExcelValues.data[paramtype][2]
																			.trim();
																	// mainTag = (JSONObject) jsonObject.get(JsonParam);
																}
																break;
															}
														}

														if (content.contains(JsonParam)) {
															// content.replaceAll(":null", ":nl");
															// flag = true;
															// istofRequestBodies.add(content);
															// System.out.println("request body " + content);
															// ApiParamValue = getValueFromJsonResponseBody(content,
															// jsonParam,jsonNode);
															ApiParamValue = get_Expected_Value_From_APIResponseBody_V2(
																	"Cust_Param", "PramNaming", zipCode, cust_param,
																	content, constant, countryCode);
															break outerloop;

															/*
															 * JSONParser parser = new JSONParser();
															 * //System.out.println("adreq1 is : "+adreq1.toString());
															 * Object obj = parser.parse(new String(content)); String
															 * JsonParam="v3-wx-observations-current"; JSONObject
															 * jsonObject = (JSONObject) obj; JSONObject mainTag =
															 * (JSONObject) jsonObject.get(JsonParam);
															 * //System.out.println("obj : "+obj);
															 * 
															 * String ApiParamValue= mainTag.get("iconCode").toString();
															 * System.out.println("value is "+ApiParamValue);
															 */

														}
														/*
														 * istofResponseBodies.add(content); String tempBparam =
														 * get_b_value_inJsonResponseBody(content); if
														 * (!"".contentEquals(tempBparam)) {
														 * listOf_b_Params.add(tempBparam); }
														 */
														// System.out.println("response body "+content);
													}
												}
											}
										}
									}
								}
							}

						}
					}
				}
			}
			flag = false;
		}

		return ApiParamValue;
	}

	/**
	 * This method gets the parameter value from the corresponding API response
	 * based on given zipcode and adds it to the map.
	 * 
	 * @param cust_param
	 * @param zipCode
	 * @return
	 * @throws Exception
	 */
	public static void load_param_value_from_APICalls(String cust_param, String zipCode, boolean clearMap)
			throws Exception {

		// readExcelValues.excelValues(excelName, sheetName);

		// Read the content form file
		File fXmlFile = new File(outfile.getName());

		if (clearMap) {
			wfxParameters.clear();

		}

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		ReadExcelValues.excelValues("Cust_Param", "PramNaming");
		int custParamCount = ExcelData.rowcount;
		// Read JSONs and get b value
		// List<String> jsonBValuesList = new ArrayList<String>();

		// String slotId = readExcelValues.data[21][Cap];

		// String slotId = "c4dd8ec4-e40c-4a63-ae81-8f756793ac5e";

		boolean flag = false;
		boolean hflag = false;
		String ApiParamValue = null;
		List<String> istofRequestBodies = new ArrayList<String>();
		List<String> istofResponseBodies = new ArrayList<String>();
		// List<String> listOf_b_Params = new ArrayList<String>();

		outerloop: for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i) instanceof Node) {
				Node node = nodeList.item(i);
				if (node.hasChildNodes()) {
					NodeList nl = node.getChildNodes();
					for (int j = 0; j < nl.getLength(); j++) {
						Node innernode = nl.item(j);
						if (innernode != null) {

							if (innernode.getNodeName().equals("request")) {
								if (innernode.hasChildNodes()) {
									NodeList n2 = innernode.getChildNodes();
									for (int k = 0; k < n2.getLength(); k++) {
										Node innernode2 = n2.item(k);
										if (innernode2 != null) {
											if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
												Element eElement = (Element) innernode2;
												if (eElement.getNodeName().equals("headers")) {
													if (innernode2.hasChildNodes()) {
														NodeList n3 = innernode2.getChildNodes();
														for (int q = 0; q < n3.getLength(); q++) {
															// System.out.println("node3 length is:
															// "+n3.getLength());
															Node innernode3 = n3.item(q);
															if (innernode3 != null) {
																// System.out.println("Innernode3 name is:
																// "+innernode3.getNodeName());
																if (innernode3.getNodeType() == Node.ELEMENT_NODE) {
																	Element eElement1 = (Element) innernode3;
																	// System.out.println("Innernode3 element name
																	// is: "+eElement1.getNodeName());
																	if (eElement1.getNodeName().equals("header")) {
																		String content = eElement1.getTextContent();
																		// System.out.println("request body
																		// "+content);

																		for (int paramtype = 1; paramtype <= custParamCount; paramtype++) {
																			if (cust_param.equals(
																					ReadExcelValues.data[paramtype][0])
																					&& content.contains(
																							ReadExcelValues.data[paramtype][3])) {
																				flag = true;
																				break;
																			}
																		}
																	}

																	// this condition especially for android since its
																	// file has path value under first-line element
																	if (eElement1.getNodeName().equals("first-line")) {
																		String content = eElement1.getTextContent();
																		// System.out.println("request body
																		// "+content);

																		for (int paramtype = 1; paramtype <= custParamCount; paramtype++) {
																			if (cust_param.equals(
																					ReadExcelValues.data[paramtype][0])
																					&& content.contains(
																							ReadExcelValues.data[paramtype][3])) {
																				flag = true;
																				break;
																			}
																		}
																	}

																}
															}
														}
													}
												}

											}
										}
									}
								}
							}

							if (flag) {
								if (innernode.getNodeName().equals("response")) {
									// System.out.println(innernode.getNodeName());
									if (innernode.hasChildNodes()) {
										NodeList n2 = innernode.getChildNodes();
										for (int k = 0; k < n2.getLength(); k++) {
											Node innernode2 = n2.item(k);
											if (innernode2 != null) {
												if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
													Element eElement = (Element) innernode2;
													if (eElement.getNodeName().equals("body")) {
														String content = eElement.getTextContent();
														String[] JsonValues = null;
														String JsonParam = null;

														for (int paramtype = 1; paramtype <= custParamCount; paramtype++) {
															if (cust_param.equals(ReadExcelValues.data[paramtype][0])) {
																if (ReadExcelValues.data[paramtype][2].contains(",")) {
																	JsonValues = ReadExcelValues.data[paramtype][2]
																			.split(",");
																	JsonParam = JsonValues[0].trim();

																} /*
																	 * else
																	 * if(readExcelValues.data[paramtype][2].contains(
																	 * "direct")) { //mainTag = (JSONObject) obj; }
																	 */else {
																	JsonParam = ReadExcelValues.data[paramtype][2]
																			.trim();
																	// mainTag = (JSONObject) jsonObject.get(JsonParam);
																}
																break;
															}
														}

														if (content.contains(JsonParam)) {
															// content.replaceAll(":null", ":nl");
															// flag = true;
															// istofRequestBodies.add(content);
															// System.out.println("request body " + content);
															// ApiParamValue = getValueFromJsonResponseBody(content,
															// jsonParam,jsonNode);
															ApiParamValue = get_Expected_Value_From_APIResponseBody(
																	"Cust_Param", "PramNaming", zipCode, cust_param,
																	content);
															break outerloop;

															/*
															 * JSONParser parser = new JSONParser();
															 * //System.out.println("adreq1 is : "+adreq1.toString());
															 * Object obj = parser.parse(new String(content)); String
															 * JsonParam="v3-wx-observations-current"; JSONObject
															 * jsonObject = (JSONObject) obj; JSONObject mainTag =
															 * (JSONObject) jsonObject.get(JsonParam);
															 * //System.out.println("obj : "+obj);
															 * 
															 * String ApiParamValue= mainTag.get("iconCode").toString();
															 * System.out.println("value is "+ApiParamValue);
															 */

														}
														/*
														 * istofResponseBodies.add(content); String tempBparam =
														 * get_b_value_inJsonResponseBody(content); if
														 * (!"".contentEquals(tempBparam)) {
														 * listOf_b_Params.add(tempBparam); }
														 */
														// System.out.println("response body "+content);
													}
												}
											}
										}
									}
								}
							}

						}
					}
				}
			}
			flag = false;
		}

		// return ApiParamValue;
		wfxParameters.put(cust_param, ApiParamValue);
		System.out.println("wfxParameters are: " + wfxParameters);
	}
	
	public static void load_param_value_from_APICalls_V2(String cust_param, String zipCode, boolean clearMap, String constant, String countryCode)
			throws Exception {

		// readExcelValues.excelValues(excelName, sheetName);

		// Read the content form file
		File fXmlFile = new File(outfile.getName());

		if (clearMap) {
			wfxParameters.clear();

		}

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		ReadExcelValues.excelValues("Cust_Param", "PramNaming");
		int custParamCount = ExcelData.rowcount;
		// Read JSONs and get b value
		// List<String> jsonBValuesList = new ArrayList<String>();

		// String slotId = readExcelValues.data[21][Cap];

		// String slotId = "c4dd8ec4-e40c-4a63-ae81-8f756793ac5e";

		boolean flag = false;
		boolean hflag = false;
		String ApiParamValue = null;
		List<String> istofRequestBodies = new ArrayList<String>();
		List<String> istofResponseBodies = new ArrayList<String>();
		// List<String> listOf_b_Params = new ArrayList<String>();

		outerloop: for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i) instanceof Node) {
				Node node = nodeList.item(i);
				if (node.hasChildNodes()) {
					NodeList nl = node.getChildNodes();
					for (int j = 0; j < nl.getLength(); j++) {
						Node innernode = nl.item(j);
						if (innernode != null) {

							if (innernode.getNodeName().equals("request")) {
								if (innernode.hasChildNodes()) {
									NodeList n2 = innernode.getChildNodes();
									for (int k = 0; k < n2.getLength(); k++) {
										Node innernode2 = n2.item(k);
										if (innernode2 != null) {
											if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
												Element eElement = (Element) innernode2;
												if (eElement.getNodeName().equals("headers")) {
													if (innernode2.hasChildNodes()) {
														NodeList n3 = innernode2.getChildNodes();
														for (int q = 0; q < n3.getLength(); q++) {
															// System.out.println("node3 length is:
															// "+n3.getLength());
															Node innernode3 = n3.item(q);
															if (innernode3 != null) {
																// System.out.println("Innernode3 name is:
																// "+innernode3.getNodeName());
																if (innernode3.getNodeType() == Node.ELEMENT_NODE) {
																	Element eElement1 = (Element) innernode3;
																	// System.out.println("Innernode3 element name
																	// is: "+eElement1.getNodeName());
																	if (eElement1.getNodeName().equals("header")) {
																		String content = eElement1.getTextContent();
																		//System.out.println("request body "+content);

																		for (int paramtype = 1; paramtype <= custParamCount; paramtype++) {
																			if (cust_param.equals(
																					ReadExcelValues.data[paramtype][0])
																					&& content.contains(
																							ReadExcelValues.data[paramtype][3])) {
																				System.out.println("request body "+content);
																				flag = true;
																				break;
																			}
																		}
																	}

																	// this condition especially for android since its
																	// file has path value under first-line element
																	if (eElement1.getNodeName().equals("first-line")) {
																		String content = eElement1.getTextContent();
																		 //System.out.println("request body "+content);

																		for (int paramtype = 1; paramtype <= custParamCount; paramtype++) {
																			if (cust_param.equals(
																					ReadExcelValues.data[paramtype][0])
																					&& content.contains(
																							ReadExcelValues.data[paramtype][3])) {
																				System.out.println("request body "+content);
																				flag = true;
																				break;
																			}
																		}
																	}

																}
															}
														}
													}
												}

											}
										}
									}
								}
							}

							if (flag) {
								if (innernode.getNodeName().equals("response")) {
									// System.out.println(innernode.getNodeName());
									if (innernode.hasChildNodes()) {
										NodeList n2 = innernode.getChildNodes();
										for (int k = 0; k < n2.getLength(); k++) {
											Node innernode2 = n2.item(k);
											if (innernode2 != null) {
												if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
													Element eElement = (Element) innernode2;
													if (eElement.getNodeName().equals("body")) {
														String content = eElement.getTextContent();
														String[] JsonValues = null;
														String JsonParam = null;

														for (int paramtype = 1; paramtype <= custParamCount; paramtype++) {
															if (cust_param.equals(ReadExcelValues.data[paramtype][0])) {
																if (ReadExcelValues.data[paramtype][2].contains(",")) {
																	JsonValues = ReadExcelValues.data[paramtype][2]
																			.split(",");
																	JsonParam = JsonValues[0].trim();

																} /*
																	 * else
																	 * if(readExcelValues.data[paramtype][2].contains(
																	 * "direct")) { //mainTag = (JSONObject) obj; }
																	 */else {
																	JsonParam = ReadExcelValues.data[paramtype][2]
																			.trim();
																	// mainTag = (JSONObject) jsonObject.get(JsonParam);
																}
																break;
															}
														}

														if (content.contains(JsonParam)) {
															// content.replaceAll(":null", ":nl");
															// flag = true;
															// istofRequestBodies.add(content);
															// System.out.println("request body " + content);
															// ApiParamValue = getValueFromJsonResponseBody(content,
															// jsonParam,jsonNode);
															ApiParamValue = get_Expected_Value_From_APIResponseBody_V2(
																	"Cust_Param", "PramNaming", zipCode, cust_param,
																	content, constant, countryCode);
															break outerloop;

															/*
															 * JSONParser parser = new JSONParser();
															 * //System.out.println("adreq1 is : "+adreq1.toString());
															 * Object obj = parser.parse(new String(content)); String
															 * JsonParam="v3-wx-observations-current"; JSONObject
															 * jsonObject = (JSONObject) obj; JSONObject mainTag =
															 * (JSONObject) jsonObject.get(JsonParam);
															 * //System.out.println("obj : "+obj);
															 * 
															 * String ApiParamValue= mainTag.get("iconCode").toString();
															 * System.out.println("value is "+ApiParamValue);
															 */

														}
														/*
														 * istofResponseBodies.add(content); String tempBparam =
														 * get_b_value_inJsonResponseBody(content); if
														 * (!"".contentEquals(tempBparam)) {
														 * listOf_b_Params.add(tempBparam); }
														 */
														// System.out.println("response body "+content);
													}
												}
											}
										}
									}
								}
							}

						}
					}
				}
			}
			flag = false;
		}

		// return ApiParamValue;
		wfxParameters.put(cust_param, ApiParamValue);
		System.out.println("wfxParameters are: " + wfxParameters);
	}

	/**
	 * Load weatherfx api parameeter values to a map by zip code
	 * @param zipCode
	 * @throws Exception
	 */
	public static void loadWeatherFXAPIParameterValuestoMap_ByZipCode(String zipCode) throws Exception {
		
		if (Ad instanceof AndroidDriver<?>) {
			Utils.load_param_value_from_APICalls("wfxtg", zipCode, false);
			Utils.load_param_value_from_APICalls("cxtg", zipCode, false);
			Utils.load_param_value_from_APICalls("zcs", zipCode, false);
			Utils.load_param_value_from_APICalls("hzcs", zipCode, false);
			Utils.load_param_value_from_APICalls("nzcs", zipCode, false);
		} else {
			Utils.load_param_value_from_APICalls_V2("wfxtg", Utils.hlzip, false, "4", "US");
			Utils.load_param_value_from_APICalls_V2("cxtg", zipCode, false, "4", "US");
			Utils.load_param_value_from_APICalls_V2("zcs", zipCode, false, "4", "US");
			Utils.load_param_value_from_APICalls_V2("hzcs", Utils.hlzip, false, "4", "US");
			Utils.load_param_value_from_APICalls_V2("nzcs", zipCode, false, "4", "US");
		}
		

	}

	/**
	 * Returns value from JSON responsee body
	 * @param jsonString
	 * @param jsonParam
	 * @param jsonNode
	 * @return
	 */
	public static String getValueFromJsonResponseBody(String jsonString, String jsonParam, String jsonNode) {
		JSONParser parser = new JSONParser();
		// String jsonParam="v3-wx-observations-current";
		JSONObject mainTag = null;
		String ApiParamValue = null;
		// System.out.println("adreq1 is : "+adreq1.toString());
		Object obj;
		try {
			obj = parser.parse(new String(jsonString));
			JSONObject jsonObject = (JSONObject) obj;
			mainTag = (JSONObject) jsonObject.get(jsonParam);
			ApiParamValue = mainTag.get(jsonNode).toString();
			System.out.println("value from json response body is " + ApiParamValue);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			ApiParamValue = "Error";
			e.printStackTrace();
		}

		return ApiParamValue;
	}

	/**
	 * This method returns the parameter value from API response body after parsing
	 * the response
	 * 
	 * @param Excelname
	 * @param sheetName
	 * @param cust_param
	 * @param apiData
	 * @return
	 * @throws Exception
	 */
	public static String get_Expected_Value_From_APIResponseBody(String Excelname, String sheetName, String cust_param,
			String apiData) throws Exception {

		ReadExcelValues.excelValues(Excelname, sheetName);
		JSONParser parser = new JSONParser();
		// System.out.println("adreq1 is : "+adreq1.toString());
		Object obj = parser.parse(new String(apiData));
		// System.out.println("obj : "+obj);
		JSONObject jsonObject = (JSONObject) obj;
		String ApiParams = null;
		String ApiParamValue = null;
		String paramName = null;
		String JsonValues[] = null;
		String JsonParam = null;

		JSONObject mainTag = null;
		int custParamCount = ExcelData.rowcount;

		for (int paramtype = 1; paramtype <= custParamCount; paramtype++) {
			if (cust_param.equals(ReadExcelValues.data[paramtype][0])) {

				if (ReadExcelValues.data[paramtype][Cap].toString().equals("hardcode")) {
					System.out.println("Param type is Hard Code");
				} else {
					System.out.println("main tag is : " + ReadExcelValues.data[paramtype][2] + ", param type is : "
							+ ReadExcelValues.data[paramtype][Cap]);
					if (ReadExcelValues.data[paramtype][2].contains("direct")) {
						mainTag = (JSONObject) obj;
					} else if (ReadExcelValues.data[paramtype][2].contains(",")) {
						JsonValues = ReadExcelValues.data[paramtype][2].split(",");
						JsonParam = JsonValues[0].trim();
						mainTag = (JSONObject) jsonObject.get(JsonParam);
						try {
							mainTag = (JSONObject) mainTag.get(JsonValues[1].trim());
						} catch (Exception e) {
							if (cust_param.equalsIgnoreCase("fcnd") || cust_param.equalsIgnoreCase("fdynght")) {
								JSONArray eleArray = (JSONArray) mainTag.get(JsonValues[1].trim());
								ArrayList<String> Ingredients_names = new ArrayList<>();
								for (int i = 0; i < eleArray.size(); i++) {
									String arrayElement = String.valueOf(eleArray.get(i));

									Ingredients_names.add(arrayElement);
									obj = parser.parse(new String(arrayElement));
									jsonObject = (JSONObject) obj;
									mainTag = (JSONObject) obj;
									String[] arEls = arrayElement.split(",");
								}
								// System.out.println(Ingredients_names);
							}

						}

					} else {
						JsonParam = ReadExcelValues.data[paramtype][2].trim();
						mainTag = (JSONObject) jsonObject.get(JsonParam);
					}

					if (cust_param.equalsIgnoreCase("fcnd") || cust_param.equalsIgnoreCase("fdynght")) {
						JSONArray dayPartElementValues = (JSONArray) mainTag.get(ReadExcelValues.data[paramtype][Cap]);
						if (String.valueOf(dayPartElementValues.get(0)).equalsIgnoreCase("null")) {
							ApiParamValue = String.valueOf(dayPartElementValues.get(1));
						} else {
							ApiParamValue = String.valueOf(dayPartElementValues.get(0));
						}

					} else {
						try {
							JSONArray arrayElementValues = (JSONArray) mainTag
									.get(ReadExcelValues.data[paramtype][Cap]);
							ApiParamValue = String.valueOf(arrayElementValues.get(0));
						} catch (Exception e) {
							ApiParamValue = String.valueOf(mainTag.get(ReadExcelValues.data[paramtype][Cap]));
						}
					}

					if (cust_param.equalsIgnoreCase("tmpc")) {
						int fahrenheit = Integer.parseInt(ApiParamValue);
						int celsius = 0;
						//For android looks they are rounding to nearest digit
						if (Ad instanceof AndroidDriver<?>) {
							celsius = (int) Math.round((double)((fahrenheit - 32) * 5) / 9);
						} else {
							celsius = ((fahrenheit - 32) * 5) / 9;
						}
						ApiParamValue = String.valueOf(celsius);
					} else if (cust_param.equalsIgnoreCase("fltmpc")) {
						int fahrenheit = Integer.parseInt(ApiParamValue);
						int celsius = 0;
						//For android looks they are rounding to nearest digit
						if (Ad instanceof AndroidDriver<?>) {
							celsius = (int) Math.round((double)((fahrenheit - 32) * 5) / 9);
						} else {
							celsius = ((fahrenheit - 32) * 5) / 9;
						}
						ApiParamValue = String.valueOf(celsius);
					}
					// ApiParamValue= mainTag.get(readExcelValues.data[paramtype][Cap]).toString();

					if (ReadExcelValues.data[paramtype][Cap].toString().equalsIgnoreCase("icon")) {
						paramName = "cnd";
					} else {
						paramName = ReadExcelValues.data[paramtype][0].toString();
					}
					// ads.add(ApiParams);
					if (ReadExcelValues.data[paramtype][4].toString().equalsIgnoreCase("Yes")) {
						ReadExcelValues.excelValues("Cust_Param_Result", cust_param);

						for (int CustParamValues = 1; CustParamValues <= ExcelData.rowcount; CustParamValues++) {
							if (ReadExcelValues.data[CustParamValues][1].contains("and more")) {
								String CellParam = ReadExcelValues.data[CustParamValues][1].toString();
								CellParam = CellParam.replaceAll("and more", "");
								CellParam = CellParam.replaceAll(" ", "");
								int celparamValue = Integer.parseInt(CellParam);
								int ApiParamNumber = Integer.parseInt(ApiParamValue);
								if (ApiParamNumber >= celparamValue) {
									ApiParamValue = ReadExcelValues.data[CustParamValues][2].toString();
									break;
								}
							} else if (ReadExcelValues.data[CustParamValues][1].contains("and less")) {
								String CellParam = ReadExcelValues.data[CustParamValues][1].toString();
								CellParam = CellParam.replaceAll("and less", "");
								CellParam = CellParam.replaceAll(" ", "");
								int celparamValue = Integer.parseInt(CellParam);
								int ApiParamNumber = Integer.parseInt(ApiParamValue);
								if (ApiParamNumber <= celparamValue) {
									ApiParamValue = ReadExcelValues.data[CustParamValues][2].toString();
									break;
								}
							} else if (ReadExcelValues.data[CustParamValues][1].contains(ApiParamValue)) {
								ApiParamValue = ReadExcelValues.data[CustParamValues][2].toString();
								break;
							}
						}
					}
					ApiParams = paramName + "=" + ApiParamValue;
					break;
				}
			}
		}
		System.out.println(cust_param + " Param Values from API Call is : " + ApiParamValue);
		if (ApiParamValue.equalsIgnoreCase("null")) {
			ApiParamValue = "nl";
		}
		return ApiParamValue;
	}

	/**
	 * This method returns the parameter value from API response body after parsing
	 * the response by zipcode
	 * 
	 * @param Excelname
	 * @param sheetName
	 * @param zipCode
	 * @param cust_param
	 * @param apiData
	 * @return
	 * @throws Exception
	 */
	public static String get_Expected_Value_From_APIResponseBody(String Excelname, String sheetName, String zipCode,
			String cust_param, String apiData) throws Exception {
		 System.out.println("Api Data Sting format is : "+apiData);
		ReadExcelValues.excelValues(Excelname, sheetName);
		JSONParser parser = new JSONParser();
		// System.out.println("adreq1 is : "+adreq1.toString());
		Object obj = parser.parse(new String(apiData));
		// System.out.println("obj : "+obj);
		JSONObject jsonObject = (JSONObject) obj;
		String ApiParams = null;
		String ApiParamValue = null;
		String paramName = null;
		String JsonValues[] = null;
		String JsonParam = null;

		JSONObject mainTag = null;
		int custParamCount = ExcelData.rowcount;

		for (int paramtype = 1; paramtype <= custParamCount; paramtype++) {
			if (cust_param.equals(ReadExcelValues.data[paramtype][0])) {

				if (ReadExcelValues.data[paramtype][Cap].toString().equals("hardcode")) {
					System.out.println("Param type is Hard Code");
				} else {
					System.out.println("main tag is : " + ReadExcelValues.data[paramtype][2] + ", param type is : "
							+ ReadExcelValues.data[paramtype][Cap]);
					if (ReadExcelValues.data[paramtype][2].contains("direct")) {
						mainTag = (JSONObject) obj;
					} else if (ReadExcelValues.data[paramtype][2].contains(",")) {
						JsonValues = ReadExcelValues.data[paramtype][2].split(",");
						JsonParam = JsonValues[0].trim();
						mainTag = (JSONObject) jsonObject.get(JsonParam);
						try {
							mainTag = (JSONObject) mainTag.get(JsonValues[1].trim());
						} catch (Exception e) {
							if (cust_param.equalsIgnoreCase("fcnd") || cust_param.equalsIgnoreCase("fdynght")) {
								JSONArray eleArray = (JSONArray) mainTag.get(JsonValues[1].trim());
								/*
								 * Note that, this JSONArray has only one json object in it, hence loop ends
								 * after one iteration.
								 */
								ArrayList<String> Ingredients_names = new ArrayList<>();
								for (int i = 0; i < eleArray.size(); i++) {
									String arrayElement = String.valueOf(eleArray.get(i));

									Ingredients_names.add(arrayElement);
									obj = parser.parse(new String(arrayElement));
									jsonObject = (JSONObject) obj;
									mainTag = (JSONObject) obj;
									String[] arEls = arrayElement.split(",");
								}
								// System.out.println(Ingredients_names);
							} else if (cust_param.equalsIgnoreCase("cxtg") || cust_param.equalsIgnoreCase("zcs")
									|| cust_param.equalsIgnoreCase("hzcs") || cust_param.equalsIgnoreCase("nzcs")) {
								JSONArray eleArray = (JSONArray) mainTag.get(JsonValues[1].trim());
								/*
								 * Generally Scatteredtags returns 3 objects, zcs, hzcs and nzcs in order in
								 * turn each object zcs, hzcs and nzcs is an array of json objects
								 */
								ArrayList<String> Ingredients_names = new ArrayList<>();
								// String arrayElement="";
								outerloop: for (int i = 0; i < eleArray.size(); i++) {
									String arrayElement = String.valueOf(eleArray.get(i));

									Ingredients_names.add(arrayElement);
									obj = parser.parse(new String(arrayElement));
									jsonObject = (JSONObject) obj;
									mainTag = (JSONObject) obj;

									try {
										/*
										 * since same block being used for cxtg, zcs, hzcs and nzcs, there are
										 * possibilities that we may get null value in first iteration for hzcs and nzcs
										 * as these are elements of second object onwards, hence exception. so when we
										 * get exception continuing to next iteration
										 */
										JSONArray eleArrays = (JSONArray) mainTag.get(JsonValues[2].trim());
										ArrayList<String> Ingredients_namess = new ArrayList<>();
										// String[] arEls = arrayElement.split(",");
										for (int j = 0; j < eleArrays.size(); j++) {
											String arrayElementt = String.valueOf(eleArrays.get(j));

											Ingredients_namess.add(arrayElementt);
											obj = parser.parse(new String(arrayElementt));
											jsonObject = (JSONObject) obj;
											mainTag = (JSONObject) obj;
											String zip = "";
											try {
												zip = String.valueOf(mainTag.get("zip"));

											} catch (Exception e1) {
												System.out.println("An Exception while fetching zip value");
												logStep("An Exception while fetching zip value");
											}
											/*
											 * since the parameters are mapped to respective zip codes and selection to
											 * be based on zip code, hence zip code comparision introduced
											 */
											if (zip.equalsIgnoreCase(zipCode)) {

												break outerloop;
											} else {
												/*
												 * this else block is written to check whether zip code found in json
												 * objects. if not printing a log message, as the exception being
												 * automatically handled and returns null value in the step JSONArray
												 * arrayElementValues = (JSONArray)
												 * mainTag.get(readExcelValues.data[paramtype][Cap]);
												 */
												if (j == eleArrays.size() - 1) {
													System.out.println("Expected Zip Code :" + zipCode
															+ " not found in JSON Array Objects, during custom parameter "
															+ cust_param + " validation.");
													logStep("Expected Zip Code :" + zipCode
															+ " not found in JSON Array Objects, during custom parameter "
															+ cust_param + " validation.");
													mainTag = null;
												}
											}

										}
										break outerloop;
									} catch (Exception e2) {
										continue;
									}

								}

							}

						}

					} else {
						JsonParam = ReadExcelValues.data[paramtype][2].trim();
						mainTag = (JSONObject) jsonObject.get(JsonParam);
					}

					if (cust_param.equalsIgnoreCase("fcnd") || cust_param.equalsIgnoreCase("fdynght")) {
						JSONArray dayPartElementValues = (JSONArray) mainTag.get(ReadExcelValues.data[paramtype][Cap]);
						if (String.valueOf(dayPartElementValues.get(0)).equalsIgnoreCase("null")) {
							ApiParamValue = String.valueOf(dayPartElementValues.get(1));
						} else {
							ApiParamValue = String.valueOf(dayPartElementValues.get(0));
						}

					} else {
						try {
							/*
							 * Here this will try to get the parameter values into jSON Array when it doesnt
							 * find any parameter it is expected, for ex: it is not seen xcurrent in json
							 * response then it returns 'null' to arrayElementValues. and continues in this
							 * try block to get ApiParamValue if jSON array doesnt exist then an exception
							 * while getting the ApiParamValue. it goes to catch block to see if it is a
							 * String
							 * 
							 */
							JSONArray arrayElementValues = (JSONArray) mainTag
									.get(ReadExcelValues.data[paramtype][Cap]);

							/*
							 * Certain custom parameters needed single element out of array and certain
							 * parameters needed complete array
							 */

							if (cust_param.equalsIgnoreCase("wfxtg") || cust_param.equalsIgnoreCase("cxtg")
									|| cust_param.equalsIgnoreCase("nzcs") || cust_param.equalsIgnoreCase("zcs")
									|| cust_param.equalsIgnoreCase("hzcs")) {
								ApiParamValue = StringUtils.jSONArrayToString(arrayElementValues);

							} else {
								ApiParamValue = String.valueOf(arrayElementValues.get(0));
							}

						} catch (Exception e) {
							try {
								/*
								 * wfxtg and cxtg parameters use encoded values 'xcurrent' and 'xcxtg'
								 * respectively. these are strings only where other parameters of trigger call
								 * are arrays.
								 * 
								 */
								/*
								 * Here this will try to get the parameter values into String when it doesnt
								 * find any parameter it is expected, for ex: it is not seen xcurrent in json
								 * response then it returns 'null' to ApiParamValue, without any exception for
								 * nonexisting parameter. at the end null value will be set to 'nl'
								 * 
								 */
								ApiParamValue = String.valueOf(mainTag.get(ReadExcelValues.data[paramtype][Cap]));
							} catch (Exception e3) {
								/*
								 * This is handled because for cxtg, zcs, hzcs and nzcs if the expected zip code
								 * is not there then throwing an exception and setting ApiParamValue to nl
								 */
								ApiParamValue = "nl";
							}
						}

					}

					if (cust_param.equalsIgnoreCase("tmpc")) {
						int fahrenheit = Integer.parseInt(ApiParamValue);
						int celsius = ((fahrenheit - 32) * 5) / 9;
						ApiParamValue = String.valueOf(celsius);
					} else if (cust_param.equalsIgnoreCase("fltmpc")) {
						int fahrenheit = Integer.parseInt(ApiParamValue);
						int celsius = ((fahrenheit - 32) * 5) / 9;
						ApiParamValue = String.valueOf(celsius);
					}
					// ApiParamValue= mainTag.get(readExcelValues.data[paramtype][Cap]).toString();

					if (ReadExcelValues.data[paramtype][Cap].toString().equalsIgnoreCase("icon")) {
						paramName = "cnd";
					} else {
						paramName = ReadExcelValues.data[paramtype][0].toString();
					}
					// ads.add(ApiParams);
					if (ReadExcelValues.data[paramtype][4].toString().equalsIgnoreCase("Yes")) {
						ReadExcelValues.excelValues("Cust_Param_Result", cust_param);

						for (int CustParamValues = 1; CustParamValues <= ExcelData.rowcount; CustParamValues++) {
							if (ReadExcelValues.data[CustParamValues][1].contains("and more")) {
								String CellParam = ReadExcelValues.data[CustParamValues][1].toString();
								CellParam = CellParam.replaceAll("and more", "");
								CellParam = CellParam.replaceAll(" ", "");
								int celparamValue = Integer.parseInt(CellParam);
								int ApiParamNumber = Integer.parseInt(ApiParamValue);
								if (ApiParamNumber >= celparamValue) {
									ApiParamValue = ReadExcelValues.data[CustParamValues][2].toString();
									break;
								}
							} else if (ReadExcelValues.data[CustParamValues][1].contains("and less")) {
								String CellParam = ReadExcelValues.data[CustParamValues][1].toString();
								CellParam = CellParam.replaceAll("and less", "");
								CellParam = CellParam.replaceAll(" ", "");
								int celparamValue = Integer.parseInt(CellParam);
								int ApiParamNumber = Integer.parseInt(ApiParamValue);
								if (ApiParamNumber <= celparamValue) {
									ApiParamValue = ReadExcelValues.data[CustParamValues][2].toString();
									break;
								}
							} else if (ReadExcelValues.data[CustParamValues][1].contains(ApiParamValue)) {
								ApiParamValue = ReadExcelValues.data[CustParamValues][2].toString();
								break;
							}
						}
					}
					ApiParams = paramName + "=" + ApiParamValue;
					break;
				}
			}
		}
		System.out.println(cust_param + " Param Values from API Call is : " + ApiParamValue);
		if (ApiParamValue.equalsIgnoreCase("null")) {
			ApiParamValue = "nl";
		}
		return ApiParamValue;
	}
	
	public static String get_Expected_Value_From_APIResponseBody_V2(String Excelname, String sheetName, String zipCode, String cust_param, 
			 String apiData, String constant, String countryCode) throws Exception {
		 System.out.println("Api Data Sting format is : "+apiData);
		ReadExcelValues.excelValues(Excelname, sheetName);
		JSONParser parser = new JSONParser();
		// System.out.println("adreq1 is : "+adreq1.toString());
		Object obj = parser.parse(new String(apiData));
		// System.out.println("obj : "+obj);
		JSONObject jsonObject = (JSONObject) obj;
		String ApiParams = null;
		String ApiParamValue = null;
		String paramName = null;
		String JsonValues[] = null;
		String JsonParam = null;

		JSONObject mainTag = null;
		int custParamCount = ExcelData.rowcount;

		for (int paramtype = 1; paramtype <= custParamCount; paramtype++) {
			if (cust_param.equals(ReadExcelValues.data[paramtype][0])) {

				if (ReadExcelValues.data[paramtype][Cap].toString().equals("hardcode")) {
					System.out.println("Param type is Hard Code");
				} else {
					System.out.println("main tag is : " + ReadExcelValues.data[paramtype][2] + ", param type is : "
							+ ReadExcelValues.data[paramtype][Cap]);
					if (ReadExcelValues.data[paramtype][2].contains("direct")) {
						mainTag = (JSONObject) obj;
					} else if (ReadExcelValues.data[paramtype][2].contains(",")) {
						JsonValues = ReadExcelValues.data[paramtype][2].split(",");
						JsonParam = JsonValues[0].trim();
						mainTag = (JSONObject) jsonObject.get(JsonParam);
						try {
							if (cust_param.equalsIgnoreCase("wfxtg") || cust_param.equalsIgnoreCase("cxtg")
									|| cust_param.equalsIgnoreCase("nzcs") || cust_param.equalsIgnoreCase("zcs")
									|| cust_param.equalsIgnoreCase("hzcs")) {
								mainTag = (JSONObject) mainTag.get(JsonValues[1].trim()+":"+zipCode+":"+constant+":"+countryCode);
							} else {
								mainTag = (JSONObject) mainTag.get(JsonValues[1].trim());
							}
							
						} catch (Exception e) {
							if (cust_param.equalsIgnoreCase("fcnd") || cust_param.equalsIgnoreCase("fdynght")) {
								JSONArray eleArray = (JSONArray) mainTag.get(JsonValues[1].trim());
								/*
								 * Note that, this JSONArray has only one json object in it, hence loop ends
								 * after one iteration.
								 */
								ArrayList<String> Ingredients_names = new ArrayList<>();
								for (int i = 0; i < eleArray.size(); i++) {
									String arrayElement = String.valueOf(eleArray.get(i));

									Ingredients_names.add(arrayElement);
									obj = parser.parse(new String(arrayElement));
									jsonObject = (JSONObject) obj;
									mainTag = (JSONObject) obj;
									String[] arEls = arrayElement.split(",");
								}
								// System.out.println(Ingredients_names);
							} else if (cust_param.equalsIgnoreCase("cxtg") || cust_param.equalsIgnoreCase("zcs")
									|| cust_param.equalsIgnoreCase("hzcs") || cust_param.equalsIgnoreCase("nzcs")) {
								JSONArray eleArray = (JSONArray) mainTag.get(JsonValues[1].trim());
								/*
								 * Generally Scatteredtags returns 3 objects, zcs, hzcs and nzcs in order in
								 * turn each object zcs, hzcs and nzcs is an array of json objects
								 */
								ArrayList<String> Ingredients_names = new ArrayList<>();
								// String arrayElement="";
								outerloop: for (int i = 0; i < eleArray.size(); i++) {
									String arrayElement = String.valueOf(eleArray.get(i));

									Ingredients_names.add(arrayElement);
									obj = parser.parse(new String(arrayElement));
									jsonObject = (JSONObject) obj;
									mainTag = (JSONObject) obj;

									try {
										/*
										 * since same block being used for cxtg, zcs, hzcs and nzcs, there are
										 * possibilities that we may get null value in first iteration for hzcs and nzcs
										 * as these are elements of second object onwards, hence exception. so when we
										 * get exception continuing to next iteration
										 */
										JSONArray eleArrays = (JSONArray) mainTag.get(JsonValues[2].trim());
										ArrayList<String> Ingredients_namess = new ArrayList<>();
										// String[] arEls = arrayElement.split(",");
										for (int j = 0; j < eleArrays.size(); j++) {
											String arrayElementt = String.valueOf(eleArrays.get(j));

											Ingredients_namess.add(arrayElementt);
											obj = parser.parse(new String(arrayElementt));
											jsonObject = (JSONObject) obj;
											mainTag = (JSONObject) obj;
											String zip = "";
											try {
												zip = String.valueOf(mainTag.get("zip"));

											} catch (Exception e1) {
												System.out.println("An Exception while fetching zip value");
												logStep("An Exception while fetching zip value");
											}
											/*
											 * since the parameters are mapped to respective zip codes and selection to
											 * be based on zip code, hence zip code comparision introduced
											 */
											if (zip.equalsIgnoreCase(zipCode)) {

												break outerloop;
											} else {
												/*
												 * this else block is written to check whether zip code found in json
												 * objects. if not printing a log message, as the exception being
												 * automatically handled and returns null value in the step JSONArray
												 * arrayElementValues = (JSONArray)
												 * mainTag.get(readExcelValues.data[paramtype][Cap]);
												 */
												if (j == eleArrays.size() - 1) {
													System.out.println("Expected Zip Code :" + zipCode
															+ " not found in JSON Array Objects, during custom parameter "
															+ cust_param + " validation.");
													logStep("Expected Zip Code :" + zipCode
															+ " not found in JSON Array Objects, during custom parameter "
															+ cust_param + " validation.");
													mainTag = null;
												}
											}

										}
										break outerloop;
									} catch (Exception e2) {
										continue;
									}

								}

							}

						}

					} else {
						JsonParam = ReadExcelValues.data[paramtype][2].trim();
						mainTag = (JSONObject) jsonObject.get(JsonParam);
					}

					if (cust_param.equalsIgnoreCase("fcnd") || cust_param.equalsIgnoreCase("fdynght")) {
						JSONArray dayPartElementValues = (JSONArray) mainTag.get(ReadExcelValues.data[paramtype][Cap]);
						if (String.valueOf(dayPartElementValues.get(0)).equalsIgnoreCase("null")) {
							ApiParamValue = String.valueOf(dayPartElementValues.get(1));
						} else {
							ApiParamValue = String.valueOf(dayPartElementValues.get(0));
						}

					} else {
						try {
							/*
							 * Here this will try to get the parameter values into jSON Array when it doesnt
							 * find any parameter it is expected, for ex: it is not seen xcurrent in json
							 * response then it returns 'null' to arrayElementValues. and continues in this
							 * try block to get ApiParamValue if jSON array doesnt exist then an exception
							 * while getting the ApiParamValue. it goes to catch block to see if it is a
							 * String
							 * 
							 */
							JSONArray arrayElementValues = (JSONArray) mainTag
									.get(ReadExcelValues.data[paramtype][Cap]);
							
							/*
							 * Certain custom parameters needed single element out of array and certain
							 * parameters needed complete array
							 */

							if (cust_param.equalsIgnoreCase("wfxtg") || cust_param.equalsIgnoreCase("cxtg")
									|| cust_param.equalsIgnoreCase("nzcs") || cust_param.equalsIgnoreCase("zcs")
									|| cust_param.equalsIgnoreCase("hzcs")) {
								if (arrayElementValues.isEmpty()) {
									ApiParamValue = "nl";
								} else {
									ApiParamValue = StringUtils.jSONArrayToString(arrayElementValues);
								}
								

							} else {
								ApiParamValue = String.valueOf(arrayElementValues.get(0));
							}

						} catch (Exception e) {
							try {
								/*
								 * wfxtg and cxtg parameters use encoded values 'xcurrent' and 'xcxtg'
								 * respectively. these are strings only where other parameters of trigger call
								 * are arrays.
								 * 
								 */
								/*
								 * Here this will try to get the parameter values into String when it doesnt
								 * find any parameter it is expected, for ex: it is not seen xcurrent in json
								 * response then it returns 'null' to ApiParamValue, without any exception for
								 * nonexisting parameter. at the end null value will be set to 'nl'
								 * 
								 */
								
								ApiParamValue = String.valueOf(mainTag.get(ReadExcelValues.data[paramtype][Cap]));
								
							} catch (Exception e3) {
								/*
								 * This is handled because for cxtg, zcs, hzcs and nzcs if the expected zip code
								 * is not there then throwing an exception and setting ApiParamValue to nl
								 */
								ApiParamValue = "nl";
							}
						}

					}

					if (cust_param.equalsIgnoreCase("tmpc")) {
						int fahrenheit = Integer.parseInt(ApiParamValue);
						int celsius = ((fahrenheit - 32) * 5) / 9;
						ApiParamValue = String.valueOf(celsius);
					} else if (cust_param.equalsIgnoreCase("fltmpc")) {
						int fahrenheit = Integer.parseInt(ApiParamValue);
						int celsius = ((fahrenheit - 32) * 5) / 9;
						ApiParamValue = String.valueOf(celsius);
					}
					// ApiParamValue= mainTag.get(readExcelValues.data[paramtype][Cap]).toString();

					if (ReadExcelValues.data[paramtype][Cap].toString().equalsIgnoreCase("icon")) {
						paramName = "cnd";
					} else {
						paramName = ReadExcelValues.data[paramtype][0].toString();
					}
					// ads.add(ApiParams);
					if (ReadExcelValues.data[paramtype][4].toString().equalsIgnoreCase("Yes")) {
						ReadExcelValues.excelValues("Cust_Param_Result", cust_param);

						for (int CustParamValues = 1; CustParamValues <= ExcelData.rowcount; CustParamValues++) {
							if (ReadExcelValues.data[CustParamValues][1].contains("and more")) {
								String CellParam = ReadExcelValues.data[CustParamValues][1].toString();
								CellParam = CellParam.replaceAll("and more", "");
								CellParam = CellParam.replaceAll(" ", "");
								int celparamValue = Integer.parseInt(CellParam);
								int ApiParamNumber = Integer.parseInt(ApiParamValue);
								if (ApiParamNumber >= celparamValue) {
									ApiParamValue = ReadExcelValues.data[CustParamValues][2].toString();
									break;
								}
							} else if (ReadExcelValues.data[CustParamValues][1].contains("and less")) {
								String CellParam = ReadExcelValues.data[CustParamValues][1].toString();
								CellParam = CellParam.replaceAll("and less", "");
								CellParam = CellParam.replaceAll(" ", "");
								int celparamValue = Integer.parseInt(CellParam);
								int ApiParamNumber = Integer.parseInt(ApiParamValue);
								if (ApiParamNumber <= celparamValue) {
									ApiParamValue = ReadExcelValues.data[CustParamValues][2].toString();
									break;
								}
							} else if (ReadExcelValues.data[CustParamValues][1].contains(ApiParamValue)) {
								ApiParamValue = ReadExcelValues.data[CustParamValues][2].toString();
								break;
							}
						}
					}
					ApiParams = paramName + "=" + ApiParamValue;
					break;
				}
			}
		}
		System.out.println(cust_param + " Param Values from API Call is : " + ApiParamValue);
		if (ApiParamValue.equalsIgnoreCase("null")) {
			ApiParamValue = "nl";
		}
		return ApiParamValue;
	}

	/**
	 * Get custom parameter value of gampad call
	 * @param excelName
	 * @param sheetName
	 * @param cust_param
	 * @return
	 * @throws Exception
	 */
	public static String get_custom_param_val_of_gampad(String excelName, String sheetName, String cust_param)
			throws Exception {
		/*
		 * Calendar calendar = Calendar.getInstance(); Date d = new Date();
		 * SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // the day of
		 * the week abbreviated String today = simpleDateformat.format(d); today =
		 * today.toLowerCase().concat("1");
		 */
		String today = dailyDetailsDayOfWeek.concat("1");

		boolean adCallFound = false;

		// Read the content form file
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		/*
		 * if(expected.equalsIgnoreCase("null")) { expected = "nl"; }
		 */
		// Getting custom_params amzn_b values
		List<String> customParamsList = new ArrayList<String>();

		// String iuId =
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		String iuId = null;
		ReadExcelValues.excelValues(excelName, sheetName);
		if (cust_param.equalsIgnoreCase("fcnd")) {
			iuId = ReadExcelValues.data[18][Cap];
			iuId = iuId.concat("_") + today;
		} else if (sheetName.equalsIgnoreCase("PreRollVideo")) {
			iuId = videoIUValue;
		} else {
			iuId = ReadExcelValues.data[18][Cap];
		}

		String tempCustmParam = null;
		for (String qry : getQueryList) {
			if (qry.contains(iuId)) {
				adCallFound = true;
				tempCustmParam = getCustomParamBy_iu_value(qry, cust_param);
				// if (!"".equals(tempCustmParam))
				// customParamsList.add(getCustomParamsBy_iu_value(qry));
				break;
			}

		}

		return tempCustmParam;

	}

	/**
	 * This method validates the Custom Parameter of gampad call with the
	 * corresponding parameter in respective API Call. This requires only Custom
	 * Parameter as input
	 * 
	 * @param excelName
	 * @param sheetName
	 * @param cust_param
	 * @throws Exception
	 */
	public static void validate_custom_param_val_of_gampad(String excelName, String sheetName, String cust_param)
			throws Exception {
		/*
		 * Calendar calendar = Calendar.getInstance(); Date d = new Date();
		 * SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // the day of
		 * the week abbreviated String today = simpleDateformat.format(d); today =
		 * today.toLowerCase().concat("1");
		 */
		String today = null;

		boolean adCallFound = false;

		// Read the content form file
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);
		String expected = null;
		if (cust_param.equalsIgnoreCase("dt")) {
			expected = dailyDetailsDateOfDay;
		} else if (cust_param.equalsIgnoreCase("mnth")) {
			expected = dailyDetailsMonthOfDate;
		} else {
			expected = get_param_value_from_APICalls(cust_param);
		}

		/*
		 * if(expected.equalsIgnoreCase("null")) { expected = "nl"; }
		 */
		// Getting custom_params amzn_b values
		List<String> customParamsList = new ArrayList<String>();

		// String iuId =
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		String iuId = null;
		ReadExcelValues.excelValues(excelName, sheetName);
		if (cust_param.equalsIgnoreCase("fcnd")) {
			try {
				today = dailyDetailsDayOfWeek.concat("1");
			} catch (Exception e) {
				System.out.println("An exception while parsing today value");
				logStep("An exception while parsing today value");
			}

			iuId = ReadExcelValues.data[18][Cap];
			iuId = iuId.concat("_") + today;
		} else if (sheetName.equalsIgnoreCase("PreRollVideo")) {
			iuId = videoIUValue;
		} else {
			iuId = ReadExcelValues.data[18][Cap];
		}

		String tempCustmParam = null;
		for (String qry : getQueryList) {
			if (qry.contains(iuId)) {
				adCallFound = true;
				tempCustmParam = getCustomParamBy_iu_value(qry, cust_param);
				// if (!"".equals(tempCustmParam))
				// customParamsList.add(getCustomParamsBy_iu_value(qry));
				if (cust_param.equalsIgnoreCase("dt")) {
					
					if (Ad instanceof AndroidDriver<?>) {
						if (tempCustmParam.equalsIgnoreCase("1")) {
							tempCustmParam = "01";
						} else if (tempCustmParam.equalsIgnoreCase("2")) {
							tempCustmParam = "02";
						} else if (tempCustmParam.equalsIgnoreCase("3")) {
							tempCustmParam = "03";
						} else if (tempCustmParam.equalsIgnoreCase("4")) {
							tempCustmParam = "04";
						} else if (tempCustmParam.equalsIgnoreCase("5")) {
							tempCustmParam = "05";
						} else if (tempCustmParam.equalsIgnoreCase("6")) {
							tempCustmParam = "06";
						} else if (tempCustmParam.equalsIgnoreCase("7")) {
							tempCustmParam = "07";
						} else if (tempCustmParam.equalsIgnoreCase("8")) {
							tempCustmParam = "08";
						} else if (tempCustmParam.equalsIgnoreCase("9")) {
							tempCustmParam = "09";
						}
						
					}
				}
				break;
			}

		}
		if (nextGenIMadDisplayed && sheetName.equalsIgnoreCase("Pulltorefresh")) {
			System.out.println("Since IM Ad displayed on App Launch, Homescreen call validation is skipped");
			logStep("Since IM Ad displayed on App Launch, Homescreen call validation is skipped");
		} else {
			if (expected == null) {
				System.out.println(
						"Either Parameter value is empty or API Call is not generated, hence Custom Parameter validation skipped");
				logStep("Either Parameter value is empty or API Call is not generated, hence Custom Parameter validation skipped");
				System.out.println("Custom Parameter :" + cust_param + " validation is failed");
				logStep("Custom Parameter :" + cust_param + " validation is failed");
				Assert.fail(
						"Either Parameter value is empty or API Call is not generated, hence Custom Parameter validation skipped");
			} else if (!adCallFound) {
				System.out.println("Ad Call :" + iuId + " not found in charles session, hence Custom Parameter: "
						+ cust_param + " validation skipped");
				logStep("Ad Call :" + iuId + " not found in charles session, hence Custom Parameter: " + cust_param
						+ " validation skipped");
				System.out.println("Custom Parameter :" + cust_param + " validation is failed");
				logStep("Custom Parameter :" + cust_param + " validation is failed");
				Assert.fail("Ad Call :" + iuId + " not found in charles session, hence Custom Parameter: " + cust_param
						+ " validation skipped");
			} else if (adCallFound && !tempCustmParam.isEmpty()) {
				System.out
						.println(cust_param + " Param value from gampad call  of : " + iuId + " is " + tempCustmParam);
				if (expected.equalsIgnoreCase("NotNull")) {
					if (!tempCustmParam.equalsIgnoreCase("nl")) {
						System.out.println("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is matched with the expected value " + expected);
						logStep("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is matched with the expected value " + expected);
						System.out.println("Custom Parameter :" + cust_param + " validation is successful");
						logStep("Custom Parameter :" + cust_param + " validation is successful");
					} else {
						System.out.println("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is not matched with the expected value " + expected);
						logStep("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is not matched with the expected value " + expected);
						System.out.println("Custom Parameter :" + cust_param + " validation is failed");
						logStep("Custom Parameter :" + cust_param + " validation is failed");
						Assert.fail("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is not matched with the expected value " + expected);
					}
				} else {
					if (tempCustmParam.equalsIgnoreCase(expected)) {
						System.out.println("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is matched with the expected value: " + expected);
						logStep("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is matched with the expected value: " + expected);
						System.out.println("Custom Parameter :" + cust_param + " validation is successful");
						logStep("Custom Parameter :" + cust_param + " validation is successful");
					} else {
						System.out.println("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is not matched with the expected value " + expected);
						logStep("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is not matched with the expected value: " + expected);
						System.out.println("Custom Parameter :" + cust_param + " validation is failed");
						logStep("Custom Parameter :" + cust_param + " validation is failed");
						Assert.fail("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is not matched with the expected value: " + expected);
					}
				}

			} else if (tempCustmParam == null || tempCustmParam.isEmpty()) {
				System.out.println(
						"Custom parameter :" + cust_param + " not found/no value in ad call, hence Custom Parameter: "
								+ cust_param + " validation skipped");
				logStep("Custom parameter :" + cust_param + " not found/no value in ad call, hence Custom Parameter: "
						+ cust_param + " validation skipped");
				System.out.println("Custom Parameter :" + cust_param + " validation is failed");
				logStep("Custom Parameter :" + cust_param + " validation is failed");
				Assert.fail(
						"Custom parameter :" + cust_param + " not found/no value in ad call, hence Custom Parameter: "
								+ cust_param + " validation skipped");
			}
		}

	}

	/**
	 * This method validates the Custom Parameter of gampad call with the expected
	 * value sent as parameter. This requires both Custom Parameter and expected
	 * value as input
	 * 
	 * @param excelName
	 * @param sheetName
	 * @param cust_param
	 * @param expected
	 * @throws Exception
	 */
	public static void validate_custom_param_val_of_gampad(String excelName, String sheetName, String cust_param,
			String expected) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String today = null;
		boolean adCallFound = false;

		// Read the content form file
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		// Getting custom_params amzn_b values
		List<String> customParamsList = new ArrayList<String>();

		String iuId = null;
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		if (cust_param.equalsIgnoreCase("fcnd")) {
			try {
				today = dailyDetailsDayOfWeek.concat("1");
			} catch (Exception e) {
				System.out.println("An exception while parsing today value");
				logStep("An exception while parsing today value");
			}

			iuId = ReadExcelValues.data[18][Cap];
			iuId = iuId.concat("_") + today;
		} else if (sheetName.equalsIgnoreCase("PreRollVideo")) {
			iuId = videoIUValue;
		} else {
			iuId = ReadExcelValues.data[18][Cap];
		}
		String tempCustmParam = null;
		for (String qry : getQueryList) {
			if (qry.contains(iuId)) {
				adCallFound = true;
				tempCustmParam = getCustomParamBy_iu_value(qry, cust_param);
				// if (!"".equals(tempCustmParam))
				// customParamsList.add(getCustomParamsBy_iu_value(qry));
				break;
			}

		}

		if (nextGenIMadDisplayed && sheetName.equalsIgnoreCase("Pulltorefresh")) {
			System.out.println("Since IM Ad displayed on App Launch, Homescreen call validation is skipped");
			logStep("Since IM Ad displayed on App Launch, Homescreen call validation is skipped");
		} else {

			if (!adCallFound) {
				System.out.println("Ad Call :" + iuId + " not found in charles session, hence Custom Parameter: "
						+ cust_param + " validation skipped");
				logStep("Ad Call :" + iuId + " not found in charles session, hence Custom Parameter: " + cust_param
						+ " validation skipped");
				System.out.println("Custom Parameter :" + cust_param + " validation is failed");
				logStep("Custom Parameter :" + cust_param + " validation is failed");
				Assert.fail("Ad Call :" + iuId + " not found in charles session, hence Custom Parameter: " + cust_param
						+ " validation skipped");
			} else if (adCallFound && !tempCustmParam.isEmpty()) {
				System.out.println(cust_param + " value of from gampad call  of : " + iuId + " is " + tempCustmParam);
				if (expected.equalsIgnoreCase("NotNull")) {
					if (!tempCustmParam.equalsIgnoreCase("nl")) {
						System.out.println("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is matched with the expected value " + expected);
						logStep("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is matched with the expected value " + expected);
						System.out.println("Custom Parameter :" + cust_param + " validation is successful");
						logStep("Custom Parameter :" + cust_param + " validation is successful");
					} else {
						System.out.println("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is not matched with the expected value " + expected);
						logStep("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is not matched with the expected value " + expected);
						System.out.println("Custom Parameter :" + cust_param + " validation is failed");
						logStep("Custom Parameter :" + cust_param + " validation is failed");
						Assert.fail("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is not matched with the expected value " + expected);
					}
				} else {
					if (tempCustmParam.equalsIgnoreCase(expected)) {
						System.out.println("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is matched with the expected value " + expected);
						logStep("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is matched with the expected value " + expected);
						System.out.println("Custom Parameter :" + cust_param + " validation is successful");
						logStep("Custom Parameter :" + cust_param + " validation is successful");
					} else {
						System.out.println("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is not matched with the expected value " + expected);
						logStep("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is not matched with the expected value " + expected);
						System.out.println("Custom Parameter :" + cust_param + " validation is failed");
						logStep("Custom Parameter :" + cust_param + " validation is failed");
						Assert.fail("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is not matched with the expected value " + expected);
					}
				}

			} else if (tempCustmParam == null || tempCustmParam.isEmpty()) {
				System.out.println(
						"Custom parameter :" + cust_param + " not found/no value in ad call, hence Custom Parameter: "
								+ cust_param + " validation skipped");
				logStep("Custom parameter :" + cust_param + " not found/no value in ad call, hence Custom Parameter: "
						+ cust_param + " validation skipped");
				System.out.println("Custom Parameter :" + cust_param + " validation is failed");
				logStep("Custom Parameter :" + cust_param + " validation is failed");
				Assert.fail(
						"Custom parameter :" + cust_param + " not found/no value in ad call, hence Custom Parameter: "
								+ cust_param + " validation skipped");
			}
		}
	}

	/**
	 * This method simply validates the presence of Custom Parameter of gampad call,
	 * it doesn't compare anything with api call
	 * 
	 * @param excelName
	 * @param sheetName
	 * @param cust_param
	 * @param isCustomParamExpectedToPresent
	 * @throws Exception
	 */
	public static void validate_custom_param_val_of_gampad(String excelName, String sheetName, String cust_param,
			boolean isCustomParamExpectedToPresent) throws Exception {
		/*
		 * Calendar calendar = Calendar.getInstance(); Date d = new Date();
		 * SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // the day of
		 * the week abbreviated String today = simpleDateformat.format(d); today =
		 * today.toLowerCase().concat("1");
		 */
		String today = null;

		boolean adCallFound = false;
		boolean isParameterFound = false;

		// Read the content form file
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		/*
		 * if(expected.equalsIgnoreCase("null")) { expected = "nl"; }
		 */
		// Getting custom_params amzn_b values
		List<String> customParamsList = new ArrayList<String>();

		// String iuId =
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		String iuId = null;
		ReadExcelValues.excelValues(excelName, sheetName);
		if (cust_param.equalsIgnoreCase("fcnd")) {
			try {
				today = dailyDetailsDayOfWeek.concat("1");
			} catch (Exception e) {
				System.out.println("An exception while parsing today value");
				logStep("An exception while parsing today value");
			}

			iuId = ReadExcelValues.data[18][Cap];
			iuId = iuId.concat("_") + today;
		} else if (sheetName.equalsIgnoreCase("PreRollVideo")) {
			iuId = videoIUValue;
		} else {
			iuId = ReadExcelValues.data[18][Cap];
		}

		String tempCustmParam = null;
		for (String qry : getQueryList) {
			if (qry.contains(iuId)) {
				adCallFound = true;
				tempCustmParam = getCustomParamBy_iu_value(qry, cust_param);
				// if (!"".equals(tempCustmParam))
				// customParamsList.add(getCustomParamsBy_iu_value(qry));
				break;
			}

		}
		if (nextGenIMadDisplayed && sheetName.equalsIgnoreCase("Pulltorefresh")) {
			System.out.println("Since IM Ad displayed on App Launch, Homescreen call validation is skipped");
			logStep("Since IM Ad displayed on App Launch, Homescreen call validation is skipped");
		} else {
			if (!adCallFound) {
				System.out.println("Ad Call :" + iuId + " not found in charles session, hence Custom Parameter: "
						+ cust_param + " validation skipped");
				logStep("Ad Call :" + iuId + " not found in charles session, hence Custom Parameter: " + cust_param
						+ " validation skipped");
				System.out.println("Custom Parameter :" + cust_param + " validation is failed");
				logStep("Custom Parameter :" + cust_param + " validation is failed");
				Assert.fail("Ad Call :" + iuId + " not found in charles session, hence Custom Parameter: " + cust_param
						+ " validation skipped");
			} else if (adCallFound && !tempCustmParam.isEmpty()) {
				System.out
						.println(cust_param + " Param value from gampad call  of : " + iuId + " is " + tempCustmParam);
				logStep(cust_param + " Param value from gampad call  of : " + iuId + " is " + tempCustmParam);

				isParameterFound = true;

			} else if (tempCustmParam == null || tempCustmParam.isEmpty()) {
				System.out.println("Custom parameter :" + cust_param + " not found/no value in ad call");
				logStep("Custom parameter :" + cust_param + " not found/no value in ad call");
				isParameterFound = false;
				// System.out.println("Custom Parameter :" + cust_param + " validation is
				// failed");
				// logStep("Custom Parameter :" + cust_param + " validation is failed");
				// Assert.fail("Custom parameter :" + cust_param + " not found/no value in ad
				// call, hence Custom Parameter: "+ cust_param + " validation skipped");
			}
		}

		if (isCustomParamExpectedToPresent == isParameterFound) {
			System.out.println(cust_param + " Custom Parameter Verification is successful");
			logStep(cust_param + " Custom Parameter Verification is successful");

		} else {
			System.out.println(cust_param + " Custom Parameter Verification is failed");
			logStep(cust_param + " Custom Parameter Verification is failed");

			if (isCustomParamExpectedToPresent) {
				System.out.println(cust_param + " Custom Parameter expected to present in " + sheetName
						+ " gampad call, but it not exists");
				logStep(cust_param + " Custom Parameter expected to present in " + sheetName
						+ " gampad call, but it not exists");
				Assert.fail(cust_param + " Custom Parameter expected to present in " + sheetName
						+ " gampad call, but it not exists");
			} else {
				System.out.println(cust_param + " Custom Parameter is not expected to present in " + sheetName
						+ " gampad call, but it exists");
				logStep(cust_param + " Custom Parameter is not expected to present in " + sheetName
						+ " gampad call, but it exists");
				Assert.fail(cust_param + " Custom Parameter is not expected to present in " + sheetName
						+ " gampad call, but it exists");
			}
		}

	}

	/**
	 * This method validates the Custom Parameter of gampad call with the
	 * corresponding parameter in respective API Call by retrieving the value based
	 * on zipcode . This requires Custom Parameter and zipcode as input
	 * 
	 * @param excelName
	 * @param sheetName
	 * @param cust_param
	 * @param zipCode
	 * @throws Exception
	 */
	public static void validate_custom_param_val_of_gampad_with_zip(String excelName, String sheetName,
			String cust_param, String zipCode) throws Exception {
		/*
		 * Calendar calendar = Calendar.getInstance(); Date d = new Date();
		 * SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // the day of
		 * the week abbreviated String today = simpleDateformat.format(d); today =
		 * today.toLowerCase().concat("1");
		 */
		if (cust_param.equalsIgnoreCase("wfxtg") || cust_param.equalsIgnoreCase("hzcs")) {
			zipCode = Utils.hlzip;
		}
		String today = null;

		boolean adCallFound = false;

		// Read the content form file
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);
		String expected = null;
		if (cust_param.equalsIgnoreCase("dt")) {
			expected = dailyDetailsDateOfDay;
		} else if (cust_param.equalsIgnoreCase("mnth")) {
			expected = dailyDetailsMonthOfDate;
		} else if (sheetName.equalsIgnoreCase("PreRollVideo") || sheetName.equalsIgnoreCase("Pulltorefresh")) {
			if (cust_param.equalsIgnoreCase("wfxtg") || cust_param.equalsIgnoreCase("cxtg")
					|| cust_param.equalsIgnoreCase("zcs") || cust_param.equalsIgnoreCase("hzcs")
					|| cust_param.equalsIgnoreCase("nzcs")) {
				expected = wfxParameters.get(cust_param);
			} else {
				if (Ad instanceof AndroidDriver<?>) {
					expected = get_param_value_from_APICalls(cust_param, zipCode);
				} else {
					expected = get_param_value_from_APICalls_V2(cust_param, zipCode, "4", "US");
				}
				
			}

		} else {
			if (Ad instanceof AndroidDriver<?>) {
				expected = get_param_value_from_APICalls(cust_param, zipCode);
			} else {
				expected = get_param_value_from_APICalls_V2(cust_param, zipCode, "4", "US");
			}
			
		}

		/*
		 * if(expected.equalsIgnoreCase("null")) { expected = "nl"; }
		 */
		// Getting custom_params amzn_b values
		List<String> customParamsList = new ArrayList<String>();

		// String iuId =
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		String iuId = null;
		ReadExcelValues.excelValues(excelName, sheetName);
		if (cust_param.equalsIgnoreCase("fcnd")) {
			try {
				today = dailyDetailsDayOfWeek.concat("1");
			} catch (Exception e) {
				System.out.println("An exception while parsing today value");
				logStep("An exception while parsing today value");
			}

			iuId = ReadExcelValues.data[18][Cap];
			iuId = iuId.concat("_") + today;
		} else if (sheetName.equalsIgnoreCase("PreRollVideo")) {
			iuId = videoIUValue;
		} else {
			iuId = ReadExcelValues.data[18][Cap];
		}

		String tempCustmParam = null;
		for (String qry : getQueryList) {
			if (qry.contains(iuId)) {
				adCallFound = true;
				tempCustmParam = getCustomParamBy_iu_value(qry, cust_param);
				// if (!"".equals(tempCustmParam))
				// customParamsList.add(getCustomParamsBy_iu_value(qry));
				break;
			}

		}
		if (nextGenIMadDisplayed && sheetName.equalsIgnoreCase("Pulltorefresh")) {
			System.out.println("Since IM Ad displayed on App Launch, Homescreen call validation is skipped");
			logStep("Since IM Ad displayed on App Launch, Homescreen call validation is skipped");
		} else {
			if (expected == null) {
				System.out.println(
						"Either Parameter value is empty or API Call is not generated, hence Custom Parameter validation skipped");
				logStep("Either Parameter value is empty or API Call is not generated, hence Custom Parameter validation skipped");
				System.out.println("Custom Parameter :" + cust_param + " validation is failed");
				logStep("Custom Parameter :" + cust_param + " validation is failed");
				Assert.fail(
						"Either Parameter value is empty or API Call is not generated, hence Custom Parameter validation skipped");
			} else if (!adCallFound) {
				System.out.println("Ad Call :" + iuId + " not found in charles session, hence Custom Parameter: "
						+ cust_param + " validation skipped");
				logStep("Ad Call :" + iuId + " not found in charles session, hence Custom Parameter: " + cust_param
						+ " validation skipped");
				System.out.println("Custom Parameter :" + cust_param + " validation is failed");
				logStep("Custom Parameter :" + cust_param + " validation is failed");
				Assert.fail("Ad Call :" + iuId + " not found in charles session, hence Custom Parameter: " + cust_param
						+ " validation skipped");
			} else if (adCallFound && !tempCustmParam.isEmpty()) {
				System.out
						.println(cust_param + " Param value from gampad call  of : " + iuId + " is " + tempCustmParam);
				if (expected.equalsIgnoreCase("NotNull")) {
					if (!tempCustmParam.equalsIgnoreCase("nl")) {
						System.out.println("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is matched with the expected value " + expected);
						logStep("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is matched with the expected value " + expected);
						System.out.println("Custom Parameter :" + cust_param + " validation is successful");
						logStep("Custom Parameter :" + cust_param + " validation is successful");
					} else {
						System.out.println("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is not matched with the expected value " + expected);
						logStep("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is not matched with the expected value " + expected);
						System.out.println("Custom Parameter :" + cust_param + " validation is failed");
						logStep("Custom Parameter :" + cust_param + " validation is failed");
						Assert.fail("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is not matched with the expected value " + expected);
					}
				} else {
					if (tempCustmParam.equalsIgnoreCase(expected)) {
						System.out.println("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is matched with the expected value: " + expected);
						logStep("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is matched with the expected value: " + expected);
						System.out.println("Custom Parameter :" + cust_param + " validation is successful");
						logStep("Custom Parameter :" + cust_param + " validation is successful");
					} else {
						System.out.println("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is not matched with the expected value " + expected);
						logStep("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is not matched with the expected value: " + expected);
						System.out.println("Custom Parameter :" + cust_param + " validation is failed");
						logStep("Custom Parameter :" + cust_param + " validation is failed");
						Assert.fail("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is not matched with the expected value: " + expected);
					}
				}

			} else if (tempCustmParam == null || tempCustmParam.isEmpty()) {
				System.out.println(
						"Custom parameter :" + cust_param + " not found/no value in ad call, hence Custom Parameter: "
								+ cust_param + " validation skipped");
				logStep("Custom parameter :" + cust_param + " not found/no value in ad call, hence Custom Parameter: "
						+ cust_param + " validation skipped");
				System.out.println("Custom Parameter :" + cust_param + " validation is failed");
				logStep("Custom Parameter :" + cust_param + " validation is failed");
				Assert.fail(
						"Custom parameter :" + cust_param + " not found/no value in ad call, hence Custom Parameter: "
								+ cust_param + " validation skipped");
			}
		}

	}

	/**
	 * Validates non-custom parameter whether to present in gampad call or not
	 * @param excelName
	 * @param sheetName
	 * @param cust_param
	 * @param expected
	 * @throws Exception
	 */
	public static void validate_Noncustom_param_val_of_gampad(String excelName, String sheetName, String cust_param,
			String expected) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		boolean adCallFound = false;

		// Read the content form file
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		// Getting custom_params amzn_b values
		List<String> customParamsList = new ArrayList<String>();

		String iuId = null;
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		if (sheetName.equalsIgnoreCase("PreRollVideo")) {
			iuId = videoIUValue;
		} else {
			iuId = ReadExcelValues.data[18][Cap];
		}
		String tempCustmParam = null;
		for (String qry : getQueryList) {
			if (qry.contains(iuId)) {
				adCallFound = true;
				tempCustmParam = getNonCustomParamBy_iu_value(qry, cust_param);
				// if (!"".equals(tempCustmParam))
				// customParamsList.add(getCustomParamsBy_iu_value(qry));
				break;
			}

		}

		if (!adCallFound) {
			System.out.println("Ad Call :" + iuId + " not found in charles session, hence Custom Parameter: "
					+ cust_param + " validation skipped");
			logStep("Ad Call :" + iuId + " not found in charles session, hence Custom Parameter: " + cust_param
					+ " validation skipped");
			System.out.println("Custom Parameter :" + cust_param + " validation is failed");
			logStep("Custom Parameter :" + cust_param + " validation is failed");
			Assert.fail("Ad Call :" + iuId + " not found in charles session, hence Custom Parameter: " + cust_param
					+ " validation skipped");
		} else if (adCallFound && !tempCustmParam.isEmpty()) {
			System.out.println(cust_param + " value of from gampad call  of : " + iuId + " is " + tempCustmParam);
			if (expected.equalsIgnoreCase("NotNull")) {
				if (!tempCustmParam.equalsIgnoreCase("nl")) {
					System.out.println("Custom Parameter :" + cust_param + " value: " + tempCustmParam
							+ " is matched with the expected value " + expected);
					logStep("Custom Parameter :" + cust_param + " value: " + tempCustmParam
							+ " is matched with the expected value " + expected);
					System.out.println("Custom Parameter :" + cust_param + " validation is successful");
					logStep("Custom Parameter :" + cust_param + " validation is successful");
				} else {
					System.out.println("Custom Parameter :" + cust_param + " value: " + tempCustmParam
							+ " is not matched with the expected value " + expected);
					logStep("Custom Parameter :" + cust_param + " value: " + tempCustmParam
							+ " is not matched with the expected value " + expected);
					System.out.println("Custom Parameter :" + cust_param + " validation is failed");
					logStep("Custom Parameter :" + cust_param + " validation is failed");
					Assert.fail("Custom Parameter :" + cust_param + " value: " + tempCustmParam
							+ " is not matched with the expected value " + expected);
				}
			} else {
				if (tempCustmParam.equalsIgnoreCase(expected)) {
					System.out.println("Custom Parameter :" + cust_param + " value: " + tempCustmParam
							+ " is matched with the expected value " + expected);
					logStep("Custom Parameter :" + cust_param + " value: " + tempCustmParam
							+ " is matched with the expected value " + expected);
					System.out.println("Custom Parameter :" + cust_param + " validation is successful");
					logStep("Custom Parameter :" + cust_param + " validation is successful");
				} else {
					System.out.println("Custom Parameter :" + cust_param + " value: " + tempCustmParam
							+ " is not matched with the expected value " + expected);
					logStep("Custom Parameter :" + cust_param + " value: " + tempCustmParam
							+ " is not matched with the expected value " + expected);
					System.out.println("Custom Parameter :" + cust_param + " validation is failed");
					logStep("Custom Parameter :" + cust_param + " validation is failed");
					Assert.fail("Custom Parameter :" + cust_param + " value: " + tempCustmParam
							+ " is not matched with the expected value " + expected);
				}
			}

		} else if (tempCustmParam == null || tempCustmParam.isEmpty()) {
			System.out.println("Custom parameter :" + cust_param
					+ " not found/no value in ad call, hence Custom Parameter: " + cust_param + " validation skipped");
			logStep("Custom parameter :" + cust_param + " not found/no value in ad call, hence Custom Parameter: "
					+ cust_param + " validation skipped");
			System.out.println("Custom Parameter :" + cust_param + " validation is failed");
			logStep("Custom Parameter :" + cust_param + " validation is failed");
			Assert.fail("Custom parameter :" + cust_param + " not found/no value in ad call, hence Custom Parameter: "
					+ cust_param + " validation skipped");
		}

	}

	/**
	 * This method gets the iu value from the first gampad call (in sequence) of
	 * current charles session.
	 * 
	 * @param excelName
	 * @param sheetName
	 * @throws Exception
	 */
	public static void get_iu_value_of_Feedcall(String excelName, String sheetName) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		boolean adCallFound = false;
		videoIUValue = null;
		outfile = new File(System.getProperty("user.dir") + "/myoutputFile.xml");
		// Read the content form file
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		iuId = null;
		String iuValue = null;

		String tempCustmParam = null;
		for (String qry : getQueryList) {
			if (qry.contains("iu=")) {
				adCallFound = true;
				tempCustmParam = getNonCustomParamBy_iu_value(qry, "iu");
				// if (!"".equals(tempCustmParam))
				// customParamsList.add(getCustomParamsBy_iu_value(qry));
				break;
			}
		}
		try {
			iuValue = tempCustmParam.replace("/", "%2F");
			iuValue = "iu=" + iuValue;
			if (sheetName.equalsIgnoreCase("PreRollVideo")) {
				videoIUValue = iuValue;
				iuId = iuValue;
			} else {
				iuId = iuValue;
			}
		} catch (Exception e) {
			System.out.println("There is an exception while framing iu value");
			logStep("There is an exception while framing iu value");
		}

		if (!adCallFound) {
			System.out.println("Ad Call not found in charles session");
			logStep("Ad Call not found in charles session");
			System.out.println(sheetName + " :Ad Call Verification is failed");
			logStep(sheetName + " :Ad Call Verification is failed");
			Assert.fail("Ad Call not found in charles session");
		} else if (iuValue == null || iuValue.isEmpty()) {
			System.out.println("Ad Call not found/no value in ad call");
			logStep("Ad Call not found/no value in ad call");
			System.out.println(sheetName + " :Ad Call Verification is failed");
			logStep(sheetName + " :Ad Call Verification is failed");
			Assert.fail("Ad Call not found/no value in ad call");
		} else {
			System.out.println("Ad Call " + iuId + " found in charles session");
			logStep("Ad Call " + iuId + " found in charles session");
			System.out.println(sheetName + " :Ad Call Verification is successful");
			logStep(sheetName + " :Ad Call Verification is successful");
		}

	}

	/**
	 * Returns iu value of a gampad call
	 * @param excelName
	 * @param sheetName
	 * @return
	 * @throws Exception
	 */
	public static String return_iu_value_of_Feedcall(String excelName, String sheetName) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		boolean adCallFound = false;
		videoIUValue = null;
		outfile = new File(System.getProperty("user.dir") + "/myoutputFile.xml");
		// Read the content form file
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		// String iuId = null;
		iuId = null;
		String iuValue = null;

		String tempCustmParam = null;
		for (String qry : getQueryList) {
			if (qry.contains("iu=")) {
				adCallFound = true;
				tempCustmParam = getNonCustomParamBy_iu_value(qry, "iu");
				// if (!"".equals(tempCustmParam))
				// customParamsList.add(getCustomParamsBy_iu_value(qry));
				break;
			}
		}
		try {
			iuValue = tempCustmParam.replace("/", "%2F");
			iuValue = "iu=" + iuValue;
			if (sheetName.equalsIgnoreCase("PreRollVideo")) {
				videoIUValue = iuValue;
				iuId = iuValue;
			} else {
				iuId = iuValue;
			}
		} catch (Exception e) {
			System.out.println("There is an exception while framing iu value");
			logStep("There is an exception while framing iu value");
		}

		if (!adCallFound) {
			System.out.println("Ad Call not found in charles session");
			logStep("Ad Call not found in charles session");
			// Assert.fail("Ad Call not found in charles session");
		} else if (iuValue == null || iuValue.isEmpty()) {
			System.out.println("Ad Call not found/no value in ad call");
			logStep("Ad Call not found/no value in ad call");
			// Assert.fail("Ad Call not found/no value in ad call");
		} else {
			System.out.println("Ad Call " + iuId + " found in charles session");
			logStep("Ad Call " + iuId + " found in charles session");
		}
		return iuId;

	}

	/**
	 * returns iu value after formatting
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public static String return_iu_value_from_query_parameter_of_Feedcall(String query) throws Exception {

		String iuValue = null;
		String tempCustmParam = null;
		tempCustmParam = getNonCustomParamBy_iu_value(query, "iu");

		try {
			iuValue = tempCustmParam.replace("/", "%2F");
			iuValue = "iu=" + iuValue;

		} catch (Exception e) {
			System.out.println("There is an exception while framing iu value");
			logStep("There is an exception while framing iu value");
		}

		return iuValue;

	}

	/**
	 * This method should be executed as a pre-requisite method before verifying any
	 * daily details page test scripts to set the global parameters pertaiining to
	 * Daily Details page this reads the data from api.wether.com call
	 */
	public static void get_v3_wx_forecast_daily_15day_data() throws Exception {
		/*
		 * Calendar calendar = Calendar.getInstance(); Date d = new Date();
		 * SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // the day of
		 * the week abbreviated String today = simpleDateformat.format(d); today =
		 * today.toLowerCase().concat("1");
		 */
		String moonRiseTimeLocal = null;
		String dayOfWeek = null;

		// Read the content form file
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		dayOfWeek = get_param_value_from_APICalls("ddDayOfWeek");
		
		moonRiseTimeLocal = get_param_value_from_APICalls("ddDtAndMnth");
		
		if (moonRiseTimeLocal.isEmpty()) {
			//sometimes moonRiseTimeLocal comes with " " blank values, hence catch uses moonSetTimeLocal
			moonRiseTimeLocal = get_param_value_from_APICalls("ddDtAndMnth_fallback");
		}
		
		String[] dtParams = moonRiseTimeLocal.split("T");
		String dateFormat = dtParams[0];

		Date moonRiseTimeLocalDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateFormat);
		System.out.println(moonRiseTimeLocalDate);
		SimpleDateFormat month_date = new SimpleDateFormat("MMM", Locale.ENGLISH);
		SimpleDateFormat day_date = new SimpleDateFormat("dd", Locale.ENGLISH);

		System.out.println(month_date.format(moonRiseTimeLocalDate));
		System.out.println(day_date.format(moonRiseTimeLocalDate));

		dailyDetailsMonthOfDate = month_date.format(moonRiseTimeLocalDate).toLowerCase();
		dailyDetailsDateOfDay = day_date.format(moonRiseTimeLocalDate);

		/*
		 * String[] dtAndMonth = dtParams[0].split("-"); // dailyDetailsMonthOfDate =
		 * dtAndMonth[1]; String dailyDetailsMonthNum = dtAndMonth[1];
		 * dailyDetailsDateOfDay = dtAndMonth[2]; int monthNum =
		 * Integer.parseInt(dailyDetailsMonthNum);
		 * 
		 * int num = monthNum - 1; String month = "wrong"; DateFormatSymbols dfs = new
		 * DateFormatSymbols(); String[] months = dfs.getShortMonths(); if (num >= 0 &&
		 * num <= 11) { month = months[num]; } dailyDetailsMonthOfDate =
		 * month.toLowerCase(); // System.out.println(month.toLowerCase());
		 */

		SimpleDateFormat sdf = new SimpleDateFormat("EEE");
		SimpleDateFormat sdf2 = new SimpleDateFormat("E");
		dailyDetailsDayOfWeek = sdf2.format(sdf.parse(dayOfWeek)).toLowerCase();

	}
	
	/**
	 * This method should be executed as a pre-requisite method before verifying any
	 * daily details page test scripts to set the global parameters pertaiining to
	 * Daily Details page this reads the data from api.wether.com call
	 */
	public static void get_v3_wx_forecast_daily_15day_dataAnroid() throws Exception {
		/*
		 * Calendar calendar = Calendar.getInstance(); Date d = new Date();
		 * SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // the day of
		 * the week abbreviated String today = simpleDateformat.format(d); today =
		 * today.toLowerCase().concat("1");
		 */
		String moonRiseTimeLocal = null;
		String dayOfWeek = null;

		// Read the content form file
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		dayOfWeek = get_param_value_from_APICalls("ddDayOfWeek");
		moonRiseTimeLocal = get_param_value_from_APICalls("ddDtAndMnth");
		String[] dtParams = moonRiseTimeLocal.split("T");
		String dateFormat = dtParams[0];

		Date moonRiseTimeLocalDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateFormat);
		System.out.println(moonRiseTimeLocalDate);
		SimpleDateFormat month_date = new SimpleDateFormat("MMM", Locale.ENGLISH);
		SimpleDateFormat day_date = new SimpleDateFormat("dd", Locale.ENGLISH);

		System.out.println(month_date.format(moonRiseTimeLocalDate));
		System.out.println(day_date.format(moonRiseTimeLocalDate));

		dailyDetailsMonthOfDate = month_date.format(moonRiseTimeLocalDate).toLowerCase();
		dailyDetailsDateOfDay = day_date.format(moonRiseTimeLocalDate);

		/*
		 * String[] dtAndMonth = dtParams[0].split("-"); // dailyDetailsMonthOfDate =
		 * dtAndMonth[1]; String dailyDetailsMonthNum = dtAndMonth[1];
		 * dailyDetailsDateOfDay = dtAndMonth[2]; int monthNum =
		 * Integer.parseInt(dailyDetailsMonthNum);
		 * 
		 * int num = monthNum - 1; String month = "wrong"; DateFormatSymbols dfs = new
		 * DateFormatSymbols(); String[] months = dfs.getShortMonths(); if (num >= 0 &&
		 * num <= 11) { month = months[num]; } dailyDetailsMonthOfDate =
		 * month.toLowerCase(); // System.out.println(month.toLowerCase());
		 */

		SimpleDateFormat sdf = new SimpleDateFormat("EEE");
		SimpleDateFormat sdf2 = new SimpleDateFormat("E");
		dailyDetailsDayOfWeek = sdf2.format(sdf.parse(dayOfWeek)).toLowerCase();

	}

	/**
	 * This method verifies whether TWC App installed or not. This is called
	 * immediately after when app installed from Firebase
	 * 
	 * @throws Exception
	 */
	public static void twcAppInstalledCheck() throws Exception {
		/*
		 * try { boolean twcAppInstalled = Ad.isAppInstalled("com.weather.TWC");
		 * }catch(Exception e) { Utils.setAbortTestSuite(true); }
		 */
		boolean twcAppInstalled = false;
		System.out.println("****************TWC App Installed Check Started****************");
		logStep("****************TWC App Installed Check Started****************");
		for (int i = 0; i <= 24; i++) {
			twcAppInstalled = Ad.isAppInstalled("com.weather.TWC");
			TestBase.waitForMilliSeconds(10000);
			if (twcAppInstalled) {
				System.out.println("TWC App Found Installed at milli seconds : " + i * 10000);
				logStep("TWC App Found Installed at milli seconds : " + i * 10000);
				break;
			}
		}

		if (twcAppInstalled) {
			System.out.println("TWC App Installed");
			logStep("TWC App Installed");
		} else {
			System.out.println("TWC App Not Installed");
			logStep("TWC App Not Installed");
			Assert.fail("TWC App Not Installed");
		}

	}

	/**
	 * This Method returns short name for a Given Card Name
	 * 
	 * @param cardName
	 * @return
	 */
	public static String shortCardName(String cardName) {
		if (cardName.contains("-card")) {
			// adcardname = cardName;
			cardName = cardName.replaceAll("-card", "");
			System.out.println("Current Card Name is : " + cardName);
			if (cardName.contains("health-and-activities")) {
				cardName = "lifestyle";
			} else if (cardName.contains("air-quality")) {
				cardName = "aq";
			} else if (cardName.contains("map")) {
				cardName = "radar.largead";
			} else if (cardName.contains("seasonal-hub")) {
				cardName = "seasonalhub";
			} else if (cardName.contains("breaking-news")) {
				cardName = "breakingnews";
			} else if (cardName.contains("daily")) {
				cardName = "daily";
			} else if (cardName.contains("hurricane-central")) {
				cardName = "hurricane-central";
			} else if (cardName.contains("today")) {
				cardName = "today";
			} else if (cardName.contains("video")) {
				cardName = "video";
			} else if (cardName.contains("news")) {
				cardName = "news";
			} else if (cardName.contains("privacy-card")) {
				cardName = "privacy";
			}
		}

		return cardName;
	}
	
	/**
	 * This method returns the derived Card Name
	 * @param card
	 */
	public static String getDerivedCardName(WebElement card, String cardName) {
		//String cardName = null;
		String feedName = null;
		if (cardName.equalsIgnoreCase("labelDescription")) {
			try {
				cardName = card.findElement(By.xpath("(//XCUIElementTypeOther)[3]"))
						.getAttribute("name");
				cardName = cardName.replaceAll("-containerView", "");
			} catch (Exception e) {
				System.out.println("An exception in deriving unique card title of labelDescription card");
				logStep("An exception in deriving unique card title of labelDescription card");
			}

		} else if (cardName.equalsIgnoreCase("labelInsightContentTitle")) {
			try {
				cardName = card
						.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"))
						.getAttribute("label");
			} catch (Exception e) {
				System.out.println(
						"An exception in deriving unique card title of labelInsightContentTitle card");
				logStep("An exception in deriving unique card title of labelInsightContentTitle card");
			}

		} else if (cardName.equalsIgnoreCase("Advertisement")) {
			try {
				feedName = card.findElement(By.xpath(
						"//XCUIElementTypeStaticText[@name='Advertisement']//parent::XCUIElementTypeOther"))
						.getAttribute("name");
				cardName = feedName.replaceAll("-adContainerView", "");
			} catch (Exception e) {
				// following is added to handle Integrated Feed Card while getting card name.
				try {
					feedName = card.findElement(By.xpath(
							"//XCUIElementTypeStaticText[@name='Advertisement']//parent::XCUIElementTypeOther/following-sibling::XCUIElementTypeOther"))
							.getAttribute("name");
					cardName = feedName.replaceAll("-adContainerView", "");
				} catch (Exception e1) {
					cardName = "Advertisement";
				}
			}

		} else if (cardName.equalsIgnoreCase("ads-card-containerView")) {
			try {
				feedName = card.findElement(By.xpath(
						"//XCUIElementTypeOther[@name='ads-card-containerView']/XCUIElementTypeOther"))
						.getAttribute("name");
				cardName = feedName.replaceAll("-adContainerView", "");
			} catch (Exception e) {
				cardName = "Advertisement";
			}
		} else if (cardName.equalsIgnoreCase("integrated-ad-card-containerView")) {
			try {
				feedName = card.findElement(By.xpath(
						"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']//XCUIElementTypeOther[contains(@name,'-adContainerView')]"))
						.getAttribute("name");
				cardName = feedName.replaceAll("-adContainerView", "");
			} catch (Exception e) {
				cardName = "Advertisement";
			}

		}
		
		return cardName;
	}

	/**
	 * Verifies Criteo initialization API call
	 * @param excelName
	 * @param sheetName
	 * @throws Exception
	 */
	public static void verifyCriteo_inapp_v2_Call(String excelName, String sheetName) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String host = ReadExcelValues.data[2][Cap];
		String path = ReadExcelValues.data[3][Cap];
		boolean flag = verifyAPICallWithHostandPath(host, path);
		if (flag) {
			System.out.println(host + path + " call is present in Charles session");
			logStep(host + path + " call is present in Charles session");
			System.out.println(host + path + " :API Call Verification is successful");
			logStep(host + path + " :API Call Verification is successful");

		} else {
			System.out.println(host + path + " call is not present in Charles session");
			logStep(host + path + " call is not present in Charles session");
			System.out.println(host + path + " :API Call Verification is failed");
			logStep(host + path + " :API Call Verification is failed");
			Assert.fail(host + path + " call is not present in Charles session");

		}
	}

	/**
	 * Verifies Criteo initialization API call
	 * @param excelName
	 * @param sheetName
	 * @param expected
	 * @throws Exception
	 */
	public static void verifyCriteo_inapp_v2_Call(String excelName, String sheetName, boolean expected)
			throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String host = ReadExcelValues.data[2][Cap];
		String path = ReadExcelValues.data[3][Cap];
		boolean flag = verifyAPICallWithHostandPath(host, path);
		if (flag) {
			System.out.println(host + path + " call is present in Charles session");
			logStep(host + path + " call is present in Charles session");

		} else {
			System.out.println(host + path + " call is not present in Charles session");
			logStep(host + path + " call is not present in Charles session");
		}

		if (expected == flag) {
			System.out.println(host + path + " :API Call Verification is successful");
			logStep(host + path + " :API Call Verification is successful");

		} else {
			System.out.println(host + path + " :API Call Verification is failed");
			logStep(host + path + " :API Call Verification is failed");
			if (expected) {
				System.out.println(host + path + " :API Call expected to present but it not exists");
				logStep(host + path + " :API Call expected to present but it not exists");
				Assert.fail(host + path + " :API Call expected to present but it not exists");
			} else {
				System.out.println(host + path + " :API Call is not expected to present but it exists");
				logStep(host + path + " :API Call is not expected to present but it exists");
				Assert.fail(host + path + " :API Call is not expected to present but it exists");
			}
		}
	}

	/**
	 * Verifies Criteo Config app call
	 * @param excelName
	 * @param sheetName
	 * @throws Exception
	 */
	public static void verifyCriteo_config_app_Call(String excelName, String sheetName) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String host = ReadExcelValues.data[2][Cap];
		String path = ReadExcelValues.data[4][Cap];
		boolean flag = verifyAPICallWithHostandPath(host, path);
		if (flag) {
			System.out.println(host + path + " call is present in Charles session");
			logStep(host + path + " call is present in Charles session");
			System.out.println(host + path + " :API Call Verification is successful");
			logStep(host + path + " :API Call Verification is successful");

		} else {
			System.out.println(host + path + " call is not present in Charles session");
			logStep(host + path + " call is not present in Charles session");
			System.out.println(host + path + " :API Call Verification is failed");
			logStep(host + path + " :API Call Verification is failed");
			Assert.fail(host + path + " call is not present in Charles session");

		}
	}

	/**
	 * Verifies Criteo Config app call
	 * @param excelName
	 * @param sheetName
	 * @param expected
	 * @throws Exception
	 */
	public static void verifyCriteo_config_app_Call(String excelName, String sheetName, boolean expected)
			throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String host = ReadExcelValues.data[2][Cap];
		String path = ReadExcelValues.data[4][Cap];
		boolean flag = verifyAPICallWithHostandPath(host, path);
		if (flag) {
			System.out.println(host + path + " call is present in Charles session");
			logStep(host + path + " call is present in Charles session");

		} else {
			System.out.println(host + path + " call is not present in Charles session");
			logStep(host + path + " call is not present in Charles session");
		}
		if (expected == flag) {
			System.out.println(host + path + " :API Call Verification is successful");
			logStep(host + path + " :API Call Verification is successful");

		} else {
			System.out.println(host + path + " :API Call Verification is failed");
			logStep(host + path + " :API Call Verification is failed");
			if (expected) {
				System.out.println(host + path + " :API Call expected to present but it not exists");
				logStep(host + path + " :API Call expected to present but it not exists");
				Assert.fail(host + path + " :API Call expected to present but it not exists");
			} else {
				System.out.println(host + path + " :API Call is not expected to present but it exists");
				logStep(host + path + " :API Call is not expected to present but it exists");
				Assert.fail(host + path + " :API Call is not expected to present but it exists");
			}
		}

	}
	
	/**
	 * Verifies Confiant SDK initialization API call
	 * @param excelName
	 * @param sheetName
	 * @throws Exception
	 */
	public static void verifyConfiantSDKInitializationCalls(String excelName, String sheetName) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String host = ReadExcelValues.data[2][Cap];
		String path1 = ReadExcelValues.data[3][Cap];
		String path2 = ReadExcelValues.data[4][Cap];
		boolean flag1 = verifyAPICallWithHostandPath(host, path1);
		boolean flag2 = verifyAPICallWithHostandPath(host, path2);
		if (flag1) {
			System.out.println(host + path1 + " call is present in Charles session");
			logStep(host + path1 + " call is present in Charles session");
			System.out.println(host + path1 + " :API Call Verification is successful");
			logStep(host + path1 + " :API Call Verification is successful");

		} else {
			System.out.println(host + path1 + " call is not present in Charles session");
			logStep(host + path1+ " call is not present in Charles session");
			
			//Assert.fail(host + path1 + " call is not present in Charles session");

		}
		
		if (flag2) {
			System.out.println(host + path2 + " call is present in Charles session");
			logStep(host + path2 + " call is present in Charles session");
			System.out.println(host + path2 + " :API Call Verification is successful");
			logStep(host + path2 + " :API Call Verification is successful");

		} else {
			System.out.println(host + path2 + " call is not present in Charles session");
			logStep(host + path2+ " call is not present in Charles session");
			//System.out.println(host + path2 + " :API Call Verification is failed");
			//logStep(host + path2 + " :API Call Verification is failed");
			//Assert.fail(host + path2 + " call is not present in Charles session");

		}
		
		/**
		 * Since the second call i.e. confiant-integrations.global.ssl.fastly.net/sdk/5.0.0/202204261912/wrap.js is a static call
		 * and at times its cached for 15 minutes or more so there are chances that we may not see wrap.js call. 
		 * hence ignoring the check of second call
		 */
		if (!flag1) {
			System.out.println(host + path1 + " :API Call Verification is failed");
			logStep(host + path1 + " :API Call Verification is failed");
			Assert.fail(host + path1 + " call is not present in Charles session");
		}
	}
	
	/**
	 * Verifies Confiant SDK initialization API call
	 * @param excelName
	 * @param sheetName
	 * @throws Exception
	 */
	public static void verifyConfiantSDKInitializationCallsAndroid(String excelName, String sheetName) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String host1 = ReadExcelValues.data[1][Cap];
		String host2 = ReadExcelValues.data[2][Cap];
		String path1 = ReadExcelValues.data[3][Cap];
		String path2 = ReadExcelValues.data[4][Cap];
		boolean flag1 = verifyAPICallWithHostandPath(host1, path1);
		boolean flag2 = verifyAPICallWithHostandPath(host2, path2);
		if (flag1) {
			System.out.println(host1 + path1 + " call is present in Charles session");
			logStep(host1 + path1 + " call is present in Charles session");
			System.out.println(host1 + path1 + " :API Call Verification is successful");
			logStep(host1 + path1 + " :API Call Verification is successful");

		} else {
			System.out.println(host1 + path1 + " call is not present in Charles session");
			logStep(host1 + path1+ " call is not present in Charles session");
			
			//Assert.fail(host + path1 + " call is not present in Charles session");

		}
		
		if (flag2) {
			System.out.println(host2 + path2 + " call is present in Charles session");
			logStep(host2 + path2 + " call is present in Charles session");
			System.out.println(host2 + path2 + " :API Call Verification is successful");
			logStep(host2 + path2 + " :API Call Verification is successful");

		} else {
			System.out.println(host2 + path2 + " call is not present in Charles session");
			logStep(host2 + path2+ " call is not present in Charles session");
			//System.out.println(host + path2 + " :API Call Verification is failed");
			//logStep(host + path2 + " :API Call Verification is failed");
			//Assert.fail(host + path2 + " call is not present in Charles session");

		}
		
		/**
		 * Since the second call i.e. confiant-integrations.global.ssl.fastly.net/sdk/5.0.0/202204261912/wrap.js is a static call
		 * and at times its cached for 15 minutes or more so there are chances that we may not see wrap.js call. 
		 * hence ignoring the check of second call
		 */
		if (!flag1) {
			System.out.println(host1 + path1 + " :API Call Verification is failed");
			logStep(host1 + path1 + " :API Call Verification is failed");
			Assert.fail(host1 + path1 + " call is not present in Charles session");
		}
	}

	/**
	 * Get parameter value from API request
	 * @param host
	 * @param path
	 * @param cust_param
	 * @return
	 * @throws Exception
	 */
	public static String get_param_value_from_APIRequest(String host, String path, String cust_param) throws Exception {
		// readExcelValues.excelValues(excelName, sheetName);
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@host";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		// Getting custom_params amzn_b values
		List<String> customParamsList = new ArrayList<String>();

		// String iuId = null;

		boolean iuExists = false;
		for (String qry : getQueryList) {
			if (qry.contains(host)) {
				iuExists = true;
				break;
			}
		}
		boolean hflag = false;
		boolean pflag = false;
		boolean resflag = false;
		String ApiParamValue = null;

		if (iuExists) {
			System.out.println(host + "  call is present");
			logStep(host + "  call is present");
			outerloop: for (int p = 0; p < nodeList.getLength(); p++) {
				// System.out.println("Total transactions: "+nodeList.getLength());
				if (nodeList.item(p) instanceof Node) {
					Node node = nodeList.item(p);
					if (node.hasChildNodes()) {
						NodeList nl = node.getChildNodes();
						for (int j = 0; j < nl.getLength(); j++) {
							// System.out.println("node1 length is: "+nl.getLength());
							Node innernode = nl.item(j);
							if (innernode != null) {
								// System.out.println("Innernode name is: "+innernode.getNodeName());
								if (innernode.getNodeName().equals("request")) {
									//System.out.println(".....................................");
									hflag = false;
									pflag = false;
									if (innernode.hasChildNodes()) {
										NodeList n2 = innernode.getChildNodes();
										for (int k = 0; k < n2.getLength(); k++) {
											// System.out.println("node2 length is: "+n2.getLength());
											Node innernode2 = n2.item(k);
											if (innernode2 != null) {
												// System.out.println("Innernode2 name is: "+innernode2.getNodeName());
												if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
													Element eElement = (Element) innernode2;
													// System.out.println("Innernode2 element name is:
													// "+eElement.getNodeName());
													if (eElement.getNodeName().equals("headers")) {
														if (innernode2.hasChildNodes()) {
															NodeList n3 = innernode2.getChildNodes();
															for (int q = 0; q < n3.getLength(); q++) {
																// System.out.println("node3 length is:
																// "+n3.getLength());
																Node innernode3 = n3.item(q);
																if (innernode3 != null) {
																	// System.out.println("Innernode3 name is:
																	// "+innernode3.getNodeName());
																	if (innernode3.getNodeType() == Node.ELEMENT_NODE) {
																		Element eElement1 = (Element) innernode3;
																		
																		// System.out.println("Innernode3 element name
																		// is: "+eElement1.getNodeName());
																		if (eElement1.getNodeName().equals("header")) {
																			String content = eElement1.getTextContent();
																			//System.out.println("request body "+content);

																			if (content.contains(host)) {
																				hflag = true;
																				//System.out.println("request body found "+ content);
																				
																			} else if (content.contains(path)) {
																				pflag = true;
																				//System.out.println("request body ound "+ content);
																			}
																		}
																		// this condition especially for android since
																		// its file has path value under first-line
																		// element
																		if (eElement1.getNodeName()
																				.equals("first-line")) {
																			String content = eElement1.getTextContent();
																			// System.out.println("request body
																			// "+content);

																			if (content.contains(path)) {
																				pflag = true;
																				// System.out.println("request body
																				// found "
																				// + content);
																			}
																		}
																	}
																}
															}
														}
													}
													if (hflag && pflag) {
														if (eElement.getNodeName().equals("body")) {
															String scontent = eElement.getTextContent();
															if (scontent.contains(cust_param)) {
																// System.out.println("request body " + scontent);
																ApiParamValue = get_Param_Value_inJsonBody(scontent,
																		cust_param);
																break outerloop;

															}

														}

													}

												}
											}
										}
									}
								}

								/*
								 * if (hflag && pflag) { resflag = true; break outerloop; }
								 */
							}
						}
					}
				}
				// flag = false;
			}

		} else {
			System.out.println(host + " ad call is not present");
			logStep(host + " ad call is not present");

		}

		// return resflag;
		// System.out.println("Parameter value obtined from criteo request is :" +
		// ApiParamValue);
		return ApiParamValue;

	}

	/**
	 * Verifies Criteo Config call with host and path
	 * @param excelName
	 * @param sheetName
	 * @param cust_param
	 * @param expected
	 * @throws Exception
	 */
	public static void validate_Criteo_SDK_config_app_call_parameter(String excelName, String sheetName,
			String cust_param, String expected) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String host = ReadExcelValues.data[2][Cap];
		String path = ReadExcelValues.data[4][Cap];

		boolean flag = verifyAPICallWithHostandPath(host, path);
		if (flag) {
			System.out.println(host + path + " call is present in Charles session");
			logStep(host + path + " call is present in Charles session");

			String actual = get_param_value_from_APIRequest(host, path, cust_param);
			
			if (actual.equalsIgnoreCase(expected)) {
				System.out.println("Custom Parameter :" + cust_param + " value: " + actual
						+ " is matched with the expected value " + expected);
				logStep("Custom Parameter :" + cust_param + " value: " + actual + " is matched with the expected value "
						+ expected);
				System.out.println("Criteo parameter: " + cust_param + " validation is successful");
				logStep("Criteo parameter: " + cust_param + " validation is successful");
			} else {
				System.out.println("Custom Parameter :" + cust_param + " value: " + actual
						+ " is not matched with the expected value " + expected);
				logStep("Custom Parameter :" + cust_param + " value: " + actual
						+ " is not matched with the expected value " + expected);
				System.out.println("Criteo parameter: " + cust_param + " validation is failed");
				logStep("Criteo parameter: " + cust_param + " validation is failed");
				Assert.fail("Custom Parameter :" + cust_param + " value: " + actual
						+ " is not matched with the expected value " + expected);
			}

		} else {
			System.out.println(host + path + " call is not present in Charles session, hence Custom Parameter: "
					+ cust_param + " validation skipped");
			logStep(host + path + " call is not present in Charles session, hence Custom Parameter: " + cust_param
					+ " validation skipped");
			System.out.println("Criteo parameter: " + cust_param + " validation is failed");
			logStep("Criteo parameter: " + cust_param + " validation is failed");
			Assert.fail(host + path + " call is not present in Charles session, hence Custom Parameter: " + cust_param
					+ " validation skipped");

		}

	}

	/**
	 * Get b values from amazon aax calls of XML File and store to list
	 * 
	 * @param excelName
	 * @param sheetName
	 * @param placementId
	 * @param cust_param
	 * @param clearList
	 * @throws Exception
	 */
	public static void get_Criteo_SDK_inapp_v2_call_response_parameter_by_placementId(String excelName,
			String sheetName, String placementId, String cust_param, boolean clearList) throws Exception {

		ReadExcelValues.excelValues(excelName, sheetName);
		String host = ReadExcelValues.data[2][Cap];
		String path = ReadExcelValues.data[3][Cap];
		// Read the content form file
		File fXmlFile = new File(outfile.getName());
		if (clearList) {
			listOf_criteo_Params.clear();
			criteocallsSize = 0;
		}

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");

		// Read JSONs and get b value
		// List<String> jsonBValuesList = new ArrayList<String>();

		// String slotId = readExcelValues.data[21][Cap];

		// String slotId = "c4dd8ec4-e40c-4a63-ae81-8f756793ac5e";
		// weather.hourly

		boolean flag = false;
		boolean hflag = false;
		boolean pflag = false;
		boolean resflag = false;
		List<String> istofRequestBodies = new ArrayList<String>();
		List<String> istofResponseBodies = new ArrayList<String>();
		// List<String> listOf_b_Params = new ArrayList<String>();

		for (int i = 0; i < nodeList.getLength(); i++) {
			hflag = false;
			pflag = false;
			if (nodeList.item(i) instanceof Node) {
				Node node = nodeList.item(i);
				if (node.hasChildNodes()) {
					NodeList nl = node.getChildNodes();
					for (int j = 0; j < nl.getLength(); j++) {
						Node innernode = nl.item(j);
						if (innernode != null) {
							if (innernode.getNodeName().equals("request")) {
								if (innernode.hasChildNodes()) {
									NodeList n2 = innernode.getChildNodes();
									for (int k = 0; k < n2.getLength(); k++) {
										Node innernode2 = n2.item(k);
										if (innernode2 != null) {
											if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
												Element eElement = (Element) innernode2;
												if (eElement.getNodeName().equals("headers")) {
													if (innernode2.hasChildNodes()) {
														NodeList n3 = innernode2.getChildNodes();
														for (int q = 0; q < n3.getLength(); q++) {
															// System.out.println("node3 length is:
															// "+n3.getLength());
															Node innernode3 = n3.item(q);
															if (innernode3 != null) {
																// System.out.println("Innernode3 name is:
																// "+innernode3.getNodeName());
																if (innernode3.getNodeType() == Node.ELEMENT_NODE) {
																	Element eElement1 = (Element) innernode3;
																	// System.out.println("Innernode3 element name
																	// is: "+eElement1.getNodeName());
																	if (eElement1.getNodeName().equals("header")) {
																		String content = eElement1.getTextContent();
																		// System.out.println("request body
																		// "+content);

																		if (content.contains(host)) {
																			hflag = true;
																			// System.out.println("request body found "
																			// + content);

																		} else if (content.contains(path)) {
																			pflag = true;
																			// System.out.println("request body found "
																			// + content);
																		}
																	}

																	// this condition especially for android since its
																	// file has path value under first-line element
																	if (eElement1.getNodeName().equals("first-line")) {
																		String content = eElement1.getTextContent();
																		// System.out.println("request body
																		// "+content);

																		if (content.contains(path)) {
																			pflag = true;
																			// System.out.println("request body
																			// found "
																			// + content);
																		}
																	}

																}
															}
														}
													}
												}

												if (hflag && pflag) {
													if (eElement.getNodeName().equals("body")) {
														String scontent = eElement.getTextContent();

														/*
														 * if (scontent.contains(placementId)) { flag = true;
														 * criteocallsSize++; istofRequestBodies.add(scontent); //
														 * System.out.println("request body "+scontent);
														 * 
														 * }
														 */
														boolean tempFlag = verify_criteo_request_for_given_placementId_inJsonRequestBody(
																placementId, scontent);
														if (tempFlag) {
															flag = true;
															criteocallsSize++;
															istofRequestBodies.add(scontent);
															// System.out.println("request body "+scontent);

														}

													}

												}
											}
										}
									}
								}
							}

							if (flag) {
								if (innernode.getNodeName().equals("response")) {
									// System.out.println(innernode.getNodeName());
									if (innernode.hasChildNodes()) {
										NodeList n2 = innernode.getChildNodes();
										for (int k = 0; k < n2.getLength(); k++) {
											Node innernode2 = n2.item(k);
											if (innernode2 != null) {
												if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
													Element eElement = (Element) innernode2;
													if (eElement.getNodeName().equals("body")) {
														String content = eElement.getTextContent();
														istofResponseBodies.add(content);
														// String tempBparam = get_b_value_inJsonResponseBody(content);
														String tempBparam = get_criteo_response_parameter_value_by_placementId_inJsonResponseBody(
																placementId, cust_param, content);
														if (!"".contentEquals(tempBparam)) {
															listOf_criteo_Params.add(tempBparam);
														} else {
															listOf_criteo_Params.add("-1");
														}
														// System.out.println("response body "+content);
													}
												}
											}
										}
									}
								}

							}

						}
					}
				}
			}
			flag = false;
		}
		System.out.println(listOf_criteo_Params);
		logStep(listOf_criteo_Params.toString());

		criteocallsResponseSize = listOf_criteo_Params.size();
		criteoparamErrorCount = 0;
		for (String b : listOf_criteo_Params) {
			System.out.println(cust_param + " value from JSON-----------> " + b);
			if (b.contentEquals("error")) {
				criteoparamErrorCount++;
			}
		}
		System.out.println("Criteo calls Size is: " + criteocallsSize);
		logStep("Criteo calls Size is: " + criteocallsSize);
		System.out.println("Criteo callsResponse Size is: " + criteocallsResponseSize);
		logStep("Criteo callsResponse Size is: " + criteocallsResponseSize);
		System.out.println("Criteo Param ErrorCount size is: " + criteoparamErrorCount);
		logStep("Criteo Param ErrorCount size is: " + criteoparamErrorCount);

	}

	/**
	 * Get criteo response parameter by placement id
	 * @param placementId
	 * @param cust_param
	 * @param apiData
	 * @return
	 * @throws Exception
	 */
	public static String get_criteo_response_parameter_value_by_placementId_inJsonResponseBody(String placementId,
			String cust_param, String apiData) throws Exception {

		// Functions.Read_Turbo_api("Cust_Param", readSheet);

		JSONParser parser = new JSONParser();
		// System.out.println("adreq1 is : "+adreq1.toString());
		Object obj = parser.parse(new String(apiData));
		// System.out.println("obj : "+obj);
		JSONObject jsonObject = (JSONObject) obj;
		String ApiParamValue = "";
		String JsonParam = null;

		JSONObject mainTag = null;
		JSONArray eleArray = null;

		try {
			JsonParam = "slots".trim();
			eleArray = (JSONArray) jsonObject.get(JsonParam);
			// System.out.println(eleArray);
			try {

				ArrayList<String> Ingredients_names = new ArrayList<>();
				for (int i = 0; i < eleArray.size(); i++) {

					String arrayElement = String.valueOf(eleArray.get(i));

					Ingredients_names.add(arrayElement);
					obj = parser.parse(new String(arrayElement));
					jsonObject = (JSONObject) obj;
					mainTag = (JSONObject) obj;

					try {
						String cApiParamValue = String.valueOf(mainTag.get("placementId"));
						if (cApiParamValue.equalsIgnoreCase(placementId)) {
							if (cust_param.equalsIgnoreCase("size")) {
								String width = String.valueOf(mainTag.get("width"));
								String height = String.valueOf(mainTag.get("height"));
								ApiParamValue = width.concat("x").concat(height);
							} else {
								ApiParamValue = String.valueOf(mainTag.get(cust_param));
							}

						} else {
							// System.out.println("... noticed");
							continue;
						}

					} catch (Exception ex) {
						ex.printStackTrace();
						continue;
					}

				}
			} catch (Exception e) {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * try { JSONArray arrayElementValues = (JSONArray) mainTag .get(cust_param);
		 * ApiParamValue = String.valueOf(arrayElementValues.get(0)); } catch (Exception
		 * e) { ApiParamValue = String.valueOf(mainTag.get(cust_param)); }
		 */
		System.out.println(cust_param + " Param Values from Criteo API Call is : " + ApiParamValue);
		logStep(cust_param + " Param Values from Criteo API Call is : " + ApiParamValue);
		/*
		 * if (ApiParamValue.equalsIgnoreCase("null")) { ApiParamValue = "nl"; }
		 */
		return ApiParamValue;
	}

	/**
	 * This method iterates through charles session file and look for all instances
	 * of given feed call and retrive the given custom parameter value and add to
	 * list
	 * 
	 * @param feedCall
	 * @param cust_param
	 * @throws Exception
	 */
	public static void get_custom_param_values_from_gampadCalls(String excelName, String sheetName, String feedCall,
			String cust_param) throws Exception {
		// readExcelValues.excelValues(excelName, sheetName);

		// Read the content form file
		File fXmlFile = new File(outfile.getName());
		customParamsList.clear();
		criteogampadcallcount = 0;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		// NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		// Getting custom_params amzn_b values
		// List<String> customParamsList = new ArrayList<String>();

		// String iuId =
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		// String iuId = "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fcard%2Fdaily";
		String queryIU = null;
		for (String qry : getQueryList) {
			if (qry.contains(feedCall)) {
				// after it checks for contains, now performing exact validation of IU value,
				// for ex: hourly contains in hourly1, hourly2, hourly3
				if (sheetName.equalsIgnoreCase("Hourly")) {
					queryIU = return_iu_value_from_query_parameter_of_Feedcall(qry);
					if (queryIU.equalsIgnoreCase(feedCall)) {
						criteogampadcallcount++;
						String tempCustmParam = getCustomParamBy_iu_value(qry, cust_param);
						if (!"".equals(tempCustmParam)) {
							customParamsList.add(getCustomParamBy_iu_value(qry, cust_param));
						} else {
							customParamsList.add("-1");
						}
					}
				} else {
					criteogampadcallcount++;
					String tempCustmParam = getCustomParamBy_iu_value(qry, cust_param);
					if (!"".equals(tempCustmParam)) {
						customParamsList.add(getCustomParamBy_iu_value(qry, cust_param));
					} else {
						customParamsList.add("-1");
					}
				}

			}
		}
		System.out.println("Criteo Parameters found in gampad call are: ");
		logStep("Criteo Parameters found in gampad call are: ");
		System.out.println(customParamsList);
		logStep(customParamsList.toString());
		System.out.println("No of times the gampad call found is: " + criteogampadcallcount);
		logStep("No of times the gampad call found is: " + criteogampadcallcount);

	}

	/**
	 * Store the Criteo parameter values to a List
	 * 
	 * @param excelName
	 * @param sheetName
	 * @param cust_param
	 * @param clearList
	 * @throws Exception
	 */
	public static void load_Criteo_SDK_inapp_v2_call_response_parameter_by_placementId(String excelName,
			String sheetName, String cust_param, boolean clearList) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String placementId = null;
		if (sheetName.equalsIgnoreCase("Pulltorefresh")) {
			placementId = "weather.homescreen.adhesive";
		} else if (sheetName.equalsIgnoreCase("Hourly")) {
			placementId = "weather.hourly";
		} else if (sheetName.equalsIgnoreCase("Feed1")) {
			if (Ad instanceof IOSDriver<?>) {
				placementId = "weather.feed0";
			} else if (Ad instanceof AndroidDriver<?>) {
				placementId = "weather.feed2";						
			}
		} else if (sheetName.equalsIgnoreCase("Feed2")) {
			if (Ad instanceof IOSDriver<?>) {
				placementId = "weather.feed1";
			} else if (Ad instanceof AndroidDriver<?>) {
				placementId = "weather.feed3";						
			}
		} else if (sheetName.equalsIgnoreCase("Feed3")) {
			placementId = "weather.feed2";
		} else if (sheetName.equalsIgnoreCase("Feed4")) {
			placementId = "weather.feed3";
		} else if (sheetName.equalsIgnoreCase("Feed5")) {
			placementId = "weather.feed4";
		} else if (sheetName.equalsIgnoreCase("Feed6")) {
			placementId = "weather.feed5";
		} else if (sheetName.equalsIgnoreCase("Feed7")) {
			placementId = "weather.feed6";
		} else if (sheetName.equalsIgnoreCase("Air Quality(Content)")) {
			placementId = "weather.aq";
		} else if (sheetName.equalsIgnoreCase("SeasonalHub(Details)")) {
			placementId = "weather.seasonlhub";
		} else if (sheetName.equalsIgnoreCase("Today")) {
			placementId = "weather.trending";
		} else if (sheetName.equalsIgnoreCase("Daily(10day)")) {
			if (Ad instanceof IOSDriver<?>) {
				placementId = "weather.10day.largeAds";
			} else if (Ad instanceof AndroidDriver<?>) {
				placementId = "weather.dailydetails.largeAds.1";						
			}
		} else if (sheetName.equalsIgnoreCase("Map")) {
			placementId = "weather.maps";
		} else if (sheetName.equalsIgnoreCase("MyAlerts")) {
			placementId = "weather.alerts.center";
		} else if (sheetName.equalsIgnoreCase("weather.alerts")) {
			placementId = "weather.alerts";
		} else if (sheetName.equalsIgnoreCase("Health(coldAndFluArticles)")) {
			placementId = "weather.articles";
		} else if (sheetName.equalsIgnoreCase("Health(allergyArticles)")) {
			placementId = "weather.articles";
		} else if (sheetName.equalsIgnoreCase("Health(goRun)")) {
			placementId = "weather.running.largeAds";
		} else if (sheetName.equalsIgnoreCase("Health(boatAndBeach)")) {
			placementId = "content.beach.largeAds";
		} else if (sheetName.equalsIgnoreCase("Hourly1")) {
			if (Ad instanceof IOSDriver<?>) {
				placementId = "weather.hourly.bigad";
			} else if (Ad instanceof AndroidDriver<?>) {
				placementId = "weather.hourly1";						
			}
		} else if (sheetName.equalsIgnoreCase("Hourly2")) {
			if (Ad instanceof IOSDriver<?>) {
				placementId = "weather.hourly.bigad";
			} else if (Ad instanceof AndroidDriver<?>) {
				placementId = "weather.hourly2";						
			}
		} else if (sheetName.equalsIgnoreCase("Hourly3")) {
			if (Ad instanceof IOSDriver<?>) {
				placementId = "weather.hourly.bigad";
			} else if (Ad instanceof AndroidDriver<?>) {
				placementId = "weather.hourly3";						
			}
		} else if (sheetName.equalsIgnoreCase("Interstitials")) {
			if (Ad instanceof IOSDriver<?>) {
				placementId = "weather.interstitial.hourly";
			} else if (Ad instanceof AndroidDriver<?>) {
				placementId = "weather.interstitial";						
			}
		}
		String feedCall = null;
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		if (sheetName.equalsIgnoreCase("PreRollVideo")) {
			feedCall = videoIUValue;
		} /*
			 * else if (sheetName.equalsIgnoreCase("IDD")) { String today =
			 * dailyDetailsDayOfWeek.concat("1"); feedCall = readExcelValues.data[18][Cap];
			 * feedCall = feedCall.concat("_") + today; }
			 */else {
			feedCall = ReadExcelValues.data[18][Cap];
		}

		get_Criteo_SDK_inapp_v2_call_response_parameter_by_placementId("Smoke", "Criteo", placementId, cust_param,
				clearList);
	}

	/**
	 * To Validate Criteo Parameter values against corresponding gampad call values
	 * 
	 * @param excelName
	 * @param sheetName
	 * @param cust_param
	 * @param clearList
	 * @throws Exception
	 */
	public static void validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value(String excelName,
			String sheetName, String cust_param, boolean clearList) throws Exception {

		ReadExcelValues.excelValues(excelName, sheetName);
		String placementId = null;
		if (sheetName.equalsIgnoreCase("Pulltorefresh")) {
			placementId = "weather.homescreen.adhesive";
		} else if (sheetName.equalsIgnoreCase("Hourly")) {
			placementId = "weather.hourly";
		} else if (sheetName.equalsIgnoreCase("Feed1")) {
			if (Ad instanceof IOSDriver<?>) {
				placementId = "weather.feed0";
			} else if (Ad instanceof AndroidDriver<?>) {
				placementId = "weather.feed2";						
			}
		} else if (sheetName.equalsIgnoreCase("Feed2")) {
			if (Ad instanceof IOSDriver<?>) {
				placementId = "weather.feed1";
			} else if (Ad instanceof AndroidDriver<?>) {
				placementId = "weather.feed3";						
			}
		} else if (sheetName.equalsIgnoreCase("Feed3")) {
			placementId = "weather.feed2";
		} else if (sheetName.equalsIgnoreCase("Feed4")) {
			placementId = "weather.feed3";
		} else if (sheetName.equalsIgnoreCase("Feed5")) {
			placementId = "weather.feed4";
		} else if (sheetName.equalsIgnoreCase("Feed6")) {
			placementId = "weather.feed5";
		} else if (sheetName.equalsIgnoreCase("Feed7")) {
			placementId = "weather.feed6";
		} else if (sheetName.equalsIgnoreCase("Air Quality(Content)")) {
			placementId = "weather.aq";
		} else if (sheetName.equalsIgnoreCase("SeasonalHub(Details)")) {
			placementId = "weather.seasonlhub";
		} else if (sheetName.equalsIgnoreCase("Today")) {
			placementId = "weather.trending";
		} else if (sheetName.equalsIgnoreCase("Daily(10day)")) {
			if (Ad instanceof IOSDriver<?>) {
				placementId = "weather.10day.largeAds";
			} else if (Ad instanceof AndroidDriver<?>) {
				placementId = "weather.dailydetails.largeAds.1";						
			}
		} else if (sheetName.equalsIgnoreCase("Map")) {
			placementId = "weather.maps";
		} else if (sheetName.equalsIgnoreCase("MyAlerts")) {
			placementId = "weather.alerts.center";
		} else if (sheetName.equalsIgnoreCase("weather.alerts")) {
			placementId = "weather.alerts";
		} else if (sheetName.equalsIgnoreCase("Health(coldAndFluArticles)")) {
			/*
			 * News detils, flu articles, alergy articles has same placement Id, here
			 * anything can be used
			 */
			placementId = "weather.articles";
		} else if (sheetName.equalsIgnoreCase("Health(allergyArticles)")) {
			/*
			 * News detils, flu articles, alergy articles has same placement Id, here
			 * anything can be used
			 */
			placementId = "weather.articles";
		} else if (sheetName.equalsIgnoreCase("Health(goRun)")) {
			placementId = "weather.running.largeAds";
		} else if (sheetName.equalsIgnoreCase("Health(boatAndBeach)")) {
			placementId = "content.beach.largeAds";
		} else if (sheetName.equalsIgnoreCase("Hourly1")) {
			if (Ad instanceof IOSDriver<?>) {
				placementId = "weather.hourly.bigad";
			} else if (Ad instanceof AndroidDriver<?>) {
				placementId = "weather.hourly1";						
			}
		} else if (sheetName.equalsIgnoreCase("Hourly2")) {
			if (Ad instanceof IOSDriver<?>) {
				placementId = "weather.hourly.bigad";
			} else if (Ad instanceof AndroidDriver<?>) {
				placementId = "weather.hourly2";						
			}
		} else if (sheetName.equalsIgnoreCase("Hourly3")) {
			if (Ad instanceof IOSDriver<?>) {
				placementId = "weather.hourly.bigad";
			} else if (Ad instanceof AndroidDriver<?>) {
				placementId = "weather.hourly3";						
			}
		} else if (sheetName.equalsIgnoreCase("Interstitials")) {
			if (Ad instanceof IOSDriver<?>) {
				placementId = "weather.interstitial.hourly";
			} else if (Ad instanceof AndroidDriver<?>) {
				placementId = "weather.interstitial";						
			}
		}
		String feedCall = null;
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		if (sheetName.equalsIgnoreCase("PreRollVideo")) {
			feedCall = videoIUValue;
		} /*
			 * else if (sheetName.equalsIgnoreCase("IDD")) { String today =
			 * dailyDetailsDayOfWeek.concat("1"); feedCall = readExcelValues.data[18][Cap];
			 * feedCall = feedCall.concat("_") + today; }
			 */else {
			feedCall = ReadExcelValues.data[18][Cap];
		}

		int failCount = 0;
		int successCount = 0;
		int criteoBiddingFailCount = 0;
		int criteoBiddingSuccessCount = 0;
		int maxIterations = 0;

		get_Criteo_SDK_inapp_v2_call_response_parameter_by_placementId("Smoke", "Criteo", placementId, cust_param,
				clearList);

		if (criteocallsSize == 0) {
			System.out.println("Criteo call is not generated in current session, so skipping the " + cust_param
					+ " value verification");
			logStep("Criteo call is not generated in current session, so skipping the " + cust_param
					+ " value verification");

		} else if (criteocallsResponseSize == 0) {
			System.out.println("Criteo call response doesn't have the placement id: " + placementId
					+ "  i.e. bidding is not happened in current session, so skipping the " + cust_param
					+ " value verification");
			logStep("Criteo call response doesn't have the placement id: " + placementId
					+ "  i.e. bidding is not happened in current session, so skipping the " + cust_param
					+ " value verification");

		} else if (criteoparamErrorCount == criteocallsResponseSize) {
			System.out.println(
					"Criteo call response contains error i.e. bidding is not happened in current session, so skipping the "
							+ cust_param + " value verification");
			logStep("Criteo call response contains error i.e. bidding is not happened in current session, so skipping the "
					+ cust_param + " value verification");
			logStep("Criteo call " + cust_param + " values validation with " + sheetName + " gampad call is failed");
			Assert.fail("Criteo call bidding is not successful in all instances for placement id: " + placementId
					+ " ,refer to charles session file for more details ");

		} else if (nextGenIMadDisplayed && sheetName.equalsIgnoreCase("Pulltorefresh")) {
			/*
			 * There may be chances that gampad call might not generated.. for ex: when IM
			 * ad displayed on home screen, then homescreen today call doesnt generate
			 * 
			 */
			System.out.println("Since IM Ad displayed on App Launch, Homescreen call: " + cust_param
					+ " id validation is skipped");
			logStep("Since IM Ad displayed on App Launch, Homescreen call: " + cust_param
					+ " id validation is skipped");
		} else {
			if (cust_param.contentEquals("displayUrl")) {
				cust_param = "displayurl";
			}
			get_custom_param_values_from_gampadCalls(excelName, sheetName, feedCall, "crt_" + cust_param);
			if (criteogampadcallcount == 0) {
				System.out.println("Ad Call :" + feedCall + " not found in charles session, hence Custom Parameter: "
						+ cust_param + " validation is failed");
				logStep("Ad Call :" + feedCall + " not found in charles session, hence Custom Parameter: " + cust_param
						+ " validation is failed");
				Assert.fail("Ad Call :" + feedCall + " not found in charles session, hence Custom Parameter: "
						+ cust_param + " validation is failed");
			} else if (customParamsList.size() == 0) {
				System.out.println("Ad Call :" + feedCall + " not having Custom Parameter: " + cust_param
						+ ", hence Custom Parameter: " + cust_param + " validation is failed");
				logStep("Ad Call :" + feedCall + " not having Custom Parameter: " + cust_param
						+ ", hence Custom Parameter: " + cust_param + " validation is failed");
				Assert.fail("Ad Call :" + feedCall + " not having Custom Parameter: " + cust_param
						+ ", hence Custom Parameter: " + cust_param + " validation is failed");
			} else {

				if (listOf_criteo_Params.size() > customParamsList.size()) {
					maxIterations = customParamsList.size();
				} else {
					maxIterations = listOf_criteo_Params.size();
				}
				
				if (sheetName.equalsIgnoreCase("Health(goRun)") || sheetName.equalsIgnoreCase("Health(boatAndBeach)")) {
					if (maxIterations%2 !=0 ) {
						maxIterations++;
					}
					int index = 0;
					for (int i = 0; i < maxIterations / 2; i++) {
						if (listOf_criteo_Params.get(i).equalsIgnoreCase("-1")) {
							criteoBiddingFailCount++;
							if (listOf_criteo_Params.size() == 1) {
								System.out.println(
										"It looks that the only Occurance of Criteo Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
								logStep("It looks that the only Occurance of Criteo Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
							} else {
								System.out.println("It looks that: " + i
										+ " Occurance of Criteo Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
								logStep("It looks that: " + i
										+ " Occurance of Criteo Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
							}

						} else {
							criteoBiddingSuccessCount++;
							index++;
							if (cust_param.equalsIgnoreCase("displayurl")) {

								if (customParamsList.get(i + index).equalsIgnoreCase("-1")) {
									System.out.println(i + 1 + " Occurance of corresponding " + sheetName
											+ " gampad call: " + feedCall + " not having parameter " + cust_param);
									logStep(i + 1 + " Occurance of corresponding " + sheetName + " gampad call: "
											+ feedCall + " not having parameter " + cust_param);
									failCount++;
								} else {

									System.out.println(i + " Occurance of Criteo call " + cust_param + " value: "
											+ listOf_criteo_Params.get(i) + " is  matched with " + (i + 1)
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i + index));
									logStep(i + " Occurance of Criteo call " + cust_param + " value: "
											+ listOf_criteo_Params.get(i) + " is  matched with " + (i + 1)
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i + index));

									successCount++;
								}

							} else {

								if (listOf_criteo_Params.get(i).equalsIgnoreCase(customParamsList.get(i + index))) {
									System.out.println(i + " Occurance of Criteo call " + cust_param + " value: "
											+ listOf_criteo_Params.get(i) + " is matched with " + (i + 1)
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i + index));
									logStep(i + " Occurance of Criteo call " + cust_param + " value: "
											+ listOf_criteo_Params.get(i) + " is matched with " + (i + 1)
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i + index));
									successCount++;
								} else {
									if (customParamsList.get(i + index).equalsIgnoreCase("-1")) {
										System.out.println(i + 1 + " Occurance of corresponding " + sheetName
												+ " gampad call: " + feedCall + " not having parameter " + cust_param);
										logStep(i + 1 + " Occurance of corresponding " + sheetName + " gampad call: "
												+ feedCall + " not having parameter " + cust_param);
										failCount++;
									} else {
										System.out.println(i + " Occurance of Criteo call " + cust_param + " value: "
												+ listOf_criteo_Params.get(i) + " is not matched with " + (i + 1)
												+ " Occurance of corresponding " + sheetName + " gampad call "
												+ cust_param + " value: " + customParamsList.get(i + index));
										logStep(i + " Occurance of Criteo call " + cust_param + " value: "
												+ listOf_criteo_Params.get(i) + " is not matched with " + (i + 1)
												+ " Occurance of corresponding " + sheetName + " gampad call "
												+ cust_param + " value: " + customParamsList.get(i + index));
										failCount++;
									}

								}
							}

						}

					}

				} else if (sheetName.equalsIgnoreCase("Health(coldAndFluArticles)")
						|| sheetName.equalsIgnoreCase("Health(allergyArticles)")) {

					if (!sheetName.equalsIgnoreCase(healthcriteoArticleCheckHappenedSheet)) {
						healthcriteoArticleCheckHappenedSheet = sheetName;
						criteoHealthArticlesParamSizeNUrlCheckStartCount = criteoHealthArticlesParamCpmCheckStartCount;
					}
					int j;
					if (criteoHealthArticlesParamCpmCheckStartCount == 0) {
						j = 0;
					} else if (cust_param.equalsIgnoreCase("size") || cust_param.equalsIgnoreCase("displayUrl")) {
						j = criteoHealthArticlesParamSizeNUrlCheckStartCount;

					} else {
						j = criteoHealthArticlesParamCpmCheckStartCount;
					}

					for (int i = 0; i < maxIterations; i++, j++) {
						if (cust_param.equalsIgnoreCase("cpm")) {
							criteoHealthArticlesParamCpmCheckStartCount++;
						}

						if (listOf_criteo_Params.get(j).equalsIgnoreCase("-1")) {
							criteoBiddingFailCount++;
							if (listOf_criteo_Params.size() == 1) {
								System.out.println(
										"It looks that the only Occurance of Criteo Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
								logStep("It looks that the only Occurance of Criteo Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
							} else {
								System.out.println("It looks that: " + j
										+ " Occurance of Criteo Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
								logStep("It looks that: " + j
										+ " Occurance of Criteo Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
							}

						} else {
							criteoBiddingSuccessCount++;
							if (cust_param.equalsIgnoreCase("displayurl")) {

								if (customParamsList.get(i).equalsIgnoreCase("-1")) {
									System.out.println(i + " Occurance of corresponding " + sheetName + " gampad call: "
											+ feedCall + " not having parameter " + cust_param);
									logStep(i + " Occurance of corresponding " + sheetName + " gampad call: " + feedCall
											+ " not having parameter " + cust_param);
									failCount++;
								} else {
									System.out.println(j + " Occurance of Criteo call " + cust_param + " value: "
											+ listOf_criteo_Params.get(j) + " is  matched with " + i
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i));
									logStep(j + " Occurance of Criteo call " + cust_param + " value: "
											+ listOf_criteo_Params.get(j) + " is  matched with " + i
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i));

									successCount++;
								}

							} else {

								if (listOf_criteo_Params.get(j).equalsIgnoreCase(customParamsList.get(i))) {
									successCount++;
									/*
									 * System.out.println("amazon aax " + sheetName +
									 * " call bid value is matched with corresponding gampad call bid value");
									 * logStep("amazon aax " + sheetName +
									 * " call bid value is matched with corresponding gampad call bid value");
									 */

									System.out.println(j + " Occurance of Criteo call " + cust_param + " value: "
											+ listOf_criteo_Params.get(j) + " is matched with " + i
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i));
									logStep(j + " Occurance of Criteo call " + cust_param + " value: "
											+ listOf_criteo_Params.get(j) + " is matched with " + i
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i));
									// System.out.println("amazon aax " + sheetName + " call bid value validation is
									// successful");
									// logStep("amazon aax " + sheetName + " call bid value vaidation is
									// successful");
									// break;

								} else {
									if (customParamsList.get(i).equalsIgnoreCase("-1")) {
										System.out.println(i + " Occurance of corresponding " + sheetName
												+ " gampad call: " + feedCall + " not having parameter " + cust_param);
										logStep(i + " Occurance of corresponding " + sheetName + " gampad call: "
												+ feedCall + " not having parameter " + cust_param);
										failCount++;
									} else {
										System.out.println(j + " Occurance of Criteo call " + cust_param + " value: "
												+ listOf_criteo_Params.get(j) + " is not matched with " + i
												+ " Occurance of corresponding " + sheetName + " gampad call "
												+ cust_param + " value: " + customParamsList.get(i));
										logStep(j + " Occurance of Criteo call " + cust_param + " value: "
												+ listOf_criteo_Params.get(j) + " is not matched with " + i
												+ " Occurance of corresponding " + sheetName + " gampad call "
												+ cust_param + " value: " + customParamsList.get(i));
										failCount++;
									}

								}
							}
						}
					}

				} else {
					for (int i = 0; i < maxIterations; i++) {
						if (listOf_criteo_Params.get(i).equalsIgnoreCase("-1")) {
							criteoBiddingFailCount++;
							if (listOf_criteo_Params.size() == 1) {
								System.out.println(
										"It looks that the only Occurance of Criteo Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
								logStep("It looks that the only Occurance of Criteo Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
							} else {
								System.out.println("It looks that: " + i
										+ " Occurance of Criteo Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
								logStep("It looks that: " + i
										+ " Occurance of Criteo Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
							}

						} else {
							criteoBiddingSuccessCount++;
							if (cust_param.equalsIgnoreCase("displayurl")) {

								if (customParamsList.get(i).equalsIgnoreCase("-1")) {
									System.out.println(i + " Occurance of corresponding " + sheetName + " gampad call: "
											+ feedCall + " not having parameter " + cust_param);
									logStep(i + " Occurance of corresponding " + sheetName + " gampad call: " + feedCall
											+ " not having parameter " + cust_param);
									failCount++;
								} else {
									System.out.println(i + " Occurance of Criteo call " + cust_param + " value: "
											+ listOf_criteo_Params.get(i) + " is  matched with " + i
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i));
									logStep(i + " Occurance of Criteo call " + cust_param + " value: "
											+ listOf_criteo_Params.get(i) + " is  matched with " + i
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i));

									successCount++;
								}

							} else {

								
								if (listOf_criteo_Params.get(i).equalsIgnoreCase(customParamsList.get(i))) {
									successCount++;
									System.out.println(i + " Occurance of Criteo call " + cust_param + " value: "
											+ listOf_criteo_Params.get(i) + " is matched with " + i
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i));
									logStep(i + " Occurance of Criteo call " + cust_param + " value: "
											+ listOf_criteo_Params.get(i) + " is matched with " + i
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i));
								} else if (sheetName.equalsIgnoreCase("Interstitials")
										&& cust_param.equalsIgnoreCase("size") && !customParamsList.get(i).equalsIgnoreCase("-1")) {
									/**
									 * Since interstitial ad size is different than the response in the criteo call, hence skipping the size comparison
									 */
									successCount++;
									System.out.println(" Since interstitial ad size is different than the response in the criteo call, hence skipping the size comparison");
									logStep(" Since interstitial ad size is different than the response in the criteo call, hence skipping the size comparison");
									System.out.println(i + " Occurance of Criteo call " + cust_param + " value: "
											+ listOf_criteo_Params.get(i) + " is matched with " + i
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i));
									logStep(i + " Occurance of Criteo call " + cust_param + " value: "
											+ listOf_criteo_Params.get(i) + " is matched with " + i
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i));
								} else {
									if (customParamsList.get(i).equalsIgnoreCase("-1")) {
										System.out.println(i + " Occurance of corresponding " + sheetName
												+ " gampad call: " + feedCall + " not having parameter " + cust_param);
										logStep(i + " Occurance of corresponding " + sheetName + " gampad call: "
												+ feedCall + " not having parameter " + cust_param);
										failCount++;
									} else {
										System.out.println(i + " Occurance of Criteo call " + cust_param + " value: "
												+ listOf_criteo_Params.get(i) + " is not matched with " + i
												+ " Occurance of corresponding " + sheetName + " gampad call "
												+ cust_param + " value: " + customParamsList.get(i));
										logStep(i + " Occurance of Criteo call " + cust_param + " value: "
												+ listOf_criteo_Params.get(i) + " is not matched with " + i
												+ " Occurance of corresponding " + sheetName + " gampad call "
												+ cust_param + " value: " + customParamsList.get(i));
										failCount++;
									}

								}
							}

						}

					}

				}

			}
		}

		if (criteoBiddingFailCount > criteoBiddingSuccessCount) {
			if (maxIterations == 1) {
				if (failCount == 0) {
					System.out.println(
							"It looks that only Occurance of gampad call doesn't have criteo parameter due to corresponding instance of criteo bidding unsucecssful");
					logStep("It looks that only Occurance of gampad call doesn't have criteo parameter due to corresponding instance of criteo bidding unsucecssful");
				} else {
					System.out.println(
							"It looks that the only Occurance of gampad call criteo parameters validation is failed");
					logStep("It looks that the only Occurance of gampad call criteo parameters validation is failed");
					Assert.fail(
							"It looks that the only Occurance of gampad call criteo parameters validation is failed");
				}

			} else {
				System.out.println("Morethan 50% Of Criteo call bidding is not successful for placement id: "
						+ placementId + " ,refer to charles session file for more details ");
				logStep("Morethan 50% Of Criteo call bidding is not successful for placement id: " + placementId
						+ " ,refer to charles session file for more details ");
				System.out.println("Criteo call " + cust_param + " values validation with " + sheetName
						+ " gampad call is failed");
				logStep("Criteo call " + cust_param + " values validation with " + sheetName
						+ " gampad call is failed");
				Assert.fail("Morethan 50% Of Criteo call bidding is not successful for placement id: " + placementId
						+ " ,refer to charles session file for more details ");
			}

		} else if (failCount > successCount) {
			if (maxIterations == 1) {
				System.out.println(
						"It looks that the only Occurance of gampad call criteo parameters validation is failed");
				logStep("It looks that the only Occurance of gampad call criteo parameters validation is failed");
				Assert.fail("It looks that the only Occurance of gampad call criteo parameters validation is failed");
			} else {
				System.out.println(
						"Morethan 50% Of Criteo call " + cust_param + " values  not matched with corresponding "
								+ sheetName + " gampad call " + cust_param + " values");
				logStep("Morethan 50% Of Criteo call " + cust_param + " values  not matched with corresponding "
						+ sheetName + " gampad call " + cust_param + " values");
				System.out.println("Criteo call " + cust_param + " values validation with " + sheetName
						+ " gampad call is failed");
				logStep("Criteo call " + cust_param + " values validation with " + sheetName
						+ " gampad call is failed");
				Assert.fail("Morethan 50% Of Criteo call " + cust_param + " values  not matched with corresponding "
						+ sheetName + " gampad call " + cust_param + " values");
			}

		}

	}

	/**
	 * When there is same placement id given for multiple gampad calls, then criteo
	 * index is sending as parameter to map criteo value with gampad call. EX:
	 * Hourly1, Hourly2 & Hourly3 big ads
	 * 
	 * @param excelName
	 * @param sheetName
	 * @param cust_param
	 * @param considerCriteoIndex
	 * @param criteoIndex
	 * @param clearList
	 * @throws Exception
	 */
	public static void validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value(String excelName,
			String sheetName, String cust_param, boolean considerCriteoIndex, int criteoIndex, boolean clearList)
			throws Exception {

		ReadExcelValues.excelValues(excelName, sheetName);
		String placementId = null;
		if (sheetName.equalsIgnoreCase("Pulltorefresh")) {
			placementId = "weather.homescreen.adhesive";
		} else if (sheetName.equalsIgnoreCase("Hourly")) {
			placementId = "weather.hourly";
		} else if (sheetName.equalsIgnoreCase("Feed1")) {
			if (Ad instanceof IOSDriver<?>) {
				placementId = "weather.feed0";
			} else if (Ad instanceof AndroidDriver<?>) {
				placementId = "weather.feed2";						
			}
		} else if (sheetName.equalsIgnoreCase("Feed2")) {
			if (Ad instanceof IOSDriver<?>) {
				placementId = "weather.feed1";
			} else if (Ad instanceof AndroidDriver<?>) {
				placementId = "weather.feed3";						
			}
		} else if (sheetName.equalsIgnoreCase("Feed3")) {
			placementId = "weather.feed2";
		} else if (sheetName.equalsIgnoreCase("Feed4")) {
			placementId = "weather.feed3";
		} else if (sheetName.equalsIgnoreCase("Feed5")) {
			placementId = "weather.feed4";
		} else if (sheetName.equalsIgnoreCase("Feed6")) {
			placementId = "weather.feed5";
		} else if (sheetName.equalsIgnoreCase("Feed7")) {
			placementId = "weather.feed6";
		} else if (sheetName.equalsIgnoreCase("Air Quality(Content)")) {
			placementId = "weather.aq";
		} else if (sheetName.equalsIgnoreCase("SeasonalHub(Details)")) {
			placementId = "weather.seasonlhub";
		} else if (sheetName.equalsIgnoreCase("Today")) {
			placementId = "weather.trending";
		} else if (sheetName.equalsIgnoreCase("Daily(10day)")) {
			if (Ad instanceof IOSDriver<?>) {
				placementId = "weather.10day.largeAds";
			} else if (Ad instanceof AndroidDriver<?>) {
				placementId = "weather.dailydetails.largeAds.1";						
			}
		} else if (sheetName.equalsIgnoreCase("Map")) {
			placementId = "weather.maps";
		} else if (sheetName.equalsIgnoreCase("MyAlerts")) {
			placementId = "weather.alerts.center";
		} else if (sheetName.equalsIgnoreCase("weather.alerts")) {
			placementId = "weather.alerts";
		} else if (sheetName.equalsIgnoreCase("Health(coldAndFluArticles)")) {
			/*
			 * News detils, flu articles, alergy articles has same placement Id, here
			 * anything can be used
			 */
			placementId = "weather.articles";
		} else if (sheetName.equalsIgnoreCase("Health(allergyArticles)")) {
			/*
			 * News detils, flu articles, alergy articles has same placement Id, here
			 * anything can be used
			 */
			placementId = "weather.articles";
		} else if (sheetName.equalsIgnoreCase("Health(goRun)")) {
			placementId = "weather.running.largeAds";
		} else if (sheetName.equalsIgnoreCase("Health(boatAndBeach)")) {
			placementId = "content.beach.largeAds";
		} else if (sheetName.equalsIgnoreCase("Hourly1")) {
			if (Ad instanceof IOSDriver<?>) {
				placementId = "weather.hourly.bigad";
			} else if (Ad instanceof AndroidDriver<?>) {
				placementId = "weather.hourly1";						
			}
		} else if (sheetName.equalsIgnoreCase("Hourly2")) {
			if (Ad instanceof IOSDriver<?>) {
				placementId = "weather.hourly.bigad";
			} else if (Ad instanceof AndroidDriver<?>) {
				placementId = "weather.hourly2";						
			}
		} else if (sheetName.equalsIgnoreCase("Hourly3")) {
			if (Ad instanceof IOSDriver<?>) {
				placementId = "weather.hourly.bigad";
			} else if (Ad instanceof AndroidDriver<?>) {
				placementId = "weather.hourly3";						
			}
		} else if (sheetName.equalsIgnoreCase("Interstitials")) {
			if (Ad instanceof IOSDriver<?>) {
				placementId = "weather.interstitial.hourly";
			} else if (Ad instanceof AndroidDriver<?>) {
				placementId = "weather.interstitial";						
			}
		}
		String feedCall = null;
		// "iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Ftoday";
		if (sheetName.equalsIgnoreCase("PreRollVideo")) {
			feedCall = videoIUValue;
		} /*
			 * else if (sheetName.equalsIgnoreCase("IDD")) { String today =
			 * dailyDetailsDayOfWeek.concat("1"); feedCall = readExcelValues.data[18][Cap];
			 * feedCall = feedCall.concat("_") + today; }
			 */else {
			feedCall = ReadExcelValues.data[18][Cap];
		}

		int failCount = 0;
		int successCount = 0;
		int criteoBiddingFailCount = 0;
		int criteoBiddingSuccessCount = 0;
		int maxIterations = 0;

		get_Criteo_SDK_inapp_v2_call_response_parameter_by_placementId("Smoke", "Criteo", placementId, cust_param,
				clearList);

		if (criteocallsSize == 0) {
			System.out.println("Criteo call is not generated in current session, so skipping the " + cust_param
					+ " value verification");
			logStep("Criteo call is not generated in current session, so skipping the " + cust_param
					+ " value verification");

		} else if (criteocallsResponseSize == 0) {
			System.out.println("Criteo call response doesn't have the placement id: " + placementId
					+ "  i.e. bidding is not happened in current session, so skipping the " + cust_param
					+ " value verification");
			logStep("Criteo call response doesn't have the placement id: " + placementId
					+ "  i.e. bidding is not happened in current session, so skipping the " + cust_param
					+ " value verification");
			logStep("Criteo call " + cust_param + " values validation with " + sheetName + " gampad call is failed");
			Assert.fail("Criteo call bidding is not successful in all instances for placement id: " + placementId
					+ " ,refer to charles session file for more details ");

		} else if (criteoparamErrorCount == criteocallsResponseSize) {
			System.out.println(
					"Criteo call response contains error i.e. bidding is not happened in current session, so skipping the "
							+ cust_param + " value verification");
			logStep("Criteo call response contains error i.e. bidding is not happened in current session, so skipping the "
					+ cust_param + " value verification");

		} else if (nextGenIMadDisplayed && sheetName.equalsIgnoreCase("Pulltorefresh")) {
			/*
			 * There may be chances that gampad call might not generated.. for ex: when IM
			 * ad displayed on home screen, then homescreen today call doesnt generate
			 * 
			 */
			System.out.println("Since IM Ad displayed on App Launch, Homescreen call: " + cust_param
					+ " id validation is skipped");
			logStep("Since IM Ad displayed on App Launch, Homescreen call: " + cust_param
					+ " id validation is skipped");
		} else {
			if (cust_param.contentEquals("displayUrl")) {
				cust_param = "displayurl";
			}
			get_custom_param_values_from_gampadCalls(excelName, sheetName, feedCall, "crt_" + cust_param);
			if (criteogampadcallcount == 0) {
				System.out.println("Ad Call :" + feedCall + " not found in charles session, hence Custom Parameter: "
						+ cust_param + " validation is failed");
				logStep("Ad Call :" + feedCall + " not found in charles session, hence Custom Parameter: " + cust_param
						+ " validation is failed");
				Assert.fail("Ad Call :" + feedCall + " not found in charles session, hence Custom Parameter: "
						+ cust_param + " validation is failed");
			} else if (customParamsList.size() == 0) {
				System.out.println("Ad Call :" + feedCall + " not having Custom Parameter: " + cust_param
						+ ", hence Custom Parameter: " + cust_param + " validation is failed");
				logStep("Ad Call :" + feedCall + " not having Custom Parameter: " + cust_param
						+ ", hence Custom Parameter: " + cust_param + " validation is failed");
				Assert.fail("Ad Call :" + feedCall + " not having Custom Parameter: " + cust_param
						+ ", hence Custom Parameter: " + cust_param + " validation is failed");
			} else {

				if (listOf_criteo_Params.size() > customParamsList.size()) {
					maxIterations = customParamsList.size();
				} else {
					maxIterations = listOf_criteo_Params.size();
				}

				if (sheetName.equalsIgnoreCase("Health(goRun)") || sheetName.equalsIgnoreCase("Health(boatAndBeach)")) {
					if (maxIterations%2 !=0 ) {
						maxIterations++;
					}
					int index = 0;
					for (int i = 0; i < maxIterations / 2; i++) {
						if (listOf_criteo_Params.get(i).equalsIgnoreCase("-1")) {
							criteoBiddingFailCount++;
							if (listOf_criteo_Params.size() == 1) {
								System.out.println(
										"It looks that the only Occurance of Criteo Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
								logStep("It looks that the only Occurance of Criteo Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
							} else {
								System.out.println("It looks that: " + i
										+ " Occurance of Criteo Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
								logStep("It looks that: " + i
										+ " Occurance of Criteo Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
							}

						} else {
							
							index++;
							criteoBiddingSuccessCount++;
							if (cust_param.equalsIgnoreCase("displayurl")) {

								if (customParamsList.get(i + 1).equalsIgnoreCase("-1")) {
									System.out.println(i + 1 + " Occurance of corresponding " + sheetName
											+ " gampad call: " + feedCall + " not having parameter " + cust_param);
									logStep(i + 1 + " Occurance of corresponding " + sheetName + " gampad call: "
											+ feedCall + " not having parameter " + cust_param);
									failCount++;
								} else {
									System.out.println(i + " Occurance of Criteo call " + cust_param + " value: "
											+ listOf_criteo_Params.get(i) + " is  matched with " + (i + 1)
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i + index));
									logStep(i + " Occurance of Criteo call " + cust_param + " value: "
											+ listOf_criteo_Params.get(i) + " is  matched with " + (i + 1)
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i + index));

									successCount++;
								}

							} else {

								if (listOf_criteo_Params.get(i).equalsIgnoreCase(customParamsList.get(i + index))) {
									successCount++;
									System.out.println(i + " Occurance of Criteo call " + cust_param + " value: "
											+ listOf_criteo_Params.get(i) + " is matched with " + (i + 1)
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i + index));
									logStep(i + " Occurance of Criteo call " + cust_param + " value: "
											+ listOf_criteo_Params.get(i) + " is matched with " + (i + 1)
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i + index));
								} else {
									if (customParamsList.get(i + index).equalsIgnoreCase("-1")) {
										System.out.println(i + 1 + " Occurance of corresponding " + sheetName
												+ " gampad call: " + feedCall + " not having parameter " + cust_param);
										logStep(i + 1 + " Occurance of corresponding " + sheetName + " gampad call: "
												+ feedCall + " not having parameter " + cust_param);
										failCount++;
									} else {
										System.out.println(i + " Occurance of Criteo call " + cust_param + " value: "
												+ listOf_criteo_Params.get(i) + " is not matched with " + (i + 1)
												+ " Occurance of corresponding " + sheetName + " gampad call "
												+ cust_param + " value: " + customParamsList.get(i + index));
										logStep(i + " Occurance of Criteo call " + cust_param + " value: "
												+ listOf_criteo_Params.get(i) + " is not matched with " + (i + 1)
												+ " Occurance of corresponding " + sheetName + " gampad call "
												+ cust_param + " value: " + customParamsList.get(i + index));
										failCount++;
									}

								}
							}

						}

					}

				} else if (sheetName.equalsIgnoreCase("Health(coldAndFluArticles)")
						|| sheetName.equalsIgnoreCase("Health(allergyArticles)")) {

					if (!sheetName.equalsIgnoreCase(healthcriteoArticleCheckHappenedSheet)) {
						healthcriteoArticleCheckHappenedSheet = sheetName;
						criteoHealthArticlesParamSizeNUrlCheckStartCount = criteoHealthArticlesParamCpmCheckStartCount;
					}
					int j;
					if (criteoHealthArticlesParamCpmCheckStartCount == 0) {
						j = 0;
					} else if (cust_param.equalsIgnoreCase("size") || cust_param.equalsIgnoreCase("displayUrl")) {
						j = criteoHealthArticlesParamSizeNUrlCheckStartCount;

					} else {
						j = criteoHealthArticlesParamCpmCheckStartCount;
					}

					for (int i = 0; i < maxIterations; i++, j++) {
						if (cust_param.equalsIgnoreCase("cpm")) {
							criteoHealthArticlesParamCpmCheckStartCount++;
						}

						if (listOf_criteo_Params.get(j).equalsIgnoreCase("-1")) {
							criteoBiddingFailCount++;
							if (listOf_criteo_Params.size() == 1) {
								System.out.println(
										"It looks that the only Occurance of Criteo Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
								logStep("It looks that the only Occurance of Criteo Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
							} else {
								System.out.println("It looks that: " + j
										+ " Occurance of Criteo Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
								logStep("It looks that: " + j
										+ " Occurance of Criteo Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
							}

						} else {
							criteoBiddingSuccessCount++;
							if (cust_param.equalsIgnoreCase("displayurl")) {

								if (customParamsList.get(i).equalsIgnoreCase("-1")) {
									System.out.println(i + " Occurance of corresponding " + sheetName + " gampad call: "
											+ feedCall + " not having parameter " + cust_param);
									logStep(i + " Occurance of corresponding " + sheetName + " gampad call: " + feedCall
											+ " not having parameter " + cust_param);
									failCount++;
								} else {
									System.out.println(j + " Occurance of Criteo call " + cust_param + " value: "
											+ listOf_criteo_Params.get(j) + " is  matched with " + i
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i));
									logStep(j + " Occurance of Criteo call " + cust_param + " value: "
											+ listOf_criteo_Params.get(j) + " is  matched with " + i
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i));

									successCount++;
								}

							} else {

								if (listOf_criteo_Params.get(j).equalsIgnoreCase(customParamsList.get(i))) {
									successCount++;
									/*
									 * System.out.println("amazon aax " + sheetName +
									 * " call bid value is matched with corresponding gampad call bid value");
									 * logStep("amazon aax " + sheetName +
									 * " call bid value is matched with corresponding gampad call bid value");
									 */

									System.out.println(j + " Occurance of Criteo call " + cust_param + " value: "
											+ listOf_criteo_Params.get(j) + " is matched with " + i
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i));
									logStep(j + " Occurance of Criteo call " + cust_param + " value: "
											+ listOf_criteo_Params.get(j) + " is matched with " + i
											+ " Occurance of corresponding " + sheetName + " gampad call " + cust_param
											+ " value: " + customParamsList.get(i));
									// System.out.println("amazon aax " + sheetName + " call bid value validation is
									// successful");
									// logStep("amazon aax " + sheetName + " call bid value vaidation is
									// successful");
									// break;

								} else {
									if (customParamsList.get(i).equalsIgnoreCase("-1")) {
										System.out.println(i + " Occurance of corresponding " + sheetName
												+ " gampad call: " + feedCall + " not having parameter " + cust_param);
										logStep(i + " Occurance of corresponding " + sheetName + " gampad call: "
												+ feedCall + " not having parameter " + cust_param);
										failCount++;
									} else {
										System.out.println(j + " Occurance of Criteo call " + cust_param + " value: "
												+ listOf_criteo_Params.get(j) + " is not matched with " + i
												+ " Occurance of corresponding " + sheetName + " gampad call "
												+ cust_param + " value: " + customParamsList.get(i));
										logStep(j + " Occurance of Criteo call " + cust_param + " value: "
												+ listOf_criteo_Params.get(j) + " is not matched with " + i
												+ " Occurance of corresponding " + sheetName + " gampad call "
												+ cust_param + " value: " + customParamsList.get(i));
										failCount++;
									}

								}
							}
						}
					}

				} else {
					for (int i = 0; i < maxIterations; i++) {
						if (listOf_criteo_Params.get(i).equalsIgnoreCase("-1")) {
							criteoBiddingFailCount++;
							if (listOf_criteo_Params.size() == 1) {
								System.out.println(
										"It looks that the only Occurance of Criteo Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
								logStep("It looks that the only Occurance of Criteo Call bidding is not happened..Hence skipping the further validation...inspect the charles response for more details");
							} else {
								System.out.println("It looks that: " + i
										+ " Occurance of Criteo Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
								logStep("It looks that: " + i
										+ " Occurance of Criteo Call bidding is not happened..Hence skipping the current instance validation...inspect the charles response for more details");
							}

						} else {
							criteoBiddingSuccessCount++;
							if (considerCriteoIndex) {
								if (listOf_criteo_Params.size() >= criteoIndex) {
									criteoIndex = criteoIndex - 1;

									if (cust_param.equalsIgnoreCase("displayurl")) {

										if (customParamsList.get(i).equalsIgnoreCase("-1")) {
											System.out.println(
													i + " Occurance of corresponding " + sheetName + " gampad call: "
															+ feedCall + " not having parameter " + cust_param);
											logStep(i + " Occurance of corresponding " + sheetName + " gampad call: "
													+ feedCall + " not having parameter " + cust_param);
											failCount++;
											break;
										} else {
											System.out.println(i + " Occurance of Criteo call " + cust_param
													+ " value: " + listOf_criteo_Params.get(criteoIndex)
													+ " is  matched with " + i + " Occurance of corresponding "
													+ sheetName + " gampad call " + cust_param + " value: "
													+ customParamsList.get(i));
											logStep(i + " Occurance of Criteo call " + cust_param + " value: "
													+ listOf_criteo_Params.get(criteoIndex) + " is  matched with " + i
													+ " Occurance of corresponding " + sheetName + " gampad call "
													+ cust_param + " value: " + customParamsList.get(i));

											successCount++;
											break;
										}

									} else {

										if (listOf_criteo_Params.get(criteoIndex)
												.equalsIgnoreCase(customParamsList.get(i))) {

											System.out.println(criteoIndex + " Occurance of Criteo call " + cust_param
													+ " value: " + listOf_criteo_Params.get(criteoIndex)
													+ " is matched with " + i + " Occurance of corresponding "
													+ sheetName + " gampad call " + cust_param + " value: "
													+ customParamsList.get(i));
											logStep(criteoIndex + " Occurance of Criteo call " + cust_param + " value: "
													+ listOf_criteo_Params.get(criteoIndex) + " is matched with " + i
													+ " Occurance of corresponding " + sheetName + " gampad call "
													+ cust_param + " value: " + customParamsList.get(i));
											successCount++;
											break;
										} else {
											if (customParamsList.get(i).equalsIgnoreCase("-1")) {
												System.out.println(i + " Occurance of corresponding " + sheetName
														+ " gampad call: " + feedCall + " not having parameter "
														+ cust_param);
												logStep(i + " Occurance of corresponding " + sheetName
														+ " gampad call: " + feedCall + " not having parameter "
														+ cust_param);
												failCount++;
												break;
											} else {
												System.out.println(criteoIndex + " Occurance of Criteo call "
														+ cust_param + " value: "
														+ listOf_criteo_Params.get(criteoIndex)
														+ " is not matched with " + i + " Occurance of corresponding "
														+ sheetName + " gampad call " + cust_param + " value: "
														+ customParamsList.get(i));
												logStep(criteoIndex + " Occurance of Criteo call " + cust_param
														+ " value: " + listOf_criteo_Params.get(criteoIndex)
														+ " is not matched with " + i + " Occurance of corresponding "
														+ sheetName + " gampad call " + cust_param + " value: "
														+ customParamsList.get(i));
												failCount++;
												break;
											}

										}

									}

								} else {
									System.out.println(criteoIndex + " " + placementId
											+ " Criteo call is not generated in current session, so skipping the "
											+ cust_param + " value verification of " + sheetName + " gampad call ");
									logStep(criteoIndex + " " + placementId
											+ " Criteo call is not generated in current session, so skipping the "
											+ cust_param + " value verification of " + sheetName + " gampad call ");

								}

							} else {

								if (cust_param.equalsIgnoreCase("displayurl")) {

									if (customParamsList.get(i).equalsIgnoreCase("-1")) {
										System.out.println(i + " Occurance of corresponding " + sheetName
												+ " gampad call: " + feedCall + " not having parameter " + cust_param);
										logStep(i + " Occurance of corresponding " + sheetName + " gampad call: "
												+ feedCall + " not having parameter " + cust_param);
										failCount++;
									} else {
										System.out.println(i + " Occurance of Criteo call " + cust_param + " value: "
												+ listOf_criteo_Params.get(i) + " is  matched with " + i
												+ " Occurance of corresponding " + sheetName + " gampad call "
												+ cust_param + " value: " + customParamsList.get(i));
										logStep(i + " Occurance of Criteo call " + cust_param + " value: "
												+ listOf_criteo_Params.get(i) + " is  matched with " + i
												+ " Occurance of corresponding " + sheetName + " gampad call "
												+ cust_param + " value: " + customParamsList.get(i));

										successCount++;
									}

								} else {

									if (listOf_criteo_Params.get(i).equalsIgnoreCase(customParamsList.get(i))) {
										successCount++;
										System.out.println(i + " Occurance of Criteo call " + cust_param + " value: "
												+ listOf_criteo_Params.get(i) + " is matched with " + i
												+ " Occurance of corresponding " + sheetName + " gampad call "
												+ cust_param + " value: " + customParamsList.get(i));
										logStep(i + " Occurance of Criteo call " + cust_param + " value: "
												+ listOf_criteo_Params.get(i) + " is matched with " + i
												+ " Occurance of corresponding " + sheetName + " gampad call "
												+ cust_param + " value: " + customParamsList.get(i));
									} else {
										if (customParamsList.get(i).equalsIgnoreCase("-1")) {
											System.out.println(
													i + " Occurance of corresponding " + sheetName + " gampad call: "
															+ feedCall + " not having parameter " + cust_param);
											logStep(i + " Occurance of corresponding " + sheetName + " gampad call: "
													+ feedCall + " not having parameter " + cust_param);
											failCount++;
										} else {
											System.out.println(i + " Occurance of Criteo call " + cust_param
													+ " value: " + listOf_criteo_Params.get(i) + " is not matched with "
													+ i + " Occurance of corresponding " + sheetName + " gampad call "
													+ cust_param + " value: " + customParamsList.get(i));
											logStep(i + " Occurance of Criteo call " + cust_param + " value: "
													+ listOf_criteo_Params.get(i) + " is not matched with " + i
													+ " Occurance of corresponding " + sheetName + " gampad call "
													+ cust_param + " value: " + customParamsList.get(i));
											failCount++;
										}

									}
								}

							}

						}

					}

				}

			}
		}

		if (criteoBiddingFailCount > criteoBiddingSuccessCount) {
			if (maxIterations == 1) {
				if (failCount == 0) {
					System.out.println(
							"It looks that only Occurance of gampad call doesn't have criteo parameter due to corresponding instance of criteo bidding unsucecssful");
					logStep("It looks that only Occurance of gampad call doesn't have criteo parameter due to corresponding instance of criteo bidding unsucecssful");
				} else {
					System.out.println(
							"It looks that the only Occurance of gampad call criteo parameters validation is failed");
					logStep("It looks that the only Occurance of gampad call criteo parameters validation is failed");
					Assert.fail(
							"It looks that the only Occurance of gampad call criteo parameters validation is failed");
				}

			} else {
				System.out.println("Morethan 50% Of Criteo call bidding is not successful for placement id: "
						+ placementId + " ,refer to charles session file for more details ");
				logStep("Morethan 50% Of Criteo call bidding is not successful for placement id: " + placementId
						+ " ,refer to charles session file for more details ");
				System.out.println("Criteo call " + cust_param + " values validation with " + sheetName
						+ " gampad call is failed");
				logStep("Criteo call " + cust_param + " values validation with " + sheetName
						+ " gampad call is failed");
				Assert.fail("Morethan 50% Of Criteo call bidding is not successful for placement id: " + placementId
						+ " ,refer to charles session file for more details ");
			}

		} else if (failCount > successCount) {
			if (maxIterations == 1) {
				System.out.println(
						"It looks that the only Occurance of gampad call criteo parameters validation is failed");
				logStep("It looks that the only Occurance of gampad call criteo parameters validation is failed");
				Assert.fail("It looks that the only Occurance of gampad call criteo parameters validation is failed");
			} else {
				System.out.println(
						"Morethan 50% Of Criteo call " + cust_param + " values  not matched with corresponding "
								+ sheetName + " gampad call " + cust_param + " values");
				logStep("Morethan 50% Of Criteo call " + cust_param + " values  not matched with corresponding "
						+ sheetName + " gampad call " + cust_param + " values");
				System.out.println("Criteo call " + cust_param + " values validation with " + sheetName
						+ " gampad call is failed");
				logStep("Criteo call " + cust_param + " values validation with " + sheetName
						+ " gampad call is failed");
				Assert.fail("Morethan 50% Of Criteo call " + cust_param + " values  not matched with corresponding "
						+ sheetName + " gampad call " + cust_param + " values");
			}

		}
	}

	/**
	 * When asset calls are dynamic similar to Integrated Feed Card, then we can use
	 * this method to validate by considering the static parameters from the asset
	 * calls.
	 * 
	 * @param host
	 * @param path1
	 * @param path2
	 * @param path3
	 * @throws Exception
	 */
	public static void verifyAssetCallWithHostandPath(String host, String path1, String path2, String path3)
			throws Exception {
		// readExcelValues.excelValues(excelName, sheetName);
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@host";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		// Getting custom_params amzn_b values
		List<String> customParamsList = new ArrayList<String>();

		// String iuId = null;

		boolean iuExists = false;
		for (String qry : getQueryList) {
			if (qry.contains(host)) {
				iuExists = true;
				break;
			}
		}
		boolean hflag = false;
		boolean pflag = false;
		boolean resflag = false;

		if (iuExists) {
			System.out.println(host + "  asset call is present");
			logStep(host + "  asset call is present");
			outerloop: for (int p = 0; p < nodeList.getLength(); p++) {
				// System.out.println("Total transactions: "+nodeList.getLength());
				if (nodeList.item(p) instanceof Node) {
					Node node = nodeList.item(p);
					if (node.hasChildNodes()) {
						NodeList nl = node.getChildNodes();
						for (int j = 0; j < nl.getLength(); j++) {
							// System.out.println("node1 length is: "+nl.getLength());
							Node innernode = nl.item(j);
							if (innernode != null) {
								// System.out.println("Innernode name is: "+innernode.getNodeName());
								if (innernode.getNodeName().equals("request")) {
									if (innernode.hasChildNodes()) {
										NodeList n2 = innernode.getChildNodes();
										for (int k = 0; k < n2.getLength(); k++) {
											// System.out.println("node2 length is: "+n2.getLength());
											Node innernode2 = n2.item(k);
											if (innernode2 != null) {
												// System.out.println("Innernode2 name is: "+innernode2.getNodeName());
												if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
													Element eElement = (Element) innernode2;
													// System.out.println("Innernode2 element name is:
													// "+eElement.getNodeName());
													if (eElement.getNodeName().equals("headers")) {
														if (innernode2.hasChildNodes()) {
															NodeList n3 = innernode2.getChildNodes();
															for (int q = 0; q < n3.getLength(); q++) {
																// System.out.println("node3 length is:
																// "+n3.getLength());
																Node innernode3 = n3.item(q);
																if (innernode3 != null) {
																	// System.out.println("Innernode3 name is:
																	// "+innernode3.getNodeName());
																	if (innernode3.getNodeType() == Node.ELEMENT_NODE) {
																		Element eElement1 = (Element) innernode3;
																		// System.out.println("Innernode3 element name
																		// is: "+eElement1.getNodeName());
																		if (eElement1.getNodeName().equals("header") || eElement1.getNodeName().equals("first-line")) {
																			String content = eElement1.getTextContent();
																			// System.out.println("request body
																			// "+content);

																			/*
																			 * if (content.contains(host)) { hflag =
																			 * true; // System.out.println("request body
																			 * // found " // + content);
																			 * 
																			 * } else if (content.contains(path)) {
																			 * pflag = true; //
																			 * System.out.println("request body // found
																			 * " // + content); }
																			 */
																			if (content.contains(host)) {
																				hflag = true;
																				// System.out.println("request body
																				// found "+ content);

																			} else if (content.contains(path1)) {
																				// System.out.println("request body
																				// found "+ content);
																				if (content.contains(path2)) {
																					// System.out.println("request body
																					// found "+ content);
																					if (content.contains(path3)) {
																						pflag = true;
																						// System.out.println("request
																						// body found "+ content);
																					}
																				}

																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}

								/*
								 * if (flag) { // System.out.println("Exiting after found true "); //
								 * System.out.println("checking innernode name is: "+innernode.getNodeName());
								 * if (innernode.getNodeName().equals("response")) { //
								 * System.out.println(innernode.getNodeName()); if (innernode.hasChildNodes()) {
								 * NodeList n2 = innernode.getChildNodes(); for (int k = 0; k < n2.getLength();
								 * k++) { Node innernode2 = n2.item(k); if (innernode2 != null) { if
								 * (innernode2.getNodeType() == Node.ELEMENT_NODE) { Element eElement =
								 * (Element) innernode2; if (eElement.getNodeName().equals("body")) { String
								 * content = eElement.getTextContent(); //
								 * System.out.println("response body "+content); if
								 * (content.contains(readExcelValues.data[13][Cap])) { resflag = true; break
								 * outerloop;
								 * 
								 * } } } } } } }
								 * 
								 * }
								 */
								if (hflag && pflag) {
									resflag = true;
									break outerloop;
								}
							}
						}
					}
				}
				// flag = false;
			}

		} else {
			System.out.println(path3 + " asset call is not present");
			logStep(path3 + " ad call is not present");

		}

		if (resflag) {
			System.out.println(path3 + " Asset Call is present");
			logStep(path3 + " Asset Call is present");
			System.out.println(path3 + " Asset Call validation is successful");
			logStep(path3 + " Asset Call validation is successful");
		} else {
			System.out.println(path3 + " Asset Call is not present");
			logStep(path3 + " Asset Call is not present");
			System.out.println(path3 + " Asset Call validation is failed");
			logStep(path3 + " Asset Call validation is failed");
			Assert.fail(path3 + " Asset Call is not present in Charles session");
		}

	}

	/**
	 * Verifies criteo request for given placement id in json request body
	 * @param placementId
	 * @param apiData
	 * @return
	 * @throws Exception
	 */
	public static boolean verify_criteo_request_for_given_placementId_inJsonRequestBody(String placementId,
			String apiData) throws Exception {

		JSONParser parser = new JSONParser();
		// System.out.println("adreq1 is : "+adreq1.toString());
		Object obj = parser.parse(new String(apiData));
		// System.out.println("obj : "+obj);
		JSONObject jsonObject = (JSONObject) obj;
		String ApiParamValue = "";
		String JsonParam = null;

		JSONObject mainTag = null;
		JSONArray eleArray = null;
		boolean matchFound = false;

		try {
			JsonParam = "slots".trim();
			eleArray = (JSONArray) jsonObject.get(JsonParam);
			// System.out.println(eleArray);
			try {

				ArrayList<String> Ingredients_names = new ArrayList<>();
				for (int i = 0; i < eleArray.size(); i++) {

					String arrayElement = String.valueOf(eleArray.get(i));

					Ingredients_names.add(arrayElement);
					obj = parser.parse(new String(arrayElement));
					jsonObject = (JSONObject) obj;
					mainTag = (JSONObject) obj;

					try {
						String cApiParamValue = String.valueOf(mainTag.get("placementId"));
						if (cApiParamValue.equalsIgnoreCase(placementId)) {

							matchFound = true;

						} else {
							// System.out.println("... noticed");
							continue;
						}

					} catch (Exception ex) {
						ex.printStackTrace();
						continue;
					}

				}
			} catch (Exception e) {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return matchFound;
	}

	/**
	 * This method asserts the existence of Gampad call in charles session file
	 * 
	 * @param excelName
	 * @param sheetName
	 * @param expected
	 * @throws Exception
	 */
	public static void verify_Gampad_adcall(String excelName, String sheetName, boolean expected) throws Exception {

		ReadExcelValues.excelValues(excelName, sheetName);
		String host = ReadExcelValues.data[2][Cap];
		String path = ReadExcelValues.data[3][Cap];

		boolean flag = verifyAPICallWithHostandPath(host, path);
		if (flag) {
			System.out.println(host + path + " call is present in Charles session");
			logStep(host + path + " call is present in Charles session");

		} else {
			System.out.println(host + path + " call is not present in Charles session");
			logStep(host + path + " call is not present in Charles session");

		}

		if (expected == flag) {
			System.out.println(sheetName + " :ad Call Verification is successful");
			logStep(sheetName + " :ad Call Verification is successful");

		} else {
			System.out.println(sheetName + " :ad Call Verification is failed");
			logStep(sheetName + " :ad Call Verification is failed");
			if (expected) {
				System.out.println(sheetName + " :ad Call expected to present but it not exists");
				logStep(sheetName + " :ad Call expected to present but it not exists");
				Assert.fail(sheetName + " :ad Call expected to present but it not exists");
			} else {
				System.out.println(sheetName + " :ad Call is not expected to present but it exists");
				logStep(sheetName + " :ad Call is not expected to present but it exists");
				Assert.fail(sheetName + " :ad Call is not expected to present but it exists");
			}
		}

	}

	/**
	 * This method asserts the existence of amazon call in charles session file
	 * 
	 * @param excelName
	 * @param sheetName
	 * @param expected
	 * @throws Exception
	 */
	public static void verify_Amazon_aax_call(String excelName, String sheetName, boolean expected) throws Exception {

		ReadExcelValues.excelValues(excelName, sheetName);
		String host = ReadExcelValues.data[2][Cap];
		String path = ReadExcelValues.data[3][Cap];

		boolean flag = verifyAPICallWithHostandPath(host, path);
		if (flag) {
			System.out.println(host + path + " call is present in Charles session");
			logStep(host + path + " call is present in Charles session");

		} else {
			System.out.println(host + path + " call is not present in Charles session");
			logStep(host + path + " call is not present in Charles session");

		}

		if (expected == flag) {
			System.out.println(sheetName + " :API Call Verification is successful");
			logStep(sheetName + " :API Call Verification is successful");

		} else {
			System.out.println(sheetName + " :API Call Verification is failed");
			logStep(sheetName + " :API Call Verification is failed");
			if (expected) {
				System.out.println(sheetName + " :API Call expected to present but it not exists");
				logStep(sheetName + " :API Call expected to present but it not exists");
				Assert.fail(sheetName + " :API Call expected to present but it not exists");
			} else {
				System.out.println(sheetName + " :API Call is not expected to present but it exists");
				logStep(sheetName + " :API Call is not expected to present but it exists");
				Assert.fail(sheetName + " :API Call is not expected to present but it exists");
			}
		}

	}

	/**
	 * verifies aax call parameter in charles session
	 * @param excelName
	 * @param sheetName
	 * @param cust_param
	 * @param expected
	 * @throws Exception
	 */
	public static void validate_Amazon_aax_call_parameter(String excelName, String sheetName, String cust_param,
			String expected) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String host = ReadExcelValues.data[2][Cap];
		String path = ReadExcelValues.data[3][Cap];

		boolean flag = verifyAPICallWithHostandPath(host, path);
		if (flag) {
			System.out.println(host + path + " call is present in Charles session");
			logStep(host + path + " call is present in Charles session");

			String actual = get_param_value_from_APIRequest(host, path, cust_param);

			if (actual.equalsIgnoreCase(expected)) {
				System.out.println("Custom Parameter :" + cust_param + " value: " + actual
						+ " is matched with the expected value " + expected);
				logStep("Custom Parameter :" + cust_param + " value: " + actual + " is matched with the expected value "
						+ expected);
				System.out.println("AAx call Custom Parameter :" + cust_param + " validation is successful");
				logStep("AAx call Custom Parameter :" + cust_param + " validation is successful");
			} else {
				System.out.println("Custom Parameter :" + cust_param + " value: " + actual
						+ " is not matched with the expected value " + expected);
				logStep("Custom Parameter :" + cust_param + " value: " + actual
						+ " is not matched with the expected value " + expected);
				System.out.println("AAx call Custom Parameter :" + cust_param + " validation is failed");
				logStep("AAx call Custom Parameter :" + cust_param + " validation is failed");
				Assert.fail("Custom Parameter :" + cust_param + " value: " + actual
						+ " is not matched with the expected value " + expected);
			}

		} else {
			System.out.println(host + path + " call is not present in Charles session, hence Custom Parameter: "
					+ cust_param + " validation skipped");
			logStep(host + path + " call is not present in Charles session, hence Custom Parameter: " + cust_param
					+ " validation skipped");
			System.out.println("AAx call Custom Parameter :" + cust_param + " validation is failed");
			logStep("AAx call Custom Parameter :" + cust_param + " validation is failed");

			Assert.fail(host + path + " call is not present in Charles session, hence Custom Parameter: " + cust_param
					+ " validation skipped");

		}

	}

	/**
	 * Verifies API call response header parameter
	 * @param host
	 * @param path
	 * @param responseParameter
	 * @param responseValue
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyAPICallResponseHeaderParameter(String host, String path, String responseParameter,
			String responseValue) throws Exception {
		// readExcelValues.excelValues(excelName, sheetName);
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@host";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

// Getting custom_params amzn_b values
		List<String> customParamsList = new ArrayList<String>();

		// String iuId = null;

		boolean iuExists = false;
		for (String qry : getQueryList) {
			if (qry.contains(host)) {
				iuExists = true;
				break;
			}
		}
		boolean flag = false;
		boolean hflag = false;
		boolean pflag = false;
		boolean resflag = false;
		boolean isresponseStatusCheckPass = false;

		if (iuExists) {
			System.out.println(host + "  call is present");
			logStep(host + "  call is present");
			outerloop: for (int p = 0; p < nodeList.getLength(); p++) {
				// System.out.println("Total transactions: "+nodeList.getLength());
				if (nodeList.item(p) instanceof Node) {
					Node node = nodeList.item(p);
					if (node.hasChildNodes()) {
						NodeList nl = node.getChildNodes();
						for (int j = 0; j < nl.getLength(); j++) {
							// System.out.println("node1 length is: "+nl.getLength());
							Node innernode = nl.item(j);
							if (innernode != null) {
								// System.out.println("Innernode name is: "+innernode.getNodeName());
								if (innernode.getNodeName().equals("request")) {
									if (innernode.hasChildNodes()) {
										NodeList n2 = innernode.getChildNodes();
										for (int k = 0; k < n2.getLength(); k++) {
											// System.out.println("node2 length is: "+n2.getLength());
											Node innernode2 = n2.item(k);
											if (innernode2 != null) {
												// System.out.println("Innernode2 name is: "+innernode2.getNodeName());
												if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
													Element eElement = (Element) innernode2;
													// System.out.println("Innernode2 element name is:
													// "+eElement.getNodeName());
													if (eElement.getNodeName().equals("headers")) {
														if (innernode2.hasChildNodes()) {
															NodeList n3 = innernode2.getChildNodes();
															for (int q = 0; q < n3.getLength(); q++) {
																// System.out.println("node3 length is:
																// "+n3.getLength());
																Node innernode3 = n3.item(q);
																if (innernode3 != null) {
																	// System.out.println("Innernode3 name is:
																	// "+innernode3.getNodeName());
																	if (innernode3.getNodeType() == Node.ELEMENT_NODE) {
																		Element eElement1 = (Element) innernode3;
																		// System.out.println("Innernode3 element name
																		// is: "+eElement1.getNodeName());
																		if (eElement1.getNodeName().equals("header")) {
																			String content = eElement1.getTextContent();
																			// System.out.println("request body
																			// "+content);

																			if (content.contains(host)) {
																				hflag = true;
																				// System.out.println("request body
																				// found "
																				// + content);

																			} else if (content.contains(path)) {
																				pflag = true;
																				// System.out.println("request body
																				// found "
																				// + content);
																			}

																		}

																		// this condition especially for android since
																		// its file has path value under first-line
																		// element
																		if (eElement1.getNodeName()
																				.equals("first-line")) {
																			String content = eElement1.getTextContent();
																			// System.out.println("request body
																			// "+content);

																			if (content.contains(path)) {
																				pflag = true;
																				// System.out.println("request body
																				// found "
																				// + content);
																			}
																		}
																		if (pflag && hflag) {

																			flag = true;
																		} else {
																			flag = false;
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}

								if (flag) {
									// System.out.println("Exiting after found true "); //
									// System.out.println("checking innernode name is: " + innernode.getNodeName());
									if (innernode.getNodeName().equals("response")) {
										// System.out.println(innernode.getNodeName());
										if (innernode.hasChildNodes()) {
											NodeList n2 = innernode.getChildNodes();
											for (int k = 0; k < n2.getLength(); k++) {
												Node innernode2 = n2.item(k);
												if (innernode2 != null) {
													if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
														Element eElement = (Element) innernode2;
														if (eElement.getNodeName().equals("headers")) {
															String contents = eElement.getTextContent();
															// System.out.println("response body "+contents);
															if (innernode2.hasChildNodes()) {
																NodeList n3 = innernode2.getChildNodes();
																for (int q = 0; q < n3.getLength(); q++) {
																	// System.out.println("node3 length is:
																	// "+n3.getLength());
																	Node innernode3 = n3.item(q);
																	if (innernode3 != null) {
																		// System.out.println("Innernode3 name is:
																		// "+innernode3.getNodeName());
																		if (innernode3
																				.getNodeType() == Node.ELEMENT_NODE) {
																			Element eElement1 = (Element) innernode3;
																			// System.out.println("Innernode3 element
																			// name
																			// is: "+eElement1.getNodeName());
																			if (eElement1.getNodeName()
																					.equals("header")) {
																				String content = eElement1
																						.getTextContent();
																				// System.out.println("response body
																				// "+content);

																				if (content.contains(responseParameter)
																						&& content.contains(
																								responseValue)) {
																					isresponseStatusCheckPass = true;
																					// System.out.println("request body
																					// found "
																					// + content);
																					break outerloop;

																				}
																			}

																			// this condition especially for android
																			// since
																			// its file has path value under first-line
																			// element
																			if (eElement1.getNodeName()
																					.equals("first-line")) {
																				String content = eElement1
																						.getTextContent();
																				// System.out.println("request body
																				// "+content); "HTTP/1.1 200 OK"

																				if (content.contains(responseValue)) {
																					isresponseStatusCheckPass = true;
																					// System.out.println("request body
																					// found "
																					// + content);
																					break outerloop;
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}

								}

								/*
								 * if (hflag && pflag) { resflag = true; break outerloop; }
								 */
							}
						}
					}
				}
				flag = false;
				hflag = false;
				pflag = false;
			}

		} else {
			System.out.println(host + " ad call is not present");
			logStep(host + " ad call is not present");

		}

		return isresponseStatusCheckPass;

	}

	/**
	 * Verifies Criteo inapp call response status code
	 * @param excelName
	 * @param sheetName
	 * @param responseParameter
	 * @param responseValue
	 * @throws Exception
	 */
	public static void verifyCriteo_inapp_v2_Call_ReponseStatusCode(String excelName, String sheetName,
			String responseParameter, String responseValue) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String host = ReadExcelValues.data[2][Cap];
		String path = ReadExcelValues.data[3][Cap];
		boolean flag = verifyAPICallWithHostandPath(host, path);

		if (flag) {
			System.out.println(host + path + " call is present in Charles session");
			logStep(host + path + " call is present in Charles session");
			System.out.println(host + path + " :API Call Verification is successful");
			logStep(host + path + " :API Call Verification is successful");
			boolean resflag = verifyAPICallResponseHeaderParameter(host, path, responseParameter, responseValue);
			if (resflag) {
				System.out.println(host + path + " call response contains response code : " + responseValue);
				logStep(host + path + " call response contains response code : " + responseValue);
				System.out.println(host + path + " :API Call response code validation is successful");
				logStep(host + path + " :API Call response code validation is successful");
			} else {
				System.out.println(host + path + " call response not contains response code : " + responseValue);
				logStep(host + path + " call response not contains response code : " + responseValue);
				System.out.println(host + path + " :API Call response code validation is failed");
				logStep(host + path + " :API Call response code validation is failed");
				Assert.fail(host + path + " :API Call response code validation is failed");
			}
		} else {
			System.out.println(host + path + " call is not present in Charles session");
			logStep(host + path + " call is not present in Charles session");
			System.out.println(host + path + " :API Call response code validation is failed");
			logStep(host + path + " :API Call response code validation is failed");
			Assert.fail(
					host + path + " call is not present in Charles session, hence response code validation is failed");

		}
	}

	/**
	 * Verifies Criteo config app call response status code
	 * @param excelName
	 * @param sheetName
	 * @param responseParameter
	 * @param responseValue
	 * @throws Exception
	 */
	public static void verifyCriteo_config_app_Call_ReponseStatusCode(String excelName, String sheetName,
			String responseParameter, String responseValue) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String host = ReadExcelValues.data[2][Cap];
		String path = ReadExcelValues.data[4][Cap];
		boolean flag = verifyAPICallWithHostandPath(host, path);

		if (flag) {
			System.out.println(host + path + " call is present in Charles session");
			logStep(host + path + " call is present in Charles session");
			System.out.println(host + path + " :API Call Verification is successful");
			logStep(host + path + " :API Call Verification is successful");
			boolean resflag = verifyAPICallResponseHeaderParameter(host, path, responseParameter, responseValue);
			if (resflag) {
				System.out.println(host + path + " call response contains response code : " + responseValue);
				logStep(host + path + " call response contains response code : " + responseValue);
				System.out.println(host + path + " :API Call response code validation is successful");
				logStep(host + path + " :API Call response code validation is successful");
			} else {
				System.out.println(host + path + " call response not contains response code : " + responseValue);
				logStep(host + path + " call response not contains response code : " + responseValue);
				System.out.println(host + path + " :API Call response code validation is failed");
				logStep(host + path + " :API Call response code validation is failed");
				Assert.fail(host + path + " :API Call response code validation is failed");
			}
		} else {
			System.out.println(host + path + " call is not present in Charles session");
			logStep(host + path + " call is not present in Charles session");
			System.out.println(host + path + " :API Call response code validation is failed");
			logStep(host + path + " :API Call response code validation is failed");
			Assert.fail(
					host + path + " call is not present in Charles session, hence response code validation is failed");

		}
	}

	/**
	 * Get param value from API response
	 * @param host
	 * @param path
	 * @param cust_param
	 * @return
	 * @throws Exception
	 */
	public static String get_param_value_from_APIResponse(String host, String path, String cust_param)
			throws Exception {
		// readExcelValues.excelValues(excelName, sheetName);
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@host";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);

		// Getting custom_params amzn_b values
		List<String> customParamsList = new ArrayList<String>();

		// String iuId = null;

		boolean iuExists = false;
		for (String qry : getQueryList) {
			if (qry.contains(host)) {
				iuExists = true;
				break;
			}
		}
		boolean hflag = false;
		boolean pflag = false;
		boolean resflag = false;
		boolean flag = false;
		String ApiParamValue = null;

		if (iuExists) {
			System.out.println(host + "  call is present");
			logStep(host + "  call is present");
			outerloop: for (int p = 0; p < nodeList.getLength(); p++) {
				// System.out.println("Total transactions: "+nodeList.getLength());
				if (nodeList.item(p) instanceof Node) {
					Node node = nodeList.item(p);
					if (node.hasChildNodes()) {
						NodeList nl = node.getChildNodes();
						for (int j = 0; j < nl.getLength(); j++) {
							// System.out.println("node1 length is: "+nl.getLength());
							Node innernode = nl.item(j);
							if (innernode != null) {
								// System.out.println("Innernode name is: "+innernode.getNodeName());
								if (innernode.getNodeName().equals("request")) {
									if (innernode.hasChildNodes()) {
										NodeList n2 = innernode.getChildNodes();
										for (int k = 0; k < n2.getLength(); k++) {
											// System.out.println("node2 length is: "+n2.getLength());
											Node innernode2 = n2.item(k);
											if (innernode2 != null) {
												// System.out.println("Innernode2 name is: "+innernode2.getNodeName());
												if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
													Element eElement = (Element) innernode2;
													// System.out.println("Innernode2 element name is:
													// "+eElement.getNodeName());
													if (eElement.getNodeName().equals("headers")) {
														if (innernode2.hasChildNodes()) {
															NodeList n3 = innernode2.getChildNodes();
															for (int q = 0; q < n3.getLength(); q++) {
																// System.out.println("node3 length is:
																// "+n3.getLength());
																Node innernode3 = n3.item(q);
																if (innernode3 != null) {
																	// System.out.println("Innernode3 name is:
																	// "+innernode3.getNodeName());
																	if (innernode3.getNodeType() == Node.ELEMENT_NODE) {
																		Element eElement1 = (Element) innernode3;
																		// System.out.println("Innernode3 element name
																		// is: "+eElement1.getNodeName());
																		if (eElement1.getNodeName().equals("header")) {
																			String content = eElement1.getTextContent();
																			// System.out.println("request body
																			// "+content);

																			if (content.contains(host)) {
																				hflag = true;
																				// System.out.println("request body
																				// found "
																				// + content);

																			} else if (content.contains(path)) {
																				pflag = true;
																				// System.out.println("request body
																				// found "
																				// + content);
																			}
																		}
																		// this condition especially for android since
																		// its file has path value under first-line
																		// element
																		if (eElement1.getNodeName()
																				.equals("first-line")) {
																			String content = eElement1.getTextContent();
																			// System.out.println("request body
																			// "+content);

																			if (content.contains(path)) {
																				pflag = true;
																				// System.out.println("request body
																				// found "
																				// + content);
																			}
																		}
																	}
																}
															}
														}
													}
													if (hflag && pflag) {
														/*
														 * if (eElement.getNodeName().equals("body")) { String scontent
														 * = eElement.getTextContent(); if
														 * (scontent.contains(cust_param)) { //
														 * System.out.println("request body " + scontent); ApiParamValue
														 * = get_Param_Value_inJsonBody(scontent, cust_param); break
														 * outerloop;
														 * 
														 * }
														 * 
														 * }
														 */
														flag = true;
													}

												}
											}
										}
									}
								}
								if (flag) {
									// System.out.println("Exiting after found true "); //
									// System.out.println("checking innernode name is: " + innernode.getNodeName());
									if (innernode.getNodeName().equals("response")) {
										// System.out.println(innernode.getNodeName());
										if (innernode.hasChildNodes()) {
											NodeList n2 = innernode.getChildNodes();
											for (int k = 0; k < n2.getLength(); k++) {
												Node innernode2 = n2.item(k);
												if (innernode2 != null) {
													if (innernode2.getNodeType() == Node.ELEMENT_NODE) {
														Element eElement = (Element) innernode2;
														if (eElement.getNodeName().equals("body")) {
															String content = eElement.getTextContent();
															// System.out.println("response body "+content);
															if (content.contains(cust_param)) {
																ApiParamValue = get_Param_Value_inJsonBody(content,
																		cust_param);
																break outerloop;

															}
														}
													}
												}
											}
										}
									}

								}
								/*
								 * if (hflag && pflag) { resflag = true; break outerloop; }
								 */
							}
						}
					}
				}
				flag = false;
				hflag = false;
				pflag = false;
			}

		} else {
			System.out.println(host + " ad call is not present");
			logStep(host + " ad call is not present");

		}

		// return resflag;
		// System.out.println("Parameter value obtined from criteo request is :" +
		// ApiParamValue);
		return ApiParamValue;

	}

	/**
	 * Verifies Criteo SDK config app call response parameter
	 * @param excelName
	 * @param sheetName
	 * @param cust_param
	 * @param expected
	 * @throws Exception
	 */
	public static void validate_Criteo_SDK_config_app_call_response_parameter(String excelName, String sheetName,
			String cust_param, String expected) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String host = ReadExcelValues.data[2][Cap];
		String path = ReadExcelValues.data[4][Cap];

		boolean flag = verifyAPICallWithHostandPath(host, path);
		if (flag) {
			System.out.println(host + path + " call is present in Charles session");
			logStep(host + path + " call is present in Charles session");

			String actual = get_param_value_from_APIResponse(host, path, cust_param);

			if (actual.equalsIgnoreCase(expected)) {
				System.out.println("Custom Parameter :" + cust_param + " value: " + actual
						+ " is matched with the expected value " + expected);
				logStep("Custom Parameter :" + cust_param + " value: " + actual + " is matched with the expected value "
						+ expected);
				System.out.println("Criteo parameter: " + cust_param + " validation is successful");
				logStep("Criteo parameter: " + cust_param + " validation is successful");
			} else {
				System.out.println("Custom Parameter :" + cust_param + " value: " + actual
						+ " is not matched with the expected value " + expected);
				logStep("Custom Parameter :" + cust_param + " value: " + actual
						+ " is not matched with the expected value " + expected);
				System.out.println("Criteo parameter: " + cust_param + " validation is failed");
				logStep("Criteo parameter: " + cust_param + " validation is failed");
				Assert.fail("Custom Parameter :" + cust_param + " value: " + actual
						+ " is not matched with the expected value " + expected);
			}

		} else {
			System.out.println(host + path + " call is not present in Charles session, hence Custom Parameter: "
					+ cust_param + " validation skipped");
			logStep(host + path + " call is not present in Charles session, hence Custom Parameter: " + cust_param
					+ " validation skipped");
			System.out.println("Criteo parameter: " + cust_param + " validation is failed");
			logStep("Criteo parameter: " + cust_param + " validation is failed");
			Assert.fail(host + path + " call is not present in Charles session, hence Custom Parameter: " + cust_param
					+ " validation skipped");

		}

	}
	
	/**
	 * Verifies whether lotame call contains 'seg' parameter
	 * @param excelName
	 * @param sheetName
	 * @param expectedSegment
	 * @throws Exception
	 */
	public static void verify_Lotame_Call_Segment_Parameter(String excelName, String sheetName, String expectedSegment)
			throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String host = ReadExcelValues.data[2][Cap];
		//String path = ReadExcelValues.data[4][Cap];
		String path = expectedSegment;
		boolean flag = verifyAPICallWithHostandPath(host, path);
		if (flag) {
			System.out.println(host+ " call is present in Charles session and contains segment: "+expectedSegment.split("=")[1]);
			logStep(host+ " call is present in Charles session and contains segment: "+expectedSegment.split("=")[1]);

		} else {
			System.out.println("Either "+host +" call is not present in Charles session or "+host +" call not contains the segment "+expectedSegment.split("=")[1]);
			logStep("Either "+host +" call is not present in Charles session or "+host +" call not contains the segment "+expectedSegment.split("=")[1]);
			Assert.fail("Either "+host +" call is not present in Charles session or "+host +" call not contains the segment "+expectedSegment.split("=")[1]);
		}
		
	}
	
	public static void validate_Video_Preroll_beacon(String excelName, String sheetName,
			String cust_param, String expected) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String host = ReadExcelValues.data[2][Cap];
		String path = ReadExcelValues.data[4][Cap];
		
		// Read the content form file
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);
		
		for (String qry : getQueryList) {
			if (qry.contains(iuId)) {
	//			adCallFound = true;
	//			tempCustmParam = getCustomParamBy_iu_value(qry, cust_param);
				// if (!"".equals(tempCustmParam))
				// customParamsList.add(getCustomParamsBy_iu_value(qry));
				break;
			}

		}
		
//		/omsdk/sendmessage?adSessionId=15A42FE5-C702-4571-82BA-7EBC45950296&timestamp=1654167702023&type=complete
	//	getQueryParamValue(String qryValue, String queryParam);
		String sessionId = "";
		path = path+"adSessionId=";
		path  = path.concat(sessionId);
//		type=complete;
		boolean flag = verifyAPICallWithHostandPath(host, path);
		if (flag) {
			System.out.println(host + path + " call is present in Charles session");
			logStep(host + path + " call is present in Charles session");

			String actual = get_param_value_from_APIResponse(host, path, cust_param);

			if (actual.equalsIgnoreCase(expected)) {
				System.out.println("Custom Parameter :" + cust_param + " value: " + actual
						+ " is matched with the expected value " + expected);
				logStep("Custom Parameter :" + cust_param + " value: " + actual + " is matched with the expected value "
						+ expected);
				System.out.println("Criteo parameter: " + cust_param + " validation is successful");
				logStep("Criteo parameter: " + cust_param + " validation is successful");
			} else {
				System.out.println("Custom Parameter :" + cust_param + " value: " + actual
						+ " is not matched with the expected value " + expected);
				logStep("Custom Parameter :" + cust_param + " value: " + actual
						+ " is not matched with the expected value " + expected);
				System.out.println("Criteo parameter: " + cust_param + " validation is failed");
				logStep("Criteo parameter: " + cust_param + " validation is failed");
				Assert.fail("Custom Parameter :" + cust_param + " value: " + actual
						+ " is not matched with the expected value " + expected);
			}

		} else {
			System.out.println(host + path + " call is not present in Charles session, hence Custom Parameter: "
					+ cust_param + " validation skipped");
			logStep(host + path + " call is not present in Charles session, hence Custom Parameter: " + cust_param
					+ " validation skipped");
			System.out.println("Criteo parameter: " + cust_param + " validation is failed");
			logStep("Criteo parameter: " + cust_param + " validation is failed");
			Assert.fail(host + path + " call is not present in Charles session, hence Custom Parameter: " + cust_param
					+ " validation skipped");

		}

	}
	
	/**
	 * This method returns the placeId from api.weather.com call , which takes the input 'placeId' as cust_param
	 * @param cust_param
	 * @throws Exception
	 */
	public static void getPlaceIdFromAPICall(String cust_param)
			throws Exception {
		
		boolean adCallFound = false;

		// Read the content form file
		File fXmlFile = new File(outfile.getName());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		// dbFactory.setNamespaceAware(true);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(fXmlFile);
		// Getting the transaction element by passing xpath expression
		NodeList nodeList = doc.getElementsByTagName("transaction");
		String xpathExpression = "charles-session/transaction/@query";
		List<String> getQueryList = evaluateXPath(doc, xpathExpression);
		if (Ad instanceof AndroidDriver<?>) {
			//in Android environment, sometimes location call generates seperatey hence explicit condition added
			try {
				placeId = get_param_value_from_APICalls(cust_param+"_directlocation");
			}catch(Exception e) {
				placeId = get_param_value_from_APICalls(cust_param);
			}
		} else {
			placeId = get_param_value_from_APICalls(cust_param);
		}
		
		System.out.println("Place Id is: "+placeId);
		logStep("Place Id is: "+placeId);

		

	}
	
	public static void clickRequiredElementonSettingsapp(String name) throws Exception {
		String[] options = name.split("\\|\\|");

		try {

			List<MobileElement> searchelements = Ad1
					.findElementsByXPath("//android.widget.TextView[@resource-id=\"android:id/title\"]");
			outerloop: for (int i = 0; i < options.length; i++) {
				for (WebElement search : searchelements) {
					// System.out.println(search.getAttribute("text"));
					// System.out.println(options[i].trim());

					if (search.getAttribute("text").contains(options[i].trim())) {
						// if(search.getText().contains(name)) {
						search.click();
						Thread.sleep(5000);
						break outerloop;

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			List<MobileElement> searchelements = Ad1.findElementsByClassName("android.widget.TextView");
			outerloop: for (int i = 0; i < options.length; i++) {
				for (WebElement search : searchelements) {
					if (search.getAttribute("text").contains(options[i].trim())) {
						search.click();
						Thread.sleep(5000);
						break outerloop;

					}
				}
			}
		}
	}
	
	public  static void clickRequiredproxyOption(String proxyOption) throws Exception {
		System.out.println("Selecting Proxy option as "+proxyOption);
		logStep("Selecting Proxy option as "+proxyOption);
		List<MobileElement> searcheleme=Ad1.findElementsById("android:id/text1");
		for(WebElement search:searcheleme) {
			if(search.getText().contains(proxyOption)) {
				search.click();
				Thread.sleep(5000);
				break;
			}
		}
		
	}
	

	public  static void enterIpAddressPortNumber(String current_IPAddress,String PortNumber) throws Exception {
		
		System.out.println("Entering the current system Ip address");
		logStep("Entering the current system Ip address");
		List<MobileElement> hostname_Portnumer = null;
		try {
			hostname_Portnumer=Ad1.findElementsById("com.android.settings:id/edittext");
			hostname_Portnumer.get(0).clear();
			Thread.sleep(10000);
			hostname_Portnumer.get(0).sendKeys(current_IPAddress);
			Thread.sleep(5000);
			Ad1.hideKeyboard();
		} catch(Exception e) {
			MobileElement hostName = Ad1.findElementById("com.android.settings:id/proxy_hostname");
			TestBase.typeText(hostName, "HostName", current_IPAddress);
			Thread.sleep(5000);
			Ad1.hideKeyboard();
		}
		
		try {
			System.out.println("Entering the port number");
			logStep("Entering the port number");
			hostname_Portnumer.get(1).clear();
			hostname_Portnumer.get(1).sendKeys(PortNumber);
			Thread.sleep(5000);
			//Ad1.hideKeyboard();
		}catch(Exception e) {
			MobileElement port = Ad1.findElementById("com.android.settings:id/proxy_port");
			TestBase.typeText(port, "Port", PortNumber);
			Thread.sleep(5000);
			//Ad1.hideKeyboard();
		}
		
		
	}

	public static void settingProxyOff(String None) throws Exception{
		//clicking network and internet
		clickRequiredElementonSettingsapp("Connections || Network & internet");
		Thread.sleep(2000);
		   attachScreen(Ad1);
		clickRequiredElementonSettingsapp("Wi-Fi || Internet || Wi‑Fi");
		Thread.sleep(2000);
		 attachScreen(Ad1);
		Ad1.findElementByAccessibilityId("Settings").click();
		Thread.sleep(2000);
		 attachScreen(Ad1);
		   try {
			   Ad1.findElementByAccessibilityId("Modify").click();
			   Ad1.findElementByAccessibilityId("Drop down list Advanced Options").click();
			   
		   }catch(Exception e) {
			    try {
				   Ad1.findElementByXPath("//android.widget.TextView[@text=\"View more\"]").click();
				   //Ad1.findElementByXPath("//android.widget.TextView[@text=\"Proxy\"]");
				  // By byProxy = MobileBy.xpath("//android.widget.TextView[@text=\"Proxy\"]");
				   // Functions.genericScroll(byProxy, true, true, 320, TOLERANCE_FROM_TOP, Ad1);
				    
				    				   
			   }catch(Exception e1) {
				   clickRequiredElementonSettingsapp("Advanced");
			   }
		   }
		   		
		Thread.sleep(2000);
		 attachScreen(Ad1);
		   System.out.println("Clicking on proxy dropdown");
		   logStep("Clicking on proxy dropdown");
		   try{
			   Ad1.findElementById("com.android.settings:id/proxy_settings").click();
				
			}catch(Exception e) {
				try{
					List <MobileElement> proxtsettings= Ad1.findElementsById("com.android.settings:id/spinner");
					proxtsettings.get(1).click();
				}catch(Exception e1) {
					Ad1.findElementByXPath("//android.widget.TextView[@text=\"Proxy\"]").click();
					
				}
			}
			
		Thread.sleep(2000);
		 attachScreen(Ad1); 
	 clickRequiredproxyOption(None);
	 attachScreen(Ad1);
		//clickRequiredproxyOption("Manual");
		Thread.sleep(2000);
	//	Ad.findElementById("com.android.settings:id/proxy_hostname").clear();
		//Ad.findElementById("com.android.settings:id/proxy_hostname").sendKeys("192.168.1.15");
		//Thread.sleep(8000);
		//Ad.findElementById("com.android.settings:id/proxy_port").clear();
	//	Ad.findElementById("com.android.settings:id/proxy_port").sendKeys("8222");
		System.out.println("Clicking on save button");
		
		try{
			Ad1.findElementById("com.android.settings:id/save_button").click();
		}catch(Exception e) {
			try{
				Ad1.findElementByXPath("//android.widget.Button[@text=\"Save\"]").click();
			}catch(Exception e1) {
				System.out.println("Save button not displayed");
				logStep("Save button not displayed");
			}
		}
		Thread.sleep(2000);
		
		try {
			//Ad.closeApp();
			Ad1.terminateApp("com.android.settings");
			Ad1.quit();
			System.out.println("Closed System app ");
			logStep("Closed System app ");
		} catch (Exception e) {
			System.out.println("Failed to close System app ");
			logStep("Failed to close System app ");
		}
		}
	
	
	public static void getIpaddress() {
		Process p;
		try {
			File bashFile = new File(System.getProperty("user.dir") + "/getIpAddress.sh");
			String[] cmd = { "/bin/sh", bashFile.getName() };

			p = Runtime.getRuntime().exec(cmd);

			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String s;
			while ((s = reader.readLine()) != null) {
				System.out.println("Current IP Address of the system is : " + s);
				logStep("Current IP Address of the system is : " + s);
				current_IPAddress = s;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("There is an exception while finding current MAC IP Address");
			e.printStackTrace();
		}

	}
	
	
 	public static void settingProxyEnable(String type,String current_IPAddress,String PortNumber) throws Exception{
 		clickRequiredElementonSettingsapp("Connections || Network & internet");
		Thread.sleep(2000);
		 attachScreen(Ad1);
		clickRequiredElementonSettingsapp("Wi-Fi || Internet || Wi‑Fi");
		Thread.sleep(2000);
		 attachScreen(Ad1);
		Ad1.findElementByAccessibilityId("Settings").click();
		Thread.sleep(2000);
		 attachScreen(Ad1);
		   try {
			   Ad1.findElementByAccessibilityId("Modify").click();
			   Thread.sleep(2000);
			   try {
				   Ad1.findElementByAccessibilityId("Drop down list Advanced Options").click();   
			   } catch(Exception e) {
				   System.out.println("Advanced Option not displayed on screen, proxy might have set already hence turning off proxy");
				   logStep("Advanced Option not displayed on screen, proxy might have set already hence turning off proxy");
				   System.out.println("Clicking on proxy dropdown");
				   logStep("Clicking on proxy dropdown");
					/*List <MobileElement> proxtsettings= Ad1.findElementsById("com.android.settings:id/spinner");
					proxtsettings.get(1).click();*/
				   try{
					   Ad1.findElementById("com.android.settings:id/proxy_settings").click();
						
					}catch(Exception e1) {
						try{
							List <MobileElement> proxtsettings= Ad1.findElementsById("com.android.settings:id/spinner");
							proxtsettings.get(1).click();
						}catch(Exception e2) {
							Ad1.findElementByXPath("//android.widget.TextView[@text=\"Proxy\"]").click();
							
						}
					}
				   clickRequiredproxyOption("None");
				   try{
						Ad1.findElementById("com.android.settings:id/save_button").click();
					}catch(Exception e3) {
						Ad1.findElementByXPath("//android.widget.Button[@text=\"Save\"]").click();
					}
				   Ad1.findElementByAccessibilityId("Modify").click();
				   Thread.sleep(8000);
				   Ad1.findElementByAccessibilityId("Drop down list Advanced Options").click();   
			   }
			  
			   
		   }catch(Exception e) {
			    try {
				   Ad1.findElementByXPath("//android.widget.TextView[@text=\"View more\"]").click();
				   //Ad1.findElementByXPath("//android.widget.TextView[@text=\"Proxy\"]");
				  // By byProxy = MobileBy.xpath("//android.widget.TextView[@text=\"Proxy\"]");
				  // Functions.genericScroll(byProxy, true, true, 224, TOLERANCE_FROM_TOP, Ad1);
				   swipe_Up(Ad1);
				   
			   }catch(Exception e1) {
				  clickRequiredElementonSettingsapp("Advanced");
			   }
		   }	
		Thread.sleep(2000);
		 attachScreen(Ad1);
		   System.out.println("Clicking on proxy dropdown");
		   logStep("Clicking on proxy dropdown");
			/*List <MobileElement> proxtsettings= Ad1.findElementsById("com.android.settings:id/spinner");
			proxtsettings.get(1).click();*/
		   try{
			   Ad1.findElementById("com.android.settings:id/proxy_settings").click();
				
			}catch(Exception e) {
				try{
					List <MobileElement> proxtsettings= Ad1.findElementsById("com.android.settings:id/spinner");
					proxtsettings.get(1).click();
				}catch(Exception e1) {
					Ad1.findElementByXPath("//android.widget.TextView[@text=\"Proxy\"]").click();
					
				}
			}
			Thread.sleep(2000);
			 attachScreen(Ad1);
		 clickRequiredproxyOption(type);
		Thread.sleep(2000);
		enterIpAddressPortNumber( current_IPAddress,PortNumber); 
		try{
			Ad1.findElementById("com.android.settings:id/save_button").click();
		}catch(Exception e) {
			try{
				Ad1.findElementByXPath("//android.widget.Button[@text=\"Save\"]").click();
			}catch(Exception e1) {
				System.out.println("Save button not displayed");
				logStep("Save button not displayed");
			}
		}
		Thread.sleep(2000);
		
		try {
			//Ad.closeApp();
			Ad1.terminateApp("com.android.settings");
			Ad1.quit();
			System.out.println("Closed System app ");
			logStep("Closed System app ");
		} catch (Exception e) {
			System.out.println("Failed to close System app ");
			logStep("Failed to close System app ");
		}
		}
	 	
 	/**
	 * This method validates the Parameter of Dynata call with the expected
	 * value sent as parameter. This requires both Custom Parameter and expected
	 * value as input
	 * 
	 * @param excelName
	 * @param sheetName
	 * @param cust_param
	 * @param expected
	 * @throws Exception
	 */
	public static void validate_param_val_of_Dynata_Call(String excelName, String sheetName, String cust_param,
			String expected) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String host = ReadExcelValues.data[2][Cap];
		String path = ReadExcelValues.data[3][Cap];
		
		String tempCustmParam = null;
		
	
		tempCustmParam = getTheQueryParamValueOfGivenHostAndPath(host, path, cust_param);

		if (!tempCustmParam.isEmpty()) {
				System.out.println(cust_param + " value of from gampad call  of : " + host + " is " + tempCustmParam);
				if (expected.equalsIgnoreCase("NotNull")) {
					if (!tempCustmParam.equalsIgnoreCase("nl")) {
						System.out.println("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is matched with the expected value " + expected);
						logStep("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is matched with the expected value " + expected);
						System.out.println("Custom Parameter :" + cust_param + " validation is successful");
						logStep("Custom Parameter :" + cust_param + " validation is successful");
					} else {
						System.out.println("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is not matched with the expected value " + expected);
						logStep("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is not matched with the expected value " + expected);
						System.out.println("Custom Parameter :" + cust_param + " validation is failed");
						logStep("Custom Parameter :" + cust_param + " validation is failed");
						Assert.fail("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is not matched with the expected value " + expected);
					}
				} else {
					if (tempCustmParam.equalsIgnoreCase(expected)) {
						System.out.println("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is matched with the expected value " + expected);
						logStep("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is matched with the expected value " + expected);
						System.out.println("Custom Parameter :" + cust_param + " validation is successful");
						logStep("Custom Parameter :" + cust_param + " validation is successful");
					} else {
						System.out.println("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is not matched with the expected value " + expected);
						logStep("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is not matched with the expected value " + expected);
						System.out.println("Custom Parameter :" + cust_param + " validation is failed");
						logStep("Custom Parameter :" + cust_param + " validation is failed");
						Assert.fail("Custom Parameter :" + cust_param + " value: " + tempCustmParam
								+ " is not matched with the expected value " + expected);
					}
				}

			} else if (tempCustmParam == null || tempCustmParam.isEmpty()) {
				System.out.println(
						"Custom parameter :" + cust_param + " not found/no value in ad call, hence Custom Parameter: "
								+ cust_param + " validation skipped");
				logStep("Custom parameter :" + cust_param + " not found/no value in ad call, hence Custom Parameter: "
						+ cust_param + " validation skipped");
				System.out.println("Custom Parameter :" + cust_param + " validation is failed");
				logStep("Custom Parameter :" + cust_param + " validation is failed");
				Assert.fail(
						"Custom parameter :" + cust_param + " not found/no value in ad call, hence Custom Parameter: "
								+ cust_param + " validation skipped");
			}
		
	}

}

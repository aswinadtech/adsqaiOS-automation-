package com.twc.ios.app.functions;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.appmanagement.ApplicationState;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Step;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.util.SystemOutLogger;
//import org.apache.xerces.util.SynchronizedSymbolTable;
//import org.apache.tools.ant.types.FileList.FileName;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
//import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
//import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.LogStatus;
import com.twc.ios.app.charlesfunctions.CharlesProxy;
import com.twc.ios.app.excel.ExcelData;
import com.twc.ios.app.excel.WriteResultintoExcel;
import com.twc.ios.app.excel.Write_result;
import com.twc.ios.app.general.Driver;
import com.twc.ios.app.general.RetryAnalyzer;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.Utils;
import com.twc.ios.app.pages.AddressScreen;
import com.twc.ios.app.pages.AirQualityCardScreen;
import com.twc.ios.app.pages.AlertCenterScreen;
import com.twc.ios.app.pages.AndroidAddressScreen;
import com.twc.ios.app.pages.AndroidAirQualityCardScreen;
import com.twc.ios.app.pages.AndroidAlertCenterScreen;
import com.twc.ios.app.pages.AndroidDailyCardScreen;
import com.twc.ios.app.pages.AndroidDailyNavTab;
import com.twc.ios.app.pages.AndroidFTLScreens;
import com.twc.ios.app.pages.AndroidHomeNavTab;
import com.twc.ios.app.pages.AndroidHourlyCardScreen;
import com.twc.ios.app.pages.AndroidHourlyNavTab;
import com.twc.ios.app.pages.AndroidLifeStyleCardScreen;
import com.twc.ios.app.pages.AndroidNewsCardScreen;
import com.twc.ios.app.pages.AndroidRadarCardScreen;
import com.twc.ios.app.pages.AndroidRadarNavTab;
import com.twc.ios.app.pages.AndroidSeasonalHubCardScreen;
import com.twc.ios.app.pages.AndroidTodayCardScreen;
import com.twc.ios.app.pages.AndroidVideoCardScreen;
import com.twc.ios.app.pages.AndroidVideoNavTab;
import com.twc.ios.app.pages.CurrentConditionsCardScreen;
import com.twc.ios.app.pages.DailyCardScreen;
import com.twc.ios.app.pages.DailyNavTab;
import com.twc.ios.app.pages.FTLScreens;
import com.twc.ios.app.pages.HomeNavTab;
import com.twc.ios.app.pages.HourlyNavTab;
import com.twc.ios.app.pages.LifeStyleCardScreen;
import com.twc.ios.app.pages.NewsCardScreen;
import com.twc.ios.app.pages.RadarCardScreen;
import com.twc.ios.app.pages.RadarNavTab;
import com.twc.ios.app.pages.SeasonalHubCardScreen;
import com.twc.ios.app.pages.TodayCardScreen;
import com.twc.ios.app.pages.VideoCardScreen;
import com.twc.ios.app.pages.VideoNavTab;
import com.twc.ios.app.pages.WatsonCardScreen;
import com.twc.ios.app.general.ReadExcelValues;

public class Functions extends Driver {

	public static DesiredCapabilities capabilities = new DesiredCapabilities();
	 /** Maximum wait time for dynamic waits */
    public static final int maxTimeout = 60;
	public static int Cap = 1;
	public static String BBcallName = null;
	public static String UserStatus = null;
	public static List<MobileElement> Addresseslist = null;
	public static MobileElement TempEle = null;
	public static MobileElement Settings = null;
	public static MobileElement AdEle = null;
	public static String Adsizes = null;
	public static MobileElement SelectAddress = null;
	public static StringBuffer sb = new StringBuffer("");
	public static String req = null;
	public static String pubreq = null;
	public static int Feed_Value = 0;
	public static int index;
	public static String pubreq1 = null;
	public static String ModuleName = null;
	public static String excelPage = null;
	// public static String ModuleName_bn=null;
	public static String BN_ModuleName = null;
	public static MobileElement module;
	public static String Hardcode = null;
	public static String adreq1 = null;
	public static List<String> container = null;
	public static String SecondParamValue = null;
	public static String firstParamValue = null;
	public static String AdParams = null;
	public static String zipCode = null;
	public static String[] splitPubvalues = null;
	public static String[] wfxcontainer = null;
	public static String pubadcal = null;
	public static String adcal = null;
	public static String HurricaneXpath = null;
	public static String prerolladxpath = null;
	public static String params_val = null;
	public static String fgeoparams_val = null;
	public static String faudparams_val = null;
	public static int startX;
	public static int endX;
	public static int startY;
	public static int endY;
	public static String Hardcoded = null;
	public static String seg = null;
	public static String Xpth = null;
	public static String VerifypubadValues = null;
	// public static ArrayList<String> firstParamValue = new ArrayList<String>();
	public static ArrayList<String> pubads = new ArrayList<String>();
	public static ArrayList<String> AdzoneList = new ArrayList<String>();
	public static ArrayList<String> ads = new ArrayList<String>();
	public static ArrayList<String> pubadvalues = new ArrayList<String>();
	public static ArrayList<String> advalues = new ArrayList<String>();
	public static ArrayList<String> pubvalues = new ArrayList<String>();
	public static ArrayList<String> fgeolist = new ArrayList<String>();
	public static ArrayList<String> faudlist = new ArrayList<String>();
	public static ArrayList<String> cxtgcontainer = new ArrayList<String>();
	public static ArrayList<String> nzcscontainer = new ArrayList<String>();
	public static ArrayList<String> hzcscontainer = new ArrayList<String>();
	public static ArrayList<String> zcscontainer = new ArrayList<String>();
	public static ArrayList<String> wfxtgcontainer = new ArrayList<String>();
	public static ArrayList<String> firstList = new ArrayList<String>();
	public static List<MobileElement> typeCell = null;
	public static ArrayList<String> aaxbvalues = new ArrayList<String>();
	public static ArrayList<String> gampadbvalues = new ArrayList<String>();
	public static String ipaPath = null;
	public static File folder = null;
	public static String buildText = null;
	public static String BuildName = null;
	public static String old_Build = null;
	public static String new_build = null;
	public static String TestName = null;
	public static String Exception = null;
	public static String testStatus = null;
	public static int testcode = 1;
	// public static SoftAssert sa = new SoftAssert();
	public static String ScreenShot = System.getProperty("user.dir") + "/screenshots";

	public static String BuildNo = null;

	public static String homescreenbval = null;
	public static String radarbval = null;
	public static String newsbval = null;
	public static String dailybval = null;
	public static String aqbval = null;
	public static String dailydetailsbval = null;
	public static String hourlydetailsbval = null;
	public static String seasonalbval = null;
	public static String aqdetailsbval = null;
	public static String newsdetailsbval = null;
	public static String mapdetailsbval = null;
	public static boolean nextGenIMadDisplayed = false;
	public static boolean interStitialChecked = false;
	public static boolean interStitialDisplayed = false;
	public static boolean interStitialAdcallSuccessful = false;
	public static boolean unlimitedInterstitial = false;
	public static boolean planningCardAdChecked = false;
	public static int feedAdCount = 0;
	public static MobileElement globalcurrentCard = null;
	public static MobileElement globalprevCard = null;
	public static int gapY = 10;
	public static int relativeY = 80;
	public static boolean isnextgenimcard = false;
	public static String appVersion = null;
	public static String dailyDetailsDayOfWeek = null;
	public static String dailyDetailsDateOfDay = null;
	public static String dailyDetailsMonthOfDate = null;
	public static String videoIUValue = null;
	public static String iuId = null;
	public static long interstitialFqCapStrtTime = 0L;
	public static boolean rainCardDisplayed = false;
	
	private static final double DEFAULT_PAGE_SCROLL_AMOUNT = 0.74;
	private static final double DEFAULT_SCROLL_DOWN_STARTING_POINT = 0.75;
	private static final double DEFAULT_SCROLL_UP_STARTING_POINT = 0.25;
	private static final double DEFAULT_TOLERANCE_FROM_TOP = 0.03;
	protected static double TOLERANCE_FROM_TOP = 0.03;
	public static int genericVariance = 0;
	public static long duration = 900;
	public static String firstCard = null;
	public static String secondCard = null;
	public static String thirdCard = null;
	
	
	
	/**
	 * Appium Start
	 * @throws Exception
	 */
	public static void startAppiumServer() throws Exception {
		ReadExcelValues.excelValues("Smoke", "Appium");
		CommandLine command = new CommandLine(ReadExcelValues.data[1][Cap]);
		command.addArgument(ReadExcelValues.data[2][Cap], false);
		command.addArgument("--address", false);
		command.addArgument(ReadExcelValues.data[3][Cap]);
		command.addArgument("--port", false);
		command.addArgument(ReadExcelValues.data[4][Cap]);
		command.addArgument("--no-reset", true);
		command.addArgument("--log-level", true);
		command.addArgument("error");
		// command.addArgument("--log");
		// command.addArgument("/Users/aparna/Documents/sys11.log");

		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		DefaultExecutor executor = new DefaultExecutor();
		executor.setExitValue(1);
		executor.execute(command, resultHandler);
		TestBase.waitForMilliSeconds(20000);

	}

	/**
	 *  Stop Appium Server
	 * @throws IOException
	 * @throws Exception
	 */
	public static void stopAppiumServer() throws IOException, Exception {
		String[] command = { "/usr/bin/killall", "-KILL", "node" };
		// String[] command = new String[] { "sh", "-c", "lsof -P | grep ':4733' | awk
		// '{print $2}' | xargs kill -9" };
		Runtime.getRuntime().exec(command);
		// System.out.println("Appium server stop");
		TestBase.waitForMilliSeconds(10000);
	}

	/**
	 * Appium Autostart
	 * @throws IOException
	 * @throws Exception
	 */
	public static void Appium_Autostart() throws IOException, Exception {
		// Auto start Appium
		//Start_Stop_AppiumServer appiumStart = new Start_Stop_AppiumServer();
		System.out.println("Stopping the appium server");
		stopAppiumServer();
		System.out.println("Appium server is stopped");
		TestBase.waitForMilliSeconds(10000);
		System.out.println("Starting the appium server");
		startAppiumServer();
		System.out.println("Appium server is started and running");
	}

	/**
	 *  Decide connected device
	 * @throws Exception
	 */
	public static void capabilities() throws Exception {
		// Read Device Platform
		ReadExcelValues.excelValues("Smoke", "Device");

		if (ReadExcelValues.data[1][1].equals("Android")) {
			Cap = Cap + 1;
		} else {
			Cap = Cap;
		}

	}

	/**
	 * Scroll Down
	 * @throws InterruptedException
	 */
	public static void scrolldown() throws InterruptedException {
		// TestBase.waitForMilliSeconds(1000);
		// Scroll JS
		JavascriptExecutor js = (JavascriptExecutor) Ad;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "down");
		js.executeScript("mobile: scroll", scrollObject);

	}

	/**
	 * Swipe 
	 */
	public static void Swipe() {
		Dimension dimensions = Ad.manage().window().getSize();
		Double startY1 = dimensions.getHeight() * 0.7;
		startY = startY1.intValue();
		Double endY1 = (double) (dimensions.getHeight() / 40); // dimensions.getHeight() 0.2; == 512.0
		endY = endY1.intValue();
		//Ad.swipe(0, startY, 0, endY, 2000);
	}

	/**
	 * App Kill Relaunch
	 * @throws Exception
	 */
	public static void Kill_relaunch() throws Exception {

		// Close the app
		//Ad.closeApp();
		Ad.terminateApp("com.weather.TWC");
		TestBase.waitForMilliSeconds(2000);
		System.out.println("App closed successfully");

		// Relaunch the app
		Ad.launchApp();
		//Ad.activateApp("com.weather.TWC");
		System.out.println("App launched successfully");
		TestBase.waitForMilliSeconds(2000);

	}

	/**
	 * clear retrycount
	 */
	public static void retryclear() {

		if (RetryAnalyzer.count == RetryAnalyzer.maxCount) {
			System.out.println("RetryCount reached MaxCount");
			// RetryAnalyzer.count=0;
			if (testStatus.equalsIgnoreCase("Passed")) {
				logStep("Test case passed and retry count reached max");
				System.out.println("Test case passed and retry count reached max");
				RetryAnalyzer.count = 0;
				throw new SkipException("Test case passed and retry count reached max");
			} else {
				logStep(Exception);
				Assert.fail(Exception);
				// break;
			}
		}
	}

	/**
	 * Launch the TWC App
	 * @param ResetType
	 * @throws Exception
	 */
	public static void launchtheApp(String ResetType) throws Exception {
		FTLScreens ftlScreens;
		AddressScreen addressScreen;
		ReadExcelValues.excelValues("Smoke", "Capabilities");
		// service = AppiumDriverLocalService.buildService(new
		// AppiumServiceBuilder().withAppiumJS(new
		// File("/usr/local/lib/node_modules/appium/build/lib/main.js")).withIPAddress("0.0.0.0").usingPort(port));
		DesiredCapabilities capabilities = new DesiredCapabilities();

		// Capabilities for IOS and Android Based on Selected on Device Selection
		capabilities.setCapability(ReadExcelValues.data[1][0], ReadExcelValues.data[1][Cap]);
		capabilities.setCapability(ReadExcelValues.data[2][0], ReadExcelValues.data[2][Cap]);
		//capabilities.setCapability(ReadExcelValues.data[3][0], ReadExcelValues.data[3][Cap]);
		capabilities.setCapability(ReadExcelValues.data[3][0], Utils.iOSVersion);
		capabilities.setCapability(ReadExcelValues.data[5][0], ReadExcelValues.data[5][Cap]);
		capabilities.setCapability(ReadExcelValues.data[6][0], ReadExcelValues.data[6][Cap]);
		capabilities.setCapability("automationName", "XCUITest");
		//capabilities.setCapability(ReadExcelValues.data[7][0], "=" + ReadExcelValues.data[7][Cap]);
		//capabilities.setCapability(ReadExcelValues.data[7][0], "=" + "iPhone "+"("+ReadExcelValues.data[3][Cap]+") "+"["+Utils.iPhoneUDID+"]");
		capabilities.setCapability(ReadExcelValues.data[7][0], "=" + "iPhone "+"("+Utils.iOSVersion+") "+"["+Utils.iPhoneUDID+"]");
		//capabilities.setCapability(ReadExcelValues.data[11][0], ReadExcelValues.data[11][Cap]);
		capabilities.setCapability(ReadExcelValues.data[11][0], Utils.iPhoneUDID);
		// capabilities.setCapability(readExcelValues.data[7][0],"iPhone");
		capabilities.setCapability(ReadExcelValues.data[8][0], ReadExcelValues.data[8][Cap]);
		capabilities.setCapability("noReset", ResetType);
		// ipaPath="/Users/apple/Downloads/ads-ios-master/Build/v9.1-int-423464.ipa";
		// capabilities.setCapability(readExcelValues.data[10][0],
		// readExcelValues.data[10][Cap]);
		// capabilities.setCapability("app","/Users/narasimhanukala/git/ads-automation/ios_Smoke_Automation/Build/iPhone_-_Flagship.ipa");
		capabilities.setCapability(ReadExcelValues.data[12][0], ReadExcelValues.data[12][Cap]);
		capabilities.setCapability(ReadExcelValues.data[13][0], "7200");
		capabilities.setCapability(ReadExcelValues.data[14][0], true);
		// capabilities.setCapability(readExcelValues.data[16][0],
		// readExcelValues.data[16][Cap]);
		capabilities.setCapability("launchTimeout", 60000);
		capabilities.setCapability("useNewWDA", true);
		// capabilities.setCapability("--session-override",true);
		capabilities.setCapability("bundleId", "com.weather.TWC");
		capabilities.setCapability("xcodeConfigfile",
				"/Applications/Appium.app/Contents/Resources/app/node_modules/appium/node_modules/appium-xcuitest-driver/WebDriverAgent/Config.xcconfig");
		// capabilities.setCapability("xcodeSigningId","iPhone Developer");
		// capabilities.setCapability("locationServicesEnabled", false);
		capabilities.setCapability("realDeviceLogger", "/Users/apple/node_modules/deviceconsole");
		capabilities.setCapability("wdaLocalPort", "7403");
		// capabilities.setCapability("locationServicesAuthorized", true);
		// capabilities.setCapability("waitForAppScript","$.delay(5000); true");
		capabilities.setCapability("clearSystemFiles", true);
		System.out.println("Reading capabilities done");
		// Wait time for Execution of node.js
		// TestBase.waitForMilliSeconds(10000);
		Ad = new IOSDriver(new URL("http://127.0.0.1:4733/wd/hub"), capabilities);
		// Ad= new IOSDriver<MobileElement>(service, capabilities);
		Ad.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		System.out.println("TWC App Launched");
		// Handle Extra popup appears when app launched (like New module ebnable)
		ftlScreens = new FTLScreens(Ad);
		ftlScreens.handle_Unwanted_Popups();
		// Functions.enternewAddress("New York City, New York");
		addressScreen = new AddressScreen(Ad);
		addressScreen.enternewAddress(true, "Atlanta, Georgia");
		// Functions.enternewAddress("Woodbridge Township, New Jersey");

		// Functions.enternewAddress("Bridgeton, New Jersey");
		// //Ad.tap(1, 10, 6, 2000);
		// Functions.BacktoWeather();
		// Functions.Take//ScreenShot();

		// Write_result wrResult1 = new Write_result();
		// wrResult1.WriteResult("Capabilities", ipaPath.toString(), 14, Cap);
	}
	
	/**
	 * Launch the Airbnb App
	 * @throws Exception
	 */
	public static void launchtheAirbnbApp() throws Exception {
		
		DesiredCapabilities capabilities = new DesiredCapabilities();

		// Capabilities for IOS and Android Based on Selected on Device Selection
		capabilities.setCapability("deviceName", "iPhone");
		capabilities.setCapability("udid", "00008030-001825112238802E");
		capabilities.setCapability("platformName", "iOS");
		capabilities.setCapability("platformVersion", "15.4.1");
		capabilities.setCapability("bundleId", "com.airbnb.app");
		capabilities.setCapability("automationName", "XCUITest");
		capabilities.setCapability("noReset", true);
		capabilities.setCapability("autoLaunch", true);
		capabilities.setCapability("launchTimeout", 60000);
		//capabilities.setCapability("useNewWDA", true);
		capabilities.setCapability("wdaLocalPort", "7403");
		capabilities.setCapability("clearSystemFiles", true);
		System.out.println("Reading capabilities done");
		// Wait time for Execution of node.js
		// TestBase.waitForMilliSeconds(10000);
		Ad = new IOSDriver(new URL("http://127.0.0.1:4733/wd/hub"), capabilities);
		// Ad= new IOSDriver<MobileElement>(service, capabilities);
		Ad.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		System.out.println("Airbnb App Launched");
		
	}
	
	/**
	 * Launch the Android App
	 * @throws Exception
	 */
	public static void launchtheAndroidApp() throws Exception {
		AndroidFTLScreens ftlScreens;
		
		ReadExcelValues.excelValues("Smoke", "Capabilities");
		
		DesiredCapabilities capabilities = new DesiredCapabilities();

		// Capabilities for IOS and Android Based on Selected on Device Selection
		/*capabilities.setCapability("deviceName", "Galaxy S21 5G");
		capabilities.setCapability("udid", "R5CR11NNFQW");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("platformVersion", "12");
		capabilities.setCapability("appPackage", "com.weather.Weather");
		capabilities.setCapability("appActivity", "com.weather.Weather.app.SplashScreenActivity");
		capabilities.setCapability("automationName", "UiAutomator2");
		capabilities.setCapability("noReset", true);
		capabilities.setCapability("autoLaunch", true);*/
		
		capabilities.setCapability(ReadExcelValues.data[2][0], ReadExcelValues.data[2][2]);
		capabilities.setCapability(ReadExcelValues.data[3][0], ReadExcelValues.data[3][2]);
		capabilities.setCapability(ReadExcelValues.data[7][0], ReadExcelValues.data[7][2]);
		capabilities.setCapability(ReadExcelValues.data[11][0], ReadExcelValues.data[11][2]);
		capabilities.setCapability(ReadExcelValues.data[12][0], ReadExcelValues.data[12][2]);
		capabilities.setCapability("appActivity", "com.weather.Weather.app.SplashScreenActivity");
		capabilities.setCapability("automationName", "UiAutomator2");
		capabilities.setCapability("noReset", true);
		capabilities.setCapability(ReadExcelValues.data[13][0], "7200");
		capabilities.setCapability(ReadExcelValues.data[14][0], true);
		capabilities.setCapability("launchTimeout", 240000);
		//capabilities.setCapability("useNewWDA", true);
		capabilities.setCapability("wdaLocalPort", "7403");
		capabilities.setCapability("clearSystemFiles", true);
		System.out.println("Reading capabilities done");
		// Wait time for Execution of node.js
		// TestBase.waitForMilliSeconds(10000);
		try {
			Ad = new AndroidDriver(new URL("http://127.0.0.1:4733/wd/hub"), capabilities);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Ad= new IOSDriver<MobileElement>(service, capabilities);
		Ad.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		System.out.println("TWC Android App Launched");
		ftlScreens = new AndroidFTLScreens(Ad);
		/*ftlScreens.clickONTerms();
		attachScreen();
		ftlScreens.clickONNext();
		attachScreen();
		//scrollforclickngIunderstand();
		ftlScreens.ClickonIUnderstand();
		attachScreen();
		ftlScreens.clickOnAllow();
		attachScreen();
		ftlScreens.clickOnWhileUsingTheApp();
		attachScreen();
		ftlScreens.Clickonclosebutton();
		attachScreen();*/
		ftlScreens.handle_Unwanted_Popups();
		Thread.sleep(10000);
		//System.out.println("Capabilities have been launched  with fullreset ");
	}
	
	public void clickOnSettingIconAndroid() throws Exception {
		try {
			System.out.println("Clicking on Setting Icon");
			logStep("Clicking on Setting Icon");
			Ad.findElementByAccessibilityId("Setting icon").click();
			Thread.sleep(10000);
			if (TestBase.isElementExists(MobileBy.AccessibilityId("Go to Alerts and Notifications"))) {
				Ad.findElementByAccessibilityId("Setting icon").click();
			}
		} catch (Exception e) {
			Ad.findElementById("com.weather.Weather:id/profile_avatar").click();
			Thread.sleep(10000);
		}

	}
	
	/**
	 * Use this method when Launch the TWC App For China, as the app gets closed automatically after launch, no need to verify popups
	 * @param ResetType
	 * @throws Exception
	 */
	public static void launchtheAppForChinaORRussiaORBelarus(String ResetType) throws Exception {
		FTLScreens ftlScreens;
		AddressScreen addressScreen;
		ReadExcelValues.excelValues("Smoke", "Capabilities");
		// service = AppiumDriverLocalService.buildService(new
		// AppiumServiceBuilder().withAppiumJS(new
		// File("/usr/local/lib/node_modules/appium/build/lib/main.js")).withIPAddress("0.0.0.0").usingPort(port));
		DesiredCapabilities capabilities = new DesiredCapabilities();

		// Capabilities for IOS and Android Based on Selected on Device Selection
		capabilities.setCapability(ReadExcelValues.data[1][0], ReadExcelValues.data[1][Cap]);
		capabilities.setCapability(ReadExcelValues.data[2][0], ReadExcelValues.data[2][Cap]);
		//capabilities.setCapability(ReadExcelValues.data[3][0], ReadExcelValues.data[3][Cap]);
		capabilities.setCapability(ReadExcelValues.data[3][0], Utils.iOSVersion);
		capabilities.setCapability(ReadExcelValues.data[5][0], ReadExcelValues.data[5][Cap]);
		capabilities.setCapability(ReadExcelValues.data[6][0], ReadExcelValues.data[6][Cap]);
		capabilities.setCapability("automationName", "XCUITest");
		//capabilities.setCapability(ReadExcelValues.data[7][0], "=" + ReadExcelValues.data[7][Cap]);
		// capabilities.setCapability(readExcelValues.data[7][0],"iPhone");
		//capabilities.setCapability(ReadExcelValues.data[7][0], "=" + ReadExcelValues.data[7][Cap]);
		capabilities.setCapability(ReadExcelValues.data[7][0], "=" + "iPhone "+"("+Utils.iOSVersion+") "+"["+Utils.iPhoneUDID+"]");
		//capabilities.setCapability(ReadExcelValues.data[11][0], ReadExcelValues.data[11][Cap]);
		capabilities.setCapability(ReadExcelValues.data[11][0], Utils.iPhoneUDID);
		capabilities.setCapability(ReadExcelValues.data[8][0], ReadExcelValues.data[8][Cap]);
		capabilities.setCapability("noReset", ResetType);
		// ipaPath="/Users/apple/Downloads/ads-ios-master/Build/v9.1-int-423464.ipa";
		// capabilities.setCapability(readExcelValues.data[10][0],
		// readExcelValues.data[10][Cap]);
		// capabilities.setCapability("app","/Users/narasimhanukala/git/ads-automation/ios_Smoke_Automation/Build/iPhone_-_Flagship.ipa");
		capabilities.setCapability(ReadExcelValues.data[12][0], ReadExcelValues.data[12][Cap]);
		capabilities.setCapability(ReadExcelValues.data[13][0], "7200");
		capabilities.setCapability(ReadExcelValues.data[14][0], true);
		// capabilities.setCapability(readExcelValues.data[16][0],
		// readExcelValues.data[16][Cap]);
		//capabilities.setCapability(ReadExcelValues.data[11][0], ReadExcelValues.data[11][Cap]);
		capabilities.setCapability("launchTimeout", 60000);
		capabilities.setCapability("useNewWDA", true);
		// capabilities.setCapability("--session-override",true);
		capabilities.setCapability("bundleId", "com.weather.TWC");
		capabilities.setCapability("xcodeConfigfile",
				"/Applications/Appium.app/Contents/Resources/app/node_modules/appium/node_modules/appium-xcuitest-driver/WebDriverAgent/Config.xcconfig");
		// capabilities.setCapability("xcodeSigningId","iPhone Developer");
		// capabilities.setCapability("locationServicesEnabled", false);
		capabilities.setCapability("realDeviceLogger", "/Users/apple/node_modules/deviceconsole");
		capabilities.setCapability("wdaLocalPort", "7403");
		// capabilities.setCapability("locationServicesAuthorized", true);
		// capabilities.setCapability("waitForAppScript","$.delay(5000); true");
		capabilities.setCapability("clearSystemFiles", true);
		System.out.println("Reading capabilities done");
		// Wait time for Execution of node.js
		// TestBase.waitForMilliSeconds(10000);
		Ad = new IOSDriver(new URL("http://127.0.0.1:4733/wd/hub"), capabilities);
		// Ad= new IOSDriver<MobileElement>(service, capabilities);
		Ad.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		System.out.println("TWC App Launched for China/Russia/Belarus");
		// Handle Extra popup appears when app launched (like New module ebnable)
		ftlScreens = new FTLScreens(Ad);
		ftlScreens.handle_Unwanted_Popups_China();
		// Functions.enternewAddress("New York City, New York");
		//addressScreen = new AddressScreen(Ad);
		//addressScreen.enternewAddress(true, "Atlanta, Georgia");
		// Functions.enternewAddress("Woodbridge Township, New Jersey");

		// Functions.enternewAddress("Bridgeton, New Jersey");
		// //Ad.tap(1, 10, 6, 2000);
		// Functions.BacktoWeather();
		// Functions.Take//ScreenShot();

		// Write_result wrResult1 = new Write_result();
		// wrResult1.WriteResult("Capabilities", ipaPath.toString(), 14, Cap);
	}
	/**
	 * Use this method when Launch the TWC App For China, as the app gets closed automatically after launch, no need to verify popups
	 * @param ResetType
	 * @throws Exception
	 */
	public static void launchtheAppForChinaORRussiaORBelarusAndroid(String ResetType) throws Exception {
		AndroidFTLScreens ftlScreens;
		AndroidAddressScreen addressScreen;
		ReadExcelValues.excelValues("Smoke", "Capabilities");
		// service = AppiumDriverLocalService.buildService(new
		// AppiumServiceBuilder().withAppiumJS(new
		// File("/usr/local/lib/node_modules/appium/build/lib/main.js")).withIPAddress("0.0.0.0").usingPort(port));
		DesiredCapabilities capabilities = new DesiredCapabilities();

		// Capabilities for IOS and Android Based on Selected on Device Selection
		capabilities.setCapability(ReadExcelValues.data[2][0], ReadExcelValues.data[2][2]);
		capabilities.setCapability(ReadExcelValues.data[3][0], ReadExcelValues.data[3][2]);
		capabilities.setCapability(ReadExcelValues.data[7][0], ReadExcelValues.data[7][2]);
		capabilities.setCapability(ReadExcelValues.data[11][0], ReadExcelValues.data[11][2]);
		capabilities.setCapability(ReadExcelValues.data[12][0], ReadExcelValues.data[12][2]);
		capabilities.setCapability("appActivity", "com.weather.Weather.app.SplashScreenActivity");
		capabilities.setCapability("automationName", "UiAutomator2");
		capabilities.setCapability("noReset", true);
		capabilities.setCapability(ReadExcelValues.data[13][0], "7200");
		capabilities.setCapability(ReadExcelValues.data[14][0], true);
		// ipaPath="/Users/apple/Downloads/ads-ios-master/Build/v9.1-int-423464.ipa";
		// capabilities.setCapability(readExcelValues.data[10][0],
		// readExcelValues.data[10][Cap]);
		capabilities.setCapability("launchTimeout", 60000);
		capabilities.setCapability("useNewWDA", true);
		// capabilities.setCapability("--session-override",true);
		// capabilities.setCapability("xcodeSigningId","iPhone Developer");
		// capabilities.setCapability("locationServicesEnabled", false);
		capabilities.setCapability("wdaLocalPort", "7403");
		// capabilities.setCapability("locationServicesAuthorized", true);
		// capabilities.setCapability("waitForAppScript","$.delay(5000); true");
		capabilities.setCapability("clearSystemFiles", true);
		System.out.println("Reading capabilities done");
		// Wait time for Execution of node.js
		// TestBase.waitForMilliSeconds(10000);
		Ad = new AndroidDriver(new URL("http://127.0.0.1:4733/wd/hub"), capabilities);
		// Ad= new IOSDriver<MobileElement>(service, capabilities);
		Ad.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		System.out.println("TWC App Launched for China/Russia/Belarus");
		// Handle Extra popup appears when app launched (like New module ebnable)
		ftlScreens = new AndroidFTLScreens(Ad);
		ftlScreens.handle_Unwanted_Popups_China();
		// Functions.enternewAddress("New York City, New York");
		//addressScreen = new AddressScreen(Ad);
		//addressScreen.enternewAddress(true, "Atlanta, Georgia");
		// Functions.enternewAddress("Woodbridge Township, New Jersey");

		// Functions.enternewAddress("Bridgeton, New Jersey");
		// //Ad.tap(1, 10, 6, 2000);
		// Functions.BacktoWeather();
		// Functions.Take//ScreenShot();

		// Write_result wrResult1 = new Write_result();
		// wrResult1.WriteResult("Capabilities", ipaPath.toString(), 14, Cap);
	}

	/**
	 * Launch the TWC App with localization settings
	 * @param ResetType
	 * @param region
	 * @param includeRegion
	 * @param language
	 * @param includelanguage
	 * @throws Exception
	 */
	public static void launchtheApp_forLocalization(String ResetType, String region, boolean includeRegion,
			String language, boolean includelanguage) throws Exception {
		FTLScreens ftlScreens;
		AddressScreen addressScreen;
		ReadExcelValues.excelValues("Smoke", "Capabilities");

		// service = AppiumDriverLocalService.buildService(new
		// AppiumServiceBuilder().withAppiumJS(new
		// File("/usr/local/lib/node_modules/appium/build/lib/main.js")).withIPAddress("0.0.0.0").usingPort(port));
		DesiredCapabilities capabilities = new DesiredCapabilities();

		// Capabilities for IOS and Android Based on Selected on Device Selection
		capabilities.setCapability(ReadExcelValues.data[1][0], ReadExcelValues.data[1][Cap]);
		capabilities.setCapability(ReadExcelValues.data[2][0], ReadExcelValues.data[2][Cap]);
		//capabilities.setCapability(ReadExcelValues.data[3][0], ReadExcelValues.data[3][Cap]);
		capabilities.setCapability(ReadExcelValues.data[3][0], Utils.iOSVersion);
		capabilities.setCapability(ReadExcelValues.data[5][0], ReadExcelValues.data[5][Cap]);
		capabilities.setCapability(ReadExcelValues.data[6][0], ReadExcelValues.data[6][Cap]);
		capabilities.setCapability("automationName", "XCUITest");
		//capabilities.setCapability(ReadExcelValues.data[7][0], "=" + ReadExcelValues.data[7][Cap]);
		// capabilities.setCapability(readExcelValues.data[7][0],"iPhone");
		//capabilities.setCapability(ReadExcelValues.data[7][0], "=" + ReadExcelValues.data[7][Cap]);
		capabilities.setCapability(ReadExcelValues.data[7][0], "=" + "iPhone "+"("+Utils.iOSVersion+") "+"["+Utils.iPhoneUDID+"]");
		//capabilities.setCapability(ReadExcelValues.data[11][0], ReadExcelValues.data[11][Cap]);
		capabilities.setCapability(ReadExcelValues.data[11][0], Utils.iPhoneUDID);
		capabilities.setCapability(ReadExcelValues.data[8][0], ReadExcelValues.data[8][Cap]);
		capabilities.setCapability("noReset", ResetType);
		// ipaPath="/Users/apple/Downloads/ads-ios-master/Build/v9.1-int-423464.ipa";
		// capabilities.setCapability(readExcelValues.data[10][0],
		// readExcelValues.data[10][Cap]);
		// capabilities.setCapability("app","/Users/narasimhanukala/git/ads-automation/ios_Smoke_Automation/Build/iPhone_-_Flagship.ipa");
		capabilities.setCapability(ReadExcelValues.data[12][0], ReadExcelValues.data[12][Cap]);
		capabilities.setCapability(ReadExcelValues.data[13][0], "7200");
		capabilities.setCapability(ReadExcelValues.data[14][0], true);
		// capabilities.setCapability(readExcelValues.data[16][0],
		// readExcelValues.data[16][Cap]);
		//capabilities.setCapability(ReadExcelValues.data[11][0], ReadExcelValues.data[11][Cap]);
		capabilities.setCapability("launchTimeout", 60000);
		capabilities.setCapability("useNewWDA", true);

		// capabilities.setCapability("--session-override",true);
		capabilities.setCapability("bundleId", "com.weather.TWC");
		capabilities.setCapability("xcodeConfigfile",
				"/Applications/Appium.app/Contents/Resources/app/node_modules/appium/node_modules/appium-xcuitest-driver/WebDriverAgent/Config.xcconfig");
		// capabilities.setCapability("xcodeSigningId","iPhone Developer");
		// capabilities.setCapability("xcodeOrgId","54VVTD24DT");
		// capabilities.setCapability("locationServicesEnabled", false);
		capabilities.setCapability("realDeviceLogger", "/Users/apple/node_modules/deviceconsole");
		capabilities.setCapability("wdaLocalPort", "8311");
		// capabilities.setCapability("locationServicesAuthorized", true);
		// capabilities.setCapability("waitForAppScript","$.delay(5000); true");
		capabilities.setCapability("clearSystemFiles", true);
		if (includeRegion) {
			capabilities.setCapability("locale", region);
		}
		if (includelanguage) {
			capabilities.setCapability("language", language);
		}

		System.out.println("Reading capabilities done");
		// Wait time for Execution of node.js
		// TestBase.waitForMilliSeconds(10000);
		Ad = new IOSDriver(new URL("http://127.0.0.1:4733/wd/hub"), capabilities);
		// Ad= new IOSDriver<MobileElement>(service, capabilities);
		Ad.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		System.out.println("TWC App Launched");
		// Handle Extra popup appears when app launched (like New module ebnable)
		ftlScreens = new FTLScreens(Ad);
		ftlScreens.handle_Unwanted_Popups();
		// Functions.enternewAddress("New York City, New York");
		addressScreen = new AddressScreen(Ad);
		addressScreen.enternewAddress(true, "Atlanta, Georgia");
		// Functions.enternewAddress("Woodbridge Township, New Jersey");

		// Functions.enternewAddress("Bridgeton, New Jersey");
		// //Ad.tap(1, 10, 6, 2000);
		// Functions.BacktoWeather();
		// Functions.Take//ScreenShot();

		Write_result wrResult1 = new Write_result();
		// wrResult1.WriteResult("Capabilities", ipaPath.toString(), 14, Cap);
	}
	
	
	/**
	 * Launch the TWC App with localization settings
	 * @param region
	 * @param includeRegion
	 * @param language
	 * @param includelanguage
	 * @throws Exception
	 */
	public static void launchtheAndroidApp_forLocalization(String region, boolean includeRegion,
			String language, boolean includelanguage) throws Exception {
		AndroidFTLScreens ftlScreens;
		
		ReadExcelValues.excelValues("Smoke", "Capabilities");
		
		DesiredCapabilities capabilities = new DesiredCapabilities();

		// Capabilities for IOS and Android Based on Selected on Device Selection
		/*capabilities.setCapability("deviceName", "Galaxy S21 5G");
		capabilities.setCapability("udid", "R5CR11NNFQW");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("platformVersion", "12");
		capabilities.setCapability("appPackage", "com.weather.Weather");
		capabilities.setCapability("appActivity", "com.weather.Weather.app.SplashScreenActivity");
		capabilities.setCapability("automationName", "UiAutomator2");
		capabilities.setCapability("noReset", true);
		capabilities.setCapability("autoLaunch", true);*/
		
		capabilities.setCapability(ReadExcelValues.data[2][0], ReadExcelValues.data[2][2]);
		capabilities.setCapability(ReadExcelValues.data[3][0], ReadExcelValues.data[3][2]);
		capabilities.setCapability(ReadExcelValues.data[7][0], ReadExcelValues.data[7][2]);
		capabilities.setCapability(ReadExcelValues.data[11][0], ReadExcelValues.data[11][2]);
		capabilities.setCapability(ReadExcelValues.data[12][0], ReadExcelValues.data[12][2]);
		capabilities.setCapability("appActivity", "com.weather.Weather.app.SplashScreenActivity");
		capabilities.setCapability("automationName", "UiAutomator2");
		capabilities.setCapability("noReset", true);
		capabilities.setCapability(ReadExcelValues.data[13][0], "7200");
		capabilities.setCapability(ReadExcelValues.data[14][0], true);
		capabilities.setCapability("launchTimeout", 240000);
		//capabilities.setCapability("useNewWDA", true);
		capabilities.setCapability("wdaLocalPort", "7403");
		capabilities.setCapability("clearSystemFiles", true);
		if (includeRegion) {
			capabilities.setCapability("locale", region);
		}
		if (includelanguage) {
			capabilities.setCapability("language", language);
		}
		System.out.println("Reading capabilities done");
		// Wait time for Execution of node.js
		// TestBase.waitForMilliSeconds(10000);
		try {
			Ad = new AndroidDriver(new URL("http://127.0.0.1:4733/wd/hub"), capabilities);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Ad= new IOSDriver<MobileElement>(service, capabilities);
		Ad.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		System.out.println("TWC Android App Launched");
		ftlScreens = new AndroidFTLScreens(Ad);
		/*ftlScreens.clickONTerms();
		attachScreen();
		ftlScreens.clickONNext();
		attachScreen();
		//scrollforclickngIunderstand();
		ftlScreens.ClickonIUnderstand();
		attachScreen();
		ftlScreens.clickOnAllow();
		attachScreen();
		ftlScreens.clickOnWhileUsingTheApp();
		attachScreen();
		ftlScreens.Clickonclosebutton();
		attachScreen();*/
		ftlScreens.handle_Unwanted_Popups();
		Thread.sleep(10000);
		//System.out.println("Capabilities have been launched  with fullreset ");
	}

	/**
	 * Launch the TWC app without full reset
	 * @throws Exception
	 */
	public static void launchtheApp_Withoutfullreset() throws Exception {

		ReadExcelValues.excelValues("Smoke", "Capabilities");
		DesiredCapabilities capabilities = new DesiredCapabilities();

		// Capabilities for IOS and Android Based on Selected on Device Selection
		capabilities.setCapability(ReadExcelValues.data[1][0], ReadExcelValues.data[1][Cap]);
		capabilities.setCapability(ReadExcelValues.data[2][0], ReadExcelValues.data[2][Cap]);
		capabilities.setCapability(ReadExcelValues.data[3][0], ReadExcelValues.data[3][Cap]);
		capabilities.setCapability(ReadExcelValues.data[5][0], ReadExcelValues.data[5][Cap]);
		capabilities.setCapability(ReadExcelValues.data[6][0], ReadExcelValues.data[6][Cap]);
		capabilities.setCapability("automationName", "XCUITest");
		capabilities.setCapability(ReadExcelValues.data[7][0], "=" + ReadExcelValues.data[7][Cap]);
		// capabilities.setCapability(readExcelValues.data[7][0],"iPhone");
		capabilities.setCapability(ReadExcelValues.data[8][0], ReadExcelValues.data[8][Cap]);
		capabilities.setCapability(ReadExcelValues.data[9][0], false);
		capabilities.setCapability(ReadExcelValues.data[10][0], ipaPath.toString());
		capabilities.setCapability(ReadExcelValues.data[12][0], ReadExcelValues.data[12][Cap]);
		capabilities.setCapability(ReadExcelValues.data[13][0], ReadExcelValues.data[13][Cap]);
		capabilities.setCapability(ReadExcelValues.data[14][0], ReadExcelValues.data[14][Cap]);
		capabilities.setCapability(ReadExcelValues.data[16][0], ReadExcelValues.data[16][Cap]);
		capabilities.setCapability(ReadExcelValues.data[11][0], ReadExcelValues.data[11][Cap]);
		// capabilities.setCapability("--session-override",true);
		capabilities.setCapability("bundleId", "com.weather.TWC");
		capabilities.setCapability("xcodeConfigfile",
				"/Users/aparna/Downloads/appium163/node_modules/appium-xcuitest-driver/WebDriverAgent/Config.xcconfig");
		// capabilities.setCapability("xcodeSigningId","iPhone Developer");
		// capabilities.setCapability("locationServicesEnabled", false);
		capabilities.setCapability("realDeviceLogger",
				"/Users/aparna/.npm-packages/lib/node_modules/deviceconsole/deviceconsole");
		// capabilities.setCapability("wdaLocalPort", "8200");
		// capabilities.setCapability("locationServicesAuthorized", true);
		capabilities.setCapability("waitForAppScript", "$.delay(5000); true");
		System.out.println("Reading capabilities done");
		// Wait time for Execution of node.js
		//TestBase.waitForMilliSeconds(80000);

		Ad = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		Ad.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		System.out.println("TWC App Launched");
		// Handle Extra popup appears when app launched (like New module ebnable)
		try {
			Ad.findElementByName("close_button").click();
		} catch (Exception e) {

		}
		try {
			Ad.findElementByName("Allow").click();
		} catch (Exception e) {

		}
		// Ad.tap(1, 10, 6, 2000);
		// Functions.BacktoWeather();
		// Functions.Take//ScreenShot();
	}

	/**
	 * Kill The app
	 * @throws Exception
	 */
	public void killTheApp() throws Exception {
		//Ad.closeApp();
		Ad.terminateApp("com.weather.TWC");
		TestBase.waitForMilliSeconds(1000);
		System.out.println("App closed successfully");
	}

	/**
	 * Relaunch the app 
	 * @throws Exception
	 */
	public void relaunchTheApp() throws Exception {
		try {
			Ad.launchApp();
			//Ad.activateApp("com.weather.TWC");
			System.out.println("App launched successfully");
		} catch (Exception e) {
			// Functions.Take//ScreenShot();
		}
	}
	
	/**
	 * Uninstall and install the app
	 * @throws Exception
	 */
	public static void uninstall_installApp() throws Exception {
		ReadExcelValues.excelValues("Smoke", "Paths");

		if (Ad.isAppInstalled(ReadExcelValues.data[10][Cap])) {
			System.out.println("App installed in the device and trying to uninstall");
			Ad.removeApp(ReadExcelValues.data[10][Cap]);
			System.out.println("App was uninstalled succesfully and trying to install");
			Ad.installApp(System.getProperty("user.dir") + ReadExcelValues.data[14][Cap]);
			System.out.println("App was installed Successfully");
		} else {
			System.out.println("App was not there in the device, installing the appa");
			Ad.installApp(System.getProperty("user.dir") + ReadExcelValues.data[14][Cap]);
			System.out.println("App was installed Successfully");
			TestBase.waitForMilliSeconds(5000);
			Ad.launchApp();
			//Ad.activateApp("com.weather.TWC");
		}
	}

	/**
	 * Uninstall the app
	 * @throws Exception
	 */
	public static void uninstallApp() throws Exception {
		ReadExcelValues.excelValues("Smoke", "Paths");
		TestBase.waitForMilliSeconds(3000);
		String line = "";
		String allLine = "";
		String[] str1 = { "/bin/bash", "-c",
				ReadExcelValues.data[13][Cap] + " " + System.getProperty("user.dir") + ReadExcelValues.data[1][Cap] };
		Process p1 = Runtime.getRuntime().exec(str1);

		TestBase.waitForMilliSeconds(2000);
		BufferedReader r = new BufferedReader(
				new FileReader(System.getProperty("user.dir") + ReadExcelValues.data[1][Cap]));
		TestBase.waitForMilliSeconds(3000);
		while ((line = r.readLine()) != null) {
			// System.out.println("Sys data is ::"+line);
			if (line.contains(ReadExcelValues.data[10][Cap])) {
				// String[] str2 ={"/bin/bash", "-c", "/usr/local/bin/ideviceinstaller -U
				// com.weather.TWC"};
				String[] str2 = { "/bin/bash", "-c", ReadExcelValues.data[11][Cap] + ReadExcelValues.data[10][Cap] };
				Process p2 = Runtime.getRuntime().exec(str2);
				System.out.println("App uninstalled in the device and trying to install the app");
				break;
			}

		}

	}

	/**
	 * install the app
	 * @throws Exception
	 */
	public static void installApp() throws Exception {
		ReadExcelValues.excelValues("Smoke", "Paths");
		String[] str = { "/bin/bash", "-c", ReadExcelValues.data[12][Cap] + " " + Functions.ipaPath };
		Process p = Runtime.getRuntime().exec(str);

		TestBase.waitForMilliSeconds(50000);
		System.out.println("App was installed in the device successfully");
	}

	/**
	 * Delete Charles session xml files
	 * 
	 * @param folderType
	 * @throws Exception
	 */
	public static void delete_folder(String folderType) throws Exception {
		ReadExcelValues.excelValues("Smoke", "Paths");
		String downloadPath = null;
		// downloadPath = System.getProperty("user.dir") + "/CapturedSessionFile/";

		if (folderType.equals("Charles")) {
			downloadPath = System.getProperty("user.dir") + ReadExcelValues.data[4][Cap];
		} else {
			downloadPath = ReadExcelValues.data[18][Cap];
		}

		// String Screenshots = readExcelValues.data[16][Cap];

		File index = new File(downloadPath);

		if (!index.exists()) {
			System.out.println("Specified charles session folder is not exist and creating the same folder now");
			index.mkdir();
		} else {
			System.out.println("Specified charles session folder is exist and deleting the same folder");
			FileUtils.cleanDirectory(index);

			System.out.println("Deleted all the files in the specified charles session folder");
		}

	}

	/**
	 * Archives Charles session xml files
	 * 
	 * @param folderType
	 * @throws Exception
	 */
	public static void archive_folder(String folderType) throws Exception {
		ReadExcelValues.excelValues("Smoke", "Paths");
		String downloadPath = null;
		String archivedSessions = System.getProperty("user.dir") + "/ArchivedSessions";

		// downloadPath = System.getProperty("user.dir") + "/CapturedSessionFile/";

		if (folderType.equals("Charles")) {
			downloadPath = System.getProperty("user.dir") + ReadExcelValues.data[4][Cap];
		} else {
			downloadPath = ReadExcelValues.data[18][Cap];
		}

		// String Screenshots = readExcelValues.data[16][Cap];

		File index = new File(downloadPath);

		if (!index.exists()) {
			index.mkdir();
		}

		for (final File fileEntry : index.listFiles()) {
			if (fileEntry.isDirectory()) {

				FileUtils.moveDirectoryToDirectory(fileEntry, new File(archivedSessions), true);

			} else {
				if (fileEntry.toString().contains("chlsx")) {

					FileUtils.moveFileToDirectory(fileEntry, new File(archivedSessions), true);
				}
			}
		}

	}

	/**
	 * Delete Screenshots session xml files
	 * 
	 * @throws Exception
	 */
	public static void delete_Screenshots() throws Exception {
		ReadExcelValues.excelValues("Smoke", "Paths");

		String downloadPath = System.getProperty("user.dir") + ReadExcelValues.data[16][Cap];
		// String Screenshots = readExcelValues.data[16][Cap];

		File index = new File(downloadPath);
		// File Screenindex= new File(Screenshots);

		if (!index.exists()) {
			System.out.println("Specified ScreenShot folder is not exist and creating the same folder now");
			index.mkdir();
		} else {
			System.out.println("Specified ScreenShot folder is exist and deleting the same folder");
			FileUtils.cleanDirectory(index);
			System.out.println("Deleted all the files in the specified ScreenShot folder");
		}

	}

	/**
	 * Navigate to WIDGET PAGE
	 * @throws Exception
	 */
	public static void navigateTo_Widgetpage() throws Exception {
		// Swinpe Status bar for notification screen
		logStep("Swip for weather Widget");

		// Ad.swipe(0, 0, 0, 1800, 4000);
		TestBase.waitForMilliSeconds(4000);
		// Ad.swipe(4, 250, 250, 50, 2000);

		WebDriverWait wait = new WebDriverWait(Ad, 30);
		WebElement SearchWidget = null;
		Dimension dimensions = Ad.manage().window().getSize();
		Double startY1 = (double) dimensions.getHeight();
		System.out.println("Y value is :" + startY1);
		startY1 = (double) (dimensions.getHeight() - 20);
		System.out.println("After Y" + startY1);
		startY = startY1.intValue();
		int y = startY1.intValue();
		int y1 = y / 2;
		int WidgetPlace;
		try {
			// XCUIElementTypeOther[@name=\"WGWidgetPlatterView\"])[1]
			SearchWidget = wait.until(ExpectedConditions
					.visibilityOf(Ad.findElementByXPath("((//XCUIElementTypeOther[@name='WGWidgetPlatterView'])[1])")));
			// Ad.findElementByXPath("(//XCUIElementTypeOther[@name=\"WGWidgetPlatterView\"])[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther[3]").click();
			SearchWidget.click();
			logStep("Weather Widgent found and tapped");
			System.out.println("Weather Widgent found and tapped");
			// Ad.findElement(By.xpath("//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[2]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther["+WidgetPlace+"]/XCUIElementTypeOther[3]/XCUIElementTypeOther[1]")).click();
		} catch (Exception e) {

			try {
				System.out.println("Weather Widgent not found so, try to tap using co-ordinats");
				logStep("Weather Widgent not found so, try to tap using co-ordinats");
				TestBase.waitForMilliSeconds(3000);
				// Ad.tap(1, 27, 196, 1000);
				TestBase.waitForMilliSeconds(3000);
			} catch (Exception e1) {
				System.out.println("Weather Widgent not found");
				logStep("Weather Widgent not found");
			}
		}
		try {
			Ad.findElementByName("Open").click();
		} catch (Exception e) {

		}

		// Double endY1 = (double) (dimensions.getHeight()/40); //
		// dimensions.getHeight() 0.2; == 512.0
		// endY = endY1.intValue()

		// WebDriverWait wait = new WebDriverWait(Ad,15);
		// WebElement WeatherChannel =
		// wait.until(ExpectedConditions.elementToBeClickable(Ad.findElementByName("The
		// Weather Channel")));
		CharlesProxy.proxy.clearCharlesSession();
		// Ad.tap(1, 24, y, 2000);
		logStep("Tap on weather channel icon on weather widget");
		// Actions builder = new Actions(driver);
		// //builder.moveToElement(WeatherChannel,10,y).click().build().perform();
		// //builder.moveByOffset(10, y).click().build().perform();
		// builder.moveByOffset(10, y).clickAndHold().build().perform();

	}

	/**
	 * Navigate Back to WIDGET PAGE 
	 * @throws Exception
	 */
	public static void navigate_BackToWidgetpage() throws Exception {

		// Swinpe Status bar for notification screen
		// Ad.swipe(0, 0, 0, 1800, 4000);
		TestBase.waitForMilliSeconds(2000);
		// Ad.swipe(0, 80, 400, 80, 2000);

		int WidgetPlace;
		// Select Weather widget in notifications
		String WidgetName = null;
		for (WidgetPlace = 1; WidgetPlace <= 6; WidgetPlace++) {
			try {
				WidgetName = Ad.findElementByXPath(
						"(//XCUIElementTypeOther[@name=\\\"WGWidgetPlatterView\\\"])[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther[3]")
						.getText();
				if (WidgetName.equals("WEATHER")) {
					System.out.println("WEATHER Widget found");
					System.out.println("Location of Widget is :" + WidgetPlace);
					break;
				} else {
					System.out.println("WEATHER widget not found search for widget");
				}
			} catch (Exception e) {
				System.out.println("WEATHER widget not found for first time search / Scroll for widget");
				// Ad.swipe(50, 200, 50, 80, 2000);
				WidgetPlace = WidgetPlace - 1;
			}

		}

		Ad.findElement(By.xpath(
				"//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[2]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther["
						+ WidgetPlace + "]/XCUIElementTypeOther[3]/XCUIElementTypeOther[1]"))
				.click();

		try {
			Ad.findElementByName("Open").click();
		} catch (Exception e) {

		}

		Dimension dimensions = Ad.manage().window().getSize();
		Double startY1 = (double) dimensions.getHeight();
		System.out.println("Y value is :" + startY1);
		startY1 = (double) (dimensions.getHeight() - 20);
		System.out.println("After Y" + startY1);
		startY = startY1.intValue();
		int y = startY1.intValue();
		// Double endY1 = (double) (dimensions.getHeight()/40); //
		// dimensions.getHeight() 0.2; == 512.0
		// endY = endY1.intValue()

		// WebDriverWait wait = new WebDriverWait(Ad,15);
		// WebElement WeatherChannel =
		// wait.until(ExpectedConditions.elementToBeClickable(Ad.findElementByName("The
		// Weather Channel")));
		// Ad.tap(1, 10, 6, 2000);
		// Actions builder = new Actions(driver);
		// //builder.moveToElement(WeatherChannel,10,y).click().build().perform();
		// //builder.moveByOffset(10, y).click().build().perform();
		// builder.moveByOffset(10, y).clickAndHold().build().perform();

	}

	/**
	 * Launch the widget app
	 * @throws Exception
	 */
	public static void launchAppFromTheWidgetApp() throws Exception {
		FTLScreens ftlScreens;
				
		ReadExcelValues.excelValues("Smoke", "Capabilities");
		// service = AppiumDriverLocalService.buildService(new
		// AppiumServiceBuilder().withAppiumJS(new
		// File("/usr/local/lib/node_modules/appium/build/lib/main.js")).withIPAddress("0.0.0.0").usingPort(port));
		DesiredCapabilities capabilities = new DesiredCapabilities();

		// Capabilities for IOS and Android Based on Selected on Device Selection
		capabilities.setCapability(ReadExcelValues.data[1][0], ReadExcelValues.data[1][Cap]);
		capabilities.setCapability(ReadExcelValues.data[2][0], ReadExcelValues.data[2][Cap]);
		capabilities.setCapability(ReadExcelValues.data[3][0], ReadExcelValues.data[3][Cap]);
		capabilities.setCapability(ReadExcelValues.data[5][0], ReadExcelValues.data[5][Cap]);
		capabilities.setCapability(ReadExcelValues.data[6][0], ReadExcelValues.data[6][Cap]);
		capabilities.setCapability("automationName", "XCUITest");
		capabilities.setCapability(ReadExcelValues.data[7][0], "=" + ReadExcelValues.data[7][Cap]);
		// capabilities.setCapability(readExcelValues.data[7][0],"iPhone");
		capabilities.setCapability(ReadExcelValues.data[8][0], ReadExcelValues.data[8][Cap]);
		capabilities.setCapability("noReset", "True");
		// ipaPath="/Users/apple/Downloads/ads-ios-master/Build/v9.1-int-423464.ipa";
		// capabilities.setCapability(readExcelValues.data[10][0],
		// readExcelValues.data[10][Cap]);
		// capabilities.setCapability("app","/Users/narasimhanukala/git/ads-automation/ios_Smoke_Automation/Build/iPhone_-_Flagship.ipa");
		// capabilities.setCapability(readExcelValues.data[12][0],
		// readExcelValues.data[12][Cap]);
		capabilities.setCapability(ReadExcelValues.data[13][0], 120);
		capabilities.setCapability(ReadExcelValues.data[14][0], true);
		capabilities.setCapability(ReadExcelValues.data[16][0], ReadExcelValues.data[16][Cap]);
		capabilities.setCapability(ReadExcelValues.data[11][0], ReadExcelValues.data[11][Cap]);
		// capabilities.setCapability("--session-override",true);
		capabilities.setCapability("bundleId", "com.apple.weather");
		capabilities.setCapability("xcodeConfigfile",
				"/Applications/Appium.app/Contents/Resources/app/node_modules/appium/node_modules/appium-xcuitest-driver/WebDriverAgent/Config.xcconfig");
		// capabilities.setCapability("xcodeSigningId","iPhone Developer");
		// capabilities.setCapability("locationServicesEnabled", false);
		capabilities.setCapability("realDeviceLogger", "/Users/apple/node_modules/deviceconsole");
		capabilities.setCapability("wdaLocalPort", "8201");
		// capabilities.setCapability("locationServicesAuthorized", true);
		// capabilities.setCapability("waitForAppScript","$.delay(5000); true");
		capabilities.setCapability("clearSystemFiles", true);
		System.out.println("Reading capabilities done");
		// Wait time for Execution of node.js
		// TestBase.waitForMilliSeconds(10000);
		Ad1 = new IOSDriver(new URL("http://127.0.0.1:4733/wd/hub"), capabilities);
		// Ad= new IOSDriver<MobileElement>(service, capabilities);
		Ad1.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		System.out.println("Widget App Launched");
		// Handle Extra popup appears when app launched (like New module ebnable)
		// Handle_unwanted_popups();
		// Functions.enternewAddress("Atlanta, Georgia");
		// //Ad.tap(1, 10, 6, 2000);
		// Functions.BacktoWeather();
		// Functions.Take//ScreenShot();
		TestBase.waitForMilliSeconds(5000);
		Functions.swipe_Up_ByIterations(Ad1, 4);
		attachScreen(Ad1);
		Ad1.findElementByAccessibilityId("The Weather Channel").click();
		TestBase.waitForMilliSeconds(10000);
		ftlScreens = new FTLScreens(Ad1);
		ftlScreens.handle_Unwanted_Popups_When_App_Launched_From_Widget(Ad1);
		attachScreen(Ad1);
	}

	/**
	 * Download Charles XML file
	 * @throws Exception
	 */
	public static void downloadXMLFile() throws Exception {
		TestBase.waitForMilliSeconds(5000);
		ReadExcelValues.excelValues("Smoke", "Charlesdeatils");
		TestBase.waitForMilliSeconds(1000);
		driver.findElement(By.linkText(ReadExcelValues.data[7][0])).click();
		TestBase.waitForMilliSeconds(2000);
		System.out.println("Export file started");
		// driver.navigate().to(readExcelValues.data[3][0]);
		TestBase.waitForMilliSeconds(25000);
		// driver.close();

		ReadExcelValues.excelValues("Smoke", "Paths");
		File folder = new File(System.getProperty("user.dir") + ReadExcelValues.data[4][Cap]);
		/*
		 * Labelled loop 'charlesloop' is created beacause certain times, during file
		 * export, partial file is created. hence checking for partial file not there in
		 * the folder, if it exist loop iterates 15 timies assuming that in these 15
		 * iterations, partial down will get deleted automatically
		 */
		charlesloop: for (int i = 0; i <= 50; i++) {
			File[] listOfFiles = folder.listFiles();
			int count = 0;
			String Filename = null;
			for (File file : listOfFiles) {
				if (file.isFile()) {
					Filename = file.getName();
					System.out.println("Checking for File export completion and file name is : " + Filename);
					logStep("Checking for File export completion and file name is : " + Filename);
					if (!Filename.contains("part")) {
						count++;
					}
				}
			}
			if (count >= 1) {
				System.out.println("Export file completed");
				logStep("Export file completed");
				break charlesloop;

			}
		}

	}

	/**
	 * Get all the files in the folder
	 * @param directoryName
	 * @return
	 */
	public static List<String> listFiles(String directoryName) {

		// String file_name = null;
		List<String> filelist = new ArrayList<String>();
		File directory = new File(directoryName);
		// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()) {
				filelist.add(file.getName());
				// file_name = file.getName();
				// break;
			}
		}
		return filelist;
	}
	
	/**
	 * To Scroll left on iOS device.
	 * 
	 * @author narasimhanukala;
	 * @throws Exception
	 */
	public static void scroll_Left() throws Exception {
		// Scroll JS

		JavascriptExecutor js = (JavascriptExecutor) Ad;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "left");
		js.executeScript("mobile: scroll", scrollObject);

	}

	/**
	 * To Scroll right on iOS device.
	 * 
	 * @author narasimhanukala;
	 * @throws Exception
	 */
	public static void scroll_Right() throws Exception {
		// Scroll JS

		JavascriptExecutor js = (JavascriptExecutor) Ad;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "right");
		js.executeScript("mobile: scroll", scrollObject);

	}

	/**
	 * Swipe up on Mobile Screen
	 * 
	 * @throws Exception
	 */
	public static void scroll_Down() throws Exception {
		// Scroll JS
		JavascriptExecutor js = (JavascriptExecutor) Ad;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "down");
		js.executeScript("mobile:scroll", scrollObject);

	}

	/**
	 * Refresh on TWC App Screen to generate the call/Swipe down on Mobile screen
	 * 
	 * @throws Exception
	 */
	public static void scroll_Up() throws Exception {
		// Scroll JS
		try {
			JavascriptExecutor js = (JavascriptExecutor) Ad;
			HashMap<String, String> scrollObject = new HashMap<String, String>();
			scrollObject.put("direction", "up");
			js.executeScript("mobile: scroll", scrollObject);
			System.out.println("Scroll / Pull to refresh done");
			logStep("Scroll / Pull to refresh done");
		} catch (Exception e) {
			System.out.println("Scroll / Pull to refresh failed");
			logStep("Scroll / Pull to refresh failed");
		}
	}

	/**
	 * Swipe Up on Location & Weather Screen during App Launch
	 * 
	 * @throws Exception
	 */
	public static void swipe_Up_OnLocationScreen() throws Exception {
		// Scroll JS
		/*
		 * JavascriptExecutor js = (JavascriptExecutor) Ad; HashMap<String, String>
		 * scrollObject = new HashMap<String, String>(); scrollObject.put("direction",
		 * "down"); js.executeScript("mobile:swipe", scrollObject);
		 */

		 TouchAction ta= new TouchAction(Ad);
		//
		// action.press(356, 626).waitAction(1000).moveTo(-11,-533).release().perform();

		Dimension dimensions = Ad.manage().window().getSize();// throwing exception

		Double startY1 = dimensions.getHeight() * 0.1;
		startY = startY1.intValue();
		// Double endY1 = (double) (dimensions.getHeight()/40); //
		// dimensions.getHeight() 0.2; == 512.0
		Double endY1 = dimensions.getHeight() * 0.7;
		endY = endY1.intValue();
		ta.press(PointOption.point(100, endY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
		.moveTo(PointOption.point(100, startY)).release().perform();
		//Ad.swipe(100, endY, 100, startY, 2000);
		//Ad.swipe(100, endY, 100, startY, 2000);
		// swipeVertical(0.9,0.8,0.5,2000);

	}

	/**
	 * SCROLL DOWN
	 * @throws Exception
	 */
	public static void swipe_Up(AppiumDriver<MobileElement> Ad) throws Exception {
		// Scroll JS

		TouchAction ta = new TouchAction(Ad);
		try {
			Dimension dimensions = Ad.manage().window().getSize();// throwing exception

			Double startY1 = dimensions.getHeight() * 0.73;
			startY = startY1.intValue();
			// Double endY1 = (double) (dimensions.getHeight()/40); //
			// dimensions.getHeight() 0.2; == 512.0
			Double endY1 = dimensions.getHeight() * 0.40;
			endY = endY1.intValue();

			ta.press(PointOption.point(0, startY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
					.moveTo(PointOption.point(0, endY)).release().perform();
			TestBase.waitForMilliSeconds(500);
			ta.press(PointOption.point(0, startY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
					.moveTo(PointOption.point(0, endY)).release().perform();

		} catch (Exception e) {
			try {

				System.out.println("An Exception in Try of Swipe");
				logStep("An Exception in Try of Swipe");
				Dimension dimensions = Ad.manage().window().getSize();// throwing exception

				Double startY1 = dimensions.getHeight() * 0.60;
				startY = startY1.intValue();
				// Double endY1 = (double) (dimensions.getHeight()/40); //
				// dimensions.getHeight() 0.2; == 512.0
				Double endY1 = dimensions.getHeight() * 0.40;
				endY = endY1.intValue();

				// Ad.swipe(0, startY, 0, endY, 2000);
				// Ad.swipe(0, startY, 0, endY, 2000);
				ta.press(PointOption.point(0, startY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
						.moveTo(PointOption.point(0, endY)).release().perform();
				TestBase.waitForMilliSeconds(500);
				ta.press(PointOption.point(0, startY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
						.moveTo(PointOption.point(0, endY)).release().perform();

			} catch (Exception e1) {
				System.out.println("An Exception in Catch of Swipe");
				logStep("An Exception in Catch of Swipe");
				attachScreen();
			}

		}

	}

	/**
	 * SCROLL UP
	 * @throws Exception
	 */
	public static void swipe_Down() throws Exception {

		TouchAction ta = new TouchAction(Ad);
		try {
			Dimension dimensions = Ad.manage().window().getSize();// throwing exception

			Double startY1 = dimensions.getHeight() * 0.73;
			startY = startY1.intValue();
			// Double endY1 = (double) (dimensions.getHeight()/40); //
			// dimensions.getHeight() 0.2; == 512.0
			Double endY1 = dimensions.getHeight() * 0.40;
			endY = endY1.intValue();
			ta.press(PointOption.point(0, endY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
					.moveTo(PointOption.point(0, startY)).release().perform();
						
		} catch (Exception e) {

		}

	}
	
	/**
	 * SCROLL UP
	 * @throws Exception
	 */
	public static void swipe_Down(AppiumDriver<MobileElement> Ad) throws Exception {

		TouchAction ta = new TouchAction(Ad);
		try {
			Dimension dimensions = Ad.manage().window().getSize();// throwing exception

			Double startY1 = dimensions.getHeight() * 0.73;
			startY = startY1.intValue();
			// Double endY1 = (double) (dimensions.getHeight()/40); //
			// dimensions.getHeight() 0.2; == 512.0
			Double endY1 = dimensions.getHeight() * 0.40;
			endY = endY1.intValue();
			swipe_Left();
			ta.press(PointOption.point(0, endY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
					.moveTo(PointOption.point(0, startY)).release().perform();
									
		} catch (Exception e) {

		}

	}
	
	/**
	 * SCROLL Left
	 * @throws Exception
	 */
	public static void swipe_Left() throws Exception {

		TouchAction ta = new TouchAction(Ad);
		try {
			Dimension dimensions = Ad.manage().window().getSize();// throwing exception
			Double startX1 = dimensions.getWidth()* 0.75;
			Double startY1 = dimensions.getHeight() * 0.73;
			
			startX = startX1.intValue();
			startY = startY1.intValue();
			// Double endY1 = (double) (dimensions.getHeight()/40); //
			// dimensions.getHeight() 0.2; == 512.0
			Double endX1 = dimensions.getWidth() * 0.10;
			Double endY1 = dimensions.getHeight() * 0.40;
			
			endX = endX1.intValue();
			endY = endY1.intValue();
			ta.press(PointOption.point(startX, endY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
					.moveTo(PointOption.point(endX, endY)).release().perform();
						
		} catch (Exception e) {

		}

	}

	/**
	 * Swipe by no of iterations
	 * @param count
	 * @throws Exception
	 */
	public static void swipe_Up_ByIterations(AppiumDriver<MobileElement> Ad, int count) throws Exception {
		for (int i = 1; i <= count; i++) {
			swipe_Up(Ad);
			TestBase.waitForMilliSeconds(3000);
		}

	}

	/**
	 * Get Current Feed Card Size
	 * @param currentCard
	 * @return
	 */
	public static int getFeedCardSize(MobileElement currentCard) {
		Dimension d = currentCard.getSize();
		System.out.println("Height is : " + d.getHeight());
		logStep("Height is : " + d.getHeight());
		int height = d.getHeight();
		return height;
	}

	/**
	 * Get Current Feed Card Size and Swipe it
	 * @param cardHeight
	 * @throws Exception
	 */
	public static void getFeedCardSizeandSwipewithNoMargin(int cardHeight) throws Exception {

		startY = 0;

		Double endY1 = (double) ((cardHeight) * 1);
		endY = endY1.intValue();

		int reEndY = 0;
		// int relativeY = 80;
		int relativeY = 80;
		// int variance = 21;
		int variance = 22;

		TouchAction ta = new TouchAction(Ad);
		TouchAction ta1 = new TouchAction(Ad);
		if (endY > 500 && endY <= 510) {
			variance = 20;
			// variance = 0;
			// relativeY = 80;
		}
		int absStartY = endY + variance + relativeY;

		if (endY > 510) {

			reEndY = endY - 510;
			endY = 510;
			variance = 20;
			//variance = 0;
			// relativeY = 80;

			ta.press(PointOption.point(0, endY + variance + relativeY))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
					.moveTo(PointOption.point(0, relativeY)).release().perform();

			if (reEndY < 100) {
				variance = 15;

				ta1.press(PointOption.point(0, reEndY + variance + relativeY))
						.waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
						.moveTo(PointOption.point(0, relativeY)).release().perform();

			} else {
				if (reEndY > 500 && reEndY <= 510) {
					variance = 20;
					// variance = 0;
					// relativeY = 80;
				} else {
					// variance = 21;
					variance = 15;
					// relativeY = 80;
				}

				ta1.press(PointOption.point(0, reEndY + variance + relativeY))
						.waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
						.moveTo(PointOption.point(0, relativeY)).release().perform();
			}

		} else {

			ta.press(PointOption.point(0, absStartY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
					.moveTo(PointOption.point(0, relativeY)).release().perform();

		}
	}

	/**
	 * Get Current Feed Card Size and Swipe it
	 * @throws Exception
	 */
	public static void getFeedCardSizeandSwipewithNoMargin() throws Exception {
		MobileElement currentCard = null;
		currentCard = globalcurrentCard;

		Dimension d = currentCard.getSize();
		System.out.println("Height is : " + d.getHeight());
		logStep("Height is : " + d.getHeight());

		int startx = d.width / 2;
		Double startY1 = d.getHeight() * 0.00;
		startY = startY1.intValue();
		startY = 0;

		Double endY1 = (double) ((d.getHeight()) * 1);
		endY = endY1.intValue();

		int reEndY = 0;
		// int relativeY = 80;
		int relativeY = 80;
		// int variance = 21;
		int variance = 22;

		TouchAction ta = new TouchAction(Ad);
		TouchAction ta1 = new TouchAction(Ad);
		if (endY > 500 && endY <= 510) {
			variance = 20;
			// variance = 0;
			// relativeY = 80;
		}
		int absStartY = endY + variance + relativeY;

		if (endY > 510) {

			reEndY = endY - 510;
			endY = 510;
			variance = 20;
			//variance = 0;
			// relativeY = 80;

			ta.press(PointOption.point(0, endY + variance + relativeY))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
					.moveTo(PointOption.point(0, relativeY)).release().perform();

			if (reEndY < 100) {
				variance = 15;

				ta1.press(PointOption.point(0, reEndY + variance + relativeY))
						.waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
						.moveTo(PointOption.point(0, relativeY)).release().perform();

			} else {
				if (reEndY > 500 && reEndY <= 510) {
					variance = 20;
					// variance = 0;
					// relativeY = 80;
				} else {
					// variance = 21;
					variance = 15;
					// relativeY = 80;
				}

				ta1.press(PointOption.point(0, reEndY + variance + relativeY))
						.waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
						.moveTo(PointOption.point(0, relativeY)).release().perform();
			}

		} else {

			ta.press(PointOption.point(0, absStartY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
					.moveTo(PointOption.point(0, relativeY)).release().perform();

		}
	}
	
	/**
	 * Wait for an Element
	 * @param Element
	 * @param sec
	 */
	public static void waitForElement(WebElement Element, int sec) {
		WebDriverWait wait = new WebDriverWait(Ad, sec);
		wait.until(ExpectedConditions.visibilityOf(Element));
	}

	/**
	 * Multy Scroll
	 * @param scrollCount
	 * @throws InterruptedException
	 */
	public static void multy_ScrollDown(int scrollCount) throws InterruptedException {
		// TestBase.waitForMilliSeconds(1000);
		// Scroll JS
		JavascriptExecutor js = (JavascriptExecutor) Ad;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "down");
		for (int i = 1; i <= scrollCount; i++) {
			js.executeScript("mobile: scroll", scrollObject);
		}

	}

	/**
	 * Close and launch the app
	 * Note appium 1.23.0 beta doesnt support closeApp() and launchApp(), hence used terminateApp() and activateApp() instead
	 */
	public static void close_launchApp() {
		FTLScreens ftlScreens;
		try {
			//Ad.closeApp();
			Ad.terminateApp("com.weather.TWC");
			System.out.println("App Closed SuccessFully");
			logStep("App Closed SuccessFully");
//			TestBase.waitForMilliSeconds(5000);
			Ad.launchApp();
			//Ad.activateApp("com.weather.TWC");
			System.out.println("App Launched SuccessFully");
			logStep("App Launched SuccessFully");
//			TestBase.waitForMilliSeconds(5000);
			ftlScreens = new FTLScreens(Ad);
			ftlScreens.handle_Unwanted_Popups();
			// attachScreen();
		} catch (Exception e) {
			try {
				Ad.launchApp();
				Ad.terminateApp("com.weather.TWC");
				System.out.println("App Closed SuccessFully");
				logStep("App Closed SuccessFully");
//				TestBase.waitForMilliSeconds(5000);
				Ad.launchApp();
				//Ad.activateApp("com.weather.TWC");
				System.out.println("App Launched SuccessFully");
				logStep("App Launched SuccessFully");
//				TestBase.waitForMilliSeconds(5000);
				ftlScreens = new FTLScreens(Ad);
				ftlScreens.handle_Unwanted_Popups();
				// attachScreen();
			} catch (Exception e1) {
				System.out.println("App  close / Launch failed");
				logStep("App  close / Launch failed");
			}
		}

	}
	
	/**
	 * Close and launch the app
	 * Note appium 1.23.0 beta doesnt support closeApp() and launchApp(), hence used terminateApp() and activateApp() instead
	 */
	public static void close_launchAppAndroid() {
		AndroidFTLScreens ftlScreens;
		try {
			Ad.terminateApp("com.weather.Weather");
			System.out.println("App Closed SuccessFully");
			logStep("App Closed SuccessFully");
//			TestBase.waitForMilliSeconds(5000);
			Ad.launchApp();
			//Ad.activateApp("com.weather.TWC");
			System.out.println("App Launched SuccessFully");
			logStep("App Launched SuccessFully");
//			TestBase.waitForMilliSeconds(5000);
			ftlScreens = new AndroidFTLScreens(Ad);
			ftlScreens.handle_Unwanted_Popups();
			// attachScreen();
		} catch (Exception e) {
			try {
				Ad.launchApp();
				Ad.terminateApp("com.weather.Weather");
				System.out.println("App Closed SuccessFully");
				logStep("App Closed SuccessFully");
//				TestBase.waitForMilliSeconds(5000);
				Ad.launchApp();
				//Ad.activateApp("com.weather.TWC");
				System.out.println("App Launched SuccessFully");
				logStep("App Launched SuccessFully");
//				TestBase.waitForMilliSeconds(5000);
				ftlScreens = new AndroidFTLScreens(Ad);
				ftlScreens.handle_Unwanted_Popups();
				// attachScreen();
				TestBase.waitForMilliSeconds(6000);
			} catch (Exception e1) {
				System.out.println("App  close / Launch failed");
				logStep("App  close / Launch failed");
				e1.printStackTrace();
			}
		}

	}
	
	/**
	 * Close and launch the app
	 * Note appium 1.23.0 beta doesnt support closeApp() and launchApp(), hence used terminateApp() and activateApp() instead
	 */
	public static void close_launchApp(AppiumDriver<MobileElement> Ad) {
		FTLScreens ftlScreens;
		try {
			//Ad.closeApp();
			Ad.terminateApp("com.weather.TWC");
			System.out.println("App Closed SuccessFully");
			logStep("App Closed SuccessFully");
//			TestBase.waitForMilliSeconds(5000);
			Ad.launchApp();
			//Ad.activateApp("com.weather.TWC");
			System.out.println("App Launched SuccessFully");
			logStep("App Launched SuccessFully");
//			TestBase.waitForMilliSeconds(5000);
			ftlScreens = new FTLScreens(Ad);
			ftlScreens.handle_Unwanted_Popups(Ad);
			// attachScreen();
		} catch (Exception e) {
			System.out.println("App  close / Launch failed");
			logStep("App  close / Launch failed");
		}

	}

	/**
	 * Put app in Background and relaunch
	 * 
	 * @param noOfSeconds
	 * @throws Exception
	 */
	public static void put_Background_launch(int noOfSeconds) throws Exception {
		FTLScreens ftlScreens;
		try {
			System.out.println("Running app in Background for: "+noOfSeconds+" seconds");
			logStep("Running app in Background for: "+noOfSeconds+" seconds");
			Ad.runAppInBackground(Duration.ofSeconds(noOfSeconds));
			ftlScreens = new FTLScreens(Ad);
			ftlScreens.handle_Unwanted_Popups();
		} catch (WebDriverException e) {
			System.out.println("Failed to Run app in Background");
			logStep("Failed to Run app in Background");
			if (e.getMessage().contains("An error occurred while executing user supplied JavaScript")) {
			} else {
				throw new RuntimeException(e);
			}
		}
	}
	
	/**
	 * Put app in Background and relaunch
	 * 
	 * @param noOfSeconds
	 * @throws Exception
	 */
	public static void put_Background_launchAndroid(int noOfSeconds) throws Exception {
		AndroidFTLScreens ftlScreens;
		try {
			System.out.println("Running app in Background for: "+noOfSeconds+" seconds");
			logStep("Running app in Background for: "+noOfSeconds+" seconds");
			Ad.runAppInBackground(Duration.ofSeconds(noOfSeconds));
			ftlScreens = new AndroidFTLScreens(Ad);
			ftlScreens.handle_Unwanted_Popups();
		} catch (WebDriverException e) {
			System.out.println("Failed to Run app in Background");
			logStep("Failed to Run app in Background");
			if (e.getMessage().contains("An error occurred while executing user supplied JavaScript")) {
			} else {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Take Screenshot
	 * 
	 * @throws Exception
	 */
	public static void takeScreenShot() throws Exception {
		ReadExcelValues.excelValues("Smoke", "Paths");
		File Screenshot = ((TakesScreenshot) Ad).getScreenshotAs(OutputType.FILE);
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm").format(Calendar.getInstance().getTime());
		FileUtils.copyFile(Screenshot, new File(
				System.getProperty("user.dir") + ReadExcelValues.data[16][Cap] + "/ScreenShot-" + timeStamp + ".png"));

	}

	/**
	 * Write Build Number to Properties file
	 * @throws Exception
	 */
	public static void Set_BuildNumber() throws Exception {
		FileOutputStream fos = new FileOutputStream(properties.getProperty("dataFilePath"));

		properties.setProperty("Old_Build", BuildName);
		properties.store(fos, "Build Information stotred");
		fos.close();
	}

	/**
	 * Read ipa details from Build Folder
	 * 
	 * @param folder
	 * @throws Exception
	 */
	public static void listFilesForFolder(File folder) throws Exception {
		ReadExcelValues.excelValues("Smoke", "Paths");
		folder = new File(System.getProperty("user.dir") + ReadExcelValues.data[15][Cap]);
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {

				listFilesForFolder(fileEntry);

			} else {
				if (fileEntry.toString().contains("ipa")) {

					ipaPath = System.getProperty("user.dir") + ReadExcelValues.data[15][Cap] + fileEntry.getName();
					System.out.println("ipaPath is :" + ipaPath.toString());
					BuildNo = fileEntry.getName().replace(".ipa", "");

				}
			}
		}

	}

	/**
	 * Scroll the app till footer text displayed
	 * 
	 * @throws Exception
	 */
	public static void scroll_ToEnd() throws Exception {
		ReadExcelValues.excelValues("Smoke", "General");
		System.out.println("User on Cc page and trying to scroll the app till end");
		logStep("User on Cc page and trying to scroll the app till end");
		for (int scrollend = 1; scrollend <= 12; scrollend++) {
			try {
				if (Ad.findElementByName(ReadExcelValues.data[1][Cap]).isDisplayed()) {
					System.out.println("User done scrolling");
					logStep("User done scrolling till last page");
					break;
				}
			} catch (Exception e) {
				System.out.println("last page not found, Need to scrol till the end");
				if (scrollend == 12) {
					logStep("last page not found");
				}
			}

			try {
				scroll_Down();
				scroll_Down();
			} catch (Exception e) {

				String[] strcleariProxy = { "/bin/bash", "-c", "killall iproxy xcodebuild XCTRunner" };
				Process proc = Runtime.getRuntime().exec(strcleariProxy);
				Exception = "Scrolling failed, need to execute test Case again	";
				Assert.fail(Exception);
			}
		}
	}

	/**
	 * Generates Screenshot
	 * @param Adtype
	 * @param ScreenType
	 * @throws Exception
	 */
	public static void screenShot(String Adtype, String ScreenType) throws Exception {
		ScreenShot = System.getProperty("user.dir") + "/screenshots";
		if (ScreenType.equals("Passed")) {

			File Screenshot = ((TakesScreenshot) Ad).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(Screenshot,
					new File(ScreenShot + "/" + BuildNo + "/ScreenShot_" + ScreenType + " " + Adtype + ".png"));
		} else {
			File Screenshot = ((TakesScreenshot) Ad).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(Screenshot,
					new File(ScreenShot + "/" + BuildNo + "/ScreenShot_" + ScreenType + " " + Adtype + ".png"));
			FileUtils.copyFile(Screenshot,
					new File(ScreenShot + "/Failed/ScreenShot_" + ScreenType + " " + Adtype + ".png"));

		}
	}

	/**
	 * Navigate Back To FeedCard
	 */
	public static void navigateBackToFeedCard() {
		try {
			Ad.findElementByAccessibilityId("Back").click();
			System.out.println("Navigating back to feed card");
			logStep("Navigating back to feed card");
			TestBase.waitForMilliSeconds(4000);
		} catch (Exception e) {
			System.out.println("An Exception while navigating back to feed card");
			logStep("An Exception while navigating back to feed card");
			Assert.fail("An Exception while navigating back to feed card");
		}

	}
	
	/**
	 * Navigate Back To FeedCard
	 */
	public static void navigateBackToFeedCardAndroid() {
		try {
			By byAlertCenter = MobileBy.AccessibilityId("Go to Alerts and Notifications");
			MobileElement alertCenter = Ad.findElement(byAlertCenter);
			System.out
					.println("Alert Center Bell icon found after app launch, hence no need to perform navigate back to feedcard");
			logStep("Alert Center Bell icon found after app launch, hence no need to perform navigate back to feedcard");
		}catch (Exception e) {
			try {
				Ad.findElementByAccessibilityId("Navigate up").click();
				System.out.println("Navigating back to feed card");
				logStep("Navigating back to feed card");
				TestBase.waitForMilliSeconds(4000);
			} catch (Exception e1) {
				try {
					Ad.findElementByAccessibilityId("Back").click();
					System.out.println("Navigating back to feed card");
					logStep("Navigating back to feed card");
					TestBase.waitForMilliSeconds(4000);
				} catch (Exception e2) {
					System.out.println("An Exception while navigating back to feed card");
					logStep("An Exception while navigating back to feed card");
					Assert.fail("An Exception while navigating back to feed card");
				}
			}	
		}
		

	}

	/**
	 * Click on Continue
	 */
	public static void click_Continue() {
		attachScreen();
		try {
			Ad.findElementByAccessibilityId("Confirm").click();
			TestBase.waitForMilliSeconds(2000);
		} catch (Exception e) {
			try {
				Ad.findElementByAccessibilityId("Continue").click();
				TestBase.waitForMilliSeconds(2000);
			} catch (Exception e1) {
				System.out.println("An Exception while clicking on Continue button");
				logStep("An Exception while clicking on Continue button");
				Assert.fail("An Exception while clicking on Continue button");
			}

		}

	}

	/**
	 * Click on Cancel
	 */
	public static void click_Cancel() {
		try {
			Ad.findElementByAccessibilityId("Cancel").click();
			TestBase.waitForMilliSeconds(2000);
		} catch (Exception e) {
			System.out.println("An Exception while clicking on Cancel button");
			logStep("An Exception while clicking on Cancel button");
			Assert.fail("An Exception while clicking on Cancel button");
		}

	}

	/**
	 * Handle Interstitial Ad
	 */
	public static void handle_Interstitial_Ad() {
		MobileElement interstitalAd = null;
		TouchAction ta = new TouchAction(Ad);
		interstitialFqCapStrtTime = 0L;

		try {
			TestBase.waitForMilliSeconds(2000);
			interstitalAd = Ad.findElementByAccessibilityId("CLOSE");
			interStitialDisplayed = true;
			attachScreen();
			boolean isInterstitialClosed = false;
			for (int i = 0; i <= 10; i++) {

				try {
					if (interstitalAd.isDisplayed()) {
						if (interstitalAd.isEnabled()) {
							interstitalAd.click();
							isInterstitialClosed = true;
							TestBase.waitForMilliSeconds(2000);
						} else {

						}

					} else {
						break;
					}
				} catch (Exception e) {
					if (isInterstitialClosed) {
						System.out.println("Interstitial Ad is Closed");
						logStep("Interstitial Ad is Closed");
						break;
					} else {
						System.out.println("Interstitial Ad cannot be Closed, please check object properties");
						logStep("Interstitial Ad cannot be Closed, please check object properties");
					}
				}

			}
			interstitialFqCapStrtTime = System.nanoTime();
			// interStitialChecked = true;
			System.out.println("Interstitial Ad displayed on the  card and closed");
			logStep("Interstitial Ad displayed on the  card and closed");
		} catch (Exception e2) {

			try {
				TestBase.waitForMilliSeconds(2000);
				interstitalAd = Ad.findElementByAccessibilityId("NO THANKS");
				interStitialDisplayed = true;
				attachScreen();
				boolean isInterstitialClosed = false;
				for (int i = 0; i <= 10; i++) {

					try {
						if (interstitalAd.isDisplayed()) {
							if (interstitalAd.isEnabled()) {
								interstitalAd.click();
								isInterstitialClosed = true;
								TestBase.waitForMilliSeconds(2000);
							} else {

							}

						} else {
							break;
						}
					} catch (Exception e) {
						if (isInterstitialClosed) {
							System.out.println("Interstitial Ad is Closed");
							logStep("Interstitial Ad is Closed");
							break;
						} else {
							System.out.println("Interstitial Ad cannot be Closed, please check object properties");
							logStep("Interstitial Ad cannot be Closed, please check object properties");
						}
					}

				}
				interstitialFqCapStrtTime = System.nanoTime();
				// interStitialChecked = true;
				System.out.println("Interstitial Ad displayed on the  card and closed");
				logStep("Interstitial Ad displayed on the  card and closed");
			} catch (Exception e1) {
				try {
					TestBase.waitForMilliSeconds(2000);
					interstitalAd = Ad.findElementByAccessibilityId("Close");
					interStitialDisplayed = true;
					attachScreen();
					boolean isInterstitialClosed = false;
					for (int i = 0; i <= 10; i++) {

						try {
							if (interstitalAd.isDisplayed()) {
								if (interstitalAd.isEnabled()) {
									interstitalAd.click();
									isInterstitialClosed = true;
									TestBase.waitForMilliSeconds(2000);
								} else {

								}

							} else {
								break;
							}
						} catch (Exception e) {
							if (isInterstitialClosed) {
								System.out.println("Interstitial Ad is Closed");
								logStep("Interstitial Ad is Closed");
								break;
							} else {
								System.out.println("Interstitial Ad cannot be Closed, please check object properties");
								logStep("Interstitial Ad cannot be Closed, please check object properties");
							}
						}

					}
					interstitialFqCapStrtTime = System.nanoTime();
					// interStitialChecked = true;
					System.out.println("Interstitial Ad displayed on the  card and closed");
					logStep("Interstitial Ad displayed on the  card and closed");
				} catch (Exception e3) {
					try {
						TestBase.waitForMilliSeconds(2000);
						interstitalAd = Ad.findElementByAccessibilityId("Close Advertisement");
						/*
						 * add below two lines to capture page source of interstitial, delete later
						 */
						System.out.println(Ad.getPageSource());
						logStep(Ad.getPageSource());
						System.out.println("Close Advertisement is displayed: " + interstitalAd.isDisplayed());
						logStep("Close Advertisement is displayed: " + interstitalAd.isDisplayed());
						System.out.println("Close Advertisement is enabled: " + interstitalAd.isEnabled());
						logStep("Close Advertisement is enabled: " + interstitalAd.isEnabled());
						interStitialDisplayed = true;
						attachScreen();
						//WebDriverWait wait = new WebDriverWait(Ad, 120);
						//wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("Close Advertisement")));
						if (interstitalAd.getAttribute("enabled").equalsIgnoreCase("false") && interstitalAd.getAttribute("visible").equalsIgnoreCase("false")) {
							System.out.println("Since Element is neither enabled nor visible, trying with Javascript Executor");
							logStep("Since Element is neither enabled nor visible, trying with Javascript Executor");
							try {
								Point location = interstitalAd.getLocation();
								ta.press(PointOption.point(location.getX(), location.getY())).release().perform();
								ta.tap(PointOption.point(location.getX(), location.getY())).release().perform();
								//JavascriptExecutor js = (JavascriptExecutor) Ad;
								//js.executeScript("arguments[0].style.border='4px solid yellow'", interstitalAd);
								//js.executeScript("arguments[0].click()", interstitalAd);
								System.out.println("Interstitial Ad is Closed by Javascript Executor");
								logStep("Interstitial Ad is Closed by Javascript Executor");
							} catch (Exception e) {
								System.out.println("Exception thrown while using Javascript executor");
								logStep("Exception thrown while using Javascript executor");
								interstitalAd.click();
								e.printStackTrace();
																
							}
							
						} else if (!interstitalAd.isDisplayed() && !interstitalAd.isEnabled()) {
							ta.tap(PointOption.point(29, 22))
									.waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).perform().release();
							System.out.println("Inside If Loop");
							logStep("Inside If Loop");
						} else {
							boolean isInterstitialClosed = false;
							for (int i = 0; i <= 10; i++) {

								try {
									if (interstitalAd.isDisplayed()) {
										if (interstitalAd.isEnabled()) {
											interstitalAd.click();
											isInterstitialClosed = true;
											TestBase.waitForMilliSeconds(2000);
										} else {

										}

									} else {
										break;
									}
								} catch (Exception e) {
									if (isInterstitialClosed) {
										System.out.println("Interstitial Ad is Closed");
										logStep("Interstitial Ad is Closed");
										break;
									} else {
										System.out.println(
												"Interstitial Ad cannot be Closed, please check object properties");
										logStep("Interstitial Ad cannot be Closed, please check object properties");
										try {
											JavascriptExecutor js = (JavascriptExecutor) Ad;
											js.executeScript("arguments[0].style.border='4px solid yellow'", interstitalAd);
											js.executeScript("arguments[0].click();", interstitalAd);
											System.out.println("Interstitial Ad is Closed by Javascript Executor");
											logStep("Interstitial Ad is Closed by Javascript Executor");
										} catch (Exception e4) {
											System.out.println("Exception thrown while using Javascript executor");
											logStep("Exception thrown while using Javascript executor");
										}
									}
								}

							}
						}
						interstitialFqCapStrtTime = System.nanoTime();
						System.out.println("Checking for existance of Interstitial ad even after closed");
						logStep("Checking for existance of Interstitial ad even after closed");

						try {
							if (interstitalAd.isDisplayed() || interstitalAd.isEnabled()) {
								System.out.println("Interstitial Ad displayed after closed");
								logStep("Interstitial Ad displayed after closed");
								Ad.findElementByAccessibilityId("Close").click();
								interstitialFqCapStrtTime = System.nanoTime();
							} else  {
								System.out.println("Interstitial Ad displayed on the  card and closed");
								logStep("Interstitial Ad displayed on the  card and closed");
							}

						} catch (Exception e) {

						}

						// interStitialChecked = true;
						//System.out.println("Interstitial Ad displayed on the  card and closed");
						//logStep("Interstitial Ad displayed on the  card and closed");
						/**
						 * Sometimes interstitia not able to closed due to Enable is false.. hence final check if interstitial still on the screen
						 * if true, kill and launching the app
						 */
						
						try {
							interstitalAd = Ad.findElementByAccessibilityId("Close Advertisement");
							/*System.out.println("Interstitial Ad not handled, hence kill and launch the app");
							logStep("Interstitial Ad not handled, hence kill and launch the app");
							Functions.close_launchApp();*/
							System.out.println("Interstitial Ad still displayed");
							logStep("Interstitial Ad still displayed");
						} catch(Exception e) {
							System.out.println("Interstitial Ad handled");
							logStep("Interstitial Ad handled");
						}
					} catch (Exception e) {
						interStitialDisplayed = false;
						System.out.println("Interstitial Ad not displayed on the  card");
						logStep("Interstitial Ad not displayed on the  card");
						attachScreen();
					}

				}

			}

		}

		System.out.println("Taking Screenshot after handling Interstitial Ad");
		logStep("Taking Screenshot after handling Interstitial Ad");
		interStitialAdcallSuccessful = false;
		attachScreen();
	}
	
	/**
	 * Handle Interstitial Ad
	 */
	public static void handle_Interstitial_AdAndroid() {
		MobileElement interstitalAd = null;
		TouchAction ta = new TouchAction(Ad);
		interstitialFqCapStrtTime = 0L;

		try {
			TestBase.waitForMilliSeconds(2000);
			interstitalAd = Ad.findElementByXPath("//android.widget.TextView[@text=\"Close\"]");
			interStitialDisplayed = true;
			attachScreen();
			boolean isInterstitialClosed = false;
			for (int i = 0; i <= 10; i++) {

				try {
					if (interstitalAd.isDisplayed()) {
						if (interstitalAd.isEnabled()) {
							interstitalAd.click();
							isInterstitialClosed = true;
							TestBase.waitForMilliSeconds(2000);
						} else {

						}

					} else {
						break;
					}
				} catch (Exception e) {
					if (isInterstitialClosed) {
						System.out.println("Interstitial Ad is Closed");
						logStep("Interstitial Ad is Closed");
						break;
					} else {
						System.out.println("Interstitial Ad cannot be Closed, please check object properties");
						logStep("Interstitial Ad cannot be Closed, please check object properties");
					}
				}

			}
			interstitialFqCapStrtTime = System.nanoTime();
			// interStitialChecked = true;
			System.out.println("Interstitial Ad displayed on the  card and closed");
			logStep("Interstitial Ad displayed on the  card and closed");
		} catch (Exception e2) {

			try {
				TestBase.waitForMilliSeconds(2000);
				interstitalAd = Ad.findElementByAccessibilityId("NO THANKS");
				interStitialDisplayed = true;
				attachScreen();
				boolean isInterstitialClosed = false;
				for (int i = 0; i <= 10; i++) {

					try {
						if (interstitalAd.isDisplayed()) {
							if (interstitalAd.isEnabled()) {
								interstitalAd.click();
								isInterstitialClosed = true;
								TestBase.waitForMilliSeconds(2000);
							} else {

							}

						} else {
							break;
						}
					} catch (Exception e) {
						if (isInterstitialClosed) {
							System.out.println("Interstitial Ad is Closed");
							logStep("Interstitial Ad is Closed");
							break;
						} else {
							System.out.println("Interstitial Ad cannot be Closed, please check object properties");
							logStep("Interstitial Ad cannot be Closed, please check object properties");
						}
					}

				}
				interstitialFqCapStrtTime = System.nanoTime();
				// interStitialChecked = true;
				System.out.println("Interstitial Ad displayed on the  card and closed");
				logStep("Interstitial Ad displayed on the  card and closed");
			} catch (Exception e1) {
				try {
					TestBase.waitForMilliSeconds(2000);
					interstitalAd = Ad.findElementByAccessibilityId("Interstitial close button");
					interStitialDisplayed = true;
					attachScreen();
					boolean isInterstitialClosed = false;
					for (int i = 0; i <= 10; i++) {

						try {
							if (interstitalAd.isDisplayed()) {
								if (interstitalAd.isEnabled()) {
									interstitalAd.click();
									isInterstitialClosed = true;
									TestBase.waitForMilliSeconds(2000);
								} else {

								}

							} else {
								break;
							}
						} catch (Exception e) {
							if (isInterstitialClosed) {
								System.out.println("Interstitial Ad is Closed");
								logStep("Interstitial Ad is Closed");
								break;
							} else {
								System.out.println("Interstitial Ad cannot be Closed, please check object properties");
								logStep("Interstitial Ad cannot be Closed, please check object properties");
							}
						}

					}
					interstitialFqCapStrtTime = System.nanoTime();
					// interStitialChecked = true;
					System.out.println("Interstitial Ad displayed on the  card and closed");
					logStep("Interstitial Ad displayed on the  card and closed");
				} catch (Exception e3) {
					try {
						TestBase.waitForMilliSeconds(2000);
						interstitalAd = Ad.findElementByXPath("//android.widget.TextView[@resource-id=\"close-button-icon\"]");
						/*
						 * add below two lines to capture page source of interstitial, delete later
						 */
						System.out.println(Ad.getPageSource());
						logStep(Ad.getPageSource());
						System.out.println("Close Advertisement is displayed: " + interstitalAd.isDisplayed());
						logStep("Close Advertisement is displayed: " + interstitalAd.isDisplayed());
						System.out.println("Close Advertisement is enabled: " + interstitalAd.isEnabled());
						logStep("Close Advertisement is enabled: " + interstitalAd.isEnabled());
						interStitialDisplayed = true;
						attachScreen();
						//WebDriverWait wait = new WebDriverWait(Ad, 120);
						//wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("Close Advertisement")));
						if (interstitalAd.getAttribute("enabled").equalsIgnoreCase("false") && interstitalAd.getAttribute("visible").equalsIgnoreCase("false")) {
							System.out.println("Since Element is neither enabled nor visible, trying with Javascript Executor");
							logStep("Since Element is neither enabled nor visible, trying with Javascript Executor");
							try {
								Point location = interstitalAd.getLocation();
								ta.press(PointOption.point(location.getX(), location.getY())).release().perform();
								ta.tap(PointOption.point(location.getX(), location.getY())).release().perform();
								//JavascriptExecutor js = (JavascriptExecutor) Ad;
								//js.executeScript("arguments[0].style.border='4px solid yellow'", interstitalAd);
								//js.executeScript("arguments[0].click()", interstitalAd);
								System.out.println("Interstitial Ad is Closed by Javascript Executor");
								logStep("Interstitial Ad is Closed by Javascript Executor");
							} catch (Exception e) {
								System.out.println("Exception thrown while using Javascript executor");
								logStep("Exception thrown while using Javascript executor");
								interstitalAd.click();
								e.printStackTrace();
																
							}
							
						} else if (!interstitalAd.isDisplayed() && !interstitalAd.isEnabled()) {
							ta.tap(PointOption.point(29, 22))
									.waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).perform().release();
							System.out.println("Inside If Loop");
							logStep("Inside If Loop");
						} else {
							boolean isInterstitialClosed = false;
							for (int i = 0; i <= 10; i++) {

								try {
									if (interstitalAd.isDisplayed()) {
										if (interstitalAd.isEnabled()) {
											interstitalAd.click();
											isInterstitialClosed = true;
											TestBase.waitForMilliSeconds(2000);
										} else {

										}

									} else {
										break;
									}
								} catch (Exception e) {
									if (isInterstitialClosed) {
										System.out.println("Interstitial Ad is Closed");
										logStep("Interstitial Ad is Closed");
										break;
									} else {
										System.out.println(
												"Interstitial Ad cannot be Closed, please check object properties");
										logStep("Interstitial Ad cannot be Closed, please check object properties");
										try {
											JavascriptExecutor js = (JavascriptExecutor) Ad;
											js.executeScript("arguments[0].style.border='4px solid yellow'", interstitalAd);
											js.executeScript("arguments[0].click();", interstitalAd);
											System.out.println("Interstitial Ad is Closed by Javascript Executor");
											logStep("Interstitial Ad is Closed by Javascript Executor");
										} catch (Exception e4) {
											System.out.println("Exception thrown while using Javascript executor");
											logStep("Exception thrown while using Javascript executor");
										}
									}
								}

							}
						}
						interstitialFqCapStrtTime = System.nanoTime();
						System.out.println("Checking for existance of Interstitial ad even after closed");
						logStep("Checking for existance of Interstitial ad even after closed");

						try {
							if (interstitalAd.isDisplayed() || interstitalAd.isEnabled()) {
								System.out.println("Interstitial Ad displayed after closed");
								logStep("Interstitial Ad displayed after closed");
								Ad.findElementByAccessibilityId("Close").click();
								interstitialFqCapStrtTime = System.nanoTime();
							} else  {
								System.out.println("Interstitial Ad displayed on the  card and closed");
								logStep("Interstitial Ad displayed on the  card and closed");
							}

						} catch (Exception e) {

						}

						// interStitialChecked = true;
						//System.out.println("Interstitial Ad displayed on the  card and closed");
						//logStep("Interstitial Ad displayed on the  card and closed");
						/**
						 * Sometimes interstitia not able to closed due to Enable is false.. hence final check if interstitial still on the screen
						 * if true, kill and launching the app
						 */
						
						try {
							interstitalAd = Ad.findElementById("close-button-icon");
							/*System.out.println("Interstitial Ad not handled, hence kill and launch the app");
							logStep("Interstitial Ad not handled, hence kill and launch the app");
							Functions.close_launchApp();*/
							System.out.println("Interstitial Ad still displayed");
							logStep("Interstitial Ad still displayed");
						} catch(Exception e) {
							System.out.println("Interstitial Ad handled");
							logStep("Interstitial Ad handled");
						}
					} catch (Exception e) {
						interStitialDisplayed = false;
						System.out.println("Interstitial Ad not displayed on the  card");
						logStep("Interstitial Ad not displayed on the  card");
						attachScreen();
					}

				}

			}

		}

		System.out.println("Taking Screenshot after handling Interstitial Ad");
		logStep("Taking Screenshot after handling Interstitial Ad");
		interStitialAdcallSuccessful = false;
		attachScreen();
	}

	/**
	 * Download and Install the latest version of app, RC or Nightly or Integration,
	 * from Firebase Utility
	 * 
	 * @param appType
	 * @throws Exception
	 */
	public static void launchFirebaseInSafariAndInstallApp(String appType) throws Exception {
		boolean appInstall = false;

		ReadExcelValues.excelValues("Smoke", "Capabilities");
		// service = AppiumDriverLocalService.buildService(new
		// AppiumServiceBuilder().withAppiumJS(new
		// File("/usr/local/lib/node_modules/appium/build/lib/main.js")).withIPAddress("0.0.0.0").usingPort(port));
		DesiredCapabilities capabilities = new DesiredCapabilities();

		// Capabilities for IOS and Android Based on Selected on Device Selection
		capabilities.setCapability(ReadExcelValues.data[1][0], ReadExcelValues.data[1][Cap]);
		capabilities.setCapability(ReadExcelValues.data[2][0], ReadExcelValues.data[2][Cap]);
		capabilities.setCapability(ReadExcelValues.data[3][0], Utils.iOSVersion);
		capabilities.setCapability(ReadExcelValues.data[5][0], ReadExcelValues.data[5][Cap]);
		// capabilities.setCapability(readExcelValues.data[6][0],
		// readExcelValues.data[6][Cap]);
		//capabilities.setCapability("automationName", "XCUITest");
		//capabilities.setCapability(ReadExcelValues.data[7][0], ReadExcelValues.data[7][Cap]);
		capabilities.setCapability(ReadExcelValues.data[7][0], "iPhone "+"("+Utils.iOSVersion+") "+"["+Utils.iPhoneUDID+"]");
		
		// capabilities.setCapability(readExcelValues.data[7][0],"iPhone");
		capabilities.setCapability(ReadExcelValues.data[8][0], ReadExcelValues.data[8][Cap]);
		// capabilities.setCapability("noReset", ResetType);

		// capabilities.setCapability(readExcelValues.data[14][0], true);
		capabilities.setCapability(ReadExcelValues.data[11][0], ReadExcelValues.data[11][Cap]);
		// capabilities.setCapability("launchTimeout", 60000);
		capabilities.setCapability("useNewWDA", true);

		capabilities.setCapability("realDeviceLogger", "/Users/apple/node_modules/deviceconsole");
		capabilities.setCapability("wdaLocalPort", "8403");

		capabilities.setCapability("clearSystemFiles", true);
		capabilities.setCapability("browserName", "safari");
		capabilities.setCapability("startIWDP", true);
		capabilities.setCapability("autoWebview", true);
		capabilities.setCapability("includeSafariInWebviews", true);
		// capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
		// capabilities.setCapability("autoWebview", true);
		capabilities.setCapability("autoAcceptAlerts", true);
		capabilities.setCapability("safariAllowPopups", true);
		capabilities.setCapability("nativeWebTap", true);
		//capabilities.setCapability("nativeWebTapStrict", true);
		// capabilities.setCapability("connectHardwareKeyboard", true);
		capabilities.setCapability("xcodeOrgId", "54VVTD24DT");
		capabilities.setCapability("xcodeSigningId", "iPhone Developer");
		// capabilities.setCapability("safariInitialUrl", "https://myhcl.com/");
		System.out.println("Reading capabilities done");
		// Wait time for Execution of node.js
		// TestBase.waitForMilliSeconds(10000);
		Ad = new IOSDriver(new URL("http://127.0.0.1:4733/wd/hub"), capabilities);

		// WebDriver Ad = new RemoteWebDriver(new URL("http://127.0.0.1:4733/wd/hub"),
		// capabilities);
		// Ad= new IOSDriver<MobileElement>(service, capabilities);
		Ad.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Firebase Launched in Safari");

		try {
			Ad.get("https://appdistribution.firebase.dev/app_distro/projects");
			System.out.println("Firebase app launched");
			TestBase.waitForMilliSeconds(10000);

			// Ad.getContext();
			// Ad.getCurrentUrl();

			String context = Ad.getContext();
			System.out.println("Current Context is: " + context);
			logStep("Current Context is: " + context);
			Ad.context(context);
////*[@class='projects-list']//*[text()='The Weather Channel']
			JavascriptExecutor js = (JavascriptExecutor) Ad;
			
			if (appType.equalsIgnoreCase("Nightly")) {
				/*
				 * MobileElement nightly = (MobileElement) Ad
				 * .findElementByXPath("//*[@class='projects-list']//*[text()='The Weather Nightly']"
				 * );
				 */
				MobileElement nightly = (MobileElement) Ad
						.findElementByXPath("//div//*[text()=' The Weather Nightly ']");
				js.executeScript("arguments[0].scrollIntoView();", nightly);
				nightly.click();
			} else if (appType.equalsIgnoreCase("RC")) {
				/*
				 * MobileElement rc = (MobileElement) Ad
				 * .findElementByXPath("//*[@class='projects-list']//*[text()='The Weather Channel']"
				 * );
				 */
				MobileElement rc = (MobileElement) Ad.findElementByXPath("//div//*[text()=' The Weather Channel ']");
				js.executeScript("arguments[0].scrollIntoView();", rc);
				rc.click();
			} else if (appType.equalsIgnoreCase("Integration")) {
				/*
				 * MobileElement rc = (MobileElement) Ad
				 * .findElementByXPath("//*[@class='projects-list']//*[text()='The Weather Channel']"
				 * );
				 */
				MobileElement integration = (MobileElement) Ad
						.findElementByXPath("//div//*[text()=' The Weather Integration ']");
				js.executeScript("arguments[0].scrollIntoView();", integration);
				integration.click();
			}

			TestBase.waitForMilliSeconds(5000);
			try {
				/*
				 * MobileElement Build_Name = (MobileElement) Ad.findElementByXPath(
				 * "//*[@class='latest-badge']//preceding::div[@class='release-header']/div[@class='version-string']"
				 * );
				 */

				MobileElement Build_Main = (MobileElement) Ad
						.findElementByXPath("//div[text()='Latest']/preceding-sibling::div/span[1]");

				// div[text()='Latest']//preceding::div[text()='Version']/span[2]
				// BuildName = Build_Name.getText();
				String buildmain = Build_Main.getText();

				MobileElement Build_Sub = (MobileElement) Ad
						.findElementByXPath("//div[text()='Latest']/preceding-sibling::div/span[2]");
				String buildsub = Build_Sub.getText();
				BuildName = buildmain.concat(buildsub);

				/*
				 * System.out.println("Latest Version available to download is: " + BuildName);
				 * logStep("Latest Version available to download is: " + BuildName);
				 */
				System.out.println("Latest Version available to download is: " + BuildName);
				logStep("Latest Version available to download is: " + BuildName);
				attachScreen();
				/*
				 * Ad.findElementByXPath(
				 * "//*[@class='latest-badge']//following-sibling::div[@class='project-release-metadata']//div[@class='install']/a")
				 * .click();
				 */
				//Ad.findElementByXPath("//div[text()='Latest']/parent::div/following-sibling::ipa-download/a").click();
				MobileElement eleToClick = Ad.findElementByXPath("//div[text()='Latest']/parent::div/following-sibling::ipa-download/a");
				js.executeScript("arguments[0].style.border='4px solid yellow'", eleToClick);
				js.executeScript("arguments[0].click();", eleToClick);
				//eleToClick.click();
				//MobileElement download = (MobileElement) Ad.findElementByXPath("//div[text()='Latest']/parent::div/following-sibling::ipa-download/a");
				//download.click();
				// TestBase.waitForMilliSeconds(5000);
			} catch (Exception e) {
				System.out.println("An Exception while clicking on Download Button");
				logStep("An Exception while clicking on Download Button");
				e.printStackTrace();
			}

		/*	try {
				attachScreen();
				// TestBase.waitForMilliSeconds(10000);
				// myWait.until(ExpectedConditions.alertIsPresent());
				// Alert installalert = Ad.switchTo().alert();
				Ad.switchTo().alert();
				System.out.println("Tried to handle alert in first try");
				logStep("Tried to handle alert in first try");
				// appInstall = true;
				try {
					TestBase.waitForMilliSeconds(10000);
					// installalert = Ad.switchTo().alert();
					Ad.switchTo().alert();
					System.out.println("In Nested try");
					logStep("In Nested try");
					appInstall = true;
				} catch (Exception d) {
					System.out.println("Alert might have handled in First Try");
					logStep("Alert might have handled in First Try");
					attachScreen();
					appInstall = true;
				}
			} catch (Exception e1) {

				try {
					System.out.println("An Exception in first try");
					logStep("An Exception in first try");
					attachScreen();
					TestBase.waitForMilliSeconds(10000);
					// Alert installalert = Ad.switchTo().alert();
					Ad.switchTo().alert();
					System.out.println("Tried to handle alert in second try");
					logStep("Tried to handle alert in second try");
					// appInstall = true;
					try {
						TestBase.waitForMilliSeconds(10000);
						// installalert = Ad.switchTo().alert();
						Ad.switchTo().alert();
						System.out.println("In Nested try of second try");
						logStep("In Nested try of second try");
						attachScreen();
						appInstall = true;
					} catch (Exception d) {
						System.out.println("Alert might have handled in second Try");
						logStep("Alert might have handled in second Try");
						appInstall = true;
					}
				} catch (Exception e2) {
					try {
						TestBase.waitForMilliSeconds(10000);
						System.out.println("An Exception in second try");
						logStep("An Exception in second try");
						// Alert installalert = Ad.switchTo().alert();
						attachScreen();
						Ad.switchTo().alert();
						// installalert.accept();
						System.out.println("Alert handled in Third try");
						logStep("Alert handled in Third try");
						appInstall = true;

						try {
							// Ad.switchTo().alert().accept();;
							TestBase.waitForMilliSeconds(10000);
							Ad.switchTo().alert();
							System.out.println("In nested try of third try");
							logStep("In nested try of third try");
							attachScreen();
							appInstall = true;
						} catch (Exception e) {
							System.out.println("Alert might have handled in third Try");
							logStep("Alert might have handled in third Try");
							attachScreen();
							appInstall = true;
						}

					} catch (Exception e3) {
						System.out.println("An Exception in third try");
						logStep("An Exception in third try");
						Alert installalert = Ad.switchTo().alert();
						installalert.accept();
						System.out.println("Alert handled in second catch");
						logStep("Alert handled in second catch");
						attachScreen();
						appInstall = true;
					}

				}

			}*/

		} catch (Exception e) {
			System.out.println("There may be an exception while installing the app");
			logStep("There may be an exception while installing the app");
			e.printStackTrace();
			attachScreen();
		}

		FileOutputStream fos = new FileOutputStream(
				new File(System.getProperty("user.dir") + "/JenkinsEmailConfig.Properties"));

		properties.setProperty("AppVersion", BuildName);
		properties.store(fos, "App Version read from firebase and updated");
		fos.close();
		// Ad1.close();
		// Ad1.closeApp();
	/*	if (appInstall) {
			System.out.println("Successfully clicked on Install button, app being installed");
			logStep("Successfully clicked on Install button, app being installed");
		} else {
			System.out.println("An Exception while clicking on Install button");
			logStep("An Exception while clicking on Install button");
			// Assert.fail("An Exception while clicking on Install button");
		}*/
		TestBase.waitForMilliSeconds(30000);
	}

	/**
	 * Download and Install the specific app version, of RC or Nightly or
	 * Integration, from Firebase Utility
	 * 
	 * @param appType
	 * @param buildVersion
	 * @throws Exception
	 */
	public static void launchFirebaseInSafariAndInstallApp(String appType, String buildVersion) throws Exception {
		boolean appInstall = false;

		ReadExcelValues.excelValues("Smoke", "Capabilities");
		// service = AppiumDriverLocalService.buildService(new
		// AppiumServiceBuilder().withAppiumJS(new
		// File("/usr/local/lib/node_modules/appium/build/lib/main.js")).withIPAddress("0.0.0.0").usingPort(port));
		DesiredCapabilities capabilities = new DesiredCapabilities();

		// Capabilities for IOS and Android Based on Selected on Device Selection
		capabilities.setCapability(ReadExcelValues.data[1][0], ReadExcelValues.data[1][Cap]);
		capabilities.setCapability(ReadExcelValues.data[2][0], ReadExcelValues.data[2][Cap]);
		capabilities.setCapability(ReadExcelValues.data[3][0], Utils.iOSVersion);
		capabilities.setCapability(ReadExcelValues.data[5][0], ReadExcelValues.data[5][Cap]);
		// capabilities.setCapability(readExcelValues.data[6][0],
		// readExcelValues.data[6][Cap]);
		//capabilities.setCapability("automationName", "XCUITest");
		//capabilities.setCapability(ReadExcelValues.data[7][0], ReadExcelValues.data[7][Cap]);
		capabilities.setCapability(ReadExcelValues.data[7][0], "iPhone "+"("+Utils.iOSVersion+") "+"["+Utils.iPhoneUDID+"]");
		// capabilities.setCapability(readExcelValues.data[7][0],"iPhone");
		capabilities.setCapability(ReadExcelValues.data[8][0], ReadExcelValues.data[8][Cap]);
		// capabilities.setCapability("noReset", ResetType);

		// capabilities.setCapability(readExcelValues.data[14][0], true);
		capabilities.setCapability(ReadExcelValues.data[11][0], ReadExcelValues.data[11][Cap]);
		// capabilities.setCapability("launchTimeout", 60000);
		capabilities.setCapability("useNewWDA", true);

		capabilities.setCapability("realDeviceLogger", "/Users/apple/node_modules/deviceconsole");
		capabilities.setCapability("wdaLocalPort", "8403");

		capabilities.setCapability("clearSystemFiles", true);
		capabilities.setCapability("browserName", "safari");
		capabilities.setCapability("startIWDP", true);
		capabilities.setCapability("autoWebview", true);
		capabilities.setCapability("includeSafariInWebviews", true);
		// capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
		// capabilities.setCapability("autoWebview", true);
		capabilities.setCapability("autoAcceptAlerts", true);
		capabilities.setCapability("safariAllowPopups", true);
		capabilities.setCapability("nativeWebTap", true);
		//capabilities.setCapability("nativeWebTapStrict", true);
		// capabilities.setCapability("connectHardwareKeyboard", true);
		capabilities.setCapability("xcodeOrgId", "54VVTD24DT");
		capabilities.setCapability("xcodeSigningId", "iPhone Developer");
		// capabilities.setCapability("safariInitialUrl", "https://myhcl.com/");
		System.out.println("Reading capabilities done");
		// Wait time for Execution of node.js
		// TestBase.waitForMilliSeconds(10000);
		Ad = new IOSDriver(new URL("http://127.0.0.1:4733/wd/hub"), capabilities);

		// WebDriver Ad = new RemoteWebDriver(new URL("http://127.0.0.1:4733/wd/hub"),
		// capabilities);
		// Ad= new IOSDriver<MobileElement>(service, capabilities);
		Ad.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Firebase Launched in Safari");

		try {
			Ad.get("https://appdistribution.firebase.dev/app_distro/projects");
			System.out.println("Firebase app launched");
			TestBase.waitForMilliSeconds(10000);

			// Ad.getContext();
			// Ad.getCurrentUrl();

			String context = Ad.getContext();
			System.out.println("Current Context is: " + context);
			logStep("Current Context is: " + context);
			Ad.context(context);
			//// *[@class='projects-list']//*[text()='The Weather Channel']
			JavascriptExecutor js = (JavascriptExecutor) Ad;
			if (appType.equalsIgnoreCase("Nightly")) {
				/*
				 * MobileElement nightly = (MobileElement) Ad
				 * .findElementByXPath("//*[@class='projects-list']//*[text()='The Weather Nightly']"
				 * );
				 */
				MobileElement nightly = (MobileElement) Ad
						.findElementByXPath("//div//*[text()=' The Weather Nightly ']");
				js.executeScript("arguments[0].scrollIntoView();", nightly);
				nightly.click();
			} else if (appType.equalsIgnoreCase("RC")) {
				/*
				 * MobileElement rc = (MobileElement) Ad
				 * .findElementByXPath("//*[@class='projects-list']//*[text()='The Weather Channel']"
				 * );
				 */
				MobileElement rc = (MobileElement) Ad.findElementByXPath("//div//*[text()=' The Weather Channel ']");
				js.executeScript("arguments[0].scrollIntoView();", rc);
				rc.click();
			} else if (appType.equalsIgnoreCase("Integration")) {
				/*
				 * MobileElement rc = (MobileElement) Ad
				 * .findElementByXPath("//*[@class='projects-list']//*[text()='The Weather Channel']"
				 * );
				 */
				MobileElement integration = (MobileElement) Ad
						.findElementByXPath("//div//*[text()=' The Weather Integration ']");
				js.executeScript("arguments[0].scrollIntoView();", integration);
				integration.click();
			}

			TestBase.waitForMilliSeconds(5000);
			try {
				/*
				 * MobileElement Build_Name = (MobileElement) Ad.findElementByXPath(
				 * "//*[@class='latest-badge']//preceding::div[@class='release-header']/div[@class='version-string']"
				 * );
				 */

				MobileElement Build_Main = (MobileElement) Ad
						.findElementByXPath("//div[text()='Latest']/preceding-sibling::div/span[1]");

				// div[text()='Latest']//preceding::div[text()='Version']/span[2]
				// BuildName = Build_Name.getText();
				String buildmain = Build_Main.getText();

				MobileElement Build_Sub = (MobileElement) Ad
						.findElementByXPath("//div[text()='Latest']/preceding-sibling::div/span[2]");
				String buildsub = Build_Sub.getText();
				BuildName = buildmain.concat(buildsub);
				/*
				 * System.out.println("Latest Version available to download is: " + BuildName);
				 * logStep("Latest Version available to download is: " + BuildName);
				 */
				System.out.println("Latest Version available to download is: " + BuildName);
				logStep("Latest Version available to download is: " + BuildName);
				/*
				 * Ad.findElementByXPath(
				 * "//*[@class='latest-badge']//following-sibling::div[@class='project-release-metadata']//div[@class='install']/a")
				 * .click();
				 */

				if (BuildName.equalsIgnoreCase(buildVersion)) {
					attachScreen();
					MobileElement eleToClick = Ad.findElementByXPath("//div[text()='Latest']/parent::div/following-sibling::ipa-download/a");
					js.executeScript("arguments[0].style.border='4px solid yellow'", eleToClick);
					js.executeScript("arguments[0].click();", eleToClick);
					//eleToClick.click();
									
				} else {
					List<MobileElement> buildsAvailable = Ad.findElementsByXPath(
							"//div[text()='Latest']/parent::div/parent::div/parent::release-row/parent::div/following-sibling::div");

					for (MobileElement currentBuild : buildsAvailable) {
						Build_Main = currentBuild.findElementByXPath(".//div[@class='version']/span[1]");
						buildmain = Build_Main.getText();
						Build_Sub = currentBuild.findElementByXPath(".//div[@class='version']/span[2]");
						buildsub = Build_Sub.getText();
						BuildName = buildmain.concat(buildsub);

						System.out.println("Current Version is: " + BuildName);
						logStep("Current Version is: " + BuildName);
						if (BuildName.equalsIgnoreCase(buildVersion)) {
							attachScreen();
							//JavascriptExecutor js = (JavascriptExecutor) Ad;
							// MobileElement element = currentBuild.findElementByXPath(".//ipa-download/a");
							js.executeScript("arguments[0].scrollIntoView();", currentBuild);
							TestBase.waitForMilliSeconds(5000);
							//currentBuild.click();
							js.executeScript("window.scrollBy(0,-100)");
							currentBuild.findElementByXPath(".//div[@class='version']").click();
							TestBase.waitForMilliSeconds(5000);
							//js.executeScript("window.scrollBy(0,-100)");
							MobileElement eleToClick = currentBuild.findElementByXPath(".//ipa-download/a");
							js.executeScript("arguments[0].style.border='4px solid yellow'", eleToClick);
							TestBase.waitForMilliSeconds(2000);
							js.executeScript("arguments[0].click();", eleToClick);
							break;
						}
					}
					
				/*	for (int i = 0; i < buildsAvailable.size(); i++ ) {
						MobileElement currentBuild = buildsAvailable.get(i);
						Build_Main = currentBuild.findElementByXPath(".//div[@class='version']/span[1]");
						buildmain = Build_Main.getText();
						Build_Sub = currentBuild.findElementByXPath(".//div[@class='version']/span[2]");
						buildsub = Build_Sub.getText();
						BuildName = buildmain.concat(buildsub);

						System.out.println("Current Version is: " + BuildName);
						logStep("Current Version is: " + BuildName);
						if (BuildName.equalsIgnoreCase(buildVersion)) {
							JavascriptExecutor js = (JavascriptExecutor) Ad;
							// MobileElement element = currentBuild.findElementByXPath(".//ipa-download/a");
							js.executeScript("arguments[0].scrollIntoView();", currentBuild);
						//	js.executeScript("window.scrollBy(0,-100)");
							TestBase.waitForMilliSeconds(5000);
							//js.executeScript("arguments[0].click();", currentBuild);
							//currentBuild.findElementByXPath("./release/div").click();
							//currentBuild.click();
							buildsAvailable = Ad.findElementsByXPath(
									"//div[text()='Latest']/parent::div/parent::div/parent::release/parent::div/following-sibling::div");
							currentBuild = buildsAvailable.get(i);
							//currentBuild.click();
							Build_Main = currentBuild.findElementByXPath(".//div[@class='version']/span[1]");
							buildmain = Build_Main.getText();
							Build_Sub = currentBuild.findElementByXPath(".//div[@class='version']/span[2]");
							buildsub = Build_Sub.getText();
							BuildName = buildmain.concat(buildsub);

							System.out.println("Current Version is: " + BuildName);
							logStep("Current Version is: " + BuildName);
							System.out.println("is element enabled: "+currentBuild.isEnabled());
							System.out.println("is element displayd: "+currentBuild.isDisplayed());
							//currentBuild.click();
							//System.out.println("Clicked on Current builld click");
							currentBuild.findElementByXPath(".//div[@class='version']").click();
							
							
						//	js.executeScript("arguments[0].click();", plaidLinkBankAccountContinue);
							System.out.println("Clicked on Current builld version click");
							TestBase.waitForMilliSeconds(2000);
							js.executeScript("window.scrollBy(0,-100)");
							buildsAvailable = Ad.findElementsByXPath(
									"//div[text()='Latest']/parent::div/parent::div/parent::release/parent::div/following-sibling::div");
							currentBuild = buildsAvailable.get(i);
							System.out.println("is element enabled: "+currentBuild.isEnabled());
							System.out.println("is element displayd: "+currentBuild.isDisplayed());
							MobileElement eleToClick = currentBuild.findElementByXPath(".//ipa-download/a");
					//		MobileElement eleToClick = currentBuild.findElementByXPath(".//div[@class='version']/parent::div/following-sibling::ipa-download/a");
							js.executeScript("arguments[0].style.border='4px solid yellow'", eleToClick);
							js.executeScript("arguments[0].click();", eleToClick);
							
							break;
						}
						
					}*/
				}
				// TestBase.waitForMilliSeconds(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}

		/*	try {
				attachScreen();
				// TestBase.waitForMilliSeconds(10000);
				// myWait.until(ExpectedConditions.alertIsPresent());
				// Alert installalert = Ad.switchTo().alert();
				Ad.switchTo().alert();
				System.out.println("Tried to handle alert in first try");
				logStep("Tried to handle alert in first try");
				// appInstall = true;
				try {
					TestBase.waitForMilliSeconds(10000);
					// installalert = Ad.switchTo().alert();
					Ad.switchTo().alert();
					System.out.println("In Nested try");
					logStep("In Nested try");
					appInstall = true;
				} catch (Exception d) {
					System.out.println("Alert might have handled in First Try");
					logStep("Alert might have handled in First Try");
					attachScreen();
					appInstall = true;
				}
			} catch (Exception e1) {

				try {
					System.out.println("An Exception in first try");
					logStep("An Exception in first try");
					attachScreen();
					TestBase.waitForMilliSeconds(10000);
					// Alert installalert = Ad.switchTo().alert();
					Ad.switchTo().alert();
					System.out.println("Tried to handle alert in second try");
					logStep("Tried to handle alert in second try");
					// appInstall = true;
					try {
						TestBase.waitForMilliSeconds(10000);
						// installalert = Ad.switchTo().alert();
						Ad.switchTo().alert();
						System.out.println("In Nested try of second try");
						logStep("In Nested try of second try");
						attachScreen();
						appInstall = true;
					} catch (Exception d) {
						System.out.println("Alert might have handled in second Try");
						logStep("Alert might have handled in second Try");
						appInstall = true;
					}
				} catch (Exception e2) {
					try {
						TestBase.waitForMilliSeconds(10000);
						System.out.println("An Exception in second try");
						logStep("An Exception in second try");
						// Alert installalert = Ad.switchTo().alert();
						attachScreen();
						Ad.switchTo().alert();
						// installalert.accept();
						System.out.println("Alert handled in Third try");
						logStep("Alert handled in Third try");
						appInstall = true;

						try {
							// Ad.switchTo().alert().accept();;
							TestBase.waitForMilliSeconds(10000);
							Ad.switchTo().alert();
							System.out.println("In nested try of third try");
							logStep("In nested try of third try");
							attachScreen();
							appInstall = true;
						} catch (Exception e) {
							System.out.println("Alert might have handled in third Try");
							logStep("Alert might have handled in third Try");
							attachScreen();
							appInstall = true;
						}

					} catch (Exception e3) {
						System.out.println("An Exception in third try");
						logStep("An Exception in third try");
						Alert installalert = Ad.switchTo().alert();
						installalert.accept();
						System.out.println("Alert handled in second catch");
						logStep("Alert handled in second catch");
						attachScreen();
						appInstall = true;
					}

				}

			}*/

		} catch (Exception e) {
			System.out.println("There may be an exception while installing the app");
			logStep("There may be an exception while installing the app");
			e.printStackTrace();
			attachScreen();
		}

		FileOutputStream fos = new FileOutputStream(
				new File(System.getProperty("user.dir") + "/JenkinsEmailConfig.Properties"));

		properties.setProperty("AppVersion", BuildName);
		properties.store(fos, "App Version read from firebase and updated");
		fos.close();
		// Ad1.close();
		// Ad1.closeApp();
		/*if (appInstall) {
			System.out.println("Successfully clicked on Install button, app being installed");
			logStep("Successfully clicked on Install button, app being installed");
		} else {
			System.out.println("An Exception while clicking on Install button");
			logStep("An Exception while clicking on Install button");
			// Assert.fail("An Exception while clicking on Install button");
		}*/
		TestBase.waitForMilliSeconds(30000);
	}

	/**
	 * Download and Install the app from Firebase Utility by reading App Type and
	 * Version values from Jenkins job
	 * 
	 * @throws Exception
	 */

	public static void launchFirebaseInSafariAndInstallApp() throws Exception {
		boolean appInstall = false;
		/*
		 * This method reads values from system properties which are generated by
		 * Jenkins job, as we are passing parameters in jenkins job to specify build
		 * type, build number and whether to run on latest build or not.
		 */
		String appType = System.getProperty("buildType");
		System.out.println("App Type is: " + appType);
		logStep("App Type is: " + appType);
		String buildVersion = System.getProperty("buildNumber");
		System.out.println("Build Version is: " + buildVersion);
		logStep("Build Version is: " + buildVersion);
		String runOnLatest = System.getProperty("runOnLatestBuild");
		System.out.println("Run on Latest is: " + runOnLatest);
		logStep("Run on Latest is: " + runOnLatest);

		ReadExcelValues.excelValues("Smoke", "Capabilities");
		// service = AppiumDriverLocalService.buildService(new
		// AppiumServiceBuilder().withAppiumJS(new
		// File("/usr/local/lib/node_modules/appium/build/lib/main.js")).withIPAddress("0.0.0.0").usingPort(port));
		DesiredCapabilities capabilities = new DesiredCapabilities();

		// Capabilities for IOS and Android Based on Selected on Device Selection
		capabilities.setCapability(ReadExcelValues.data[1][0], ReadExcelValues.data[1][Cap]);
		capabilities.setCapability(ReadExcelValues.data[2][0], ReadExcelValues.data[2][Cap]);
		capabilities.setCapability(ReadExcelValues.data[3][0], Utils.iOSVersion);
		capabilities.setCapability(ReadExcelValues.data[5][0], ReadExcelValues.data[5][Cap]);
		// capabilities.setCapability(readExcelValues.data[6][0],
		// readExcelValues.data[6][Cap]);
		//capabilities.setCapability("automationName", "XCUITest");
		//capabilities.setCapability(ReadExcelValues.data[7][0], ReadExcelValues.data[7][Cap]);
		capabilities.setCapability(ReadExcelValues.data[7][0], "iPhone "+"("+Utils.iOSVersion+") "+"["+Utils.iPhoneUDID+"]");
		// capabilities.setCapability(readExcelValues.data[7][0],"iPhone");
		capabilities.setCapability(ReadExcelValues.data[8][0], ReadExcelValues.data[8][Cap]);
		// capabilities.setCapability("noReset", ResetType);

		// capabilities.setCapability(readExcelValues.data[14][0], true);
		capabilities.setCapability(ReadExcelValues.data[11][0], ReadExcelValues.data[11][Cap]);
		// capabilities.setCapability("launchTimeout", 60000);
		capabilities.setCapability("useNewWDA", true);

		capabilities.setCapability("realDeviceLogger", "/Users/apple/node_modules/deviceconsole");
		capabilities.setCapability("wdaLocalPort", "8403");

		capabilities.setCapability("clearSystemFiles", true);
		capabilities.setCapability("browserName", "safari");
		capabilities.setCapability("startIWDP", true);
		capabilities.setCapability("autoWebview", true);
		capabilities.setCapability("includeSafariInWebviews", true);
		// capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
		// capabilities.setCapability("autoWebview", true);
		capabilities.setCapability("autoAcceptAlerts", true);
		capabilities.setCapability("safariAllowPopups", true);
		capabilities.setCapability("nativeWebTap", true);
		//capabilities.setCapability("nativeWebTapStrict", true);
		// capabilities.setCapability("connectHardwareKeyboard", true);
		capabilities.setCapability("xcodeOrgId", "54VVTD24DT");
		capabilities.setCapability("xcodeSigningId", "iPhone Developer");
		// capabilities.setCapability("safariInitialUrl", "https://myhcl.com/");
		System.out.println("Reading capabilities done");
		// Wait time for Execution of node.js
		// TestBase.waitForMilliSeconds(10000);
		Ad = new IOSDriver(new URL("http://127.0.0.1:4733/wd/hub"), capabilities);

		// WebDriver Ad = new RemoteWebDriver(new URL("http://127.0.0.1:4733/wd/hub"),
		// capabilities);
		// Ad= new IOSDriver<MobileElement>(service, capabilities);
		Ad.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Firebase Launched in Safari");
		
		try {
			Ad.get("https://appdistribution.firebase.dev/app_distro/projects");
			System.out.println("Firebase app launched");
			TestBase.waitForMilliSeconds(10000);

			// Ad.getContext();
			// Ad.getCurrentUrl();

			String context = Ad.getContext();
			System.out.println("Current Context is: " + context);
			logStep("Current Context is: " + context);
			Ad.context(context);
			//// *[@class='projects-list']//*[text()='The Weather Channel']

			boolean executeOnLatest = true;
			if (runOnLatest.equalsIgnoreCase("True")) {
				executeOnLatest = true;
			} else {
				executeOnLatest = false;
			}
			JavascriptExecutor js = (JavascriptExecutor) Ad;
			if (appType.equalsIgnoreCase("Nightly")) {
				/*
				 * MobileElement nightly = (MobileElement) Ad
				 * .findElementByXPath("//*[@class='projects-list']//*[text()='The Weather Nightly']"
				 * );
				 */
				MobileElement nightly = (MobileElement) Ad
						.findElementByXPath("//div//*[text()=' The Weather Nightly ']");
				js.executeScript("arguments[0].scrollIntoView();", nightly);
				nightly.click();
			} else if (appType.equalsIgnoreCase("RC")) {
				/*
				 * MobileElement rc = (MobileElement) Ad
				 * .findElementByXPath("//*[@class='projects-list']//*[text()='The Weather Channel']"
				 * );
				 */
				MobileElement rc = (MobileElement) Ad.findElementByXPath("//div//*[text()=' The Weather Channel ']");
				js.executeScript("arguments[0].scrollIntoView();", rc);
				rc.click();
			} else if (appType.equalsIgnoreCase("Integration")) {
				/*
				 * MobileElement rc = (MobileElement) Ad
				 * .findElementByXPath("//*[@class='projects-list']//*[text()='The Weather Channel']"
				 * );
				 */
				MobileElement integration = (MobileElement) Ad
						.findElementByXPath("//div//*[text()=' The Weather Integration ']");
				js.executeScript("arguments[0].scrollIntoView();", integration);
				integration.click();
			}

			TestBase.waitForMilliSeconds(5000);
			if (executeOnLatest) {
				try {
					/*
					 * MobileElement Build_Name = (MobileElement) Ad.findElementByXPath(
					 * "//*[@class='latest-badge']//preceding::div[@class='release-header']/div[@class='version-string']"
					 * );
					 */

					MobileElement Build_Main = (MobileElement) Ad
							.findElementByXPath("//div[text()='Latest']/preceding-sibling::div/span[1]");

					// div[text()='Latest']//preceding::div[text()='Version']/span[2]
					// BuildName = Build_Name.getText();
					String buildmain = Build_Main.getText();

					MobileElement Build_Sub = (MobileElement) Ad
							.findElementByXPath("//div[text()='Latest']/preceding-sibling::div/span[2]");
					String buildsub = Build_Sub.getText();
					BuildName = buildmain.concat(buildsub);
					/*
					 * System.out.println("Latest Version available to download is: " + BuildName);
					 * logStep("Latest Version available to download is: " + BuildName);
					 */
					System.out.println("Latest Version available to download is: " + BuildName);
					logStep("Latest Version available to download is: " + BuildName);
					/*
					 * Ad.findElementByXPath(
					 * "//*[@class='latest-badge']//following-sibling::div[@class='project-release-metadata']//div[@class='install']/a")
					 * .click();
					 */

					//Ad.findElementByXPath("//div[text()='Latest']/parent::div/following-sibling::ipa-download/a").click();
					MobileElement eleToClick = Ad.findElementByXPath("//div[text()='Latest']/parent::div/following-sibling::ipa-download/a");
					js.executeScript("arguments[0].style.border='4px solid yellow'", eleToClick);
					js.executeScript("arguments[0].click();", eleToClick);
					//eleToClick.click();

					// TestBase.waitForMilliSeconds(5000);
				} catch (Exception e) {

					System.out.println("An Exception while downloading the latest build");
					logStep("An Exception while downloading the latest build");
					e.printStackTrace();

				}
			} else {
				try {
					/*
					 * MobileElement Build_Name = (MobileElement) Ad.findElementByXPath(
					 * "//*[@class='latest-badge']//preceding::div[@class='release-header']/div[@class='version-string']"
					 * );
					 */

					MobileElement Build_Main = (MobileElement) Ad
							.findElementByXPath("//div[text()='Latest']/preceding-sibling::div/span[1]");

					// div[text()='Latest']//preceding::div[text()='Version']/span[2]
					// BuildName = Build_Name.getText();
					String buildmain = Build_Main.getText();

					MobileElement Build_Sub = (MobileElement) Ad
							.findElementByXPath("//div[text()='Latest']/preceding-sibling::div/span[2]");
					String buildsub = Build_Sub.getText();
					BuildName = buildmain.concat(buildsub);
					/*
					 * System.out.println("Latest Version available to download is: " + BuildName);
					 * logStep("Latest Version available to download is: " + BuildName);
					 */
					System.out.println("Latest Version available to download is: " + BuildName);
					logStep("Latest Version available to download is: " + BuildName);
					/*
					 * Ad.findElementByXPath(
					 * "//*[@class='latest-badge']//following-sibling::div[@class='project-release-metadata']//div[@class='install']/a")
					 * .click();
					 */

					if (BuildName.equalsIgnoreCase(buildVersion)) {
						attachScreen();
						MobileElement eleToClick = Ad.findElementByXPath("//div[text()='Latest']/parent::div/following-sibling::ipa-download/a");
						js.executeScript("arguments[0].style.border='4px solid yellow'", eleToClick);
						js.executeScript("arguments[0].click();", eleToClick);
						//eleToClick.click();
					} else {
						List<MobileElement> buildsAvailable = Ad.findElementsByXPath(
								"//div[text()='Latest']/parent::div/parent::div/parent::release-row/parent::div/following-sibling::div");
								 
						for (MobileElement currentBuild : buildsAvailable) {
							Build_Main = currentBuild.findElementByXPath(".//div[@class='version']/span[1]");
							buildmain = Build_Main.getText();
							Build_Sub = currentBuild.findElementByXPath(".//div[@class='version']/span[2]");
							buildsub = Build_Sub.getText();
							BuildName = buildmain.concat(buildsub);

							System.out.println("Current Version is: " + BuildName);
							logStep("Current Version is: " + BuildName);
							if (BuildName.equalsIgnoreCase(buildVersion)) {
								attachScreen();
								//JavascriptExecutor js = (JavascriptExecutor) Ad;
								// MobileElement element = currentBuild.findElementByXPath(".//ipa-download/a");
								js.executeScript("arguments[0].scrollIntoView();", currentBuild);
								TestBase.waitForMilliSeconds(5000);
								//currentBuild.click();
								js.executeScript("window.scrollBy(0,-100)");
								currentBuild.findElementByXPath(".//div[@class='version']").click();
								TestBase.waitForMilliSeconds(5000);
								//js.executeScript("window.scrollBy(0,-100)");
								MobileElement eleToClick = currentBuild.findElementByXPath(".//ipa-download/a");
								js.executeScript("arguments[0].style.border='4px solid yellow'", eleToClick);
								TestBase.waitForMilliSeconds(2000);
								js.executeScript("arguments[0].click();", eleToClick);
								break;
							}
						}
					}
					// TestBase.waitForMilliSeconds(5000);
				} catch (Exception e) {

					System.out.println("An Exception while downloading the specified build");
					logStep("An Exception while downloading the specified build");
					e.printStackTrace();
				}
			}

			/*try {
				attachScreen();
				// TestBase.waitForMilliSeconds(10000);
				// myWait.until(ExpectedConditions.alertIsPresent());
				// Alert installalert = Ad.switchTo().alert();
				Ad.switchTo().alert();
				System.out.println("Tried to handle alert in first try");
				logStep("Tried to handle alert in first try");
				// appInstall = true;
				try {
					TestBase.waitForMilliSeconds(10000);
					// installalert = Ad.switchTo().alert();
					Ad.switchTo().alert();
					System.out.println("In Nested try");
					logStep("In Nested try");
					appInstall = true;
				} catch (Exception d) {
					System.out.println("Alert might have handled in First Try");
					logStep("Alert might have handled in First Try");
					attachScreen();
					appInstall = true;
				}
			} catch (Exception e1) {

				try {
					System.out.println("An Exception in first try");
					logStep("An Exception in first try");
					attachScreen();
					TestBase.waitForMilliSeconds(10000);
					// Alert installalert = Ad.switchTo().alert();
					Ad.switchTo().alert();
					System.out.println("Tried to handle alert in second try");
					logStep("Tried to handle alert in second try");
					// appInstall = true;
					try {
						TestBase.waitForMilliSeconds(10000);
						// installalert = Ad.switchTo().alert();
						Ad.switchTo().alert();
						System.out.println("In Nested try of second try");
						logStep("In Nested try of second try");
						attachScreen();
						appInstall = true;
					} catch (Exception d) {
						System.out.println("Alert might have handled in second Try");
						logStep("Alert might have handled in second Try");
						appInstall = true;
					}
				} catch (Exception e2) {
					try {
						TestBase.waitForMilliSeconds(10000);
						System.out.println("An Exception in second try");
						logStep("An Exception in second try");
						// Alert installalert = Ad.switchTo().alert();
						attachScreen();
						Ad.switchTo().alert();
						// installalert.accept();
						System.out.println("Alert handled in Third try");
						logStep("Alert handled in Third try");
						appInstall = true;

						try {
							// Ad.switchTo().alert().accept();;
							TestBase.waitForMilliSeconds(10000);
							Ad.switchTo().alert();
							System.out.println("In nested try of third try");
							logStep("In nested try of third try");
							attachScreen();
							appInstall = true;
						} catch (Exception e) {
							System.out.println("Alert might have handled in third Try");
							logStep("Alert might have handled in third Try");
							attachScreen();
							appInstall = true;
						}

					} catch (Exception e3) {
						System.out.println("An Exception in third try");
						logStep("An Exception in third try");
						Alert installalert = Ad.switchTo().alert();
						installalert.accept();
						System.out.println("Alert handled in second catch");
						logStep("Alert handled in second catch");
						attachScreen();
						appInstall = true;
					}

				}

			}*/

		} catch (Exception e) {
			System.out.println("There may be an exception while installing the app");
			logStep("There may be an exception while installing the app");
			e.printStackTrace();
			attachScreen();
		}

		FileOutputStream fos = new FileOutputStream(
				new File(System.getProperty("user.dir") + "/JenkinsEmailConfig.Properties"));

		properties.setProperty("AppVersion", BuildName);
		properties.store(fos, "App Version read from firebase and updated");
		fos.close();
		// Ad1.close();
		// Ad1.closeApp();
		/*if (appInstall) {
			System.out.println("Successfully clicked on Install button, app being installed");
			logStep("Successfully clicked on Install button, app being installed");
		} else {
			System.out.println("An Exception while clicking on Install button");
			logStep("An Exception while clicking on Install button");
			// Assert.fail("An Exception while clicking on Install button");
		}*/
		TestBase.waitForMilliSeconds(30000);
	}
	
	/**
	 * Launch IOS Settings App
	 * 
	 * @throws Exception
	 */
	public static void launchiOSSettings() throws Exception {

		ReadExcelValues.excelValues("Smoke", "Capabilities");

		DesiredCapabilities capabilities = new DesiredCapabilities();

		// Capabilities for IOS and Android Based on Selected on Device Selection
		//capabilities.setCapability(ReadExcelValues.data[7][0], "=" + ReadExcelValues.data[7][Cap]);
		capabilities.setCapability(ReadExcelValues.data[7][0], "=" + "iPhone "+"("+Utils.iOSVersion+") "+"["+Utils.iPhoneUDID+"]");
		//capabilities.setCapability(ReadExcelValues.data[11][0], ReadExcelValues.data[11][Cap]);
		capabilities.setCapability(ReadExcelValues.data[11][0], Utils.iPhoneUDID);
		
		capabilities.setCapability(ReadExcelValues.data[2][0], ReadExcelValues.data[2][Cap]);
		capabilities.setCapability(ReadExcelValues.data[3][0], Utils.iOSVersion);
		capabilities.setCapability(ReadExcelValues.data[14][0], true);
		capabilities.setCapability("automationName", "XCUITest");
		capabilities.setCapability("useNewWDA", true);
		capabilities.setCapability("xcodeOrgId", "54VVTD24DT");
		capabilities.setCapability("xcodeSigningId", "iPhone Developer");

		capabilities.setCapability(ReadExcelValues.data[1][0], ReadExcelValues.data[1][Cap]);

		capabilities.setCapability(ReadExcelValues.data[5][0], ReadExcelValues.data[5][Cap]);

		capabilities.setCapability(ReadExcelValues.data[8][0], ReadExcelValues.data[8][Cap]);

		capabilities.setCapability("realDeviceLogger", "/Users/apple/node_modules/deviceconsole");
		capabilities.setCapability("wdaLocalPort", "7402");

		capabilities.setCapability("clearSystemFiles", true);

		capabilities.setCapability("app", "Settings");
		capabilities.setCapability("connectHardwareKeyboard", true);

		System.out.println("Reading capabilities done");

		Ad1 = new IOSDriver(new URL("http://127.0.0.1:4733/wd/hub"), capabilities);

		Ad1.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
//		TestBase.waitForMilliSeconds(10000);
		try {

			Ad1.findElementByXPath("//XCUIElementTypeApplication[@name='Settings']");
			System.out.println("Settings app launched");
			logStep("Settings app launched");
		} catch (Exception e) {
			System.out.println("Settings app not launched");
			logStep("Settings app not launched");
		}
		attachScreen(Ad1);
	}

	/**
	 * Enable the proxy
	 * 
	 * @param currentIPAddress
	 * @param wifiName
	 * @param proxy
	 * @throws Exception
	 */
	public static void enableDeviceProxy(String currentIPAddress, String wifiName, boolean proxy) throws Exception {
		// clicking on Wifi
		try {
			Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='WIFI']").click();
			;
			System.out.println("Clicked on Wifi on Settings screen");
			logStep("Clicked on Wifi on Settings screen");
		} catch (Exception e) {
			System.out.println("Not able to Click on Wifi on Settings screen");
			logStep("Not able to Click on Wifi on Settings screen");
		}
//		TestBase.waitForMilliSeconds(20000);
		// Click on Desired Network on Wifi selection screen
		try {
			// Ad.findElementByXPath("//XCUIElementTypeStaticText[@name=\"Gibson-WiFi\"]").click();
			Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='" + wifiName + "']").click();
			System.out.println("Click on Desired Wifi Network ");
			logStep("Click on Desired Wifi Network ");
			try {
				if (Ad1.findElementByXPath("//XCUIElementTypeNavigationBar[@name='Enter Password']").isDisplayed()) {

					MobileElement passWord = Ad1
							.findElementByXPath("//XCUIElementTypeSecureTextField[@name='Password']");

					passWord.click();
					try {
						passWord.clear();
						// serverIp.sendKeys("10.138.179.128");
						passWord.sendKeys(properties.getProperty("wifiPassword"));
						System.out.println("Entered Wifi Password ");
						logStep("Entered Wifi Password ");

						Ad1.findElementByXPath("//XCUIElementTypeButton[@name='Join']").click();
						TestBase.waitForMilliSeconds(20000);
						try {
							Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='" + wifiName + "']").click();
							System.out.println("Click on Desired Wifi Network after input wifi password");
							logStep("Click on Desired Wifi Network after input wifi password ");
						} catch (Exception e) {
							System.out.println("Not able to click on Desired Wifi Network after input wifi password ");
							logStep("Not able to click on Desired Wifi Network after input password ");
						}

					} catch (Exception e) {
						System.out.println("Not able to input wifi password ");
						logStep("Not able to input wifi password ");
					}
				}

			} catch (Exception e) {
				System.out.println("Wifi password input prompt not displayed ");
				logStep("Wifi password input prompt not displayed ");
			}

		} catch (Exception e) {
			System.out.println("Not able to click on Desired Wifi Network ");
			logStep("Not able to click on Desired Wifi Network ");
		}
//		TestBase.waitForMilliSeconds(10000);
		// checking for 'Foget This Network to makesure Network is selected and moved to
		// next page
		try {
			Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='Forget This Network']");
			System.out.println("Navigated to Desired Wifi Network screen");
			logStep("Navigated to Desired Wifi Network screen");
			// make a check to see if Join this network is displayed or not, if yes then
			// select
			try {
				Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='Join This Network']").click();
				;
				System.out.println("Joining desired network ");
				logStep("Joining desired network ");

				// Ad.findElementByXPath("//XCUIElementTypeStaticText[@name=\"Gibson-WiFi\"]").click();
				Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='" + wifiName + "']").click();
				System.out.println("Click on Desired Wifi Network after joining it");
				logStep("Click on Desired Wifi Network after joining it");
			} catch (Exception e) {
				System.out.println("Join This Network is not displayed hence wifi network is selected");
				logStep("Join This Network is not displayed hence wifi network is selected");
			}
		} catch (Exception e) {
			// Ad.findElementByXPath("//XCUIElementTypeStaticText[@name=\"Gibson-WiFi\"]").click();
			Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='" + wifiName + "']").click();
			System.out.println("Navigating to Desired network page after selecting it");
			logStep("Navigating to Desired network page after selecting it");

		}
		// swipe up to navigate to configure manual proxy
		swipe_Up(Ad1);
//		TestBase.waitForMilliSeconds(10000);
		Ad1.context("NATIVE_APP");
		try {
			Ad1.findElementByAccessibilityId("Configure Proxy").click();
			;
			System.out.println("Clicked on Configure Proxy ");
			logStep("Clicked on Configure Proxy ");
			try {
				Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='Port']");
				System.out.println("Navigated to Proxy Configurtion Page");
				logStep("Navigated to Proxy Configurtion Page");
			} catch (Exception e) {
				try {
					Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='Configure Proxy']").click();
					;
					System.out.println("Clicked on Configure Proxy ");
					logStep("Clicked on Configure Proxy ");
				} catch (Exception e1) {
					System.out.println("Not able to click on Configure Proxy ");
					logStep("Not able to click on Configure Proxy ");
				}
			}
		} catch (Exception e) {

			try {
				Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='Configure Proxy']").click();
				;
				System.out.println("Clicked on Configure Proxy ");
				logStep("Clicked on Configure Proxy ");
			} catch (Exception e1) {
				System.out.println("Not able to click on Configure Proxy ");
				logStep("Not able to click on Configure Proxy ");
			}

		}
//		TestBase.waitForMilliSeconds(10000);

		if (!proxy) {
			try {
				Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='Off']").click();
				System.out.println("Turned Off Proxy ");
				logStep("Turned Off Proxy ");
			} catch (Exception e) {
				System.out.println("Not able to turned off Proxy ");
				logStep("Not able to turned off Proxy ");
			}
		} else {
			try {
				Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='Off']").click();
				System.out.println("Turned Off Proxy ");
				logStep("Turned Off Proxy ");
			} catch (Exception e) {
				System.out.println("Not able to turned off Proxy ");
				logStep("Not able to turned off Proxy ");
			}
			try {
				if (Ad1.findElementByXPath("//XCUIElementTypeButton[@name='Save']").isEnabled()) {
					Ad1.findElementByXPath("//XCUIElementTypeButton[@name='Save']").click();
					;
					System.out.println("Saved proxy details ");
					logStep("Saved proxy details ");
//					TestBase.waitForMilliSeconds(10000);
				} else {
					Ad1.findElementByXPath("//XCUIElementTypeButton[@name='" + wifiName + "']").click();
					System.out.println("Navigated back to Configure proxy page ");
					logStep("Navigated back to Configure proxy page ");
				}

			} catch (Exception e) {
				System.out.println("Not able to Save proxy details ");
				logStep("Not able to Save proxy details ");
			}
//			TestBase.waitForMilliSeconds(10000);
			try {
				Ad1.findElementByAccessibilityId("Configure Proxy").click();
				;
				System.out.println("Clicked on Configure Proxy ");
				logStep("Clicked on Configure Proxy ");
				try {
					Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='Port']");
					System.out.println("Navigated to Proxy Configurtion Page");
					logStep("Navigated to Proxy Configurtion Page");
				} catch (Exception e) {
					try {
						Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='Configure Proxy']").click();
						;
						System.out.println("Clicked on Configure Proxy ");
						logStep("Clicked on Configure Proxy ");
					} catch (Exception e1) {
						System.out.println("Not able to click on Configure Proxy ");
						logStep("Not able to click on Configure Proxy ");
					}
				}
			} catch (Exception e) {

				try {
					Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='Configure Proxy']").click();
					;
					System.out.println("Clicked on Configure Proxy ");
					logStep("Clicked on Configure Proxy ");
				} catch (Exception e1) {
					System.out.println("Not able to click on Configure Proxy ");
					logStep("Not able to click on Configure Proxy ");
				}

			}
//			TestBase.waitForMilliSeconds(10000);

			try {
				Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='Manual']").click();
				System.out.println("Clicked on Manual Proxy ");
				logStep("Clicked on Manual Proxy ");
			} catch (Exception e) {
				System.out.println("Not able to click on Manual Proxy ");
				logStep("Not able to click on Manual Proxy ");
			}

//			TestBase.waitForMilliSeconds(10000);
			try {
				MobileElement serverPort = Ad1.findElementByXPath("//XCUIElementTypeTextField[@name='Port']");
				serverPort.clear();
				serverPort.click();
				serverPort.clear();
				System.out.println("Clicked on Port to input port number");
				logStep("Clicked on Port to input port number ");
//				TestBase.waitForMilliSeconds(5000);
				try {
					serverPort.sendKeys("8111");
					System.out.println("Entered port details ");
					logStep("Entered port details ");
//					TestBase.waitForMilliSeconds(3000);

				} catch (Exception e) {
					System.out.println("Not able to input  port details ");
					logStep("Not able to input  port details ");
				}
			} catch (Exception e) {
				System.out.println("Not able to click on Port to input port number ");
				logStep("Not able to click on Port to input port number ");
			}

			try {
				MobileElement serverIp = Ad1.findElementByXPath("//XCUIElementTypeTextField[@name='Server']");

				serverIp.clear();
				serverIp.click();
				serverIp.clear();

				System.out.println("Clicked on Server to input ip address ");
				logStep("Clicked on Server to input ip address ");
				// TestBase.waitForMilliSeconds(20000);
				try {

					// serverIp.sendKeys("10.138.179.128");
					serverIp.sendKeys(currentIPAddress);
					System.out.println("Entered proxy address ");
					logStep("Entered proxy address ");
//					TestBase.waitForMilliSeconds(3000);
				} catch (Exception e) {
					System.out.println("Not able to input manual poxy address ");
					logStep("Not able to input manual poxy address ");
				}
			} catch (Exception e) {
				System.out.println("Not able to click on Server to input ip address ");
				logStep("Not able to click on Server to input ip address ");
			}
//			TestBase.waitForMilliSeconds(5000);

		}
		attachScreen(Ad1);
		try {
			Ad1.findElementByXPath("//XCUIElementTypeButton[@name='Save']").click();
			;
			System.out.println("Saved proxy details ");
			logStep("Saved proxy details ");
			TestBase.waitForMilliSeconds(3000);
		} catch (Exception e) {
			System.out.println("Not able to Save proxy details ");
			logStep("Not able to Save proxy details ");
		}
		try {
			//Ad.closeApp();
			Ad1.terminateApp("com.apple.Preferences");
			Ad1.quit();
			System.out.println("Closed System app ");
			logStep("Closed System app ");
		} catch (Exception e) {
			System.out.println("Failed to close System app ");
			logStep("Failed to close System app ");
		}

	}

	/**
	 * Turn Off the Proxy
	 * 
	 * @param wifiName
	 * @param proxy
	 * @throws Exception
	 */
	public static void enableDeviceProxy(String wifiName, boolean proxy) throws Exception {
		
		// clicking on Wifi
		try {
			Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='WIFI']").click();
			;
			System.out.println("Clicked on Wifi on Settings screen");
			logStep("Clicked on Wifi on Settings screen");
		} catch (Exception e) {
			System.out.println("Not able to Click on Wifi on Settings screen");
			logStep("Not able to Click on Wifi on Settings screen");
		}
//		TestBase.waitForMilliSeconds(20000);
		// Click on Desired Network on Wifi selection screen
		try {
			// Ad.findElementByXPath("//XCUIElementTypeStaticText[@name=\"Gibson-WiFi\"]").click();
			Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='" + wifiName + "']").click();
			System.out.println("Click on Desired Wifi Network ");
			logStep("Click on Desired Wifi Network ");
			try {
				if (Ad1.findElementByXPath("//XCUIElementTypeNavigationBar[@name='Enter Password']").isDisplayed()) {

					MobileElement passWord = Ad1
							.findElementByXPath("//XCUIElementTypeSecureTextField[@name='Password']");

					passWord.click();
					try {
						passWord.clear();
						// serverIp.sendKeys("10.138.179.128");
						passWord.sendKeys(properties.getProperty("wifiPassword"));
						System.out.println("Entered Wifi Password ");
						logStep("Entered Wifi Password ");

						Ad1.findElementByXPath("//XCUIElementTypeButton[@name='Join']").click();
//						TestBase.waitForMilliSeconds(20000);
						try {
							Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='" + wifiName + "']").click();
							System.out.println("Click on Desired Wifi Network after input wifi password");
							logStep("Click on Desired Wifi Network after input wifi password ");
						} catch (Exception e) {
							System.out.println("Not able to click on Desired Wifi Network after input wifi password ");
							logStep("Not able to click on Desired Wifi Network after input password ");
						}

					} catch (Exception e) {
						System.out.println("Not able to input wifi password ");
						logStep("Not able to input wifi password ");
					}
				}

			} catch (Exception e) {
				System.out.println("Wifi password input prompt not displayed ");
				logStep("Wifi password input prompt not displayed ");
			}

		} catch (Exception e) {
			System.out.println("Not able to click on Desired Wifi Network ");
			logStep("Not able to click on Desired Wifi Network ");
		}
//		TestBase.waitForMilliSeconds(10000);
		// checking for 'Foget This Network to makesure Network is selected and moved to
		// next page
		try {
			Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='Forget This Network']");
			System.out.println("Navigated to Desired Wifi Network screen");
			logStep("Navigated to Desired Wifi Network screen");
			// make a check to see if Join this network is displayed or not, if yes then
			// select
			try {
				Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='Join This Network']").click();
				;
				System.out.println("Joining desired network ");
				logStep("Joining desired network ");

				// Ad.findElementByXPath("//XCUIElementTypeStaticText[@name=\"Gibson-WiFi\"]").click();
				Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='" + wifiName + "']").click();
				System.out.println("Click on Desired Wifi Network after joining it");
				logStep("Click on Desired Wifi Network after joining it");
			} catch (Exception e) {
				System.out.println("Join This Network is not displayed hence wifi network is selected");
				logStep("Join This Network is not displayed hence wifi network is selected");
			}
		} catch (Exception e) {
			// Ad.findElementByXPath("//XCUIElementTypeStaticText[@name=\"Gibson-WiFi\"]").click();
			Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='" + wifiName + "']").click();
			System.out.println("Navigating to Desired network page after selecting it");
			logStep("Navigating to Desired network page after selecting it");

		}
		// swipe up to navigate to configure manual proxy
		swipe_Up(Ad1);
//		TestBase.waitForMilliSeconds(10000);
		Ad1.context("NATIVE_APP");
		try {
			Ad1.findElementByAccessibilityId("Configure Proxy").click();
			;
			System.out.println("Clicked on Configure Proxy ");
			logStep("Clicked on Configure Proxy ");
			try {
				Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='Port']");
				System.out.println("Navigated to Proxy Configurtion Page");
				logStep("Navigated to Proxy Configurtion Page");
			} catch (Exception e) {
				try {
					Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='Configure Proxy']").click();
					;
					System.out.println("Clicked on Configure Proxy ");
					logStep("Clicked on Configure Proxy ");
				} catch (Exception e1) {
					System.out.println("Not able to click on Configure Proxy ");
					logStep("Not able to click on Configure Proxy ");
				}
			}
		} catch (Exception e) {

			try {
				Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='Configure Proxy']").click();
				;
				System.out.println("Clicked on Configure Proxy ");
				logStep("Clicked on Configure Proxy ");
			} catch (Exception e1) {
				System.out.println("Not able to click on Configure Proxy ");
				logStep("Not able to click on Configure Proxy ");
			}

		}
//		TestBase.waitForMilliSeconds(10000);

		if (!proxy) {
			try {
				Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name='Off']").click();
				System.out.println("Turned Off Proxy ");
				logStep("Turned Off Proxy ");
			} catch (Exception e) {
				System.out.println("Not able to turned off Proxy ");
				logStep("Not able to turned off Proxy ");
			}
		} else {

		}
		attachScreen(Ad1);
		try {
			Ad1.findElementByXPath("//XCUIElementTypeButton[@name='Save']").click();
			;
			System.out.println("Saved proxy details ");
			logStep("Saved proxy details ");
			TestBase.waitForMilliSeconds(3000);
		} catch (Exception e) {
			System.out.println("Not able to Save proxy details ");
			logStep("Not able to Save proxy details ");
		}
		try {
			//Ad.closeApp();
			Ad1.terminateApp("com.apple.Preferences");
			Ad1.quit();
			System.out.println("Closed System app ");
			logStep("Closed System app ");
		} catch (Exception e) {
			System.out.println("Failed to close System app ");
			logStep("Failed to close System app ");
		}

	}
	
	public static void launchAndroidSettingsApp() throws Exception{
		ReadExcelValues.excelValues("Smoke", "Capabilities");

		DesiredCapabilities capabilities = new DesiredCapabilities();

		/* --- Start Android Device Capabilities --- */

		//capabilities.setCapability(capabilitydata[1][0], capabilitydata[1][Cap]);
		//capabilities.setCapability("platformName", "Android");
		//capabilities.setCapability(ReadExcelValues.data[2][0], ReadExcelValues.data[2][2]);
		capabilities.setCapability("platformName", ReadExcelValues.data[2][2]);
		//capabilities.setCapability("platformVersion", "11");
		capabilities.setCapability("platformVersion", ReadExcelValues.data[3][2]);
		//capabilities.setCapability("deviceName", "Samsung");
		capabilities.setCapability("deviceName", ReadExcelValues.data[5][2]);
		capabilities.setCapability("noReset", "true");
		// capabilities.setCapability("app",app.getAbsolutePath() );
		capabilities.setCapability("appPackage", "com.android.settings");
		capabilities.setCapability("appActivity", "com.android.settings.Settings");
		// capabilities.setCapability("appActivity","dev.firebase.appdistribution.main.MainActivity");
		capabilities.setCapability("automationName", "UiAutomator2");
		capabilities.setCapability("newCommandTimeout", "100000");
		capabilities.setCapability("autoLaunch", "true");
		Thread.sleep(5000);
		Ad1 = new AndroidDriver(new URL("http://127.0.0.1:4733/wd/hub"), capabilities);
		//Thread.sleep(50000);
		/* ---End Android Device Capabilities --- */
		Ad1.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		//System.out.println("Capabilities have been launched  with fullreset ");
		try {

			Ad1.findElementByXPath("//android.widget.TextView[@text=\"Settings\"]");
			System.out.println("Settings app launched");
			logStep("Settings app launched");
		} catch (Exception e) {
			System.out.println("Settings app not launched");
			logStep("Settings app not launched");
		}
		attachScreen(Ad1);
			
		}
	
	/**
	 * Enable/Disable Apps Tracking
	 * 
	 * @throws Exception
	 */
	public static void modifyAppsTracking(boolean enableTracking) throws Exception {
		MobileElement AdsTestSwitch = null;
		String SwitchValue = null;
		// swipe up to navigate to Privacy
			swipe_Up(Ad1);
			swipe_Up(Ad1);
		
		try {
			// clicking on Privacy
			Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name=\"Privacy & Security\"]").click();
			System.out.println("Clicked on Privacy on Settings screen");
			logStep("Clicked on Privacy on Settings screen");
			try {
				// clicking on Tracking
				Ad1.findElementByXPath("//XCUIElementTypeStaticText[@name=\"USER_TRACKING\"]").click();
				System.out.println("Clicked on Tracking On Privacy Screen");
				logStep("Clicked on Tracking On Privacy Screen");
				
				try {
					//Checking for Back button on Tracking Screen
					Ad1.findElementByXPath("//XCUIElementTypeButton[@name=\"Privacy & Security\"]");
					
					AdsTestSwitch = Ad1.findElementByXPath("//XCUIElementTypeSwitch[@name=\"Allow Apps to Request to Track\"]");
					
					SwitchValue = AdsTestSwitch.getAttribute("value");
					
					if (enableTracking) {
						if (SwitchValue.equals("1")) {
							attachScreen(Ad1);
							System.out.println("Tracking already enabled");
							logStep("Tracking already enabled");
							Ad1.findElementByXPath("//XCUIElementTypeButton[@name=\"Privacy\"]").click();
						} else {
							AdsTestSwitch.click();
							attachScreen(Ad1);
							System.out.println("Tracking enabled");
							logStep("Tracking enabled");
							//Also enabling Tracking for individual applications
							List<MobileElement> listOfAppsToTrack =  Ad1.findElements(By.xpath("//XCUIElementTypeSwitch"));
							
							for (int i = 1; i < listOfAppsToTrack.size(); i++) {
								listOfAppsToTrack.get(i).click();
							}
							Ad1.findElementByXPath("//XCUIElementTypeButton[@name=\"Privacy & Security\"]").click();
						}
					} else {
						if (SwitchValue.equals("1")) {
							System.out.println("Tracking already enabled, hence disabling");
							logStep("Tracking already enabled, hence disabling");
							AdsTestSwitch.click();
							attachScreen(Ad1);
							////XCUIElementTypeButton[@name="Allow Apps to Continue Tracking"]
							////XCUIElementTypeButton[@name="Cancel"]
							if (TestBase.isElementExists(By.xpath("//XCUIElementTypeButton[@name=\"Ask Apps to Stop Tracking\"]"), Ad1)) {
								Ad1.findElementByXPath("//XCUIElementTypeButton[@name=\"Ask Apps to Stop Tracking\"]").click();
							}
							Ad1.findElementByXPath("//XCUIElementTypeButton[@name=\"Privacy & Security\"]").click();
						} else {
							attachScreen(Ad1);
							System.out.println("Tracking already disabled");
							logStep("Tracking already disabled");
							Ad1.findElementByXPath("//XCUIElementTypeButton[@name=\"Privacy & Security\"]").click();
						}
					}
				
				} catch (Exception e) {
					System.out.println("An Exception on Tracking Screen");
					logStep("An Exception on Tracking Screen");
				}

			} catch (Exception e) {
				System.out.println("Not able to click on Tracking on Privacy Screen");
				logStep("Not able to click on Tracking on Privacy Screen");
			}
		} catch (Exception e) {
			System.out.println("Not able to Click on Privacy on Settings screen");
			logStep("Not able to Click on Privacy on Settings screen");
		}
		
		try {
			Ad1.terminateApp("com.apple.Preferences");
			Ad1.quit();
			System.out.println("Closed System app ");
			logStep("Closed System app ");
		} catch (Exception e) {
			System.out.println("Failed to close System app ");
			logStep("Failed to close System app ");
		}

	}

	/**
	 * This method to be called immediately after when we kill and launch the app.
	 * This method verifies wheather the app is launched and running in foreground
	 * or not.
	 */
	public static void checkForAppState() {
		boolean flag = false;
		boolean istwcAppInstalled = Ad.isAppInstalled("com.weather.TWC");
		if (istwcAppInstalled) {
			while (!flag) {
				ApplicationState appState = Ad.queryAppState("com.weather.TWC");
				if (appState.equals(ApplicationState.RUNNING_IN_FOREGROUND)) {
					System.out.println("App running in Foreground");
					logStep("App running in Foreground");
					flag = true;
				} else {
					System.out.println("App Not running in Foreground, Kill and launching the app again");
					logStep("App Not running in Foreground, Kill and launching the app again");
					Functions.close_launchApp();
				}
			}
		} else {
			System.out.println("TWC App is Not yet installed, hence ignoring the Application State Check");
			logStep("TWC App is Not yet installed, hence ignoring the Application State Check");
		}

	}
	/**
	 * This method verifies app state with expected state.
	 * NOT_INSTALLED
	 * NOT_RUNNING
	 * RUNNING_IN_BACKGROUND
	 * RUNNING_IN_BACKGROUND_SUSPENDED
	 * RUNNING_IN_FOREGROUND
	 * @param expectedState
	 */
	public static void checkForAppState(ApplicationState  expectedState) {
		ApplicationState appState = Ad.queryAppState("com.weather.TWC");
		Assert.assertEquals(appState, expectedState);
		

	}
	
	/**
	 * This method verifies app state not equal to expected state.
	 * NOT_INSTALLED
	 * NOT_RUNNING
	 * RUNNING_IN_BACKGROUND
	 * RUNNING_IN_BACKGROUND_SUSPENDED
	 * RUNNING_IN_FOREGROUND
	 * @param expectedState
	 */
	public static void checkForAppStateNotEqual(ApplicationState  notExpectedState) {
		ApplicationState appState = Ad.queryAppState("com.weather.TWC");
		
		if (appState.equals(notExpectedState)) {
			System.out.println("App Current State: "+appState +" is matched with Not Expected State: "+notExpectedState);
			logStep("App Current State: "+appState +" is matched with Not Expected State: "+notExpectedState);
			Assert.fail("App Current State: "+appState +" is matched with Not Expected State: "+notExpectedState);
			//flag = true;
		} else {
			System.out.println("App Current State: "+appState +" is not matched with Not Expected State: "+notExpectedState);
			logStep("App Current State: "+appState +" is not matched with Not Expected State: "+notExpectedState);
		}

	}
	
	/**
	 * This method verifies app state not equal to expected state.
	 * NOT_INSTALLED
	 * NOT_RUNNING
	 * RUNNING_IN_BACKGROUND
	 * RUNNING_IN_BACKGROUND_SUSPENDED
	 * RUNNING_IN_FOREGROUND
	 * @param expectedState
	 */
	public static void checkForAppStateNotEqualAndroid(ApplicationState  notExpectedState) {
		ApplicationState appState = Ad.queryAppState("com.weather.Weather");
		
		if (appState.equals(notExpectedState)) {
			System.out.println("App Current State: "+appState +" is matched with Not Expected State: "+notExpectedState);
			logStep("App Current State: "+appState +" is matched with Not Expected State: "+notExpectedState);
			Assert.fail("App Current State: "+appState +" is matched with Not Expected State: "+notExpectedState);
			//flag = true;
		} else {
			System.out.println("App Current State: "+appState +" is not matched with Not Expected State: "+notExpectedState);
			logStep("App Current State: "+appState +" is not matched with Not Expected State: "+notExpectedState);
		}

	}
	
	public static int getOffsetYTop() {

		int offset = 0;
		try {
			// for IOS
			if(Ad instanceof IOSDriver<?>) {
				By navBarClassBy = By.className("XCUIElementTypeNavigationBar");
				WebElement navBar = Ad.findElement(navBarClassBy);
				Rectangle r = navBar.getRect();
				//System.out.println("Rectangle Element is: "+r.getPoint());
				//System.out.println("Height of Rectangle Element is: "+r.getHeight());
				offset = r.y + r.getHeight();
				//System.out.println("Offset Y Top is: "+offset);
			} else if(Ad instanceof AndroidDriver<?>) {
				//By toolbarIdBy = By.id(":id/main_feed_toolbar");
				By toolbarIdBy = By.id("com.weather.Weather:id/toolbar");
				WebElement toolbar = Ad.findElement(toolbarIdBy);
				Rectangle r = toolbar.getRect();
				//System.out.println("Rectangle Element is: "+r.getPoint());
				//System.out.println("Height of Rectangle Element is: "+r.getHeight());
				offset = r.y + r.getHeight();
				//System.out.println("Offset Y Top is: "+offset);
			}
		} catch (WebDriverException e) {
		}
		return offset;
	
		//return 0;
	}
	
	/**
	 * GENERIC ONE
	 * @param item
	 * @param down
	 * @param scrollToTop
	 * @param offsetY
	 */
	public static void genericScroll(By locator, boolean down, boolean scrollToTop, int offsetY, double tolerance) throws Exception {

		duration = TimeUnit.SECONDS.toMillis(duration);
		final long startTime = Calendar.getInstance().getTimeInMillis();
		long currTime = startTime;
		final Dimension dim = Ad.manage().window().getSize();
		int startY = (int)(dim.height * DEFAULT_SCROLL_DOWN_STARTING_POINT);
		int moveY = -(int)(dim.height * DEFAULT_PAGE_SCROLL_AMOUNT);
		// If scrolling up, use 15% so we start beyond the search bar/toolbar
		// and make Y a positive value
		if(!down) {
			startY = (int)(dim.height * DEFAULT_SCROLL_UP_STARTING_POINT);
			moveY = moveY * -1;
		}
		
		boolean elementFound = false;
		elementFound = TestBase.isElementDisplayed(locator);
		int scrollCount =  0;
		while(!elementFound && scrollCount <= 15) {
		//while(!elementFound) {
			attachScreen();
			performScrollTouchAction(1, startY, 0, moveY);
			currTime = Calendar.getInstance().getTimeInMillis();
			elementFound = TestBase.isElementDisplayed(locator);
			if (elementFound) {
				System.out.println("Element found in the view");
				logStep("Element found in the view");
				attachScreen();
			}
			currTime = Calendar.getInstance().getTimeInMillis();
			scrollCount++;
		}
		
			System.out.println("Scroll complete, Expected element: " + locator + " found: " + elementFound);
			logStep("Scroll complete, Expected element: " + locator + " found: " + elementFound);
		
		if (elementFound) {
			if(scrollToTop) {
				System.out.println("Moving element to top");
				moveElementToTop(locator, dim, offsetY, tolerance);
				attachScreen();
			}
		}
		
		
		//final long mins = TimeUnit.MILLISECONDS.toMinutes(currTime - startTime);
		//final long secs = TimeUnit.MILLISECONDS.toSeconds(currTime - startTime) - (mins * 60);
		
	
	}
	
	/**
	 * GENERIC ONE
	 * @param item
	 * @param down
	 * @param scrollToTop
	 * @param offsetY
	 */
	public static void genericScroll(By locator, boolean down, boolean scrollToTop, int offsetY, double tolerance, AppiumDriver<MobileElement> Ad) throws Exception {

		duration = TimeUnit.SECONDS.toMillis(duration);
		final long startTime = Calendar.getInstance().getTimeInMillis();
		long currTime = startTime;
		final Dimension dim = Ad.manage().window().getSize();
		int startY = (int)(dim.height * DEFAULT_SCROLL_DOWN_STARTING_POINT);
		int moveY = -(int)(dim.height * DEFAULT_PAGE_SCROLL_AMOUNT);
		// If scrolling up, use 15% so we start beyond the search bar/toolbar
		// and make Y a positive value
		if(!down) {
			startY = (int)(dim.height * DEFAULT_SCROLL_UP_STARTING_POINT);
			moveY = moveY * -1;
		}
		
		boolean elementFound = false;
		elementFound = TestBase.isElementDisplayed(locator, Ad);
		int scrollCount =  0;
		while(!elementFound && scrollCount <= 15) {
		//while(!elementFound) {
			attachScreen(Ad);
			performScrollTouchAction(1, startY, 0, moveY, Ad);
			currTime = Calendar.getInstance().getTimeInMillis();
			elementFound = TestBase.isElementDisplayed(locator, Ad);
			if (elementFound) {
				System.out.println("Element found in the view");
				logStep("Element found in the view");
				attachScreen(Ad);
			}
			currTime = Calendar.getInstance().getTimeInMillis();
			scrollCount++;
		}
		
			System.out.println("Scroll complete, Expected element: " + locator + " found: " + elementFound);
			logStep("Scroll complete, Expected element: " + locator + " found: " + elementFound);
		
		if (elementFound) {
			if(scrollToTop) {
				System.out.println("Moving element to top");
				moveElementToTop(locator, dim, offsetY, tolerance, Ad);
				attachScreen(Ad);
			}
		}
		
		
		//final long mins = TimeUnit.MILLISECONDS.toMinutes(currTime - startTime);
		//final long secs = TimeUnit.MILLISECONDS.toSeconds(currTime - startTime) - (mins * 60);
		
	
	}

	/**
	 * GENERIC ONE For Airbnb
	 * @param locator
	 * @param down
	 * @param scrollToTop
	 * @param offsetY
	 * @param tolerance
	 * @throws Exception
	 */
	public static void genericScrollAirbnb(By locator, boolean down, boolean scrollToTop, int offsetY, double tolerance)
			throws Exception {

		int i = 0;
		while (i < 20) {
			By byele = MobileBy.xpath("//XCUIElementTypeCollectionView/XCUIElementTypeCell[1]");
			MobileElement ele = Ad.findElement(byele);
			Point loc = ele.getLocation();
			System.out.println("Element located at " + loc);
			final Dimension dim = ele.getSize();
			int startY = loc.y + (int) (dim.height);
			int moveY = -(int) (dim.height);
			if (loc.y < 0) {
				genericVariance = 44;
				performScrollTouchAction(1, startY, 0, -startY + 174);
			} else {
				performScrollTouchAction(1, startY, 0, moveY);
			}

			i++;
		}
	}
	
	/**
	 * Scoll On Airbnb App
	 * @throws Exception
	 */
	public static void scrollOnAirbnb() throws Exception {
		//aQCard = Ad.findElement(byAirQualityCard);
		Functions.genericScrollAirbnb(MobileBy.AccessibilityId("air-quality-card"), true, true, 174, TOLERANCE_FROM_TOP);
	}

	/**
	 * This Method used to Scroll on TWC app and scroll up one card each time and
	 * navigate content pages of respective cards if applicable. Scrolling will end
	 * once it finds the given locator
	 * 
	 * @param locator
	 * @param down
	 * @param scrollToTop
	 * @param offsetY
	 * @param tolerance
	 * @param includeDetailsPages
	 * @param navigateTwiceToDetailsPages
	 * @throws Exception
	 */
	public static void genericScrollTWC(By locator, boolean down, boolean scrollToTop, int offsetY, double tolerance,
			boolean includeDetailsPages, boolean navigateTwiceToDetailsPages) throws Exception {
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
		
		ReadExcelValues.excelValues("Smoke", "General");
		By byFooterCard = MobileBy.name(ReadExcelValues.data[1][Cap]);

		String cardName = "homescreen";
		String feedName = null;
		ArrayList<String> cardsList = new ArrayList<String>();
		nextGenIMadDisplayed = false;
		rainCardDisplayed = false;
		feedAdCount = 0;
		duration = TimeUnit.SECONDS.toMillis(duration);
		final long startTime = Calendar.getInstance().getTimeInMillis();
		long currTime = startTime;

		boolean elementFound = false;
		elementFound = TestBase.isElementDisplayed(locator);
		boolean footerElementFound = false;
		footerElementFound = TestBase.isElementDisplayed(byFooterCard);
		//while (!elementFound && !footerElementFound && ((currTime - startTime) <= duration)) {
		while (!elementFound && !footerElementFound) {
			/*
			 * List<MobileElement> els = Ad.findElements(MobileBy.xpath(
			 * "//XCUIElementTypeCollectionView[@y='0']/XCUIElementTypeCell"));
			 * System.out.println("Number Of Cards In List " + els.size() );
			 * System.out.println("********************************************"); for
			 * (MobileElement el: els) { Point loc = el.getLocation();
			 * System.out.println("Element located at " + loc ); Dimension dim =
			 * el.getSize(); System.out.println("Element Dimensions " + dim ); }
			 */
			System.out.println("********************************************");
			By byele = MobileBy.xpath("//XCUIElementTypeCollectionView[@y='0']/XCUIElementTypeCell[1]");
			MobileElement ele = Ad.findElement(byele);
			Point loc = ele.getLocation();
			System.out.println("Element located at " + loc);
			Dimension dim = ele.getSize();
			System.out.println("Element Dimensions " + dim);
			System.out.println("Element Height is " + dim.height);
			int startY = loc.y + (int) (dim.height);
			int moveY = -(int) (dim.height);
			//if (startY == 88 || startY <= 88) {
			if (startY == offsetY || startY <= offsetY) {
				byele = MobileBy.xpath("//XCUIElementTypeCollectionView[@y='0']/XCUIElementTypeCell[2]");
				ele = Ad.findElement(byele);
				loc = ele.getLocation();
				System.out.println("Element located at " + loc);
				dim = ele.getSize();
				System.out.println("Element Dimensions " + dim);
				System.out.println("Element Height is " + dim.height);
				startY = loc.y + (int) (dim.height);
				moveY = -(int) (dim.height);
				//if (startY == 88 || startY <= 88) {
				if (startY == offsetY || startY <= offsetY) {
					byele = MobileBy.xpath("//XCUIElementTypeCollectionView[@y='0']/XCUIElementTypeCell[3]");
					ele = Ad.findElement(byele);
					loc = ele.getLocation();
					System.out.println("Element located at " + loc);
					dim = ele.getSize();
					System.out.println("Element Dimensions " + dim);
					System.out.println("Element Height is " + dim.height);
					startY = loc.y + (int) (dim.height);
					moveY = -(int) (dim.height);

				}
			}

			if (loc.y < 0) {
				genericVariance = 10;
				//performScrollTouchAction(1, startY, 0, -startY + 88);
				performScrollTouchAction(1, startY, 0, -startY + offsetY);
			} else {
				try {
					cardName = ele
							.findElement(By.xpath(
									"//XCUIElementTypeOther[@name='nextgen-integrated-marquee-card-containerView']"))
							.getAttribute("name");
				} catch (Exception e) {
					try {
						cardName = ele.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"))
								.getAttribute("name");
					} catch (Exception e1) {
						try {
							// some times when big advertisement displayed, it doenst have name as
							// 'Advertisement' hence below implemented
							cardName = ele
									.findElement(By.xpath("//XCUIElementTypeOther[@name='ads-card-containerView']"))
									.getAttribute("name");

						} catch (Exception e2) {
							try {
								// when Integrated Feed card is displayed, it doenst have name as
								// 'Advertisement' hence below implemented
								cardName = ele
										.findElement(By.xpath(
												"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']"))
										.getAttribute("name");

							} catch (Exception e3) {
								cardName = ele.findElement(By.xpath("(//XCUIElementTypeOther)[3]"))
										.getAttribute("name");
							}
						}

					}
				}

				if (cardName.equalsIgnoreCase("Advertisement")) {
					try {
						feedName = ele.findElement(By.xpath(
								"//XCUIElementTypeStaticText[@name='Advertisement']//parent::XCUIElementTypeOther"))
								.getAttribute("name");
						cardName = feedName.replaceAll("-adContainerView", "");
					} catch (Exception e) {
						// following is added to handle Integrated Feed Card while getting card name.
						try {
							feedName = ele.findElement(By.xpath(
									"//XCUIElementTypeStaticText[@name='Advertisement']//parent::XCUIElementTypeOther/following-sibling::XCUIElementTypeOther"))
									.getAttribute("name");
							cardName = feedName.replaceAll("-adContainerView", "");
						} catch (Exception e1) {
							cardName = "Advertisement";
						}
					}

				} else if (cardName.equalsIgnoreCase("ads-card-containerView")) {
					try {
						feedName = ele
								.findElement(By.xpath(
										"//XCUIElementTypeOther[@name='ads-card-containerView']/XCUIElementTypeOther"))
								.getAttribute("name");
						cardName = feedName.replaceAll("-adContainerView", "");
					} catch (Exception e) {
						cardName = "Advertisement";
					}
				} else if (cardName.equalsIgnoreCase("integrated-ad-card-containerView")) {
					try {
						feedName = ele.findElement(By.xpath(
								"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']//XCUIElementTypeOther[contains(@name,'-adContainerView')]"))
								.getAttribute("name");
						cardName = feedName.replaceAll("-adContainerView", "");
					} catch (Exception e) {
						cardName = "Advertisement";
					}
				}
				cardsList.add(cardName);
				System.out.println("Current Card is : " + cardName);
				logStep("Current Card is : " + cardName);
				attachScreen();

				if (cardName.equalsIgnoreCase("nextgen-integrated-marquee-card-containerView")) {
					nextGenIMadDisplayed = true;
				} else if (cardName.equalsIgnoreCase("Rain")) {
					rainCardDisplayed = true;
				}

				cardName = Utils.shortCardName(cardName);

				if (cardName.contains("weather.feed") || cardName.contains("Advertisement")) {
					feedAdCount++;
				}

				if (includeDetailsPages && !cardName.contains("weather.feed")) {
					try {

						if (cardName.equalsIgnoreCase("lifestyle")) {
							lStyleCardScreen.navigateToLifeStyleCardIndexes();
							if (navigateTwiceToDetailsPages) {
								lStyleCardScreen.navigateToLifeStyleCardIndexes();
							}
						} else if (cardName.equalsIgnoreCase("seasonalhub")) {
							// sHubCardScreen.navigateToFirstIndexOfSeasonalHubCard();
							sHubCardScreen.navigateToSeasonalHubCardIndexes();
							if (navigateTwiceToDetailsPages) {
								// sHubCardScreen.navigateToFirstIndexOfSeasonalHubCard();
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

				//performScrollTouchAction(1, startY, 0, moveY);
				if (startY - offsetY > 625) {
					System.out.println("Since Card position  >625, reducing startY valueto 625 to avoid press on sticky ad ");
					logStep("Since Card position >625, reducing startY valueto 625 to avoid press on sticky ad ");
					startY = 625;
					performScrollTouchAction(1, startY, 0, moveY);
				} else {
					performScrollTouchAction(1, startY, 0, moveY);
				}
			}
			elementFound = TestBase.isElementDisplayed(locator);
			if (elementFound) {
				System.out.println("Element found in the view");
				logStep("Element found in the view");
				attachScreen();
				System.out.println("User done scrolling, Printing last 3 cards when Scroll ends");
				logStep("User done scrolling, Printing last 3 cards when Scroll ends");

				//getLastThreeCardsNames();
			}
			
			footerElementFound = TestBase.isElementDisplayed(byFooterCard);
			currTime = Calendar.getInstance().getTimeInMillis();

		}
		System.out.println("Total time in milli seconds to navigate to all cards and contents: "+(currTime - startTime));
		logStep("Total time in milli seconds to navigate to all cards and contents: "+(currTime - startTime));
		getLastThreeCardsNames();
		System.out.println("Cards List: " + cardsList);
		logStep("Cards List: " + cardsList);
		System.out.println("Total no of Feed Ad Cards counted are :" + feedAdCount);
		logStep("Total no of Feed Ad Cards counted are :" + feedAdCount);

				
		if (footerElementFound) {
			System.out.println("Since footer element found on screen, can't move element to top");
		} else {
			final Dimension dim1 = Ad.manage().window().getSize();
			if (scrollToTop) {
				System.out.println("Moving element to top");
				moveElementToTop(locator, dim1, offsetY, tolerance);
				attachScreen();
			}
		}
	}
	
	/**
	 * This Method used to Scroll on TWC app and scroll up one card each time and
	 * navigate content pages of respective cards if applicable. Scrolling will end
	 * once it finds the given locator
	 * 
	 * @param locator
	 * @param down
	 * @param scrollToTop
	 * @param offsetY
	 * @param tolerance
	 * @param includeDetailsPages
	 * @param navigateTwiceToDetailsPages
	 * @throws Exception
	 */
	public static void genericScrollTWCAndroid(By locator, boolean down, boolean scrollToTop, int offsetY, double tolerance,
			boolean includeDetailsPages, boolean navigateTwiceToDetailsPages) throws Exception {
		AndroidHomeNavTab hmTab = new AndroidHomeNavTab(Ad);
		AndroidHourlyNavTab hrTab = new AndroidHourlyNavTab(Ad);
		AndroidDailyNavTab dTab = new AndroidDailyNavTab(Ad);
		AndroidRadarNavTab rTab = new AndroidRadarNavTab(Ad);
		AndroidVideoNavTab vTab = new AndroidVideoNavTab(Ad);
		AndroidAlertCenterScreen alertScreen = new AndroidAlertCenterScreen(Ad);
		
		AndroidLifeStyleCardScreen lStyleCardScreen = new AndroidLifeStyleCardScreen(Ad);
		AndroidSeasonalHubCardScreen sHubCardScreen = new AndroidSeasonalHubCardScreen(Ad);
		AndroidHourlyCardScreen hCardScreen = new AndroidHourlyCardScreen(Ad);
		AndroidDailyCardScreen dCardScreen = new AndroidDailyCardScreen(Ad);
		AndroidRadarCardScreen rCardScreen = new AndroidRadarCardScreen(Ad);
		AndroidTodayCardScreen tCardScreen = new AndroidTodayCardScreen(Ad);
		AndroidVideoCardScreen vCardScreen = new AndroidVideoCardScreen(Ad);
		AndroidAirQualityCardScreen aqCardScreen = new AndroidAirQualityCardScreen(Ad);
		AndroidNewsCardScreen nCardScreen = new AndroidNewsCardScreen(Ad);
		
		ReadExcelValues.excelValues("Smoke", "General");
		String footerText = ReadExcelValues.data[1][Cap];
		//By byFooterCard = MobileBy.name(ReadExcelValues.data[1][Cap]);
		
		By byFooterCard = MobileBy.xpath("//android.widget.TextView[@text='"+footerText+"']");

		String cardName = "homescreen";
		String feedName = null;
		ArrayList<String> cardsList = new ArrayList<String>();
		nextGenIMadDisplayed = false;
		rainCardDisplayed = false;
		feedAdCount = 0;
		duration = TimeUnit.SECONDS.toMillis(duration);
		final long startTime = Calendar.getInstance().getTimeInMillis();
		long currTime = startTime;

		boolean elementFound = false;
		elementFound = TestBase.isElementDisplayed(locator);
		boolean footerElementFound = false;
		footerElementFound = TestBase.isElementDisplayed(byFooterCard);
		//while (!elementFound && !footerElementFound && ((currTime - startTime) <= duration)) {
		while (!elementFound && !footerElementFound) {
			System.out.println("********************************************");
			By byele = MobileBy.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.weather.Weather:id/home_screen_list_view\"]/android.widget.FrameLayout[1]");
			MobileElement ele = Ad.findElement(byele);
			Point loc = ele.getLocation();
			System.out.println("Element located at " + loc);
			Dimension dim = ele.getSize();
			System.out.println("Element Dimensions " + dim);
			System.out.println("Element Height is " + dim.height);
			//int startY = loc.y + (int) (dim.height);
			int startY = offsetY + (int) (dim.height);
			//int startY = (int) (dim.height);
			//int moveY = -(int) (dim.height)-(loc.y-offsetY);
			int moveY = -(int) (dim.height);
			//if (startY == offsetY || startY <= offsetY) {
			//if (startY - offsetY <= 15) {
			if ((int) (dim.height) <= 15) {
				byele = MobileBy.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.weather.Weather:id/home_screen_list_view\"]/android.widget.FrameLayout[2]");
				ele = Ad.findElement(byele);
				loc = ele.getLocation();
				System.out.println("Element located at " + loc);
				dim = ele.getSize();
				System.out.println("Element Dimensions " + dim);
				System.out.println("Element Height is " + dim.height);
				//startY = loc.y + (int) (dim.height);
				startY = offsetY + (int) (dim.height);
				//moveY = -(int) (dim.height)-(loc.y-offsetY);
				moveY = -(int) (dim.height);
				//if (startY == offsetY || startY <= offsetY) {
				//if (startY - offsetY <= 15) {
				if ((int) (dim.height) <= 15) {
					byele = MobileBy.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.weather.Weather:id/home_screen_list_view\"]/android.widget.FrameLayout[3]");
					ele = Ad.findElement(byele);
					loc = ele.getLocation();
					System.out.println("Element located at " + loc);
					dim = ele.getSize();
					System.out.println("Element Dimensions " + dim);
					System.out.println("Element Height is " + dim.height);
					//startY = loc.y + (int) (dim.height);
					startY = offsetY + (int) (dim.height);
					//moveY = -(int) (dim.height)-(loc.y-offsetY);
					moveY = -(int) (dim.height);

				}
			}

			//if (loc.y < 0) {
			if ((int) (dim.height) <= 35) {
				genericVariance = 20;
				performScrollTouchAction(1, startY, 0, -startY + offsetY);
			} else {

				try {
					cardName = ele
							.findElement(By.xpath(
									"//android.widget.TextView[@resource-id='com.weather.Weather:id/header_title']"))
							.getAttribute("text");
				} catch (Exception e) {
					try {
						cardName = ele.findElement(By.xpath("//*[@resource-id='com.weather.Weather:id/title']"))
								.getAttribute("text");
					} catch (Exception e1) {
						try {
							cardName = ele
									.findElement(By.xpath("//android.widget.TextView[@resource-id='com.weather.Weather:id/ad_card_title']"))
									.getAttribute("text");

						} catch (Exception e2) {
							try {
								
								MobileElement el = ele.findElement(By.xpath("//android.view.ViewGroup[@resource-id='com.weather.Weather:id/current_conditions_card_root']"));
								cardName = "Current Condition";
							} catch (Exception e3) {
								try {
									MobileElement el = ele.findElement(By.xpath("//android.widget.FrameLayout[@resource-id='com.weather.Weather:id/card_integrated_ad_root']"));
									cardName = "Integrated Marquee";

								} catch (Exception e4) {
									try {
									cardName = ele
												.findElement(By.xpath(
														"//android.widget.TextView"))
												.getAttribute("text");
									} catch (Exception e5) {
										e5.printStackTrace();
									}
									
								}
							}
						}

					}
				}

				
				cardsList.add(cardName);
				System.out.println("Current Card is : " + cardName);
				logStep("Current Card is : " + cardName);
				attachScreen();
				
				if (cardName.equalsIgnoreCase("Integrated Marquee")) {
					nextGenIMadDisplayed = true;
				} else if (cardName.equalsIgnoreCase("Rain")) {
					rainCardDisplayed = true;
				}

				/**
				 * Code to navigate to content pages.....
				 */
				if (includeDetailsPages && !cardName.contains("weather.feed")) {
					try {

						if (cardName.equalsIgnoreCase("Health & Activities")) {
							lStyleCardScreen.navigateToLifeStyleCardIndexes();
							if (navigateTwiceToDetailsPages) {
								lStyleCardScreen.navigateToLifeStyleCardIndexes();
							}
						} else if (cardName.equalsIgnoreCase("Outdoor Conditions")) {
							//sHubCardScreen.navigateToFirstIndexOfSeasonalHubCard();
							sHubCardScreen.navigateToSeasonalHubCardIndexes();
							if (navigateTwiceToDetailsPages) {
								//sHubCardScreen.navigateToFirstIndexOfSeasonalHubCard();
								sHubCardScreen.navigateToSeasonalHubCardIndexes();
							}
						} else if (cardName.equalsIgnoreCase("Hourly Forecast")) {
							hCardScreen.navigateToHourlyCardContentPage();
							if (navigateTwiceToDetailsPages) {
								hCardScreen.navigateToHourlyCardContentPage();
							}
						} else if (cardName.equalsIgnoreCase("Daily Forecast")) {
							dCardScreen.navigateToDailyCardContentPage();
							if (navigateTwiceToDetailsPages) {
								dCardScreen.navigateToDailyCardContentPage();
							}
						} else if (cardName.equalsIgnoreCase("hurricane-central")) {
							// navigate to hurricanecentral card content page
						} else if (cardName.equalsIgnoreCase("Radar & Maps")) {
							rCardScreen.navigateToRadarCardContentPage();
							if (navigateTwiceToDetailsPages) {
								rCardScreen.navigateToRadarCardContentPage();
							}
						} else if (cardName.equalsIgnoreCase("snapshot")) {
							// navigate to snapshot card content page
						} else if (cardName.equalsIgnoreCase("Today's Details")) {
							tCardScreen.navigateToTodayCardContentPage();
							if (navigateTwiceToDetailsPages) {
								tCardScreen.navigateToTodayCardContentPage();
							}
						} else if (cardName.equalsIgnoreCase("Latest News")) {
							vCardScreen.navigateToVideoCardContentPage();
							if (navigateTwiceToDetailsPages) {
								vCardScreen.navigateToVideoCardContentPage();
							}

						} else if (cardName.equalsIgnoreCase("Air Quality")) {
							aqCardScreen.navigateToAirQualityCardContentPage();
							if (navigateTwiceToDetailsPages) {
								aqCardScreen.navigateToAirQualityCardContentPage();
							}

						} else if (cardName.equalsIgnoreCase("More News")) {
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
				
				int nextY = 0;
				int currentY = startY - getOffsetYTop();
				//if (startY - getOffsetYTop() > 1250) {
				if (currentY > 500) {
					//startY = 1250;
					while (startY - offsetY > 500) {
						nextY = startY - (500 + offsetY);
						startY = 500 + offsetY;
						
						System.out.println("Since Card position  >724, reducing startY valueto 724 to avoid press on sticky ad ");
						//logStep("Since Card position >724, reducing startY valueto 724 to avoid press on sticky ad ");
						performScrollTouchAction(1, startY, 0, -startY + offsetY+15);				
						startY = nextY + getOffsetYTop();
					}
					if (startY - offsetY >= 35) {
						performScrollTouchAction(1, startY, 0, -startY + offsetY+15);
					}
				//performScrollTouchAction(1, startY, 0, moveY);
				//performScrollTouchAction(1, startY, 0, -startY + offsetY);
			} else {
				performScrollTouchAction(1, startY, 0, -startY + offsetY);
			}
			}	
			elementFound = TestBase.isElementDisplayed(locator);
			if (elementFound) {
				System.out.println("Element found in the view");
				logStep("Element found in the view");
				attachScreen();
				System.out.println("User done scrolling, Printing last 3 cards when Scroll ends");
				logStep("User done scrolling, Printing last 3 cards when Scroll ends");

				//getLastThreeCardsNames();
			}
			
			footerElementFound = TestBase.isElementDisplayed(byFooterCard);
			currTime = Calendar.getInstance().getTimeInMillis();

		}
		System.out.println("Total time in milli seconds to navigate to all cards and contents: "+(currTime - startTime));
		logStep("Total time in milli seconds to navigate to all cards and contents: "+(currTime - startTime));
		getLastThreeCardsNamesAndroid();
		System.out.println("Cards List: " + cardsList);
		logStep("Cards List: " + cardsList);
		System.out.println("Total no of Feed Ad Cards counted are :" + feedAdCount);
		logStep("Total no of Feed Ad Cards counted are :" + feedAdCount);

				
		if (footerElementFound) {
			System.out.println("Since footer element found on screen, can't move element to top");
		} else {
			final Dimension dim1 = Ad.manage().window().getSize();
			if (scrollToTop) {
				System.out.println("Moving element to top");
				moveElementToTop(locator, dim1, offsetY, tolerance);
				attachScreen();
			}
		}
	}
	
	/**
	 * This Method used to Scroll on TWC app and scroll up one card each time and
	 * navigate content pages of respective cards if applicable. Scrolling will end
	 * once it finds the given Card by name
	 * @param desiredCardName
	 * @param down
	 * @param scrollToTop
	 * @param offsetY
	 * @param tolerance
	 * @param includeDetailsPages
	 * @param navigateTwiceToDetailsPages
	 * @throws Exception
	 */
	public static void genericScrollTWC(String desiredCardName, boolean down, boolean scrollToTop, int offsetY, double tolerance,
			boolean includeDetailsPages, boolean navigateTwiceToDetailsPages) throws Exception {
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
		
		ReadExcelValues.excelValues("Smoke", "General");
		By byFooterCard = MobileBy.name(ReadExcelValues.data[1][Cap]);

		MobileElement ele = null;
		String cardName = "homescreen";
		String feedName = null;
		ArrayList<String> cardsList = new ArrayList<String>();
		nextGenIMadDisplayed = false;
		rainCardDisplayed = false;
		feedAdCount = 0;
		firstCard = null;
		secondCard = null;
		thirdCard = null;
		duration = TimeUnit.SECONDS.toMillis(duration);
		final long startTime = Calendar.getInstance().getTimeInMillis();
		long currTime = startTime;
		
		boolean elementFound = false;
		if (cardName.equalsIgnoreCase(desiredCardName)) {
			elementFound = true;
		}
		boolean footerElementFound = false;
		footerElementFound = TestBase.isElementDisplayed(byFooterCard);
		while (!elementFound && !footerElementFound && ((currTime - startTime) <= duration)) {
			/*
			 * List<MobileElement> els = Ad.findElements(MobileBy.xpath(
			 * "//XCUIElementTypeCollectionView[@y='0']/XCUIElementTypeCell"));
			 * System.out.println("Number Of Cards In List " + els.size() );
			 * System.out.println("********************************************"); for
			 * (MobileElement el: els) { Point loc = el.getLocation();
			 * System.out.println("Element located at " + loc ); Dimension dim =
			 * el.getSize(); System.out.println("Element Dimensions " + dim ); }
			 */
			System.out.println("********************************************");
			By byele = MobileBy.xpath("//XCUIElementTypeCollectionView[@y='0']/XCUIElementTypeCell[1]");
			ele = Ad.findElement(byele);
			Point loc = ele.getLocation();
			System.out.println("Element located at " + loc);
			Dimension dim = ele.getSize();
			System.out.println("Element Dimensions " + dim);
			System.out.println("Element Height is " + dim.height);
			int startY = loc.y + (int) (dim.height);
			int moveY = -(int) (dim.height);
			//if (startY == 88 || startY <= 88) {
			if (startY == offsetY || startY <= offsetY) {
				byele = MobileBy.xpath("//XCUIElementTypeCollectionView[@y='0']/XCUIElementTypeCell[2]");
				ele = Ad.findElement(byele);
				loc = ele.getLocation();
				System.out.println("Element located at " + loc);
				dim = ele.getSize();
				System.out.println("Element Dimensions " + dim);
				System.out.println("Element Height is " + dim.height);
				startY = loc.y + (int) (dim.height);
				moveY = -(int) (dim.height);
				//if (startY == 88 || startY <= 88) {
				if (startY == offsetY || startY <= offsetY) {
					byele = MobileBy.xpath("//XCUIElementTypeCollectionView[@y='0']/XCUIElementTypeCell[3]");
					ele = Ad.findElement(byele);
					loc = ele.getLocation();
					System.out.println("Element located at " + loc);
					dim = ele.getSize();
					System.out.println("Element Dimensions " + dim);
					System.out.println("Element Height is " + dim.height);
					startY = loc.y + (int) (dim.height);
					moveY = -(int) (dim.height);

				}
			}

			if (loc.y < 0) {
				genericVariance = 10;
				//performScrollTouchAction(1, startY, 0, -startY + 88);
				performScrollTouchAction(1, startY, 0, -startY + offsetY);
			} else {
				try {
					cardName = ele
							.findElement(By.xpath(
									"//XCUIElementTypeOther[@name='nextgen-integrated-marquee-card-containerView']"))
							.getAttribute("name");
				} catch (Exception e) {
					try {
						cardName = ele.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"))
								.getAttribute("name");
					} catch (Exception e1) {
						try {
							// some times when big advertisement displayed, it doenst have name as
							// 'Advertisement' hence below implemented
							cardName = ele
									.findElement(By.xpath("//XCUIElementTypeOther[@name='ads-card-containerView']"))
									.getAttribute("name");

						} catch (Exception e2) {
							try {
								// when Integrated Feed card is displayed, it doenst have name as
								// 'Advertisement' hence below implemented
								cardName = ele
										.findElement(By.xpath(
												"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']"))
										.getAttribute("name");

							} catch (Exception e3) {
								cardName = ele.findElement(By.xpath("(//XCUIElementTypeOther)[3]"))
										.getAttribute("name");
							}
						}

					}
				}

				if (cardName.equalsIgnoreCase("Advertisement")) {
					try {
						feedName = ele.findElement(By.xpath(
								"//XCUIElementTypeStaticText[@name='Advertisement']//parent::XCUIElementTypeOther"))
								.getAttribute("name");
						cardName = feedName.replaceAll("-adContainerView", "");
					} catch (Exception e) {
						// following is added to handle Integrated Feed Card while getting card name.
						try {
							feedName = ele.findElement(By.xpath(
									"//XCUIElementTypeStaticText[@name='Advertisement']//parent::XCUIElementTypeOther/following-sibling::XCUIElementTypeOther"))
									.getAttribute("name");
							cardName = feedName.replaceAll("-adContainerView", "");
						} catch (Exception e1) {
							cardName = "Advertisement";
						}
					}

				} else if (cardName.equalsIgnoreCase("ads-card-containerView")) {
					try {
						feedName = ele
								.findElement(By.xpath(
										"//XCUIElementTypeOther[@name='ads-card-containerView']/XCUIElementTypeOther"))
								.getAttribute("name");
						cardName = feedName.replaceAll("-adContainerView", "");
					} catch (Exception e) {
						cardName = "Advertisement";
					}
				} else if (cardName.equalsIgnoreCase("integrated-ad-card-containerView")) {
					try {
						feedName = ele.findElement(By.xpath(
								"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']//XCUIElementTypeOther[contains(@name,'-adContainerView')]"))
								.getAttribute("name");
						cardName = feedName.replaceAll("-adContainerView", "");
					} catch (Exception e) {
						cardName = "Advertisement";
					}
				} else if (cardName.equalsIgnoreCase("labelInsightContentTitle")) {
					try {
						feedName = ele.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"))
								.getAttribute("value");
						if (feedName.equalsIgnoreCase("Severe Outlook"))
						cardName = feedName;
					} catch (Exception e) {
						
					}
				}
				cardsList.add(cardName);
				System.out.println("Current Card is : " + cardName);
				logStep("Current Card is : " + cardName);
				attachScreen();

				if (cardName.equalsIgnoreCase("nextgen-integrated-marquee-card-containerView")) {
					nextGenIMadDisplayed = true;
				} else if (cardName.equalsIgnoreCase("Rain")) {
					rainCardDisplayed = true;
				}

				cardName = Utils.shortCardName(cardName);

				if (cardName.contains("weather.feed") || cardName.contains("Advertisement")) {
					feedAdCount++;
				}

				if (includeDetailsPages && !cardName.contains("weather.feed")) {
					try {

						if (cardName.equalsIgnoreCase("lifestyle")) {
							lStyleCardScreen.navigateToLifeStyleCardIndexes();
							if (navigateTwiceToDetailsPages) {
								lStyleCardScreen.navigateToLifeStyleCardIndexes();
							}
						} else if (cardName.equalsIgnoreCase("seasonalhub")) {
							// sHubCardScreen.navigateToFirstIndexOfSeasonalHubCard();
							sHubCardScreen.navigateToSeasonalHubCardIndexes();
							if (navigateTwiceToDetailsPages) {
								// sHubCardScreen.navigateToFirstIndexOfSeasonalHubCard();
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
				
				if (cardName.equalsIgnoreCase(desiredCardName)) {
					elementFound = true;
				}
				if (elementFound) {
					System.out.println("Element found in the view");
					logStep("Element found in the view");
					attachScreen();
					System.out.println("User done scrolling, Printing last 3 cards when Scroll ends");
					logStep("User done scrolling, Printing last 3 cards when Scroll ends");

					//getLastThreeCardsNames();
//					break;
				} else if (cardName.equalsIgnoreCase("Rain") && desiredCardName.equalsIgnoreCase("radar.largead")) {
					
					System.out.println("Since Rain Card appears, Map Card will not be displayed, hence skipping the navigation and further validation");
					logStep("Since Rain Card appears, Map Card will not be displayed, hence skipping the navigation and further validation");

					break;
				} else {
					if (startY - offsetY > 625) {
						System.out.println("Since Card position  >625, reducing startY valueto 625 to avoid press on sticky ad ");
						//logStep("Since Card position >625, reducing startY valueto 625 to avoid press on sticky ad ");
						startY = 625;
						performScrollTouchAction(1, startY, 0, moveY);
					} else {
						performScrollTouchAction(1, startY, 0, moveY);
					}
				}

				
			}
			footerElementFound = TestBase.isElementDisplayed(byFooterCard);
			currTime = Calendar.getInstance().getTimeInMillis();

		}
		System.out.println("Total time in milli seconds to navigate to all cards: "+(currTime - startTime));
		logStep("Total time in milli seconds to navigate to all cards: "+(currTime - startTime));
		getLastThreeCardsNames();
		
		System.out.println("Cards List: " + cardsList);
		logStep("Cards List: " + cardsList);
		System.out.println("Total no of Feed Ad Cards counted are :" + feedAdCount);
		logStep("Total no of Feed Ad Cards counted are :" + feedAdCount);
		
//		footerElementFound = TestBase.isElementDisplayed(byFooterCard);
		
		if (footerElementFound) {
			System.out.println("Since footer element found on screen, can't move element to top");
			if (desiredCardName.equalsIgnoreCase(firstCard) || desiredCardName.equalsIgnoreCase(secondCard) || desiredCardName.equalsIgnoreCase(thirdCard)) {
				elementFound = true;
			}
		} else {
			final Dimension dim1 = Ad.manage().window().getSize();
			if (scrollToTop) {
				System.out.println("Moving element to top");
				moveElementToTop(ele, dim1, offsetY, tolerance);
				attachScreen();
			}
		}
		
		
		if (elementFound == false) {
			if (cardName.equalsIgnoreCase("Rain") && desiredCardName.equalsIgnoreCase("radar.largead")) {
				System.out.println("Since Rain Card appears, Map Card will not be displayed, hence skipping the navigation and further validation");
				logStep("Since Rain Card appears, Map Card will not be displayed, hence skipping the navigation and further validation");
			} else {
				System.out.println("Corresponding card: " + desiredCardName + " is not found, hence navigation is failed");
				logStep("Corresponding card: " + desiredCardName + " is not found, hence navigation is failed");
				Assert.fail("Corresponding card: " + desiredCardName + " is not found");
			}
			
		}
	}
	
	/**
	 * This Method used to Scroll on TWC app and scroll up one card each time and
	 * navigate content pages of respective cards if applicable. Scrolling will end
	 * once it finds the given Card by name
	 * 
	 * @param desiredCardName
	 * @param down
	 * @param scrollToTop
	 * @param offsetY
	 * @param tolerance
	 * @param includeDetailsPages
	 * @param navigateTwiceToDetailsPages
	 * @throws Exception
	 */
	public static void genericScrollTWCAndroid(String desiredCardName, boolean down, boolean scrollToTop, int offsetY, double tolerance,
			boolean includeDetailsPages, boolean navigateTwiceToDetailsPages) throws Exception {
		AndroidHomeNavTab hmTab = new AndroidHomeNavTab(Ad);
		AndroidHourlyNavTab hrTab = new AndroidHourlyNavTab(Ad);
		AndroidDailyNavTab dTab = new AndroidDailyNavTab(Ad);
		AndroidRadarNavTab rTab = new AndroidRadarNavTab(Ad);
		AndroidVideoNavTab vTab = new AndroidVideoNavTab(Ad);
		AndroidAlertCenterScreen alertScreen = new AndroidAlertCenterScreen(Ad);
		
		AndroidLifeStyleCardScreen lStyleCardScreen = new AndroidLifeStyleCardScreen(Ad);
		AndroidSeasonalHubCardScreen sHubCardScreen = new AndroidSeasonalHubCardScreen(Ad);
		AndroidHourlyCardScreen hCardScreen = new AndroidHourlyCardScreen(Ad);
		AndroidDailyCardScreen dCardScreen = new AndroidDailyCardScreen(Ad);
		AndroidRadarCardScreen rCardScreen = new AndroidRadarCardScreen(Ad);
		AndroidTodayCardScreen tCardScreen = new AndroidTodayCardScreen(Ad);
		AndroidVideoCardScreen vCardScreen = new AndroidVideoCardScreen(Ad);
		AndroidAirQualityCardScreen aqCardScreen = new AndroidAirQualityCardScreen(Ad);
		AndroidNewsCardScreen nCardScreen = new AndroidNewsCardScreen(Ad);
		
		ReadExcelValues.excelValues("Smoke", "General");
		String footerText = ReadExcelValues.data[1][Cap];
		//By byFooterCard = MobileBy.name(ReadExcelValues.data[1][Cap]);
		
		By byFooterCard = MobileBy.xpath("//android.widget.TextView[@text='"+footerText+"']");

		MobileElement ele = null;
		String cardName = "homescreen";
		String feedName = null;
		ArrayList<String> cardsList = new ArrayList<String>();
		nextGenIMadDisplayed = false;
		rainCardDisplayed = false;
		feedAdCount = 0;
		firstCard = null;
		secondCard = null;
		thirdCard = null;
		duration = TimeUnit.SECONDS.toMillis(duration);
		final long startTime = Calendar.getInstance().getTimeInMillis();
		long currTime = startTime;

		boolean elementFound = false;
		if (cardName.equalsIgnoreCase(desiredCardName)) {
			elementFound = true;
		}
		boolean footerElementFound = false;
		footerElementFound = TestBase.isElementDisplayed(byFooterCard);
		while (!elementFound && !footerElementFound && ((currTime - startTime) <= duration)) {
		//while (!elementFound && !footerElementFound) {
			System.out.println("********************************************");
			By byele = MobileBy.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.weather.Weather:id/home_screen_list_view\"]/android.widget.FrameLayout[1]");
			ele = Ad.findElement(byele);
			Point loc = ele.getLocation();
			System.out.println("Element located at " + loc);
			Dimension dim = ele.getSize();
			System.out.println("Element Dimensions " + dim);
			System.out.println("Element Height is " + dim.height);
			//int startY = loc.y + (int) (dim.height);
			int startY = offsetY + (int) (dim.height);
			//int startY = (int) (dim.height);
			//int moveY = -(int) (dim.height)-(loc.y-offsetY);
			int moveY = -(int) (dim.height);
			//if (startY == offsetY || startY <= offsetY) {
			//if (startY - offsetY <= 15) {
			if ((int) (dim.height) <= 15) {
				byele = MobileBy.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.weather.Weather:id/home_screen_list_view\"]/android.widget.FrameLayout[2]");
				ele = Ad.findElement(byele);
				loc = ele.getLocation();
				System.out.println("Element located at " + loc);
				dim = ele.getSize();
				System.out.println("Element Dimensions " + dim);
				System.out.println("Element Height is " + dim.height);
				//startY = loc.y + (int) (dim.height);
				startY = offsetY + (int) (dim.height);
				//moveY = -(int) (dim.height)-(loc.y-offsetY);
				moveY = -(int) (dim.height);
				//if (startY == offsetY || startY <= offsetY) {
				//if (startY - offsetY <= 15) {
				if ((int) (dim.height) <= 15) {
					byele = MobileBy.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.weather.Weather:id/home_screen_list_view\"]/android.widget.FrameLayout[3]");
					ele = Ad.findElement(byele);
					loc = ele.getLocation();
					System.out.println("Element located at " + loc);
					dim = ele.getSize();
					System.out.println("Element Dimensions " + dim);
					System.out.println("Element Height is " + dim.height);
					//startY = loc.y + (int) (dim.height);
					startY = offsetY + (int) (dim.height);
					//moveY = -(int) (dim.height)-(loc.y-offsetY);
					moveY = -(int) (dim.height);

				}
			}

			//if (loc.y < 0) {
			if ((int) (dim.height) <= 35) {
				genericVariance = 20;
				performScrollTouchAction(1, startY, 0, -startY + offsetY);
			} else {

				try {
					cardName = ele
							.findElement(By.xpath(
									"//android.widget.TextView[@resource-id='com.weather.Weather:id/header_title']"))
							.getAttribute("text");
				} catch (Exception e) {
					try {
						cardName = ele.findElement(By.xpath("//*[@resource-id='com.weather.Weather:id/title']"))
								.getAttribute("text");
					} catch (Exception e1) {
						try {
							cardName = ele
									.findElement(By.xpath("//android.widget.TextView[@resource-id='com.weather.Weather:id/ad_card_title']"))
									.getAttribute("text");

						} catch (Exception e2) {
							try {
								
								MobileElement el = ele.findElement(By.xpath("//android.view.ViewGroup[@resource-id='com.weather.Weather:id/current_conditions_card_root']"));
								cardName = "Current Condition";
							} catch (Exception e3) {
								try {
									MobileElement el = ele.findElement(By.xpath("//android.widget.FrameLayout[@resource-id='com.weather.Weather:id/card_integrated_ad_root']"));
									cardName = "Integrated Marquee";

								} catch (Exception e4) {
									try {
									cardName = ele
												.findElement(By.xpath(
														"//android.widget.TextView"))
												.getAttribute("text");
									} catch (Exception e5) {
										e5.printStackTrace();
									}
									
								}
							}
						}

					}
				}

				
				cardsList.add(cardName);
				System.out.println("Current Card is : " + cardName);
				logStep("Current Card is : " + cardName);
				attachScreen();
				
				if (cardName.equalsIgnoreCase("Integrated Marquee")) {
					nextGenIMadDisplayed = true;
				} else if (cardName.equalsIgnoreCase("Rain")) {
					rainCardDisplayed = true;
				}

				/**
				 * Code to navigate to content pages.....
				 */
				if (includeDetailsPages && !cardName.contains("weather.feed")) {
					try {

						if (cardName.equalsIgnoreCase("Health & Activities")) {
							lStyleCardScreen.navigateToLifeStyleCardIndexes();
							if (navigateTwiceToDetailsPages) {
								lStyleCardScreen.navigateToLifeStyleCardIndexes();
							}
						} else if (cardName.equalsIgnoreCase("Outdoor Conditions")) {
							//sHubCardScreen.navigateToFirstIndexOfSeasonalHubCard();
							sHubCardScreen.navigateToSeasonalHubCardIndexes();
							if (navigateTwiceToDetailsPages) {
								//sHubCardScreen.navigateToFirstIndexOfSeasonalHubCard();
								sHubCardScreen.navigateToSeasonalHubCardIndexes();
							}
						} else if (cardName.equalsIgnoreCase("Hourly Forecast")) {
							hCardScreen.navigateToHourlyCardContentPage();
							if (navigateTwiceToDetailsPages) {
								hCardScreen.navigateToHourlyCardContentPage();
							}
						} else if (cardName.equalsIgnoreCase("Daily Forecast")) {
							dCardScreen.navigateToDailyCardContentPage();
							if (navigateTwiceToDetailsPages) {
								dCardScreen.navigateToDailyCardContentPage();
							}
						} else if (cardName.equalsIgnoreCase("hurricane-central")) {
							// navigate to hurricanecentral card content page
						} else if (cardName.equalsIgnoreCase("Radar & Maps")) {
							rCardScreen.navigateToRadarCardContentPage();
							if (navigateTwiceToDetailsPages) {
								rCardScreen.navigateToRadarCardContentPage();
							}
						} else if (cardName.equalsIgnoreCase("snapshot")) {
							// navigate to snapshot card content page
						} else if (cardName.equalsIgnoreCase("Today's Details")) {
							tCardScreen.navigateToTodayCardContentPage();
							if (navigateTwiceToDetailsPages) {
								tCardScreen.navigateToTodayCardContentPage();
							}
						} else if (cardName.equalsIgnoreCase("Latest News")) {
							vCardScreen.navigateToVideoCardContentPage();
							if (navigateTwiceToDetailsPages) {
								vCardScreen.navigateToVideoCardContentPage();
							}

						} else if (cardName.equalsIgnoreCase("Air Quality")) {
							aqCardScreen.navigateToAirQualityCardContentPage();
							if (navigateTwiceToDetailsPages) {
								aqCardScreen.navigateToAirQualityCardContentPage();
							}

						} else if (cardName.equalsIgnoreCase("More News")) {
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
				
				if (cardName.equalsIgnoreCase(desiredCardName)) {
					elementFound = true;
				}
				if (elementFound) {
					System.out.println("Element found in the view");
					logStep("Element found in the view");
					attachScreen();
					System.out.println("User done scrolling, Printing last 3 cards when Scroll ends");
					logStep("User done scrolling, Printing last 3 cards when Scroll ends");

					//getLastThreeCardsNames();
					break;
				} else if (cardName.equalsIgnoreCase("Rain") && desiredCardName.equalsIgnoreCase("Radar & Maps")) {
					
					System.out.println("Since Rain Card appears, Map Card will not be displayed, hence skipping the navigation and further validation");
					logStep("Since Rain Card appears, Map Card will not be displayed, hence skipping the navigation and further validation");

					break;
				} else {
					int nextY = 0;
					int currentY = startY - getOffsetYTop();
					//if (startY - getOffsetYTop() > 1250) {
					if (currentY > 500) {
						//startY = 1250;
						while (startY - offsetY > 500) {
							nextY = startY - (500 + offsetY);
							startY = 500 + offsetY;
							
							System.out.println("Since Card position  >724, reducing startY valueto 724 to avoid press on sticky ad ");
							//logStep("Since Card position >724, reducing startY valueto 724 to avoid press on sticky ad ");
							performScrollTouchAction(1, startY, 0, -startY + offsetY+15);				
							startY = nextY + getOffsetYTop();
						}
						if (startY - offsetY >= 35) {
							performScrollTouchAction(1, startY, 0, -startY + offsetY+15);
						}
					//performScrollTouchAction(1, startY, 0, moveY);
					//performScrollTouchAction(1, startY, 0, -startY + offsetY);
				} else {
					performScrollTouchAction(1, startY, 0, -startY + offsetY);
				}
				}
				
				
			}	
		
			
			
			footerElementFound = TestBase.isElementDisplayed(byFooterCard);
			currTime = Calendar.getInstance().getTimeInMillis();

		}
		System.out.println("Total time in milli seconds to navigate to all cards and contents: "+(currTime - startTime));
		logStep("Total time in milli seconds to navigate to all cards and contents: "+(currTime - startTime));
		getLastThreeCardsNamesAndroid();
		System.out.println("Cards List: " + cardsList);
		logStep("Cards List: " + cardsList);
		System.out.println("Total no of Feed Ad Cards counted are :" + feedAdCount);
		logStep("Total no of Feed Ad Cards counted are :" + feedAdCount);

		if (footerElementFound) {
			System.out.println("Since footer element found on screen, can't move element to top");
			if (desiredCardName.equalsIgnoreCase(firstCard) || desiredCardName.equalsIgnoreCase(secondCard) || desiredCardName.equalsIgnoreCase(thirdCard)) {
				elementFound = true;
			}
		} else {
			final Dimension dim1 = Ad.manage().window().getSize();
			if (scrollToTop) {
				System.out.println("Moving element to top");
				moveElementToTop(ele, dim1, offsetY, tolerance);
				attachScreen();
			}
		}
		
		if (elementFound == false) {
			if (cardName.equalsIgnoreCase("Rain") && desiredCardName.equalsIgnoreCase("Radar & Maps")) {
				System.out.println("Since Rain Card appears, Map Card will not be displayed, hence skipping the navigation and further validation");
				logStep("Since Rain Card appears, Map Card will not be displayed, hence skipping the navigation and further validation");
			} else {
				System.out.println("Corresponding card: " + desiredCardName + " is not found, hence navigation is failed");
				logStep("Corresponding card: " + desiredCardName + " is not found, hence navigation is failed");
				Assert.fail("Corresponding card: " + desiredCardName + " is not found");
			}
			
		}
	}
	
	/**
	 * Performs a touch action, using RELATIVE coordinates for destination, as
	 * stated in TouchAction API.
	 *
	 * @param startX absolute X to start at
	 * @param startY absolute Y to start at
	 * @param moveX  relative X to move to
	 * @param moveY  relative Y to move to
	 */
	public static void performScrollTouchAction(int startX, int startY, int moveX, int moveY) throws Exception {
		System.out.println("Performing TouchAction starting from (" + startX + ", " + startY + ") and moving by ("
				+ moveX + ", " + moveY + ")");
		if (Ad instanceof IOSDriver<?>) {
			
			try {
				new TouchAction<>((PerformsTouchActions) Ad)
						.press(PointOption.point(startX, startY + genericVariance))
						.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
						.moveTo(PointOption.point(startX + moveX, startY + moveY))
						.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).release().perform();

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			genericVariance = 0;
			// BaseTest.wait(1, true);
			Thread.sleep(1000);
		} else if (Ad instanceof AndroidDriver<?>) {
	
			try {
				new TouchAction<>((PerformsTouchActions) Ad).press(PointOption.point(startX, startY + genericVariance))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
				.moveTo(PointOption.point(startX + moveX, startY + moveY)).release().perform();
			} catch (Exception e) {
				e.printStackTrace();
			}
						
			genericVariance = 0;
			Thread.sleep(1000);
		}

	}
	
	/**
	 * Performs a touch action, using RELATIVE coordinates for destination, as
	 * stated in TouchAction API.
	 *
	 * @param startX absolute X to start at
	 * @param startY absolute Y to start at
	 * @param moveX  relative X to move to
	 * @param moveY  relative Y to move to
	 */
	public static void performScrollTouchAction(int startX, int startY, int moveX, int moveY, AppiumDriver<MobileElement> Ad) throws Exception {
		System.out.println("Performing TouchAction starting from (" + startX + ", " + startY + ") and moving by ("
				+ moveX + ", " + moveY + ")");
		if (Ad instanceof IOSDriver<?>) {
			
			try {
				new TouchAction<>((PerformsTouchActions) Ad)
						.press(PointOption.point(startX, startY + genericVariance))
						.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
						.moveTo(PointOption.point(startX + moveX, startY + moveY))
						.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).release().perform();

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			genericVariance = 0;
			// BaseTest.wait(1, true);
			Thread.sleep(1000);
		} else if (Ad instanceof AndroidDriver<?>) {
	
			try {
				new TouchAction<>((PerformsTouchActions) Ad).press(PointOption.point(startX, startY + genericVariance))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
				.moveTo(PointOption.point(startX + moveX, startY + moveY)).release().perform();
			} catch (Exception e) {
				e.printStackTrace();
			}
						
			genericVariance = 0;
			Thread.sleep(1000);
		}

	}
	
		
	/**
	 * Method to move element to the top of screen.
	 * @param element Element
	 * @param dim  Bounds
	 * @param offsetY Padding to what is top
	 * @throws java.lang.Exception 
	 */
	public static void moveElementToTop(final By locator, final Dimension dim, int offsetY, double tolerance) throws java.lang.Exception {
		WebElement element =  Ad.findElement(locator);
		Point loc = element.getLocation();
		System.out.println("Element reported at " + loc + " before move to top");

		int MAX_SCROLL_TRIES = 1;
		if(offsetY > 0) {
			//MAX_SCROLL_TRIES = 10;
			MAX_SCROLL_TRIES = 6;
		}
		int tries = 0;
		
		while(tries++ < MAX_SCROLL_TRIES && !isElementAtTop(dim, loc, offsetY, tolerance)) {
			if(loc.y < offsetY) {
				System.out.println("element has been scrolled past top");
				if(Math.abs(offsetY - loc.y) < 200) { // to help not do mini scrolling
					swipe(Direction.DOWN, 0.15);
					loc = element.getLocation();
				}
			} else if(loc.y == offsetY) {
				System.out.println("element exactly at threshold... maybe its cropped");
				swipe(Direction.DOWN, 0.15);
				loc = element.getLocation();
			} else {
				System.out.println("element needs to be scrolled up some more");
				if(Math.abs(offsetY - loc.y) < 200) { // to help not do mini scrolling
					swipe(Direction.UP, 0.15);
					loc = element.getLocation();
				}
			}
			performScrollTouchAction(1, loc.y, 0, offsetY - loc.y);
			loc = element.getLocation();
			System.out.println("Element reported at " + loc + " after move to top");

		}
		 
			
		
		
		if(isElementAtTop(dim, loc, offsetY, tolerance)) {
			System.out.println("Element at top of page");
		}
	}
	
	/**
	 * Method to move element to the top of screen.
	 * @param element Element
	 * @param dim  Bounds
	 * @param offsetY Padding to what is top
	 * @throws java.lang.Exception 
	 */
	public static void moveElementToTop(final By locator, final Dimension dim, int offsetY, double tolerance, AppiumDriver<MobileElement> Ad) throws java.lang.Exception {
		WebElement element =  Ad.findElement(locator);
		Point loc = element.getLocation();
		System.out.println("Element reported at " + loc + " before move to top");

		int MAX_SCROLL_TRIES = 1;
		if(offsetY > 0) {
			//MAX_SCROLL_TRIES = 10;
			MAX_SCROLL_TRIES = 6;
		}
		int tries = 0;
		
		while(tries++ < MAX_SCROLL_TRIES && !isElementAtTop(dim, loc, offsetY, tolerance, Ad)) {
			if(loc.y < offsetY) {
				System.out.println("element has been scrolled past top");
				if(Math.abs(offsetY - loc.y) < 200) { // to help not do mini scrolling
					swipe(Direction.DOWN, 0.15, Ad);
					loc = element.getLocation();
				}
			} else if(loc.y == offsetY) {
				System.out.println("element exactly at threshold... maybe its cropped");
				swipe(Direction.DOWN, 0.15, Ad);
				loc = element.getLocation();
			} else {
				System.out.println("element needs to be scrolled up some more");
				if(Math.abs(offsetY - loc.y) < 200) { // to help not do mini scrolling
					swipe(Direction.UP, 0.15, Ad);
					loc = element.getLocation();
				}
			}
			performScrollTouchAction(1, loc.y, 0, offsetY - loc.y, Ad);
			loc = element.getLocation();
			System.out.println("Element reported at " + loc + " after move to top");

		}
		
		
		if(isElementAtTop(dim, loc, offsetY, tolerance, Ad)) {
			System.out.println("Element at top of page");
		}
	}
	
	public static void moveElementToTop(final MobileElement element, final Dimension dim, int offsetY, double tolerance) throws java.lang.Exception {
		Point loc = element.getLocation();
		System.out.println("Element reported at " + loc + " before move to top");

		int MAX_SCROLL_TRIES = 1;
		if(offsetY > 0) {
			MAX_SCROLL_TRIES = 6;
		}

		int tries = 0;
		
		while(tries++ < MAX_SCROLL_TRIES && !isElementAtTop(dim, loc, offsetY, tolerance)) {
			if(loc.y < offsetY) {
				System.out.println("element has been scrolled past top");
				if(Math.abs(offsetY - loc.y) < 200) { // to help not do mini scrolling
					swipe(Direction.DOWN, 0.15);
					loc = element.getLocation();
				}
			} else if(loc.y == offsetY) {
				System.out.println("element exactly at threshold... maybe its cropped");
				swipe(Direction.DOWN, 0.15);
				loc = element.getLocation();
			} else {
				System.out.println("element needs to be scrolled up some more");
				if(Math.abs(offsetY - loc.y) < 200) { // to help not do mini scrolling
					swipe(Direction.UP, 0.15);
					loc = element.getLocation();
				}
			}
			performScrollTouchAction(1, loc.y, 0, offsetY - loc.y);
			loc = element.getLocation();
			System.out.println("Element reported at " + loc + " after move to top");

		}
		  
		
		if(isElementAtTop(dim, loc, offsetY, tolerance)) {
			System.out.println("Element at top of page");
		}
	}
	
	public static void moveElementToTop(final MobileElement element, final Dimension dim, int offsetY, double tolerance, AppiumDriver<MobileElement> Ad) throws java.lang.Exception {
		Point loc = element.getLocation();
		System.out.println("Element reported at " + loc + " before move to top");

		int MAX_SCROLL_TRIES = 1;
		if(offsetY > 0) {
			MAX_SCROLL_TRIES = 6;
		}

		int tries = 0;
		
		while(tries++ < MAX_SCROLL_TRIES && !isElementAtTop(dim, loc, offsetY, tolerance, Ad)) {
			if(loc.y < offsetY) {
				System.out.println("element has been scrolled past top");
				if(Math.abs(offsetY - loc.y) < 200) { // to help not do mini scrolling
					swipe(Direction.DOWN, 0.15, Ad);
					loc = element.getLocation();
				}
			} else if(loc.y == offsetY) {
				System.out.println("element exactly at threshold... maybe its cropped");
				swipe(Direction.DOWN, 0.15, Ad);
				loc = element.getLocation();
			} else {
				System.out.println("element needs to be scrolled up some more");
				if(Math.abs(offsetY - loc.y) < 200) { // to help not do mini scrolling
					swipe(Direction.UP, 0.15, Ad);
					loc = element.getLocation();
				}
			}
			
			performScrollTouchAction(1, loc.y, 0, offsetY - loc.y, Ad);
			loc = element.getLocation();
			System.out.println("Element reported at " + loc + " after move to top");

		}
		
		if(isElementAtTop(dim, loc, offsetY, tolerance, Ad)) {
			System.out.println("Element at top of page");
		}
	}
	
	/**
	 * Common generic method to perform scroll or swipe by given value.
	 *
	 * @param direction Direction to swipe
	 * @param howMuch   a number between 0.0 to 1.0. for e.g. 0.5 denotes 50% swipe on
	 *                  screen
	 */
	public static void swipe(final Direction direction, final double howMuch) throws java.lang.Exception {
		try {
			swipe(new Point(0, 0), Ad.manage().window().getSize(), direction, howMuch);
		} catch (final WebDriverException e) {
			System.out.println("Unable to perform swipe for the given params, direction : [" + direction.name() + "]");
		}
	}
	
	/**
	 * Common generic method to perform scroll or swipe by given value.
	 *
	 * @param direction Direction to swipe
	 * @param howMuch   a number between 0.0 to 1.0. for e.g. 0.5 denotes 50% swipe on
	 *                  screen
	 */
	public static void swipe(final Direction direction, final double howMuch, AppiumDriver<MobileElement> Ad) throws java.lang.Exception {
		try {
			swipe(new Point(0, 0), Ad.manage().window().getSize(), direction, howMuch, Ad);
		} catch (final WebDriverException e) {
			System.out.println("Unable to perform swipe for the given params, direction : [" + direction.name() + "]");
		}
	}
	
	/**
	 * Common generic method to perform scroll or swipe by given value.
	 *
	 * @param element   parent element to perform swipe/scroll within
	 * @param direction Direction to swipe
	 * @param howMuch   a number between 0.0 to 1.0. for e.g. 0.5 denotes 50% swipe on
	 *                  screen
	 * @throws java.lang.Exception 
	 */
	public static void swipe(final MobileElement element, final Direction direction, final double howMuch) throws java.lang.Exception {
		swipe(element.getLocation(), element.getSize(), direction, howMuch);
	}
	
	/**
	 * Common generic method to perform scroll or swipe by given value.
	 *
	 * @param element   parent element to perform swipe/scroll within
	 * @param direction Direction to swipe
	 * @param howMuch   a number between 0.0 to 1.0. for e.g. 0.5 denotes 50% swipe on
	 *                  screen
	 * @throws java.lang.Exception 
	 */
	public static void swipe(final MobileElement element, final Direction direction, final double howMuch, AppiumDriver<MobileElement> Ad) throws java.lang.Exception {
		swipe(element.getLocation(), element.getSize(), direction, howMuch, Ad);
	}
	
	/**
	 * Common generic method to perform scroll or swipe on a particular element or card by given value.
	 *
	 * @param direction Direction to swipe
	 * @param howMuch   a number between 0.0 to 1.0. for e.g. 0.5 denotes 50% swipe on
	 *                  screen
	 */
	public static void swipe(final By locator, final Direction direction, final double howMuch) throws java.lang.Exception {
		try {
			swipe(locator, new Point(0, 0),  direction, howMuch);
		} catch (final WebDriverException e) {
			System.out.println("Unable to perform swipe for the given params, direction : [" + direction.name() + "]");
		}
	}
	
	/**
	 * Common generic method to perform scroll or swipe on a particular element or card by given value.
	 *
	 * @param direction Direction to swipe
	 * @param howMuch   a number between 0.0 to 1.0. for e.g. 0.5 denotes 50% swipe on
	 *                  screen
	 */
	public static void swipe(final By locator, final Direction direction, final double howMuch, AppiumDriver<MobileElement> Ad) throws java.lang.Exception {
		try {
			swipe(locator, new Point(0, 0),  direction, howMuch, Ad);
		} catch (final WebDriverException e) {
			System.out.println("Unable to perform swipe for the given params, direction : [" + direction.name() + "]");
		}
	}
	
	
	/**
	 * Common generic method to perform scroll or swipe by given value.
	 *
	 * @param originPoint Origin point for coordinates. Pass 0,0 for coordinates related
	 *                    to screen.
	 * @param dim         Element size / screen size
	 * @param direction   Direction to swipe
	 * @param howMuch     unit to swipe from (0 to 1)
	 */
	public static void swipe(Point originPoint, Dimension dim, final Direction direction, final double howMuch) throws java.lang.Exception {
	//	System.out.println("Swiping " + direction + " by " + (howMuch * 100) + "%", true);
		if((howMuch > 1) || (howMuch < 0)) {
			throw new UnsupportedOperationException("Swipe unit :" + howMuch + " should be in between 0.0 to 1.0");
		}

		final Point bottomRight = new Point(originPoint.x + dim.width, originPoint.y + dim.height);
		final Point centerPoint = new Point(originPoint.x + (dim.width / 2), originPoint.y + (dim.height / 2));
		int startX = 0, startY = 0, moveX = 0, moveY = 0, offset = 0;
		if((direction == Direction.LEFT) || (direction == Direction.RIGHT)) {
			offset = (dim.width - (int)(dim.width * howMuch)) / 2;
			moveY = 0;
		} else if((direction == Direction.UP) || (direction == Direction.DOWN)) {
			offset = (dim.height - (int)(dim.height * howMuch)) / 2;
			moveX = 0;
		}

		switch (direction) {
			case LEFT:
				startX = bottomRight.x - offset;
				startY = centerPoint.y;
				moveX = (int)(dim.width * howMuch) * (-1);
				break;
			case RIGHT:
				startX = originPoint.x + offset;
				startY = centerPoint.y;
				moveX = (int)(dim.width * howMuch);
				break;
			case UP:
				startX = centerPoint.x;
				startY = (bottomRight.y - offset);
				moveY = (int)(dim.height * howMuch) * (-1);
				break;
			case DOWN:
				startX = centerPoint.x;
				startY = originPoint.y + offset;
				moveY = (int)(dim.height * howMuch);
				break;
			default:
				System.out.println("Invalid swipe direction: " + direction);
				break;
		}

		performScrollTouchAction(startX, startY, moveX, moveY);
		System.out.println("Swipe complete");
	}
	
	/**
	 * Common generic method to perform scroll or swipe by given value.
	 *
	 * @param originPoint Origin point for coordinates. Pass 0,0 for coordinates related
	 *                    to screen.
	 * @param dim         Element size / screen size
	 * @param direction   Direction to swipe
	 * @param howMuch     unit to swipe from (0 to 1)
	 */
	public static void swipe(Point originPoint, Dimension dim, final Direction direction, final double howMuch, AppiumDriver<MobileElement> Ad) throws java.lang.Exception {
	//	System.out.println("Swiping " + direction + " by " + (howMuch * 100) + "%", true);
		if((howMuch > 1) || (howMuch < 0)) {
			throw new UnsupportedOperationException("Swipe unit :" + howMuch + " should be in between 0.0 to 1.0");
		}

		final Point bottomRight = new Point(originPoint.x + dim.width, originPoint.y + dim.height);
		final Point centerPoint = new Point(originPoint.x + (dim.width / 2), originPoint.y + (dim.height / 2));
		int startX = 0, startY = 0, moveX = 0, moveY = 0, offset = 0;
		if((direction == Direction.LEFT) || (direction == Direction.RIGHT)) {
			offset = (dim.width - (int)(dim.width * howMuch)) / 2;
			moveY = 0;
		} else if((direction == Direction.UP) || (direction == Direction.DOWN)) {
			offset = (dim.height - (int)(dim.height * howMuch)) / 2;
			moveX = 0;
		}

		switch (direction) {
			case LEFT:
				startX = bottomRight.x - offset;
				startY = centerPoint.y;
				moveX = (int)(dim.width * howMuch) * (-1);
				break;
			case RIGHT:
				startX = originPoint.x + offset;
				startY = centerPoint.y;
				moveX = (int)(dim.width * howMuch);
				break;
			case UP:
				startX = centerPoint.x;
				startY = (bottomRight.y - offset);
				moveY = (int)(dim.height * howMuch) * (-1);
				break;
			case DOWN:
				startX = centerPoint.x;
				startY = originPoint.y + offset;
				moveY = (int)(dim.height * howMuch);
				break;
			default:
				System.out.println("Invalid swipe direction: " + direction);
				break;
		}

		performScrollTouchAction(startX, startY, moveX, moveY, Ad);
		System.out.println("Swipe complete");
	}
	
	/**
	 * Common generic method to perform scroll or swipe by given locator.
	 *
	 * @param originPoint Origin point for coordinates. Pass 0,0 for coordinates related
	 *                    to screen.
	 * @param dim         Element size / screen size
	 * @param direction   Direction to swipe
	 * @param howMuch     unit to swipe from (0 to 1)
	 */
	public static void swipe(final By locator, Point originPoint, final Direction direction, final double howMuch) throws java.lang.Exception {
		//	System.out.println("Swiping " + direction + " by " + (howMuch * 100) + "%", true);
		WebElement element =  Ad.findElement(locator);
		Point loc = element.getLocation();
		Dimension dim = element.getSize();
		System.out.println("Element reported at " + loc + " before move to top");
			if((howMuch > 1) || (howMuch < 0)) {
				throw new UnsupportedOperationException("Swipe unit :" + howMuch + " should be in between 0.0 to 1.0");
			}

			final Point bottomRight = new Point(originPoint.x + dim.width, originPoint.y + dim.height+loc.y);
			final Point centerPoint = new Point(originPoint.x + (dim.width / 2), originPoint.y + (dim.height / 2)+loc.y);
			int startX = 0, startY = 0, moveX = 0, moveY = 0, offset = 0;
			if((direction == Direction.LEFT) || (direction == Direction.RIGHT)) {
				offset = (dim.width - (int)(dim.width * howMuch)) / 2;
				moveY = 0;
			} else if((direction == Direction.UP) || (direction == Direction.DOWN)) {
				offset = (dim.height - (int)(dim.height * howMuch)) / 2;
				moveX = 0;
			}

			switch (direction) {
				case LEFT:
					startX = bottomRight.x - offset;
					startY = centerPoint.y;
					moveX = (int)(dim.width * howMuch) * (-1);
					break;
				case RIGHT:
					startX = originPoint.x + offset;
					startY = centerPoint.y;
					moveX = (int)(dim.width * howMuch);
					break;
				case UP:
					startX = centerPoint.x;
					startY = (bottomRight.y - offset);
					moveY = (int)(dim.height * howMuch) * (-1);
					break;
				case DOWN:
					startX = centerPoint.x;
					startY = originPoint.y + offset;
					moveY = (int)(dim.height * howMuch);
					break;
				default:
					System.out.println("Invalid swipe direction: " + direction);
					break;
			}

			performScrollTouchAction(startX, startY, moveX, moveY);
			System.out.println("Swipe complete");
		}
	
	/**
	 * Common generic method to perform scroll or swipe by given locator.
	 *
	 * @param originPoint Origin point for coordinates. Pass 0,0 for coordinates related
	 *                    to screen.
	 * @param dim         Element size / screen size
	 * @param direction   Direction to swipe
	 * @param howMuch     unit to swipe from (0 to 1)
	 */
	public static void swipe(final By locator, Point originPoint, final Direction direction, final double howMuch, AppiumDriver<MobileElement> Ad) throws java.lang.Exception {
		//	System.out.println("Swiping " + direction + " by " + (howMuch * 100) + "%", true);
		WebElement element =  Ad.findElement(locator);
		Point loc = element.getLocation();
		Dimension dim = element.getSize();
		System.out.println("Element reported at " + loc + " before move to top");
			if((howMuch > 1) || (howMuch < 0)) {
				throw new UnsupportedOperationException("Swipe unit :" + howMuch + " should be in between 0.0 to 1.0");
			}

			final Point bottomRight = new Point(originPoint.x + dim.width, originPoint.y + dim.height+loc.y);
			final Point centerPoint = new Point(originPoint.x + (dim.width / 2), originPoint.y + (dim.height / 2)+loc.y);
			int startX = 0, startY = 0, moveX = 0, moveY = 0, offset = 0;
			if((direction == Direction.LEFT) || (direction == Direction.RIGHT)) {
				offset = (dim.width - (int)(dim.width * howMuch)) / 2;
				moveY = 0;
			} else if((direction == Direction.UP) || (direction == Direction.DOWN)) {
				offset = (dim.height - (int)(dim.height * howMuch)) / 2;
				moveX = 0;
			}

			switch (direction) {
				case LEFT:
					startX = bottomRight.x - offset;
					startY = centerPoint.y;
					moveX = (int)(dim.width * howMuch) * (-1);
					break;
				case RIGHT:
					startX = originPoint.x + offset;
					startY = centerPoint.y;
					moveX = (int)(dim.width * howMuch);
					break;
				case UP:
					startX = centerPoint.x;
					startY = (bottomRight.y - offset);
					moveY = (int)(dim.height * howMuch) * (-1);
					break;
				case DOWN:
					startX = centerPoint.x;
					startY = originPoint.y + offset;
					moveY = (int)(dim.height * howMuch);
					break;
				default:
					System.out.println("Invalid swipe direction: " + direction);
					break;
			}

			performScrollTouchAction(startX, startY, moveX, moveY, Ad);
			System.out.println("Swipe complete");
		}
	/**
	 * Enum for different swipe directions
	 */
	public static enum Direction {
		LEFT,
		RIGHT,
		UP,
		DOWN;
	}
	
	/**
	 * Method to check the element is present at top of the screen.
	 * Said element to be at top when it is within 5% of dimension.
	 *
	 * @param dim Bounds
	 * @param loc Element's location
	 * @param offsetY Add padding to what is top
	 * @return true if element is within 5% of top
	 */
	public static boolean isElementAtTop(final Dimension dim, final Point loc, int offsetY, double tolerance) {
		boolean flag = false;
		if (Ad instanceof IOSDriver<?>) {
			if(loc.y > offsetY && loc.y <= (offsetY + dim.height * tolerance)) {
				System.out.println("Element at top of page");
				//return true;
				flag = true;
			} else {
				System.out.println("Element not at top of page");
				//return false;
				flag = false;
			}
		}  else if (Ad instanceof AndroidDriver<?>)  {
			if(loc.y > offsetY && loc.y <= (offsetY + dim.height * tolerance)) {
				System.out.println("Element at top of page");
				//return true;
				flag = true;
			} else {
				System.out.println("Element not at top of page");
				//return false;
				flag = false;
			}
		}
		return flag;
	}
	
	/**
	 * Method to check the element is present at top of the screen.
	 * Said element to be at top when it is within 5% of dimension.
	 *
	 * @param dim Bounds
	 * @param loc Element's location
	 * @param offsetY Add padding to what is top
	 * @return true if element is within 5% of top
	 */
	public static boolean isElementAtTop(final Dimension dim, final Point loc, int offsetY, double tolerance, AppiumDriver<MobileElement> Ad) {
		boolean flag = false;
		if (Ad instanceof IOSDriver<?>) {
			if(loc.y > offsetY && loc.y <= (offsetY + dim.height * tolerance)) {
				System.out.println("Element at top of page");
				//return true;
				flag = true;
			} else {
				System.out.println("Element not at top of page");
				//return false;
				flag = false;
			}
		}  else if (Ad instanceof AndroidDriver<?>)  {
			if(loc.y > offsetY && loc.y <= (offsetY + dim.height * tolerance)) {
				System.out.println("Element at top of page");
				//return true;
				flag = true;
			} else {
				System.out.println("Element not at top of page");
				//return false;
				flag = false;
			}
		}
		return flag;
	}
	
	
	/**
	 * Print Last Three Card Names of TWC iOS App when Scrolling ends.
	 */
	public static void getLastThreeCardsNames() {
		By byele = MobileBy.xpath("//XCUIElementTypeCollectionView[@y='0']/XCUIElementTypeCell[1]");
		MobileElement ele = Ad.findElement(byele);
		String cardName = "homescreen";
		try {
			byele = MobileBy.xpath("//XCUIElementTypeCollectionView[@y='0']/XCUIElementTypeCell[1]");
			ele = Ad.findElement(byele);
			cardName = ele
					.findElement(By.xpath("//XCUIElementTypeOther[@name='nextgen-integrated-marquee-card-containerView']"))
					.getAttribute("name");
		} catch (Exception e) {
			try {
				cardName = ele
						.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"))
						.getAttribute("name");
				 if (cardName.equalsIgnoreCase("labelInsightContentTitle")) {
						try {
							String feedName = ele.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"))
									.getAttribute("value");
							if (feedName.equalsIgnoreCase("Severe Outlook"))
							cardName = feedName;
						} catch (Exception e1) {
							
						}
					}
			} catch (Exception e1) {
				try {
					// some times when big advertisement displayed, it doenst have name as
					// 'Advertisement' hence below implemented
					cardName = ele
							.findElement(By.xpath("//XCUIElementTypeOther[@name='ads-card-containerView']"))
							.getAttribute("name");
					
				} catch (Exception e2) {
					try {
						// when Integrated Feed card is displayed, it doenst have name as
						// 'Advertisement' hence below implemented
						cardName = ele
								.findElement(By.xpath(
										"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']"))
								.getAttribute("name");
						

					}catch (Exception e3) {
						cardName = ele.findElement(By.xpath("(//XCUIElementTypeOther)[3]"))
								.getAttribute("name");
					}
				}
				
			}
		}
		firstCard = cardName;
		System.out.println("1st  Card is : " + cardName);
		logStep("1st  Card is : " + cardName);
		
		
		try {
			byele = MobileBy.xpath("//XCUIElementTypeCollectionView[@y='0']/XCUIElementTypeCell[2]");
			ele = Ad.findElement(byele);
			cardName = ele
					.findElement(By.xpath("//XCUIElementTypeOther[@name='nextgen-integrated-marquee-card-containerView']"))
					.getAttribute("name");
		} catch (Exception e) {
			try {
				cardName = ele
						.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"))
						.getAttribute("name");
				 if (cardName.equalsIgnoreCase("labelInsightContentTitle")) {
						try {
							String feedName = ele.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"))
									.getAttribute("value");
							if (feedName.equalsIgnoreCase("Severe Outlook"))
							cardName = feedName;
						} catch (Exception e1) {
							
						}
					}
			} catch (Exception e1) {
				try {
					// some times when big advertisement displayed, it doenst have name as
					// 'Advertisement' hence below implemented
					cardName = ele
							.findElement(By.xpath("//XCUIElementTypeOther[@name='ads-card-containerView']"))
							.getAttribute("name");
					
				} catch (Exception e2) {
					try {
						// when Integrated Feed card is displayed, it doenst have name as
						// 'Advertisement' hence below implemented
						cardName = ele
								.findElement(By.xpath(
										"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']"))
								.getAttribute("name");
						

					}catch (Exception e3) {
						cardName = ele.findElement(By.xpath("(//XCUIElementTypeOther)[3]"))
								.getAttribute("name");
					}
				}
				
			}
		}
		secondCard = cardName;
		System.out.println("2nd  Card is : " + cardName);
		logStep("2nd  Card is : " + cardName);
		
		
		try {
			byele = MobileBy.xpath("//XCUIElementTypeCollectionView[@y='0']/XCUIElementTypeCell[3]");
			ele = Ad.findElement(byele);
			cardName = ele
					.findElement(By.xpath("//XCUIElementTypeOther[@name='nextgen-integrated-marquee-card-containerView']"))
					.getAttribute("name");
		} catch (Exception e) {
			try {
				cardName = ele
						.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"))
						.getAttribute("name");
				 if (cardName.equalsIgnoreCase("labelInsightContentTitle")) {
					try {
						String feedName = ele.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"))
								.getAttribute("value");
						if (feedName.equalsIgnoreCase("Severe Outlook"))
						cardName = feedName;
					} catch (Exception e1) {
						
					}
				}
			} catch (Exception e1) {
				try {
					// some times when big advertisement displayed, it doenst have name as
					// 'Advertisement' hence below implemented
					cardName = ele
							.findElement(By.xpath("//XCUIElementTypeOther[@name='ads-card-containerView']"))
							.getAttribute("name");
					
				} catch (Exception e2) {
					try {
						// when Integrated Feed card is displayed, it doenst have name as
						// 'Advertisement' hence below implemented
						cardName = ele
								.findElement(By.xpath(
										"//XCUIElementTypeOther[@name='integrated-ad-card-containerView']"))
								.getAttribute("name");
						

					}catch (Exception e3) {
						cardName = ele.findElement(By.xpath("(//XCUIElementTypeOther)[3]"))
								.getAttribute("name");
					}
				}
				
			}
		}
		thirdCard = cardName;
		System.out.println("3rd  Card is : " + cardName);
		logStep("3rd  Card is : " + cardName);
	}
	
	/**
	 * Print Last Three Card Names of TWC iOS App when Scrolling ends.
	 */
	public static void getLastThreeCardsNamesAndroid() {
		// By byele =
		// MobileBy.xpath("//XCUIElementTypeCollectionView[@y='0']/XCUIElementTypeCell[1]");
		By byele = MobileBy.xpath(
				"//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.weather.Weather:id/home_screen_list_view\"]/android.widget.FrameLayout[1]");
		MobileElement ele = Ad.findElement(byele);
		String cardName = "homescreen";
		try {
			byele = MobileBy.xpath(
					"//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.weather.Weather:id/home_screen_list_view\"]/android.widget.FrameLayout[1]");
			ele = Ad.findElement(byele);
			cardName = ele
					.findElement(
							By.xpath("//android.widget.TextView[@resource-id='com.weather.Weather:id/header_title']"))
					.getAttribute("text");
		} catch (Exception e) {
			try {
				cardName = ele.findElement(By.xpath("//*[@resource-id='com.weather.Weather:id/title']"))
						.getAttribute("text");
			} catch (Exception e1) {
				try {
					cardName = ele
							.findElement(By.xpath(
									"//android.widget.TextView[@resource-id='com.weather.Weather:id/ad_card_title']"))
							.getAttribute("text");

				} catch (Exception e2) {
					try {

						MobileElement el = ele.findElement(By.xpath(
								"//android.view.ViewGroup[@resource-id='com.weather.Weather:id/current_conditions_card_root']"));
						cardName = "Current Condition";
					} catch (Exception e3) {
						try {
							MobileElement el = ele.findElement(By.xpath(
									"//android.widget.FrameLayout[@resource-id='com.weather.Weather:id/card_integrated_ad_root']"));
							cardName = "Integrated Marquee";

						} catch (Exception e4) {
							try {
								cardName = ele.findElement(By.xpath("//android.widget.TextView")).getAttribute("text");
							} catch (Exception e5) {
								e5.printStackTrace();
							}

						}
					}
				}

			}
		}
		firstCard = cardName;
		System.out.println("1st  Card is : " + cardName);
		logStep("1st  Card is : " + cardName);

		try {
			byele = MobileBy.xpath(
					"//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.weather.Weather:id/home_screen_list_view\"]/android.widget.FrameLayout[2]");
			ele = Ad.findElement(byele);
			cardName = ele
					.findElement(
							By.xpath("//android.widget.TextView[@resource-id='com.weather.Weather:id/header_title']"))
					.getAttribute("text");
		} catch (Exception e) {
			try {
				cardName = ele.findElement(By.xpath("//*[@resource-id='com.weather.Weather:id/title']"))
						.getAttribute("text");
			} catch (Exception e1) {
				try {
					cardName = ele
							.findElement(By.xpath(
									"//android.widget.TextView[@resource-id='com.weather.Weather:id/ad_card_title']"))
							.getAttribute("text");

				} catch (Exception e2) {
					try {

						MobileElement el = ele.findElement(By.xpath(
								"//android.view.ViewGroup[@resource-id='com.weather.Weather:id/current_conditions_card_root']"));
						cardName = "Current Condition";
					} catch (Exception e3) {
						try {
							MobileElement el = ele.findElement(By.xpath(
									"//android.widget.FrameLayout[@resource-id='com.weather.Weather:id/card_integrated_ad_root']"));
							cardName = "Integrated Marquee";

						} catch (Exception e4) {
							try {
								cardName = ele.findElement(By.xpath("//android.widget.TextView")).getAttribute("text");
							} catch (Exception e5) {
								e5.printStackTrace();
							}

						}
					}
				}

			}
		}
		secondCard = cardName;
		System.out.println("2nd  Card is : " + cardName);
		logStep("2nd  Card is : " + cardName);

		try {
			byele = MobileBy.xpath(
					"//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.weather.Weather:id/home_screen_list_view\"]/android.widget.FrameLayout[3]");
			ele = Ad.findElement(byele);
			cardName = ele
					.findElement(
							By.xpath("//android.widget.TextView[@resource-id='com.weather.Weather:id/header_title']"))
					.getAttribute("text");
		} catch (Exception e) {
			try {
				cardName = ele.findElement(By.xpath("//*[@resource-id='com.weather.Weather:id/title']"))
						.getAttribute("text");
			} catch (Exception e1) {
				try {
					cardName = ele
							.findElement(By.xpath(
									"//android.widget.TextView[@resource-id='com.weather.Weather:id/ad_card_title']"))
							.getAttribute("text");

				} catch (Exception e2) {
					try {

						MobileElement el = ele.findElement(By.xpath(
								"//android.view.ViewGroup[@resource-id='com.weather.Weather:id/current_conditions_card_root']"));
						cardName = "Current Condition";
					} catch (Exception e3) {
						try {
							MobileElement el = ele.findElement(By.xpath(
									"//android.widget.FrameLayout[@resource-id='com.weather.Weather:id/card_integrated_ad_root']"));
							cardName = "Integrated Marquee";

						} catch (Exception e4) {
							try {
								cardName = ele.findElement(By.xpath("//android.widget.TextView")).getAttribute("text");
							} catch (Exception e5) {
								e5.printStackTrace();
							}

						}
					}
				}

			}
		}
		thirdCard = cardName;
		System.out.println("3rd  Card is : " + cardName);
		logStep("3rd  Card is : " + cardName);
	}
	

}

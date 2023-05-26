package com.twc.ios.app.testcases;

import java.io.File;
import java.lang.reflect.Method;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.twc.ios.app.charlesfunctions.CharlesProxy;
import com.twc.ios.app.functions.Functions;
import com.twc.ios.app.general.ParseForVideoOrderedList;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.TwcIosBaseTest;
import com.twc.ios.app.general.Utils;
import com.twc.ios.app.pages.AddressScreen;
import com.twc.ios.app.pages.AndroidAddressScreen;
import com.twc.ios.app.pages.AndroidAirQualityCardContentScreen;
import com.twc.ios.app.pages.AndroidAirQualityCardScreen;
import com.twc.ios.app.pages.AndroidBreakingNewsCardScreen;
import com.twc.ios.app.pages.AndroidDailyNavTab;
import com.twc.ios.app.pages.AndroidFeedOneCardScreen;
import com.twc.ios.app.pages.AndroidHomeNavTab;
import com.twc.ios.app.pages.AndroidHourlyNavTab;
import com.twc.ios.app.pages.AndroidLogInScreen;
import com.twc.ios.app.pages.AndroidRadarNavTab;
import com.twc.ios.app.pages.AndroidSeasonalHubCardScreen;
import com.twc.ios.app.pages.AndroidSettingsScreen;
import com.twc.ios.app.pages.AndroidVideoNavTab;
import com.twc.ios.app.pages.DailyNavTab;
import com.twc.ios.app.pages.HomeNavTab;
import com.twc.ios.app.pages.HourlyNavTab;
import com.twc.ios.app.pages.PlanningCardScreen;
import com.twc.ios.app.pages.RadarNavTab;
import com.twc.ios.app.pages.SeasonalHubCardScreen;
import com.twc.ios.app.pages.VideoNavTab;

import io.qameta.allure.Description;

public class AndroidBURDAPrivacyTest extends TwcIosBaseTest {
	// private static final MobileAutomationLogger LOGGER = new
	// MobileAutomationLogger(GDPRFunctionalEULaunchTests.class);
	//private static final String CONFIG_FILE_PATH = "enableLGPD.config";
	private static final String CONFIG_FILE_PATH = "charles_common.config";
	private File configFile;
	AndroidHourlyNavTab hrTab;
	AndroidDailyNavTab dTab;
	AndroidHomeNavTab hmTab;
	AndroidRadarNavTab rTab;
	AndroidVideoNavTab vTab;
	AndroidAddressScreen addrScreen;
	AndroidSeasonalHubCardScreen sScreen;

	@BeforeClass(alwaysRun = true)
	@Description("BeforeClass")
	public void beforeClass() {
		System.out.println("****** BURDA Privacy Test Started");
		logStep("****** BURDA Privacy Test Started");
		//this.configFile = this.rewriteRuleToEnableLGPD(CONFIG_FILE_PATH);
		this.configFile = this.charlesGeneralConfigFile(CONFIG_FILE_PATH);
		proxy = new CharlesProxy("localhost", 8111, CONFIG_FILE_PATH);

		proxy.startCharlesProxyWithUI();
		proxy.enableNoCache();
		proxy.disableRewriting();
		proxy.stopRecording();
		proxy.disableMapLocal();
	}

	@AfterClass(alwaysRun = true)
	@Description("AfterClass")
	public void afterClass() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** ==============================================");
		System.out.println("****** AfterClass Started");
		logStep("****** AfterClass Started");
		if (this.configFile != null) {
			this.configFile.delete();
		}
		Functions.archive_folder("Charles");
		proxy.disableRewriting();
		proxy.quitCharlesProxy();
		try {
			//Ad.closeApp();
			Ad.terminateApp("com.weather.Weather");
			System.out.println("App closed successfully");
			logStep("App closed successfully");
		} catch (Exception e) {
			System.out.println("An exception while closeApp() executed");
			logStep("An exception while closeApp() executed");
		}

		if (Ad != null) {
			try {
				Ad.quit();
			} catch (Exception ex) {
				// Session crashed/died probably so no big deal, since
				// this exception was thrown when trying to close session.
				// Also, avoids failures in before/after methods for TestNG.
				System.out.println(
						"NoSuchSessionException was thrown while attempting to close session. Ignoring this error.");
				logStep("NoSuchSessionException was thrown while attempting to close session. Ignoring this error.");
			}
			System.out.println("Closing appium session.. Done");
			logStep("Closing appium session.. Done");
		}
		/*
		 * Instead of Uninstall and install app for every regime, waiting for 5 mins to get dsx call is more time saviour
		 * hence below hard wait steps are added and corresponding uninstall and install steps will be commented in next regimes.
		 */
		System.out.println("****** Waiting for five minutes to get dsx call to override privacy and geo ip country for next test");
		logStep("****** Waiting for five minutes to get dsx call to override privacy and geo ip country for next test");
		TestBase.waitForMilliSeconds(240000);
		
		System.out.println("****** BURDA Privacy Test Ended");
		logStep("****** BURDA Privacy Test Ended");
		System.out.println("==============================================");
	}

	/*
	 * @BeforeMethod(alwaysRun = true) public void beforeTest() {
	 * proxy.startCharlesProxyWithUI(); proxy.disableRewriting();
	 * proxy.stopRecording(); proxy.disableMapLocal(); }
	 */

	/*
	 * @AfterMethod(alwaysRun = true) public void afterTest(Method m) {
	 * proxy.disableRewriting(); proxy.quitCharlesProxy();
	 * 
	 * }
	 */
	
	@Test(priority = 0)
	@Description("Enable Preconditions to change region to Germany for BURDA ")
	public void enable_preConditions_toChange_Region_for_BURDA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** enable Preconditions to change region to Germany for BURDA test case Started");
		logStep("****** enable Preconditions to change region to Germany for BURDA test case Started");
		Functions.Appium_Autostart();
		Functions.archive_folder("Charles");
		proxy.startRecording();
		proxy.clearCharlesSession();
		Functions.launchtheAndroidApp_forLocalization("DE",true,"de",true);
		System.out.println("App launched ");
		logStep("App launched ");
		proxy.getXml();
		Functions.archive_folder("Charles");
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(10000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		hrTab = new AndroidHourlyNavTab(Ad);
		dTab = new AndroidDailyNavTab(Ad);
		hmTab = new AndroidHomeNavTab(Ad);
		rTab = new AndroidRadarNavTab(Ad);
		vTab = new AndroidVideoNavTab(Ad);
		addrScreen = new AndroidAddressScreen(Ad);
		sScreen = new AndroidSeasonalHubCardScreen(Ad);

	}

	@Test(priority = 130, enabled = true)
	@Description("Validating NextGen IM Call npa value")
	public void validate_NextGen_IM_call_npa_val_for_BURDA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Call npa value");
		logStep("Validating NextGen IM Call npa value ");

		Utils.validate_npa_val_in_gampad_url("Smoke", "Marquee_BURDA", true);

	}
	
	@Test(priority = 140, enabled = true)
	@Description("Enable Preconditions to change region to Germany and language to German for BURDA ")
	public void enable_preConditions_toChange_Region_and_Language_for_BURDA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Enable Preconditions to change region to Germany and language to German for BURDA test case Started");
		logStep("****** Enable Preconditions to change region to Germany and language to German for BURDA test case Started");
		Ad.terminateApp("com.weather.Weather");
		TestBase.waitForMilliSeconds(10000);
		Functions.Appium_Autostart();
		Functions.archive_folder("Charles");
		proxy.startRecording();
		proxy.clearCharlesSession();
		Functions.launchtheAndroidApp_forLocalization("DE",true,"de",true);
		System.out.println("App launched ");
		logStep("App launched ");
		proxy.getXml();
		Functions.archive_folder("Charles");
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		hrTab = new AndroidHourlyNavTab(Ad);
		dTab = new AndroidDailyNavTab(Ad);
		hmTab = new AndroidHomeNavTab(Ad);
		rTab = new AndroidRadarNavTab(Ad);
		vTab = new AndroidVideoNavTab(Ad);
		addrScreen = new AndroidAddressScreen(Ad);
		sScreen = new AndroidSeasonalHubCardScreen(Ad);
	}
	
	@Test(priority = 150, enabled = true)
	@Description("Deriving Video Call")
	public void derive_VideoCall_IU_for_BURDA() throws Exception {
		
		System.out.println("==============================================");
		System.out.println("****** Deriving VideoCall For BURDA test case Started");
		logStep("****** Deriving VideoCall For BURDA test case Started");
		proxy.clearCharlesSession();
		hmTab.clickonHomeTab();
		hmTab.clickonHomeTab();
		Functions.archive_folder("Charles");
		proxy.clearCharlesSession();
		// navigate to Video tab
		vTab.navigateToVideoTab(true, proxy);
		TestBase.waitForMilliSeconds(20000);
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.get_iu_value_of_Feedcall("Smoke", "PreRollVideo");
	}
			
	@Test(priority = 151, enabled = true)
	@Description("Validating Video Call npa value")
	public void validate_Video_call_npa_val_for_BURDA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Video Call npa value");
		logStep("Validating Video Call npa value ");

		Utils.validate_npa_val_in_gampad_url("Smoke", "PreRollVideo", true);

	}
	
	@Test(priority = 305, enabled = true)
	@Description("Enable Preconditions for en_US")
	public void preConditionsTest_for_en_US() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** PreConditions For en_US test case Started");
		logStep("****** PreConditions For en_US test case Started");
		
		Functions.Appium_Autostart();
		Functions.archive_folder("Charles");
		proxy.startRecording();
		proxy.clearCharlesSession();
		Functions.launchtheAndroidApp_forLocalization("US", true, "en", true);
		System.out.println("App launched ");
		logStep("App launched ");
		Functions.close_launchAppAndroid();
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(10000);
		
	}

}

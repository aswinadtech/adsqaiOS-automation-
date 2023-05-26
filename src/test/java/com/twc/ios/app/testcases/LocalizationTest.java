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
import com.twc.ios.app.pages.DailyNavTab;
import com.twc.ios.app.pages.HomeNavTab;
import com.twc.ios.app.pages.HourlyNavTab;
import com.twc.ios.app.pages.PlanningCardScreen;
import com.twc.ios.app.pages.RadarNavTab;
import com.twc.ios.app.pages.SeasonalHubCardScreen;
import com.twc.ios.app.pages.SettingsScreen;
import com.twc.ios.app.pages.VideoNavTab;

import io.qameta.allure.Description;

public class LocalizationTest extends TwcIosBaseTest {
	// private static final MobileAutomationLogger LOGGER = new
	// MobileAutomationLogger(GDPRFunctionalEULaunchTests.class);
	// private static final String CONFIG_FILE_PATH = "enableLGPD.config";
	private static final String CONFIG_FILE_PATH = "charles_common.config";
	private File configFile;
	HourlyNavTab hrTab;
	DailyNavTab dTab;
	HomeNavTab hmTab;
	RadarNavTab rTab;
	VideoNavTab vTab;
	AddressScreen addrScreen;
	PlanningCardScreen pScreen;
	SeasonalHubCardScreen sScreen;
	SettingsScreen stScreen;

	@BeforeClass(alwaysRun = true)
	@Description("BeforeClass")
	public void beforeClass() {
		System.out.println("****** Localization Test Started");
		logStep("****** Localization Test Started");
		// this.configFile = this.rewriteRuleToEnableLGPD(CONFIG_FILE_PATH);
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
			// Ad.closeApp();
			Ad.terminateApp("com.weather.TWC");
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
				
		System.out.println("****** Localization Test Ended");
		logStep("****** Localization Test Ended");
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

	@Test(priority = 100, enabled = true)
	@Description("Enable Preconditions for en_US")
	public void preConditionsTest_for_en_US() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** PreConditions For en_US test case Started");
		logStep("****** PreConditions For en_US test case Started");
		// Preconditions
		Utils.getiPhoneUDID();
		Utils.getiOSVersion();
		Functions.capabilities();
		Functions.Appium_Autostart();
//		Utils.getCurrentMacIPAddressAndSetiPhoneProxy(true, true);
		
		proxy.startRecording();
		proxy.clearCharlesSession();
		
		Functions.archive_folder("Charles");
		Functions.launchtheApp("true");
		System.out.println("App launched ");
		logStep("App launched ");
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.checkForAppState();
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Functions.archive_folder("Charles");
		hrTab = new HourlyNavTab(Ad);
		dTab = new DailyNavTab(Ad);
		hmTab = new HomeNavTab(Ad);
		rTab = new RadarNavTab(Ad);
		vTab = new VideoNavTab(Ad);
		addrScreen = new AddressScreen(Ad);
		pScreen = new PlanningCardScreen(Ad);
		sScreen = new SeasonalHubCardScreen(Ad);
		stScreen = new SettingsScreen(Ad);
		Ad.terminateApp("com.weather.TWC");
	}

	@Test(priority = 101, enabled = true)
	@Description("Validating NextGen IM Call for en_US")
	public void validate_NextGen_IM_call_for_en_US() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Call for en_US");
		logStep("Validating NextGen IM Call for en_US");

		Utils.verifyPubadCal("iu=%2F7646%2Fapp_iphone_us%2Fdb_display%2Fhome_screen%2Fmarquee");

	}

	@Test(priority = 110, enabled = true)
	@Description("Enable Preconditions to change region to de_DE")
	public void enable_preConditions_toChange_Region_for_de_DE() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** enable Preconditions to change region to de_DE test case Started");
		logStep("****** enable Preconditions to change region to de_DE test case Started");
		
		Functions.Appium_Autostart();
		Functions.archive_folder("Charles");
		proxy.startRecording();
		proxy.clearCharlesSession();
		Functions.launchtheApp_forLocalization("true", "de_DE", true, "de", true);
		System.out.println("App launched ");
		logStep("App launched ");
		//proxy.getXml();
		Functions.archive_folder("Charles");
		Functions.close_launchApp();
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.checkForAppState();
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		hrTab = new HourlyNavTab(Ad);
		dTab = new DailyNavTab(Ad);
		hmTab = new HomeNavTab(Ad);
		rTab = new RadarNavTab(Ad);
		vTab = new VideoNavTab(Ad);
		addrScreen = new AddressScreen(Ad);
		pScreen = new PlanningCardScreen(Ad);
		sScreen = new SeasonalHubCardScreen(Ad);
		Ad.terminateApp("com.weather.TWC");
	}

	@Test(priority = 111, enabled = true)
	@Description("Validating NextGen IM Call for de_DE")
	public void validate_NextGen_IM_call_for_de_DE() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Call for de_DE");
		logStep("Validating NextGen IM Call for de_DE");

		Utils.verifyPubadCal("iu=%2F3673%2Fm_app_ios_iphone_wx%2Fdb_display%2Fhome_screen%2Fmarquee");

	}

	@Test(priority = 120, enabled = true)
	@Description("Enable Preconditions to change region to es_US")
	public void enable_preConditions_toChange_Region_for_es_US() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** enable Preconditions to change region to es_US test case Started");
		logStep("****** enable Preconditions to change region to es_US test case Started");
		
		Functions.Appium_Autostart();
		Functions.archive_folder("Charles");
		proxy.startRecording();
		proxy.clearCharlesSession();
		Functions.launchtheApp_forLocalization("true", "es_US", true, "es", true);
		System.out.println("App launched ");
		logStep("App launched ");
		//proxy.getXml();
		Functions.archive_folder("Charles");
		Functions.close_launchApp();
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.checkForAppState();
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		hrTab = new HourlyNavTab(Ad);
		dTab = new DailyNavTab(Ad);
		hmTab = new HomeNavTab(Ad);
		rTab = new RadarNavTab(Ad);
		vTab = new VideoNavTab(Ad);
		addrScreen = new AddressScreen(Ad);
		pScreen = new PlanningCardScreen(Ad);
		sScreen = new SeasonalHubCardScreen(Ad);
		Ad.terminateApp("com.weather.TWC");
	}

	@Test(priority = 121, enabled = true)
	@Description("Validating NextGen IM Call for es_US")
	public void validate_NextGen_IM_call_for_es_US() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Call for es_US");
		logStep("Validating NextGen IM Call for es_US");

		Utils.verifyPubadCal("iu=%2F7646%2Fapp_iphone_us_es%2Fdb_display%2Fhome_screen%2Fmarquee");

	}

	@Test(priority = 130, enabled = true)
	@Description("Enable Preconditions to change region to hi_IN")
	public void enable_preConditions_toChange_Region_for_hi_IN() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** enable Preconditions to change region to hi_IN test case Started");
		logStep("****** enable Preconditions to change region to hi_IN test case Started");
	
		Functions.Appium_Autostart();
		Functions.archive_folder("Charles");
		proxy.startRecording();
		proxy.clearCharlesSession();
		Functions.launchtheApp_forLocalization("true", "hi_IN", true, "hi", true);
		System.out.println("App launched ");
		logStep("App launched ");
		//proxy.getXml();
		Functions.archive_folder("Charles");
		Functions.close_launchApp();
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.checkForAppState();
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		hrTab = new HourlyNavTab(Ad);
		dTab = new DailyNavTab(Ad);
		hmTab = new HomeNavTab(Ad);
		rTab = new RadarNavTab(Ad);
		vTab = new VideoNavTab(Ad);
		addrScreen = new AddressScreen(Ad);
		pScreen = new PlanningCardScreen(Ad);
		sScreen = new SeasonalHubCardScreen(Ad);
		Ad.terminateApp("com.weather.TWC");
	}

	@Test(priority = 131, enabled = true)
	@Description("Validating NextGen IM Call for hi_IN")
	public void validate_NextGen_IM_call_for_hi_IN() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Call for hi_IN");
		logStep("Validating NextGen IM Call for hi_IN");

		Utils.verifyPubadCal("iu=%2F7646%2Fapp_iphone_hi_in%2Fdb_display%2Fhome_screen%2Fmarquee");

	}

	@Test(priority = 140, enabled = true)
	@Description("Enable Preconditions to change region to en_IN")
	public void enable_preConditions_toChange_Region_for_en_IN() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** enable Preconditions to change region to en_IN test case Started");
		logStep("****** enable Preconditions to change region to en_IN test case Started");
		
		Functions.Appium_Autostart();
		Functions.archive_folder("Charles");
		proxy.startRecording();
		proxy.clearCharlesSession();
		Functions.launchtheApp_forLocalization("true", "en_IN", true, "en", false);
		System.out.println("App launched ");
		logStep("App launched ");
		//proxy.getXml();
		Functions.archive_folder("Charles");
		Functions.close_launchApp();
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.checkForAppState();
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		hrTab = new HourlyNavTab(Ad);
		dTab = new DailyNavTab(Ad);
		hmTab = new HomeNavTab(Ad);
		rTab = new RadarNavTab(Ad);
		vTab = new VideoNavTab(Ad);
		addrScreen = new AddressScreen(Ad);
		pScreen = new PlanningCardScreen(Ad);
		sScreen = new SeasonalHubCardScreen(Ad);
		Ad.terminateApp("com.weather.TWC");
	}

	@Test(priority = 141, enabled = true)
	@Description("Validating NextGen IM Call for en_IN")
	public void validate_NextGen_IM_call_for_en_IN() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Call for en_IN");
		logStep("Validating NextGen IM Call for en_IN");

		Utils.verifyPubadCal("iu=%2F7646%2Fapp_iphone_en_in%2Fdb_display%2Fhome_screen%2Fmarquee");

	}
	
	@Test(priority = 150, enabled = true)
	@Description("Enable Preconditions to change region to en_GB")
	public void enable_preConditions_toChange_Region_for_en_GB() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** enable Preconditions to change region to en_GB test case Started");
		logStep("****** enable Preconditions to change region to en_GB test case Started");
		
		Functions.Appium_Autostart();
		Functions.archive_folder("Charles");
		proxy.startRecording();
		proxy.clearCharlesSession();
		Functions.launchtheApp_forLocalization("true", "en_GB", true, "en", false);
		System.out.println("App launched ");
		logStep("App launched ");
		//proxy.getXml();
		Functions.archive_folder("Charles");
		Functions.close_launchApp();
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.checkForAppState();
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		hrTab = new HourlyNavTab(Ad);
		dTab = new DailyNavTab(Ad);
		hmTab = new HomeNavTab(Ad);
		rTab = new RadarNavTab(Ad);
		vTab = new VideoNavTab(Ad);
		addrScreen = new AddressScreen(Ad);
		pScreen = new PlanningCardScreen(Ad);
		sScreen = new SeasonalHubCardScreen(Ad);
		Ad.terminateApp("com.weather.TWC");
	}

	@Test(priority = 151, enabled = true)
	@Description("Validating NextGen IM Call for en_GB")
	public void validate_NextGen_IM_call_for_en_GB() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Call for en_GB");
		logStep("Validating NextGen IM Call for en_GB");

		Utils.verifyPubadCal("iu=%2F7646%2Fapp_iphone_intl%2Fdb_display%2Fhome_screen%2Fmarquee");

	}
	
	@Test(priority = 160, enabled = true)
	@Description("Enable Preconditions to change region to en_CA")
	public void enable_preConditions_toChange_Region_for_en_CA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** enable Preconditions to change region to en_CA test case Started");
		logStep("****** enable Preconditions to change region to en_CA test case Started");
		
		Functions.Appium_Autostart();
		Functions.archive_folder("Charles");
		proxy.startRecording();
		proxy.clearCharlesSession();
		Functions.launchtheApp_forLocalization("true", "en_CA", true, "en", false);
		System.out.println("App launched ");
		logStep("App launched ");
		//proxy.getXml();
		Functions.archive_folder("Charles");
		Functions.close_launchApp();
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.checkForAppState();
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		hrTab = new HourlyNavTab(Ad);
		dTab = new DailyNavTab(Ad);
		hmTab = new HomeNavTab(Ad);
		rTab = new RadarNavTab(Ad);
		vTab = new VideoNavTab(Ad);
		addrScreen = new AddressScreen(Ad);
		pScreen = new PlanningCardScreen(Ad);
		sScreen = new SeasonalHubCardScreen(Ad);
		Ad.terminateApp("com.weather.TWC");
	}

	@Test(priority = 161, enabled = true)
	@Description("Validating NextGen IM Call for en_CA")
	public void validate_NextGen_IM_call_for_en_CA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Call for en_CA");
		logStep("Validating NextGen IM Call for en_CA");

		Utils.verifyPubadCal("iu=%2F7646%2Fapp_iphone_intl%2Fdb_display%2Fhome_screen%2Fmarquee");

	}
	
	@Test(priority = 170, enabled = true)
	@Description("Enable Preconditions to change region to fr_FR")
	public void enable_preConditions_toChange_Region_for_fr_FR() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** enable Preconditions to change region to fr_FR test case Started");
		logStep("****** enable Preconditions to change region to fr_FR test case Started");
	
		Functions.Appium_Autostart();
		Functions.archive_folder("Charles");
		proxy.startRecording();
		proxy.clearCharlesSession();
		Functions.launchtheApp_forLocalization("true", "fr_FR", true, "fr", true);
		System.out.println("App launched ");
		logStep("App launched ");
		//proxy.getXml();
		Functions.archive_folder("Charles");
		Functions.close_launchApp();
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.checkForAppState();
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		hrTab = new HourlyNavTab(Ad);
		dTab = new DailyNavTab(Ad);
		hmTab = new HomeNavTab(Ad);
		rTab = new RadarNavTab(Ad);
		vTab = new VideoNavTab(Ad);
		addrScreen = new AddressScreen(Ad);
		pScreen = new PlanningCardScreen(Ad);
		sScreen = new SeasonalHubCardScreen(Ad);
		Ad.terminateApp("com.weather.TWC");
	}

	@Test(priority = 171, enabled = true)
	@Description("Validating NextGen IM Call for fr_FR")
	public void validate_NextGen_IM_call_for_fr_FR() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Call for fr_FR");
		logStep("Validating NextGen IM Call for fr_FR");

		Utils.verifyPubadCal("iu=%2F7646%2Fapp_iphone_intl%2Fdb_display%2Fhome_screen%2Fmarquee");

	}

}

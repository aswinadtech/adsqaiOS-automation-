package com.twc.ios.app.testcases;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Listeners;

import org.testng.annotations.BeforeClass;

import io.appium.java_client.MobileElement;
//import ru.yandex.qatools.allure.annotations.Title;
import io.qameta.allure.Description;

import com.twc.ios.app.charlesfunctions.CharlesProxy;
import com.twc.ios.app.functions.Functions;
import com.twc.ios.app.general.ParseForVideoOrderedList;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.TwcIosBaseTest;
import com.twc.ios.app.general.Utils;
import com.twc.ios.app.pages.AddressScreen;
import com.twc.ios.app.pages.AirQualityCardScreen;
import com.twc.ios.app.pages.DailyNavTab;
import com.twc.ios.app.pages.FTLScreens;
import com.twc.ios.app.pages.FeedOneCardScreen;
import com.twc.ios.app.pages.HomeNavTab;
import com.twc.ios.app.pages.HourlyNavTab;
import com.twc.ios.app.pages.LogInScreen;
import com.twc.ios.app.pages.PlanningCardScreen;
import com.twc.ios.app.pages.RadarNavTab;
import com.twc.ios.app.pages.SeasonalHubCardScreen;
import com.twc.ios.app.pages.SettingsScreen;
import com.twc.ios.app.pages.VideoNavTab;

@Listeners(value = com.twc.ios.app.general.MyTestListenerAdapter.class)
public class SmokeTest extends TwcIosBaseTest {

	//private static final String CONFIG_FILE_PATH = "charles_common.config";
	private static final String CONFIG_FILE_PATH = "enableUSA.config";

	// public static CharlesProxy proxy;
	public File configFile;
	HourlyNavTab hrTab;
	DailyNavTab dTab;
	HomeNavTab hmTab;
	RadarNavTab rTab;
	VideoNavTab vTab;
	AddressScreen addrScreen;
	PlanningCardScreen pScreen;
	SeasonalHubCardScreen sScreen;
	SettingsScreen stScreen;
	LogInScreen loginScreen;
	FTLScreens ftlScreens;
	AirQualityCardScreen aqCardScreen;
	FeedOneCardScreen fOneCardScreen;
	
	@BeforeClass(alwaysRun = true)
	@Description("BeforeClass")
	public void beforeClass() {
		System.out.println("****** Smoke Test Started");
		logStep("****** Smoke Test Started");
		//this.configFile = this.charlesGeneralConfigFile(CONFIG_FILE_PATH);
		//this.configFile = this.rewriteRuleToEnableUSA(CONFIG_FILE_PATH);
		this.configFile = this.rewriteRuleToEnableUSAWhenNoTunnelBear(CONFIG_FILE_PATH, "usa", "US", "WA");
		proxy = new CharlesProxy("localhost", 8111, CONFIG_FILE_PATH);
		proxy.startCharlesProxyWithUI();
		proxy.disableRewriting();
		proxy.stopRecording();
		proxy.disableMapLocal();
	}

	@AfterClass(alwaysRun = true)
	@Description("AfterClass")
	public void afterClass() throws Exception {
		System.out.println("****** After Class Started");
		logStep("****** After Class Started");
		if (this.configFile != null) {
			this.configFile.delete();
		}
		Ad.launchApp();
		stScreen.getAppVersion();
		Functions.archive_folder("Charles");
		proxy.disableRewriting();
		proxy.quitCharlesProxy();
		try {
			//Ad.closeApp();
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

		System.out.println("****** Smoke Test Ended");
		logStep("****** Smoke Test Ended");
	}

	@Test(priority = 0)
	@Description("Updating Device Proxy Details and Launch the App")
	public void beforeTest() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Updating Device Proxy Details and Launch the App test case Started");
		logStep("****** Updating Device Proxy Details and Launch the App test case Started");
		// Preconditions
		Utils.getiPhoneUDID();
		Utils.getiOSVersion();
		Functions.capabilities();
		Functions.Appium_Autostart();
		Utils.getCurrentMacIPAddressAndSetiPhoneProxy(true, true);
		// Enable rewriting to USA on Charles because Tunnel Bear not working in India
		proxy.enableRewriting();
		proxy.startRecording();
		proxy.clearCharlesSession();
		Functions.archive_folder("Charles");
		Functions.launchtheApp("true");
		System.out.println("App launched ");
		logStep("App launched ");
		Functions.archive_folder("Charles");
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		proxy.clearCharlesSession();
		hrTab = new HourlyNavTab(Ad);
		dTab = new DailyNavTab(Ad);
		hmTab = new HomeNavTab(Ad);
		rTab = new RadarNavTab(Ad);
		vTab = new VideoNavTab(Ad);
		addrScreen = new AddressScreen(Ad);
		pScreen = new PlanningCardScreen(Ad);
		sScreen = new SeasonalHubCardScreen(Ad);
		stScreen = new SettingsScreen(Ad);
		loginScreen = new LogInScreen(Ad);
		ftlScreens = new FTLScreens(Ad);
		aqCardScreen = new AirQualityCardScreen(Ad);
		fOneCardScreen = new FeedOneCardScreen(Ad);
				
	}
	
	@Test(priority = 51, enabled = true)
	@Description("Verify Consent Calls On FTL")
	public void Verify_Consent_Calls_On_FTL() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================Consent Calls Verification On FTL====================");
		
		System.out.println("****** Consent Calls Verification On FTL Started");
		logStep("****** Consent Calls Verification On FTL Started");
		ftlScreens.verifyNoOfConsentCallsOnFTL();
	}
	
	@Test(priority = 75, enabled = true)
	@Description("Verify Interstitial Ad Calll")
	public void Verify_Interstitial_Ad_Call() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================Interstitial Ad Call iu====================");

		System.out.println("****** Interstitial Ad Call iu validation Started");
		logStep("****** Interstitial Ad Call iu validation Started");
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(15000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.verifyPubadCal("Smoke", "Interstitials");
	}
	
	/**
	 * This method validates pos custom parameter of Interstitial Ad call
	 */
	@Test(priority = 76, enabled = true)
	@Description("Validating 'pos' custom parameter of Interstitial Ad call ")
	public void Validate_Interstitial_Ad_Call_pos_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating pos custom parameter of Interstitial Ad call");
		logStep("Validating pos custom parameter of Interstitial Ad call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Interstitials", "pos", "intr");

	}
	
	/**
	 * Verify Feed Calls
	 * 
	 * @throws Exception This case validates the ad calls of Home Screen Today,and
	 *                   new Feed ad Cards
	 */
	@Test(priority = 100, enabled = true)
	@Description("Feed Calls Validation")
	public void Verify_Feed_Calls() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Feed Calls Validation test started");
		logStep("Feed Calls Validation test started");
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		Functions.close_launchApp();
		stScreen.select_Airlock_Branch("IOSAUTOMATION03");
		stScreen.select_Airlock_UserGroup("IOSAUTOMATION");
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		hmTab.clickonHomeTab();
		hmTab.clickonHomeTab();
		
		/*
		 * Handling Interstitial ad ahead of navigating to all cards
		 */
		
		//Utils.verifytinterstitialAdcallBeforeClearSession("Smoke", "Hourly");

		hrTab.navigateToHourlyTabAndHandleInterstitialAd();
		Functions.close_launchApp();
		hmTab.clickonHomeTab();
		hmTab.clickonHomeTab();
		proxy.clearCharlesSession();
		
		addrScreen.enternewAddress(false, "Atlanta, Georgia");
		TestBase.waitForMilliSeconds(20000);
		try {
			Utils.navigateToAllCards(true, false);
			
		} catch (Exception e) {
			System.out.println("There is an exception while navigting to all the feed cards.");
			logStep("There is an exception while navigting to all the feed cards.");
			e.printStackTrace();
		} finally {
			proxy.stopRecording();
			proxy.getXml();
			Utils.createXMLFileForCharlesSessionFile();
			proxy.startRecording();
			ParseForVideoOrderedList.getVideoCall_IU_Value_from_adZone();
			Utils.get_v3_wx_forecast_daily_15day_data();
			Utils.getPlaceIdFromAPICall("placeId");
			Utils.Verify_newfeedAdcalls("Smoke", "CleanLaunch");

		}

	}

	/*
	 * //disabling this as Daily Card integrated forecast ad been removed as part of
	 * 12.5 build
	 * 
	 * @Test(priority = 101, enabled = true)
	 * 
	 * @Description("Verify Daily card iu") public void
	 * Verify_DailyCard_AdCall() throws Exception {
	 * System.out.println("==============================================");
	 * System.out.
	 * println("===========================Daily card iu====================");
	 * 
	 * System.out.println("****** Daily card iu validation Started");
	 * logStep("****** Daily card iu validation Started");
	 * 
	 * // Functions.verifyPubadCal("Smoke", "Daily"); Utils.verifyPubadCal("Smoke",
	 * "Daily");
	 * 
	 * }
	 */

	@Test(priority = 101, enabled = true)
	@Description("Verify Homescreen Adhesive ad call iu")
	public void Verify_Homescreen_Adhesive_AdCall() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================Homescreen Adhesive Adcall iu====================");
		
		System.out.println("****** Homescreen Adhesive Adcall verification Started");
		logStep("****** Homescreen Adhesive Adcall verification Started");
		Utils.verifyPubadCal("Smoke", "Pulltorefresh");
	}
	
	
	@Test(priority = 102, enabled = true)
	@Description("Verify NextGen IM ad call iu")
	public void Verify_NextGen_IM_AdCall() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================NextGen IM Adcall iu====================");
		
		System.out.println("****** NextGen IM Adcall verification Started");
		logStep("****** NextGen IM Adcall verification Started");
		Utils.verifyPubadCal("Smoke", "Marquee");
	}

	@Test(priority = 103, enabled = true)
	@Description("Verify Hourly details page iu")
	public void Verify_HourlyDetails_AdCall() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================Hourly details iu====================");

		System.out.println("****** Hourly details iu validation Started");
		logStep("****** Hourly details iu validation Started");
		Utils.verifyPubadCal("Smoke", "Hourly");

	}

	@Test(priority = 104, enabled = true)
	@Description("Verify Today's Detailed/Trending page iu")
	public void Verify_TrendingPage_AdCall() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"===========================Verify Today's Detailed/Trending page Adcal iu====================");

		System.out.println("****** Today's Detailed/Trending page Ad cal iu validation Started ");
		logStep("****** Today's Detailed/Trending page Ad cal iu validation Started ");
		Utils.verifyPubadCal("Smoke", "Today", false);
	}

	@Test(priority = 105, enabled = true)
	@Description("Verify Air Quality details page iu")
	public void Verify_Air_Quality_Details_AdCall() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================Air Quality Details page Adcal iu====================");

		System.out.println("****** Air Quality Details page test cases Started");
		logStep("****** Air Quality Details page test cases Started");
		Utils.verifyPubadCal("Smoke", "Air Quality(Content)");
	}

	@Test(priority = 106, enabled = true)
	@Description("Verify Maps detail page iu")
	public void Verify_MapsDetails_AdCall() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================Verify  Maps detail page iu====================");

		System.out.println("****** Maps detail page Ad cal iu validation Started");
		logStep("****** Maps detail page Ad cal iu validation Started");
		Utils.verifyPubadCal("Smoke", "Map");

	}

	@Test(priority = 107, enabled = true)
	@Description("Verify Health & Activities (Flu) Content page iu")
	public void Verify_Health_Flu_ContentPage_AdCall() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"===========================Health & Activities (Flu) Content page Adcal iu====================");

		System.out.println("****** Health & Activities (Flu) Content page Ad cal iu validation Started");
		logStep("****** Health & Activities (Flu) Content page Ad cal iu validation Started");
		Utils.verifyPubadCal("Smoke", "Health(coldAndFlu)");

	}
	
	@Test(priority = 108, enabled = true)
	@Description("Verify Health & Activities (Allergy) Content page iu")
	public void Verify_Health_Allergy_ContentPage_AdCall() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"===========================Health & Activities (Allergy) Content page Adcal iu====================");

		System.out.println("****** Health & Activities (Allergy) Content page Ad cal iu validation Started");
		logStep("****** Health & Activities (Allergy) Content page Ad cal iu validation Started");
		Utils.verifyPubadCal("Smoke", "Health(allergy)");

	}

	@Test(priority = 109, enabled = true)
	@Description("Verify Health & Activities (Running) Content iu")
	public void Verify_Health_Running_ContentPage_AdCall() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"===========================Health & Activities (Running) Content page Adcal iu====================");

		System.out.println("****** Health & Activities (Running) Content page Ad cal iu validation Started");
		logStep("****** Health & Activities (Running) Content page Ad cal iu validation Started");
		Utils.verifyPubadCal("Smoke", "Health(goRun)", false);

	}
	
	@Test(priority = 110, enabled = true)
	@Description("Verify Health & Activities (Boat & Beach) Content iu")
	public void Verify_Health_BoatAndBeach_ContentPage_AdCall() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"===========================Health & Activities (Boat & Beach) Content page Adcal iu====================");

		System.out.println("****** Health & Activities (Boat & Beach) Content page Ad cal iu validation Started");
		logStep("****** Health & Activities (Boat & Beach) Content page Ad cal iu validation Started");
		Utils.verifyPubadCal("Smoke", "Health(boatAndBeach)", false);

	}

	@Test(priority = 111, enabled = true)
	@Description("Verify Alert Center ad on My Alerts Page")
	public void Verify_AlertCenterAd() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================Aleret Center page Adcal iu====================");

		System.out.println("****** Alert Center ad test case Started");
		logStep("****** Alert Center ad test case Started");
		Utils.verifyPubadCal("Smoke", "MyAlerts");
	}
	
	@Test(priority = 115, enabled = true)
	@Description("Verify Safety And Preparedness Card iu")
	public void Verify_SafetyAndPreparednessCard_AdCall() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================Safety And Preparedness Card Adcal iu====================");

		System.out.println("****** Safety And Preparedness card ad test case Started");
		logStep("****** Safety And Preparedness card ad test case Started");
		Utils.verifyPubadCal("Smoke", "SafetyAndPreparedness");
	}
	
/*	@Test(priority = 116, enabled = true)
	@Description("Validating Safety And Preparedness call Response")
	public void Validate_SafetyAndPreparednessCard_Call_Response() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating SafetyAndPreparednessCard call response in charles");
		logStep("Validating SafetyAndPreparednessCard call response in charles ");

		Utils.Verify_Gampad_Call_ByResponseText("Smoke", "SafetyAndPreparedness");

	}*/
	
	@Test(priority = 117, enabled = true)
	@Description("Verify Seasonal Hub details page iu")
	public void Verify_Seasonal_Hub_Details_AdCall() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================Seasonal Hub Details page Adcal iu====================");

		System.out.println("****** Seasonal Hub Details page test cases Started");
		logStep("****** Seasonal Hub Details page test cases Started");
		Utils.verifyPubadCal("Smoke", "SeasonalHub(Details)", false);
	}

	/**
	 * This method validates sod custom parameter of Marquee Call
	 */
	@Test(priority = 201, enabled = true)
	@Description("Validating 'sod' custom parameter of Marquee Call")
	public void validate_Marquee_sod_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating sod custom parameter of Marquee call");
		logStep("Validating sod custom parameter of Marquee call");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "sod", "yes");
	}
	 
	
	/**
	 * This method validates sod custom parameter of Feed1 call
	 */
	@Test(priority = 202, enabled = true)
	@Description("Validating 'sod' custom parameter of Feed1 call ")
	public void Validate_Feed1_sod_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating sod custom parameter of Feed1 call");
		logStep("Validating sod custom parameter of Feed1 call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "sod", "yes");

	}

	/**
	 * This method validates sod custom parameter of Hourly details call
	 */
	/*@Test(priority = 203, enabled = true)
	@Description("Validating 'sod' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_sod_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating sod custom parameter of Hourly Details call");
		logStep("Validating sod custom parameter of Hourly Details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "sod", "yes");

	}*/

	/**
	 * This method validates pos custom parameter of new ad feed cards
	 */
	/*
	 * @Test(priority = 204, enabled = true)
	 * 
	 * @Description("Validating 'pos' custom parameter for new Ad FeedCards ")
	 * public void Validate_FeedCards_pos_Custom_param() throws Exception {
	 * System.out.println("==============================================");
	 * System.out.
	 * println("****** Validating pos custom parameter of new ad feed cards");
	 * logStep("Validating pos custom parameter of new ad feed cards ");
	 * 
	 * Utils.validate_custom_param_val_of_newFeedAds("Smoke", "CleanLaunch", "pos");
	 * 
	 * }
	 */

	/**
	 * This method validates zip custom parameter of Marquee call
	 */
	@Test(priority = 205, enabled = true)
	@Description("Validating 'zip' custom parameter of Marquee call ")
	public void Validate_Marquee_zip_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating zip custom parameter of Marquee call");
		logStep("Validating zip custom parameter of Marquee call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "zip");

	}

	/**
	 * This method validates zip custom parameter of Feed1 call
	 */
	@Test(priority = 206, enabled = true)
	@Description("Validating 'zip' custom parameter of Feed1 call ")
	public void Validate_Feed1_zip_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating zip custom parameter of Feed1 call");
		logStep("Validating zip custom parameter of Feed1 call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "zip");

	}

	/**
	 * This method validates zip custom parameter of Hourly details call
	 */
	/*@Test(priority = 207, enabled = true)
	@Description("Validating 'zip' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_zip_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating zip custom parameter of Hourly details call");
		logStep("Validating zip custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "zip");

	}*/

	/**
	 * This method validates plat custom parameter of Marquee call
	 */
	@Test(priority = 208, enabled = true)
	@Description("Validating 'plat' custom parameter of Marquee call ")
	public void Validate_Marquee_plat_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating plat custom parameter of Marquee call");
		logStep("Validating plat custom parameter of Marquee call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "plat", "iphone");

	}

	/**
	 * This method validates plat custom parameter of Feed1 call
	 */
	@Test(priority = 209, enabled = true)
	@Description("Validating 'plat' custom parameter of Feed1 call ")
	public void Validate_Feed1_plat_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating plat custom parameter of Feed1 call");
		logStep("Validating plat custom parameter of Feed1 call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "plat", "iphone");

	}

	/**
	 * This method validates plat custom parameter of Hourly details call
	 */
	/*@Test(priority = 210, enabled = true)
	@Description("Validating 'plat' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_plat_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating plat custom parameter of Hourly details call");
		logStep("Validating plat custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "plat", "iphone");

	}*/

	/**
	 * This method validates adid custom parameter of Marquee call
	 */
	@Test(priority = 211, enabled = true)
	@Description("Validating 'adid' custom parameter of Marquee call ")
	public void Validate_Marquee_adid_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating adid custom parameter of Marquee call");
		logStep("Validating adid custom parameter of Marquee call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "adid", "NotNull");

	}

	/**
	 * This method validates adid custom parameter of Feed1 call
	 */
	@Test(priority = 212, enabled = true)
	@Description("Validating 'adid' custom parameter of Feed1 call ")
	public void Validate_Feed1_adid_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating adid custom parameter of Feed1 call");
		logStep("Validating adid custom parameter of Feed1 call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "adid", "NotNull");

	}

	/**
	 * This method validates adid custom parameter of Hourly details call
	 */
	/*@Test(priority = 213, enabled = true)
	@Description("Validating 'adid' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_adid_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating adid custom parameter of Hourly details call");
		logStep("Validating adid custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "adid", "NotNull");

	}*/

	/**
	 * This method validates cnd custom parameter of Marquee call
	 */
	@Test(priority = 214, enabled = true)
	@Description("Validating 'cnd' custom parameter of Marquee call ")
	public void Validate_Marquee_cnd_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating cnd custom parameter of Marquee call");
		logStep("Validating cnd custom parameter of Marquee call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "cnd");

	}

	/**
	 * This method validates cnd custom parameter of Feed1 call
	 */
	@Test(priority = 215, enabled = true)
	@Description("Validating 'cnd' custom parameter of Feed1 call ")
	public void Validate_Feed1_cnd_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating cnd custom parameter of Feed1 call");
		logStep("Validating cnd custom parameter of Feed1 call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "cnd");

	}

	/**
	 * This method validates cnd custom parameter of Hourly details call
	 */
	/*@Test(priority = 216, enabled = true)
	@Description("Validating 'cnd' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_cnd_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating cnd custom parameter of Hourly details call");
		logStep("Validating cnd custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "cnd");

	}*/

	/**
	 * This method validates dma custom parameter of Marquee call
	 */
	@Test(priority = 217, enabled = true)
	@Description("Validating 'dma' custom parameter of Marquee call ")
	public void Validate_Marquee_dma_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating dma custom parameter of Marquee call");
		logStep("Validating dma custom parameter of Marquee call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "dma");

	}

	/**
	 * This method validates dma custom parameter of Feed1 call
	 */
	@Test(priority = 218, enabled = true)
	@Description("Validating 'dma' custom parameter of Feed1 call ")
	public void Validate_Feed1_dma_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating dma custom parameter of Feed1 call");
		logStep("Validating dma custom parameter of Feed1 call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "dma");

	}

	/**
	 * This method validates dma custom parameter of Hourly details call
	 */
	/*@Test(priority = 219, enabled = true)
	@Description("Validating 'dma' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_dma_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating dma custom parameter of Hourly details call");
		logStep("Validating dma custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "dma");

	}*/

	/**
	 * This method validates tmp custom parameter of Marquee call for non-existence
	 */
	@Test(priority = 220, enabled = true)
	@Description("Validating 'tmp' custom parameter of Marquee call ")
	public void Validate_Marquee_tmp_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating tmp custom parameter of Marquee call for non-existence");
		logStep("Validating tmp custom parameter of Marquee call for non-existence ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "tmp", false);

	}

	/**
	 * This method validates tmp custom parameter of Feed1 call for non-existence
	 */
	@Test(priority = 221, enabled = true)
	@Description("Validating 'tmp' custom parameter of Feed1 call ")
	public void Validate_Feed1_tmp_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating tmp custom parameter of Feed1 call for non-existence");
		logStep("Validating tmp custom parameter of Feed1 call for non-existence ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "tmp", false);

	}

	/**
	 * This method validates tmp custom parameter of Hourly details call for non-existence
	 */
	/*@Test(priority = 222, enabled = true)
	@Description("Validating 'tmp' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_tmp_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating tmp custom parameter of Hourly details call for non-existence");
		logStep("Validating tmp custom parameter of Hourly details call for non-existence ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "tmp", false);

	}*/

	/**
	 * This method validates tmpc custom parameter of Marquee call
	 */
	@Test(priority = 223, enabled = true)
	@Description("Validating 'tmpc' custom parameter of Marquee call ")
	public void Validate_Marquee_tmpc_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating tmpc custom parameter of Marquee call");
		logStep("Validating tmpc custom parameter of Marquee call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "tmpc");

	}

	/**
	 * This method validates tmpc custom parameter of Feed1 call
	 */
	@Test(priority = 224, enabled = true)
	@Description("Validating 'tmpc' custom parameter of Feed1 call ")
	public void Validate_Feed1_tmpc_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating tmpc custom parameter of Feed1 call");
		logStep("Validating tmpc custom parameter of Feed1 call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "tmpc");

	}

	/**
	 * This method validates tmpc custom parameter of Hourly details call
	 */
	/*@Test(priority = 225, enabled = true)
	@Description("Validating 'tmpc' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_tmpc_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating tmpc custom parameter of Hourly details call");
		logStep("Validating tmpc custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "tmpc");

	}*/

	/**
	 * This method validates tmpr custom parameter of Marquee call for non-existence
	 */
	@Test(priority = 226, enabled = true)
	@Description("Validating 'tmpr' custom parameter of Marquee call ")
	public void Validate_Marquee_tmpr_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating tmpr custom parameter of Marquee call for non-existence");
		logStep("Validating tmpr custom parameter of Marquee call for non-existence ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "tmpr", false);

	}

	/**
	 * This method validates tmpr custom parameter of Feed1 call for non-existence
	 */
	@Test(priority = 227, enabled = true)
	@Description("Validating 'tmpr' custom parameter of Feed1 call ")
	public void Validate_Feed1_tmpr_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating tmpr custom parameter of Feed1 call for non-existence");
		logStep("Validating tmpr custom parameter of Feed1 call for non-existence ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "tmpr", false);

	}

	/**
	 * This method validates tmpr custom parameter of Hourly details call for non-existence
	 */
	/*@Test(priority = 228, enabled = true)
	@Description("Validating 'tmpr' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_tmpr_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating tmpr custom parameter of Hourly details call for non-existence");
		logStep("Validating tmpr custom parameter of Hourly details call for non-existence ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "tmpr", false);

	}*/

	/**
	 * This method validates ct custom parameter of Marquee call
	 */
	@Test(priority = 229, enabled = true)
	@Description("Validating 'ct' custom parameter of Marquee call ")
	public void Validate_Marquee_ct_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ct custom parameter of Marquee call");
		logStep("Validating ct custom parameter of Marquee call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "ct");

	}

	/**
	 * This method validates ct custom parameter of Feed1 call
	 */
	@Test(priority = 230, enabled = true)
	@Description("Validating 'ct' custom parameter of Feed1 call ")
	public void Validate_Feed1_ct_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ct custom parameter of Feed1 call");
		logStep("Validating ct custom parameter of Feed1 call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "ct");

	}

	/**
	 * This method validates ct custom parameter of Hourly details call
	 */
	/*@Test(priority = 231, enabled = true)
	@Description("Validating 'ct' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_ct_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ct custom parameter of Hourly details call");
		logStep("Validating ct custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "ct");

	}*/

	/**
	 * This method validates lang custom parameter of Marquee call
	 */
	@Test(priority = 232, enabled = true)
	@Description("Validating 'lang' custom parameter of Marquee call ")
	public void Validate_Marquee_lang_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating lang custom parameter of Marquee call");
		logStep("Validating lang custom parameter of Marquee call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "lang", "en");

	}

	/**
	 * This method validates lang custom parameter of Feed1 call
	 */
	@Test(priority = 233, enabled = true)
	@Description("Validating 'lang' custom parameter of Feed1 call ")
	public void Validate_Feed1_lang_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating lang custom parameter of Feed1 call");
		logStep("Validating lang custom parameter of Feed1 call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "lang", "en");

	}

	/**
	 * This method validates lang custom parameter of Hourly details call
	 */
	/*@Test(priority = 234, enabled = true)
	@Description("Validating 'lang' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_lang_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating lang custom parameter of Hourly details call");
		logStep("Validating lang custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "lang", "en");

	}*/

	/**
	 * This method validates aid custom parameter of Marquee call for non-existence
	 */
	@Test(priority = 235, enabled = true)
	@Description("Validating 'aid' custom parameter of Marquee call ")
	public void Validate_Marquee_aid_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating aid custom parameter of Marquee call for non-existence");
		logStep("Validating aid custom parameter of Marquee call for non-existence ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "aid", false);

	}
	 

	/**
	 * This method validates aid custom parameter of Feed1 call for non-existence
	 */
	@Test(priority = 236, enabled = true)
	@Description("Validating 'aid' custom parameter of Feed1 call ")
	public void Validate_Feed1_aid_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating aid custom parameter of Feed1 call for non-existence");
		logStep("Validating aid custom parameter of Feed1 call for non-existence ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "aid", false);

	}
	 

	/**
	 * This method validates aid custom parameter of Hourly details call for non-existence
	 */
	/*@Test(priority = 237, enabled = true)
	@Description("Validating 'aid' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_aid_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating aid custom parameter of Hourly details call for non-existence");
		logStep("Validating aid custom parameter of Hourly details call for non-existence ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "aid", false);

	}*/
	 

	/**
	 * This method validates dynght custom parameter of Marquee call
	 */
	@Test(priority = 238, enabled = true)
	@Description("Validating 'dynght' custom parameter of Marquee call ")
	public void Validate_Marquee_dynght_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating dynght custom parameter of Marquee call");
		logStep("Validating dynght custom parameter of Marquee call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "dynght");

	}

	/**
	 * This method validates dynght custom parameter of Feed1 call
	 */
	@Test(priority = 239, enabled = true)
	@Description("Validating 'dynght' custom parameter of Feed1 call ")
	public void Validate_Feed1_dynght_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating dynght custom parameter of Feed1 call");
		logStep("Validating dynght custom parameter of Feed1 call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "dynght");

	}

	/**
	 * This method validates dynght custom parameter of Hourly details call
	 */
	/*@Test(priority = 240, enabled = true)
	@Description("Validating 'dynght' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_dynght_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating dynght custom parameter of Hourly details call");
		logStep("Validating dynght custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "dynght");

	}*/

	/**
	 * This method validates locale custom parameter of Marquee call
	 */
	@Test(priority = 241, enabled = true)
	@Description("Validating 'locale' custom parameter of Marquee call ")
	public void Validate_Marquee_locale_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating locale custom parameter of Marquee call");
		logStep("Validating locale custom parameter of Marquee call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "locale");

	}

	/**
	 * This method validates locale custom parameter of Feed1 call
	 */
	@Test(priority = 242, enabled = true)
	@Description("Validating 'locale' custom parameter of Feed1 call ")
	public void Validate_Feed1_locale_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating locale custom parameter of Feed1 call");
		logStep("Validating locale custom parameter of Feed1 call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "locale");

	}

	/**
	 * This method validates locale custom parameter of Hourly details call
	 */
	/*@Test(priority = 243, enabled = true)
	@Description("Validating 'locale' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_locale_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating locale custom parameter of Hourly details call");
		logStep("Validating locale custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "locale");

	}*/

	/**
	 * This method validates tile custom parameter of Marquee call for non-existence
	 */
	@Test(priority = 244, enabled = true)
	@Description("Validating 'tile' custom parameter of Marquee call ")
	public void Validate_Marquee_tile_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating tile custom parameter of Marquee call for non-existence");
		logStep("Validating tile custom parameter of Marquee call for non-existence ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "tile", false);

	}

	/**
	 * This method validates tile custom parameter of Feed1 call for non-existence
	 */
	@Test(priority = 245, enabled = true)
	@Description("Validating 'tile' custom parameter of Feed1 call ")
	public void Validate_Feed1_tile_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating tile custom parameter of Feed1 call for non-existence");
		logStep("Validating tile custom parameter of Feed1 call for non-existence ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "tile", false);

	}

	/**
	 * This method validates tile custom parameter of Hourly details call for non-existence
	 */
	/*@Test(priority = 246, enabled = true)
	@Description("Validating 'tile' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_tile_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating tile custom parameter of Hourly details call for non-existence");
		logStep("Validating tile custom parameter of Hourly details call for non-existence ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "tile", false);

	}*

	/**
	 * This method validates ver custom parameter of Marquee call
	 */
	@Test(priority = 248, enabled = true)
	@Description("Validating 'ver' custom parameter of Marquee call ")
	public void Validate_Marquee_ver_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ver custom parameter of Marquee call");
		logStep("Validating ver custom parameter of Marquee call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "ver", "NotNull");

	}

	/**
	 * This method validates ver custom parameter of Feed1 call
	 */
	@Test(priority = 249, enabled = true)
	@Description("Validating 'ver' custom parameter of Feed1 call ")
	public void Validate_Feed1_ver_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ver custom parameter of Feed1 call");
		logStep("Validating ver custom parameter of Feed1 call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "ver", "NotNull");

	}

	/**
	 * This method validates ver custom parameter of Hourly details call
	 */
	/*@Test(priority = 250, enabled = true)
	@Description("Validating 'ver' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_ver_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ver custom parameter of Hourly details call");
		logStep("Validating ver custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "ver", "NotNull");

	}*/

	/**
	 * This method validates ord custom parameter of Marquee call
	 */
	@Test(priority = 251, enabled = true)
	@Description("Validating 'ord' custom parameter of Marquee call ")
	public void Validate_Marquee_ord_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ord custom parameter of Marquee call");
		logStep("Validating ord custom parameter of Marquee call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "ord", "NotNull");

	}

	/**
	 * This method validates ord custom parameter of Feed1 call
	 */
	@Test(priority = 252, enabled = true)
	@Description("Validating 'ord' custom parameter of Feed1 call ")
	public void Validate_Feed1_ord_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ord custom parameter of Feed1 call");
		logStep("Validating ord custom parameter of Feed1 call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "ord", "NotNull");

	}

	/**
	 * This method validates ord custom parameter of Hourly details call
	 */
	/*@Test(priority = 253, enabled = true)
	@Description("Validating 'ord' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_ord_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ord custom parameter of Hourly details call");
		logStep("Validating ord custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "ord", "NotNull");

	}*/

	/**
	 * This method validates rmid custom parameter of Marquee call
	 */
	@Test(priority = 254, enabled = true)
	@Description("Validating 'rmid' custom parameter of Marquee call ")
	public void Validate_Marquee_rmid_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating rmid custom parameter of Marquee call");
		logStep("Validating rmid custom parameter of Marquee call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "rmid", "NotNull");

	}

	/**
	 * This method validates rmid custom parameter of Feed1 call
	 */
	@Test(priority = 255, enabled = true)
	@Description("Validating 'rmid' custom parameter of Feed1 call ")
	public void Validate_Feed1_rmid_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating rmid custom parameter of Feed1 call");
		logStep("Validating rmid custom parameter of Feed1 call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "rmid", "NotNull");

	}

	/**
	 * This method validates rmid custom parameter of Hourly details call
	 */
	/*@Test(priority = 256, enabled = true)
	@Description("Validating 'rmid' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_rmid_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating rmid custom parameter of Hourly details call");
		logStep("Validating rmid custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "rmid", "NotNull");

	}*/

	/**
	 * This method validates st custom parameter of Marquee call
	 */
	@Test(priority = 257, enabled = true)
	@Description("Validating 'st' custom parameter of Marquee call ")
	public void Validate_Marquee_st_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating st custom parameter of Marquee call");
		logStep("Validating st custom parameter of Marquee call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "st");

	}

	/**
	 * This method validates st custom parameter of Feed1 call
	 */
	@Test(priority = 258, enabled = true)
	@Description("Validating 'st' custom parameter of Feed1 call ")
	public void Validate_Feed1_st_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating st custom parameter of Feed1 call");
		logStep("Validating st custom parameter of Feed1 call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "st");

	}

	/**
	 * This method validates st custom parameter of Hourly details call
	 */
	/*@Test(priority = 259, enabled = true)
	@Description("Validating 'st' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_st_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating st custom parameter of Hourly details call");
		logStep("Validating st custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "st");

	}*/
	
	/**
	 * This method validates tf custom parameter of Homescreen adhesive call
	 */
	@Test(priority = 260, enabled = true)
	@Description("Validating 'tf' custom parameter of HomeScreen Adhesive call ")
	public void Validate_HomeScreen_Call_tf_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating tf custom parameter of HomeScreen adhesive call");
		logStep("Validating tf custom parameter of HomeScreen adhesive call");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Pulltorefresh", "tf", "NotNull");

	}
	
	/**
	 * This method validates tf custom parameter of Hourly details call
	 */
	/*@Test(priority = 261, enabled = true)
	@Description("Validating 'tf' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_tf_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating tf custom parameter of Hourly details call");
		logStep("Validating tf custom parameter of Hourly details call");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "tf", "NotNull");

	}*/
	
	/**
	 * This method validates tf custom parameter of Daily details call
	 */
	@Test(priority = 262, enabled = true)
	@Description("Validating 'tf' custom parameter of Daily details call ")
	public void Validate_DailyDetails_tf_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating tf custom parameter of Daily details call");
		logStep("Validating tf custom parameter of Daily details call");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Daily(10day)", "tf", "NotNull");

	}

	/**
	 * This method validates fcnd custom parameter of daily details call
	 */
	@Test(priority = 264, enabled = true)
	@Description("Validating 'fcnd' custom parameter of daily details call ")
	public void Validate_daily_details_fcnd_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating fcnd custom parameter of daily details call");
		logStep("Validating fcnd custom parameter of daily details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Daily(10day)", "fcnd");

	}

	/**
	 * This method validates fdynght custom parameter of daily details call
	 */
	@Test(priority = 265, enabled = true)
	@Description("Validating 'fdynght' custom parameter of daily details call ")
	public void Validate_daily_details_fdynght_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating fdynght custom parameter of daily details call");
		logStep("Validating fdynght custom parameter of daily details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Daily(10day)", "fdynght");

	}

	/**
	 * This method validates dt custom parameter of daily details call
	 */
	@Test(priority = 266, enabled = true)
	@Description("Validating 'dt' custom parameter of daily details call ")
	public void Validate_daily_details_dt_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating dt custom parameter of daily details call");
		logStep("Validating dt custom parameter of daily details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Daily(10day)", "dt");

	}

	/**
	 * This method validates mnth custom parameter of daily details call
	 */
	@Test(priority = 267, enabled = true)
	@Description("Validating 'mnth' custom parameter of daily details call ")
	public void Validate_daily_details_mnth_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating mnth custom parameter of daily details call");
		logStep("Validating mnth custom parameter of daily details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Daily(10day)", "mnth");

	}

	/**
	 * This method validates ltv custom parameter of Marquee call
	 */
	@Test(priority = 270, enabled = true)
	@Description("Validating 'ltv' custom parameter of Marquee call ")
	public void Validate_Marquee_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of Marquee call");
		logStep("Validating ltv custom parameter of Marquee call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "ltv", "NotNull");

	}
	
	/**
	 * This method validates ltv custom parameter of Homescreen Adhesive call
	 */
	@Test(priority = 271, enabled = true)
	@Description("Validating 'ltv' custom parameter of Homescreen Adhesive call ")
	public void Validate_Homescreen_Adhesive_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of Homescreen Adhesive call");
		logStep("Validating ltv custom parameter of Homescreen Adhesive call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Pulltorefresh", "ltv", "NotNull");

	}

	/**
	 * This method validates ltv custom parameter of Feed1 call
	 */
	@Test(priority = 272, enabled = true)
	@Description("Validating 'ltv' custom parameter of Feed1 call ")
	public void Validate_Feed1_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of Feed1 call");
		logStep("Validating ltv custom parameter of Feed1 call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "ltv", "NotNull");

	}
	
	
	/**
	 * This method validates ltv custom parameter of Hourly details call
	 */
	/*@Test(priority = 272, enabled = true)
	@Description("Validating 'ltv' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of Hourly details call");
		logStep("Validating ltv custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "ltv", "NotNull");

	}*/
	
	/**
	 * This method validates ltv custom parameter of Health(allergy) ContentPage call
	 */
	@Test(priority = 273, enabled = true)
	@Description("Validating 'ltv' custom parameter of Health(allergy) ContentPage call ")
	public void Validate_Health_Allergy_ContentPage_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of Health(allergy) ContentPage call");
		logStep("Validating ltv custom parameter of Health(allergy) ContentPage call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Health(allergy)", "ltv", "NotNull");

	}
	
	/**
	 * This method validates ltv custom parameter of Health(allergy) Spotlight call
	 */
	@Test(priority = 274, enabled = true)
	@Description("Validating 'ltv' custom parameter of Health(allergy) Spotlight call ")
	public void Validate_Health_Allergy_Spotlight_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of Health(allergy) Spotlight call");
		logStep("Validating ltv custom parameter of Health(allergy) Spotlight call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Health(allergySpotLight)", "ltv", "NotNull");

	}
	
	/**
	 * This method validates ltv custom parameter of Health(allergy) Articles call
	 */
	@Test(priority = 275, enabled = true)
	@Description("Validating 'ltv' custom parameter of Health(allergy) Articles call ")
	public void Validate_Health_Allergy_Articles_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of Health(allergy) Articles call");
		logStep("Validating ltv custom parameter of Health(allergy) Articles call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Health(allergyArticles)", "ltv", "NotNull");

	}
	
	/**
	 * This method validates ltv custom parameter of Health(coldAndFlu) ContentPage call
	 */
	@Test(priority = 276, enabled = true)
	@Description("Validating 'ltv' custom parameter of Health(coldAndFlu)) ContentPage call ")
	public void Validate_Health_Flu_ContentPage_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of Health(coldAndFlu) ContentPage call");
		logStep("Validating ltv custom parameter of Health(coldAndFlu) ContentPage call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Health(coldAndFlu)", "ltv", "NotNull");

	}
	
	/**
	 * This method validates ltv custom parameter of Health(coldAndFlu) Articles call
	 */
	@Test(priority = 277, enabled = true)
	@Description("Validating 'ltv' custom parameter of Health(coldAndFlu) Articles call ")
	public void Validate_Health_Flu_Articles_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of Health(coldAndFlu) Articles call");
		logStep("Validating ltv custom parameter of Health(coldAndFlu) Articles call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Health(coldAndFluArticles)", "ltv", "NotNull");

	}
	
	/**
	 * This method validates ltv custom parameter of Map Details call
	 */
	@Test(priority = 278, enabled = true)
	@Description("Validating 'ltv' custom parameter of Map Details call ")
	public void Validate_MapDetails_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of Map Details call");
		logStep("Validating ltv custom parameter of Map Details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Map", "ltv", "NotNull");

	}
	
	/**
	 * This method validates ltv custom parameter of AirQuality Details call
	 */
	@Test(priority = 279, enabled = true)
	@Description("Validating 'ltv' custom parameter of AirQuality Details call ")
	public void Validate_AirQuality_Details_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of AirQuality Details call");
		logStep("Validating ltv custom parameter of AirQuality Details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Air Quality(Content)", "ltv", "NotNull");

	}
	
	/**
	 * This method validates ltv custom parameter of Alert Center call
	 */
	@Test(priority = 280, enabled = true)
	@Description("Validating 'ltv' custom parameter of Alert Center call ")
	public void Validate_Alert_Center_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of Alert Center call");
		logStep("Validating ltv custom parameter of Alert Center call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "MyAlerts", "ltv", "NotNull");

	}
	
	/**
	 * This method validates ltv custom parameter of SafetyAndPreparedness call
	 */
	@Test(priority = 281, enabled = true)
	@Description("Validating 'ltv' custom parameter of SafetyAndPreparedness call ")
	public void Validate_SafetyAndPreparedness_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of SafetyAndPreparedness call");
		logStep("Validating ltv custom parameter of SafetyAndPreparedness call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "SafetyAndPreparedness", "ltv", "NotNull");

	}

	/**
	 * This method validates fhic custom parameter of Marquee call for non-existence
	 */
	@Test(priority = 283, enabled = true)
	@Description("Validating 'fhic' custom parameter of Marquee call ")
	public void Validate_Marquee_fhic_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating fhic custom parameter of Marquee call for non-existence");
		logStep("Validating fhic custom parameter of Marquee call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "fhic", false);

	}

	/**
	 * This method validates fhic custom parameter of Feed1 call for non-existence
	 */
	@Test(priority = 284, enabled = true)
	@Description("Validating 'fhic' custom parameter of Feed1 call ")
	public void Validate_Feed1_fhic_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating fhic custom parameter of Feed1 call for non-existence");
		logStep("Validating fhic custom parameter of Feed1 call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "fhic", false);

	}

	/**
	 * This method validates fhic custom parameter of Hourly details call for
	 * non-existence
	 */
	/*@Test(priority = 285, enabled = true)
	@Description("Validating 'fhic' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_fhic_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating fhic custom parameter of Hourly details call for non-existence");
		logStep("Validating fhic custom parameter of Hourly details call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "fhic", false);

	}*/

	/**
	 * This method validates floc custom parameter of Marquee call for non-existence
	 */
	@Test(priority = 286, enabled = true)
	@Description("Validating 'floc' custom parameter of Marquee call ")
	public void Validate_Marquee_floc_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating floc custom parameter of Marquee call for non-existence");
		logStep("Validating floc custom parameter of Marquee call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "floc", false);

	}

	/**
	 * This method validates floc custom parameter of Feed1 call for non-existence
	 */
	@Test(priority = 287, enabled = true)
	@Description("Validating 'floc' custom parameter of Feed1 call ")
	public void Validate_Feed1_floc_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating floc custom parameter of Feed1 call for non-existence");
		logStep("Validating floc custom parameter of Feed1 call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "floc", false);

	}

	/**
	 * This method validates floc custom parameter of Hourly details call for
	 * non-existence
	 */
	/*@Test(priority = 288, enabled = true)
	@Description("Validating 'floc' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_floc_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating floc custom parameter of Hourly details call for non-existence");
		logStep("Validating floc custom parameter of Hourly details call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "floc", false);

	}*/

	/**
	 * This method validates fltmpc custom parameter of Marquee call for
	 * non-existence
	 */
	@Test(priority = 289, enabled = true)
	@Description("Validating 'fltmpc' custom parameter of Marquee call ")
	public void Validate_Marquee_fltmpc_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating fltmpc custom parameter of Marquee call for non-existence");
		logStep("Validating fltmpc custom parameter of Marquee call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "fltmpc", false);

	}

	/**
	 * This method validates fltmpc custom parameter of Feed1 call for non-existence
	 */
	@Test(priority = 290, enabled = true)
	@Description("Validating 'fltmpc' custom parameter of Feed1 call ")
	public void Validate_Feed1_fltmpc_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating fltmpc custom parameter of Feed1 call for non-existence");
		logStep("Validating fltmpc custom parameter of Feed1 call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "fltmpc", false);

	}

	/**
	 * This method validates fltmpc custom parameter of Hourly details call for
	 * non-existence
	 */
	/*@Test(priority = 291, enabled = true)
	@Description("Validating 'fltmpc' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_fltmpc_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating fltmpc custom parameter of Hourly details call for non-existence");
		logStep("Validating fltmpc custom parameter of Hourly details call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "fltmpc", false);

	}*/

	/**
	 * This method validates ftl custom parameter of Marquee call for non-existence
	 */
	@Test(priority = 292, enabled = true)
	@Description("Validating 'ftl' custom parameter of Marquee call ")
	public void Validate_Marquee_ftl_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ftl custom parameter of Marquee call for non-existence");
		logStep("Validating ftl custom parameter of Marquee call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "ftl", false);

	}

	/**
	 * This method validates ftl custom parameter of Feed1 call for non-existence
	 */
	@Test(priority = 293, enabled = true)
	@Description("Validating 'ftl' custom parameter of Feed1 call ")
	public void Validate_Feed1_ftl_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ftl custom parameter of Feed1 call for non-existence");
		logStep("Validating ftl custom parameter of Feed1 call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "ftl", false);

	}

	/**
	 * This method validates ftl custom parameter of Hourly details call for
	 * non-existence
	 */
	/*@Test(priority = 294, enabled = true)
	@Description("Validating 'ftl' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_ftl_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ftl custom parameter of Hourly details call for non-existence");
		logStep("Validating ftl custom parameter of Hourly details call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "ftl", false);

	}*/

	/**
	 * This method validates h2o custom parameter of Marquee call for non-existence
	 */
	@Test(priority = 295, enabled = true)
	@Description("Validating 'h2o' custom parameter of Marquee call ")
	public void Validate_Marquee_h2o_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating h2o custom parameter of Marquee call for non-existence");
		logStep("Validating h2o custom parameter of Marquee call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "h2o", false);

	}

	/**
	 * This method validates h2o custom parameter of Feed1 call for non-existence
	 */
	@Test(priority = 296, enabled = true)
	@Description("Validating 'h2o' custom parameter of Feed1 call ")
	public void Validate_Feed1_h2o_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating h2o custom parameter of Feed1 call for non-existence");
		logStep("Validating h2o custom parameter of Feed1 call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "h2o", false);

	}

	/**
	 * This method validates h2o custom parameter of Hourly details call for
	 * non-existence
	 */
	/*@Test(priority = 297, enabled = true)
	@Description("Validating 'h2o' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_h2o_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating h2o custom parameter of Hourly details call for non-existence");
		logStep("Validating h2o custom parameter of Hourly details call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "h2o", false);

	}*/

	/**
	 * This method validates hmid custom parameter of Marquee call for non-existence
	 */
	@Test(priority = 298, enabled = true)
	@Description("Validating 'hmid' custom parameter of Marquee call ")
	public void Validate_Marquee_hmid_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating hmid custom parameter of Marquee call for non-existence");
		logStep("Validating hmid custom parameter of Marquee call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "hmid", false);

	}

	/**
	 * This method validates hmid custom parameter of Feed1 call for non-existence
	 */
	@Test(priority = 299, enabled = true)
	@Description("Validating 'hmid' custom parameter of Feed1 call ")
	public void Validate_Feed1_hmid_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating hmid custom parameter of Feed1 call for non-existence");
		logStep("Validating hmid custom parameter of Feed1 call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "hmid", false);

	}

	/**
	 * This method validates hmid custom parameter of Hourly details call for
	 * non-existence
	 */
	/*@Test(priority = 300, enabled = true)
	@Description("Validating 'hmid' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_hmid_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating hmid custom parameter of Hourly details call for non-existence");
		logStep("Validating hmid custom parameter of Hourly details call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "hmid", false);

	}*/

	/**
	 * This method validates layer custom parameter of Marquee call for
	 * non-existence
	 */
	@Test(priority = 301, enabled = true)
	@Description("Validating 'layer' custom parameter of Marquee call ")
	public void Validate_Marquee_layer_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating layer custom parameter of Marquee call for non-existence");
		logStep("Validating layer custom parameter of Marquee call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "layer", false);

	}

	/**
	 * This method validates layer custom parameter of Feed1 call for non-existence
	 */
	@Test(priority = 302, enabled = true)
	@Description("Validating 'layer' custom parameter of Feed1 call ")
	public void Validate_Feed1_layer_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating layer custom parameter of Feed1 call for non-existence");
		logStep("Validating layer custom parameter of Feed1 call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "layer", false);

	}

	/**
	 * This method validates layer custom parameter of Hourly details call for
	 * non-existence
	 */
	/*@Test(priority = 303, enabled = true)
	@Description("Validating 'layer' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_layer_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating layer custom parameter of Hourly details call for non-existence");
		logStep("Validating layer custom parameter of Hourly details call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "layer", false);

	}*/

	/**
	 * This method validates uv custom parameter of Marquee call for non-existence
	 */
	@Test(priority = 304, enabled = true)
	@Description("Validating 'uv' custom parameter of Marquee call ")
	public void Validate_Marquee_uv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating uv custom parameter of Marquee call for non-existence");
		logStep("Validating uv custom parameter of Marquee call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "uv", false);

	}

	/**
	 * This method validates uv custom parameter of Feed1 call for non-existence
	 */
	@Test(priority = 305, enabled = true)
	@Description("Validating 'uv' custom parameter of Feed1 call ")
	public void Validate_Feed1_uv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating uv custom parameter of Feed1 call for non-existence");
		logStep("Validating uv custom parameter of Feed1 call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "uv", false);

	}

	/**
	 * This method validates uv custom parameter of Hourly details call for
	 * non-existence
	 */
	/*@Test(priority = 306, enabled = true)
	@Description("Validating 'uv' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_uv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating uv custom parameter of Hourly details call for non-existence");
		logStep("Validating uv custom parameter of Hourly details call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "uv", false);

	}*/

	/**
	 * This method validates wind custom parameter of Marquee call for non-existence
	 */
	@Test(priority = 307, enabled = true)
	@Description("Validating 'wind' custom parameter of Marquee call ")
	public void Validate_Marquee_wind_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating wind custom parameter of Marquee call for non-existence");
		logStep("Validating wind custom parameter of Marquee call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "wind", false);

	}

	/**
	 * This method validates wind custom parameter of Feed1 call for non-existence
	 */
	@Test(priority = 308, enabled = true)
	@Description("Validating 'wind' custom parameter of Feed1 call ")
	public void Validate_Feed1_wind_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating wind custom parameter of Feed1 call for non-existence");
		logStep("Validating wind custom parameter of Feed1 call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "wind", false);

	}

	/**
	 * This method validates wind custom parameter of Hourly details call for
	 * non-existence
	 */
	/*@Test(priority = 309, enabled = true)
	@Description("Validating 'wind' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_wind_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating wind custom parameter of Hourly details call for non-existence");
		logStep("Validating wind custom parameter of Hourly details call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "wind", false);

	}*/
	
	/**
	 * This method validates mr custom parameter of Marquee Call
	 */
	@Test(priority = 310, enabled = true)
	@Description("Validating 'mr' custom parameter of Marquee Call")
	public void validate_Marquee_mr_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating mr custom parameter of Marquee call");
		logStep("Validating mr custom parameter of Marquee call");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "mr", "0");
	}
	 
	
	/**
	 * This method validates mr custom parameter of Feed1 call
	 */
	@Test(priority = 311, enabled = true)
	@Description("Validating 'mr' custom parameter of Feed1 call ")
	public void Validate_Feed1_mr_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating mr custom parameter of Feed1 call");
		logStep("Validating mr custom parameter of Feed1 call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "mr", "0");

	}
	
	/**
	 * This method validates mr custom parameter of Sticky ad Call
	 */
	@Test(priority = 312, enabled = true)
	@Description("Validating 'mr' custom parameter of Sticky ad Call")
	public void validate_Sticky_Ad_mr_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating mr custom parameter of Sticky ad call");
		logStep("Validating mr custom parameter of Sticky ad call");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Pulltorefresh", "mr", "0");
	}

	/**
	 * This method validates mr custom parameter of Hourly details call
	 */
	/*@Test(priority = 313, enabled = true)
	@Description("Validating 'mr' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_mr_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating mr custom parameter of Hourly Details call");
		logStep("Validating mr custom parameter of Hourly Details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "mr", "0");

	}*/
	
	/**
	 * This method validates dnt custom parameter of Marquee call
	 */
	@Test(priority = 314, enabled = true)
	@Description("Validating 'dnt' custom parameter of Marquee call ")
	public void Validate_Marquee_dnt_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating dnt custom parameter of Marquee call");
		logStep("Validating dnt custom parameter of Marquee call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "dnt", "n");

	}

	/**
	 * This method validates dnt custom parameter of Feed1 call
	 */
	@Test(priority = 315, enabled = true)
	@Description("Validating 'dnt' custom parameter of Feed1 call ")
	public void Validate_Feed1_dnt_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating dnt custom parameter of Feed1 call");
		logStep("Validating dnt custom parameter of Feed1 call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "dnt", "n");

	}

	/**
	 * This method validates dnt custom parameter of Hourly details call
	 */
	/*@Test(priority = 316, enabled = true)
	@Description("Validating 'dnt' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_dnt_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating dnt custom parameter of Hourly details call");
		logStep("Validating dnt custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "dnt", "n");

	}*/
	
	/**
	 * This method validates attmas parameter of Marquee call when tracking enabled
	 */
	@Test(priority = 317, enabled = true)
	@Description("Validating 'attmas' parameter of Marquee call when tracking enabled")
	public void Validate_Marquee_call_attmas_param_when_tracking_enabled() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating attmas parameter of Marquee call when tracking enabled");
		logStep("Validating attmas parameter of Marquee call when tracking enabled");
		Utils.validate_non_custom_param_val_in_gampad_url("Smoke", "Marquee", "attmas", "authorized");

	}
	
	/**
	 * This method validates attmas parameter of Feed1 call when tracking enabled
	 */
	@Test(priority = 318, enabled = true)
	@Description("Validating 'attmas' parameter of Feed1 call when tracking enabled")
	public void Validate_Feed1_call_attmas_param_when_tracking_enabled() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating attmas parameter of Feed1 call when tracking enabled");
		logStep("Validating attmas parameter of Feed1 call when tracking enabled");
		Utils.validate_non_custom_param_val_in_gampad_url("Smoke", "Feed1", "attmas", "authorized");

	}
	
	@Test(priority = 320, enabled = true)
	@Description("Verify Allergy Details Spot Light ad call iu")
	public void Verify_AllergyDetails_Spotlight_AdCall() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Allergy Details Spotlight Adcall verification test case Started");
		logStep("****** Allergy Details Adcall verification test case Started");
		Utils.verifyPubadCal("Smoke", "WMAllergy(SpotLight)");
	}

	/**
	 * This method validates pos custom parameter of Week Ahead call
	 */
	@Test(priority = 321, enabled = true)
	@Description("Validating 'pos' custom parameter of Allergy Details Spot Light call ")
	public void Validate_AllergyDetails_Spotlight_pos_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating pos custom parameter of Allergy Details Spot Light call");
		logStep("Validating pos custom parameter of Allergy Details Spot Light call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "WMAllergy(SpotLight)", "pos", "app_sl");

	}

	@Test(priority = 322, enabled = true)
	@Description("Validating Allergy Details Spot Light call Ad sz parameter")
	public void Validate_AllergyDetails_Spotlight_Ad_Size() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Allergy Details Spot Light call Ad sz parameter in charles");
		logStep("Validating Allergy Details Spot Light call Ad sz parameter in charles ");
		Utils.verify_Ad_Size("Smoke", "WMAllergy(SpotLight)");

	}
	
	/**
	 * This method validates content_url parameter of Marquee Call
	 */
	/*@Test(priority = 325, enabled = true)
	@Description("Validating 'content_url' parameter of Marquee Call")
	public void validate_Marquee_Call_content_url_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating content_url parameter of Marquee call");
		logStep("Validating content_url parameter of Marquee call");
		Utils.validate_non_custom_param_val_in_gampad_url("Smoke", "Marquee", "content_url", "https://www.weather.com");
	}*/
	
	/**
	 * This method validates content_url parameter of Sticky ad Call
	 */
	@Test(priority = 326, enabled = true)
	@Description("Validating 'content_url' parameter of Sticky ad Call")
	public void validate_Sticky_Ad_Call_content_url_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating content_url parameter of Sticky ad call");
		logStep("Validating content_url parameter of Sticky ad call");
		Utils.validate_non_custom_param_val_in_gampad_url("Smoke", "Pulltorefresh", "content_url", "https://www.weather.com");
	}
	
	/**
	 * This method validates content_url parameter of Feed1 ad Call
	 */
	@Test(priority = 327, enabled = true)
	@Description("Validating 'content_url' parameter of Feed1 ad Call")
	public void validate_Feed1_Ad_Call_content_url_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating content_url parameter of Feed1 ad call");
		logStep("Validating content_url parameter of Feed1 ad call");
		Utils.validate_non_custom_param_val_in_gampad_url("Smoke", "Feed1", "content_url", "https://www.weather.com");
	}
	
	/**
	 * This method validates content_url parameter of Daily Details ad Call
	 */
	@Test(priority = 328, enabled = true)
	@Description("Validating 'content_url' parameter of Daily Details ad Call")
	public void validate_Daily_Details_Ad_Call_content_url_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating content_url parameter of Daily Details ad call");
		logStep("Validating content_url parameter of Daily Details ad call");
		Utils.validate_non_custom_param_val_in_gampad_url("Smoke", "Daily(10day)", "content_url", "https://weather.com/weather/tenday/l/"+Utils.placeId);
	}
	
	/**
	 * This method validates content_url parameter of Map Details ad Call
	 */
	@Test(priority = 329, enabled = true)
	@Description("Validating 'content_url' parameter of Map Details ad Call")
	public void validate_Map_Details_Ad_Call_content_url_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating content_url parameter of Map Details ad call");
		logStep("Validating content_url parameter of Map Details ad call");
		Utils.validate_non_custom_param_val_in_gampad_url("Smoke", "Map", "content_url", "https://weather.com/weather/radar/interactive/l/"+Utils.placeId);
	}
	
	/**
	 * This method validates content_url parameter of Covid ad Call
	 */
	/*@Test(priority = 330, enabled = true)
	@Description("Validating 'content_url' parameter of Covid ad Call")
	public void validate_Covid_Ad_Call_content_url_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating content_url parameter of Covid ad call");
		logStep("Validating content_url parameter of Covid ad call");
		Utils.validate_non_custom_param_val_in_gampad_url("Smoke", "Covid", "content_url", "https://www.weather.com");
	}*/
	
	/**
	 * This method validates content_url parameter of AQ Details ad Call
	 */
	/*@Test(priority = 331, enabled = true)
	@Description("Validating 'content_url' parameter of AQ Details ad Call")
	public void validate_AQ_Details_Ad_Call_content_url_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating content_url parameter of AQ Details ad call");
		logStep("Validating content_url parameter of AQ Details ad call");
		Utils.validate_non_custom_param_val_in_gampad_url("Smoke", "Air Quality(Content)", "content_url", "https://www.weather.com");
	}*/
	
	/**
	 * This method validates content_url parameter of Alert Center ad Call
	 */
	/*@Test(priority = 332, enabled = true)
	@Description("Validating 'content_url' parameter of Alert Center ad Call")
	public void validate_Alert_Center_Ad_Call_content_url_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating content_url parameter of Alert Center ad call");
		logStep("Validating content_url parameter of Alert Center ad call");
		Utils.validate_non_custom_param_val_in_gampad_url("Smoke", "MyAlerts", "content_url", "https://www.weather.com");
	}*/
	
	/**
	 * This method validates content_url parameter of Health Allergy ad Call
	 */
	/*@Test(priority = 333, enabled = true)
	@Description("Validating 'content_url' parameter of Health Allergy ad Call")
	public void validate_Health_Allergy_Ad_Call_content_url_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating content_url parameter of Health Allergy ad call");
		logStep("Validating content_url parameter of Health Allergy ad call");
		Utils.validate_non_custom_param_val_in_gampad_url("Smoke", "Health(allergy)", "content_url", "https://www.weather.com");
	}*/
	
	/**
	 * This method validates content_url parameter of Health Allergy Articles ad Call
	 */
	/*@Test(priority = 334, enabled = true)
	@Description("Validating 'content_url' parameter of Health Allergy Articles ad Call")
	public void validate_Health_Allergy_Articles_Ad_Call_content_url_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating content_url parameter of Health Allergy Articles ad call");
		logStep("Validating content_url parameter of Health Allergy Articles ad call");
		Utils.validate_non_custom_param_val_in_gampad_url("Smoke", "Health(allergyArticles)", "content_url", "https://www.weather.com");
	}*/
	
	/**
	 * This method validates content_url parameter of Allergy Details Spotlight ad Call
	 */
	/*@Test(priority = 335, enabled = true)
	@Description("Validating 'content_url' parameter of Allergy Details Spotlight ad Call")
	public void validate_Allergy_Details_Spotlight_Ad_Call_content_url_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating content_url parameter of Allergy Details Spotlight ad call");
		logStep("Validating content_url parameter of Allergy Details Spotlight ad call");
		Utils.validate_non_custom_param_val_in_gampad_url("Smoke", "WMAllergy(SpotLight)", "content_url", "https://www.weather.com");
	}*/
	
	/**
	 * This method validates content_url parameter of Health Flu ad Call
	 */
	/*@Test(priority = 336, enabled = true)
	@Description("Validating 'content_url' parameter of Health Flu ad Call")
	public void validate_Health_Flu_Ad_Call_content_url_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating content_url parameter of Health Flu ad call");
		logStep("Validating content_url parameter of Health Flu ad call");
		Utils.validate_non_custom_param_val_in_gampad_url("Smoke", "Health(coldAndFlu)", "content_url", "https://www.weather.com");
	}*/
	
	/**
	 * This method validates content_url parameter of Health Flu Articles ad Call
	 */
	/*@Test(priority = 337, enabled = true)
	@Description("Validating 'content_url' parameter of Health Flu Articles ad Call")
	public void validate_Health_Flu_Articles_Ad_Call_content_url_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating content_url parameter of Health Flu Articles ad call");
		logStep("Validating content_url parameter of Health Flu Articles ad call");
		Utils.validate_non_custom_param_val_in_gampad_url("Smoke", "Health(coldAndFluArticles)", "content_url", "https://www.weather.com");
	}*/
	
	/**
	 * This method validate the daily details ad units and its parameters To execute
	 * below method alone, Utils.get_v3_wx_forecast_daily_15day_data() to be
	 * executed as a prerequisite script
	 */
	@Test(priority = 450, enabled = true)
	@Description("Verify Daily Details Ad Units")
	public void Verify_Daily_Details_AdUnits() throws Exception {
	System.out.println("==============================================");

	System.out.println("****** Daily Details Ad Units verification test case Started");
	logStep("****** Daily Details Ad Units verification test case Started");
	// Utils.get_v3_wx_forecast_daily_15day_data();
	proxy.startRecording();
	Functions.close_launchApp();
	hmTab.clickonHomeTab();
	hmTab.clickonHomeTab();
	Functions.checkForAppState();
	addrScreen.enternewAddress(false, "Atlanta, Georgia");
	TestBase.waitForMilliSeconds(20000);
	proxy.clearCharlesSession();
	dTab.validateDailyDetailsAdUnits("Smoke", "Daily(10day)");
	}
	
	/**
	 * This method validates ltv custom parameter of Daily Details call
	 */
	@Test(priority = 451, enabled = true)
	@Description("Validating 'ltv' custom parameter of Daily Details call ")
	public void Validate_Daily_Details_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of Daily Details call");
		logStep("Validating ltv custom parameter of Daily Details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Daily(10day)", "ltv", "NotNull");

	}
	
	/**
	 * This method validate the 'mr' custom parameter of Hourly Details
	 * @throws Exception
	 */
	/*@Test(priority = 475, enabled = true)
	@Description("Verify 'mr' custom parameter of Hourly Details")
	public void Verify_Hourly_Details_mr_Custom_Parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Verifying mr custom parameter of Hourly Details call");
		logStep("****** Verifying mr custom parameter of Hourly Details call");
			
		hmTab.clickonHomeTab();
		proxy.clearCharlesSession();
		TestBase.waitForMilliSeconds(2000);
		// navigate to Hourly tab
		hrTab.navigateToHourlyTab();
		Functions.archive_folder("Charles");
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "mr", "0");
		proxy.clearCharlesSession();
		//Functions.swipe_Down();
		
		//30 seconds hard wait to refresh the ad call
		 
		TestBase.waitForMilliSeconds(30000);
		Functions.archive_folder("Charles");
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "mr", "1");
		
	}*/
	
	/**
	 * This Script Enable preconfiguration for denying tracking of apps at device level
	 * @throws Exception    
	 */
	@Test(priority = 480, enabled = true)
	@Description("Enabling Preconfiguration for denying tracking of apps at device level")
	public void enable_PreConfiguration_for_denying_tracking_at_device_level() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Enable Preconfiguration for denying tracking of apps at device level");
		logStep("Enable Preconfiguration for denying tracking of apps at device level");
		Ad.terminateApp("com.weather.TWC");
		Functions.launchiOSSettings();
		Functions.modifyAppsTracking(false);
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(2000);
		Ad.launchApp();
		proxy.clearCharlesSession();
		Functions.close_launchApp();
						
		try {
			//Utils.navigateTofeedCard("weather.feed0", false, false);
			fOneCardScreen.scrollToFeedOneCard();
					
		} catch (Exception e) {
			System.out.println("There is an exception while navigting to feed1 card");
			logStep("There is an exception while navigting to feed1 card");
		} finally {
			proxy.getXml();
			Utils.createXMLFileForCharlesSessionFile();
		}
	}
	
	/**
	 * This method validates dnt custom parameter of Marquee call when tracking denied
	 */
	@Test(priority = 481, enabled = true)
	@Description("Validating 'dnt' custom parameter of Marquee call when tracking denied")
	public void Validate_Marquee_dnt_Custom_param_when_tracking_denied() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating dnt custom parameter of Marquee call when tracking denied");
		logStep("Validating dnt custom parameter of Marquee call when tracking denied");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "dnt", "y");

	}

	/**
	 * This method validates dnt custom parameter of Feed1 call  when tracking denied
	 */
	@Test(priority = 482, enabled = true)
	@Description("Validating 'dnt' custom parameter of Feed1 call when tracking denied")
	public void Validate_Feed1_dnt_Custom_param_when_tracking_denied() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating dnt custom parameter of Feed1 call when tracking denied");
		logStep("Validating dnt custom parameter of Feed1 call when tracking denied");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "dnt", "y");

	}

	/**
	 * This method validates attmas parameter of Marquee call when tracking denied
	 */
	@Test(priority = 483, enabled = true)
	@Description("Validating 'attmas' parameter of Marquee call when tracking denied")
	public void Validate_Marquee_call_attmas_param_when_tracking_denied() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating attmas parameter of Marquee call when tracking denied");
		logStep("Validating attmas parameter of Marquee call when tracking denied");
		Utils.validate_non_custom_param_val_in_gampad_url("Smoke", "Marquee", "attmas", "denied");

	}
	
	/**
	 * This method validates attmas parameter of Feed1 call when tracking denied
	 */
	@Test(priority = 484, enabled = true)
	@Description("Validating 'attmas' parameter of Feed1 call when tracking denied")
	public void Validate_Feed1_call_attmas_param_when_tracking_denied() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating attmas parameter of Feed1 call when tracking denied");
		logStep("Validating attmas parameter of Feed1 call when tracking denied");
		Utils.validate_non_custom_param_val_in_gampad_url("Smoke", "Feed1", "attmas", "denied");

	}
	
	/**
	 * This Script Enables tracking of apps at device level post validation of denying app tracking
	 * @throws Exception    
	 */
	@Test(priority = 485, enabled = true)
	@Description("Enabling tracking of apps at device level")
	public void enable_tracking_at_device_level() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Enable tracking of apps at device level");
		logStep("Enable tracking of apps at device level");
		Ad.terminateApp("com.weather.TWC");
		Functions.launchiOSSettings();
		Functions.modifyAppsTracking(true);
		
	}
	
	/**
	 * This method validate the 'mr' custom parameter of Map Details
	 * @throws Exception
	 */
	@Test(priority = 500, enabled = true)
	@Description("Verify 'mr' custom parameter of Map Details")
	public void Verify_Map_Details_mr_Custom_Parameter() throws Exception {
		System.out.println("==============================================");

		System.out.println("****** Verifying mr custom parameter of Map Details call");
		logStep("****** Verifying mr custom parameter of Map Details call");
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(2000);
		Ad.launchApp();
		Functions.close_launchApp();
		hmTab.clickonHomeTab();
		hmTab.clickonHomeTab();
		proxy.clearCharlesSession();
		
		// navigate to Radar tab
		rTab.navigateToRadarTab();
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.validate_custom_param_val_of_gampad("Smoke", "Map", "mr", "0");
		proxy.clearCharlesSession();
		System.out.println("****** 30 seconds hard wait to refresh the ad call");
		logStep("****** 30 seconds hard wait to refresh the ad call");
		TestBase.waitForMilliSeconds(30000);
		Functions.archive_folder("Charles");
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.validate_custom_param_val_of_gampad("Smoke", "Map", "mr", "1");
		hmTab.clickonHomeTab();
		/**
		 * Interaction refresh is disabled, hence commented below code
		 */
		/*proxy.clearCharlesSession();
		TestBase.waitForMilliSeconds(2000);
		// navigate to Radar tab
		Functions.archive_folder("Charles");
		rTab.navigateToRadarTab();
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.validate_custom_param_val_of_gampad("Smoke", "Map", "mr", "0");
		proxy.clearCharlesSession();
		//Functions.scroll_Up();
		Functions.swipe_Down();
		Functions.archive_folder("Charles");
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.validate_custom_param_val_of_gampad("Smoke", "Map", "mr", "1");*/
		
	}
	
	
	
	/**
	 * Enable Preconditions for WeatherFX API Parameters validation
	 */
	@Test(priority = 526, enabled = true)
	@Description("Verify Enable Preconditions For WeatherFX API Parameters")
	public void Verify_enable_preConditions_for_WeatherFx_API_Parameters() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Verify Enable Preconditions for WeatherFX API Parameters test started");
		logStep("Verify Enable Preconditions for WeatherFX API Parameters test started");
		Functions.archive_folder("Charles");
				
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.checkForAppState();
		/*addrScreen.enternewAddress(false, "30124", "Cave Spring, Georgia");
		TestBase.waitForMilliSeconds(20000);
		addrScreen.enternewAddress(false, "07095", "Woodbridge, New Jersey");
		TestBase.waitForMilliSeconds(20000);
		addrScreen.enternewAddress(false, "61920", "Charleston, Illinois");
		TestBase.waitForMilliSeconds(20000);
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.checkForAppState();
		TestBase.waitForMilliSeconds(2000);*/
		
		proxy.clearCharlesSession();
		
		addrScreen.enternewAddress(false, "30124", "Cave Spring, Georgia");
		TestBase.waitForMilliSeconds(20000);
		hmTab.clickonHomeTab();
		hmTab.clickonHomeTab();
		// navigate to Hourly tab
		hrTab.navigateToHourlyTab();
		TestBase.waitForMilliSeconds(2000);
		// navigate to Daily tab
		dTab.navigateToDailyTab();
		TestBase.waitForMilliSeconds(2000);
		// navigate to Radar tab
		rTab.navigateToRadarTab();
		TestBase.waitForMilliSeconds(2000);
		hmTab.clickonHomeTab();
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		// Utils.get_v3_wx_forecast_daily_15day_data();
		Utils.hlzip = Utils.get_custom_param_val_of_gampad("Smoke", "Hourly", "hlzip");
		System.out.println("hlzip value is: "+Utils.hlzip);
		logStep("hlzip value is: "+Utils.hlzip);
		

	}

	/**
	 * This method validates wfxtg custom parameter of Hourly details call
	 */
	
	  @Test(priority = 527, enabled = true)
	  
	  @Description("Validating 'wfxtg' custom parameter of Hourly details call ")
	  public void Validate_HourlyDetails_wfxtg_Custom_param() throws Exception {
			System.out.println("==============================================");
			System.out.println("****** Validating wfxtg custom parameter of Hourly details call");
			logStep("Validating wfxtg custom parameter of Hourly details call ");
			Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Hourly", "wfxtg", "30124");
	  
	  }
	 

	/**
	 * This method validates wfxtg custom parameter of Daily details call
	 */
	
	@Test(priority = 528, enabled = true)
	@Description("Validating 'wfxtg' custom parameter of Daily details call ")
	public void Validate_DailyDetails_wfxtg_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating wfxtg custom parameter of Daily details call");
		logStep("Validating wfxtg custom parameter of Daily details call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Daily(10day)", "wfxtg", "30124");

	}
	 

	/**
	 * This method validates wfxtg custom parameter of Map details call
	 */
	@Test(priority = 529, enabled = true)
	@Description("Validating 'wfxtg' custom parameter of Map details call ")
	public void Validate_MapDetails_wfxtg_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating wfxtg custom parameter of Map details call");
		logStep("Validating wfxtg custom parameter of Map details call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Map", "wfxtg", "30124");

	}

	/**
	 * This method validates cxtg custom parameter of Hourly details call
	 */
	
	@Test(priority = 530, enabled = true)
	@Description("Validating 'cxtg' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_cxtg_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating cxtg custom parameter of Hourly details call");
		logStep("Validating cxtg custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Hourly", "cxtg", "30124");

	}
	 

	/**
	 * This method validates cxtg custom parameter of Daily details call
	 */
	
	@Test(priority = 531, enabled = true)
	@Description("Validating 'cxtg' custom parameter of Daily details call ")
	public void Validate_DailyDetails_cxtg_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating cxtg custom parameter of Daily details call");
		logStep("Validating cxtg custom parameter of Daily details call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Daily(10day)", "cxtg", "30124");

	}
	 

	/**
	 * This method validates cxtg custom parameter of Map details call
	 */
	@Test(priority = 532, enabled = true)
	@Description("Validating 'cxtg' custom parameter of Map details call ")
	public void Validate_MapDetails_cxtg_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating cxtg custom parameter of Map details call");
		logStep("Validating cxtg custom parameter of Map details call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Map", "cxtg", "30124");

	}

	/**
	 * This method validates zcs custom parameter of Hourly details call
	 */
	
	@Test(priority = 533, enabled = true)
	@Description("Validating 'zcs' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_zcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating zcs custom parameter of Hourly details call");
		logStep("Validating zcs custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Hourly", "zcs", "30124");
	  
	  }
	 

	/**
	 * This method validates zcs custom parameter of Daily details call
	 */
	
	@Test(priority = 534, enabled = true)
	@Description("Validating 'zcs' custom parameter of Daily details call ")
	public void Validate_DailyDetails_zcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating zcs custom parameter of Daily details call");
		logStep("Validating zcs custom parameter of Daily details call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Daily(10day)", "zcs", "30124");

	}
	 

	/**
	 * This method validates zcs custom parameter of Map details call
	 */
	@Test(priority = 535, enabled = true)
	@Description("Validating 'zcs' custom parameter of Map details call ")
	public void Validate_MapDetails_zcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating zcs custom parameter of Map details call");
		logStep("Validating zcs custom parameter of Map details call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Map", "zcs", "30124");

	}

	/**
	 * This method validates hzcs custom parameter of Hourly details call
	 */
	
	@Test(priority = 536, enabled = true)
	@Description("Validating 'hzcs' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_hzcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating hzcs custom parameter of Hourly details call");
		logStep("Validating hzcs custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Hourly", "hzcs", "30124");

	}
	 

	/**
	 * This method validates hzcs custom parameter of Daily details call
	 */
	
	@Test(priority = 537, enabled = true)
	@Description("Validating 'hzcs' custom parameter of Daily details call ")
	public void Validate_DailyDetails_hzcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating hzcs custom parameter of Daily details call");
		logStep("Validating hzcs custom parameter of Daily details call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Daily(10day)", "hzcs", "30124");

	}
	 

	/**
	 * This method validates hzcs custom parameter of Map details call
	 */
	@Test(priority = 538, enabled = true)
	@Description("Validating 'hzcs' custom parameter of Map details call ")
	public void Validate_MapDetails_hzcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating hzcs custom parameter of Map details call");
		logStep("Validating hzcs custom parameter of Map details call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Map", "hzcs", "30124");

	}

	/**
	 * This method validates nzcs custom parameter of Hourly details call
	 */
	
	@Test(priority = 539, enabled = true)
	@Description("Validating 'nzcs' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_nzcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating nzcs custom parameter of Hourly details call");
		logStep("Validating nzcs custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Hourly", "nzcs", "30124");

	}
	 
	
	/**
	 * This method validates nzcs custom parameter of Daily details call
	 */
	
	@Test(priority = 540, enabled = true)
	@Description("Validating 'nzcs' custom parameter of Daily details call ")
	public void Validate_DailyDetails_nzcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating nzcs custom parameter of Daily details call");
		logStep("Validating nzcs custom parameter of Daily details call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Daily(10day)", "nzcs", "30124");

	}
	 

	/**
	 * This method validates nzcs custom parameter of Map details call
	 */
	@Test(priority = 541, enabled = true)
	@Description("Validating 'nzcs' custom parameter of Map details call ")
	public void Validate_MapDetails_nzcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating nzcs custom parameter of Map details call");
		logStep("Validating nzcs custom parameter of Map details call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Map", "nzcs", "30124");

	}

	/**
	 * Enable Preconditions for WeatherFX API Parameters validation from Planning
	 * Card
	 */
	@Test(priority = 575, enabled = true)
	@Description("Verify Enable Preconditions For WeatherFX API Parameters From Planning Card")
	public void Verify_enable_preConditions_for_WeatherFx_API_ParametersFromPlanningCard() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"****** Verify Enable Preconditions for WeatherFX API Parameters from planning card test started");
		logStep("Verify Enable Preconditions for WeatherFX API Parameters from planning card test started");
		Functions.archive_folder("Charles");
		Utils.wfxParameters.clear();
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.checkForAppState();
		
		proxy.clearCharlesSession();
		
		addrScreen.enternewAddress(false, "61920", "Charleston, Illinois");
		TestBase.waitForMilliSeconds(20000);
		// navigate to Planning Card
		 //Utils.navigateToPlanningCard();
		//Utils.navigateTofeedCard("planning-containerView");
		pScreen.scrollToPlanningCard();
		TestBase.waitForMilliSeconds(5000);
		// navigate to Hourly Details from Planning Card
		pScreen.navigateToHourlyDetailsFromPlanningCard();
		TestBase.waitForMilliSeconds(2000);
		navigateBackToFeedCard();
		TestBase.waitForMilliSeconds(2000);
		// navigate to Daily Details from Planning Card
		pScreen.navigateToDailyDetailsFromPlanningCard();
		TestBase.waitForMilliSeconds(2000);
		navigateBackToFeedCard();
		TestBase.waitForMilliSeconds(2000);
		// hmTab.clickonHomeTab();
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		//Utils.loadWeatherFXAPIParameterValuestoMap_ByZipCode("61920");
		// Utils.get_v3_wx_forecast_daily_15day_data();

	}

	/**
	 * This method validates wfxtg custom parameter of Hourly details banner ad call
	 */
	@Test(priority = 576, enabled = true)
	@Description("Validating 'wfxtg' custom parameter of Hourly details banner ad call ")
	public void Validate_HourlyDetails_Banner_Ad_wfxtg_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating wfxtg custom parameter of Hourly details banner ad call");
		logStep("Validating wfxtg custom parameter of Hourly details banner ad call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Hourly", "wfxtg", "61920");

	}

	/**
	 * This method validates wfxtg custom parameter of Daily details banner ad call
	 */
	@Test(priority = 577, enabled = true)
	@Description("Validating 'wfxtg' custom parameter of Daily details banner ad call ")
	public void Validate_DailyDetails_Banner_Ad_wfxtg_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating wfxtg custom parameter of Daily details banner ad call");
		logStep("Validating wfxtg custom parameter of Daily details banner ad call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Daily(10day)", "wfxtg", "61920");

	}

	/**
	 * This method validates cxtg custom parameter of Home Screen banner banner ad
	 * call
	 */
	@Test(priority = 578, enabled = true)
	@Description("Validating 'cxtg' custom parameter of Hourly details banner ad call ")
	public void Validate_HourlyDetails_Banner_Ad_cxtg_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating cxtg custom parameter of Hourly details banner ad call");
		logStep("Validating cxtg custom parameter of Hourly details banner ad call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Hourly", "cxtg", "61920");

	}

	/**
	 * This method validates cxtg custom parameter of Daily details banner ad call
	 */
	@Test(priority = 579, enabled = true)
	@Description("Validating 'cxtg' custom parameter of Daily details banner ad call ")
	public void Validate_DailyDetails_Banner_Ad_cxtg_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating cxtg custom parameter of Daily details banner ad call");
		logStep("Validating cxtg custom parameter of Daily details banner ad call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Daily(10day)", "cxtg", "61920");

	}

	/**
	 * This method validates zcs custom parameter of Hourly details banner ad call
	 */
	@Test(priority = 580, enabled = true)
	@Description("Validating 'zcs' custom parameter of Hourly details banner ad call ")
	public void Validate_HourlyDetails_Banner_Ad_zcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating zcs custom parameter of Hourly details banner ad call");
		logStep("Validating zcs custom parameter of Hourly details banner ad call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Hourly", "zcs", "61920");

	}

	/**
	 * This method validates zcs custom parameter of Daily details banner ad call
	 */
	@Test(priority = 581, enabled = true)
	@Description("Validating 'zcs' custom parameter of Daily details banner ad call ")
	public void Validate_DailyDetails_Banner_Ad_zcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating zcs custom parameter of Daily details banner ad call");
		logStep("Validating zcs custom parameter of Daily details banner ad call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Daily(10day)", "zcs", "61920");

	}

	/**
	 * This method validates hzcs custom parameter of Hourly details banner ad call
	 */
	@Test(priority = 582, enabled = true)
	@Description("Validating 'hzcs' custom parameter of Hourly details banner ad call ")
	public void Validate_HourlyDetails_Banner_Ad_hzcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating hzcs custom parameter of Hourly details banner ad call");
		logStep("Validating hzcs custom parameter of Hourly details banner ad call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Hourly", "hzcs", "61920");

	}

	/**
	 * This method validates hzcs custom parameter of Daily details banner ad call
	 */
	@Test(priority = 583, enabled = true)
	@Description("Validating 'hzcs' custom parameter of Daily details banner ad call ")
	public void Validate_DailyDetails_Banner_Ad_hzcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating hzcs custom parameter of Daily details banner ad call");
		logStep("Validating hzcs custom parameter of Daily details banner ad call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Daily(10day)", "hzcs", "61920");

	}

	/**
	 * This method validates nzcs custom parameter of Hourly details banner ad call
	 */
	@Test(priority = 584, enabled = true)
	@Description("Validating 'nzcs' custom parameter of Hourly details banner ad call ")
	public void Validate_HourlyDetails_Banner_Ad_nzcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating nzcs custom parameter of Hourly details banner ad call");
		logStep("Validating nzcs custom parameter of Hourly details banner ad call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Hourly", "nzcs", "61920");

	}

	/**
	 * This method validates nzcs custom parameter of Daily details banner ad call
	 */
	@Test(priority = 585, enabled = true)
	@Description("Validating 'nzcs' custom parameter of Daily details banner ad call ")
	public void Validate_DailyDetails_Banner_Ad_nzcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating nzcs custom parameter of Daily details banner ad call");
		logStep("Validating nzcs custom parameter of Daily details banner ad call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Daily(10day)", "nzcs", "61920");

	}

	/**
	 * Enable Preconditions for WeatherFX API Parameters of Homescreen banner ad
	 * validation from Planning Card
	 */

	/*
	 * @Test(priority = 586, enabled = true)
	 * 
	 * @Description("Verify Enable Preconditions For WeatherFX API Parameters of HomeScreen Banner Ad From Planning Card"
	 * ) public void
	 * Verify_enable_preConditions_for_WeatherFx_API_ParametersOfHomescreenbannerAdFromPlanningCard
	 * () throws Exception {
	 * System.out.println("==============================================");
	 * System.out.
	 * println("****** Verify Enable Preconditions for WeatherFX API Parameters of homescreen banner ad from planning card test started"
	 * );
	 * logStep("Verify Enable Preconditions for WeatherFX API Parameters of homescreen banner ad from planning card test started"
	 * ); proxy.clearCharlesSession(); TestBase.waitForMilliSeconds(2000);
	 * Utils.navigateTofeedCard("planning-containerView"); // navigate to Today
	 * Details from Planning Card navigateToTodayDetailsFromPlanningCard();
	 * TestBase.waitForMilliSeconds(2000); navigateBackToFeedCard(); TestBase.waitForMilliSeconds(2000);
	 * hmTab.clickonHomeTab(); Functions.scroll_Up(); Functions.scroll_Up();
	 * Functions.archive_folder("Charles"); TestBase.waitForMilliSeconds(5000); proxy.getXml();
	 * Utils.createXMLFileForCharlesSessionFile(); //
	 * Utils.get_v3_wx_forecast_daily_15day_data();
	 * 
	 * }
	 */

	/**
	 * This method validates wfxtg custom parameter of Home Screen banner ad call
	 */
	/*
	 * @Test(priority = 587, enabled = true)
	 * 
	 * @Description("Validating 'wfxtg' custom parameter of Home Screen banner ad i.e. Home Screen today call "
	 * ) public void Validate_HomeScreen_Banner_Ad_wfxtg_Custom_param() throws
	 * Exception {
	 * System.out.println("==============================================");
	 * System.out.
	 * println("****** Validating wfxtg custom parameter of Home Screen banner ad call"
	 * );
	 * logStep("Validating wfxtg custom parameter of Home Screen banner ad call ");
	 * Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Pulltorefresh",
	 * "wfxtg", "30124");
	 * 
	 * }
	 */

	/**
	 * This method validates cxtg custom parameter of Home Screen banner ad call
	 */
	/*
	 * @Test(priority = 588, enabled = true)
	 * 
	 * @Description("Validating 'cxtg' custom parameter of Home Screen banner ad i.e. Home Screen today call "
	 * ) public void Validate_HomeScreen_Banner_Ad_cxtg_Custom_param() throws
	 * Exception {
	 * System.out.println("==============================================");
	 * System.out.
	 * println("****** Validating cxtg custom parameter of Home Screen banner ad call"
	 * );
	 * logStep("Validating cxtg custom parameter of Home Screen banner ad call ");
	 * Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Pulltorefresh",
	 * "cxtg", "30124");
	 * 
	 * }
	 */

	/**
	 * This method validates zcs custom parameter of Home Screen banner ad call
	 */
	/*
	 * @Test(priority = 589, enabled = true)
	 * 
	 * @Description("Validating 'zcs' custom parameter of Home Screen banner ad i.e. Home Screen today call "
	 * ) public void Validate_HomeScreen_Banner_Ad_zcs_Custom_param() throws
	 * Exception {
	 * System.out.println("==============================================");
	 * System.out.
	 * println("****** Validating zcs custom parameter of Home Screen banner ad call"
	 * ); logStep("Validating zcs custom parameter of Home Screen banner ad call ");
	 * Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Pulltorefresh",
	 * "zcs", "30124");
	 * 
	 * }
	 */

	/**
	 * This method validates hzcs custom parameter of Home Screen banner ad call
	 */
	/*
	 * @Test(priority = 590, enabled = true)
	 * 
	 * @Description("Validating 'hzcs' custom parameter of Home Screen banner ad i.e. Home Screen today call "
	 * ) public void Validate_HomeScreen_Banner_Ad_hzcs_Custom_param() throws
	 * Exception {
	 * System.out.println("==============================================");
	 * System.out.
	 * println("****** Validating hzcs custom parameter of Home Screen banner ad call"
	 * );
	 * logStep("Validating hzcs custom parameter of Home Screen banner ad call ");
	 * Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Pulltorefresh",
	 * "hzcs", "30124");
	 * 
	 * }
	 */

	/**
	 * This method validates nzcs custom parameter of Home Screen banner ad call
	 */
	/*
	 * @Test(priority = 591, enabled = true)
	 * 
	 * @Description("Validating 'nzcs' custom parameter of Home Screen banner ad i.e. Home Screen today call "
	 * ) public void Validate_HomeScreen_Banner_Ad_nzcs_Custom_param() throws
	 * Exception {
	 * System.out.println("==============================================");
	 * System.out.
	 * println("****** Validating nzcs custom parameter of Home Screen banner ad call"
	 * );
	 * logStep("Validating nzcs custom parameter of Home Screen banner ad call ");
	 * Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Pulltorefresh",
	 * "nzcs", "30124");
	 * 
	 * }
	 */

	/**
	 * This method navigates to video module on navtab and gets video call iu value
	 */
	@Test(priority = 650, enabled = true)
	@Description("Verify Preroll ad on Video module")
	public void Verify_PrerollAd() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Preroll-video ad test case Started");
		logStep("****** Preroll-video ad test case Started");
		Functions.archive_folder("Charles");
		Utils.wfxParameters.clear();
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.checkForAppState();
		
		proxy.clearCharlesSession();
		
		// addrScreen.enternewAddress(false, "Atlanta, Georgia");
		addrScreen.enternewAddress(false, "07095", "Woodbridge, New Jersey");
		TestBase.waitForMilliSeconds(20000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.getPlaceIdFromAPICall("placeId");
		Utils.loadWeatherFXAPIParameterValuestoMap_ByZipCode("07095");
		Functions.archive_folder("Charles");
		//TestBase.waitForMilliSeconds(5000);
		hmTab.clickonHomeTab();
		hmTab.clickonHomeTab();
		proxy.clearCharlesSession();
		// TestBase.waitForMilliSeconds(5000);
		// navigate to Video tab
		vTab.navigateToVideoTab(true, proxy);
		TestBase.waitForMilliSeconds(10000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.get_iu_value_of_Feedcall("Smoke", "PreRollVideo");
		Utils.verifyPubadCal("Smoke", "PreRollVideo");
		//Utils.hlzip = Utils.get_custom_param_val_of_gampad("Smoke", "PreRollVideo", "hlzip");
	}
	
	/**
	 * This method validates ltv custom parameter of video call
	 */
	@Test(priority = 651, enabled = true)
	@Description("Validating 'ltv' custom parameter of video call ")
	public void Validate_PreRollVideo_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of Video call");
		logStep("Validating ltv custom parameter of Video call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "PreRollVideo", "ltv", "NotNull");

	}
	

	/**
	 * This method validates cmsid custom parameter of video call
	 */
	@Test(priority = 652, enabled = true)
	@Description("Validating 'cmsid' custom parameter of Video call ")
	public void Validate_PreRollVideo_cmsid_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating cmsid custom parameter of Video call");
		logStep("Validating cmsid custom parameter of Video call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "PreRollVideo", "cmsid", false);

	}

	/**
	 * This method validates ttid custom parameter of video call
	 */
	@Test(priority = 653, enabled = true)
	@Description("Validating 'ttid' custom parameter of Video call ")
	public void Validate_PreRollVideo_ttid_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ttid custom parameter of Video call");
		logStep("Validating ttid custom parameter of Video call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "PreRollVideo", "ttid", "NotNull");

	}

	/**
	 * This method validates vid custom parameter of video call
	 */
	@Test(priority = 654, enabled = true)
	@Description("Validating 'vid' custom parameter of Video call ")
	public void Validate_PreRollVideo_vid_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating vid custom parameter of Video call");
		logStep("Validating vid custom parameter of Video call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "PreRollVideo", "vid", false);

	}

	/**
	 * This method validates descritpion url of video call
	 */
	@Test(priority = 655, enabled = true)
	@Description("Validating 'description url' of Video call ")
	public void Validate_PreRollVideo_description_url_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating description url of Video call");
		logStep("Validating description url of Video call ");
		Utils.validate_Noncustom_param_val_of_gampad("Smoke", "PreRollVideo", "description_url", "NotNull");

	}
	
	/**
	 * This method validates ref custom parameter of video call
	 */
	@Test(priority = 656, enabled = true)
	@Description("Validating 'ref' custom parameter of Video call when navigated from nav tab ")
	public void Validate_PreRollVideo_ref_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ref custom parameter of Video call");
		logStep("Validating ref custom parameter of Video call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "PreRollVideo", "ref", "NotNull");

	}
	
	/**
	 * This method validates dnt custom parameter of PreRollVideo call
	 */
	@Test(priority = 657, enabled = true)
	@Description("Validating 'dnt' custom parameter of PreRollVideo call ")
	public void Validate_PreRollVideo_dnt_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating dnt custom parameter of PreRollVideo call");
		logStep("Validating dnt custom parameter of PreRollVideo call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "PreRollVideo", "dnt", "n");

	}

	/**
	 * This method validates wfxtg custom parameter of video call
	 */
	@Test(priority = 660, enabled = true)
	@Description("Validating 'wfxtg' custom parameter of video call ")
	public void Validate_PreRollVideo_wfxtg_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating wfxtg custom parameter of video call");
		logStep("Validating wfxtg custom parameter of video call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "PreRollVideo", "wfxtg", "07095");

	}

	/**
	 * This method validates cxtg custom parameter of video call
	 */
	@Test(priority = 661, enabled = true)
	@Description("Validating 'cxtg' custom parameter of video call ")
	public void Validate_PreRollVideo_cxtg_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating cxtg custom parameter of video call");
		logStep("Validating cxtg custom parameter of video call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "PreRollVideo", "cxtg", "07095");

	}

	/**
	 * This method validates zcs custom parameter of video call
	 */
	@Test(priority = 662, enabled = true)
	@Description("Validating 'zcs' custom parameter of video call ")
	public void Validate_PreRollVideo_zcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating zcs custom parameter of video call");
		logStep("Validating zcs custom parameter of video call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "PreRollVideo", "zcs", "07095");

	}

	/**
	 * This method validates hzcs custom parameter of video call
	 */
	@Test(priority = 663, enabled = true)
	@Description("Validating 'hzcs' custom parameter of video call ")
	public void Validate_PreRollVideo_hzcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating hzcs custom parameter of video call");
		logStep("Validating hzcs custom parameter of video call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "PreRollVideo", "hzcs", "07095");

	}

	/**
	 * This method validates nzcs custom parameter of video call
	 */
	@Test(priority = 664, enabled = true)
	@Description("Validating 'nzcs' custom parameter of video call ")
	public void Validate_PreRollVideo_nzcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating nzcs custom parameter of video call");
		logStep("Validating nzcs custom parameter of video call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "PreRollVideo", "nzcs", "07095");
		Utils.wfxParameters.clear();

	}

	/**
	 * This method validates Google Interactive Media Ads SDK version i.e. IMA SDK
	 */
	@Test(priority = 675, enabled = true)
	@Description("Validating Google Interactive Media Ads SDK version of Preroll video call ")
	public void Validate_IMA_SDK_version() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"****** Validating Google Interactive Media Ads SDK version i.e. 'js' parameter of Preroll video call");
		logStep("Validating Google Interactive Media Ads SDK version i.e. 'js' parameter of Preroll video call");

		Utils.validate_Noncustom_param_val_of_gampad("Smoke", "PreRollVideo", "js",
				properties.getProperty("IMASDKVersion"));

	}
	
	/**
	 * This method validates content_url parameter of Video Call
	 */
	@Test(priority = 680, enabled = true)
	@Description("Validating 'content_url' parameter of Video Call")
	public void validate_PreRollVideo_Call_content_url_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating content_url parameter of PreRollVideo call");
		logStep("Validating content_url parameter of PreRollVideo call");
		Utils.validate_non_custom_param_val_in_gampad_url("Smoke", "PreRollVideo", "content_url", "https://www.weather.com");
	}

	/**
	 * This method validates Hourly Details BigAd1 ad call
	 */
	@Test(priority = 701, enabled = true)
	@Description("Verify Hourly Details BigAd1 Ad call")
	public void Verify_Hourly_Details_BigAd1_Adcall() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Hourly Details Big Ad1 call verification test case Started");
		logStep("****** Hourly Details Big Ad1 call verification test case Started");
		Functions.close_launchApp();
		proxy.clearCharlesSession();
		addrScreen.enternewAddress(false, "Atlanta, Georgia");
		TestBase.waitForMilliSeconds(20000);
		Functions.archive_folder("Charles");
		//hrTab.navigateToHourlyTab();
		hrTab.navigateToHourlyTabAndHandleInterstitialAd();
		Functions.swipe_Up_ByIterations(Ad, 10);
		attachScreen();
		TestBase.waitForMilliSeconds(5000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.getPlaceIdFromAPICall("placeId");
		Utils.verifyPubadCal("Smoke", "Hourly1");
	}

	/**
	 * This method validates Hourly Details BigAd2 ad call
	 */
	@Test(priority = 702, enabled = true)
	@Description("Verify Hourly Details BigAd2 Ad call")
	public void Verify_Hourly_Details_BigAd2_Adcall() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Hourly Details Big Ad2 call verification test case Started");
		logStep("****** Hourly Details Big Ad2 call verification test case Started");
		Utils.verifyPubadCal("Smoke", "Hourly2");
	}

	/**
	 * This method validates Hourly Details BigAd3 ad call
	 */
	@Test(priority = 703, enabled = true)
	@Description("Verify Hourly Details BigAd3 Ad call")
	public void Verify_Hourly_Details_BigAd3_Adcall() throws Exception {
		System.out.println("==============================================");

		System.out.println("****** Hourly Details Big Ad3 call verification test case Started");
		logStep("****** Hourly Details Big Ad3 call verification test case Started");
		Utils.verifyPubadCal("Smoke", "Hourly3");
	}
	
	/**
	 * This method validates content_url parameter of Hourly Details BigAd1 Call
	 */
	@Test(priority = 704, enabled = true)
	@Description("Validating 'content_url' parameter of Hourly Details BigAd1 Call")
	public void validate_Hourly_Details_BigAd1_Call_content_url_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating content_url parameter of Hourly Details BigAd1 call");
		logStep("Validating content_url parameter of Hourly Details BigAd1 call");
		Utils.validate_non_custom_param_val_in_gampad_url("Smoke", "Hourly1", "content_url", "https://weather.com/weather/hourbyhour/l/"+Utils.placeId);
	}
	
	/**
	 * This method validates content_url parameter of Hourly Details BigAd2 Call
	 */
	@Test(priority = 705, enabled = true)
	@Description("Validating 'content_url' parameter of Hourly Details BigAd2 Call")
	public void validate_Hourly_Details_BigAd2_Call_content_url_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating content_url parameter of Hourly Details BigAd2 call");
		logStep("Validating content_url parameter of Hourly Details BigAd2 call");
		Utils.validate_non_custom_param_val_in_gampad_url("Smoke", "Hourly2", "content_url", "https://weather.com/weather/hourbyhour/l/"+Utils.placeId);
	}
	
	/**
	 * This method validates content_url parameter of Hourly Details BigAd3 Call
	 */
	@Test(priority = 706, enabled = true)
	@Description("Validating 'content_url' parameter of Hourly Details BigAd3 Call")
	public void validate_Hourly_Details_BigAd3_Call_content_url_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating content_url parameter of Hourly Details BigAd3 call");
		logStep("Validating content_url parameter of Hourly Details BigAd3 call");
		Utils.validate_non_custom_param_val_in_gampad_url("Smoke", "Hourly3", "content_url", "https://weather.com/weather/hourbyhour/l/"+Utils.placeId);
	}
	
	/**
	 * This method validates ltv custom parameter of Hourly Details BigAd1 Call
	 */
	@Test(priority = 710, enabled = true)
	@Description("Validating 'ltv' custom parameter of Hourly Details BigAd1 Call ")
	public void Validate_Hourly_Details_BigAd1_Call_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of Hourly Details BigAd1 Call");
		logStep("Validating ltv custom parameter of Hourly Details BigAd1 Call");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly1", "ltv", "NotNull");

	}
	
	/**
	 * This method validates ltv custom parameter of Hourly Details BigAd2 Call
	 */
	@Test(priority = 711, enabled = true)
	@Description("Validating 'ltv' custom parameter of Hourly Details BigAd2 Call ")
	public void Validate_Hourly_Details_BigAd2_Call_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of Hourly Details BigAd2 Call");
		logStep("Validating ltv custom parameter of Hourly Details BigAd2 Call");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly2", "ltv", "NotNull");

	}
	
	/**
	 * This method validates ltv custom parameter of Hourly Details BigAd3 Call
	 */
	@Test(priority = 712, enabled = true)
	@Description("Validating 'ltv' custom parameter of Hourly Details BigAd3 Call ")
	public void Validate_Hourly_Details_BigAd3_Call_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of Hourly Details BigAd3 Call");
		logStep("Validating ltv custom parameter of Hourly Details BigAd3 Call");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly3", "ltv", "NotNull");

	}
	
	/**
	 * Kill and Launch the app for API Calls verification
	 */
	@Test(priority = 800, enabled = true)
	@Description("Kill and Launch the app for API calls verification")
	public void kill_and_Launch_app_for_api_calls() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Kill and Launch the app for API calls verification");
		logStep("Kill and Launch the app for API calls verification");
		TestBase.waitForMilliSeconds(5000);
		stScreen.select_Airlock_Branch("Clear");
		stScreen.select_Airlock_UserGroup("Clear");
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.checkForAppState();
		Functions.put_Background_launch(15);
		Functions.checkForAppState();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		addrScreen.clearAddedAddresses();
		TestBase.waitForMilliSeconds(5000);
		addrScreen.enternewAddress(false, "07095", "Woodbridge, New Jersey");
		TestBase.waitForMilliSeconds(20000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();

	}

	/**
	 * This method validates lotame call
	 */
	@Test(priority = 801, enabled = true)
	@Description("Lotame Call verification on Background Launch")
	public void Verify_Lotame_Call_On_BG_Launch() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** bcp.crwdcntrl.net Call  on Background Launch test case Started");
		logStep("****** bcp.crwdcntrl.net Call  on Background Launch test case Started");
		Utils.verifyAPICal("Smoke", "Lotame");

	}
	
	/**
	 * This method validates Turbo Api call i.e. api.weather.com call
	 */
	@Test(priority = 802, enabled = true)
	@Description("Turbo API Call verification on Kill and Launch")
	public void Verify_TurboAPI_Call_On_KillAndLaunch() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** api.weather.com Call On Kill and Launch test case Started");
		logStep("****** api.weather.com Call On Kill and Launch test case Started");
		Utils.verifyAPICal("Smoke", "TurboApi");

	}

	/**
	 * This method validates WFXTriggers call
	 */
	@Test(priority = 803, enabled = true)
	@Description("WFX Trigger Call verification On Kill and Launch")
	public void Verify_WFXTriggers_Call_On_KillAndLaunch() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** prod.weatherfx.com Call On Kill and Launch test case Started");
		logStep("****** prod.weatherfx.com Call On Kill and Launch test case Started");
		Utils.verifyAPICal("Smoke", "WFXTrigger");

	}

	/**
	 * Factual call is blocked, hence expected to not present this call in charles
	 * session from 12.6 builds onwards...
	 */
	@Test(priority = 804, enabled = true)
	@Description("Factual Call verification on Kill and Launch")
	public void Verify_LocationWFXTriggers_Call_On_KillAndLaunch() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** location.wfxtriggers.com Call on Kill and Launch test case Started");
		logStep("location.wfxtriggers.com Call on Kill and Launch test case Started");
		Utils.verifyAPICal("Smoke", "LocationWFX", false);

	}

	/**
	 * This method validates Amazon SDK version
	 */
	@Test(priority = 825, enabled = true)
	@Description("Validating 'adsdk' parameter of Amazon aax call ")
	public void Validate_Amazon_SDK_adsdk_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Amazon SDK version i.e. 'adsdk' parameter of Amazon aax call");
		logStep("****** Validating Amazon SDK version i.e. 'adsdk' parameter of Amazon aax call");
		Utils.validate_Amazon_aax_call_parameter("Smoke", "Amazon", "adsdk",
				properties.getProperty("AmazonSDKVersion"));

	}

	/**
	 * This method validates Google Mobile Ads SDK Version i.e. GMA SDK
	 */
	@Test(priority = 826, enabled = true)
	@Description("Validating Google Mobile Ads SDK version of gampad call ")
	public void Validate_GMA_SDK_version() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Google Mobile Ads SDK Version i.e. 'js' parameter of gampad call");
		logStep("Validating Google Mobile Ads SDK Version i.e. 'js' parameter of gampad call");
		Utils.validate_Noncustom_param_val_of_gampad("Smoke", "Marquee", "js", properties.getProperty("GMASDKVersion"));

	}

	/**
	 * This method validates Criteo SDK Version
	 */
	@Test(priority = 827, enabled = true)
	@Description("Validating 'sdkVersion' parameter of Criteo SDK config app call ")
	public void Validate_Criteo_SDK_config_app_Call_sdkVersion_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating 'sdkVersion' parameter of Criteo SDK config app call");
		logStep("****** Validating 'sdkVersion' parameter of Criteo SDK config app call");
		Utils.validate_Criteo_SDK_config_app_call_parameter("Smoke", "Criteo", "sdkVersion",
				properties.getProperty("CriteoSDKVersion"));
	}

	/**
	 * This method validates Criteo Bidder API (invapp v2) call response code
	 */
	@Test(priority = 851, enabled = true)
	@Description("Validating Criteo Bidder API (invapp v2) call response code")
	public void Validate_Criteo_SDK_Bidder_API_Call_Response_Code() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Criteo Bidder API (invapp v2) Call Response Code");
		logStep("****** Validating Criteo Bidder API (invapp v2) Call Response Code");
		Utils.verifyCriteo_inapp_v2_Call_ReponseStatusCode("Smoke", "Criteo", "status",
				properties.getProperty("CriteoResponseCode"));
	}

	/**
	 * This method validates Initialization API (config app) call response code
	 */
	@Test(priority = 852, enabled = true)
	@Description("Validating Criteo Initialization API (config app) call response code")
	public void Validate_Criteo_SDK_Initialization_API_Call_Response_Code() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Criteo Initialization API (config app) Call Response Code");
		logStep("****** Validating Criteo Initialization API (config app) Call Response Code");
		Utils.verifyCriteo_config_app_Call_ReponseStatusCode("Smoke", "Criteo", "status",
				properties.getProperty("CriteoResponseCode"));
	}

	/**
	 * This method validates Initialization API (config app) call response parameter
	 * 'csmEnabled'
	 */
	@Test(priority = 853, enabled = true)
	@Description("Validating Criteo Initialization API (config app) call response parameter 'csmEnabled' value")
	public void Validate_Criteo_SDK_Initialization_API_Call_Response_Parameter_csmEnabled() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"****** Validating csmEnabled parameter value in Criteo Initialization API (config app) Call Response");
		logStep("****** Validating csmEnabled parameter value in Criteo Initialization API (config app) Call Response");
		Utils.validate_Criteo_SDK_config_app_call_response_parameter("Smoke", "Criteo", "csmEnabled",
				properties.getProperty("csmEnabled"));
	}

	/**
	 * This method validates Initialization API (config app) call response parameter
	 * 'liveBiddingEnabled'
	 */
	@Test(priority = 854, enabled = true)
	@Description("Validating Criteo Initialization API (config app) call response parameter 'liveBiddingEnabled' value")
	public void Validate_Criteo_SDK_Initialization_API_Call_Response_Parameter_liveBiddingEnabled() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"****** Validating liveBiddingEnabled parameter value in Criteo Initialization API (config app) Call Response");
		logStep("****** Validating liveBiddingEnabled parameter value in Criteo Initialization API (config app) Call Response");
		Utils.validate_Criteo_SDK_config_app_call_response_parameter("Smoke", "Criteo", "liveBiddingEnabled",
				properties.getProperty("liveBiddingEnabled"));
	}

	/**
	 * This method validates Initialization API (config app) call response parameter
	 * 'liveBiddingTimeBudgetInMillis'
	 */ 
	 /* @Test(priority = 855, enabled = true)
	 *  @Description("Validating Criteo Initialization API (config app) call response parameter 'liveBiddingTimeBudgetInMillis' value"
	 * ) public void
	 * Validate_Criteo_SDK_Initialization_API_Call_Response_Parameter_liveBiddingTimeBudgetInMillis
	 * () throws Exception {
	 * System.out.println("==============================================");
	 * System.out.
	 * println("****** Validating liveBiddingTimeBudgetInMillis parameter value in Criteo Initialization API (config app) Call Response"
	 * );
	 * logStep("****** Validating liveBiddingTimeBudgetInMillis parameter value in Criteo Initialization API (config app) Call Response"
	 * ); Utils.validate_Criteo_SDK_config_app_call_response_parameter("Smoke",
	 * "Criteo", "liveBiddingTimeBudgetInMillis",
	 * properties.getProperty("liveBiddingTimeBudgetInMillis")); }
	 */

	/**
	 * This method validates Initialization API (config app) call response parameter
	 * 'prefetchOnInitEnabled'
	 */
	@Test(priority = 856, enabled = true)
	@Description("Validating Criteo Initialization API (config app) call response parameter 'prefetchOnInitEnabled' value")
	public void Validate_Criteo_SDK_Initialization_API_Call_Response_Parameter_prefetchOnInitEnabled()
			throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"****** Validating prefetchOnInitEnabled parameter value in Criteo Initialization API (config app) Call Response");
		logStep("****** Validating prefetchOnInitEnabled parameter value in Criteo Initialization API (config app) Call Response");
		Utils.validate_Criteo_SDK_config_app_call_response_parameter("Smoke", "Criteo", "prefetchOnInitEnabled",
				properties.getProperty("prefetchOnInitEnabled"));
	}

	/**
	 * This method validates Initialization API (config app) call response parameter
	 * 'remoteLogLevel'
	 */
	@Test(priority = 857, enabled = true)
	@Description("Validating Criteo Initialization API (config app) call response parameter 'remoteLogLevel' value")
	public void Validate_Criteo_SDK_Initialization_API_Call_Response_Parameter_remoteLogLevel() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"****** Validating remoteLogLevel parameter value in Criteo Initialization API (config app) Call Response");
		logStep("****** Validating remoteLogLevel parameter value in Criteo Initialization API (config app) Call Response");
		Utils.validate_Criteo_SDK_config_app_call_response_parameter("Smoke", "Criteo", "remoteLogLevel",
				properties.getProperty("remoteLogLevel"));
	}
	
	/*@Test(priority = 875, enabled = true)
	@Description("Verify Confiant SDK Calls")
	public void Verify_Confiant_SDK_Calls() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Confiant SDK Calls Validation test started");
		logStep("Confiant SDK Calls Validation test started");
		//Functions.archive_folder("Charles");
		//TestBase.waitForMilliSeconds(5000);
		//stScreen.select_Airlock_Branch("Clear");
		//stScreen.select_Airlock_UserGroup("confiant");
		//proxy.clearCharlesSession();
		//Functions.close_launchApp();
		//proxy.getXml();
		//Utils.createXMLFileForCharlesSessionFile();
		Utils.verifyConfiantSDKInitializationCalls("Smoke", "Confiant");
	}*/
	
	/**
	 * This method validates Dynata call
	 */
	@Test(priority = 901, enabled = true)
	@Description("Dynata Call verification on Kill and  Launch")
	public void Verify_Dynata_Pixel_Call_On_KillAndLaunch() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** tag.researchnow.com Call  on Kill and Launch test case Started");
		logStep("****** tag.researchnow.com  on Kill and Launch test case Started");
		Utils.verifyAPICal("Smoke", "Dynata");

	}
	
	/**
	 * This method validates Dynata call 'pr' parameter
	 */
	@Test(priority = 902, enabled = true)
	@Description("Dynata Call 'pr' parameter verification on Kill and  Launch")
	public void Verify_Dynata_Call_pr_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** tag.researchnow.com Call pr parameter validation test case Started");
		logStep("****** tag.researchnow.com Call pr parameter validation test case Started");
		Utils.validate_param_val_of_Dynata_Call("Smoke", "Dynata", "pr", "287554");

	}
	
	/**
	 * This method validates Dynata call 'adn' parameter
	 */
	@Test(priority = 903, enabled = true)
	@Description("Dynata Call 'adn' parameter verification on Kill and  Launch")
	public void Verify_Dynata_Call_adn_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** tag.researchnow.com Call adn parameter validation test case Started");
		logStep("****** tag.researchnow.com Call adn parameter validation test case Started");
		Utils.validate_param_val_of_Dynata_Call("Smoke", "Dynata", "adn", "128");

	}
	
	/**
	 * This method validates Dynata call 'us_privacy' parameter
	 */
	@Test(priority = 904, enabled = true)
	@Description("Dynata Call 'us_privacy' parameter verification on Kill and  Launch")
	public void Verify_Dynata_Call_us_privacy_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** tag.researchnow.com Call us_privacy parameter validation test case Started");
		logStep("****** tag.researchnow.com Call us_privacy parameter validation test case Started");
		Utils.validate_param_val_of_Dynata_Call("Smoke", "Dynata", "us_privacy", "usa");

	}
	
	/**
	 * This method validates Dynata call 'ca' parameter
	 */
	@Test(priority = 905, enabled = true)
	@Description("Dynata Call 'ca' parameter verification on Kill and  Launch")
	public void Verify_Dynata_Call_ca_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** tag.researchnow.com Call ca parameter validation test case Started");
		logStep("****** tag.researchnow.com Call ca parameter validation test case Started");
		Utils.validate_param_val_of_Dynata_Call("Smoke", "Dynata", "ca", "NotNull");

	}
	
	/**
	 * This method validates Dynata call 'ca' parameter
	 */
	@Test(priority = 906, enabled = true)
	@Description("Dynata Call 'si' parameter verification on Kill and  Launch")
	public void Verify_Dynata_Call_si_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** tag.researchnow.com Call si parameter validation test case Started");
		logStep("****** tag.researchnow.com Call si parameter validation test case Started");
		Utils.validate_param_val_of_Dynata_Call("Smoke", "Dynata", "si", "NotNull");

	}
	
	/**
	 * This method validates Dynata call 'pl' parameter
	 */
	@Test(priority = 907, enabled = true)
	@Description("Dynata Call 'pl' parameter verification on Kill and  Launch")
	public void Verify_Dynata_Call_pl_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** tag.researchnow.com Call pl parameter validation test case Started");
		logStep("****** tag.researchnow.com Call pl parameter validation test case Started");
		Utils.validate_param_val_of_Dynata_Call("Smoke", "Dynata", "pl", "NotNull");

	}
	
	/**
	 * This method validates Dynata call 'cr' parameter
	 */
	@Test(priority = 908, enabled = true)
	@Description("Dynata Call 'cr' parameter verification on Kill and  Launch")
	public void Verify_Dynata_Call_cr_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** tag.researchnow.com Call cr parameter validation test case Started");
		logStep("****** tag.researchnow.com Call cr parameter validation test case Started");
		Utils.validate_param_val_of_Dynata_Call("Smoke", "Dynata", "cr", "NotNull");

	}
	
	/**
	 * This method validates Dynata call 'did' parameter
	 */
	@Test(priority = 909, enabled = true)
	@Description("Dynata Call 'did' parameter verification on Kill and  Launch")
	public void Verify_Dynata_Call_did_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** tag.researchnow.com Call did parameter validation test case Started");
		logStep("****** tag.researchnow.com Call did parameter validation test case Started");
		Utils.validate_param_val_of_Dynata_Call("Smoke", "Dynata", "did", "NotNull");

	}
	
	/**
	 * This method validates Dynata call 'eid' parameter
	 */
	@Test(priority = 910, enabled = true)
	@Description("Dynata Call 'eid' parameter verification on Kill and  Launch")
	public void Verify_Dynata_Call_eid_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** tag.researchnow.com Call eid parameter validation test case Started");
		logStep("****** tag.researchnow.com Call eid parameter validation test case Started");
		Utils.validate_param_val_of_Dynata_Call("Smoke", "Dynata", "eid", "NotNull");

	}
	
	/**
	 * This method validates Dynata call 'ord' parameter
	 */
	@Test(priority = 911, enabled = true)
	@Description("Dynata Call 'ord' parameter verification on Kill and  Launch")
	public void Verify_Dynata_Call_ord_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** tag.researchnow.com Call ord parameter validation test case Started");
		logStep("****** tag.researchnow.com Call ord parameter validation test case Started");
		Utils.validate_param_val_of_Dynata_Call("Smoke", "Dynata", "ord", "NotNull");

	}
	
	

	/**
	 * This method validates Seasonal Hub details page ad call
	 */
/*Since Ads are disabled on Seasonal hub card, now navigating to all indexes and validating for non presence of gampad call in the above, to reduce execution time
 * @Test(priority = 900, enabled = true)
	@Description("Verify Seasonal Hub details page iu")
	public void Verify_Seasonal_Hub_Details_Page_AdCall() throws Exception {
		System.out.println("==============================================");
		// Functions.retryclear();
		System.out.println("****** Seasonal Hub Details page validation test cases Started");
		logStep("****** Seasonal Hub Details page validation test cases Started");
		Functions.archive_folder("Charles");
		addrScreen.enternewAddress(false, "Atlanta, Georgia");
		TestBase.waitForMilliSeconds(20000);
		Utils.navigateTofeedCard("seasonalhub");
		//sScreen.verifyPubadCal_SeasonalHub("Smoke", "SeasonalHub(Details)");
		sScreen.verifyPubadCal_SeasonalHub("Smoke", "SeasonalHub(Details)", false);
		hmTab.clickonHomeTab();
	}*/
		
	@Test(priority = 1000, enabled = true)
	@Description("Launch the app from widget")
	public void launchAppFromWidget() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Launch the App from widget test case Started");
		logStep("****** Launch the App from widget test case Started");
		Utils.getiOSVersion();
		if (Utils.iOSVersion.contains("16.")) {
			System.out.println("Since iOS version >= 16, skipping the app launching from iOS widget and par parameter validation");
			logStep("Since iOS version >= 16, skipping the app launching from iOS widget and par parameter validation");
			
		} else {
			Ad.terminateApp("com.weather.TWC");
			System.out.println("App closed successfully");
			logStep("App closed successfully");
			proxy.clearCharlesSession();
			Functions.launchAppFromTheWidgetApp();
			System.out.println("App launched From Widget");
			logStep("App launched From Widget");
			Functions.archive_folder("Charles");
			proxy.getXml();
			Utils.createXMLFileForCharlesSessionFile();
			Ad1.terminateApp("com.apple.weather");
			Ad1.terminateApp("com.weather.TWC");
			Ad1.quit();
			
			System.out.println("==============================================");
			System.out.println("****** Validating par custom parameter of Marquee call when app launched from widget");
			logStep("Validating par custom parameter of Marquee call ");
			Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "par", "apple_widget");
		}
		
		
	}
	
	/**
	 * This method validates par custom parameter of Marquee call when app launched from widget
	 */
/*	@Test(priority = 1001, enabled = true)
	@Description("Validating 'par' custom parameter of Marquee call when app launched from widget")
	public void Validate_Marquee_Call_par_Custom_param_when_app_launched_from_widget() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating par custom parameter of Marquee call when app launched from widget");
		logStep("Validating par custom parameter of Marquee call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "par", "apple_widget");

	}*/
	
	

}

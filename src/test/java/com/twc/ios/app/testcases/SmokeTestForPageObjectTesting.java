package com.twc.ios.app.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import java.io.File;

import org.testng.annotations.Listeners;

import org.testng.annotations.BeforeClass;

import io.appium.java_client.MobileElement;

import io.qameta.allure.Description;

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

@Listeners(value = com.twc.ios.app.general.MyTestListenerAdapter.class)
public class SmokeTestForPageObjectTesting extends TwcIosBaseTest {

	private static final String CONFIG_FILE_PATH = "charles_common.config";
	
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

	@BeforeClass(alwaysRun = true)
	@Description("BeforeClass")
	public void beforeClass() {
		System.out.println("****** Smoke Test Started");
		logStep("****** Smoke Test Started");
		this.configFile = this.charlesGeneralConfigFile(CONFIG_FILE_PATH);
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
		// Functions.close_launchApp();
		// Functions.checkForAppState();
		stScreen.getAppVersion();
		Functions.archive_folder("Charles");
		proxy.disableRewriting();
		proxy.quitCharlesProxy();
		try {
			Ad.closeApp();
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
		proxy.startRecording();
		proxy.clearCharlesSession();
		Functions.archive_folder("Charles");
		Functions.launchtheApp("true");
		System.out.println("App launched ");
		logStep("App launched ");
		Functions.archive_folder("Charles");
		proxy.getXml();
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
	}

	/*
	 * @Test(priority = 9999) public void afterTest() throws Exception {
	 * System.out.println("==============================================");
	 * System.out.println("****** After Test Started"); Utils.getAppVersion();
	 * Functions.Setappinto_TestMode("UnSelect"); Functions.clear_session();
	 * Functions.archive_folder("Charles"); driver.quit(); //Ad.closeApp();
	 * Ad.quit(); System.out.println("App closed successfully");
	 * 
	 * }
	 */

	/**
	 * Verify Feed Calls on FTL
	 * 
	 * @throws Exception This case validates the ad calls of Home Screen Today,and
	 *                   new Feed ad Cards
	 */
	@Test(priority = 100, enabled = true)
	@Description("Feed Calls Validation on FTL")
	public void Verify_Feed_Calls_On_FTL() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Feed Calls Validation on FTL test started");
		logStep("Feed Calls Validation on FTL test started");
		proxy.clearCharlesSession();
				
		addrScreen.enternewAddress(false, "Atlanta, Georgia");
		TestBase.waitForMilliSeconds(10000);
		try {
			Utils.navigateToAllCards(true, false);

		} catch (Exception e) {
			System.out.println("There is an exception while navigting to all the feed cards.");
			logStep("There is an exception while navigting to all the feed cards.");
			e.printStackTrace();
		} finally {
			Functions.archive_folder("Charles");
			TestBase.waitForMilliSeconds(5000);
			proxy.getXml();
			Utils.createXMLFileForCharlesSessionFile();
			ParseForVideoOrderedList.getVideoCall_IU_Value_from_adZone();
			Utils.get_v3_wx_forecast_daily_15day_data();
			Utils.Verify_newfeedAdcalls("Smoke", "CleanLaunch");

		}
	}

	/*
	 * //disabling this as Daily Card integrated forecast ad been removed as part of
	 * 12.5 build
	 * 
	 * @Test(priority = 101, enabled = true)
	 * 
	 * @Description("Verify Daily card iu On FTL") public void
	 * Verify_DailyCard_AdCall_On_FTL() throws Exception {
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

	@Test(priority = 102, enabled = true)
	@Description("Verify NextGen IM ad call iu On FTL")
	public void Verify_NextGen_IM_AdCall_On_FTL() throws Exception {
		System.out.println("==============================================");
		// Functions.retryclear();

		System.out.println("****** NextGen IM Adcall verification On FTL test case Started");
		logStep("****** NextGen IM Adcall verification On FTL test case Started");
		Utils.verifyPubadCal("Smoke", "Marquee");
	}

	@Test(priority = 103, enabled = true)
	@Description("Verify Hourly details page iu on FTL")
	public void Verify_HourlyDetails_AdCall_On_FTL() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================Hourly details iu====================");

		System.out.println("****** Hourly details iu validation Started");
		logStep("****** Hourly details iu validation Started");
		Utils.verifyPubadCal("Smoke", "Hourly");

	}

	@Test(priority = 104, enabled = true)
	@Description("Verify Today's Detailed/Trending page iu on FTL")
	public void Verify_TrendingPage_AdCall_On_FTL() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"===========================Verify Today's Detailed/Trending page Adcal iu====================");

		System.out.println("****** Today's Detailed/Trending page Ad cal iu validation Started ");
		logStep("****** Today's Detailed/Trending page Ad cal iu validation Started ");
		Utils.verifyPubadCal("Smoke", "Today");
	}

	@Test(priority = 105, enabled = true)
	@Description("Verify Air Quality details page iu on FTL")
	public void Verify_Air_Quality_Details_AdCall_On_FTL() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================Air Quality Details page Adcal iu====================");

		System.out.println("****** Air Quality Details page test cases Started");
		logStep("****** Air Quality Details page test cases Started");
		Utils.verifyPubadCal("Smoke", "Air Quality(Content)");
	}

	@Test(priority = 106, enabled = true)
	@Description("Verify Maps detail page iu on FTL")
	public void Verify_MapsDetails_AdCall_On_FTL() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================Verify  Maps detail page iu====================");

		System.out.println("****** Maps detail page Ad cal iu validation Started");
		logStep("****** Maps detail page Ad cal iu validation Started");
		Utils.verifyPubadCal("Smoke", "Map");

	}

	/*
	 * @Test(priority = 107, enabled = true)
	 * 
	 * @Title("Verify News Details page iu on FTL") public void
	 * Verify_NewsDetails_AdCall_On_FTL() throws Exception {
	 * System.out.println("==============================================");
	 * System.out.
	 * println("===========================News Details page Adcal iu===================="
	 * );
	 * 
	 * System.out.println("****** News Details page Ad cal iu validation Started");
	 * logStep("****** News Details page Ad cal iu validation Started");
	 * 
	 * //Functions.verifyPubadCal("Smoke", "News(details)");
	 * Utils.verifyPubadCal("Smoke", "News(details)");
	 * 
	 * }
	 */

	@Test(priority = 108, enabled = true)
	@Description("Verify Health & Activities (Allergy) Content page iu on FTL")
	public void Verify_Health_Allergy_ContentPage_AdCall_On_FTL() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"===========================Health & Activities (Allergy) Content page Adcal iu====================");

		System.out.println("****** Health & Activities (Allergy) Content page Ad cal iu validation Started");
		logStep("****** Health & Activities (Allergy) Content page Ad cal iu validation Started");
		Utils.verifyPubadCal("Smoke", "Health(allergy)");

	}

	@Test(priority = 109, enabled = true)
	@Description("Verify Health & Activities (Running) Content iu on FTL")
	public void Verify_Health_Running_ContentPage_AdCall_On_FTL() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"===========================Health & Activities (Running) Content page Adcal iu====================");

		System.out.println("****** Health & Activities (Running) Content page Ad cal iu validation Started");
		logStep("****** Health & Activities (Running) Content page Ad cal iu validation Started");
		Utils.verifyPubadCal("Smoke", "Health(goRun)");

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

	/*
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
	 
	
	/*
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

	/*
	 * This method validates sod custom parameter of Hourly details call
	 */
	@Test(priority = 203, enabled = true)
	@Description("Validating 'sod' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_sod_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating sod custom parameter of Hourly Details call");
		logStep("Validating sod custom parameter of Hourly Details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "sod", "yes");

	}

	/*
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

	/*
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

	/*
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

	/*
	 * This method validates zip custom parameter of Hourly details call
	 */
	@Test(priority = 207, enabled = true)
	@Description("Validating 'zip' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_zip_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating zip custom parameter of Hourly details call");
		logStep("Validating zip custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "zip");

	}

	/*
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

	/*
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

	/*
	 * This method validates plat custom parameter of Hourly details call
	 */
	@Test(priority = 210, enabled = true)
	@Description("Validating 'plat' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_plat_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating plat custom parameter of Hourly details call");
		logStep("Validating plat custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "plat", "iphone");

	}

	/*
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

	/*
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

	/*
	 * This method validates adid custom parameter of Hourly details call
	 */
	@Test(priority = 213, enabled = true)
	@Description("Validating 'adid' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_adid_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating adid custom parameter of Hourly details call");
		logStep("Validating adid custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "adid", "NotNull");

	}

	/*
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

	/*
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

	/*
	 * This method validates cnd custom parameter of Hourly details call
	 */
	@Test(priority = 216, enabled = true)
	@Description("Validating 'cnd' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_cnd_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating cnd custom parameter of Hourly details call");
		logStep("Validating cnd custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "cnd");

	}

	/*
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

	/*
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

	/*
	 * This method validates dma custom parameter of Hourly details call
	 */
	@Test(priority = 219, enabled = true)
	@Description("Validating 'dma' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_dma_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating dma custom parameter of Hourly details call");
		logStep("Validating dma custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "dma");

	}

	/*
	 * This method validates tmp custom parameter of Marquee call
	 */
	@Test(priority = 220, enabled = true)
	@Description("Validating 'tmp' custom parameter of Marquee call ")
	public void Validate_Marquee_tmp_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating tmp custom parameter of Marquee call");
		logStep("Validating tmp custom parameter of Marquee call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "tmp", false);

	}

	/*
	 * This method validates tmp custom parameter of Feed1 call
	 */
	@Test(priority = 221, enabled = true)
	@Description("Validating 'tmp' custom parameter of Feed1 call ")
	public void Validate_Feed1_tmp_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating tmp custom parameter of Feed1 call");
		logStep("Validating tmp custom parameter of Feed1 call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "tmp", false);

	}

	/*
	 * This method validates tmp custom parameter of Hourly details call
	 */
	@Test(priority = 222, enabled = true)
	@Description("Validating 'tmp' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_tmp_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating tmp custom parameter of Hourly details call");
		logStep("Validating tmp custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "tmp", false);

	}

	/*
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

	/*
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

	/*
	 * This method validates tmpc custom parameter of Hourly details call
	 */
	@Test(priority = 225, enabled = true)
	@Description("Validating 'tmpc' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_tmpc_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating tmpc custom parameter of Hourly details call");
		logStep("Validating tmpc custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "tmpc");

	}

	/*
	 * This method validates tmpr custom parameter of Marquee call
	 */
	@Test(priority = 226, enabled = true)
	@Description("Validating 'tmpr' custom parameter of Marquee call ")
	public void Validate_Marquee_tmpr_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating tmpr custom parameter of Marquee call");
		logStep("Validating tmpr custom parameter of Marquee call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "tmpr", false);

	}

	/*
	 * This method validates tmpr custom parameter of Feed1 call
	 */
	@Test(priority = 227, enabled = true)
	@Description("Validating 'tmpr' custom parameter of Feed1 call ")
	public void Validate_Feed1_tmpr_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating tmpr custom parameter of Feed1 call");
		logStep("Validating tmpr custom parameter of Feed1 call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "tmpr", false);

	}

	/*
	 * This method validates tmpr custom parameter of Hourly details call
	 */
	@Test(priority = 228, enabled = true)
	@Description("Validating 'tmpr' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_tmpr_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating tmpr custom parameter of Hourly details call");
		logStep("Validating tmpr custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "tmpr", false);

	}

	/*
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

	/*
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

	/*
	 * This method validates ct custom parameter of Hourly details call
	 */
	@Test(priority = 231, enabled = true)
	@Description("Validating 'ct' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_ct_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ct custom parameter of Hourly details call");
		logStep("Validating ct custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "ct");

	}

	/*
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

	/*
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

	/*
	 * This method validates lang custom parameter of Hourly details call
	 */
	@Test(priority = 234, enabled = true)
	@Description("Validating 'lang' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_lang_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating lang custom parameter of Hourly details call");
		logStep("Validating lang custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "lang", "en");

	}

	/*
	 * This method validates aid custom parameter of Marquee call
	 */
	/*
	 * @Test(priority = 235, enabled = true)
	 * 
	 * @Description("Validating 'aid' custom parameter of Marquee call ") public
	 * void Validate_Marquee_aid_Custom_param() throws Exception {
	 * System.out.println("==============================================");
	 * System.out.println("****** Validating aid custom parameter of Marquee call");
	 * logStep("Validating aid custom parameter of Marquee call ");
	 * 
	 * Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "aid",
	 * "NotNull");
	 * 
	 * }
	 */

	/*
	 * This method validates aid custom parameter of Feed1 call
	 */
	/*
	 * @Test(priority = 236, enabled = true)
	 * 
	 * @Description("Validating 'aid' custom parameter of Feed1 call ") public void
	 * Validate_Feed1_aid_Custom_param() throws Exception {
	 * System.out.println("==============================================");
	 * System.out.println("****** Validating aid custom parameter of Feed1 call");
	 * logStep("Validating aid custom parameter of Feed1 call ");
	 * 
	 * Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "aid",
	 * "NotNull");
	 * 
	 * }
	 */

	/*
	 * This method validates aid custom parameter of Hourly details call
	 */
	/*
	 * @Test(priority = 237, enabled = true)
	 * 
	 * @Description("Validating 'aid' custom parameter of Hourly details call ")
	 * public void Validate_HourlyDetails_aid_Custom_param() throws Exception {
	 * System.out.println("==============================================");
	 * System.out.
	 * println("****** Validating aid custom parameter of Hourly details call");
	 * logStep("Validating aid custom parameter of Hourly details call ");
	 * 
	 * Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "aid",
	 * "NotNull");
	 * 
	 * }
	 */

	/*
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

	/*
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

	/*
	 * This method validates dynght custom parameter of Hourly details call
	 */
	@Test(priority = 240, enabled = true)
	@Description("Validating 'dynght' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_dynght_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating dynght custom parameter of Hourly details call");
		logStep("Validating dynght custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "dynght");

	}

	/*
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

	/*
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

	/*
	 * This method validates locale custom parameter of Hourly details call
	 */
	@Test(priority = 243, enabled = true)
	@Description("Validating 'locale' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_locale_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating locale custom parameter of Hourly details call");
		logStep("Validating locale custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "locale");

	}

	/*
	 * This method validates tile custom parameter of Marquee call
	 */
	@Test(priority = 244, enabled = true)
	@Description("Validating 'tile' custom parameter of Marquee call ")
	public void Validate_Marquee_tile_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating tile custom parameter of Marquee call");
		logStep("Validating tile custom parameter of Marquee call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "tile", false);

	}

	/*
	 * This method validates tile custom parameter of Feed1 call
	 */
	@Test(priority = 245, enabled = true)
	@Description("Validating 'tile' custom parameter of Feed1 call ")
	public void Validate_Feed1_tile_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating tile custom parameter of Feed1 call");
		logStep("Validating tile custom parameter of Feed1 call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "tile", false);

	}

	/*
	 * This method validates tile custom parameter of Hourly details call
	 */
	@Test(priority = 246, enabled = true)
	@Description("Validating 'tile' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_tile_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating tile custom parameter of Hourly details call");
		logStep("Validating tile custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "tile", false);

	}

	/*
	 * This method validates tf custom parameter of Homescreen Today call tf
	 * parameter not exists for NextGenIM and Feed calls, hence limited to
	 * Homescreen Today call
	 */
	/*
	 * @Test(priority = 247, enabled = true)
	 * 
	 * @Description("Validating 'tf' custom parameter of HomeScreen Today call ")
	 * public void Validate_HomeScreen_Today_tf_Custom_param() throws Exception {
	 * System.out.println("==============================================");
	 * System.out.
	 * println("****** Validating tf custom parameter of HomeScreen Today call");
	 * logStep("Validating tf custom parameter of HomeScreen Today call");
	 * Utils.validate_custom_param_val_of_gampad("Smoke", "Pulltorefresh", "tf",
	 * "NotNull");
	 * 
	 * }
	 */

	/*
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

	/*
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

	/*
	 * This method validates ver custom parameter of Hourly details call
	 */
	@Test(priority = 250, enabled = true)
	@Description("Validating 'ver' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_ver_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ver custom parameter of Hourly details call");
		logStep("Validating ver custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "ver", "NotNull");

	}

	/*
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

	/*
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

	/*
	 * This method validates ord custom parameter of Hourly details call
	 */
	@Test(priority = 253, enabled = true)
	@Description("Validating 'ord' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_ord_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ord custom parameter of Hourly details call");
		logStep("Validating ord custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "ord", "NotNull");

	}

	/*
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

	/*
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

	/*
	 * This method validates rmid custom parameter of Hourly details call
	 */
	@Test(priority = 256, enabled = true)
	@Description("Validating 'rmid' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_rmid_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating rmid custom parameter of Hourly details call");
		logStep("Validating rmid custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "rmid", "NotNull");

	}

	/*
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

	/*
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

	/*
	 * This method validates st custom parameter of Hourly details call
	 */
	@Test(priority = 259, enabled = true)
	@Description("Validating 'st' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_st_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating st custom parameter of Hourly details call");
		logStep("Validating st custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "st");

	}

	/*
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

	/*
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

	/*
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

	/*
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

	/*
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

	/*
	 * This method validates ltv custom parameter of Feed1 call
	 */
	@Test(priority = 271, enabled = true)
	@Description("Validating 'ltv' custom parameter of Feed1 call ")
	public void Validate_Feed1_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of Feed1 call");
		logStep("Validating ltv custom parameter of Feed1 call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "ltv", "NotNull");

	}

	/*
	 * This method validates ltv custom parameter of Hourly details call
	 */
	@Test(priority = 272, enabled = true)
	@Description("Validating 'ltv' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of Hourly details call");
		logStep("Validating ltv custom parameter of Hourly details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "ltv", "NotNull");

	}

	/*
	 * This method validates fhic custom parameter of Marquee call for non-existence
	 */
	@Test(priority = 273, enabled = true)
	@Description("Validating 'fhic' custom parameter of Marquee call ")
	public void Validate_Marquee_fhic_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating fhic custom parameter of Marquee call for non-existence");
		logStep("Validating fhic custom parameter of Marquee call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "fhic", false);

	}

	/*
	 * This method validates fhic custom parameter of Feed1 call for non-existence
	 */
	@Test(priority = 274, enabled = true)
	@Description("Validating 'fhic' custom parameter of Feed1 call ")
	public void Validate_Feed1_fhic_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating fhic custom parameter of Feed1 call for non-existence");
		logStep("Validating fhic custom parameter of Feed1 call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "fhic", false);

	}

	/*
	 * This method validates fhic custom parameter of Hourly details call for
	 * non-existence
	 */
	@Test(priority = 275, enabled = true)
	@Description("Validating 'fhic' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_fhic_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating fhic custom parameter of Hourly details call for non-existence");
		logStep("Validating fhic custom parameter of Hourly details call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "fhic", false);

	}

	/*
	 * This method validates floc custom parameter of Marquee call for non-existence
	 */
	@Test(priority = 276, enabled = true)
	@Description("Validating 'floc' custom parameter of Marquee call ")
	public void Validate_Marquee_floc_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating floc custom parameter of Marquee call for non-existence");
		logStep("Validating floc custom parameter of Marquee call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "floc", false);

	}

	/*
	 * This method validates floc custom parameter of Feed1 call for non-existence
	 */
	@Test(priority = 277, enabled = true)
	@Description("Validating 'floc' custom parameter of Feed1 call ")
	public void Validate_Feed1_floc_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating floc custom parameter of Feed1 call for non-existence");
		logStep("Validating floc custom parameter of Feed1 call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "floc", false);

	}

	/*
	 * This method validates floc custom parameter of Hourly details call for
	 * non-existence
	 */
	@Test(priority = 278, enabled = true)
	@Description("Validating 'floc' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_floc_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating floc custom parameter of Hourly details call for non-existence");
		logStep("Validating floc custom parameter of Hourly details call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "floc", false);

	}

	/*
	 * This method validates fltmpc custom parameter of Marquee call for
	 * non-existence
	 */
	@Test(priority = 279, enabled = true)
	@Description("Validating 'fltmpc' custom parameter of Marquee call ")
	public void Validate_Marquee_fltmpc_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating fltmpc custom parameter of Marquee call for non-existence");
		logStep("Validating fltmpc custom parameter of Marquee call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "fltmpc", false);

	}

	/*
	 * This method validates fltmpc custom parameter of Feed1 call for non-existence
	 */
	@Test(priority = 280, enabled = true)
	@Description("Validating 'fltmpc' custom parameter of Feed1 call ")
	public void Validate_Feed1_fltmpc_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating fltmpc custom parameter of Feed1 call for non-existence");
		logStep("Validating fltmpc custom parameter of Feed1 call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "fltmpc", false);

	}

	/*
	 * This method validates fltmpc custom parameter of Hourly details call for
	 * non-existence
	 */
	@Test(priority = 281, enabled = true)
	@Description("Validating 'fltmpc' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_fltmpc_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating fltmpc custom parameter of Hourly details call for non-existence");
		logStep("Validating fltmpc custom parameter of Hourly details call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "fltmpc", false);

	}

	/*
	 * This method validates ftl custom parameter of Marquee call for non-existence
	 */
	@Test(priority = 282, enabled = true)
	@Description("Validating 'ftl' custom parameter of Marquee call ")
	public void Validate_Marquee_ftl_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ftl custom parameter of Marquee call for non-existence");
		logStep("Validating ftl custom parameter of Marquee call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "ftl", false);

	}

	/*
	 * This method validates ftl custom parameter of Feed1 call for non-existence
	 */
	@Test(priority = 283, enabled = true)
	@Description("Validating 'ftl' custom parameter of Feed1 call ")
	public void Validate_Feed1_ftl_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ftl custom parameter of Feed1 call for non-existence");
		logStep("Validating ftl custom parameter of Feed1 call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "ftl", false);

	}

	/*
	 * This method validates ftl custom parameter of Hourly details call for
	 * non-existence
	 */
	@Test(priority = 284, enabled = true)
	@Description("Validating 'ftl' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_ftl_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ftl custom parameter of Hourly details call for non-existence");
		logStep("Validating ftl custom parameter of Hourly details call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "ftl", false);

	}

	/*
	 * This method validates h2o custom parameter of Marquee call for non-existence
	 */
	@Test(priority = 285, enabled = true)
	@Description("Validating 'h2o' custom parameter of Marquee call ")
	public void Validate_Marquee_h2o_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating h2o custom parameter of Marquee call for non-existence");
		logStep("Validating h2o custom parameter of Marquee call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "h2o", false);

	}

	/*
	 * This method validates h2o custom parameter of Feed1 call for non-existence
	 */
	@Test(priority = 286, enabled = true)
	@Description("Validating 'h2o' custom parameter of Feed1 call ")
	public void Validate_Feed1_h2o_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating h2o custom parameter of Feed1 call for non-existence");
		logStep("Validating h2o custom parameter of Feed1 call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "h2o", false);

	}

	/*
	 * This method validates h2o custom parameter of Hourly details call for
	 * non-existence
	 */
	@Test(priority = 287, enabled = true)
	@Description("Validating 'h2o' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_h2o_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating h2o custom parameter of Hourly details call for non-existence");
		logStep("Validating h2o custom parameter of Hourly details call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "h2o", false);

	}

	/*
	 * This method validates hmid custom parameter of Marquee call for non-existence
	 */
	@Test(priority = 288, enabled = true)
	@Description("Validating 'hmid' custom parameter of Marquee call ")
	public void Validate_Marquee_hmid_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating hmid custom parameter of Marquee call for non-existence");
		logStep("Validating hmid custom parameter of Marquee call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "hmid", false);

	}

	/*
	 * This method validates hmid custom parameter of Feed1 call for non-existence
	 */
	@Test(priority = 289, enabled = true)
	@Description("Validating 'hmid' custom parameter of Feed1 call ")
	public void Validate_Feed1_hmid_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating hmid custom parameter of Feed1 call for non-existence");
		logStep("Validating hmid custom parameter of Feed1 call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "hmid", false);

	}

	/*
	 * This method validates hmid custom parameter of Hourly details call for
	 * non-existence
	 */
	@Test(priority = 290, enabled = true)
	@Description("Validating 'hmid' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_hmid_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating hmid custom parameter of Hourly details call for non-existence");
		logStep("Validating hmid custom parameter of Hourly details call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "hmid", false);

	}

	/*
	 * This method validates layer custom parameter of Marquee call for
	 * non-existence
	 */
	@Test(priority = 291, enabled = true)
	@Description("Validating 'layer' custom parameter of Marquee call ")
	public void Validate_Marquee_layer_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating layer custom parameter of Marquee call for non-existence");
		logStep("Validating layer custom parameter of Marquee call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "layer", false);

	}

	/*
	 * This method validates layer custom parameter of Feed1 call for non-existence
	 */
	@Test(priority = 292, enabled = true)
	@Description("Validating 'layer' custom parameter of Feed1 call ")
	public void Validate_Feed1_layer_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating layer custom parameter of Feed1 call for non-existence");
		logStep("Validating layer custom parameter of Feed1 call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "layer", false);

	}

	/*
	 * This method validates layer custom parameter of Hourly details call for
	 * non-existence
	 */
	@Test(priority = 293, enabled = true)
	@Description("Validating 'layer' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_layer_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating layer custom parameter of Hourly details call for non-existence");
		logStep("Validating layer custom parameter of Hourly details call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "layer", false);

	}

	/*
	 * This method validates uv custom parameter of Marquee call for non-existence
	 */
	@Test(priority = 294, enabled = true)
	@Description("Validating 'uv' custom parameter of Marquee call ")
	public void Validate_Marquee_uv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating uv custom parameter of Marquee call for non-existence");
		logStep("Validating uv custom parameter of Marquee call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "uv", false);

	}

	/*
	 * This method validates uv custom parameter of Feed1 call for non-existence
	 */
	@Test(priority = 295, enabled = true)
	@Description("Validating 'uv' custom parameter of Feed1 call ")
	public void Validate_Feed1_uv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating uv custom parameter of Feed1 call for non-existence");
		logStep("Validating uv custom parameter of Feed1 call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "uv", false);

	}

	/*
	 * This method validates uv custom parameter of Hourly details call for
	 * non-existence
	 */
	@Test(priority = 296, enabled = true)
	@Description("Validating 'uv' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_uv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating uv custom parameter of Hourly details call for non-existence");
		logStep("Validating uv custom parameter of Hourly details call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "uv", false);

	}

	/*
	 * This method validates wind custom parameter of Marquee call for non-existence
	 */
	@Test(priority = 297, enabled = true)
	@Description("Validating 'wind' custom parameter of Marquee call ")
	public void Validate_Marquee_wind_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating wind custom parameter of Marquee call for non-existence");
		logStep("Validating wind custom parameter of Marquee call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "wind", false);

	}

	/*
	 * This method validates wind custom parameter of Feed1 call for non-existence
	 */
	@Test(priority = 298, enabled = true)
	@Description("Validating 'wind' custom parameter of Feed1 call ")
	public void Validate_Feed1_wind_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating wind custom parameter of Feed1 call for non-existence");
		logStep("Validating wind custom parameter of Feed1 call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "wind", false);

	}

	/*
	 * This method validates wind custom parameter of Hourly details call for
	 * non-existence
	 */
	@Test(priority = 299, enabled = true)
	@Description("Validating 'wind' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_wind_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating wind custom parameter of Hourly details call for non-existence");
		logStep("Validating wind custom parameter of Hourly details call for non-existence");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "wind", false);

	}
	
	/*
	 * This method validates mr custom parameter of Marquee Call
	 */
	@Test(priority = 300, enabled = true)
	@Description("Validating 'mr' custom parameter of Marquee Call")
	public void validate_Marquee_mr_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating mr custom parameter of Marquee call");
		logStep("Validating mr custom parameter of Marquee call");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "mr", "0");
	}
	 
	
	/*
	 * This method validates mr custom parameter of Feed1 call
	 */
	@Test(priority = 301, enabled = true)
	@Description("Validating 'mr' custom parameter of Feed1 call ")
	public void Validate_Feed1_mr_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating mr custom parameter of Feed1 call");
		logStep("Validating mr custom parameter of Feed1 call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Feed1", "mr", "0");

	}

	/*
	 * This method validates mr custom parameter of Hourly details call
	 */
	@Test(priority = 302, enabled = true)
	@Description("Validating 'mr' custom parameter of Hourly details call ")
	public void Validate_HourlyDetails_mr_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating mr custom parameter of Hourly Details call");
		logStep("Validating mr custom parameter of Hourly Details call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "mr", "0");

	}
	
	@Test(priority = 310, enabled = true)
	@Description("Verify Allergy Details Spot Light ad call iu")
	public void Verify_AllergyDetails_Spotlight_AdCall() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Allergy Details Spotlight Adcall verification test case Started");
		logStep("****** Allergy Details Adcall verification test case Started");
		Utils.verifyPubadCal("Smoke", "WMAllergy(SpotLight)");
	}

	/*
	 * This method validates pos custom parameter of Week Ahead call
	 */
	@Test(priority = 311, enabled = true)
	@Description("Validating 'pos' custom parameter of Allergy Details Spot Light call ")
	public void Validate_AllergyDetails_Spotlight_pos_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating pos custom parameter of Allergy Details Spot Light call");
		logStep("Validating pos custom parameter of Allergy Details Spot Light call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "WMAllergy(SpotLight)", "pos", "app_sl");

	}

	@Test(priority = 312, enabled = true)
	@Description("Validating Allergy Details Spot Light call Ad sz parameter")
	public void Validate_AllergyDetails_Spotlight_Ad_Size() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Allergy Details Spot Light call Ad sz parameter in charles");
		logStep("Validating Allergy Details Spot Light call Ad sz parameter in charles ");
		Utils.verify_Ad_Size("Smoke", "WMAllergy(SpotLight)");

	}

	/*
	 * This method validate the daily details ad units and its parameters To execute
	 * below method alone, Utils.get_v3_wx_forecast_daily_15day_data() to be
	 * executed as a prerequisite script
	 */
	@Test(priority = 326, enabled = true)
	@Description("Verify Daily Details Ad Units")
	public void Verify_Daily_Details_AdUnits() throws Exception {
	System.out.println("==============================================");

	System.out.println("****** Daily Details Ad Units verification test case Started");
	logStep("****** Daily Details Ad Units verification test case Started");
	// Utils.get_v3_wx_forecast_daily_15day_data();
	Functions.close_launchApp();
	Functions.checkForAppState();
	addrScreen.enternewAddress(false, "Atlanta, Georgia");
	proxy.clearCharlesSession();
	dTab.validateDailyDetailsAdUnits("Smoke", "Daily(10day)");
	}
	
	/**
	 * This method validate the 'mr' custom parameter of Hourly Details
	 * @throws Exception
	 */
	@Test(priority = 327, enabled = true)
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
		/*
		 * 30 seconds hard wait to refresh the ad call
		 */
		TestBase.waitForMilliSeconds(30000);
		Functions.archive_folder("Charles");
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "mr", "1");
		/*Functions.clickonHomeTab();
		proxy.clearCharlesSession();
		TestBase.waitForMilliSeconds(2000);
		// navigate to Hourly tab
		navigateToHourlyTab();
		Functions.archive_folder("Charles");
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "mr", "0");
		proxy.clearCharlesSession();
		//Functions.scroll_Up();
		TestBase.waitForMilliSeconds(30000);
		Functions.archive_folder("Charles");
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "mr", "1");*/
	}
	
	/**
	 * This method validate the 'mr' custom parameter of Map Details
	 * @throws Exception
	 */
	@Test(priority = 328, enabled = true)
	@Description("Verify 'mr' custom parameter of Map Details")
	public void Verify_Map_Details_mr_Custom_Parameter() throws Exception {
		System.out.println("==============================================");

		System.out.println("****** Verifying mr custom parameter of Map Details call");
		logStep("****** Verifying mr custom parameter of Map Details call");
		hmTab.clickonHomeTab();
		proxy.clearCharlesSession();
		TestBase.waitForMilliSeconds(2000);
		// navigate to Radar tab
		rTab.navigateToRadarTab();
		Functions.archive_folder("Charles");
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.validate_custom_param_val_of_gampad("Smoke", "Map", "mr", "0");
		proxy.clearCharlesSession();
		Functions.scroll_Up();
		Functions.archive_folder("Charles");
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.validate_custom_param_val_of_gampad("Smoke", "Map", "mr", "1");
		hmTab.clickonHomeTab();
		proxy.clearCharlesSession();
		TestBase.waitForMilliSeconds(2000);
		// navigate to Radar tab
		rTab.navigateToRadarTab();
		Functions.archive_folder("Charles");
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.validate_custom_param_val_of_gampad("Smoke", "Map", "mr", "0");
		proxy.clearCharlesSession();
		Functions.scroll_Up();
		Functions.archive_folder("Charles");
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.validate_custom_param_val_of_gampad("Smoke", "Map", "mr", "1");
	}
	
	/*
	 * Enable Preconditions for WeatherFX API Parameters validation
	 */
	@Test(priority = 351, enabled = true)
	@Description("Verify Enable Preconditions For WeatherFX API Parameters")
	public void Verify_enable_preConditions_for_WeatherFx_API_Parameters() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Verify Enable Preconditions for WeatherFX API Parameters test started");
		logStep("Verify Enable Preconditions for WeatherFX API Parameters test started");
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.checkForAppState();
		addrScreen.enternewAddress(false, "30124", "Cave Spring, Georgia");
		TestBase.waitForMilliSeconds(5000);
		addrScreen.enternewAddress(false, "07095", "Woodbridge Township, New Jersey");
		TestBase.waitForMilliSeconds(5000);
		addrScreen.enternewAddress(false, "61920", "Charleston, Illinois");
		TestBase.waitForMilliSeconds(5000);
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.checkForAppState();
		TestBase.waitForMilliSeconds(2000);
		addrScreen.enternewAddress(false, "30124", "Cave Spring, Georgia");
		TestBase.waitForMilliSeconds(5000);
		// navigate to Hourly tab
		// navigateToHourlyTab();
		// TestBase.waitForMilliSeconds(2000);
		// navigate to Daily tab
		// navigateToDailyTab();
		// TestBase.waitForMilliSeconds(2000);
		// navigate to Radar tab
		rTab.navigateToRadarTab();
		TestBase.waitForMilliSeconds(2000);
		hmTab.clickonHomeTab();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		// Utils.get_v3_wx_forecast_daily_15day_data();

	}

	/*
	 * This method validates wfxtg custom parameter of Hourly details call
	 */
	/*
	 * @Test(priority = 352, enabled = true)
	 * 
	 * @Description("Validating 'wfxtg' custom parameter of Hourly details call ")
	 * public void Validate_HourlyDetails_wfxtg_Custom_param() throws Exception {
	 * System.out.println("==============================================");
	 * System.out.
	 * println("****** Validating wfxtg custom parameter of Hourly details call");
	 * logStep("Validating wfxtg custom parameter of Hourly details call ");
	 * Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Hourly",
	 * "wfxtg", "30124");
	 * 
	 * }
	 */

	/*
	 * This method validates wfxtg custom parameter of Daily details call
	 */
	/*
	 * @Test(priority = 353, enabled = true)
	 * 
	 * @Description("Validating 'wfxtg' custom parameter of Daily details call ")
	 * public void Validate_DailyDetails_wfxtg_Custom_param() throws Exception {
	 * System.out.println("==============================================");
	 * System.out.
	 * println("****** Validating wfxtg custom parameter of Daily details call");
	 * logStep("Validating wfxtg custom parameter of Daily details call ");
	 * Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Daily(10day)",
	 * "wfxtg", "30124");
	 * 
	 * }
	 */

	/*
	 * This method validates wfxtg custom parameter of Map details call
	 */
	@Test(priority = 354, enabled = true)
	@Description("Validating 'wfxtg' custom parameter of Map details call ")
	public void Validate_MapDetails_wfxtg_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating wfxtg custom parameter of Map details call");
		logStep("Validating wfxtg custom parameter of Map details call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Map", "wfxtg", "30124");

	}

	/*
	 * This method validates cxtg custom parameter of Hourly details call
	 */
	/*
	 * @Test(priority = 355, enabled = true)
	 * 
	 * @Description("Validating 'cxtg' custom parameter of Hourly details call ")
	 * public void Validate_HourlyDetails_cxtg_Custom_param() throws Exception {
	 * System.out.println("==============================================");
	 * System.out.
	 * println("****** Validating cxtg custom parameter of Hourly details call");
	 * logStep("Validating cxtg custom parameter of Hourly details call ");
	 * Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Hourly", "cxtg",
	 * "30124");
	 * 
	 * }
	 */

	/*
	 * This method validates cxtg custom parameter of Daily details call
	 */
	/*
	 * @Test(priority = 356, enabled = true)
	 * 
	 * @Description("Validating 'cxtg' custom parameter of Daily details call ")
	 * public void Validate_DailyDetails_cxtg_Custom_param() throws Exception {
	 * System.out.println("==============================================");
	 * System.out.
	 * println("****** Validating cxtg custom parameter of Daily details call");
	 * logStep("Validating cxtg custom parameter of Daily details call ");
	 * Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Daily(10day)",
	 * "cxtg", "30124");
	 * 
	 * }
	 */

	/*
	 * This method validates cxtg custom parameter of Map details call
	 */
	@Test(priority = 357, enabled = true)
	@Description("Validating 'cxtg' custom parameter of Map details call ")
	public void Validate_MapDetails_cxtg_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating cxtg custom parameter of Map details call");
		logStep("Validating cxtg custom parameter of Map details call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Map", "cxtg", "30124");

	}

	/*
	 * This method validates zcs custom parameter of Hourly details call
	 */
	/*
	 * @Test(priority = 358, enabled = true)
	 * 
	 * @Description("Validating 'zcs' custom parameter of Hourly details call ")
	 * public void Validate_HourlyDetails_zcs_Custom_param() throws Exception {
	 * System.out.println("==============================================");
	 * System.out.
	 * println("****** Validating zcs custom parameter of Hourly details call");
	 * logStep("Validating zcs custom parameter of Hourly details call ");
	 * Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Hourly", "zcs",
	 * "30124");
	 * 
	 * }
	 */

	/*
	 * This method validates zcs custom parameter of Daily details call
	 */
	/*
	 * @Test(priority = 359, enabled = true)
	 * 
	 * @Description("Validating 'zcs' custom parameter of Daily details call ")
	 * public void Validate_DailyDetails_zcs_Custom_param() throws Exception {
	 * System.out.println("==============================================");
	 * System.out.
	 * println("****** Validating zcs custom parameter of Daily details call");
	 * logStep("Validating zcs custom parameter of Daily details call ");
	 * Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Daily(10day)",
	 * "zcs", "30124");
	 * 
	 * }
	 */

	/*
	 * This method validates zcs custom parameter of Map details call
	 */
	@Test(priority = 360, enabled = true)
	@Description("Validating 'zcs' custom parameter of Map details call ")
	public void Validate_MapDetails_zcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating zcs custom parameter of Map details call");
		logStep("Validating zcs custom parameter of Map details call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Map", "zcs", "30124");

	}

	/*
	 * This method validates hzcs custom parameter of Hourly details call
	 */
	/*
	 * @Test(priority = 361, enabled = true)
	 * 
	 * @Description("Validating 'hzcs' custom parameter of Hourly details call ")
	 * public void Validate_HourlyDetails_hzcs_Custom_param() throws Exception {
	 * System.out.println("==============================================");
	 * System.out.
	 * println("****** Validating hzcs custom parameter of Hourly details call");
	 * logStep("Validating hzcs custom parameter of Hourly details call ");
	 * Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Hourly", "hzcs",
	 * "30124");
	 * 
	 * }
	 */

	/*
	 * This method validates hzcs custom parameter of Daily details call
	 */
	/*
	 * @Test(priority = 362, enabled = true)
	 * 
	 * @Description("Validating 'hzcs' custom parameter of Daily details call ")
	 * public void Validate_DailyDetails_hzcs_Custom_param() throws Exception {
	 * System.out.println("==============================================");
	 * System.out.
	 * println("****** Validating hzcs custom parameter of Daily details call");
	 * logStep("Validating hzcs custom parameter of Daily details call ");
	 * Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Daily(10day)",
	 * "hzcs", "30124");
	 * 
	 * }
	 */

	/*
	 * This method validates hzcs custom parameter of Map details call
	 */
	@Test(priority = 363, enabled = true)
	@Description("Validating 'hzcs' custom parameter of Map details call ")
	public void Validate_MapDetails_hzcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating hzcs custom parameter of Map details call");
		logStep("Validating hzcs custom parameter of Map details call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Map", "hzcs", "30124");

	}

	/*
	 * This method validates nzcs custom parameter of Hourly details call
	 */
	/*
	 * @Test(priority = 364, enabled = true)
	 * 
	 * @Description("Validating 'nzcs' custom parameter of Hourly details call ")
	 * public void Validate_HourlyDetails_nzcs_Custom_param() throws Exception {
	 * System.out.println("==============================================");
	 * System.out.
	 * println("****** Validating nzcs custom parameter of Hourly details call");
	 * logStep("Validating nzcs custom parameter of Hourly details call ");
	 * Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Hourly", "nzcs",
	 * "30124");
	 * 
	 * }
	 */
	/*
	 * This method validates nzcs custom parameter of Daily details call
	 */
	/*
	 * @Test(priority = 365, enabled = true)
	 * 
	 * @Description("Validating 'nzcs' custom parameter of Daily details call ")
	 * public void Validate_DailyDetails_nzcs_Custom_param() throws Exception {
	 * System.out.println("==============================================");
	 * System.out.
	 * println("****** Validating nzcs custom parameter of Daily details call");
	 * logStep("Validating nzcs custom parameter of Daily details call ");
	 * Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Daily(10day)",
	 * "nzcs", "30124");
	 * 
	 * }
	 */

	/*
	 * This method validates nzcs custom parameter of Map details call
	 */
	@Test(priority = 366, enabled = true)
	@Description("Validating 'nzcs' custom parameter of Map details call ")
	public void Validate_MapDetails_nzcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating nzcs custom parameter of Map details call");
		logStep("Validating nzcs custom parameter of Map details call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Map", "nzcs", "30124");

	}

	/*
	 * Enable Preconditions for WeatherFX API Parameters validation from Planning
	 * Card
	 */

	@Test(priority = 401, enabled = true)
	@Description("Verify Enable Preconditions For WeatherFX API Parameters From Planning Card")
	public void Verify_enable_preConditions_for_WeatherFx_API_ParametersFromPlanningCard() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"****** Verify Enable Preconditions for WeatherFX API Parameters from planning card test started");
		logStep("Verify Enable Preconditions for WeatherFX API Parameters from planning card test started");
		Utils.wfxParameters.clear();
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.checkForAppState();
		addrScreen.enternewAddress(false, "61920", "Charleston, Illinois");
		TestBase.waitForMilliSeconds(5000);
		// navigate to Planning Card
		// Utils.navigateToPlanningCard();
		//Utils.navigateTofeedCard("planning-containerView", false, false);
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
		// Functions.clickonHomeTab();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.loadWeatherFXAPIParameterValuestoMap_ByZipCode("61920");
		// Utils.get_v3_wx_forecast_daily_15day_data();

	}

	/*
	 * This method validates wfxtg custom parameter of Hourly details banner ad call
	 */
	@Test(priority = 403, enabled = true)
	@Description("Validating 'wfxtg' custom parameter of Hourly details banner ad call ")
	public void Validate_HourlyDetails_Banner_Ad_wfxtg_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating wfxtg custom parameter of Hourly details banner ad call");
		logStep("Validating wfxtg custom parameter of Hourly details banner ad call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Hourly", "wfxtg", "61920");

	}

	/*
	 * This method validates wfxtg custom parameter of Daily details banner ad call
	 */
	@Test(priority = 404, enabled = true)
	@Description("Validating 'wfxtg' custom parameter of Daily details banner ad call ")
	public void Validate_DailyDetails_Banner_Ad_wfxtg_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating wfxtg custom parameter of Daily details banner ad call");
		logStep("Validating wfxtg custom parameter of Daily details banner ad call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Daily(10day)", "wfxtg", "61920");

	}

	/*
	 * This method validates cxtg custom parameter of Home Screen banner banner ad
	 * call
	 */
	@Test(priority = 406, enabled = true)
	@Description("Validating 'cxtg' custom parameter of Hourly details banner ad call ")
	public void Validate_HourlyDetails_Banner_Ad_cxtg_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating cxtg custom parameter of Hourly details banner ad call");
		logStep("Validating cxtg custom parameter of Hourly details banner ad call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Hourly", "cxtg", "61920");

	}

	/*
	 * This method validates cxtg custom parameter of Daily details banner ad call
	 */
	@Test(priority = 407, enabled = true)
	@Description("Validating 'cxtg' custom parameter of Daily details banner ad call ")
	public void Validate_DailyDetails_Banner_Ad_cxtg_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating cxtg custom parameter of Daily details banner ad call");
		logStep("Validating cxtg custom parameter of Daily details banner ad call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Daily(10day)", "cxtg", "61920");

	}

	/*
	 * This method validates zcs custom parameter of Hourly details banner ad call
	 */
	@Test(priority = 409, enabled = true)
	@Description("Validating 'zcs' custom parameter of Hourly details banner ad call ")
	public void Validate_HourlyDetails_Banner_Ad_zcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating zcs custom parameter of Hourly details banner ad call");
		logStep("Validating zcs custom parameter of Hourly details banner ad call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Hourly", "zcs", "61920");

	}

	/*
	 * This method validates zcs custom parameter of Daily details banner ad call
	 */
	@Test(priority = 410, enabled = true)
	@Description("Validating 'zcs' custom parameter of Daily details banner ad call ")
	public void Validate_DailyDetails_Banner_Ad_zcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating zcs custom parameter of Daily details banner ad call");
		logStep("Validating zcs custom parameter of Daily details banner ad call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Daily(10day)", "zcs", "61920");

	}

	/*
	 * This method validates hzcs custom parameter of Hourly details banner ad call
	 */
	@Test(priority = 412, enabled = true)
	@Description("Validating 'hzcs' custom parameter of Hourly details banner ad call ")
	public void Validate_HourlyDetails_Banner_Ad_hzcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating hzcs custom parameter of Hourly details banner ad call");
		logStep("Validating hzcs custom parameter of Hourly details banner ad call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Hourly", "hzcs", "61920");

	}

	/*
	 * This method validates hzcs custom parameter of Daily details banner ad call
	 */
	@Test(priority = 413, enabled = true)
	@Description("Validating 'hzcs' custom parameter of Daily details banner ad call ")
	public void Validate_DailyDetails_Banner_Ad_hzcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating hzcs custom parameter of Daily details banner ad call");
		logStep("Validating hzcs custom parameter of Daily details banner ad call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Daily(10day)", "hzcs", "61920");

	}

	/*
	 * This method validates nzcs custom parameter of Hourly details banner ad call
	 */
	@Test(priority = 415, enabled = true)
	@Description("Validating 'nzcs' custom parameter of Hourly details banner ad call ")
	public void Validate_HourlyDetails_Banner_Ad_nzcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating nzcs custom parameter of Hourly details banner ad call");
		logStep("Validating nzcs custom parameter of Hourly details banner ad call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Hourly", "nzcs", "61920");

	}

	/*
	 * This method validates nzcs custom parameter of Daily details banner ad call
	 */
	@Test(priority = 416, enabled = true)
	@Description("Validating 'nzcs' custom parameter of Daily details banner ad call ")
	public void Validate_DailyDetails_Banner_Ad_nzcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating nzcs custom parameter of Daily details banner ad call");
		logStep("Validating nzcs custom parameter of Daily details banner ad call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "Daily(10day)", "nzcs", "61920");

	}

	/*
	 * Enable Preconditions for WeatherFX API Parameters of Homescreen banner ad
	 * validation from Planning Card
	 */

	/*
	 * @Test(priority = 426, enabled = true)
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
	 * Details from Planning Card pScreen.navigateToTodayDetailsFromPlanningCard();
	 * TestBase.waitForMilliSeconds(2000); navigateBackToFeedCard(); TestBase.waitForMilliSeconds(2000);
	 * Functions.clickonHomeTab(); Functions.scroll_Up(); Functions.scroll_Up();
	 * Functions.archive_folder("Charles"); TestBase.waitForMilliSeconds(5000); proxy.getXml();
	 * Utils.createXMLFileForCharlesSessionFile(); //
	 * Utils.get_v3_wx_forecast_daily_15day_data();
	 * 
	 * }
	 */

	/*
	 * This method validates wfxtg custom parameter of Home Screen banner ad call
	 */
	/*
	 * @Test(priority = 427, enabled = true)
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

	/*
	 * This method validates cxtg custom parameter of Home Screen banner ad call
	 */
	/*
	 * @Test(priority = 428, enabled = true)
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

	/*
	 * This method validates zcs custom parameter of Home Screen banner ad call
	 */
	/*
	 * @Test(priority = 429, enabled = true)
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

	/*
	 * This method validates hzcs custom parameter of Home Screen banner ad call
	 */
	/*
	 * @Test(priority = 430, enabled = true)
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

	/*
	 * This method validates nzcs custom parameter of Home Screen banner ad call
	 */
	/*
	 * @Test(priority = 431, enabled = true)
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

	/*
	 * This method navigates to video module on navtab and gets video call iu value
	 */
	@Test(priority = 451, enabled = true)
	@Description("Verify Preroll ad on Video module")
	public void Verify_PrerollAd() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Preroll-video ad test case Started");
		logStep("****** Preroll-video ad test case Started");
		Utils.wfxParameters.clear();
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.checkForAppState();
		// Functions.enternewAddress(false, "Atlanta, Georgia");
		addrScreen.enternewAddress(false, "07095", "Woodbridge Township, New Jersey");
		TestBase.waitForMilliSeconds(10000);
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.loadWeatherFXAPIParameterValuestoMap_ByZipCode("07095");
		proxy.clearCharlesSession();
		// TestBase.waitForMilliSeconds(5000);
		// navigate to Video tab
		vTab.navigateToVideoTab();
		TestBase.waitForMilliSeconds(10000);
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.get_iu_value_of_Feedcall("Smoke", "PreRollVideo");
		Utils.verifyPubadCal("Smoke", "PreRollVideo");
	}

	/*
	 * This method validates cmsid custom parameter of video call
	 */
	@Test(priority = 452, enabled = true)
	@Description("Validating 'cmsid' custom parameter of Video call ")
	public void Validate_PreRollVideo_cmsid_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating cmsid custom parameter of Video call");
		logStep("Validating cmsid custom parameter of Video call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "PreRollVideo", "cmsid", false);

	}

	/*
	 * This method validates ttid custom parameter of video call
	 */
	@Test(priority = 453, enabled = true)
	@Description("Validating 'ttid' custom parameter of Video call ")
	public void Validate_PreRollVideo_ttid_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ttid custom parameter of Video call");
		logStep("Validating ttid custom parameter of Video call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "PreRollVideo", "ttid", "NotNull");

	}

	/*
	 * This method validates vid custom parameter of video call
	 */
	@Test(priority = 454, enabled = true)
	@Description("Validating 'vid' custom parameter of Video call ")
	public void Validate_PreRollVideo_vid_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating vid custom parameter of Video call");
		logStep("Validating vid custom parameter of Video call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "PreRollVideo", "vid", false);

	}

	/*
	 * This method validates descritpion url of video call
	 */
	@Test(priority = 455, enabled = true)
	@Description("Validating 'description url' of Video call ")
	public void Validate_PreRollVideo_description_url_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating description url of Video call");
		logStep("Validating description url of Video call ");
		Utils.validate_Noncustom_param_val_of_gampad("Smoke", "PreRollVideo", "description_url", "NotNull");

	}

	/*
	 * This method validates wfxtg custom parameter of video call
	 */
	@Test(priority = 460, enabled = true)
	@Description("Validating 'wfxtg' custom parameter of video call ")
	public void Validate_PreRollVideo_wfxtg_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating wfxtg custom parameter of video call");
		logStep("Validating wfxtg custom parameter of video call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "PreRollVideo", "wfxtg", "07095");

	}

	/*
	 * This method validates cxtg custom parameter of video call
	 */
	@Test(priority = 461, enabled = true)
	@Description("Validating 'cxtg' custom parameter of video call ")
	public void Validate_PreRollVideo_cxtg_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating cxtg custom parameter of video call");
		logStep("Validating cxtg custom parameter of video call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "PreRollVideo", "cxtg", "07095");

	}

	/*
	 * This method validates zcs custom parameter of video call
	 */
	@Test(priority = 462, enabled = true)
	@Description("Validating 'zcs' custom parameter of video call ")
	public void Validate_PreRollVideo_zcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating zcs custom parameter of video call");
		logStep("Validating zcs custom parameter of video call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "PreRollVideo", "zcs", "07095");

	}

	/*
	 * This method validates hzcs custom parameter of video call
	 */
	@Test(priority = 463, enabled = true)
	@Description("Validating 'hzcs' custom parameter of video call ")
	public void Validate_PreRollVideo_hzcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating hzcs custom parameter of video call");
		logStep("Validating hzcs custom parameter of video call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "PreRollVideo", "hzcs", "07095");

	}

	/*
	 * This method validates nzcs custom parameter of video call
	 */
	@Test(priority = 464, enabled = true)
	@Description("Validating 'nzcs' custom parameter of video call ")
	public void Validate_PreRollVideo_nzcs_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating nzcs custom parameter of video call");
		logStep("Validating nzcs custom parameter of video call ");
		Utils.validate_custom_param_val_of_gampad_with_zip("Smoke", "PreRollVideo", "nzcs", "07095");
		Utils.wfxParameters.clear();

	}

	/*
	 * This method validates Google Interactive Media Ads SDK version i.e. IMA SDK
	 */
	@Test(priority = 475, enabled = true)
	@Description("Validating Google Interactive Media Ads SDK version of Preroll video call ")
	public void Validate_IMA_SDK_version() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"****** Validating Google Interactive Media Ads SDK version i.e. 'js' parameter of Preroll video call");
		logStep("Validating Google Interactive Media Ads SDK version i.e. 'js' parameter of Preroll video call");

		Utils.validate_Noncustom_param_val_of_gampad("Smoke", "PreRollVideo", "js",
				properties.getProperty("IMASDKVersion"));

	}

	/*
	 * This method validates Hourly Details BigAd1 ad call
	 */
	@Test(priority = 501, enabled = true)
	@Description("Verify Hourly Details BigAd1 Ad call")
	public void Verify_Hourly_Details_BigAd1_Adcall() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Hourly Details Big Ad1 call verification test case Started");
		logStep("****** Hourly Details Big Ad1 call verification test case Started");
		proxy.clearCharlesSession();
		Functions.archive_folder("Charles");
		hrTab.navigateToHourlyTab();
		Functions.swipe_Up_ByIterations(Ad, 10);
		TestBase.waitForMilliSeconds(5000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.verifyPubadCal("Smoke", "Hourly1");
	}

	/*
	 * This method validates Hourly Details BigAd2 ad call
	 */
	@Test(priority = 502, enabled = true)
	@Description("Verify Hourly Details BigAd2 Ad call")
	public void Verify_Hourly_Details_BigAd2_Adcall() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Hourly Details Big Ad2 call verification test case Started");
		logStep("****** Hourly Details Big Ad2 call verification test case Started");
		Utils.verifyPubadCal("Smoke", "Hourly2");
	}

	/*
	 * This method validates Hourly Details BigAd3 ad call
	 */
	@Test(priority = 503, enabled = true)
	@Description("Verify Hourly Details BigAd3 Ad call")
	public void Verify_Hourly_Details_BigAd3_Adcall() throws Exception {
		System.out.println("==============================================");

		System.out.println("****** Hourly Details Big Ad3 call verification test case Started");
		logStep("****** Hourly Details Big Ad3 call verification test case Started");
		Utils.verifyPubadCal("Smoke", "Hourly3");
	}

	/*
	 * Kill and Launch the app for API Calls verification
	 */
	@Test(priority = 600, enabled = true)
	@Description("Kill and Launch the app for API calls verification")
	public void kill_and_Launch_app_for_api_calls() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Kill and Launch the app for API calls verification");
		logStep("Kill and Launch the app for API calls verification");
		TestBase.waitForMilliSeconds(5000);
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.checkForAppState();
		// Functions.enternewAddress(false, "Atlanta, Georgia");
		// TestBase.waitForMilliSeconds(10000);
		Functions.put_Background_launch(15);
		Functions.checkForAppState();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();

	}

	/*
	 * This method validates lotame call
	 */
	@Test(priority = 601, enabled = true)
	@Description("Lotame Call verification on Background Launch")
	public void Verify_Lotame_Call_On_KillAndLaunch() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** bcp.crwdcntrl.net Call  on Background Launch test case Started");
		logStep("****** bcp.crwdcntrl.net Call  on Background Launch test case Started");
		Utils.verifyAPICal("Smoke", "Lotame");

	}

	/*
	 * This method validates Turbo Api call i.e. api.weather.com call
	 */
	@Test(priority = 602, enabled = true)
	@Description("Turbo API Call verification on Kill and Launch")
	public void Verify_TurboAPI_Call_On_KillAndLaunch() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** api.weather.com Call On Kill and Launch test case Started");
		logStep("****** api.weather.com Call On Kill and Launch test case Started");
		Utils.verifyAPICal("Smoke", "TurboApi");

	}

	/*
	 * This method validates WFXTriggers call
	 */
	@Test(priority = 603, enabled = true)
	@Description("WFX Trigger Call verification On Kill and Launch")
	public void Verify_WFXTriggers_Call_On_KillAndLaunch() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** triggers.wfxtriggers.com Call On Kill and Launch test case Started");
		logStep("****** triggers.wfxtriggers.com Call On Kill and Launch test case Started");
		Utils.verifyAPICal("Smoke", "WFXTrigger");

	}

	/*
	 * Factual call is blocked, hence expected to not present this call in charles
	 * session from 12.6 builds onwards...
	 */
	@Test(priority = 604, enabled = true)
	@Description("Factual Call verification on Kill and Launch")
	public void Verify_LocationWFXTriggers_Call_On_KillAndLaunch() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** location.wfxtriggers.com Call on Kill and Launch test case Started");
		logStep("location.wfxtriggers.com Call on Kill and Launch test case Started");
		Utils.verifyAPICal("Smoke", "LocationWFX", false);

	}

	/*
	 * This method validates Amazon SDK version
	 */
	@Test(priority = 625, enabled = true)
	@Description("Validating 'adsdk' parameter of Amazon aax call ")
	public void Validate_Amazon_SDK_adsdk_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Amazon SDK version i.e. 'adsdk' parameter of Amazon aax call");
		logStep("****** Validating Amazon SDK version i.e. 'adsdk' parameter of Amazon aax call");
		Utils.validate_Amazon_aax_call_parameter("Smoke", "Amazon", "adsdk",
				properties.getProperty("AmazonSDKVersion"));

	}

	/*
	 * This method validates Google Mobile Ads SDK Version i.e. GMA SDK
	 */
	@Test(priority = 626, enabled = true)
	@Description("Validating Google Mobile Ads SDK version of gampad call ")
	public void Validate_GMA_SDK_version() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Google Mobile Ads SDK Version i.e. 'js' parameter of gampad call");
		logStep("Validating Google Mobile Ads SDK Version i.e. 'js' parameter of gampad call");
		Utils.validate_Noncustom_param_val_of_gampad("Smoke", "Marquee", "js", properties.getProperty("GMASDKVersion"));

	}

	/*
	 * This method validates Criteo SDK Version
	 */
	@Test(priority = 627, enabled = true)
	@Description("Validating 'sdkVersion' parameter of Criteo SDK config app call ")
	public void Validate_Criteo_SDK_config_app_Call_sdkVersion_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating 'sdkVersion' parameter of Criteo SDK config app call");
		logStep("****** Validating 'sdkVersion' parameter of Criteo SDK config app call");
		Utils.validate_Criteo_SDK_config_app_call_parameter("Smoke", "Criteo", "sdkVersion",
				properties.getProperty("CriteoSDKVersion"));
	}

	/*
	 * This method validates Criteo Bidder API (invapp v2) call response code
	 */
	@Test(priority = 651, enabled = true)
	@Description("Validating Criteo Bidder API (invapp v2) call response code")
	public void Validate_Criteo_SDK_Bidder_API_Call_Response_Code() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Criteo Bidder API (invapp v2) Call Response Code");
		logStep("****** Validating Criteo Bidder API (invapp v2) Call Response Code");
		Utils.verifyCriteo_inapp_v2_Call_ReponseStatusCode("Smoke", "Criteo", "status",
				properties.getProperty("CriteoResponseCode"));
	}

	/*
	 * This method validates Initialization API (config app) call response code
	 */
	@Test(priority = 652, enabled = true)
	@Description("Validating Criteo Initialization API (config app) call response code")
	public void Validate_Criteo_SDK_Initialization_API_Call_Response_Code() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Criteo Initialization API (config app) Call Response Code");
		logStep("****** Validating Criteo Initialization API (config app) Call Response Code");
		Utils.verifyCriteo_config_app_Call_ReponseStatusCode("Smoke", "Criteo", "status",
				properties.getProperty("CriteoResponseCode"));
	}

	/*
	 * This method validates Initialization API (config app) call response parameter
	 * 'csmEnabled'
	 */
	@Test(priority = 653, enabled = true)
	@Description("Validating Criteo Initialization API (config app) call response parameter 'csmEnabled' value")
	public void Validate_Criteo_SDK_Initialization_API_Call_Response_Parameter_csmEnabled() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"****** Validating csmEnabled parameter value in Criteo Initialization API (config app) Call Response");
		logStep("****** Validating csmEnabled parameter value in Criteo Initialization API (config app) Call Response");
		Utils.validate_Criteo_SDK_config_app_call_response_parameter("Smoke", "Criteo", "csmEnabled",
				properties.getProperty("csmEnabled"));
	}

	/*
	 * This method validates Initialization API (config app) call response parameter
	 * 'liveBiddingEnabled'
	 */
	@Test(priority = 654, enabled = true)
	@Description("Validating Criteo Initialization API (config app) call response parameter 'liveBiddingEnabled' value")
	public void Validate_Criteo_SDK_Initialization_API_Call_Response_Parameter_liveBiddingEnabled() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"****** Validating liveBiddingEnabled parameter value in Criteo Initialization API (config app) Call Response");
		logStep("****** Validating liveBiddingEnabled parameter value in Criteo Initialization API (config app) Call Response");
		Utils.validate_Criteo_SDK_config_app_call_response_parameter("Smoke", "Criteo", "liveBiddingEnabled",
				properties.getProperty("liveBiddingEnabled"));
	}

	/*
	 * This method validates Initialization API (config app) call response parameter
	 * 'liveBiddingTimeBudgetInMillis'
	 * 
	 * @Test(priority = 655, enabled = true)
	 * 
	 * @Description("Validating Criteo Initialization API (config app) call response parameter 'liveBiddingTimeBudgetInMillis' value"
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

	/*
	 * This method validates Initialization API (config app) call response parameter
	 * 'prefetchOnInitEnabled'
	 */
	@Test(priority = 656, enabled = true)
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

	/*
	 * This method validates Initialization API (config app) call response parameter
	 * 'remoteLogLevel'
	 */
	@Test(priority = 657, enabled = true)
	@Description("Validating Criteo Initialization API (config app) call response parameter 'remoteLogLevel' value")
	public void Validate_Criteo_SDK_Initialization_API_Call_Response_Parameter_remoteLogLevel() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"****** Validating remoteLogLevel parameter value in Criteo Initialization API (config app) Call Response");
		logStep("****** Validating remoteLogLevel parameter value in Criteo Initialization API (config app) Call Response");
		Utils.validate_Criteo_SDK_config_app_call_response_parameter("Smoke", "Criteo", "remoteLogLevel",
				properties.getProperty("remoteLogLevel"));
	}

	/*
	 * This method validates Seasonal Hub details page ad call
	 */
	@Test(priority = 700, enabled = true)
	@Description("Verify Seasonal Hub details page iu on FTL")
	public void Verify_Seasonal_Hub_Details_Page_AdCall_On_FTL() throws Exception {
		System.out.println("==============================================");
		// Functions.retryclear();
		System.out.println("****** Seasonal Hub Details page validation test cases Started");
		logStep("****** Seasonal Hub Details page validation test cases Started");
		addrScreen.enternewAddress(false, "Atlanta, Georgia");
		TestBase.waitForMilliSeconds(10000);
		Utils.navigateTofeedCard("seasonalhub", false, false);
		Functions.archive_folder("Charles");
		sScreen.verifyPubadCal_SeasonalHub("Smoke", "SeasonalHub(Details)");
		hmTab.clickonHomeTab();
	}

}

package com.twc.ios.app.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.twc.ios.app.charlesfunctions.CharlesProxy;
import com.twc.ios.app.functions.Functions;
import com.twc.ios.app.general.ParseForVideoOrderedList;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.TwcIosBaseTest;
import com.twc.ios.app.general.Utils;
import com.twc.ios.app.pages.AddressScreen;
import com.twc.ios.app.pages.AndroidAddressScreen;
import com.twc.ios.app.pages.AndroidDailyNavTab;
import com.twc.ios.app.pages.AndroidHomeNavTab;
import com.twc.ios.app.pages.AndroidHourlyNavTab;
import com.twc.ios.app.pages.AndroidNewsCardScreen;
import com.twc.ios.app.pages.AndroidRadarNavTab;
import com.twc.ios.app.pages.AndroidSeasonalHubCardScreen;
import com.twc.ios.app.pages.AndroidSettingsScreen;
import com.twc.ios.app.pages.AndroidVideoNavTab;
import com.twc.ios.app.pages.DailyNavTab;
import com.twc.ios.app.pages.HomeNavTab;
import com.twc.ios.app.pages.HourlyNavTab;
import com.twc.ios.app.pages.NewsCardScreen;
import com.twc.ios.app.pages.PlanningCardScreen;
import com.twc.ios.app.pages.RadarNavTab;
import com.twc.ios.app.pages.SeasonalHubCardScreen;
import com.twc.ios.app.pages.SettingsScreen;
import com.twc.ios.app.pages.VideoNavTab;

import java.io.File;

import org.testng.annotations.Listeners;

import org.testng.annotations.BeforeClass;

//import ru.yandex.qatools.allure.annotations.Title;
import io.qameta.allure.Description;

@Listeners(value = com.twc.ios.app.general.MyTestListenerAdapter.class)
public class AndroidCriteoAndAmazonBidTest extends TwcIosBaseTest {

	private static final String CONFIG_FILE_PATH = "charles_common.config";
	private static final String BN_SEVERE1_CONFIG_FILE_PATH = "BNSevere1charles_common.config";
	private static final String BN_SEVERE2_CONFIG_FILE_PATH = "BNSevere2charles_common.config";
	private static final String CRITEO_CONFIG_FILE_PATH = "Criteocharles_common.config";

	// public static CharlesProxy proxy;
	public File configFile;
	AndroidHourlyNavTab hrTab;
	AndroidDailyNavTab dTab;
	AndroidHomeNavTab hmTab;
	AndroidRadarNavTab rTab;
	AndroidVideoNavTab vTab;
	AndroidAddressScreen addrScreen;
	AndroidSeasonalHubCardScreen sScreen;
	AndroidNewsCardScreen nScreen;
	AndroidSettingsScreen stScreen;

	@BeforeClass(alwaysRun = true)
	@Description("BeforeClass")
	public void beforeClass() {
		System.out.println("****** Criteo and Amazon Bid Parameters validation Test Started");
		logStep("****** Criteo and Amazon Bid Parameters validation Test Started");
		// this.configFile = this.charlesGeneralConfigFile(CONFIG_FILE_PATH);
		// proxy = new CharlesProxy("localhost", 8111, CONFIG_FILE_PATH);
		//this.configFile = this.rewriteRuleToOverrideGeoIpCountry(CRITEO_CONFIG_FILE_PATH, "US");
		this.configFile = this.rewriteRuleToEnableUSAWhenNoTunnelBearAndroid(CRITEO_CONFIG_FILE_PATH, "usa", "US", "WA");
		proxy = new CharlesProxy("localhost", 8111, CRITEO_CONFIG_FILE_PATH);
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
		//stScreen.getAppVersion();
		Functions.close_launchAppAndroid();
		stScreen.gettingApkVersion_UPSX();
		Functions.archive_folder("Charles");
		proxy.disableRewriting();
		proxy.quitCharlesProxy();
		try {
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

		System.out.println("****** Criteo and Amazon Bid Parameters validation Test Ended");
		logStep("****** Criteo and Amazon Bid Parameters validation Test Ended");
	}
	
	@Test(priority = 0)
	@Description("Updating Device Proxy Details and Launch the App")
	public void beforeTest() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Updating Device Proxy Details and Launch the App test case Started");
		logStep("****** Updating Device Proxy Details and Launch the App test case Started");
		// Preconditions
		Functions.capabilities();
		Functions.Appium_Autostart();
		Utils.getCurrentMacIPAddressAndSetandroidProxy(true, true);
		// Enable rewriting on Charles install/launch TWC to rewrite geoipcountry to US
		proxy.enableRewriting();
		proxy.startRecording();
		proxy.clearCharlesSession();
		Functions.archive_folder("Charles");
		//Functions.launchtheApp("true");
		Functions.launchtheAndroidApp();
		System.out.println("App launched ");
		logStep("App launched ");
		hrTab = new AndroidHourlyNavTab(Ad);
		dTab = new AndroidDailyNavTab(Ad);
		hmTab = new AndroidHomeNavTab(Ad);
		rTab = new AndroidRadarNavTab(Ad);
		vTab = new AndroidVideoNavTab(Ad);
		addrScreen = new AndroidAddressScreen(Ad);
		sScreen = new AndroidSeasonalHubCardScreen(Ad);
		nScreen = new AndroidNewsCardScreen(Ad);
		stScreen = new AndroidSettingsScreen(Ad);
	}

	@Test(priority = 50, enabled = true)
	@Description("Kill and Launch the app for aax calls verification")
	public void kill_and_Launch_app_for_aax_calls() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Kill and Launch the app for aax calls");
		logStep("Kill and Launch the app for aax calls");
		//stScreen.enableTestModeForAmazonAds();
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		Functions.archive_folder("Charles");
		//addrScreen.enternewAddress(false, "Atlanta, Georgia");
		addrScreen.enter_requiredLocation("30303");
		TestBase.waitForMilliSeconds(20000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.get_v3_wx_forecast_daily_15day_data();
	}

	
	@Test(priority = 51, enabled = true)
	@Description("Verify amazon aax homescreen adhesive preload ad call")
	public void Verify_amazon_aax_preload_homescreen_adhesive_adcall() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== amazon aax homescreen adhesive preload ad call ====================");

		System.out.println("****** amazon aax homescreen adhesive preload ad call validation Started");
		logStep("****** amazon aax homescreen adhesive preload ad call validation Started");

		Utils.verifyAAX_SlotId("Smoke", "Pulltorefresh");

	}
	 

	@Test(priority = 52, enabled = true)
	@Description("Verify amazon aax Feed1 preload ad call")
	public void Verify_amazon_aax_preload_feed1_adcall() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== amazon aax feed1 preload ad call ====================");
		System.out.println("****** amazon aax feed1 preload ad call validation Started");
		logStep("****** amazon aax feed1 preload ad call validation Started");
		Utils.verifyAAX_SlotId("Smoke", "Feed1", true);

	}

	@Test(priority = 53, enabled = true)
	@Description("Verify amazon aax Feed2 preload ad call")
	public void Verify_amazon_aax_preload_feed2_adcall() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== amazon aax feed2 preload ad call ====================");
		System.out.println("****** amazon aax feed2 preload ad call validation Started");
		logStep("****** amazon aax feed2 preload ad call validation Started");
		Utils.verifyAAX_SlotId("Smoke", "Feed2", true);

	}

	@Test(priority = 54, enabled = true)
	@Description("Verify amazon aax Feed3 preload ad call")
	public void Verify_amazon_aax_preload_feed3_adcall() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== amazon aax feed3 preload ad call ====================");
		System.out.println("****** amazon aax feed3 preload ad call validation Started");
		logStep("****** amazon aax feed3 preload ad call validation Started");
		Utils.verifyAAX_SlotId("Smoke", "Feed3", false);

	}

	@Test(priority = 55, enabled = true)
	@Description("Verify amazon aax Feed4 preload ad call")
	public void Verify_amazon_aax_preload_feed4_adcall() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== amazon aax feed4 preload ad call ====================");
		System.out.println("****** amazon aax feed4 preload ad call validation Started");
		logStep("****** amazon aax feed4 preload ad call validation Started");
		Utils.verifyAAX_SlotId("Smoke", "Feed4", false);

	}

	@Test(priority = 57, enabled = true)
	@Description("Verify amazon aax PreRollVideo preload ad call")
	public void Verify_amazon_aax_preload_PreRollVideo_adcall() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== amazon PreRollVideo preload ad call ====================");
		System.out.println("****** amazon aax PreRollVideo preload ad call validation Started");
		logStep("****** amazon aax PreRollVideo preload ad call validation Started");
		Utils.verifyAAX_SlotId("Smoke", "PreRollVideo");

	}

	@Test(priority = 58, enabled = true)
	@Description("Verify amazon aax map details preload ad call")
	public void Verify_amazon_aax_preload_map_details_adcall() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== amazon aax map details preload ad call ====================");
		System.out.println("****** amazon aax Map details preload ad call validation Started");
		logStep("****** amazon aax Map details preload ad call validation Started");
		Utils.verifyAAX_SlotId("Smoke", "Map");

	}

	@Test(priority = 59, enabled = true)
	@Description("Verify amazon aax Daily Details preload ad call")
	public void Verify_amazon_aax_preload_Daily_details_adcall() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== amazon aax Daily Details preload ad call ====================");
		System.out.println("****** amazon aax Daily Details preload ad call validation Started");
		logStep("****** amazon aax Daily Details preload ad call validation Started");
		Utils.verifyAAX_SlotId("Smoke", "Daily(10day)");

	}

	@Test(priority = 60, enabled = true)
	@Description("Verify amazon aax Hourly Details preload ad call")
	public void Verify_amazon_aax_preload_Hourly_details_adcall() throws Exception {
		System.out.println("==============================================");
		System.out
				.println("=========================== amazon aax Hourly Details preload ad call ====================");
		System.out.println("****** amazon aax Hourly Details preload ad call validation Started");
		logStep("****** amazon aax Hourly Details preload ad call validation Started");
		Utils.verifyAAX_SlotId("Smoke", "Hourly");
	}
	
	@Test(priority = 61, enabled = true)
	@Description("Verify amazon aax Hourly1 Bigad preload ad call")
	public void Verify_amazon_aax_preload_Hourly1_bigad_adcall() throws Exception {
		System.out.println("==============================================");
		System.out
				.println("=========================== amazon aax Hourly1 Bigad preload ad call ====================");
		System.out.println("****** amazon aax Hourly1 Bigad preload ad call validation Started");
		logStep("****** amazon aax Hourly1 Bigad preload ad call validation Started");
		Utils.verifyAAX_SlotId("Smoke", "Hourly1");
	}
	
	@Test(priority = 62, enabled = true)
	@Description("Verify amazon aax Hourly2 Bigad preload ad call")
	public void Verify_amazon_aax_preload_Hourly2_bigad_adcall() throws Exception {
		System.out.println("==============================================");
		System.out
				.println("=========================== amazon aax Hourly2 Bigad preload ad call ====================");
		System.out.println("****** amazon aax Hourly2 Bigad preload ad call validation Started");
		logStep("****** amazon aax Hourly2 Bigad preload ad call validation Started");
		Utils.verifyAAX_SlotId("Smoke", "Hourly2");
	}
	
	@Test(priority = 63, enabled = true)
	@Description("Verify amazon aax Hourly3 Bigad preload ad call")
	public void Verify_amazon_aax_preload_Hourly3_bigad_adcall() throws Exception {
		System.out.println("==============================================");
		System.out
				.println("=========================== amazon aax Hourly3 Bigad preload ad call ====================");
		System.out.println("****** amazon aax Hourly3 Bigad preload ad call validation Started");
		logStep("****** amazon aax Hourly3 Bigad preload ad call validation Started");
		Utils.verifyAAX_SlotId("Smoke", "Hourly3");
	}
	
	/*@Test(priority = 62, enabled = true)
	@Description("Verify amazon aax Covid details preload ad call")
	public void Verify_amazon_aax_preload_Covid_details_adcall() throws Exception {
		System.out.println("==============================================");
		System.out
				.println("=========================== amazon aax Covid details preload ad call ====================");
		System.out.println("****** amazon aax Covid details preload ad call validation Started");
		logStep("****** amazon aax Covid details preload ad call validation Started");
		Utils.verifyAAX_SlotId("Smoke", "Covid");
	}*/
	
	@Test(priority = 65, enabled = true)
	@Description("Validating 'adsdk' parameter of Amazon aax call ")
	public void Validate_Amazon_SDK_adsdk_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Amazon SDK version i.e. 'adsdk' parameter of Amazon aax call");
		logStep("****** Validating Amazon SDK version i.e. 'adsdk' parameter of Amazon aax call");
		Utils.validate_Amazon_aax_call_parameter("Smoke", "Amazon", "adsdk",
				properties.getProperty("AmazonSDKVersion"));

	}

	@Test(priority = 70, enabled = true)
	@Description("Validating Google Mobile Ads SDK version of gampad call ")
	public void Validate_GMA_SDK_version() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Google Mobile Ads SDK Version i.e. 'js' parameter of gampad call");
		logStep("Validating Google Mobile Ads SDK Version i.e. 'js' parameter of gampad call");
		Utils.validate_Noncustom_param_val_of_gampad("Smoke", "Marquee", "js", properties.getProperty("GMASDKVersion"));

	}

	/*
	 * Below script validates amazon UUID's of amazon calls of feed ads from 5th
	 * feed onwards
	 */

	/*
	 * @Test(priority = 227, enabled = true)
	 * 
	 * @Description("Verify amazon aax values of feed ads") public void
	 * Verify_amazon_aax_feed_ads() throws Exception {
	 * System.out.println("==============================================");
	 * System.out.
	 * println("=========================== amazon slot id's of new feed ad cards ===================="
	 * );
	 * 
	 * System.out.
	 * println("****** amazon aax slot ids of new feed ads validation Started"); //
	 * Functions.verifyAAX_SlotId("Smoke", "News(details)");
	 * Utils.verifyFeedAds_AAX_SlotIds("Smoke", "CleanLaunch"); }
	 */

	@Test(priority = 250, enabled = true)
	@Description("Verify amazon video bid id's")
	public void Verify_amazon_video_adcall_bid_id() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== amazon video ad call bid id's ====================");
		System.out.println("****** amazon video ad call bid id validation Started");
		logStep("****** amazon video ad call bid id validation Started");
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(5000);
		Functions.archive_folder("Charles");
		//Functions.enternewAddress(false, "Atlanta, Georgia");
		//TestBase.waitForMilliSeconds(10000);
		hmTab.clickonHomeTab();
		hmTab.clickonHomeTab();
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.load_amazon_bid_values_from_aaxCalls("Smoke", "PreRollVideo", true);
		proxy.clearCharlesSession();
		// TestBase.waitForMilliSeconds(10000);
		// navigate to Video tab
		vTab.navigateToVideoTab(true, proxy);
		TestBase.waitForMilliSeconds(10000);
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.get_iu_value_of_Feedcall("Smoke", "PreRollVideo");
		Utils.validate_aax_bid_value_with_gampad_bid_value("Smoke", "PreRollVideo", false);
	}

	@Test(priority = 251, enabled = true)
	@Description("Validating Google Interactive Media Ads SDK version of Preroll video call ")
	public void Validate_IMA_SDK_version() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"****** Validating Google Interactive Media Ads SDK version i.e. 'js' parameter of Preroll video call");
		logStep("Validating Google Interactive Media Ads SDK version i.e. 'js' parameter of Preroll video call");
		Utils.validate_Noncustom_param_val_of_gampad("Smoke", "PreRollVideo", "js",
				properties.getProperty("IMASDKVersion"));

	}

	@Test(priority = 301, enabled = true)
	@Description("Enabling Preconfigurations to validate amazon bid id's and criteo parameters")
	public void enable_PreConditions_to_Validate_amazon_bid_ids_And_Criteo_Parameters() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Enabling Preconfigurations to validate amazon bid id's and criteo parameters ====================");
		System.out
				.println("****** Enabling Preconfigurations to validate amazon bid id's and criteo parameters Started");
		logStep("****** Enabling Preconfigurations to validate amazon bid id's and criteo parameters Started");
		//stScreen.select_Airlock_Branch("IOSAUTOMATION02");
		//stScreen.select_Airlock_UserGroup("IOSAUTOMATION");
		Functions.archive_folder("Charles");
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(5000);
		hmTab.clickonHomeTab();
		hmTab.clickonHomeTab();
		
		//Handling Interstitial ad ahead of navigating to all cards
		 
		hrTab.navigateToHourlyTabAndHandleInterstitialAd();
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		
		//addrScreen.enternewAddress(false, "New York City, New York");
		addrScreen.enter_requiredLocation("10003");
		TestBase.waitForMilliSeconds(20000);
		Utils.navigateToAllCardsAndroid(true, true);
		hmTab.clickonHomeTab();
		TestBase.waitForMilliSeconds(5000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		// Utils.get_v3_wx_forecast_daily_15day_data();
				
	}

	
	@Test(priority = 302, enabled = true)
	@Description("Verify homescreen adhesive ad call amazon bid id")
	public void Verify_homescreen_adhesive_ad_call_amazon_bid_id() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== homescreen adhesive ad call amazon bid id ====================");

		System.out.println("****** homescreen adhesive ad call amazon bid id validation Started");
		logStep("****** homescreen adhesive ad call amazon bid id validation Started");
		Utils.validate_aax_bid_value_with_gampad_bid_value("Smoke", "Pulltorefresh", true, false);
	}
	 
	@Test(priority = 303, enabled = true)
	@Description("Verify Feed1 ad call amazon bid id")
	public void Verify_Feed1_ad_call_amazon_bid_id() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Feed1 ad call amazon bid id ====================");
		System.out.println("****** Feed1 ad call amazon bid id validation Started");
		logStep("****** Feed1 ad call amazon bid id validation Started");
		Utils.validate_aax_bid_value_with_gampad_bid_value("Smoke", "Feed1", true, false);
	}

    @Test(priority = 304, enabled = true)
	@Description("Verify Feed2 ad call amazon bid id")
	public void Verify_Feed2_ad_call_amazon_bid_id() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Feed2 ad call amazon bid id ====================");
		System.out.println("****** Feed2 ad call amazon bid id validation Started");
		logStep("****** Feed2 ad call amazon bid id validation Started");
		Utils.validate_aax_bid_value_with_gampad_bid_value("Smoke", "Feed2", true, false);
	}

	/*@Test(priority = 305, enabled = true)
	@Description("Verify Feed3 ad call amazon bid id")
	public void Verify_Feed3_ad_call_amazon_bid_id() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Feed3 ad call amazon bid id ====================");

		System.out.println("****** Feed3 ad call amazon bid id validation Started");
		logStep("****** Feed3 ad call amazon bid id validation Started");
		Utils.validate_aax_bid_value_with_gampad_bid_value("Smoke", "Feed3", true, true);
	}*/

	/*@Test(priority = 306, enabled = true)
	@Description("Verify Feed4 ad call amazon bid id")
	public void Verify_Feed4_ad_call_amazon_bid_id() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Feed4 ad call amazon bid id ====================");

		System.out.println("****** Feed4 ad call amazon bid id validation Started");
		logStep("****** Feed4 ad call amazon bid id validation Started");
		Utils.validate_aax_bid_value_with_gampad_bid_value("Smoke", "Feed4", true, true);
	}*/

	@Test(priority = 307, enabled = true)
	@Description("Verify amazon aax Feed5 ad call")
	public void Verify_amazon_aax_feed5_adcall() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== amazon aax feed5 ad call ====================");

		System.out.println("****** amazon aax feed5 ad call validation Started");
		logStep("****** amazon aax feed5 ad call validation Started");
		Utils.verifyAAX_SlotId("Smoke", "Feed5", false);

	}

	/*@Test(priority = 308, enabled = true)
	@Description("Verify Feed5 ad call amazon bid id")
	public void Verify_Feed5_ad_call_amazon_bid_id() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Feed5 ad call amazon bid id ====================");

		System.out.println("****** Feed5 ad call amazon bid id validation Started");
		logStep("****** Feed5 ad call amazon bid id validation Started");
		Utils.validate_aax_bid_value_with_gampad_bid_value("Smoke", "Feed5", true, true);
	}*/

	/*@Test(priority = 309, enabled = true)
	@Description("Verify amazon aax Feed6 ad call")
	public void Verify_amazon_aax_feed6_adcall() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== amazon aax feed6 ad call ====================");

		System.out.println("****** amazon aax feed6 ad call validation Started");
		logStep("****** amazon aax feed6 ad call validation Started");
		Utils.verifyAAX_SlotId("Smoke", "Feed6", false);

	}*/

	/*@Test(priority = 310, enabled = true)
	@Description("Verify Feed6 ad call amazon bid id")
	public void Verify_Feed6_ad_call_amazon_bid_id() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Feed6 ad call amazon bid id ====================");

		System.out.println("****** Feed6 ad call amazon bid id validation Started");
		logStep("****** Feed6 ad call amazon bid id validation Started");
		Utils.validate_aax_bid_value_with_gampad_bid_value("Smoke", "Feed6", true, true);
	}*/

	@Test(priority = 320, enabled = true)
	@Description("Verify Hourly Details ad call amazon bid id")
	public void Verify_Hourly_Details_ad_call_amazon_bid_id() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Hourly Details ad call amazon bid id ====================");

		System.out.println("****** Hourly Details ad call amazon bid id validation Started");
		logStep("****** Hourly Details ad call amazon bid id validation Started");
		Utils.validate_aax_bid_value_with_gampad_bid_value("Smoke", "Hourly", true, false);
	}

	@Test(priority = 321, enabled = true)
	@Description("Verify Daily Details ad call amazon bid id")
	public void Verify_Daily_Details_ad_call_amazon_bid_id() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Daily Details ad call amazon bid id ====================");

		System.out.println("****** Daily Details ad call amazon bid id validation Started");
		logStep("****** Daily Details ad call amazon bid id validation Started");
		Utils.validate_aax_bid_value_with_gampad_bid_value("Smoke", "Daily(10day)", true, false);
	}

	@Test(priority = 322, enabled = true)
	@Description("Verify Map Details ad call amazon bid id")
	public void Verify_Map_Details_ad_call_amazon_bid_id() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Map Details ad call amazon bid id ====================");

		System.out.println("****** Map Details ad call amazon bid id validation Started");
		logStep("****** Map Details ad call amazon bid id validation Started");
		Utils.validate_aax_bid_value_with_gampad_bid_value("Smoke", "Map", true, false);
	}

	@Test(priority = 323, enabled = true)
	@Description("Verify amazon aax Today Details ad call")
	public void Verify_amazon_aax_Today_details_adcall() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== amazon aax Today Details ad call ====================");

		System.out.println("****** amazon aax Today Details ad call validation Started");
		logStep("****** amazon aax Today Details ad call validation Started");

		Utils.verifyAAX_SlotId("Smoke", "Today", false);

	}

	/*@Test(priority = 324, enabled = true)
	@Description("Verify Today Details ad call amazon bid id")
	public void Verify_Today_Details_ad_call_amazon_bid_id() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Today Details ad call amazon bid id ====================");

		System.out.println("****** Today Details ad call amazon bid id validation Started");
		logStep("****** Today Details ad call amazon bid id validation Started");
		Utils.validate_aax_bid_value_with_gampad_bid_value("Smoke", "Today", true, true);
	}*/

	@Test(priority = 325, enabled = true)
	@Description("Verify amazon aax News Details ad call")
	public void Verify_amazon_aax_News_details_adcall() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== amazon aax News Details ad call ====================");

		System.out.println("****** amazon aax News Details ad call validation Started");
		logStep("****** amazon aax News Details ad call validation Started");

		Utils.verifyAAX_SlotId("Smoke", "News(details)");

	}

	@Test(priority = 327, enabled = true)
	@Description("Verify amazon aax AQ Details ad call")
	public void Verify_amazon_aax_AQ_details_adcall() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== amazon aax AQ Details ad call ====================");

		System.out.println("****** amazon aax AQ Details ad call validation Started");
		logStep("****** amazon aax AQ Details ad call validation Started");

		Utils.verifyAAX_SlotId("Smoke", "Air Quality(Content)");

	}

	@Test(priority = 328, enabled = true)
	@Description("Verify AQ Details ad call amazon bid id")
	public void Verify_AQ_Details_ad_call_amazon_bid_id() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== AQ Details ad call amazon bid id ====================");

		System.out.println("****** AQ Details ad call amazon bid id validation Started");
		logStep("****** AQ Details ad call amazon bid id validation Started");
		Utils.validate_aax_bid_value_with_gampad_bid_value("Smoke", "Air Quality(Content)", true, true);
	}

	@Test(priority = 335, enabled = true)
	@Description("Verify amazon aax Seasonal Hub Details ad call")
	public void Verify_amazon_aax_SeasonalHub_details_adcall() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== amazon aax Seasonal Hub Details ad call ====================");

		System.out.println("****** amazon aax Seasonal Hub Details ad call validation Started");
		logStep("****** amazon aax Seasonal Hub Details ad call validation Started");

		Utils.verifyAAX_SlotId("Smoke", "SeasonalHub(Details)", false);

	}

	/*@Test(priority = 336, enabled = true)
	@Description("Verify Seasonal Hub Details ad call amazon bid id")
	public void Verify_SeasonalHub_Details_ad_call_amazon_bid_id() throws Exception {
		System.out.println("==============================================");
		System.out
				.println("=========================== Seasonal Hub Details ad call amazon bid id ====================");

		System.out.println("****** Seasonal Hub Details ad call amazon bid id validation Started");
		logStep("****** Seasonal Hub Details ad call amazon bid id validation Started");
		Utils.validate_aax_bid_value_with_gampad_bid_value("Smoke", "SeasonalHub(Details)", true, true);
	}*/

	@Test(priority = 337, enabled = true)
	@Description("Verify amazon aax AlertCenter ad call")
	public void Verify_amazon_aax_AlertCenter_adcall() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== amazon aax AlertCenter ad call ====================");

		System.out.println("****** amazon aax AlertCenter ad call validation Started");
		logStep("****** amazon aax AlertCenter ad call validation Started");

		Utils.verifyAAX_SlotId("Smoke", "MyAlerts");

	}

	@Test(priority = 338, enabled = true)
	@Description("Verify AlertCenter ad call amazon bid id")
	public void Verify_AlertCenter_ad_call_amazon_bid_id() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== AlertCenter ad call amazon bid id ====================");

		System.out.println("****** AlertCenter ad call amazon bid id validation Started");
		logStep("****** AlertCenter ad call amazon bid id validation Started");
		Utils.validate_aax_bid_value_with_gampad_bid_value("Smoke", "MyAlerts", true, true);

	}

	/*@Test(priority = 339, enabled = true)
	@Description("Verify amazon aax Health Boat & Beach Details ad call")
	public void Verify_amazon_aax_Health_BoatAndBeach_Details_adcall() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== amazon aax Health Boat & Beach Details ad call ====================");

		System.out.println("****** amazon aax Health Boat & Beach Details ad call validation Started");
		logStep("****** amazon aax Health Boat & Beach Details ad call validation Started");

		Utils.verifyAAX_SlotId("Smoke", "Health(boatAndBeach)", false);

	}*/

	/*@Test(priority = 340, enabled = true)
	@Description("Verify Health Boat & Beach Details ad call amazon bid id")
	public void Verify_Health_BoatAndBeach_Details_ad_call_amazon_bid_id() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Health Boat & Beach Details ad call amazon bid id ====================");

		System.out.println("****** Health Boat & Beach Details ad call amazon bid id validation Started");
		logStep("****** Health Boat & Beach Details ad call amazon bid id validation Started");
		Utils.validate_aax_bid_value_with_gampad_bid_value("Smoke", "Health(boatAndBeach)", true, true);
	}*/

	/*@Test(priority = 341, enabled = true)
	@Description("Verify amazon aax Health Running Details ad call")
	public void Verify_amazon_aax_Running_Details_adcall() throws Exception {
		System.out.println("==============================================");
		System.out
				.println("=========================== amazon aax Health Running Details ad call ====================");

		System.out.println("****** amazon aax Health Running Details ad call validation Started");
		logStep("****** amazon aax Health Running Details ad call validation Started");

		Utils.verifyAAX_SlotId("Smoke", "Health(goRun)", false);

	}*/

	/*@Test(priority = 342, enabled = true)
	@Description("Verify Health Running Details ad call amazon bid id")
	public void Verify_Health_Running_Details_ad_call_amazon_bid_id() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Health Running Details ad call amazon bid id ====================");

		System.out.println("****** Health Running Details ad call amazon bid id validation Started");
		logStep("****** Health Running Details ad call amazon bid id validation Started");
		Utils.validate_aax_bid_value_with_gampad_bid_value("Smoke", "Health(goRun)", true, true);
	}*/

	@Test(priority = 343, enabled = true)
	@Description("Verify amazon aax Flu Articles ad call")
	public void Verify_amazon_aax_Flu_Articles_adcall() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== amazon aax Flu Articles ad call ====================");

		System.out.println("****** amazon aax Flu Articles ad call validation Started");
		logStep("****** amazon aax Flu Articles ad call validation Started");

		Utils.verifyAAX_SlotId("Smoke", "Health(coldAndFluArticles)");

	}

	@Test(priority = 344, enabled = true)
	@Description("Verify Health Flu Articles ad call amazon bid id")
	public void Verify_Health_Flu_Articles_ad_call_amazon_bid_id() throws Exception {
		System.out.println("==============================================");
		System.out
				.println("=========================== Health Flu Articles ad call amazon bid id ====================");

		System.out.println("****** Health Flu Articles ad call amazon bid id validation Started");
		logStep("****** Health Flu Articles ad call amazon bid id validation Started");
		Utils.validate_aax_bid_value_with_gampad_bid_value("Smoke", "Health(coldAndFluArticles)", true, true);
	}

	@Test(priority = 345, enabled = true)
	@Description("Verify amazon aax Allergy Articles ad call")
	public void Verify_amazon_aax_Allergy_Articles_adcall() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== amazon aax Allergy Articles ad call ====================");

		System.out.println("****** amazon aax Allergy Articles ad call validation Started");
		logStep("****** amazon aax Allergy Articles ad call validation Started");
		Utils.verifyAAX_SlotId("Smoke", "Health(allergyArticles)");

	}

	@Test(priority = 346, enabled = true)
	@Description("Verify Health Allergy Articles ad call amazon bid id")
	public void Verify_Health_Allergy_Articles_ad_call_amazon_bid_id() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Health Allergy Articles ad call amazon bid id ====================");

		System.out.println("****** Health Allergy Articles ad call amazon bid id validation Started");
		logStep("****** Health Allergy Articles ad call amazon bid id validation Started");
		Utils.validate_aax_bid_value_with_gampad_bid_value("Smoke", "Health(allergyArticles)", true, true);
	}
	
	@Test(priority = 375, enabled = true)
	@Description("Verify Criteo SDK inapp v2 call")
	public void Verify_Criteo_SDK_inapp_v2_Call() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Criteo SDK inapp/v2 call ====================");

		System.out.println("****** Criteo SDK inapp/v2 call validation Started");
		logStep("****** Criteo SDK inapp/v2 call validation Started");

		Utils.verifyCriteo_inapp_v2_Call("Smoke", "Criteo");

	}

	@Test(priority = 376, enabled = true)
	@Description("Verify Criteo SDK config app call")
	public void Verify_Criteo_SDK_config_app_Call() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Criteo SDK config/app call ====================");

		System.out.println("****** Criteo SDK config/app call validation Started");
		logStep("****** Criteo SDK config/app call validation Started");

		Utils.verifyCriteo_config_app_Call("Smoke", "Criteo");

	}

	@Test(priority = 377, enabled = true)
	@Description("Validating 'cpId' parameter of Criteo SDK config app call ")
	public void Validate_Criteo_SDK_config_app_Call_cpId_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating 'cpId' parameter of Criteo SDK config app call");
		logStep("****** Validating 'cpId' parameter of Criteo SDK config app call");
		Utils.validate_Criteo_SDK_config_app_call_parameter("Smoke", "Criteo", "cpId",
				properties.getProperty("CriteoCpId"));

	}

	@Test(priority = 378, enabled = true)
	@Description("Validating 'bundleId' parameter of Criteo SDK config app call ")
	public void Validate_Criteo_SDK_config_app_Call_bundleId_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating 'bundleId' parameter of Criteo SDK config app call");
		logStep("****** Validating 'bundleId' parameter of Criteo SDK config app call");
		Utils.validate_Criteo_SDK_config_app_call_parameter("Smoke", "Criteo", "bundleId",
				properties.getProperty("CriteoBundleId"));

	}

	@Test(priority = 379, enabled = true)
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
	@Test(priority = 401, enabled = true)
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
	@Test(priority = 402, enabled = true)
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
	@Test(priority = 403, enabled = true)
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
	@Test(priority = 404, enabled = true)
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
	 * @Test(priority = 405, enabled = true)
	 * 
	 * @Description("Validating Criteo Initialization API (config app) call response parameter 'liveBiddingTimeBudgetInMillis' value"
	 * ) public void
	 * Validate_Criteo_SDK_Initialization_API_Call_Response_Parameter_liveBiddingTimeBudgetInMillis
	 * () throws Exception {
	 * System.out.println("==============================================");
	 * System.out.println(
	 * "****** Validating liveBiddingTimeBudgetInMillis parameter value in Criteo Initialization API (config app) Call Response"
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
	@Test(priority = 406, enabled = true)
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
	@Test(priority = 407, enabled = true)
	@Description("Validating Criteo Initialization API (config app) call response parameter 'remoteLogLevel' value")
	public void Validate_Criteo_SDK_Initialization_API_Call_Response_Parameter_remoteLogLevel() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"****** Validating remoteLogLevel parameter value in Criteo Initialization API (config app) Call Response");
		logStep("****** Validating remoteLogLevel parameter value in Criteo Initialization API (config app) Call Response");
		Utils.validate_Criteo_SDK_config_app_call_response_parameter("Smoke", "Criteo", "remoteLogLevel",
				properties.getProperty("remoteLogLevel"));
	}
	
	@Test(priority = 411, enabled = true)
	@Description("Verify cpm parameter of Criteo SDK inapp v2 call with homescreen adhesive call")
	public void Verify_Criteo_SDK_inapp_v2_Call_cpm_parameter_with_homescreen_adhesive_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call cpm parameter with  homescreen adhesive call====================");

		System.out.println(
				"****** Criteo SDK invapp v2 call cpm parameter with  homescreen adhesive call validation Started");
		logStep("****** Criteo SDK invapp v2 call cpm parameter with homescreen adhesive call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Pulltorefresh", "cpm",
				true);
	}

	@Test(priority = 412, enabled = true)
	@Description("Verify size parameter of Criteo SDK inapp v2 call with homescreen adhesive call")
	public void Verify_Criteo_SDK_inapp_v2_Call_size_parameter_with_homescreen_adhesive_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call size parameter with homescreen adhesive call====================");

		System.out.println(
				"****** Criteo SDK invapp v2 call size parameter with homescreen adhesive call validation Started");
		logStep("****** Criteo SDK invapp v2 call size parameter with homescreen adhesive call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Pulltorefresh", "size",
				true);
	}

	@Test(priority = 413, enabled = true)
	@Description("Verify displayUrl parameter of Criteo SDK inapp v2 call with homescreen adhesive call")
	public void Verify_Criteo_SDK_inapp_v2_Call_displayUrl_parameter_with_homescreen_adhesive_gampad_call()
			throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call displayUrl parameter with homescreen adhesive call====================");

		System.out.println(
				"****** Criteo SDK invapp v2 call displayUrl parameter with homescreen adhesive call validation Started");
		logStep("****** Criteo SDK invapp v2 call displayUrl parameter with homescreen adhesive call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Pulltorefresh",
				"displayUrl", true);
	}
	 

	@Test(priority = 414, enabled = true)
	@Description("Verify cpm parameter of Criteo SDK inapp v2 call with Feed1 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_cpm_parameter_with_Feed1_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call cpm parameter with  Feed1 call====================");

		System.out.println("****** Criteo SDK invapp v2 call cpm parameter with  Feed1 validation Started");
		logStep("****** Criteo SDK invapp v2 call cpm parameter with Feed1 call validation Started");

		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Feed1", "cpm", true);
	}

	@Test(priority = 415, enabled = true)
	@Description("Verify size parameter of Criteo SDK inapp v2 call with Feed1 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_size_parameter_with_Feed1_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call size parameter with Feed1 call====================");

		System.out.println("****** Criteo SDK invapp v2 call size parameter with Feed1 call validation Started");
		logStep("****** Criteo SDK invapp v2 call size parameter with Feed1 call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Feed1", "size", true);
	}

	@Test(priority = 416, enabled = true)
	@Description("Verify displayUrl parameter of Criteo SDK inapp v2 call with Feed1 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_displayUrl_parameter_with_Feed1_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call displayUrl parameter with Feed1 call====================");

		System.out.println("****** Criteo SDK invapp v2 call displayUrl parameter with Feed1 call validation Started");
		logStep("****** Criteo SDK invapp v2 call displayUrl parameter with Feed1 call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Feed1", "displayUrl",
				true);
	}

	@Test(priority = 417, enabled = true)
	@Description("Verify cpm parameter of Criteo SDK inapp v2 call with Feed2 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_cpm_parameter_with_Feed2_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call cpm parameter with  Feed2 call====================");

		System.out.println("****** Criteo SDK invapp v2 call cpm parameter with  Feed2 validation Started");
		logStep("****** Criteo SDK invapp v2 call cpm parameter with Feed2 call validation Started");

		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Feed2", "cpm", true);
	}

	@Test(priority = 418, enabled = true)
	@Description("Verify size parameter of Criteo SDK inapp v2 call with Feed2 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_size_parameter_with_Feed2_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call size parameter with Feed2 call====================");

		System.out.println("****** Criteo SDK invapp v2 call size parameter with Feed2 call validation Started");
		logStep("****** Criteo SDK invapp v2 call size parameter with Feed2 call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Feed2", "size", true);
	}

	@Test(priority = 419, enabled = true)
	@Description("Verify displayUrl parameter of Criteo SDK inapp v2 call with Feed2 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_displayUrl_parameter_with_Feed2_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call displayUrl parameter with Feed2 call====================");

		System.out.println("****** Criteo SDK invapp v2 call displayUrl parameter with Feed2 call validation Started");
		logStep("****** Criteo SDK invapp v2 call displayUrl parameter with Feed2 call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Feed2", "displayUrl",
				true);
	}

	/*@Test(priority = 420, enabled = true)
	@Description("Verify cpm parameter of Criteo SDK inapp v2 call with Feed3 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_cpm_parameter_with_Feed3_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call cpm parameter with  Feed3 call====================");

		System.out.println("****** Criteo SDK invapp v2 call cpm parameter with  Feed3 validation Started");
		logStep("****** Criteo SDK invapp v2 call cpm parameter with Feed3 call validation Started");

		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Feed3", "cpm", true);
	}

	@Test(priority = 421, enabled = true)
	@Description("Verify size parameter of Criteo SDK inapp v2 call with Feed3 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_size_parameter_with_Feed3_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call size parameter with Feed3 call====================");

		System.out.println("****** Criteo SDK invapp v2 call size parameter with Feed3 call validation Started");
		logStep("****** Criteo SDK invapp v2 call size parameter with Feed3 call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Feed3", "size", true);
	}

	@Test(priority = 422, enabled = true)
	@Description("Verify displayUrl parameter of Criteo SDK inapp v2 call with Feed3 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_displayUrl_parameter_with_Feed3_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call displayUrl parameter with Feed3 call====================");

		System.out.println("****** Criteo SDK invapp v2 call displayUrl parameter with Feed3 call validation Started");
		logStep("****** Criteo SDK invapp v2 call displayUrl parameter with Feed3 call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Feed3", "displayUrl",
				true);
	}*/

	/*@Test(priority = 423, enabled = true)
	@Description("Verify cpm parameter of Criteo SDK inapp v2 call with Feed4 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_cpm_parameter_with_Feed4_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call cpm parameter with  Feed4 call====================");

		System.out.println("****** Criteo SDK invapp v2 call cpm parameter with  Feed4 validation Started");
		logStep("****** Criteo SDK invapp v2 call cpm parameter with Feed4 call validation Started");

		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Feed4", "cpm", true);
	}

	@Test(priority = 424, enabled = true)
	@Description("Verify size parameter of Criteo SDK inapp v2 call with Feed4 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_size_parameter_with_Feed4_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call size parameter with Feed4 call====================");

		System.out.println("****** Criteo SDK invapp v2 call size parameter with Feed4 call validation Started");
		logStep("****** Criteo SDK invapp v2 call size parameter with Feed4 call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Feed4", "size", true);
	}

	@Test(priority = 425, enabled = true)
	@Description("Verify displayUrl parameter of Criteo SDK inapp v2 call with Feed4 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_displayUrl_parameter_with_Feed4_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call displayUrl parameter with Feed4 call====================");

		System.out.println("****** Criteo SDK invapp v2 call displayUrl parameter with Feed4 call validation Started");
		logStep("****** Criteo SDK invapp v2 call displayUrl parameter with Feed4 call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Feed4", "displayUrl",
				true);
	}*/

	/*@Test(priority = 426, enabled = true)
	@Description("Verify cpm parameter of Criteo SDK inapp v2 call with Feed5 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_cpm_parameter_with_Feed5_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call cpm parameter with  Feed5 call====================");

		System.out.println("****** Criteo SDK invapp v2 call cpm parameter with  Feed5 validation Started");
		logStep("****** Criteo SDK invapp v2 call cpm parameter with Feed5 call validation Started");

		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Feed5", "cpm", true);
	}

	@Test(priority = 427, enabled = true)
	@Description("Verify size parameter of Criteo SDK inapp v2 call with Feed5 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_size_parameter_with_Feed5_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call size parameter with Feed5 call====================");

		System.out.println("****** Criteo SDK invapp v2 call size parameter with Feed5 call validation Started");
		logStep("****** Criteo SDK invapp v2 call size parameter with Feed5 call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Feed5", "size", true);
	}

	@Test(priority = 428, enabled = true)
	@Description("Verify displayUrl parameter of Criteo SDK inapp v2 call with Feed5 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_displayUrl_parameter_with_Feed5_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call displayUrl parameter with Feed5 call====================");

		System.out.println("****** Criteo SDK invapp v2 call displayUrl parameter with Feed5 call validation Started");
		logStep("****** Criteo SDK invapp v2 call displayUrl parameter with Feed5 call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Feed5", "displayUrl",
				true);
	}*/

	/*@Test(priority = 429, enabled = true)
	@Description("Verify cpm parameter of Criteo SDK inapp v2 call with Feed6 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_cpm_parameter_with_Feed6_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call cpm parameter with  Feed6 call====================");

		System.out.println("****** Criteo SDK invapp v2 call cpm parameter with  Feed6 validation Started");
		logStep("****** Criteo SDK invapp v2 call cpm parameter with Feed6 call validation Started");

		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Feed6", "cpm", true);
	}

	@Test(priority = 430, enabled = true)
	@Description("Verify size parameter of Criteo SDK inapp v2 call with Feed6 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_size_parameter_with_Feed6_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call size parameter with Feed6 call====================");

		System.out.println("****** Criteo SDK invapp v2 call size parameter with Feed6 call validation Started");
		logStep("****** Criteo SDK invapp v2 call size parameter with Feed6 call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Feed6", "size", true);
	}

	@Test(priority = 431, enabled = true)
	@Description("Verify displayUrl parameter of Criteo SDK inapp v2 call with Feed6 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_displayUrl_parameter_with_Feed6_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call displayUrl parameter with Feed6 call====================");

		System.out.println("****** Criteo SDK invapp v2 call displayUrl parameter with Feed6 call validation Started");
		logStep("****** Criteo SDK invapp v2 call displayUrl parameter with Feed6 call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Feed6", "displayUrl",
				true);
	}*/

	/*@Test(priority = 432, enabled = true)
	@Description("Verify cpm parameter of Criteo SDK inapp v2 call with Hourly Details call")
	public void Verify_Criteo_SDK_inapp_v2_Call_cpm_parameter_with_hourly_details_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call cpm parameter with hourly details call====================");

		System.out
				.println("****** Criteo SDK invapp v2 call cpm parameter with Hourly Details call validation Started");
		logStep("****** Criteo SDK invapp v2 call cpm parameter with Hourly Details call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Hourly", "cpm", true);
	}

	@Test(priority = 433, enabled = true)
	@Description("Verify size parameter of Criteo SDK inapp v2 call with Hourly Details call")
	public void Verify_Criteo_SDK_inapp_v2_Call_size_parameter_with_hourly_details_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call size parameter with hourly details call====================");

		System.out
				.println("****** Criteo SDK invapp v2 call size parameter with hourly details call validation Started");
		logStep("****** Criteo SDK invapp v2 call size parameter with hourly details call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Hourly", "size", true);
	}

	@Test(priority = 434, enabled = true)
	@Description("Verify displayUrl parameter of Criteo SDK inapp v2 call with Hourly Details call")
	public void Verify_Criteo_SDK_inapp_v2_Call_displayUrl_parameter_with_hourly_details_gampad_call()
			throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call displayUrl parameter with hourly details call====================");

		System.out.println(
				"****** Criteo SDK invapp v2 call displayUrl parameter with hourly details call validation Started");
		logStep("****** Criteo SDK invapp v2 call displayUrl parameter with hourly details call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Hourly", "displayUrl",
				true);
	}*/

	/*@Test(priority = 435, enabled = true)
	@Description("Verify cpm parameter of Criteo SDK inapp v2 call with Today Details call")
	public void Verify_Criteo_SDK_inapp_v2_Call_cpm_parameter_with_today_details_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call cpm parameter with Today details call====================");

		System.out.println("****** Criteo SDK invapp v2 call cpm parameter with Today Details call validation Started");
		logStep("****** Criteo SDK invapp v2 call cpm parameter with Today Details call validation Started");

		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Today", "cpm", true);
	}

	@Test(priority = 436, enabled = true)
	@Description("Verify size parameter of Criteo SDK inapp v2 call with Today Details call")
	public void Verify_Criteo_SDK_inapp_v2_Call_size_parameter_with_today_details_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call size parameter with Today details call====================");

		System.out
				.println("****** Criteo SDK invapp v2 call size parameter with Today details call validation Started");
		logStep("****** Criteo SDK invapp v2 call size parameter with Today details call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Today", "size", true);
	}

	@Test(priority = 437, enabled = true)
	@Description("Verify displayUrl parameter of Criteo SDK inapp v2 call with Today Details call")
	public void Verify_Criteo_SDK_inapp_v2_Call_displayUrl_parameter_with_today_details_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call displayUrl parameter with Today details call====================");

		System.out.println(
				"****** Criteo SDK invapp v2 call displayUrl parameter with Today details call validation Started");
		logStep("****** Criteo SDK invapp v2 call displayUrl parameter with Today details call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Today", "displayUrl",
				true);
	}*/

	@Test(priority = 438, enabled = true)
	@Description("Verify cpm parameter of Criteo SDK inapp v2 call with Daily Details call")
	public void Verify_Criteo_SDK_inapp_v2_Call_cpm_parameter_with_daily_details_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call cpm parameter with Daily details call====================");

		System.out.println("****** Criteo SDK invapp v2 call cpm parameter with Daily Details call validation Started");
		logStep("****** Criteo SDK invapp v2 call cpm parameter with Dailly Details call validation Started");

		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Daily(10day)", "cpm",
				true);
	}

	@Test(priority = 439, enabled = true)
	@Description("Verify size parameter of Criteo SDK inapp v2 call with Today Details call")
	public void Verify_Criteo_SDK_inapp_v2_Call_size_parameter_with_daily_details_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call size parameter with Daily details call====================");

		System.out
				.println("****** Criteo SDK invapp v2 call size parameter with Daily details call validation Started");
		logStep("****** Criteo SDK invapp v2 call size parameter with Daily details call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Daily(10day)", "size",
				true);
	}

	@Test(priority = 440, enabled = true)
	@Description("Verify displayUrl parameter of Criteo SDK inapp v2 call with Daily Details call")
	public void Verify_Criteo_SDK_inapp_v2_Call_displayUrl_parameter_with_daily_details_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call displayUrl parameter with Daily details call====================");

		System.out.println(
				"****** Criteo SDK invapp v2 call displayUrl parameter with Daily details call validation Started");
		logStep("****** Criteo SDK invapp v2 call displayUrl parameter with Daily details call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Daily(10day)",
				"displayUrl", true);
	}

	@Test(priority = 441, enabled = true)
	@Description("Verify cpm parameter of Criteo SDK inapp v2 call with Map Details call")
	public void Verify_Criteo_SDK_inapp_v2_Call_cpm_parameter_with_map_details_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call cpm parameter with map details call====================");

		System.out.println("****** Criteo SDK invapp v2 call cpm parameter with map details call validation Started");
		logStep("****** Criteo SDK invapp v2 call cpm parameter with map details call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Map", "cpm", true);
	}

	@Test(priority = 442, enabled = true)
	@Description("Verify size parameter of Criteo SDK inapp v2 call with Map Details call")
	public void Verify_Criteo_SDK_inapp_v2_Call_size_parameter_with_map_details_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call size parameter with map details call====================");

		System.out.println("****** Criteo SDK invapp v2 call size parameter with map details call validation Started");
		logStep("****** Criteo SDK invapp v2 call size parameter with map details call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Map", "size", true);
	}

	@Test(priority = 443, enabled = true)
	@Description("Verify displayUrl parameter of Criteo SDK inapp v2 call with Map Details call")
	public void Verify_Criteo_SDK_inapp_v2_Call_displayUrl_parameter_with_map_details_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call displayUrl parameter with map details call====================");

		System.out.println(
				"****** Criteo SDK invapp v2 call displayUrl parameter with map details call validation Started");
		logStep("****** Criteo SDK invapp v2 call displayUrl parameter with map details call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Map", "displayUrl", true);
	}

	/*@Test(priority = 444, enabled = true)
	@Description("Verify cpm parameter of Criteo SDK inapp v2 call with AQ Details call")
	public void Verify_Criteo_SDK_inapp_v2_Call_cpm_parameter_with_aq_details_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call cpm parameter with aq details call====================");

		System.out.println("****** Criteo SDK invapp v2 call cpm parameter with aq details call validation Started");
		logStep("****** Criteo SDK invapp v2 call cpm parameter with aq details call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Air Quality(Content)",
				"cpm", true);
	}

	@Test(priority = 445, enabled = true)
	@Description("Verify size parameter of Criteo SDK inapp v2 call with AQ Details call")
	public void Verify_Criteo_SDK_inapp_v2_Call_size_parameter_with_aq_details_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call size parameter with aq details call====================");

		System.out.println("****** Criteo SDK invapp v2 call size parameter with aq details call validation Started");
		logStep("****** Criteo SDK invapp v2 call size parameter with aq details call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Air Quality(Content)",
				"size", true);
	}

	@Test(priority = 446, enabled = true)
	@Description("Verify displayUrl parameter of Criteo SDK inapp v2 call with AQ Details call")
	public void Verify_Criteo_SDK_inapp_v2_Call_displayUrl_parameter_with_aq_details_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call displayUrl parameter with aq details call====================");

		System.out.println(
				"****** Criteo SDK invapp v2 call displayUrl parameter with aq details call validation Started");
		logStep("****** Criteo SDK invapp v2 call displayUrl parameter with aq details call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Air Quality(Content)",
				"displayUrl", true);
	}*/

	@Test(priority = 450, enabled = true)
	@Description("Verify cpm parameter of Criteo SDK inapp v2 call with AlertCenter call")
	public void Verify_Criteo_SDK_inapp_v2_Call_cpm_parameter_with_AlertCenter_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call cpm parameter with AlertCenter call====================");

		System.out.println("****** Criteo SDK invapp v2 call cpm parameter with AlertCenter call validation Started");
		logStep("****** Criteo SDK invapp v2 call cpm parameter with AlertCenter call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "MyAlerts", "cpm", true);
	}

	@Test(priority = 451, enabled = true)
	@Description("Verify size parameter of Criteo SDK inapp v2 call with AlertCenter call")
	public void Verify_Criteo_SDK_inapp_v2_Call_size_parameter_with_AlertCenter_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call size parameter with AlertCenter call====================");

		System.out.println("****** Criteo SDK invapp v2 call size parameter with AlertCenter call validation Started");
		logStep("****** Criteo SDK invapp v2 call size parameter with AlertCenter call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "MyAlerts", "size", true);
	}

	@Test(priority = 452, enabled = true)
	@Description("Verify displayUrl parameter of Criteo SDK inapp v2 call with AlertCenter call")
	public void Verify_Criteo_SDK_inapp_v2_Call_displayUrl_parameter_with_AlertCenter_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call displayUrl parameter with AlertCenter call====================");

		System.out.println(
				"****** Criteo SDK invapp v2 call displayUrl parameter with AlertCenter call validation Started");
		logStep("****** Criteo SDK invapp v2 call displayUrl parameter with AlertCenter call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "MyAlerts", "displayUrl",
				true);
	}

	/*@Test(priority = 453, enabled = true)
	@Description("Verify cpm parameter of Criteo SDK inapp v2 call with boatAndBeach Details call")
	public void Verify_Criteo_SDK_inapp_v2_Call_cpm_parameter_with_boatAndBeach_details_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call cpm parameter with boatAndBeach Details call====================");

		System.out.println("****** Criteo SDK invapp v2 call cpm parameter of boatAndBeach Details validation Started");
		logStep("****** Criteo SDK invapp v2 call cpm parameter of boatAndBeach Details validation Started");

		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Health(boatAndBeach)",
				"cpm", true);
	}

	@Test(priority = 454, enabled = true)
	@Description("Verify size parameter of Criteo SDK inapp v2 call with boatAndBeach Details call")
	public void Verify_Criteo_SDK_inapp_v2_Call_size_parameter_with_boatAndBeach_details_gampad_call()
			throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call size parameter with boatAndBeach Details call====================");

		System.out.println(
				"****** Criteo SDK invapp v2 call size parameter with boatAndBeach Details call validation Started");
		logStep("****** Criteo SDK invapp v2 call size parameter with boatAndBeach Details call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Health(boatAndBeach)",
				"size", true);
	}

	@Test(priority = 455, enabled = true)
	@Description("Verify displayUrl parameter of Criteo SDK inapp v2 call with boatAndBeach Details call")
	public void Verify_Criteo_SDK_inapp_v2_Call_displayUrl_parameter_with_boatAndBeach_details_gampad_call()
			throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call displayUrl parameter with boatAndBeach Details call====================");

		System.out.println(
				"****** Criteo SDK invapp v2 call displayUrl parameter with boatAndBeach Details call validation Started");
		logStep("****** Criteo SDK invapp v2 call displayUrl parameter with boatAndBeach Details call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Health(boatAndBeach)",
				"displayUrl", true);
	}*/

	/*@Test(priority = 456, enabled = true)
	@Description("Verify cpm parameter of Criteo SDK inapp v2 call with Running Details call")
	public void Verify_Criteo_SDK_inapp_v2_Call_cpm_parameter_with_Running_details_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call cpm parameter with Running Details call====================");

		System.out.println("****** Criteo SDK invapp v2 call cpm parameter of Running Details validation Started");
		logStep("****** Criteo SDK invapp v2 call cpm parameter of Running Details validation Started");

		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Health(goRun)", "cpm",
				true);
	}

	@Test(priority = 457, enabled = true)
	@Description("Verify size parameter of Criteo SDK inapp v2 call with Running Details call")
	public void Verify_Criteo_SDK_inapp_v2_Call_size_parameter_with_Running_details_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call size parameter with Running Details call====================");

		System.out.println(
				"****** Criteo SDK invapp v2 call size parameter with Running Details call validation Started");
		logStep("****** Criteo SDK invapp v2 call size parameter with Running Details call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Health(goRun)", "size",
				true);
	}

	@Test(priority = 458, enabled = true)
	@Description("Verify displayUrl parameter of Criteo SDK inapp v2 call with Running Details call")
	public void Verify_Criteo_SDK_inapp_v2_Call_displayUrl_parameter_with_Running_details_gampad_call()
			throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call displayUrl parameter with Running Details call====================");

		System.out.println(
				"****** Criteo SDK invapp v2 call displayUrl parameter with Running Details call validation Started");
		logStep("****** Criteo SDK invapp v2 call displayUrl parameter with Running Details call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Health(goRun)",
				"displayUrl", true);
	}*/

	@Test(priority = 459, enabled = true)
	@Description("Verify cpm parameter of Criteo SDK inapp v2 call with Weather Articles--Health(coldAndFluArticles) call")
	public void Verify_Criteo_SDK_inapp_v2_Call_cpm_parameter_with_HealthcoldAndFluArticles_gampad_call()
			throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call cpm parameter with Weather Articles--Health(coldAndFluArticles) call====================");

		System.out.println(
				"****** Criteo SDK invapp v2 call cpm parameter of Weather Articles--Health(coldAndFluArticles) validation Started");
		logStep("****** Criteo SDK invapp v2 call cpm parameter of Weather Articles--Health(coldAndFluArticles) validation Started");

		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke",
				"Health(coldAndFluArticles)", "cpm", true);
	}

	@Test(priority = 460, enabled = true)
	@Description("Verify size parameter of Criteo SDK inapp v2 call with Weather Articles--Health(coldAndFluArticles) call")
	public void Verify_Criteo_SDK_inapp_v2_Call_size_parameter_with_HealthcoldAndFluArticles_gampad_call()
			throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call size parameter with Weather Articles--Health(coldAndFluArticles) Details call====================");

		System.out.println(
				"****** Criteo SDK invapp v2 call size parameter with Weather Articles--Health(coldAndFluArticles) call validation Started");
		logStep("****** Criteo SDK invapp v2 call size parameter with Weather Articles--Health(coldAndFluArticles) call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke",
				"Health(coldAndFluArticles)", "size", true);
	}

	@Test(priority = 461, enabled = true)
	@Description("Verify displayUrl parameter of Criteo SDK inapp v2 call with Weather Articles--Health(coldAndFluArticles) call")
	public void Verify_Criteo_SDK_inapp_v2_Call_displayUrl_parameter_with_HealthcoldAndFluArticles_gampad_call()
			throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call displayUrl parameter with Weather Articles--Health(coldAndFluArticles) call====================");

		System.out.println(
				"****** Criteo SDK invapp v2 call displayUrl parameter with Weather Articles--Health(coldAndFluArticles) call validation Started");
		logStep("****** Criteo SDK invapp v2 call displayUrl parameter with Weather Articles--Health(coldAndFluArticles) call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke",
				"Health(coldAndFluArticles)", "displayUrl", true);
	}
	
	@Test(priority = 462, enabled = true)
	@Description("Verify cpm parameter of Criteo SDK inapp v2 call with Weather Articles--Health(allergyArticles) call")
	public void Verify_Criteo_SDK_inapp_v2_Call_cpm_parameter_with_HealthallergyArticles_gampad_call()
			throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call cpm parameter with Weather Articles--Health(allergyArticles) call====================");

		System.out.println(
				"****** Criteo SDK invapp v2 call cpm parameter of Weather Articles--Health(allergyArticles) validation Started");
		logStep("****** Criteo SDK invapp v2 call cpm parameter of Weather Articles--Health(allergyArticles) validation Started");

		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke",
				"Health(allergyArticles)", "cpm", true);
	}

	@Test(priority = 463, enabled = true)
	@Description("Verify size parameter of Criteo SDK inapp v2 call with Weather Articles--Health(allergyArticles) call")
	public void Verify_Criteo_SDK_inapp_v2_Call_size_parameter_with_HealthallergyArticles_gampad_call()
			throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call size parameter with Weather Articles--Health(allergyArticles) Details call====================");

		System.out.println(
				"****** Criteo SDK invapp v2 call size parameter with Weather Articles--Health(allergyArticles) call validation Started");
		logStep("****** Criteo SDK invapp v2 call size parameter with Weather Articles--Health(allergyArticles) call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke",
				"Health(allergyArticles)", "size", true);
	}

	@Test(priority = 464, enabled = true)
	@Description("Verify displayUrl parameter of Criteo SDK inapp v2 call with Weather Articles--Health(allergyArticles) call")
	public void Verify_Criteo_SDK_inapp_v2_Call_displayUrl_parameter_with_HealthallergyArticles_gampad_call()
			throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call displayUrl parameter with Weather Articles--Health(allergyArticles) call====================");

		System.out.println(
				"****** Criteo SDK invapp v2 call displayUrl parameter with Weather Articles--Health(allergyArticles) call validation Started");
		logStep("****** Criteo SDK invapp v2 call displayUrl parameter with Weather Articles--Health(allergyArticles) call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke",
				"Health(allergyArticles)", "displayUrl", true);
	}

	@Test(priority = 475, enabled = true)
	@Description("Enabling Preconfigurations to validate Hourly Big ads amazon bid id's and criteo parameters")
	public void enable_PreConditions_to_Validate_Hourly_big_ad_Criteo_Parameters() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Enabling Preconfigurations to validate Hourly Big ads Aamazon bid id's and criteo parameters ====================");

		System.out.println(
				"****** Enabling Preconfigurations to validate Hourly Big ads amazon bid id's and criteo parameters Started");
		logStep("****** Enabling Preconfigurations to validate Hourly Big ads amazon bid id's and criteo parameters Started");
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(5000);
		hmTab.clickonHomeTab();
		hmTab.clickonHomeTab();
		Functions.archive_folder("Charles");
		//Functions.enternewAddress(false, "New York City, New York");
		//TestBase.waitForMilliSeconds(15000);

		//Navigating to Hourly Tab and swiping to get hourly1, hourly2 and hourly3 big ads
		 

		hrTab.navigateToHourlyTab();
		Functions.swipe_Up_ByIterations(Ad, 10);
		hmTab.clickonHomeTab();
		TestBase.waitForMilliSeconds(5000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		// Utils.get_v3_wx_forecast_daily_15day_data();

	}

	@Test(priority = 476, enabled = true)
	@Description("Verify cpm parameter of Criteo SDK inapp v2 call with Hourly1 Big ad call")
	public void Verify_Criteo_SDK_inapp_v2_Call_cpm_parameter_with_Hourly1_Big_ad_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call cpm parameter with Hourly1 Big ad call====================");

		System.out
				.println("****** Criteo SDK invapp v2 call cpm parameter with Hourly1 Big ad call validation Started");
		logStep("****** Criteo SDK invapp v2 call cpm parameter with Hourly1 Big ad call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Hourly1", "cpm", true);
	}

	@Test(priority = 477, enabled = true)
	@Description("Verify size parameter of Criteo SDK inapp v2 call with Hourly1 Big ad call")
	public void Verify_Criteo_SDK_inapp_v2_Call_size_parameter_with_Hourly1_Big_ad_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call size parameter with Hourly1 Big ad call====================");

		System.out
				.println("****** Criteo SDK invapp v2 call size parameter with Hourly1 Big ad call validation Started");
		logStep("****** Criteo SDK invapp v2 call size parameter with Hourly1 Big ad call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Hourly1", "size", true);
	}

	@Test(priority = 478, enabled = true)
	@Description("Verify displayUrl parameter of Criteo SDK inapp v2 call with Hourly1 Big ad call")
	public void Verify_Criteo_SDK_inapp_v2_Call_displayUrl_parameter_with_Hourly1_Big_ad_gampad_call()
			throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call displayUrl parameter with Hourly1 Big ad call====================");

		System.out.println(
				"****** Criteo SDK invapp v2 call displayUrl parameter with Hourly1 Big ad call validation Started");
		logStep("****** Criteo SDK invapp v2 call displayUrl parameter with Hourly1 Big ad call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Hourly1", "displayUrl", true);
	}

	@Test(priority = 479, enabled = true)
	@Description("Verify cpm parameter of Criteo SDK inapp v2 call with Hourly2 Big ad call")
	public void Verify_Criteo_SDK_inapp_v2_Call_cpm_parameter_with_Hourly2_Big_ad_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call cpm parameter with Hourly2 Big ad call====================");

		System.out
				.println("****** Criteo SDK invapp v2 call cpm parameter with Hourly2 Big ad call validation Started");
		logStep("****** Criteo SDK invapp v2 call cpm parameter with Hourly2 Big ad call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Hourly2", "cpm", true);
	}

	@Test(priority = 480, enabled = true)
	@Description("Verify size parameter of Criteo SDK inapp v2 call with Hourly2 Big ad call")
	public void Verify_Criteo_SDK_inapp_v2_Call_size_parameter_with_Hourly2_Big_ad_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call size parameter with Hourly2 Big ad call====================");

		System.out
				.println("****** Criteo SDK invapp v2 call size parameter with Hourly2 Big ad call validation Started");
		logStep("****** Criteo SDK invapp v2 call size parameter with Hourly2 Big ad call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Hourly2", "size", true);
	}

	@Test(priority = 481, enabled = true)
	@Description("Verify displayUrl parameter of Criteo SDK inapp v2 call with Hourly2 Big ad call")
	public void Verify_Criteo_SDK_inapp_v2_Call_displayUrl_parameter_with_Hourly2_Big_ad_gampad_call()
			throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call displayUrl parameter with Hourly2 Big ad call====================");

		System.out.println(
				"****** Criteo SDK invapp v2 call displayUrl parameter with Hourly2 Big ad call validation Started");
		logStep("****** Criteo SDK invapp v2 call displayUrl parameter with Hourly2 Big ad call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Hourly2", "displayUrl", true);
	}

	@Test(priority = 482, enabled = true)
	@Description("Verify cpm parameter of Criteo SDK inapp v2 call with Hourly3 Big ad call")
	public void Verify_Criteo_SDK_inapp_v2_Call_cpm_parameter_with_Hourly3_Big_ad_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call cpm parameter with Hourly3 Big ad call====================");

		System.out
				.println("****** Criteo SDK invapp v2 call cpm parameter with Hourly3 Big ad call validation Started");
		logStep("****** Criteo SDK invapp v2 call cpm parameter with Hourly3 Big ad call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Hourly3", "cpm", true);
	}

	@Test(priority = 483, enabled = true)
	@Description("Verify size parameter of Criteo SDK inapp v2 call with Hourly3 Big ad call")
	public void Verify_Criteo_SDK_inapp_v2_Call_size_parameter_with_Hourly3_Big_ad_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call size parameter with Hourly3 Big ad call====================");

		System.out
				.println("****** Criteo SDK invapp v2 call size parameter with Hourly3 Big ad call validation Started");
		logStep("****** Criteo SDK invapp v2 call size parameter with Hourly3 Big ad call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Hourly3", "size", true);
	}

	@Test(priority = 484, enabled = true)
	@Description("Verify displayUrl parameter of Criteo SDK inapp v2 call with Hourly3 Big ad call")
	public void Verify_Criteo_SDK_inapp_v2_Call_displayUrl_parameter_with_Hourly3_Big_ad_gampad_call()
			throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call displayUrl parameter with Hourly3 Big ad call====================");

		System.out.println(
				"****** Criteo SDK invapp v2 call displayUrl parameter with Hourly3 Big ad call validation Started");
		logStep("****** Criteo SDK invapp v2 call displayUrl parameter with Hourly3 Big ad call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Hourly3", "displayUrl", true);
	}

	@Test(priority = 490, enabled = true)
	@Description("Verify amazon aax Hourly Big ad call")
	public void Verify_amazon_aax_Hourly_Big_adcall() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== amazon aax Hourly Big ad call ====================");

		System.out.println("****** amazon aax Hourly Big ad call validation Started");
		logStep("****** amazon aax Hourly Big ad call validation Started");
		Utils.verifyAAX_SlotId("Smoke", "Hourly1");

	}

	@Test(priority = 491, enabled = true)
	@Description("Verify Hourly1 Big ad call amazon bid id")
	public void Verify_Hourly1_Big_adcall_amazon_bid_id() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Hourly1 Big ad call amazon bid id ====================");

		System.out.println("****** Hourly1 Big ad call amazon bid id validation Started");
		logStep("****** Hourly1 Big ad call amazon bid id validation Started");
		Utils.validate_aax_bid_value_with_gampad_bid_value("Smoke", "Hourly1", true);
	}

	@Test(priority = 492, enabled = true)
	@Description("Verify Hourly2 Big ad call amazon bid id")
	public void Verify_Hourly2_Big_adcall_amazon_bid_id() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Hourly2 Big ad call amazon bid id ====================");

		System.out.println("****** Hourly2 Big ad call amazon bid id validation Started");
		logStep("****** Hourly2 Big ad call amazon bid id validation Started");
		Utils.validate_aax_bid_value_with_gampad_bid_value("Smoke", "Hourly2", true);
	}

	@Test(priority = 493, enabled = true)
	@Description("Verify Hourly3 Big ad call amazon bid id")
	public void Verify_Hourly3_Big_adcall_amazon_bid_id() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Hourly3 Big ad call amazon bid id ====================");

		System.out.println("****** Hourly3 Big ad call amazon bid id validation Started");
		logStep("****** Hourly3 Big ad call amazon bid id validation Started");
		Utils.validate_aax_bid_value_with_gampad_bid_value("Smoke", "Hourly3", true);
	}
	

	@Test(priority = 500, enabled = true)
	@Description("Verify News Details ad call amazon bid id")
	public void Verify_amazon_News_Details_adcall_bid_id() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== News Details ad call amazon bid id ====================");
		System.out.println("****** News Details ad call amazon bid id validation Started");
		logStep("****** News Details ad call bid id validation Started");
		try {
			proxy.clearCharlesSession();
			Functions.archive_folder("Charles");
			try {
				// navigate to News Card
				//Utils.navigateTofeedCard("news", false, false);
				nScreen.scrollToNewsCard();
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			proxy.clearCharlesSession();
			try {
				nScreen.navigateToNewsDetails();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			/**
			 * Waiting more than 30 seconds to allow ad refresh and generate  gampad call twice
			 */
			TestBase.waitForMilliSeconds(45000);
			proxy.getXml();
			Utils.createXMLFileForCharlesSessionFile();
			
			Utils.validate_aax_bid_value_with_gampad_bid_value("Smoke", "News(details)", true, true);
		} finally {
			navigateBackToFeedCardAndroid();
			CharlesProxy.proxy.stopRecording();
			Functions.archive_folder("Charles");
			TestBase.waitForMilliSeconds(5000);
			CharlesProxy.proxy.getXml();
			Utils.createXMLFileForCharlesSessionFile();
			if (Utils.isInterStitialAdCalExists("Smoke", "News(details)")) {

				if (Utils.isInterstitialCall_hasResponse("Smoke", "News(details)")) {
					if (unlimitedInterstitial) {
						handle_Interstitial_AdAndroid();
					} else {
						if (!interStitialDisplayed) {
							/*
							 * Since Exit Interstitial displayed upon click on back icon, handling it once
							 * click on back
							 */
							handle_Interstitial_AdAndroid();
						} else {
							System.out.println("Interstitial Ad is already handled, hence not handling again");
							logStep("Interstitial Ad is already handled, hence not handling again");

						}
					}
				}
			}
			Functions.delete_folder("Charles");	
			CharlesProxy.proxy.startRecording();
		}
	}
	
	@Test(priority = 525, enabled = true)
	@Description("Enabling Preconfigurations to validate amazon bid id's Interstitial Call")
	public void enable_PreConditions_to_Validate_amazon_bid_ids_Interstitial_Call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Enabling Preconfigurations to validate amazon bid id's of Interstitial Call ====================");
		System.out
				.println("****** Enabling Preconfigurations to validate amazon bid id's of Interstitial Call Started");
		logStep("****** Enabling Preconfigurations to validate amazon bid id's of Interstitial Call Started");
//		stScreen.select_Airlock_Branch("UnlimitedInterstitialAutomation02");
		stScreen.select_Airlock_UserGroup("unlimitedinterstitials");
		Functions.archive_folder("Charles");
		//stScreen.select_Airlock_Branch("HDB");
		//stScreen.select_Airlock_UserGroup("Criteo");
		Functions.close_launchAppAndroid();
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(15000);
		Functions.put_Background_launchAndroid(15);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		// Utils.get_v3_wx_forecast_daily_15day_data();
				
	}
	
	@Test(priority = 526, enabled = true)
	@Description("Verify amazon aax Interstitials preload ad call")
	public void Verify_amazon_aax_preload_Intersitials_adcall() throws Exception {
		System.out.println("==============================================");
		System.out
				.println("=========================== amazon aax Interstitials preload ad call ====================");
		System.out.println("****** amazon aax Interstitials preload ad call validation Started");
		logStep("****** amazon aax Interstitials preload ad call validation Started");
		Utils.verifyAAX_SlotId("Smoke", "Interstitials");
	}
	
	@Test(priority = 527, enabled = true)
	@Description("Verify Interstitials ad call amazon bid id")
	public void Verify_Interstitials_ad_call_amazon_bid_id() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Interstitials ad call amazon bid id ====================");

		System.out.println("****** Interstitials ad call amazon bid id validation Started");
		logStep("****** Interstitials ad call amazon bid id validation Started");
		//Utils.validate_aax_bid_value_with_gampad_bid_value("Smoke", "Interstitials", true);
		Utils.validate_aax_bid_value_with_gampad_bid_value("Smoke", "Interstitials", true, true);
	}
	
	@Test(priority = 540, enabled = true)
	@Description("Verify cpm parameter of Criteo SDK inapp v2 call with Interstitials call")
	public void Verify_Criteo_SDK_inapp_v2_Call_cpm_parameter_with_Interstitials_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call cpm parameter with Interstitials call====================");

		System.out
				.println("****** Criteo SDK invapp v2 call cpm parameter with Interstitials call validation Started");
		logStep("****** Criteo SDK invapp v2 call cpm parameter with Interstitials call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Interstitials", "cpm", true);
	}

	@Test(priority = 541, enabled = true)
	@Description("Verify size parameter of Criteo SDK inapp v2 call with Interstitials call")
	public void Verify_Criteo_SDK_inapp_v2_Call_size_parameter_with_Interstitials_gampad_call() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call size parameter with Interstitials call====================");

		System.out
				.println("****** Criteo SDK invapp v2 call size parameter with Interstitials call validation Started");
		logStep("****** Criteo SDK invapp v2 call size parameter with Interstitials call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Interstitials", "size", true);
	}

	@Test(priority = 542, enabled = true)
	@Description("Verify displayUrl parameter of Criteo SDK inapp v2 call with Interstitials call")
	public void Verify_Criteo_SDK_inapp_v2_Call_displayUrl_parameter_with_Interstitials_gampad_call()
			throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK invapp v2 call displayUrl parameter with Interstitials call====================");

		System.out.println(
				"****** Criteo SDK invapp v2 call displayUrl parameter with Interstitials call validation Started");
		logStep("****** Criteo SDK invapp v2 call displayUrl parameter with Interstitials call validation Started");
		Utils.validate_Criteo_SDK_inapp_v2_call_param_value_with_gampad_param_value("Smoke", "Interstitials", "displayUrl", true);
	}

}

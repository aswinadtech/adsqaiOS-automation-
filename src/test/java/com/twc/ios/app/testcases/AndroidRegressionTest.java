package com.twc.ios.app.testcases;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.twc.ios.app.charlesfunctions.CharlesProxy;
import com.twc.ios.app.functions.Functions;
import com.twc.ios.app.general.ParseForVideoOrderedList;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.TwcIosBaseTest;
import com.twc.ios.app.general.Utils;
import com.twc.ios.app.pages.AddressScreen;
import com.twc.ios.app.pages.AirQualityCardContentScreen;
import com.twc.ios.app.pages.AirQualityCardScreen;
import com.twc.ios.app.pages.AndroidAddressScreen;
import com.twc.ios.app.pages.AndroidAirQualityCardContentScreen;
import com.twc.ios.app.pages.AndroidAirQualityCardScreen;
import com.twc.ios.app.pages.AndroidBreakingNewsCardScreen;
import com.twc.ios.app.pages.AndroidDailyNavTab;
import com.twc.ios.app.pages.AndroidFeedOneCardScreen;
import com.twc.ios.app.pages.AndroidFooterCardScreen;
import com.twc.ios.app.pages.AndroidHomeNavTab;
import com.twc.ios.app.pages.AndroidHourlyNavTab;
import com.twc.ios.app.pages.AndroidLogInScreen;
import com.twc.ios.app.pages.AndroidRadarNavTab;
import com.twc.ios.app.pages.AndroidSeasonalHubCardScreen;
import com.twc.ios.app.pages.AndroidSettingsScreen;
import com.twc.ios.app.pages.AndroidVideoNavTab;
import com.twc.ios.app.pages.BreakingNewsCardScreen;
import com.twc.ios.app.pages.DailyNavTab;
import com.twc.ios.app.pages.FeedOneCardScreen;
import com.twc.ios.app.pages.FooterCardScreen;
import com.twc.ios.app.pages.HomeNavTab;
import com.twc.ios.app.pages.HourlyNavTab;
import com.twc.ios.app.pages.LogInScreen;
import com.twc.ios.app.pages.PlanningCardScreen;
import com.twc.ios.app.pages.RadarNavTab;
import com.twc.ios.app.pages.SeasonalHubCardScreen;
import com.twc.ios.app.pages.SettingsScreen;
import com.twc.ios.app.pages.VideoNavTab;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Listeners;

import org.testng.annotations.BeforeClass;

//import ru.yandex.qatools.allure.annotations.Title;
import io.qameta.allure.Description;

@Listeners(value = com.twc.ios.app.general.MyTestListenerAdapter.class)
public class AndroidRegressionTest extends TwcIosBaseTest {

	private static final String CONFIG_FILE_PATH = "charles_common.config";
	private static final String BN_SEVERE1_CONFIG_FILE_PATH = "BNSevere1charles_common.config";
	private static final String BN_SEVERE2_CONFIG_FILE_PATH = "BNSevere2charles_common.config";
	private static final String CRITEO_CONFIG_FILE_PATH = "Criteocharles_common.config";
	private static final String SEVERE_MAP_LOCAL_CONFIG_FILE_PATH = "severemaplocalcharles_common.config";
	private static final String EDITORIAL_SEVERE1_MAP_LOCAL_CONFIG_FILE_PATH = "editorialsevere1maplocalcharles_common.config";
	private static final String EDITORIAL_SEVERE2_MAP_LOCAL_CONFIG_FILE_PATH = "editorialsevere2maplocalcharles_common.config";
	private static final String TORNADO_MAP_LOCAL_CONFIG_FILE_PATH = "tornadomaplocalcharles_common.config";
	private static final String HEATADVISORY_MAP_LOCAL_CONFIG_FILE_PATH = "headadvisorymaplocalcharles_common.config";

	// public static CharlesProxy proxy;
	public File configFile;
	
	AndroidHourlyNavTab hrTab;
	AndroidDailyNavTab dTab;
	AndroidHomeNavTab hmTab;
	AndroidRadarNavTab rTab;
	AndroidVideoNavTab vTab;
	AndroidAddressScreen addrScreen;

	AndroidSeasonalHubCardScreen sScreen;
	AndroidBreakingNewsCardScreen bnScreen;
	AndroidSettingsScreen stScreen;
	AndroidLogInScreen loginScreen;
	AndroidFeedOneCardScreen fOneCardScreen;
	AndroidAirQualityCardScreen aqCardScreen;
	AndroidAirQualityCardContentScreen aqCardContentScreen;
	AndroidFooterCardScreen footerCardScreen;
	
	long subscriptionFqCapStrtTime = 0L;
	long start = 0;
	long finish = 0;
	long subscriptionFqCapEndTime = 0L;
	long timeElapsed = 0;
	long subscriptionFqtimeElapsed = 0L;
	long convert = 0;
	long subscriptionFqtimeconvert = 0L;
	

	@BeforeClass(alwaysRun = true)
	@Description("BeforeClass")
	public void beforeClass() {
		System.out.println("****** Regression Test Started");
		logStep("****** Regression Test Started");
		//this.configFile = this.charlesGeneralConfigFile(CONFIG_FILE_PATH);
		//proxy = new CharlesProxy("localhost", 8111, CONFIG_FILE_PATH);
		this.configFile = this.rewriteRuleToEnableUSAWhenNoTunnelBearAndroid(CONFIG_FILE_PATH, "usa", "US", "WA");
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
		//stScreen.getAppVersion();
		Ad.quit();
		Functions.launchtheAndroidApp();
		Functions.close_launchAppAndroid();	
		stScreen = new AndroidSettingsScreen(Ad);
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

		System.out.println("****** Regression Test Ended");
		logStep("****** Regression Test Ended");
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
		bnScreen = new AndroidBreakingNewsCardScreen(Ad) ;
		stScreen = new AndroidSettingsScreen(Ad);
		loginScreen = new AndroidLogInScreen(Ad);
		fOneCardScreen = new AndroidFeedOneCardScreen(Ad);
		aqCardScreen = new AndroidAirQualityCardScreen(Ad);
		aqCardContentScreen = new AndroidAirQualityCardContentScreen(Ad);
		footerCardScreen =  new AndroidFooterCardScreen(Ad);
		
	}
	
	@Test(priority = 50, enabled = true)
	@Description("Kill and Launch the app for aax calls verification")
	public void kill_and_Launch_app_for_aax_calls() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Kill and Launch the app for aax calls");
		logStep("Kill and Launch the app for aax calls");
		TestBase.waitForMilliSeconds(5000);
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		//Functions.checkForAppState();
		//addrScreen.enternewAddress(false, "Atlanta, Georgia");
		addrScreen.enter_requiredLocation("30303");
		TestBase.waitForMilliSeconds(20000);
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
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
		Utils.verifyAAX_SlotId("Smoke", "Feed1");

	}

	@Test(priority = 53, enabled = true)
	@Description("Verify amazon aax Feed2 preload ad call")
	public void Verify_amazon_aax_preload_feed2_adcall() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== amazon aax feed2 preload ad call ====================");
		System.out.println("****** amazon aax feed2 preload ad call validation Started");
		logStep("****** amazon aax feed2 preload ad call validation Started");
		Utils.verifyAAX_SlotId("Smoke", "Feed2");

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
	@Description("Verify amazon aax Hourly Bigad preload ad call")
	public void Verify_amazon_aax_preload_Hourly_bigad_adcall() throws Exception {
		System.out.println("==============================================");
		System.out
				.println("=========================== amazon aax Hourly Bigad preload ad call ====================");
		System.out.println("****** amazon aax Hourly Bigad preload ad call validation Started");
		logStep("****** amazon aax Hourly Bigad preload ad call validation Started");
		Utils.verifyAAX_SlotId("Smoke", "Hourly1");
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
	
	@Test(priority = 75, enabled = true)
	@Description("Verify Criteo SDK inapp v2 call")
	public void Verify_Criteo_SDK_inapp_v2_Call() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Criteo SDK inapp/v2 call ====================");

		System.out.println("****** Criteo SDK inapp/v2 call validation Started");
		logStep("****** Criteo SDK inapp/v2 call validation Started");

		Utils.verifyCriteo_inapp_v2_Call("Smoke", "Criteo");

	}

	@Test(priority = 76, enabled = true)
	@Description("Verify Criteo SDK config app call")
	public void Verify_Criteo_SDK_config_app_Call() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Criteo SDK config/app call ====================");

		System.out.println("****** Criteo SDK config/app call validation Started");
		logStep("****** Criteo SDK config/app call validation Started");

		Utils.verifyCriteo_config_app_Call("Smoke", "Criteo");

	}

	@Test(priority = 77, enabled = true)
	@Description("Validating 'cpId' parameter of Criteo SDK config app call ")
	public void Validate_Criteo_SDK_config_app_Call_cpId_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating 'cpId' parameter of Criteo SDK config app call");
		logStep("****** Validating 'cpId' parameter of Criteo SDK config app call");
		Utils.validate_Criteo_SDK_config_app_call_parameter("Smoke", "Criteo", "cpId",
				properties.getProperty("CriteoCpId"));

	}

	@Test(priority = 78, enabled = true)
	@Description("Validating 'bundleId' parameter of Criteo SDK config app call ")
	public void Validate_Criteo_SDK_config_app_Call_bundleId_parameter() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating 'bundleId' parameter of Criteo SDK config app call");
		logStep("****** Validating 'bundleId' parameter of Criteo SDK config app call");
		Utils.validate_Criteo_SDK_config_app_call_parameter("Smoke", "Criteo", "bundleId",
				properties.getProperty("CriteoBundleId"));

	}

	@Test(priority = 79, enabled = true)
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
	@Test(priority = 81, enabled = true)
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
	@Test(priority = 82, enabled = true)
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
	@Test(priority = 83, enabled = true)
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
	@Test(priority = 84, enabled = true)
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
	 * @Test(priority = 85, enabled = true)
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

	/**
	 * This method validates Initialization API (config app) call response parameter
	 * 'prefetchOnInitEnabled'
	 */
	@Test(priority = 86, enabled = true)
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
	@Test(priority = 87, enabled = true)
	@Description("Validating Criteo Initialization API (config app) call response parameter 'remoteLogLevel' value")
	public void Validate_Criteo_SDK_Initialization_API_Call_Response_Parameter_remoteLogLevel() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"****** Validating remoteLogLevel parameter value in Criteo Initialization API (config app) Call Response");
		logStep("****** Validating remoteLogLevel parameter value in Criteo Initialization API (config app) Call Response");
		Utils.validate_Criteo_SDK_config_app_call_response_parameter("Smoke", "Criteo", "remoteLogLevel",
				properties.getProperty("remoteLogLevel"));
	}

	/**
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

	@Test(priority = 101, enabled = true)
	@Description("Verify preroll video iu")
	public void Verify_video_adcall_iu() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== preroll video ad call iu ====================");

		System.out.println("****** preroll video ad call iu validation Started");
		logStep("****** preroll video ad call iu validation Started");
		proxy.clearCharlesSession();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		hmTab.clickonHomeTab();
		hmTab.clickonHomeTab();
		proxy.clearCharlesSession();
		// navigate to Video tab
		vTab.navigateToVideoTab(true, proxy);
		TestBase.waitForMilliSeconds(10000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.get_iu_value_of_Feedcall("Smoke", "PreRollVideo");
	}

	@Test(priority = 102, enabled = true)
	@Description("Validating Google Interactive Media Ads SDK version of Preroll video call ")
	public void Validate_IMA_SDK_version() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"****** Validating Google Interactive Media Ads SDK version i.e. 'js' parameter of Preroll video call");
		logStep("Validating Google Interactive Media Ads SDK version i.e. 'js' parameter of Preroll video call");

		Utils.validate_Noncustom_param_val_of_gampad("Smoke", "PreRollVideo", "js",
				properties.getProperty("IMASDKVersion"));

	}
			
	@Test(priority = 250,   enabled = true)
	@Description("Segment Parameter Verificatiion when background launch the app from hourly tab")
	public void seg_Parameter_Verification_When_Navigated_To_Hourly_Tab() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Segment Parameter Verificatiion when background launch the app from hourly tab test case Started");
		logStep("****** Segment Parameter Verificatiion when background launch the app from hourly tab test case Started");
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		hmTab.clickonHomeTab();
		hmTab.clickonHomeTab();
		//Functions.checkForAppState();
		Functions.archive_folder("Charles");
		//hrTab.navigateToHourlyTab();
		hrTab.navigateToHourlyTabAndHandleInterstitialAd();
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		hmTab.clickonHomeTab();
		hmTab.clickonHomeTab();
		hrTab.navigateToHourlyTab();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(5000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.verify_Lotame_Call_Segment_Parameter("Smoke", "Lotame", "seg=Hourly");
	
	}
	
	@Test(priority = 251,   enabled = true)
	@Description("Segment Parameter Verificatiion when background launch the app from daily tab")
	public void seg_Parameter_Verification_When_Navigated_To_Daily_Tab() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Segment Parameter Verificatiion when background launch the app from daily tab test case Started");
		logStep("****** Segment Parameter Verificatiion when background launch the app from daily tab test case Started");
		proxy.clearCharlesSession();
		Functions.archive_folder("Charles");
		dTab.navigateToDailyTab();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(5000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.verify_Lotame_Call_Segment_Parameter("Smoke", "Lotame", "seg=Daily");

	}
	
	@Test(priority = 252,   enabled = true)
	@Description("Segment Parameter Verificatiion when background launch the app from radar tab")
	public void seg_Parameter_Verification_When_Navigated_To_Radar_Tab() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Segment Parameter Verificatiion when background launch the app from radar tab test case Started");
		logStep("****** Segment Parameter Verificatiion when background launch the app from radar tab test case Started");
		proxy.clearCharlesSession();
		Functions.archive_folder("Charles");
		rTab.navigateToRadarTab();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(5000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.verify_Lotame_Call_Segment_Parameter("Smoke", "Lotame", "seg=Radar");
	
	}
	
	@Test(priority = 253,   enabled = true)
	@Description("Segment Parameter Verificatiion when background launch the app from video tab")
	public void seg_Parameter_Verification_When_Navigated_To_Video_Tab() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Segment Parameter Verificatiion when background launch the app from video tab test case Started");
		logStep("****** Segment Parameter Verificatiion when background launch the app from video tab test case Started");
		proxy.clearCharlesSession();
		Functions.archive_folder("Charles");
		vTab.navigateToVideoTab();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(5000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.verify_Lotame_Call_Segment_Parameter("Smoke", "Lotame", "seg=Video");
	
	}
	
	@Test(priority = 270, enabled = true)
	@Description("Enable Preconditions to change region to de_DE")
	public void enable_preConditions_toChange_Region_for_de_DE() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** enable Preconditions to change region to de_DE test case Started");
		logStep("****** enable Preconditions to change region to de_DE test case Started");
		Ad.terminateApp("com.weather.Weather");
		
		Functions.Appium_Autostart();
		Functions.archive_folder("Charles");
		proxy.startRecording();
		proxy.clearCharlesSession();
		Functions.launchtheAndroidApp_forLocalization("DE", true, "de", true);
		System.out.println("App launched ");
		logStep("App launched ");
		//proxy.getXml();
		Functions.archive_folder("Charles");
		Functions.close_launchAppAndroid();
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(10000);
		//Functions.checkForAppState();
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		
		Ad.terminateApp("com.weather.Weather");
	}

	@Test(priority = 271, enabled = true)
	@Description("Validating NextGen IM Call for de_DE")
	public void validate_NextGen_IM_call_for_de_DE() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Call for de_DE");
		logStep("Validating NextGen IM Call for de_DE");

		//Utils.verifyPubadCal("iu=%2F3673%2Fm_app_droid_phone_wx%2Fdb_display%2Fhome_screen%2Fmarquee");
		Utils.verifyPubadCal("iu=%2F3673%2Fm_app_droid_phone_wx");

	}

	@Test(priority = 275, enabled = true)
	@Description("Enable Preconditions to change region to es_US")
	public void enable_preConditions_toChange_Region_for_es_US() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** enable Preconditions to change region to es_US test case Started");
		logStep("****** enable Preconditions to change region to es_US test case Started");
		
		Functions.Appium_Autostart();
		Functions.archive_folder("Charles");
		proxy.startRecording();
		proxy.clearCharlesSession();
		Functions.launchtheAndroidApp_forLocalization("US", true, "es", true);
		System.out.println("App launched ");
		logStep("App launched ");
		//proxy.getXml();
		Functions.archive_folder("Charles");
		Functions.close_launchAppAndroid();
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(10000);
		//Functions.checkForAppState();
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		
		Ad.terminateApp("com.weather.Weather");
	}

	@Test(priority = 276, enabled = true)
	@Description("Validating NextGen IM Call for es_US")
	public void validate_NextGen_IM_call_for_es_US() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Call for es_US");
		logStep("Validating NextGen IM Call for es_US");

		Utils.verifyPubadCal("iu=%2F7646%2Fapp_android_us_es%2Fdb_display%2Fhome_screen%2Fmarquee");

	}

	@Test(priority = 280, enabled = true)
	@Description("Enable Preconditions to change region to hi_IN")
	public void enable_preConditions_toChange_Region_for_hi_IN() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** enable Preconditions to change region to hi_IN test case Started");
		logStep("****** enable Preconditions to change region to hi_IN test case Started");
	
		Functions.Appium_Autostart();
		Functions.archive_folder("Charles");
		proxy.startRecording();
		proxy.clearCharlesSession();
		Functions.launchtheAndroidApp_forLocalization("IN", true, "hi", true);
		System.out.println("App launched ");
		logStep("App launched ");
		//proxy.getXml();
		Functions.archive_folder("Charles");
		Functions.close_launchAppAndroid();
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(10000);
		//Functions.checkForAppState();
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		
		Ad.terminateApp("com.weather.Weather");
	}

	@Test(priority = 281, enabled = true)
	@Description("Validating NextGen IM Call for hi_IN")
	public void validate_NextGen_IM_call_for_hi_IN() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Call for hi_IN");
		logStep("Validating NextGen IM Call for hi_IN");

		Utils.verifyPubadCal("iu=%2F7646%2Fapp_android_hi_in%2Fdb_display%2Fhome_screen%2Fmarquee");

	}

	@Test(priority = 285, enabled = true)
	@Description("Enable Preconditions to change region to en_IN")
	public void enable_preConditions_toChange_Region_for_en_IN() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** enable Preconditions to change region to en_IN test case Started");
		logStep("****** enable Preconditions to change region to en_IN test case Started");
		
		Functions.Appium_Autostart();
		Functions.archive_folder("Charles");
		proxy.startRecording();
		proxy.clearCharlesSession();
		Functions.launchtheAndroidApp_forLocalization("IN", true, "en", true);
		System.out.println("App launched ");
		logStep("App launched ");
		//proxy.getXml();
		Functions.archive_folder("Charles");
		Functions.close_launchAppAndroid();
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(10000);
		//Functions.checkForAppState();
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		
		Ad.terminateApp("com.weather.Weather");
	}

	@Test(priority = 286, enabled = true)
	@Description("Validating NextGen IM Call for en_IN")
	public void validate_NextGen_IM_call_for_en_IN() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Call for en_IN");
		logStep("Validating NextGen IM Call for en_IN");

		Utils.verifyPubadCal("iu=%2F7646%2Fapp_android_en_in%2Fdb_display%2Fhome_screen%2Fmarquee");

	}
	
	@Test(priority = 290, enabled = true)
	@Description("Enable Preconditions to change region to en_GB")
	public void enable_preConditions_toChange_Region_for_en_GB() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** enable Preconditions to change region to en_GB test case Started");
		logStep("****** enable Preconditions to change region to en_GB test case Started");
		
		Functions.Appium_Autostart();
		Functions.archive_folder("Charles");
		proxy.startRecording();
		proxy.clearCharlesSession();
		Functions.launchtheAndroidApp_forLocalization("GB", true, "en", true);
		System.out.println("App launched ");
		logStep("App launched ");
		//proxy.getXml();
		Functions.archive_folder("Charles");
		Functions.close_launchAppAndroid();
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(10000);
		//Functions.checkForAppState();
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		
		Ad.terminateApp("com.weather.Weather");
	}

	@Test(priority = 291, enabled = true)
	@Description("Validating NextGen IM Call for en_GB")
	public void validate_NextGen_IM_call_for_en_GB() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Call for en_GB");
		logStep("Validating NextGen IM Call for en_GB");

		Utils.verifyPubadCal("iu=%2F7646%2Fapp_android_intl%2Fdb_display%2Fhome_screen%2Fmarquee");

	}
	
	@Test(priority = 295, enabled = true)
	@Description("Enable Preconditions to change region to en_CA")
	public void enable_preConditions_toChange_Region_for_en_CA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** enable Preconditions to change region to en_CA test case Started");
		logStep("****** enable Preconditions to change region to en_CA test case Started");
		
		Functions.Appium_Autostart();
		Functions.archive_folder("Charles");
		proxy.startRecording();
		proxy.clearCharlesSession();
		Functions.launchtheAndroidApp_forLocalization("CA", true, "en", true);
		System.out.println("App launched ");
		logStep("App launched ");
		//proxy.getXml();
		Functions.archive_folder("Charles");
		Functions.close_launchAppAndroid();
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(10000);
		//Functions.checkForAppState();
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		
		Ad.terminateApp("com.weather.Weather");
	}

	@Test(priority = 296, enabled = true)
	@Description("Validating NextGen IM Call for en_CA")
	public void validate_NextGen_IM_call_for_en_CA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Call for en_CA");
		logStep("Validating NextGen IM Call for en_CA");

		Utils.verifyPubadCal("iu=%2F7646%2Fapp_android_intl%2Fdb_display%2Fhome_screen%2Fmarquee");

	}
	
	@Test(priority = 300, enabled = true)
	@Description("Enable Preconditions to change region to fr_FR")
	public void enable_preConditions_toChange_Region_for_fr_FR() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** enable Preconditions to change region to fr_FR test case Started");
		logStep("****** enable Preconditions to change region to fr_FR test case Started");
	
		Functions.Appium_Autostart();
		Functions.archive_folder("Charles");
		proxy.startRecording();
		proxy.clearCharlesSession();
		Functions.launchtheAndroidApp_forLocalization("FR", true, "fr", true);
		System.out.println("App launched ");
		logStep("App launched ");
		//proxy.getXml();
		Functions.archive_folder("Charles");
		Functions.close_launchAppAndroid();
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(10000);
		//Functions.checkForAppState();
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		
		Ad.terminateApp("com.weather.Weather");
	}

	@Test(priority = 301, enabled = true)
	@Description("Validating NextGen IM Call for fr_FR")
	public void validate_NextGen_IM_call_for_fr_FR() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Call for fr_FR");
		logStep("Validating NextGen IM Call for fr_FR");

		Utils.verifyPubadCal("iu=%2F7646%2Fapp_android_intl%2Fdb_display%2Fhome_screen%2Fmarquee");

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
		//Functions.checkForAppState();
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Functions.archive_folder("Charles");
		hrTab = new AndroidHourlyNavTab(Ad);
		dTab = new AndroidDailyNavTab(Ad);
		hmTab = new AndroidHomeNavTab(Ad);
		rTab = new AndroidRadarNavTab(Ad);
		vTab = new AndroidVideoNavTab(Ad);
		addrScreen = new AndroidAddressScreen(Ad);
		
		sScreen = new AndroidSeasonalHubCardScreen(Ad);
		bnScreen = new AndroidBreakingNewsCardScreen(Ad);
		stScreen = new AndroidSettingsScreen(Ad);
		loginScreen = new AndroidLogInScreen(Ad);
		fOneCardScreen = new AndroidFeedOneCardScreen(Ad);
		aqCardScreen = new AndroidAirQualityCardScreen(Ad);
		aqCardContentScreen = new AndroidAirQualityCardContentScreen(Ad);
		
		//Ad.terminateApp("com.weather.Weather");
	}

	@Test(priority = 306, enabled = true)
	@Description("Validating NextGen IM Call for en_US")
	public void validate_NextGen_IM_call_for_en_US() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Call for en_US");
		logStep("Validating NextGen IM Call for en_US");

		Utils.verifyPubadCal("iu=%2F7646%2Fapp_android_us%2Fdb_display%2Fhome_screen%2Fmarquee");

	}

	@Test(priority = 325, enabled = true)
	@Description("Validating Privacy Card and its options existance")
	public void validate_Privacy_card_and_Options() throws Exception {
		System.out.println("==============================================");
		//System.out.println("Started Validating Privacy Card");
		//System.out.println("****** Navigating to Privacy Card ");
		//logStep("Navigating to Privacy Card ");
		proxy.clearCharlesSession();
		// Utils.navigateTofeedCard("privacy");
		System.out.println("****** Validating Privacy Card and its options existance");
		logStep("****** Validating Privacy Card and its options existance");
		stScreen.verify_PrivacyCard_Options_From_Settings("Smoke", "Privacy");

	}

	@Test(priority = 350, enabled = true)
	@Description("Enabling Preconfiguration for Severe1 Breaking News Card")
	public void enable_PreConfiguration_for_servere1_BreakingNewsCard() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Enable Preconfiguration for Severe1 Breaking News Card");
		logStep("Enable Preconfiguration for Severe1 Breaking News Card");
		proxy.quitCharlesProxy();
		//this.configFile = this.changeVt1ContentMode(BN_SEVERE1_CONFIG_FILE_PATH, "severe1");
		this.configFile = this.changeVt1ContentModeWhenNoTunnelBearAndroid(BN_SEVERE1_CONFIG_FILE_PATH, "severe1", "usa", "US", "WA");
		proxy = new CharlesProxy("localhost", 8111, BN_SEVERE1_CONFIG_FILE_PATH);
		proxy.startCharlesProxyWithUI();
		proxy.enableRewriting();
		proxy.startRecording();
		proxy.disableMapLocal();
		Ad.resetApp();
		TestBase.waitForMilliSeconds(2000);
		Functions.close_launchAppAndroid();
		//Functions.checkForAppState();
		//stScreen.clear_Airlock_Cache();
		proxy.clearCharlesSession();
		Functions.archive_folder("Charles");
		Functions.close_launchAppAndroid();
		//Functions.checkForAppState();
		addrScreen.enter_requiredLocation("30303");
		TestBase.waitForMilliSeconds(20000);
		//Utils.navigateTofeedCard("breakingnews", false, false);
		bnScreen.navigateToBreakingNewsCard();
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		
	}

	@Test(priority = 351, enabled = true)
	@Description("Verify BreakingNews Severe1 ad call iu")
	public void Verify_BreakingNews_Severe1_AdCall() throws Exception {
		System.out.println("==============================================");
		
		System.out.println("****** Breaking News Severe1 Adcall verification test case Started");
		logStep("****** Breaking News Severe1 Adcall verification test case Started");
		
		Utils.verifyPubadCal("Smoke", "BreakingNews");
	}

	/**
	 * This method validates bn custom parameter of Breaking News Severe1 call
	 */
	@Test(priority = 352, enabled = true)
	@Description("Validating 'bn' custom parameter of BreakingNews Severe1 call ")
	public void Validate_BreakingNews_Severe1_bn_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating bn custom parameter of Breaking News Severe1 call");
		logStep("Validating bn custom parameter of Breaking News Severe1 call ");

		Utils.validate_custom_param_val_of_gampad("Smoke", "BreakingNews", "bn", "h");

	}

	/**
	 * This method validates pos custom parameter of Breaking News Severe1 call
	 */
	@Test(priority = 353, enabled = true)
	@Description("Validating 'pos' custom parameter of BreakingNews Severe1 call ")
	public void Validate_BreakingNews_Severe1_pos_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating pos custom parameter of Breaking News Severe1 call");
		logStep("Validating pos custom parameter of Breaking News Severe1 call ");

		Utils.validate_custom_param_val_of_gampad("Smoke", "BreakingNews", "pos", "app_sl");

	}
	
	/**
	 * This method validates ltv custom parameter of Breaking News Severe1 call
	 */
	@Test(priority = 354, enabled = true)
	@Description("Validating 'ltv' custom parameter of Breaking News Severe1 call ")
	public void Validate_BreakingNews_Severe1_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of Breaking News Severe1 call");
		logStep("Validating ltv custom parameter of Breaking News Severe1 call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "BreakingNews", "ltv", "NotNull");

	}

	@Test(priority = 361, enabled = true)
	@Description("Verify BreakingNews Severe1 Video1 ad call iu")
	public void Verify_BreakingNews_Video1_Severe1_AdCall() throws Exception {
		System.out.println("==============================================");
		
		System.out.println("****** Breaking News Severe1 Video1 Adcall verification test case Started");
		logStep("****** Breaking News Severe1 Video1 Adcall verification test case Started");
		Functions.archive_folder("Charles");
		proxy.clearCharlesSession();
		bnScreen.navigateToBreakingNewsDetailsPage(proxy);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.get_iu_value_of_Feedcall("Smoke", "PreRollVideo");
		navigateBackToFeedCardAndroid();

	}

	/**
	 * This method validates bn custom parameter of Breaking News Severe1 Video1
	 * call
	 */
	@Test(priority = 362, enabled = true)
	@Description("Validating 'bn' custom parameter of BreakingNews Severe1 Video1 call ")
	public void Validate_BreakingNews_Video1_Severe1_bn_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating bn custom parameter of Breaking News Severe1 Video1 call");
		logStep("Validating bn custom parameter of Breaking News Severe1 Video1 call ");

		Utils.validate_custom_param_val_of_gampad("Smoke", "PreRollVideo", "bn", "h");

	}

	/**
	 * This method validates ref custom parameter of Breaking News Severe1 Video1
	 * call
	 */
	@Test(priority = 363, enabled = true)
	@Description("Validating 'ref' custom parameter of BreakingNews Severe1 Video1 call ")
	public void Validate_BreakingNews_Video1_Severe1_ref_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ref custom parameter of Breaking News Severe1 Video1 call");
		logStep("Validating ref custom parameter of Breaking News Severe1 Video1 call ");

		Utils.validate_custom_param_val_of_gampad("Smoke", "PreRollVideo", "ref", "brn");

	}
	
	@Test(priority = 375, enabled = true)
	@Description("Enabling Preconfiguration for Severe2 Breaking News Card")
	public void enable_PreConfiguration_for_servere2_BreakingNewsCard() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Enable Preconfiguration for Severe2 Breaking News Card");
		logStep("Enable Preconfiguration for Severe2 Breaking News Card");
		proxy.quitCharlesProxy();
		//this.configFile = this.changeVt1ContentMode(BN_SEVERE2_CONFIG_FILE_PATH, "severe2");
		this.configFile = this.changeVt1ContentModeWhenNoTunnelBearAndroid(BN_SEVERE2_CONFIG_FILE_PATH, "severe2", "usa", "US", "WA");
		proxy = new CharlesProxy("localhost", 8111, BN_SEVERE2_CONFIG_FILE_PATH);
		proxy.startCharlesProxyWithUI();
		proxy.enableRewriting();
		proxy.startRecording();
		proxy.disableMapLocal();
		Ad.resetApp();
		TestBase.waitForMilliSeconds(2000);
		Functions.close_launchAppAndroid();
		//Functions.checkForAppState();
		//stScreen.clear_Airlock_Cache();
		proxy.clearCharlesSession();
		Functions.archive_folder("Charles");
		Functions.close_launchAppAndroid();
		//Functions.checkForAppState();
		addrScreen.enter_requiredLocation("30303");
		TestBase.waitForMilliSeconds(20000);
		bnScreen.navigateToBreakingNewsCard();
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();

	}

	@Test(priority = 376, enabled = true)
	@Description("Verify BreakingNews Severe2 ad call iu")
	public void Verify_BreakingNews_Severe2_AdCall() throws Exception {
		System.out.println("==============================================");
		
		System.out.println("****** Breaking News Severe2 Adcall verification test case Started");
		logStep("****** Breaking News Severe2 Adcall verification test case Started");
		
		Utils.verifyPubadCal("Smoke", "BreakingNews");
	}

	/**
	 * This method validates bn custom parameter of Breaking News Severe2 call
	 */
	@Test(priority = 377, enabled = true)
	@Description("Validating 'bn' custom parameter of BreakingNews Severe2 call ")
	public void Validate_BreakingNews_Severe2_bn_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating bn custom parameter of Breaking News Severe2 call");
		logStep("Validating bn custom parameter of Breaking News Severe2 call ");

		Utils.validate_custom_param_val_of_gampad("Smoke", "BreakingNews", "bn", "y");

	}

	/**
	 * This method validates pos custom parameter of Breaking News Severe2 call
	 */
	@Test(priority = 378, enabled = true)
	@Description("Validating 'pos' custom parameter of BreakingNews Severe2 call ")
	public void Validate_BreakingNews_Severe2_pos_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating pos custom parameter of Breaking News Severe2 call");
		logStep("Validating pos custom parameter of Breaking News Severe2 call ");

		Utils.validate_custom_param_val_of_gampad("Smoke", "BreakingNews", "pos", "app_sl");

	}
	
	/**
	 * This method validates ltv custom parameter of Breaking News Severe2 call
	 */
	@Test(priority = 379, enabled = true)
	@Description("Validating 'ltv' custom parameter of Breaking News Severe2 call ")
	public void Validate_BreakingNews_Severe2_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of Breaking News Severe2 call");
		logStep("Validating ltv custom parameter of Breaking News Severe2 call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "BreakingNews", "ltv", "NotNull");

	}

	@Test(priority = 381, enabled = true)
	@Description("Verify BreakingNews Severe2 Video1 ad call iu")
	public void Verify_BreakingNews_Video1_Severe2_AdCall() throws Exception {
		System.out.println("==============================================");
		
		System.out.println("****** Breaking News Severe2 Video1 Adcall verification test case Started");
		logStep("****** Breaking News Severe2 Video1 Adcall verification test case Started");
		Functions.archive_folder("Charles");
		proxy.clearCharlesSession();
		bnScreen.navigateToBreakingNewsDetailsPage(proxy);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.get_iu_value_of_Feedcall("Smoke", "PreRollVideo");
		navigateBackToFeedCardAndroid();
	}

	/**
	 * This method validates bn custom parameter of Breaking News Severe2 Video1
	 * call
	 */
	@Test(priority = 382, enabled = true)
	@Description("Validating 'bn' custom parameter of BreakingNews Severe2 Video1 call ")
	public void Validate_BreakingNews_Video1_Severe2_bn_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating bn custom parameter of Breaking News Severe2 Video1 call");
		logStep("Validating bn custom parameter of Breaking News Severe2 Video1 call ");

		Utils.validate_custom_param_val_of_gampad("Smoke", "PreRollVideo", "bn", "y");

	}

	/**
	 * This method validates ref custom parameter of Breaking News Severe1 Video1
	 * call
	 */
	@Test(priority = 383, enabled = true)
	@Description("Validating 'ref' custom parameter of BreakingNews Severe2 Video1 call ")
	public void Validate_BreakingNews_Video1_Severe2_ref_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ref custom parameter of Breaking News Severe2 Video1 call");
		logStep("Validating ref custom parameter of Breaking News Severe2 Video1 call ");

		Utils.validate_custom_param_val_of_gampad("Smoke", "PreRollVideo", "ref", "brn");

	}
	
	@Test(priority = 400, enabled = true)
	@Description("Enabling Preconfiguration for Severe1 Editorial Video Headline Card Breaking News Card")
	public void enable_PreConfiguration_for_servere1_EditorialVideoHeadlineCard_BreakingNewsCard() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Enable Preconfiguration for Severe1 Editorial Video Headline Card Breaking News Card");
		logStep("Enable Preconfiguration for Severe1 Editorial Video Headline Card Breaking News Card");
		
		proxy.quitCharlesProxy();
		String jsonPath = "src/test/resources/Editorial.json";
		//reWriteContentModeAndMapLocalForEditorialVideoHeadLineCard(EDITORIAL_SEVERE1_MAP_LOCAL_CONFIG_FILE_PATH, jsonPath, "severe1");
		reWriteContentModeAndMapLocalForEditorialVideoHeadLineCardWhenNoTunnelBearAndroid(EDITORIAL_SEVERE1_MAP_LOCAL_CONFIG_FILE_PATH, jsonPath, "severe1", "usa", "US", "WA");
		proxy = new CharlesProxy("localhost", 8111, EDITORIAL_SEVERE1_MAP_LOCAL_CONFIG_FILE_PATH);
		proxy.startCharlesProxyWithUI();
		proxy.stopRecording();
		proxy.enableMapLocal();
		proxy.enableRewriting();
		
		proxy.startRecording();
		
		Functions.archive_folder("Charles");
		Ad.resetApp();
		TestBase.waitForMilliSeconds(2000);
		Functions.close_launchAppAndroid();
		//Functions.checkForAppState();
		
//		stScreen.select_Airlock_UserGroup("IOSFLAG-6963");
//		stScreen.clear_Airlock_Cache();
		
//		proxy.clearCharlesSession();
//		Functions.close_launchAppAndroid();
//		//Functions.checkForAppState();
		addrScreen.enter_requiredLocation("30303");
		TestBase.waitForMilliSeconds(20000);
			
		bnScreen.navigateToEditorialVideoHeadlineBreakingNewsCard();		
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
	}

	@Test(priority = 401, enabled = true)
	@Description("Verify Editorial Video Headline Card Breaking News Severe1 Video1 ad call iu")
	public void Verify_Editorial_Video_Headline_Card_Breaking_News_Card_Video1_Severe1_AdCalls() throws Exception {
		System.out.println("==============================================");
		
		System.out.println("****** Editorial Video Headline Card Breaking News Card Severe1 Video1 Adcall verification test case Started");
		logStep("****** Editorial Video Headline Card Breaking News Card Severe1 Video1 Adcall verification test case Started");
		Functions.archive_folder("Charles");
		proxy.clearCharlesSession();
		bnScreen.navigateToEditorialVideoHeadlineCardBreakingNewsDetailsPage(proxy);	
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.get_iu_value_of_Feedcall("Smoke", "PreRollVideo");
		navigateBackToFeedCardAndroid();

	}

	
	/**
	 * This method validates ref custom parameter of Editorial Video Headline Card Breaking News Severe1 Video1
	 * call
	 */
	@Test(priority = 402, enabled = true)
	@Description("Validating 'ref' custom parameter of Editorial Video Headline Card Breaking News Severe1 Video1 call ")
	public void Validate_Editorial_Video_Headline_Card_Breaking_News_Card_Video1_Severe1_ref_Custom_params() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ref custom parameter of Editorial Video Headline Card Breaking News Severe1 Video1 call");
		logStep("Validating ref custom parameter of Editorial Video Headline Card Breaking News Severe1 Video1 call ");

		Utils.validate_custom_param_val_of_gampad("Smoke", "PreRollVideo", "ref", "brn");

	}
	
	/**
	 * This method validates bn custom parameter of Editorial Video Headline Card Breaking News Severe1 Video1
	 * call
	 */
	@Test(priority = 403, enabled = true)
	@Description("Validating 'bn' custom parameter of Editorial Video Headline Card Breaking News Severe1 Video1 call ")
	public void Validate_Editorial_Video_Headline_Card_Breaking_News_Card_Video1_Severe1_bn_Custom_params() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating bn custom parameter of Editorial Video Headline Card Breaking News Severe1 Video1 call");
		logStep("Validating bn custom parameter of Editorial Video Headline Card Breaking News Severe1 Video1 call");

		Utils.validate_custom_param_val_of_gampad("Smoke", "PreRollVideo", "bn", "h");

	}
	
	@Test(priority = 425, enabled = true)
	@Description("Enabling Preconfiguration for Severe2 Editorial Video Headline Card Breaking News Card")
	public void enable_PreConfiguration_for_servere2_EditorialVideoHeadlineCard_BreakingNewsCard() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Enable Preconfiguration for Severe2 Editorial Video Headline Card Breaking News Card");
		logStep("Enable Preconfiguration for Severe2 Editorial Video Headline Card Breaking News Card");
		
		proxy.quitCharlesProxy();
		String jsonPath = "src/test/resources/Editorial.json";
		//reWriteContentModeAndMapLocalForEditorialVideoHeadLineCard(EDITORIAL_SEVERE2_MAP_LOCAL_CONFIG_FILE_PATH, jsonPath, "severe2");
		reWriteContentModeAndMapLocalForEditorialVideoHeadLineCardWhenNoTunnelBearAndroid(EDITORIAL_SEVERE2_MAP_LOCAL_CONFIG_FILE_PATH, jsonPath, "severe2", "usa", "US", "WA");
		proxy = new CharlesProxy("localhost", 8111, EDITORIAL_SEVERE2_MAP_LOCAL_CONFIG_FILE_PATH);
		proxy.startCharlesProxyWithUI();
		proxy.stopRecording();
		proxy.enableMapLocal();
		proxy.enableRewriting();

		proxy.startRecording();
		
		Functions.archive_folder("Charles");
		Ad.resetApp();
		TestBase.waitForMilliSeconds(2000);
		Functions.close_launchAppAndroid();
		//Functions.checkForAppState();
		
//		stScreen.select_Airlock_UserGroup("IOSFLAG-6963");
//		stScreen.clear_Airlock_Cache();
		
//		proxy.clearCharlesSession();
//		Functions.close_launchAppAndroid();
//		//Functions.checkForAppState();
		addrScreen.enter_requiredLocation("10003");
		TestBase.waitForMilliSeconds(20000);
			
		bnScreen.navigateToEditorialVideoHeadlineBreakingNewsCard();		
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
	}

	@Test(priority = 426, enabled = true)
	@Description("Verify Editorial Video Headline Card Breaking News Severe2 Video1 ad call iu")
	public void Verify_Editorial_Video_Headline_Card_Breaking_News_Card_Video1_Severe2_AdCalls() throws Exception {
		System.out.println("==============================================");
		
		System.out.println("****** Editorial Video Headline Card Breaking News Card Severe2 Video1 Adcall verification test case Started");
		logStep("****** Editorial Video Headline Card Breaking News Card Severe2 Video1 Adcall verification test case Started");
		Functions.archive_folder("Charles");
		proxy.clearCharlesSession();
		bnScreen.navigateToEditorialVideoHeadlineCardBreakingNewsDetailsPage(proxy);	
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.get_iu_value_of_Feedcall("Smoke", "PreRollVideo");
		navigateBackToFeedCardAndroid();

	}

	
	/**
	 * This method validates ref custom parameter of Editorial Video Headline Card Breaking News Severe2 Video1
	 * call
	 */
	@Test(priority = 427, enabled = true)
	@Description("Validating 'ref' custom parameter of Editorial Video Headline Card Breaking News Severe2 Video1 call ")
	public void Validate_Editorial_Video_Headline_Card_Breaking_News_Card_Video1_Severe2_ref_Custom_params() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ref custom parameter of Editorial Video Headline Card Breaking News Severe2 Video1 call");
		logStep("Validating ref custom parameter of Editorial Video Headline Card Breaking News Severe2 Video1 call ");

		Utils.validate_custom_param_val_of_gampad("Smoke", "PreRollVideo", "ref", "brn");

	}
	
	/**
	 * This method validates bn custom parameter of Editorial Video Headline Card Breaking News Severe2 Video1
	 * call
	 */
	@Test(priority = 428, enabled = true)
	@Description("Validating 'bn' custom parameter of Editorial Video Headline Card Breaking News Severe2 Video1 call ")
	public void Validate_Editorial_Video_Headline_Card_Breaking_News_Card_Video1_Severe2_bn_Custom_params() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating bn custom parameter of Editorial Video Headline Card Breaking News Severe2 Video1 call");
		logStep("Validating bn custom parameter of Editorial Video Headline Card Breaking News Severe2 Video1 call ");

		Utils.validate_custom_param_val_of_gampad("Smoke", "PreRollVideo", "bn", "y");

	}

	/**
	 * This Script Validate NextGen IM Ad and its parameters
	 * @throws Exception
	 */
	@Test(priority = 601, enabled = true)
	@Description("Validating NextGen IM Static Ad when app in test mode")
	public void Validate_NextGenIM_StaticAd() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Static Ad in test mode");
		logStep("Validating NextGen IM Static Ad in test mode ");
		proxy.quitCharlesProxy();
		//proxy = new CharlesProxy("localhost", 8111, CRITEO_CONFIG_FILE_PATH);
		proxy = new CharlesProxy("localhost", 8111, CONFIG_FILE_PATH);
		proxy.startCharlesProxyWithUI();
		//proxy.disableRewriting();
		//proxy.disableMapLocal();
		proxy.enableRewriting();
		proxy.disableMapLocal();
		proxy.clearCharlesSession();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		Functions.close_launchAppAndroid();
		hmTab.clickonHomeTab();
		stScreen.select_Airlock_UserGroup("AdsTestAdUnitOnly");
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		//Functions.checkForAppState();
		//addrScreen.enter_requiredLocation("07095");
		addrScreen.enter_requiredLocation("31553");
		TestBase.waitForMilliSeconds(20000);		
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.verifyPubadCal("Smoke", "NextGenIM");
		
	}

	/**
	 * This Script Validate NextGen IM Ad response
	 * @throws Exception
	 */
	@Test(priority = 602, enabled = true)
	@Description("Validating NextGen IM Static Ad response when app in test mode")
	public void Validate_NextGenIM_StaticAd_response() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Static Ad response in test mode");
		logStep("Validating NextGen IM Static Ad response in test mode ");

		Utils.verifyMarqueeAd_byCallResponse("Smoke", "NextGenIM");

	}

	/**
	 * This Script Validate NextGen IM Ad and its parameters
	 * @throws Exception
	 */

	@Test(priority = 603, enabled = true)

	@Description("Validating NextGen IM Static Ad BackGround Asset Call")
	public void Validate_NextGenIM_StaticAd_bgAssetCall() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Static Ad BG Asset Call");
		logStep("Validating NextGen IM Static Ad BG Asset Call ");

		Utils.verifyBGAd_byCallResponse("Smoke", "NextGenIM", "Static");

	}

	/**
	 * This Script Validate NextGen IM Ad and its parameters
	 * @throws Exception
	 */

/*	@Test(priority = 604, enabled = true)
	@Description("Validating NextGen IM Static Ad ForeGround Asset Call")
	public void Validate_NextGenIM_StaticAd_fgAssetCall() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Static Ad FG Asset Call");
		logStep("Validating NextGen IM Static Ad FG Asset Call ");

		Utils.verifyFGAd_byCallResponse("Smoke", "NextGenIM", "Static");

	}*/

	@Test(priority = 605, enabled = true)
	@Description("Validating NextGen IM Static Ad sz parameter")
	public void Validate_NextGenIM_StaticAd_Size() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Static Ad sz parameter in charles");
		logStep("Validating NextGen IM Static Ad sz parameter in charles ");

		Utils.verify_Ad_Size("Smoke", "NextGenIM");

	}

	@Test(priority = 611, enabled = true)
	@Description("Validating NextGen IM Video Ad and its Parameters when app in test mode")
	public void Validate_NextGenIM_VideoAd() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Video Ad in test mode");
		logStep("Validating NextGen IM Video Ad in test mode ");
		proxy.clearCharlesSession();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		//addrScreen.enternewAddress(false, "61920", "Charleston, Illinois");
		//addrScreen.enter_requiredLocation("37421");
		addrScreen.enter_requiredLocation("30096");
		TestBase.waitForMilliSeconds(20000);	
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.verifyPubadCal("Smoke", "NextGenIM");

	}

	@Test(priority = 612, enabled = true)
	@Description("Validating NextGen IM Video Ad response when app in test mode")
	public void Validate_NextGenIM_VideoAd_response() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Video Ad response in test mode");
		logStep("Validating NextGen IM Video Ad response in test mode ");
		Utils.verifyMarqueeAd_byCallResponse("Smoke", "NextGenIM");

	}

	/**
	 * This Script Validate NextGen IM Ad and its parameters
	 * @throws Exception
	 */
	@Test(priority = 613, enabled = true)
	@Description("Validating NextGen IM Video Ad BackGround Asset Call")
	public void Validate_NextGenIM_VideoAd_bgAssetCall() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Video Ad BG Asset Call");
		logStep("Validating NextGen IM Video Ad BG Asset Call ");
		Utils.verifyBGAd_byCallResponse("Smoke", "NextGenIM", "Video");

	}

	/**
	 *This Script Validate NextGen IM Ad and its parameters Video
	 *ad will not be having only fg asset call always, it has
	 *only bg asset call hence commenting
	 *@throws Exception 
	 */
	/*
	 * @Test(priority = 614, enabled = true)
	 * 
	 * @Description("Validating NextGen IM Video Ad ForeGround Asset Call") public
	 * void Validate_NextGenIM_VideoAd_fgAssetCall() throws Exception {
	 * System.out.println("==============================================");
	 * System.out.println("****** Validating NextGen IM Video Ad FG Asset Call");
	 * logStep("Validating NextGen IM Video Ad FG Asset Call ");
	 * 
	 * Utils.verifyFGAd_byCallResponse("Smoke", "NextGenIM", "Video");
	 * 
	 * }
	 */

	@Test(priority = 615, enabled = true)
	@Description("Validating NextGen IM Video Ad sz parameter")
	public void Validate_NextGenIM_VideoAd_Size() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGen IM Video Ad sz parameter in charles");
		logStep("Validating NextGen IM Video Ad sz parameter in charles ");
		Utils.verify_Ad_Size("Smoke", "NextGenIM");

	}
	
	@Test(priority = 651, enabled = true)
	@Description("Validating Tapability Of HomeScreen Sticky Test Ad when app in test mode")
	public void Validate_TapabilityOfHomeScreenStickyTestAd() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Tapability Of HomeScreen Sticky Test Ad in test mode");
		logStep("Validating Tapability Of HomeScreen Sticky Test Ad in test mode ");
		//proxy.disableRewriting();
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		addrScreen.selectCurrentLocation();
		TestBase.waitForMilliSeconds(20000);
		hmTab.clickonHomeTab();
		hmTab.clickonHomeTab();
		hmTab.verifyTapabilityOfStickyTestAdOnHomeScreen();
		
	}
	
	@Test(priority = 652, enabled = true)
	@Description("Validating Tapability Of Daily Nav Tab Test Ad when app in test mode")
	public void Validate_TapabilityOfDailyNavTabTestAd() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Tapability Of Daily Nav Tab Test Ad in test mode");
		logStep("Validating Tapability Of Daily Nav Tab Test Ad in test mode ");
		//proxy.disableRewriting();
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		dTab.navigateToDailyTab();
		dTab.verifyTapabilityOfTestAdOnDailyNavTab();
		
	}
	
	@Test(priority = 653, enabled = true)
	@Description("Validating Tapability Of Radar Nav Tab Test Ad when app in test mode")
	public void Validate_TapabilityOfRadarNavTabTestAd() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Tapability Of Radar Nav Tab Test Ad in test mode");
		logStep("Validating Tapability Of Radar Nav Tab Test Ad in test mode ");
		//proxy.disableRewriting();
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		hmTab.clickonHomeTab();
		hmTab.clickonHomeTab();
		rTab.navigateToRadarTab();
		rTab.verifyTapabilityOfTestAdOnRadarNavTab();
		
	}
	
	/*@Test(priority = 654, enabled = true)
	@Description("Validating Tapability Of Seasonal Hub Details Page Test Ad when app in test mode")
	public void Validate_TapabilityOfSeasonalHubDetailsPageTestAd() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Tapability Of Seasonal Hub Details Page Test Ad in test mode");
		logStep("Validating Tapability Of Seasonal Hub Details Page Test Ad in test mode ");
		proxy.disableRewriting();
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		hmTab.clickonHomeTab();
		Utils.navigateTofeedCard("seasonalhub");
		sScreen.navigateToDetailsOfFirstIndexOfSeasonalHubCard();
		sScreen.verifyTapabilityOfTestAdOnSeasonalHubDetailsPage();
		
	}*/

	/**
	 *This Script Validate Integrated Daily Details Ad Call and its response
	 * @throws Exception 
	 */
	@Test(priority = 701, enabled = true)
	@Description("Validating IDD Ad when app in test mode")
	public void Validate_IDD_Ad() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating IDD Ad in test mode");
		logStep("Validating IDD Ad in test mode ");
		//Functions.close_launchAppAndroid();
		hmTab.clickonHomeTab();
		//stScreen.select_Airlock_UserGroup("IOSFLAG-4200");
		//Functions.close_launchAppAndroid();
		//Functions.checkForAppState();
		proxy.clearCharlesSession();
		//addrScreen.enternewAddress(false, "30124", "Cave Spring, Georgia");
		addrScreen.enter_requiredLocation("30124");
		TestBase.waitForMilliSeconds(20000);
		dTab.navigateToDailyTab();
		TestBase.waitForMilliSeconds(10000);
		Functions.archive_folder("Charles");
		//TestBase.waitForMilliSeconds(5000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.get_v3_wx_forecast_daily_15day_data();
		Utils.verifyPubadCal("Smoke", "IDD");

	}

	@Test(priority = 702, enabled = true)
	@Description("Validating IDD Ad response")
	public void Validate_IDD_Ad_response() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating IDD Ad response");
		logStep("Validating IDD Ad response");
		dTab.verifyIDDAd_byCallResponse("Smoke", "IDD");

	}

	/**
	 * This Script Validate IDD Ad and its parameters
	 * @throws Exception 
	 */

	@Test(priority = 703, enabled = true)
	@Description("Validating IDD Static Ad BackGround Asset Call")
	public void Validate_IDD_StaticAd_bgAssetCall() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating IDD Static Ad BG Asset Call");
		logStep("Validating IDD Static Ad BG Asset Call ");
		Utils.verifyBGAd_byCallResponse("Smoke", "IDD", "Static");

	}

	/**
	 * This Script Validate IDD Ad and its parameters
	 * @throws Exception 
	 */

	@Test(priority = 704, enabled = true)
	@Description("Validating IDD Static Ad ForeGround Asset Call")
	public void Validate_IDD_StaticAd_fgAssetCall() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating IDD Static Ad FG Asset Call");
		logStep("Validating IDD Static Ad FG Asset Call ");
		Utils.verifyFGAd_byCallResponse("Smoke", "IDD", "Static");
	}

	@Test(priority = 705, enabled = true)
	@Description("Validating IDD Ad sz parameter")
	public void Validate_IDD_Ad_Size() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating IDD Ad sz parameter in charles");
		logStep("Validating IDD Ad sz parameter in charles ");
		Utils.verify_Ad_Size("Smoke", "IDD");

	}

	/**
	 * This Script Validate Integrated Feed Card Static Ad Call and its response
	 * @throws Exception                 
	 */
	@Test(priority = 751, enabled = true)
	@Description("Validating Integrated Feed Card Static Ad i.e. Feed1 Card when app in test mode")
	public void Validate_Integrated_FeedCard_StaticAd() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Integrated Feed Card Static Ad in test mode");
		logStep("Validating Integrated Feed Card Static Ad in test mode ");
		try{
			Functions.close_launchAppAndroid();
			hmTab.clickonHomeTab();
			/*stScreen.select_Airlock_UserGroup("IntegratedAdCard");
			proxy.clearCharlesSession();
			Functions.archive_folder("Charles");
			Functions.close_launchAppAndroid();*/
			//Functions.checkForAppState();
			//addrScreen.enternewAddress(false, "30124", "Cave Spring, Georgia");
			proxy.clearCharlesSession();
			Functions.archive_folder("Charles");
			addrScreen.enter_requiredLocation("30124");
			TestBase.waitForMilliSeconds(20000);
			// Since as part of sticky ad implementation on UI weather.feed1 appears as weather.feed0 and so on
			//hence modified Integrated feed card name to weather.feed0 from weather.feed1
			//Utils.navigateTofeedCard("weather.feed0", false, false);
			fOneCardScreen.scrollToFeedOneCard();
			TestBase.waitForMilliSeconds(20000);
					
		} finally {
			proxy.getXml();
			Utils.createXMLFileForCharlesSessionFile();
			Utils.verifyPubadCal("Smoke", "IntegratedFeedCard");
		}
	}

	@Test(priority = 752, enabled = true)
	@Description("Validating Integrated Feed Card Static Ad response")
	public void Validate_Integrated_FeedCard_StaticAd_response() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Integrated Feed Card Static Ad response");
		logStep("Validating Integrated Feed Card Static Ad response");
		Utils.verifyIntegratedFeedCardAd_byCallResponse("Smoke", "IntegratedFeedCard");
	}

	/**
	 * This Script Validate Integrated Feed Card Static Ad and its parameters
	 * @throws Exception                
	 */

	@Test(priority = 753, enabled = true)
	@Description("Validating Integrated Feed Card Static Ad BackGround Asset Call")
	public void Validate_Integrated_FeedCard_StaticAd_bgAssetCall() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Integrated Feed Card Static Ad BG Asset Call");
		logStep("Validating Integrated Feed Card Static Ad BG Asset Call ");
		
		Utils.verifyAssetCallWithHostandPath("s.w-x.co", "/cl/prototype/", "-ifc-stc", "-ifc-stc");
	}

	/**
	 * This Script Validate Integrated Feed Card Static Ad and its parameters
	 * @throws Exception              
	 */
	@Test(priority = 754, enabled = true)
	@Description("Validating Integrated Feed Card Static Ad ForeGround Asset Call")
	public void Validate_Integrated_FeedCard_StaticAd_fgAssetCall() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Integrated Feed Card Static Ad FG Asset Call");
		logStep("Validating Integrated Feed Card Static Ad FG Asset Call ");
		
		Utils.verifyAssetCallWithHostandPath("s.w-x.co", "/cl/prototype/", "-ifc-stc", "-fg");

	}

	@Test(priority = 755, enabled = true)
	@Description("Validating Integrated Feed Card Static Ad sz parameter")
	public void Validate_Integrated_FeedCard_StaticAd_Size() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Integrated Feed Card Static Ad sz parameter in charles");
		logStep("Validating Integrated Feed Card Static Ad sz parameter in charles ");
		Utils.verify_Ad_Size("Smoke", "IntegratedFeedCard");

	}

	/**
	 * This Script Validate Integrated Feed Card Video Ad Call and its response
	 * @throws Exception          
	 */
	@Test(priority = 761, enabled = true)
	@Description("Validating Integrated Feed Card Video Ad i.e. Feed1 Card when app in test mode")
	public void Validate_Integrated_FeedCard_VideoAd() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Integrated Feed Card Video Ad in test mode");
		logStep("Validating Integrated Feed Card Video Ad in test mode ");
		try {
			Functions.close_launchAppAndroid();
			hmTab.clickonHomeTab();
			proxy.clearCharlesSession();
			Functions.archive_folder("Charles");
			//addrScreen.enternewAddress(false, "08824", "Kendall Park, New Jersey");
			addrScreen.enter_requiredLocation("08824");
			TestBase.waitForMilliSeconds(20000);
			// Since as part of sticky ad implementation on UI weather.feed1 appears as weather.feed0 and so on
			//hence modified Integrated feed card name to weather.feed0 from weather.feed1
			//Utils.navigateTofeedCard("weather.feed0", false, false);
			fOneCardScreen.scrollToFeedOneCard();
			TestBase.waitForMilliSeconds(20000);		
			
		} finally {
			proxy.getXml();
			Utils.createXMLFileForCharlesSessionFile();
			Utils.verifyPubadCal("Smoke", "IntegratedFeedCard");
		}
	}

	@Test(priority = 762, enabled = true)
	@Description("Validating Integrated Feed Card Video Ad response")
	public void Validate_Integrated_FeedCard_VideoAd_response() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Integrated Feed Card Video Ad response");
		logStep("Validating Integrated Feed Card Video Ad response");
		Utils.verifyIntegratedFeedCardAd_byCallResponse("Smoke", "IntegratedFeedCard");

	}

	/**
	 * This Script Validate Integrated Feed Card Video Ad and its parameters
	 * @throws Exception            
	 */

	@Test(priority = 763, enabled = true)
	@Description("Validating Integrated Feed Card Video Ad BackGround Asset Call")
	public void Validate_Integrated_FeedCard_VideoAd_bgAssetCall() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Integrated Feed Card Video Ad BG Asset Call");
		logStep("Validating Integrated Feed Card Video Ad BG Asset Call ");
		
		Utils.verifyAssetCallWithHostandPath("v.w-x.co", "/digital_video/",
				"The_Weather_Channel_-_Reach_Engine%2F8e82ac41-bb5b-49f1-b608-c00b6860375b", "-bg");
	}

	/**
	 * This Script Validate Integrated Feed Card Static Ad and its
	 * parameters Since fg asset call is same for static and video
	 * calls, once it displays in static mode it doesnt occur in
	 * video mode as its get cached. hence below case is commented
	 *                   
	 * @throws Exception 
	 */

	/*
	 * @Test(priority = 764, enabled = true)
	 * 
	 * @Description("Validating Integrated Feed Card Video Ad ForeGround Asset Call"
	 * ) public void Validate_Integrated_FeedCard_VideoAd_fgAssetCall() throws
	 * Exception {
	 * System.out.println("==============================================");
	 * System.out.
	 * println("****** Validating Integrated Feed Card Video Ad FG Asset Call");
	 * logStep("Validating Integrated Feed Card Video Ad FG Asset Call ");
	 * 
	 * Utils.verifyFGAd_byCallResponse("Smoke", "IntegratedFeedCard", "Video"); }
	 */

	@Test(priority = 765, enabled = true)
	@Description("Validating Integrated Feed Card Video Ad sz parameter")
	public void Validate_Integrated_FeedCard_VideoAd_Size() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Integrated Feed Card Video Ad sz parameter in charles");
		logStep("Validating Integrated Feed Card Video Ad sz parameter in charles ");

		Utils.verify_Ad_Size("Smoke", "IntegratedFeedCard");

	}

	@Test(priority = 800, enabled = true)
	@Description("Verify Entry Interstitial Ads of Hourly Tab")
	public void Verify_interstitial_ads_hourlyTab() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Entry Interstitial Ad Verification of Hourly Tab====================");

		System.out.println("****** Entry Interstitial Ad validation of Hourly Tab Started");
		logStep("Entry Interstitial Ad validation of Hourly Tab Started ");
		Ad.resetApp();
		TestBase.waitForMilliSeconds(2000);
		Functions.close_launchAppAndroid();
		//stScreen.select_Airlock_Branch("UnlimitedInterstitialAutomation02");
		stScreen.select_Airlock_UserGroup("unlimitedinterstitials");
		addrScreen.enter_requiredLocation("30124");
		TestBase.waitForMilliSeconds(20000);
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(5000);
		hmTab.clickonHomeTab();
		hmTab.clickonHomeTab();
		//Functions.checkForAppState();
		Functions.unlimitedInterstitial = true;
		Utils.assertinterStitialAd("Smoke", "Hourly", "hourlyTab", "Exit");

	}
	
	/**
	 * This method validates ltv custom parameter of Interstitial call
	 */
	@Test(priority = 801, enabled = true)
	@Description("Validating 'ltv' custom parameter of Interstitial call ")
	public void Validate_Interstitial_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of Interstitial call");
		logStep("Validating ltv custom parameter of Interstitial call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Interstitials", "ltv", "NotNull");

	}

	@Test(priority = 802, enabled = true)
	@Description("Verify Exit Interstitial Ads of Daily Tab")
	public void Verify_interstitial_ads_dailyTab() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Exit Interstitial Ad Verification of Daily Tab====================");

		System.out.println("****** Exit Interstitial Ad validation of Daily Tab Started");
		logStep("Exit Interstitial Ad validation of Daily Tab Started ");
		TestBase.waitForMilliSeconds(40000);
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(5000);
		hmTab.clickonHomeTab();
		hmTab.clickonHomeTab();
		//Functions.checkForAppState();
		Functions.unlimitedInterstitial = true;
		Utils.assertinterStitialAd("Smoke", "Daily(10day)", "dailyTab", "Exit");
	}

	@Test(priority = 803, enabled = true)
	@Description("Verify Exit Interstitial Ads of Radar Tab")
	public void Verify_interstitial_ads_radarTab() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Exit Interstitial Ad Verification of Radar Tab====================");

		System.out.println("****** Exit Interstitial Ad validation of Radar Tab Started");
		logStep("Exit Interstitial Ad validation of Radar Tab Started ");
		TestBase.waitForMilliSeconds(40000);
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(5000);
		hmTab.clickonHomeTab();
		hmTab.clickonHomeTab();
		//Functions.checkForAppState();
		Functions.unlimitedInterstitial = true;
		Utils.assertinterStitialAd("Smoke", "Map", "mapTab", "Exit");
	}

	@Test(priority = 804, enabled = true)
	@Description("Verify Exit Interstitial Ads of Video Tab")
	public void Verify_interstitial_ads_videoTab() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Exit Interstitial Ad Verification of Video Tab====================");

		System.out.println("****** Exit Interstitial Ad validation of Video Tab Started");
		logStep("Exit Interstitial Ad validation of Video Tab Started ");
		TestBase.waitForMilliSeconds(40000);
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(5000);
		hmTab.clickonHomeTab();
		hmTab.clickonHomeTab();
		//Functions.checkForAppState();
		Functions.unlimitedInterstitial = true;
		Utils.assertinterStitialAd("Smoke", "PreRollVideo", "videoTab", "Exit");
		// Functions.unlimitedInterstitial = false;
	}

	@Test(priority = 807, enabled = true)
	@Description("Verify Exit Interstitial Ads of Daily Card")
	public void Verify_interstitial_ads_dailyCard() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Exit Interstitial Ad Verification of Daily Card====================");

		System.out.println("****** Exit Interstitial Ad validation of Daily Card Started");
		logStep("Exit Interstitial Ad validation of Daily Card Started ");
		TestBase.waitForMilliSeconds(40000);
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(5000);
		//Functions.checkForAppState();
		Functions.unlimitedInterstitial = true;
		Utils.assertinterStitialAd("Smoke", "Daily(10day)", "daily", "Exit");
		
	}

	
	
	@Test(priority = 808, enabled = true)
	@Description("Verify Entry Interstitial Ads of Today's Details Card")
	public void Verify_interstitial_ads_todaysdetailsCard() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Exit Interstitial Ad Verification of Today's Details Card====================");

		System.out.println("****** Entry Interstitial Ad validation of Today's Details Card Started");
		logStep("Entry Interstitial Ad validation of Today's Details Card Started ");
		TestBase.waitForMilliSeconds(40000);
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(5000);
		//Functions.checkForAppState();
		Functions.unlimitedInterstitial = true;
		Utils.assertinterStitialAd("Smoke", "Today", "today", "Entry");
		Functions.unlimitedInterstitial = false;
	}
	 

	/*
	 * @Test(priority = 805, enabled = true)
	 * 
	 * @Description("Verify Exit Interstitial Ads of video feed card") public void
	 * Verify_interstitial_ads_video_feedcard() throws Exception {
	 * System.out.println("==============================================");
	 * System.out.
	 * println("=========================== Exit Interstitial Ad Verification of video feed card===================="
	 * );
	 * 
	 * System.out.
	 * println("****** Exit Interstitial Ad validation of video feed card Started");
	 * 
	 * //Functions.Setappinto_TestMode("UnlimitedInterstitial"); try {
	 * Utils.assertinterStitialAd("Smoke", "PreRollVideo", "video");
	 * }catch(Exception e) { System.out.
	 * println("There is an exception in validating Interstitial Ads of video feed card"
	 * );
	 * logStep("There is an exception in validating Interstitial Ads of video feed card"
	 * ); }finally { Functions.unlimitedInterstitial = false;
	 * Functions.Setappinto_TestMode("UnSelect");
	 * 
	 * } }
	 */

	/**
	 * This Script Enable preconfiguration for spotlight cards i.e. Flu, Allergy, Week Ahead, Weekend
	 * @throws Exception    
	 */
	@Test(priority = 901, enabled = true)
	@Description("Enabling Preconfiguration for Watson Moment and Planning Moment Cards")
	public void enable_PreConfiguration_for_WatsonAndPlanningMomentCards() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Enable Preconfiguration for Watson Moment and Planning Moment Cards");
		logStep("Enable Preconfiguration for Watson Moment and Planning Moment Cards ");
		
		proxy.quitCharlesProxy();
		//this.configFile = this.changeVt1ContentMode(BN_SEVERE1_CONFIG_FILE_PATH, "severe1");
		this.configFile = this.changeVt1ContentModeWhenNoTunnelBearAndroid(BN_SEVERE1_CONFIG_FILE_PATH, "severe1", "usa", "US", "WA");
		proxy = new CharlesProxy("localhost", 8111, BN_SEVERE1_CONFIG_FILE_PATH);
		proxy.startCharlesProxyWithUI();
		proxy.enableRewriting();
		proxy.startRecording();
		proxy.disableMapLocal();
		Ad.resetApp();
		TestBase.waitForMilliSeconds(2000);
		Functions.close_launchAppAndroid();
		//Functions.checkForAppState();
		//stScreen.clear_Airlock_Cache();
				
		//hmTab.clickonHomeTab();
		stScreen.select_Airlock_UserGroup("WMCards");
		stScreen.select_Airlock_Branch("WMCards");
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		hmTab.clickonHomeTab();
		hmTab.clickonHomeTab();
		hrTab.navigateToHourlyTabAndHandleInterstitialAd();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		proxy.clearCharlesSession();
		//addrScreen.enternewAddress(false, "New York City, New York");
		addrScreen.enter_requiredLocation("10003");
		TestBase.waitForMilliSeconds(20000);
		try {
			Utils.navigateToAllCardsAndroid(false, false);
			
		} catch (Exception e) {
			System.out.println("There is an exception while navigting to all the feed cards.");
			logStep("There is an exception while navigting to all the feed cards.");
		} finally {
			proxy.getXml();
			Utils.createXMLFileForCharlesSessionFile();
		}
	}

	@Test(priority = 902, enabled = true)
	@Description("Verify Week Ahead ad call iu")
	public void Verify_Week_Ahead_AdCall() throws Exception {
		System.out.println("==============================================");
		
		System.out.println("****** Week Ahead Adcall verification test case Started");
		logStep("****** Week Ahead Adcall verification test case Started");

		Utils.verifyPubadCal("Smoke", "WeekAhead");
	}

	/**
	 * This method validates pos custom parameter of Week Ahead call
	 */
	@Test(priority = 903, enabled = true)
	@Description("Validating 'pos' custom parameter of Week Ahead call ")
	public void Validate_Week_Ahead_pos_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating pos custom parameter of Week Ahead call");
		logStep("Validating pos custom parameter of Week Ahead call ");

		Utils.validate_custom_param_val_of_gampad("Smoke", "WeekAhead", "pos", "app_sl");

	}

	@Test(priority = 904, enabled = true)
	@Description("Validating Week Ahead call Ad sz parameter")
	public void Validate_Week_Ahead_Ad_Size() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Week Ahead call Ad sz parameter in charles");
		logStep("Validating Week Ahead call Ad sz parameter in charles ");

		Utils.verify_Ad_Size("Smoke", "WeekAhead");

	}

	@Test(priority = 905, enabled = true)
	@Description("Validating Week Ahead call Response")
	public void Validate_Week_Ahead_Call_Response() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Week Ahead call response in charles");
		logStep("Validating Week Ahead call response in charles ");

		Utils.Verify_Gampad_Call_ByResponseText("Smoke", "WeekAhead");

	}
	
	/**
	 * This method validates ltv custom parameter of WeekAhead call
	 */
	@Test(priority = 906, enabled = true)
	@Description("Validating 'ltv' custom parameter of WeekAhead call ")
	public void Validate_Week_Ahead_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of WeekAhead call");
		logStep("Validating ltv custom parameter of WeekAhead call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "WeekAhead", "ltv", "NotNull");

	}

	@Test(priority = 911, enabled = true)
	@Description("Verify Weekend ad call iu")
	public void Verify_Weekend_AdCall() throws Exception {
		System.out.println("==============================================");
	
		System.out.println("****** Weekend Adcall verification test case Started");
		logStep("****** Weekend Adcall verification test case Started");

		Utils.verifyPubadCal("Smoke", "Weekend");
	}

	/**
	 * This method validates pos custom parameter of Weekend call
	 */
	@Test(priority = 912, enabled = true)
	@Description("Validating 'pos' custom parameter of Weekend call ")
	public void Validate_Weekend_pos_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating pos custom parameter of Weekend call");
		logStep("Validating pos custom parameter of Weekend call ");

		Utils.validate_custom_param_val_of_gampad("Smoke", "Weekend", "pos", "app_sl");

	}

	@Test(priority = 913, enabled = true)
	@Description("Validating Weekend call Ad sz parameter")
	public void Validate_Weekend_Ad_Size() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Weekend call Ad sz parameter in charles");
		logStep("Validating Weekend call Ad sz parameter in charles ");

		Utils.verify_Ad_Size("Smoke", "Weekend");

	}

	@Test(priority = 914, enabled = true)
	@Description("Validating Weekend call Response")
	public void Validate_Weekend_Call_Response() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Weekend call response in charles");
		logStep("Validating Weekend call response in charles ");

		Utils.Verify_Gampad_Call_ByResponseText("Smoke", "Weekend");

	}
	
	/**
	 * This method validates ltv custom parameter of Weekend call
	 */
	@Test(priority = 915, enabled = true)
	@Description("Validating 'ltv' custom parameter of Weekend call ")
	public void Validate_Weekend_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of Weekend call");
		logStep("Validating ltv custom parameter of Weekend call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Weekend", "ltv", "NotNull");

	}

	@Test(priority = 921, enabled = true)
	@Description("Verify WM Flu ad call iu")
	public void Verify_WMFlu_AdCall() throws Exception {
		System.out.println("==============================================");
		
		System.out.println("****** WM Flu Adcall verification test case Started");
		logStep("****** WM Flu Adcall verification test case Started");

		Utils.verifyPubadCal("Smoke", "WMFlu");
	}

	/**
	 * This method validates pos custom parameter of WM Flu call
	 */
	@Test(priority = 922, enabled = true)
	@Description("Validating 'pos' custom parameter of WM Flu call ")
	public void Validate_WMFlu_pos_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating pos custom parameter of WM Flu call");
		logStep("Validating pos custom parameter of WM Flu call ");

		Utils.validate_custom_param_val_of_gampad("Smoke", "WMFlu", "pos", "app_sl");

	}

	@Test(priority = 923, enabled = true)
	@Description("Validating WM Flu call Ad sz parameter")
	public void Validate_WMFlu_Ad_Size() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating WM Flu call Ad sz parameter in charles");
		logStep("Validating WM Flu call Ad sz parameter in charles ");

		Utils.verify_Ad_Size("Smoke", "WMFlu");

	}

	@Test(priority = 924, enabled = true)
	@Description("Validating WM Flu call Response")
	public void Validate_WMFlu_Call_Response() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating WM Flu call response in charles");
		logStep("Validating WM Flu call response in charles ");

		Utils.Verify_Gampad_Call_ByResponseText("Smoke", "WMFlu");

	}
	
	/**
	 * This method validates ltv custom parameter of WM Flu call
	 */
	@Test(priority = 925, enabled = true)
	@Description("Validating 'ltv' custom parameter of WM Flu call ")
	public void Validate_WMFlu_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of WM Flu call");
		logStep("Validating ltv custom parameter of WM Flu call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "WMFlu", "ltv", "NotNull");

	}

	@Test(priority = 931, enabled = true)
	@Description("Verify WM Allergy ad call iu")
	public void Verify_WMAllergy_AdCall() throws Exception {
		System.out.println("==============================================");
	
		System.out.println("****** WM Allergy Adcall verification test case Started");
		logStep("****** WM Allergy Adcall verification test case Started");

		Utils.verifyPubadCal("Smoke", "WMAllergy");
	}

	/**
	 * This method validates pos custom parameter of WM Allergy call
	 */
	@Test(priority = 932, enabled = true)
	@Description("Validating 'pos' custom parameter of WM Allergy call ")
	public void Validate_WMAllergy_pos_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating pos custom parameter of WM Allergy call");
		logStep("Validating pos custom parameter of WM Allergy call ");

		Utils.validate_custom_param_val_of_gampad("Smoke", "WMAllergy", "pos", "app_sl");

	}

	@Test(priority = 933, enabled = true)
	@Description("Validating WM Allergy call Ad sz parameter")
	public void Validate_WMAllergy_Ad_Size() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating WM Allergy call Ad sz parameter in charles");
		logStep("Validating WM Allergy call Ad sz parameter in charles ");

		Utils.verify_Ad_Size("Smoke", "WMAllergy");

	}

	@Test(priority = 934, enabled = true)
	@Description("Validating WM Allergy call Response")
	public void Validate_WMAllergy_Call_Response() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating WM Allergy call response in charles");
		logStep("Validating WM Allergy call response in charles ");

		Utils.Verify_Gampad_Call_ByResponseText("Smoke", "WMAllergy");

	}
	
	/**
	 * This method validates ltv custom parameter of WM Allergy call
	 */
	@Test(priority = 935, enabled = true)
	@Description("Validating 'ltv' custom parameter of WM Allergy call ")
	public void Validate_WMAllergy_ltv_Custom_param() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating ltv custom parameter of WM Allergy call");
		logStep("Validating ltv custom parameter of WM Allergy call ");
		Utils.validate_custom_param_val_of_gampad("Smoke", "WMAllergy", "ltv", "NotNull");

	}
	
	@Test(priority = 950, enabled = true)
	@Description("Validating Safety And Preparedness call Response")
	public void Validate_SafetyAndPreparednessCard_Call_Response() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating SafetyAndPreparednessCard call response in charles");
		logStep("Validating SafetyAndPreparednessCard call response in charles ");

		Utils.Verify_Gampad_Call_ByResponseText("Smoke", "SafetyAndPreparedness(Test)");

	}
	
	@Test(priority = 951, enabled = true)
	@Description("Validating Breaking News Severe1 call Response")
	public void Validate_BreakingNews_Severe1_Call_Response() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating Breaking News Severe1 call response in charles");
		logStep("Validating Breaking News Severe1 call response in charles ");

		Utils.Verify_Gampad_Call_ByResponseText("Smoke", "BreakingNews(Test)");

	}
	
	/**
	 * This method validate the 'mr' custom parameter of Homescreen sticky ad when kill and launch the app
	 * @throws Exception
	 */
	@Test(priority = 975, enabled = true)
	@Description("Verify 'mr' custom parameter of Homescreen sticky ad when kill and launch the app")
	public void Verify_HomeScreen_Sticky_Ad_mr_Custom_Parameter_When_Kill_And_Launch() throws Exception {
		System.out.println("==============================================");

		System.out.println("****** Verifying mr custom parameter of Homescreen sticky ad call when kill and launch the app");
		logStep("****** Verifying mr custom parameter of Homescreen sticky ad call when kill and launch the app");
		//Functions.close_launchAppAndroid();
		Functions.close_launchAppAndroid();
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(2000);
		// navigate to Radar tab
		Functions.archive_folder("Charles");
		
		if (Utils.isNextGenIMAdDisplayedAndroid()) {
			System.out.println("****** Since NextGen IM Ad displayed on homescreen, skipping sticky ad 'mr' parameter validation");
			logStep("****** Since NextGen IM Ad displayed on homescreen, skipping sticky ad 'mr' parameter validation");
		} else {
			proxy.getXml();
			Utils.createXMLFileForCharlesSessionFile();
			Utils.validate_custom_param_val_of_gampad("Smoke", "PulltorefreshTestMode", "mr", "0");
			proxy.clearCharlesSession();
			System.out.println("****** 30 seconds hard wait to refresh the ad call");
			logStep("****** 30 seconds hard wait to refresh the ad call");
			TestBase.waitForMilliSeconds(30000);
			Functions.archive_folder("Charles");
			proxy.getXml();
			Utils.createXMLFileForCharlesSessionFile();
			Utils.validate_custom_param_val_of_gampad("Smoke", "PulltorefreshTestMode", "mr", "1");
						
		}
		
	}
	
	
	
	/**
	 * This Script Enable preconfiguration for ThirdParty Tracking Pixe Into The AQ Details Page
	 * @throws Exception    
	 */
/*	@Test(priority = 1001, enabled = true)
	@Description("Enabling Preconfiguration for ThirdParty Tracking Pixel Into The AQ Details Page")
	public void enable_PreConfiguration_for_ThirdParty_Tracking_Pixel_Into_AQ_Details_Page() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Enable Preconfiguration for ThirdParty Tracking Pixel Into The AQ Details Page");
		logStep("Enable Preconfiguration for ThirdParty Tracking Pixel Into The AQ Details Page ");
		hmTab.clickonHomeTab();
		stScreen.select_Airlock_UserGroup("IOSFLAG-8105");
		Functions.close_launchAppAndroid();
		//Functions.checkForAppState();
		proxy.clearCharlesSession();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		addrScreen.enternewAddress(false, "99833", "Petersburg, Alaska");
		TestBase.waitForMilliSeconds(20000);
		try {
			Utils.navigateTofeedCard("weather.feed0");
			TestBase.waitForMilliSeconds(20000);
			fOneCardScreen.navigateToFeedOneCardContentPage();
			aqCardContentScreen.waitForAirQualityCardContentPage();
			navigateBackToFeedCard();		
		} catch (Exception e) {
			System.out.println("There is an exception while navigting to feed1 card");
			logStep("There is an exception while navigting to feed1 card");
		} finally {
			proxy.getXml();
			Utils.createXMLFileForCharlesSessionFile();
		}
	}
	
	@Test(priority = 1002, enabled = true)
	@Description("Verify Third Party Pixel Call When Navigated to AQ Detaills Page From Feed1 Card")
	public void Verify_ThirdParty_Pixel_Call_When_Navigated_To_AQDetails_Page_From_Feed1_Card() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Third Party Pixel Call ====================");

		System.out.println("****** Third Party Pixel Call validation Started");
		logStep("****** Third Party Pixel Call validation Started");

		aqCardContentScreen.verifyPixel_Call_When_Navigated_To_AQDetails_Page_From_Feed1_Card("Smoke", "Air Quality(Content)");

	}*/
	
	/**
	 * This Script Enable preconfiguration for PreRoll Video Beacon
	 * @throws Exception
	 */
	/*@Test(priority = 1010, enabled = true)
	@Description("Enabling Preconfiguration for PreRoll Video Beacon")
	public void enable_PreConfiguration_for_PreRoll_Video_Beacon() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Enable Preconfiguration for PreRoll Video Beacon");
		logStep("Enable Preconfiguration for PreRoll Video Beacon");
		hmTab.clickonHomeTab();
		stScreen.select_Airlock_UserGroup("AdsTestAdUnitOnly");
		Functions.close_launchAppAndroid();
		//Functions.checkForAppState();
		
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		//addrScreen.enternewAddress(false, "12758", "Livingston Manor, New York");
		addrScreen.enter_requiredLocation("12758");
		TestBase.waitForMilliSeconds(20000);
		proxy.clearCharlesSession();
		try {
			// navigate to Video tab
			vTab.navigateToVideoTab(true, proxy);
			hmTab.clickonHomeTab();
			proxy.clearCharlesSession();
			vTab.navigateToVideoTab(true, proxy);
			TestBase.waitForMilliSeconds(40000);
		} catch (Exception e) {
			System.out.println("There is an exception while navigting to video tab");
			logStep("There is an exception while navigting to video tab");
		} finally {
			proxy.getXml();
			Utils.createXMLFileForCharlesSessionFile();
		}
	}*/
	
	/*@Test(priority = 1011, enabled = true)
	@Description("Verify PreRoll Video Beacon")
	public void Verify_PreRoll_Video_Beacon() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== PreRoll Video Beacon ====================");

		System.out.println("****** PreRoll Video Beacon validation Started");
		logStep("****** PreRoll Video Beacon validation Started");

		vTab.validatePreRollVideoBeacon("Smoke", "PreRollVideo", "type", "complete");

	}*/
	
/*	@Test(priority = 1020, enabled = true)
	@Description("Enabling Preconfiguration of Map Local to get Severe Insight Card")
	public void enable_PreConfiguration_of_mapLocal_For_Severe_Insight_Card() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Enable Preconfiguration of Map Local for Severe Insight Card");
		logStep("Enable Preconfiguration of Map Local for Severe Insight Card");
		proxy.quitCharlesProxy();
		String jsonPath = "src/test/resources/SevereInsightAtlantaAndroid.json";
		//mapLocalForSevereInsight(SEVERE_MAP_LOCAL_CONFIG_FILE_PATH, jsonPath);
		mapLocalForSevereInsightWhenNoTunnelBearAndroid(SEVERE_MAP_LOCAL_CONFIG_FILE_PATH, jsonPath, "usa", "US", "WA");
		proxy = new CharlesProxy("localhost", 8111, SEVERE_MAP_LOCAL_CONFIG_FILE_PATH);
		proxy.startCharlesProxyWithUI();
		proxy.enableMapLocal();
		proxy.enableRewriting();
		Functions.close_launchAppAndroid();
//		stScreen.select_Airlock_Branch("Clear");
//		stScreen.select_Airlock_UserGroup("IOSFLAG 7782SevereInsight");
		addrScreen.enter_requiredLocation("30303");
		Functions.close_launchAppAndroid();
		proxy.clearCharlesSession();
		//proxy.enableMapLocal();
		//Functions.close_launchAppAndroid();
		//addrScreen.enternewAddress(false, "Atlanta, Georgia");
		//addrScreen.enternewAddress(false, "New York City, New York");
				
		try {
			Utils.navigateTofeedCard("Severe Outlook", false, false);
					
		} catch (Exception e) {
			System.out.println("There is an exception while navigting to Severe Outlook card");
			logStep("There is an exception while navigting to Severe Outlook card");
		}
		
	}*/
	
/*	@Test(priority = 1021, enabled = true)
	@Description("Verify Severe Insight Text On Hourly Nav Tab")
	public void Verify_Severe_Insight_Text_On_Hourly_Nav_Tab() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Severe Insight Text On Hourly Nav Tab ====================");

		System.out.println("****** Severe Insight Text On Hourly Nav Tab validation Started");
		logStep("****** Severe Insight Text On Hourly Nav Tab validation Started");
		hmTab.clickonHomeTab();
		hrTab.navigateToHourlyTab();
		hrTab.assertSevereInsightTextOnHourlyNavTab();

	}*/
	
/*	@Test(priority = 1022, enabled = true)
	@Description("Verify Severe Insight Text On Daily Nav Tab")
	public void Verify_Severe_Insight_Text_On_Daily_Nav_Tab() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Severe Insight Text On Daily Nav Tab ====================");

		System.out.println("****** Severe Insight Text On Daily Nav Tab validation Started");
		logStep("****** Severe Insight Text On Daily Nav Tab validation Started");
		hmTab.clickonHomeTab();
		dTab.navigateToDailyTab();
		dTab.assertSevereInsightTextOnDailyNavTab();

	}*/
	
	@Test(priority = 1030, enabled = true)
	@Description("Enabling Preconfiguration of Map Local for Interstitials Verification during Severe Tornado Warning")
	public void enable_PreConfiguration_of_mapLocal_For_Interstitials_verification_during_Tornado_Warning() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Enable Preconfiguration of Map Local for Interstitials Verification during severe Tornado Warning");
		logStep("Enable Preconfiguration of Map Local for Interstitials Verification during severe Tornado Warning");
		proxy.disableMapLocal();
		proxy.quitCharlesProxy();
		String jsonPath = "src/test/resources/TornadoNYAndroid.json";
		//mapLocalForSevereInsight(TORNADO_MAP_LOCAL_CONFIG_FILE_PATH, jsonPath);
		mapLocalForSevereInsightWhenNoTunnelBearAndroid(TORNADO_MAP_LOCAL_CONFIG_FILE_PATH, jsonPath, "usa", "US", "WA");
		proxy = new CharlesProxy("localhost", 8111, TORNADO_MAP_LOCAL_CONFIG_FILE_PATH);
		proxy.startCharlesProxyWithUI();
		proxy.enableMapLocal();
		proxy.enableRewriting();
		Ad.resetApp();
		Functions.close_launchAppAndroid();
//		stScreen.GPS_Spoof_On("New York City", "New York");
		addrScreen.enter_requiredLocation("10003");
		//addrScreen.enternewAddress(false, "New York City, New York");
		//proxy.enableMapLocal();
//		stScreen.select_Airlock_UserGroup("iosflag8541");
		//Functions.close_launchAppAndroid();
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(10000);
		Functions.archive_folder("Charles");
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();		
	}
	
	@Test(priority = 1031, enabled = true)
	@Description("Verify Interstitial ad call iu during Severe Tornado Warning")
	public void Verify_Interstitial_AdCall_during_Severe_Tornado_Warning() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================Interstitial Adcall iu====================");
		
		System.out.println("****** Interstitial Adcall verification Started during Severe Tornado Warning");
		logStep("****** Interstitial Adcall verification Started during Severe Tornado Warning");
		Utils.verifyPubadCal("Smoke", "Interstitials", false);
	}
	
	@Test(priority = 1035, enabled = true)
	@Description("Enabling Preconfiguration of Map Local for Interstitials Verification during Heat Advisory")
	public void enable_PreConfiguration_of_mapLocal_For_Interstitials_verification_during_Heat_Advisory() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Enable Preconfiguration of Map Local for Interstitials Verification during Heat Advisory");
		logStep("Enable Preconfiguration of Map Local for Interstitials Verification during Heat Advisory");
		proxy.disableMapLocal();
		proxy.quitCharlesProxy();
		String jsonPath = "src/test/resources/HeatAdvisoryYakimaWashingtonAndroid.json";
		//mapLocalForSevereInsight(HEATADVISORY_MAP_LOCAL_CONFIG_FILE_PATH, jsonPath);
		mapLocalForSevereInsightWhenNoTunnelBearAndroid(HEATADVISORY_MAP_LOCAL_CONFIG_FILE_PATH, jsonPath, "usa", "US", "WA");
		proxy = new CharlesProxy("localhost", 8111, HEATADVISORY_MAP_LOCAL_CONFIG_FILE_PATH);
		proxy.startCharlesProxyWithUI();
		proxy.enableMapLocal();
		proxy.enableRewriting();
		Functions.close_launchAppAndroid();
		//stScreen.GPS_Spoof_On("Yakima", "Washington");
		
		addrScreen.enter_requiredLocation("98902");
		//proxy.enableMapLocal();
		//Functions.close_launchAppAndroid();
		//stScreen.select_Airlock_UserGroup("iosflag8541");
		Functions.close_launchAppAndroid();
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(10000);
		Functions.archive_folder("Charles");
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();	
		//stScreen.GPS_Spoof_Off();
	}
	
	@Test(priority = 1036, enabled = true)
	@Description("Verify Interstitial ad call iu during Heat Advisory")
	public void Verify_Interstitial_AdCall_during_Heat_Advisory() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================Interstitial Adcall iu====================");
		
		System.out.println("****** Interstitial Adcall verification Started during Heat Advisory");
		logStep("****** Interstitial Adcall verification Started during Heat Advisory");
		Utils.verifyPubadCal("Smoke", "Interstitials", true);
		logStep("****** Interstitial Adcall verification Started during Heat Advisory");
	}
	
	/**
	 * LogIn to the App
	 * @throws Exception
	 */
	@Test(priority = 1075, enabled = true)
	@Description("Verify Ads With UPSX LogIn")
	public void verifyAdsWithUPSXLogIn() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** UPSX LogIn test Started");
		logStep("****** UPSX LogIn test Started");
		proxy.quitCharlesProxy();
		proxy = new CharlesProxy("localhost", 8111, CONFIG_FILE_PATH);
		proxy.startCharlesProxyWithUI();
		proxy.enableRewriting();
		proxy.startRecording();
		Ad.resetApp();
		Functions.close_launchAppAndroid();
		Functions.close_launchAppAndroid();
		hmTab.clickonHomeTab();
		hmTab.clickonHomeTab();
		stScreen.UPSX_login("twcremoveads@gmail.com", "Monocept@123");
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(10000);
		Functions.archive_folder("Charles");
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
	}
	
	/**
	 * This method verifies Lotame call 
	 * @throws Exception
	 */
	@Test(priority = 1076, enabled = true)
	@Description("Lotame Call verification")
	public void Verify_Lotame_Call_When_UPSX_LogIn() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** bcp.crwdcntrl.net Call test case Started");
		logStep("****** bcp.crwdcntrl.net Call test case Started");
		Utils.verifyAPICal("Smoke", "Lotame", true);
	}

	/**
	 * This method verifies FACTUAL call
	 * @throws Exception
	 */
	@Test(priority = 1077, enabled = true)
	@Description("Factual Call verification")
	public void Verify_LocationWFXTriggers_Call_When_UPSX_LogIn() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** location.wfxtriggers.com Call test case Started");
		logStep("location.wfxtriggers.com Call test case Started");
		Utils.verifyAPICal("Smoke", "LocationWFX", false);
	}
	
	/**
	 * This method verifies WFXTriggers call
	 * @throws Exception
	 */
	@Test(priority = 1078, enabled = true)
	@Description("WFXTrigger Call verification")
	public void Verify_WFXTriggers_Call_When_UPSX_LogIn() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** triggers.wfxtriggers.com Call test case Started");
		logStep("****** triggers.wfxtriggers.com Call test case Started");
		Utils.verifyAPICal("Smoke", "WFXTrigger", true);
	}
	
	/**
	 * This method verifies Amazon call
	 * @throws Exception
	 */
	@Test(priority = 1079, enabled = true)
	@Description("Amazon aax call verification")
	public void Verify_Amazon_Call_When_UPSX_LogIn() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** amazon-adsystem.com Call test case Started");
		logStep("****** amazon-adsystem.com Call test case Started");
		Utils.verify_Amazon_aax_call("Smoke", "Amazon", true);
	}
	
	/**
	 * This method verifies Criteo Initialization API call
	 * @throws Exception
	 */
	@Test(priority = 1080, enabled = true)
	@Description("Verify Criteo SDK inapp v2 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_When_UPSX_LogIn() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Criteo SDK inapp/v2 call ====================");
		System.out.println("****** Criteo SDK inapp/v2 call validation Started");
		logStep("****** Criteo SDK inapp/v2 call validation Started");
		Utils.verifyCriteo_inapp_v2_Call("Smoke", "Criteo", true);
	}
	
	/**
	 * This method verifies Criteo Bidder API call
	 * @throws Exception
	 */
	@Test(priority = 1081, enabled = true)
	@Description("Verify Criteo SDK config app call")
	public void Verify_Criteo_SDK_config_app_Call_When_UPSX_LogIn() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Criteo SDK config/app call ====================");
		System.out.println("****** Criteo SDK config/app call validation Started");
		logStep("****** Criteo SDK config/app call validation Started");
		Utils.verifyCriteo_config_app_Call("Smoke", "Criteo", true);
	}

	/**
	 * This method verifies NextGen IM gampad call
	 * @throws Exception
	 */
	@Test(priority = 1082, enabled = true)
	@Description("Verify Gampad Ad Call")
	public void Verify_Gampad_call_When_UPSX_LogIn() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Gampad Call verification test case Started");
		logStep("****** Gampad Call verification test case Started");
		Utils.verify_Gampad_adcall("Smoke", "Gampad", true);
	}
	
	/**
	 * LogOut from the App
	 * @throws Exception
	 */
	@Test(priority = 2000, enabled = true)
	@Description("Verify Ads With UPSX LogOut")
	public void verifyAdsWithUPSXLogOut() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** UPSX LogOut test Started");
		logStep("****** UPSX LogOut test Started");
		Functions.close_launchAppAndroid();
		stScreen.UPSX_logout();;
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		TestBase.waitForMilliSeconds(10000);
		Functions.archive_folder("Charles");
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
	}
	
	/**
	 * This method verifies Lotame call 
	 * @throws Exception
	 */
	@Test(priority = 2001, enabled = true)
	@Description("Lotame Call verification")
	public void Verify_Lotame_Call_When_UPSX_LogOut() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** bcp.crwdcntrl.net Call test case Started");
		logStep("****** bcp.crwdcntrl.net Call test case Started");
		Utils.verifyAPICal("Smoke", "Lotame", true);
	}

	/**
	 * This method verifies FACTUAL call
	 * @throws Exception
	 */
	@Test(priority = 2002, enabled = true)
	@Description("Factual Call verification")
	public void Verify_LocationWFXTriggers_Call_When_UPSX_LogOut() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** location.wfxtriggers.com Call test case Started");
		logStep("location.wfxtriggers.com Call test case Started");
		Utils.verifyAPICal("Smoke", "LocationWFX", false);
	}
	
	/**
	 * This method verifies WFXTriggers call
	 * @throws Exception
	 */
	@Test(priority = 2003, enabled = true)
	@Description("WFXTrigger Call verification")
	public void Verify_WFXTriggers_Call_When_UPSX_LogOut() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** triggers.wfxtriggers.com Call test case Started");
		logStep("****** triggers.wfxtriggers.com Call test case Started");
		Utils.verifyAPICal("Smoke", "WFXTrigger", true);
	}
	
	/**
	 * This method verifies Amazon call
	 * @throws Exception
	 */
	@Test(priority = 2004, enabled = true)
	@Description("Amazon aax call verification")
	public void Verify_Amazon_Call_When_UPSX_LogOut() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** amazon-adsystem.com Call test case Started");
		logStep("****** amazon-adsystem.com Call test case Started");
		Utils.verify_Amazon_aax_call("Smoke", "Amazon", true);
	}
	
	/**
	 * This method verifies Criteo Initialization API call
	 * @throws Exception
	 */
	@Test(priority = 2005, enabled = true)
	@Description("Verify Criteo SDK inapp v2 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_When_UPSX_LogOut() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Criteo SDK inapp/v2 call ====================");
		System.out.println("****** Criteo SDK inapp/v2 call validation Started");
		logStep("****** Criteo SDK inapp/v2 call validation Started");
		Utils.verifyCriteo_inapp_v2_Call("Smoke", "Criteo", true);
	}
	
	/**
	 * This method verifies Criteo Bidder API call
	 * @throws Exception
	 */
	@Test(priority = 2006, enabled = true)
	@Description("Verify Criteo SDK config app call")
	public void Verify_Criteo_SDK_config_app_Call_When_UPSX_LogOut() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Criteo SDK config/app call ====================");
		System.out.println("****** Criteo SDK config/app call validation Started");
		logStep("****** Criteo SDK config/app call validation Started");
		Utils.verifyCriteo_config_app_Call("Smoke", "Criteo", true);
	}

	/**
	 * This method verifies NextGen IM gampad call
	 * @throws Exception
	 */
	@Test(priority = 2007, enabled = true)
	@Description("Verify Gampad Ad Call")
	public void Verify_Gampad_call_When_UPSX_LogOut() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Gampad Call verification test case Started");
		logStep("****** Gampad Call verification test case Started");
		Utils.verify_Gampad_adcall("Smoke", "Gampad", true);
	}
	
	/**
	 * This method enables monthly premium subscription
	 * @throws Exception
	 */
	@Test(priority = 3000, enabled = true)
	@Description("Verify AD Free Plan")
	public void enableAdfreeplan() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Enable Ad free plan test Started");
		logStep("****** Enable Ad free plan test Started");
		Utils.getCurrentMacIPAddressAndSetandroidProxy(false, true);
	//Ad.launchApp();
		Ad.quit();
	//	Ad.resetApp();
		Functions.launchtheAndroidApp();
		Functions.close_launchAppAndroid();
		loginScreen = new AndroidLogInScreen(Ad);
	//	Utils.PremiumSubscriptionforMonthly();
//		TestBase.waitForMilliSeconds(5000);
//		Ad.launchApp();
		try {
			//stScreen.UPSX_login("twcremoveads@gmail.com", "Monocept@123");
			loginScreen.enableMonthlyAdFreeSubscription();
			
		//loginScreen.enableMonthlyPremiumSubscription("twcremoveads@gmail.com", "Monocept@123");
//			loginScreen.enableYearlyPremiumSubscription("jmktwc4@gmail.com", "300Interstate");
		} finally {
			subscriptionFqCapStrtTime = System.nanoTime();
			start = System.nanoTime();
			Functions.close_launchAppAndroid();
			attachScreen();
								
			Utils.getCurrentMacIPAddressAndSetandroidProxy(true, true);
			Ad.quit();
			Functions.launchtheAndroidApp();
			//	Ad.resetApp();
			Functions.launchtheAndroidApp();
			Functions.close_launchAppAndroid();
		//	Ad.launchApp();
			Ad.terminateApp("com.weather.Weather");
			System.out.println("App Closed SuccessFully");
			logStep("App Closed SuccessFully");
			Ad.launchApp();
			System.out.println("App Launched SuccessFully");
			logStep("App Launched SuccessFully");
			proxy.clearCharlesSession();
			Functions.close_launchAppAndroid();
			Functions.put_Background_launchAndroid(10);
			Functions.archive_folder("Charles");
			proxy.getXml();
			Utils.createXMLFileForCharlesSessionFile();
		}
		
	}
	
	
	/**
	 * This method verifies Lotame call 
	 * @throws Exception
	 */
	@Test(priority = 3002, enabled = true)
	@Description("Lotame Call verification")
	public void Verify_Lotame_Call_When_AdFree_Subscription_Monthly_Enabled() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** bcp.crwdcntrl.net Call test case Started");
		logStep("****** bcp.crwdcntrl.net Call test case Started");
		Utils.verifyAPICal("Smoke", "Lotame", false);
	}

	/**
	 * This method verifies FACTUAL call
	 * @throws Exception
	 */
	@Test(priority = 3004, enabled = true)
	@Description("Factual Call verification")
	public void Verify_LocationWFXTriggers_Call_When_AdFree_Subscription_Monthly_Enabled() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** location.wfxtriggers.com Call test case Started");
		logStep("location.wfxtriggers.com Call test case Started");
		Utils.verifyAPICal("Smoke", "LocationWFX", false);
	}
	
	/**
	 * This method verifies WFXTriggers call
	 * @throws Exception
	 */
	@Test(priority = 3006, enabled = true)
	@Description("WFXTrigger Call verification")
	public void Verify_WFXTriggers_Call_When_AdFree_Subscription_Monthly_Enabled() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** triggers.wfxtriggers.com Call test case Started");
		logStep("****** triggers.wfxtriggers.com Call test case Started");
		Utils.verifyAPICal("Smoke", "WFXTrigger", false);
	}
	
	/**
	 * This method verifies Amazon call
	 * @throws Exception
	 */
	@Test(priority = 3008, enabled = true)
	@Description("Amazon aax call verification")
	public void Verify_Amazon_Call_When_AdFree_Subscription_Monthly_Enabled() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** amazon-adsystem.com Call test case Started");
		logStep("****** amazon-adsystem.com Call test case Started");
		Utils.verify_Amazon_aax_call("Smoke", "Amazon", false);
	}
	
	/**
	 * This method verifies Criteo Initialization API call
	 * @throws Exception
	 */
	@Test(priority = 3010, enabled = true)
	@Description("Verify Criteo SDK inapp v2 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_When_AdFree_Subscription_Monthly_Enabled() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Criteo SDK inapp/v2 call ====================");
		System.out.println("****** Criteo SDK inapp/v2 call validation Started");
		logStep("****** Criteo SDK inapp/v2 call validation Started");
		Utils.verifyCriteo_inapp_v2_Call("Smoke", "Criteo", false);
	}
	
	/**
	 * This method verifies Criteo Bidder API call
	 * @throws Exception
	 */
	@Test(priority = 3012, enabled = true)
	@Description("Verify Criteo SDK config app call")
	public void Verify_Criteo_SDK_config_app_Call_When_AdFree_Subscription_Monthly_Enabled() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Criteo SDK config/app call ====================");
		System.out.println("****** Criteo SDK config/app call validation Started");
		logStep("****** Criteo SDK config/app call validation Started");
		Utils.verifyCriteo_config_app_Call("Smoke", "Criteo", false);
	}

	/**
	 * This method verifies NextGen IM gampad call
	 * @throws Exception
	 */
	@Test(priority = 3014, enabled = true)
	@Description("Verify Gampad Ad Call")
	public void Verify_Gampad_call_When_AdFree_Subscription_Monthly_Enabled() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Gampad Call verification test case Started");
		logStep("****** Gampad Call verification test case Started");
		Utils.verify_Gampad_adcall("Smoke", "Gampad", false);
	}
	
	
	/**
	 * This method verifies whether premium subscription expired or not
	 * @throws Exception
	 */
	@Test(priority = 3016, enabled = true)
	@Description("Verify Premium  Ad Free Monthly Expiry")
	public void checkPremiumSubsriptionAdfreeExpiry() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Premium Subscription Ad FreeExpiry test Started");
		logStep("****** Premium Subscription Ad FreeExpiry test Started");
		Utils.getCurrentMacIPAddressAndSetandroidProxy(false, true);
		Ad.quit();
		Functions.launchtheAndroidApp();
		Functions.close_launchAppAndroid();	
		loginScreen = new AndroidLogInScreen(Ad);
		loginScreen.CancelPremiumSubscription();
		System.out.println("****** Waiting for five minutes after cancelled the Ad free subsctption");
		logStep("****** Waiting for five minutes after cancelled the Ad free subsctption");
		TestBase.waitForMilliSeconds(240000);
		/*finish = System.nanoTime();
		subscriptionFqCapEndTime = System.nanoTime();
		timeElapsed = finish - start;
		subscriptionFqtimeElapsed = subscriptionFqCapEndTime - subscriptionFqCapStrtTime;
		convert = TimeUnit.SECONDS.convert(timeElapsed, TimeUnit.NANOSECONDS);
		subscriptionFqtimeconvert = TimeUnit.SECONDS.convert(subscriptionFqtimeElapsed,
				TimeUnit.NANOSECONDS);
		// System.out.println("Elapsed time is: " + convert);
		System.out.println("Subscription Frequency Cap Elapsed time is: " + subscriptionFqtimeconvert);
		
		if (subscriptionFqtimeconvert >= 305) {
			System.out.println("Subscription Frequency Cap Elapsed time is: >= 300, hence ads to be displayed, continuing validation");
		} else {
			System.out.println("Will be going to for loop due to elapsed time less than 300 s ");
			logStep("Will be going to forloop due to elapsed time less than 300 s ");
			for (int i = 0; i <= 300; i++) {
				TestBase.waitForMilliSeconds(1000);
				finish = System.nanoTime();
				subscriptionFqCapEndTime = System.nanoTime();

				timeElapsed = finish - start;
				subscriptionFqtimeElapsed = subscriptionFqCapEndTime - subscriptionFqCapStrtTime;

				convert = TimeUnit.SECONDS.convert(timeElapsed, TimeUnit.NANOSECONDS);
				subscriptionFqtimeconvert = TimeUnit.SECONDS.convert(subscriptionFqtimeElapsed,
						TimeUnit.NANOSECONDS);
				// System.out.println("Elapsed time is: " + convert);
				System.out.println(
						"Subscription Frequency Cap Elapsed time is: " + subscriptionFqtimeconvert);
				
				if (subscriptionFqtimeconvert >= 305) {
					System.out.println("Subscription Frequency Cap Elapsed time is: >= 300, hence ads to be displayed, continuing validation");
					break;
				}
			}
		}*/
		Utils.getCurrentMacIPAddressAndSetandroidProxy(true, true);
	//	Ad.launchApp();
		//Ad.quit();
		Functions.launchtheAndroidApp();
		attachScreen();
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		//Functions.put_Background_launch(10);
		Functions.archive_folder("Charles");
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
	}
	
	
	/**
	 * This method verifies Lotame call 
	 * @throws Exception
	 */
	@Test(priority = 3018, enabled = true)
	@Description("Lotame Call verification")
	public void Verify_Lotame_Call_When_AdFree_Subscription_Monthly_Expired() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** bcp.crwdcntrl.net Call test case Started");
		logStep("****** bcp.crwdcntrl.net Call test case Started");
		Utils.verifyAPICal("Smoke", "Lotame", true);
	}

	/**
	 * This method verifies FACTUAL call
	 * @throws Exception
	 */
	@Test(priority = 3020, enabled = true)
	@Description("Factual Call verification")
	public void Verify_LocationWFXTriggers_Call_When_AdFree_Subscription_Monthly_Expired() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** location.wfxtriggers.com Call test case Started");
		logStep("location.wfxtriggers.com Call test case Started");
		Utils.verifyAPICal("Smoke", "LocationWFX", true);
	}
	
	/**
	 * This method verifies WFXTriggers call
	 * @throws Exception
	 */
	@Test(priority = 3022, enabled = true)
	@Description("WFXTrigger Call verification")
	public void Verify_WFXTriggers_Call_When_AdFree_Subscription_Monthly_Expired() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** triggers.wfxtriggers.com Call test case Started");
		logStep("****** triggers.wfxtriggers.com Call test case Started");
		Utils.verifyAPICal("Smoke", "WFXTrigger", true);
	}
	
	/**
	 * This method verifies Amazon call
	 * @throws Exception
	 */
	@Test(priority = 3024, enabled = true)
	@Description("Amazon aax call verification")
	public void Verify_Amazon_Call_When_AdFree_Subscription_Monthly_Expired() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** amazon-adsystem.com Call test case Started");
		logStep("****** amazon-adsystem.com Call test case Started");
		Utils.verify_Amazon_aax_call("Smoke", "Amazon", true);
	}
	
	/**
	 * This method verifies Criteo Initialization API call
	 * @throws Exception
	 */
	@Test(priority = 3026, enabled = true)
	@Description("Verify Criteo SDK inapp v2 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_When_AdFree_Subscription_Monthly_Expired() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Criteo SDK inapp/v2 call ====================");
		System.out.println("****** Criteo SDK inapp/v2 call validation Started");
		logStep("****** Criteo SDK inapp/v2 call validation Started");
		Utils.verifyCriteo_inapp_v2_Call("Smoke", "Criteo", true);
	}
	
	/**
	 * This method verifies Criteo Bidder API call
	 * @throws Exception
	 */
	@Test(priority = 3028, enabled = true)
	@Description("Verify Criteo SDK config app call")
	public void Verify_Criteo_SDK_config_app_Call_When_AdFree_Subscription_Monthly_Expired() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Criteo SDK config/app call ====================");
		System.out.println("****** Criteo SDK config/app call validation Started");
		logStep("****** Criteo SDK config/app call validation Started");
		Utils.verifyCriteo_config_app_Call("Smoke", "Criteo", true);
	}


	/**
	 * This method verifies NextGen IM gampad call
	 * @throws Exception
	 */
	@Test(priority = 3030, enabled = true)
	@Description("Verify Gampad Ad Call")
	public void Verify_Gampad_call_When_AdFree_Subscription_Monthly_Expired() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Gampad Call verification test case Started");
		logStep("****** Gampad Call verification test case Started");
		Utils.verify_Gampad_adcall("Smoke", "Gampad", true);
	}
	
	/**
	 * This method enables monthly premium subscription
	 * @throws Exception
	 */
	@Test(priority = 4000, enabled = true)
	@Description("Verify Premium Subscription")
	public void enablePremiumSubsription() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Enable Premium Subscription test Started");
		logStep("****** Enable Premium Subscription test Started");
		Utils.getCurrentMacIPAddressAndSetandroidProxy(false, true);
	//Ad.launchApp();
		Ad.quit();
	//	Ad.resetApp();
		Functions.launchtheAndroidApp();
		Functions.close_launchAppAndroid();
		loginScreen = new AndroidLogInScreen(Ad);
	//	Utils.PremiumSubscriptionforMonthly();
//		TestBase.waitForMilliSeconds(5000);
//		Ad.launchApp();
		try {
			//stScreen.UPSX_login("twcremoveads@gmail.com", "Monocept@123");
			loginScreen.enableMonthlyPremiumSubscription();
			
		//loginScreen.enableMonthlyPremiumSubscription("twcremoveads@gmail.com", "Monocept@123");
//			loginScreen.enableYearlyPremiumSubscription("jmktwc4@gmail.com", "300Interstate");
		} finally {
			subscriptionFqCapStrtTime = System.nanoTime();
			start = System.nanoTime();
			Functions.close_launchAppAndroid();
			attachScreen();
								
			Utils.getCurrentMacIPAddressAndSetandroidProxy(true, true);
			Ad.quit();
			Functions.launchtheAndroidApp();
			//	Ad.resetApp();
			Functions.launchtheAndroidApp();
			Functions.close_launchAppAndroid();
		//	Ad.launchApp();
			Ad.terminateApp("com.weather.Weather");
			System.out.println("App Closed SuccessFully");
			logStep("App Closed SuccessFully");
			Ad.launchApp();
			System.out.println("App Launched SuccessFully");
			logStep("App Launched SuccessFully");
			proxy.clearCharlesSession();
			Functions.close_launchAppAndroid();
			Functions.put_Background_launchAndroid(10);
			Functions.archive_folder("Charles");
			proxy.getXml();
			Utils.createXMLFileForCharlesSessionFile();
		}
		
	}
	
	/**
	 * This method verifies Lotame call 
	 * @throws Exception
	 */
	@Test(priority = 4002, enabled = true)
	@Description("Lotame Call verification")
	public void Verify_Lotame_Call_When_Premium_Subscription_Monthly_Enabled() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** bcp.crwdcntrl.net Call test case Started");
		logStep("****** bcp.crwdcntrl.net Call test case Started");
		Utils.verifyAPICal("Smoke", "Lotame", false);
	}

	/**
	 * This method verifies FACTUAL call
	 * @throws Exception
	 */
	@Test(priority = 4004, enabled = true)
	@Description("Factual Call verification")
	public void Verify_LocationWFXTriggers_Call_When_Premium_Subscription_Monthly_Enabled() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** location.wfxtriggers.com Call test case Started");
		logStep("location.wfxtriggers.com Call test case Started");
		Utils.verifyAPICal("Smoke", "LocationWFX", false);
	}
	
	/**
	 * This method verifies WFXTriggers call
	 * @throws Exception
	 */
	@Test(priority = 4006, enabled = true)
	@Description("WFXTrigger Call verification")
	public void Verify_WFXTriggers_Call_When_Premium_Subscription_Monthly_Enabled() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** triggers.wfxtriggers.com Call test case Started");
		logStep("****** triggers.wfxtriggers.com Call test case Started");
		Utils.verifyAPICal("Smoke", "WFXTrigger", false);
	}
	
	/**
	 * This method verifies Amazon call
	 * @throws Exception
	 */
	@Test(priority = 4008, enabled = true)
	@Description("Amazon aax call verification")
	public void Verify_Amazon_Call_When_Premium_Subscription_Monthly_Enabled() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** amazon-adsystem.com Call test case Started");
		logStep("****** amazon-adsystem.com Call test case Started");
		Utils.verify_Amazon_aax_call("Smoke", "Amazon", false);
	}
	
	/**
	 * This method verifies Criteo Initialization API call
	 * @throws Exception
	 */
	@Test(priority = 4010, enabled = true)
	@Description("Verify Criteo SDK inapp v2 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_When_Premium_Subscription_Monthly_Enabled() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Criteo SDK inapp/v2 call ====================");
		System.out.println("****** Criteo SDK inapp/v2 call validation Started");
		logStep("****** Criteo SDK inapp/v2 call validation Started");
		Utils.verifyCriteo_inapp_v2_Call("Smoke", "Criteo", false);
	}
	
	/**
	 * This method verifies Criteo Bidder API call
	 * @throws Exception
	 */
	@Test(priority = 4012, enabled = true)
	@Description("Verify Criteo SDK config app call")
	public void Verify_Criteo_SDK_config_app_Call_When_Premium_Subscription_Monthly_Enabled() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Criteo SDK config/app call ====================");
		System.out.println("****** Criteo SDK config/app call validation Started");
		logStep("****** Criteo SDK config/app call validation Started");
		Utils.verifyCriteo_config_app_Call("Smoke", "Criteo", false);
	}

	/**
	 * This method verifies NextGen IM gampad call
	 * @throws Exception
	 */
	@Test(priority = 4014, enabled = true)
	@Description("Verify Gampad Ad Call")
	public void Verify_Gampad_call_When_Premium_Subscription_Monthly_Enabled() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Gampad Call verification test case Started");
		logStep("****** Gampad Call verification test case Started");
		Utils.verify_Gampad_adcall("Smoke", "Gampad", false);
	}
	
	
	/**
	 * This method verifies whether premium subscription expired or not
	 * @throws Exception
	 */
	@Test(priority = 4016, enabled = true)
	@Description("Verify Premium Subscription Monthly Expiry")
	public void checkPremiumSubsriptionMonthlyExpiry() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Premium Subscription Expiry test Started");
		logStep("****** Premium Subscription Expiry test Started");
		Utils.getCurrentMacIPAddressAndSetandroidProxy(false, true);
		Ad.quit();
		Functions.launchtheAndroidApp();
		Functions.close_launchAppAndroid();	
		loginScreen = new AndroidLogInScreen(Ad);
		stScreen = new AndroidSettingsScreen(Ad);
		loginScreen.CancelPremiumSubscription();
		System.out.println("****** Waiting for five minutes after cancelled the premium subsctption");
		logStep("****** Waiting for five minutes after cancelled the premium subsctption");
		TestBase.waitForMilliSeconds(240000);
		/*finish = System.nanoTime();
		subscriptionFqCapEndTime = System.nanoTime();
		timeElapsed = finish - start;
		subscriptionFqtimeElapsed = subscriptionFqCapEndTime - subscriptionFqCapStrtTime;
		convert = TimeUnit.SECONDS.convert(timeElapsed, TimeUnit.NANOSECONDS);
		subscriptionFqtimeconvert = TimeUnit.SECONDS.convert(subscriptionFqtimeElapsed,
				TimeUnit.NANOSECONDS);
		// System.out.println("Elapsed time is: " + convert);
		System.out.println("Subscription Frequency Cap Elapsed time is: " + subscriptionFqtimeconvert);
		
		if (subscriptionFqtimeconvert >= 305) {
			System.out.println("Subscription Frequency Cap Elapsed time is: >= 300, hence ads to be displayed, continuing validation");
		} else {
			System.out.println("Will be going to for loop due to elapsed time less than 300 s ");
			logStep("Will be going to forloop due to elapsed time less than 300 s ");
			for (int i = 0; i <= 300; i++) {
				TestBase.waitForMilliSeconds(1000);
				finish = System.nanoTime();
				subscriptionFqCapEndTime = System.nanoTime();

				timeElapsed = finish - start;
				subscriptionFqtimeElapsed = subscriptionFqCapEndTime - subscriptionFqCapStrtTime;

				convert = TimeUnit.SECONDS.convert(timeElapsed, TimeUnit.NANOSECONDS);
				subscriptionFqtimeconvert = TimeUnit.SECONDS.convert(subscriptionFqtimeElapsed,
						TimeUnit.NANOSECONDS);
				// System.out.println("Elapsed time is: " + convert);
				System.out.println(
						"Subscription Frequency Cap Elapsed time is: " + subscriptionFqtimeconvert);
				
				if (subscriptionFqtimeconvert >= 305) {
					System.out.println("Subscription Frequency Cap Elapsed time is: >= 300, hence ads to be displayed, continuing validation");
					break;
				}
			}
		}*/
		Utils.getCurrentMacIPAddressAndSetandroidProxy(true, true);
	//	Ad.launchApp();
		//Ad.quit();
		Functions.launchtheAndroidApp();
		attachScreen();
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		//Functions.put_Background_launch(10);
		Functions.archive_folder("Charles");
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
	}
	
	
	/**
	 * This method verifies Lotame call 
	 * @throws Exception
	 */
	@Test(priority = 4018, enabled = true)
	@Description("Lotame Call verification")
	public void Verify_Lotame_Call_When_Premium_Subscription_Monthly_Expired() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** bcp.crwdcntrl.net Call test case Started");
		logStep("****** bcp.crwdcntrl.net Call test case Started");
		Utils.verifyAPICal("Smoke", "Lotame", true);
	}

	/**
	 * This method verifies FACTUAL call
	 * @throws Exception
	 */
	@Test(priority = 4020, enabled = true)
	@Description("Factual Call verification")
	public void Verify_LocationWFXTriggers_Call_When_Premium_Subscription_Monthly_Expired() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** location.wfxtriggers.com Call test case Started");
		logStep("location.wfxtriggers.com Call test case Started");
		Utils.verifyAPICal("Smoke", "LocationWFX", true);
	}
	
	/**
	 * This method verifies WFXTriggers call
	 * @throws Exception
	 */
	@Test(priority = 4022, enabled = true)
	@Description("WFXTrigger Call verification")
	public void Verify_WFXTriggers_Call_When_Premium_Subscription_Monthly_Expired() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** triggers.wfxtriggers.com Call test case Started");
		logStep("****** triggers.wfxtriggers.com Call test case Started");
		Utils.verifyAPICal("Smoke", "WFXTrigger", true);
	}
	
	/**
	 * This method verifies Amazon call
	 * @throws Exception
	 */
	@Test(priority = 4024, enabled = true)
	@Description("Amazon aax call verification")
	public void Verify_Amazon_Call_When_Premium_Subscription_Monthly_Expired() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** amazon-adsystem.com Call test case Started");
		logStep("****** amazon-adsystem.com Call test case Started");
		Utils.verify_Amazon_aax_call("Smoke", "Amazon", true);
	}
	
	/**
	 * This method verifies Criteo Initialization API call
	 * @throws Exception
	 */
	@Test(priority = 4026, enabled = true)
	@Description("Verify Criteo SDK inapp v2 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_When_Premium_Subscription_Monthly_Expired() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Criteo SDK inapp/v2 call ====================");
		System.out.println("****** Criteo SDK inapp/v2 call validation Started");
		logStep("****** Criteo SDK inapp/v2 call validation Started");
		Utils.verifyCriteo_inapp_v2_Call("Smoke", "Criteo", true);
	}
	
	/**
	 * This method verifies Criteo Bidder API call
	 * @throws Exception
	 */
	@Test(priority = 4028, enabled = true)
	@Description("Verify Criteo SDK config app call")
	public void Verify_Criteo_SDK_config_app_Call_When_Premium_Subscription_Monthly_Expired() throws Exception {
		System.out.println("==============================================");
		System.out.println("=========================== Criteo SDK config/app call ====================");
		System.out.println("****** Criteo SDK config/app call validation Started");
		logStep("****** Criteo SDK config/app call validation Started");
		Utils.verifyCriteo_config_app_Call("Smoke", "Criteo", true);
	}

	/**
	 * This method verifies NextGen IM gampad call
	 * @throws Exception
	 */
	@Test(priority = 4030, enabled = true)
	@Description("Verify Gampad Ad Call")
	public void Verify_Gampad_call_When_Premium_Subscription_Monthly_Expired() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Gampad Call verification test case Started");
		logStep("****** Gampad Call verification test case Started");
		Utils.verify_Gampad_adcall("Smoke", "Gampad", true);
	}


}

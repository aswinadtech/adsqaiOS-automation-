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
import com.twc.ios.app.pages.AndroidDailyNavTab;
import com.twc.ios.app.pages.AndroidHomeNavTab;
import com.twc.ios.app.pages.AndroidHourlyNavTab;
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
import com.twc.ios.app.pages.SettingsScreen;
import com.twc.ios.app.pages.VideoNavTab;

import io.qameta.allure.Description;

public class AndroidVIRGINIAPrivacyTest extends TwcIosBaseTest {
	// private static final MobileAutomationLogger LOGGER = new
	// MobileAutomationLogger(GDPRFunctionalEULaunchTests.class);
	private static final String CONFIG_FILE_PATH = "enableVIRGINIA.config";
	private static final String LGPD_CONFIG_FILE_PATH = "enableLGPD.config";
	private static final String GDPR_CONFIG_FILE_PATH = "enableGDPR.config";
	private static final String SERBIA_CONFIG_FILE_PATH = "enableSERBIA.config";
	private static final String LATAMCO_CONFIG_FILE_PATH = "enableLATAMCO.config";
	private static final String USA_CONFIG_FILE_PATH = "enableUSA.config";
	private File configFile;
	AndroidHourlyNavTab hrTab;
	AndroidDailyNavTab dTab;
	AndroidHomeNavTab hmTab;
	AndroidRadarNavTab rTab;
	AndroidVideoNavTab vTab;
	AndroidAddressScreen addrScreen;
	AndroidSeasonalHubCardScreen sScreen;
	AndroidSettingsScreen stScreen;

	@BeforeClass(alwaysRun = true)
	@Description("BeforeClass")
	public void beforeClass() {
		System.out.println("****** VIRGINIA Privacy Test Started");
		logStep("****** VIRGINIA Privacy Test Started");
		this.configFile = this.rewriteRuleToEnableVIRGINIAAndroid(CONFIG_FILE_PATH);
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
			Ad.terminateApp("com.weather.Weather");;
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
		
		System.out.println("****** VIRGINIA Privacy Test Ended");
		logStep("****** VIRGINIA Privacy Test Ended");
		System.out.println("==============================================");
	}

	/*
	 * This method is disabled as uninstalling and installing app and set proxy takes 
	 * almost 20 mins, instead waiting for 5 mins to get the dsx call to rewrite config
	 */
/*	@Test(priority = -1)
	@Description("Download and Install App from Firebase")
	public void downloadAndInstallApp_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** downloadAndInstallApp Test Started");
		logStep("****** downloadAndInstallApp Test Started");
		// Preconditions
		Functions.capabilities();
		Functions.Appium_Autostart();
		Utils.getCurrentMacIPAddressAndSetiPhoneProxy(false);
		Functions.uninstallApp();
		Functions.launchFirebaseInSafariAndInstallApp();
		Utils.twcAppInstalledCheck();
		Ad.closeApp();
		System.out.println("****** downloadAndInstallApp Test Completed");
		logStep("****** downloadAndInstallApp Test Completed");

	}*/

	@Test(priority = 0)
	@Description("Enable Preconditions for VIRGINIA")
	public void preConditionsTest_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** PreConditions For VIRGINIA test case Started");
		logStep("****** PreConditions For VIRGINIA test case Started");
		// Preconditions
		Functions.capabilities();
		Functions.Appium_Autostart();
		// Enable rewriting on Charles install/launch TWC
		proxy.enableRewriting();
		proxy.startRecording();
		proxy.clearCharlesSession();
		/* Preconditions
		 * as we are not uninstalling and installing, this step not required
		Utils.getCurrentMacIPAddressAndSetiPhoneProxy(true, true);*/
		Functions.archive_folder("Charles");
		Functions.launchtheAndroidApp();
		System.out.println("App launched ");
		logStep("App launched ");
		Functions.close_launchAppAndroid();
		proxy.getXml();
		Functions.archive_folder("Charles");
		hrTab = new AndroidHourlyNavTab(Ad);
		dTab = new AndroidDailyNavTab(Ad);
		hmTab = new AndroidHomeNavTab(Ad);
		rTab = new AndroidRadarNavTab(Ad);
		vTab = new AndroidVideoNavTab(Ad);
		addrScreen = new AndroidAddressScreen(Ad);
		sScreen = new AndroidSeasonalHubCardScreen(Ad);
		stScreen = new AndroidSettingsScreen(Ad);
		hmTab.clickonHomeTab();
		hmTab.clickonHomeTab();
	}
	/*
	 * to reduce the time of execution, privacy optin being selected
	 * from settings page
	 */
	  @Test(priority = 10, enabled = true)
	  @Description("Enable privacy optin feature") public void
	  verify_enabling_Privacy_optin_for_VIRGINIA() throws Exception {
	  System.out.println("==============================================");
	  System.out.
	  println("****** Enable Privacy optin feature ");
	  logStep("Enable Privacy optin feature ");
	  proxy.clearCharlesSession(); 
	  //Utils.navigateTofeedCard("privacy");
	  System.out.println("****** Enable Privacy optin feature ");
	  logStep("Enable Privacy optin feature ");
	  //Functions.select_Privacy_Optin("Smoke", "Privacy");
	  //Functions.select_Privacy_Optin("Smoke", "Privacy");
	  stScreen.select_Privacy_Optin_From_Settings_Virginia("Smoke", "Privacy");
	  stScreen.select_Privacy_Optin_From_Settings_Virginia("Smoke", "Privacy"); 
	  //proxy.getXml();
	  Functions.archive_folder("Charles"); 
	  proxy.clearCharlesSession(); 
	  System.out.println("Kill and Launching the app after privacy optin is selected");
	  logStep("Kill and Launching the app after privacy optin is selected");
	  Functions.close_launchAppAndroid();
	  //Functions.checkForAppState();
	  proxy.clearCharlesSession();
	  Functions.close_launchAppAndroid();
   	  Functions.put_Background_launchAndroid(15);
	  }
	 

	/**
	 * @throws Exception This Script Validate Ads on Feed Cards...
	 */
	@Test(priority = 11, enabled = true)
	@Description("Navigating to Feed Cards when privacy optin ")
	public void navigate_To_Feed_Cards_when_Privacy_optin_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");

		System.out.println("****** Navigating to Feed Cards when privacy optin");
		logStep("Navigating to Feed Cards when privacy optin ");

		try {
			
			hmTab.clickonHomeTab();
			hmTab.clickonHomeTab();
			//hrTab.navigateToHourlyTab();
			hrTab.navigateToHourlyTabAndHandleInterstitialAd();
			TestBase.waitForMilliSeconds(2000);
			
			/*
			 * Sometimes Close Advertisement button interstitial button technically disabled,
			 * hence navigation to other tabs gets failed. hence kill and launching the app after calling the handleinterstiital method. 
			 */
			 /*proxy.clearCharlesSession();
			 Functions.close_launchApp();
			 
			 Functions.put_Background_launch(15);
			 Functions.checkForAppState();
			 hmTab.clickonHomeTab();*/
			Functions.close_launchAppAndroid();
			 hmTab.clickonHomeTab();
			 hmTab.clickonHomeTab();
			// navigate to Daily tab
			dTab.navigateToDailyTab();
			TestBase.waitForMilliSeconds(2000);

			// navigate to Video tab
			// vTab.navigateToVideoTab();
			
		} catch (Exception e) {
			System.out.println("There is an exception while navigting to all the feed cards.");
			logStep("There is an exception while navigting to all the feed cards.");
		} finally {
			Functions.archive_folder("Charles");
			TestBase.waitForMilliSeconds(5000);
			proxy.getXml();
			Utils.createXMLFileForCharlesSessionFile();
		}
		
	}

	@Test(priority = 19, enabled = true)
	@Description("Verify NextGen IM ad call sod value when privacy optin")
	public void validate_NextGen_IM_Adcall_sod_val_privacy_optin_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");
		// Functions.retryclear();

		System.out.println("****** Verify NextGen IM Adcall sod value when privacy optin");
		logStep("Verify NextGen IM Adcall sod value when privacy optin");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "sod", "yes");

	}

/*	@Test(priority = 20, enabled = true)
	@Description("Verify Hourly details page Call sod value when privacy optin")
	public void verify_Hourly_details_call_sod_val_privacy_optin_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");

		System.out.println("****** Verify Hourly details Call sod value when privacy optin");
		logStep("Verify Hourly details Call sod value when privacy optin");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "sod", "yes");

	}*/

	@Test(priority = 21, enabled = true)
	@Description("Verify Daily details page Call sod value when privacy optin")
	public void verify_Daily_details_call_sod_val_privacy_optin_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");

		System.out.println("****** Verify Daily details Call sod value when privacy optin");
		logStep("Verify Daily details Call sod value when privacy optin");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Daily(10day)", "sod", "yes");

	}
	
	/**
	 * This method verifies Amazon call
	 * @throws Exception
	 */
	@Test(priority = 26, enabled = true)
	@Description("Amazon aax call verification")
	public void Verify_Amazon_Call_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** amazon-adsystem.com Call test case Started");
		logStep("****** amazon-adsystem.com Call test case Started");
		Utils.verify_Amazon_aax_call("Smoke", "Amazon", true);
	}

	
	// Lotame Test case
	@Test(priority = 38, enabled = true)
	@Description("Lotame Call when privacy optin")
	public void Verify_Lotame_call_privacy_optin_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** bcp.crwdcntrl.net Call test case Started when privacy optin");
		logStep("****** bcp.crwdcntrl.net Call test case Started when privacy optin");

		Utils.verifyAPICal("Smoke", "Lotame", true);

	}

	// FACTUAL Test cases
	/*
	 * Factual call is blocked, hence expected to not present this call in charles
	 * session from 12.6 builds onwards...
	 */

	@Test(priority = 39, enabled = true)
	@Description("Factual Call when privacy optin")
	public void Verify_LocationWFXTriggers_Call_privacy_optin_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** location.wfxtriggers.com Call test case Started when privacy optin");
		logStep("location.wfxtriggers.com Call test case Started when privacy optin");
		Utils.verifyAPICal("Smoke", "LocationWFX", false);

	}
	
	/*
	 * This method validates WFXTriggers call
	 */
	@Test(priority = 40, enabled = true)
	@Description("WFXTrigger Call when privacy optin")
	public void Verify_WFXTriggers_Call_privacy_optin_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** triggers.wfxtriggers.com Call test case Started when privacy optin");
		logStep("****** triggers.wfxtriggers.com Call test case Started when privacy optin");
		Utils.verifyAPICal("Smoke", "WFXTrigger", true);

	}

	@Test(priority = 42, enabled = true)
	@Description("Validating NextGen IM Call rdp value when privacy optin")
	public void validate_NextGen_IM_call_rdp_val_privacy_optin_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGenIM Call rdp value when privacy optin");
		logStep("Validating NextGenIM Call rdp value when privacy optin ");

		Utils.validate_rdp_val_in_gampad_url("Smoke", "Marquee", false);

	}

	@Test(priority = 51, enabled = true)
	@Description("Verify Criteo SDK inapp v2 call when privacy optin")
	public void Verify_Criteo_SDK_inapp_v2_Call_privacy_optin_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");
		System.out
				.println("=========================== Criteo SDK inapp/v2 call when privacy optin====================");
		System.out.println("****** Criteo SDK inapp/v2 call when privacy optin validation Started");
		logStep("****** Criteo SDK inapp/v2 call when privacy optin validation Started");
		Utils.verifyCriteo_inapp_v2_Call("Smoke", "Criteo", true);

	}

	@Test(priority = 52, enabled = true)
	@Description("Verify Criteo SDK config app call when privacy optin")
	public void Verify_Criteo_SDK_config_app_Call_privacy_optin_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK config/app call when privacy optin====================");
		System.out.println("****** Criteo SDK config/app call when privacy optin validation Started");
		logStep("****** Criteo SDK config/app call when privacy optin validation Started");
		Utils.verifyCriteo_config_app_Call("Smoke", "Criteo", true);

	}

	@Test(priority = 75, enabled = true)
	@Description("Deriving Video Call when privacy optin")
	public void derive_VideoCall_IU_when_Privacy_optin_for_VIRGINIA() throws Exception {

		System.out.println("==============================================");
		System.out.println("****** Deriving VideoCall For VIRGINIA when privacy optin");
		logStep("****** Deriving VideoCall For VIRGINIA when privacy optin");
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

	@Test(priority = 76, enabled = true)
	@Description("Verify Preroll ad on Video Call sod value when privacy optin")
	public void verify_PrerollAd_call_sod_val_privacy_optin_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");

		System.out.println("****** Prerol-video Call sod value when privacy optin");
		logStep("Verify Prerol-video Call sod value when privacy optin");
		Utils.validate_custom_param_val_of_gampad("Smoke", "PreRollVideo", "sod", "yes");

	}

	@Test(priority = 77, enabled = true)
	@Description("Validating PrerollVideo Call rdp value when privacy optin")
	public void validate_PrerollVideo_call_rdp_val_privacy_optin_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating PrerollVideo Call rdp value when privacy optin");
		logStep("Validating PrerollVideo Call rdp value when privacy optin ");

		Utils.validate_rdp_val_in_gampad_url("Smoke", "PrerollVideo", false);

	}

	@Test(priority = 101, enabled = true)
	@Description("Select privacy optout ")
	public void verify_enabling_Privacy_optout_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Enable Privacy optout feature ");
		logStep("Enable Privacy optout feature ");
		
		hmTab.clickonHomeTab();
		hmTab.clickonHomeTab();
		//Functions.select_Privacy_Optout("Smoke", "Privacy");
		//Functions.select_Privacy_Optout("Smoke", "Privacy");
		stScreen.select_Privacy_Optout_From_Settings_Virginia("Smoke", "Privacy");
		stScreen.select_Privacy_Optout_From_Settings_Virginia("Smoke", "Privacy");
		//proxy.getXml();
		Functions.archive_folder("Charles");
		proxy.clearCharlesSession();
		System.out.println("Kill and Launching the app after privacy optout is selected");
		logStep("Kill and Launching the app after privacy optout is selected");
		//Functions.close_launchApp();
		//Functions.checkForAppState();
		Functions.close_launchAppAndroid();
		proxy.clearCharlesSession();
		Functions.close_launchAppAndroid();
		Functions.put_Background_launchAndroid(15);
	}

	@Test(priority = 102, enabled = true)
	@Description("Navigating to Feed Cards when privacy optout ")
	public void navigate_To_Feed_Cards_when_Privacy_optout_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");

		System.out.println("****** Navigating to Feed Cards when privacy optout");
		logStep("Navigating to Feed Cards when privacy optout ");

		try {
			
			hmTab.clickonHomeTab();
			hmTab.clickonHomeTab();
			//hrTab.navigateToHourlyTab();
			hrTab.navigateToHourlyTabAndHandleInterstitialAd();
			TestBase.waitForMilliSeconds(2000);
			Functions.close_launchAppAndroid();
			hmTab.clickonHomeTab();
			hmTab.clickonHomeTab();
			// navigate to Daily tab
			dTab.navigateToDailyTab();
			TestBase.waitForMilliSeconds(2000);

			// navigate to Video tab
			// vTab.navigateToVideoTab();
			

		} catch (Exception e) {
			System.out.println("There is an exception while navigting to all the feed cards.");
			logStep("There is an exception while navigting to all the feed cards.");
		} finally {
			Functions.archive_folder("Charles");
			TestBase.waitForMilliSeconds(5000);
			proxy.getXml();
			Utils.createXMLFileForCharlesSessionFile();
		}

	}

	@Test(priority = 105, enabled = true)
	@Description("Verify NextGen IM ad call sod value when privacy optout")
	public void validate_NextGen_IM_Adcall_sod_val_privacy_optout_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");
		// Functions.retryclear();

		System.out.println("****** Verify NextGen IM Adcall sod value when privacy optout");
		logStep("Verify NextGen IM Adcall sod value when privacy optout");
		
		Utils.validate_custom_param_val_of_gampad("Smoke", "Marquee", "sod", "no");

	}

	/*@Test(priority = 106, enabled = true)
	@Description("Verify Hourly details page Call sod value when privacy optout")
	public void verify_Hourly_details_call_sod_val_privacy_optout_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");

		System.out.println("****** Verify Hourly details Call sod value when privacy optout");
		logStep("Verify Hourly details Call sod value when privacy optout");
		
		Utils.validate_custom_param_val_of_gampad("Smoke", "Hourly", "sod", "no");

	}*/

	@Test(priority = 107, enabled = true)
	@Description("Verify Daily details page Call sod value when privacy optout")
	public void verify_Daily_details_call_sod_val_privacy_optout_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Verify Daily details Call sod value when privacy optout");
		logStep("Verify Daily details Call sod value when privacy optout");
		Utils.validate_custom_param_val_of_gampad("Smoke", "Daily(10day)", "sod", "no");
	}
	
	/**
	 * This method verifies Amazon call
	 * @throws Exception
	 */
	@Test(priority = 111, enabled = true)
	@Description("Amazon aax call verification when privacy optout")
	public void Verify_Amazon_Call_privacy_optout_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** amazon-adsystem.com Call test case Started when privacy optout");
		logStep("****** amazon-adsystem.com Call test case Started when privacy optout");
		Utils.verify_Amazon_aax_call("Smoke", "Amazon", false);
	}


	// Lotame Test case
	@Test(priority = 131, enabled = true)
	@Description("Lotame Call when privacy optout")
	public void Verify_Lotame_Call_privacy_optout_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** bcp.crwdcntrl.net Call test case Started when privacy optout");
		logStep("****** bcp.crwdcntrl.net Call test case Started when privacy optout");

		Utils.verifyAPICal("Smoke", "Lotame", false);

	}

	// FACTUAL Test cases
	/*
	 * Factual call is blocked, hence expected to not present this call in charles
	 * session from 12.6 builds onwards...
	 */

	@Test(priority = 132, enabled = true)
	@Description("Factual Call when privacy optout")
	public void Verify_LocationWFXTriggers_Call_privacy_optout_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** location.wfxtriggers.com Call test case Started when privacy optout");
		logStep("location.wfxtriggers.com Call test case Started when privacy optout");
		Utils.verifyAPICal("Smoke", "LocationWFX", false);

	}
	
	/*
	 * This method validates WFXTriggers call
	 */
	@Test(priority = 133, enabled = true)
	@Description("WFXTrigger Call when privacy optout")
	public void Verify_WFXTriggers_Call_privacy_optout_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** triggers.wfxtriggers.com Call test case Started when privacy optout");
		logStep("****** triggers.wfxtriggers.com Call test case Started when privacy optout");
		Utils.verifyAPICal("Smoke", "WFXTrigger", false);

	}

	@Test(priority = 135, enabled = true)
	@Description("Validating NextGen IM Call rdp value when privacy optout")
	public void validate_NextGen_IM_call_rdp_val_privacy_optout_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating NextGenIM Call rdp value when privacy optout");
		logStep("Validating NextGenIM Call rdp value when privacy optout ");

		Utils.validate_rdp_val_in_gampad_url("Smoke", "Marquee", true);

	}
	

	@Test(priority = 140, enabled = true)
	@Description("Verify Criteo SDK inapp v2 call when privacy optout")
	public void Verify_Criteo_SDK_inapp_v2_Call_privacy_optout_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK inapp/v2 call when privacy optout ====================");
		System.out.println("****** Criteo SDK inapp/v2 call when privacy optout validation Started");
		logStep("****** Criteo SDK inapp/v2 call when privacy optout validation Started");
		Utils.verifyCriteo_inapp_v2_Call("Smoke", "Criteo", false);

	}

	@Test(priority = 141, enabled = true)
	@Description("Verify Criteo SDK config app call when privacy optout")
	public void Verify_Criteo_SDK_config_app_Call_privacy_optout_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");
		System.out.println(
				"=========================== Criteo SDK config/app call when privacy optout====================");
		System.out.println("****** Criteo SDK config/app call when privacy optout validation Started");
		logStep("****** Criteo SDK config/app call when privacy optout validation Started");
		Utils.verifyCriteo_config_app_Call("Smoke", "Criteo", false);

	}

	@Test(priority = 175, enabled = true)
	@Description("Deriving Video Call when privacy optout")
	public void derive_VideoCall_IU_when_Privacy_optout_for_VIRGINIA() throws Exception {

		System.out.println("==============================================");
		System.out.println("****** Deriving VideoCall For VIRGINIA when privacy optout");
		logStep("****** Deriving VideoCall For VIRGINIA when privacy optout");
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

	@Test(priority = 176, enabled = true)
	@Description("Verify Preroll ad on Video Call sod value when privacy optout")
	public void verify_PrerollAd_call_sod_val_privacy_optout_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");

		System.out.println("****** Prerol-video Call sod value when privacy optout");
		logStep("Verify Prerol-video Call sod value when privacy optout");
		Utils.validate_custom_param_val_of_gampad("Smoke", "PreRollVideo", "sod", "no");

	}

	@Test(priority = 177, enabled = true)
	@Description("Validating PrerollVideo Call rdp value when privacy optout")
	public void validate_PrerollVideo_call_rdp_val_privacy_optout_for_VIRGINIA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Validating PrerollVideo Call rdp value when privacy optout");
		logStep("Validating PrerollVideo Call rdp value when privacy optout ");

		Utils.validate_rdp_val_in_gampad_url("Smoke", "PrerollVideo", true);
		
		/*
		 * Instead of Uninstall and install app for every regime, waiting for 5 mins to get dsx call is more time saviour
		 * hence below hard wait steps are added and corresponding uninstall and install steps will be commented in next regimes.
		 */
		System.out.println("****** Waiting for five minutes to get dsx call to override privacy and geo ip country for next test");
		logStep("****** Waiting for five minutes to get dsx call to override privacy and geo ip country for next test");
		TestBase.waitForMilliSeconds(240000);

	}

	
}

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
import com.twc.ios.app.pages.DailyNavTab;
import com.twc.ios.app.pages.HomeNavTab;
import com.twc.ios.app.pages.HourlyNavTab;
import com.twc.ios.app.pages.PlanningCardScreen;
import com.twc.ios.app.pages.RadarNavTab;
import com.twc.ios.app.pages.SeasonalHubCardScreen;
import com.twc.ios.app.pages.VideoNavTab;

import org.testng.annotations.BeforeClass;
import java.io.File;
import java.lang.reflect.Method;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.qameta.allure.Description;

public class SuppressAdsByGeoIpCountryTest_FullCountriesVerification extends TwcIosBaseTest {
	// private static final MobileAutomationLogger LOGGER = new
	// MobileAutomationLogger(GDPRFunctionalEULaunchTests.class);
	private static final String UA_CONFIG_FILE_PATH = "enableUA.config";
	private static final String CU_CONFIG_FILE_PATH = "enableCU.config";
	private static final String IR_CONFIG_FILE_PATH = "enableIR.config";
	private static final String KP_CONFIG_FILE_PATH = "enableKP.config";
	private static final String SD_CONFIG_FILE_PATH = "enableSD.config";
	private static final String SY_CONFIG_FILE_PATH = "enableSY.config";

	private File configFile;
	HourlyNavTab hrTab;
	DailyNavTab dTab;
	HomeNavTab hmTab;
	RadarNavTab rTab;
	VideoNavTab vTab;
	AddressScreen addrScreen;
	PlanningCardScreen pScreen;
	SeasonalHubCardScreen sScreen;

	@BeforeClass(alwaysRun = true)
	@Description("BeforeClass")
	public void beforeClass() {
		System.out.println("****** Suppress Ads By Geo IP Country Test Started");
		logStep("****** Suppress Ads By Geo IP Country Test Started");
		//this.configFile = this.rewriteRuleToEnableGDPR(CONFIG_FILE_PATH);
		this.configFile = this.rewriteRuleToOverrideGeoIpCountry(UA_CONFIG_FILE_PATH, "UA");
		proxy = new CharlesProxy("localhost", 8111, UA_CONFIG_FILE_PATH);

		proxy.startCharlesProxyWithUI();
		proxy.disableRewriting();
		proxy.stopRecording();
		proxy.disableMapLocal();
	}

	@AfterClass(alwaysRun = true)
	@Description("AfterClass")
	public void afterClass() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** ==============================================");
		if (this.configFile != null) {
			this.configFile.delete();
		}
		Functions.archive_folder("Charles");
		proxy.disableRewriting();
		proxy.quitCharlesProxy();
		try {
			Ad.terminateApp("com.weather.TWC");;
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

		System.out.println("****** Suppress Ads By Geo IP Country Test Ended");
		logStep("****** Suppress Ads By Geo IP Country Test Ended");
		System.out.println("==============================================");
	}

	/*
	 * @Test(priority = -1)
	 * 
	 * @Description("Download and Install App from Firebase") public void
	 * downloadAndInstallApp_for_SuppressAds() throws Exception {
	 * System.out.println("==============================================");
	 * System.out.println("****** downloadAndInstallApp Test Started");
	 * logStep("****** downloadAndInstallApp Test Started"); // Preconditions
	 * Functions.capabilities(); Functions.Appium_Autostart();
	 * Utils.getCurrentMacIPAddressAndSetiPhoneProxy(false);
	 * Functions.uninstallApp();
	 * //Functions.launchFirebaseInSafariAndInstallApp("RC");
	 * Functions.launchFirebaseInSafariAndInstallApp();
	 * Utils.twcAppInstalledCheck(); Ad.closeApp();
	 * System.out.println("****** downloadAndInstallApp Test Completed");
	 * logStep("****** downloadAndInstallApp Test Completed"); }
	 */

	@Test(priority = 0)
	@Description("Enable Preconditions for UA")
	public void preConditionsTest_for_UA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** PreConditions For UA test case Started");
		logStep("****** PreConditions For UA test case Started");
		// Preconditions
		Utils.getiPhoneUDID();
		Utils.getiOSVersion();
		Functions.capabilities();
		Functions.Appium_Autostart();
		// Enable rewriting on Charles install/launch TWC
		proxy.enableRewriting();
		proxy.startRecording();
		proxy.clearCharlesSession();
		/*as we are not uninstalling and installing, this step not required
		 * // Preconditions 
		 * Utils.getCurrentMacIPAddressAndSetiPhoneProxy(true, true);
		 */
		Functions.archive_folder("Charles");
		Functions.launchtheApp("true");
		System.out.println("App launched ");
		logStep("App launched ");
		proxy.getXml();
		Functions.archive_folder("Charles");
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.checkForAppState();
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.checkForAppState();
		Functions.put_Background_launch(15);
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

	}

	/**
	 * This method verifies Lotame call 
	 * @throws Exception
	 */
	@Test(priority = 101, enabled = true)
	@Description("Lotame Call verification")
	public void Verify_Lotame_Call_for_UA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** bcp.crwdcntrl.net Call test case Started");
		logStep("****** bcp.crwdcntrl.net Call test case Started");
		Utils.verifyAPICal("Smoke", "Lotame", false);
	}

	/**
	 * This method verifies FACTUAL call
	 * @throws Exception
	 */
	@Test(priority = 102, enabled = true)
	@Description("Factual Call verification")
	public void Verify_LocationWFXTriggers_Call__for_UA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** location.wfxtriggers.com Call test case Started");
		logStep("location.wfxtriggers.com Call test case Started");
		Utils.verifyAPICal("Smoke", "LocationWFX", false);
	}
	
	/**
	 * This method verifies WFXTriggers call
	 * @throws Exception
	 */
	@Test(priority = 103, enabled = true)
	@Description("WFXTrigger Call verification")
	public void Verify_WFXTriggers_Call_for_UA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** triggers.wfxtriggers.com Call test case Started");
		logStep("****** triggers.wfxtriggers.com Call test case Started");
		Utils.verifyAPICal("Smoke", "WFXTrigger", false);
	}
	
	/**
	 * This method verifies Amazon call
	 * @throws Exception
	 */
	@Test(priority = 104, enabled = true)
	@Description("Amazon aax call verification")
	public void Verify_Amazon_Call_for_UA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** amazon-adsystem.com Call test case Started");
		logStep("****** amazon-adsystem.com Call test case Started");
		Utils.verify_Amazon_aax_call("Smoke", "Amazon", false);
	}
	
	/**
	 * This method verifies Criteo Initialization API call
	 * @throws Exception
	 */
	@Test(priority = 105, enabled = true)
	@Description("Verify Criteo SDK inapp v2 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_for_UA() throws Exception {
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
	@Test(priority = 106, enabled = true)
	@Description("Verify Criteo SDK config app call")
	public void Verify_Criteo_SDK_config_app_Call_for_UA() throws Exception {
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
	@Test(priority = 107, enabled = true)
	@Description("Verify NextGen IM Call")
	public void Verify_NextGen_IM_call_for_UA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** NextGen IM Call verification test case Started");
		logStep("****** NextGen IM Call verification test case Started");
		Utils.verify_Gampad_adcall("Smoke", "Gampad", false);
	}
	
	/**
	 * This method verifies Video call
	 * @throws Exception
	 */
	@Test(priority = 150, enabled = true)
	@Description("Verify Video Call")
	public void Verify_VideoCall_for_UA() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Video Call verification test case Started");
		logStep("****** Video Call verification test case Started");
		hmTab.clickonHomeTab();
		Functions.archive_folder("Charles");
		proxy.clearCharlesSession();
		// navigate to Video tab
		vTab.navigateToVideoTab();
		TestBase.waitForMilliSeconds(10000);
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.verify_Gampad_adcall("Smoke", "Gampad", false);
		System.out.println("****** Waiting for five minutes to get dsx call to override geo ip country for next test");
		logStep("****** Waiting for five minutes to get dsx call to override geo ip country for next test");
		TestBase.waitForMilliSeconds(240000);
	}
	
		
	@Test(priority = 200, enabled = true)
	@Description("Enabling Preconfiguration for CU")
	public void enable_PreConfiguration_for_CU() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Enable Preconfiguration for CU");
		logStep("Enable Preconfiguration for CU");
		proxy.quitCharlesProxy();
		// Ad.closeApp();
		this.configFile = this.rewriteRuleToOverrideGeoIpCountry(CU_CONFIG_FILE_PATH, "CU");
		proxy = new CharlesProxy("localhost", 8111, CU_CONFIG_FILE_PATH);
		proxy.startCharlesProxyWithUI();
		proxy.enableRewriting();
		proxy.startRecording();
		proxy.disableMapLocal();
		// Ad.launchApp();
		Functions.close_launchApp();
		Functions.checkForAppState();
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.checkForAppState();
		Functions.put_Background_launch(15);
		Functions.checkForAppState();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
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
	}
	
	/**
	 * This method verifies Lotame call 
	 * @throws Exception
	 */
	@Test(priority = 201, enabled = true)
	@Description("Lotame Call verification")
	public void Verify_Lotame_Call_for_CU() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** bcp.crwdcntrl.net Call test case Started");
		logStep("****** bcp.crwdcntrl.net Call test case Started");
		Utils.verifyAPICal("Smoke", "Lotame", false);
	}

	/**
	 * This method verifies FACTUAL call
	 * @throws Exception
	 */
	@Test(priority = 202, enabled = true)
	@Description("Factual Call verification")
	public void Verify_LocationWFXTriggers_Call__for_CU() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** location.wfxtriggers.com Call test case Started");
		logStep("location.wfxtriggers.com Call test case Started");
		Utils.verifyAPICal("Smoke", "LocationWFX", false);
	}
	
	/**
	 * This method verifies WFXTriggers call
	 * @throws Exception
	 */
	@Test(priority = 203, enabled = true)
	@Description("WFXTrigger Call verification")
	public void Verify_WFXTriggers_Call_for_CU() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** triggers.wfxtriggers.com Call test case Started");
		logStep("****** triggers.wfxtriggers.com Call test case Started");
		Utils.verifyAPICal("Smoke", "WFXTrigger", false);
	}
	
	/**
	 * This method verifies Amazon call
	 * @throws Exception
	 */
	@Test(priority = 204, enabled = true)
	@Description("Amazon aax call verification")
	public void Verify_Amazon_Call_for_CU() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** amazon-adsystem.com Call test case Started");
		logStep("****** amazon-adsystem.com Call test case Started");
		Utils.verify_Amazon_aax_call("Smoke", "Amazon", false);
	}
	
	/**
	 * This method verifies Criteo Initialization API call
	 * @throws Exception
	 */
	@Test(priority = 205, enabled = true)
	@Description("Verify Criteo SDK inapp v2 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_for_CU() throws Exception {
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
	@Test(priority = 206, enabled = true)
	@Description("Verify Criteo SDK config app call")
	public void Verify_Criteo_SDK_config_app_Call_for_CU() throws Exception {
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
	@Test(priority = 207, enabled = true)
	@Description("Verify NextGen IM Call")
	public void Verify_NextGen_IM_call_for_CU() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** NextGen IM Call verification test case Started");
		logStep("****** NextGen IM Call verification test case Started");
		Utils.verify_Gampad_adcall("Smoke", "Gampad", false);
	}
	
	/**
	 * This method verifies Video call
	 * @throws Exception
	 */
	@Test(priority = 250, enabled = true)
	@Description("Verify Video Call")
	public void Verify_VideoCall_for_CU() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Video Call verification test case Started");
		logStep("****** Video Call verification test case Started");
		hmTab.clickonHomeTab();
		Functions.archive_folder("Charles");
		proxy.clearCharlesSession();
		// navigate to Video tab
		vTab.navigateToVideoTab();
		TestBase.waitForMilliSeconds(10000);
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.verify_Gampad_adcall("Smoke", "Gampad", false);
		System.out.println("****** Waiting for five minutes to get dsx call to override geo ip country for next test");
		logStep("****** Waiting for five minutes to get dsx call to override geo ip country for next test");
		TestBase.waitForMilliSeconds(240000);
	}
	
	@Test(priority = 300, enabled = true)
	@Description("Enabling Preconfiguration for IR")
	public void enable_PreConfiguration_for_IR() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Enable Preconfiguration for IR");
		logStep("Enable Preconfiguration for IR");
		proxy.quitCharlesProxy();
		// Ad.closeApp();
		this.configFile = this.rewriteRuleToOverrideGeoIpCountry(IR_CONFIG_FILE_PATH, "IR");
		proxy = new CharlesProxy("localhost", 8111, IR_CONFIG_FILE_PATH);
		proxy.startCharlesProxyWithUI();
		proxy.enableRewriting();
		proxy.startRecording();
		proxy.disableMapLocal();
		// Ad.launchApp();
		Functions.close_launchApp();
		Functions.checkForAppState();
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.checkForAppState();
		Functions.put_Background_launch(15);
		Functions.checkForAppState();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
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
	}
	
	/**
	 * This method verifies Lotame call 
	 * @throws Exception
	 */
	@Test(priority = 301, enabled = true)
	@Description("Lotame Call verification")
	public void Verify_Lotame_Call_for_IR() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** bcp.crwdcntrl.net Call test case Started");
		logStep("****** bcp.crwdcntrl.net Call test case Started");
		Utils.verifyAPICal("Smoke", "Lotame", false);
	}

	/**
	 * This method verifies FACTUAL call
	 * @throws Exception
	 */
	@Test(priority = 302, enabled = true)
	@Description("Factual Call verification")
	public void Verify_LocationWFXTriggers_Call__for_IR() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** location.wfxtriggers.com Call test case Started");
		logStep("location.wfxtriggers.com Call test case Started");
		Utils.verifyAPICal("Smoke", "LocationWFX", false);
	}
	
	/**
	 * This method verifies WFXTriggers call
	 * @throws Exception
	 */
	@Test(priority = 303, enabled = true)
	@Description("WFXTrigger Call verification")
	public void Verify_WFXTriggers_Call_for_IR() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** triggers.wfxtriggers.com Call test case Started");
		logStep("****** triggers.wfxtriggers.com Call test case Started");
		Utils.verifyAPICal("Smoke", "WFXTrigger", false);
	}
	
	/**
	 * This method verifies Amazon call
	 * @throws Exception
	 */
	@Test(priority = 304, enabled = true)
	@Description("Amazon aax call verification")
	public void Verify_Amazon_Call_for_IR() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** amazon-adsystem.com Call test case Started");
		logStep("****** amazon-adsystem.com Call test case Started");
		Utils.verify_Amazon_aax_call("Smoke", "Amazon", false);
	}
	
	/**
	 * This method verifies Criteo Initialization API call
	 * @throws Exception
	 */
	@Test(priority = 305, enabled = true)
	@Description("Verify Criteo SDK inapp v2 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_for_IR() throws Exception {
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
	@Test(priority = 306, enabled = true)
	@Description("Verify Criteo SDK config app call")
	public void Verify_Criteo_SDK_config_app_Call_for_IR() throws Exception {
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
	@Test(priority = 307, enabled = true)
	@Description("Verify NextGen IM Call")
	public void Verify_NextGen_IM_call_for_IR() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** NextGen IM Call verification test case Started");
		logStep("****** NextGen IM Call verification test case Started");
		Utils.verify_Gampad_adcall("Smoke", "Gampad", false);
	}
	
	/**
	 * This method verifies Video call
	 * @throws Exception
	 */
	@Test(priority = 350, enabled = true)
	@Description("Verify Video Call")
	public void Verify_VideoCall_for_IR() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Video Call verification test case Started");
		logStep("****** Video Call verification test case Started");
		hmTab.clickonHomeTab();
		Functions.archive_folder("Charles");
		proxy.clearCharlesSession();
		// navigate to Video tab
		vTab.navigateToVideoTab();
		TestBase.waitForMilliSeconds(10000);
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.verify_Gampad_adcall("Smoke", "Gampad", false);
		System.out.println("****** Waiting for five minutes to get dsx call to override geo ip country for next test");
		logStep("****** Waiting for five minutes to get dsx call to override geo ip country for next test");
		TestBase.waitForMilliSeconds(240000);
	}
	
	@Test(priority = 400, enabled = true)
	@Description("Enabling Preconfiguration for KP")
	public void enable_PreConfiguration_for_KP() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Enable Preconfiguration for KP");
		logStep("Enable Preconfiguration for KP");
		proxy.quitCharlesProxy();
		// Ad.closeApp();
		this.configFile = this.rewriteRuleToOverrideGeoIpCountry(KP_CONFIG_FILE_PATH, "KP");
		proxy = new CharlesProxy("localhost", 8111, KP_CONFIG_FILE_PATH);
		proxy.startCharlesProxyWithUI();
		proxy.enableRewriting();
		proxy.startRecording();
		proxy.disableMapLocal();
		// Ad.launchApp();
		Functions.close_launchApp();
		Functions.checkForAppState();
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.checkForAppState();
		Functions.put_Background_launch(15);
		Functions.checkForAppState();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
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
	}
	
	/**
	 * This method verifies Lotame call 
	 * @throws Exception
	 */
	@Test(priority = 401, enabled = true)
	@Description("Lotame Call verification")
	public void Verify_Lotame_Call_for_KP() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** bcp.crwdcntrl.net Call test case Started");
		logStep("****** bcp.crwdcntrl.net Call test case Started");
		Utils.verifyAPICal("Smoke", "Lotame", false);
	}

	/**
	 * This method verifies FACTUAL call
	 * @throws Exception
	 */
	@Test(priority = 402, enabled = true)
	@Description("Factual Call verification")
	public void Verify_LocationWFXTriggers_Call__for_KP() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** location.wfxtriggers.com Call test case Started");
		logStep("location.wfxtriggers.com Call test case Started");
		Utils.verifyAPICal("Smoke", "LocationWFX", false);
	}
	
	/**
	 * This method verifies WFXTriggers call
	 * @throws Exception
	 */
	@Test(priority = 403, enabled = true)
	@Description("WFXTrigger Call verification")
	public void Verify_WFXTriggers_Call_for_KP() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** triggers.wfxtriggers.com Call test case Started");
		logStep("****** triggers.wfxtriggers.com Call test case Started");
		Utils.verifyAPICal("Smoke", "WFXTrigger", false);
	}
	
	/**
	 * This method verifies Amazon call
	 * @throws Exception
	 */
	@Test(priority = 404, enabled = true)
	@Description("Amazon aax call verification")
	public void Verify_Amazon_Call_for_KP() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** amazon-adsystem.com Call test case Started");
		logStep("****** amazon-adsystem.com Call test case Started");
		Utils.verify_Amazon_aax_call("Smoke", "Amazon", false);
	}
	
	/**
	 * This method verifies Criteo Initialization API call
	 * @throws Exception
	 */
	@Test(priority = 405, enabled = true)
	@Description("Verify Criteo SDK inapp v2 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_for_KP() throws Exception {
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
	@Test(priority = 406, enabled = true)
	@Description("Verify Criteo SDK config app call")
	public void Verify_Criteo_SDK_config_app_Call_for_KP() throws Exception {
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
	@Test(priority = 407, enabled = true)
	@Description("Verify NextGen IM Call")
	public void Verify_NextGen_IM_call_for_KP() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** NextGen IM Call verification test case Started");
		logStep("****** NextGen IM Call verification test case Started");
		Utils.verify_Gampad_adcall("Smoke", "Gampad", false);
	}
	
	/**
	 * This method verifies Video call
	 * @throws Exception
	 */
	@Test(priority = 450, enabled = true)
	@Description("Verify Video Call")
	public void Verify_VideoCall_for_KP() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Video Call verification test case Started");
		logStep("****** Video Call verification test case Started");
		hmTab.clickonHomeTab();
		Functions.archive_folder("Charles");
		proxy.clearCharlesSession();
		// navigate to Video tab
		vTab.navigateToVideoTab();
		TestBase.waitForMilliSeconds(10000);
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.verify_Gampad_adcall("Smoke", "Gampad", false);
		System.out.println("****** Waiting for five minutes to get dsx call to override geo ip country for next test");
		logStep("****** Waiting for five minutes to get dsx call to override geo ip country for next test");
		TestBase.waitForMilliSeconds(240000);
	}
	
	@Test(priority = 500, enabled = true)
	@Description("Enabling Preconfiguration for SD")
	public void enable_PreConfiguration_for_SD() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Enable Preconfiguration for SD");
		logStep("Enable Preconfiguration for SD");
		proxy.quitCharlesProxy();
		// Ad.closeApp();
		this.configFile = this.rewriteRuleToOverrideGeoIpCountry(SD_CONFIG_FILE_PATH, "SD");
		proxy = new CharlesProxy("localhost", 8111, SD_CONFIG_FILE_PATH);
		proxy.startCharlesProxyWithUI();
		proxy.enableRewriting();
		proxy.startRecording();
		proxy.disableMapLocal();
		// Ad.launchApp();
		Functions.close_launchApp();
		Functions.checkForAppState();
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.checkForAppState();
		Functions.put_Background_launch(15);
		Functions.checkForAppState();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
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
	}
	
	/**
	 * This method verifies Lotame call 
	 * @throws Exception
	 */
	@Test(priority = 501, enabled = true)
	@Description("Lotame Call verification")
	public void Verify_Lotame_Call_for_SD() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** bcp.crwdcntrl.net Call test case Started");
		logStep("****** bcp.crwdcntrl.net Call test case Started");
		Utils.verifyAPICal("Smoke", "Lotame", false);
	}

	/**
	 * This method verifies FACTUAL call
	 * @throws Exception
	 */
	@Test(priority = 502, enabled = true)
	@Description("Factual Call verification")
	public void Verify_LocationWFXTriggers_Call__for_SD() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** location.wfxtriggers.com Call test case Started");
		logStep("location.wfxtriggers.com Call test case Started");
		Utils.verifyAPICal("Smoke", "LocationWFX", false);
	}
	
	/**
	 * This method verifies WFXTriggers call
	 * @throws Exception
	 */
	@Test(priority = 503, enabled = true)
	@Description("WFXTrigger Call verification")
	public void Verify_WFXTriggers_Call_for_SD() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** triggers.wfxtriggers.com Call test case Started");
		logStep("****** triggers.wfxtriggers.com Call test case Started");
		Utils.verifyAPICal("Smoke", "WFXTrigger", false);
	}
	
	/**
	 * This method verifies Amazon call
	 * @throws Exception
	 */
	@Test(priority = 504, enabled = true)
	@Description("Amazon aax call verification")
	public void Verify_Amazon_Call_for_SD() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** amazon-adsystem.com Call test case Started");
		logStep("****** amazon-adsystem.com Call test case Started");
		Utils.verify_Amazon_aax_call("Smoke", "Amazon", false);
	}
	
	/**
	 * This method verifies Criteo Initialization API call
	 * @throws Exception
	 */
	@Test(priority = 505, enabled = true)
	@Description("Verify Criteo SDK inapp v2 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_for_SD() throws Exception {
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
	@Test(priority = 506, enabled = true)
	@Description("Verify Criteo SDK config app call")
	public void Verify_Criteo_SDK_config_app_Call_for_SD() throws Exception {
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
	@Test(priority = 507, enabled = true)
	@Description("Verify NextGen IM Call")
	public void Verify_NextGen_IM_call_for_SD() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** NextGen IM Call verification test case Started");
		logStep("****** NextGen IM Call verification test case Started");
		Utils.verify_Gampad_adcall("Smoke", "Gampad", false);
	}
	
	/**
	 * This method verifies Video call
	 * @throws Exception
	 */
	@Test(priority = 550, enabled = true)
	@Description("Verify Video Call")
	public void Verify_VideoCall_for_SD() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Video Call verification test case Started");
		logStep("****** Video Call verification test case Started");
		hmTab.clickonHomeTab();
		Functions.archive_folder("Charles");
		proxy.clearCharlesSession();
		// navigate to Video tab
		vTab.navigateToVideoTab();
		TestBase.waitForMilliSeconds(10000);
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.verify_Gampad_adcall("Smoke", "Gampad", false);
		System.out.println("****** Waiting for five minutes to get dsx call to override geo ip country for next test");
		logStep("****** Waiting for five minutes to get dsx call to override geo ip country for next test");
		TestBase.waitForMilliSeconds(240000);
	}
	
	@Test(priority = 600, enabled = true)
	@Description("Enabling Preconfiguration for SY")
	public void enable_PreConfiguration_for_SY() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Enable Preconfiguration for SY");
		logStep("Enable Preconfiguration for SY");
		proxy.quitCharlesProxy();
		// Ad.closeApp();
		this.configFile = this.rewriteRuleToOverrideGeoIpCountry(SY_CONFIG_FILE_PATH, "SY");
		proxy = new CharlesProxy("localhost", 8111, SY_CONFIG_FILE_PATH);
		proxy.startCharlesProxyWithUI();
		proxy.enableRewriting();
		proxy.startRecording();
		proxy.disableMapLocal();
		// Ad.launchApp();
		Functions.close_launchApp();
		Functions.checkForAppState();
		proxy.clearCharlesSession();
		Functions.close_launchApp();
		Functions.checkForAppState();
		Functions.put_Background_launch(15);
		Functions.checkForAppState();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
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
	}
	
	/**
	 * This method verifies Lotame call 
	 * @throws Exception
	 */
	@Test(priority = 601, enabled = true)
	@Description("Lotame Call verification")
	public void Verify_Lotame_Call_for_SY() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** bcp.crwdcntrl.net Call test case Started");
		logStep("****** bcp.crwdcntrl.net Call test case Started");
		Utils.verifyAPICal("Smoke", "Lotame", false);
	}

	/**
	 * This method verifies FACTUAL call
	 * @throws Exception
	 */
	@Test(priority = 602, enabled = true)
	@Description("Factual Call verification")
	public void Verify_LocationWFXTriggers_Call__for_SY() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** location.wfxtriggers.com Call test case Started");
		logStep("location.wfxtriggers.com Call test case Started");
		Utils.verifyAPICal("Smoke", "LocationWFX", false);
	}
	
	/**
	 * This method verifies WFXTriggers call
	 * @throws Exception
	 */
	@Test(priority = 603, enabled = true)
	@Description("WFXTrigger Call verification")
	public void Verify_WFXTriggers_Call_for_SY() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** triggers.wfxtriggers.com Call test case Started");
		logStep("****** triggers.wfxtriggers.com Call test case Started");
		Utils.verifyAPICal("Smoke", "WFXTrigger", false);
	}
	
	/**
	 * This method verifies Amazon call
	 * @throws Exception
	 */
	@Test(priority = 604, enabled = true)
	@Description("Amazon aax call verification")
	public void Verify_Amazon_Call_for_SY() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** amazon-adsystem.com Call test case Started");
		logStep("****** amazon-adsystem.com Call test case Started");
		Utils.verify_Amazon_aax_call("Smoke", "Amazon", false);
	}
	
	/**
	 * This method verifies Criteo Initialization API call
	 * @throws Exception
	 */
	@Test(priority = 605, enabled = true)
	@Description("Verify Criteo SDK inapp v2 call")
	public void Verify_Criteo_SDK_inapp_v2_Call_for_SY() throws Exception {
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
	@Test(priority = 606, enabled = true)
	@Description("Verify Criteo SDK config app call")
	public void Verify_Criteo_SDK_config_app_Call_for_SY() throws Exception {
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
	@Test(priority = 607, enabled = true)
	@Description("Verify NextGen IM Call")
	public void Verify_NextGen_IM_call_for_SY() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** NextGen IM Call verification test case Started");
		logStep("****** NextGen IM Call verification test case Started");
		Utils.verify_Gampad_adcall("Smoke", "Gampad", false);
	}
	
	/**
	 * This method verifies Video call
	 * @throws Exception
	 */
	@Test(priority = 650, enabled = true)
	@Description("Verify Video Call")
	public void Verify_VideoCall_for_SY() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Video Call verification test case Started");
		logStep("****** Video Call verification test case Started");
		hmTab.clickonHomeTab();
		Functions.archive_folder("Charles");
		proxy.clearCharlesSession();
		// navigate to Video tab
		vTab.navigateToVideoTab();
		TestBase.waitForMilliSeconds(10000);
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		Utils.verify_Gampad_adcall("Smoke", "Gampad", false);
		
	}
	

}

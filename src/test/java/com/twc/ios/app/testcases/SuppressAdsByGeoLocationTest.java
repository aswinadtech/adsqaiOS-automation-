package com.twc.ios.app.testcases;

import org.openqa.selenium.html5.Location;
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

public class SuppressAdsByGeoLocationTest extends TwcIosBaseTest {
	// private static final MobileAutomationLogger LOGGER = new
	// MobileAutomationLogger(GDPRFunctionalEULaunchTests.class);
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

	@BeforeClass(alwaysRun = true)
	@Description("BeforeClass")
	public void beforeClass() {
		System.out.println("****** Suppress Ads By Geo Location Test Started");
		logStep("****** Suppress Ads By Geo Location Test Started");
		//this.configFile = this.rewriteRuleToEnableGDPR(CONFIG_FILE_PATH);
		this.configFile = this.rewriteRuleToOverrideGeoIpCountry(CONFIG_FILE_PATH, "US");
		proxy = new CharlesProxy("localhost", 8111, CONFIG_FILE_PATH);

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

		System.out.println("****** Suppress Ads By Geo Location Test Ended");
		logStep("****** Suppress Ads By Geo Location Test Ended");
		System.out.println("==============================================");
	}

	/*
	 * @Test(priority = -1)
	 * 
	 * @Description("Download and Install App from Firebase") public void
	 * downloadAndInstallApp_for_GDPR() throws Exception {
	 * System.out.println("==============================================");
	 * System.out.println("****** downloadAndInstallApp Test Started");
	 * logStep("****** downloadAndInstallApp Test Started"); // Preconditions
	 * Functions.capabilities(); Functions.Appium_Autostart();
	 * Utils.getCurrentMacIPAddressAndSetiPhoneProxy(false);
	 * Functions.uninstallApp();
	 * Functions.launchFirebaseInSafariAndInstallApp("RC");
	 * //Functions.launchFirebaseInSafariAndInstallApp();
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
		/* Preconditions
		 * as we are not uninstalling and installing, this step not required
		Utils.getCurrentMacIPAddressAndSetiPhoneProxy(true, true);*/
		Functions.archive_folder("Charles");
		Functions.launchtheApp("true");
		System.out.println("App launched ");
		logStep("App launched ");
		proxy.getXml();
		Functions.archive_folder("Charles");
		proxy.clearCharlesSession();
		Ad.setLocation(new Location(48.379433, 31.16558, 15));
		//Ad.setLocation(new Location(20.593684, 78.96288, 15));
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
	public void Verify_LocationWFXTriggers_Call_for_UA() throws Exception {
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
	@Description("Deriving Video Call")
	public void derive_VideoCall_IU_for_UA() throws Exception {
		
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

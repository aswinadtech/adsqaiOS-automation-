package com.twc.ios.app.testcases;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import java.io.File;
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
import com.twc.ios.app.pages.AndroidAddressScreen;
import com.twc.ios.app.pages.AndroidAirQualityCardContentScreen;
import com.twc.ios.app.pages.AndroidAirQualityCardScreen;
import com.twc.ios.app.pages.AndroidDailyNavTab;
import com.twc.ios.app.pages.AndroidFTLScreens;
import com.twc.ios.app.pages.AndroidFeedOneCardScreen;
import com.twc.ios.app.pages.AndroidFeedTwoCardScreen;
import com.twc.ios.app.pages.AndroidHomeNavTab;
import com.twc.ios.app.pages.AndroidHourlyNavTab;
import com.twc.ios.app.pages.AndroidLifeStyleCardScreen;
import com.twc.ios.app.pages.AndroidLogInScreen;
import com.twc.ios.app.pages.AndroidNewsCardScreen;
import com.twc.ios.app.pages.AndroidRadarNavTab;
import com.twc.ios.app.pages.AndroidSeasonalHubCardScreen;
import com.twc.ios.app.pages.AndroidSettingsScreen;
import com.twc.ios.app.pages.AndroidVideoNavTab;
import com.twc.ios.app.pages.DailyNavTab;
import com.twc.ios.app.pages.FTLScreens;
import com.twc.ios.app.pages.FeedOneCardScreen;
import com.twc.ios.app.pages.HomeNavTab;
import com.twc.ios.app.pages.HourlyNavTab;
import com.twc.ios.app.pages.LifeStyleCardScreen;
import com.twc.ios.app.pages.LogInScreen;
import com.twc.ios.app.pages.NewsCardScreen;
import com.twc.ios.app.pages.PlanningCardScreen;
import com.twc.ios.app.pages.RadarNavTab;
import com.twc.ios.app.pages.SeasonalHubCardScreen;
import com.twc.ios.app.pages.SettingsScreen;
import com.twc.ios.app.pages.VideoNavTab;

@Listeners(value = com.twc.ios.app.general.MyTestListenerAdapter.class)
public class AndroidAdsMonitoringTest extends TwcIosBaseTest {

	//private static final String CONFIG_FILE_PATH = "charles_common.config";
	private static final String CONFIG_FILE_PATH = "enableUSA.config";

	// public static CharlesProxy proxy;
	public File configFile;
	AndroidHourlyNavTab hrTab;
	AndroidDailyNavTab dTab;
	AndroidHomeNavTab hmTab;
	AndroidRadarNavTab rTab;
	AndroidVideoNavTab vTab;
	AndroidAddressScreen addrScreen;
	AndroidSeasonalHubCardScreen sScreen;
	AndroidSettingsScreen stScreen;
	AndroidLogInScreen loginScreen;
	AndroidFTLScreens ftlScreens;
	AndroidAirQualityCardScreen aqCardScreen;
	AndroidAirQualityCardContentScreen aqCardContentScreen;
	AndroidFeedOneCardScreen fOneCardScreen;
	AndroidFeedTwoCardScreen fTwoCardScreen;
	AndroidNewsCardScreen nCardScreen;
	AndroidLifeStyleCardScreen lCardScreen;
	
	
	@BeforeClass(alwaysRun = true)
	@Description("BeforeClass")
	public void beforeClass() {
		System.out.println("****** Ads Monitoring Test Started");
		logStep("****** Ads Monitoring Test Started");
		//this.configFile = this.charlesGeneralConfigFile(CONFIG_FILE_PATH);
		//this.configFile = this.rewriteRuleToEnableUSA(CONFIG_FILE_PATH);
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
		//Ad.launchApp();
		//stScreen.getAppVersion();
		Functions.close_launchAppAndroid();
		stScreen.gettingApkVersion_UPSX();
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

		System.out.println("****** Ads Monitoring Test Ended");
		logStep("****** Ads Monitoring Test Ended");
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
		// Enable rewriting to USA on Charles because Tunnel Bear not working in India
		proxy.enableRewriting();
		proxy.startRecording();
		proxy.clearCharlesSession();
		Functions.archive_folder("Charles");
		Functions.launchtheAndroidApp();
		System.out.println("App launched ");
		logStep("App launched ");
		
		Functions.archive_folder("Charles");
		proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		proxy.clearCharlesSession();
		hrTab = new AndroidHourlyNavTab(Ad);
		dTab = new AndroidDailyNavTab(Ad);
		hmTab = new AndroidHomeNavTab(Ad);
		rTab = new AndroidRadarNavTab(Ad);
		vTab = new AndroidVideoNavTab(Ad);
		addrScreen = new AndroidAddressScreen(Ad);
	
		sScreen = new AndroidSeasonalHubCardScreen(Ad);
		stScreen = new AndroidSettingsScreen(Ad);
		loginScreen = new AndroidLogInScreen(Ad);
		ftlScreens = new AndroidFTLScreens(Ad);
		aqCardScreen = new AndroidAirQualityCardScreen(Ad);
		aqCardContentScreen = new AndroidAirQualityCardContentScreen(Ad);
		fOneCardScreen = new AndroidFeedOneCardScreen(Ad);
		fTwoCardScreen = new AndroidFeedTwoCardScreen(Ad);
		nCardScreen = new AndroidNewsCardScreen(Ad);
		lCardScreen = new AndroidLifeStyleCardScreen(Ad);
		
	}
	
	
	/**
	 * Handle Interstitial Ad
	 */
	@Test(priority = 100, enabled = true)
	@Description("Handle Interstitial Ad")
	public void Handle_Interstitial_Ad() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** Handle Interstitial Ad test started");
		logStep("Handle Interstitial Ad test started");
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
	
		Functions.close_launchAppAndroid();
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		
		/*
		 * Handling Interstitial ad ahead of navigating to all cards
		 */
		
		//Utils.verifytinterstitialAdcallBeforeClearSession("Smoke", "Hourly");
		hrTab.navigateToHourlyTab();
		hrTab.navigateToHourlyTabAndHandleInterstitialAd();
		Functions.close_launchAppAndroid();
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		proxy.clearCharlesSession();
		try {
			addrScreen.enter_requiredLocation("30303", proxy);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//addrScreen.enternewAddress(false, "Atlanta, Georgia");
		TestBase.waitForMilliSeconds(20000);
		
	}

	@Test(priority = 101, enabled = true)
	@Description("Verify Integrated Marquee Ad")
	public void Verify_Integrated_Marquee_Ad() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================Integrated Marquee Ad====================");

		System.out.println("****** Integrated Marquee Ad validation Started");
		logStep("****** Integrated Marquee Ad validation Started");
		
		
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		hmTab.navigateToHomeTab_toGetInterStitialAd();
				
		TestBase.waitForMilliSeconds(10000);
		hmTab.assertIntegratedMarqueeAd();
	}
	
	@Test(priority = 102, enabled = true)
	@Description("Verify HomeScreen Sticky Ad")
	public void Verify_HomeScreen_Sticky_Ad() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================HomeScreen Sticky Ad====================");

		System.out.println("****** HomeScreen Sticky Ad validation Started");
		logStep("****** HomeScreen Sticky Ad validation Started");
		
		
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		hmTab.navigateToHomeTab_toGetInterStitialAd();
				
		TestBase.waitForMilliSeconds(10000);
		hmTab.assertStickyAd();
	}
	
	@Test(priority = 103, enabled = true)
	@Description("Verify Ad on Hourly NavTab")
	public void Verify_Ad_On_Hourly_NavTab() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================Hourly Nav Tab Ad====================");

		System.out.println("****** Hourly Nav Tab Ad validation Started");
		logStep("****** Hourly Nav Tab Ad validation Started");
		
		
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		
		// navigate to Hourly tab
		hrTab.navigateToHourlyTab();
		TestBase.waitForMilliSeconds(10000);
		hrTab.assertAdOnHourlyNavTab();
	}

	@Test(priority = 104, enabled = true)
	@Description("Verify Ad on Daily NavTab")
	public void Verify_Ad_On_Daily_NavTab() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================Daily Nav Tab Ad====================");

		System.out.println("****** Daily Nav Tab Ad validation Started");
		logStep("****** Daily Nav Tab Ad validation Started");
		
		
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		
		// navigate to Daily tab
		dTab.navigateToDailyTab();
		TestBase.waitForMilliSeconds(10000);
		dTab.assertAdOnDailyNavTab();
	}
	
	@Test(priority = 105, enabled = true)
	@Description("Verify Ad on Radar NavTab")
	public void Verify_Ad_On_Radar_NavTab() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================Radar Nav Tab Ad====================");

		System.out.println("****** Radar Nav Tab Ad validation Started");
		logStep("****** Radar Nav Tab Ad validation Started");
		
		
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		
		// navigate to Radar tab
		rTab.navigateToRadarTab();
		TestBase.waitForMilliSeconds(10000);
		rTab.assertAdOnRadarNavTab();
	}
	
	@Test(priority = 106, enabled = true)
	@Description("Verify Ad on Feed1 Card")
	public void Verify_Ad_On_Feed1_Card() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================Feed1 Card Ad====================");

		System.out.println("****** Feed1 Card Ad validation Started");
		logStep("****** Feed1 Card Ad validation Started");
		
		
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		
		// navigate to Feed1 Card
		fOneCardScreen.scrollToFeedOneCard();
		TestBase.waitForMilliSeconds(10000);
		fOneCardScreen.assertAdOnFeed1Card();
	}
	
	@Test(priority = 107, enabled = true)
	@Description("Verify Ad on Feed2 Card")
	public void Verify_Ad_On_Feed2_Card() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================Feed2 Card Ad====================");

		System.out.println("****** Feed2 Card Ad validation Started");
		logStep("****** Feed2 Card Ad validation Started");
		
		
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		
		// navigate to Feed2 Card
		fTwoCardScreen.scrollToFeedTwoCard();
		TestBase.waitForMilliSeconds(10000);
		fTwoCardScreen.assertAdOnFeed2Card();
	}
	
	@Test(priority = 108, enabled = true)
	@Description("Verify Ad on AQ Details Page")
	public void Verify_Ad_On_AQ_Details_Page() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================AQ Details Page Ad====================");

		System.out.println("****** AQ Details Page Ad validation Started");
		logStep("****** AQ Details Page Ad validation Started");
		
		
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		
		// navigate to AQ Card
		//Utils.navigateTofeedCard("aq", false, false);
		try {
			aqCardScreen.scrollToAQCard();
			aqCardScreen.navigateToAirQualityCardContentPageAndDontHandleInterstitials();
			TestBase.waitForMilliSeconds(10000);
			aqCardContentScreen.assertAdOnAQDetailsPage();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			navigateBackToFeedCardAndroid();
		}
		
		
	}
	
	@Test(priority = 109, enabled = true)
	@Description("Verify Ad on News Details Page")
	public void Verify_Ad_On_News_Details_Page() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================News Details Page Ad====================");

		System.out.println("****** News Details Page Ad validation Started");
		logStep("****** News Details Page Ad validation Started");
		
		hrTab.navigateToHourlyTab();
		TestBase.waitForMilliSeconds(2000);
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		
		// navigate to News Card
		//Utils.navigateTofeedCard("aq", false, false);
		nCardScreen.scrollToNewsCard();
		nCardScreen.navigateToNewsCardContentPageAndDontHandleInterstitials();
		TestBase.waitForMilliSeconds(10000);
		nCardScreen.assertAdOnNewsDetailsPage();
		navigateBackToFeedCardAndroid();
	}
	
	@Test(priority = 110, enabled = true)
	@Description("Verify Ad on Health & Activities Index Pages")
	public void Verify_Ad_On_lifeSyle_Index_Pages() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================Health & Activities Index Pages Ad====================");

		System.out.println("****** Health & Activities Index Pages Ad validation Started");
		logStep("****** Health & Activities Index Pages Ad validation Started");
		
		hrTab.navigateToHourlyTab();
		TestBase.waitForMilliSeconds(2000);
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		
		// navigate to Health & Activities Card
		//Utils.navigateTofeedCard("lifestyle", false, false);
		lCardScreen.scrollToLifeStyleCard();
		lCardScreen.navigateToLifeStyleCardIndexAndAssertAd("Flu");
		lCardScreen.navigateToLifeStyleCardIndexAndAssertAd("Allergy");
		
	}

}

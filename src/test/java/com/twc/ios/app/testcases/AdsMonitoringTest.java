package com.twc.ios.app.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
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
import com.twc.ios.app.pages.AirQualityCardContentScreen;
import com.twc.ios.app.pages.AirQualityCardScreen;
import com.twc.ios.app.pages.DailyNavTab;
import com.twc.ios.app.pages.FTLScreens;
import com.twc.ios.app.pages.FeedOneCardScreen;
import com.twc.ios.app.pages.FeedTwoCardScreen;
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
public class AdsMonitoringTest extends TwcIosBaseTest {

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
	AirQualityCardContentScreen aqCardContentScreen;
	FeedOneCardScreen fOneCardScreen;
	FeedTwoCardScreen fTwoCardScreen;
	NewsCardScreen nCardScreen;
	LifeStyleCardScreen lCardScreen;
	
	@BeforeClass(alwaysRun = true)
	@Description("BeforeClass")
	public void beforeClass() {
		System.out.println("****** Ads Monitoring Test Started");
		logStep("****** Ads Monitoring Test Started");
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
		aqCardContentScreen = new AirQualityCardContentScreen(Ad);
		fOneCardScreen = new FeedOneCardScreen(Ad);
		fTwoCardScreen = new FeedTwoCardScreen(Ad);
		nCardScreen = new NewsCardScreen(Ad);
		lCardScreen = new LifeStyleCardScreen(Ad);
		
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
		Functions.close_launchApp();
		//stScreen.select_Airlock_Branch("IOSAUTOMATION03");
		//stScreen.select_Airlock_UserGroup("IOSAUTOMATION");
		//proxy.clearCharlesSession();
		//Functions.close_launchApp();
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		
		/*
		 * Handling Interstitial ad ahead of navigating to all cards
		 */
		
		//Utils.verifytinterstitialAdcallBeforeClearSession("Smoke", "Hourly");

		hrTab.navigateToHourlyTabAndHandleInterstitialAd();
		Functions.close_launchApp();
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		proxy.clearCharlesSession();
		
		addrScreen.enternewAddress(false, "Atlanta, Georgia");
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
	
	@Test(priority = 104, enabled = true)
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
	
	@Test(priority = 105, enabled = true)
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
	
	@Test(priority = 106, enabled = true)
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
	
	@Test(priority = 107, enabled = true)
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
			navigateBackToFeedCard();
		}
		
		
	}
	
	@Test(priority = 108, enabled = true)
	@Description("Verify Ad on News Details Page")
	public void Verify_Ad_On_News_Details_Page() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================News Details Page Ad====================");

		System.out.println("****** News Details Page Ad validation Started");
		logStep("****** News Details Page Ad validation Started");
		
		
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		
		// navigate to News Card
		//Utils.navigateTofeedCard("aq", false, false);
		nCardScreen.scrollToNewsCard();
		nCardScreen.navigateToNewsCardContentPageAndDontHandleInterstitials();
		TestBase.waitForMilliSeconds(10000);
		nCardScreen.assertAdOnNewsDetailsPage();
		navigateBackToFeedCard();
	}
	
	@Test(priority = 109, enabled = true)
	@Description("Verify Ad on Health & Activities Index Pages")
	public void Verify_Ad_On_lifeSyle_Index_Pages() throws Exception {
		System.out.println("==============================================");
		System.out.println("===========================Health & Activities Index Pages Ad====================");

		System.out.println("****** Health & Activities Index Pages Ad validation Started");
		logStep("****** Health & Activities Index Pages Ad validation Started");
		
		
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		hmTab.navigateToHomeTab_toGetInterStitialAd();
		
		// navigate to Health & Activities Card
		//Utils.navigateTofeedCard("lifestyle", false, false);
		lCardScreen.scrollToLifeStyleCard();
		lCardScreen.navigateToLifeStyleCardIndexAndAssertAd("Flu");
		lCardScreen.navigateToLifeStyleCardIndexAndAssertAd("Allergy");
		
	}
	
	
	

}

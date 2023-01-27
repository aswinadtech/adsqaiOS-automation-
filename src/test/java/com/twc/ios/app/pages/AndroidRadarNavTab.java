package com.twc.ios.app.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.twc.ios.app.charlesfunctions.CharlesProxy;
import com.twc.ios.app.functions.Functions;
import com.twc.ios.app.general.Driver;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.Utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;

public class AndroidRadarNavTab extends Utils{
	AppiumDriver<MobileElement> Ad;
	
	String radarTab_AccessibilityId = "Radar Tab";
	String closeLight_AccessibilityId = "close light";
	String radarTabStickyTestAd_Xpath = "(//android.widget.FrameLayout[@resource-id=\"com.weather.Weather:id/ad_view_holder\"]//android.webkit.WebView)[1]";
	String radarTabStickyTestAdContainer_Id = "com.weather.Weather:id/ad_view_holder";
	String advertisementOnRadarTab_Xpath = "//android.widget.LinearLayout[@resource-id='com.weather.Weather:id/map_ad_space']/android.widget.FrameLayout[@resource-id='com.weather.Weather:id/ad_view_holder']//android.webkit.WebView";

	By byRadarNavTab = MobileBy.AccessibilityId(radarTab_AccessibilityId);
	By byCloseLight = MobileBy.AccessibilityId(closeLight_AccessibilityId);
	By byRadarTabStickyTestAd = MobileBy.xpath(radarTabStickyTestAd_Xpath);
	By byRadarTabStickyTestAdContainer = MobileBy.id(radarTabStickyTestAdContainer_Id);
	By byAdvertisementOnRadarTab = MobileBy.xpath(advertisementOnRadarTab_Xpath);

	MobileElement radarNavTab = null;
	MobileElement closeLight = null;
	MobileElement radarTabStickyTestAd = null;
	MobileElement radarTabStickyTestAdContainer = null;
	MobileElement advertisementOnRadarTab = null;

	public AndroidRadarNavTab(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigate To Radar Tab")
	public void navigateToRadarTab() throws Exception {
		try {
			radarNavTab = Ad.findElement(byRadarNavTab);
			TestBase.clickOnElement(byRadarNavTab, radarNavTab, "Radar Nav Tab");
			TestBase.waitForMilliSeconds(10000);
			System.out.println("Navigated to Radar tab ");
			logStep("Navigated to Radar tab");
			attachScreen();
		/*	try {
				closeLight = Ad.findElement(byCloseLight);
				TestBase.clickOnElement(byCloseLight, closeLight, "Close Light icon");
				TestBase.waitForMilliSeconds(4000);
			} catch (Exception e) {
				System.out.println("Tooltip not displayed on map details page");
				logStep("Tooltip not displayed on map details page");
			}*/

		} catch (Exception e) {
			System.out.println("Radar tab not displayed");
			logStep("Radar tab not displayed");
			attachScreen();
		}

	}
	
	@Step("Navigate to Radar tab and Handle Exit interstitial")
	public void navigateToRadarTabAndHandleInterstitialAd() throws Exception {
		AndroidHomeNavTab hmTab = new AndroidHomeNavTab(Ad);
		navigateToRadarTab();
		
		CharlesProxy.proxy.stopRecording();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		if (interStitialAdcallSuccessful) {
			/**
			 * Since Radar has Exit Interstitial, handling it once navigated to Radar Tab and click on Home Tab
			 */
			hmTab.clickonHomeTab();
			handle_Interstitial_AdAndroid();
		} else {
			CharlesProxy.proxy.getXml();
			Utils.createXMLFileForCharlesSessionFile();
			if (Utils.isInterStitialAdCalExists("Smoke", "Map")) {

				if (Utils.isInterstitialCall_hasResponse("Smoke", "Map")) {
					if (!interStitialDisplayed) {
						/**
						 * Since Radar has Exit Interstitial, handling it once navigated to Radar Tab and click on Home Tab
						 */
						hmTab.clickonHomeTab();
						handle_Interstitial_AdAndroid();
					} else {
						System.out.println("Interstitial Ad is already handled, hence not handling again");
						logStep("Interstitial Ad is already handled, hence not handling again");

					}
				}
			}
		}
		CharlesProxy.proxy.startRecording();
		Functions.delete_folder("Charles");	

	}
	
	@Step("Verify Tapability of Sticky Test Ad On Radar Nav Tab")
	public void verifyTapabilityOfTestAdOnRadarNavTab() {
			try {
				radarTabStickyTestAd = Ad.findElement(byRadarTabStickyTestAd);
				TestBase.waitForMilliSeconds(2000);
				TestBase.clickOnElement(byRadarTabStickyTestAd, radarTabStickyTestAd, "Radar Nav Tab Sticky Test Ad");
				
				TestBase.waitForMilliSeconds(10000);
				if (TestBase.isElementExists(byRadarTabStickyTestAd)) {
					radarTabStickyTestAdContainer = Ad.findElement(byRadarTabStickyTestAdContainer);
					TestBase.waitForMilliSeconds(2000);
					TestBase.clickOnElement(byRadarTabStickyTestAdContainer, radarTabStickyTestAdContainer, "Radar Nav Tab Sticky Test Ad Container");
				}
			
				//Functions.checkForAppState();
				Assert.assertFalse(TestBase.isElementExists(byRadarTabStickyTestAd));
			} catch (Exception e) {
				try {
					radarTabStickyTestAd = Ad.findElement(byRadarTabStickyTestAd);
					TestBase.waitForMilliSeconds(2000);
					TestBase.clickOnElement(byRadarTabStickyTestAd, radarTabStickyTestAd, "Radar Nav Tab Sticky Test Ad");
					TestBase.waitForMilliSeconds(10000);
					if (TestBase.isElementExists(byRadarTabStickyTestAd)) {
						radarTabStickyTestAdContainer = Ad.findElement(byRadarTabStickyTestAdContainer);
						TestBase.waitForMilliSeconds(2000);
						TestBase.clickOnElement(byRadarTabStickyTestAdContainer, radarTabStickyTestAdContainer, "Radar Nav Tab Sticky Test Ad Container");
					}
					
					//Functions.checkForAppState();
					Assert.assertFalse(TestBase.isElementExists(byRadarTabStickyTestAd));
				} catch (Exception e1) {
					
					System.out.println("Radar Nav Tab Test Ad is Not displayed");
					logStep("Radar Nav Tab Test Ad is Not displayed");
					Assert.fail("Radar Nav Tab Test Ad is Not displayed");
				}
			} finally {
				attachScreen();
			}
	}
	
	@Step("Assert Ad On Radar Nav Tab")
	public void assertAdOnRadarNavTab() {
		try {
			advertisementOnRadarTab = Ad.findElement(byAdvertisementOnRadarTab);
			System.out.println("Radar Nav Tab Ad displayed");
			logStep("Radar Nav Tab Ad displayed");
		} catch (Exception e) {
			Assert.fail("Radar Nav Tab Ad is Not displayed");
		}finally {
			attachScreen();
		}
		
	}

}

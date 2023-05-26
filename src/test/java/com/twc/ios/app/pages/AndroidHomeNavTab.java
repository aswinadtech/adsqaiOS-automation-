package com.twc.ios.app.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.twc.ios.app.functions.Functions;
import com.twc.ios.app.general.Driver;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.Utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;

public class AndroidHomeNavTab extends Utils {
	AppiumDriver<MobileElement> Ad;
	String homeTab_AccessibilityId = "Home Tab";
	String integratedMarqueeCard_Id = "com.weather.Weather:id/card_integrated_ad_root";
	String homeScreenStickyTestAd_Xpath = "(//android.widget.LinearLayout[@resource-id=\"com.weather.Weather:id/sticky_ad_container\"]//android.webkit.WebView)[1]";
	String integratedMarqueeAd_Xpath="//android.widget.FrameLayout[@resource-id='com.weather.Weather:id/card_integrated_ad_root']//android.widget.FrameLayout[@resource-id='com.weather.Weather:id/ad_view_holder']//android.webkit.WebView";
	String homeScreenStickyAd_Xpath = "//android.widget.LinearLayout[@resource-id='com.weather.Weather:id/sticky_ad_container']//android.webkit.WebView";
	

	By byFeedTab = MobileBy.AccessibilityId(homeTab_AccessibilityId);
	By byIntegratedMarqueeCard = MobileBy.id(integratedMarqueeCard_Id);
	By byHomeScreenStickyTestAd = MobileBy.xpath(homeScreenStickyTestAd_Xpath);

	By byIntegratedMarqueeAd = MobileBy.xpath(integratedMarqueeAd_Xpath);
	By byHomeScreenStickyAd = MobileBy.xpath(homeScreenStickyAd_Xpath);
	
	MobileElement feedTab = null;
	MobileElement homeScreenStickyTestAd = null;
	MobileElement integratedMarqueeAd = null;
	MobileElement homeScreenStickyAd = null;

	public AndroidHomeNavTab(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Click on Home Tab")
	public void clickonHomeTab() throws Exception {
		try {
			feedTab = Ad.findElement(byFeedTab);
			TestBase.clickOnElement(byFeedTab, feedTab, "Home Nav Tab");
			TestBase.waitForMilliSeconds(10000);
			System.out.println("Clicked on Home tab ");
			logStep("Clicked on Home tab");
			attachScreen();

		} catch (Exception e) {
			System.out.println("Home Tab is not displayed, launching app");
			logStep("Home Tab is not displayed, launching app");
			attachScreen();
			close_launchAppAndroid();
		}
	}

	@Step("Click on Home Tab and Handle Interstitial")
	public void clickonHomeTabAndHandleInterstitialAd() {
		try {
			feedTab = Ad.findElement(byFeedTab);
			TestBase.clickOnElement(byFeedTab, feedTab, "Home Nav Tab");
			TestBase.waitForMilliSeconds(5000);
			System.out.println("Clicked on Home tab ");
			logStep("Clicked on Home tab");
			attachScreen();
			/*
			 * Since Exit Interstitial displayed upon click on home tab, handling it once
			 * navigated to home Tab
			 */
			if (!interStitialDisplayed) {
				handle_Interstitial_Ad();
			} else {
				System.out.println("Interstitial Ad is already handled, hence not handling again");
				logStep("Interstitial Ad is already handled, hence not handling again");

			}

		} catch (Exception e) {
			System.out.println("Home Tab is not displayed, launching app");
			logStep("Home Tab is not displayed, launching app");
			attachScreen();
			close_launchApp();
		}
		// handle_Interstitial_Ad();
	}

	@Step("Navigate to HomeTab to get Interstitial Ad")
	public void navigateToHomeTab_toGetInterStitialAd() {
		try {
			feedTab = Ad.findElement(byFeedTab);
			TestBase.clickOnElement(byFeedTab, feedTab, "Home Nav Tab");
			TestBase.waitForMilliSeconds(2000);
			System.out.println("Clicked on Home tab ");
			logStep("Clicked on Home tab");
		} catch (Exception e) {
			System.out.println("Home Tab is not displayed, to get interstitial ad");
			logStep("Home Tab is not displayed, to get interstitial ad");
			Assert.fail("Home Tab is not displayed, to get interstitial ad");
		}

	}
	
	/**
	 * This method to be used to findout whether NextGen IM Ad displayed on the
	 * screen and set the flag 'nextGenIMadDisplayed' value
	 */
	@Step("Verify Whether NextGen IM Ad Displayed on HomeScreen and Set 'nextGenIMadDisplayed' flag accordingly")
	public void verifyForNextGenIMAdOnHomeScreen() {
		MobileElement currentCard = null;
		nextGenIMadDisplayed = false;
		try {
			//currentCard = Ad.findElementByAccessibilityId("nextgen-integrated-marquee-card-containerView");
			currentCard = Ad.findElement(byIntegratedMarqueeCard);
			nextGenIMadDisplayed = true;
		} catch (Exception e) {
			System.out.println("NextGen IM Card Not displayed");
			logStep("NextGen IM Card Not displayed");
		}
	}
	
	@Step("Assert Integrated Marquee Ad")
	public void assertIntegratedMarqueeAd() {
		MobileElement currentCard = null;
		try {
			//currentCard = Ad.findElementByAccessibilityId("nextgen-integrated-marquee-card-containerView");
			currentCard = Ad.findElement(byIntegratedMarqueeCard);
			nextGenIMadDisplayed = true;
		} catch (Exception e) {
			System.out.println("NextGen IM Card Not displayed");
			logStep("NextGen IM Card Not displayed");
		}
		if (nextGenIMadDisplayed) {
			try {
				integratedMarqueeAd = Ad.findElement(byIntegratedMarqueeAd);
				System.out.println("NextGen IM Ad displayed");
				logStep("NextGen IM Ad displayed");
			}catch (Exception e) {
				Assert.fail("NextGen IM Ad is Not displayed");
			}finally {
				attachScreen();
			}
		}
		
	}
	
	@Step("Verify Tapability of Sticky Test Ad On HomeScreen")
	public void verifyTapabilityOfStickyTestAdOnHomeScreen() {
		verifyForNextGenIMAdOnHomeScreen();
		if (!nextGenIMadDisplayed) {
			try {
				homeScreenStickyTestAd = Ad.findElement(byHomeScreenStickyTestAd);
				TestBase.clickOnElement(byHomeScreenStickyTestAd, homeScreenStickyTestAd, "HomeScreen Sticky Test Ad");
				TestBase.waitForMilliSeconds(10000);
				
				//Functions.checkForAppState();
				Assert.assertFalse(TestBase.isElementExists(byHomeScreenStickyTestAd));
			} catch (Exception e) {
				try {
					homeScreenStickyTestAd = Ad.findElement(byHomeScreenStickyTestAd);
					TestBase.clickOnElement(byHomeScreenStickyTestAd, homeScreenStickyTestAd, "HomeScreen Sticky Test Ad");
					TestBase.waitForMilliSeconds(10000);
					
					Assert.assertFalse(TestBase.isElementExists(byHomeScreenStickyTestAd));
				} catch (Exception e1) {
					
					System.out.println("HomeScreen Sticky Test Ad is Not displayed");
					logStep("HomeScreen Sticky Test Ad is Not displayed");
					Assert.fail("HomeScreen Sticky Test Ad is Not displayed");
				}
			} finally {
				attachScreen();
			}
		} else {
			System.out.println("Since IM Ad is displayed, skipping the validation of Tapability of HomeScreen Sticky Test Ad");
			logStep("Since IM Ad is displayed, skipping the validation of Tapability of HomeScreen Sticky Test Ad");
			attachScreen();
		}
		
	}
	
	@Step("Assert Sticky Ad")
	public void assertStickyAd() {
		verifyForNextGenIMAdOnHomeScreen();
		if (!nextGenIMadDisplayed) {
			try {
				homeScreenStickyAd = Ad.findElement(byHomeScreenStickyAd);
				System.out.println("HomeScreen Sticky Ad displayed");
				logStep("HomeScreen Sticky Ad displayed");
			}catch (Exception e) {
				Assert.fail("HomeScreen Sticky Ad is Not displayed");
			}finally {
				attachScreen();
			}
			
			
		} else {
			System.out.println("Since IM Ad is displayed, skipping the validation of HomeScreen Sticky Ad");
			logStep("Since IM Ad is displayed, skipping the validation of HomeScreen Sticky Ad");
			attachScreen();
		}
		
	}
}

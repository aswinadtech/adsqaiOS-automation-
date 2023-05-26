package com.twc.ios.app.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.twc.ios.app.charlesfunctions.CharlesProxy;
import com.twc.ios.app.functions.Functions;
import com.twc.ios.app.general.Driver;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.Utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Step;

public class SeasonalHubCardScreen extends Utils {
	AppiumDriver<MobileElement> Ad;

	String seasonalHubCardFirstIndex_Xpath = "//XCUIElementTypeOther[@name='seasonal-hub-card-containerView']//XCUIElementTypeCell[1]";
	String cancelButton_AccessibilityId = "Cancel";
	String seasonalHubCardAllIndexes_Xpath = "//XCUIElementTypeOther[@name='seasonal-hub-card-containerView']//XCUIElementTypeCell";
	String seasonalHubCardDynamicIndex_Xpath = "//XCUIElementTypeOther[@name='seasonal-hub-card-containerView']//XCUIElementTypeCell[";
	String seasonalHubDetailsStickyTestAd_AccessibilityId = "weather.seasonalhub-adContentView";
															 
	By bySeasonalHubCardFirstIndex = MobileBy.xpath(seasonalHubCardFirstIndex_Xpath);
	By bySeasonalHubDetailsStickyTestAd = MobileBy.AccessibilityId(seasonalHubDetailsStickyTestAd_AccessibilityId);

	MobileElement seasonalHubCardFirstIndex = null;
	MobileElement articlesLink = null;
	MobileElement articlesHeader = null;
	MobileElement allergyContentNavigationBar = null;
	MobileElement fluContentNavigationBar = null;
	MobileElement seasonalHubCardDynamicIndex = null;
	MobileElement seasonalHubCardDynamicIndexLabel = null;
	MobileElement seasonalHubDetailsStickyTestAd = null;

	public SeasonalHubCardScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigae To First Index of SeasonalHub Card")
	public void navigateToFirstIndexOfSeasonalHubCard() throws Exception {
		// Since there is another method which takes through all the index on seasonal
		// hub card, hence commented here. but navigating to first index to validate
		// amazon bid ids
		/*
		 * List<MobileElement> sh; sh = Ad.findElementsByXPath(
		 * "//XCUIElementTypeOther[@name='seasonal-hub-card-containerView']//XCUIElementTypeCell"
		 * ); int shsize = sh.size(); System.out.println("There are " + shsize +
		 * " index on the seasonalhub card"); for (int m = 1; m <= shsize; m++) { String
		 * subIndex = Ad.findElementByXPath(
		 * "//XCUIElementTypeOther[@name='seasonal-hub-card-containerView']//XCUIElementTypeCell["
		 * + m + "]/XCUIElementTypeStaticText[2]") .getAttribute("label");
		 * System.out.println("Current subindex : " + subIndex); Ad.findElementByXPath(
		 * "//XCUIElementTypeOther[@name='seasonal-hub-card-containerView']//XCUIElementTypeCell["
		 * + m + "]") .click(); Thread.sleep(2000);
		 * 
		 * // ScreenShot(cardName+" "+m,"Passed"); // attachScreen();
		 * navigateBackToFeedCard(); if (unlimitedInterstitial) {
		 * handle_Interstitial_Ad(); } }
		 */
		/*Ad.findElementByXPath(
				"//XCUIElementTypeOther[@name='seasonal-hub-card-containerView']//XCUIElementTypeCell[1]")
				.click();*/
		
		seasonalHubCardFirstIndex = Ad.findElement(bySeasonalHubCardFirstIndex);
		TestBase.clickOnElement(bySeasonalHubCardFirstIndex, seasonalHubCardFirstIndex, "SeasonalHub Card First Index");
		TestBase.waitForMilliSeconds(6000);
		/**
		 * Since Seasonalhub has Entry Interstitial, handling it once navigated to it
		 */
		CharlesProxy.proxy.stopRecording();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		CharlesProxy.proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		if (Utils.isInterStitialAdCalExists("Smoke", "SeasonalHub(Details)")) {

			if (Utils.isInterstitialCall_hasResponse("Smoke", "SeasonalHub(Details)")) {
				if (unlimitedInterstitial) {
					handle_Interstitial_Ad();
				} else {
					if (!interStitialDisplayed) {
						handle_Interstitial_Ad();
					} else {
						System.out.println("Interstitial Ad is already handled, hence not handling again");
						logStep("Interstitial Ad is already handled, hence not handling again");

					}
				}
			}
		}
		Functions.delete_folder("Charles");	
		CharlesProxy.proxy.startRecording();
		attachScreen();
		navigateBackToFeedCard();
		
	}
	
	@Step("Verify All Seasonal Hub index pages ad calls iu")
	public void verifyPubadCal_SeasonalHub(String excelName, String sheetName) throws Exception {
		SoftAssert sa = new SoftAssert();
		List<MobileElement> sh;
		sh = Ad.findElementsByXPath(seasonalHubCardAllIndexes_Xpath);
		int shsize = sh.size();
		System.out.println("There are " + shsize + " index present on Seasonal Hub page");
		logStep("There are " + shsize + " index present on Seasonal Hub page");
		for (int m = 1; m <= shsize; m++) {
			CharlesProxy.proxy.clearCharlesSession();
			Functions.archive_folder("Charles");
			
			By bySeasonalHubCardDynamicIndex = MobileBy.xpath(seasonalHubCardDynamicIndex_Xpath+m+"]");
			By bySeasonalHubCardDynamicIndexLabel = MobileBy.xpath(seasonalHubCardDynamicIndex_Xpath+m+"]/XCUIElementTypeStaticText[2]");
			seasonalHubCardDynamicIndexLabel = Ad.findElement(bySeasonalHubCardDynamicIndexLabel);
			
			String currentIndex = seasonalHubCardDynamicIndexLabel.getAttribute("label");
			
			System.out.println("Current index is: " + currentIndex);
			logStep("Current index is: " + currentIndex);
			seasonalHubCardDynamicIndex = Ad.findElement(bySeasonalHubCardDynamicIndex);
			TestBase.clickOnElement(bySeasonalHubCardDynamicIndex, seasonalHubCardDynamicIndex, "SeasonalHub Card Index "+m);
			TestBase.waitForMilliSeconds(5000);
			CharlesProxy.proxy.getXml();
			Utils.createXMLFileForCharlesSessionFile();

			System.out.println(currentIndex + " ad call verification started");
			logStep(currentIndex + " ad call verification started");
			Utils.verifyPubadCal(excelName, sheetName);
			System.out.println(currentIndex + " ad call verification completed");
			logStep(currentIndex + " ad call verification completed");

			navigateBackToFeedCard();

		}
		Functions.archive_folder("Charles");
		sa.assertAll();
	}
	
	@Step("Verify All Seasonal Hub index pages ad calls iu")
	public void navigateToSeasonalHubCardIndexes() throws Exception {
		SoftAssert sa = new SoftAssert();
		List<MobileElement> sh;
		sh = Ad.findElementsByXPath(seasonalHubCardAllIndexes_Xpath);
		int shsize = sh.size();
		System.out.println("There are " + shsize + " index present on Seasonal Hub page");
		logStep("There are " + shsize + " index present on Seasonal Hub page");
		for (int m = 1; m <= shsize; m++) {
						
			By bySeasonalHubCardDynamicIndex = MobileBy.xpath(seasonalHubCardDynamicIndex_Xpath+m+"]");
			By bySeasonalHubCardDynamicIndexLabel = MobileBy.xpath(seasonalHubCardDynamicIndex_Xpath+m+"]/XCUIElementTypeStaticText[2]");
			seasonalHubCardDynamicIndexLabel = Ad.findElement(bySeasonalHubCardDynamicIndexLabel);
			
			String currentIndex = seasonalHubCardDynamicIndexLabel.getAttribute("label");
			
			System.out.println("Current index is: " + currentIndex);
			logStep("Current index is: " + currentIndex);
			seasonalHubCardDynamicIndex = Ad.findElement(bySeasonalHubCardDynamicIndex);
			TestBase.clickOnElement(bySeasonalHubCardDynamicIndex, seasonalHubCardDynamicIndex, "SeasonalHub Card Index "+m);
			TestBase.waitForMilliSeconds(5000);
			
			navigateBackToFeedCard();

		}
		
	}
	
	@Step("Navigate to All Seasonal Hub index pages")
	public void verifyPubadCal_SeasonalHub(String excelName, String sheetName, boolean expected) throws Exception {
		SoftAssert sa = new SoftAssert();
		List<MobileElement> sh;
		sh = Ad.findElementsByXPath(seasonalHubCardAllIndexes_Xpath);
		int shsize = sh.size();
		System.out.println("There are " + shsize + " index present on Seasonal Hub page");
		logStep("There are " + shsize + " index present on Seasonal Hub page");
		for (int m = 1; m <= shsize; m++) {
			CharlesProxy.proxy.clearCharlesSession();
			Functions.archive_folder("Charles");
			
			By bySeasonalHubCardDynamicIndex = MobileBy.xpath(seasonalHubCardDynamicIndex_Xpath+m+"]");
			By bySeasonalHubCardDynamicIndexLabel = MobileBy.xpath(seasonalHubCardDynamicIndex_Xpath+m+"]/XCUIElementTypeStaticText[2]");
			seasonalHubCardDynamicIndexLabel = Ad.findElement(bySeasonalHubCardDynamicIndexLabel);
			
			String currentIndex = seasonalHubCardDynamicIndexLabel.getAttribute("label");
			
			System.out.println("Current index is: " + currentIndex);
			logStep("Current index is: " + currentIndex);
			seasonalHubCardDynamicIndex = Ad.findElement(bySeasonalHubCardDynamicIndex);
			TestBase.clickOnElement(bySeasonalHubCardDynamicIndex, seasonalHubCardDynamicIndex, "SeasonalHub Card Index "+m);
			TestBase.waitForMilliSeconds(5000);
			CharlesProxy.proxy.getXml();
			Utils.createXMLFileForCharlesSessionFile();

			System.out.println(currentIndex + " ad call verification started");
			logStep(currentIndex + " ad call verification started");
			Utils.verifyPubadCal(excelName, sheetName, expected);
			System.out.println(currentIndex + " ad call verification completed");
			logStep(currentIndex + " ad call verification completed");

			navigateBackToFeedCard();

		}
		Functions.archive_folder("Charles");
		sa.assertAll();
	}
	
	@Step("Navigae To Details Of First Index of SeasonalHub Card")
	public void navigateToDetailsOfFirstIndexOfSeasonalHubCard() throws Exception {
		seasonalHubCardFirstIndex = Ad.findElement(bySeasonalHubCardFirstIndex);
		TestBase.clickOnElement(bySeasonalHubCardFirstIndex, seasonalHubCardFirstIndex, "SeasonalHub Card First Index");
		TestBase.waitForMilliSeconds(6000);
		attachScreen();
		
	}

	@Step("Verify Tapability of Sticky Test Ad On Seasonal Hub Details Page")
	public void verifyTapabilityOfTestAdOnSeasonalHubDetailsPage() {
			try {
				seasonalHubDetailsStickyTestAd = Ad.findElement(bySeasonalHubDetailsStickyTestAd);
				TestBase.clickOnElement(bySeasonalHubDetailsStickyTestAd, seasonalHubDetailsStickyTestAd, "Seasonal Hub Details Page Sticky Test Ad");
				TestBase.waitForMilliSeconds(10000);
				
				//Functions.checkForAppState();
				Assert.assertFalse(TestBase.isElementExists(bySeasonalHubDetailsStickyTestAd));
			} catch (Exception e) {
				try {
					seasonalHubDetailsStickyTestAd = Ad.findElement(bySeasonalHubDetailsStickyTestAd);
					TestBase.clickOnElement(bySeasonalHubDetailsStickyTestAd, seasonalHubDetailsStickyTestAd, "Seasonal Hub Details Page Sticky Test Ad");
					TestBase.waitForMilliSeconds(10000);
					
					//Functions.checkForAppState();
					Assert.assertFalse(TestBase.isElementExists(bySeasonalHubDetailsStickyTestAd));
				} catch (Exception e1) {
					
					System.out.println("Seasonal Hub Details Page Test Ad is Not displayed");
					logStep("Seasonal Hub Details Page Test Ad is Not displayed");
					Assert.fail("Seasonal Hub Details Page Test Ad is Not displayed");
				}
			} finally {
				attachScreen();
			}
	}

}

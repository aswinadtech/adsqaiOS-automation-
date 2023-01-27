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

public class DailyCardScreen extends Utils {
	AppiumDriver<MobileElement> Ad;
	String dailyDetails_AccessibilityId = "dailyCollectionViewCell_0";
	String tooltipClose_AccessibilityId = "tooltip close";
	String dailyCardIFAd_Name = "weather.card.daily-adContentView";
	
	By byDailyDetails = MobileBy.AccessibilityId(dailyDetails_AccessibilityId);
	By byToolTip = MobileBy.AccessibilityId(tooltipClose_AccessibilityId);
	By byDailyCardIFAd = MobileBy.AccessibilityId(dailyCardIFAd_Name);

	MobileElement dailyDetails = null;
	MobileElement toolTip = null;
	MobileElement dailyCardIFAd = null;

	public DailyCardScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigate To Daily Card Content Page")
	public void navigateToDailyCardContentPage() throws Exception {
		//MobileElement dailyDetails = Ad.findElementByAccessibilityId("dailyCollectionViewCell_0");
		dailyDetails = Ad.findElement(byDailyDetails);
		TestBase.clickOnElement(byDailyDetails, dailyDetails, "Daily Details");
		TestBase.waitForMilliSeconds(6000);
		// ScreenShot(cardName,"Passed");
		// attachScreen();
		try {
			toolTip = Ad.findElement(byToolTip);
			TestBase.clickOnElement(byToolTip, toolTip, "Tooltip");
			TestBase.waitForMilliSeconds(4000);
		} catch (Exception e) {
			System.out.println("Tooltip not displayed on daily details page");
			logStep("Tooltip not displayed on daily details page");
		}
		attachScreen();
		navigateBackToFeedCard();
		CharlesProxy.proxy.stopRecording();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		CharlesProxy.proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		if (Utils.isInterStitialAdCalExists("Smoke", "Daily(10day)")) {

			if (Utils.isInterstitialCall_hasResponse("Smoke", "Daily(10day)")) {
				if (unlimitedInterstitial) {
					handle_Interstitial_Ad();
				} else {
					if (!interStitialDisplayed) {
						/*
						 * Since Exit Interstitial displayed upon click on back icon, handling it once
						 * click on back
						 */
						handle_Interstitial_Ad();
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
	
	@Step("Navigate To Daily Card Content Page And Not to Handle Interstitials")
	public void navigateToDailyCardContentPageAndDontHandleInterstitials() throws Exception {
		//MobileElement dailyDetails = Ad.findElementByAccessibilityId("dailyCollectionViewCell_0");
		dailyDetails = Ad.findElement(byDailyDetails);
		TestBase.clickOnElement(byDailyDetails, dailyDetails, "Daily Details");
		TestBase.waitForMilliSeconds(6000);
		// ScreenShot(cardName,"Passed");
		// attachScreen();
		try {
			toolTip = Ad.findElement(byToolTip);
			TestBase.clickOnElement(byToolTip, toolTip, "Tooltip");
			TestBase.waitForMilliSeconds(4000);
		} catch (Exception e) {
			System.out.println("Tooltip not displayed on daily details page");
			logStep("Tooltip not displayed on daily details page");
		}
		attachScreen();
	}
	
	/**
	 * Verify Daily IF Ad by response
	 * @param Excelname
	 * @param sheetName
	 * @throws Exception
	 */
	public void verifyDailyIFAd_byCallResponse(String Excelname, String sheetName) throws Exception {

		boolean iMCallResponse = Utils.isNextGenIMorIFCall_hasResponse(Excelname, sheetName);
		String cardName = "Dailycard";
		MobileElement adele;

		Thread.sleep(10000);
		if (iMCallResponse == true) {
			try {
				//adele = Ad.findElementByName("weather.card.daily-adContentView");
				dailyCardIFAd = Ad.findElement(byDailyCardIFAd);
				if (dailyCardIFAd.isDisplayed()) {
					logStep("Daily IF Ad presented on the page " + cardName + " :--- Sizes are " + dailyCardIFAd.getSize());
					System.out.println(
							"Daily IF Ad presented on the page " + cardName + " :--- Sizes are " + dailyCardIFAd.getSize());
					attachScreen();

				}

			} catch (Exception e) {
				System.out.println("Ad Not presented on the " + cardName + " screen though IF call response true");
				logStep("Ad Not presented on the " + cardName + " screen though IF call response true");
				attachScreen();
				Assert.fail("Ad Not presented on the " + cardName + " screen though IF call response true");
			}

		} else {
			try {
				//adele = Ad.findElementByName("weather.card.daily-adContentView");
				dailyCardIFAd = Ad.findElement(byDailyCardIFAd);
				if (dailyCardIFAd.isDisplayed()) {
					logStep("Daily IF Ad presented on the page " + cardName + " when response is false :--- Sizes are "
							+ dailyCardIFAd.getSize());
					System.out.println("Daily IF Ad presented on the page " + cardName
							+ " when response is false :--- Sizes are " + dailyCardIFAd.getSize());
					attachScreen();
					Assert.fail("Daily IF Ad presented on the page " + cardName + " when response is false");
				}

			} catch (Exception e) {
				System.out.println("Ad Not presented on the " + cardName + " screen since IF call response false");
				logStep("Ad Not presented on the " + cardName + " screen since IF call response false");
				attachScreen();

			}
		}

	}

}

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

public class AndroidDailyCardScreen extends Utils {
	AppiumDriver<MobileElement> Ad;
	String dailyDetails_Id = "com.weather.Weather:id/daily_forecast_container";
	String tooltipClose_AccessibilityId = "tooltip close";
	String dailyCardIFAd_Name = "weather.card.daily-adContentView";
	
	By byDailyCard = MobileBy.id("com.weather.Weather:id/card_daily_forecast_view");
	By byDailyDetails = MobileBy.id(dailyDetails_Id);
	By byToolTip = MobileBy.AccessibilityId(tooltipClose_AccessibilityId);
	By byDailyCardIFAd = MobileBy.AccessibilityId(dailyCardIFAd_Name);
	
	MobileElement dailyCard = null;
	MobileElement dailyDetails = null;
	MobileElement toolTip = null;
	MobileElement dailyCardIFAd = null;

	public AndroidDailyCardScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}
	
	public void scrollToDailyCard() throws Exception {
		//aQCard = Ad.findElement(byAirQualityCard);
		Functions.genericScroll(byDailyCard, true, true, getOffsetYTop(), TOLERANCE_FROM_TOP);
		//Functions.genericScrollTWC(byFeedOneCard, true, true, getOffsetYTop(), TOLERANCE_FROM_TOP, false, false);
	}

	@Step("Navigate To Daily Card Content Page")
	public void navigateToDailyCardContentPage() throws Exception {
		//MobileElement dailyDetails = Ad.findElementByAccessibilityId("dailyCollectionViewCell_0");
		dailyDetails = Ad.findElement(byDailyDetails);
		TestBase.clickOnElement(byDailyDetails, dailyDetails, "Daily Details");
		TestBase.waitForMilliSeconds(6000);
		// ScreenShot(cardName,"Passed");
		// attachScreen();
	/*	try {
			toolTip = Ad.findElement(byToolTip);
			TestBase.clickOnElement(byToolTip, toolTip, "Tooltip");
			TestBase.waitForMilliSeconds(4000);
		} catch (Exception e) {
			System.out.println("Tooltip not displayed on daily details page");
			logStep("Tooltip not displayed on daily details page");
		}*/
		attachScreen();
		navigateBackToFeedCardAndroid();
	/*	CharlesProxy.proxy.stopRecording();
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
						
						handle_Interstitial_Ad();
					} else {
						System.out.println("Interstitial Ad is already handled, hence not handling again");
						logStep("Interstitial Ad is already handled, hence not handling again");

					}
				}
			}
		}
		CharlesProxy.proxy.startRecording();
		Functions.delete_folder("Charles");	*/	
	}
	
	@Step("Navigate To Daily Card Content Page And Not to Handle Interstitials")
	public void navigateToDailyCardContentPageAndDontHandleInterstitials() throws Exception {
		//MobileElement dailyDetails = Ad.findElementByAccessibilityId("dailyCollectionViewCell_0");
		dailyDetails = Ad.findElement(byDailyDetails);
		TestBase.clickOnElement(byDailyDetails, dailyDetails, "Daily Details");
		TestBase.waitForMilliSeconds(6000);
		// ScreenShot(cardName,"Passed");
		// attachScreen();
	/*	try {
			toolTip = Ad.findElement(byToolTip);
			TestBase.clickOnElement(byToolTip, toolTip, "Tooltip");
			TestBase.waitForMilliSeconds(4000);
		} catch (Exception e) {
			System.out.println("Tooltip not displayed on daily details page");
			logStep("Tooltip not displayed on daily details page");
		}*/
		attachScreen();
	}
	
	

}

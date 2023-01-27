package com.twc.ios.app.pages;

import org.openqa.selenium.By;

import com.twc.ios.app.charlesfunctions.CharlesProxy;
import com.twc.ios.app.functions.Functions;
import com.twc.ios.app.general.Driver;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.Utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;

public class AndroidAirQualityCardScreen extends Utils {
	AppiumDriver<MobileElement> Ad;
	String aQCard_Id = "com.weather.Weather:id/call_to_action_button";
	String closeLight_AccessibilityId = "close light";
	
	By byAQCard = MobileBy.id(aQCard_Id);
	By byCloseLight = MobileBy.AccessibilityId(closeLight_AccessibilityId);
	
	By byAirQualityCard = MobileBy.id("com.weather.Weather:id/air_quality_primary_pollutant_container");

	MobileElement aQCard = null;
	MobileElement closeLight = null;

	public AndroidAirQualityCardScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigate To AirQuality Card Content Page")
	public void navigateToAirQualityCardContentPage() throws Exception {
		aQCard = Ad.findElement(byAQCard);
		TestBase.clickOnElement(byAQCard, aQCard, "AQ Card Details");
		TestBase.waitForMilliSeconds(6000);
	/*	try {
			closeLight = Ad.findElement(byCloseLight);
			TestBase.clickOnElement(byCloseLight, closeLight, "Siri PopUp");
			TestBase.waitForMilliSeconds(4000);
		} catch (Exception e) {
			System.out.println("Siri popup not present validating aq page ad");
			logStep("Siri popup not present validating aq page ad");
		}*/
	/*	CharlesProxy.proxy.stopRecording();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		CharlesProxy.proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		if (Utils.isInterStitialAdCalExists("Smoke", "Air Quality(Content)")) {

			if (Utils.isInterstitialCall_hasResponse("Smoke", "Air Quality(Content)")) {
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
		CharlesProxy.proxy.startRecording();*/
		attachScreen();
		navigateBackToFeedCardAndroid();
		
	}
	
	@Step("Navigate To AirQuality Card Content Page And Not to Handle Interstitials")
	public void navigateToAirQualityCardContentPageAndDontHandleInterstitials() throws Exception {
		aQCard = Ad.findElement(byAQCard);
		TestBase.clickOnElement(byAQCard, aQCard, "AQ Card Details");
		TestBase.waitForMilliSeconds(6000);
	/*	try {
			closeLight = Ad.findElement(byCloseLight);
			TestBase.clickOnElement(byCloseLight, closeLight, "Siri PopUp");
			TestBase.waitForMilliSeconds(4000);
		} catch (Exception e) {
			System.out.println("Siri popup not present validating aq page ad");
			logStep("Siri popup not present validating aq page ad");
		}*/
		attachScreen();
	}
	
	public  void scrollToAQCard() throws Exception {
		//aQCard = Ad.findElement(byAirQualityCard);
		Functions.genericScroll(byAirQualityCard, true, true, getOffsetYTop(), TOLERANCE_FROM_TOP);
		//Functions.genericScrollTWC(byAirQualityCard, true, true, getOffsetYTop(), TOLERANCE_FROM_TOP, false, false);
	}

}

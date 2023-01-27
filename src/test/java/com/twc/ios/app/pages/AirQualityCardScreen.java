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

public class AirQualityCardScreen extends Utils {
	AppiumDriver<MobileElement> Ad;
	String aQCardDetaiButton_AccessibilityId = "air-quality-card_detailedButton";
	String closeLight_AccessibilityId = "close light";
	
	By byAQCardDetailButton = MobileBy.AccessibilityId(aQCardDetaiButton_AccessibilityId);
	By byCloseLight = MobileBy.AccessibilityId(closeLight_AccessibilityId);
	
	By byAirQualityCard = MobileBy.AccessibilityId("air-quality-card-containerView");

	MobileElement aQCardDetailButton = null;
	MobileElement closeLight = null;

	public AirQualityCardScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigate To AirQuality Card Content Page")
	public void navigateToAirQualityCardContentPage() throws Exception {
		aQCardDetailButton = Ad.findElement(byAQCardDetailButton);
		TestBase.clickOnElement(byAQCardDetailButton, aQCardDetailButton, "AQ Card Detail Button");
		TestBase.waitForMilliSeconds(6000);
		try {
			closeLight = Ad.findElement(byCloseLight);
			TestBase.clickOnElement(byCloseLight, closeLight, "Siri PopUp");
			TestBase.waitForMilliSeconds(4000);
		} catch (Exception e) {
			System.out.println("Siri popup not present validating aq page ad");
			logStep("Siri popup not present validating aq page ad");
		}
		CharlesProxy.proxy.stopRecording();
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
		CharlesProxy.proxy.startRecording();
		attachScreen();
		navigateBackToFeedCard();
		
	}
	
	@Step("Navigate To AirQuality Card Content Page And Not to Handle Interstitials")
	public void navigateToAirQualityCardContentPageAndDontHandleInterstitials() throws Exception {
		aQCardDetailButton = Ad.findElement(byAQCardDetailButton);
		TestBase.clickOnElement(byAQCardDetailButton, aQCardDetailButton, "AQ Card Detail Button");
		TestBase.waitForMilliSeconds(6000);
		try {
			closeLight = Ad.findElement(byCloseLight);
			TestBase.clickOnElement(byCloseLight, closeLight, "Siri PopUp");
			TestBase.waitForMilliSeconds(4000);
		} catch (Exception e) {
			System.out.println("Siri popup not present validating aq page ad");
			logStep("Siri popup not present validating aq page ad");
		}
		attachScreen();
	}
	
	public  void scrollToAQCard() throws Exception {
		//aQCard = Ad.findElement(byAirQualityCard);
		Functions.genericScroll(byAirQualityCard, true, false, getOffsetYTop(), TOLERANCE_FROM_TOP);
		//Functions.genericScrollTWC(byAirQualityCard, true, true, getOffsetYTop(), TOLERANCE_FROM_TOP, false, false);
	}

}

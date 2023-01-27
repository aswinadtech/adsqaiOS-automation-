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

public class RadarCardScreen extends Utils{
	AppiumDriver<MobileElement> Ad;
	
	String radarCard_Xpath = "//XCUIElementTypeOther[@name='map-card-containerView']//XCUIElementTypeButton[@name='contentClickAction']";
	String closeLight_AccessibilityId = "close light";

	By byRadarCard = MobileBy.xpath(radarCard_Xpath);
	By byCloseLight = MobileBy.AccessibilityId(closeLight_AccessibilityId);	

	MobileElement radarCard = null;
	MobileElement closeLight = null;

	public RadarCardScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigate To Radar Card Content Page")
	public void navigateToRadarCardContentPage() throws Exception {
		
		radarCard = Ad.findElement(byRadarCard);
		TestBase.clickOnElement(byRadarCard, radarCard, "Radar Card");
		TestBase.waitForMilliSeconds(6000);
		System.out.println("Navigated to Radar Card Content Page");
		logStep("Navigated to Radar Card Content Page");

		try {
			closeLight = Ad.findElement(byCloseLight);
			TestBase.clickOnElement(byCloseLight, closeLight, "Close Light icon");
			TestBase.waitForMilliSeconds(4000);
		} catch (Exception e) {
			System.out.println("Tooltip not displayed on map details page");
			logStep("Tooltip not displayed on map details page");
		}
		attachScreen();
		navigateBackToFeedCard();
		CharlesProxy.proxy.stopRecording();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		CharlesProxy.proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		if (Utils.isInterStitialAdCalExists("Smoke", "Map")) {

			if (Utils.isInterstitialCall_hasResponse("Smoke", "Map")) {
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
		Functions.delete_folder("Charles");
		CharlesProxy.proxy.startRecording();
	}
	
	@Step("Navigate To Radar Card Content Page And Not to Handle Interstitials")
	public void navigateToRadarCardContentPageAndDontHandleInterstitials() throws Exception {
		radarCard = Ad.findElement(byRadarCard);
		TestBase.clickOnElement(byRadarCard, radarCard, "Radar Card");
		TestBase.waitForMilliSeconds(6000);
		System.out.println("Navigated to Radar Card Content Page");
		logStep("Navigated to Radar Card Content Page");

		try {
			closeLight = Ad.findElement(byCloseLight);
			TestBase.clickOnElement(byCloseLight, closeLight, "Close Light icon");
			TestBase.waitForMilliSeconds(4000);
		} catch (Exception e) {
			System.out.println("Tooltip not displayed on map details page");
			logStep("Tooltip not displayed on map details page");
		}
		attachScreen();
		
	}

}

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

public class HourlyNavTab extends Utils {
	AppiumDriver<MobileElement> Ad;
	String hourlyTab_AccessibilityId = "hourlyForecast2Tab";
	String severeInsightText_Xpath = "//XCUIElementTypeStaticText[@name='Seek  safe shelter immediately. Damaging winds, large hail and tornados very likely.']";

	By byHourlyNavTab = MobileBy.AccessibilityId(hourlyTab_AccessibilityId);
	By bySevereInsightText = MobileBy.xpath(severeInsightText_Xpath);
	

	MobileElement hourlyNavTab = null;
	MobileElement severeInsightText = null;

	public HourlyNavTab(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	
	
	@Step("Navigate To Hourly Tab")
	public void navigateToHourlyTab() throws Exception {
		try {
			hourlyNavTab = Ad.findElement(byHourlyNavTab);
			TestBase.clickOnElement(byHourlyNavTab, hourlyNavTab, "Hourly Nav Tab");
			TestBase.waitForMilliSeconds(10000);
			System.out.println("Navigated to Hourly tab ");
			logStep("Navigated to Hourly tab");
			attachScreen();

		} catch (Exception e) {
			System.out.println("Hourly tab not displayed");
			logStep("Hourly tab not displayed");
			attachScreen();
		}
	}

	@Step("Navigate to Hourly tab and Handle Entry interstitial")
	public void navigateToHourlyTabAndHandleInterstitialAd() throws Exception {
		navigateToHourlyTab();
		/**
		 * Since Hourly has Entry Interstitial, handling it once navigated to Hourly Tab
		 */
		CharlesProxy.proxy.stopRecording();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		if (interStitialAdcallSuccessful) {
			handle_Interstitial_Ad();
		} else {
			CharlesProxy.proxy.getXml();
			Utils.createXMLFileForCharlesSessionFile();
			if (Utils.isInterStitialAdCalExists("Smoke", "Hourly")) {

				if (Utils.isInterstitialCall_hasResponse("Smoke", "Hourly")) {
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
		Functions.delete_folder("Charles");	

	}
	
	@Step("Assert Severe Insight Text On Hourly Nav Tab")
	public void assertSevereInsightTextOnHourlyNavTab() {
		severeInsightText = Ad.findElement(bySevereInsightText);
	}
		
}

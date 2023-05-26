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

public class TodayCardScreen extends Utils {
	AppiumDriver<MobileElement> Ad;
	String todayCardDetailsButton_AccessibilityId = "today-card_detailedButton";
	
	By byTodayCardDetailsButton = MobileBy.AccessibilityId(todayCardDetailsButton_AccessibilityId);
	

	MobileElement todayCardDetailsButton = null;
	

	public TodayCardScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigate To Today Card Content Page")
	public void navigateToTodayCardContentPage() throws Exception {
		
		todayCardDetailsButton = Ad.findElement(byTodayCardDetailsButton);
		TestBase.clickOnElement(byTodayCardDetailsButton, todayCardDetailsButton, "Today Card Details Button");
		TestBase.waitForMilliSeconds(6000);
		CharlesProxy.proxy.stopRecording();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		CharlesProxy.proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		if (Utils.isInterStitialAdCalExists("Smoke", "Today")) {

			if (Utils.isInterstitialCall_hasResponse("Smoke", "Today")) {
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
	
	@Step("Navigate To Today Card Content Page And Not to Handle Interstitials")
	public void navigateToTodayCardContentPageAndDontHandleInterstitials() throws Exception {
		todayCardDetailsButton = Ad.findElement(byTodayCardDetailsButton);
		TestBase.clickOnElement(byTodayCardDetailsButton, todayCardDetailsButton, "Today Card Details Button");
		TestBase.waitForMilliSeconds(6000);
		attachScreen();
	}

}

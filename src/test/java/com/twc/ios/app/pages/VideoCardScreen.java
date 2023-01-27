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

public class VideoCardScreen extends Utils{
	AppiumDriver<MobileElement> Ad;
	String videoCard_AccessibilityId = "video_item_0_cell_container";

	By byVideoCard = MobileBy.AccessibilityId(videoCard_AccessibilityId);
	

	MobileElement videoCard = null;
	

	public VideoCardScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigate To Video Card Content Page")
	public void navigateToVideoCardContentPage() throws Exception {
		
		videoCard = Ad.findElement(byVideoCard);
		TestBase.clickOnElement(byVideoCard, videoCard, "Video Card");
		TestBase.waitForMilliSeconds(10000);
		attachScreen();
		navigateBackToFeedCard();
		CharlesProxy.proxy.stopRecording();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		CharlesProxy.proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		if (Utils.isInterStitialAdCalExists("Smoke", "PreRollVideo")) {

			if (Utils.isInterstitialCall_hasResponse("Smoke", "PreRollVideo")) {
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
	
	@Step("Navigate To Video Card Content Page And Not to Handle Interstitials")
	public void navigateToVideoCardContentPageAndDontHandleInterstitials() throws Exception {
		
		videoCard = Ad.findElement(byVideoCard);
		TestBase.clickOnElement(byVideoCard, videoCard, "Video Card");
		TestBase.waitForMilliSeconds(10000);
		attachScreen();
	}

}

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

public class AndroidVideoCardScreen extends Utils{
	AppiumDriver<MobileElement> Ad;
	String videoGridItem0_Id = "com.weather.Weather:id/video_grid_item_0";

	
	By byVideoCard = MobileBy.id("com.weather.Weather:id/card_videos_view");
	By byVideoGridItem0 = MobileBy.id(videoGridItem0_Id);
	
	MobileElement videoCard = null;
	MobileElement videoGridItem0 = null;
	

	public AndroidVideoCardScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigate To Video Card Content Page")
	public void navigateToVideoCardContentPage() throws Exception {
		videoGridItem0 = Ad.findElement(byVideoGridItem0);
		TestBase.clickOnElement(byVideoGridItem0, videoGridItem0, "Video Card Details");
		TestBase.waitForMilliSeconds(10000);
		attachScreen();
		navigateBackToFeedCardAndroid();
	/*	CharlesProxy.proxy.stopRecording();
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
	}
	
	@Step("Navigate To Video Card Content Page And Not to Handle Interstitials")
	public void navigateToVideoCardContentPageAndDontHandleInterstitials() throws Exception {
		
		videoGridItem0 = Ad.findElement(byVideoGridItem0);
		TestBase.clickOnElement(byVideoGridItem0, videoGridItem0, "Video Card Details");
		TestBase.waitForMilliSeconds(10000);
		attachScreen();
	}

}

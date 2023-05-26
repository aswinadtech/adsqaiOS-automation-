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

public class AndroidHourlyCardScreen extends Utils {
	AppiumDriver<MobileElement> Ad;
	String hourlyCard_Id = "com.weather.Weather:id/hourly_forecast_card_view";
	String hourlyDetails_Id = "com.weather.Weather:id/details_button";
	
	
	By byHourlyDetails = MobileBy.id(hourlyDetails_Id);
	By byHourlyCard = MobileBy.id(hourlyCard_Id);
	
	
	MobileElement hourlyDetails = null;
	MobileElement hourlyCard = null;
	

	public AndroidHourlyCardScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	public void scrollToHourlyCard() throws Exception {
		//aQCard = Ad.findElement(byAirQualityCard);
		Functions.genericScroll(byHourlyCard, true, true, getOffsetYTop(), TOLERANCE_FROM_TOP);
		//Functions.genericScrollTWC(byFeedOneCard, true, true, getOffsetYTop(), TOLERANCE_FROM_TOP, false, false);
	}
	
	@Step("Navigate To Hourly Card Content Page")
	public void navigateToHourlyCardContentPage() throws Exception {
		//MobileElement dailyDetails = Ad.findElementByAccessibilityId("dailyCollectionViewCell_0");
		hourlyDetails = Ad.findElement(byHourlyDetails);
		TestBase.clickOnElement(byHourlyDetails, hourlyDetails, "Hourly Details");
		TestBase.waitForMilliSeconds(6000);
		attachScreen();
		navigateBackToFeedCardAndroid();
		/*CharlesProxy.proxy.stopRecording();
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
		Functions.delete_folder("Charles");		*/
	}
	

}

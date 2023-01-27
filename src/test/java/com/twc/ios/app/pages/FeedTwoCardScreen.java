package com.twc.ios.app.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.twc.ios.app.functions.Functions;
import com.twc.ios.app.general.Driver;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.Utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;

public class FeedTwoCardScreen extends Utils {
	AppiumDriver<MobileElement> Ad;
	//String feedOneCardAd_AccessibilityId = "weather.feed1-adContainerView";
	String feedTwoCardAd_AccessibilityId = "weather.feed1-adContentView";
	String snapshotLogo_AccessibilityId = "SnapshotLogo";
	String buttonClose_AccessibilityId  = "button close";
	
	By byFeedTwoCard = MobileBy.AccessibilityId("ads-card-containerView");
	By byFeedTwoCardAd = MobileBy.AccessibilityId(feedTwoCardAd_AccessibilityId);
	By bySnapshotLogo = MobileBy.AccessibilityId(snapshotLogo_AccessibilityId);
	By byButtonClose = MobileBy.AccessibilityId(buttonClose_AccessibilityId);
	

	MobileElement feedTwoCard = null;
	MobileElement feedTwoCardAd = null;
	MobileElement snapshotLogo = null;
	MobileElement buttonClose = null;

	public FeedTwoCardScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigate To FeedTwo Card Content Page")
	public void navigateToFeedTwoCardContentPage() throws Exception {

		feedTwoCardAd = Ad.findElement(byFeedTwoCardAd);
		TestBase.clickOnElement(byFeedTwoCardAd, feedTwoCardAd, "FeedTwoCard Ad");
		Thread.sleep(6000);
		attachScreen();
	}
	
	public  void scrollToFeedTwoCard() throws Exception {
		//aQCard = Ad.findElement(byAirQualityCard);
		Functions.genericScroll(byFeedTwoCard, true, true, getOffsetYTop(), TOLERANCE_FROM_TOP);
		//Functions.genericScrollTWC(byFeedOneCard, true, true, getOffsetYTop(), TOLERANCE_FROM_TOP, false, false);
	}
	
	@Step("Assert Ad On Feed2 Card")
	public void assertAdOnFeed2Card() {
		try {
			feedTwoCardAd = Ad.findElement(byFeedTwoCardAd);
			System.out.println("Feed2 Card Ad displayed");
			logStep("Feed2 Card Ad displayed");
		} catch (Exception e) {
			Assert.fail("Ad not displayed on Feed2 Card");
		}finally {
			attachScreen();
		}
		
	}

}

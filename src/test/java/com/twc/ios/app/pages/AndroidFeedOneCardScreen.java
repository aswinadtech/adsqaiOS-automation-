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

public class AndroidFeedOneCardScreen extends Utils {
	AppiumDriver<MobileElement> Ad;
	//String feedOneCardAd_Xpath = "(//android.widget.FrameLayout[@resource-id=\"com.weather.Weather:id/ad_view_holder\"]//android.webkit.WebView)[1]";
	String feedOneCardAd_Xpath = "//android.widget.FrameLayout[@resource-id='com.weather.Weather:id/card_integrated_ad_view']//android.widget.FrameLayout[@resource-id='com.weather.Weather:id/ad_view_holder']//android.webkit.WebView";
	String snapshotLogo_AccessibilityId = "SnapshotLogo";
	String buttonClose_AccessibilityId  = "button close";
	
	//By byFeedOneCard = MobileBy.id("com.weather.Weather:id/ia_card_title");
	By byFeedOneCard = MobileBy.id("com.weather.Weather:id/card_integrated_ad_view");
	By byFeedOneCardAd = MobileBy.xpath(feedOneCardAd_Xpath);
	By bySnapshotLogo = MobileBy.AccessibilityId(snapshotLogo_AccessibilityId);
	By byButtonClose = MobileBy.AccessibilityId(buttonClose_AccessibilityId);
	

	MobileElement feedOneCard = null;
	MobileElement feedOneCardAd = null;
	MobileElement snapshotLogo = null;
	MobileElement buttonClose = null;

	public AndroidFeedOneCardScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigate To FeedOne Card Content Page")
	public void navigateToFeedOneCardContentPage() throws Exception {

		feedOneCardAd = Ad.findElement(byFeedOneCardAd);
		TestBase.clickOnElement(byFeedOneCardAd, feedOneCardAd, "FeedOneCard Ad");
		Thread.sleep(6000);
		attachScreen();
	}
	
	public  void scrollToFeedOneCard() throws Exception {
		//aQCard = Ad.findElement(byAirQualityCard);
		Functions.genericScroll(byFeedOneCard, true, true, getOffsetYTop(), TOLERANCE_FROM_TOP);
		//Functions.genericScrollTWC(byFeedOneCard, true, true, getOffsetYTop(), TOLERANCE_FROM_TOP, false, false);
	}
	
	@Step("Assert Ad On Feed1 Card")
	public void assertAdOnFeed1Card() {
		try {
			feedOneCardAd = Ad.findElement(byFeedOneCardAd);
			System.out.println("Feed1 Card Ad displayed");
			logStep("Feed1 Card Ad displayed");
		} catch (Exception e) {
			Assert.fail("Ad not displayed on Feed1 Card");
		}finally {
			attachScreen();
		}
		
	}

}

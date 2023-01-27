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

public class NewsCardScreen extends Utils {
	AppiumDriver<MobileElement> Ad;
	String newsItem0Cell_AccessibilityId = "news_item_0_cell_container";
	String advertisementOnNewsDetails_Xpath = "//XCUIElementTypeOther[@name=\"weather.articles-adContentView\"]";
	
	By byNewsCard = MobileBy.AccessibilityId("news-card-containerView");
	By byNewsItem0Cell = MobileBy.AccessibilityId(newsItem0Cell_AccessibilityId);

	
	By byAdvertisementOnNewsDetails = MobileBy.xpath(advertisementOnNewsDetails_Xpath);
	
	MobileElement newsCard = null;
	MobileElement newsItem0Cell = null;
	MobileElement advertisementOnNewsDetails =  null;
	

	public NewsCardScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigate To News Card Content Page")
	public void navigateToNewsCardContentPage() throws Exception {
		newsItem0Cell = Ad.findElement(byNewsItem0Cell);
		TestBase.clickOnElement(byNewsItem0Cell, newsItem0Cell, "News Card Details");
		TestBase.waitForMilliSeconds(6000);
		attachScreen();
		navigateBackToFeedCard();
		CharlesProxy.proxy.stopRecording();
		Functions.archive_folder("Charles");
		TestBase.waitForMilliSeconds(5000);
		CharlesProxy.proxy.getXml();
		Utils.createXMLFileForCharlesSessionFile();
		if (Utils.isInterStitialAdCalExists("Smoke", "News(details)")) {

			if (Utils.isInterstitialCall_hasResponse("Smoke", "News(details)")) {
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
	
	@Step("Navigate To News Card Content Page And Not to Handle Interstitials")
	public void navigateToNewsCardContentPageAndDontHandleInterstitials() throws Exception {
		newsItem0Cell = Ad.findElement(byNewsItem0Cell);
		TestBase.clickOnElement(byNewsItem0Cell, newsItem0Cell, "News Card Details");
		TestBase.waitForMilliSeconds(6000);
		attachScreen();
	}

	
	public void navigateToNewsDetails() {

		try {
			//MobileElement newsDetails = Ad.findElementByAccessibilityId("news_item_0_cell_container");
			//newsDetails.click();
			newsItem0Cell = Ad.findElement(byNewsItem0Cell);
			TestBase.clickOnElement(byNewsItem0Cell, newsItem0Cell, "News Card Details");
			TestBase.waitForMilliSeconds(6000);
			attachScreen();
			System.out.println("Navigated to News Details ");
			logStep("Navigated to News Details ");
		} catch (Exception e) {
			System.out.println("News Details not displayed");
			logStep("News Details not displayed");
			// attachScreen();
		}
	}
	
	public  void scrollToNewsCard() throws Exception {
		//byNewsCard = MobileBy.AccessibilityId("news-card-containerView");
		//aQCard = Ad.findElement(byAirQualityCard);
		Functions.genericScroll(byNewsCard, true, true, getOffsetYTop(), TOLERANCE_FROM_TOP);
		//Functions.genericScrollTWC(byNewsCard, true, true, getOffsetYTop(), TOLERANCE_FROM_TOP, false, false);
	}
	
	@Step("Assert Ad On News Details Page")
	public void assertAdOnNewsDetailsPage() {
		try {
			advertisementOnNewsDetails = Ad.findElement(byAdvertisementOnNewsDetails);
			System.out.println("News Details Page Ad displayed");
			logStep("News Details Page Ad displayed");
		}catch (Exception e) {
			Assert.fail("Ad not displayed on News Details Page");
		}finally {
			attachScreen();
		}
		
	}
}

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

public class AndroidBreakingNewsCardScreen extends Utils {
	AppiumDriver<MobileElement> Ad;
	
	String breakingNewsCard_Id = "com.weather.Weather:id/card_breaking_news_view";
	String breakingNews_Id = "com.weather.Weather:id/breaking_news_grid_item_0";
	String breakingNow_Id = "com.weather.Weather:id/breaking_news_grid_item_1";
	
	String editorialBreakingNewsCard_Xpath = "//android.widget.FrameLayout[@resource-id=\"com.weather.Weather:id/card_videos_view\"]//android.widget.TextView[@text=\"Breaking News\"]";
	String editorialVideobreakingNews_Id = "com.weather.Weather:id/video_grid_item_0";
	
	By byBreakingNewsCard = MobileBy.id(breakingNewsCard_Id);
	By byEditorialBreakingNewsCard = MobileBy.xpath(editorialBreakingNewsCard_Xpath);
	By byEditorialVideobreakingNews = MobileBy.id(editorialVideobreakingNews_Id);
	
	MobileElement breakingNews = null;
	MobileElement breakingNewsCard = null;
	MobileElement editorialBreakingNewsCard = null;
	MobileElement editorialVideobreakingNews = null;
	
	

	public AndroidBreakingNewsCardScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}
	
	@Step("Navigate to Breaking News Card")
	public void navigateToBreakingNewsCard() throws Exception {

		Functions.genericScroll(byBreakingNewsCard, true, true, getOffsetYTop(), TOLERANCE_FROM_TOP);
	}

	@Step("Navigate to Breaking News Details Page")
	public void navigateToBreakingNewsDetailsPage() throws Exception {
		try {
			By byBreakingNews = null;
			try {
				byBreakingNews = MobileBy.id(breakingNews_Id);
				breakingNews = Ad.findElement(byBreakingNews);
				
			} catch (Exception e) {
				byBreakingNews = MobileBy.id(breakingNow_Id);
				breakingNews = Ad.findElement(byBreakingNews);
			}
			CharlesProxy.proxy.clearCharlesSession();
			TestBase.clickOnElement(byBreakingNews, breakingNews, "Breaking News Item");
			TestBase.waitForMilliSeconds(15000);
			System.out.println("Navigated to Breaking News Details page");
			logStep("Navigated to Breaking News Details page");
			attachScreen();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(Ad.getPageSource());
			System.out.println("Failed to Navigate to Breaking News Details page");
			logStep("Failed to Navigate to Breaking News Details page");
		}

	}
	
	@Step("Navigate to Editorial Video Headline Breaking News Card")
	public void navigateToEditorialVideoHeadlineBreakingNewsCard() throws Exception {

		Functions.genericScroll(byEditorialBreakingNewsCard, true, true, getOffsetYTop(), TOLERANCE_FROM_TOP);
	}
	
	@Step("Navigate to Editorial Video Headline Card Breaking News Details Page")
	public void navigateToEditorialVideoHeadlineCardBreakingNewsDetailsPage() throws Exception {
		try {
			editorialVideobreakingNews = Ad.findElement(byEditorialVideobreakingNews);
			CharlesProxy.proxy.clearCharlesSession();
			TestBase.clickOnElement(byEditorialVideobreakingNews, editorialVideobreakingNews, "Editorial Video Headline Card Breaking News Item");
			TestBase.waitForMilliSeconds(15000);
			System.out.println("Navigated to Editorial Video Headline Card Breaking News Details page");
			logStep("Navigated to Editorial Video Headline Card Breaking News Details page");
			attachScreen();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(Ad.getPageSource());
			System.out.println("Failed to Navigate to Editorial Video Headline Card Breaking News Details page");
			logStep("Failed to Navigate to Editorial Video Headline Card Breaking News Details page");
		}

	}
	
	@Step("Navigate to Breaking News Details Page")
	public void navigateToBreakingNewsDetailsPage(CharlesProxy proxy) throws Exception {
		try {
			By byBreakingNews = null;
			try {
				byBreakingNews = MobileBy.id(breakingNews_Id);
				breakingNews = Ad.findElement(byBreakingNews);
				
			} catch (Exception e) {
				byBreakingNews = MobileBy.id(breakingNow_Id);
				breakingNews = Ad.findElement(byBreakingNews);
			}
			proxy.clearCharlesSession();
			TestBase.clickOnElement(byBreakingNews, breakingNews, "Breaking News Item", proxy);
			TestBase.waitForMilliSeconds(15000);
			System.out.println("Navigated to Breaking News Details page");
			logStep("Navigated to Breaking News Details page");
			attachScreen();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(Ad.getPageSource());
			System.out.println("Failed to Navigate to Breaking News Details page");
			logStep("Failed to Navigate to Breaking News Details page");
		}

	}
	
	@Step("Navigate to Editorial Video Headline Card Breaking News Details Page")
	public void navigateToEditorialVideoHeadlineCardBreakingNewsDetailsPage(CharlesProxy proxy) throws Exception {
		try {
			editorialVideobreakingNews = Ad.findElement(byEditorialVideobreakingNews);
			proxy.clearCharlesSession();
			TestBase.clickOnElement(byEditorialVideobreakingNews, editorialVideobreakingNews, "Editorial Video Headline Card Breaking News Item", proxy);
			TestBase.waitForMilliSeconds(15000);
			System.out.println("Navigated to Editorial Video Headline Card Breaking News Details page");
			logStep("Navigated to Editorial Video Headline Card Breaking News Details page");
			attachScreen();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(Ad.getPageSource());
			System.out.println("Failed to Navigate to Editorial Video Headline Card Breaking News Details page");
			logStep("Failed to Navigate to Editorial Video Headline Card Breaking News Details page");
		}

	}

}

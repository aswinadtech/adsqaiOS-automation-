package com.twc.ios.app.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.twc.ios.app.general.Driver;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.Utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;

public class RadarNavTab extends Utils{
	AppiumDriver<MobileElement> Ad;
	
	String radarTab_AccessibilityId = "mapDetailContainerTab";
	String closeLight_AccessibilityId = "close light";
	String radarTabStickyTestAd_AccessibilityId = "weather.maps-adContentView";
	String radarTabStickyTestAdContainer_AccessibilityId = "weather.maps-adContainterView";
	String advertisementOnRadarTab_Xpath = "//XCUIElementTypeOther[@name=\"weather.maps-adContentView\"]";

	By byRadarNavTab = MobileBy.AccessibilityId(radarTab_AccessibilityId);
	By byCloseLight = MobileBy.AccessibilityId(closeLight_AccessibilityId);
	By byRadarTabStickyTestAd = MobileBy.AccessibilityId(radarTabStickyTestAd_AccessibilityId);
	By byRadarTabStickyTestAdContainer = MobileBy.AccessibilityId(radarTabStickyTestAdContainer_AccessibilityId);
	
	By byAdvertisementOnRadarTab = MobileBy.xpath(advertisementOnRadarTab_Xpath);

	MobileElement radarNavTab = null;
	MobileElement closeLight = null;
	MobileElement radarTabStickyTestAd = null;
	MobileElement radarTabStickyTestAdContainer = null;
	MobileElement advertisementOnRadarTab = null;

	public RadarNavTab(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigate To Radar Tab")
	public void navigateToRadarTab() throws Exception {
		try {
			radarNavTab = Ad.findElement(byRadarNavTab);
			TestBase.clickOnElement(byRadarNavTab, radarNavTab, "Radar Nav Tab");
			TestBase.waitForMilliSeconds(10000);
			System.out.println("Navigated to Radar tab ");
			logStep("Navigated to Radar tab");
			attachScreen();
			try {
				closeLight = Ad.findElement(byCloseLight);
				TestBase.clickOnElement(byCloseLight, closeLight, "Close Light icon");
				TestBase.waitForMilliSeconds(4000);
			} catch (Exception e) {
				System.out.println("Tooltip not displayed on map details page");
				logStep("Tooltip not displayed on map details page");
			}

		} catch (Exception e) {
			System.out.println("Radar tab not displayed");
			logStep("Radar tab not displayed");
			attachScreen();
		}

	}
	
	@Step("Verify Tapability of Sticky Test Ad On Radar Nav Tab")
	public void verifyTapabilityOfTestAdOnRadarNavTab() {
			try {
				radarTabStickyTestAd = Ad.findElement(byRadarTabStickyTestAd);
				TestBase.waitForMilliSeconds(2000);
				TestBase.clickOnElement(byRadarTabStickyTestAd, radarTabStickyTestAd, "Radar Nav Tab Sticky Test Ad");
				
				TestBase.waitForMilliSeconds(10000);
				if (TestBase.isElementExists(byRadarTabStickyTestAd)) {
					radarTabStickyTestAdContainer = Ad.findElement(byRadarTabStickyTestAdContainer);
					TestBase.waitForMilliSeconds(2000);
					TestBase.clickOnElement(byRadarTabStickyTestAdContainer, radarTabStickyTestAdContainer, "Radar Nav Tab Sticky Test Ad Container");
				}
			
				//Functions.checkForAppState();
				Assert.assertFalse(TestBase.isElementExists(byRadarTabStickyTestAd));
			} catch (Exception e) {
				try {
					radarTabStickyTestAd = Ad.findElement(byRadarTabStickyTestAd);
					TestBase.waitForMilliSeconds(2000);
					TestBase.clickOnElement(byRadarTabStickyTestAd, radarTabStickyTestAd, "Radar Nav Tab Sticky Test Ad");
					TestBase.waitForMilliSeconds(10000);
					if (TestBase.isElementExists(byRadarTabStickyTestAd)) {
						radarTabStickyTestAdContainer = Ad.findElement(byRadarTabStickyTestAdContainer);
						TestBase.waitForMilliSeconds(2000);
						TestBase.clickOnElement(byRadarTabStickyTestAdContainer, radarTabStickyTestAdContainer, "Radar Nav Tab Sticky Test Ad Container");
					}
					
					//Functions.checkForAppState();
					Assert.assertFalse(TestBase.isElementExists(byRadarTabStickyTestAd));
				} catch (Exception e1) {
					
					System.out.println("Radar Nav Tab Test Ad is Not displayed");
					logStep("Radar Nav Tab Test Ad is Not displayed");
					Assert.fail("Radar Nav Tab Test Ad is Not displayed");
				}
			} finally {
				attachScreen();
			}
	}
	
	@Step("Assert Ad On Radar Nav Tab")
	public void assertAdOnRadarNavTab() {
		try {
			advertisementOnRadarTab = Ad.findElement(byAdvertisementOnRadarTab);
			System.out.println("Radar Nav Tab Ad displayed");
			logStep("Radar Nav Tab Ad displayed");
		} catch (Exception e) {
			Assert.fail("Radar Nav Tab Ad is Not displayed");
		}finally {
			attachScreen();
		}
		
	}

}

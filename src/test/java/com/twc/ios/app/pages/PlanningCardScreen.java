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

public class PlanningCardScreen extends Utils {
	AppiumDriver<MobileElement> Ad;
	//String hourlyTab_Xpath = "//XCUIElementTypeButton[@name=' Hourly Tab']";
	String hourlyTab_AccessibilityId = "hourlyTab";
	//String selectedHourlyTab_Xpath = "//XCUIElementTypeButton[@name='Selected Hourly Tab']";
	String selectedHourlyTab_Xpath = "//XCUIElementTypeButton[@name='hourlyTab']";
	String hourlyGraph_Xpath = "//XCUIElementTypeOther[@name='hourlyGraph']";
	
	//String dailyTab_Xpath = "//XCUIElementTypeButton[@name=' Daily Tab']";
	String dailyTab_Xpath = "//XCUIElementTypeButton[@name='dailyTab']";
	String dailyGraph_Xpath = "//XCUIElementTypeOther[@name='dailyGraph']";
	String tooltipClose_AccessibilityId = "tooltip close";
	
	//String todayTab_Xpath = "//XCUIElementTypeButton[@name=' Today Tab']";
	String todayTab_Xpath = "//XCUIElementTypeButton[@name='todayTab']";
	String todayGraph_Xpath = "//XCUIElementTypeOther[@name='todayGraph']";
	

	By byHourlyGraph = MobileBy.xpath(hourlyGraph_Xpath);
	By byDailyTab = MobileBy.xpath(dailyTab_Xpath);
	By byDailyGraph = MobileBy.xpath(dailyGraph_Xpath);
	By byToolTip = MobileBy.AccessibilityId(tooltipClose_AccessibilityId);
	By byTodayTab = MobileBy.xpath(todayTab_Xpath);
	By byTodayGraph = MobileBy.xpath(todayGraph_Xpath);
	
	By byPlanningCard = MobileBy.AccessibilityId("planning-card-containerView");

	MobileElement planningCard = null;
	MobileElement hourlyTab = null;
	MobileElement selectedHourlyTab = null;
	MobileElement hourlyGraph = null;
	MobileElement dailyTab = null;
	MobileElement dailyGraph = null;
	MobileElement toolTip = null;
	MobileElement todayTab = null;
	MobileElement todayGraph = null;
	
	

	public PlanningCardScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}
	
	@Step("Scroll To Planning Card")
	public  void scrollToPlanningCard() throws Exception {
		//planningCard = Ad.findElement(byPlanningCard);
		//Functions.genericScroll(byPlanningCard, true, true, getOffsetYTop(), TOLERANCE_FROM_TOP);
		//Functions.swipe(byPlanningCard, Direction.LEFT,  0.15);
		Functions.genericScrollTWC(byPlanningCard, true, true, getOffsetYTop(), TOLERANCE_FROM_TOP, false, false);
		//Functions.swipe(planningCard, Direction.LEFT,  0.15);
		
	}
	
	@Step("Swipe Left On Planning Card")
	public  void swipeLeftOnPlanningCard() throws Exception {
		planningCard = Ad.findElement(byPlanningCard);
	    Functions.swipe(planningCard, Direction.LEFT,  0.15);
		
	}
	
	@Step("Swipe Right On Planning Card")
	public  void swipeRightOnPlanningCard() throws Exception {
		planningCard = Ad.findElement(byPlanningCard);
		Functions.swipe(planningCard, Direction.RIGHT,  0.15);
		
	}

	@Step("Navigate To Hourly Tab from Planning Card")
	public void navigateToHourlyTabFromPlanningCard() {
		try {
			By byHourlyTab = null;
			try {
				//byHourlyTab = MobileBy.xpath(hourlyTab_Xpath);
				byHourlyTab = MobileBy.AccessibilityId(hourlyTab_AccessibilityId);
				hourlyTab = Ad.findElement(byHourlyTab);
				
			} catch (Exception e) {
				byHourlyTab = MobileBy.xpath(selectedHourlyTab_Xpath);
				hourlyTab = Ad.findElement(byHourlyTab);
				
			}
			TestBase.clickOnElement(byHourlyTab, hourlyTab, "Hourly Tab");
			TestBase.waitForMilliSeconds(5000);
			attachScreen();
		} catch (Exception e) {
			System.out.println("Hourly Tab not displayed");
			logStep("Hourly Tab not displayed");
			attachScreen();
		}
	}
	
	@Step("Navigate To Hourly Details from Planning Card")
	public void navigateToHourlyDetailsFromPlanningCard() {
		try {
			By byHourlyTab = null;
			try {
				//byHourlyTab = MobileBy.xpath(hourlyTab_Xpath);
				byHourlyTab = MobileBy.AccessibilityId(hourlyTab_AccessibilityId);
				hourlyTab = Ad.findElement(byHourlyTab);
				
			} catch (Exception e) {
				byHourlyTab = MobileBy.xpath(selectedHourlyTab_Xpath);
				hourlyTab = Ad.findElement(byHourlyTab);
				
			}
			TestBase.clickOnElement(byHourlyTab, hourlyTab, "Hourly Tab");
			TestBase.waitForMilliSeconds(5000);

			hourlyGraph = Ad.findElement(byHourlyGraph);
			TestBase.clickOnElement(byHourlyGraph, hourlyGraph, "Hourly Graph");
			TestBase.waitForMilliSeconds(5000);

			System.out.println("Navigated to Hourly Details from Planning Card");
			logStep("Navigated to Hourly Details from Planning Card");
			attachScreen();
			/*
			 * Since Hourly has Entry Interstitial, handling it once navigated to Hourly Tab
			 */
			CharlesProxy.proxy.stopRecording();
			Functions.archive_folder("Charles");
			TestBase.waitForMilliSeconds(5000);
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
			Functions.delete_folder("Charles");	
			CharlesProxy.proxy.startRecording();

		} catch (Exception e) {
			System.out.println("Hourly Details not displayed");
			logStep("Hourly Details not displayed");
			attachScreen();
		}
	}
	
	@Step("Navigate To Hourly Details from Planning Card And Not to Handle Interstitials")
	public void navigateToHourlyDetailsFromPlanningCardAndDontHandleInterstitials() {
		try {
			By byHourlyTab = null;
			try {
				//byHourlyTab = MobileBy.xpath(hourlyTab_Xpath);
				byHourlyTab = MobileBy.AccessibilityId(hourlyTab_AccessibilityId);
				hourlyTab = Ad.findElement(byHourlyTab);
				
			} catch (Exception e) {
				byHourlyTab = MobileBy.xpath(selectedHourlyTab_Xpath);
				hourlyTab = Ad.findElement(byHourlyTab);
				
			}
			TestBase.clickOnElement(byHourlyTab, hourlyTab, "Hourly Tab");
			TestBase.waitForMilliSeconds(5000);

			hourlyGraph = Ad.findElement(byHourlyGraph);
			TestBase.clickOnElement(byHourlyGraph, hourlyGraph, "Hourly Graph");
			TestBase.waitForMilliSeconds(5000);

			System.out.println("Navigated to Hourly Details from Planning Card");
			logStep("Navigated to Hourly Details from Planning Card");
			attachScreen();

		} catch (Exception e) {
			System.out.println("Hourly Details not displayed");
			logStep("Hourly Details not displayed");
			attachScreen();
		}
	}
	
	@Step("Navigate To Daily Tab from Planning Card")
	public void navigateToDailyTabFromPlanningCard() {
		try {
			
			dailyTab = Ad.findElement(byDailyTab);
			TestBase.clickOnElement(byDailyTab, dailyTab, "Daily Tab");
			TestBase.waitForMilliSeconds(5000);
			attachScreen();
		} catch (Exception e) {
			System.out.println("Daily Tab not displayed");
			logStep("Daily Tab not displayed");
			attachScreen();
		}
	}

	@Step("Navigate To Daily Details from Planning Card")
	public void navigateToDailyDetailsFromPlanningCard() {
		try {
			
			dailyTab = Ad.findElement(byDailyTab);
			TestBase.clickOnElement(byDailyTab, dailyTab, "Daily Tab");
			TestBase.waitForMilliSeconds(5000);
			
			
			dailyGraph = Ad.findElement(byDailyGraph);
			TestBase.clickOnElement(byDailyGraph, dailyGraph, "Daily Graph");
			TestBase.waitForMilliSeconds(5000);
			
			System.out.println("Navigated to Daily Details from Planning Card");
			logStep("Navigated to Daily Details from Planning Card");
			attachScreen();
			try {
				toolTip = Ad.findElement(byToolTip);
				TestBase.clickOnElement(byToolTip, toolTip, "Tooltip");
				TestBase.waitForMilliSeconds(4000);
			} catch (Exception e) {
				System.out.println("Tooltip not displayed on daily details page");
				logStep("Tooltip not displayed on daily details page");
			}
		} catch (Exception e) {
			System.out.println("Daily Details not displayed");
			logStep("Daily Details not displayed");
			attachScreen();
		}
	}
	
	@Step("Navigate To Daily Details from Planning Card And Not to Handle Interstitials")
	public void navigateToDailyDetailsFromPlanningCardAndDontHandleInterstitials() {
		try {
			
			dailyTab = Ad.findElement(byDailyTab);
			TestBase.clickOnElement(byDailyTab, dailyTab, "Daily Tab");
			TestBase.waitForMilliSeconds(5000);
			
			
			dailyGraph = Ad.findElement(byDailyGraph);
			TestBase.clickOnElement(byDailyGraph, dailyGraph, "Daily Graph");
			TestBase.waitForMilliSeconds(5000);
			
			System.out.println("Navigated to Daily Details from Planning Card");
			logStep("Navigated to Daily Details from Planning Card");
			attachScreen();
			try {
				
				toolTip = Ad.findElement(byToolTip);
				TestBase.clickOnElement(byToolTip, toolTip, "Tooltip");
				TestBase.waitForMilliSeconds(4000);
			} catch (Exception e) {
				System.out.println("Tooltip not displayed on daily details page");
				logStep("Tooltip not displayed on daily details page");
			}
		} catch (Exception e) {
			System.out.println("Daily Details not displayed");
			logStep("Daily Details not displayed");
			attachScreen();
		}
	}
	
	@Step("Navigate To Today Tab from Planning Card")
	public void navigateToTodayTabFromPlanningCard() {

		try {
			
			todayTab = Ad.findElement(byTodayTab);
			TestBase.clickOnElement(byTodayTab, todayTab, "Today Tab");
			TestBase.waitForMilliSeconds(5000);
			attachScreen();
			
		} catch (Exception e) {
			System.out.println("Today Tab not displayed");
			logStep("Today Tab not displayed");
			attachScreen();
		}
	}
	
	@Step("Navigate To Today Details from Planning Card")
	public void navigateToTodayDetailsFromPlanningCard() {

		try {
			
			todayTab = Ad.findElement(byTodayTab);
			TestBase.clickOnElement(byTodayTab, todayTab, "Today Tab");
			TestBase.waitForMilliSeconds(5000);
			
			
			todayGraph = Ad.findElement(byTodayGraph);
			TestBase.clickOnElement(byTodayGraph, todayGraph, "Today Graph");
			TestBase.waitForMilliSeconds(5000);
			
			
			System.out.println("Navigated to Today Details from Planning Card");
			logStep("Navigated to Today Details from Planning Card");
			attachScreen();
			/*
			 * Since Today Details has Entry Interstitial, handling it once navigated to Hourly Tab
			 */
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

		} catch (Exception e) {
			System.out.println("Today Details not displayed");
			logStep("Today Details not displayed");
			attachScreen();
		}
	}

}

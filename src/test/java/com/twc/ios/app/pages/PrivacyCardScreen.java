package com.twc.ios.app.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.twc.ios.app.general.Driver;
import com.twc.ios.app.general.ReadExcelValues;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.Utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;

public class PrivacyCardScreen extends Utils {
	AppiumDriver<MobileElement> Ad;

	String radarCard_Xpath = "//XCUIElementTypeOther[@name='map-card-containerView']//XCUIElementTypeButton[@name='contentClickAction']";
	String closeLight_AccessibilityId = "close light";

	By byRadarCard = MobileBy.xpath(radarCard_Xpath);

	MobileElement radarCard = null;
	MobileElement closeLight = null;

	public PrivacyCardScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigate to Privacy Page")
	public void navigateToPrivacyPage(String Excelname, String sheetName) throws Exception {

		ReadExcelValues.excelValues(Excelname, sheetName);
		try {
			Ad.findElementByName(ReadExcelValues.data[22][Cap]).click();
			TestBase.waitForMilliSeconds(5000);
			attachScreen();
			System.out.println("Clicked on Privacy Card to navigate to Privacy page");
			logStep("Clicked on Privacy Card to navigate to Privacy page");
		} catch (Exception e) {

			try {
				Ad.findElementByName(ReadExcelValues.data[26][Cap]).click();
				TestBase.waitForMilliSeconds(5000);
				attachScreen();
				System.out.println("Clicked on Privacy Card to navigate to Privacy page");
				logStep("Clicked on Privacy Card to navigate to Privacy page");
			} catch (Exception e1) {
				System.out.println("Cannot click on Privacy and Settings link on Privacy Card");
				logStep("Cannot click on Privacy and Settings link on Privacy Card");
				attachScreen();
				Assert.fail("Cannot click on Privacy and Settings link on Privacy Card");
			}
		}

	}

	@Step("Verify Privacy Card Options")
	public void verify_PrivacyCard_Options(String Excelname, String sheetName) throws Exception {
		ReadExcelValues.excelValues(Excelname, sheetName);
		String privacy_Optin_label = null;
		String privacy_Optout_label = null;
		String privacy_Optin_default_label = null;
		MobileElement privacy_Optout = null;
		MobileElement privacy_Optin = null;
		MobileElement privacy_Optin_default = null;
		try {
			navigateToPrivacyPage(Excelname, sheetName);
			System.out.println("Returned From 'Navigate to Privacy'");
			logStep("Returned From 'Navigate to Privacy'");
			TestBase.waitForMilliSeconds(30000);
		} catch (Exception e) {
			System.out.println("An exception while navigating to Privacy Page");
			logStep("An exception while navigating to Privacy Page");
		}

		try {
			System.out.println("Current Context is: " + Ad.getContext());
			logStep("Current Context is: " + Ad.getContext());
			try {
				Ad.context("NATIVE_APP");
				System.out.println("Switching to Native App Context");
				logStep("Switching to Native App Context");
			} catch (Exception ex) {
				System.out.println("An Exception while Switching to Native App Context");
				logStep("An Exception while Switching to Native App Context");
			}
			swipe_Up(Ad);
			swipe_Up(Ad);
			swipe_Up(Ad);
			TestBase.waitForMilliSeconds(5000);
			// swipe_Up();

			try {
				privacy_Optin = Ad.findElementByXPath(ReadExcelValues.data[23][Cap]);
				privacy_Optin_label = privacy_Optin.getAttribute("name");
				try {
					privacy_Optout = Ad.findElementByXPath(ReadExcelValues.data[24][Cap]);
					privacy_Optout_label = privacy_Optout.getAttribute("name");
				} catch (Exception e) {
					privacy_Optout = Ad.findElementByXPath(ReadExcelValues.data[27][Cap]);
					privacy_Optout_label = privacy_Optout.getAttribute("name");
				}
				privacy_Optin_default = Ad.findElementByXPath(ReadExcelValues.data[25][Cap]);
				privacy_Optin_default_label = privacy_Optin_default.getAttribute("value");
				if (!privacy_Optin_default_label.equalsIgnoreCase("1")) {
					System.out.println("By default Privacy Option is set to false");
					logStep("By default Privacy Option is set to false");
					attachScreen();
					Assert.fail("By default Privacy Option is set to false");
				} else {
					System.out.println("By default Privacy Option is set to true");
					logStep("By default Privacy Option is set to true");
				}
			} catch (Exception e) {
				System.out.println("Privacy feature options are not displayed on the Privacy Settings page");
				logStep("Privacy feature options are not displayed on the Privacy Settings page");
				attachScreen();
				Assert.fail("Privacy feature options are not displayed on the Privacy Settings page");
			}
		} catch (Exception e) {
			System.out.println(
					"There is an exception while validating Privacy feature options on the Privacy Settings page");
			logStep("There is an exception while validating Privacy feature options on the Privacy Settings page");
		} finally {
			navigateBackToFeedCard();
			attachScreen();
			/*
			 * if (!interStitialChecked) { handle_Interstitial_Ad(); }
			 */

		}
	}

	@Step("Select Privacy Optout")
	public void select_Privacy_Optout(String Excelname, String sheetName) throws Exception {
		ReadExcelValues.excelValues(Excelname, sheetName);
		String privacy_Optin_label = null;
		String privacy_Optout_label = null;
		String privacy_Optin_default_label = null;
		MobileElement privacy_Optout = null;
		MobileElement privacy_Optin = null;
		MobileElement privacy_Optin_default = null;
		navigateToPrivacyPage(Excelname, sheetName);
		System.out.println("Returned From 'Navigate to Privacy'");
		logStep("Returned From 'Navigate to Privacy'");
		TestBase.waitForMilliSeconds(30000);
		swipe_Up(Ad);
		swipe_Up(Ad);
		swipe_Up(Ad);
		TestBase.waitForMilliSeconds(5000);
		// swipe_Up();
		try {
			privacy_Optin = Ad.findElementByXPath(ReadExcelValues.data[23][Cap]);
			privacy_Optin_label = privacy_Optin.getAttribute("name");
			try {
				privacy_Optout = Ad.findElementByXPath(ReadExcelValues.data[24][Cap]);
				privacy_Optout_label = privacy_Optout.getAttribute("name");
			} catch (Exception e) {
				privacy_Optout = Ad.findElementByXPath(ReadExcelValues.data[27][Cap]);
				privacy_Optout_label = privacy_Optout.getAttribute("name");
			}
			privacy_Optin_default = Ad.findElementByXPath(ReadExcelValues.data[25][Cap]);
			privacy_Optin_default_label = privacy_Optin_default.getAttribute("value");

			if (!privacy_Optin_default_label.equalsIgnoreCase("1")) {
				System.out.println("Already Privacy Optout is selected");
				logStep("Already Privacy Optout is selected");
				attachScreen();

			} else {
				try {
					privacy_Optout.click();
					System.out.println("Privacy Optout is selected");
					logStep("Privacy Optout is selected");
					attachScreen();
				} catch (Exception e) {
					System.out.println("An exception while selecting optout");
					logStep("An exception while selecting optout");
					attachScreen();
					// Assert.fail("An exception while selecting optout");
				}

			}
		} catch (Exception e) {
			System.out.println("Privacy feature options are not displayed on the Privacy Settings page");
			logStep("Privacy feature options are not displayed on the Privacy Settings page");
			attachScreen();
			// Assert.fail("Privacy feature options are not displayed on the Privacy
			// Settings page");
		} finally {
			navigateBackToFeedCard();
			attachScreen();
			/*
			 * if (!interStitialChecked) { handle_Interstitial_Ad(); }
			 */

			/*
			 * CharlesProxy.proxy.clearCharlesSession(); System.out.
			 * println("Kill and Launching the app after privacy optout is selected");
			 * logStep("Kill and Launching the app after privacy optout is selected");
			 * Functions.close_launchApp();
			 */
		}

	}

	@Step("Select Privacy Optin")
	public void select_Privacy_Optin(String Excelname, String sheetName) throws Exception {
		ReadExcelValues.excelValues(Excelname, sheetName);
		String privacy_Optin_label = null;
		String privacy_Optout_label = null;
		String privacy_Optin_default_label = null;
		MobileElement privacy_Optout = null;
		MobileElement privacy_Optin = null;
		MobileElement privacy_Optin_default = null;
		navigateToPrivacyPage(Excelname, sheetName);
		System.out.println("Returned From 'Navigate to Privacy'");
		logStep("Returned From 'Navigate to Privacy'");
		TestBase.waitForMilliSeconds(30000);
		swipe_Up(Ad);
		swipe_Up(Ad);
		swipe_Up(Ad);
		TestBase.waitForMilliSeconds(5000);
		// swipe_Up();
		try {
			privacy_Optin = Ad.findElementByXPath(ReadExcelValues.data[23][Cap]);
			privacy_Optin_label = privacy_Optin.getAttribute("name");
			try {
				privacy_Optout = Ad.findElementByXPath(ReadExcelValues.data[24][Cap]);
				privacy_Optout_label = privacy_Optout.getAttribute("name");
			} catch (Exception e) {
				privacy_Optout = Ad.findElementByXPath(ReadExcelValues.data[27][Cap]);
				privacy_Optout_label = privacy_Optout.getAttribute("name");
			}
			privacy_Optin_default = Ad.findElementByXPath(ReadExcelValues.data[25][Cap]);
			privacy_Optin_default_label = privacy_Optin_default.getAttribute("value");
			if (privacy_Optin_default_label.equalsIgnoreCase("1")) {
				System.out.println("Privacy Option is already set to optin");
				logStep("Privacy Option is already set to optin");
				attachScreen();

			} else {
				try {
					privacy_Optin.click();
					TestBase.waitForMilliSeconds(3000);
					click_Continue();
					System.out.println("Privacy Optin is selected");
					logStep("Privacy Optin is selected");
					attachScreen();
				} catch (Exception e) {
					System.out.println("An exception while selecting optin");
					logStep("An exception while selecting optin");
					attachScreen();
					// Assert.fail("An exception while selecting optin");
				}
			}
		} catch (Exception e) {
			System.out.println("Privacy feature options are not displayed on the Privacy Settings page");
			logStep("Privacy feature options are not displayed on the Privacy Settings page");
			attachScreen();
			// Assert.fail("Privacy feature options are not displayed on the Privacy
			// Settings page");
		} finally {
			navigateBackToFeedCard();
			attachScreen();
			/*
			 * if (!interStitialChecked) { handle_Interstitial_Ad(); }
			 */
			/*
			 * CharlesProxy.proxy.clearCharlesSession(); System.out.
			 * println("Kill and Launching the app after privacy optin is selected");
			 * logStep("Kill and Launching the app after privacy optin is selected");
			 * Functions.close_launchApp();
			 */
		}

	}

}

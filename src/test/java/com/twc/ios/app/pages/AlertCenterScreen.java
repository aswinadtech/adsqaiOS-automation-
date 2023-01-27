package com.twc.ios.app.pages;

import org.openqa.selenium.By;

import com.twc.ios.app.functions.Functions;
import com.twc.ios.app.general.Driver;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.Utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;

public class AlertCenterScreen extends Utils {
	AppiumDriver<MobileElement> Ad;

	String alertCenter_AccessibilityId = "AlertsCenter";
	String doneButton_AccessibilityId = "Done";
	String settingsBackButton_AccessibilityId = "Settings back button";

	By byAlertCenter = MobileBy.AccessibilityId(alertCenter_AccessibilityId);
	By byDoneButton = MobileBy.AccessibilityId(doneButton_AccessibilityId);
	By bySettingsBackButton = MobileBy.AccessibilityId(settingsBackButton_AccessibilityId);
	
	

	MobileElement alertCenter = null;
	MobileElement doneButton = null;
	MobileElement settingsBackButton = null;

	public AlertCenterScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigate to Alerts Center")
	public void navigateToMyAlerts() {
		clickOnAlertsCenter();

		// Navigate back from My Alerts Page by clickon 'Done' button
		try {
			doneButton = Ad.findElement(byDoneButton);
			TestBase.clickOnElement(byDoneButton, doneButton, "Done Button");
			System.out.println("Clicked on Done button on My Alerts");
			logStep("Clicked on Done button on My Alerts");
			attachScreen();
		} catch (Exception e) {
			try {
				settingsBackButton = Ad.findElement(bySettingsBackButton);
				TestBase.clickOnElement(bySettingsBackButton, settingsBackButton, "Settings Back Button");
				System.out.println("Clicked on Done button on My Alerts");
				logStep("Clicked on Done button on My Alerts");
				attachScreen();
			} catch (Exception e1) {
				System.out.println("Not able to Click on Done button on My Alerts");
				logStep("Not able to Click on Done button on My Alerts");
				attachScreen();
			}
		}
	}

	@Step("Click on Alerts Center")
	public void clickOnAlertsCenter() {
		try {
			alertCenter = Ad.findElement(byAlertCenter);
			TestBase.clickOnElement(byAlertCenter, alertCenter, "Alert Center");
			TestBase.waitForMilliSeconds(5000);
			System.out.println("Navigated to My Alerts");
			logStep("Navigated to My Alerts");
			attachScreen();

		} catch (Exception e) {
			System.out.println("Alerts Center not displayed");
			logStep("Alerts Center not displayed");
			attachScreen();
		}
	}

}

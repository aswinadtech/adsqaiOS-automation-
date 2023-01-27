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

public class SnapshotCardScreen extends Utils {
	AppiumDriver<MobileElement> Ad;
	String snapshotStories_AccessibilityId = "snapshot-stories-card_detailedButton";
	String snapshotLogo_AccessibilityId = "SnapshotLogo";
	String buttonClose_AccessibilityId  = "button close";
	
	By bySnapshotStories = MobileBy.AccessibilityId(snapshotStories_AccessibilityId);
	By bySnapshotLogo = MobileBy.AccessibilityId(snapshotLogo_AccessibilityId);
	By byButtonClose = MobileBy.AccessibilityId(buttonClose_AccessibilityId);
	

	MobileElement snapshotStories = null;
	MobileElement snapshotLogo = null;
	MobileElement buttonClose = null;

	public SnapshotCardScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigate To Snapthot Card Content Page")
	public void navigateToSnapshotCardContentPage() throws Exception {

		/*MobileElement seeStories = Ad
				.findElementByAccessibilityId("snapshot-stories-card_detailedButton");
		seeStories.click();*/
		snapshotStories = Ad.findElement(bySnapshotStories);
		TestBase.clickOnElement(bySnapshotStories, snapshotStories, "Snapshot Stories Button");
		Thread.sleep(6000);
		// ScreenShot(cardName,"Passed");
		// attachScreen();
		for (int i = 0; i < 10; i++) {
			try {
				snapshotLogo = Ad.findElement(bySnapshotLogo);
				//String snapText = Ad.findElementByAccessibilityId("SnapshotLogo").getAttribute("name");
				String snapText = TestBase.getElementAttribute(snapshotLogo, "name");
				System.out.println("Scrolled till last screen and Snapshot Logo is displayed");
				//Ad.findElementByAccessibilityId("button close").click();
				buttonClose = Ad.findElement(byButtonClose);
				TestBase.clickOnElement(byButtonClose, buttonClose, "Close Button");
				break;
				// ScreenShot(Pagename,"Passed");
				// attachScreen();
			} catch (Exception e) {
				Functions.scroll_Right();
			}
		}

	}

}

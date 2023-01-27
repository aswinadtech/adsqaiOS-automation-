package com.twc.ios.app.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import com.twc.ios.app.functions.Functions;
import com.twc.ios.app.general.Driver;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.Utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Step;

public class CurrentConditionsCardScreen extends Utils {
	AppiumDriver<MobileElement> Ad;

	String currentConditionCard_AccessibilityId = "current-condition-updated-card-containerView";

	By byCurrentConditioncard = MobileBy.AccessibilityId(currentConditionCard_AccessibilityId);

	MobileElement currentConditionCard = null;
	
	

	public CurrentConditionsCardScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	
	@Step("Swipe Up on Current Condition Card")
	public void swipe_Up_CurrentConditionCard() throws Exception {
		int relativeY = 80;
		boolean iscurrentconditioncard = false;
		Dimension dimensions = Ad.manage().window().getSize();
		int startY11 = dimensions.getHeight();

		//MobileElement currentCard = null;
		try {
			currentConditionCard = Ad.findElement(byCurrentConditioncard);
			iscurrentconditioncard = true;
		} catch (Exception e) {
			System.out.println("Current Conditions Card Not displayed");
			logStep("Current Conditions Card Not displayed");
		}

		Dimension d = currentConditionCard.getSize();
		System.out.println("Current-Condition Card Height is : " + d.getHeight());
		logStep("Current-Condition Card Height is : " + d.getHeight());
		System.out.println("Current-Condition Card  Width is: " + d.getWidth());
		logStep("Current-Condition Card  Width is: " + d.getWidth());

		int startx = d.width / 2;
		Double startY1 = d.getHeight() * 0.00;
		startY = startY1.intValue();
		startY = 0;
		Double endY1 = (double) ((d.getHeight()) * 1); // dimensions.getHeight() 0.2; == 512.0
		endY = endY1.intValue();
		int reEndY = 0;
		int variance = 21;
		int absStartY = endY + variance + relativeY;
		TouchAction ta = new TouchAction(Ad);
		TouchAction ta1 = new TouchAction(Ad);

		if (endY > 510) {
			reEndY = endY - 510;
			endY = 510;

			ta.press(PointOption.point(0, endY + startY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
					.moveTo(PointOption.point(0, relativeY)).release().perform();

			ta1.press(PointOption.point(0, reEndY + gapY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
					.moveTo(PointOption.point(0, relativeY)).release().perform();
		} else {

			ta.press(PointOption.point(0, absStartY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
					.moveTo(PointOption.point(0, relativeY)).release().perform();
		}

	}
	
	public MobileElement returnCurrentConditionCard(MobileElement element) {
		return element.findElement(byCurrentConditioncard);
	}
		

}

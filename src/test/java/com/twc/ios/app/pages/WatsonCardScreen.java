package com.twc.ios.app.pages;

import org.openqa.selenium.By;

import com.twc.ios.app.general.Driver;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.Utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;

public class WatsonCardScreen extends Utils{
	AppiumDriver<MobileElement> Ad;
	
	String watsonColdAndFluCard_Name = "watson-cold-and-flu-card-containerView";
	String watsonColdAndFluDetails_AccessibilityId = "viewWMMapContainer";
	String watsonAllergyCard_Name = "watson-allergy-card-containerView";
	String watsonAllergyDetails_AccessibilityId = "buttonGetPreparedAction";

	By byWatsonColdAndFluCard = MobileBy.name(watsonColdAndFluCard_Name);
	By byWatsonColdAndFluDetails = MobileBy.AccessibilityId(watsonColdAndFluDetails_AccessibilityId);
	By byWatsonAllergyCard = MobileBy.name(watsonAllergyCard_Name);
	By byWatsonAllergyDetails = MobileBy.AccessibilityId(watsonAllergyDetails_AccessibilityId);
	

	MobileElement watsonColdAndFluCard = null;
	MobileElement watsonColdAndFluDetails = null;
	MobileElement watsonAllergyCard = null;
	MobileElement watsonAllergyDetails = null;

	public WatsonCardScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigate To Watson Card Content Page")
	public void navigateToWatsonCardContentPage(boolean includeDetailsPages) throws Exception {

		try {
			watsonColdAndFluCard = Ad.findElement(byWatsonColdAndFluCard);
			if (includeDetailsPages) {
				//Ad.findElementByAccessibilityId("viewWMMapContainer").click();
				watsonColdAndFluDetails = Ad.findElement(byWatsonColdAndFluDetails);
				TestBase.clickOnElement(byWatsonColdAndFluDetails, watsonColdAndFluDetails, "Cold And Flu Details Button");
				Thread.sleep(3000);

				navigateBackToFeedCard();
			}
			/*if (Ad.findElementByName("watson-cold-and-flu-card-containerView").isDisplayed()) {
				if (includeDetailsPages) {
					Ad.findElementByAccessibilityId("viewWMMapContainer").click();
					Thread.sleep(3000);

					navigateBackToFeedCard();

				}

			}*/
		} catch (Exception e) {
			System.out.println("Watson Flu Card is not diplayed");
			logStep("Watson Flu Card is not diplayed");
		}

		try {
			watsonAllergyCard = Ad.findElement(byWatsonAllergyCard);
			if (includeDetailsPages) {
				//Ad.findElementByAccessibilityId("viewWMMapContainer").click();
				watsonAllergyDetails = Ad.findElement(byWatsonAllergyDetails);
				TestBase.clickOnElement(byWatsonAllergyDetails, watsonAllergyDetails, "Allergy Details Button");
				Thread.sleep(3000);

				navigateBackToFeedCard();
			}
			/*if (Ad.findElementByName("watson-allergy-card-containerView").isDisplayed()) {
				if (includeDetailsPages) {
					Ad.findElementByAccessibilityId("buttonGetPreparedAction").click();
					Thread.sleep(3000);

					navigateBackToFeedCard();

				}

			}*/
		} catch (Exception e) {
			System.out.println("Watson allergy Card is not diplayed");
			logStep("Watson allergy Card is not diplayed");
		}

	
	}

}

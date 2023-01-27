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

public class HurricaneCentralCardScreen extends Utils {
	AppiumDriver<MobileElement> Ad;
	String dailyDetails_AccessibilityId = "dailyCollectionViewCell_0";
	String tooltipClose_AccessibilityId = "tooltip close";
	By byDailyDetails = MobileBy.AccessibilityId(dailyDetails_AccessibilityId);
	

	MobileElement dailyDetails = null;
	MobileElement toolTip = null;

	public HurricaneCentralCardScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigate To Hurricane Central Card Content Page")
	public void navigateToHurricaneCentralCardContentPage() throws Exception {
		/*
		 * // MobileElement hcDetails = //
		 * Ad.findElementByAccessibilityId("hurricane-central-card_detailedButton");
		 * MobileElement hcDetails = Ad.findElementByXPath(
		 * "//XCUIElementTypeOther[@name='hurricane-central-card-containerView']//XCUIElementTypeButton[@name='contentClickAction']"
		 * ); hcDetails.click(); Thread.sleep(2000);
		 * 
		 * // ScreenShot(cardName,"Passed"); // attachScreen();
		 * 
		 * try { Ad.findElementByAccessibilityId("close light").click();
		 * 
		 * } catch (Exception e) { System.out.
		 * println("Siri popup not present validating Hurricance Central Details Page ad"
		 * );
		 * 
		 * } try { MobileElement hcMaps = Ad
		 * .findElementByAccessibilityId("button_hurricanePathFeedItemContainerMap"); //
		 * MobileElement hcMaps = // Ad.findElementByXPath(
		 * "//XCUIElementTypeOther[@name='hurricane-central-card-containerView']//XCUIElementTypeButton[@name='contentClickAction']"
		 * ); hcMaps.click(); Thread.sleep(2000); navigateBackToFeedCard(); if
		 * (unlimitedInterstitial) { handle_Interstitial_Ad(); } } catch (Exception e) {
		 * System.out.println("HC Maps button is not available"); }
		 * 
		 * for (int k = 0; k <= 10; k++) { // navigating to video try { if
		 * (Ad.findElementByAccessibilityId("view_videoFeedItem").isDisplayed()) {
		 * Ad.findElementByAccessibilityId("view_videoFeedItem").click();
		 * Thread.sleep(2000); navigateBackToFeedCard(); if (unlimitedInterstitial) {
		 * handle_Interstitial_Ad(); } break; } } catch (Exception e) {
		 * Functions.swipe_Up(); }
		 * 
		 * }
		 * 
		 * for (int n = 0; n <= 10; n++) { // navigating to articles try { if
		 * (Ad.findElementByAccessibilityId("view_newsFeedItem").isDisplayed()) {
		 * Ad.findElementByAccessibilityId("view_newsFeedItem").click();
		 * Thread.sleep(2000); navigateBackToFeedCard(); if (unlimitedInterstitial) {
		 * handle_Interstitial_Ad(); } break; } } catch (Exception e) {
		 * Functions.swipe_Up(); }
		 * 
		 * }
		 * 
		 * for (int p =0;p<=10;p++) { //navigating to Hurricane Bulletins-- looks these
		 * are dynamic... in complete... try { if (Ad.findElementByAccessibilityId(
		 * "label_NHCBulletinsFeedItemContainerPublicAdvisoryAdvisory").isDisplayed()) {
		 * Ad.findElementByAccessibilityId(
		 * "label_NHCBulletinsFeedItemContainerPublicAdvisoryAdvisory").click();
		 * Thread.sleep(2000);
		 * Ad.findElementByAccessibilityId("arrow left light").click(); break; }
		 * }catch(Exception e1) { Functions.scroll_Down(); }
		 * 
		 * }
		 * 
		 * 
		 * navigateBackToFeedCard(); if (unlimitedInterstitial) {
		 * handle_Interstitial_Ad(); }
		 */}

}

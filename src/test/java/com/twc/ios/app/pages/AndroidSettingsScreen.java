package com.twc.ios.app.pages;

import java.io.File;
import java.io.FileOutputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.twc.ios.app.functions.Functions;
import com.twc.ios.app.general.Driver;
import com.twc.ios.app.general.ReadExcelValues;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.Utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Step;

public class AndroidSettingsScreen extends Utils {
	AppiumDriver<MobileElement> Ad;
	String settingsButton_AccessibilityId = "Setting icon";
	String privacySettingsLabel_Xpath = "//android.widget.TextView[@text=\"Privacy Settings\"]";
	String privacySettingsCell_AccessibilityId = "privacy_settings_cell";
	String applicationVersion_AccessibilityId = "app_version";
	String settingsBackButton_AccessibilityId = "Settings back button";
	String aboutLabel_Name = "about_label";
	String closeMenuButton_Xpath = "(//XCUIElementTypeButton[@name='close_menu_button'])[1]";
	String userGroupSearchField_ClassName = "XCUIElementTypeSearchField";
	String cancel_Name = "Cancel";
	String done_Name = "Done";
	String oK_Name = "OK";
	String clearCache_Name = "Clear Cache";
	String general_Name = "General";
	String pricacyOptInConfirm_Id = "com.weather.Weather:id/popup_positive_button";

	By bySettingsButton = MobileBy.AccessibilityId(settingsButton_AccessibilityId);
	By byApplicationVersion = MobileBy.AccessibilityId(applicationVersion_AccessibilityId);
	By bySettingsBackButton = MobileBy.AccessibilityId(settingsBackButton_AccessibilityId);
	By byAboutLabel = MobileBy.name(aboutLabel_Name);
	By byCloseMenuButton = MobileBy.xpath(closeMenuButton_Xpath);
	By byUserGroupSearchField = MobileBy.className(userGroupSearchField_ClassName);
	By byCancel = MobileBy.name(cancel_Name);
	By byDone = MobileBy.name(done_Name);
	By byOK = MobileBy.name(oK_Name);
	By byClearCache = MobileBy.name(clearCache_Name);
	By byGeneral = MobileBy.name(general_Name);
	By byPricacyOptInConfirm = MobileBy.id(pricacyOptInConfirm_Id);
	
	By byAlertCenter = MobileBy.AccessibilityId("Go to Alerts and Notifications");
	By bySearchIcon = MobileBy.xpath("//android.widget.ImageView[@resource-id=\"com.weather.Weather:id/search_icon\"]");
	
	

	
	MobileElement settingsButton = null;
	MobileElement privacySettings = null;
	MobileElement applicationVersion = null;
	MobileElement settingsBackButton = null;
	MobileElement aboutLabel = null;
	MobileElement closeMenuButton = null;
	MobileElement userGroupSearchField = null;
	MobileElement cancel = null;
	MobileElement done = null;
	MobileElement oK = null;
	MobileElement clearCache = null;
	MobileElement general = null;
	MobileElement pricacyOptInConfirm = null;
	

	public AndroidSettingsScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigate to SettingPage")
	public void navigatetoSettingPage() throws Exception {
		TestBase.waitForMilliSeconds(10000);
		settingsButton = Ad.findElement(bySettingsButton);
		TestBase.clickOnElement(bySettingsButton, settingsButton, "Settings Button");
	}

	@Step("Get App version under About This App and writes it to JenkinsEmailConfig.Properties file")
	public void getAppVersion() throws Exception {

		// logStep("Getting the app version and writing in Properties file");
		ReadExcelValues.excelValues("Smoke", "TestMode");

		MobileElement el = null;
		Thread.sleep(2000);

		try {
			Ad.findElementByName(ReadExcelValues.data[1][Cap]).click();
		} catch (Exception e1) {
			try {
				Functions.close_launchApp();
			} catch (Exception e2) {

			} finally {
				Ad.findElementByName(ReadExcelValues.data[1][Cap]).click();
			}
		}

		Thread.sleep(3000);

		//MobileElement el2 = (MobileElement) Ad.findElementByName(ReadExcelValues.data[6][Cap]);
		//el2.click();
		aboutLabel = Ad.findElement(byAboutLabel);
		TestBase.clickOnElement(byAboutLabel, aboutLabel, "About Label");
		Thread.sleep(1000);
		//appVersion = Ad.findElementByAccessibilityId("app_version").getAttribute("label");
		applicationVersion = Ad.findElement(byApplicationVersion);
		appVersion = TestBase.getElementAttribute(applicationVersion, "label");
		appVersion = appVersion.split("Version")[1].trim();
		System.out.println("App Version is: " + appVersion);

		FileOutputStream fos = new FileOutputStream(
				new File(System.getProperty("user.dir") + "/JenkinsEmailConfig.Properties"));

		properties.setProperty("AppVersion", appVersion);
		properties.store(fos, "App Version read from app and updated");
		fos.close();

		// Navigating back from About This app page.
		//Ad.findElementByAccessibilityId("Settings back button").click();
		settingsBackButton = Ad.findElement(bySettingsBackButton);
		TestBase.clickOnElement(bySettingsBackButton, settingsBackButton, "Settings Back Button");
		Thread.sleep(2000);
		// Click on Close icon on Settings page.
		//Ad.findElementByXPath("(//XCUIElementTypeButton[@name='close_menu_button'])[1]").click();
//		Functions.scroll_Up();
//		Functions.swipe_Up(Ad);
		Functions.swipe_Down();
		Functions.swipe_Down();
		Functions.swipe_Down();
		closeMenuButton = Ad.findElement(byCloseMenuButton);
		TestBase.clickOnElement(byCloseMenuButton, closeMenuButton, "Close Menu Button");
		Thread.sleep(1000);
	}

	@Step("Navigate to Privacy Page from Settings")
	public void navigateToPrivacyPage_From_Settings() throws Exception {

		clickOnSettingIcon();
		
		By byPrivacySettingsButton = null;
		try {
			byPrivacySettingsButton = MobileBy.xpath(privacySettingsLabel_Xpath);
			
		} catch (Exception e) {
			byPrivacySettingsButton = MobileBy.xpath(privacySettingsLabel_Xpath);
			
		}
		Functions.genericScroll(byPrivacySettingsButton, true, false, getOffsetYTop(), TOLERANCE_FROM_TOP);
		
		privacySettings = Ad.findElement(byPrivacySettingsButton);
		TestBase.clickOnElement(byPrivacySettingsButton, privacySettings, "Privacy Settings Button");
	}

	@Step("Select Privacy Optin Navigating from Settings")
	public void select_Privacy_Optin_From_Settings(String Excelname, String sheetName) throws Exception {

		navigateToPrivacyPage_From_Settings();
		Thread.sleep(30000);
		ReadExcelValues.excelValues(Excelname, sheetName);
		String privacy_Optin_label = null;
		String privacy_Optout_label = null;
		String privacy_Optin_default_label = null;
		MobileElement privacy_Optout = null;
		MobileElement privacy_Optin = null;
		MobileElement privacy_Optin_default = null;
		// navigateToPrivacyPage(Excelname, sheetName);
		// System.out.println("Returned From 'Navigate to Privacy'");
		// logStep("Returned From 'Navigate to Privacy'");
		// Thread.sleep(30000);

//		swipe_Up(Ad);
//		swipe_Up(Ad);
//		swipe_Up(Ad);
		
		// swipe_Up();
		By byPrivacy_Optin = MobileBy.xpath(ReadExcelValues.data[23][Cap]);
		By byPrivacy_Optout = MobileBy.xpath(ReadExcelValues.data[24][Cap]);
		Functions.genericScroll(byPrivacy_Optin, true, true, 224, TOLERANCE_FROM_TOP);
		Thread.sleep(5000);
		try {
			privacy_Optin = Ad.findElement(byPrivacy_Optin);
						
			try {
				privacy_Optout = Ad.findElement(byPrivacy_Optout);
				
			} catch (Exception e) {
				
				try {
					byPrivacy_Optout = MobileBy.xpath(ReadExcelValues.data[27][Cap]);
					privacy_Optout = Ad.findElement(byPrivacy_Optout);
					
				} catch (Exception e1) {
					byPrivacy_Optout = MobileBy.xpath(ReadExcelValues.data[28][Cap]);
					privacy_Optout = Ad.findElement(byPrivacy_Optout);
					
				}
				
			}
								
			try {
				privacy_Optin.click();
				Thread.sleep(3000);
				clickOnPrivacyOptInConfirm();
				System.out.println("Privacy Optin is selected");
				logStep("Privacy Optin is selected");
				attachScreen();
			} catch (Exception e) {
				System.out.println("An exception while selecting optin");
				logStep("An exception while selecting optin");
				attachScreen();
				// Assert.fail("An exception while selecting optin");
				}
			
		} catch (Exception e) {
			System.out.println("Privacy feature options are not displayed on the Privacy Settings page");
			logStep("Privacy feature options are not displayed on the Privacy Settings page");
			attachScreen();
			// Assert.fail("Privacy feature options are not displayed on the Privacy
			// Settings page");
		} finally {
			// Navigating back from Privacy Settings page.
			Ad.navigate().back();
			TestBase.waitForMilliSeconds(2000);
			Ad.navigate().back();
			TestBase.waitForMilliSeconds(2000);
			attachScreen();
			
		}

	}
	
	@Step("Select Privacy Optin Navigating from Settings")
	public void select_Privacy_Optin_From_Settings_Virginia(String Excelname, String sheetName) throws Exception {

		navigateToPrivacyPage_From_Settings();
		Thread.sleep(30000);
		ReadExcelValues.excelValues(Excelname, sheetName);
		String privacy_Optin_label = null;
		String privacy_Optout_label = null;
		String privacy_Optin_default_label = null;
		MobileElement privacy_Optout = null;
		MobileElement privacy_Optin = null;
		MobileElement privacy_Optin_default = null;
		// navigateToPrivacyPage(Excelname, sheetName);
		// System.out.println("Returned From 'Navigate to Privacy'");
		// logStep("Returned From 'Navigate to Privacy'");
		// Thread.sleep(30000);

//		swipe_Up(Ad);
//		swipe_Up(Ad);
//		swipe_Up(Ad);
		
		// swipe_Up();
		//By byPrivacy_Optin = MobileBy.xpath("//android.widget.TextView[@text=\"Accept Standard Advertising Settings (Opt In)\"]");
		//By byPrivacy_Optout = MobileBy.xpath("//android.widget.TextView[@text=\"Decline Targeted Advertising (Opt Out)\"]");
		By byPrivacy_Optin = MobileBy.xpath(ReadExcelValues.data[31][Cap]);
		By byPrivacy_Optout = MobileBy.xpath(ReadExcelValues.data[32][Cap]);
		Functions.genericScroll(byPrivacy_Optin, true, true, 224, TOLERANCE_FROM_TOP);
		Thread.sleep(5000);
		try {
			privacy_Optin = Ad.findElement(byPrivacy_Optin);
						
			try {
				privacy_Optout = Ad.findElement(byPrivacy_Optout);
				
			} catch (Exception e) {
				e.printStackTrace();
				
			}
								
			try {
				privacy_Optin.click();
				Thread.sleep(3000);
				clickOnPrivacyOptInConfirm();
				System.out.println("Privacy Optin is selected");
				logStep("Privacy Optin is selected");
				attachScreen();
			} catch (Exception e) {
				System.out.println("An exception while selecting optin");
				logStep("An exception while selecting optin");
				attachScreen();
				// Assert.fail("An exception while selecting optin");
				}
			
		} catch (Exception e) {
			System.out.println("Privacy feature options are not displayed on the Privacy Settings page");
			logStep("Privacy feature options are not displayed on the Privacy Settings page");
			attachScreen();
			// Assert.fail("Privacy feature options are not displayed on the Privacy
			// Settings page");
		} finally {
			// Navigating back from Privacy Settings page.
			Ad.navigate().back();
			TestBase.waitForMilliSeconds(2000);
			Ad.navigate().back();
			TestBase.waitForMilliSeconds(2000);
			attachScreen();
			
		}

	}

	@Step("Select Privacy Optout Navigating from Settings")
	public void select_Privacy_Optout_From_Settings(String Excelname, String sheetName) throws Exception {

		navigateToPrivacyPage_From_Settings();
		Thread.sleep(30000);

		ReadExcelValues.excelValues(Excelname, sheetName);
		String privacy_Optin_label = null;
		String privacy_Optout_label = null;
		String privacy_Optin_default_label = null;
		MobileElement privacy_Optout = null;
		MobileElement privacy_Optin = null;
		MobileElement privacy_Optin_default = null;
//		navigateToPrivacyPage(Excelname, sheetName);
//		System.out.println("Returned From 'Navigate to Privacy'");
//		logStep("Returned From 'Navigate to Privacy'");

//		swipe_Up(Ad);
//		swipe_Up(Ad);
//		swipe_Up(Ad);
	
		// swipe_Up();
		By byPrivacy_Optin = MobileBy.xpath(ReadExcelValues.data[23][Cap]);
		By byPrivacy_Optout = MobileBy.xpath(ReadExcelValues.data[24][Cap]);
		Functions.genericScroll(byPrivacy_Optin, true, true, 224, TOLERANCE_FROM_TOP);
		Thread.sleep(5000);
		try {
			privacy_Optin = Ad.findElement(byPrivacy_Optin);
			
			try {
				privacy_Optout = Ad.findElement(byPrivacy_Optout);
				
			} catch (Exception e) {
				try {
					byPrivacy_Optout = MobileBy.xpath(ReadExcelValues.data[27][Cap]);
					privacy_Optout = Ad.findElement(byPrivacy_Optout);
					
				} catch (Exception e1) {
					byPrivacy_Optout = MobileBy.xpath(ReadExcelValues.data[28][Cap]);
					privacy_Optout = Ad.findElement(byPrivacy_Optout);
					
				}
				
			}
			
			try {
				privacy_Optout.click();
				Thread.sleep(3000);
				System.out.println("Privacy Optout is selected");
				logStep("Privacy Optout is selected");
				attachScreen();
			} catch (Exception e) {
				System.out.println("An exception while selecting optout");
				logStep("An exception while selecting optout");
				attachScreen();
				// Assert.fail("An exception while selecting optout");
			}

		
		} catch (Exception e) {
			System.out.println("Privacy feature options are not displayed on the Privacy Settings page");
			logStep("Privacy feature options are not displayed on the Privacy Settings page");
			attachScreen();
			// Assert.fail("Privacy feature options are not displayed on the Privacy
			// Settings page");
		} finally {
			// Navigating back from Privacy Settings page.
			Ad.navigate().back();
			TestBase.waitForMilliSeconds(2000);
			Ad.navigate().back();
			TestBase.waitForMilliSeconds(2000);
			attachScreen();
		}

	}
	
	@Step("Select Privacy Optout Navigating from Settings")
	public void select_Privacy_Optout_From_Settings_Virginia(String Excelname, String sheetName) throws Exception {

		navigateToPrivacyPage_From_Settings();
		Thread.sleep(30000);

		ReadExcelValues.excelValues(Excelname, sheetName);
		String privacy_Optin_label = null;
		String privacy_Optout_label = null;
		String privacy_Optin_default_label = null;
		MobileElement privacy_Optout = null;
		MobileElement privacy_Optin = null;
		MobileElement privacy_Optin_default = null;
//		navigateToPrivacyPage(Excelname, sheetName);
//		System.out.println("Returned From 'Navigate to Privacy'");
//		logStep("Returned From 'Navigate to Privacy'");

//		swipe_Up(Ad);
//		swipe_Up(Ad);
//		swipe_Up(Ad);
	
		// swipe_Up();
		//By byPrivacy_Optin = MobileBy.xpath("//android.widget.TextView[@text=\"Accept Standard Advertising Settings (Opt In)\"]");
		//By byPrivacy_Optout = MobileBy.xpath("//android.widget.TextView[@text=\"Decline Targeted Advertising (Opt Out)\"]");
		By byPrivacy_Optin = MobileBy.xpath(ReadExcelValues.data[31][Cap]);
		By byPrivacy_Optout = MobileBy.xpath(ReadExcelValues.data[32][Cap]);
		Functions.genericScroll(byPrivacy_Optin, true, true, 224, TOLERANCE_FROM_TOP);
		Thread.sleep(5000);
		try {
			privacy_Optin = Ad.findElement(byPrivacy_Optin);
			
			try {
				privacy_Optout = Ad.findElement(byPrivacy_Optout);
				
			} catch (Exception e) {
				e.printStackTrace();
				
			}
			
			try {
				privacy_Optout.click();
				Thread.sleep(3000);
				System.out.println("Privacy Optout is selected");
				logStep("Privacy Optout is selected");
				attachScreen();
			} catch (Exception e) {
				System.out.println("An exception while selecting optout");
				logStep("An exception while selecting optout");
				attachScreen();
				// Assert.fail("An exception while selecting optout");
			}

		
		} catch (Exception e) {
			System.out.println("Privacy feature options are not displayed on the Privacy Settings page");
			logStep("Privacy feature options are not displayed on the Privacy Settings page");
			attachScreen();
			// Assert.fail("Privacy feature options are not displayed on the Privacy
			// Settings page");
		} finally {
			// Navigating back from Privacy Settings page.
			Ad.navigate().back();
			TestBase.waitForMilliSeconds(2000);
			Ad.navigate().back();
			TestBase.waitForMilliSeconds(2000);
			attachScreen();
		}

	}

	@Step("Verify Privacy Card Options Navigating from Settings")
	public void verify_PrivacyCard_Options_From_Settings(String Excelname, String sheetName) throws Exception {
		navigateToPrivacyPage_From_Settings();
		Thread.sleep(30000);

		ReadExcelValues.excelValues(Excelname, sheetName);
		String privacy_Optin_label = null;
		String privacy_Optout_label = null;
		String privacy_Optin_default_label = null;
		MobileElement privacy_Optout = null;
		MobileElement privacy_Optin = null;
		MobileElement privacy_Optin_default = null;
		/*
		 * try { navigateToPrivacyPage(Excelname, sheetName);
		 * System.out.println("Returned From 'Navigate to Privacy'");
		 * logStep("Returned From 'Navigate to Privacy'"); Thread.sleep(30000); } catch
		 * (Exception e) {
		 * System.out.println("An exception while navigating to Privacy Page");
		 * logStep("An exception while navigating to Privacy Page"); }
		 */

		try {
			System.out.println("Current Context is: " + Ad.getContext());
			logStep("Current Context is: " + Ad.getContext());
			

//			swipe_Up(Ad);
//			swipe_Up(Ad);
//			swipe_Up(Ad);
			
			// swipe_Up();
			By byPrivacy_Optin = MobileBy.xpath(ReadExcelValues.data[23][Cap]);
			By byPrivacy_Optout = MobileBy.xpath(ReadExcelValues.data[24][Cap]);
			Functions.genericScroll(byPrivacy_Optin, true, true, 224, TOLERANCE_FROM_TOP);
			Thread.sleep(5000);

			try {
				privacy_Optin = Ad.findElement(byPrivacy_Optin);
				
				try {
					privacy_Optout = Ad.findElement(byPrivacy_Optout);
					
				} catch (Exception e) {
					byPrivacy_Optout = MobileBy.xpath(ReadExcelValues.data[27][Cap]);
					privacy_Optout = Ad.findElement(byPrivacy_Optout);
					
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
			// Navigating back from Privacy Settings page.
			Ad.navigate().back();
			TestBase.waitForMilliSeconds(2000);
			Ad.navigate().back();
			TestBase.waitForMilliSeconds(2000);
			attachScreen();

		}
	}

	@Step("Set App in to Test Mode by selecting User Group: {0}")
	public void Setappinto_TestMode(String TestMode) throws Exception {
		FTLScreens ftlScreens;
		System.out.println("Trying to put app in test mode");
		logStep("Trying to put app in test mode");
		ReadExcelValues.excelValues("Smoke", "TestMode");

		MobileElement el = null;
		Thread.sleep(2000);
		try {
			Ad.findElementByName(ReadExcelValues.data[1][Cap]).click();
		} catch (Exception e1) {
			try {
				Functions.close_launchApp();
			} catch (Exception e2) {

			} finally {
				Ad.findElementByName(ReadExcelValues.data[1][Cap]).click();
			}
		}

		Thread.sleep(3000);
		Functions.scroll_Down();
		Thread.sleep(4000);
		// Select About This app
		//MobileElement el2 = (MobileElement) Ad.findElementByName(ReadExcelValues.data[6][Cap]);
		//el2.click();
		aboutLabel = Ad.findElement(byAboutLabel);
		TestBase.clickOnElement(byAboutLabel, aboutLabel, "About Label");
		Thread.sleep(1000);
		for (int i = 1; i <= 10; i++) {
			Ad.findElementByName(ReadExcelValues.data[11][Cap]).click();
		}

		// airlock
		// Ad.findElementByName(readExcelValues.data[12][Cap]).click();

		// Enable Responsive Mode
		checkResponsiveMode();

		// Select UserGroup
		Ad.findElementByName(ReadExcelValues.data[16][Cap]).click();
		Thread.sleep(10000);
		MobileElement AdsTestSwitch = null;
		String SwitchValue = null;
		if (TestMode.equals("Select")) {
			clearAirlock();
			// Select Test Group

			//Ad.findElementByClassName("XCUIElementTypeSearchField").sendKeys("AdsTestAdUnitOnly");
			userGroupSearchField = Ad.findElement(byUserGroupSearchField);
			TestBase.typeText(userGroupSearchField, "User Group Search Field", "AdsTestAdUnitOnly");
			try {
				List<MobileElement> AdsTestSwitches = Ad.findElementsByName("AdsTestAdUnitOnly");
				AdsTestSwitch = AdsTestSwitches.get(1);
			} catch (Exception e) {
				// Back to AirlockGroup
				Ad.findElementByName(ReadExcelValues.data[22][Cap]).click();
				// Select UserGroup

				Ad.findElementByName(ReadExcelValues.data[16][Cap]).click();
				Thread.sleep(10000);
				List<MobileElement> AdsTestSwitches = Ad.findElementsByName("AdsTestAdUnitOnly");
				AdsTestSwitch = AdsTestSwitches.get(1);

			}
			SwitchValue = AdsTestSwitch.getAttribute("value");
			if (SwitchValue.equals("1")) {
				System.out.println("Test Mode already enabled");
				logStep("Test Mode already enabled");
			} else {
				AdsTestSwitch.click();
				System.out.println("Test Mode enabled");
				logStep("Test Mode enabled");
			}
			try {
				//Ad.findElementByName("Cancel").click();
				cancel = Ad.findElement(byCancel);
				TestBase.clickOnElement(byCancel, cancel, "Cancel Button");
			} catch (Exception e) {
				//Ad.findElementByName("Done").click();
				done = Ad.findElement(byDone);
				TestBase.clickOnElement(byDone, done, "Done Button");
			}
			// Ad.findElementByXPath(readExcelValues.data[23][Cap]).click();
		} else if (TestMode.equals("UnlimitedInterstitial")) {
			clearAirlock();
			// Select Test Group

			//Ad.findElementByClassName("XCUIElementTypeSearchField").sendKeys("UnlimitedInterstitial");
			userGroupSearchField = Ad.findElement(byUserGroupSearchField);
			TestBase.typeText(userGroupSearchField, "User Group Search Field", "UnlimitedInterstitial");

			try {
				List<MobileElement> AdsTestSwitches = Ad.findElementsByName("UnlimitedInterstitial");
				AdsTestSwitch = AdsTestSwitches.get(1);
			} catch (Exception e) {
				// Back to AirlockGroup
				Ad.findElementByName(ReadExcelValues.data[22][Cap]).click();
				// Select UserGroup

				Ad.findElementByName(ReadExcelValues.data[16][Cap]).click();
				Thread.sleep(10000);
				List<MobileElement> AdsTestSwitches = Ad.findElementsByName("UnlimitedInterstitial");
				AdsTestSwitch = AdsTestSwitches.get(1);

			}
			SwitchValue = AdsTestSwitch.getAttribute("value");
			if (SwitchValue.equals("1")) {
				System.out.println("UnlimitedInterstitial already enabled");
				logStep("UnlimitedInterstitial already enabled");
			} else {
				AdsTestSwitch.click();
				System.out.println("UnlimitedInterstitial enabled");
				logStep("UnlimitedInterstitial enabled");
			}
			try {
				//Ad.findElementByName("Cancel").click();
				cancel = Ad.findElement(byCancel);
				TestBase.clickOnElement(byCancel, cancel, "Cancel Button");
			} catch (Exception e) {
				//Ad.findElementByName("Done").click();
				done = Ad.findElement(byDone);
				TestBase.clickOnElement(byDone, done, "Done Button");
			}
			// Ad.findElementByXPath(readExcelValues.data[23][Cap]).click();
		} else if (TestMode.equals("IDD")) {
			/*
			 * Integrated Daily Details requires both 'AdsTestAdUnitOnly' and 'IOSFLAG-4200'
			 * user groups to be enabled.
			 */
			clearAirlock();
			// Select Test Group

			//Ad.findElementByClassName("XCUIElementTypeSearchField").sendKeys("AdsTestAdUnitOnly");
			userGroupSearchField = Ad.findElement(byUserGroupSearchField);
			TestBase.typeText(userGroupSearchField, "User Group Search Field", "AdsTestAdUnitOnly");
			try {
				List<MobileElement> AdsTestSwitches = Ad.findElementsByName("AdsTestAdUnitOnly");
				AdsTestSwitch = AdsTestSwitches.get(1);
			} catch (Exception e) {
				// Back to AirlockGroup
				Ad.findElementByName(ReadExcelValues.data[22][Cap]).click();
				// Select UserGroup

				Ad.findElementByName(ReadExcelValues.data[16][Cap]).click();
				Thread.sleep(10000);
				List<MobileElement> AdsTestSwitches = Ad.findElementsByName("AdsTestAdUnitOnly");
				AdsTestSwitch = AdsTestSwitches.get(1);

			}
			SwitchValue = AdsTestSwitch.getAttribute("value");
			if (SwitchValue.equals("1")) {
				System.out.println("Test Mode already enabled");
				logStep("Test Mode already enabled");
			} else {
				AdsTestSwitch.click();
				System.out.println("Test Mode enabled");
				logStep("Test Mode enabled");
			}
			try {
				//Ad.findElementByName("Cancel").click();
				cancel = Ad.findElement(byCancel);
				TestBase.clickOnElement(byCancel, cancel, "Cancel Button");
			} catch (Exception e) {
				//Ad.findElementByName("Done").click();
				done = Ad.findElement(byDone);
				TestBase.clickOnElement(byDone, done, "Done Button");
			}
			Thread.sleep(5000);
			//Ad.findElementByClassName("XCUIElementTypeSearchField").sendKeys("IOSFLAG-4200");
			userGroupSearchField = Ad.findElement(byUserGroupSearchField);
			TestBase.typeText(userGroupSearchField, "User Group Search Field", "IOSFLAG-4200");
			try {
				List<MobileElement> AdsTestSwitches = Ad.findElementsByName("IOSFLAG-4200");
				AdsTestSwitch = AdsTestSwitches.get(1);
			} catch (Exception e) {
				// Back to AirlockGroup
				Ad.findElementByName(ReadExcelValues.data[22][Cap]).click();
				// Select UserGroup

				Ad.findElementByName(ReadExcelValues.data[16][Cap]).click();
				Thread.sleep(10000);
				List<MobileElement> AdsTestSwitches = Ad.findElementsByName("IOSFLAG-4200");
				AdsTestSwitch = AdsTestSwitches.get(1);

			}
			SwitchValue = AdsTestSwitch.getAttribute("value");
			if (SwitchValue.equals("1")) {
				System.out.println("IOSFLAG-4200 already enabled");
				logStep("IOSFLAG-4200 already enabled");
			} else {
				AdsTestSwitch.click();
				System.out.println("IOSFLAG-4200 enabled");
				logStep("IOSFLAG-4200 enabled");
			}
			try {
				//Ad.findElementByName("Cancel").click();
				cancel = Ad.findElement(byCancel);
				TestBase.clickOnElement(byCancel, cancel, "Cancel Button");
			} catch (Exception e) {
				//Ad.findElementByName("Done").click();
				done = Ad.findElement(byDone);
				TestBase.clickOnElement(byDone, done, "Done Button");
			}
		} else if (TestMode.equals("IntegratedFeedCard")) {
			/*
			 * Integrated Daily Details requires both 'AdsTestAdUnitOnly' and 'IOSFLAG-4200'
			 * user groups to be enabled.
			 */
			clearAirlock();
			// Select Test Group

			//Ad.findElementByClassName("XCUIElementTypeSearchField").sendKeys("AdsTestAdUnitOnly");
			userGroupSearchField = Ad.findElement(byUserGroupSearchField);
			TestBase.typeText(userGroupSearchField, "User Group Search Field", "AdsTestAdUnitOnly");
			try {
				List<MobileElement> AdsTestSwitches = Ad.findElementsByName("AdsTestAdUnitOnly");
				AdsTestSwitch = AdsTestSwitches.get(1);
			} catch (Exception e) {
				// Back to AirlockGroup
				Ad.findElementByName(ReadExcelValues.data[22][Cap]).click();
				// Select UserGroup

				Ad.findElementByName(ReadExcelValues.data[16][Cap]).click();
				Thread.sleep(10000);
				List<MobileElement> AdsTestSwitches = Ad.findElementsByName("AdsTestAdUnitOnly");
				AdsTestSwitch = AdsTestSwitches.get(1);

			}
			SwitchValue = AdsTestSwitch.getAttribute("value");
			if (SwitchValue.equals("1")) {
				System.out.println("Test Mode already enabled");
				logStep("Test Mode already enabled");
			} else {
				AdsTestSwitch.click();
				System.out.println("Test Mode enabled");
				logStep("Test Mode enabled");
			}
			try {
				//Ad.findElementByName("Cancel").click();
				cancel = Ad.findElement(byCancel);
				TestBase.clickOnElement(byCancel, cancel, "Cancel Button");
			} catch (Exception e) {
				//Ad.findElementByName("Done").click();
				done = Ad.findElement(byDone);
				TestBase.clickOnElement(byDone, done, "Done Button");
			}
			Thread.sleep(5000);
			//Ad.findElementByClassName("XCUIElementTypeSearchField").sendKeys("IntegratedAdCard");
			userGroupSearchField = Ad.findElement(byUserGroupSearchField);
			TestBase.typeText(userGroupSearchField, "User Group Search Field", "IntegratedAdCard");
			try {
				List<MobileElement> AdsTestSwitches = Ad.findElementsByName("IntegratedAdCard");
				AdsTestSwitch = AdsTestSwitches.get(1);
			} catch (Exception e) {
				// Back to AirlockGroup
				Ad.findElementByName(ReadExcelValues.data[22][Cap]).click();
				// Select UserGroup

				Ad.findElementByName(ReadExcelValues.data[16][Cap]).click();
				Thread.sleep(10000);
				List<MobileElement> AdsTestSwitches = Ad.findElementsByName("IntegratedAdCard");
				AdsTestSwitch = AdsTestSwitches.get(1);

			}
			SwitchValue = AdsTestSwitch.getAttribute("value");
			if (SwitchValue.equals("1")) {
				System.out.println("IntegratedAdCard already enabled");
				logStep("IntegratedAdCardalready enabled");
			} else {
				AdsTestSwitch.click();
				System.out.println("IntegratedAdCard enabled");
				logStep("IntegratedAdCard enabled");
			}
			try {
				//Ad.findElementByName("Cancel").click();
				cancel = Ad.findElement(byCancel);
				TestBase.clickOnElement(byCancel, cancel, "Cancel Button");
			} catch (Exception e) {
				//Ad.findElementByName("Done").click();
				done = Ad.findElement(byDone);
				TestBase.clickOnElement(byDone, done, "Done Button");
			}
		} else if (TestMode.equals("WM")) {
			/*
			 * This enables User Group WM Cards which displays Flu Card, Allergy Card, Week
			 * AHead and Weekend cards. this also requires test user group to display test
			 * ads.
			 */
			clearAirlock();
			// Select Test Group
			//Ad.findElementByClassName("XCUIElementTypeSearchField").sendKeys("AdsTestAdUnitOnly");
			userGroupSearchField = Ad.findElement(byUserGroupSearchField);
			TestBase.typeText(userGroupSearchField, "User Group Search Field", "AdsTestAdUnitOnly");
			try {
				List<MobileElement> AdsTestSwitches = Ad.findElementsByName("AdsTestAdUnitOnly");
				AdsTestSwitch = AdsTestSwitches.get(1);
			} catch (Exception e) {
				// Back to AirlockGroup
				Ad.findElementByName(ReadExcelValues.data[22][Cap]).click();
				// Select UserGroup

				Ad.findElementByName(ReadExcelValues.data[16][Cap]).click();
				Thread.sleep(10000);
				List<MobileElement> AdsTestSwitches = Ad.findElementsByName("AdsTestAdUnitOnly");
				AdsTestSwitch = AdsTestSwitches.get(1);

			}
			SwitchValue = AdsTestSwitch.getAttribute("value");
			if (SwitchValue.equals("1")) {
				System.out.println("Test Mode already enabled");
				logStep("Test Mode already enabled");
			} else {
				AdsTestSwitch.click();
				System.out.println("Test Mode enabled");
				logStep("Test Mode enabled");
			}
			try {
				//Ad.findElementByName("Cancel").click();
				cancel = Ad.findElement(byCancel);
				TestBase.clickOnElement(byCancel, cancel, "Cancel Button");
			} catch (Exception e) {
				//Ad.findElementByName("Done").click();
				done = Ad.findElement(byDone);
				TestBase.clickOnElement(byDone, done, "Done Button");
			}
			Thread.sleep(5000);

			//Ad.findElementByClassName("XCUIElementTypeSearchField").sendKeys("WM Cards");
			userGroupSearchField = Ad.findElement(byUserGroupSearchField);
			TestBase.typeText(userGroupSearchField, "User Group Search Field", "WM Cards");
			try {
				List<MobileElement> AdsTestSwitches = Ad.findElementsByName("WM Cards");
				AdsTestSwitch = AdsTestSwitches.get(1);
			} catch (Exception e) {
				// Back to AirlockGroup
				Ad.findElementByName(ReadExcelValues.data[22][Cap]).click();
				// Select UserGroup

				Ad.findElementByName(ReadExcelValues.data[16][Cap]).click();
				Thread.sleep(10000);
				List<MobileElement> AdsTestSwitches = Ad.findElementsByName("WM Cards");
				AdsTestSwitch = AdsTestSwitches.get(1);

			}
			SwitchValue = AdsTestSwitch.getAttribute("value");
			if (SwitchValue.equals("1")) {
				System.out.println("WM Cards already enabled");
				logStep("WM Cards already enabled");
			} else {
				AdsTestSwitch.click();
				System.out.println("WM Cards enabled");
				logStep("WM Cards enabled");
			}
			try {
				//Ad.findElementByName("Cancel").click();
				cancel = Ad.findElement(byCancel);
				TestBase.clickOnElement(byCancel, cancel, "Cancel Button");
			} catch (Exception e) {
				//Ad.findElementByName("Done").click();
				done = Ad.findElement(byDone);
				TestBase.clickOnElement(byDone, done, "Done Button");
			}

		} else {
			clearAirlock();

		}

		// Back to AirlockGroup
		Ad.findElementByName(ReadExcelValues.data[22][Cap]).click();

		// Click on Done
		//Ad.findElementByName("Done").click();
		done = Ad.findElement(byDone);
		TestBase.clickOnElement(byDone, done, "Done Button");

		Thread.sleep(3000);
		try {
			Functions.close_launchApp();
			Functions.checkForAppState();
		} catch (Exception e) {
			Ad.closeApp();
			Ad.launchApp();
			Functions.checkForAppState();
		}
		try {
			Functions.close_launchApp();
			Functions.checkForAppState();
		} catch (Exception e) {
			Ad.closeApp();
			Ad.launchApp();
			Functions.checkForAppState();
		}

		// after close and launch check if home screen is displayed or not by validating
		// settings button, for this verifying for settings button
		try {
			MobileElement appSettings = Ad.findElementByName(ReadExcelValues.data[1][Cap]);
		} catch (Exception e) {
			System.out.println("App home screen is not dispalyed after kill and launch");
			logStep("App home screen is not dispalyed after kill and launch");
			attachScreen();
			ftlScreens = new FTLScreens(Ad);
			ftlScreens.handle_Unwanted_Popups();
		}

	}

	@Step("Select Airlock User Group: {0}")
	public void select_Airlock_UserGroupiOS(String userGroupName) throws Exception {
		FTLScreens ftlScreens;
		System.out.println("Trying to select Airlock  User Group");
		logStep("Trying to select Airlock  User Group");
		ReadExcelValues.excelValues("Smoke", "TestMode");

		MobileElement el = null;
		Thread.sleep(2000);

		try {
			Ad.findElementByName(ReadExcelValues.data[1][Cap]).click();
		} catch (Exception e1) {
			try {
				Functions.close_launchApp();
			} catch (Exception e2) {

			} finally {
				Ad.findElementByName(ReadExcelValues.data[1][Cap]).click();
			}
		}

		Thread.sleep(3000);
		Functions.scroll_Down();
		Thread.sleep(4000);
		// Select About This app
		//MobileElement el2 = (MobileElement) Ad.findElementByName(ReadExcelValues.data[6][Cap]);
		//el2.click();
		aboutLabel = Ad.findElement(byAboutLabel);
		TestBase.clickOnElement(byAboutLabel, aboutLabel, "About Label");
		Thread.sleep(1000);
		for (int i = 1; i <= 10; i++) {
			Ad.findElementByName(ReadExcelValues.data[11][Cap]).click();
		}

		// Enable Responsive Mode
		checkResponsiveMode();

		// Select UserGroup
		Ad.findElementByName(ReadExcelValues.data[16][Cap]).click();
		Thread.sleep(10000);

		MobileElement AdsTestSwitch = null;
		String SwitchValue = null;

		if (userGroupName.equalsIgnoreCase("Clear") || userGroupName.equalsIgnoreCase("UnSelect")) {
			clearAirlock();
		} else {
			clearAirlock();
			// Select desired user Group

			//Ad.findElementByClassName("XCUIElementTypeSearchField").sendKeys(userGroupName);
			
			userGroupSearchField = Ad.findElement(byUserGroupSearchField);
			TestBase.typeText(userGroupSearchField, "User Group Search Field", userGroupName);
			try {
				List<MobileElement> AdsTestSwitches = Ad.findElementsByName(userGroupName);
				try {
					AdsTestSwitch = AdsTestSwitches.get(1);
				}catch (Exception ex) {
					AdsTestSwitch = Ad.findElementByXPath("//*[@name='"+userGroupName+"']/following-sibling::XCUIElementTypeSwitch");
				}
				
				
			} catch (Exception e) {
				// Back to AirlockGroup
				Ad.findElementByName(ReadExcelValues.data[22][Cap]).click();
				// Select UserGroup

				Ad.findElementByName(ReadExcelValues.data[16][Cap]).click();
				Thread.sleep(10000);
				List<MobileElement> AdsTestSwitches = Ad.findElementsByName(userGroupName);
				try {
					AdsTestSwitch = AdsTestSwitches.get(1);
				}catch (Exception ex) {
					AdsTestSwitch = Ad.findElementByXPath("//*[@name='"+userGroupName+"']/following-sibling::XCUIElementTypeSwitch");
				}

			}
			SwitchValue = AdsTestSwitch.getAttribute("value");
			if (SwitchValue.equals("1")) {
				System.out.println(userGroupName + " already enabled");
				logStep(userGroupName + " already enabled");
			} else {
				AdsTestSwitch.click();
				System.out.println(userGroupName + " enabled");
				logStep(userGroupName + " enabled");
			}

			try {
				//Ad.findElementByName("Cancel").click();
				cancel = Ad.findElement(byCancel);
				TestBase.clickOnElement(byCancel, cancel, "Cancel Button");
			} catch (Exception e) {
				//Ad.findElementByName("Done").click();
				done = Ad.findElement(byDone);
				TestBase.clickOnElement(byDone, done, "Done Button");
			}
			Thread.sleep(5000);
			if (userGroupName.equalsIgnoreCase("IOSFLAG-4200") || userGroupName.equalsIgnoreCase("IntegratedAdCard")
					|| userGroupName.equalsIgnoreCase("WM Cards") || userGroupName.equalsIgnoreCase("IOSFLAG-8105")) {
				// Select Test Group
				//Ad.findElementByClassName("XCUIElementTypeSearchField").sendKeys("AdsTestAdUnitOnly");
				userGroupSearchField = Ad.findElement(byUserGroupSearchField);
				TestBase.typeText(userGroupSearchField, "User Group Search Field", "AdsTestAdUnitOnly");
				try {
					List<MobileElement> AdsTestSwitches = Ad.findElementsByName("AdsTestAdUnitOnly");
					try {
						AdsTestSwitch = AdsTestSwitches.get(1);
					}catch (Exception ex) {
						AdsTestSwitch = Ad.findElementByXPath("//*[@name='AdsTestAdUnitOnly']/following-sibling::XCUIElementTypeSwitch");
					}
				} catch (Exception e) {
					// Back to AirlockGroup
					Ad.findElementByName(ReadExcelValues.data[22][Cap]).click();
					// Select UserGroup

					Ad.findElementByName(ReadExcelValues.data[16][Cap]).click();
					Thread.sleep(10000);
					List<MobileElement> AdsTestSwitches = Ad.findElementsByName("AdsTestAdUnitOnly");
					try {
						AdsTestSwitch = AdsTestSwitches.get(1);
					}catch (Exception ex) {
						AdsTestSwitch = Ad.findElementByXPath("//*[@name='AdsTestAdUnitOnly']/following-sibling::XCUIElementTypeSwitch");
					}

				}
				SwitchValue = AdsTestSwitch.getAttribute("value");
				if (SwitchValue.equals("1")) {
					System.out.println("AdsTestAdUnitOnly already enabled");
					logStep("AdsTestAdUnitOnly already enabled");
				} else {
					AdsTestSwitch.click();
					System.out.println("AdsTestAdUnitOnly enabled");
					logStep("AdsTestAdUnitOnly enabled");
				}
				try {
					//Ad.findElementByName("Cancel").click();
					cancel = Ad.findElement(byCancel);
					TestBase.clickOnElement(byCancel, cancel, "Cancel Button");
				} catch (Exception e) {
					//Ad.findElementByName("Done").click();
					done = Ad.findElement(byDone);
					TestBase.clickOnElement(byDone, done, "Done Button");
				}
			} else if (userGroupName.equalsIgnoreCase("UnlimitedInterstitial")) {
				//Ad.findElementByClassName("XCUIElementTypeSearchField").sendKeys("IOSFLAG-6119");
				userGroupSearchField = Ad.findElement(byUserGroupSearchField);
				TestBase.typeText(userGroupSearchField, "User Group Search Field", "IOSFLAG-6119");
				try {
					List<MobileElement> AdsTestSwitches = Ad.findElementsByName("IOSFLAG-6119");
					try {
						AdsTestSwitch = AdsTestSwitches.get(1);
					}catch (Exception ex) {
						AdsTestSwitch = Ad.findElementByXPath("//*[@name='IOSFLAG-6119']/following-sibling::XCUIElementTypeSwitch");
					}
				} catch (Exception e) {
					// Back to AirlockGroup
					Ad.findElementByName(ReadExcelValues.data[22][Cap]).click();
					// Select UserGroup

					Ad.findElementByName(ReadExcelValues.data[16][Cap]).click();
					Thread.sleep(10000);
					List<MobileElement> AdsTestSwitches = Ad.findElementsByName("IOSFLAG-6119");
					try {
						AdsTestSwitch = AdsTestSwitches.get(1);
					}catch (Exception ex) {
						AdsTestSwitch = Ad.findElementByXPath("//*[@name='IOSFLAG-6119']/following-sibling::XCUIElementTypeSwitch");
					}

				}
				SwitchValue = AdsTestSwitch.getAttribute("value");
				if (SwitchValue.equals("1")) {
					System.out.println("IOSFLAG-6119 already enabled");
					logStep("IOSFLAG-6119 already enabled");
				} else {
					AdsTestSwitch.click();
					System.out.println("IOSFLAG-6119 enabled");
					logStep("IOSFLAG-6119 enabled");
				}
				try {
					//Ad.findElementByName("Cancel").click();
					cancel = Ad.findElement(byCancel);
					TestBase.clickOnElement(byCancel, cancel, "Cancel Button");
				} catch (Exception e) {
					//Ad.findElementByName("Done").click();
					done = Ad.findElement(byDone);
					TestBase.clickOnElement(byDone, done, "Done Button");
				}
			} else if (userGroupName.equalsIgnoreCase("IOSFLAG-6963")) {
				//Ad.findElementByClassName("XCUIElementTypeSearchField").sendKeys("IOSFLAG-7423");
				userGroupSearchField = Ad.findElement(byUserGroupSearchField);
				TestBase.typeText(userGroupSearchField, "User Group Search Field", "IOSFLAG-7423");
				try {
					List<MobileElement> AdsTestSwitches = Ad.findElementsByName("IOSFLAG-7423");
					try {
						AdsTestSwitch = AdsTestSwitches.get(1);
					}catch (Exception ex) {
						AdsTestSwitch = Ad.findElementByXPath("//*[@name='IOSFLAG-7423']/following-sibling::XCUIElementTypeSwitch");
					}
				} catch (Exception e) {
					// Back to AirlockGroup
					Ad.findElementByName(ReadExcelValues.data[22][Cap]).click();
					// Select UserGroup

					Ad.findElementByName(ReadExcelValues.data[16][Cap]).click();
					Thread.sleep(10000);
					List<MobileElement> AdsTestSwitches = Ad.findElementsByName("IOSFLAG-7423");
					try {
						AdsTestSwitch = AdsTestSwitches.get(1);
					}catch (Exception ex) {
						AdsTestSwitch = Ad.findElementByXPath("//*[@name='IOSFLAG-7423']/following-sibling::XCUIElementTypeSwitch");
					}

				}
				SwitchValue = AdsTestSwitch.getAttribute("value");
				if (SwitchValue.equals("1")) {
					System.out.println("IOSFLAG-7423 already enabled");
					logStep("IOSFLAG-7423 already enabled");
				} else {
					AdsTestSwitch.click();
					System.out.println("IOSFLAG-7423 enabled");
					logStep("IOSFLAG-7423 enabled");
				}
				try {
					//Ad.findElementByName("Cancel").click();
					cancel = Ad.findElement(byCancel);
					TestBase.clickOnElement(byCancel, cancel, "Cancel Button");
				} catch (Exception e) {
					//Ad.findElementByName("Done").click();
					done = Ad.findElement(byDone);
					TestBase.clickOnElement(byDone, done, "Done Button");
				}
			} else if (userGroupName.equalsIgnoreCase("amazon")) {
				/**
				 * To get amazon preload amazon call 'amazon' usergroup to be enabled as per
				 * https://jira.weather.com:8443/browse/IOSFLAG-7375
				 * This usergroup selection to be removed later, once config moved to production
				 */
				//Ad.findElementByClassName("XCUIElementTypeSearchField").sendKeys("UnlimitedInterstitial");
				userGroupSearchField = Ad.findElement(byUserGroupSearchField);
				TestBase.typeText(userGroupSearchField, "User Group Search Field", "UnlimitedInterstitial");
				try {
					List<MobileElement> AdsTestSwitches = Ad.findElementsByName("UnlimitedInterstitial");
					try {
						AdsTestSwitch = AdsTestSwitches.get(1);
					}catch (Exception ex) {
						AdsTestSwitch = Ad.findElementByXPath("//*[@name='UnlimitedInterstitial']/following-sibling::XCUIElementTypeSwitch");
					}
				} catch (Exception e) {
					// Back to AirlockGroup
					Ad.findElementByName(ReadExcelValues.data[22][Cap]).click();
					// Select UserGroup

					Ad.findElementByName(ReadExcelValues.data[16][Cap]).click();
					Thread.sleep(10000);
					List<MobileElement> AdsTestSwitches = Ad.findElementsByName("UnlimitedInterstitial");
					try {
						AdsTestSwitch = AdsTestSwitches.get(1);
					}catch (Exception ex) {
						AdsTestSwitch = Ad.findElementByXPath("//*[@name='UnlimitedInterstitial']/following-sibling::XCUIElementTypeSwitch");
					}

				}
				SwitchValue = AdsTestSwitch.getAttribute("value");
				if (SwitchValue.equals("1")) {
					System.out.println("UnlimitedInterstitial already enabled");
					logStep("UnlimitedInterstitial already enabled");
				} else {
					AdsTestSwitch.click();
					System.out.println("UnlimitedInterstitial enabled");
					logStep("UnlimitedInterstitial enabled");
				}
				try {
					//Ad.findElementByName("Cancel").click();
					cancel = Ad.findElement(byCancel);
					TestBase.clickOnElement(byCancel, cancel, "Cancel Button");
				} catch (Exception e) {
					//Ad.findElementByName("Done").click();
					done = Ad.findElement(byDone);
					TestBase.clickOnElement(byDone, done, "Done Button");
				}
				
				//Ad.findElementByClassName("XCUIElementTypeSearchField").sendKeys("IOSFLAG-6119");
				userGroupSearchField = Ad.findElement(byUserGroupSearchField);
				TestBase.typeText(userGroupSearchField, "User Group Search Field", "IOSFLAG-6119");
				try {
					List<MobileElement> AdsTestSwitches = Ad.findElementsByName("IOSFLAG-6119");
					try {
						AdsTestSwitch = AdsTestSwitches.get(1);
					}catch (Exception ex) {
						AdsTestSwitch = Ad.findElementByXPath("//*[@name='IOSFLAG-6119']/following-sibling::XCUIElementTypeSwitch");
					}
				} catch (Exception e) {
					// Back to AirlockGroup
					Ad.findElementByName(ReadExcelValues.data[22][Cap]).click();
					// Select UserGroup

					Ad.findElementByName(ReadExcelValues.data[16][Cap]).click();
					Thread.sleep(10000);
					List<MobileElement> AdsTestSwitches = Ad.findElementsByName("IOSFLAG-6119");
					try {
						AdsTestSwitch = AdsTestSwitches.get(1);
					}catch (Exception ex) {
						AdsTestSwitch = Ad.findElementByXPath("//*[@name='IOSFLAG-6119']/following-sibling::XCUIElementTypeSwitch");
					}

				}
				SwitchValue = AdsTestSwitch.getAttribute("value");
				if (SwitchValue.equals("1")) {
					System.out.println("IOSFLAG-6119 already enabled");
					logStep("IOSFLAG-6119 already enabled");
				} else {
					AdsTestSwitch.click();
					System.out.println("IOSFLAG-6119 enabled");
					logStep("IOSFLAG-6119 enabled");
				}
				try {
					//Ad.findElementByName("Cancel").click();
					cancel = Ad.findElement(byCancel);
					TestBase.clickOnElement(byCancel, cancel, "Cancel Button");
				} catch (Exception e) {
					//Ad.findElementByName("Done").click();
					done = Ad.findElement(byDone);
					TestBase.clickOnElement(byDone, done, "Done Button");
				}
				
				//Ad.findElementByClassName("XCUIElementTypeSearchField").sendKeys("Criteo");
				userGroupSearchField = Ad.findElement(byUserGroupSearchField);
				TestBase.typeText(userGroupSearchField, "User Group Search Field", "Criteo");
				try {
					List<MobileElement> AdsTestSwitches = Ad.findElementsByName("Criteo");
					try {
						AdsTestSwitch = AdsTestSwitches.get(1);
					}catch (Exception ex) {
						AdsTestSwitch = Ad.findElementByXPath("//*[@name='Criteo']/following-sibling::XCUIElementTypeSwitch");
					}
				} catch (Exception e) {
					// Back to AirlockGroup
					Ad.findElementByName(ReadExcelValues.data[22][Cap]).click();
					// Select UserGroup

					Ad.findElementByName(ReadExcelValues.data[16][Cap]).click();
					Thread.sleep(10000);
					List<MobileElement> AdsTestSwitches = Ad.findElementsByName("Criteo");
					try {
						AdsTestSwitch = AdsTestSwitches.get(1);
					}catch (Exception ex) {
						AdsTestSwitch = Ad.findElementByXPath("//*[@name='Criteo']/following-sibling::XCUIElementTypeSwitch");
					}

				}
				SwitchValue = AdsTestSwitch.getAttribute("value");
				if (SwitchValue.equals("1")) {
					System.out.println("Criteo already enabled");
					logStep("Criteo already enabled");
				} else {
					AdsTestSwitch.click();
					System.out.println("Criteo enabled");
					logStep("Criteo enabled");
				}
				try {
					//Ad.findElementByName("Cancel").click();
					cancel = Ad.findElement(byCancel);
					TestBase.clickOnElement(byCancel, cancel, "Cancel Button");
				} catch (Exception e) {
					//Ad.findElementByName("Done").click();
					done = Ad.findElement(byDone);
					TestBase.clickOnElement(byDone, done, "Done Button");
				}
			} else if (userGroupName.equalsIgnoreCase("Criteo") || userGroupName.equalsIgnoreCase("iosflag8541")) {
				/**
				 * To get amazon preload amazon call 'amazon' usergroup to be enabled as per
				 * https://jira.weather.com:8443/browse/IOSFLAG-7375
				 * This usergroup selection to be removed later, once config moved to production
				 */
				//Ad.findElementByClassName("XCUIElementTypeSearchField").sendKeys("UnlimitedInterstitial");
				userGroupSearchField = Ad.findElement(byUserGroupSearchField);
				TestBase.typeText(userGroupSearchField, "User Group Search Field", "UnlimitedInterstitial");
				try {
					List<MobileElement> AdsTestSwitches = Ad.findElementsByName("UnlimitedInterstitial");
					try {
						AdsTestSwitch = AdsTestSwitches.get(1);
					}catch (Exception ex) {
						AdsTestSwitch = Ad.findElementByXPath("//*[@name='UnlimitedInterstitial']/following-sibling::XCUIElementTypeSwitch");
					}
				} catch (Exception e) {
					// Back to AirlockGroup
					Ad.findElementByName(ReadExcelValues.data[22][Cap]).click();
					// Select UserGroup

					Ad.findElementByName(ReadExcelValues.data[16][Cap]).click();
					Thread.sleep(10000);
					List<MobileElement> AdsTestSwitches = Ad.findElementsByName("UnlimitedInterstitial");
					try {
						AdsTestSwitch = AdsTestSwitches.get(1);
					}catch (Exception ex) {
						AdsTestSwitch = Ad.findElementByXPath("//*[@name='UnlimitedInterstitial']/following-sibling::XCUIElementTypeSwitch");
					}

				}
				SwitchValue = AdsTestSwitch.getAttribute("value");
				if (SwitchValue.equals("1")) {
					System.out.println("UnlimitedInterstitial already enabled");
					logStep("UnlimitedInterstitial already enabled");
				} else {
					AdsTestSwitch.click();
					System.out.println("UnlimitedInterstitial enabled");
					logStep("UnlimitedInterstitial enabled");
				}
				try {
					//Ad.findElementByName("Cancel").click();
					cancel = Ad.findElement(byCancel);
					TestBase.clickOnElement(byCancel, cancel, "Cancel Button");
				} catch (Exception e) {
					//Ad.findElementByName("Done").click();
					done = Ad.findElement(byDone);
					TestBase.clickOnElement(byDone, done, "Done Button");
				}
				
				
			}
			/*
			 * Below snippet of code is being added for time being to set the app into
			 * non-premium mode, currently 7 day trial of premium mode is enabled This will
			 * be removed once trial period is ended Enable below commented code whenever
			 * premium mode trial is enabled.. to set the app in non-premium mode
			 */

			/*
			 * Thread.sleep(5000); if (!userGroupName.equalsIgnoreCase("Any")) { // Select
			 * Test Group Ad.findElementByClassName("XCUIElementTypeSearchField").sendKeys(
			 * "IOSFLAG-4326"); try { List<MobileElement> AdsTestSwitches =
			 * Ad.findElementsByName("IOSFLAG-4326"); AdsTestSwitch =
			 * AdsTestSwitches.get(1); } catch (Exception e) { // Back to AirlockGroup
			 * Ad.findElementByName(readExcelValues.data[22][Cap]).click(); // Select
			 * UserGroup
			 * 
			 * Ad.findElementByName(readExcelValues.data[16][Cap]).click();
			 * Thread.sleep(10000); List<MobileElement> AdsTestSwitches =
			 * Ad.findElementsByName("IOSFLAG-4326"); AdsTestSwitch =
			 * AdsTestSwitches.get(1);
			 * 
			 * } SwitchValue = AdsTestSwitch.getAttribute("value"); if
			 * (SwitchValue.equals("1")) {
			 * System.out.println("IOSFLAG-4326 already enabled");
			 * logStep("IOSFLAG-4326 already enabled"); } else { AdsTestSwitch.click();
			 * System.out.println("IOSFLAG-4326 enabled"); logStep("IOSFLAG-4326 enabled");
			 * } try { Ad.findElementByName("Cancel").click(); } catch (Exception e) {
			 * Ad.findElementByName("Done").click(); } }
			 */
		}

		// Back to AirlockGroup
		Ad.findElementByName(ReadExcelValues.data[22][Cap]).click();

		// Click on Done
		//Ad.findElementByName("Done").click();
		done = Ad.findElement(byDone);
		TestBase.clickOnElement(byDone, done, "Done Button");
		Thread.sleep(3000);
		try {
			Functions.close_launchApp();
			Functions.checkForAppState();
		} catch (Exception e) {
			Ad.closeApp();
			Ad.launchApp();
			Functions.checkForAppState();
		}
		try {
			Functions.close_launchApp();
			Functions.checkForAppState();
		} catch (Exception e) {
			Ad.closeApp();
			Ad.launchApp();
			Functions.checkForAppState();
		}

		// after close and launch check if home screen is displayed or not by validating
		// settings button, for this verifying for settings button
		try {
			MobileElement appSettings = Ad.findElementByName(ReadExcelValues.data[1][Cap]);
		} catch (Exception e) {
			System.out.println("App home screen is not dispalyed after kill and launch");
			logStep("App home screen is not dispalyed after kill and launch");
			attachScreen();
			ftlScreens = new FTLScreens(Ad);
			ftlScreens.handle_Unwanted_Popups();
		}

	}
	
	public  void clickOnVersionnumber() throws Exception {
		By byTestModeSettings = MobileBy.id("com.weather.Weather:id/test_mode_settings");
		if (!TestBase.isElementExists(byTestModeSettings)) {
			try {
				
				System.out.println("Clicking on Version number  till test mode settings option is displaying");
				logStep("Clicking on Version number till test mode settings option is displaying");	
				for(int i=1;i<10;i++) {		 
					Ad.findElementById("com.weather.Weather:id/about_version").click();
					 Thread.sleep(6000);
			}
			}
			catch(Exception e) {
				System.out.println("Clicking on Version number  till test mode settings option is displaying");
				logStep("Clicking on Version number till test mode settings option is displaying");	
				for(int i=1;i<10;i++) {			 
					Ad.findElementById("com.weather.Weather:id/about_version").click();
					 Thread.sleep(6000);
				}
			}	
		}
		
			
		}
	
	public  void clickOnTestModeSettings() throws Exception {
		try {
		System.out.println("Clicking on test mode settings");
		logStep("Clicking on test mode settings");
		Ad.findElementById("com.weather.Weather:id/test_mode_settings").click();
		Thread.sleep(6000);
		}catch(Exception e) {
			System.out.println("Clicking on test mode settings");
			logStep("Clicking on test mode settings");
			Ad.findElementById("com.weather.Weather:id/test_mode_settings").click();
			Thread.sleep(6000);
		}
	}
	
public  void clickOnAirlock() throws Exception {
		
	 	//clicking on Airlock
			try {
	System.out.println("Clicking on Airlock");
	logStep("Clicking on Airlock");
	//Thread.sleep(15000);
			 List<MobileElement> all=Ad.findElementsById("android:id/title");
			// Thread.sleep(15000);
			 for(WebElement Airlock:all) {
				if( Airlock.getAttribute("text").equalsIgnoreCase("Airlock")) {
					new WebDriverWait(Ad, Functions.maxTimeout).until(ExpectedConditions.elementToBeClickable(Airlock));
					Airlock.click();
				//	 Thread.sleep(15000);
					 break;
				 }
			 }
			}
			catch(Exception e) {
				System.out.println("Clicking on Airlock");
				logStep("Clicking on Airlock");
				 List<MobileElement> all=Ad.findElementsByClassName("android.widget.LinearLayout");
				 for(WebElement Airlock:all) {
					if( Airlock.getAttribute("text").contains("Airlock")) {
						new WebDriverWait(Ad, Functions.maxTimeout).until(ExpectedConditions.elementToBeClickable(Airlock));
						 Airlock.click();
						// Thread.sleep(5000);
						 break;
			}
				 }
		}
		}

public  void clickOnUserGroups() throws Exception {
	System.out.println("Clicking on User Groups");
	logStep("Clicking on User Groups");
	List<MobileElement> all=Ad.findElementsById("android:id/title");
	 for(WebElement Airlock:all) {
		if( Airlock.getAttribute("text").equalsIgnoreCase("User Groups")) {
			new WebDriverWait(Ad, Functions.maxTimeout).until(ExpectedConditions.elementToBeClickable(Airlock));
			 Airlock.click();
			 Thread.sleep(5000);
			 break;
		 }
	 }
}

public  void clickOnBranch() throws Exception {
	System.out.println("Clicking on Branch");
	logStep("Clicking on Branch");
	List<MobileElement> all=Ad.findElementsById("android:id/title");
	 for(WebElement Airlock:all) {
		if( Airlock.getAttribute("text").equalsIgnoreCase("Branch")) {
			new WebDriverWait(Ad, Functions.maxTimeout).until(ExpectedConditions.elementToBeClickable(Airlock));
			 Airlock.click();
			 Thread.sleep(5000);
			 break;
		 }
	 }
}

public  void enablingResponsiveMode() throws Exception{
System.out.println("Enabling Responsive Mode");
	logStep("Enabling Responsive Mode");
	
		Ad.findElementById("android:id/checkbox").click();

		Thread.sleep(6000);
}
	

public  void enterRequiredUserGroup(String usergroup) throws Exception {
	
	System.out.println("entering "+ usergroup);
	logStep("entering "+ usergroup);
	MobileElement ugSearchBar = null;
	try {
		Thread.sleep(5000);
		ugSearchBar = Ad.findElementById("com.weather.Weather:id/search_bar");
		//Ad.findElementById("com.weather.Weather:id/search_bar").sendKeys(usergroup);
		
	} catch (Exception e) {
		System.out.println("Looks user group selection page not displayed");
		logStep("Looks user group selection page not displayed");
		clickOnUserGroups();
	}
	TestBase.typeText(ugSearchBar, "UserGroup", usergroup);
	  Thread.sleep(15000);
  try {
  List<MobileElement> all=Ad.findElementsById("android:id/text1");
	// Thread.sleep(5000);
	 for(WebElement req:all) {
		if( req.getAttribute("text").equalsIgnoreCase(usergroup)) {
			new WebDriverWait(Ad, Functions.maxTimeout).until(ExpectedConditions.elementToBeClickable(req));
		
		//	 Thread.sleep(15000);
			 req.click();
			Thread.sleep(5000);
			attachScreen();
			 break;
		 }
	 }
	 
	 if (usergroup.equalsIgnoreCase("WMCards")) {
		 TestBase.typeText(ugSearchBar, "UserGroup", "AdsTestAdUnitOnly");
		 Thread.sleep(15000);
		 List<MobileElement> alls=Ad.findElementsById("android:id/text1");
			// Thread.sleep(5000);
			 for(WebElement req:alls) {
				if( req.getAttribute("text").equalsIgnoreCase("AdsTestAdUnitOnly")) {
					new WebDriverWait(Ad, Functions.maxTimeout).until(ExpectedConditions.elementToBeClickable(req));
				
				//	 Thread.sleep(15000);
					 req.click();
					Thread.sleep(5000);
					attachScreen();
					 break;
				 }
			 }
	 }
  }
  catch(Exception e){
	System.out.println("There is an exception while selecting usergroup");
	logStep("There is an exception while selecting usergroup");
	e.printStackTrace();
  }
  Ad.navigate().back();
  Thread.sleep(2000);
  Ad.navigate().back();
}

public  void enterRequiredBranch(String branch) throws Exception {
	
	System.out.println("entering "+ branch);
	logStep("entering "+ branch);
	MobileElement branchSearchBar = null;
	try {
		Thread.sleep(5000);
		branchSearchBar = Ad.findElementById("com.weather.Weather:id/search_bar");
		//Ad.findElementById("com.weather.Weather:id/search_bar").sendKeys(usergroup);
		
	} catch (Exception e) {
		System.out.println("Looks branch selection page not displayed");
		logStep("Looks branch selection page not displayed");
		clickOnBranch();
	}
	TestBase.typeText(branchSearchBar, "Branch", branch);
  Thread.sleep(15000);
  try {
  List<MobileElement> all=Ad.findElementsById("android:id/text1");
	// Thread.sleep(5000);
	 for(WebElement req:all) {
		if( req.getAttribute("text").equalsIgnoreCase(branch)) {
			new WebDriverWait(Ad, Functions.maxTimeout).until(ExpectedConditions.elementToBeClickable(req));
		
		//	 Thread.sleep(15000);
			 req.click();
			Thread.sleep(5000);
			
			 break;
		 }
	 }
	 
  }
  catch(Exception e){
	 System.out.println("There is an exception while selecting branch");
	logStep("There is an exception while selecting branch");
	e.printStackTrace();
  }
  Ad.navigate().back();
  Thread.sleep(2000);
  Ad.navigate().back();
  Thread.sleep(2000);
  Ad.navigate().back();
}
	
	public  void select_Airlock_UserGroup(String usergroup) throws Exception{
		
		try {
			clickOnSettingIcon();
			attachScreen();
		} catch (Exception e) {
			 System.out.println("There is an exception while navigating to Settings");
			logStep("There is an exception while navigating to Settings");
			attachScreen();
			e.printStackTrace();
		}
		
			swipe_Up(Ad);
			swipe_Up(Ad);
			//Swipe_Conter(10);
			// cliking on aboutthisapp
			clickOnAboutthisapp_UPSX();
			attachScreen();
		
	     	
	     	clickOnVersionnumber();
	 	
	     	clickOnTestModeSettings() ;
			
	     	clickOnAirlock();
			
	     	//enablingResponsiveMode();
			
	     	clickOnUserGroups();
		
	     	enterRequiredUserGroup(usergroup);
	     	
	     	Functions.close_launchAppAndroid();
		
		}
	
public  void select_Airlock_Branch(String branch) throws Exception{
		
		try {
			clickOnSettingIcon();
			attachScreen();
		} catch (Exception e) {
			System.out.println("There is an exception while navigating to Settings");
			logStep("There is an exception while navigating to Settings");
			e.printStackTrace();
		}
		
			swipe_Up(Ad);
			swipe_Up(Ad);
			//Swipe_Conter(10);
			// cliking on aboutthisapp
			clickOnAboutthisapp_UPSX();
			attachScreen();
		
	     	
	     	clickOnVersionnumber();
	 	
	     	clickOnTestModeSettings() ;
			
	     	clickOnAirlock();
			
	     	//enablingResponsiveMode();
			
	     	clickOnBranch();
		
	     	enterRequiredBranch(branch);
	     	
	     	Functions.close_launchAppAndroid();
		
		}

	@Step("Clear Airlock User Groups")
	public void clearAirlock() throws Exception {
		System.out.println("Try to clear Airlock groups/branch");
		logStep("Try to clear Airlock groups/branch");
		ReadExcelValues.excelValues("Smoke", "TestMode");
		Ad.findElementByName(ReadExcelValues.data[24][Cap]).click();
		try {
			//Ad.findElementByName("OK").click();
			oK = Ad.findElement(byOK);
			TestBase.clickOnElement(byOK, oK, "OK Button");
			System.out.println("Airlock filter Clear with OK");
			logStep("Airlock filter Clear with OK");

		} catch (Exception e) {
			System.out.println("An exception in Airlock filter Clear");
			logStep("An exception in Airlock filter Clear");
		}
	}

	@Step("Enable Responsive Mode, if not enabled already")
	public void checkResponsiveMode() throws Exception {
		logStep("try to enable Responsive Mode");
		ReadExcelValues.excelValues("Smoke", "TestMode");
		Functions.scroll_Down();
		List<MobileElement> responsiveModes = Ad.findElementsByName("Responsive Mode");
		attachScreen();
		try {
			MobileElement responsiveSwitch = responsiveModes.get(1);
			String responsiveSwitchValue = responsiveSwitch.getAttribute("value");
			
			if (responsiveSwitchValue.equals("1")) {
				System.out.println("Responsive Mode already enabled");
				logStep("Responsive Mode already enabled");
			} else {
				responsiveSwitch.click();
				logStep("Responsive Mode enabled");
			}	
		}catch (Exception e) {
			MobileElement responsiveSwitch = Ad.findElementByXPath("//*[@name='Responsive Mode']/following-sibling::XCUIElementTypeSwitch");
			String responsiveSwitchValue = responsiveSwitch.getAttribute("value");
			
			if (responsiveSwitchValue.equals("1")) {
				System.out.println("Responsive Mode already enabled");
				logStep("Responsive Mode already enabled");
			} else {
				responsiveSwitch.click();
				logStep("Responsive Mode enabled");
			}
		}
		

	}


	@Step("Clear Airlock Cache")
	public void clear_Airlock_Cache() throws Exception {
		FTLScreens ftlScreens;

		System.out.println("Trying to clear Airlock Cache");
		logStep("Trying to clear Airlock Cache");
		ReadExcelValues.excelValues("Smoke", "TestMode");

		MobileElement el = null;
		Thread.sleep(2000);

		try {
			Ad.findElementByName(ReadExcelValues.data[1][Cap]).click();
		} catch (Exception e1) {
			try {
				Functions.close_launchApp();
			} catch (Exception e2) {

			} finally {
				Ad.findElementByName(ReadExcelValues.data[1][Cap]).click();
			}
		}

		Thread.sleep(3000);
		Functions.scroll_Down();
		Thread.sleep(4000);
		// Select Diagnostics
		MobileElement el2 = (MobileElement) Ad.findElementByName(ReadExcelValues.data[7][Cap]);
		el2.click();
		Thread.sleep(1000);

		// airlock
		Ad.findElementByName(ReadExcelValues.data[12][Cap]).click();
		Thread.sleep(1000);
		Functions.scroll_Down();

		// Select General
		//Ad.findElementByName("General").click();
		general = Ad.findElement(byGeneral);
		TestBase.clickOnElement(byGeneral, general, "General Button");
		Thread.sleep(4000);

		//Ad.findElementByName("Clear Cache").click();
		clearCache = Ad.findElement(byClearCache);
		TestBase.clickOnElement(byClearCache, clearCache, "Clear Cache Button");
		Thread.sleep(4000);
		try {
			//Ad.findElementByName("OK").click();
			oK = Ad.findElement(byOK);
			TestBase.clickOnElement(byOK, oK, "OK Button");
			System.out.println("Airlock Cache Clear with OK");
			logStep("Airlock Cache Clear with OK");

		} catch (Exception e) {
			System.out.println("An exception in Airlock Cache Clear");
			logStep("An exception in Airlock Cache Clear");
		}

		// Back to AirlockGroup
		Ad.findElementByName(ReadExcelValues.data[22][Cap]).click();
		// Back to About this page
		Ad.findElementByName(ReadExcelValues.data[18][Cap]).click();

		// Save the settings
		// Ad.findElementByName(readExcelValues.data[19][Cap]).click();

		Thread.sleep(3000);
		try {
			Functions.close_launchApp();
			Functions.checkForAppState();
		} catch (Exception e) {
			Ad.closeApp();
			Ad.launchApp();
			Functions.checkForAppState();
		}
		try {
			Functions.close_launchApp();
			Functions.checkForAppState();
		} catch (Exception e) {
			Ad.closeApp();
			Ad.launchApp();
			Functions.checkForAppState();
		}

		// after close and launch check if home screen is displayed or not by validating
		// settings button, for this verifying for settings button
		try {
			MobileElement appSettings = Ad.findElementByName(ReadExcelValues.data[1][Cap]);
		} catch (Exception e) {
			System.out.println("App home screen is not dispalyed after kill and launch");
			logStep("App home screen is not dispalyed after kill and launch");
			attachScreen();
			ftlScreens = new FTLScreens(Ad);
			ftlScreens.handle_Unwanted_Popups();
		}

	}
	
	@Step("Enable Test Mode For Amazon Ads")
	public void enableTestModeForAmazonAds() throws Exception {
		FTLScreens ftlScreens;
		System.out.println("Trying to enable Test Mode For Amazon Ads");
		logStep("Trying to enable Test Mode For Amazon Ads");
		ReadExcelValues.excelValues("Smoke", "TestMode");

		MobileElement el = null;
		Thread.sleep(2000);
		try {
			Ad.findElementByName(ReadExcelValues.data[1][Cap]).click();
		} catch (Exception e1) {
			try {
				Functions.close_launchApp();
			} catch (Exception e2) {

			} finally {
				Ad.findElementByName(ReadExcelValues.data[1][Cap]).click();
			}
		}

		Thread.sleep(3000);
		Functions.scroll_Down();
		
		Thread.sleep(4000);
		// Select Diagnostics
		MobileElement el2 = (MobileElement) Ad.findElementByName(ReadExcelValues.data[7][Cap]);
		el2.click();
		Thread.sleep(1000);
		Functions.scroll_Down();
		Functions.scroll_Down();
		MobileElement AmazonAdsSwitch = null;
		String SwitchValue = null;
		
		List<MobileElement> AmazonAdsSwitches = Ad.findElementsByName("Amazon Ads");
		try {
			AmazonAdsSwitch = AmazonAdsSwitches.get(1);
		}catch (Exception ex) {
			AmazonAdsSwitch = Ad.findElementByXPath("//*[@name='Amazon Ads']/following-sibling::XCUIElementTypeSwitch");
		}
			
		SwitchValue = AmazonAdsSwitch.getAttribute("value");
		if (SwitchValue.equals("1")) {
			System.out.println("AmazonAds Test Mode already enabled");
			logStep("AmazonAds Test Mode already enabled");
		} else {
			AmazonAdsSwitch.click();
			System.out.println("AmazonAds Test Mode enabled");
			logStep("AmazonAds Test Mode enabled");
		}
	
		Thread.sleep(3000);
		try {
			Functions.close_launchApp();
			Functions.checkForAppState();
		} catch (Exception e) {
			Ad.closeApp();
			Ad.launchApp();
			Functions.checkForAppState();
		}
		try {
			Functions.close_launchApp();
			Functions.checkForAppState();
		} catch (Exception e) {
			Ad.closeApp();
			Ad.launchApp();
			Functions.checkForAppState();
		}

		// after close and launch check if home screen is displayed or not by validating
		// settings button, for this verifying for settings button
		try {
			MobileElement appSettings = Ad.findElementByName(ReadExcelValues.data[1][Cap]);
		} catch (Exception e) {
			System.out.println("App home screen is not dispalyed after kill and launch");
			logStep("App home screen is not dispalyed after kill and launch");
			attachScreen();
			ftlScreens = new FTLScreens(Ad);
			ftlScreens.handle_Unwanted_Popups();
		}

	}

	@Step("Set GPS Spoof")
	public void GPS_Spoof_On(String cityName, String stateName) throws Exception {
				
		System.out.println("Spoofing GPS Location For: "+cityName+", "+stateName);
		logStep("Spoofing GPS Location For: "+cityName+", "+stateName);
		ReadExcelValues.excelValues("Smoke", "TestMode");

		MobileElement el = null;
		Thread.sleep(2000);
		try {
			Ad.findElementByName(ReadExcelValues.data[1][Cap]).click();
		} catch (Exception e1) {
			try {
				Functions.close_launchApp();
			} catch (Exception e2) {

			} finally {
				Ad.findElementByName(ReadExcelValues.data[1][Cap]).click();
			}
		}

		Thread.sleep(3000);
		Functions.scroll_Down();
		Thread.sleep(4000);
		// Select Diagnostics
		MobileElement el2 = (MobileElement) Ad.findElementByName(ReadExcelValues.data[7][Cap]);
		el2.click();
		Thread.sleep(1000);
		
		//Select Spoof GPS Location
		Ad.findElement(By.xpath("//XCUIElementTypeStaticText[@name='Spoof GPS Location']")).click();

		//Turn Off Spoofing
		Ad.findElement(By.xpath("//XCUIElementTypeButton[@name='Off']")).click();
		
		//Click on City
		Ad.findElement(By.xpath("//XCUIElementTypeButton[@name='City']")).click();
		
		MobileElement spoofCity = Ad.findElement(By.xpath("//XCUIElementTypeStaticText[@name='City:']/following-sibling::XCUIElementTypeTextField"));
		MobileElement spoofState = Ad.findElement(By.xpath("//XCUIElementTypeStaticText[@name='State:']/following-sibling::XCUIElementTypeTextField"));
		
		TestBase.typeText(spoofCity, "City:", cityName);
		TestBase.typeText(spoofState, "State:", stateName);
		
		Ad.findElement(By.xpath("//XCUIElementTypeButton[@name='Geocode']")).click();
		
		//click Ok on Spoofing Success Alert
		Ad.findElement(By.xpath("//XCUIElementTypeButton[@name='Ok']")).click();
		
		//Navigate Back
		Ad.findElement(By.xpath("//XCUIElementTypeButton[@name='Internal Diagnostics']")).click();
				
		Thread.sleep(3000);
		try {
			Ad.terminateApp("com.weather.TWC");
			Ad.launchApp();
		} catch (Exception e) {
			Functions.close_launchApp();
		}

	}
	
	@Step("GPS Spoof Off")
	public void GPS_Spoof_Off() throws Exception {
				
		System.out.println("Turn Off GPS Location Spoofing");
		logStep("Turn Off GPS Location Spoofing");
		ReadExcelValues.excelValues("Smoke", "TestMode");

		MobileElement el = null;
		Thread.sleep(2000);
		try {
			Ad.findElementByName(ReadExcelValues.data[1][Cap]).click();
		} catch (Exception e1) {
			try {
				Functions.close_launchApp();
			} catch (Exception e2) {

			} finally {
				Ad.findElementByName(ReadExcelValues.data[1][Cap]).click();
			}
		}

		Thread.sleep(3000);
		Functions.scroll_Down();
		Thread.sleep(4000);
		// Select Diagnostics
		MobileElement el2 = (MobileElement) Ad.findElementByName(ReadExcelValues.data[7][Cap]);
		el2.click();
		Thread.sleep(1000);
		
		//Select Spoof GPS Location
		Ad.findElement(By.xpath("//XCUIElementTypeStaticText[@name='Spoof GPS Location']")).click();

		//Turn Off Spoofing
		Ad.findElement(By.xpath("//XCUIElementTypeButton[@name='Off']")).click();
		
		//Navigate Back
		Ad.findElement(By.xpath("//XCUIElementTypeButton[@name='Internal Diagnostics']")).click();
				
		Thread.sleep(3000);
		try {
			Ad.terminateApp("com.weather.TWC");
			Ad.launchApp();
		} catch (Exception e) {
			Functions.close_launchApp();
		}

	}
	
	public void clickOnPrivacyOptInConfirm() throws Exception {
		try {
			System.out.println("Clicking on Confirm On Privacy Optin Modal");
			logStep("Clicking on Confirm On Privacy Optin Modal");
			//Ad.findElementByAccessibilityId("Setting icon").click();
			pricacyOptInConfirm = Ad.findElement(byPricacyOptInConfirm);
			TestBase.clickOnElement(byPricacyOptInConfirm, pricacyOptInConfirm, "Confirm Button");
			Thread.sleep(10000);
			
		} catch (Exception e) {
			System.out.println("Privacy Optin already might have selected or an Exception while clicking on Confirm button");
			logStep("Privacy Optin already might have selected or an Exception while clicking on Confirm button");
			attachScreen();
			//Assert.fail("An Exception while clicking on Continue button");
		}

	}
	
	public void clickOnSettingIcon() throws Exception {
		try {
			System.out.println("Clicking on Setting Icon");
			logStep("Clicking on Setting Icon");
			//Ad.findElementByAccessibilityId("Setting icon").click();
			try {
				settingsButton = Ad.findElement(bySettingsButton);
				TestBase.clickOnElement(bySettingsButton, settingsButton, "Settings Button");
				Thread.sleep(10000);
			} catch (Exception e) {
				try {
					bySettingsButton = MobileBy.id("com.weather.Weather:id/profile_avatar");
					settingsButton = Ad.findElement(bySettingsButton);
					TestBase.clickOnElement(bySettingsButton, settingsButton, "Settings Button");
					Thread.sleep(10000);
				} catch (Exception e1) {
					//MobileElement moreOptions = Ad.findElement(By.id("com.weather.Weather:id/more_icon"));
					Ad.findElementById("com.weather.Weather:id/more_icon").click();
					Thread.sleep(2000);
					bySettingsButton = MobileBy.id("com.weather.Weather:id/item_settings");
					settingsButton = Ad.findElement(bySettingsButton);
					TestBase.clickOnElement(bySettingsButton, settingsButton, "Settings Button");
					Thread.sleep(10000);
				}
			}
			
		/*	if (TestBase.isElementExists(bySearchIcon)) {
				TestBase.clickOnElement(bySettingsButton, settingsButton, "Settings Button");
			}*/
			boolean found = true;
			while (found) {

				if (TestBase.isElementExists(bySearchIcon, Ad)) {
					found = true;
					TestBase.clickOnElement(bySettingsButton, settingsButton, "Settings Button");
					Thread.sleep(10000);
				} else {
					found = false;
				}
			}
		} catch (Exception e) {
			attachScreen();
			e.printStackTrace();
		}

	}
	
	public void clickOnAboutthisapp_UPSX() throws Exception {
		// Functions.verifyElement(ByAccessibilityId("About this App"));
		try {
			List<MobileElement> all = Ad.findElementsById("android:id/title");
			// Thread.sleep(15000);
			for (WebElement seetingsscreen : all) {
				if (seetingsscreen.getAttribute("text").equalsIgnoreCase("About this App")) {
					//new WebDriverWait(Ad, Functions.maxTimeout).until(ExpectedConditions.elementToBeClickable(seetingsscreen));
					//TestBase.waitForElementToBeClickable(Ad, 60, byAboutLabel);
					seetingsscreen.click();
					Thread.sleep(15000);
					break;
				}
			}
		} catch (Exception e) {

		}
	}
	
	public void gettingApkVersion_UPSX() throws Exception {
		String apkVersion = null;
		// cliking View more Button
		try {
			clickOnSettingIcon();
			attachScreen();
		} catch (Exception e) {
			clickOnSettingIcon();
			attachScreen();
		}
		try {
			swipe_Up(Ad);
			swipe_Up(Ad);
			//Swipe_Conter(10);
			// cliking on aboutthisapp
			clickOnAboutthisapp_UPSX();
			attachScreen();
		} catch (Exception e) {
			swipe_Up(Ad);
			swipe_Up(Ad);
			//Swipe_Conter(10);
			// cliking on aboutthisapp
			clickOnAboutthisapp_UPSX();
			attachScreen();
		}
		try {
			apkVersion = Ad.findElementById("com.weather.Weather:id/about_version").getText();
		} catch (Exception e) {
			apkVersion = Ad.findElementById("com.weather.Weather:id/about_version").getText();

		}
		System.out.println("Android apk build number ::  " + apkVersion);
		apkVersion = apkVersion.split("Version:")[1].trim();

		System.out.println(apkVersion);
		navigateBackToFeedCardAndroid();
		navigateBackToFeedCardAndroid();
		//AppiumFunctions.clickOnBackArrowElement();
		//AppiumFunctions.clickOnBackArrowElement();
		FileOutputStream fos = new FileOutputStream(
				new File(System.getProperty("user.dir") + "/JenkinsEmailConfig.Properties"));
		properties.setProperty("AppVersion", apkVersion);
		properties.store(fos, " App Version read from app and updated");
		fos.close();

	}
	
	public  void clickOnLogin() throws Exception {
		System.out.println("Clicking on Login");
		logStep("Clicking on Login");
		List<MobileElement> all=Ad.findElementsById("android:id/title");
		 for(WebElement login:all) {
			if( login.getAttribute("text").equalsIgnoreCase("Log In")) {
				new WebDriverWait(Ad, Functions.maxTimeout).until(ExpectedConditions.elementToBeClickable(login));
				login.click();
				 Thread.sleep(5000);
				 break;
			 }
		 }
	}
	
	public void clickOnUPSXProfile() throws Exception {
		try {
			System.out.println("Clicking on UPSX profile Icon");
			logStep("Clicking on UPSX profile Icon");
			Ad.findElementById("com.weather.Weather:id/profile_avatar").click();
			Thread.sleep(10000);
			boolean found = true;
			while (found) {

				if (TestBase.isElementExists(bySearchIcon, Ad)) {
					found = true;
					Ad.findElementById("com.weather.Weather:id/profile_avatar").click();
					
				} else {
					found = false;
				}
			}
		} catch (Exception e) {
			settingsButton = Ad.findElement(bySettingsButton);
			TestBase.clickOnElement(bySettingsButton, settingsButton, "Settings Button");
			Thread.sleep(10000);
			boolean found = true;
			while (found) {

				if (TestBase.isElementExists(bySearchIcon, Ad)) {
					found = true;
					TestBase.clickOnElement(bySettingsButton, settingsButton, "Settings Button");
					
				} else {
					found = false;
				}
			}
		}

	}
	
	public  void EmailUPSXLoginUserName(String email) throws Exception {
		try {
		System.out.println("entering "+ email);
		logStep("entering "+ email);
		Ad.findElementById("com.weather.Weather:id/email_edit_text").sendKeys(email);
      Thread.sleep(10000);
		}
		catch(Exception e) {
			System.out.println("entering "+ email);
			logStep("entering "+ email);
			Ad.findElementById("com.weather.Weather:id/email_edit_text").sendKeys(email);
	      Thread.sleep(10000);
		}
      }
	
	public  void EmailUPSXLoginPassword(String password) throws Exception {
		try {
		System.out.println("entering "+ password);
		logStep("entering "+ password);
		Ad.findElementById("com.weather.Weather:id/password_edit_text").sendKeys(password);
      Thread.sleep(10000);
		}
		catch(Exception e) {
			System.out.println("entering "+ password);
			logStep("entering "+ password);
			Ad.findElementById("com.weather.Weather:id/email_edit_text").sendKeys(password);
	      Thread.sleep(10000);
		}
      }
	
	
	public  void clickOnUPSXloginbutton() throws Exception {
		try {
			System.out.println("Clicking on Login button");
			logStep("Clicking on Login button");
			Ad.findElementById("com.weather.Weather:id/login_button").click();
	      Thread.sleep(10000);
			}
			catch(Exception e) {
				System.out.println("Clicking on Login button");
				logStep("Clicking on Login button");
				Ad.findElementById("com.weather.Weather:id/login_button").click();
		      Thread.sleep(10000);
			}
	      }
	
	
	
	public  void logoutfromUPSXAcoount(String email) throws Exception {
		try {
		System.out.println("entering "+ email);
		logStep("entering "+ email);
		Ad.findElementById("com.weather.Weather:id/email_edit_text").sendKeys(email);
      Thread.sleep(10000);
		}
		catch(Exception e) {
			System.out.println("entering "+ email);
			logStep("entering "+ email);
			Ad.findElementById("com.weather.Weather:id/email_edit_text").sendKeys(email);
	      Thread.sleep(10000);
		}
      }
	
	public  void UPSX_login(String email,String password) throws Exception {
		try {
			clickOnSettingIcon();
			attachScreen();
		} catch (Exception e) {
			clickOnSettingIcon();
			attachScreen();
		}
		clickOnLogin();
		attachScreen();
		EmailUPSXLoginUserName(email);
		attachScreen();
	   EmailUPSXLoginPassword(password);
	   attachScreen();
	   clickOnUPSXloginbutton();
	  
	   try {
		   navigateBackToFeedCardAndroid(); 
		   Functions.close_launchAppAndroid();
	   }catch(Exception e){
		   Functions.close_launchAppAndroid(); 
	   }
	   
	   attachScreen();
	}

	public  void UPSX_logout() throws Exception {
		
		clickOnUPSXProfile();
		attachScreen();
		clickOnUPSXSignOut();
		 attachScreen();
		 clickOnUPSXSignOut();
		 try {
			   navigateBackToFeedCardAndroid(); 
			   Functions.close_launchAppAndroid();
		   }catch(Exception e){
			   Functions.close_launchAppAndroid(); 
		   }
		 attachScreen();
	}
	
	public void clickOnUPSXSignOut() throws Exception {
		// Functions.verifyElement(ByAccessibilityId("About this App"));
		try {
			List<MobileElement> all = Ad.findElementsById("android:id/title");
			// Thread.sleep(15000);
			for (WebElement signout : all) {
				if (signout.getAttribute("text").equalsIgnoreCase("Sign Out")) {
					//new WebDriverWait(Ad, Functions.maxTimeout).until(ExpectedConditions.elementToBeClickable(seetingsscreen));
					//TestBase.waitForElementToBeClickable(Ad, 60, byAboutLabel);
					signout.click();
					Thread.sleep(15000);
					break;
				}
			}
		} catch (Exception e) {
			List<MobileElement> all = Ad.findElementsByClassName("android.widget.TextView");
			// Thread.sleep(15000);
			for (WebElement signout : all) {
				if (signout.getAttribute("text").equalsIgnoreCase("	Sign Out")) {
					//new WebDriverWait(Ad, Functions.maxTimeout).until(ExpectedConditions.elementToBeClickable(seetingsscreen));
					//TestBase.waitForElementToBeClickable(Ad, 60, byAboutLabel);
					signout.click();
					Thread.sleep(15000);
					break;
		}
	}
		}
	}

}

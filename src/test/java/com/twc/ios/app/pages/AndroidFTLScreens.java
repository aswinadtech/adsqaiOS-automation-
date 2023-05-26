package com.twc.ios.app.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.twc.ios.app.functions.Functions;
import com.twc.ios.app.general.Driver;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.Utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;

public class AndroidFTLScreens extends Utils {
	AppiumDriver<MobileElement> Ad;
	String settingsButton_AccessibilityId = "settingsButton";
	String closeButton_AccessibilityId = "close_button";
	String cancelButton_Name = "Cancel";
	String changeToAlwaysAllow_Name = "Change to Always Allow";
	String letsGoButton_AccessibilityId = "letsGoButton";
	String continueButton_AccessibilityId = "Continue";
	String allowButton_AccessibilityId = "Allow";
	String iUnderstand_AccessibilityId = "I Understand";
	String oKButton_Name = "OK";
	String alwaysAllow_AccessibilityId = "Always Allow";
	String allowWhileUsingApp_AccessibilityId = "Allow While Using App";
	
	String termsCheckbox_Id = "com.weather.Weather:id/sign_up_checkbox";
	String nextButton_Id = "com.weather.Weather:id/ok_button";
	String iUnderstand_Id = "com.weather.Weather:id/next_button_text";
	String closeButton_Id = "android:id/button2";
	String allow_Id = "com.android.permissioncontroller:id/permission_allow_button";
	String alwaysAllow_Id = "com.android.permissioncontroller:id/permission_allow_always_button";
	String allowWhileUsingApp_Id = "com.android.permissioncontroller:id/permission_allow_foreground_only_button";
	String skipForNow_Id = "com.weather.Weather:id/onboarding_screen_skip_button";
	
	
	
	By bySettingsButton = MobileBy.AccessibilityId(settingsButton_AccessibilityId);
	//By byCloseButton = MobileBy.AccessibilityId(closeButton_AccessibilityId);
	By byCancelButton = MobileBy.name(cancelButton_Name);
	By byChangeToAlwaysAllow = MobileBy.name(changeToAlwaysAllow_Name);
	By byLetsGoButton = MobileBy.AccessibilityId(letsGoButton_AccessibilityId);
	By byContinueButton = MobileBy.AccessibilityId(continueButton_AccessibilityId);
	By byAllow = MobileBy.id(allow_Id);
	//By byIUnderstand = MobileBy.AccessibilityId(iUnderstand_AccessibilityId);
	By byOKButton = MobileBy.name(oKButton_Name);
	//By byAlwaysAllow = MobileBy.AccessibilityId(alwaysAllow_AccessibilityId);
	//By byAllowWhileUsingApp = MobileBy.AccessibilityId(allowWhileUsingApp_AccessibilityId);
	
	By byAlertCenter = MobileBy.AccessibilityId("Go to Alerts and Notifications");
	By bySearchIcon = MobileBy.xpath("//android.widget.ImageView[@resource-id=\"com.weather.Weather:id/search_icon\"]");
	By byTermsCheckbox = MobileBy.id(termsCheckbox_Id);
	By byNextButton = MobileBy.id(nextButton_Id);
	By byIUnderstand = MobileBy.id(iUnderstand_Id);
	By byCloseButton = MobileBy.id(closeButton_Id);
	By byAlwaysAllow = MobileBy.id(alwaysAllow_Id);
	By byAllowWhileUsingApp = MobileBy.id(allowWhileUsingApp_Id);
	By bySkipForNow = MobileBy.id(skipForNow_Id);
	
	MobileElement settingsButton = null;
	MobileElement closeButton = null;
	MobileElement cancelButton = null;
	MobileElement changeToAlwaysAllow = null;
	MobileElement letsGoButton = null;
	MobileElement continueButton = null;
	MobileElement allowButton = null;
	MobileElement iUnderstand = null;
	MobileElement oKButton = null;
	MobileElement alwaysAllow = null;
	MobileElement allowWhileUsingApp = null;
	MobileElement allow = null;
	
	MobileElement alertCenter = null;
	MobileElement termsCheckbox = null;
	MobileElement nextButton = null;
	MobileElement searchIcon = null;
	MobileElement skipForNow  = null;

	public AndroidFTLScreens(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Verify No Of Consent Calls On FTL")
	public void verifyNoOfConsentCallsOnFTL() throws Exception {
	/**
	 * There should be 4 Consent calls be generated on FTL,  
	 * location-apps-2: two calls
	 * advertising-apps-3: one call
	 * sale-apps-1: one call
	 */
		int value = Utils.getNoOfOccurancesOfAPICallWithHostandPath("upsx.weather.com", "/consent");
		System.out.println("No Of Consent Calls found are: "+value);
		Assert.assertEquals(value, 4);
	}
	
	public void clickONTerms() throws Exception {
		try {
			System.out.println("Clicking on Terms Checkbox");
			TestBase.waitForVisibilityOfElementLocated(Ad, 20, byTermsCheckbox);
			// new WebDriverWait(Ad,
			// Functions.maxTimeout).until(ExpectedConditions.elementToBeClickable(Ad.findElementById("com.weather.Weather:id/ok_button")));
			//Ad.findElementById("com.weather.Weather:id/sign_up_checkbox").click();
			termsCheckbox = Ad.findElement(byTermsCheckbox);
			TestBase.clickOnElement(byTermsCheckbox, termsCheckbox, "Terms Checkbox");
			System.out.println("Terms Checkbox displayed on the screen and clicked");
			logStep("Terms Checkbox displayed on the screen and clicked");
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println("Terms Checkbox not displayed");
			logStep("Terms Checkbox not displayed");
		}
	}

	public void clickONNext() throws Exception {
		try {
			System.out.println("Clicking on Next");
			// new WebDriverWait(Ad,
			// Functions.maxTimeout).until(ExpectedConditions.elementToBeClickable(Ad.findElementById("com.weather.Weather:id/ok_button")));
			//Ad.findElementById("com.weather.Weather:id/ok_button").click();
			TestBase.waitForVisibilityOfElementLocated(Ad, 20, byNextButton);
			nextButton = Ad.findElement(byNextButton);
			TestBase.clickOnElement(byNextButton, nextButton, "Next Button");
			System.out.println("Next Button displayed on the screen and clicked");
			logStep("Next Button displayed on the screen and clicked");
			Thread.sleep(5000);
			
		} catch (Exception e) {
			System.out.println("Next Button not displayed");
			logStep("Next Button not displayed");
		}
	}
	
	public void clickOnIUnderstand() throws Exception {
		try {
			System.out.println("Clicking on IUnderstand");
			/*new WebDriverWait(Ad, Functions.maxTimeout).until(ExpectedConditions
					.elementToBeClickable(Ad.findElementById("com.weather.Weather:id/next_button_text")));
			//Swipe_Conter(2);
			swipe_Up_OnLocationScreen();
			Ad.findElementById("com.weather.Weather:id/next_button_text").click();
			Thread.sleep(5000);*/
			TestBase.waitForVisibilityOfElementLocated(Ad, 20, byIUnderstand);
			swipe_Up_OnLocationScreen();
			iUnderstand = Ad.findElement(byIUnderstand);
			TestBase.clickOnElement(byIUnderstand, iUnderstand, "IUnderstand");
			System.out.println("IUnderstand Button displayed on the screen and clicked");
			logStep("IUnderstand Button displayed on the screen and clicked");
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println("IUnderstand Button not displayed");
			logStep("IUnderstand Button not displayed");
		}
	}

	public void clickOnclosebutton() throws Exception {
		try {
			System.out.println("Clicking on Close button");
			/*new WebDriverWait(Ad, Functions.maxTimeout)
					.until(ExpectedConditions.elementToBeClickable(Ad.findElementById("android:id/button2")));
			Ad.findElementById("android:id/button2").click();
			
			Thread.sleep(5000);*/
			TestBase.waitForVisibilityOfElementLocated(Ad, 20, byCloseButton);
			closeButton = Ad.findElement(byCloseButton);
			TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
			System.out.println("Close Button displayed on the screen and clicked");
			logStep("Close Button displayed on the screen and clicked");
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println("Close Button not displayed");
			logStep("Close Button not displayed");
		}
	}
	

	public void clickOnAllow() throws Exception {
		try {
			System.out.println("Clicking on Allow");
			/*new WebDriverWait(Ad, Functions.maxTimeout).until(ExpectedConditions.elementToBeClickable(
					Ad.findElementById("com.android.permissioncontroller:id/permission_allow_always_button")));
			Ad.findElementById("com.android.permissioncontroller:id/permission_allow_always_button").click();
			Thread.sleep(5000);*/
			TestBase.waitForVisibilityOfElementLocated(Ad, 20, byAllow);
			allow = Ad.findElement(byAllow);
			TestBase.clickOnElement(byAllow, allow, "Allow Button");
			System.out.println("Allow Button displayed on the screen and closed");
			logStep("Allow Button displayed on the screen and closed");
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println("Allow Button not displayed");
			logStep("Allow Button not displayed");
		}
	}
	
	public void clickOnAlwaysAllow() throws Exception {
		try {
			System.out.println("Clicking on Always Allow");
			/*new WebDriverWait(Ad, Functions.maxTimeout).until(ExpectedConditions.elementToBeClickable(
					Ad.findElementById("com.android.permissioncontroller:id/permission_allow_always_button")));
			Ad.findElementById("com.android.permissioncontroller:id/permission_allow_always_button").click();
			Thread.sleep(5000);*/
			TestBase.waitForVisibilityOfElementLocated(Ad, 20, byAlwaysAllow);
			alwaysAllow = Ad.findElement(byAlwaysAllow);
			TestBase.clickOnElement(byAlwaysAllow, alwaysAllow, "Always Allow Button");
			System.out.println("Always Allow Button displayed on the screen and closed");
			logStep("Always Allow Button displayed on the screen and closed");
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println("Always Allow Button not displayed");
			logStep("Always Allow Button not displayed");
		}
	}
	
	public void clickOnAllowWhileUsingApp() throws Exception {
		try {
			System.out.println("Clicking on Allow While Using App");
			/*new WebDriverWait(Ad, Functions.maxTimeout).until(ExpectedConditions.elementToBeClickable(
					Ad.findElementById("com.android.permissioncontroller:id/permission_allow_foreground_only_button")));
			Ad.findElementById("com.android.permissioncontroller:id/permission_allow_foreground_only_button").click();
			Thread.sleep(5000);*/
			TestBase.waitForVisibilityOfElementLocated(Ad, 20, byAllowWhileUsingApp);
			allowWhileUsingApp = Ad.findElement(byAllowWhileUsingApp);
			TestBase.clickOnElement(byAllowWhileUsingApp, allowWhileUsingApp, "Allow While Using App Button");
			System.out.println("Allow While Using App Button displayed on the screen and closed");
			logStep("Allow While Using App Button displayed on the screen and closed");
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println("Allow While Using App Button not displayed");
			logStep("Allow While Using App Button not displayed");
		}
	}
	
	public void clickOnSkipForNow() throws Exception {
		try {
			System.out.println("Clicking on Skip For Now");
			TestBase.waitForVisibilityOfElementLocated(Ad, 20, bySkipForNow);
			skipForNow = Ad.findElement(bySkipForNow);
			TestBase.clickOnElement(bySkipForNow, skipForNow, "Skip For Now Button");
			System.out.println("Skip For Now Button displayed on the screen and closed");
			logStep("Skip For Now Button displayed on the screen and closed");
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println("Skip For Now Button not displayed");
			logStep("Skip For Now Button not displayed");
		}
	}
	
		
	@Step("Handle Unwanted Popups during app launch")
	public void handle_Unwanted_Popups() throws Exception {
		attachScreen();
		boolean isSettingsButtonDisplayed = false;
		
			try {
				/*TestBase.waitForVisibilityOfElementLocated(Ad, 30, byAlertCenter);
				alertCenter = Ad.findElement(byAlertCenter);*/
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, bySearchIcon);
				searchIcon = Ad.findElement(bySearchIcon);
				
				System.out
						.println("Search icon found after app launch, hence no need to verify the application alerts");
				logStep("Search icon found after app launch, hence no need to verify the application alerts");
				
				isSettingsButtonDisplayed = true;
				
			} catch (Exception e1) {
				boolean found = false;
				while(!found) {
					if (TestBase.isElementExists(bySearchIcon)) {
						System.out
						.println("Search icon found after app launch, hence no need to verify the application alerts");
						logStep("Search icon found after app launch, hence no need to verify the application alerts");
					found = true;
					break;
					}
					clickOnAllow();
					clickONTerms();
					attachScreen();
					clickONNext();
					attachScreen();
					clickOnIUnderstand();
					attachScreen();
					if (TestBase.isElementExists(bySearchIcon)) {
						System.out
						.println("Search icon found after app launch, hence no need to verify the application alerts");
						logStep("Search icon found after app launch, hence no need to verify the application alerts");
					found = true;
					break;
					}
					clickOnAlwaysAllow();
					attachScreen();
					clickOnAllowWhileUsingApp();
					attachScreen();
					clickOnclosebutton();
					attachScreen();
					clickOnSkipForNow();
					attachScreen();
					if (TestBase.isElementExists(bySearchIcon)) {
						System.out
						.println("Search icon found after app launch, hence no need to verify the application alerts");
						logStep("Search icon found after app launch, hence no need to verify the application alerts");
					found = true;					
					}
				}
				
								
			}
		
		try {
			attachScreen();
		} catch (Exception e) {
			System.out.println("An exception while attaching screenshot");
			logStep("An exception while attaching screenshot");
		}

	}
	
	@Step("Handle Unwanted Popups during app launch")
	public void handle_Unwanted_Popups(AppiumDriver<MobileElement> Ad) throws Exception {
		attachScreen(Ad);
		boolean isSettingsButtonDisplayed = false;
		try {
			/*TestBase.waitForVisibilityOfElementLocated(Ad, 30, byAlertCenter);
			alertCenter = Ad.findElement(byAlertCenter);*/
			TestBase.waitForVisibilityOfElementLocated(Ad, 20, bySearchIcon);
			searchIcon = Ad.findElement(bySearchIcon);
			
			System.out
					.println("Search icon found after app launch, hence no need to verify the application alerts");
			logStep("Search icon found after app launch, hence no need to verify the application alerts");
			
			isSettingsButtonDisplayed = true;
			
		} catch (Exception e1) {
			boolean found = false;
			while(!found) {
				clickOnAllow();
				clickONTerms();
				attachScreen(Ad);
				clickONNext();
				attachScreen(Ad);
				clickOnIUnderstand();
				attachScreen(Ad);
				if (TestBase.isElementExists(bySearchIcon, Ad)) {
					System.out
					.println("Search icon found after app launch, hence no need to verify the application alerts");
					logStep("Search icon found after app launch, hence no need to verify the application alerts");
				found = true;
				break;
				}
				clickOnAlwaysAllow();
				attachScreen(Ad);
				clickOnAllowWhileUsingApp();
				attachScreen(Ad);
				clickOnclosebutton();
				attachScreen(Ad);
				clickOnSkipForNow();
				attachScreen();
				if (TestBase.isElementExists(bySearchIcon, Ad)) {
					System.out
					.println("Search icon found after app launch, hence no need to verify the application alerts");
					logStep("Search icon found after app launch, hence no need to verify the application alerts");
				found = true;					
				}
			}
									
		}
		
		
		try {
			attachScreen(Ad);
		} catch (Exception e) {
			System.out.println("An exception while attaching screenshot");
			logStep("An exception while attaching screenshot");
		}

	}
	
	@Step("Handle Unwanted Popups during app launch For China/Russia/Belarus")
	public void handle_Unwanted_Popups_China() throws Exception {
		attachScreen();
		boolean isSettingsButtonDisplayed = false;
		
			try {
				/*TestBase.waitForVisibilityOfElementLocated(Ad, 30, byAlertCenter);
				alertCenter = Ad.findElement(byAlertCenter);*/
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, bySearchIcon);
				searchIcon = Ad.findElement(bySearchIcon);
				
				System.out
						.println("Search icon found after app launch, hence no need to verify the application alerts");
				logStep("Search icon found after app launch, hence no need to verify the application alerts");
				
				isSettingsButtonDisplayed = true;
				
			} catch (Exception e1) {
				boolean found = false;
				clickOnAllow();
				clickONTerms();
				attachScreen();
				clickONNext();
				attachScreen();
				clickOnIUnderstand();
				attachScreen();
				
				clickOnAlwaysAllow();
				attachScreen();
				clickOnAllowWhileUsingApp();
				attachScreen();
				clickOnclosebutton();
				attachScreen();
				clickOnSkipForNow();
				attachScreen();
				
								
			}
		
		try {
			attachScreen();
		} catch (Exception e) {
			System.out.println("An exception while attaching screenshot");
			logStep("An exception while attaching screenshot");
		}

	}
	
	@Step("Handle Unwanted Popups during app launch For China/Russia/Belarus")
	public void handle_Unwanted_Popups_Chinass() throws Exception {
		attachScreen();
		try {
			TestBase.waitForVisibilityOfElementLocated(Ad, 30, bySettingsButton);
			settingsButton = Ad.findElement(bySettingsButton);
			System.out
					.println("Settings Button found after app launch, hence no need to verify the application alerts");
			logStep("Settings Button found after app launch, hence no need to verify the application alerts");
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 10, byCloseButton);
				closeButton = Ad.findElement(byCloseButton);
				TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
				System.out.println("Premium Big Ad displayed on the screen and closed");
				logStep("Premium Big Ad displayed on the screen and closed");
			} catch (Exception e) {
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 10, byCancelButton);
					cancelButton = Ad.findElement(byCancelButton);
					TestBase.clickOnElement(byCancelButton, cancelButton, "Cancel Button");
					System.out.println("Premium Big Ad displayed on the screen and closed");
					logStep("Premium Big Ad displayed on the screen and closed");
				} catch (Exception e1) {
					System.out.println("Premium Big Ad not displayed on the screen");
					logStep("Premium Big Ad not displayed on the screen");
				}
			}
		} catch (Exception e1) {
			try {
				//TestBase.waitForVisibilityOfElementLocated(Ad, 20, byCloseButton);
				closeButton = Ad.findElement(byCloseButton);
				TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
				System.out.println("Intermittent Ad displayed on the screen and closed");
				logStep("Intermittent Ad displayed on the screen and closed");
			} catch (Exception e) {
				System.out.println("Intermittent Ad not displayed on the screen");
				logStep("Intermittent Ad not displayed on the screen");
			}
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byChangeToAlwaysAllow);
				changeToAlwaysAllow = Ad.findElement(byChangeToAlwaysAllow);
				TestBase.clickOnElement(byChangeToAlwaysAllow, changeToAlwaysAllow, "Change to Always Allow Button");
				System.out.println("Change to Always Allow button available on the screen and handled");
				logStep("Change to Always Allow button available on the screen and handled");
			} catch (Exception e) {
				System.out.println("Change to Always Allow button not available on the screen");
				logStep("Change to Always Allow button not available on the screen");
			}

			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byCloseButton);
				closeButton = Ad.findElement(byCloseButton);
				TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
				//Ad.findElementByName("close_button").click();
				System.out.println("App upgrade alert available on the screen and handled");
				logStep("App upgrade alert available on the screen and handled");
			} catch (Exception e) {
				System.out.println("App upgrade alert not available on the screen");
				logStep("App upgrade alert not available on the screen");
			}
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byLetsGoButton);
				letsGoButton = Ad.findElement(byLetsGoButton);
				TestBase.clickOnElement(byLetsGoButton, letsGoButton, "Let's Go/Next Button");
				System.out.println("Let's Go/Next button available on the screen and handled");
				logStep("Let's Go/Next button available on the screen and handled");
			} catch (Exception e) {
				System.out.println("Let's Go/Next button not available on the screen");
				logStep("Let's Go/Next button not available on the screen");
			}

			
		}
		try {
			attachScreen();
		} catch (Exception e) {
			System.out.println("An exception while attaching screenshot");
			logStep("An exception while attaching screenshot");
		}

	}

	
	// Swipe based on counter //by naresh
		public void Swipe_Conter(int Counter) throws Exception {

			int swipe = Counter;

			for (int i = 1; i <= swipe; i++) {
				// Thread.sleep(2000);
				// Swipe();
				try {
					Thread.sleep(2000);
					if (Ad.findElementByName("Name any course, dish, or ingredient").isDisplayed()) {
						// System.out.println("Watson ad presented");
						break;
					}
				} catch (Exception e) {
					Swipe();
					// System.out.println("watson ad not present");
				}

				// Thread.sleep(2000);
			}
		}
		
		public void clickOnsettingIcon() throws Exception {
			try {
				System.out.println("Clicking on Setting Icon");
				logStep("Clicking on Setting Icon");
				Ad.findElementByAccessibilityId("Setting icon").click();
				Thread.sleep(10000);
				if (TestBase.isElementExists(MobileBy.AccessibilityId("Go to Alerts and Notifications"))) {
					Ad.findElementByAccessibilityId("Setting icon").click();
				}
			} catch (Exception e) {
				Ad.findElementById("com.weather.Weather:id/profile_avatar").click();
				Thread.sleep(10000);
			}

		}

}

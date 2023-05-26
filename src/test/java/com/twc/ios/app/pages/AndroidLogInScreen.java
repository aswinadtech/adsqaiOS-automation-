package com.twc.ios.app.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.twc.ios.app.functions.Functions;
import com.twc.ios.app.general.Driver;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.Utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;

public class AndroidLogInScreen extends Utils {
	AppiumDriver<MobileElement> Ad;

	
	String settingsButton_AccessibilityId = "Setting icon";
	String settingsButton_Id = "com.weather.Weather:id/profile_avatar";
	String settingsBackButton_AccessibilityId = "Settings back button";
	String logInLink_AccessibilityId = "Log In";
	String email_AccessibilityId = "login.email.input.text";
	String password_AccessibilityId = "login.password.input.text";
	String logInButton_AccessibilityId = "account.loginButton";
	String logInBackButton_AccessibilityId = "Back";
	String logInPageWelcomeMessage_AccessibilityId = "Welcome Back!";
	String logInPageLogInMessage_AccessibilityId = "Please log in to your account.";
	String closeMenuButton_Xpath = "(//XCUIElementTypeButton[@name='close_menu_button'])[1]";
	
	String goPremium_Id = "com.weather.Weather:id/go_premium_text";
	String manageSubscription_Id = "com.weather.Weather:id/manage_sub_link";
	String signOut_Xpath = "//XCUIElementTypeCell[@name=\"signout_cell\"]";
	String signOutSuccessOkayButton_Xpath = "//XCUIElementTypeButton[@name=\"Okay\"]";
	String premiumProMonthlyButton_Xpath = "(//XCUIElementTypeButton[@name=\"purchase_purchase_button\"])[1]";
	String premiumProAnnualButton_Xpath = "(//XCUIElementTypeButton[@name=\"purchase_purchase_button\"])[2]";
	String appleIdInput_Xpath = "//XCUIElementTypeTextField";
	String applePasswordInput_Xpath = "//XCUIElementTypeSecureTextField";
	String appleLogInOK_Xpath = "//XCUIElementTypeButton[@name=\"OK\"]";
	String appleLogInCancel_Xpath = "//XCUIElementTypeButton[@name=\"Cancel\"]";
	String subScribeButton_Xpath = "//XCUIElementTypeButton[@name=\"Subscribe\"]";
	String sandboxPasswordInput_Xpath = "//XCUIElementTypeSecureTextField";
	String sandboxSignInButton_Xpath = "//XCUIElementTypeButton[@name=\"Sign In\"]";
	String youAreAllSetText_Xpath = "//XCUIElementTypeStaticText[@name=\"You’re all set.\"]";
	String youAreAllSetOK_Xpath = "//XCUIElementTypeButton[@name=\"OK\"]";
	String backButtonFromPremium_Xpath = "//XCUIElementTypeButton[@name=\"Back\"]";
	String gotoAlertsandNotifications_AccessibilityId="Go to Alerts and Notifications";
	
	By byGotoAlertsandNotifications=MobileBy.AccessibilityId(gotoAlertsandNotifications_AccessibilityId);
	By bySettingsButton = MobileBy.AccessibilityId(settingsButton_AccessibilityId);
	//By bySettingsButtonn = MobileBy.id(settingsButton_Id);
	By bySettingsBackButton = MobileBy.AccessibilityId(settingsBackButton_AccessibilityId);
	By byLogInLink = MobileBy.AccessibilityId(logInLink_AccessibilityId);
	By byEmail = MobileBy.AccessibilityId(email_AccessibilityId);
	By byPassword = MobileBy.AccessibilityId(password_AccessibilityId);
	By byLogInButton = MobileBy.AccessibilityId(logInButton_AccessibilityId);
	By byLogInBackButton = MobileBy.AccessibilityId(logInBackButton_AccessibilityId);
	By byLogInPageWelcomeMessage = MobileBy.AccessibilityId(logInPageWelcomeMessage_AccessibilityId);
	By byLogInPageLogInMessage = MobileBy.AccessibilityId(logInPageLogInMessage_AccessibilityId);
	By byCloseMenuButton = MobileBy.xpath(closeMenuButton_Xpath);
	
	By byGoPremium = MobileBy.id(goPremium_Id);
	By byManageSubscription = MobileBy.id(manageSubscription_Id);
	By bySignOut = MobileBy.xpath(signOut_Xpath);
	By bySignOutSuccessOkayButton = MobileBy.xpath(signOutSuccessOkayButton_Xpath);
	By byPremiumProMonthlyButton = MobileBy.xpath(premiumProMonthlyButton_Xpath);
	By byPremiumProAnnualButton = MobileBy.xpath(premiumProAnnualButton_Xpath);
	By byAppleIdInput = MobileBy.xpath(appleIdInput_Xpath);
	By byApplePasswordInput = MobileBy.xpath(applePasswordInput_Xpath);
	By byAppleLogInOK = MobileBy.xpath(appleLogInOK_Xpath);
	By byAppleLogInCancel = MobileBy.xpath(appleLogInCancel_Xpath);
	By bySubScribeButton = MobileBy.xpath(subScribeButton_Xpath);
	By bySandboxPasswordInput = MobileBy.xpath(sandboxPasswordInput_Xpath);
	By bySandboxSignInButton = MobileBy.xpath(sandboxSignInButton_Xpath);
	By byYouAreAllSetText = MobileBy.xpath(youAreAllSetText_Xpath);
	By byYouAreAllSetOK = MobileBy.xpath(youAreAllSetOK_Xpath);
	By byBackButtonFromPremium = MobileBy.xpath(backButtonFromPremium_Xpath);
	

	
	MobileElement settingsButton = null;
	MobileElement gotoAlertsandNotifications =null;
	//MobileElement settingsButtonn = null;
	MobileElement settingsBackButton = null;
	MobileElement logInLink = null;
	MobileElement email = null;
	MobileElement password = null;
	MobileElement logInButton = null;
	MobileElement logInBackButton = null;
	MobileElement logInPageWelcomeMessage = null;
	MobileElement logInPageLogInMessage = null;
	MobileElement closeMenuButton = null;
	MobileElement goPremium = null;
	MobileElement manageSubscription = null;
	MobileElement signOut = null;
	MobileElement signOutSuccessOkayButton = null;
	MobileElement premiumProMonthlyButton = null;
	MobileElement premiumProAnnualButton = null;
	MobileElement appleIdInput = null;
	MobileElement applePasswordInput = null;
	MobileElement appleLogInOK = null;
	MobileElement appleLogInCancel = null;
	MobileElement subScribeButton = null;
	MobileElement sandboxPasswordInput = null;
	MobileElement sandboxSignInButton = null;
	MobileElement youAreAllSetText = null;
	MobileElement youAreAllSetOK = null;
	MobileElement backButtonFromPremium = null;

	public AndroidLogInScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigate to LogInPage")
	public void navigatetoLogInPage() throws Exception {
		settingsButton = Ad.findElement(bySettingsButton);
		TestBase.clickOnElement(bySettingsButton, settingsButton, "Settings Button");
		TestBase.waitForMilliSeconds(10000);
		logInLink = Ad.findElement(byLogInLink);
		TestBase.clickOnElement(byLogInLink, logInLink, "LogIn Link");
		TestBase.waitForVisibilityOfElementLocated(Ad, 90, byLogInPageWelcomeMessage);
	}
	
	@Step("Login To App with UPSX Credentials")
	public void logInToApp(String emailAddress, String pwd) throws Exception {
		
		navigatetoLogInPage();
		
		email = Ad.findElement(byEmail);
		password = Ad.findElement(byPassword);
		logInButton = Ad.findElement(byLogInButton);
		TestBase.typeText(email, "Email", emailAddress);
		TestBase.typeText(password, "Password", pwd);
		TestBase.clickOnElement(byLogInButton, logInButton, "LogIn Button");
		
		TestBase.waitForVisibilityOfElementLocated(Ad, 90, byCloseMenuButton);
		closeMenuButton = Ad.findElement(byCloseMenuButton);
		TestBase.clickOnElement(byCloseMenuButton, closeMenuButton, "Done Button");
	}
	
	@Step("LogOut From App")
	public void logOutFromApp() throws Exception {
		settingsButton = Ad.findElement(bySettingsButton);
		TestBase.clickOnElement(bySettingsButton, settingsButton, "Settings Button");
		TestBase.waitForMilliSeconds(10000);
		
		signOut = Ad.findElement(bySignOut);
		TestBase.clickOnElement(bySignOut, signOut, "SignOut Button");
		
		TestBase.waitForVisibilityOfElementLocated(Ad, 90, bySignOutSuccessOkayButton);
		signOutSuccessOkayButton = Ad.findElement(bySignOutSuccessOkayButton);
		TestBase.clickOnElement(bySignOutSuccessOkayButton, signOutSuccessOkayButton, "Okay Button");
				
		closeMenuButton = Ad.findElement(byCloseMenuButton);
		TestBase.clickOnElement(byCloseMenuButton, closeMenuButton, "Done Button");
	}
	
	
	@Step("AdFree Subscription For Monthly")
	public void enableMonthlyAdFreeSubscription() throws Exception {
		System.out.println("clicking on Go Premium option");
		logStep("clicking on Go Premium option");
		goPremium = Ad.findElement(byGoPremium);
		TestBase.clickOnElement(byGoPremium, goPremium, "Go Premium");
		
		//Ad.findElementById("com.weather.Weather:id/go_premium_text").click();
		Thread.sleep(20000);
		attachScreen();

		System.out.println("Clicking on Ad free monthly option");
		logStep("Clicking on monthly option");
		List<MobileElement> subscription = Ad.findElementsById("com.weather.Weather:id/subscription_price");
		subscription.get(3).click();
		Thread.sleep(10000);
		attachScreen();
		System.out.println("clicking on subscribe option");
		logStep("clicking on subscribe option");

		List<MobileElement> subscribeids = Ad.findElementsById("com.android.vending:id/0_resource_name_obfuscated");
		for (WebElement subscribe : subscribeids) {
			if (subscribe.getText().contains("Subscribe")) {

				subscribe.click();
				Thread.sleep(20000);
				break;

			}
		}
		Thread.sleep(10000);
		attachScreen();
	}
	
	
	@Step("Premium Subscription For Monthly")
	public void enableMonthlyPremiumSubscription() throws Exception {
		System.out.println("clicking on Go Premium option");
		logStep("clicking on Go Premium option");
		goPremium = Ad.findElement(byGoPremium);
		TestBase.clickOnElement(byGoPremium, goPremium, "Go Premium");
		System.out.println("Clicking on monthly option");
		logStep("Clicking on monthly option");
		List<MobileElement> subscription = Ad.findElementsById("com.weather.Weather:id/subscription_price");
		subscription.get(1).click();
		Thread.sleep(10000);
		attachScreen();
		System.out.println("clicking on subscribe option");
		logStep("clicking on subscribe option");

		List<MobileElement> subscribeids = Ad.findElementsById("com.android.vending:id/0_resource_name_obfuscated");
		for (WebElement subscribe : subscribeids) {
			if (subscribe.getText().contains("Subscribe")) {

				subscribe.click();
				Thread.sleep(20000);
				break;

			}
		}
		Thread.sleep(10000);
		attachScreen();
	}
	
	@Step("Premium Subscription For Yearly")
	public void enableYearlyPremiumSubscription() throws Exception {
 		System.out.println("clicking on Go Premium option");
 		logStep("clicking on Go Premium option");
 		goPremium = Ad.findElement(byGoPremium);
		TestBase.clickOnElement(byGoPremium, goPremium, "Go Premium");
 		Thread.sleep(25000);
 	   attachScreen();
 		System.out.println("Clicking on yearly option");
 		logStep("Clicking on yearly option");
 		List<MobileElement> subscription=Ad.findElementsById("com.weather.Weather:id/subscription_price");
 		subscription.get(0).click();
 		Thread.sleep(10000);
 	   attachScreen();
 		System.out.println("clicking on subscribe option");
 		logStep("clicking on subscribe option");
 		List<MobileElement> subscribeids=Ad.findElementsById("com.android.vending:id/0_resource_name_obfuscated");
 		for(WebElement subscribe:subscribeids) {
 			if(subscribe.getText().contains("Subscribe")) {
 				subscribe.click();
 				Thread.sleep(20000);
 			}
 		}
 	   attachScreen();
 		
 	}
	
	@Step("Cancel Premium Subscription")
	public void CancelPremiumSubscription() throws Exception {
 		try {
			System.out.println("Clicking on Setting Icon");
			logStep("Clicking on Setting Icon");
			settingsButton = Ad.findElement(bySettingsButton);
			TestBase.clickOnElement(bySettingsButton, settingsButton, "Settings 	Button");
			//Ad.findElementByAccessibilityId("Setting icon").click();
			Thread.sleep(10000);		
			
			if (TestBase.isElementExists(byGotoAlertsandNotifications)) {
				settingsButton = Ad.findElement(bySettingsButton);
				TestBase.clickOnElement(bySettingsButton, settingsButton, "Settings 	Button");
				   attachScreen();
			}
		} catch (Exception e) {
			bySettingsButton=MobileBy.id(settingsButton_Id);
			settingsButton = Ad.findElement(bySettingsButton);
			TestBase.clickOnElement(bySettingsButton, settingsButton, "Settings 	ButtonId");
		//	Ad.findElementById("com.weather.Weather:id/profile_avatar").click();
			Thread.sleep(10000);
			   attachScreen();
		}
 		
 	System.out.println("Clciking on Manage Subscription");
 	logStep("Clciking on Manage Subscription");
 	clickRequiredElementonWeatherapp("Manage Subscription");
 	//clickRequiredElementonSettingsapp("Manage Subscription");
 	Thread.sleep(10000);
    attachScreen();
    Functions.swipe_Up_ByIterations(Ad, 1);
 	System.out.println("Clcking on manage subscription on premium page");
 	logStep("Clcking on manage subscription on premium page");
 	
 	manageSubscription = Ad.findElement(byManageSubscription);
	TestBase.clickOnElement(byManageSubscription, manageSubscription, "Manage Subscription");
 //	Ad.findElementById("com.weather.Weather:id/manage_sub_link").click();
 	Thread.sleep(10000);
    attachScreen();
 	System.out.println("clicking on weather logo");
 	logStep("clicking on weather logo");
 	List<MobileElement> Susbcriptions=Ad.findElementsById("com.android.vending:id/0_resource_name_obfuscated");
 	Susbcriptions.get(3).click();
 	Thread.sleep(10000);
    attachScreen();
 	System.out.println("clicking on cancel subscrition");
 	logStep("clicking on cancel subscrition");
 	List<MobileElement> unsubscribe=Ad.findElementsById("com.android.vending:id/0_resource_name_obfuscated");
		for(WebElement subscribe:unsubscribe) {
			if(subscribe.getText().contains("Cancel subscription")) {
				subscribe.click();
				Thread.sleep(20000);
				break;
			}
		}
 	Thread.sleep(10000);
    attachScreen();
 	System.out.println("selecting option for what's making you cancel");
 	logStep("selecting option for what's making you cancel");
 	List<MobileElement> options=Ad.findElementsByClassName("android.widget.RadioButton");
 	for(WebElement option:options) {
		if(option.getText().contains("Decline to answer")) {
			option.click();
			Thread.sleep(20000);
			break;
		}
	}
 	Thread.sleep(10000);
    attachScreen();
 	System.out.println("clicking on continue");
 	logStep("clicking on continue");
 	List<MobileElement> Continue=Ad.findElementsById("com.android.vending:id/0_resource_name_obfuscated");
	for(WebElement subscribe:Continue) {
		if(subscribe.getText().contains("Continue")) {
			subscribe.click();
			Thread.sleep(20000);
			break;
		}
	}
 	Thread.sleep(10000);
    attachScreen();
 	System.out.println("clciking on cancel subscription");
 	logStep("clciking on cancel subscription");
 	List<MobileElement> cancel=Ad.findElementsByClassName("android.widget.Button");
	for(WebElement option:cancel) {
		if(option.getText().contains("Cancel subscription")) {
			option.click();
			Thread.sleep(20000);
			attachScreen();
			break;
		}
	}
   
 	
 	
 	}
	
	public void clickRequiredElementonWeatherapp(String name) throws Exception {
		String[] options = name.split("\\|\\|");
		
		try {

			
			List<MobileElement> searchelements = Ad
					.findElementsByXPath("//android.widget.TextView[@resource-id=\"android:id/title\"]");
			outerloop:
			for (int i = 0; i < options.length; i++) {
				for (WebElement search : searchelements) {
					//System.out.println(search.getAttribute("text"));
					//System.out.println(options[i].trim());
					
					if (search.getAttribute("text").contains(options[i].trim())) {
						// if(search.getText().contains(name)) {
						search.click();
						Thread.sleep(5000);
						break outerloop;

					}
				}
			}

		} catch (Exception e) {
		//	e.printStackTrace();
			List<MobileElement> searchelements = Ad.findElementsByClassName("android.widget.TextView");
			outerloop:
			for (int i = 0; i < options.length; i++) {
				for (WebElement search : searchelements) {
					if (search.getAttribute("text").contains(options[i].trim())) {
						search.click();
						Thread.sleep(5000);
						break outerloop;

					}
				}
			}
		}
	}
	

}

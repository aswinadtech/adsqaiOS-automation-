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

public class LogInScreen extends Utils {
	AppiumDriver<MobileElement> Ad;

	String settingsButton_AccessibilityId = "settingsButton";
	String settingsBackButton_AccessibilityId = "Settings back button";
	String logInLink_AccessibilityId = "Log In";
	String email_AccessibilityId = "login.email.input.text";
	String password_AccessibilityId = "login.password.input.text";
	String logInButton_AccessibilityId = "account.loginButton";
	String logInBackButton_AccessibilityId = "Back";
	String logInPageWelcomeMessage_AccessibilityId = "Welcome Back!";
	String logInPageLogInMessage_AccessibilityId = "Please log in to your account.";
	String closeMenuButton_Xpath = "(//XCUIElementTypeButton[@name='close_menu_button'])[1]";
	
	String goPremium_Xpath = "//XCUIElementTypeCell[@name=\"remove_ads_cell\"]";
	String manageSubscription_Xpath = "//XCUIElementTypeCell[@name=\"manage_subscriptions_cell\"]";
	String signOut_Xpath = "//XCUIElementTypeCell[@name=\"signout_cell\"]";
	String signOutSuccessOkayButton_Xpath = "//XCUIElementTypeButton[@name=\"Okay\"]";
	String premiumProMonthlyButton_Xpath = "(//XCUIElementTypeButton[@name=\"purchase_purchase_button_monthly\"])[2]";
	String adFreeMonthlyButton_Xpath = "(//XCUIElementTypeButton[@name=\"purchase_purchase_button_monthly\"])[1]";
	String premiumProAnnualButton_Xpath = "(//XCUIElementTypeButton[@name=\"purchase_purchase_button_yearly\"])[2]";
	String adFreeAnnualButton_Xpath = "(//XCUIElementTypeButton[@name=\"purchase_purchase_button_yearly\"])[1]";
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

	
	By bySettingsButton = MobileBy.AccessibilityId(settingsButton_AccessibilityId);
	By bySettingsBackButton = MobileBy.AccessibilityId(settingsBackButton_AccessibilityId);
	By byLogInLink = MobileBy.AccessibilityId(logInLink_AccessibilityId);
	By byEmail = MobileBy.AccessibilityId(email_AccessibilityId);
	By byPassword = MobileBy.AccessibilityId(password_AccessibilityId);
	By byLogInButton = MobileBy.AccessibilityId(logInButton_AccessibilityId);
	By byLogInBackButton = MobileBy.AccessibilityId(logInBackButton_AccessibilityId);
	By byLogInPageWelcomeMessage = MobileBy.AccessibilityId(logInPageWelcomeMessage_AccessibilityId);
	By byLogInPageLogInMessage = MobileBy.AccessibilityId(logInPageLogInMessage_AccessibilityId);
	By byCloseMenuButton = MobileBy.xpath(closeMenuButton_Xpath);
	
	By byGoPremium = MobileBy.xpath(goPremium_Xpath);
	By byManageSubscription = MobileBy.xpath(manageSubscription_Xpath);
	By bySignOut = MobileBy.xpath(signOut_Xpath);
	By bySignOutSuccessOkayButton = MobileBy.xpath(signOutSuccessOkayButton_Xpath);
	By byPremiumProMonthlyButton = MobileBy.xpath(premiumProMonthlyButton_Xpath);
	By byAdFreeMonthlyButton = MobileBy.xpath(adFreeMonthlyButton_Xpath);
	By byPremiumProAnnualButton = MobileBy.xpath(premiumProAnnualButton_Xpath);
	By byAdFreeAnnualButton = MobileBy.xpath(adFreeAnnualButton_Xpath);
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
	MobileElement adFreeMonthlyButton = null;
	MobileElement premiumProAnnualButton = null;
	MobileElement adFreeAnnualButton = null;
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

	public LogInScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigate to LogInPage")
	public void navigatetoLogInPage() throws Exception {
		settingsButton = Ad.findElement(bySettingsButton);
		TestBase.clickOnElement(bySettingsButton, settingsButton, "Settings Button");
		TestBase.waitForMilliSeconds(10000);
		attachScreen();
		logInLink = Ad.findElement(byLogInLink);
		TestBase.clickOnElement(byLogInLink, logInLink, "LogIn Link");
		TestBase.waitForVisibilityOfElementLocated(Ad, 90, byLogInPageWelcomeMessage);
		attachScreen();
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
		attachScreen();
		TestBase.waitForVisibilityOfElementLocated(Ad, 90, byCloseMenuButton);
		closeMenuButton = Ad.findElement(byCloseMenuButton);
		TestBase.clickOnElement(byCloseMenuButton, closeMenuButton, "Done Button");
		attachScreen();
	}
	
	@Step("LogOut From App")
	public void logOutFromApp() throws Exception {
		settingsButton = Ad.findElement(bySettingsButton);
		TestBase.clickOnElement(bySettingsButton, settingsButton, "Settings Button");
		TestBase.waitForMilliSeconds(10000);
		
		signOut = Ad.findElement(bySignOut);
		TestBase.clickOnElement(bySignOut, signOut, "SignOut Button");
		attachScreen();
		
		TestBase.waitForVisibilityOfElementLocated(Ad, 90, bySignOutSuccessOkayButton);
		signOutSuccessOkayButton = Ad.findElement(bySignOutSuccessOkayButton);
		TestBase.clickOnElement(bySignOutSuccessOkayButton, signOutSuccessOkayButton, "Okay Button");
				
		closeMenuButton = Ad.findElement(byCloseMenuButton);
		TestBase.clickOnElement(byCloseMenuButton, closeMenuButton, "Done Button");
		attachScreen();
	}
	
	@Step("Premium Subscription For Monthly")
	public void enableMonthlyPremiumSubscription(String appleId, String applePwd) throws Exception {
		
		settingsButton = Ad.findElement(bySettingsButton);
		TestBase.clickOnElement(bySettingsButton, settingsButton, "Settings Button");
		TestBase.waitForMilliSeconds(10000);
		if (TestBase.isElementExists(byManageSubscription)) {
			System.out.println("****** Premium Subscription already enabled, continuing evaluation");
			logStep("****** Premium Subscription already enabled, continuing evaluation");
		} else {
			goPremium = Ad.findElement(byGoPremium);
			TestBase.clickOnElement(byGoPremium, goPremium, "Go Premium Button");
			//attachScreen();
			TestBase.waitForVisibilityOfElementLocated(Ad, 30, byPremiumProMonthlyButton);
			
			premiumProMonthlyButton = Ad.findElement(byPremiumProMonthlyButton);
			TestBase.clickOnElement(byPremiumProMonthlyButton, premiumProMonthlyButton, "Premium Pro Monthly Button");
			attachScreen();
			
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 45, byAppleIdInput);
				appleIdInput = Ad.findElement(byAppleIdInput);
				applePasswordInput = Ad.findElement(byApplePasswordInput);
				
				TestBase.typeText(appleIdInput, "Apple ID", appleId);
				TestBase.typeText(applePasswordInput, "Apple Password", applePwd);
				//attachScreen();
				appleLogInOK = Ad.findElement(byAppleLogInOK);
				
				TestBase.clickOnElement(byAppleLogInOK, appleLogInOK, "OK Button");
				
				TestBase.waitForVisibilityOfElementLocated(Ad, 60, bySubScribeButton);
				//attachScreen();
				subScribeButton = Ad.findElement(bySubScribeButton);
				TestBase.clickOnElement(bySubScribeButton, subScribeButton, "Subscribe Button");
				attachScreen();
			}catch(Exception e) {
				attachScreen();
				TestBase.waitForVisibilityOfElementLocated(Ad, 60, bySubScribeButton);
				subScribeButton = Ad.findElement(bySubScribeButton);
				TestBase.clickOnElement(bySubScribeButton, subScribeButton, "Subscribe Button");
				
			}
			
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 60, bySandboxPasswordInput);
				sandboxPasswordInput = Ad.findElement(bySandboxPasswordInput);
				TestBase.typeText(sandboxPasswordInput, "Sandbox Password", applePwd);
				//attachScreen();
				
				sandboxSignInButton = Ad.findElement(bySandboxSignInButton);
				TestBase.clickOnElement(bySandboxSignInButton, sandboxSignInButton, "Sandbox Sign In Button");
				attachScreen();	
			}catch (Exception e) {
				attachScreen();
				System.out.println("Sandbox password input not displayed");
				logStep("Sandbox password input not displayed");
			}
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 120, byYouAreAllSetText);
				youAreAllSetOK = Ad.findElement(byYouAreAllSetOK);
				TestBase.clickOnElement(byYouAreAllSetOK, youAreAllSetOK, "You Are All Set OK Button");
				//attachScreen();
				
				backButtonFromPremium = Ad.findElement(byBackButtonFromPremium);
				TestBase.clickOnElement(byBackButtonFromPremium, backButtonFromPremium, "Back Button");
				//attachScreen();
				
				closeMenuButton = Ad.findElement(byCloseMenuButton);
				TestBase.clickOnElement(byCloseMenuButton, closeMenuButton, "Done Button");
				attachScreen();
			} catch  (Exception e) {
				attachScreen();
				System.out.println("Looks Adfree not enabled, look at screenshot");
				logStep("Looks Adfree not enabled, look at screenshot");
			}
						
			
		}
			
	}
	
	@Step("AdFree Subscription For Monthly")
	public void enableMonthlyAdFreeSubscription(String appleId, String applePwd) throws Exception {
		
		settingsButton = Ad.findElement(bySettingsButton);
		TestBase.clickOnElement(bySettingsButton, settingsButton, "Settings Button");
		TestBase.waitForMilliSeconds(10000);
		if (TestBase.isElementExists(byManageSubscription)) {
			System.out.println("****** Premium Subscription already enabled, continuing evaluation");
			logStep("****** Premium Subscription already enabled, continuing evaluation");
		} else {
			goPremium = Ad.findElement(byGoPremium);
			TestBase.clickOnElement(byGoPremium, goPremium, "Go Premium Button");
			//attachScreen();
			TestBase.waitForVisibilityOfElementLocated(Ad, 30, byAdFreeMonthlyButton);
			
			adFreeMonthlyButton = Ad.findElement(byAdFreeMonthlyButton);
			TestBase.clickOnElement(byAdFreeMonthlyButton, adFreeMonthlyButton, "AdFree Monthly Button");
			attachScreen();
			
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 45, byAppleIdInput);
				appleIdInput = Ad.findElement(byAppleIdInput);
				applePasswordInput = Ad.findElement(byApplePasswordInput);
				
				TestBase.typeText(appleIdInput, "Apple ID", appleId);
				TestBase.typeText(applePasswordInput, "Apple Password", applePwd);
				//attachScreen();
				appleLogInOK = Ad.findElement(byAppleLogInOK);
				
				TestBase.clickOnElement(byAppleLogInOK, appleLogInOK, "OK Button");
				
				TestBase.waitForVisibilityOfElementLocated(Ad, 60, bySubScribeButton);
				//attachScreen();
				subScribeButton = Ad.findElement(bySubScribeButton);
				TestBase.clickOnElement(bySubScribeButton, subScribeButton, "Subscribe Button");
				attachScreen();
			}catch(Exception e) {
				attachScreen();
				TestBase.waitForVisibilityOfElementLocated(Ad, 60, bySubScribeButton);
				subScribeButton = Ad.findElement(bySubScribeButton);
				TestBase.clickOnElement(bySubScribeButton, subScribeButton, "Subscribe Button");
				
			}
			
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 60, bySandboxPasswordInput);
				sandboxPasswordInput = Ad.findElement(bySandboxPasswordInput);
				TestBase.typeText(sandboxPasswordInput, "Sandbox Password", applePwd);
				//attachScreen();
				
				sandboxSignInButton = Ad.findElement(bySandboxSignInButton);
				TestBase.clickOnElement(bySandboxSignInButton, sandboxSignInButton, "Sandbox Sign In Button");
				attachScreen();	
			}catch (Exception e) {
				attachScreen();
				System.out.println("Sandbox password input not displayed");
				logStep("Sandbox password input not displayed");
			}
						
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 120, byYouAreAllSetText);
				youAreAllSetOK = Ad.findElement(byYouAreAllSetOK);
				TestBase.clickOnElement(byYouAreAllSetOK, youAreAllSetOK, "You Are All Set OK Button");
				//attachScreen();
				
				backButtonFromPremium = Ad.findElement(byBackButtonFromPremium);
				TestBase.clickOnElement(byBackButtonFromPremium, backButtonFromPremium, "Back Button");
				//attachScreen();
				
				closeMenuButton = Ad.findElement(byCloseMenuButton);
				TestBase.clickOnElement(byCloseMenuButton, closeMenuButton, "Done Button");
				attachScreen();
			} catch (Exception e) {
				attachScreen();
				System.out.println("Looks Adfree not enabled, look at screenshot");
				logStep("Looks Adfree not enabled, look at screenshot");
			}
			
		}
	
	}
	
	@Step("Premium Subscription For Yearly")
	public void enableYearlyPremiumSubscription(String appleId, String applePwd) throws Exception {
		
		settingsButton = Ad.findElement(bySettingsButton);
		TestBase.clickOnElement(bySettingsButton, settingsButton, "Settings Button");
		TestBase.waitForMilliSeconds(10000);
		if (TestBase.isElementExists(byManageSubscription)) {
			System.out.println("****** Premium Subscription already enabled, continuing evaluation");
			logStep("****** Premium Subscription already enabled, continuing evaluation");
		} else {
			goPremium = Ad.findElement(byGoPremium);
			TestBase.clickOnElement(byGoPremium, goPremium, "Go Premium Button");
			//attachScreen();
			TestBase.waitForVisibilityOfElementLocated(Ad, 30, byPremiumProAnnualButton);
			
			premiumProAnnualButton = Ad.findElement(byPremiumProAnnualButton);
			TestBase.clickOnElement(byPremiumProAnnualButton, premiumProAnnualButton, "Premium Pro Annual Button");
			attachScreen();
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 45, byAppleIdInput);
				appleIdInput = Ad.findElement(byAppleIdInput);
				applePasswordInput = Ad.findElement(byApplePasswordInput);
				
				TestBase.typeText(appleIdInput, "Apple ID", appleId);
				TestBase.typeText(applePasswordInput, "Apple Password", applePwd);
				//attachScreen();
				appleLogInOK = Ad.findElement(byAppleLogInOK);
				
				TestBase.clickOnElement(byAppleLogInOK, appleLogInOK, "OK Button");
				
				TestBase.waitForVisibilityOfElementLocated(Ad, 60, bySubScribeButton);
				//attachScreen();
				subScribeButton = Ad.findElement(bySubScribeButton);
				TestBase.clickOnElement(bySubScribeButton, subScribeButton, "Subscribe Button");
				attachScreen();
			}catch(Exception e) {
				attachScreen();
				TestBase.waitForVisibilityOfElementLocated(Ad, 60, bySubScribeButton);
				subScribeButton = Ad.findElement(bySubScribeButton);
				TestBase.clickOnElement(bySubScribeButton, subScribeButton, "Subscribe Button");
				
			}
			
			
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 60, bySandboxPasswordInput);
				sandboxPasswordInput = Ad.findElement(bySandboxPasswordInput);
				TestBase.typeText(sandboxPasswordInput, "Sandbox Password", applePwd);
				//attachScreen();
				
				sandboxSignInButton = Ad.findElement(bySandboxSignInButton);
				TestBase.clickOnElement(bySandboxSignInButton, sandboxSignInButton, "Sandbox Sign In Button");
				attachScreen();	
			}catch (Exception e) {
				attachScreen();
				System.out.println("Sandbox password input not displayed");
				logStep("Sandbox password input not displayed");
			}
			
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 120, byYouAreAllSetText);
				youAreAllSetOK = Ad.findElement(byYouAreAllSetOK);
				TestBase.clickOnElement(byYouAreAllSetOK, youAreAllSetOK, "You Are All Set OK Button");
				//attachScreen();
				
				backButtonFromPremium = Ad.findElement(byBackButtonFromPremium);
				TestBase.clickOnElement(byBackButtonFromPremium, backButtonFromPremium, "Back Button");
				//attachScreen();
				
				closeMenuButton = Ad.findElement(byCloseMenuButton);
				TestBase.clickOnElement(byCloseMenuButton, closeMenuButton, "Done Button");
				attachScreen();
			} catch (Exception e) {
				attachScreen();
				System.out.println("Looks Premium not enabled, look at screenshot");
				logStep("Looks Premium not enabled, look at screenshot");
			}
		}
			
	}
	
	@Step("AdFree Subscription For Yearly")
	public void enableYearlyAdFreeSubscription(String appleId, String applePwd) throws Exception {
		
		settingsButton = Ad.findElement(bySettingsButton);
		TestBase.clickOnElement(bySettingsButton, settingsButton, "Settings Button");
		TestBase.waitForMilliSeconds(10000);
		if (TestBase.isElementExists(byManageSubscription)) {
			System.out.println("****** Premium Subscription already enabled, continuing evaluation");
			logStep("****** Premium Subscription already enabled, continuing evaluation");
		} else {
			goPremium = Ad.findElement(byGoPremium);
			TestBase.clickOnElement(byGoPremium, goPremium, "Go Premium Button");
			//attachScreen();
			TestBase.waitForVisibilityOfElementLocated(Ad, 30, byAdFreeAnnualButton);
			
			adFreeAnnualButton = Ad.findElement(byAdFreeAnnualButton);
			TestBase.clickOnElement(byAdFreeAnnualButton, adFreeAnnualButton, "AdFree Annual Button");
			attachScreen();
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 45, byAppleIdInput);
				appleIdInput = Ad.findElement(byAppleIdInput);
				applePasswordInput = Ad.findElement(byApplePasswordInput);
				
				TestBase.typeText(appleIdInput, "Apple ID", appleId);
				TestBase.typeText(applePasswordInput, "Apple Password", applePwd);
				//attachScreen();
				appleLogInOK = Ad.findElement(byAppleLogInOK);
				
				TestBase.clickOnElement(byAppleLogInOK, appleLogInOK, "OK Button");
				
				TestBase.waitForVisibilityOfElementLocated(Ad, 60, bySubScribeButton);
				//attachScreen();
				subScribeButton = Ad.findElement(bySubScribeButton);
				TestBase.clickOnElement(bySubScribeButton, subScribeButton, "Subscribe Button");
				attachScreen();
			}catch(Exception e) {
				attachScreen();
				TestBase.waitForVisibilityOfElementLocated(Ad, 60, bySubScribeButton);
				subScribeButton = Ad.findElement(bySubScribeButton);
				TestBase.clickOnElement(bySubScribeButton, subScribeButton, "Subscribe Button");
				
			}
			
			
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 60, bySandboxPasswordInput);
				sandboxPasswordInput = Ad.findElement(bySandboxPasswordInput);
				TestBase.typeText(sandboxPasswordInput, "Sandbox Password", applePwd);
				//attachScreen();
				
				sandboxSignInButton = Ad.findElement(bySandboxSignInButton);
				TestBase.clickOnElement(bySandboxSignInButton, sandboxSignInButton, "Sandbox Sign In Button");
				attachScreen();	
			}catch (Exception e) {
				attachScreen();
				System.out.println("Sandbox password input not displayed");
				logStep("Sandbox password input not displayed");
			}
			
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 120, byYouAreAllSetText);
				youAreAllSetOK = Ad.findElement(byYouAreAllSetOK);
				TestBase.clickOnElement(byYouAreAllSetOK, youAreAllSetOK, "You Are All Set OK Button");
				//attachScreen();
				
				backButtonFromPremium = Ad.findElement(byBackButtonFromPremium);
				TestBase.clickOnElement(byBackButtonFromPremium, backButtonFromPremium, "Back Button");
				//attachScreen();
				
				closeMenuButton = Ad.findElement(byCloseMenuButton);
				TestBase.clickOnElement(byCloseMenuButton, closeMenuButton, "Done Button");
				attachScreen();
			} catch (Exception e) {
				attachScreen();
				System.out.println("Looks Adfree not enabled, look at screenshot");
				logStep("Looks Adfree not enabled, look at screenshot");
			}
		}
			
	}

}

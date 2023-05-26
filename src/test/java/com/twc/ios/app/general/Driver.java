package com.twc.ios.app.general;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class Driver extends PropertyFile {

	@SuppressWarnings("rawtypes")
	protected static AppiumDriver<MobileElement> Ad;
	protected static AppiumDriver<MobileElement> Ad1;
	public static WebDriver driver = null;
	public static WebDriver driver_ipa = null;
	public static WebDriver driver_Report = null;
	// protected static ExtentReports reporter;
	// protected static ExtentTest logger;
	public static String Excel_Path = null;

	@Step("{0}")
	public static void logStep(String stepName) {
	}

	@Step("Generating Screenshot")
	public static void attachScreen() {
		captureScreenShot();
	}
	
	@Step("Generating Screenshot")
	public static void attachScreen(AppiumDriver<MobileElement> Ad) {
		captureScreenShot(Ad);
	}

	@Attachment("Screenshot")
	public static byte[] captureScreenShot() {
		// public static byte[] attachScreen() {
		try {
			System.out.println("Generating Screenshot");
			logStep("Generating Screenshot");
			// logStep("Taking screenshot");
			return (((TakesScreenshot) Ad).getScreenshotAs(OutputType.BYTES));
		} catch (Exception e) {
			System.out.println("Failed to capture Screenshot");
			logStep("Failed to capture Screenshot");
			e.printStackTrace();
			System.out.println(e.getMessage());
			logStep(e.getMessage());
			return null;
		}

	}
	
	@Attachment("Screenshot")
	public static byte[] captureScreenShot(AppiumDriver<MobileElement> Ad) {
		// public static byte[] attachScreen() {
		try {
			System.out.println("Generating Screenshot");
			logStep("Generating Screenshot");
			// logStep("Taking screenshot");
			return (((TakesScreenshot) Ad).getScreenshotAs(OutputType.BYTES));
		} catch (Exception e) {
			System.out.println("Failed to capture Screenshot");
			logStep("Failed to capture Screenshot");
			e.printStackTrace();
			System.out.println(e.getMessage());
			logStep(e.getMessage());
			return null;
		}

	}
}

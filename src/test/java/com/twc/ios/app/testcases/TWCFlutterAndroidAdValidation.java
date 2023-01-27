package com.twc.ios.app.testcases;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.allure.Description;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;

import com.twc.ios.app.charlesfunctions.CharlesProxy;
import com.twc.ios.app.functions.Functions;
import com.twc.ios.app.general.ReadExcelValues;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.TwcIosBaseTest;
import com.twc.ios.app.general.Utils;

public class TWCFlutterAndroidAdValidation extends TwcIosBaseTest {
	private static final String CONFIG_FILE_PATH = "charles_common.config";
	public File configFile;

	/*
	 * @BeforeClass(alwaysRun = true)
	 * 
	 * @Description("BeforeClass") public void beforeClass() {
	 * System.out.println("****** TWC Flutter Test Started");
	 * logStep("****** TWC Flutter Test Started"); this.configFile =
	 * this.charlesGeneralConfigFile(CONFIG_FILE_PATH); proxy = new
	 * CharlesProxy("localhost", 8111, CONFIG_FILE_PATH);
	 * proxy.startCharlesProxyWithUI(); proxy.disableRewriting();
	 * proxy.stopRecording(); proxy.disableMapLocal(); }
	 */

	@AfterClass(alwaysRun = true)
	@Description("AfterClass")
	public void afterTest() throws Exception {
		try {
			/*
			 * Functions.archive_folder("Charles"); proxy.disableRewriting();
			 * proxy.quitCharlesProxy();
			 */

			Ad.closeApp();
			;
			System.out.println("App closed successfully");
		} catch (Exception e) {
			System.out.println("An exception while closeApp() executed");
			logStep("An exception while closeApp() executed");
		}
		if (Ad != null) {
			try {
				Ad.quit();
			} catch (Exception ex) {
				// Session crashed/died probably so no big deal, since
				// this exception was thrown when trying to close session.
				// Also, avoids failures in before/after methods for TestNG.
				System.out.println(
						"NoSuchSessionException was thrown while attempting to close session. Ignoring this error.");
				logStep("NoSuchSessionException was thrown while attempting to close session. Ignoring this error.");
			}
			System.out.println("Closing appium session.. Done");
			logStep("Closing appium session.. Done");
		}
	}

	@Test(priority = -1)
	@Description("Updating Device Proxy Details and Launch the App")
	public void beforeTest() throws Exception {

		String[] command = { "/usr/bin/killall", "-KILL", "adb" };
		Runtime.getRuntime().exec(command);
		String[] command1 = { "/usr/bin/killall", "-KILL", "-9 adb" };
		Runtime.getRuntime().exec(command1);
		Thread.sleep(5000);
		String[] command2 = { "/bin/sh", "-c", "adb start-server" };
		Runtime.getRuntime().exec(command2);

		Functions.capabilities();
		Functions.Appium_Autostart();
//		Utils.getCurrentMacIPAddressAndSetiPhoneProxy(true, true);
		/*
		 * proxy.startRecording(); proxy.clearCharlesSession();
		 * Functions.archive_folder("Charles");
		 */

		ReadExcelValues.excelValues("Smoke", "Capabilities");

		DesiredCapabilities capabilities = new DesiredCapabilities();

		// Capabilities for IOS and Android Based on Device Selection
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("platformVersion", "12");
		capabilities.setCapability("deviceName", "Galaxy S21 5G");
		capabilities.setCapability("udid", "R5CR11NNFQW");

		capabilities.setCapability("automationName", "UiAutomator2");
		capabilities.setCapability("appPackage", "com.weather.admob_latency_test");

		capabilities.setCapability("appActivity", "com.weather.admob_latency_test.MainActivity");
		capabilities.setCapability("noReset", "true");

		// capabilities.setCapability("launchTimeout", 60000);
		// capabilities.setCapability("useNewWDA", true);

		// capabilities.setCapability("wdaLocalPort", "7403");

		// capabilities.setCapability("clearSystemFiles", true);
		System.out.println("Reading capabilities done");

		Ad = new AndroidDriver(new URL("http://127.0.0.1:4733/wd/hub"), capabilities);
		// Ad= new IOSDriver<MobileElement>(service, capabilities);
		Ad.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		Ad.closeApp();
		System.out.println("App Closed SuccessFully");
		logStep("App Closed SuccessFully");
		TestBase.waitForMilliSeconds(5000);
		/*
		 * Ad.launchApp(); // Ad.activateApp("com.weather.TWC");
		 * System.out.println("App Launched SuccessFully");
		 * logStep("App Launched SuccessFully");
		 */

	}

	@Test(priority = 1, enabled = true)
	@Description("Validating Interstitial ad")
	public void validating_Interstitial_Ad() throws Exception {
		WebDriverWait wait = new WebDriverWait(Ad, 180);
		System.out.println("================= Interstitial Ad Test Started =========================");
		logStep("================= Interstitial Ad Test Started =========================");
		Ad.closeApp();
		Ad.launchApp();

		HashMap<Integer, Float> dTime = new HashMap<Integer, Float>();
		for (int i = 1; i <= 50; i++) {
			try {

				System.out.println("Iteration Number: " + i);
				Ad.closeApp();
				Ad.launchApp();

				// Interstitial

				Ad.findElementByAccessibilityId("Interstitial ad").click();

				long start = System.currentTimeMillis();

				// Interstitial

				MobileElement adElement = (MobileElement) wait
						.until(ExpectedConditions.presenceOfElementLocated(By.className("android.webkit.WebView")));

	// finding the time after the operation is executed
				long end = System.currentTimeMillis();
				// Interstitial

				//Ad.findElementById("close-button-icon").click();

	//finding the time difference and converting it into seconds
				float sec = (end - start) / 1000F;
				dTime.put(i, sec);
				System.out.println(sec + " seconds");
				logStep(sec + " seconds");
			} catch (Exception e) {
				System.out.println("An Exception in the iteration: " + i);
				logStep("An Exception in the iteration: " + i);
			}

		}
		for (Map.Entry m : dTime.entrySet()) {
			System.out.println("Iteration No: " + m.getKey() + " " + " Ad display Time is: " + m.getValue());
		}

		System.out.println("================= Interstitial Ad Test Ended =========================");
		logStep("================= Interstitial Ad Test Ended =========================");
	}

	@Test(priority = 2, enabled = true)
	@Description("Validating Marquee Static ad")
	public void validating_Marquee_Static_Ad() throws Exception {
		WebDriverWait wait = new WebDriverWait(Ad, 180);
		System.out.println("================= Marquee Static Ad Test Started =========================");
		logStep("================= Marquee Static Ad Test Started =========================");

		Ad.closeApp();
		Ad.launchApp();

		HashMap<Integer, Float> dTime = new HashMap<Integer, Float>();
		for (int i = 1; i <= 50; i++) {
			try {
				System.out.println("Iteration Number: " + i);
				Ad.closeApp();
				Ad.launchApp();

				// Static Marque
				Ad.findElementByAccessibilityId("Marquee image ad").click();
				long start = System.currentTimeMillis();

				MobileElement adElement = (MobileElement) wait
						.until(ExpectedConditions.visibilityOf(Ad.findElementByClassName("android.webkit.WebView")));

	// finding the time after the operation is executed
				long end = System.currentTimeMillis();

	//finding the time difference and converting it into seconds
				float sec = (end - start) / 1000F;
				dTime.put(i, sec);
				System.out.println(sec + " seconds");
				logStep(sec + " seconds");

			} catch (Exception e) {
				System.out.println("An Exception in the iteration: " + i);
				logStep("An Exception in the iteration: " + i);
			}
			
		}
		for (Map.Entry m : dTime.entrySet()) {
			System.out.println("Iteration No: " + m.getKey() + " " + " Ad display Time is: " + m.getValue());
		}

		System.out.println("================= Marquee Static Ad Test Ended =========================");
		logStep("================= Marquee Static Ad Test Ended =========================");
	}

	@Test(priority = 3, enabled = true)
	@Description("Validating Marquee Video ad")
	public void validating_Marquee_Video_Ad() throws Exception {
		WebDriverWait wait = new WebDriverWait(Ad, 180);
		System.out.println("================= Marquee Video Ad Test Started =========================");
		logStep("================= Marquee Video Ad Test Started =========================");

		Ad.closeApp();
		Ad.launchApp();

		HashMap<Integer, Float> dTime = new HashMap<Integer, Float>();
		for (int i = 1; i <= 50; i++) {
			try {
				System.out.println("Iteration Number: " + i);
				Ad.closeApp();
				Ad.launchApp();

				// Video Marque
				Ad.findElementByAccessibilityId("Marquee video ad").click();
				long start = System.currentTimeMillis();

				MobileElement adElement = (MobileElement) wait
						.until(ExpectedConditions.visibilityOf(Ad.findElementByClassName("android.webkit.WebView")));

	// finding the time after the operation is executed
				long end = System.currentTimeMillis();

	//finding the time difference and converting it into seconds
				float sec = (end - start) / 1000F;
				dTime.put(i, sec);
				System.out.println(sec + " seconds");
				logStep(sec + " seconds");
			} catch (Exception e) {
				System.out.println("An Exception in the iteration: " + i);
				logStep("An Exception in the iteration: " + i);
			}
			

		}
		for (Map.Entry m : dTime.entrySet()) {
			System.out.println("Iteration No: " + m.getKey() + " " + " Ad display Time is: " + m.getValue());
		}

		System.out.println("================= Marquee Video Ad Test Ended =========================");
		logStep("================= Marquee Video Ad Test Ended =========================");
	}

	@Test(priority = 4, enabled = true)
	@Description("Validating Small Banner ad")
	public void validating_Small_Banner_Ad() throws Exception {
		WebDriverWait wait = new WebDriverWait(Ad, 180);
		System.out.println("================= Small Banner Ad Test Started =========================");
		logStep("================= Small Banner Ad Test Started =========================");

		Ad.closeApp();
		Ad.launchApp();

		HashMap<Integer, Float> dTime = new HashMap<Integer, Float>();
		for (int i = 1; i <= 50; i++) {
			try {
				System.out.println("Iteration Number: " + i);
				Ad.closeApp();
				Ad.launchApp();

				// Video Marque
				Ad.findElementByAccessibilityId("Banner ad").click();
				long start = System.currentTimeMillis();

				MobileElement adElement = (MobileElement) wait
						.until(ExpectedConditions.visibilityOf(Ad.findElementByClassName("android.webkit.WebView")));

	// finding the time after the operation is executed
				long end = System.currentTimeMillis();

	//finding the time difference and converting it into seconds
				float sec = (end - start) / 1000F;
				dTime.put(i, sec);
				System.out.println(sec + " seconds");
				logStep(sec + " seconds");
			} catch (Exception e) {
				System.out.println("An Exception in the iteration: " + i);
				logStep("An Exception in the iteration: " + i);
			}
			

		}
		for (Map.Entry m : dTime.entrySet()) {
			System.out.println("Iteration No: " + m.getKey() + " " + " Ad display Time is: " + m.getValue());
		}

		System.out.println("================= Small Banner Ad Test Ended =========================");
		logStep("================= Small Banner Ad Test Ended =========================");
	}

	@Test(priority = 5, enabled = true)
	@Description("Validating Large Banner ad")
	public void validating_Large_Banner_Ad() throws Exception {
		WebDriverWait wait = new WebDriverWait(Ad, 180);
		System.out.println("================= Large Banner Ad Test Started =========================");
		logStep("================= Large Banner Ad Test Started =========================");

		Ad.closeApp();
		Ad.launchApp();

		HashMap<Integer, Float> dTime = new HashMap<Integer, Float>();
		for (int i = 1; i <= 50; i++) {
			try {
				System.out.println("Iteration Number: " + i);
				Ad.closeApp();
				Ad.launchApp();

				// Video Marque
				Ad.findElementByAccessibilityId("Large banner ad").click();
				long start = System.currentTimeMillis();

				MobileElement adElement = (MobileElement) wait
						.until(ExpectedConditions.visibilityOf(Ad.findElementByClassName("android.webkit.WebView")));

	// finding the time after the operation is executed
				long end = System.currentTimeMillis();

	//finding the time difference and converting it into seconds
				float sec = (end - start) / 1000F;
				dTime.put(i, sec);
				System.out.println(sec + " seconds");
				logStep(sec + " seconds");
			} catch (Exception e) {
				System.out.println("An Exception in the iteration: " + i);
				logStep("An Exception in the iteration: " + i);
			}
			

		}
		for (Map.Entry m : dTime.entrySet()) {
			System.out.println("Iteration No: " + m.getKey() + " " + " Ad display Time is: " + m.getValue());
		}

		System.out.println("================= Large Banner Ad Test Ended =========================");
		logStep("================= Large Banner Ad Test Ended =========================");
	}
	@Test(priority = 6, enabled = true)
	@Description("Validating Full Banner ad")
	public void validating_Full_Banner_Ad() throws Exception {
		WebDriverWait wait = new WebDriverWait(Ad, 180);
		System.out.println("================= Full Banner Ad Test Started =========================");
		logStep("================= Full Banner Ad Test Started =========================");

		Ad.closeApp();
		Ad.launchApp();

		HashMap<Integer, Float> dTime = new HashMap<Integer, Float>();
		for (int i = 1; i <= 50; i++) {
			try {
				System.out.println("Iteration Number: " + i);
				Ad.closeApp();
				Ad.launchApp();

				// Video Marque
				Ad.findElementByAccessibilityId("Full banner ad").click();
				long start = System.currentTimeMillis();

				MobileElement adElement = (MobileElement) wait
						.until(ExpectedConditions.visibilityOf(Ad.findElementByClassName("android.webkit.WebView")));

	// finding the time after the operation is executed
				long end = System.currentTimeMillis();

	//finding the time difference and converting it into seconds
				float sec = (end - start) / 1000F;
				dTime.put(i, sec);
				System.out.println(sec + " seconds");
				logStep(sec + " seconds");
			} catch (Exception e) {
				System.out.println("An Exception in the iteration: " + i);
				logStep("An Exception in the iteration: " + i);
			}
			

		}
		for (Map.Entry m : dTime.entrySet()) {
			System.out.println("Iteration No: " + m.getKey() + " " + " Ad display Time is: " + m.getValue());
		}

		System.out.println("================= Full Banner Ad Test Ended =========================");
		logStep("================= Full Banner Ad Test Ended =========================");
	}
	@Test(priority = 7, enabled = true)
	@Description("Validating Medium rectangle ad")
	public void validating_Medium_Rectangle_Ad() throws Exception {
		WebDriverWait wait = new WebDriverWait(Ad, 180);
		System.out.println("================= Medium rectangle Test Started =========================");
		logStep("================= Medium rectangle Ad Test Started =========================");

		Ad.closeApp();
		Ad.launchApp();

		HashMap<Integer, Float> dTime = new HashMap<Integer, Float>();
		for (int i = 1; i <= 50; i++) {
			try {
				System.out.println("Iteration Number: " + i);
				Ad.closeApp();
				Ad.launchApp();

				// Video Marque
				Ad.findElementByAccessibilityId("Medium rectangle ad").click();
				long start = System.currentTimeMillis();

				MobileElement adElement = (MobileElement) wait
						.until(ExpectedConditions.visibilityOf(Ad.findElementByClassName("android.webkit.WebView")));

	// finding the time after the operation is executed
				long end = System.currentTimeMillis();

	//finding the time difference and converting it into seconds
				float sec = (end - start) / 1000F;
				dTime.put(i, sec);
				System.out.println(sec + " seconds");
				logStep(sec + " seconds");
			} catch (Exception e) {
				System.out.println("An Exception in the iteration: " + i);
				logStep("An Exception in the iteration: " + i);
			}
			

		}
		for (Map.Entry m : dTime.entrySet()) {
			System.out.println("Iteration No: " + m.getKey() + " " + " Ad display Time is: " + m.getValue());
		}

		System.out.println("================= Medium rectangle Ad Test Ended =========================");
		logStep("================= Medium rectangle Ad Test Ended =========================");
	}

}

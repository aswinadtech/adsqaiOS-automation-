package com.twc.ios.app.testcases;

import io.appium.java_client.MobileElement;
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

public class TWCFlutteriOSAdValidation extends TwcIosBaseTest {
	private static final String CONFIG_FILE_PATH = "charles_common.config";
	public File configFile;

	/*@BeforeClass(alwaysRun = true)
	@Description("BeforeClass")
	public void beforeClass() {
		System.out.println("****** TWC Flutter Test Started");
		logStep("****** TWC Flutter Test Started");
		this.configFile = this.charlesGeneralConfigFile(CONFIG_FILE_PATH);
		proxy = new CharlesProxy("localhost", 8111, CONFIG_FILE_PATH);
		proxy.startCharlesProxyWithUI();
		proxy.disableRewriting();
		proxy.stopRecording();
		proxy.disableMapLocal();
	}*/
	
	@AfterClass(alwaysRun = true)
	@Description("AfterClass")
	public void afterTest() throws Exception {
		try {
		/*Functions.archive_folder("Charles");
			proxy.disableRewriting();
			proxy.quitCharlesProxy();*/

			Ad.terminateApp("com.weather.admobLatencyTest");
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

		Functions.capabilities();
		Functions.Appium_Autostart();
//		Utils.getCurrentMacIPAddressAndSetiPhoneProxy(true, true);
		/*proxy.startRecording();
		proxy.clearCharlesSession();
		Functions.archive_folder("Charles");*/

		ReadExcelValues.excelValues("Smoke", "Capabilities");

		DesiredCapabilities capabilities = new DesiredCapabilities();

		// Capabilities for IOS and Android Based on Device Selection
		capabilities.setCapability(ReadExcelValues.data[1][0], ReadExcelValues.data[1][Cap]);
		capabilities.setCapability(ReadExcelValues.data[2][0], ReadExcelValues.data[2][Cap]);
		capabilities.setCapability(ReadExcelValues.data[3][0], ReadExcelValues.data[3][Cap]);
		capabilities.setCapability(ReadExcelValues.data[5][0], ReadExcelValues.data[5][Cap]);
//		capabilities.setCapability(ReadExcelValues.data[6][0], ReadExcelValues.data[6][Cap]);
		capabilities.setCapability("automationName", "XCUITest");
		capabilities.setCapability(ReadExcelValues.data[7][0], "=" + ReadExcelValues.data[7][Cap]);

		capabilities.setCapability(ReadExcelValues.data[8][0], ReadExcelValues.data[8][Cap]);
		capabilities.setCapability("noReset", "true");
//		capabilities.setCapability(ReadExcelValues.data[12][0], ReadExcelValues.data[12][Cap]);
		capabilities.setCapability(ReadExcelValues.data[13][0], "7200");
		capabilities.setCapability(ReadExcelValues.data[14][0], true);

		capabilities.setCapability(ReadExcelValues.data[11][0], ReadExcelValues.data[11][Cap]);
		capabilities.setCapability("launchTimeout", 60000);
		capabilities.setCapability("useNewWDA", true);

		capabilities.setCapability("bundleId", "com.weather.admobLatencyTest");
		capabilities.setCapability("xcodeConfigfile",
				"/Applications/Appium.app/Contents/Resources/app/node_modules/appium/node_modules/appium-xcuitest-driver/WebDriverAgent/Config.xcconfig");

		capabilities.setCapability("realDeviceLogger", "/Users/apple/node_modules/deviceconsole");
		capabilities.setCapability("wdaLocalPort", "7403");

		capabilities.setCapability("clearSystemFiles", true);
		System.out.println("Reading capabilities done");

		Ad = new IOSDriver(new URL("http://127.0.0.1:4733/wd/hub"), capabilities);
		// Ad= new IOSDriver<MobileElement>(service, capabilities);
		Ad.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		Ad.terminateApp("com.weather.admobLatencyTest");
		System.out.println("App Closed SuccessFully");
		logStep("App Closed SuccessFully");
		TestBase.waitForMilliSeconds(5000);
		/*Ad.launchApp();
		// Ad.activateApp("com.weather.TWC");
		System.out.println("App Launched SuccessFully");
		logStep("App Launched SuccessFully");*/

	}

	@Test(priority = 1, enabled = true)
	@Description("Validating Interstitial ad")
	public void validating_Interstitial_Ad() throws Exception {
		WebDriverWait wait = new WebDriverWait(Ad, 180);
		System.out.println("================= Interstitial Ad Test Started =========================");
		logStep("================= Interstitial Ad Test Started =========================");
		Ad.terminateApp("com.weather.admobLatencyTest");
		Ad.launchApp();

		HashMap<Integer, Float> dTime = new HashMap<Integer, Float>();
		for (int i = 1; i <= 50; i++) {
			try {
				System.out.println("Iteration Number: " + i);
				Ad.terminateApp("com.weather.admobLatencyTest");
				Ad.launchApp();

				// Interstitial

				Ad.findElementByAccessibilityId("Interstitial ad").click();

				long start = System.currentTimeMillis();

				// Interstitial

				MobileElement adElement = (MobileElement) wait.until(ExpectedConditions.visibilityOf(Ad.findElementByXPath(
						"//XCUIElementTypeStaticText[@name=\"Test mode\"]")));

	// finding the time after the operation is executed
				long end = System.currentTimeMillis();
				// Interstitial

				//Ad.findElementByAccessibilityId("Close Advertisement").click();
				

	//finding the time difference and converting it into seconds
				float sec = (end - start) / 1000F;
				dTime.put(i, sec);
				System.out.println(sec + " seconds");
				logStep(sec + " seconds");
				
			}catch (Exception e) {
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
		Ad.terminateApp("com.weather.admobLatencyTest");
		Ad.launchApp();

		HashMap<Integer, Float> dTime = new HashMap<Integer, Float>();
		for (int i = 1; i <= 50; i++) {
			try {
				System.out.println("Iteration Number: " + i);
				Ad.terminateApp("com.weather.admobLatencyTest");
				Ad.launchApp();


				// Static Marque
				Ad.findElementByAccessibilityId("Marquee image ad").click();
				long start = System.currentTimeMillis();


				MobileElement adElement = (MobileElement) wait.until(ExpectedConditions
						.presenceOfElementLocated(By.xpath("//XCUIElementTypeOther[@name=\"IM 4 (Next Gen) w/ Preview Mode - Static Images Test\"]")));

	// finding the time after the operation is executed
				long end = System.currentTimeMillis();
				
	//finding the time difference and converting it into seconds
				float sec = (end - start) / 1000F;
				dTime.put(i, sec);
				System.out.println(sec + " seconds");
				logStep(sec + " seconds");
				
			}catch (Exception e) {
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
		Ad.terminateApp("com.weather.admobLatencyTest");
		Ad.launchApp();

		HashMap<Integer, Float> dTime = new HashMap<Integer, Float>();
		for (int i = 1; i <= 50; i++) {
			try {
				System.out.println("Iteration Number: " + i);
				Ad.terminateApp("com.weather.admobLatencyTest");
				Ad.launchApp();

				// Video Marque
				Ad.findElementByAccessibilityId("Marquee video ad").click();
				long start = System.currentTimeMillis();

				MobileElement adElement = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//XCUIElementTypeOther[@name=\"Nextgen IM Video BG Test with Fade + Gridlines\"]")));

	// finding the time after the operation is executed
				long end = System.currentTimeMillis();

	//finding the time difference and converting it into seconds
				float sec = (end - start) / 1000F;
				dTime.put(i, sec);
				System.out.println(sec + " seconds");
				logStep(sec + " seconds");
			}catch (Exception e) {
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
		Ad.terminateApp("com.weather.admobLatencyTest");
		Ad.launchApp();

		HashMap<Integer, Float> dTime = new HashMap<Integer, Float>();
		for (int i = 1; i <= 50; i++) {
			try {
				System.out.println("Iteration Number: " + i);
				Ad.terminateApp("com.weather.admobLatencyTest");
				Ad.launchApp();

				// Video Marque
				Ad.findElementByAccessibilityId("Banner ad").click();
				long start = System.currentTimeMillis();

				MobileElement adElement = (MobileElement) wait.until(ExpectedConditions.visibilityOf(Ad.findElementByXPath(
						"//XCUIElementTypeStaticText[@name=\"Test mode\"]")));

	// finding the time after the operation is executed
				long end = System.currentTimeMillis();

	//finding the time difference and converting it into seconds
				float sec = (end - start) / 1000F;
				dTime.put(i, sec);
				System.out.println(sec + " seconds");
				logStep(sec + " seconds");
				
			}catch (Exception e) {
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
		Ad.terminateApp("com.weather.admobLatencyTest");
		Ad.launchApp();

		HashMap<Integer, Float> dTime = new HashMap<Integer, Float>();
		for (int i = 1; i <= 50; i++) {
			try {
				System.out.println("Iteration Number: " + i);
				Ad.terminateApp("com.weather.admobLatencyTest");
				Ad.launchApp();

				// Video Marque
				Ad.findElementByAccessibilityId("Large banner ad").click();
				long start = System.currentTimeMillis();

				MobileElement adElement = (MobileElement) wait.until(ExpectedConditions.visibilityOf(Ad.findElementByXPath(
						"//XCUIElementTypeStaticText[@name=\"Test mode\"]")));

	// finding the time after the operation is executed
				long end = System.currentTimeMillis();

	//finding the time difference and converting it into seconds
				float sec = (end - start) / 1000F;
				dTime.put(i, sec);
				System.out.println(sec + " seconds");
				logStep(sec + " seconds");
			}catch (Exception e) {
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
		Ad.terminateApp("com.weather.admobLatencyTest");
		Ad.launchApp();

		HashMap<Integer, Float> dTime = new HashMap<Integer, Float>();
		for (int i = 1; i <= 50; i++) {
			try {
				System.out.println("Iteration Number: " + i);
				Ad.terminateApp("com.weather.admobLatencyTest");
				Ad.launchApp();

				// Video Marque
				Ad.findElementByAccessibilityId("Full banner ad").click();
				long start = System.currentTimeMillis();

				MobileElement adElement = (MobileElement) wait.until(ExpectedConditions.visibilityOf(Ad.findElementByXPath(
						"//XCUIElementTypeStaticText[@name=\"Test mode\"]")));

	// finding the time after the operation is executed
				long end = System.currentTimeMillis();

	//finding the time difference and converting it into seconds
				float sec = (end - start) / 1000F;
				dTime.put(i, sec);
				System.out.println(sec + " seconds");
				logStep(sec + " seconds");
			}catch (Exception e) {
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
		System.out.println("================= Medium Rectangle Ad Test Started =========================");
		logStep("================= Medium Rectangle Ad Test Started =========================");
		Ad.terminateApp("com.weather.admobLatencyTest");
		Ad.launchApp();

		HashMap<Integer, Float> dTime = new HashMap<Integer, Float>();
		for (int i = 1; i <= 50; i++) {
			try {
				System.out.println("Iteration Number: " + i);
				Ad.terminateApp("com.weather.admobLatencyTest");
				Ad.launchApp();

				// Video Marque
				Ad.findElementByAccessibilityId("Medium rectangle ad").click();
				long start = System.currentTimeMillis();

				MobileElement adElement = (MobileElement) wait.until(ExpectedConditions.visibilityOf(Ad.findElementByXPath(
						"//XCUIElementTypeStaticText[@name=\"Test mode\"]")));

	// finding the time after the operation is executed
				long end = System.currentTimeMillis();

	//finding the time difference and converting it into seconds
				float sec = (end - start) / 1000F;
				dTime.put(i, sec);
				System.out.println(sec + " seconds");
				logStep(sec + " seconds");
			}catch (Exception e) {
				System.out.println("An Exception in the iteration: " + i);
				logStep("An Exception in the iteration: " + i);
			}
			
			
		}
		for (Map.Entry m : dTime.entrySet()) {
			System.out.println("Iteration No: " + m.getKey() + " " + " Ad display Time is: " + m.getValue());
		}

		System.out.println("================= Medium Rectangle Ad Test Ended =========================");
		logStep("================= Medium Rectangle Ad Test Ended =========================");
	}

}

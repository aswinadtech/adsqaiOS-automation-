package com.twc.ios.app.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.io.File;

import org.aspectj.lang.reflect.UnlockSignature;
import org.openqa.selenium.io.TemporaryFilesystem;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

//import ru.yandex.qatools.allure.annotations.Title;
import io.qameta.allure.Description;

import com.twc.ios.app.charlesfunctions.CharlesProxy;
import com.twc.ios.app.functions.Functions;
import com.twc.ios.app.general.Driver;
import com.twc.ios.app.general.RetryAnalyzer;
import com.twc.ios.app.general.TwcIosBaseTest;
import com.twc.ios.app.general.Utils;

@Listeners(value = com.twc.ios.app.general.MyTestListenerAdapter.class)
public class BuildDownLoadTest extends TwcIosBaseTest {

		private static final String CONFIG_FILE_PATH = "charles_common.config";
		//public static CharlesProxy proxy;
		public File configFile;

		@BeforeClass(alwaysRun = true)
		public void beforeClass() {
			System.out.println("****** Build Download Test Started");
			logStep("****** Build Download Test Started");
			this.configFile = this.charlesGeneralConfigFile(CONFIG_FILE_PATH);
			proxy = new CharlesProxy("localhost", 8111, CONFIG_FILE_PATH);

			proxy.startCharlesProxyWithUI();
			proxy.disableRewriting();
			proxy.stopRecording();
			proxy.disableMapLocal();
		}
		
		@AfterClass(alwaysRun = true)
		public void afterClass() throws Exception {
			if (this.configFile != null) {
				this.configFile.delete();
			}
			proxy.disableRewriting();
			proxy.quitCharlesProxy();
			try {
				//Ad.closeApp();
				Ad.quit();
				System.out.println("App closed successfully");
				logStep("App closed successfully");
			}catch(Exception e) {
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
			
			System.out.println("****** Build Download Test Ended");
			logStep("****** Build Download Test Ended");
		}
		
	@Test(priority = 0)
	public void downloadAndInstallApp() throws Exception {
		System.out.println("==============================================");
		System.out.println("****** downloadAndInstallApp Test Started");
		//Preconditions
		Utils.getiPhoneUDID();
		Utils.getiOSVersion();
		Functions.capabilities();
		Functions.Appium_Autostart();
		Utils.getCurrentMacIPAddressAndSetiPhoneProxy(false);
		Functions.uninstallApp();
		Functions.launchFirebaseInSafariAndInstallApp(properties.getProperty("downloadBuildType"));
		Utils.twcAppInstalledCheck();
		
	}

	/*
	 * @Test(priority = 9999) public void afterTest() throws Exception {
	 * System.out.println("==============================================");
	 * System.out.println("****** After Test Started"); Ad.getOrientation();
	 * Ad.closeApp(); if(Ad != null) { try { Ad.quit(); } catch (Exception ex) { //
	 * Session crashed/died probably so no big deal, since // this exception was
	 * thrown when trying to close session. // Also, avoids failures in before/after
	 * methods for TestNG. System.out.
	 * println("NoSuchSessionException was thrown while attempting to close session. Ignoring this error."
	 * );
	 * logStep("NoSuchSessionException was thrown while attempting to close session. Ignoring this error."
	 * ); } System.out.println("Closing appium session.. Done");
	 * logStep("Closing appium session.. Done"); }
	 * 
	 * System.out.println("App closed successfully"); }
	 */


}

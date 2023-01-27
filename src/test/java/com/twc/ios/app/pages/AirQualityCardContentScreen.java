package com.twc.ios.app.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.twc.ios.app.charlesfunctions.CharlesProxy;
import com.twc.ios.app.functions.Functions;
import com.twc.ios.app.general.Driver;
import com.twc.ios.app.general.ReadExcelValues;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.Utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;

public class AirQualityCardContentScreen extends Utils {
	AppiumDriver<MobileElement> Ad;
	
	String aQCardContentPageHeader_Name = "Air Quality";
	String advertisementOnAQDetails_Xpath = "//XCUIElementTypeOther[@name=\"weather.aq-adContentView\"]";
	

	By byAQCardContentPageHeader = MobileBy.name(aQCardContentPageHeader_Name);
	
	By byAdvertisementOnAQDetails = MobileBy.xpath(advertisementOnAQDetails_Xpath);
	
	
	

	MobileElement aQCardContentPageHeader = null;
	MobileElement advertisementOnAQDetails = null;
	

	public AirQualityCardContentScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Wait For AirQuality Card Content Page")
	public void waitForAirQualityCardContentPage() throws Exception {
		TestBase.waitForVisibilityOfElementLocated(Ad, 80, byAQCardContentPageHeader);
		aQCardContentPageHeader = Ad.findElement(byAQCardContentPageHeader);
		attachScreen();
				
	}
	
	/**
	 * Verifies Pixel Call
	 * @param excelName
	 * @param sheetName
	 * @throws Exception
	 */
	public void verifyPixel_Call_When_Navigated_To_AQDetails_Page_From_Feed1_Card(String excelName, String sheetName) throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String host = ReadExcelValues.data[2][Cap];
		String path = ReadExcelValues.data[3][Cap];
		boolean flag = verifyAPICallWithHostandPath(host, path);
		if (flag) {
			System.out.println(host + path + " call is present in Charles session");
			logStep(host + path + " call is present in Charles session");
			System.out.println(host + path + " :Call Verification is successful");
			logStep(host + path + " :Call Verification is successful");

		} else {
			System.out.println(host + path + " call is not present in Charles session");
			logStep(host + path + " call is not present in Charles session");
			System.out.println(host + path + " :Call Verification is failed");
			logStep(host + path + " :Call Verification is failed");
			Assert.fail(host + path + " call is not present in Charles session");

		}
	}
	
	/**
	 * Verifies Pixel Call
	 * @param excelName
	 * @param sheetName
	 * @param expected
	 * @throws Exception
	 */
	public void verifyPixel_Call_When_Navigated_To_AQDetails_Page_From_Feed1_Card(String excelName, String sheetName, boolean expected)
			throws Exception {
		ReadExcelValues.excelValues(excelName, sheetName);
		String host = ReadExcelValues.data[2][Cap];
		String path = ReadExcelValues.data[3][Cap];
		boolean flag = verifyAPICallWithHostandPath(host, path);
		if (flag) {
			System.out.println(host + path + " call is present in Charles session");
			logStep(host + path + " call is present in Charles session");

		} else {
			System.out.println(host + path + " call is not present in Charles session");
			logStep(host + path + " call is not present in Charles session");
		}
		if (expected == flag) {
			System.out.println(host + path + " :Call Verification is successful");
			logStep(host + path + " :Call Verification is successful");

		} else {
			System.out.println(host + path + " :Call Verification is failed");
			logStep(host + path + " :Call Verification is failed");
			if (expected) {
				System.out.println(host + path + " :Call expected to present but it not exists");
				logStep(host + path + " :Call expected to present but it not exists");
				Assert.fail(host + path + " :API Call expected to present but it not exists");
			} else {
				System.out.println(host + path + " :Call is not expected to present but it exists");
				logStep(host + path + " :Call is not expected to present but it exists");
				Assert.fail(host + path + " :Call is not expected to present but it exists");
			}
		}

	}
	
	@Step("Assert Ad On AQ Details Page")
	public void assertAdOnAQDetailsPage() {
		try {
			advertisementOnAQDetails = Ad.findElement(byAdvertisementOnAQDetails);
			System.out.println("AQ Details Page Ad displayed");
			logStep("AQ Details Page Ad displayed");
		} catch (Exception e) {
			Assert.fail("Ad not didplayed on AQ Details page");
		} finally {
			attachScreen();
		}
		
	}
	
}

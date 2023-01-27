package com.twc.ios.app.pages;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.w3c.dom.Document;

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

public class DailyNavTab extends Utils {
	AppiumDriver<MobileElement> Ad;
	String dailyTab_AccessibilityId = "dailyForecast2Tab";
	String tooltipClose_AccessibilityId = "tooltip close";
	String dynamicDailyCell_Xpath = "//XCUIElementTypeCell[@name='dailyCollectionViewCell_";
	String iDDAd_AccessibilityId = "PROTOTYPE GRIDS FOR IDD - w/CREATIVE";
	String dailyNavTabTestAd_AccessibilityId = "an ad";
	String dailyNavTabTestAd_Xpath = "//XCUIElementTypeOther[@name='Ad']";
	String severeInsightText_Xpath = "//XCUIElementTypeStaticText[@name='Seek  safe shelter immediately. Damaging winds, large hail and tornados very likely.']";
	
	String advertisementOnDailyTab_Xpath = "//XCUIElementTypeOther[@name='an ad']//XCUIElementTypeWebView";
	
	By byDailyNavTab = MobileBy.AccessibilityId(dailyTab_AccessibilityId);
	By byToolTip = MobileBy.AccessibilityId(tooltipClose_AccessibilityId);
	By byIDDAd = MobileBy.AccessibilityId(iDDAd_AccessibilityId);
	//By byDailyNavTabTestAd = MobileBy.AccessibilityId(dailyNavTabTestAd_AccessibilityId);
	By byDailyNavTabTestAd = MobileBy.xpath(dailyNavTabTestAd_Xpath);
	By bySevereInsightText = MobileBy.xpath(severeInsightText_Xpath);
	
	By byAdvertisementOnDailyTab = MobileBy.xpath(advertisementOnDailyTab_Xpath);

	MobileElement dailyNavTab = null;
	MobileElement toolTip = null;
	MobileElement dynamicDailyCell = null;
	MobileElement iDDAd = null;
	MobileElement dailyNavTabTestAd = null;
	MobileElement severeInsightText = null;
	MobileElement advertisementOnDailyTab = null;

	public DailyNavTab(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigate To Daily Tab")
	public void navigateToDailyTab() throws Exception {
		try {
			dailyNavTab = Ad.findElement(byDailyNavTab);
			TestBase.clickOnElement(byDailyNavTab, dailyNavTab, "Daily Nav Tab");
			TestBase.waitForMilliSeconds(10000);
			System.out.println("Navigated to Daily tab ");
			logStep("Navigated to Daily tab");
			attachScreen();
			try {
				toolTip = Ad.findElement(byToolTip);
				TestBase.clickOnElement(byToolTip, toolTip, "Tooltip");
				TestBase.waitForMilliSeconds(4000);
			} catch (Exception e) {
				System.out.println("Tooltip not displayed on daily details page");
				logStep("Tooltip not displayed on daily details page");
			}

		} catch (Exception e) {
			System.out.println("Daily tab not displayed");
			logStep("Daily tab not displayed");
			attachScreen();
		}

	}
	
	@Step("Verify Tapability of Sticky Test Ad On Daily Nav Tab")
	public void verifyTapabilityOfTestAdOnDailyNavTab() {
			try {
				TestBase.waitForMilliSeconds(10000);
				dailyNavTabTestAd = Ad.findElement(byDailyNavTabTestAd);
				TestBase.clickOnElement(byDailyNavTabTestAd, dailyNavTabTestAd, "Daily Nav Tab Test Ad");
				
				TestBase.waitForMilliSeconds(10000);
			
				//Functions.checkForAppState();
				Assert.assertFalse(TestBase.isElementExists(byDailyNavTabTestAd));
			} catch (Exception e) {
				try {
					TestBase.waitForMilliSeconds(10000);
					dailyNavTabTestAd = Ad.findElement(byDailyNavTabTestAd);
					TestBase.clickOnElement(byDailyNavTabTestAd, dailyNavTabTestAd, "Daily Nav Tab Test Ad");
					TestBase.waitForMilliSeconds(10000);
					
					//Functions.checkForAppState();
					Assert.assertFalse(TestBase.isElementExists(byDailyNavTabTestAd));
				} catch (Exception e1) {
					
					System.out.println("Daily Nav Tab Test Ad is Not displayed");
					logStep("Daily Nav Tab Test Ad is Not displayed");
					Assert.fail("Daily Nav Tab Test Ad is Not displayed");
				}
			} finally {
				attachScreen();
			}
	}
	
	/**
	 * This method Validates Daily Details Ad Units of 15 days, navigating from Daily Nav Tab
	 * @param excelName
	 * @param sheetName
	 * @throws Exception
	 */
	@Step("Validate Daily Details Ad Units of 15 days, navigating from Daily Nav Tab: {0}, {1}")
	public void validateDailyDetailsAdUnits(String excelName, String sheetName) throws Exception {
		HomeNavTab hmtab = new HomeNavTab(Ad);
		DailyNavTab dtab = new DailyNavTab(Ad);
		ReadExcelValues.excelValues(excelName, sheetName);
		String expected = ReadExcelValues.data[18][Cap];
		ArrayList<String> namesOfDays = new ArrayList<String>();
		
		String ddWeekDayName = dailyDetailsDayOfWeek;
		System.out.println("ddWeekDayName :" + ddWeekDayName);
		logStep("ddWeekDayName :" + ddWeekDayName);
		int dayn = 0;
		switch (ddWeekDayName) {
		case "sun":
			dayn = 1;
			break;
		case "mon":
			dayn = 2;
			break;
		case "tue":
			dayn = 3;
			break;
		case "wed":
			dayn = 4;
			break;
		case "thu":
			dayn = 5;
			break;
		case "fri":
			dayn = 6;
			break;
		case "sat":
			dayn = 7;
			break;
		}
		for (int i = 1; i <= 7; i++) {
			// calendar.add(Calendar.DATE, 1);

			// int daynum = calendar.get(Calendar.DAY_OF_WEEK);
			int daynum = dayn;
			String dayName = "";
			if (daynum == 1 || daynum == 8) {
				dayName = "sun";
			} else if (daynum == 2 || daynum == 9) {
				dayName = "mon";
			} else if (daynum == 3 || daynum == 10) {
				dayName = "tue";
			} else if (daynum == 4 || daynum == 11) {
				dayName = "wed";
			} else if (daynum == 5 || daynum == 12) {
				dayName = "thu";
			} else if (daynum == 6 || daynum == 13) {
				dayName = "fri";
			} else if (daynum == 7 || daynum == 14) {
				dayName = "sat";
			}
			namesOfDays.add(dayName);
			System.out.println(dayName);
			logStep(dayName);
			dayn++;
		}
		System.out.println(namesOfDays);

		Functions.archive_folder("Charles");
		hmtab.clickonHomeTab();
		CharlesProxy.proxy.clearCharlesSession();
		dtab.navigateToDailyTab();

		int j = 0;
		int k = 0;
		int l = 0;
		String dayNotPresent = "";
		int failCount = 0;
		for (int i = 0; i < 15; i++) {
		//for (int i = 0; i < 7; i++) {
			//Ad.findElementByXPath("//XCUIElementTypeCell[@name='dailyCollectionViewCell_" + i + "'" + "]").click();
			By byDynamicDailyCell = MobileBy.xpath(dynamicDailyCell_Xpath+ i + "'" + "]");
			dynamicDailyCell = Ad.findElement(byDynamicDailyCell);
			TestBase.clickOnElement(byDynamicDailyCell, dynamicDailyCell, "Daily Cell "+i);
			// Functions.delete_folder("Charles");
			Functions.archive_folder("Charles");
			Thread.sleep(10000);
			attachScreen();
			CharlesProxy.proxy.getXml();
			Utils.createXMLFileForCharlesSessionFile();
			// Read the content form file
			File fXmlFile = new File(outfile.getName());
			// File fXmlFile = outfile;

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setValidating(false);
			dbFactory.setNamespaceAware(true);
			dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
			dbFactory.setFeature("http://xml.org/sax/features/validation", false);
			dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
			dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			Document doc = dBuilder.parse(fXmlFile);
			// Getting the transaction element by passing xpath expression
			// NodeList nodeList = doc.getElementsByTagName("transaction");
			String xpathExpression = "charles-session/transaction/@query";
			List<String> getQueryList = Utils.evaluateXPath(doc, xpathExpression);
			if (i < 7) {

				String cday = namesOfDays.get(j).concat("1");
				// cday.concat("_").concat("1");
				// System.out.println(cday);
				String expectediu = expected.concat("_") + cday;
				String iuId = expectediu;
				boolean iuExists = false;

				for (String qry : getQueryList) {
					if (qry.contains(iuId)) {

						iuExists = true;

						break;

					}
				}
				if (iuExists) {
					System.out.println(expectediu + " ad call is present");
					logStep(expectediu + " ad call is present");
				} else {
					System.out.println(expectediu + " ad call is not present");
					logStep(expectediu + " ad call is not present");
					failCount++;
					// Assert.fail(feedCards[i] + " ad call is not present");
					dayNotPresent = dayNotPresent.concat(cday + ", ");

				}

				j++;
			} else if (i < 14) {

				String cday = namesOfDays.get(k).concat("2");
				// cday.concat("_").concat("1");
				// System.out.println(cday);
				String expectediu = expected.concat("_") + cday;
				String iuId = expectediu;
				boolean iuExists = false;

				for (String qry : getQueryList) {
					if (qry.contains(iuId)) {

						iuExists = true;

						break;

					}
				}
				if (iuExists) {
					System.out.println(expectediu + " ad call is present");
					logStep(expectediu + " ad call is present");
				} else {
					System.out.println(expectediu + " ad call is not present");
					logStep(expectediu + " ad call is not present");
					failCount++;
					// Assert.fail(feedCards[i] + " ad call is not present");
					dayNotPresent = dayNotPresent.concat(cday + ", ");

				}

				k++;
			} else {
				String cday = namesOfDays.get(l).concat("3");
				// cday.concat("_").concat("1");
				// System.out.println(cday);
				String expectediu = expected.concat("_") + cday;
				String iuId = expectediu;
				boolean iuExists = false;

				for (String qry : getQueryList) {
					if (qry.contains(iuId)) {

						iuExists = true;

						break;

					}
				}
				if (iuExists) {
					System.out.println(expectediu + " ad call is present");
					logStep(expectediu + " ad call is present");
				} else {
					System.out.println(expectediu + " ad call is not present");
					logStep(expectediu + " ad call is not present");
					failCount++;
					// Assert.fail(feedCards[i] + " ad call is not present");
					dayNotPresent = dayNotPresent.concat(cday + ", ");

				}
				l++;
			}
			CharlesProxy.proxy.clearCharlesSession();
		}

		if (failCount > 0) {
			System.out.println(dayNotPresent + " ad call is not present");
			logStep(dayNotPresent + " ad call is not present");
			Exception = dayNotPresent + " ad call is not present";
			System.out.println("Daily Details ad units Verification is failed");
			logStep("Daily Details ad units Verification is failed");
			Assert.fail(Exception);
		}

	}
	
	/**
	 * Verifies IDD ad by call response
	 * @param Excelname
	 * @param sheetName
	 * @throws Exception
	 */
	public void verifyIDDAd_byCallResponse(String Excelname, String sheetName) throws Exception {

		boolean iMCallResponse = Utils.isIDDCall_hasResponse(Excelname, sheetName);
		String cardName = "homescreen";
		MobileElement adele;
		MobileElement nextGenAdImage;

		Thread.sleep(10000);
		if (iMCallResponse == true) {
			try {
				//adele = Ad.findElementByAccessibilityId("PROTOTYPE GRIDS FOR IDD - w/CREATIVE");
				iDDAd = Ad.findElement(byIDDAd);
				
				if (iDDAd.isDisplayed()) {
					logStep("IDD Ad presented on the page ");
					System.out.println("IDD Ad presented on the page ");
					attachScreen();

				}

			} catch (Exception e) {
				System.out.println("IDD Ad Not presented on the screen though call response true");
				logStep("IDD Ad Not presented on the screen though call response true");
				attachScreen();
				Assert.fail("IDD Ad Not presented on the screen though call response true");
			}

		} else {
			try {
				//adele = Ad.findElementByAccessibilityId("PROTOTYPE GRIDS FOR IDD - w/CREATIVE");
				iDDAd = Ad.findElement(byIDDAd);
				if (iDDAd.isDisplayed()) {
					logStep("IDD Ad presented on the page when response is false");
					System.out.println("IDD Ad presented on the page when response is false");
					attachScreen();
					Assert.fail("IDD Ad presented on the page when response is false");
				}

			} catch (Exception e) {
				System.out.println("IDD Ad Not presented on the screen since call response false");
				logStep("IDD Ad Not presented on the screen since call response false");
				attachScreen();

			}
		}

	}
	
	@Step("Assert Severe Insight Text On Daily Nav Tab")
	public void assertSevereInsightTextOnDailyNavTab() {
		//Ad.findElement(By.xpath("(//XCUIElementTypeCell[@name=\"dailyCollectionViewCell_0\"]/../../../following-sibling::XCUIElementTypeOther)[1]/XCUIElementTypeStaticText")).getAttribute("label");
		severeInsightText = Ad.findElement(bySevereInsightText);
	}
	
	@Step("Assert Ad On Daily Nav Tab")
	public void assertAdOnDailyNavTab() {
		try {
			advertisementOnDailyTab = Ad.findElement(byAdvertisementOnDailyTab);
			System.out.println("Daily Nav Tab Ad displayed");
			logStep("Daily Nav Tab Ad displayed");
		} catch (Exception e) {
			Assert.fail("Ad not displayed on Daily Nav Tab");
		}finally {
			attachScreen();
		}
		
	}

}

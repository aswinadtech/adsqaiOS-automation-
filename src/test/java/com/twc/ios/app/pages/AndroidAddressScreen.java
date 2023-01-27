package com.twc.ios.app.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

public class AndroidAddressScreen extends Utils {
	AppiumDriver<MobileElement> Ad;

	String currentLocation_AccessibilityId = "currentLocation";
	String currentLocation2_AccessibilityId = "locationButton";
	String addCityOrZIPCode_AccessibilityId = "Add City or ZIP Code";
	String searchBar_AccessibilityId = "searchBar";
	String searchForYourLocation_Xpath = "//XCUIElementTypeButton[@name=\" Or search for your location\"]";
	String cancel_Name = "Cancel";
	String done_Name = "Done";
	String done_Xpath = "//XCUIElementTypeButton[@name='Done']";
	String search_Name = "Search";
	String eyeglassGrey_Xpath = "//XCUIElementTypeButton[@name='eyeglassGrey']";
	String settingsButton_AccessibilityId = "settingsButton";
	String locationSettingsDetailLabel_AccessibilityId = "location_settings_detail_label";
	String locationCell_Xpath = "//XCUIElementTypeCell[contains(@name,'Location')]";
	String neverLocation_Xpath = "//XCUIElementTypeCell[@name='Never']";
	String returnToTheWeather_AccessibilityId = "Return to The Weather";
	String labelCurrentLocation_Id = "label_currentLocation";
	String buttonLocationManagementClear_AccessibilityId = "button_locationManagementClear";
	String oK_AccessibilityId = "OK";

	By bySearchForYourLocation = MobileBy.xpath(searchForYourLocation_Xpath);
	//By byCurrentLocation = MobileBy.AccessibilityId(currentLocation_AccessibilityId);
	By byCurrentLocation2 = MobileBy.AccessibilityId(currentLocation2_AccessibilityId);
	By byAddCityOrZIPCode = MobileBy.AccessibilityId(addCityOrZIPCode_AccessibilityId);
	By bySearchBar = MobileBy.AccessibilityId(searchBar_AccessibilityId);
	By byCancel = MobileBy.name(cancel_Name);
	By byDone = MobileBy.name(done_Name);
	By bySearchName = MobileBy.name(search_Name);
	By byEyeglassGrey = MobileBy.xpath(eyeglassGrey_Xpath);
	By bySettingsButton = MobileBy.AccessibilityId(settingsButton_AccessibilityId);
	By byLocationSettingsDetailLabel = MobileBy.AccessibilityId(locationSettingsDetailLabel_AccessibilityId);
	By byLocationCell = MobileBy.xpath(locationCell_Xpath);
	By byNeverLocation = MobileBy.xpath(neverLocation_Xpath);
	By byReturnToTheWeather = MobileBy.AccessibilityId(returnToTheWeather_AccessibilityId);
	By byLabelCurrentLocation = MobileBy.id(labelCurrentLocation_Id);
	By byButtonLocationManagementClear = MobileBy.id(buttonLocationManagementClear_AccessibilityId);
	By byOK = MobileBy.id(oK_AccessibilityId);

	String search_AccessibilityId = "Search";
	String locationSearch_Id = "com.weather.Weather:id/search_text";
	String currentLocation_Xpath = "//android.widget.TextView[@text=\"Current Location\"]";
	

	By bySearch = MobileBy.AccessibilityId(search_AccessibilityId);
	By byLocationSearch = MobileBy.id(locationSearch_Id);
	By byCurrentLocation = MobileBy.xpath(currentLocation_Xpath);
	

	MobileElement searchForYourLocation = null;
	MobileElement cancel = null;
	MobileElement done = null;
	MobileElement search = null;
	MobileElement PresentAddress = null;
	MobileElement locationNameElement = null;
	MobileElement eyeglassGrey = null;
	MobileElement settingsButton = null;
	MobileElement locationSettingsDetailLabel = null;
	MobileElement locationCell = null;
	MobileElement neverLocation = null;
	MobileElement returnToTheWeather = null;
	MobileElement labelCurrentLocation = null;
	MobileElement buttonLocationManagementClear = null;
	MobileElement oK = null;

	MobileElement allowButton = null;
	MobileElement iUnderstand = null;
	MobileElement oKButton = null;
	MobileElement alwaysAllow = null;
	MobileElement allowWhileUsingApp = null;

	MobileElement locationSearch = null;
	MobileElement currentLocation = null;

	public AndroidAddressScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}
	
	public void selectCurrentLocation() throws Exception {
		try {
			System.out.println("Current Location Selection Started");
			// TestBase.waitForElementToBeClickable(Ad, 60, bySearch);
	
			search = Ad.findElement(bySearch);
			try {
				TestBase.clickOnElement(bySearch, search, "Search Icon");
				// Ad.findElementByAccessibilityId("Search").click();
			} catch (Exception e) {
				e.printStackTrace();
			}
			/*
			 * try{ TestBase.clickOnElement(bySearch, search, "Search Icon");
			 * }catch(Exception e) { e.printStackTrace(); }
			 */
			boolean found = false;
			while (!found) {

				if (TestBase.isElementExists(byLocationSearch, Ad)) {
					found = true;
					break;
				} else {
					TestBase.clickOnElement(bySearch, search, "Search Icon");
				}

			}
			currentLocation = Ad.findElement(byCurrentLocation);
			TestBase.clickOnElement(byCurrentLocation, currentLocation, "Current Location");
			System.out.println("Current Location Selection Ended");
			attachScreen();
		} catch (Exception ex) {
			attachScreen();
			ex.printStackTrace();
		}

	}

	public void enter_requiredLocation(String location) throws Exception {
		try {
			System.out.println("Searching for location Started");
			// TestBase.waitForElementToBeClickable(Ad, 60, bySearch);
//			new WebDriverWait(Ad, Functions.maxTimeout).until(ExpectedConditions.elementToBeClickable(Ad.findElementByAccessibilityId("Search")));	
			search = Ad.findElement(bySearch);
			try {
				TestBase.clickOnElement(bySearch, search, "Search Icon");
				// Ad.findElementByAccessibilityId("Search").click();
			} catch (Exception e) {
				e.printStackTrace();
			}
			/*
			 * try{ TestBase.clickOnElement(bySearch, search, "Search Icon");
			 * }catch(Exception e) { e.printStackTrace(); }
			 */
//			new WebDriverWait(Ad, Functions.maxTimeout).until(ExpectedConditions.visibilityOfElementLocated(By.id("com.weather.Weather:id/search_text")));
			boolean found = false;
			while (!found) {

				if (TestBase.isElementExists(byLocationSearch, Ad)) {
					found = true;
					break;
				} else {
					TestBase.clickOnElement(bySearch, search, "Search Icon");
				}

			}

		/*	try {
				TestBase.waitForElementToBeClickable(Ad, 60, byLocationSearch);
			} catch (Exception e) {
				e.printStackTrace();
			}*/
			locationSearch = Ad.findElement(byLocationSearch);
//			Ad.findElementById("com.weather.Weather:id/search_text").sendKeys(location);
			TestBase.typeText(locationSearch, "Location Field", location);
			Thread.sleep(10000);
			By byAllLocations = MobileBy.id("com.weather.Weather:id/title");
//			new WebDriverWait(Ad, Functions.maxTimeout).until(ExpectedConditions.elementToBeClickable(Ad.findElementById("com.weather.Weather:id/title")));	
			TestBase.waitForElementToBeClickable(Ad, 60, byAllLocations);
			// List<MobileElement>
			// allLocations=Ad.findElementsById("com.weather.Weather:id/title");
			List<MobileElement> allLocations = Ad.findElements(byAllLocations);
			Thread.sleep(6000);

			// allLocations.get(0).getText();
			// System.out.println(allLocations.size());

			for (int i = 0; i <= allLocations.size(); i++) {

				if (location.contains("Atlanta")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("Atlanta")) {
						Thread.sleep(6000);
						allLocations.get(i).click();
						Thread.sleep(6000);
						attachScreen();
						break;
					}
				} else if (location.contains("New York City")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("New York City")) {
						Thread.sleep(6000);
						allLocations.get(i).click();
						Thread.sleep(6000);
						attachScreen();
						break;
					}
				} else if (location.contains("10001")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("New York City")) {
						Thread.sleep(6000);
						allLocations.get(i).click();
						Thread.sleep(6000);
						attachScreen();
						break;
					}
				} else if (location.contains("10003")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("Manhattan")) {
						Thread.sleep(6000);
						allLocations.get(i).click();
						Thread.sleep(6000);
						attachScreen();
						break;
					}
				} else if (location.contains("30303")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("Atlanta")) {
						Thread.sleep(6000);
						allLocations.get(i).click();
						Thread.sleep(6000);
						attachScreen();
						break;
					}
				} else if (location.contains("07095")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("Woodbridge")) {
						Thread.sleep(6000);
						allLocations.get(i).click();
						Thread.sleep(6000);
						break;
					}
				} else if (location.contains("73645")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("Eric")) {
						Thread.sleep(6000);
						allLocations.get(i).click();
						Thread.sleep(6000);
						break;
					}
				} else if (location.contains("30124")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("Cave Spring")) {

						allLocations.get(i).click();
						Thread.sleep(3000);
						break;
					}
				} else if (location.contains("08824")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("Kendall Park")) {

						allLocations.get(i).click();
						Thread.sleep(3000);
						break;
					}
				} else if (location.contains("61920")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("Charleston")) {

						allLocations.get(i).click();
						Thread.sleep(3000);
						break;
					}
				} else if (location.contains("12758")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("Livingston Manor")) {

						allLocations.get(i).click();
						Thread.sleep(3000);
						break;
					}
				} else if (location.contains("31553")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("Nahunta")) {

						allLocations.get(i).click();
						Thread.sleep(3000);
						break;
					}
				} else if (location.contains("37421")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("Chattanooga")) {

						allLocations.get(i).click();
						Thread.sleep(3000);
						break;
					}
				} else if (location.contains("98902")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("Yakima")) {

						allLocations.get(i).click();
						Thread.sleep(3000);
						break;
					}
				}else if (location.contains("30096")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("Duluth")) {

						allLocations.get(i).click();
						Thread.sleep(3000);
						break;
					}
				}

			}
			System.out.println("Searching for location Ended");
			attachScreen();
		} catch (Exception ex) {
			attachScreen();
			ex.printStackTrace();
		}

	}
	
	public void enter_requiredLocation(String location, CharlesProxy proxy) throws Exception {
		try {
			System.out.println("Searching for location Started");
			// TestBase.waitForElementToBeClickable(Ad, 60, bySearch);
//			new WebDriverWait(Ad, Functions.maxTimeout).until(ExpectedConditions.elementToBeClickable(Ad.findElementByAccessibilityId("Search")));	
			search = Ad.findElement(bySearch);
			try {
				TestBase.clickOnElement(bySearch, search, "Search Icon");
				// Ad.findElementByAccessibilityId("Search").click();
			} catch (Exception e) {
				e.printStackTrace();
			}
			/*
			 * try{ TestBase.clickOnElement(bySearch, search, "Search Icon");
			 * }catch(Exception e) { e.printStackTrace(); }
			 */
//			new WebDriverWait(Ad, Functions.maxTimeout).until(ExpectedConditions.visibilityOfElementLocated(By.id("com.weather.Weather:id/search_text")));
			boolean found = false;
			while (!found) {

				if (TestBase.isElementExists(byLocationSearch, Ad)) {
					found = true;
					break;
				} else {
					TestBase.clickOnElement(bySearch, search, "Search Icon");
				}

			}

		/*	try {
				TestBase.waitForElementToBeClickable(Ad, 60, byLocationSearch);
			} catch (Exception e) {
				e.printStackTrace();
			}*/
			locationSearch = Ad.findElement(byLocationSearch);
//			Ad.findElementById("com.weather.Weather:id/search_text").sendKeys(location);
			TestBase.typeText(locationSearch, "Location Field", location);
			Thread.sleep(10000);
			By byAllLocations = MobileBy.id("com.weather.Weather:id/title");
//			new WebDriverWait(Ad, Functions.maxTimeout).until(ExpectedConditions.elementToBeClickable(Ad.findElementById("com.weather.Weather:id/title")));	
			TestBase.waitForElementToBeClickable(Ad, 60, byAllLocations);
			// List<MobileElement>
			// allLocations=Ad.findElementsById("com.weather.Weather:id/title");
			List<MobileElement> allLocations = Ad.findElements(byAllLocations);
			Thread.sleep(6000);

			// allLocations.get(0).getText();
			// System.out.println(allLocations.size());

			for (int i = 0; i <= allLocations.size(); i++) {
				proxy.clearCharlesSession();
				if (location.contains("Atlanta")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("Atlanta")) {
						Thread.sleep(6000);
						allLocations.get(i).click();
						Thread.sleep(6000);
						attachScreen();
						break;
					}
				} else if (location.contains("New York City")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("New York City")) {
						Thread.sleep(6000);
						allLocations.get(i).click();
						Thread.sleep(6000);
						attachScreen();
						break;
					}
				} else if (location.contains("10010")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("New York City")) {
						Thread.sleep(6000);
						allLocations.get(i).click();
						Thread.sleep(6000);
						attachScreen();
						break;
					}
				} else if (location.contains("10003")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("Manhattan")) {
						Thread.sleep(6000);
						allLocations.get(i).click();
						Thread.sleep(6000);
						attachScreen();
						break;
					}
				} else if (location.contains("30303")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("Atlanta")) {
						Thread.sleep(6000);
						allLocations.get(i).click();
						Thread.sleep(6000);
						attachScreen();
						break;
					}
				} else if (location.contains("07095")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("Woodbridge")) {
						Thread.sleep(6000);
						allLocations.get(i).click();
						Thread.sleep(6000);
						break;
					}
				} else if (location.contains("73645")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("Eric")) {
						Thread.sleep(6000);
						allLocations.get(i).click();
						Thread.sleep(6000);
						break;
					}
				} else if (location.contains("30124")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("Cave Spring")) {

						allLocations.get(i).click();
						Thread.sleep(3000);
						break;
					}
				} else if (location.contains("08824")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("Kendall Park")) {

						allLocations.get(i).click();
						Thread.sleep(3000);
						break;
					}
				} else if (location.contains("61920")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("Charleston")) {

						allLocations.get(i).click();
						Thread.sleep(3000);
						break;
					}
				} else if (location.contains("12758")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("Livingston Manor")) {

						allLocations.get(i).click();
						Thread.sleep(3000);
						break;
					}
				} else if (location.contains("31553")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("Nahunta")) {

						allLocations.get(i).click();
						Thread.sleep(3000);
						break;
					}
				} else if (location.contains("37421")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("Chattanooga")) {

						allLocations.get(i).click();
						Thread.sleep(3000);
						break;
					}
				} else if (location.contains("98902")) {
					// System.out.println(loc.getText());
					if (allLocations.get(i).getText().contains("Yakima")) {

						allLocations.get(i).click();
						Thread.sleep(3000);
						break;
					}
				}

			}
			System.out.println("Searching for location Ended");
		} catch (Exception ex) {
			attachScreen();
			ex.printStackTrace();
		}

	}

}

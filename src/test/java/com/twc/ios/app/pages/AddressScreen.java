package com.twc.ios.app.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.twc.ios.app.functions.Functions;
import com.twc.ios.app.general.Driver;
import com.twc.ios.app.general.ReadExcelValues;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.Utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;

public class AddressScreen extends Utils {
	AppiumDriver<MobileElement> Ad;
	
	String currentLocation_AccessibilityId = "currentLocation";
	String currentLocation2_AccessibilityId = "locationButton";
	String addCityOrZIPCode_AccessibilityId = "Add City or ZIP Code";
	String searchBar_AccessibilityId = "searchBar";
	String searchForYourLocation_Xpath = "//XCUIElementTypeButton[@name=\" Or search for your location\"]";
	String cancel_Name =  "Cancel";
	String done_Name =  "Done";
	String done_Xpath =  "//XCUIElementTypeButton[@name='Done']";
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
	By byCurrentLocation = MobileBy.AccessibilityId(currentLocation_AccessibilityId);
	By byCurrentLocation2 = MobileBy.AccessibilityId(currentLocation2_AccessibilityId);
	By byAddCityOrZIPCode = MobileBy.AccessibilityId(addCityOrZIPCode_AccessibilityId);
	By bySearchBar = MobileBy.AccessibilityId(searchBar_AccessibilityId);
	By byCancel = MobileBy.name(cancel_Name);
	By byDone = MobileBy.name(done_Name);
	By bySearch = MobileBy.name(search_Name);
	By byEyeglassGrey = MobileBy.xpath(eyeglassGrey_Xpath);
	By bySettingsButton = MobileBy.AccessibilityId(settingsButton_AccessibilityId);
	By byLocationSettingsDetailLabel = MobileBy.AccessibilityId(locationSettingsDetailLabel_AccessibilityId);
	By byLocationCell = MobileBy.xpath(locationCell_Xpath);
	By byNeverLocation = MobileBy.xpath(neverLocation_Xpath);
	By byReturnToTheWeather = MobileBy.AccessibilityId(returnToTheWeather_AccessibilityId);
	By byLabelCurrentLocation = MobileBy.id(labelCurrentLocation_Id);
	By byButtonLocationManagementClear = MobileBy.id(buttonLocationManagementClear_AccessibilityId);
	By byOK = MobileBy.id(oK_AccessibilityId);
	
	
	MobileElement searchForYourLocation = null;
	MobileElement cancel = null;
	MobileElement done = null;
	MobileElement search = null;
	MobileElement PresentAddress = null;
	MobileElement locationNameElement = null;
	MobileElement eyeglassGrey =  null;
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

	public AddressScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Select New Address Go With Default Location : {0} OR Select By Given Zip: {1} And Location Name: {2}")
	public void enternewAddress(boolean goWithDefaultLocation, String zip, String locationName) throws Exception {

		ReadExcelValues.excelValues("Smoke", "AddressPage");
		String PresentLocation = "Local Address";
		//WebElement PresentAddress = null;
		//MobileElement done = null;

		/*try {
			TestBase.waitForMilliSeconds(5000);
			searchForYourLocation = Ad.findElement(bySearchForYourLocation);
			//Ad.findElementByXPath("//XCUIElementTypeButton[@name=\" Or search for your location\"]").click();
			TestBase.clickOnElement(bySearchForYourLocation, searchForYourLocation, "Search For Your Location Link");
			
		} catch (Exception e) {
			System.out.println("Or search for your location button not available on the screen");
			logStep("Or search for your location button not available on the screen");
		}*/
		try {
			//PresentAddress = Ad.findElementByAccessibilityId("currentLocation");
			PresentAddress = Ad.findElement(byCurrentLocation);
			PresentLocation = PresentAddress.getAttribute("label");
			PresentLocation = PresentLocation.split(":")[1].trim();
			System.out.println("Current displayed location: " + PresentLocation);
			logStep("Current displayed location: " + PresentLocation);

		} catch (Exception e) {
			// PrsentLocation ="Local Address";
			System.out.println("PresentLocation =" + PresentLocation);
			logStep("PresentLocation =" + PresentLocation);
		}
		try {
			if (goWithDefaultLocation && !PresentLocation.equalsIgnoreCase("Local Address")) {
				System.out.println("User on default location: " + PresentLocation);
				logStep("User on default location: " + PresentLocation);
			} else if (PresentLocation.equals(locationName)) {
				System.out.println("User on expected location --" + locationName);
				logStep("User on expected location --" + locationName);
			} else {
				try {
					TestBase.waitForMilliSeconds(5000);
					//TempEle = Ad.findElementByAccessibilityId("Add City or ZIP Code");
					TempEle = Ad.findElement(byAddCityOrZIPCode);
					if (!TempEle.isDisplayed()) {
						Ad.findElementByXPath(ReadExcelValues.data[1][Cap]).click();
						TestBase.waitForMilliSeconds(5000);
						// TempEle = (MobileElement)
						// Ad.findElementByClassName(readExcelValues.data[9][Cap]);
						//TempEle = Ad.findElementByAccessibilityId("Add City or ZIP Code");
						TempEle = Ad.findElement(byAddCityOrZIPCode);
					} else {
						System.out.println("User on Address select page");
						logStep("User on Address select page");
					}
				} catch (Exception e) {
					try {
						//PresentAddress.click();
						TestBase.clickOnElement(byCurrentLocation, PresentAddress, "Current Location");
						System.out.println("Clicked on Present Address");
						logStep("Clicked on Present Address");
						TestBase.waitForMilliSeconds(5000);
					} catch (Exception e1) {
						try {
							PresentAddress = Ad.findElement(byCurrentLocation2);
							TestBase.clickOnElement(byCurrentLocation2, PresentAddress, "Current Location");
							System.out.println("Clicked on Present Address");
							logStep("Clicked on Present Address");
							TestBase.waitForMilliSeconds(5000);
						} catch (Exception e2) {
							System.out.println("There is an exception when clicked on Present Address");
							logStep("There is an exception when clicked on Present Address");
							// Ad.tap(1, 164, 42, 2000);//commented on 23 oct 2020
						}
					}
					try {
						TestBase.waitForMilliSeconds(4000);
						//TempEle = Ad.findElementByAccessibilityId("Add City or ZIP Code");
						TempEle = Ad.findElement(byAddCityOrZIPCode);
						System.out.println("TempEle is identified in try block after Present Address is clicked");
						logStep("TempEle is identified in try block after Present Address is clicked");
					} catch (Exception e1) {
						System.out.println(
								"TempEle is failed to identifed in try block after Present Address is clicked");
						logStep("TempEle is failed to identified in try block after Present Address is clicked");
						//TempEle = Ad.findElementByAccessibilityId("searchBar");
						TempEle = Ad.findElement(bySearchBar);
						System.out.println("TempEle is identified in catch block after Present Address is clicked");
						logStep("TempEle is identified in catch block after Present Address is clicked");
					}

				}
				try {
					TempEle.clear();
					System.out.println("Cleared the Address input field");
					logStep("Cleared the Address input field");
				} catch (Exception e) {
					System.out.println("There is an exception while clearing the Address input field");
					logStep("There is an exception while clearing the Address input field");
				}
				try {
					//TempEle.click();
					//TempEle.click();
					TestBase.clickOnElement(byAddCityOrZIPCode, TempEle, "Add City Or Zip Code");
					TestBase.clickOnElement(byAddCityOrZIPCode, TempEle, "Add City Or Zip Code");
					System.out.println("Clicked on Address input field");
					logStep("Clicked on Address input field");
				} catch (Exception e) {
					System.out.println("There is an exception while clicking on Address input field");
					logStep("There is an exception while clicking on Address input field");
				}

				TestBase.waitForMilliSeconds(4000);
				// TempEle.clear();
				try {
					//TempEle.sendKeys(zip);
					TestBase.typeText(TempEle, "Add City Or Zip Code", zip);
					System.out.println("entered Address in input field");
					logStep("entered Address in input field");
				} catch (Exception e) {
					System.out.println("There is an exception while entering Address in input field");
					logStep("There is an exception while entering Address in input field");
					try {
						
						/*if (Ad.findElementByName("Cancel").isDisplayed()) {
							Ad.findElementByName("Cancel").click();
						}*/
						if (Ad.findElement(byCancel).isDisplayed()) {
							//Ad.findElement(byCancel).click();
							cancel = Ad.findElement(byCancel);
							TestBase.clickOnElement(byCancel, cancel, "Cancel Button");
						}
						try {
							done = Ad.findElement(byDone);
							//Ad.findElementByName("Done").click();
							TestBase.clickOnElement(byDone, done, "Done Button");
						} catch (Exception exe) {
							try {
								By byDone = MobileBy.xpath(done_Xpath);
								done = Ad.findElement(byDone);
								//Ad.findElementByXPath("//XCUIElementTypeButton[@name='Done']").click();
								TestBase.clickOnElement(byDone, done, "Done Button");
							} catch (Exception exe1) {
								System.out.println("Done Element not displayed");
								logStep("Done Element not displayed");
							}
						}
						try {
							//PresentAddress.click();
							TestBase.clickOnElement(byCurrentLocation, PresentAddress, "Current Location");
							System.out.println("Clicked on Present Address");
							logStep("Clicked on Present Address");
						} catch (Exception e1) {
							System.out.println("There is an exception when clicked on Present Address");
							logStep("There is an exception when clicked on Present Address");
						}
						try {
							//TempEle = Ad.findElementByAccessibilityId("Add City or ZIP Code");
							TempEle = Ad.findElement(byAddCityOrZIPCode);
						} catch (Exception e1) {
							System.out.println("Failed to identify address input field");
							logStep("Failed to identify address input field");
						}

						try {
							TempEle.clear();
							System.out.println("Cleared the Address input field");
							logStep("Cleared the Address input field");
						} catch (Exception e1) {
							System.out.println("There is an exception while clearing the Address input field");
							logStep("There is an exception while clearing the Address input field");
						}
						try {
							//TempEle.click();
							//TempEle.click();
							TestBase.clickOnElement(byAddCityOrZIPCode, TempEle, "Add City Or Zip Code");
							TestBase.clickOnElement(byAddCityOrZIPCode, TempEle, "Add City Or Zip Code");
							System.out.println("Clicked on Address input field");
							logStep("Clicked on Address input field");
						} catch (Exception e2) {
							System.out.println("There is an exception while clicking on Address input field");
							logStep("There is an exception while clicking on Address input field");
						}
						try {
							//TempEle.sendKeys(zip);
							TestBase.typeText(TempEle, "Add City Or Zip Code", zip);
						} catch (Exception e1) {
							System.out.println(
									"There is an exception while entering Address in input field of catch block");
							logStep("There is an exception while entering Address in input field of catch block");
						}

					} catch (Exception ex) {
						/*if (Ad.findElementByName("Done").isDisplayed()) {
							Ad.findElementByName("Done").click();
						}*/
						if (Ad.findElement(byDone).isDisplayed()) {
							done = Ad.findElement(byDone);
							TestBase.clickOnElement(byDone, done, "Done Button");
						}
						try {
							//PresentAddress.click();
							TestBase.clickOnElement(byCurrentLocation, PresentAddress, "Current Location");
							System.out.println("Clicked on Present Address");
							logStep("Clicked on Present Address");
						} catch (Exception e1) {
							System.out.println("There is an exception when clicked on Present Address");
							logStep("There is an exception when clicked on Present Address");
						}
						try {
							//TempEle = Ad.findElementByAccessibilityId("Add City or ZIP Code");
							TempEle = Ad.findElement(byAddCityOrZIPCode);
						} catch (Exception e1) {
							System.out.println("Failed to identify address input field");
							logStep("Failed to identify address input field");
						}

						try {
							TempEle.clear();
							System.out.println("Cleared the Address input field");
							logStep("Cleared the Address input field");
						} catch (Exception e1) {
							System.out.println("There is an exception while clearing the Address input field");
							logStep("There is an exception while clearing the Address input field");
						}
						try {
							//TempEle.click();
							//TempEle.click();
							TestBase.clickOnElement(byAddCityOrZIPCode, TempEle, "Add City Or Zip Code");
							TestBase.clickOnElement(byAddCityOrZIPCode, TempEle, "Add City Or Zip Code");
							System.out.println("Clicked on Address input field");
							logStep("Clicked on Address input field");
						} catch (Exception e2) {
							System.out.println("There is an exception while clicking on Address input field");
							logStep("There is an exception while clicking on Address input field");
						}
						try {
							//TempEle.sendKeys(zip);
							TestBase.typeText(TempEle, "Add City Or Zip Code", zip);
						} catch (Exception e1) {
							System.out.println(
									"There is an exception while entering Address in input field of catch block");
							logStep("There is an exception while entering Address in input field of catch block");
						}
					}

				}

				TestBase.waitForMilliSeconds(3000);
				try {
					//Ad.findElementByName("Search").click();
					search = Ad.findElement(bySearch);
					TestBase.clickOnElement(bySearch, search, "Search Button");
				} catch (Exception e) {
					System.out.println("There is an exception while clicking Search icon after input address");
					logStep("There is an exception while clicking Search icon after input address");
				}
				TestBase.waitForMilliSeconds(5000);
				try {
					// select first name in the list
					//Ad.findElementByName(locationName).click();
					By byLocationName = MobileBy.name(locationName);
					locationNameElement = Ad.findElement(byLocationName);
					TestBase.waitForMilliSeconds(15000);
					TestBase.clickOnElement(byLocationName, locationNameElement, "Location Name");
					System.out.println(locationName + " address Selected");
					logStep(locationName + " address Selected");
				} catch (Exception e) {
					try {
						TempEle.clear();
						//TempEle.click();
						//TempEle.click();
						TestBase.clickOnElement(byAddCityOrZIPCode, TempEle, "Add City Or Zip Code");
						TestBase.clickOnElement(byAddCityOrZIPCode, TempEle, "Add City Or Zip Code");
						//TempEle.sendKeys(zip);
						TestBase.typeText(TempEle, "Add City Or Zip Code", zip);
						//Ad.findElementByName("Search").click();
						search = Ad.findElement(bySearch);
						TestBase.clickOnElement(bySearch, search, "Search Button");
						//Ad.findElementByName(zip).click();
						By byLocationName = MobileBy.name(zip);
						locationNameElement = Ad.findElement(byLocationName);
						TestBase.clickOnElement(byLocationName, locationNameElement, "Location Name");
						System.out.println(zip + " address Selected");
						logStep(zip + " address Selected");
					} catch(Exception e1) {

						try {
							done = Ad.findElement(byDone);
							//Ad.findElementByName("Done").click();
							TestBase.clickOnElement(byDone, done, "Done Button");
						} catch (Exception exe) {
							try {
								By byDone = MobileBy.xpath(done_Xpath);
								done = Ad.findElement(byDone);
								//Ad.findElementByXPath("//XCUIElementTypeButton[@name='Done']").click();
								TestBase.clickOnElement(byDone, done, "Done Button");
							} catch (Exception exe1) {
								System.out.println("Done Element not displayed");
								logStep("Done Element not displayed");
							}
						}
												
						try {
							//PresentAddress.click();
							TestBase.clickOnElement(byCurrentLocation, PresentAddress, "Current Location");
							System.out.println("Clicked on Present Address");
							logStep("Clicked on Present Address");
						} catch (Exception e2) {
							System.out.println("There is an exception when clicked on Present Address");
							logStep("There is an exception when clicked on Present Address");
						}
						try {
							//TempEle = Ad.findElementByAccessibilityId("Add City or ZIP Code");
							TempEle = Ad.findElement(byAddCityOrZIPCode);
						} catch (Exception e2) {
							System.out.println("Failed to identify address input field");
							logStep("Failed to identify address input field");
						}

						try {
							TempEle.clear();
							System.out.println("Cleared the Address input field");
							logStep("Cleared the Address input field");
						} catch (Exception e2) {
							System.out.println("There is an exception while clearing the Address input field");
							logStep("There is an exception while clearing the Address input field");
						}
						try {
							//TempEle.click();
							//TempEle.click();
							TestBase.clickOnElement(byAddCityOrZIPCode, TempEle, "Add City Or Zip Code");
							TestBase.clickOnElement(byAddCityOrZIPCode, TempEle, "Add City Or Zip Code");
							System.out.println("Clicked on Address input field");
							logStep("Clicked on Address input field");
						} catch (Exception e2) {
							System.out.println("There is an exception while clicking on Address input field");
							logStep("There is an exception while clicking on Address input field");
						}
						try {
							//TempEle.sendKeys(zip);
							TestBase.typeText(TempEle, "Add City Or Zip Code", zip);
						} catch (Exception e2) {
							System.out.println(
									"There is an exception while entering Address in input field of catch block");
							logStep("There is an exception while entering Address in input field of catch block");
						}
						
						try {
							//Ad.findElementByName("Search").click();
							search = Ad.findElement(bySearch);
							TestBase.clickOnElement(bySearch, search, "Search Button");
						} catch (Exception e2) {
							System.out.println("There is an exception while clicking Search icon after input address");
							logStep("There is an exception while clicking Search icon after input address");
						}
						TestBase.waitForMilliSeconds(5000);
						
						try {
							// select first name in the list
							//Ad.findElementByName(zip).click();
							By byLocationName = MobileBy.name(zip);
							locationNameElement = Ad.findElement(byLocationName);
							TestBase.clickOnElement(byLocationName, locationNameElement, "Location Name");
							System.out.println(zip + " address Selected");
							logStep(zip + " address Selected");
						} catch (Exception e2) {
							System.out.println("There is an exception while selecting desired location from list");
							logStep("There is an exception while selecting desired location from list");
							TempEle.clear();
							//TempEle.click();
							//TempEle.click();
							TestBase.clickOnElement(byAddCityOrZIPCode, TempEle, "Add City Or Zip Code");
							TestBase.clickOnElement(byAddCityOrZIPCode, TempEle, "Add City Or Zip Code");
							//TempEle.sendKeys(zip);
							TestBase.typeText(TempEle, "Add City Or Zip Code", zip);
							//Ad.findElementByName("Search").click();
							search = Ad.findElement(bySearch);
							TestBase.clickOnElement(bySearch, search, "Search Button");
							//Ad.findElementByName(zip).click();
							By byLocationName = MobileBy.name(zip);
							locationNameElement = Ad.findElement(byLocationName);
							TestBase.clickOnElement(byLocationName, locationNameElement, "Location Name");
							System.out.println(zip + " address Selected");
							logStep(zip + " address Selected");
						}
					
					}
				}
				TestBase.waitForMilliSeconds(15000);
				attachScreen();
			}
		} catch (Exception e) {
			attachScreen();
			System.out.println("There is an exception in the process of address input");
			logStep("There is an exception in the process of address input");

		}

	}

	@Step("Select New Address Go With Default Location : {0} OR Select By Given Zip: {1}")
	public void enternewAddress(boolean goWithDefaultLocation, String zip) throws Exception {

		ReadExcelValues.excelValues("Smoke", "AddressPage");
		String PresentLocation = "Local Address";
		//WebElement PresentAddress = null;
		MobileElement done = null;

	/*	try {
			TestBase.waitForMilliSeconds(5000);
			//Ad.findElementByXPath("//XCUIElementTypeButton[@name=\" Or search for your location\"]").click();
			searchForYourLocation = Ad.findElement(bySearchForYourLocation);
			TestBase.clickOnElement(bySearchForYourLocation, searchForYourLocation, "Search For Your Location Link");
			
		} catch (Exception e) {
			System.out.println("Or search for your location button not available on the screen");
			logStep("Or search for your location button not available on the screen");
		}*/
		try {
			//PresentAddress = Ad.findElementByAccessibilityId("currentLocation");
			PresentAddress = Ad.findElement(byCurrentLocation);
			PresentLocation = PresentAddress.getAttribute("label");
			PresentLocation = PresentLocation.split(":")[1].trim();
			System.out.println("Current displayed location: " + PresentLocation);
			logStep("Current displayed location: " + PresentLocation);

		} catch (Exception e) {
			// PrsentLocation ="Local Address";
			System.out.println("PresentLocation =" + PresentLocation);
			logStep("PresentLocation =" + PresentLocation);
		}
		try {
			if (goWithDefaultLocation && !PresentLocation.equalsIgnoreCase("Local Address")) {
				System.out.println("User on default location: " + PresentLocation);
				logStep("User on default location: " + PresentLocation);
			} else if (PresentLocation.equals(zip)) {
				System.out.println("User on expected location --" + zip);
				logStep("User on expected location --" + zip);
			} else {
				try {
					TestBase.waitForMilliSeconds(5000);
					//TempEle = Ad.findElementByAccessibilityId("Add City or ZIP Code");
					TempEle = Ad.findElement(byAddCityOrZIPCode);
					if (!TempEle.isDisplayed()) {
						Ad.findElementByXPath(ReadExcelValues.data[1][Cap]).click();
						TestBase.waitForMilliSeconds(5000);
						//TempEle = Ad.findElementByAccessibilityId("Add City or ZIP Code");
						TempEle = Ad.findElement(byAddCityOrZIPCode);
					} else {
						System.out.println("User on Address select page");
						logStep("User on Address select page");
					}
				} catch (Exception e) {
					try {
						//PresentAddress.click();
						TestBase.clickOnElement(byCurrentLocation, PresentAddress, "Current Location");
						System.out.println("Clicked on Present Address");
						logStep("Clicked on Present Address");
						TestBase.waitForMilliSeconds(5000);
					} catch (Exception e1) {
						try {
							PresentAddress = Ad.findElement(byCurrentLocation2);
							TestBase.clickOnElement(byCurrentLocation2, PresentAddress, "Current Location");
							System.out.println("Clicked on Present Address");
							logStep("Clicked on Present Address");
							TestBase.waitForMilliSeconds(5000);
						} catch (Exception e2) {
							System.out.println("There is an exception when clicked on Present Address");
							logStep("There is an exception when clicked on Present Address");
							// Ad.tap(1, 164, 42, 2000);//commented on 23 oct 2020
						}
					}
					try {
						TestBase.waitForMilliSeconds(4000);
						//TempEle = Ad.findElementByAccessibilityId("Add City or ZIP Code");
						TempEle = Ad.findElement(byAddCityOrZIPCode);
						System.out.println("TempEle is identified in try block after Present Address is clicked");
						logStep("TempEle is identified in try block after Present Address is clicked");
					} catch (Exception e1) {
						System.out.println(
								"TempEle is failed to identifed in try block after Present Address is clicked");
						logStep("TempEle is failed to identified in try block after Present Address is clicked");
						//TempEle = Ad.findElementByAccessibilityId("searchBar");
						TempEle = Ad.findElement(bySearchBar);
						System.out.println("TempEle is identified in catch block after Present Address is clicked");
						logStep("TempEle is identified in catch block after Present Address is clicked");
					}

				}
				try {
					TempEle.clear();
					System.out.println("Cleared the Address input field");
					logStep("Cleared the Address input field");
				} catch (Exception e) {
					System.out.println("There is an exception while clearing the Address input field");
					logStep("There is an exception while clearing the Address input field");
				}
				try {
					//TempEle.click();
					//TempEle.click();
					TestBase.clickOnElement(byAddCityOrZIPCode, TempEle, "Add City Or Zip Code");
					TestBase.clickOnElement(byAddCityOrZIPCode, TempEle, "Add City Or Zip Code");
					System.out.println("Clicked on Address input field");
					logStep("Clicked on Address input field");
				} catch (Exception e) {
					System.out.println("There is an exception while clicking on Address input field");
					logStep("There is an exception while clicking on Address input field");
				}

				TestBase.waitForMilliSeconds(4000);
				// TempEle.clear();
				try {
					//TempEle.sendKeys(zip);
					TestBase.typeText(TempEle, "Add City Or Zip Code", zip);
					System.out.println("entered Address in input field");
					logStep("entered Address in input field");
				} catch (Exception e) {
					System.out.println("There is an exception while entering Address in input field");
					logStep("There is an exception while entering Address in input field");
					try {

						/*if (Ad.findElementByName("Cancel").isDisplayed()) {
							Ad.findElementByName("Cancel").click();
						}*/
						if (Ad.findElement(byCancel).isDisplayed()) {
							//Ad.findElement(byCancel).click();
							cancel = Ad.findElement(byCancel);
							TestBase.clickOnElement(byCancel, cancel, "Cancel Button");
						}
						
						try {
							done = Ad.findElement(byDone);
							//Ad.findElementByName("Done").click();
							TestBase.clickOnElement(byDone, done, "Done Button");
						} catch (Exception exe) {
							try {
								By byDone = MobileBy.xpath(done_Xpath);
								done = Ad.findElement(byDone);
								//Ad.findElementByXPath("//XCUIElementTypeButton[@name='Done']").click();
								TestBase.clickOnElement(byDone, done, "Done Button");
							} catch (Exception exe1) {
								System.out.println("Done Element not displayed");
								logStep("Done Element not displayed");
							}
						}
						try {
							//PresentAddress.click();
							TestBase.clickOnElement(byCurrentLocation, PresentAddress, "Current Location");
							System.out.println("Clicked on Present Address");
							logStep("Clicked on Present Address");
						} catch (Exception e1) {
							System.out.println("There is an exception when clicked on Present Address");
							logStep("There is an exception when clicked on Present Address");
						}
						try {
							//TempEle = Ad.findElementByAccessibilityId("Add City or ZIP Code");
							TempEle = Ad.findElement(byAddCityOrZIPCode);
						} catch (Exception e1) {
							System.out.println("Failed to identify address input field");
							logStep("Failed to identify address input field");
						}

						try {
							TempEle.clear();
							System.out.println("Cleared the Address input field");
							logStep("Cleared the Address input field");
						} catch (Exception e1) {
							System.out.println("There is an exception while clearing the Address input field");
							logStep("There is an exception while clearing the Address input field");
						}
						try {
							//TempEle.click();
							//TempEle.click();
							TestBase.clickOnElement(byAddCityOrZIPCode, TempEle, "Add City Or Zip Code");
							TestBase.clickOnElement(byAddCityOrZIPCode, TempEle, "Add City Or Zip Code");
							System.out.println("Clicked on Address input field");
							logStep("Clicked on Address input field");
						} catch (Exception e2) {
							System.out.println("There is an exception while clicking on Address input field");
							logStep("There is an exception while clicking on Address input field");
						}
						try {
							//TempEle.sendKeys(zip);
							TestBase.typeText(TempEle, "Add City Or Zip Code", zip);
						} catch (Exception e1) {
							System.out.println(
									"There is an exception while entering Address in input field of catch block");
							logStep("There is an exception while entering Address in input field of catch block");
						}

					} catch (Exception ex) {
						/*if (Ad.findElementByName("Done").isDisplayed()) {
						Ad.findElementByName("Done").click();
						}*/
						if (Ad.findElement(byDone).isDisplayed()) {
							done = Ad.findElement(byDone);
							TestBase.clickOnElement(byDone, done, "Done Button");
						}
						
						try {
							//PresentAddress.click();
							TestBase.clickOnElement(byCurrentLocation, PresentAddress, "Current Location");
							System.out.println("Clicked on Present Address");
							logStep("Clicked on Present Address");
						} catch (Exception e1) {
							System.out.println("There is an exception when clicked on Present Address");
							logStep("There is an exception when clicked on Present Address");
						}
						try {
							//TempEle = Ad.findElementByAccessibilityId("Add City or ZIP Code");
							TempEle = Ad.findElement(byAddCityOrZIPCode);
						} catch (Exception e1) {
							System.out.println("Failed to identify address input field");
							logStep("Failed to identify address input field");
						}

						try {
							TempEle.clear();
							System.out.println("Cleared the Address input field");
							logStep("Cleared the Address input field");
						} catch (Exception e1) {
							System.out.println("There is an exception while clearing the Address input field");
							logStep("There is an exception while clearing the Address input field");
						}
						try {
							//TempEle.click();
							//TempEle.click();
							TestBase.clickOnElement(byAddCityOrZIPCode, TempEle, "Add City Or Zip Code");
							TestBase.clickOnElement(byAddCityOrZIPCode, TempEle, "Add City Or Zip Code");
							System.out.println("Clicked on Address input field");
							logStep("Clicked on Address input field");
						} catch (Exception e2) {
							System.out.println("There is an exception while clicking on Address input field");
							logStep("There is an exception while clicking on Address input field");
						}
						try {
							//TempEle.sendKeys(zip);
							TestBase.typeText(TempEle, "Add City Or Zip Code", zip);
						} catch (Exception e1) {
							System.out.println(
									"There is an exception while entering Address in input field of catch block");
							logStep("There is an exception while entering Address in input field of catch block");
						}
					}

				}

				TestBase.waitForMilliSeconds(3000);
				try {
					//Ad.findElementByName("Search").click();
					search = Ad.findElement(bySearch);
					TestBase.clickOnElement(bySearch, search, "Search Button");
				} catch (Exception e) {
					System.out.println("There is an exception while clicking Search icon after input address");
					logStep("There is an exception while clicking Search icon after input address");
				}
				TestBase.waitForMilliSeconds(5000);
				try {
					// select first name in the list
					//Ad.findElementByName(zip).click();
					By byLocationName = MobileBy.name(zip);
					locationNameElement = Ad.findElement(byLocationName);
					TestBase.clickOnElement(byLocationName, locationNameElement, "Location Name");
					System.out.println(zip + " address Selected");
					logStep(zip + " address Selected");
				} catch (Exception e) {
					try {
						TempEle.clear();
						//TempEle.click();
						//TempEle.click();
						TestBase.clickOnElement(byAddCityOrZIPCode, TempEle, "Add City Or Zip Code");
						TestBase.clickOnElement(byAddCityOrZIPCode, TempEle, "Add City Or Zip Code");
						//TempEle.sendKeys(zip);
						TestBase.typeText(TempEle, "Add City Or Zip Code", zip);
						//Ad.findElementByName("Search").click();
						search = Ad.findElement(bySearch);
						TestBase.clickOnElement(bySearch, search, "Search Button");
						//Ad.findElementByName(zip).click();
						By byLocationName = MobileBy.name(zip);
						locationNameElement = Ad.findElement(byLocationName);
						TestBase.clickOnElement(byLocationName, locationNameElement, "Location Name");
						System.out.println(zip + " address Selected");
						logStep(zip + " address Selected");
					} catch(Exception e1) {

						try {
							done = Ad.findElement(byDone);
							//Ad.findElementByName("Done").click();
							TestBase.clickOnElement(byDone, done, "Done Button");
						} catch (Exception exe) {
							try {
								By byDone = MobileBy.xpath(done_Xpath);
								done = Ad.findElement(byDone);
								//Ad.findElementByXPath("//XCUIElementTypeButton[@name='Done']").click();
								TestBase.clickOnElement(byDone, done, "Done Button");
							} catch (Exception exe1) {
								System.out.println("Done Element not displayed");
								logStep("Done Element not displayed");
							}
						}
												
						try {
							//PresentAddress.click();
							TestBase.clickOnElement(byCurrentLocation, PresentAddress, "Current Location");
							System.out.println("Clicked on Present Address");
							logStep("Clicked on Present Address");
						} catch (Exception e2) {
							try {
								PresentAddress = Ad.findElement(byCurrentLocation2);
								TestBase.clickOnElement(byCurrentLocation2, PresentAddress, "Current Location");
								System.out.println("Clicked on Present Address");
								logStep("Clicked on Present Address");
								TestBase.waitForMilliSeconds(5000);
							} catch (Exception e3) {
								System.out.println("There is an exception when clicked on Present Address");
								logStep("There is an exception when clicked on Present Address");
								// Ad.tap(1, 164, 42, 2000);//commented on 23 oct 2020
							}
						}
						try {
							//TempEle = Ad.findElementByAccessibilityId("Add City or ZIP Code");
							TempEle = Ad.findElement(byAddCityOrZIPCode);
						} catch (Exception e2) {
							System.out.println("Failed to identify address input field");
							logStep("Failed to identify address input field");
						}

						try {
							TempEle.clear();
							System.out.println("Cleared the Address input field");
							logStep("Cleared the Address input field");
						} catch (Exception e2) {
							System.out.println("There is an exception while clearing the Address input field");
							logStep("There is an exception while clearing the Address input field");
						}
						try {
							//TempEle.click();
							//TempEle.click();
							TestBase.clickOnElement(byAddCityOrZIPCode, TempEle, "Add City Or Zip Code");
							TestBase.clickOnElement(byAddCityOrZIPCode, TempEle, "Add City Or Zip Code");
							System.out.println("Clicked on Address input field");
							logStep("Clicked on Address input field");
						} catch (Exception e2) {
							System.out.println("There is an exception while clicking on Address input field");
							logStep("There is an exception while clicking on Address input field");
						}
						try {
							//TempEle.sendKeys(zip);
							TestBase.typeText(TempEle, "Add City Or Zip Code", zip);
						} catch (Exception e2) {
							System.out.println(
									"There is an exception while entering Address in input field of catch block");
							logStep("There is an exception while entering Address in input field of catch block");
						}
						
						try {
							//Ad.findElementByName("Search").click();
							search = Ad.findElement(bySearch);
							TestBase.clickOnElement(bySearch, search, "Search Button");
						} catch (Exception e2) {
							System.out.println("There is an exception while clicking Search icon after input address in catch block");
							logStep("There is an exception while clicking Search icon after input address in catch block");
						}
						TestBase.waitForMilliSeconds(5000);
						
						try {
							// select first name in the list
							//Ad.findElementByName(zip).click();
							By byLocationName = MobileBy.name(zip);
							locationNameElement = Ad.findElement(byLocationName);
							TestBase.clickOnElement(byLocationName, locationNameElement, "Location Name");
							System.out.println(zip + " address Selected");
							logStep(zip + " address Selected");
						} catch (Exception e2) {
							System.out.println("There is an exception while selecting desired location from list");
							logStep("There is an exception while selecting desired location from list");
							TempEle.clear();
							//TempEle.click();
							//TempEle.click();
							TestBase.clickOnElement(byAddCityOrZIPCode, TempEle, "Add City Or Zip Code");
							TestBase.clickOnElement(byAddCityOrZIPCode, TempEle, "Add City Or Zip Code");
							//TempEle.sendKeys(zip);
							TestBase.typeText(TempEle, "Add City Or Zip Code", zip);
							//Ad.findElementByName("Search").click();
							search = Ad.findElement(bySearch);
							TestBase.clickOnElement(bySearch, search, "Search Button");
							//Ad.findElementByName(zip).click();
							By byLocationName = MobileBy.name(zip);
							locationNameElement = Ad.findElement(byLocationName);
							TestBase.clickOnElement(byLocationName, locationNameElement, "Location Name");
							System.out.println(zip + " address Selected");
							logStep(zip + " address Selected");
						}
					
					}
				}

				TestBase.waitForMilliSeconds(10000);
				attachScreen();
			}
		} catch (Exception e) {
			e.printStackTrace();
			attachScreen();
			System.out.println("There is an exception in the process of address input");
			logStep("There is an exception in the process of address input");

		}

	}

	@Step("NavigatetoSettingPage or manage locations")
	public void navigatetoAddressPage() throws Exception {
		ReadExcelValues.excelValues("Smoke", "AddressPage");
		MobileElement addressPage = null;
		TestBase.waitForMilliSeconds(5000);
		try {
			addressPage = (MobileElement) Ad.findElementByName(ReadExcelValues.data[1][Cap]);
			logStep("Naviagate to Address Page");
		} catch (Exception e) {
			//addressPage = (MobileElement) Ad.findElementByXPath("//XCUIElementTypeButton[@name='eyeglassGrey']");
			addressPage = Ad.findElement(byEyeglassGrey);
			logStep("Naviagate to Address Page");
		}
		addressPage.click();
		MobileElement Address = null;
		// try{
		// Address =(MobileElement) Ad.findElementByXPath(readExcelValues.data[2][Cap]);
		// //XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeTable[1]/XCUIElementTypeCell[2]
		//
		// }catch(Exception e){
		// Address =(MobileElement)
		// Ad.findElementByXPath("//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeTable[1]/XCUIElementTypeCell[2]");
		//
		// }
		clearAddedAddresses();
		Addresseslist = Ad.findElementsByClassName(ReadExcelValues.data[3][Cap]);
	}

	@Step("Disbale Location Services")
	public void disableLocationServices() throws Exception {
		String Locationname = "Atlanta, Georgia";
		enternewAddress(false, Locationname.toString());
		ReadExcelValues.excelValues("Smoke", "TestMode");
		try {
			Ad.findElementByName(ReadExcelValues.data[1][Cap]).click();
			logStep("Navigate to Settings page");
		} catch (Exception e) {
			//Ad.findElementByXPath("//XCUIElementTypeButton[@name='topNav settings']").click();
			settingsButton = Ad.findElement(bySettingsButton);
			TestBase.clickOnElement(bySettingsButton, settingsButton, "Settings Button");
			logStep("Navigate to Settings page");
		}
		TestBase.waitForMilliSeconds(1000);
		//Ad.findElementByAccessibilityId("location_settings_detail_label").click();
		locationSettingsDetailLabel = Ad.findElement(byLocationSettingsDetailLabel);
		TestBase.clickOnElement(byLocationSettingsDetailLabel, locationSettingsDetailLabel, "Location Settings Button");
		logStep("Clicked on locations settings");
		//Ad.findElementByXPath("//XCUIElementTypeCell[@name='Location']").click();
		locationCell = Ad.findElement(byLocationCell);
		TestBase.clickOnElement(byLocationCell, locationCell, "Location Cell Button");
		//Ad.findElementByXPath("//XCUIElementTypeCell[@name='Never']").click();
		neverLocation = Ad.findElement(byNeverLocation);
		TestBase.clickOnElement(byNeverLocation, neverLocation, "Never Button");
		logStep("Disabled location services in device settings");
		//Ad.findElementByAccessibilityId("Return to The Weather").click();
		returnToTheWeather = Ad.findElement(byReturnToTheWeather);
		TestBase.clickOnElement(byReturnToTheWeather, returnToTheWeather, "Return To Weather Button");
		logStep("Clicked on Back to weather option");
		//String PrsentLocation = Ad.findElementById("label_currentLocation").getText();
		labelCurrentLocation = Ad.findElement(byLabelCurrentLocation);
		String PrsentLocation = TestBase.getElementText(labelCurrentLocation);
		if (Locationname.toString().equals(PrsentLocation.toString())) {
			System.out.println("Locations are matched : " + Locationname + "----" + PrsentLocation);
			logStep("location are matched with previous address " + Locationname + "----" + PrsentLocation);
		} else {
			System.out.println("Locations are not matched : " + Locationname + "----" + PrsentLocation);
			logStep("location are not  matched with previous address " + Locationname + "----" + PrsentLocation);
		}

	}

	@Step("Verify saved address list")
	public void verifysavedAddresses() throws Exception {
		ReadExcelValues.excelValues("Smoke", "AddressPage");
		int savedlistcount = Addresseslist.size() - 2;
		System.out.println("Saved address list count is :" + savedlistcount);
		if (Addresseslist.size() > 1) {
			logStep("Saved address are found");
			for (int addresslist = 1; addresslist <= Addresseslist.size() - 2; addresslist++) {
				int Count = addresslist + 1;
				// int TempEle = addresslist+1;
				// String xyz = readExcelValues.data[4][Cap];
				// //System.out.println("String xyz is "+xyz);
				// String[] AddrestSplit =xyz.split("Count");
				// xyz=AddrestSplit[0]+Count+AddrestSplit[1];

				SelectAddress = (MobileElement) Ad.findElementByXPath(
						"(//XCUIElementTypeStaticText[@name='label_locationManagementFavoriteLocName'])[" + addresslist
								+ "]");
				// readExcelValues.data[4][Cap]);
				String SavedAddress = SelectAddress.getText();
				System.out.println("Saved Address is  - " + SavedAddress);

			}
		}

	}

	@Step("Clear added addresses")
	public void clearAddedAddresses() {
		try {
			//MobileElement el1 = (MobileElement) Ad.findElementByAccessibilityId("button_locationManagementClear");
			//el1.click();
			buttonLocationManagementClear = Ad.findElement(byButtonLocationManagementClear);
			TestBase.clickOnElement(byButtonLocationManagementClear, buttonLocationManagementClear, "Location Management Clear Button");
			//MobileElement el2 = (MobileElement) Ad.findElementByAccessibilityId("OK");
			//el2.click();
			oK = Ad.findElement(byOK);
			TestBase.clickOnElement(byOK, oK, "OK Button");
		} catch (Exception e) {
			System.out.println("No Saved addresses are availabe ");
		}

	}

}

package com.twc.ios.app.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.twc.ios.app.functions.Functions;
import com.twc.ios.app.general.Driver;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.Utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;

public class FTLScreens extends Utils {
	AppiumDriver<MobileElement> Ad;
	String settingsButton_AccessibilityId = "settingsButton";
	String closeButton_AccessibilityId = "close_button";
	String cancelButton_Name = "Cancel";
	String changeToAlwaysAllow_Name = "Change to Always Allow";
	String letsGoButton_AccessibilityId = "letsGoButton";
	String continueButton_AccessibilityId = "Continue";
	String allowButton_AccessibilityId = "Allow";
	String iUnderstand_AccessibilityId = "I Understand";
	String oKButton_Name = "OK";
	String alwaysAllow_AccessibilityId = "Always Allow";
	String allowWhileUsingApp_AccessibilityId = "Allow While Using App";
	
	By bySettingsButton = MobileBy.AccessibilityId(settingsButton_AccessibilityId);
	By byCloseButton = MobileBy.AccessibilityId(closeButton_AccessibilityId);
	By byCancelButton = MobileBy.name(cancelButton_Name);
	By byChangeToAlwaysAllow = MobileBy.name(changeToAlwaysAllow_Name);
	By byLetsGoButton = MobileBy.AccessibilityId(letsGoButton_AccessibilityId);
	By byContinueButton = MobileBy.AccessibilityId(continueButton_AccessibilityId);
	By byAllow = MobileBy.AccessibilityId(allowButton_AccessibilityId);
	By byIUnderstand = MobileBy.AccessibilityId(iUnderstand_AccessibilityId);
	By byOKButton = MobileBy.name(oKButton_Name);
	By byAlwaysAllow = MobileBy.AccessibilityId(alwaysAllow_AccessibilityId);
	By byAllowWhileUsingApp = MobileBy.AccessibilityId(allowWhileUsingApp_AccessibilityId);
	
	
	
	
	MobileElement settingsButton = null;
	MobileElement closeButton = null;
	MobileElement cancelButton = null;
	MobileElement changeToAlwaysAllow = null;
	MobileElement letsGoButton = null;
	MobileElement continueButton = null;
	MobileElement allowButton = null;
	MobileElement iUnderstand = null;
	MobileElement oKButton = null;
	MobileElement alwaysAllow = null;
	MobileElement allowWhileUsingApp = null;

	public FTLScreens(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Verify No Of Consent Calls On FTL")
	public void verifyNoOfConsentCallsOnFTL() throws Exception {
	/**
	 * There should be 4 Consent calls be generated on FTL,  
	 * location-apps-2: two calls
	 * advertising-apps-3: one call
	 * sale-apps-1: one call
	 */
		int value = Utils.getNoOfOccurancesOfAPICallWithHostandPath("upsx.weather.com", "/consent");
		System.out.println("No Of Consent Calls found are: "+value);
		Assert.assertEquals(value, 4);
	}
	
	@Step("Handle Unwanted Popups during app launch")
	public void handle_Unwanted_Popups_Old() throws Exception {
		attachScreen();
		try {
			TestBase.waitForVisibilityOfElementLocated(Ad, 30, bySettingsButton);
			settingsButton = Ad.findElement(bySettingsButton);
			System.out
					.println("Settings Button found after app launch, hence no need to verify the application alerts");
			logStep("Settings Button found after app launch, hence no need to verify the application alerts");
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 10, byCloseButton);
				closeButton = Ad.findElement(byCloseButton);
				TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
				System.out.println("Premium Big Ad displayed on the screen and closed");
				logStep("Premium Big Ad displayed on the screen and closed");
			} catch (Exception e) {
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 10, byCancelButton);
					cancelButton = Ad.findElement(byCancelButton);
					TestBase.clickOnElement(byCancelButton, cancelButton, "Cancel Button");
					System.out.println("Premium Big Ad displayed on the screen and closed");
					logStep("Premium Big Ad displayed on the screen and closed");
				} catch (Exception e1) {
					System.out.println("Premium Big Ad not displayed on the screen");
					logStep("Premium Big Ad not displayed on the screen");
				}
			}
		} catch (Exception e1) {
			try {
				//TestBase.waitForVisibilityOfElementLocated(Ad, 20, byCloseButton);
				closeButton = Ad.findElement(byCloseButton);
				TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
				System.out.println("Intermittent Ad displayed on the screen and closed");
				logStep("Intermittent Ad displayed on the screen and closed");
			} catch (Exception e) {
				System.out.println("Intermittent Ad not displayed on the screen");
				logStep("Intermittent Ad not displayed on the screen");
			}
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byChangeToAlwaysAllow);
				changeToAlwaysAllow = Ad.findElement(byChangeToAlwaysAllow);
				TestBase.clickOnElement(byChangeToAlwaysAllow, changeToAlwaysAllow, "Change to Always Allow Button");
				System.out.println("Change to Always Allow button available on the screen and handled");
				logStep("Change to Always Allow button available on the screen and handled");
			} catch (Exception e) {
				System.out.println("Change to Always Allow button not available on the screen");
				logStep("Change to Always Allow button not available on the screen");
			}

			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byCloseButton);
				closeButton = Ad.findElement(byCloseButton);
				TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
				//Ad.findElementByName("close_button").click();
				System.out.println("App upgrade alert available on the screen and handled");
				logStep("App upgrade alert available on the screen and handled");
			} catch (Exception e) {
				System.out.println("App upgrade alert not available on the screen");
				logStep("App upgrade alert not available on the screen");
			}
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byLetsGoButton);
				letsGoButton = Ad.findElement(byLetsGoButton);
				TestBase.clickOnElement(byLetsGoButton, letsGoButton, "Let's Go/Next Button");
				System.out.println("Let's Go/Next button available on the screen and handled");
				logStep("Let's Go/Next button available on the screen and handled");
			} catch (Exception e) {
				System.out.println("Let's Go/Next button not available on the screen");
				logStep("Let's Go/Next button not available on the screen");
			}

			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byContinueButton);
				continueButton = Ad.findElement(byContinueButton);
				TestBase.clickOnElement(byContinueButton, continueButton, "Continue Button");
				System.out.println("Continue button available on the Apple Tracking Transparency screen and handled");
				logStep("Continue button available on the Apple Tracking Transparency screen and handled");
			} catch (Exception e) {
				System.out.println("Continue button not available on the Apple Tracking Transparency screen");
				logStep("Continue button not available on the Apple Tracking Transparency screen");
			}
			
			/*
			 * ADTECH-1342:Update automation suites to handle Tracking Options flow during
			 * FTL iOS14.3 & iPhone 8 plus
			 */
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byAllow);
				allowButton = Ad.findElement(byAllow);
				TestBase.clickOnElement(byAllow, allowButton, "Allow Button");
				System.out.println("Allow button available on the App Tracking screen and handled");
				logStep("Allow button available on the App Tracking screen and handled");
			} catch (Exception e) {
				System.out.println("Allow button not available on the App Tracking screen");
				logStep("Allow button not available on the App Tracking screen");
			}
			/**
			 * on iPhone 7, we need to scroll on Location and Weatcher screen due to size of
			 * screen as privacy content increased, then only I Understand button enabled,
			 * else it is disabled.
			 */

			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byIUnderstand);
				iUnderstand = Ad.findElement(byIUnderstand);
				TestBase.clickOnElement(byIUnderstand, iUnderstand, "iUnderstand Button");
				System.out.println("I Understand button available on the screen and handled");
				logStep("I Understand button available on the screen and handled");
				try {
					oKButton = Ad.findElement(byOKButton);
					TestBase.clickOnElement(byOKButton, oKButton, "OK Button");
					Functions.swipe_Up_OnLocationScreen();
				    byIUnderstand = MobileBy.AccessibilityId("I Understand");
					iUnderstand = Ad.findElement(byIUnderstand);
					TestBase.clickOnElement(byIUnderstand, iUnderstand, "iUnderstand Button");
					
				} catch (Exception e) {

				}

			} catch (Exception e) {
				System.out.println("I Understand button not available on the screen");
				logStep("I Understand button not available on the screen");
			}

			/**
			 * on iPhone 7, we need to scroll on Location and Weatcher screen due to size of
			 * screen as privacy content increased, then only I Understand button enabled,
			 * else it is disabled.
			 */

			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byIUnderstand);
				iUnderstand = Ad.findElement(byIUnderstand);
				TestBase.clickOnElement(byIUnderstand, iUnderstand, "iUnderstand Button");
				System.out.println("I Understand button available on the screen and handled");
				logStep("I Understand button available on the screen and handled");
				try {
					oKButton = Ad.findElement(byOKButton);
					TestBase.clickOnElement(byOKButton, oKButton, "OK Button");
					Functions.swipe_Up_OnLocationScreen();
				    byIUnderstand = MobileBy.AccessibilityId("I Understand");
					iUnderstand = Ad.findElement(byIUnderstand);
					TestBase.clickOnElement(byIUnderstand, iUnderstand, "iUnderstand Button");
					
				} catch (Exception e) {

				}

			} catch (Exception e) {
				System.out.println("I Understand button not available on the screen");
				logStep("I Understand button not available on the screen");
			}

			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byAlwaysAllow);
				alwaysAllow = Ad.findElement(byAlwaysAllow);
				TestBase.clickOnElement(byAlwaysAllow, alwaysAllow, "Always Allow Button");
				System.out.println("Always Allow button available on the screen and handled");
				logStep("Always Allow button available on the screen and handled");
			} catch (Exception e) {
				System.out.println("Always Allow button not available on the screen");
				logStep("Always Allow button not available on the screen");
			}
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byAllowWhileUsingApp);
				allowWhileUsingApp = Ad.findElement(byAllowWhileUsingApp);
				TestBase.clickOnElement(byAllowWhileUsingApp, allowWhileUsingApp, "Allow While Using App Button");
				System.out.println("Allow While Using App button available on the screen and handled");
				logStep("Allow While Using App button available on the screen and handled");
			} catch (Exception e) {
				System.out.println("Allow While Using App button not available on the screen");
				logStep("Allow While Using App button not available on the screen");
			}
			
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byAllow);
				allowButton = Ad.findElement(byAllow);
				TestBase.clickOnElement(byAllow, allowButton, "Allow Button");
				System.out.println("Allow button available on the screen and handled");
				logStep("Allow button available on the screen and handled");
			} catch (Exception e) {
				System.out.println("Allow button not available on the screen");
				logStep("Allow button not available on the screen");
			}
						

			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byCloseButton);
				closeButton = Ad.findElement(byCloseButton);
				TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
				System.out.println("Privacy notification displayed on the screen and handled");
				logStep("Privacy notification displayed on the screen and handled");
			} catch (Exception e) {
				System.out.println("Privacy notification not displayed on the screen");
				logStep("Privacy notification not displayed on the screen");
			}
			
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, bySettingsButton);
				settingsButton = Ad.findElement(bySettingsButton);
				System.out
						.println("Settings Button found after handling all alerts");
				logStep("Settings Button found after handling all alerts");
			}catch(Exception e) {
				System.out
				.println("Settings Button not found after handling all alerts, hence kill and launching it again");
				logStep("Settings Button not found after handling all alerts, hence kill and launching it again");
				Functions.close_launchApp();
				
			}
		}
		
		try {
			attachScreen();
		} catch (Exception e) {
			System.out.println("An exception while attaching screenshot");
			logStep("An exception while attaching screenshot");
		}

	}
	
	@Step("Handle Unwanted Popups during app launch")
	public void handle_Unwanted_Popups() throws Exception {
		attachScreen();
		boolean isSettingsButtonDisplayed = false;
		while (!isSettingsButtonDisplayed) {
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 30, bySettingsButton);
				settingsButton = Ad.findElement(bySettingsButton);
				System.out
						.println("Settings Button found after app launch, hence no need to verify the application alerts");
				logStep("Settings Button found after app launch, hence no need to verify the application alerts");
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 10, byCloseButton);
					closeButton = Ad.findElement(byCloseButton);
					TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
					System.out.println("Premium Big Ad displayed on the screen and closed");
					logStep("Premium Big Ad displayed on the screen and closed");
				} catch (Exception e) {
					try {
						TestBase.waitForVisibilityOfElementLocated(Ad, 10, byCancelButton);
						cancelButton = Ad.findElement(byCancelButton);
						TestBase.clickOnElement(byCancelButton, cancelButton, "Cancel Button");
						System.out.println("Premium Big Ad displayed on the screen and closed");
						logStep("Premium Big Ad displayed on the screen and closed");
					} catch (Exception e1) {
						System.out.println("Premium Big Ad not displayed on the screen");
						logStep("Premium Big Ad not displayed on the screen");
					}
				}
				isSettingsButtonDisplayed = true;
				break;
			} catch (Exception e1) {
				try {
					//TestBase.waitForVisibilityOfElementLocated(Ad, 20, byCloseButton);
					closeButton = Ad.findElement(byCloseButton);
					TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
					System.out.println("Intermittent Ad displayed on the screen and closed");
					logStep("Intermittent Ad displayed on the screen and closed");
				} catch (Exception e) {
					System.out.println("Intermittent Ad not displayed on the screen");
					logStep("Intermittent Ad not displayed on the screen");
				}
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 20, byChangeToAlwaysAllow);
					changeToAlwaysAllow = Ad.findElement(byChangeToAlwaysAllow);
					TestBase.clickOnElement(byChangeToAlwaysAllow, changeToAlwaysAllow, "Change to Always Allow Button");
					System.out.println("Change to Always Allow button available on the screen and handled");
					logStep("Change to Always Allow button available on the screen and handled");
				} catch (Exception e) {
					System.out.println("Change to Always Allow button not available on the screen");
					logStep("Change to Always Allow button not available on the screen");
				}

				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 20, byCloseButton);
					closeButton = Ad.findElement(byCloseButton);
					TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
					//Ad.findElementByName("close_button").click();
					System.out.println("App upgrade alert available on the screen and handled");
					logStep("App upgrade alert available on the screen and handled");
				} catch (Exception e) {
					System.out.println("App upgrade alert not available on the screen");
					logStep("App upgrade alert not available on the screen");
				}
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 5, bySettingsButton);
					settingsButton = Ad.findElement(bySettingsButton);
					System.out
							.println("Settings Button found during handling all alerts");
					logStep("Settings Button found during handling all alerts");
					isSettingsButtonDisplayed = true;
					break;
					
				}catch(Exception e) {
										
				}
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 20, byLetsGoButton);
					letsGoButton = Ad.findElement(byLetsGoButton);
					TestBase.clickOnElement(byLetsGoButton, letsGoButton, "Let's Go/Next Button");
					System.out.println("Let's Go/Next button available on the screen and handled");
					logStep("Let's Go/Next button available on the screen and handled");
				} catch (Exception e) {
					System.out.println("Let's Go/Next button not available on the screen");
					logStep("Let's Go/Next button not available on the screen");
				}
				
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 5, bySettingsButton);
					settingsButton = Ad.findElement(bySettingsButton);
					System.out
							.println("Settings Button found during handling all alerts");
					logStep("Settings Button found during handling all alerts");
					isSettingsButtonDisplayed = true;
					break;
					
				}catch(Exception e) {
										
				}

				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 20, byContinueButton);
					continueButton = Ad.findElement(byContinueButton);
					TestBase.clickOnElement(byContinueButton, continueButton, "Continue Button");
					System.out.println("Continue button available on the Apple Tracking Transparency screen and handled");
					logStep("Continue button available on the Apple Tracking Transparency screen and handled");
				} catch (Exception e) {
					System.out.println("Continue button not available on the Apple Tracking Transparency screen");
					logStep("Continue button not available on the Apple Tracking Transparency screen");
				}
				
				/*
				 * ADTECH-1342:Update automation suites to handle Tracking Options flow during
				 * FTL iOS14.3 & iPhone 8 plus
				 */
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 20, byAllow);
					allowButton = Ad.findElement(byAllow);
					TestBase.clickOnElement(byAllow, allowButton, "Allow Button");
					System.out.println("Allow button available on the App Tracking screen and handled");
					logStep("Allow button available on the App Tracking screen and handled");
				} catch (Exception e) {
					System.out.println("Allow button not available on the App Tracking screen");
					logStep("Allow button not available on the App Tracking screen");
				}
				/**
				 * on iPhone 7, we need to scroll on Location and Weather screen due to size of
				 * screen as privacy content increased, then only I Understand button enabled,
				 * else it is disabled.
				 */

				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 20, byIUnderstand);
					iUnderstand = Ad.findElement(byIUnderstand);
					TestBase.clickOnElement(byIUnderstand, iUnderstand, "iUnderstand Button");
					System.out.println("I Understand button available on the screen and handled");
					logStep("I Understand button available on the screen and handled");
					try {
						oKButton = Ad.findElement(byOKButton);
						TestBase.clickOnElement(byOKButton, oKButton, "OK Button");
						Functions.swipe_Up_OnLocationScreen();
					    byIUnderstand = MobileBy.AccessibilityId("I Understand");
						iUnderstand = Ad.findElement(byIUnderstand);
						TestBase.clickOnElement(byIUnderstand, iUnderstand, "iUnderstand Button");
						
					} catch (Exception e) {

					}

				} catch (Exception e) {
					System.out.println("I Understand button not available on the screen");
					logStep("I Understand button not available on the screen");
				}
				
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 5, bySettingsButton);
					settingsButton = Ad.findElement(bySettingsButton);
					System.out
							.println("Settings Button found during handling all alerts");
					logStep("Settings Button found during handling all alerts");
					isSettingsButtonDisplayed = true;
					break;
					
				}catch(Exception e) {
										
				}

				/**
				 * on iPhone 7, we need to scroll on Location and Weather screen due to size of
				 * screen as privacy content increased, then only I Understand button enabled,
				 * else it is disabled.
				 */

				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 20, byIUnderstand);
					iUnderstand = Ad.findElement(byIUnderstand);
					TestBase.clickOnElement(byIUnderstand, iUnderstand, "iUnderstand Button");
					System.out.println("I Understand button available on the screen and handled");
					logStep("I Understand button available on the screen and handled");
					try {
						oKButton = Ad.findElement(byOKButton);
						TestBase.clickOnElement(byOKButton, oKButton, "OK Button");
						Functions.swipe_Up_OnLocationScreen();
					    byIUnderstand = MobileBy.AccessibilityId("I Understand");
						iUnderstand = Ad.findElement(byIUnderstand);
						TestBase.clickOnElement(byIUnderstand, iUnderstand, "iUnderstand Button");
						
					} catch (Exception e) {

					}

				} catch (Exception e) {
					System.out.println("I Understand button not available on the screen");
					logStep("I Understand button not available on the screen");
				}
				
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 5, bySettingsButton);
					settingsButton = Ad.findElement(bySettingsButton);
					System.out
							.println("Settings Button found during handling all alerts");
					logStep("Settings Button found during handling all alerts");
					isSettingsButtonDisplayed = true;
					break;
					
				}catch(Exception e) {
										
				}

				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 20, byAlwaysAllow);
					alwaysAllow = Ad.findElement(byAlwaysAllow);
					TestBase.clickOnElement(byAlwaysAllow, alwaysAllow, "Always Allow Button");
					System.out.println("Always Allow button available on the screen and handled");
					logStep("Always Allow button available on the screen and handled");
				} catch (Exception e) {
					System.out.println("Always Allow button not available on the screen");
					logStep("Always Allow button not available on the screen");
				}
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 20, byAllowWhileUsingApp);
					allowWhileUsingApp = Ad.findElement(byAllowWhileUsingApp);
					TestBase.clickOnElement(byAllowWhileUsingApp, allowWhileUsingApp, "Allow While Using App Button");
					System.out.println("Allow While Using App button available on the screen and handled");
					logStep("Allow While Using App button available on the screen and handled");
				} catch (Exception e) {
					System.out.println("Allow While Using App button not available on the screen");
					logStep("Allow While Using App button not available on the screen");
				}
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 5, bySettingsButton);
					settingsButton = Ad.findElement(bySettingsButton);
					System.out
							.println("Settings Button found during handling all alerts");
					logStep("Settings Button found during handling all alerts");
					isSettingsButtonDisplayed = true;
					break;
					
				}catch(Exception e) {
										
				}
				
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 20, byAllow);
					allowButton = Ad.findElement(byAllow);
					TestBase.clickOnElement(byAllow, allowButton, "Allow Button");
					System.out.println("Allow button available on the screen and handled");
					logStep("Allow button available on the screen and handled");
				} catch (Exception e) {
					System.out.println("Allow button not available on the screen");
					logStep("Allow button not available on the screen");
				}
				
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 5, bySettingsButton);
					settingsButton = Ad.findElement(bySettingsButton);
					System.out
							.println("Settings Button found during handling all alerts");
					logStep("Settings Button found during handling all alerts");
					isSettingsButtonDisplayed = true;
					break;
					
				}catch(Exception e) {
										
				}
							

				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 20, byCloseButton);
					closeButton = Ad.findElement(byCloseButton);
					TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
					System.out.println("Privacy notification displayed on the screen and handled");
					logStep("Privacy notification displayed on the screen and handled");
				} catch (Exception e) {
					System.out.println("Privacy notification not displayed on the screen");
					logStep("Privacy notification not displayed on the screen");
				}
				
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 20, bySettingsButton);
					settingsButton = Ad.findElement(bySettingsButton);
					System.out
							.println("Settings Button found after handling all alerts");
					logStep("Settings Button found after handling all alerts");
					isSettingsButtonDisplayed = true;
					break;
				}catch(Exception e) {
					System.out
					.println("Settings Button not found after handling all alerts, hence kill and launching it again");
					logStep("Settings Button not found after handling all alerts, hence kill and launching it again");
					Functions.close_launchApp();
					
				}
			}
		}
		
		
		try {
			attachScreen();
		} catch (Exception e) {
			System.out.println("An exception while attaching screenshot");
			logStep("An exception while attaching screenshot");
		}

	}
	
	@Step("Handle Unwanted Popups during app launch")
	public void handle_Unwanted_Popups(AppiumDriver<MobileElement> Ad) throws Exception {
		attachScreen(Ad);
		boolean isSettingsButtonDisplayed = false;
		while (!isSettingsButtonDisplayed) {
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 30, bySettingsButton);
				settingsButton = Ad.findElement(bySettingsButton);
				System.out
						.println("Settings Button found after app launch, hence no need to verify the application alerts");
				logStep("Settings Button found after app launch, hence no need to verify the application alerts");
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 10, byCloseButton);
					closeButton = Ad.findElement(byCloseButton);
					TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
					System.out.println("Premium Big Ad displayed on the screen and closed");
					logStep("Premium Big Ad displayed on the screen and closed");
				} catch (Exception e) {
					try {
						TestBase.waitForVisibilityOfElementLocated(Ad, 10, byCancelButton);
						cancelButton = Ad.findElement(byCancelButton);
						TestBase.clickOnElement(byCancelButton, cancelButton, "Cancel Button");
						System.out.println("Premium Big Ad displayed on the screen and closed");
						logStep("Premium Big Ad displayed on the screen and closed");
					} catch (Exception e1) {
						System.out.println("Premium Big Ad not displayed on the screen");
						logStep("Premium Big Ad not displayed on the screen");
					}
				}
				isSettingsButtonDisplayed = true;
				break;
			} catch (Exception e1) {
				try {
					//TestBase.waitForVisibilityOfElementLocated(Ad, 20, byCloseButton);
					closeButton = Ad.findElement(byCloseButton);
					TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
					System.out.println("Intermittent Ad displayed on the screen and closed");
					logStep("Intermittent Ad displayed on the screen and closed");
				} catch (Exception e) {
					System.out.println("Intermittent Ad not displayed on the screen");
					logStep("Intermittent Ad not displayed on the screen");
				}
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 20, byChangeToAlwaysAllow);
					changeToAlwaysAllow = Ad.findElement(byChangeToAlwaysAllow);
					TestBase.clickOnElement(byChangeToAlwaysAllow, changeToAlwaysAllow, "Change to Always Allow Button");
					System.out.println("Change to Always Allow button available on the screen and handled");
					logStep("Change to Always Allow button available on the screen and handled");
				} catch (Exception e) {
					System.out.println("Change to Always Allow button not available on the screen");
					logStep("Change to Always Allow button not available on the screen");
				}

				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 20, byCloseButton);
					closeButton = Ad.findElement(byCloseButton);
					TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
					//Ad.findElementByName("close_button").click();
					System.out.println("App upgrade alert available on the screen and handled");
					logStep("App upgrade alert available on the screen and handled");
				} catch (Exception e) {
					System.out.println("App upgrade alert not available on the screen");
					logStep("App upgrade alert not available on the screen");
				}
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 5, bySettingsButton);
					settingsButton = Ad.findElement(bySettingsButton);
					System.out
							.println("Settings Button found during handling all alerts");
					logStep("Settings Button found during handling all alerts");
					isSettingsButtonDisplayed = true;
					break;
					
				}catch(Exception e) {
										
				}
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 20, byLetsGoButton);
					letsGoButton = Ad.findElement(byLetsGoButton);
					TestBase.clickOnElement(byLetsGoButton, letsGoButton, "Let's Go/Next Button");
					System.out.println("Let's Go/Next button available on the screen and handled");
					logStep("Let's Go/Next button available on the screen and handled");
				} catch (Exception e) {
					System.out.println("Let's Go/Next button not available on the screen");
					logStep("Let's Go/Next button not available on the screen");
				}
				
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 5, bySettingsButton);
					settingsButton = Ad.findElement(bySettingsButton);
					System.out
							.println("Settings Button found during handling all alerts");
					logStep("Settings Button found during handling all alerts");
					isSettingsButtonDisplayed = true;
					break;
					
				}catch(Exception e) {
										
				}

				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 20, byContinueButton);
					continueButton = Ad.findElement(byContinueButton);
					TestBase.clickOnElement(byContinueButton, continueButton, "Continue Button");
					System.out.println("Continue button available on the Apple Tracking Transparency screen and handled");
					logStep("Continue button available on the Apple Tracking Transparency screen and handled");
				} catch (Exception e) {
					System.out.println("Continue button not available on the Apple Tracking Transparency screen");
					logStep("Continue button not available on the Apple Tracking Transparency screen");
				}
				
				/*
				 * ADTECH-1342:Update automation suites to handle Tracking Options flow during
				 * FTL iOS14.3 & iPhone 8 plus
				 */
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 20, byAllow);
					allowButton = Ad.findElement(byAllow);
					TestBase.clickOnElement(byAllow, allowButton, "Allow Button");
					System.out.println("Allow button available on the App Tracking screen and handled");
					logStep("Allow button available on the App Tracking screen and handled");
				} catch (Exception e) {
					System.out.println("Allow button not available on the App Tracking screen");
					logStep("Allow button not available on the App Tracking screen");
				}
				/**
				 * on iPhone 7, we need to scroll on Location and Weatcher screen due to size of
				 * screen as privacy content increased, then only I Understand button enabled,
				 * else it is disabled.
				 */

				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 20, byIUnderstand);
					iUnderstand = Ad.findElement(byIUnderstand);
					TestBase.clickOnElement(byIUnderstand, iUnderstand, "iUnderstand Button");
					System.out.println("I Understand button available on the screen and handled");
					logStep("I Understand button available on the screen and handled");
					try {
						oKButton = Ad.findElement(byOKButton);
						TestBase.clickOnElement(byOKButton, oKButton, "OK Button");
						Functions.swipe_Up_OnLocationScreen();
					    byIUnderstand = MobileBy.AccessibilityId("I Understand");
						iUnderstand = Ad.findElement(byIUnderstand);
						TestBase.clickOnElement(byIUnderstand, iUnderstand, "iUnderstand Button");
						
					} catch (Exception e) {

					}

				} catch (Exception e) {
					System.out.println("I Understand button not available on the screen");
					logStep("I Understand button not available on the screen");
				}
				
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 5, bySettingsButton);
					settingsButton = Ad.findElement(bySettingsButton);
					System.out
							.println("Settings Button found during handling all alerts");
					logStep("Settings Button found during handling all alerts");
					isSettingsButtonDisplayed = true;
					break;
					
				}catch(Exception e) {
										
				}

				/**
				 * on iPhone 7, we need to scroll on Location and Weatcher screen due to size of
				 * screen as privacy content increased, then only I Understand button enabled,
				 * else it is disabled.
				 */

				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 20, byIUnderstand);
					iUnderstand = Ad.findElement(byIUnderstand);
					TestBase.clickOnElement(byIUnderstand, iUnderstand, "iUnderstand Button");
					System.out.println("I Understand button available on the screen and handled");
					logStep("I Understand button available on the screen and handled");
					try {
						oKButton = Ad.findElement(byOKButton);
						TestBase.clickOnElement(byOKButton, oKButton, "OK Button");
						Functions.swipe_Up_OnLocationScreen();
					    byIUnderstand = MobileBy.AccessibilityId("I Understand");
						iUnderstand = Ad.findElement(byIUnderstand);
						TestBase.clickOnElement(byIUnderstand, iUnderstand, "iUnderstand Button");
						
					} catch (Exception e) {

					}

				} catch (Exception e) {
					System.out.println("I Understand button not available on the screen");
					logStep("I Understand button not available on the screen");
				}
				
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 5, bySettingsButton);
					settingsButton = Ad.findElement(bySettingsButton);
					System.out
							.println("Settings Button found during handling all alerts");
					logStep("Settings Button found during handling all alerts");
					isSettingsButtonDisplayed = true;
					break;
					
				}catch(Exception e) {
										
				}

				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 20, byAlwaysAllow);
					alwaysAllow = Ad.findElement(byAlwaysAllow);
					TestBase.clickOnElement(byAlwaysAllow, alwaysAllow, "Always Allow Button");
					System.out.println("Always Allow button available on the screen and handled");
					logStep("Always Allow button available on the screen and handled");
				} catch (Exception e) {
					System.out.println("Always Allow button not available on the screen");
					logStep("Always Allow button not available on the screen");
				}
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 20, byAllowWhileUsingApp);
					allowWhileUsingApp = Ad.findElement(byAllowWhileUsingApp);
					TestBase.clickOnElement(byAllowWhileUsingApp, allowWhileUsingApp, "Allow While Using App Button");
					System.out.println("Allow While Using App button available on the screen and handled");
					logStep("Allow While Using App button available on the screen and handled");
				} catch (Exception e) {
					System.out.println("Allow While Using App button not available on the screen");
					logStep("Allow While Using App button not available on the screen");
				}
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 5, bySettingsButton);
					settingsButton = Ad.findElement(bySettingsButton);
					System.out
							.println("Settings Button found during handling all alerts");
					logStep("Settings Button found during handling all alerts");
					isSettingsButtonDisplayed = true;
					break;
					
				}catch(Exception e) {
										
				}
				
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 20, byAllow);
					allowButton = Ad.findElement(byAllow);
					TestBase.clickOnElement(byAllow, allowButton, "Allow Button");
					System.out.println("Allow button available on the screen and handled");
					logStep("Allow button available on the screen and handled");
				} catch (Exception e) {
					System.out.println("Allow button not available on the screen");
					logStep("Allow button not available on the screen");
				}
				
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 5, bySettingsButton);
					settingsButton = Ad.findElement(bySettingsButton);
					System.out
							.println("Settings Button found during handling all alerts");
					logStep("Settings Button found during handling all alerts");
					isSettingsButtonDisplayed = true;
					break;
					
				}catch(Exception e) {
										
				}
							

				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 20, byCloseButton);
					closeButton = Ad.findElement(byCloseButton);
					TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
					System.out.println("Privacy notification displayed on the screen and handled");
					logStep("Privacy notification displayed on the screen and handled");
				} catch (Exception e) {
					System.out.println("Privacy notification not displayed on the screen");
					logStep("Privacy notification not displayed on the screen");
				}
				
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 20, bySettingsButton);
					settingsButton = Ad.findElement(bySettingsButton);
					System.out
							.println("Settings Button found after handling all alerts");
					logStep("Settings Button found after handling all alerts");
					isSettingsButtonDisplayed = true;
					break;
				}catch(Exception e) {
					System.out
					.println("Settings Button not found after handling all alerts, hence kill and launching it again");
					logStep("Settings Button not found after handling all alerts, hence kill and launching it again");
					Functions.close_launchApp(Ad);
					
				}
			}
		}
		
		
		try {
			attachScreen(Ad);
		} catch (Exception e) {
			System.out.println("An exception while attaching screenshot");
			logStep("An exception while attaching screenshot");
		}

	}
	
	@Step("Handle Unwanted Popups during app launch For China/Russia/Belarus")
	public void handle_Unwanted_Popups_China() throws Exception {
		attachScreen();
		try {
			TestBase.waitForVisibilityOfElementLocated(Ad, 30, bySettingsButton);
			settingsButton = Ad.findElement(bySettingsButton);
			System.out
					.println("Settings Button found after app launch, hence no need to verify the application alerts");
			logStep("Settings Button found after app launch, hence no need to verify the application alerts");
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 10, byCloseButton);
				closeButton = Ad.findElement(byCloseButton);
				TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
				System.out.println("Premium Big Ad displayed on the screen and closed");
				logStep("Premium Big Ad displayed on the screen and closed");
			} catch (Exception e) {
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 10, byCancelButton);
					cancelButton = Ad.findElement(byCancelButton);
					TestBase.clickOnElement(byCancelButton, cancelButton, "Cancel Button");
					System.out.println("Premium Big Ad displayed on the screen and closed");
					logStep("Premium Big Ad displayed on the screen and closed");
				} catch (Exception e1) {
					System.out.println("Premium Big Ad not displayed on the screen");
					logStep("Premium Big Ad not displayed on the screen");
				}
			}
		} catch (Exception e1) {
			try {
				//TestBase.waitForVisibilityOfElementLocated(Ad, 20, byCloseButton);
				closeButton = Ad.findElement(byCloseButton);
				TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
				System.out.println("Intermittent Ad displayed on the screen and closed");
				logStep("Intermittent Ad displayed on the screen and closed");
			} catch (Exception e) {
				System.out.println("Intermittent Ad not displayed on the screen");
				logStep("Intermittent Ad not displayed on the screen");
			}
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byChangeToAlwaysAllow);
				changeToAlwaysAllow = Ad.findElement(byChangeToAlwaysAllow);
				TestBase.clickOnElement(byChangeToAlwaysAllow, changeToAlwaysAllow, "Change to Always Allow Button");
				System.out.println("Change to Always Allow button available on the screen and handled");
				logStep("Change to Always Allow button available on the screen and handled");
			} catch (Exception e) {
				System.out.println("Change to Always Allow button not available on the screen");
				logStep("Change to Always Allow button not available on the screen");
			}

			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byCloseButton);
				closeButton = Ad.findElement(byCloseButton);
				TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
				//Ad.findElementByName("close_button").click();
				System.out.println("App upgrade alert available on the screen and handled");
				logStep("App upgrade alert available on the screen and handled");
			} catch (Exception e) {
				System.out.println("App upgrade alert not available on the screen");
				logStep("App upgrade alert not available on the screen");
			}
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byLetsGoButton);
				letsGoButton = Ad.findElement(byLetsGoButton);
				TestBase.clickOnElement(byLetsGoButton, letsGoButton, "Let's Go/Next Button");
				System.out.println("Let's Go/Next button available on the screen and handled");
				logStep("Let's Go/Next button available on the screen and handled");
			} catch (Exception e) {
				System.out.println("Let's Go/Next button not available on the screen");
				logStep("Let's Go/Next button not available on the screen");
			}

			
		}
		try {
			attachScreen();
		} catch (Exception e) {
			System.out.println("An exception while attaching screenshot");
			logStep("An exception while attaching screenshot");
		}

	}
	
	@Step("Handle Unwanted Popups during app launch from widget")
	public void handle_Unwanted_Popups_When_App_Launched_From_Widget() throws Exception {
		attachScreen();
		try {
			TestBase.waitForVisibilityOfElementLocated(Ad, 30, bySettingsButton);
			settingsButton = Ad.findElement(bySettingsButton);
			System.out
					.println("Settings Button found after app launch, hence no need to verify the application alerts");
			logStep("Settings Button found after app launch, hence no need to verify the application alerts");
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 10, byCloseButton);
				closeButton = Ad.findElement(byCloseButton);
				TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
				System.out.println("Premium Big Ad displayed on the screen and closed");
				logStep("Premium Big Ad displayed on the screen and closed");
			} catch (Exception e) {
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 10, byCancelButton);
					cancelButton = Ad.findElement(byCancelButton);
					TestBase.clickOnElement(byCancelButton, cancelButton, "Cancel Button");
					System.out.println("Premium Big Ad displayed on the screen and closed");
					logStep("Premium Big Ad displayed on the screen and closed");
				} catch (Exception e1) {
					System.out.println("Premium Big Ad not displayed on the screen");
					logStep("Premium Big Ad not displayed on the screen");
				}
			}
		} catch (Exception e1) {
			try {
				//TestBase.waitForVisibilityOfElementLocated(Ad, 20, byCloseButton);
				closeButton = Ad.findElement(byCloseButton);
				TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
				System.out.println("Intermittent Ad displayed on the screen and closed");
				logStep("Intermittent Ad displayed on the screen and closed");
			} catch (Exception e) {
				System.out.println("Intermittent Ad not displayed on the screen");
				logStep("Intermittent Ad not displayed on the screen");
			}
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byChangeToAlwaysAllow);
				changeToAlwaysAllow = Ad.findElement(byChangeToAlwaysAllow);
				TestBase.clickOnElement(byChangeToAlwaysAllow, changeToAlwaysAllow, "Change to Always Allow Button");
				System.out.println("Change to Always Allow button available on the screen and handled");
				logStep("Change to Always Allow button available on the screen and handled");
			} catch (Exception e) {
				System.out.println("Change to Always Allow button not available on the screen");
				logStep("Change to Always Allow button not available on the screen");
			}

			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byCloseButton);
				closeButton = Ad.findElement(byCloseButton);
				TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
				//Ad.findElementByName("close_button").click();
				System.out.println("App upgrade alert available on the screen and handled");
				logStep("App upgrade alert available on the screen and handled");
			} catch (Exception e) {
				System.out.println("App upgrade alert not available on the screen");
				logStep("App upgrade alert not available on the screen");
			}
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byLetsGoButton);
				letsGoButton = Ad.findElement(byLetsGoButton);
				TestBase.clickOnElement(byLetsGoButton, letsGoButton, "Let's Go/Next Button");
				System.out.println("Let's Go/Next button available on the screen and handled");
				logStep("Let's Go/Next button available on the screen and handled");
			} catch (Exception e) {
				System.out.println("Let's Go/Next button not available on the screen");
				logStep("Let's Go/Next button not available on the screen");
			}

			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byContinueButton);
				continueButton = Ad.findElement(byContinueButton);
				TestBase.clickOnElement(byContinueButton, continueButton, "Continue Button");
				System.out.println("Continue button available on the Apple Tracking Transparency screen and handled");
				logStep("Continue button available on the Apple Tracking Transparency screen and handled");
			} catch (Exception e) {
				System.out.println("Continue button not available on the Apple Tracking Transparency screen");
				logStep("Continue button not available on the Apple Tracking Transparency screen");
			}
			
			/*
			 * ADTECH-1342:Update automation suites to handle Tracking Options flow during
			 * FTL iOS14.3 & iPhone 8 plus
			 */
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byAllow);
				allowButton = Ad.findElement(byAllow);
				TestBase.clickOnElement(byAllow, allowButton, "Allow Button");
				System.out.println("Allow button available on the App Tracking screen and handled");
				logStep("Allow button available on the App Tracking screen and handled");
			} catch (Exception e) {
				System.out.println("Allow button not available on the App Tracking screen");
				logStep("Allow button not available on the App Tracking screen");
			}
			/**
			 * on iPhone 7, we need to scroll on Location and Weatcher screen due to size of
			 * screen as privacy content increased, then only I Understand button enabled,
			 * else it is disabled.
			 */

			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byIUnderstand);
				iUnderstand = Ad.findElement(byIUnderstand);
				TestBase.clickOnElement(byIUnderstand, iUnderstand, "iUnderstand Button");
				System.out.println("I Understand button available on the screen and handled");
				logStep("I Understand button available on the screen and handled");
				try {
					oKButton = Ad.findElement(byOKButton);
					TestBase.clickOnElement(byOKButton, oKButton, "OK Button");
					Functions.swipe_Up_OnLocationScreen();
				    byIUnderstand = MobileBy.AccessibilityId("I Understand");
					iUnderstand = Ad.findElement(byIUnderstand);
					TestBase.clickOnElement(byIUnderstand, iUnderstand, "iUnderstand Button");
					
				} catch (Exception e) {

				}

			} catch (Exception e) {
				System.out.println("I Understand button not available on the screen");
				logStep("I Understand button not available on the screen");
			}

			/**
			 * on iPhone 7, we need to scroll on Location and Weatcher screen due to size of
			 * screen as privacy content increased, then only I Understand button enabled,
			 * else it is disabled.
			 */

			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byIUnderstand);
				iUnderstand = Ad.findElement(byIUnderstand);
				TestBase.clickOnElement(byIUnderstand, iUnderstand, "iUnderstand Button");
				System.out.println("I Understand button available on the screen and handled");
				logStep("I Understand button available on the screen and handled");
				try {
					oKButton = Ad.findElement(byOKButton);
					TestBase.clickOnElement(byOKButton, oKButton, "OK Button");
					Functions.swipe_Up_OnLocationScreen();
				    byIUnderstand = MobileBy.AccessibilityId("I Understand");
					iUnderstand = Ad.findElement(byIUnderstand);
					TestBase.clickOnElement(byIUnderstand, iUnderstand, "iUnderstand Button");
					
				} catch (Exception e) {

				}

			} catch (Exception e) {
				System.out.println("I Understand button not available on the screen");
				logStep("I Understand button not available on the screen");
			}

			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byAlwaysAllow);
				alwaysAllow = Ad.findElement(byAlwaysAllow);
				TestBase.clickOnElement(byAlwaysAllow, alwaysAllow, "Always Allow Button");
				System.out.println("Always Allow button available on the screen and handled");
				logStep("Always Allow button available on the screen and handled");
			} catch (Exception e) {
				System.out.println("Always Allow button not available on the screen");
				logStep("Always Allow button not available on the screen");
			}
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byAllowWhileUsingApp);
				allowWhileUsingApp = Ad.findElement(byAllowWhileUsingApp);
				TestBase.clickOnElement(byAllowWhileUsingApp, allowWhileUsingApp, "Allow While Using App Button");
				System.out.println("Allow While Using App button available on the screen and handled");
				logStep("Allow While Using App button available on the screen and handled");
			} catch (Exception e) {
				System.out.println("Allow While Using App button not available on the screen");
				logStep("Allow While Using App button not available on the screen");
			}
			
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byAllow);
				allowButton = Ad.findElement(byAllow);
				TestBase.clickOnElement(byAllow, allowButton, "Allow Button");
				System.out.println("Allow button available on the screen and handled");
				logStep("Allow button available on the screen and handled");
			} catch (Exception e) {
				System.out.println("Allow button not available on the screen");
				logStep("Allow button not available on the screen");
			}
						

			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byCloseButton);
				closeButton = Ad.findElement(byCloseButton);
				TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
				System.out.println("Privacy notification displayed on the screen and handled");
				logStep("Privacy notification displayed on the screen and handled");
			} catch (Exception e) {
				System.out.println("Privacy notification not displayed on the screen");
				logStep("Privacy notification not displayed on the screen");
			}
			
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, bySettingsButton);
				settingsButton = Ad.findElement(bySettingsButton);
				System.out
						.println("Settings Button found after handling all alerts");
				logStep("Settings Button found after handling all alerts");
			}catch(Exception e) {
				System.out
				.println("Settings Button not found after handling all alerts, hence kill and launching it again");
				logStep("Settings Button not found after handling all alerts, hence kill and launching it again");
				Functions.close_launchApp();
				
			}
		}
		
	}
	
	@Step("Handle Unwanted Popups during app launch from widget")
	public void handle_Unwanted_Popups_When_App_Launched_From_Widget(AppiumDriver<MobileElement> Ad) throws Exception {
		attachScreen(Ad);
		try {
			TestBase.waitForVisibilityOfElementLocated(Ad, 30, bySettingsButton);
			settingsButton = Ad.findElement(bySettingsButton);
			System.out
					.println("Settings Button found after app launch, hence no need to verify the application alerts");
			logStep("Settings Button found after app launch, hence no need to verify the application alerts");
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 10, byCloseButton);
				closeButton = Ad.findElement(byCloseButton);
				TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
				System.out.println("Premium Big Ad displayed on the screen and closed");
				logStep("Premium Big Ad displayed on the screen and closed");
			} catch (Exception e) {
				try {
					TestBase.waitForVisibilityOfElementLocated(Ad, 10, byCancelButton);
					cancelButton = Ad.findElement(byCancelButton);
					TestBase.clickOnElement(byCancelButton, cancelButton, "Cancel Button");
					System.out.println("Premium Big Ad displayed on the screen and closed");
					logStep("Premium Big Ad displayed on the screen and closed");
				} catch (Exception e1) {
					System.out.println("Premium Big Ad not displayed on the screen");
					logStep("Premium Big Ad not displayed on the screen");
				}
			}
		} catch (Exception e1) {
			try {
				//TestBase.waitForVisibilityOfElementLocated(Ad, 20, byCloseButton);
				closeButton = Ad.findElement(byCloseButton);
				TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
				System.out.println("Intermittent Ad displayed on the screen and closed");
				logStep("Intermittent Ad displayed on the screen and closed");
			} catch (Exception e) {
				System.out.println("Intermittent Ad not displayed on the screen");
				logStep("Intermittent Ad not displayed on the screen");
			}
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byChangeToAlwaysAllow);
				changeToAlwaysAllow = Ad.findElement(byChangeToAlwaysAllow);
				TestBase.clickOnElement(byChangeToAlwaysAllow, changeToAlwaysAllow, "Change to Always Allow Button");
				System.out.println("Change to Always Allow button available on the screen and handled");
				logStep("Change to Always Allow button available on the screen and handled");
			} catch (Exception e) {
				System.out.println("Change to Always Allow button not available on the screen");
				logStep("Change to Always Allow button not available on the screen");
			}

			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byCloseButton);
				closeButton = Ad.findElement(byCloseButton);
				TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
				//Ad.findElementByName("close_button").click();
				System.out.println("App upgrade alert available on the screen and handled");
				logStep("App upgrade alert available on the screen and handled");
			} catch (Exception e) {
				System.out.println("App upgrade alert not available on the screen");
				logStep("App upgrade alert not available on the screen");
			}
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byLetsGoButton);
				letsGoButton = Ad.findElement(byLetsGoButton);
				TestBase.clickOnElement(byLetsGoButton, letsGoButton, "Let's Go/Next Button");
				System.out.println("Let's Go/Next button available on the screen and handled");
				logStep("Let's Go/Next button available on the screen and handled");
			} catch (Exception e) {
				System.out.println("Let's Go/Next button not available on the screen");
				logStep("Let's Go/Next button not available on the screen");
			}

			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byContinueButton);
				continueButton = Ad.findElement(byContinueButton);
				TestBase.clickOnElement(byContinueButton, continueButton, "Continue Button");
				System.out.println("Continue button available on the Apple Tracking Transparency screen and handled");
				logStep("Continue button available on the Apple Tracking Transparency screen and handled");
			} catch (Exception e) {
				System.out.println("Continue button not available on the Apple Tracking Transparency screen");
				logStep("Continue button not available on the Apple Tracking Transparency screen");
			}
			
			/*
			 * ADTECH-1342:Update automation suites to handle Tracking Options flow during
			 * FTL iOS14.3 & iPhone 8 plus
			 */
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byAllow);
				allowButton = Ad.findElement(byAllow);
				TestBase.clickOnElement(byAllow, allowButton, "Allow Button");
				System.out.println("Allow button available on the App Tracking screen and handled");
				logStep("Allow button available on the App Tracking screen and handled");
			} catch (Exception e) {
				System.out.println("Allow button not available on the App Tracking screen");
				logStep("Allow button not available on the App Tracking screen");
			}
			/**
			 * on iPhone 7, we need to scroll on Location and Weatcher screen due to size of
			 * screen as privacy content increased, then only I Understand button enabled,
			 * else it is disabled.
			 */

			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byIUnderstand);
				iUnderstand = Ad.findElement(byIUnderstand);
				TestBase.clickOnElement(byIUnderstand, iUnderstand, "iUnderstand Button");
				System.out.println("I Understand button available on the screen and handled");
				logStep("I Understand button available on the screen and handled");
				try {
					oKButton = Ad.findElement(byOKButton);
					TestBase.clickOnElement(byOKButton, oKButton, "OK Button");
					Functions.swipe_Up_OnLocationScreen();
				    byIUnderstand = MobileBy.AccessibilityId("I Understand");
					iUnderstand = Ad.findElement(byIUnderstand);
					TestBase.clickOnElement(byIUnderstand, iUnderstand, "iUnderstand Button");
					
				} catch (Exception e) {

				}

			} catch (Exception e) {
				System.out.println("I Understand button not available on the screen");
				logStep("I Understand button not available on the screen");
			}

			/**
			 * on iPhone 7, we need to scroll on Location and Weatcher screen due to size of
			 * screen as privacy content increased, then only I Understand button enabled,
			 * else it is disabled.
			 */

			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byIUnderstand);
				iUnderstand = Ad.findElement(byIUnderstand);
				TestBase.clickOnElement(byIUnderstand, iUnderstand, "iUnderstand Button");
				System.out.println("I Understand button available on the screen and handled");
				logStep("I Understand button available on the screen and handled");
				try {
					oKButton = Ad.findElement(byOKButton);
					TestBase.clickOnElement(byOKButton, oKButton, "OK Button");
					Functions.swipe_Up_OnLocationScreen();
				    byIUnderstand = MobileBy.AccessibilityId("I Understand");
					iUnderstand = Ad.findElement(byIUnderstand);
					TestBase.clickOnElement(byIUnderstand, iUnderstand, "iUnderstand Button");
					
				} catch (Exception e) {

				}

			} catch (Exception e) {
				System.out.println("I Understand button not available on the screen");
				logStep("I Understand button not available on the screen");
			}

			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byAlwaysAllow);
				alwaysAllow = Ad.findElement(byAlwaysAllow);
				TestBase.clickOnElement(byAlwaysAllow, alwaysAllow, "Always Allow Button");
				System.out.println("Always Allow button available on the screen and handled");
				logStep("Always Allow button available on the screen and handled");
			} catch (Exception e) {
				System.out.println("Always Allow button not available on the screen");
				logStep("Always Allow button not available on the screen");
			}
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byAllowWhileUsingApp);
				allowWhileUsingApp = Ad.findElement(byAllowWhileUsingApp);
				TestBase.clickOnElement(byAllowWhileUsingApp, allowWhileUsingApp, "Allow While Using App Button");
				System.out.println("Allow While Using App button available on the screen and handled");
				logStep("Allow While Using App button available on the screen and handled");
			} catch (Exception e) {
				System.out.println("Allow While Using App button not available on the screen");
				logStep("Allow While Using App button not available on the screen");
			}
			
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byAllow);
				allowButton = Ad.findElement(byAllow);
				TestBase.clickOnElement(byAllow, allowButton, "Allow Button");
				System.out.println("Allow button available on the screen and handled");
				logStep("Allow button available on the screen and handled");
			} catch (Exception e) {
				System.out.println("Allow button not available on the screen");
				logStep("Allow button not available on the screen");
			}
						

			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, byCloseButton);
				closeButton = Ad.findElement(byCloseButton);
				TestBase.clickOnElement(byCloseButton, closeButton, "Close Button");
				System.out.println("Privacy notification displayed on the screen and handled");
				logStep("Privacy notification displayed on the screen and handled");
			} catch (Exception e) {
				System.out.println("Privacy notification not displayed on the screen");
				logStep("Privacy notification not displayed on the screen");
			}
			
			try {
				TestBase.waitForVisibilityOfElementLocated(Ad, 20, bySettingsButton);
				settingsButton = Ad.findElement(bySettingsButton);
				System.out
						.println("Settings Button found after handling all alerts");
				logStep("Settings Button found after handling all alerts");
			}catch(Exception e) {
				System.out
				.println("Settings Button not found after handling all alerts, hence kill and launching it again");
				logStep("Settings Button not found after handling all alerts, hence kill and launching it again");
				Functions.close_launchApp(Ad);
				
			}
		}
		
	}

}

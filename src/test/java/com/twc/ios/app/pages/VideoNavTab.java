package com.twc.ios.app.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.twc.ios.app.charlesfunctions.CharlesProxy;
import com.twc.ios.app.general.Driver;
import com.twc.ios.app.general.ReadExcelValues;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.Utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;

public class VideoNavTab extends Utils{
	AppiumDriver<MobileElement> Ad;
	String videoDetailTab_AccessibilityId = "videoDetailTab";
	String videoTab_AccessibilityId = "Video";
	String videosTab_AccessibilityId = "Videos";
	String winterTab_AccessibilityId = "Winter";
	String severeTab_AccessibilityId = "Severe";
	String tropicsTab_AccessibilityId = "Tropics";
	String ianTab_AccessibilityId = "Ian";

	By byVideoDetailNavTab = MobileBy.AccessibilityId(videoDetailTab_AccessibilityId);
	By byVideoNavTab = MobileBy.AccessibilityId(videoTab_AccessibilityId);
	By byVideosNavTab = MobileBy.AccessibilityId(videosTab_AccessibilityId);
	By byWinterNavTab = MobileBy.AccessibilityId(winterTab_AccessibilityId);
	By bySevereNavTab = MobileBy.AccessibilityId(severeTab_AccessibilityId);
	By byTropicsNavTab = MobileBy.AccessibilityId(tropicsTab_AccessibilityId);
	By byIanNavTab = MobileBy.AccessibilityId(ianTab_AccessibilityId);
	By byVideoTabFallback = MobileBy.xpath("(//XCUIElementTypeTabBar/XCUIElementTypeButton)[5]");

	MobileElement videoNavTab = null;
	

	public VideoNavTab(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	@Step("Navigate To Video Tab")
	public void navigateToVideoTab() throws Exception {
		try {
			videoNavTab = Ad.findElement(byVideoDetailNavTab);
			TestBase.clickOnElement(byVideoDetailNavTab, videoNavTab, "Video Nav Tab");
			TestBase.waitForMilliSeconds(20000);
			System.out.println("Navigated to Video tab ");
			logStep("Navigated to Video tab");
			attachScreen();
		} catch (Exception e) {
			try {
				videoNavTab = Ad.findElement(byVideoNavTab);
				TestBase.clickOnElement(byVideoNavTab, videoNavTab, "Video Nav Tab");
				TestBase.waitForMilliSeconds(20000);
				System.out.println("Navigated to Video tab ");
				logStep("Navigated to Video tab");
				attachScreen();
			}catch (Exception e1) {
				try {
					videoNavTab = Ad.findElement(byWinterNavTab);
					TestBase.clickOnElement(byWinterNavTab, videoNavTab, "Winter Nav Tab");
					TestBase.waitForMilliSeconds(20000);
					System.out.println("Navigated to Winter tab ");
					logStep("Navigated to Winter tab");
					attachScreen();
				} catch (Exception ex) {
					try {
						videoNavTab = Ad.findElement(bySevereNavTab);
						TestBase.clickOnElement(bySevereNavTab, videoNavTab, "Severe Nav Tab");
						TestBase.waitForMilliSeconds(20000);
						System.out.println("Navigated to Severe tab ");
						logStep("Navigated to Severe tab");
						attachScreen();
					} catch (Exception ex1) {
						try {
						videoNavTab = Ad.findElement(byTropicsNavTab);
						TestBase.clickOnElement(byTropicsNavTab, videoNavTab, "Tropics Nav Tab");
						TestBase.waitForMilliSeconds(20000);
						System.out.println("Navigated to Tropics tab ");
						logStep("Navigated to Tropics tab");
						attachScreen();
					} catch (Exception ex2) {
						try {
							videoNavTab = Ad.findElement(byVideosNavTab);
							TestBase.clickOnElement(byVideosNavTab, videoNavTab, "Videos Nav Tab");
							TestBase.waitForMilliSeconds(20000);
							System.out.println("Navigated to Videos tab ");
							logStep("Navigated to Videos tab");
							attachScreen();
						} catch (Exception ex3) {
							try {
								videoNavTab = Ad.findElement(byIanNavTab);
								TestBase.clickOnElement(byIanNavTab, videoNavTab, "Ian Nav Tab");
								TestBase.waitForMilliSeconds(20000);
								System.out.println("Navigated to Ian tab ");
								logStep("Navigated to Ian tab");
								attachScreen();
							} catch (Exception ex4) {
								try {
									videoNavTab = Ad.findElement(byVideoTabFallback);
									TestBase.clickOnElement(byVideoTabFallback, videoNavTab, "Video Nav Tab");
									TestBase.waitForMilliSeconds(20000);
									System.out.println("Navigated to Video tab ");
									logStep("Navigated to Video tab");
									attachScreen();
								} catch (Exception ex5) {
									System.out.println("Video tab not displayed");
									logStep("Video tab not displayed");
									attachScreen();
								}
							}
						}
					
					}
					}
				}
			}
		}

	}
	
	@Step("Navigate To Video Tab: {0}")
	public void navigateToVideoTab_Old(boolean clearCharles, CharlesProxy proxy) throws Exception {
		try {
			if (clearCharles) {
				proxy.clearCharlesSession();
			}
			videoNavTab = Ad.findElement(byVideoDetailNavTab);
			if (clearCharles) {
				proxy.clearCharlesSession();
			}
			TestBase.clickOnElement(byVideoDetailNavTab, videoNavTab, "Video Nav Tab");
			TestBase.waitForMilliSeconds(20000);
			System.out.println("Navigated to Video tab ");
			logStep("Navigated to Video tab");
			attachScreen();
		} catch (Exception e) {
			try {
				if (clearCharles) {
					proxy.clearCharlesSession();
				}
				videoNavTab = Ad.findElement(byVideoNavTab);
				if (clearCharles) {
					proxy.clearCharlesSession();
				}
				TestBase.clickOnElement(byVideoNavTab, videoNavTab, "Video Nav Tab");
				TestBase.waitForMilliSeconds(20000);
				System.out.println("Navigated to Video tab ");
				logStep("Navigated to Video tab");
				attachScreen();
			}catch (Exception e1) {

				try {
					if (clearCharles) {
						proxy.clearCharlesSession();
					}
					videoNavTab = Ad.findElement(byWinterNavTab);
					if (clearCharles) {
						proxy.clearCharlesSession();
					}
					TestBase.clickOnElement(byWinterNavTab, videoNavTab, "Winter Nav Tab");
					TestBase.waitForMilliSeconds(20000);
					System.out.println("Navigated to Winter tab ");
					logStep("Navigated to Winter tab");
					attachScreen();
				} catch (Exception ex) {
					try {
						if (clearCharles) {
							proxy.clearCharlesSession();
						}
						videoNavTab = Ad.findElement(bySevereNavTab);
						if (clearCharles) {
							proxy.clearCharlesSession();
						}
						TestBase.clickOnElement(bySevereNavTab, videoNavTab, "Severe Nav Tab");
						TestBase.waitForMilliSeconds(20000);
						System.out.println("Navigated to Severe tab ");
						logStep("Navigated to Severe tab");
						attachScreen();
					} catch (Exception ex1) {
						try {
						if (clearCharles) {
							proxy.clearCharlesSession();
						}
						videoNavTab = Ad.findElement(byTropicsNavTab);
						if (clearCharles) {
							proxy.clearCharlesSession();
						}
						TestBase.clickOnElement(byTropicsNavTab, videoNavTab, "Tropics Nav Tab");
						TestBase.waitForMilliSeconds(20000);
						System.out.println("Navigated to Tropics tab ");
						logStep("Navigated to Tropics tab");
						attachScreen();
					} catch (Exception ex2) {
						try {
							if (clearCharles) {
								proxy.clearCharlesSession();
							}
							videoNavTab = Ad.findElement(byVideosNavTab);
							if (clearCharles) {
								proxy.clearCharlesSession();
							}
							TestBase.clickOnElement(byVideosNavTab, videoNavTab, "Videos Nav Tab");
							TestBase.waitForMilliSeconds(20000);
							System.out.println("Navigated to Videos tab ");
							logStep("Navigated to Videos tab");
							attachScreen();
						} catch (Exception ex3) {
							try {
								videoNavTab = Ad.findElement(byIanNavTab);
								if (clearCharles) {
									proxy.clearCharlesSession();
								}
								TestBase.clickOnElement(byIanNavTab, videoNavTab, "Ian Nav Tab");
								TestBase.waitForMilliSeconds(20000);
								System.out.println("Navigated to Ian tab ");
								logStep("Navigated to Ian tab");
								attachScreen();
							} catch (Exception ex4) {
								try {
									videoNavTab = Ad.findElement(byVideoTabFallback);
									if (clearCharles) {
										proxy.clearCharlesSession();
									}
									TestBase.clickOnElement(byVideoTabFallback, videoNavTab, "Video Nav Tab");
									TestBase.waitForMilliSeconds(20000);
									System.out.println("Navigated to Video tab ");
									logStep("Navigated to Video tab");
									attachScreen();
								} catch (Exception ex5) {
									System.out.println("Video tab not displayed");
									logStep("Video tab not displayed");
									attachScreen();
								}
							}
						}
					}
					}
				}
			
			}
		}

	}
	
	@Step("Navigate To Video Tab: {0}")
	public void navigateToVideoTab(boolean clearCharles, CharlesProxy proxy) throws Exception {
		try {
			
			videoNavTab = Ad.findElement(byVideoDetailNavTab);
			if (clearCharles) {
				proxy.clearCharlesSession();
			}
			TestBase.clickOnElement(byVideoDetailNavTab, videoNavTab, "Video Nav Tab");
			TestBase.waitForMilliSeconds(20000);
			System.out.println("Navigated to Video tab ");
			logStep("Navigated to Video tab");
			attachScreen();
		} catch (Exception e) {
			try {
				
				videoNavTab = Ad.findElement(byVideoNavTab);
				if (clearCharles) {
					proxy.clearCharlesSession();
				}
				TestBase.clickOnElement(byVideoNavTab, videoNavTab, "Video Nav Tab");
				TestBase.waitForMilliSeconds(20000);
				System.out.println("Navigated to Video tab ");
				logStep("Navigated to Video tab");
				attachScreen();
			}catch (Exception e1) {

				try {
					
					videoNavTab = Ad.findElement(byWinterNavTab);
					if (clearCharles) {
						proxy.clearCharlesSession();
					}
					TestBase.clickOnElement(byWinterNavTab, videoNavTab, "Winter Nav Tab");
					TestBase.waitForMilliSeconds(20000);
					System.out.println("Navigated to Winter tab ");
					logStep("Navigated to Winter tab");
					attachScreen();
				} catch (Exception ex) {
					try {
						
						videoNavTab = Ad.findElement(bySevereNavTab);
						if (clearCharles) {
							proxy.clearCharlesSession();
						}
						TestBase.clickOnElement(bySevereNavTab, videoNavTab, "Severe Nav Tab");
						TestBase.waitForMilliSeconds(20000);
						System.out.println("Navigated to Severe tab ");
						logStep("Navigated to Severe tab");
						attachScreen();
					} catch (Exception ex1) {
						try {
						
						videoNavTab = Ad.findElement(byTropicsNavTab);
						if (clearCharles) {
							proxy.clearCharlesSession();
						}
						TestBase.clickOnElement(byTropicsNavTab, videoNavTab, "Tropics Nav Tab");
						TestBase.waitForMilliSeconds(20000);
						System.out.println("Navigated to Tropics tab ");
						logStep("Navigated to Tropics tab");
						attachScreen();
					} catch (Exception ex2) {
						try {
							if (clearCharles) {
								proxy.clearCharlesSession();
							}
							videoNavTab = Ad.findElement(byVideosNavTab);
							if (clearCharles) {
								proxy.clearCharlesSession();
							}
							TestBase.clickOnElement(byVideosNavTab, videoNavTab, "Videos Nav Tab");
							TestBase.waitForMilliSeconds(20000);
							System.out.println("Navigated to Videos tab ");
							logStep("Navigated to Videos tab");
							attachScreen();
						} catch (Exception ex3) {
							try {
								videoNavTab = Ad.findElement(byIanNavTab);
								if (clearCharles) {
									proxy.clearCharlesSession();
								}
								TestBase.clickOnElement(byIanNavTab, videoNavTab, "Ian Nav Tab");
								TestBase.waitForMilliSeconds(20000);
								System.out.println("Navigated to Ian tab ");
								logStep("Navigated to Ian tab");
								attachScreen();
							} catch (Exception ex4) {
								try {
									videoNavTab = Ad.findElement(byVideoTabFallback);
									if (clearCharles) {
										proxy.clearCharlesSession();
									}
									TestBase.clickOnElement(byVideoTabFallback, videoNavTab, "Video Nav Tab");
									TestBase.waitForMilliSeconds(20000);
									System.out.println("Navigated to Video tab ");
									logStep("Navigated to video tab");
									attachScreen();
								} catch (Exception ex5) {
									System.out.println("Video tab not displayed");
									logStep("Video tab not displayed");
									attachScreen();
								}
							}
						}
					}
					}
				}
			
			}
		}

	}
	
	@Step("Validate Preroll Video Beacon")
	public void validatePreRollVideoBeacon(String excelName, String sheetName, String beaconParam, String beaconValue) throws Exception{
		ReadExcelValues.excelValues(excelName, sheetName);
		String host = ReadExcelValues.data[2][Cap];
		String path = ReadExcelValues.data[3][Cap];
		//boolean flag = Utils.verifyPreRollVideoBeaconValueInCharlesSession(host, path,"type","complete");
		boolean flag = Utils.verifyPreRollVideoBeaconValueInCharlesSession(host, path, beaconParam, beaconValue);
		if (flag) {
			System.out.println(host + path + " call is present in Charles session with "+beaconParam+" = "+ beaconValue);
			logStep(host + path + " call is present in Charles session with "+beaconParam+" = "+ beaconValue);
			System.out.println(host + path + " call with "+beaconParam+" = "+ beaconValue+ " Verification is successful");
			logStep(host + path + " call with "+beaconParam+" = "+ beaconValue+ " Verification is successful");

		} else {
			System.out.println(host + path + " call with "+beaconParam+" = "+ beaconValue +" is not present in Charles session");
			logStep(host + path + " call with "+beaconParam+" = "+ beaconValue +" is not present in Charles session");
			System.out.println(host + path + " call with "+beaconParam+" = "+ beaconValue+ " Verification is failed");
			logStep(host + path + " call with "+beaconParam+" = "+ beaconValue+ " Verification is failed");
			Assert.fail(host + path + " call with "+beaconParam+" = "+ beaconValue+ " Verification is failed");

		}
	}

}

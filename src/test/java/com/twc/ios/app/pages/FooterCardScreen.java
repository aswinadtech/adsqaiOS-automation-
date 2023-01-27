package com.twc.ios.app.pages;

import org.openqa.selenium.By;

import com.twc.ios.app.functions.Functions;
import com.twc.ios.app.general.Driver;
import com.twc.ios.app.general.ReadExcelValues;
import com.twc.ios.app.general.TestBase;
import com.twc.ios.app.general.Utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;

public class FooterCardScreen extends Utils {
	AppiumDriver<MobileElement> Ad;
		
	
	MobileElement footerCard = null;
	

	public FooterCardScreen(AppiumDriver<MobileElement> Ad) {
		this.Ad = Ad;
	}

	public  void scrollToFooterCard() throws Exception {
		ReadExcelValues.excelValues("Smoke", "General");
		By byFooterCard = MobileBy.name(ReadExcelValues.data[1][Cap]);
		//aQCard = Ad.findElement(byAirQualityCard);
		Functions.genericScroll(byFooterCard, true, false, getOffsetYTop(), TOLERANCE_FROM_TOP);
		//Functions.genericScrollTWC(byFooterCard, true, false, getOffsetYTop(), TOLERANCE_FROM_TOP, false, false);
	}

}

package com.twc.ios.app.general;

import org.testng.annotations.Test;
import org.openqa.selenium.ScreenOrientation;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

public class MoveTo_TestMode extends Driver{
	
	@Test
	public void testmode() throws InterruptedException
	{
		Ad.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[1]").click();
		MobileElement el =(MobileElement) Ad.findElementByName("My Profile");
		MobileElement el1 =(MobileElement) Ad.findElementByName("Units");
		
		TouchAction action1 = new TouchAction(Ad);
		//action1.longPress(el1).moveTo(el).release().perform();
		Thread.sleep(1000);
		MobileElement el2=(MobileElement) Ad.findElementByName("About This App");
		el2.click();
		Thread.sleep(1000);
		for(int i=1;i<=10;i++)
		{
			Ad.findElementByName("twc logo").click();
		}
		
		Ad.findElementByName("adSource").click();
		Ad.findElementByName("test").click();
	
		Ad.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[1]").click();
		Ad.findElementByName("Save").click();
	}

}

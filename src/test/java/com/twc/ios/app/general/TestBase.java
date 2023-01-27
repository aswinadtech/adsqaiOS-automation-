package com.twc.ios.app.general;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

import com.twc.ios.app.charlesfunctions.CharlesProxy;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;




public class TestBase extends Driver{

	public static WebDriver driver;
	
	public Properties p = new Properties();
	
	public WebDriver getDriver() {
		return driver;
	}



	public void loadData() throws IOException {
		File file = new File(System.getProperty("user.dir") + "/data.properties");
		FileInputStream f = new FileInputStream(file);
		p.load(f);

	}

	public void setDriver(EventFiringWebDriver driver) {
		this.driver = driver;
	}

	public void waitForElement(WebDriver driver, int timeOutInSeconds, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public static void highlightMe(WebDriver driver, WebElement element) throws InterruptedException {
		// Creating JavaScriptExecuter Interface
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Execute javascript
		js.executeScript("arguments[0].style.border='4px solid yellow'", element);
		Thread.sleep(3000);
		js.executeScript("arguments[0].style.border=''", element);
	}

	
	public Iterator<String> getAllWindows() {
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> itr = windows.iterator();
		return itr;
	}

	
	public String captureScreen(String fileName) {
		if (fileName == "") {
			fileName = "blank";
		}
		File destFile = null;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "/src/main/java/com/test/automation/uiAutomation/screenshot/";
			destFile = new File((String) reportDirectory + fileName + "_" + formater.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(scrFile, destFile);
			// This will help us to link the screen shot in testNG report
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath() + "' height='100' width='100'/> </a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destFile.toString();
	}

/*	@AfterClass(alwaysRun = true)
	public void endTest() {
		closeBrowser();
	}*/

	@AfterTest
	public void allureParameterSectionClean() throws Exception {
		AllureUtilities.removeParametersInReport();
	}

	public void closeBrowser() {
		driver.quit();
		
	}

	public MobileElement waitForElement(WebDriver driver, MobileElement element, long timeOutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		return element;
	}

	
	
	public void typeData(WebElement element, String text) {
		element.click();
		element.clear();
		element.sendKeys(text);
	}
	

	//WebDriver  driver;

	/*
	 * public Utilities(WebDriver driver) { this.driver=driver; }
	 */
	
	public void scrollToText(String text)
	{
	    // driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+text+"\"));");
	}
	
	/**
	 * 
	 * @param element
	 * @param elementName
	 * @param inputText
	 */
	public static void typeText(MobileElement element, String elementName, String inputText) {
		element.click();
		System.out.println("Clicked on "+elementName+ " field");
		Driver.logStep("Clicked on "+elementName+ " field");
		element.clear();
		System.out.println("Cleared content of "+elementName+ " field");
		Driver.logStep("Cleared content of "+elementName+ " field");
		element.sendKeys(inputText);
		System.out.println("Entered text: "+"\""+inputText+"\""+" in "+elementName+ " field");
		Driver.logStep("Entered text: "+"\""+inputText+"\""+" in "+elementName+ " field");
		
	}
	
	/**
	 * 
	 * @param element
	 * @param elementName
	 */
	public static void clearText(MobileElement element, String elementName) {
		element.clear();
		System.out.println("Cleared content of "+elementName+ " field");
		Driver.logStep("Cleared content of "+elementName+ " field");
	}
	
	/**
	 * 
	 * @param element
	 * @return 
	 */
	public static String getElementText(MobileElement element) {
		return element.getText();
	}
	
	/**
	 * 
	 * @param byLocatorStrategy
	 * @return
	 */
	public static boolean isElementExists(By byLocatorStrategy) {
		if(getElementsCount(byLocatorStrategy)==0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 
	 * @param byLocatorStrategy
	 * @param Ad
	 * @return
	 */
	public static boolean isElementExists(By byLocatorStrategy, AppiumDriver<MobileElement> Ad) {
		if(getElementsCount(byLocatorStrategy, Ad)==0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 
	 * @param element
	 * @return
	 */
	public static boolean isElementDisplayed(MobileElement element) {
		return element.isDisplayed();
	}
	
	/**
	 * 
	 * @param locator
	 * @return
	 */
	public  static boolean isElementDisplayed(By locator) {
		boolean isVisible;
		try {
			isVisible = Ad.findElement(locator).isDisplayed();
		} catch (final WebDriverException e) {
			isVisible = false;
		}
		System.out.println("Element visible: " + isVisible);
		return isVisible;
	}
	
	/**
	 * 
	 * @param locator
	 * @return
	 */
	public  static boolean isElementDisplayed(By locator, AppiumDriver<MobileElement> Ad) {
		boolean isVisible;
		try {
			isVisible = Ad.findElement(locator).isDisplayed();
		} catch (final WebDriverException e) {
			isVisible = false;
		}
		System.out.println("Element visible: " + isVisible);
		return isVisible;
	}
	
	/**
	 * 
	 * @param element
	 * @return
	 */
	public static boolean isElementEnabled(MobileElement element) {
		return element.isEnabled();
	}
	
	/**
	 * 
	 * @param element
	 * @param attributeName
	 * @return
	 */
	public static String getElementAttribute(MobileElement element, String attributeName) {
		return element.getAttribute(attributeName);
		
	}
	
	/**
	 * 
	 * @param byLocatorStrategy
	 * @param element
	 * @param elementName
	 */
	public static void clickOnElement(By byLocatorStrategy, MobileElement element, String elementName) {
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (getElementsCount(byLocatorStrategy) == 0) {
			try {
				Thread.sleep(4000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long elementCheckStrtTime = 0L;
		long elementCheckEndTime = 0L;
		long elementCheckTimeElapsed = 0L;
		
		long elementCheckTimeconvert = 0L;
		elementCheckStrtTime = System.nanoTime();
		elementCheckEndTime = System.nanoTime();
		elementCheckTimeElapsed = elementCheckEndTime - elementCheckStrtTime;
		elementCheckTimeconvert = TimeUnit.SECONDS.convert(elementCheckTimeElapsed, TimeUnit.NANOSECONDS);
		
		while (!element.isEnabled()) {
			elementCheckEndTime = System.nanoTime();
			elementCheckTimeElapsed = elementCheckEndTime - elementCheckStrtTime;
			elementCheckTimeconvert = TimeUnit.SECONDS.convert(elementCheckTimeElapsed, TimeUnit.NANOSECONDS);
			
			if (elementCheckTimeconvert >= 240) {
				System.out.println("Noticed that " + elementName + " not enabled to perform click action, waited for: "+elementCheckTimeconvert +" Seconds, test may fail");
				Driver.logStep("Noticed that " + elementName + " not enabled to perform click action, waited for: "+elementCheckTimeconvert +" Seconds, test may fail");
				break;
			} 
			try {
				Thread.sleep(2000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("Clicking on " + elementName + " Element");
		Driver.logStep("Clicking on " + elementName + " Element");
		element.click();
		// Driver.attachScreen();

	}
	
	/**
	 * 
	 * @param byLocatorStrategy
	 * @param element
	 * @param elementName
	 * @param proxy
	 */
	public static void clickOnElement(By byLocatorStrategy, MobileElement element, String elementName, CharlesProxy proxy) {
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (getElementsCount(byLocatorStrategy) == 0) {
			try {
				Thread.sleep(4000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long elementCheckStrtTime = 0L;
		long elementCheckEndTime = 0L;
		long elementCheckTimeElapsed = 0L;
		
		long elementCheckTimeconvert = 0L;
		elementCheckStrtTime = System.nanoTime();
		elementCheckEndTime = System.nanoTime();
		elementCheckTimeElapsed = elementCheckEndTime - elementCheckStrtTime;
		elementCheckTimeconvert = TimeUnit.SECONDS.convert(elementCheckTimeElapsed, TimeUnit.NANOSECONDS);
		
		while (!element.isEnabled()) {
			elementCheckEndTime = System.nanoTime();
			elementCheckTimeElapsed = elementCheckEndTime - elementCheckStrtTime;
			elementCheckTimeconvert = TimeUnit.SECONDS.convert(elementCheckTimeElapsed, TimeUnit.NANOSECONDS);
			
			if (elementCheckTimeconvert >= 240) {
				System.out.println("Noticed that " + elementName + " not enabled to perform click action, waited for: "+elementCheckTimeconvert +" Seconds, test may fail");
				Driver.logStep("Noticed that " + elementName + " not enabled to perform click action, waited for: "+elementCheckTimeconvert +" Seconds, test may fail");
				break;
			} 
			try {
				Thread.sleep(2000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

				
		System.out.println("Clearing charles session before cliking the element");
		Driver.logStep("Clearing charles session before cliking the element");
		proxy.clearCharlesSession();
				
		System.out.println("Clicking on " + elementName + " Element");
		Driver.logStep("Clicking on " + elementName + " Element");
		element.click();
		// Driver.attachScreen();

	}
	
	/**
	 * 
	 */
	public static void navigateBack() {
		Ad.navigate().back();
	}
	
	/**
	 * 
	 */
	public static void implicitWait() {
		Ad.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	/**
	 * 
	 * @param byLocatorStrategy
	 * @return
	 */
	public static int getElementsCount(By byLocatorStrategy) {
		return Ad.findElements(byLocatorStrategy).size();
	}
	
	/**
	 * 
	 * @param byLocatorStrategy
	 * @param Ad
	 * @return
	 */
	public static int getElementsCount(By byLocatorStrategy, AppiumDriver<MobileElement> Ad) {
		return Ad.findElements(byLocatorStrategy).size();
	}
	
	/**
	 * This method generates random number and returns it
	 * @return
	 */
	public static int getRand()
	{
	Random r = new Random(); 

	return r.nextInt((9999999 - 111) + 1) + 111;
	 
	}
	
	/**
	 * 
	 * @param Ad
	 * @param timeOutInSeconds
	 * @param byLocator
	 */
	public static void waitForVisibilityOfElementLocated(AppiumDriver<MobileElement> Ad, int timeOutInSeconds, By byLocator) {
		WebDriverWait wait = new WebDriverWait(Ad, timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
	}
	
	/**
	 * 
	 * @param driver
	 * @param timeOutInSeconds
	 * @param byLocator
	 */
	public static void waitForElementToBeClickable(AppiumDriver<MobileElement> Ad, int timeOutInSeconds, By byLocator) {
		WebDriverWait wait = new WebDriverWait(Ad, timeOutInSeconds);
		wait.until(ExpectedConditions.elementToBeClickable(byLocator));
	}
	
	
	
	/**
	 * 
	 * @param driver
	 */
	public static void switchToDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}
	
	/**
	 * 
	 * @param driver
	 * @param element
	 */
	public static void moveToElementAndClickUsingActionsClass(AppiumDriver<MobileElement> Ad, WebElement element) {
		Actions actions = new Actions(Ad); 
		actions.moveToElement(element);
	    actions.click().build().perform();
	}
	
	/**
	 * 
	 * @param milliSeconds
	 * @throws Exception
	 */
	public static void waitForMilliSeconds(long milliSeconds) throws Exception {
		Thread.sleep(milliSeconds);
	}

	
}

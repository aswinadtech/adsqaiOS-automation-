package com.twc.ios.app.general;

//import org.apache.log4j.Logger;
//import org.apache.log4j.PropertyConfigurator;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;

import com.twc.ios.app.functions.Functions;

import junit.framework.Assert;

@Listeners(value = MyTestListenerAdapter.class)
public class RetryAnalyzer implements IRetryAnalyzer {
	//public static int retryCount = 0;
	//public static int maxRetryCount = 1;

	public static int count = 0;
	public static int maxCount = 1;

	// Below method returns 'true' if the test method has to be retried else 'false' 
	//and it takes the 'Result' as parameter of the test method that just ran
	public boolean retry(ITestResult result) {
		if (count < maxCount) {
			System.out.println("Retrying test " + result.getName() + " with status "
					+ getResultStatusName(result.getStatus()) + " for the " + (count+1) + " time(s).");
			count++;
			return true;

		}else {
			count = 0;
			Assert.fail(Functions.Exception);
		}

		return false;
	}

	public String getResultStatusName(int status) {
		String resultName = null;
		if(status==1)
			resultName = "SUCCESS";
		if(status==2)
			resultName = "FAILURE";
		if(status==3)
			resultName = "SKIP";
		return resultName;
	}




}

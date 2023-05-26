package com.twc.ios.app.general;

import java.util.Iterator;

import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.annotations.Listeners;
//import retrylistener.RetryException;
//import ru.yandex.qatools.allure.Allure;.
import io.qameta.allure.Allure;
//import ru.yandex.qatools.allure.events.StepStartedEvent;
//import io.qameta.allure.Step;
//import ru.yandex.qatools.allure.events.TestCaseFinishedEvent;

//import ru.yandex.qatools.allure.events.TestCasePendingEvent;
@Listeners(value = MyTestListenerAdapter.class)
public class MyTestListenerAdapter extends TestListenerAdapter {

	@Override
	public void onFinish(ITestContext context) {
		Iterator<ITestResult> failedTestCases =context.getFailedTests().getAllResults().iterator();
		while (failedTestCases.hasNext()) {
			System.out.println("failedTestCases");
			ITestResult failedTestCase = failedTestCases.next();
			ITestNGMethod method = failedTestCase.getMethod();
			if (context.getFailedTests().getResults(method).size() > 1) {
				System.out.println("failed test case remove as dup:" + failedTestCase.getTestClass().toString());
				failedTestCases.remove();
				
			} else {

				if (context.getPassedTests().getResults(method).size() > 0) {
					System.out.println("failed test case remove as pass retry:" + failedTestCase.getTestClass().toString());
					failedTestCases.remove();
					
				}
			}
		}
		Iterator<ITestResult> skippedTestCases =context.getSkippedTests().getAllResults().iterator();

		while (skippedTestCases.hasNext()) {
			System.out.println("skippedTestCases");
			ITestResult skippedTestCase = skippedTestCases.next();
			ITestNGMethod method = skippedTestCase.getMethod();
			if (context.getSkippedTests().getResults(method).size() > 0) {
				System.out.println("skipped test case remove as dup:" + skippedTestCase.getTestClass().toString());
				skippedTestCases.remove();
				
			} else {

				if (context.getPassedTests().getResults(method).size() > 0) {
					System.out.println("Skipped test case remove as pass retry:" + skippedTestCase.getTestClass().toString());
					skippedTestCases.remove();
				}
			}
		}
	}
}

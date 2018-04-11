package com.vv.test.automation.listeners;

import org.testng.*;

import com.relevantcodes.extentreports.LogStatus;
import com.vv.test.automation.utilities.CommonVariables;
import com.vv.test.automation.web.accelerators.ActionsLibrary;


public class ExecutionListener extends ActionsLibrary implements ITestListener, ISuiteListener, IInvokedMethodListener,IExecutionListener {

	// This belongs to ISuiteListener and will execute before the Suite start
	@Override
	public void onStart(ISuite arg0) {
		Reporter.log("About to begin executing Suite :- " + arg0.getName(), true);
	}

	// This belongs to ISuiteListener and will execute, once the Suite is finished
	@Override
	public void onFinish(ISuite arg0) {
		Reporter.log("About to end executing Suite :- " + arg0.getName(), true);
		//	CommonVariables.extentTest.get().log(LogStatus.INFO,"About to end executing Suite " + arg0.getName());
	}

	// This belongs to ITestListener and will execute before starting of Test set/batch
	public void onStart(ITestContext arg0) {
		Reporter.log("About to begin executing Test :- " + arg0.getName(), true);
		//	CommonVariables.extentTest.get().log(LogStatus.INFO,"About to begin executing Test" + arg0.getName());
	}

	// This belongs to ITestListener and will execute, once the Test set/batch is finished
	public void onFinish(ITestContext arg0) {
		Reporter.log("Completed executing test :- " + arg0.getName(), true);
		//	CommonVariables.extentTest.get().log(LogStatus.INFO,"Completed executing test " + arg0.getName());
	}

	// This belongs to ITestListener and will execute before the main test start (@Test)
	public void onTestStart(ITestResult arg0) {
		Reporter.log("About to begin Test :- " + arg0.getName(), true);
		//CommonVariables.extentTest.get().log(LogStatus.INFO,"About to begin Test " + arg0.getName());
	}

	// This belongs to ITestListener and will execute only when the test is pass
	public void onTestSuccess(ITestResult arg0) {
		CommonVariables.extentTest.get().log(LogStatus.PASS,"Status","Feature PASSED.");
	}

	// This belongs to ITestListener and will execute only on the event of fail test
	public void onTestFailure(ITestResult arg0) {
		CommonVariables.extentTest.get().log(LogStatus.FAIL,"Status","Feature FAILED.");		
	}

	// This belongs to ITestListener and will execute only if any of the main test(@Test) get skipped
	public void onTestSkipped(ITestResult arg0) {
		CommonVariables.extentTest.get().log(LogStatus.SKIP,"Status","TEST CASE SKIPPED.");
	}

	// This is just a piece of shit, ignore this
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		//CommonVariables.extentTest.get().log(LogStatus.WARNING,"Status","Test Failed But Within Success Percentage : "+arg0.SUCCESS_PERCENTAGE_FAILURE);
	}

	// This belongs to IInvokedMethodListener and will execute before every method including @Before @After @Test
	public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {
		String textMsg = "About to begin executing method :- " + returnMethodName(arg0.getTestMethod());
		Reporter.log(textMsg, true);		
	}

	// This belongs to IInvokedMethodListener and will execute after every method including @Before @After @Test
	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
		String textMsg = "Completed executing method  :- " + returnMethodName(arg0.getTestMethod());
		Reporter.log(textMsg, true);
	}

	// This will return method names to the calling function
	private String returnMethodName(ITestNGMethod method) {
		return method.getRealClass().getSimpleName() + "." + method.getMethodName();
	}

	@Override
	public void onExecutionStart() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onExecutionFinish() {
		// TODO Auto-generated method stub
	}
}
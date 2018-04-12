package com.vv.test.automation.web.stepdefinition;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.google.common.base.Optional;
import com.relevantcodes.extentreports.LogStatus;
import com.vv.test.automation.fileutils.ExcelReader;
import com.vv.test.automation.logs.ExtentLogs;
import com.vv.test.automation.utilities.CommonVariables;
import com.vv.test.automation.web.accelerators.ActionsLibrary;
import com.vv.test.automation.web.accelerators.TestEngineWeb;
import com.vv.test.automation.web.pages.BookACruisePage;
import com.vv.test.automation.web.pages.SignUpPage;
import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.testng.TestNGCucumberRunner;

@CucumberOptions(features = "src/test/resources/feature/BookACruise.feature", plugin = "json:target/BookACruise.json", tags = "@BookACruise")
public class BookACruiseStep extends TestEngineWeb{
	
	
	/** The action lib. */
	public ActionsLibrary actionLib;

	/** The timeout in second. */
	private Optional<Long> timeoutInSecond = Optional.of(Long.parseLong("5"));

	/** The scenariotxt. */
	public String url, scenariotxt,month,date,day,from,to;

	/** The sign up page. */
	BookACruisePage bookCruise;

	/** The test case failure reason. */
	private String testCaseFailureReason = "";

	/** The test case status. */
	public boolean testCaseStatus = true;

	/** The scenario name. */
	private String scenarioName;

	/** The scenario. */
	public Scenario scenario;

	/** The sheet path. */
	protected String sheetPath = System.getProperty("user.dir").replace("\\", "/") + "/testdata/testData.xlsx";

	/** The sheet name. */
	protected String sheetName = "TestData";

	/** The testdata. */
	Map<String, List<String>> testdata = null;

	/** The extent logs. */
	private ExtentLogs extentLogs = new ExtentLogs();

	/** The logger. */
	private Logger LOGGER = LoggerFactory.getLogger(SignUpAsLearnerStep.class.getName());

	/**
	 * Agency end to end.
	 */
	@Test(groups = "smoke", description = "BookACruise")
	public void BookACruiseStep() {
		new TestNGCucumberRunner(getClass()).runCukes();
	}

	/**
	 * Test case status.
	 *
	 * @param status
	 *            the status
	 * @param reason
	 *            the reason
	 */
	public void TestCaseStatus(Boolean status, String reason) {
		if (status == false) {
			CommonVariables.extentTest.get().log(LogStatus.FAIL, "Status", "Scenario Failed.");
			Assert.fail("Test Case Failed because - " + reason);
		} else {
			CommonVariables.extentTest.get().log(LogStatus.PASS, "Status", "Scenario Passed.");
		}
	}

	/**
	 * After.
	 *
	 * @param scenario
	 *            the scenario
	 */
	@After("@BookACruise")
	public void after(Scenario scenario) {
		extentLogs.info(scenario.getName().toString() + " Scenario", "Ends");
		LOGGER.info("This is after scenario: " + scenario.getName().toString());
		TestCaseStatus(testCaseStatus, testCaseFailureReason);
	}

	/**
	 * Before method.
	 *
	 * @param scenario
	 *            the scenario
	 */
	@Before("@BookACruise")
	public void beforeMethod(Scenario scenario) {
		try {
			this.scenario = scenario;
			String scenarioId = scenario.getId();
			String rowNumber = scenarioId.split("--")[1];
			if (rowNumber.contains(";;")) {
				rowNumber = rowNumber.split(";;")[0];
			}
			int row = Integer.parseInt(rowNumber);
			extentLogs.info(scenario.getName().toString() + " Scenario", "Starts");
			LOGGER.info("This is before scenario: " + scenario.getName().toString());
			scenariotxt = scenario.getName().toString();
			this.url = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, row, "Url");
			this.month = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, row, "Month");
			this.day = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, row, "Day");
			this.date = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, row, "Date");
			this.from = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, row, "From");
			this.to = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, row, "To");
			bookCruise = new BookACruisePage(CommonVariables.CommonDriver.get());

			actionLib = CommonVariables.getActionLib();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

@Given("^the user has the access to TallLink$")
public void the_user_has_the_access_to_TallLink() throws Throwable {
	try {
		actionLib.OpenUrl(url);
	
	} catch (Exception e) {
		testCaseFailureReason = "Failed to complete - "
				+ "the user has the access to TallLink";
		String stackTrace = extentLogs.getStackTraceAsString("Test", testCaseFailureReason, e);
		extentLogs.fail(scenarioName, testCaseFailureReason + "Failed Reason : " + stackTrace);
		testCaseStatus = false;
	}
}

@When("^the user is able to book a cruise$")
public void the_user_is_able_to_book_a_cruise() throws Throwable {  
	try {
		/*WebElement date =CommonVariables.CommonDriver.get().findElement(bookCruise.selectDate(month, day));
		System.out.println("xpath is : "+date);
		actionLib.Click(date,10,"selecting date");*/
		WebElement selectDate =CommonVariables.CommonDriver.get().findElement(bookCruise.selectDate(date));
		actionLib.Click(selectDate,10,"selecting date");
	
		actionLib.Click(CommonVariables.CommonDriver.get().findElement(bookCruise.btnAdults),10,"selecting adults");
		actionLib.Click(CommonVariables.CommonDriver.get().findElement(bookCruise.btnChildrens),10,"selecting childrens");
		actionLib.Click(CommonVariables.CommonDriver.get().findElement(bookCruise.selectFromIcon),10,"click Icon");
		actionLib.Click(CommonVariables.CommonDriver.get().findElement(bookCruise.departureFrom(from)),10,"click departure from");
		//bookCruise.departureTo(to);
		actionLib.Click(CommonVariables.CommonDriver.get().findElement(bookCruise.selectTo),10,"click departure to");
		actionLib.Click(CommonVariables.CommonDriver.get().findElement(bookCruise.btnBookYourTrip),10,"click on book your trip");
		String mainwindow = CommonVariables.CommonDriver.get().getWindowHandle();
		actionLib.switchToLatestWindow(CommonVariables.CommonDriver.get());
	    actionLib.WaitForElementVisible(bookCruise.btnVechiclle, 100000, "clickk on vechicle");

		actionLib.Click(CommonVariables.CommonDriver.get().findElement(bookCruise.btnVechiclle),10,"click on vechicle");
	    actionLib.WaitForElementVisible(bookCruise.vechicleIcon, 100000, "clickk on vechicle");
		actionLib.Click(CommonVariables.CommonDriver.get().findElement(bookCruise.vechicleIcon),10,"click on vechicle Icon");
		
		actionLib.WaitForElementVisible(bookCruise.selectCar, 100000, "clickk on vechicle");
		actionLib.Click(CommonVariables.CommonDriver.get().findElement(bookCruise.selectCar),10,"click on car Icon");
		actionLib.Click(CommonVariables.CommonDriver.get().findElement(bookCruise.clickPlusIcon),10,"click on car Icon");
		actionLib.Click(CommonVariables.CommonDriver.get().findElement(bookCruise.clickPlusIcon),10,"click on car Icon");
		actionLib.Click(CommonVariables.CommonDriver.get().findElement(bookCruise.btnBookTrip),10,"click on btnBookTrip");

		
	} catch (Exception e) {
		testCaseFailureReason = "Failed to complete - "
				+ "the user is able to book a cruise";
		String stackTrace = extentLogs.getStackTraceAsString("Test", testCaseFailureReason, e);
		extentLogs.fail(scenarioName, testCaseFailureReason + "Failed Reason : " + stackTrace);
		testCaseStatus = false;
	}
}

@Then("^user should be able to navigate Payment page$")
public void user_should_be_able_to_navigate_Payment_page() throws Throwable {
	try {
		
	} catch (Exception e) {
		testCaseFailureReason = "Failed to complete - "
				+ "user should be able to navigate Payment page";
		String stackTrace = extentLogs.getStackTraceAsString("Test", testCaseFailureReason, e);
		extentLogs.fail(scenarioName, testCaseFailureReason + "Failed Reason : " + stackTrace);
		testCaseStatus = false;
	} 
}

}


package com.vv.test.automation.web.stepdefinition;

import static org.junit.Assert.assertTrue;
import java.util.List;
import java.util.Map;
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
import com.vv.test.automation.web.pages.SignUpPage;
import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.testng.TestNGCucumberRunner;

/**
 * The Class SignUpAsLearnerStep.
 */
@CucumberOptions(features = "src/test/resources/feature/SignUp.feature", plugin = "json:target/SignuUp.json", tags = "@KhanAcademySignuUp")

public class SignUpAsLearnerStep extends TestEngineWeb {

	/** The action lib. */
	public ActionsLibrary actionLib;

	/** The timeout in second. */
	private Optional<Long> timeoutInSecond = Optional.of(Long.parseLong("5"));

	/** The scenariotxt. */
	public String url, scenariotxt, day, year, month;

	/** The sign up page. */
	SignUpPage signUpPage;

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
	@Test(groups = "smoke", description = "KhanAcademy")
	public void SignUp() {
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
	@After("@KhanAcademySignuUp")
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
	@Before("@KhanAcademySignuUp")
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
			this.day = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, row, "Day");
			this.month = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, row, "Month");
			this.year = ExcelReader.fn_GetCellData(this.sheetPath, this.sheetName, row, "Year");
			signUpPage = new SignUpPage(CommonVariables.CommonDriver.get());

			actionLib = CommonVariables.getActionLib();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * The user has the access to khan academy and want to sign up as a learner.
	 *
	 * @throws Throwable
	 *             the throwable
	 */
	@Given("^the user has the access to Khan Academy and want to sign up as a Learner$")
	public void the_user_has_the_access_to_Khan_Academy_and_want_to_sign_up_as_a_Learner() throws Throwable {
		try {
			signUpPage.signUpHome();

		} catch (Exception e) {
			testCaseFailureReason = "Failed to complete - "
					+ "the user has the access to Khan Academy and want to sign up as a Learner";
			String stackTrace = extentLogs.getStackTraceAsString("Test", testCaseFailureReason, e);
			extentLogs.fail(scenarioName, testCaseFailureReason + "Failed Reason : " + stackTrace);
			testCaseStatus = false;
		}

	}

	/**
	 * User enters valid data for signup.
	 *
	 * @throws Throwable
	 *             the throwable
	 */
	@When("^user enters valid data for signup$")
	public void user_enters_valid_data_for_signup() throws Throwable {

		try {
			signUpPage.signUpLearner(day, month, year);

		} catch (Exception e) {
			testCaseFailureReason = "Failed to complete - " + "user enters valid data for signup";
			String stackTrace = extentLogs.getStackTraceAsString("Test", testCaseFailureReason, e);
			extentLogs.fail(scenarioName, testCaseFailureReason + "Failed Reason : " + stackTrace);
			testCaseStatus = false;
		}
	}

	/**
	 * User should be able to signup successfully as a learner.
	 *
	 * @throws Throwable
	 *             the throwable
	 */
	@Then("^user should be able to signup successfully as a Learner$")
	public void user_should_be_able_to_signup_successfully_as_a_Learner() throws Throwable {
		try {

			String title = CommonVariables.CommonDriver.get().getTitle();
			assertTrue(title.contains("Khan Academy"));

		} catch (Exception e) {
			testCaseFailureReason = "Failed to complete - " + "user should be able to signup successfully as a Learner";
			String stackTrace = extentLogs.getStackTraceAsString("Test", testCaseFailureReason, e);
			extentLogs.fail(scenarioName, testCaseFailureReason + "Failed Reason : " + stackTrace);
			testCaseStatus = false;
		}
	}

}

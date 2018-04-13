package com.vv.test.automation.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.google.common.base.Optional;
import com.vv.test.automation.logs.ExtentLogs;
import com.vv.test.automation.utilities.CommonVariables;
import com.vv.test.automation.utilities.ConfigManager;
import com.vv.test.automation.web.accelerators.ActionsLibrary;

/**
 * The Class SignUpPage.
 */
public class SignUpPage {

	/** The driver. */
	public WebDriver driver;

	/** The action lib. */
	public ActionsLibrary actionLib;

	/** The extent logs. */
	public ExtentLogs extentLogs = new ExtentLogs();

	/** The timeout in second. */
	Optional<Long> timeoutInSecond = Optional.of(Long.parseLong("5"));

	/** The sign up home. */
	public By signUpHome = By.xpath("//a[text()='Sign up']");

	/** The learner button. */
	public By learnerButton = By.xpath("//*[contains(text(),'Learner')]");

	/** The month drop down. */
	public By monthDropDown = By.xpath("//div[@class='datePicker_py3uvl']//select[contains(@name,'birth[month]')]");

	/** The day drop down. */
	public By dayDropDown = By.xpath("//div[@class='datePicker_py3uvl']//select[contains(@class,'birth-day')]");

	/** The year drop down. */
	public By yearDropDown = By.xpath("//div[@class='datePicker_py3uvl']//select[contains(@class,'birth-year')]");

	/** The teacher button. */
	public By teacherButton = By.xpath("//*[contains(text(),'Teacher')]");

	/** The sign upwith mail. */
	public By signUpwithMail = By.xpath("//*[contains(text(),'Sign up with Email')]");

	/** The continue withface book. */
	public By continueWithfaceBook = By.xpath("//*[text()='Continue with Facebook']");

	/** The continue with google. */
	public By continueWithGoogle = By.xpath("//*[text()='Continue with Google']");

	/** The mail text box. */
	public By mailTextBox = By.xpath("//input[@type='email']");

	/** The first name text box. */
	public By firstNameTextBox = By.xpath(
			"//label[starts-with(@class,'label_') and contains( text(),'First name')] /form[starts-with(@class,'input_')]/input[@type='text']");

	/** The last name text box. */
	public By lastNameTextBox = By.xpath(
			"//label[starts-with(@class,'label_') and contains( text(),'Last name')] /form[starts-with(@class,'input_')]/input[@type='text']");

	/** The password text box. */
	public By passwordTextBox = By.xpath("//input[@type='password']");

	/** The sign up button. */
	public By signUpButton = By.xpath("//div[text()='Sign up']");

	/**
	 * Instantiates a new sign up page.
	 *
	 * @param driver
	 *            the driver
	 */
	public SignUpPage(WebDriver driver) {

		if (CommonVariables.getDriver() == null) {
			CommonVariables.setDriver(driver);
		}
		actionLib = CommonVariables.getActionLib();
		try {
			if (!ConfigManager.ArePropertiesSet.get()) {
				ConfigManager.setProperties();
				ConfigManager.UpdateProperties();
			}
		} catch (Exception e) {
			System.out.println("Failed to load Properties file");
		}
	}

	/**
	 * Sign up home.
	 */
	public void signUpHome() {

		actionLib.Click(CommonVariables.CommonDriver.get().findElement(signUpHome), 10, "signUpHome");
	}

	/**
	 * Sign up learner.
	 *
	 * @param day
	 *            the day
	 * @param month
	 *            the month
	 * @param year
	 *            the year
	 * @throws Throwable
	 *             the throwable
	 */
	// a learner specific method
	public void signUpLearner(String day, String month, String year) throws Throwable {
		actionLib.Click(CommonVariables.CommonDriver.get().findElement(learnerButton), 10, "learnerButton");
		actionLib.selectByVisibleText(monthDropDown, month, "selecting month");
		actionLib.selectByVisibleText(dayDropDown, day, "selecting day");
		actionLib.selectByVisibleText(yearDropDown, year, "selecting month");
		actionLib.Click(CommonVariables.CommonDriver.get().findElement(signUpwithMail), 10, "learnerButton");

	}

	/**
	 * Sign up teacher.
	 */
	public void signUpTeacher() {
		actionLib.Click(CommonVariables.CommonDriver.get().findElement(teacherButton), 10, "learnerButton");

	}

	/**
	 * Sign up with mail user details.
	 *
	 * @param mailId
	 *            the mail id
	 * @param first
	 *            the first
	 * @param last
	 *            the last
	 * @param pass
	 *            the pass
	 * @throws Throwable
	 *             the throwable
	 */
	// same for learner, parent and teacher
	public void signUpWithMailUserDetails(String mailId, String first, String last, String pass) throws Throwable {

		actionLib.type(CommonVariables.CommonDriver.get().findElement(mailTextBox), mailId);
		actionLib.type(CommonVariables.CommonDriver.get().findElement(firstNameTextBox), first);
		actionLib.type(CommonVariables.CommonDriver.get().findElement(lastNameTextBox), last);
		actionLib.type(CommonVariables.CommonDriver.get().findElement(passwordTextBox), pass);
		actionLib.Click(CommonVariables.CommonDriver.get().findElement(signUpButton), "signUpButton");

	}

}

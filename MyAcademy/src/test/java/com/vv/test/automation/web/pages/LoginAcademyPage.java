package com.vv.test.automation.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.google.common.base.Optional;
import com.vv.test.automation.logs.ExtentLogs;
import com.vv.test.automation.utilities.CommonVariables;
import com.vv.test.automation.utilities.ConfigManager;
import com.vv.test.automation.web.accelerators.ActionsLibrary;

/**
 * The Class LoginAcademyPage.
 */
public class LoginAcademyPage {
	/** The driver. */
	public WebDriver driver;

	/** The action lib. */
	public ActionsLibrary actionLib;

	/** The extent logs. */
	public ExtentLogs extentLogs = new ExtentLogs();

	/** The timeout in second. */
	Optional<Long> timeoutInSecond = Optional.of(Long.parseLong("5"));

	/** The sign up home. */
	public By logInPage = By.xpath("//a[contains(text(),'Login')]");

	/** The user name. */
	public By userName = By.xpath("//input[contains(@class,'input') and @type='text']");
	
	/** The password. */
	public By password = By.xpath("//input[contains(@type,'password')]");
	
	/** The Login. */
	public By Login = By.xpath("//*[text()='Log in']");
	
	/** The user profile. */
	public By userProfile = By
			.xpath("//span[@data-test-id='header-profile-button']/span[contains(@class,nicknameDisplay)]");
	
	/** The log out. */
	public By logOut = By.xpath("//a[text()='Log out']");
	
	/** The continue with FB. */
	public By continueWithFB = By.xpath("//*[contains(text(),'Continue with Facebook')]");
	
	/** The user name error message. */
	public By userNameErrorMessage = By
			.xpath("//*[contains(@class,'fieldRequirement') and contains(text(),'An email or username')]");

	/**
	 * Instantiates a new login academy page.
	 *
	 * @param driver the driver
	 */
	public LoginAcademyPage(WebDriver driver) {

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
	 * Click continuewith FB.
	 */
	public void clickContinuewithFB() {
		actionLib.Click(CommonVariables.CommonDriver.get().findElement(continueWithFB), 10,
				"Continue With FaceBook Button");

	}

	/**
	 * Click login page.
	 */
	public void clickLoginPage() {
		actionLib.Click(CommonVariables.CommonDriver.get().findElement(logInPage), 10, "Login Page Button");

	}

	/**
	 * Login.
	 */
	public void login() {
		actionLib.Click(CommonVariables.CommonDriver.get().findElement(Login), 10, "Login Button");
	}

	/**
	 * Gets the user profile name.
	 *
	 * @return the user profile name
	 */
	public String getUserProfileName() {

		return CommonVariables.CommonDriver.get().findElement(userProfile).getText();
	}

	/**
	 * Log out.
	 */
	public void logOut() {
		actionLib.Click(CommonVariables.CommonDriver.get().findElement(userProfile), 10, "User Profile Button");
		actionLib.Click(CommonVariables.CommonDriver.get().findElement(logOut), 10, "LogOut Button");
	}

	/**
	 * Login with credentials.
	 *
	 * @param userName the user name
	 * @param password the password
	 * @throws Throwable the throwable
	 */
	public void loginWithCredentials(String userName, String password) throws Throwable {
		this.clickLoginPage();
		actionLib.type(CommonVariables.CommonDriver.get().findElement(this.userName), userName);
		actionLib.type(CommonVariables.CommonDriver.get().findElement(this.password), password);
		actionLib.Click(CommonVariables.CommonDriver.get().findElement(Login), 10, "LogIn Button");

	}

	/**
	 * Login blank.
	 *
	 * @throws Throwable the throwable
	 */
	public void loginBlank() throws Throwable {
		actionLib.Click(CommonVariables.CommonDriver.get().findElement(logInPage), 10, "LogIn Page");

		String error = CommonVariables.CommonDriver.get().findElement(userNameErrorMessage).getText();

		System.out.println("Error: " + error);
	}

}

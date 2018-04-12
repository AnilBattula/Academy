package com.vv.test.automation.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.google.common.base.Optional;
import com.vv.test.automation.logs.ExtentLogs;
import com.vv.test.automation.utilities.CommonVariables;
import com.vv.test.automation.utilities.ConfigManager;
import com.vv.test.automation.web.accelerators.ActionsLibrary;

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
	
	public By userName = By.xpath("//input[contains(@class,'input') and @type='text']");
	public By password = By.xpath("//input[contains(@type,'password')]");
	public By Login = By.xpath("//*[text()='Log in']");
	public By userProfile = By.xpath("//span[@data-test-id='header-profile-button']/span[contains(@class,nicknameDisplay)]");
	public By logOut = By.xpath("//a[text()='Log out']");
	public By continueWithFB = By.xpath("//*[contains(text(),'Continue with Facebook')]");
	public By userNameErrorMessage = By.xpath("//*[contains(@class,'fieldRequirement') and contains(text(),'An email or username')]");

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
		//******************methods
		//continue with face book
		public void clickContinuewithFB(){
			actionLib.Click(CommonVariables.CommonDriver.get().findElement(continueWithFB), 10, "Continue With FaceBook Button");
			
		}
		
		//navigating to Login Page
		public void clickLoginPage(){
		actionLib.Click(CommonVariables.CommonDriver.get().findElement(logInPage), 10, "Login Page Button");

			
		}
		
		//clicking on Login
		public void login(){
		actionLib.Click(CommonVariables.CommonDriver.get().findElement(Login), 10, "Login Button");
			
		}
		
		//getting profile name
		public String getUserProfileName(){

			return CommonVariables.CommonDriver.get().findElement(userProfile).getText();
		}
		
		//logingout
		public void logOut(){
			actionLib.Click(CommonVariables.CommonDriver.get().findElement(userProfile), 10, "User Profile Button");
			actionLib.Click(CommonVariables.CommonDriver.get().findElement(logOut), 10, "LogOut Button");
		}
		
		
		
		
		//******************************methods for each work flow
		public void loginWithCredentials(String userName, String password) throws Throwable{
			this.clickLoginPage();
			actionLib.type(CommonVariables.CommonDriver.get().findElement(this.userName), userName);
			actionLib.type(CommonVariables.CommonDriver.get().findElement(this.password), password);
			actionLib.Click(CommonVariables.CommonDriver.get().findElement(Login), 10, "LogIn Button");

		}
			
		public void loginBlank() throws Throwable{
			actionLib.Click(CommonVariables.CommonDriver.get().findElement(logInPage), 10, "LogIn Page");
			//actionLib.type(CommonVariables.CommonDriver.get().findElement(this.userName),"");

			 String error=CommonVariables.CommonDriver.get().findElement(userNameErrorMessage).getText();
			
			 System.out.println("Error: "+error);
		}
				
		
	
	
}

/*
 * 
 */
package com.vv.test.automation.common.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.google.common.base.Optional;
import com.vv.test.automation.logs.ExtentLogs;
import com.vv.test.automation.utilities.CommonVariables;
import com.vv.test.automation.utilities.ConfigManager;
import com.vv.test.automation.web.accelerators.ActionsLibrary;

/**
 * The Class DotComHomePage. This class contains all the objects related to
 * DotComHomePage and reusable method libraries related to business scenarios in
 * DotComHomePage
 */
public class DotComHomePage {

	/** The lnk home logo. */
	public By lnkHomeLogo = By.cssSelector(".headerbar-logo>img");

	/** The lnk sign up for special offers. */
	public By lnkSignUpForSpecialOffers = By
			.xpath(".//*[@data-nav-name='Sign Up for Special Offers']");

	/** The lnk sign in register. */
	public By lnkSignInRegister = By
			.xpath(".//*[@data-sign-out-text='Sign In / Register']");

	/** The lnk my account. */
	public By lnkMyAccount = By.xpath(".//*[@data-sign-in-text='My Account']");

	/** The lnk sign out. */
	public By lnkSignOut = By.xpath(".//*[@class='my-account-signout-btn']");
	/** The lbl username. */
	public By lblUsername = By.xpath("//span[contains(@class,'guest-name')]");

	/** The lbl cruise booking. */
	public By lblCruiseBooking = By.xpath(".//*[text()='Manage This Booking']");

	/** The lbl cruise sailed. */
	public By lblCruiseSailed = By
			.xpath(".//*[contains(@class,'cruises-sailed')]");

	/** The lbl total days. */
	public By lblTotalDays = By.xpath(".//*[contains(@class,'total-days')]");

	/** The lbl guest blue. */
	public By lblGuestBlue = By
			.xpath(".//*[@class='guest-name-wrapper']/*[text()='Mr. Blue Pax']");

	/** The driver. */
	public WebDriver driver;

	/** The action lib. */
	public ActionsLibrary actionLib;

	/** The extent logs. */
	public ExtentLogs extentLogs = new ExtentLogs();


	/** The tab names. */
	private String[] tabNames = { "Plan a Cruise", "Onboard Our Ships",
			"Destinations", "Booked Guests" };

	/** The timeout in second. */
	Optional<Long> timeoutInSecond = Optional.of(Long.parseLong("-1"));

	/**
	 * Instantiates a new dot com home page.
	 *
	 * @param driver
	 *            the driver
	 */
	public DotComHomePage(WebDriver driver) {
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
	 * Timeout.
	 *
	 * @param time
	 *            the time
	 * @return the optional
	 */
	public Optional<Long> timeout(Integer time) {
		return Optional.of(Long.parseLong(String.valueOf(time)));
	}

	/**
	 * Click link home logo.
	 */
	public void clickLinkHomeLogo() {
		try {
			actionLib
			.Click(CommonVariables.CommonDriver.get().findElement(
					lnkHomeLogo), "Home logo");
			extentLogs.pass("click Home page link",
					"Clicked on Home Page link successfully.");
			actionLib.compareValues(actionLib.GetURl(),
					"https://stagewww.princess.com/", "URL");

		} catch (Exception e) {
			String stackTrace = extentLogs.getStackTraceAsString("Test",
					e.getMessage(), e);
			extentLogs.fail("clickLinkHomeLogo", "Failed Reason : "
					+ stackTrace);
		}

	}

	/**
	 * Click link sign up for special offers.
	 */
	public void clickLinkSignUpForSpecialOffers() {
		try {
			actionLib.Click(actionLib.FindElement(lnkSignUpForSpecialOffers,
					timeoutInSecond), "Sign Up For SpecialOffers");
			extentLogs.pass("click Sign up for special offers link",
					"Clicked on Sign up for special offers link successfully.");
		} catch (Exception e) {
			String stackTrace = extentLogs.getStackTraceAsString("Test",
					e.getMessage(), e);
			extentLogs.fail("clickLinkSignUpForSpecialOffers",
					"Failed Reason : " + stackTrace);
		}

	}

	/**
	 * Click link sign in register.
	 */
	public void clickLinkSignIn_Register() {
		try {
			actionLib.Click(
					CommonVariables.CommonDriver.get().findElement(
							lnkSignInRegister), "Sign In");
			for (String ar : tabNames) {
				By existedTabsAre = By.xpath("//a[@data-us-text='" + ar + "']");
				Assert.assertEquals(
						actionLib.FindElement(existedTabsAre, timeout(5))
						.getText(), ar);
			}
			extentLogs.pass("click Sign in - Register link",
					"Clicked on Sign in - Register link successfully.");
		} catch (Exception e) {
			String stackTrace = extentLogs.getStackTraceAsString("Test",
					e.getMessage(), e);
			extentLogs.fail("clickLinkSignIn_Register", "Failed Reason : "
					+ stackTrace);
		}

	}

}

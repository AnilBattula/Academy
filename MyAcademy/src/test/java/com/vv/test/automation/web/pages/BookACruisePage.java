package com.vv.test.automation.web.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.google.common.base.Optional;
import com.vv.test.automation.logs.ExtentLogs;
import com.vv.test.automation.utilities.CommonVariables;
import com.vv.test.automation.utilities.ConfigManager;
import com.vv.test.automation.web.accelerators.ActionsLibrary;

/**
 * The Class BookACruisePage.
 */
public class BookACruisePage {

	/** The driver. */
	public WebDriver driver;

	/** The action lib. */
	public ActionsLibrary actionLib;

	/** The extent logs. */
	public ExtentLogs extentLogs = new ExtentLogs();

	/** The timeout in second. */
	Optional<Long> timeoutInSecond = Optional.of(Long.parseLong("5"));

	/** The btn adults. */
	public By btnAdults = By.xpath("//input[@id='passengers-adults']/following-sibling::button[@class='increase']");
	
	/** The btn childrens. */
	public By btnChildrens = By.xpath("//input[@id='passengers-juniors']/following-sibling::button[@class='increase']");
	
	/** The btn book your trip. */
	public By btnBookYourTrip = By.xpath("//a[text()='Book your trip' and @class='searchButton']");
	
	/** The select from icon. */
	public By selectFromIcon = By.xpath("//span[@class='icon']");
	
	/** The select to. */
	public By selectTo = By.xpath("//label[contains(@class,'destinationPortButton')]/span[text()='Riga']");
	
	/** The btn vechiclle. */
	public By btnVechiclle = By.xpath("//div[@class='siteContent booking']//span[@class='vehicles']");
	
	/** The vechicle icon. */
	public By vechicleIcon = By.xpath("//div[@class='ddTitle']/*[@class='carIcon vehicles-white-novehicle']");
	
	/** The btn book trip. */
	public By btnBookTrip = By.xpath("//button[text()='Book the trip']");
	
	/** The click plus icon. */
	public By clickPlusIcon = By.xpath("//div[text()='DeLuxe']/../../../td[2]//button/div");
	
	/** The select car. */
	public By selectCar = By.xpath("//table[contains(@class,'vehicleDropDown')]/tbody/tr/td[2]");

	/**
	 * Select date.
	 *
	 * @param date the date
	 * @return the by
	 */
	public By selectDate(String date) {
		By date1 = By.xpath("//table[@class='calendarBody']//*[@date-iso='" + date + "']");
		return date1;
	}

	/**
	 * Destination.
	 *
	 * @param dest the dest
	 * @return the by
	 */
	public By destination(String dest) {
		By selectTo = By.xpath("//label[contains(@class,'destinationPortButton')]/span[text()='" + dest + "']");
		return selectTo;
	}

	/**
	 * Departure from.
	 *
	 * @param departureFrom the departure from
	 * @return the by
	 */
	public By departureFrom(String departureFrom) {
		By departure = By
				.xpath("//div[@class='departurePortSelection']//ul/li[contains(text(),'" + departureFrom + "')]");
		return departure;
	}

	/**
	 * Departure to.
	 *
	 * @param To the to
	 */
	public void departureTo(String To) {
		List<WebElement> values = CommonVariables.CommonDriver.get()
				.findElements(By.xpath("//div[@class='destinationPortSelection']/label/span"));
		for (WebElement webelem : values) {

			String s = webelem.getText().trim();
			if (s.equalsIgnoreCase(To)) {

				webelem.click();

				break;

			}
		}
	}


	/**
	 * Instantiates a new book A cruise page.
	 *
	 * @param driver the driver
	 */
	public BookACruisePage(WebDriver driver) {

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
}

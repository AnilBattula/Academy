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

public class BookACruisePage {

	/** The driver. */
	public WebDriver driver;

	/** The action lib. */
	public ActionsLibrary actionLib;

	/** The extent logs. */
	public ExtentLogs extentLogs = new ExtentLogs();

	/** The timeout in second. */
	Optional<Long> timeoutInSecond = Optional.of(Long.parseLong("5"));


	public By btnAdults = By.xpath("//input[@id='passengers-adults']/following-sibling::button[@class='increase']");
	public By btnChildrens = By.xpath("//input[@id='passengers-juniors']/following-sibling::button[@class='increase']");
	public By btnBookYourTrip = By.xpath("//a[text()='Book your trip' and @class='searchButton']");
	public By selectFromIcon = By.xpath("//span[@class='icon']");
	public By selectTo =By.xpath("//label[contains(@class,'destinationPortButton')]/span[text()='Riga']");
	public By btnVechiclle = By.xpath("//div[@class='siteContent booking']//span[@class='vehicles']");
    public By vechicleIcon = By.xpath("//div[@class='ddTitle']/*[@class='carIcon vehicles-white-novehicle']");
    public By selectCar = By.xpath("//table[contains(@class,'vehicleDropDown')]//td[@class='description']//h4[contains(.,'Parked car')]");
    public By btnBookTrip = By.xpath("//button[text()='Book the trip']");
    public By clickPlusIcon = By.xpath("//div[text()='DeLuxe']/../../../td[2]//button/div");
	public By selectDate(String date) {
	 By date1 = By.xpath("//table[@class='calendarBody']//*[@date-iso='"+date+"']");
	return date1;
	}
	
	public By departureFrom(String departureFrom) {
		 By departure = By.xpath("//div[@class='departurePortSelection']//ul/li[contains(text(),'"+departureFrom+"')]");
		return departure;
		}
	
	
	public void  departureTo(String To) {
		List <WebElement> values =CommonVariables.CommonDriver.get().findElements(By.xpath("//div[@class='destinationPortSelection']/label/span"));
		for(WebElement webelem:values) {

		String s = webelem.getText().trim();
		if(s.equalsIgnoreCase(To)) {

		webelem.click();

		break;

		}
		}
	}
	
	/*
	public  By selectDate(String month,String day) {
  //  By date = By.xpath(String.format("//table[@class='calendarBody']/tbody/tr/th/span[contains(text(),'+%s+')]/../../td[contains(text(),'"%d"')]", month,day));
    System.out.println(""+date);
    return date;
	}*/
	
	
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



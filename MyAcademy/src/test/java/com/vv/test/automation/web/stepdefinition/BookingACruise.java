package com.vv.test.automation.web.stepdefinition;

import java.util.List;

import java.util.Set;

import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.firefox.FirefoxDriver;


import java.util.Iterator;


public class BookingACruise {

 public static void main(String[] args) throws InterruptedException {

WebDriver driver = new FirefoxDriver();

driver.manage().window().maximize();

driver.manage().timeouts().implicitlyWait(20000, TimeUnit.MILLISECONDS);

driver.get("https://www.tallinksilja.com/book-a-cruise");



driver.findElement(By.xpath("//table[@class='calendarBody']//*[@date-iso='2018-11-06']")).click();

driver.findElement(By.xpath("//input[@id='passengers-adults']/following-sibling::button[@class='increase']")).click();

driver.findElement(By.xpath("//input[@id='passengers-juniors']/following-sibling::button[@class='increase']")).click();

driver.findElement(By.xpath("//span[text()='Helsinki']/following-sibling::span[@class='icon']")).click();

List <WebElement> values =driver.findElements(By.xpath("//*[contains(@class,'departurePortDropdown open')]/*[@class='list']/child::li"));

for(WebElement webelem:values) {

String s = webelem.getText().trim();

if(s.equalsIgnoreCase("Riga")) {

webelem.click();

break;

}

}


driver.findElement(By.xpath("//label[contains(@class,'destinationPortButton')]/span[text()='Stockholm']")).click();

driver.findElement(By.xpath("//a[text()='Book your trip' and @class='searchButton']")).click();

String mainWindow=driver.getWindowHandle();





Set<String> winds=driver.getWindowHandles();

Iterator <String> it = winds.iterator();


while(it.hasNext()) {

//System.out.println(winds.iterator().next());


String childWindow=it.next();

System.out.println(driver.getTitle());

if(!mainWindow.equalsIgnoreCase(childWindow)) {

driver.switchTo().window(childWindow);

}

/*
if(driver.getTitle().contains("Online Booking")) {

driver.switchTo().window(winds.iterator().next());

}*/


}


System.out.println(driver.getTitle());




driver.findElement(By.xpath("//table[@class='dayReturns']//*[@data-ship-code='ROMANTIKA']/td[contains(@data-datetime,'2018-11-10')]")).click();
driver.findElement(By.xpath("//div[@class='siteContent booking']//span[@class='vehicles']")).click();

driver.findElement(By.xpath("//div[@class='ddTitle']/*[@class='carIcon vehicles-white-novehicle']")).click();


driver.findElement(By.xpath("//table[@class='vehicleDropDown']//td[@class='description']/*[text()='Motorcycle']")).click();

driver.findElement(By.xpath("//table[contains(@class,'sailOptions')]//td[contains(@class,'outwardSail')]//button[@value='SUIT']/span[contains(text(),'cabin')]")).click();

driver.findElement(By.xpath("//table[contains(@class,'sailOptions')]//td[contains(@class,'returnSail')]//button[@value='SUIT']/span[contains(text(),'cabin')]")).click();



/*driver.findElement(By.xpath("//div[@data-bind='text: event.title' and contains(text(),'Wildlife Park tickets')]/../../../td[@class='outwardSail']//*[@class='button eventTime']")).click();

driver.findElement(By.xpath("//*[@class='eventTickets']//*[text()='Kolm�rden Zoo - two day entrance ticket for a child (3-12 yrs)']//following-sibling::div/div/button[@class='increase']")).click();

driver.findElement(By.xpath("//*[@class='eventTickets']//*[text()='Kolm�rden Zoo - two day entrance ticket for a child (3-12 yrs)']//following-sibling::div/div/button[@class='increase']")).click();

*/





  driver.findElement(By.xpath("//button[@class='pay' and text()='Book the trip']")).click();

 }



}


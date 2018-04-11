package com.vv.test.automation.listeners;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

public class MyListener extends AbstractWebDriverEventListener {
	

    public void beforeFindBy(By by, WebElement element, WebDriver driver){
        WebElement webElement = driver.findElement(by);

        /*Highlight the Element on which the Find.By action is taking place*/
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='4px solid yellow'", webElement);
        }
    }
}

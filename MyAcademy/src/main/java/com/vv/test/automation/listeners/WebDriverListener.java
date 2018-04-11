package com.vv.test.automation.listeners;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;

import com.vv.test.automation.web.accelerators.ActionsLibrary;
public class WebDriverListener extends MyListener implements WebDriverEventListener {
	
private EventFiringWebDriver webDriver;
private ActionsLibrary objCommonfunclib;
	
	public WebDriverListener(EventFiringWebDriver webDriver){
		this.webDriver = webDriver;
		objCommonfunclib = new ActionsLibrary(this.webDriver);
	}
	
    /**
     * Called before {@link WebDriver#get get(String url)} respectively
     * {@link WebDriver.Navigation#to navigate().to(String url)}.
     *
     * @param url
     * @param driver
     */
    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {

    	System.out.println("Navigating to URL: " + url);
    }

    /**
     * Called after {@link WebDriver#get get(String url)} respectively
     * {@link WebDriver.Navigation#to navigate().to(String url)}. Not called, if an
     * exception is thrown.
     *
     * @param url
     * @param driver
     */
    @Override
    public void afterNavigateTo(String url, WebDriver driver) {

    }

    /**
     * Called before {@link WebDriver.Navigation#back navigate().back()}.
     *
     * @param driver
     */
    @Override
    public void beforeNavigateBack(WebDriver driver) {

    }

    /**
     * Called after {@link WebDriver.Navigation navigate().back()}. Not called, if an
     * exception is thrown.
     *
     * @param driver
     */
    @Override
    public void afterNavigateBack(WebDriver driver) {

    }

    /**
     * Called before {@link WebDriver.Navigation#forward navigate().forward()}.
     *
     * @param driver
     */
    @Override
    public void beforeNavigateForward(WebDriver driver) {

    }

    /**
     * Called after {@link WebDriver.Navigation#forward navigate().forward()}. Not called,
     * if an exception is thrown.
     *
     * @param driver
     */
    @Override
    public void afterNavigateForward(WebDriver driver) {

    }

    /**
     * Called before {@link WebDriver#findElement WebDriver.findElement(...)}, or
     * {@link WebDriver#findElements WebDriver.findElements(...)}, or {@link WebElement#findElement
     * WebElement.findElement(...)}, or {@link WebElement#findElement WebElement.findElements(...)}.
     *
     * @param by
     * @param element will be <code>null</code>, if a find method of <code>WebDriver</code> is called.
     * @param driver
     */
    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
    	 WebElement webElement = driver.findElement(by);

         /*Highlight the Element on which the Find.By action is taking place*/
         if (driver instanceof JavascriptExecutor) {
             ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='4px solid green'", webElement);
         }
    }

    /**
     * Called after {@link WebDriver#findElement WebDriver.findElement(...)}, or
     * {@link WebDriver#findElements WebDriver.findElements(...)}, or {@link WebElement#findElement
     * WebElement.findElement(...)}, or {@link WebElement#findElement WebElement.findElements(...)}.
     *
     * @param by
     * @param element will be <code>null</code>, if a find method of <code>WebDriver</code> is called.
     * @param driver
     */
    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {

    }

    /**
     * Called before {@link WebElement#click WebElement.click()}.
     *
     * @param element
     * @param driver
     */
    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {

    }

    /**
     * Called after {@link WebElement#click WebElement.click()}. Not called, if an exception is
     * thrown.
     *
     * @param element
     * @param driver
     */
    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {

    }

    /**
     * Called before {@link RemoteWebDriver#executeScript(String, Object[]) }
     *
     * @param script
     * @param driver
     */
    @Override
    public void beforeScript(String script, WebDriver driver) {

    }

    /**
     * Called after {@link RemoteWebDriver#executeScript(String, Object[]) }. Not called if an exception is thrown
     *
     * @param script
     * @param driver
     */
    @Override
    public void afterScript(String script, WebDriver driver) {

    }

    /**
     * Called whenever an exception would be thrown.
     *
     * @param throwable
     * @param driver
     */
    @Override
    public void onException(Throwable throwable, WebDriver driver) {

    	if(throwable.getMessage().toLowerCase().contains("Error communicating with the remote browser. it may have died")){
			driver.navigate().refresh();
		}
    }

	@Override
	public void afterNavigateRefresh(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeNavigateRefresh(WebDriver arg0) {
		// TODO Auto-generated method stub
	}
}

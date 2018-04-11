package com.vv.test.automation.web.accelerators;




import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.openqa.selenium.Cookie;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.mortbay.log.Log;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.browserstack.local.Local;
import com.google.common.base.Optional;
import com.vv.test.automation.listeners.WebDriverListener;
import com.vv.test.automation.logs.CustomLogName;
import com.vv.test.automation.logs.CustomLogs;
import com.vv.test.automation.logs.ExtentLogs;
import com.vv.test.automation.utilities.CaptureScreenShot;
import com.vv.test.automation.utilities.CommonVariables;
import com.vv.test.automation.utilities.ConfigManager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.jetty.html.Page;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/**
 * The Class ActionsLibrary.
 */
public class ActionsLibrary extends com.vv.test.automation.listeners.MyListener {

	/** The wd. */
	public static WebDriver wd;

	/** The driver. */
	//public static EventFiringWebDriver driver;

	/** The event listener. */
	private WebDriverListener eventListener;

	/** The rdriver. */
	public static RemoteWebDriver rdriver;

	/** The appium driver. */
	private AppiumDriver appiumDriver;

	/** The wait. */
	private WebDriverWait wait;

	/** The driver startfailure count. */
	public static int driverStartfailureCount = 1;

	/** The obj capabilities. */
	private DesiredCapabilities objCapabilities;

	/** The chromeoptions. */
	private ChromeOptions chromeoptions;

	/** The arr known browser hwnd. */
	private Set<String> arrKnownBrowserHwnd; // Stores windows handle when

	/** The hwnd first window. */
	// launching a new browser
	public String hwndFirstWindow; // This will store handle of original window

	/** The hwnd most recent window. */
	public String hwndMostRecentWindow; // This will store handle of most

	/** The location service enabled. */
	// recently known window
	public Boolean locationServiceEnabled;

	/** The do full reset. */
	private Boolean doFullReset;

	/** The chrome profile. */
	private String chromeProfile;

	/** The firefox profile path. */
	private String firefoxProfilePath;

	/** The prefs. */
	private LoggingPreferences prefs;

	/** The extent logs. */
	private ExtentLogs extentLogs = new ExtentLogs();

	/** The fw. */
	private FileWriter fw;

	/** The pw. */
	private PrintWriter pw;

	/** The downloadedfilepath. */
	private static String downloadedfilepath;

	/** The firefox profile. */
	private FirefoxProfile firefoxProfile;

	/** The g str err msg. */
	public static String gStrErrMsg = " ";
	
	/** The log. */
	private static Logger LOG = LoggerFactory.getLogger(ActionsLibrary.class);
	
	/** The server. */
	public static BrowserMobProxyServer server;
	
	/** The bs local. */
	public Local bsLocal;

	/**
	 * Instantiates a new actions library.
	 *
	 * @param driver
	 *            the driver
	 */
	public ActionsLibrary(WebDriver driver) {
		CommonVariables.setDriver(driver);
		//ActionsLibrary.driver = (EventFiringWebDriver) driver;
		initPropertiesFile();
		this.locationServiceEnabled = Boolean
				.parseBoolean(ConfigManager.getProperties().getProperty("locationServiceEnabled").trim().toLowerCase());
		doFullReset = true;
		if (this.locationServiceEnabled) {
			this.chromeProfile = "browser-profile-location-boston";
			this.firefoxProfilePath = "Firefox_profile_For_Location_Simulate";
		}
	}

	/**
	 * Sets the driver.
	 *
	 * @author Cigniti
	 * @param driver
	 *            the new driver
	 * @description: setter for EventFiringWebDriver
	 */
	public void setDriver(EventFiringWebDriver driver) {
		//ActionsLibrary.driver = driver;
		CommonVariables.setDriver(driver);
	}

	/**
	 * Instantiates a new actions library.
	 *
	 * @author Cigniti
	 */
	public ActionsLibrary() {
		initPropertiesFile();
	}

	/**
	 * Inits the properties file.
	 *
	 * @author Cigniti
	 * @description: Intialize config properties
	 */
	private void initPropertiesFile() {
		try {
			if (!ConfigManager.ArePropertiesSet.get()) {
				ConfigManager.getProperties();
				ConfigManager.UpdateProperties();
			}
		} catch (Exception e) {
			e.getLocalizedMessage();
		}
	}

	/**
	 * Webdriver wait for page.
	 *
	 * @author Cigniti
	 * @description : Webdriver Wait For Page with time
	 */
	public void WebdriverWaitForPage() {
		CommonVariables.eventDriver.get().manage().timeouts().implicitlyWait(
				Long.parseLong(ConfigManager.getProperties().getProperty("globalTimeOut")), TimeUnit.SECONDS);
		((JavascriptExecutor) CommonVariables.eventDriver.get()).executeScript("window.focus();");
		
	}

	/**
	 * Instantiate driver.
	 *
	 * @author Cigniti
	 * @param browserType
	 *            the browser type
	 * @return the web driver
	 * @description : start webdriver with an instance of browser type.
	 */	
	public synchronized WebDriver instantiateDriver(String browserType) {
		String chromeprofilepath = null;
		try {
			String osname = System.getProperty("os.name");
			String DriverPath = "";
			CommonVariables.getActionLib().tearDown();
			System.out.println("************ Starting the driver: " + browserType +" on "+osname+ " ***************");
			downloadedfilepath = System.getProperty("user.dir").replace("\\", "/") + "/Tool/downloadedFiles";
			switch (browserType.trim().toLowerCase()) {
			case "chrome-web":
				if (ConfigManager.getProperties().getProperty("KillDriverBinaries").toLowerCase().trim()
						.contains("yes")) {
					KillWindowProcess("chromedriver.exe");
				}
				chromeoptions = new ChromeOptions();
				chromeoptions.addArguments("test-type");
				chromeoptions.addArguments("disable-session-crashed-bubble");
				chromeoptions.addArguments("disable-popup-blocking");
				chromeoptions.addArguments("--lang=" + ConfigManager.getProperties().getProperty("lacaleLang"));
				if(osname.equalsIgnoreCase("Linux")) {
					DriverPath = System.getProperty("user.dir").replace("\\", "/");	
					DriverPath = DriverPath.split("workspace")[0]+"workspace/Test_Automation_Tools/chromedriver "; 
				}else {
					DriverPath = System.getProperty("user.dir").replace("\\", "/")+ "/Tool/chromedrivers/windows/chromedriver.exe";
				}
				
				LOG.info(DriverPath);
				if (locationServiceEnabled != null) {
					if (!locationServiceEnabled) {
						Map<String, Object> prefs = new HashMap<String, Object>();
						prefs.put("profile.default_content_settings.geolocation", 2);
						chromeoptions.setExperimentalOption("prefs", prefs);
					} else if (locationServiceEnabled) {
						chromeprofilepath = System.getProperty("user.dir").replace("\\", "/")
								+ "/Tool/BrowserProfiles/Chrome/" + chromeProfile;
						if (locationServiceEnabled && ConfigManager.getProperties().getProperty("seleniumGrid")
								.toLowerCase().trim().contains("true"))
							chromeprofilepath = System.getProperty("user.dir").replace("\\", "/")
									+ "/Tool/BrowserProfiles/Chrome/" + chromeProfile;
						chromeoptions.addArguments("user-data-dir=" + chromeprofilepath);
					}
				}
				prefs = new LoggingPreferences();
				prefs.enable(LogType.BROWSER, Level.ALL);
				setDownloadPathForFile(downloadedfilepath);
				objCapabilities = new DesiredCapabilities();
				objCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeoptions);
				objCapabilities.setCapability(CapabilityType.LOGGING_PREFS, prefs);
				objCapabilities.setCapability("os", CommonVariables.OS.get());
				objCapabilities.setCapability("os_version", CommonVariables.OS_VERSION.get());
				objCapabilities.setCapability("platform", CommonVariables.PlatformName.get().toUpperCase().trim());
				objCapabilities.setCapability("takesScreenshot", true);
				objCapabilities.setCapability("handlesAlerts", true);
				if(ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
				.contains("true")) {
					objCapabilities.setCapability("browserName", "chrome");
					objCapabilities.setCapability("browserVersion", CommonVariables.Version.get());
					objCapabilities.setCapability("username", ConfigManager.getProperties().getProperty("browserStackUser"));
					objCapabilities.setCapability("accessKey", ConfigManager.getProperties().getProperty("browserStackKey"));
					
				}
				if(System.getenv("JENKINS_HOME")!=null) {
					objCapabilities.setCapability("build",System.getenv("BUILD_NUMBER")+":"+System.getenv("JOB_NAME"));
					objCapabilities.setCapability("Feature", CommonVariables.TestCaseName.get());
				}
				if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim().contains("true")) {					
					rdriver = null;
					rdriver = new RemoteWebDriver(new URL(
							"http://" + ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"),
							objCapabilities);									
					CommonVariables.eventDriver.set(new EventFiringWebDriver(rdriver));					
					eventListener = null;
					eventListener = new WebDriverListener(CommonVariables.eventDriver.get());
					CommonVariables.eventDriver.get().register(eventListener);
				} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
						.contains("true")) {
					rdriver = null;
					try {
                        if(check("BrowserStackLocal.exe")) {
                            LOG.info("Browser stack local is already running.");
                        }else {
                            setBrowserStackLocal();    
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
					//setProxy();
					objCapabilities.setCapability("browserstack.local", "true");
					rdriver = new RemoteWebDriver(new URL(
							"https://"+ConfigManager.getProperties().getProperty("browserStackUser")+":"+ConfigManager.getProperties().getProperty("browserStackKey") + ConfigManager.getProperties().getProperty("browserStackURL") + "/wd/hub"),
							objCapabilities);
					
					
					System.out.println("rdriver : "+rdriver.getSessionId());					
					CommonVariables.eventDriver.set(new EventFiringWebDriver(rdriver));										
					eventListener = null;
					eventListener = new WebDriverListener(CommonVariables.eventDriver.get());
					CommonVariables.eventDriver.get().register(eventListener);
					break;
				} else {					
					LOG.info(DriverPath);
					System.setProperty("webdriver.chrome.driver", DriverPath);
					ActionsLibrary.wd = null;
					ActionsLibrary.wd = new ChromeDriver(objCapabilities);
					//ActionsLibrary.driver = new EventFiringWebDriver(wd);
					CommonVariables.eventDriver.set(new EventFiringWebDriver(wd));
					eventListener = null;
					//eventListener = new WebDriverListener(ActionsLibrary.driver);
					//ActionsLibrary.driver.register(eventListener);
					eventListener = new WebDriverListener(CommonVariables.eventDriver.get());
					CommonVariables.eventDriver.get().register(eventListener);
				}			
				CommonVariables.eventDriver.get().manage().window().maximize(); 
				CommonVariables.eventDriver.get().getWrappedDriver().manage().deleteAllCookies();
				WebdriverWaitForPage();
				if (locationServiceEnabled) {
					/*driver.navigate().to("chrome://settings/clearBrowserData");
					WebElement frame = driver
							.findElement(By.xpath("//iframe[@src='chrome://settings-frame/clearBrowserData']"));
					WebDriver frameDriver = driver.switchTo().frame(frame);
					Select dropDown = new Select(frameDriver.findElement(By.id("clear-browser-data-time-period")));
					dropDown.selectByIndex(4);
					WebElement elm = driver.findElement(By.id("delete-cookies-checkbox"));
					if (!elm.isSelected())
						elm.click();
					elm = driver.findElement(By.xpath("//button[@id='clear-browser-data-commit']"));
					elm.click();
					(new WebDriverWait(driver, 90)).until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//button[@id='clear-browser-data-commit']")));
				*/}

				break;
			
		case "safari-web":
			SafariOptions safariOptions = new SafariOptions();
			objCapabilities = DesiredCapabilities.safari();
			objCapabilities.setCapability("browser", "Safari");
			objCapabilities.setCapability("platform", CommonVariables.PlatformName.get().toUpperCase());				
			objCapabilities.setCapability("os", CommonVariables.OS.get());
			objCapabilities.setCapability("os_version", CommonVariables.OS_VERSION.get());
			objCapabilities.setCapability("version", CommonVariables.Version.get());
			objCapabilities.setCapability("takesScreenshot", true);
			objCapabilities.setCapability("handlesAlerts", true);
			objCapabilities.setCapability("username", ConfigManager.getProperties().getProperty("browserStackUser"));
			objCapabilities.setCapability("accessKey", ConfigManager.getProperties().getProperty("browserStackKey"));		
			if (locationServiceEnabled != null) {
				objCapabilities.setCapability("locationContextEnabled", locationServiceEnabled);
			}
			objCapabilities.setCapability("username", ConfigManager.getProperties().getProperty("browserStackUser"));
			objCapabilities.setCapability("accessKey", ConfigManager.getProperties().getProperty("browserStackKey"));
			if (locationServiceEnabled != null) {
				objCapabilities.setCapability("locationContextEnabled", locationServiceEnabled);
			}
			SafariOptions.fromCapabilities(objCapabilities);
			try {
				if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim()
						.contains("true")) {
					rdriver = null;
					rdriver = new RemoteWebDriver(new URL(
							"http://" + ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"),
							objCapabilities);
					CommonVariables.eventDriver.set(new EventFiringWebDriver(rdriver));					
					eventListener = null;
					eventListener = new WebDriverListener(CommonVariables.eventDriver.get());
					CommonVariables.eventDriver.get().register(eventListener);
				} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
						.contains("true")) {
					rdriver = null;
					try {
                        if(check("BrowserStackLocal.exe")) {
                            LOG.info("Browser stack local is already running.");
                        }else {
                            setBrowserStackLocal();    
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
					//setProxy();
					objCapabilities.setCapability("browserstack.local", "true");
					rdriver = new RemoteWebDriver(new URL(
							"https://"+ConfigManager.getProperties().getProperty("browserStackUser")+":"+ConfigManager.getProperties().getProperty("browserStackKey") + ConfigManager.getProperties().getProperty("browserStackURL") + "/wd/hub"),
							objCapabilities);
					Thread.sleep(30000);
					CommonVariables.eventDriver.set(new EventFiringWebDriver(rdriver));					
					eventListener = null;
					eventListener = new WebDriverListener(CommonVariables.eventDriver.get());
					CommonVariables.eventDriver.get().register(eventListener);
					break;
				} else {
					String SafariDriverPath = System.getProperty("user.dir").replace("\\", "/")
							+ "/Tool/SafariDriver/SafariDriver.safariextz";
					System.setProperty("webdriver.safari.driver", SafariDriverPath);
					System.setProperty("webdriver.safari.noinstall", "true");
					ActionsLibrary.wd = null;
					ActionsLibrary.wd = new SafariDriver(safariOptions);
					CommonVariables.eventDriver.set(new EventFiringWebDriver(wd));					
					eventListener = null;
					eventListener = new WebDriverListener(CommonVariables.eventDriver.get());
					CommonVariables.eventDriver.get().register(eventListener);
				}
			} catch (Exception e1) {
				if (e1.getClass().toString().contains("UnreachableBrowserException")
						|| e1.getClass().toString().toLowerCase().contains("timeoutException")) {
					System.out.println("org.openqa.selenium.remote.UnreachableBrowserException occured");
					KillWindowProcess("Safari");
					try {
						Thread.sleep(22000);
					} catch (InterruptedException e2) {
					}
					if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim()
							.contains("true")) {
						rdriver = null;
						rdriver = new RemoteWebDriver(new URL("http://"
								+ ConfigManager.getProperties().getProperty("SeleniumGridHubIP") + ":4444/wd/hub"),
								objCapabilities);
						CommonVariables.eventDriver.set(new EventFiringWebDriver(rdriver));					
						eventListener = null;
						eventListener = new WebDriverListener(CommonVariables.eventDriver.get());
						CommonVariables.eventDriver.get().register(eventListener);
					} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
							.contains("true")) {
						rdriver = null;
						setBrowserStackLocal();	
						//setProxy();
						objCapabilities.setCapability("browserstack.local", "true");
						rdriver = new RemoteWebDriver(new URL(
								"https://"+ConfigManager.getProperties().getProperty("browserStackUser")+":"+ConfigManager.getProperties().getProperty("browserStackKey") + ConfigManager.getProperties().getProperty("browserStackURL") + "/wd/hub"),
								objCapabilities);
						Thread.sleep(30000);
						CommonVariables.eventDriver.set(new EventFiringWebDriver(rdriver));					
						eventListener = null;
						eventListener = new WebDriverListener(CommonVariables.eventDriver.get());
						CommonVariables.eventDriver.get().register(eventListener);
						break;
					} else {
						wd = null;
						wd = new SafariDriver(safariOptions);
						CommonVariables.eventDriver.set(new EventFiringWebDriver(wd));					
						eventListener = null;
						eventListener = new WebDriverListener(CommonVariables.eventDriver.get());
						CommonVariables.eventDriver.get().register(eventListener);
					}
				} else {
					throw e1;
				}
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
			CommonVariables.eventDriver.get().manage().window().maximize();
			break;
		case "ie-web":
			if (ConfigManager.getProperties().getProperty("KillDriverBinaries").toLowerCase().trim()
					.contains("yes")) {
				KillWindowProcess("IEDriverServer.exe");
			}
			objCapabilities = new DesiredCapabilities();
			objCapabilities = DesiredCapabilities.internetExplorer();
			objCapabilities.setCapability("platform", CommonVariables.PlatformName.get().toUpperCase());
			objCapabilities.setCapability("username", ConfigManager.getProperties().getProperty("browserStackUser"));
			objCapabilities.setCapability("accessKey", ConfigManager.getProperties().getProperty("browserStackKey"));
			objCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
					true);
			objCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			objCapabilities.setJavascriptEnabled(true);
			objCapabilities.setCapability("requireWindowFocus", true);
			objCapabilities.setCapability("enablePersistentHover", false);
			/*
			 * objCapabilities.setCapability("ignoreZoomSetting", true);
			 * objCapabilities.setCapability("nativeEvents",false);
			 */
			objCapabilities.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, true);
			objCapabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);

			if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim().contains("true")) {
				rdriver = null;
				rdriver = new RemoteWebDriver(new URL(
						"http://" + ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"),
						objCapabilities);
				CommonVariables.eventDriver.set(new EventFiringWebDriver(rdriver));					
				eventListener = null;
				eventListener = new WebDriverListener(CommonVariables.eventDriver.get());
				CommonVariables.eventDriver.get().register(eventListener);
			} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
					.contains("true")) {
				rdriver = null;
				try {
                    if(check("BrowserStackLocal.exe")) {
                        LOG.info("Browser stack local is already running.");
                    }else {
                        setBrowserStackLocal();    
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }	
				//setProxy();
				objCapabilities.setCapability("browserstack.local", "true");
				rdriver = new RemoteWebDriver(new URL(
						"https://"+ConfigManager.getProperties().getProperty("browserStackUser")+":"+ConfigManager.getProperties().getProperty("browserStackKey") + ConfigManager.getProperties().getProperty("browserStackURL") + "/wd/hub"),
						objCapabilities);
				Thread.sleep(30000);
				CommonVariables.eventDriver.set(new EventFiringWebDriver(rdriver));					
				eventListener = null;
				eventListener = new WebDriverListener(CommonVariables.eventDriver.get());
				CommonVariables.eventDriver.get().register(eventListener);
				break;
			} else {
				File fileIE = new File("Tool/IEDrivers/IEDriverServer.exe");
				System.setProperty("webdriver.ie.driver", fileIE.getAbsolutePath());
				ActionsLibrary.wd = null;
				ActionsLibrary.wd = new InternetExplorerDriver(objCapabilities);
				CommonVariables.eventDriver.set(new EventFiringWebDriver(wd));					
				eventListener = null;
				eventListener = new WebDriverListener(CommonVariables.eventDriver.get());
				CommonVariables.eventDriver.get().register(eventListener);
			}
			CommonVariables.eventDriver.get().manage().window().maximize();
			CommonVariables.eventDriver.get().manage().deleteAllCookies();
			/*
			 * process = Runtime .getRuntime() .exec(
			 * "RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255"); process.waitFor();
			 * Thread.sleep(3000);
			 */
			// code to start windows batch file for windows focus during
			// execution
			try {
				if (System.getProperty("os.name").toLowerCase().contains("windows")) {
					String command = "cmd /c start  " + " CALL " + "\"" + System.getProperty("user.dir")
							+ "\\Drivers\\MakeDisplayActive.bat" + "\"";
					String command1 = "cmd /c start  " + " CALL " + "\"" + System.getProperty("user.dir")
							+ "\\Drivers\\EnableDisplayContentIESetting.bat" + "\"";
					String command2 = "cmd /c start  " + " CALL " + "\"" + System.getProperty("user.dir")
							+ "\\Drivers\\ReturnSessionToConsole.bat" + "\"";
					Runtime runtime = Runtime.getRuntime();
					@SuppressWarnings("unused")
					Process pr = runtime.exec(command);
					@SuppressWarnings("unused")
					Process pr1 = runtime.exec(command1);
					@SuppressWarnings("unused")
					Process pr2 = runtime.exec(command2);
				} else {
					System.out.println(
							"****** Batch file to make display active is not executed because this is not the windows platform. ******");
				}
			} catch (Exception e) {
				System.out.println("Unable to execute windows focus batch file due to exception :- "
						+ e.getLocalizedMessage());
			}
			// code to handle security dialog in IE browser
			if (CommonVariables.CommonDriver.get().getPageSource().trim()
					.contains("There is a problem with this website")) {
				CommonVariables.CommonDriver.get().navigate()
						.to("javascript:document.getElementById('overridelink').click()");
				try {
					Thread.sleep(Long.parseLong("3000"));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			break;
		case "firefox-web":
			KillWindowProcess("geckodriver.exe");
			objCapabilities = new DesiredCapabilities();
			objCapabilities = DesiredCapabilities.firefox();
			objCapabilities.setCapability("marionette", true);
			objCapabilities.setCapability("platform", CommonVariables.PlatformName.get().toUpperCase());
			objCapabilities.setCapability("browserName", BrowserType.FIREFOX);				
			objCapabilities.setCapability("os", CommonVariables.OS.get());
			objCapabilities.setCapability("os_version", CommonVariables.OS_VERSION.get());				
			//comment out for local execution
			//objCapabilities.setCapability("version", CommonVariables.Version.get());
			objCapabilities.setCapability("takesScreenshot", true);
			objCapabilities.setCapability("handlesAlerts", true);
			objCapabilities.setCapability("username", ConfigManager.getProperties().getProperty("browserStackUser"));
			objCapabilities.setCapability("accessKey", ConfigManager.getProperties().getProperty("browserStackKey"));				
			if (this.locationServiceEnabled == true) {
				firefoxProfile = new FirefoxProfile(new File(System.getProperty("user.dir").replace("\\", "/")
						+ "/Tool/BrowserProfiles/Firefox/" + firefoxProfilePath));
				if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim()
						.contains("true")) {
					firefoxProfile = new FirefoxProfile(new File(System.getProperty("user.dir").replace("\\", "/")
							+ "/Tool/BrowserProfiles/Firefox/" + firefoxProfilePath));
				}
				firefoxProfile.setPreference("geo.enabled", true);
				firefoxProfile.setPreference("extensions.ui.locale.hidden", true);
				firefoxProfile.setPreference("intl.accept_languages",
						ConfigManager.getProperties().getProperty("lacaleLang"));
				firefoxProfile.setPreference("extensions.enabledAddons", "georelocate%40netzgewitter.com:0.2.3");
				firefoxProfile.setPreference("extensions.georelocate.latitude",
						ConfigManager.getProperties().getProperty("latitude"));
				firefoxProfile.setPreference("extensions.georelocate.longitude",
						ConfigManager.getProperties().getProperty("longitude"));
				firefoxProfile.setPreference("extensions.georelocate@netzgewitter.com.install-event-fired", true);
				firefoxProfile.setPreference("browser.cache.disk.enable", false);
				firefoxProfile.setPreference("browser.cache.memory.enable", false);
				firefoxProfile.setPreference("browser.cache.offline.enable", false);
				firefoxProfile.setPreference("network.http.use-cache", false);
				firefoxProfile.setPreference("browser.download.folderList", 2);
				firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
				firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
				firefoxProfile.setPreference("browser.download.dir", System.getProperty("user.dir") + "\\testdata");
				firefoxProfile.setPreference("browser.download.defaultFolder",
						System.getProperty("user.dir") + "\\testdata");
				firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
						"text/anytext ,text/plain,text/html,application/plain");
				firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/vnd.ms-excel");
				firefoxProfile.setPreference("pdfjs.disabled", true);
				firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
				firefoxProfile.setPreference("browser.download.manager.closeWhenDone", true);
				firefoxProfile.setPreference("browser.download.manager.useWindow", false);
				firefoxProfile.setPreference("browser.download.manager.alertOnExeOpen", false);
				ProfilesIni allProfiles = new ProfilesIni();
				allProfiles.getProfile("");
			} else {
				firefoxProfile = new FirefoxProfile();
				firefoxProfile.setPreference("geo.enabled", false);
			}
			firefoxProfile.setAcceptUntrustedCertificates(true);
			firefoxProfile.setAssumeUntrustedCertificateIssuer(true);
			objCapabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);

			if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim().contains("true")) {
				rdriver = null;
				rdriver = new RemoteWebDriver(new URL(
						"http://" + ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"),
						objCapabilities);
				CommonVariables.eventDriver.set(new EventFiringWebDriver(rdriver));					
				eventListener = null;
				eventListener = new WebDriverListener(CommonVariables.eventDriver.get());
				CommonVariables.eventDriver.get().register(eventListener);
			} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
					.contains("true")) {
				rdriver = null;
				try {
                    if(check("BrowserStackLocal.exe")) {
                        LOG.info("Browser stack local is already running.");
                    }else {
                        setBrowserStackLocal();    
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
				//setProxy();
				objCapabilities.setCapability("browserstack.local", "true");
				rdriver = new RemoteWebDriver(new URL(
						"https://"+ConfigManager.getProperties().getProperty("browserStackUser")+":"+ConfigManager.getProperties().getProperty("browserStackKey") + ConfigManager.getProperties().getProperty("browserStackURL") + "/wd/hub"),
						objCapabilities);
				Thread.sleep(30000);
				CommonVariables.eventDriver.set(new EventFiringWebDriver(rdriver));					
				eventListener = null;
				eventListener = new WebDriverListener(CommonVariables.eventDriver.get());
				CommonVariables.eventDriver.get().register(eventListener);
				break;
			} else {
				DriverPath = System.getProperty("user.dir").replace("\\", "/")
						+ "/Tool/FirefoxDrivers/windows/geckodriver.exe";
				System.setProperty("webdriver.gecko.driver", DriverPath);
				ActionsLibrary.wd = null;
				ActionsLibrary.wd = new FirefoxDriver(objCapabilities);
				CommonVariables.eventDriver.set(new EventFiringWebDriver(wd));					
				eventListener = null;
				eventListener = new WebDriverListener(CommonVariables.eventDriver.get());
				CommonVariables.eventDriver.get().register(eventListener);
			}
			CommonVariables.eventDriver.get().getWrappedDriver().manage().deleteAllCookies();
			break;

		case "android":
			objCapabilities = new DesiredCapabilities();
			objCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserType.CHROME);
			objCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME,
					ConfigManager.getProperties().getProperty("androidDeviceName"));
			objCapabilities.setCapability(MobileCapabilityType.PLATFORM, Platform.ANDROID);
			objCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			objCapabilities.setCapability(MobileCapabilityType.VERSION,
					ConfigManager.getProperties().getProperty("Version"));
			objCapabilities.setCapability("launchTimeout", "300000");
			objCapabilities.setCapability("newCommandTimeout", "600");
			objCapabilities.setCapability("realMobile", "true");
			objCapabilities.setCapability("username", ConfigManager.getProperties().getProperty("browserStackUser"));
			objCapabilities.setCapability("accessKey", ConfigManager.getProperties().getProperty("browserStackKey"));
			// this.setBrowserProxy();
			if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim().contains("true")) {
				rdriver = null;
				rdriver = new RemoteWebDriver(new URL(
						"http://" + ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"),
						objCapabilities);
				Thread.sleep(20000);
				for (String winHandle : CommonVariables.eventDriver.get().getWindowHandles()) {
					CommonVariables.eventDriver.get().switchTo().window(winHandle);
				}
				CommonVariables.eventDriver.set(new EventFiringWebDriver(rdriver));					
				eventListener = null;
				eventListener = new WebDriverListener(CommonVariables.eventDriver.get());
				CommonVariables.eventDriver.get().register(eventListener);
				break;
			} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
					.contains("true")) {
				rdriver = null;
				objCapabilities.setCapability("browserstack.local", "true");
				try {
                    if(check("BrowserStackLocal.exe")) {
                        LOG.info("Browser stack local is already running.");
                    }else {
                        setBrowserStackLocal();    
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }					
				if(System.getenv("JENKINS_HOME")!=null) {
					objCapabilities.setCapability("build",  System.getenv("BUILD_NUMBER")+":"+System.getenv("JOB_NAME"));
					objCapabilities.setCapability("Feature", CommonVariables.TestCaseName.get());
				}
				objCapabilities.setCapability("browserstack.local", "true");
				rdriver = new RemoteWebDriver(new URL(
						"https://"+ConfigManager.getProperties().getProperty("browserStackUser")+":"+ConfigManager.getProperties().getProperty("browserStackKey") + ConfigManager.getProperties().getProperty("browserStackURL") + "/wd/hub"),
						objCapabilities);
				Thread.sleep(30000);
				CommonVariables.eventDriver.set(new EventFiringWebDriver(rdriver));					
				eventListener = null;
				eventListener = new WebDriverListener(CommonVariables.eventDriver.get());
				CommonVariables.eventDriver.get().register(eventListener);
				break;
			} else {
				rdriver = null;
				ActionsLibrary.rdriver = new RemoteWebDriver(
						new URL("http://" + ConfigManager.getProperties().getProperty("appiumServerIP") + ":"
								+ ConfigManager.getProperties().getProperty("appiumPort") + "/wd/hub"),
						objCapabilities);
				// this.rdriver = appiumDriver;
				Thread.sleep(20000);
				CommonVariables.eventDriver.set(new EventFiringWebDriver(rdriver));					
				eventListener = null;
				eventListener = new WebDriverListener(CommonVariables.eventDriver.get());
				CommonVariables.eventDriver.get().register(eventListener);
			}
			CommonVariables.eventDriver.get().getWrappedDriver().manage().deleteAllCookies();
			break;
		case "edge-web":
			if (ConfigManager.getProperties().getProperty("KillDriverBinaries").toLowerCase().trim()
					.contains("yes")) {
				KillWindowProcess("MicrosoftWebDriver.exe");
			}
			objCapabilities = new DesiredCapabilities();
			objCapabilities.setCapability(CapabilityType.LOGGING_PREFS, prefs);
			objCapabilities = DesiredCapabilities.edge();
			objCapabilities.setCapability("platform", Platform.WINDOWS);
			objCapabilities.setCapability("browser", "edge");
			objCapabilities.setCapability("version", objCapabilities.getVersion());
			objCapabilities.setCapability("takesScreenshot", true);
			objCapabilities.setCapability("handlesAlerts", true);
			objCapabilities.setCapability("username", ConfigManager.getProperties().getProperty("browserStackUser"));
			objCapabilities.setCapability("accessKey", ConfigManager.getProperties().getProperty("browserStackKey"));
			objCapabilities.setJavascriptEnabled(true);

			if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim().contains("true")) {
				rdriver = null;
				rdriver = new RemoteWebDriver(new URL(
						"http://" + ConfigManager.getProperties().getProperty("gridHubIP") + ":4444/wd/hub"),
						objCapabilities);
				CommonVariables.eventDriver.set(new EventFiringWebDriver(rdriver));					
				eventListener = null;
				eventListener = new WebDriverListener(CommonVariables.eventDriver.get());
				CommonVariables.eventDriver.get().register(eventListener);
			} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
					.contains("true")) {
				rdriver = null;
				try {
                    if(check("BrowserStackLocal.exe")) {
                        LOG.info("Browser stack local is already running.");
                    }else {
                        setBrowserStackLocal();    
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
				//setProxy();
				objCapabilities.setCapability("browserstack.local", "true");
				rdriver = new RemoteWebDriver(new URL(
						"https://"+ConfigManager.getProperties().getProperty("browserStackUser")+":"+ConfigManager.getProperties().getProperty("browserStackKey") + ConfigManager.getProperties().getProperty("browserStackURL") + "/wd/hub"),
						objCapabilities);
				Thread.sleep(30000);
				CommonVariables.eventDriver.set(new EventFiringWebDriver(rdriver));					
				eventListener = null;
				eventListener = new WebDriverListener(CommonVariables.eventDriver.get());
				CommonVariables.eventDriver.get().register(eventListener);
				break;
			} else {
				ActionsLibrary.wd = null;
				// File fileIE = new File("\\Program Files (x86)\\Microsoft
				// Web Driver\\MicrosoftWebDriver.exe");
				DriverPath = System.getProperty("user.dir").replace("\\", "/")
						+ "/Tool/EdgeDriver/MicrosoftWebDriver.exe";
				System.setProperty("webdriver.edge.driver", DriverPath);// fileIE.getAbsolutePath()
				ActionsLibrary.wd = new EdgeDriver();
				CommonVariables.eventDriver.set(new EventFiringWebDriver(wd));					
				eventListener = null;
				eventListener = new WebDriverListener(CommonVariables.eventDriver.get());
				CommonVariables.eventDriver.get().register(eventListener);
			}
			CommonVariables.eventDriver.get().manage().window().maximize();
			CommonVariables.eventDriver.get().getWrappedDriver().manage().deleteAllCookies();
			break;
		default:
			return null;

		}
			if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim().contains("true")) {				
				CommonVariables.gridHubIP.set(GetIPOfSeleniumGridNode(rdriver));
				
			} else {
				CommonVariables.gridHubIP.set(CommonVariables.MachineHostName.get());
			}

			System.out.println("Successfully Launched the driver: " + browserType + " on the node IP: "
					+ CommonVariables.gridHubIP.get());
			if (browserType.toLowerCase().contains("iphone") || browserType.toLowerCase().contains("ipad")) {
			} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
					.contains("false")) {
				hwndFirstWindow = CommonVariables.getDriver().getWindowHandle();
				arrKnownBrowserHwnd = CommonVariables.getDriver().getWindowHandles();
			}
			/**
			 * If the location service is enabled set the geo location codes under test
			 */
			if (this.locationServiceEnabled && (ConfigManager.getProperties().getProperty("deviceType").toLowerCase()
					.contains("tablet")
					|| ConfigManager.getProperties().getProperty("deviceType").toLowerCase().contains("iPad")
					|| ConfigManager.getProperties().getProperty("deviceType").toLowerCase().contains("android")
					|| ConfigManager.getProperties().getProperty("deviceType").toLowerCase().contains("iPhone"))) {
			}
			driverStartfailureCount = 1;
			System.out.println("Before setting driver: "+CommonVariables.CommonDriver.get());
			CommonVariables.setDriver(CommonVariables.eventDriver.get());
			System.out.println("After setting driver: "+CommonVariables.CommonDriver.get());
			return CommonVariables.getDriver();
		} catch (Exception ex) {
			System.out.println(ex.getLocalizedMessage());
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			System.out.println(
					"Caught SessionNotFoundException/UnreachableBrowserException/SessionNotCreatedException in Start Driver Method. Try to start one time more.");
			if (driverStartfailureCount == 1) {
				driverStartfailureCount = 2;
				try {
					Thread.sleep(10);
				} catch (InterruptedException intr) {
				}
				//ActionsLibrary.driver = (EventFiringWebDriver) instantiateDriver(browserType);
				return CommonVariables.getDriver();
			} else {
				System.out.println(
						"Again Caught UnreachableBrowserException in Start Driver Method. Stopping the execution");
				Assert.fail("Browser Unreachable twice. Exception message: " + ex.getMessage());
				return CommonVariables.getDriver();
			}
		}
	}

	/**
	 * Tear down.
	 *
	 * @author Cigniti
	 * @description : shut down driver :- This method will close all instances of
	 *              all drivers.
	 */
	public void tearDown() {
		try {
			if (CommonVariables.getDriver() != null && CommonVariables.DeviceName.get().toLowerCase().contains("chrome")
					&& CommonVariables.PlatformName.get().toLowerCase().contains("windows")) {
				LogEntries logEntries = null;
				try {
					logEntries = CommonVariables.getDriver().manage().logs().get(LogType.BROWSER);
				} catch (Exception e) {
				}
				if (logEntries != null) {
					for (LogEntry entry : logEntries) {
						if ("SEVERE".equals(entry.getLevel().toString())) {
							writeCapturedJSErrors(
									new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
						}
					}
				}
				//bsLocal.stop();
			}
		} catch (Exception e) {
		}
		
		try {
			try {
				if (rdriver != null) {
					//rdriver.quit();
					try {
						Thread.sleep(50);
					} catch (InterruptedException e1) {
					} catch (IllegalStateException e2) {
					}
				}
			} catch (Exception ex) {
			}

			try {
				if (wd != null) {
					System.out.println("Quitting the driver");
					wd.quit();
					try {
						Thread.sleep(80);
					} catch (InterruptedException e1) {
					} catch (IllegalStateException e2) {
					}
				}
			} catch (Exception ex) {
			}

			try {
				if (CommonVariables.getDriver() != null) {
					//ActionsLibrary.driver.quit();
					try {
						Thread.sleep(80);
					} catch (InterruptedException e1) {
					} catch (IllegalStateException e2) {
					}
				}
			} catch (Exception ex) {
			}

			try {
				CommonVariables.CommonDriver.get().quit();				
				CommonVariables.CommonDriver.set(null);
				CommonVariables.CommonDriver.remove();
			} catch (Exception e) {
				CommonVariables.CommonDriver.set(null);
			}

			if (ConfigManager.getProperties().getProperty("deviceType").trim().toLowerCase().contains("android")
					|| CommonVariables.DeviceName.get().toLowerCase().contains("safari")
					|| CommonVariables.DeviceName.get().toLowerCase().contains("iphone")) {
				if (CommonVariables.getDriver() != null || wd != null || rdriver != null) {
					try {
						Thread.sleep(20);
					} catch (InterruptedException e1) {
					}
					;
					rdriver = null;
					//driver = null;
					wd = null;
				}
			}
		} catch (WebDriverException e) {
			CommonVariables.CommonDriver.set(null);
			try {
				Thread.sleep(250);
			} catch (InterruptedException e1) {
			} catch (IllegalStateException e2) {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		CommonVariables.CommonDriver.set(null);
	}

	/**
	 * Gets the driver info.
	 *
	 * @author Cigniti
	 * @return the map
	 * @description : In this method, it will return the driver information like, if
	 *              android or iOS return mobile else windows on this basis of
	 *              browser selected by the user.
	 */
	public static Map<String, String> GetDriverInfo() {
		Map<String, String> DriverInfo = new HashMap<String, String>();
		WebDriver driver = null;
		if (wd != null) {
			driver = wd;
		} else if (rdriver != null) {
			driver = rdriver;
		} else {
			driver = CommonVariables.getDriver();
		}
		try {
			String DriverType = "";
			String DriverName = "";
			String osName = "";
			if (driver == null || driver.getClass().toString().toLowerCase().contains("androiddriver")
					|| driver.getClass().toString().toLowerCase().contains("iosdriver")
					|| driver.getClass().toString().toLowerCase().contains("remotewebdriver")) {
				try {
					osName = CommonVariables.PlatformName.get();
					DriverName = CommonVariables.DeviceName.get();
				} catch (NullPointerException e) {
				}
				switch (osName.toUpperCase()) {
				case "WINDOWS": {
					if (DriverName.equals("android-chrome")) {
						DriverType = "mobile";
					} else {
						DriverType = "desktop";
					}
				}
					break;

				case "MAC": {
					if (DriverName.toLowerCase().contains("ipad")) {
						DriverType = "tablet";
					} else if (DriverName.toLowerCase().contains("iphone")) {
						DriverType = "mobile";
					} else {
						DriverType = "desktop";
					}
				}
					break;

				case "ANDROID": {
					DriverType = "mobile";
				}
					break;

				default: {
				}
				}
			} else if (driver.getClass().toString().toLowerCase().contains("chrome")) {
				if (driver.toString().contains("chrome on ANDROID")) {
					DriverType = "mobile";
					DriverName = CommonVariables.DeviceName.get();
				} else {
					DriverType = "Desktop";
					DriverName = "Chrome";
				}
			} else if (driver.getClass().toString().toLowerCase().contains("safari")) {
				DriverType = "Desktop";
				DriverName = "Safari";
			} else if (driver.getClass().toString().toLowerCase().contains("firefox")) {
				DriverType = "Desktop";
				DriverName = "Firefox";
			} else if (driver.getClass().toString().toLowerCase().contains("ie")
					|| driver.getClass().toString().toLowerCase().contains("internet_explorer")) {
				DriverType = "Desktop";
				DriverName = "internet_explorer";
			} else if (driver.getClass().toString().toLowerCase().contains("Edge")
					|| driver.getClass().toString().toLowerCase().contains("Microsoft")) {
				DriverType = "Desktop";
				DriverName = "Edge";
			} else if (driver.getClass().toString().toLowerCase().contains("Opera")) {
				DriverType = "Desktop";
				DriverName = "Opera";
			}
			DriverInfo.put("DriverType", DriverType);
			// DriverInfo.put("DriverType", "tablet");
			DriverInfo.put("DriverName", DriverName);
			return DriverInfo;
		} catch (Exception e) {
			return DriverInfo;
		}
	}

	/**
	 * Write captured JS errors.
	 *
	 * @author Cigniti
	 * @param errors
	 *            the errors
	 * @description : write JS Errors
	 */
	private void writeCapturedJSErrors(String errors) {
		try {
			// archiveFile();
			String filePath = System.getProperty("user.dir").replace("\\", "/") + "/logs/CapturedJSErrors/jsErrors.txt";
			fw = new FileWriter(filePath, true);
			pw = new PrintWriter(fw);
			pw.write(errors);
			pw.write("\n");
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Find element.
	 *
	 * @author Cigniti
	 * @param by
	 *            the by
	 * @param timeoutInSeconds
	 *            the timeout in seconds
	 * @return the web element
	 * @description : Method to Find Element
	 */
	public WebElement FindElement(By by, Optional<Long> timeoutInSeconds) {
		long timeout = timeoutInSeconds.isPresent() ? timeoutInSeconds.get() : -1;
		if (timeout == -1) {
			timeout = Long.parseLong(ConfigManager.getProperties().getProperty("globalTimeOut"));
		}
		WebElement webElement = null;
		try {
			AcceptAlert();
			webElement = (new WebDriverWait(CommonVariables.getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(by));
			try {
				if (webElement != null || CommonVariables.TestCaseLog.get() != null) {
					extentLogs.pass("Find Element",
							"Successfully find an element( " + by + " ) on '" + CommonVariables.getDriver().getTitle() + "' page.");
					CommonVariables.TestCaseLog.get()
							.info("Successfully find '" + by + "' element on '" + CommonVariables.getDriver().getTitle() + "' page");
				} else {
					extentLogs.fail("Find Element", "Element not found on '" + CommonVariables.getDriver().getTitle() + "' page.");
					CommonVariables.TestClassLog.get()
							.info("Successfully find '" + by + "' element on '" + CommonVariables.getDriver().getTitle() + "' page");
				}
			} catch (NullPointerException e) {
				extentLogs.error("Find Element", "Element not found on '" + CommonVariables.getDriver().getTitle()
						+ "' page due to exception - " + e.getLocalizedMessage());
				throw new NullPointerException();
			}
			try {
				ScrollToElementVisible(by);
			} catch (org.openqa.selenium.ElementNotVisibleException e) {
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
							.info("Element (" + by + ") is not visible on '" + CommonVariables.getDriver().getTitle() + "' page");
				} else {
					CommonVariables.TestClassLog.get()
							.info("Element (" + by + ") is not visible on '" + CommonVariables.getDriver().getTitle() + "' page");
				}
				extentLogs.error("Find Element", "Element not visible on '" + CommonVariables.getDriver().getTitle()
						+ "' page due to exception - " + e.getLocalizedMessage());
				throw new ElementNotVisibleException("Element not visible.");
			} catch (org.openqa.selenium.WebDriverException e) {
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
							.info("Failed to move Element (" + by + ") on the running driver type.");
				} else {
					CommonVariables.TestClassLog.get()
							.info("Failed to move Element (" + by + ") on the running driver type.");
				}
				extentLogs.error("Find Element",
						"Failed to move to an Element due to exception - " + e.getLocalizedMessage());
				throw new WebDriverException();
			} catch (NullPointerException ex) {
				System.out.println("Getting exception when movetoElement under FindElement method in" + getClass()
						+ " class. Driver info under CommonVariable :" + CommonVariables.getDriver());
				throw new NullPointerException();
			}
			return webElement;
		} catch (Exception ex) {
			if (ex.getCause().toString().contains("SessionNotFoundException")) {
				try {
					throw new Exception();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (ex.getCause().toString().contains("UnhandledAlertException")) {
				if (AcceptAlert()) {
					return CommonVariables.getDriver().findElement(by);
				}
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get().info("caught 'UnhandledAlertException' exception while finding '"
							+ by + "' element on '" + CommonVariables.getDriver().getTitle() + "' page");
				} else {
					CommonVariables.TestClassLog.get().info("caught 'UnhandledAlertException' exception while finding '"
							+ by + "' element on '" + CommonVariables.getDriver().getTitle() + "' page");
				}
				extentLogs.error("Find Element",
						"caught 'UnhandledAlertException' exception while finding an element on '" + CommonVariables.getDriver().getTitle()
								+ "' page");
				throw new UnhandledAlertException("");
			} else if (ex.getCause().toString().contains("NoSuchElementException")) {
				if (AcceptAlert()) {
					return CommonVariables.getDriver().findElement(by);
				}
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get().info("No such element(" + by + ") found on '" + CommonVariables.getDriver().getTitle()
							+ "' page at " + CommonVariables.getDriver().getCurrentUrl() + "' url.");
				} else {
					CommonVariables.TestClassLog.get().info("No such element(" + by + ") found on '" + CommonVariables.getDriver().getTitle()
							+ "' page at " + CommonVariables.getDriver().getCurrentUrl() + "' url.");
				}
				extentLogs.error("Find Element", "No such element éxception found on '" + CommonVariables.getDriver().getTitle()
						+ "' page at " + CommonVariables.getDriver().getCurrentUrl() + "' url.");
				throw new NoSuchElementException("No such Element");
				
			} else if (ex.getCause().toString().contains("TimeoutException")) {
				Log.info("TimeOut Exception during Find Element");
				if (AcceptAlert()) {
					return CommonVariables.getDriver().findElement(by);
				} else {
					if (CommonVariables.TestCaseLog.get() != null) {
						CommonVariables.TestCaseLog.get()
								.info("No such element(" + by + ") found on '" + CommonVariables.getDriver().getTitle() + "' page at "
										+ CommonVariables.getDriver().getCurrentUrl() + "' url within timelimit (" + timeout + ").");
					} else {
						CommonVariables.TestClassLog.get()
								.info("No such element(" + by + ") found on '" + CommonVariables.getDriver().getTitle() + "' page at "
										+ CommonVariables.getDriver().getCurrentUrl() + "' url within timelimit (" + timeout + ").");
					}
					extentLogs.error("Find Element", "No such element exception found on '" + CommonVariables.getDriver().getTitle()
							+ "' page at " + CommonVariables.getDriver().getCurrentUrl() + "' url within timelimit (" + timeout + ").");
				}
				throw new TimeoutException();
			} else if (ex.getCause().toString().contains("StaleElementReferenceException")) {
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
							.info("Info. Caught 'StaleElementReferenceException' exception while finding (" + by
									+ ") element. Class: " + ex.getClass());
				} else {
					CommonVariables.TestClassLog.get()
							.info("Info. Caught 'StaleElementReferenceException' exception while finding (" + by
									+ ") element. Class: " + ex.getClass());
				}
				extentLogs.error("Find Element",
						"Caught 'StaleElementReferenceException' exception while finding an element. Class: "
								+ ex.getClass());
				throw new StaleElementReferenceException("Stale Exception while finding an element.");
			}
			AcceptAlert();
			try {
				webElement = CommonVariables.getDriver().findElement(by);
				ScrollToElementVisible(by);
				return webElement;
			} catch (Exception e) {
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
							.info("Error. Caught 'StaleElementReferenceException' exception while finding (" + by
									+ ") element. Exception Type: " + e.getClass());
				} else {
					CommonVariables.TestClassLog.get()
							.info("Error. Caught 'StaleElementReferenceException' exception while finding (" + by
									+ ") element. Exception Type: " + e.getClass());
				}
				throw new StaleElementReferenceException("Stale Exception while finding an element.");
			}
		}
	}

	/**
	 * Click.
	 *
	 * @author Cigniti
	 * @param webElement
	 *            the web element
	 * @return true, if successful
	 * @description: click on webElement
	 */
	public boolean Click(WebElement webElement) {
		return Click(webElement, 4);		
	}

	/**
	 * Click.
	 *
	 * @author Cigniti
	 * @param webElement
	 *            the web element
	 * @param str
	 *            the str
	 * @return true, if successful
	 * @description: click on webElement
	 */
	public boolean Click(WebElement webElement, String str) {
		return Click(webElement, 4, str);
	}

	/**
	 * Click.
	 *
	 * @author Cigniti
	 * @param webElement
	 *            and wait time in seconds
	 * @param waitTime
	 *            the wait time
	 * @return true, if successful
	 * @description : Webdriver and Java Script Click. Wait time in seconds i.e. 8
	 *              for 8 seconds
	 */
	public boolean Click(WebElement webElement, Integer waitTime) {
		boolean state = false;
		try {
			WebDriverWait wait = new WebDriverWait(CommonVariables.getDriver(),20);
			wait.until(ExpectedConditions.elementToBeClickable(webElement));
			webElement.click();
			try {
				Thread.sleep(waitTime * 10);
				extentLogs.pass("Click " + webElement, "Successfully clicked.");
				if (CommonVariables.TestCaseLog.get() != null) {
					CustomLogs.addToLog("CurrentTestCaseLog", "info", "Passed. Successfully clicked.");
				} else {
					CustomLogs.addToLog("CurrentTestClassLog", "info", "Passed. Successfully clicked.");
				}
				state = true;
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new InterruptedException();
			}
		} catch (ElementNotVisibleException e1) {
			extentLogs.error("Click " + webElement, "Element is not visible.");

			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get().debug("Error: Element (" + webElement + ") is not visible.");
			} else {
				CommonVariables.TestClassLog.get().debug("Error: Element (" + webElement + ") is not visible.");
			}
			throw new ElementNotVisibleException(webElement.toString());
		} catch (StaleElementReferenceException e2) {
			extentLogs.error("Click " + webElement,
					"Caught 'StaleElementReferenceException' exception while clicking on element.");
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get()
						.debug("Error. Caught 'StaleElementReferenceException' exception while clicking on '"
								+ webElement + "' element.");
			} else {
				CommonVariables.TestClassLog.get()
						.debug("Error. Caught 'StaleElementReferenceException' exception while clicking on '"
								+ webElement + "' element.");
			}
			AcceptAlert();
			
			// driver.navigate().refresh();
			try {
				if (webElement.isEnabled()) {
					webElement.click();
				}
			} catch (Exception e) {
			}
			throw new StaleElementReferenceException(webElement.toString());
		} catch (org.openqa.selenium.UnhandledAlertException e3) {
			extentLogs.error("Click " + webElement,
					"Error. Caught 'UnhandledAlertException' exception while clicking on element.");
			AcceptAlert();
			try {
				if (webElement.isDisplayed()) {
					webElement.click();
				}
			} catch (Exception e) {
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
							.info("Info. Caught 'UnhandledAlertException' exception while clicking on '" + webElement
									+ "' element.");
				} else {
					CommonVariables.TestClassLog.get()
							.info("Info. Caught 'UnhandledAlertException' exception while clicking on '" + webElement
									+ "' element.");
				}
				e.printStackTrace();
			}
			throw new UnhandledAlertException(webElement.toString());
		} catch (NullPointerException e5) {
			extentLogs.error("Click", "Error. Caught 'NullPointerException' exception while clicking element on '"
					+ CommonVariables.getDriver().getTitle() + "'.");
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get()
						.debug("Info. Caught 'NullPointerException' exception while clicking element on '"
								+ CommonVariables.getDriver().getTitle() + "'.");
			} else {
				CommonVariables.TestClassLog.get()
						.debug("Info. Caught 'NullPointerException' exception while clicking element on '"
								+ CommonVariables.getDriver().getTitle() + "'.");
			}
			throw new NullPointerException();
		} catch (org.openqa.selenium.WebDriverException e6) {
			if (e6.getMessage().toString().toLowerCase().contains("user supplied")) {
				try {
					JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.getDriver());
					js.executeScript("arguments[0].click();", webElement);
					Thread.sleep(80);
					state = true;
				} catch (Exception e) {
					if (!state) {
						try {
							JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.getDriver());
							js.executeScript("$(arguments[0]).focusin();", webElement);
							Thread.sleep(80);
							state = true;
						} catch (Exception e1) {
							state = false;
						}
					}
				}
				
			}
			if (e6.getMessage().toString().toLowerCase().contains("Element is not clickable")) {
				extentLogs.error("Click " + webElement,
						"Caught exception about 'Element is not clickable' while clicking element on '"
								+ CommonVariables.getDriver().getTitle()
								+ "'. So Scroll Top/Bottom and wait for sometime to make clickable");

				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
							.info("Info: Caught exception about 'Element is not clickable' while clicking element on '"
									+ CommonVariables.getDriver().getTitle() + "'. So Scroll Top/Bottom and wait for sometime to make '"
									+ webElement + "' clickable");
				} else {
					CommonVariables.TestClassLog.get()
							.info("Info: Caught exception about 'Element is not clickable' while clicking element on '"
									+ CommonVariables.getDriver().getTitle() + "'. So Scroll Top/Bottom and wait for sometime to make '"
									+ webElement + "' clickable");
				}
			}
			
			try {
				try {
					if (ScrollToElementVisible(webElement)) {
						try {
							new WebDriverWait(CommonVariables.getDriver(), Long.parseLong("2"))
									.until(ExpectedConditions.elementToBeClickable(webElement)).click();
							Thread.sleep(80);
							return true;
						} catch (Exception ex) {
							try {
								JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
								js.executeScript("arguments[0].click();", webElement);
							} catch (Exception e) {
								return false;
							}
						}
					}
				} catch (Exception e) {
					return false;
				}
				try {
					ScrollToTop();
					JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
					js.executeScript("arguments[0].focusin();", webElement);
					new WebDriverWait(CommonVariables.getDriver(), Long.parseLong("10"))
							.until(ExpectedConditions.elementToBeClickable(webElement)).click();
					Thread.sleep(80);
					state = true;
				} catch (Exception e1) {
					if (e6.getMessage().toString().toLowerCase().contains("element is not clickable")) {
						try {
							ScrollToBottom();
							JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
							js.executeScript("arguments[0].focusin();", webElement);
							new WebDriverWait(CommonVariables.getDriver(), Long.parseLong("10"))
									.until(ExpectedConditions.elementToBeClickable(webElement)).click();
							state = true;
						} catch (Exception e) {
							return false;
						}
					} else {
						state = false;
					}
				}
			} catch (Exception e) {
				try {
					Actions clicker = new Actions(CommonVariables.CommonDriver.get());
					clicker.moveToElement(webElement).click().perform();

				} catch (Exception e7) {
					state = false;
					extentLogs.error("Click", "Element is not clickable.");
					if (CommonVariables.TestCaseLog.get() != null) {
						CommonVariables.TestCaseLog.get().error("Error: element is not clickable.");
					} else {
						CommonVariables.TestClassLog.get().error("Error: element is not clickable.");
					}
				}

			}
		throw new WebDriverException();
		} catch (Exception e4) {
			state = false;
		}
		return state;
	}

	/**
	 * Click.
	 *
	 * @param webElement
	 *            the web element
	 * @param waitTime
	 *            the wait time
	 * @param str
	 *            the str
	 * @return true, if successful
	 */
	public boolean Click(WebElement webElement, Integer waitTime, String str) {
		boolean state = false;
		try {
			WebDriverWait wait = new WebDriverWait(CommonVariables.getDriver(), 5);
			if(ExpectedConditions.elementToBeClickable(webElement) == null) {
			 System.out.println("null");	
			}
			wait.until(ExpectedConditions.elementToBeClickable(webElement));
			if (IsElementVisible(webElement)) {
				webElement.click();
			}
			else {
				throw new NoSuchElementException("");
			}
			
			Thread.sleep(1000);
			try {
				Thread.sleep(waitTime * 10);
				extentLogs.pass("Click", "Successfully clicked '" + str + "'");
				if (CommonVariables.TestCaseLog.get() != null) {
					CustomLogs.addToLog("CurrentTestCaseLog", "info", "Passed. Successfully clicked.");
				} else {
					CustomLogs.addToLog("CurrentTestClassLog", "info", "Passed. Successfully clicked.");
				}
				state = true;
			} catch (InterruptedException e) {
				e.printStackTrace();
				extentLogs.error("Click", "Element is not visible.");
				throw new InterruptedException();
			}
		} catch (ElementNotVisibleException e1) {
			extentLogs.error("Click", "Element is not visible.");
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get().debug("Error: Element (" + webElement + ") is not visible.");
			} else {
				CommonVariables.TestClassLog.get().debug("Error: Element (" + webElement + ") is not visible.");
			}
			throw new ElementNotVisibleException("");
		} catch (StaleElementReferenceException e2) {
			extentLogs.error("Click", "Caught 'StaleElementReferenceException' exception while clicking on element.");
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get()
						.debug("Error. Caught 'StaleElementReferenceException' exception while clicking on '"
								+ webElement + "' element.");
			} else {
				CommonVariables.TestClassLog.get()
						.debug("Error. Caught 'StaleElementReferenceException' exception while clicking on '"
								+ webElement + "' element.");
			}
			AcceptAlert();
			// driver.navigate().refresh();
			try {
				if (webElement.isEnabled()) {
					webElement.click();
				}
			} catch (Exception e) {
			}
			throw new StaleElementReferenceException("");
		} catch (org.openqa.selenium.UnhandledAlertException e3) {
			extentLogs.error("Click", "Error. Caught 'UnhandledAlertException' exception while clicking on element.");
			AcceptAlert();
			try {
				if (webElement.isDisplayed()) {
					webElement.click();
				}
			} catch (Exception e) {
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
							.info("Info. Caught 'UnhandledAlertException' exception while clicking on '" + webElement
									+ "' element.");
				} else {
					CommonVariables.TestClassLog.get()
							.info("Info. Caught 'UnhandledAlertException' exception while clicking on '" + webElement
									+ "' element.");
				}
				e.printStackTrace();
			}
			throw new UnhandledAlertException("");
		} catch (NullPointerException e5) {
			extentLogs.error("Click", "Error. Caught 'NullPointerException' exception while clicking element on '"
					+ CommonVariables.getDriver().getTitle() + "'.");
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get()
						.debug("Info. Caught 'NullPointerException' exception while clicking element on '"
								+ CommonVariables.getDriver().getTitle() + "'.");
			} else {
				CommonVariables.TestClassLog.get()
						.debug("Info. Caught 'NullPointerException' exception while clicking element on '"
								+ CommonVariables.getDriver().getTitle() + "'.");
			}
			throw new NullPointerException();
		} catch (TimeoutException e6) {
			extentLogs.error("Click", "Error. Caught 'TimeoutException' exception while clicking element on '"
					+ CommonVariables.getDriver().getTitle() + "'.");
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get()
						.debug("Info. Caught 'TimeoutException' exception while clicking element on '"
								+ CommonVariables.getDriver().getTitle() + "'.");
			} else {
				CommonVariables.TestClassLog.get()
						.debug("Info. Caught 'TimeoutException' exception while clicking element on '"
								+ CommonVariables.getDriver().getTitle() + "'.");
			}
			throw new TimeoutException();
		} catch (org.openqa.selenium.WebDriverException e7) {
			if (e7.getMessage().toString().toLowerCase().contains("user supplied")) {
				try {
					JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.getDriver());
					js.executeScript("arguments[0].click();", webElement);
					Thread.sleep(80);
					state = true;
				} catch (Exception e) {
					if (!state) {
						try {
							JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.getDriver());
							js.executeScript("$(arguments[0]).focusin();", webElement);
							Thread.sleep(80);
							state = true;
						} catch (Exception e1) {
							state = false;
						}
					}
				}
			}
			if (e7.getMessage().toString().toLowerCase().contains("Element is not clickable")) {
				extentLogs.error("Click",
						"Caught exception about 'Element is not clickable' while clicking element on '"
								+ CommonVariables.getDriver().getTitle()
								+ "'. So Scroll Top/Bottom and wait for sometime to make clickable");

				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
							.info("Info: Caught exception about 'Element is not clickable' while clicking element on '"
									+ CommonVariables.getDriver().getTitle() + "'. So Scroll Top/Bottom and wait for sometime to make '"
									+ webElement + "' clickable");
				} else {
					CommonVariables.TestClassLog.get()
							.info("Info: Caught exception about 'Element is not clickable' while clicking element on '"
									+ CommonVariables.getDriver().getTitle() + "'. So Scroll Top/Bottom and wait for sometime to make '"
									+ webElement + "' clickable");
				}
			}
			try {
				try {
					if (ScrollToElementVisible(webElement)) {
						try {
							new WebDriverWait(CommonVariables.getDriver(), Long.parseLong("2"))
									.until(ExpectedConditions.elementToBeClickable(webElement)).click();
							Thread.sleep(80);
							return true;
						} catch (Exception ex) {
							try {
								JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
								js.executeScript("arguments[0].click();", webElement);
							} catch (Exception e) {
								return false;
							}
						}
					}
				} catch (Exception e) {
					return false;
				}
				try {
					ScrollToTop();
					JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
					js.executeScript("arguments[0].focusin();", webElement);
					new WebDriverWait(CommonVariables.getDriver(), Long.parseLong("10"))
							.until(ExpectedConditions.elementToBeClickable(webElement)).click();
					Thread.sleep(80);
					state = true;
				} catch (Exception e1) {
					if (e7.getMessage().toString().toLowerCase().contains("element is not clickable")) {
						try {
							ScrollToBottom();
							JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
							js.executeScript("arguments[0].focusin();", webElement);
							new WebDriverWait(CommonVariables.getDriver(), Long.parseLong("10"))
									.until(ExpectedConditions.elementToBeClickable(webElement)).click();
							state = true;
						} catch (Exception e) {
							return false;
						}
					} else {
						state = false;
					}
				}
			} catch (Exception e) {
				try {
					Actions clicker = new Actions(CommonVariables.CommonDriver.get());
					clicker.moveToElement(webElement).click().perform();

				} catch (Exception e8) {
					state = false;
					extentLogs.error("Click", "Element is not clickable.");
					if (CommonVariables.TestCaseLog.get() != null) {
						CommonVariables.TestCaseLog.get().error("Error: element is not clickable.");
					} else {
						CommonVariables.TestClassLog.get().error("Error: element is not clickable.");
					}
				}

			}
		} catch (Exception e4) {
			state = false;
		}
		return state;
	}

	/**
	 * Javascript click.
	 *
	 * @author Cigniti
	 * @param webElement
	 *            the web element
	 * @return true, if successful
	 * @description : javascript Click
	 */
	public boolean javascriptClick(WebElement webElement) {
		boolean state = false;
		try {
			WebDriverWait wait = new WebDriverWait(CommonVariables.getDriver(), 60);
			wait.until(ExpectedConditions.elementToBeClickable(webElement));
			JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
			js.executeScript("arguments[0].click();", webElement);
			Thread.sleep(1000);
			state = true;
			return true;
		} catch (NullPointerException ex) {
			return false;
		} catch (Exception e) {
			try {
				if (ScrollToElementVisible(By.xpath(getElementXPath(webElement)))) {
					JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
					js.executeScript("$(arguments[0]).click();", webElement);
					Thread.sleep(40);
					state = true;
					return true;
				} else {
					return false;
				}
			} catch (Exception ex) {

			}
			if (!state) {
				try {
					JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
					js.executeScript("$(arguments[0]).focusin();", webElement);
					Thread.sleep(80);
					state = true;
				}

				catch (Exception e1) {
					state = false;
				}
			}
		}
		return state;
	}

	/**
	 * Javascript send keys.
	 *
	 * @author Cigniti
	 * @param locator
	 *            the locator
	 * @param valueToType
	 *            the value to type
	 * @return true, if successful
	 * @description : javascript Send Keys
	 */
	public boolean javascriptSendKeys(WebElement locator, String valueToType) {
		boolean state = false;
		try {
			WebDriverWait wait = new WebDriverWait(CommonVariables.getDriver(), 60);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
			js.executeScript("arguments[0].value=\"" + valueToType + "\";", locator);
			Thread.sleep(250);
			state = true;
			extentLogs.pass("Type", "Successfully entered text value - '" + valueToType + "'.");
		} catch (Exception ex) {
			extentLogs.error("Type",
					"Failed to enter text value - '" + valueToType + "' due to execption - " + ex.getMessage() + ".");
			state = false;
		}
		return state;
	}

	/**
	 * Type.
	 *
	 * @author Cigniti
	 * @param element
	 *            the element
	 * @param valueToType
	 *            the value to type
	 * @return true, if successful
	 * @throws Throwable
	 *             the throwable
	 * @description: webdriver send keys
	 */
	public boolean type(WebElement element, String valueToType) throws Throwable {
		boolean status = false;
		try {
			if (valueToType != null) {
				WebDriverWait wait = new WebDriverWait(CommonVariables.getDriver(), 60);
				ScrollToElementVisible(element);
				wait.until(ExpectedConditions.elementToBeClickable(element));
				element.clear();
				element.sendKeys(valueToType);
				Thread.sleep(200);
				status = true;
				extentLogs.pass("Type", "Successfully entered text value - '" + valueToType + "'.");
			}

		} catch (Exception e) {
			status = false;
			extentLogs.error("Type",
					"Failed to enter text value - '" + valueToType + "' due to execption - " + e.getMessage() + ".");
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get().error("Error: Failed to enter text value - '" + valueToType
						+ "' due to execption - " + e.getMessage() + ".");
			} else {
				CommonVariables.TestClassLog.get().error("Error: Failed to enter text value - '" + valueToType
						+ "' due to execption - " + e.getMessage() + ".");
			}
		}
		return status;
	}

	/**
	 * Browser specific send keys.
	 *
	 * @author Cigniti
	 * @param locator
	 *            the locator
	 * @param valueToType
	 *            the value to type
	 * @throws Throwable
	 *             the throwable
	 * @description: browser specific send keys.
	 */
	public void browserSpecificSendKeys(WebElement locator, String valueToType) throws Throwable {
		String browserName = CommonVariables.DeviceName.get();
		try {
			switch (browserName.toLowerCase()) {
			case "firefox":
			case "chrome":
			case "opera": {
				type(locator, valueToType);
				break;
			}
			case "ie":
			case "safari":
			case "edge":
			case "pahntomjs": {
				javascriptSendKeys(locator, valueToType);
				break;
			}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Select check box.
	 *
	 * @param locator
	 *            the locator
	 * @param value
	 *            the value
	 * @param locatorName
	 *            the locator name
	 * @param timeoutInSeconds
	 *            the timeout in seconds
	 * @return true, if successful
	 */
	public boolean selectCheckBox(By locator, String value, String locatorName, Optional<Long> timeoutInSeconds) {
		boolean flag = false;
		try {
			List<WebElement> locatorList = FindElements(locator, timeoutInSeconds);
			int locatorListSize = locatorList.size();
			for (int i = 0; i < locatorListSize; i++) {
				String sValue = locatorList.get(i).getAttribute("value");
				if (sValue.equalsIgnoreCase(value)) {
					Click(locatorList.get(i), value);
					Thread.sleep(20);
					break;
				}
			}
			flag = true;
		} catch (Exception e) {
			extentLogs.error("Select", "'" + value + "'" + "is not selected from the Check Box " + locatorName
					+ " due to exception - " + e.getLocalizedMessage());
			flag = false;
		} finally {
			if (flag == false) {
				extentLogs.fail("Select", "'" + value + "'" + "is not selected from the Check Box" + locatorName);
			} else if (flag == true) {
				extentLogs.pass("Select", "'" + value + "'" + "is selected from the Check Box" + locatorName);
			}
		}
		return flag;

	}

	/**
	 * Select radio button.
	 *
	 * @param locator
	 *            the locator
	 * @param value
	 *            the value
	 * @param locatorName
	 *            the locator name
	 * @param timeoutInSeconds
	 *            the timeout in seconds
	 * @return true, if successful
	 */
	public boolean selectRadioButton(By locator, String value, String locatorName, Optional<Long> timeoutInSeconds) {

		boolean flag = false;
		try {
			if (value != null) {
				List<WebElement> selectTravelAgentList = FindElements(locator, timeoutInSeconds);
				int locatorSize = selectTravelAgentList.size();
				for (int i = 0; i < locatorSize; i++) {
					String sValue = selectTravelAgentList.get(i).getAttribute("value");
					if (sValue.equalsIgnoreCase(value)) {
						Click(selectTravelAgentList.get(i));
						break;
					}
				}
				flag = true;
			}

		} catch (Exception e) {
			extentLogs.error("Select", "'" + value + "'" + "is not selected from the Radio button " + locatorName
					+ " due to exception - " + e.getLocalizedMessage());
			flag = false;
		} finally {
			if (flag == false) {
				extentLogs.fail("Select", "'" + value + "'" + "is not selected from the Radio button" + locatorName);
			} else if (flag == true) {
				extentLogs.pass("Select", "'" + value + "'" + "is selected from the Radio button" + locatorName);
			}
		}
		return flag;

	}

	/**
	 * Select by visible text.
	 *
	 * @param locator
	 *            the locator
	 * @param value
	 *            the value
	 * @param locatorName
	 *            the locator name
	 * @return true, if successful
	 * @throws Throwable
	 *             the throwable
	 * @description: select a value from drop down list on the basis of visible text
	 */
	public boolean selectByVisibleText(By locator, String value, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			ScrollToElementVisible(locator);
			WebElement dropDownListBox = CommonVariables.getDriver().findElement(locator);
			Select clickThis = new Select(dropDownListBox);
			clickThis.selectByVisibleText(value);
			fluentWait(5, 6);
			flag = true;
			Thread.sleep(200);
		} catch (Exception e) {
			extentLogs.error("Select", "'" + value + "'" + "is not selected from the DropDown " + locatorName
					+ " due to exception - " + e.getLocalizedMessage());
			flag = false;
		} finally {
			if (flag == false) {
				extentLogs.fail("Select", "'" + value + "'" + "is not selected from the DropDown " + locatorName);
			} else if (flag == true) {
				extentLogs.pass("Select", "'" + value + "'" + "is selected from the DropDown " + locatorName);
			}
		}
		return flag;
	}

	/**
	 * Select by sendkeys.
	 *
	 * @author Cigniti Select a value from Dropdown using send keys.
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * @param value
	 *            : Value wish type in dropdown list
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Year Dropdown, items Listbox
	 *            etc..)
	 * @return true, if successful
	 * @throws Throwable
	 *             the throwable
	 */
	public boolean selectBySendkeys(By locator, String value, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			if (value != null) {
				WebDriverWait wait = new WebDriverWait(CommonVariables.getDriver(), 60);
				ScrollToElementVisible(locator);
				wait.until(ExpectedConditions.elementToBeClickable(locator));
				if (CommonVariables.DeviceName.get().equalsIgnoreCase("ie")
						|| CommonVariables.DeviceName.get().equalsIgnoreCase("safari")) {
					CommonVariables.getDriver().findElement(locator).click();
					Select drp = new Select(CommonVariables.getDriver().findElement(locator));
					drp.selectByVisibleText(value);
				} else {
					CommonVariables.getDriver().findElement(locator).sendKeys(value);
				}
				fluentWait(30, 6);
				flag = true;
			}
		} catch (Exception e) {
			extentLogs.error("Select", "'" + value + "'" + "is not selected from the DropDown " + locatorName
					+ " due to exception - " + e.getLocalizedMessage());
			flag = false;
		} finally {
			if (flag == false) {
				extentLogs.fail("Select", "'" + value + "'" + "is not selected from the DropDown " + locatorName);
			} else if (flag == true) {
				extentLogs.pass("Select", "'" + value + "'" + "is selected from the DropDown " + locatorName);
			}
		}
		return flag;
	}

	/**
	 * Select by index.
	 *
	 * @author Cigniti select value from DropDown by using selectByIndex.
	 * @param locator
	 *            the locator
	 * @param index
	 *            the index
	 * @param locatorName
	 *            the locator name
	 * @return true, if successful
	 * @throws Throwable
	 *             the throwable
	 */
	public boolean selectByIndex(By locator, int index, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			WebDriverWait wait = new WebDriverWait(CommonVariables.getDriver(), 60);
			ScrollToElementVisible(locator);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			Select s = new Select(CommonVariables.getDriver().findElement(locator));
			s.selectByIndex(index);
			flag = true;
		} catch (Exception e) {
			extentLogs.error("Select", " Option at index " + index + " is Not selected from the DropDown" + locatorName
					+ " due to exception - " + e.getLocalizedMessage());
			flag = false;
		} finally {
			if (flag == false) {
				extentLogs.fail("Select",
						" Option at index " + index + " is not Selected from the DropDown" + locatorName);
			} else if (flag == true) {
				extentLogs.pass("Select", " Option at index " + index + " is Selected from the DropDown" + locatorName);
			}
		}
		return flag;
	}

	/**
	 * Wait for visibility of element.
	 *
	 * @param locator
	 *            the locator
	 * @param locatorName
	 *            the locator name
	 * @param timeOutInSeconds
	 *            the time out in seconds
	 * @return true, if successful
	 * @throws Throwable
	 *             the throwable
	 * @description: waitForVisibilityOfElement
	 */
	public boolean waitForVisibilityOfElement(By locator, String locatorName, long timeOutInSeconds) throws Throwable {
		try {
			WebDriverWait wait = new WebDriverWait(CommonVariables.getDriver(), timeOutInSeconds);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			CommonVariables.getDriver().findElement(locator);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			extentLogs.error("Element", "'" + locatorName +"-"+  locator+ "'" + "is not visible in the page and thrown exception - " + e.getLocalizedMessage());
			return false;
		}
	}

	/**
	 * Gets the all options from dropdown.
	 *
	 * @author Cigniti
	 * @param locator
	 *            the locator
	 * @param locatorName
	 *            the locator name
	 * @return the all options from dropdown
	 * @throws Throwable
	 *             the throwable
	 * @description: Get all the options from drop down list
	 */
	public List<String> getAllOptionsFromDropdown(By locator, String locatorName) throws Throwable {
		try {
			Select dropdown = new Select(CommonVariables.getDriver().findElement(locator));
			List<WebElement> optionsList = dropdown.getOptions();
			List<String> optionNamesList = new ArrayList<String>();
			for (WebElement option : optionsList) {
				optionNamesList.add(option.getText());
			}
			return optionNamesList;
		} catch (Exception e) {

			return null;
		}
	}

	/**
	 * JS accept alert.
	 *
	 * @author Cigniti
	 * @return true, if successful
	 * @throws Throwable
	 *             the throwable
	 * @description: Verify alert present or not
	 * @return: Boolean (True: If alert preset, False: If no alert)
	 */
	public boolean JSAcceptAlert() throws Throwable {

		boolean flag = false;
		try {
			JavascriptExecutor executor = (JavascriptExecutor) CommonVariables.getDriver();
			executor.executeScript("confirm = function(message){return true;};");
			executor.executeScript("alert = function(message){return true;};");
			executor.executeScript("prompt = function(message){return true;}");
			flag = true;
		} catch (Exception e) {
			extentLogs.fail("Accept Alert", "Failed to accept alert due to execption - " + e.getLocalizedMessage());
		} finally {
			if (flag == false) {
				extentLogs.fail("Accept Alert", "Failed to accept alert.");
				return flag;
			} else if (flag == true) {
				extentLogs.fail("Accept Alert", "Alert accepted successfully.");
				return flag;
			}
		}
		return flag;
	}

	/**
	 * Switch to latest window.
	 *
	 * @author Cigniti
	 * @param driver
	 *            the driver
	 * @return the string
	 * @description: Switch to window
	 */
	public static String switchToLatestWindow(WebDriver driver) {
		String newWindowHandle = null;
		try {
			Iterator<String> allWindowHandles = driver.getWindowHandles().iterator();
			while (allWindowHandles.hasNext()) {
				newWindowHandle = allWindowHandles.next();
				driver.switchTo().window(newWindowHandle);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return newWindowHandle;
	}

	/**
	 * Sets the window handles.
	 *
	 * @author Cigniti
	 * @description : Set Window Handles
	 */
	public void SetWindowHandles() {
		try {
			Thread.sleep(20);
		} catch (InterruptedException e1) {
		}
		try {
			// Retrieve handles for all opened browser windows
			if (appiumDriver != null && CommonVariables.getDriver()!= null) {
				/*
				 * arrKnownBrowserHwnd= appiumDriver.getContextHandles();
				 */} else {
				arrKnownBrowserHwnd = CommonVariables.getDriver().getWindowHandles();
			}
			if (appiumDriver == null) {
				if (arrKnownBrowserHwnd.size() >= 1) {
					for (String winHandle : arrKnownBrowserHwnd) {
						hwndMostRecentWindow = winHandle; // Set variable value
															// to the newly
															// opened window
					}
				} else if (arrKnownBrowserHwnd.size() == 0) {

					// No window appeared
					hwndMostRecentWindow = null;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Switch to most recent browser.
	 *
	 * @author Cigniti
	 * @return the string
	 * @description : Switch to most recent browser
	 */
	public String switchToMostRecentBrowser() {
		return switchToMostRecentBrowser("");
	}

	/**
	 * Switch to most recent browser.
	 *
	 * @author Cigniti
	 * @param windowTitle
	 *            the window title
	 * @return the string
	 * @description : Switch to most recent browser with title
	 */
	public String switchToMostRecentBrowser(String windowTitle) {
		try {
			try {
				Thread.sleep(10);
			} catch (InterruptedException excep) {
			}
			;
			SetWindowHandles();
			if (windowTitle.isEmpty()) {
				if (appiumDriver != null && CommonVariables.getDriver() != null) {
					/*
					 * appiumDriver.context(hwndMostRecentWindow); this.driver = appiumDriver;
					 * driver = new EventFiringWebDriver(this.driver); eventListener = null;
					 * eventListener = new WebDriverListeners(driver);
					 * driver.register(eventListener);
					 */} else {
						 CommonVariables.getDriver().switchTo().window(hwndMostRecentWindow);
				}

				// If its IE browser and locally running, set focus as well
				if (ConfigManager.getProperties().getProperty("seleniumGrid").toLowerCase().trim().contains("false")) {
					activateCurrentBrowserWindow();
				} else if (ConfigManager.getProperties().getProperty("cloudExecution").toLowerCase().trim()
						.contains("false")) {
					activateCurrentBrowserWindow();
				}
			} else {
				if (appiumDriver != null && CommonVariables.getDriver() != null) {
					/*
					 * appiumDriver.context(windowTitle.trim()); this.driver = appiumDriver; driver
					 * = new EventFiringWebDriver(this.driver); eventListener = null; eventListener
					 * = new WebDriverListeners(driver); driver.register(eventListener);
					 */} else {
					windowTitle.trim().toLowerCase();
					for (String winHandle : arrKnownBrowserHwnd) {
						if (CommonVariables.getDriver().switchTo().window(winHandle).getTitle().trim().toLowerCase().contains(windowTitle)) {
							WebdriverWaitForPage();
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			return null;
		}
		return hwndMostRecentWindow;
	}

	/**
	 * Activate current browser window.
	 *
	 * @author Cigniti
	 * @return the string
	 * @description : Activate current browser window
	 */
	public String activateCurrentBrowserWindow() {
		// Try to switch to most recent browser window, if require
		return hwndMostRecentWindow;
	}

	/**
	 * Open application.
	 *
	 * @author Cigniti
	 * @param url
	 *            the url
	 * @description : open application using url
	 */
	public void OpenApplication(String url) {
		OpenUrl(url);
	}

	/**
	 * Open url.
	 *
	 * @author Cigniti
	 * @param url
	 *            the url
	 * @return true, if successful
	 * @description : Open url
	 */
	public boolean OpenUrl(String url) {
		try {
			driverStartfailureCount = 1;
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestClassLog.get().info("Info: navigating URL : '" + url + "'");
			} else {
				CommonVariables.TestClassLog.get().info("Info: navigating URL : '" + url + "'");
				// extentLogs.info("Open URL : "+ url, "Opened successfully");

			}
			CommonVariables.CommonDriver.get().navigate().to(url);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			if (AcceptAlert()) {
				CommonVariables.CommonDriver.get().navigate().to(url);
				try {
					Thread.sleep(150);
				} catch (InterruptedException e1) {
				}
				return true;
			} else {
				extentLogs.info("Open URL",
						"caught 'NoSuchElementException' exception while try to open '" + url + "'.");
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestClassLog.get()
							.info("Info: caught 'NoSuchElementException' exception while try to open '" + url + "'.");
				} else {
					CommonVariables.TestClassLog.get()
							.info("Info: caught 'NoSuchElementException' exception while try to open '" + url + "'.");
				}
			}
			throw new NoSuchElementException("");
		} catch (org.openqa.selenium.UnhandledAlertException e) {
			if (AcceptAlert()) {
				CommonVariables.CommonDriver.get().navigate().to(url);
				try {
					Thread.sleep(150);
				} catch (InterruptedException e1) {
				}
				return true;
			}
			extentLogs.info("Open URL", "caught 'UnhandledAlertException' exception while try to open '" + url + "'.");
			throw new UnhandledAlertException("");
		} catch (TimeoutException e1) {
			extentLogs.info("Open URL", "Timed out receiving message from renderer.");
			this.RefreshBrowser();
			throw new TimeoutException();
		} catch (Exception e) {
			AcceptAlert();
			extentLogs.info("Open URL",
					"Failed to open Url. Err : " + e.getMessage() + "Stace track" + e.getStackTrace());
			return false;
		}
	}

	/**
	 * Gets the element attribute value.
	 *
	 * @author Cigniti
	 * @param objWebElement
	 *            the obj web element
	 * @param attribute
	 *            the attribute
	 * @return the string
	 * @description: Get WebElement attribute value
	 */
	public String GetElementAttributeValue(WebElement objWebElement, String attribute) {
		try {
			return objWebElement.getAttribute(attribute);
		} catch (org.openqa.selenium.NoSuchElementException e) {
			extentLogs.error("Attribute Value",
					"caught 'ElementNotFoundException' exception. Failed to get attribute - '" + attribute + "' on "
							+ CommonVariables.getDriver().getTitle() + "' page.");
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info",
					"Error: caught 'ElementNotFoundException' exception. Failed to get '" + attribute + "' value for '"
							+ objWebElement + "' on '" + CommonVariables.getDriver().getTitle() + "' page");
			return "";
		} catch (ElementNotVisibleException e) {
			extentLogs.error("Attribute Value",
					"caught 'ElementNotVisibleException' exception. Failed to get attribute - '" + attribute + "' on "
							+ CommonVariables.getDriver().getTitle() + "' page.");
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info",
					"Error: caught 'ElementNotVisibleException' exception. Failed to get '" + attribute
							+ "' value for '" + objWebElement + "' on '" + CommonVariables.getDriver().getTitle() + "' page");
			return "";
		} catch (WebDriverException e) {
			extentLogs.error("Attribute Value", "caught 'WebDriverException' exception. Failed to attribute - '"
					+ attribute + "' on " + CommonVariables.getDriver().getTitle() + "' page.");
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info",
					"Error: caught 'WebDriverException' exception. Failed to get '" + attribute + "' value for '"
							+ objWebElement + "' on '" + CommonVariables.getDriver().getTitle() + "' page");
			return "";
		} catch (NullPointerException e5) {
			extentLogs.error("Attribute Value", "Caught 'NullPointerException' exception while try to get attribute - '"
					+ attribute + "' on " + CommonVariables.getDriver().getTitle() + "' page.");
			CommonVariables.TestCaseLog.get()
					.info("Info. Caught 'NullPointerException' exception while try to get Element Attribute ("
							+ attribute + ") value on '" + CommonVariables.getDriver().getTitle() + "'.");
			return "";
		} catch (Exception e) {
			extentLogs.error("Attribute Value", "Failed to attribute - '" + attribute + "' on " + CommonVariables.getDriver().getTitle()
					+ "' page due to exception - " + e.getMessage());
			CommonVariables.TestCaseLog.get()
					.error("Failed to get '" + attribute + "' value. Error Message: " + e.getMessage());
			return "";
		}
	}

	/**
	 * Checks if is element exist.
	 *
	 * @author Cigniti
	 * @param by
	 *            the by
	 * @param timeoutInSeconds
	 *            the timeout in seconds
	 * @return true, if successful
	 * @description: check the existence of an element in DOM
	 */
	public boolean IsElementExist(By by, Optional<Long> timeoutInSeconds) {
		long timeout = timeoutInSeconds.isPresent() ? timeoutInSeconds.get() : 999999;
		if (timeout == 999999) {
			timeout = Long.parseLong(ConfigManager.getProperties().getProperty("globalTimeOut"));
		}
		try {
			AcceptAlert();
			if (FindElement(by, Optional.of(timeout)) != null) {
				extentLogs.pass("Presence of Element", "Element exists on '" + CommonVariables.getDriver().getTitle() + "' page.");
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
							.info("Info. Element '" + by + "' exists on '" + CommonVariables.getDriver().getTitle() + "' page.");
				} else {
					CommonVariables.TestClassLog.get()
							.info("Info. Element '" + by + "' exists on '" + CommonVariables.getDriver().getTitle() + "' page.");
				}
				return true;
			} else {
				extentLogs.fail("Presence of Element", "Element does not exist on '" + CommonVariables.getDriver().getTitle() + "' page.");
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
							.info("Info. Element '" + by + "' deos not exist on '" + CommonVariables.getDriver().getTitle() + "' page.");
				} else {
					CommonVariables.TestClassLog.get()
							.info("Info. Element '" + by + "' deos not exist on '" + CommonVariables.getDriver().getTitle() + "' page.");
				}
				return false;
			}
		} catch (NullPointerException e) {
			extentLogs.error("Presence of Element",
					"Element not exist on " + CommonVariables.getDriver().getTitle() + "' page due to exception - " + e.getMessage());
			throw e;
		} catch (NoSuchElementException e) {
			extentLogs.error("Presence of Element",
					"Element not exist on " + CommonVariables.getDriver().getTitle() + "' page due to exception - " + e.getMessage());
			throw e;
		} catch (Exception e) {
			extentLogs.error("Presence of Element",
					"Element not exist on " + CommonVariables.getDriver().getTitle() + "' page due to exception - " + e.getMessage());
			throw e;
		}
	}
	
	public boolean IsElementDisplayed(By by, Optional<Long> timeoutInSeconds) {
		long timeout = timeoutInSeconds.isPresent() ? timeoutInSeconds.get() : 999999;
		if (timeout == 999999) {
			timeout = Long.parseLong(ConfigManager.getProperties().getProperty("globalTimeOut"));
		}
		try {
			AcceptAlert();
			if (FindElement(by, Optional.of(timeout)).isDisplayed()) {
				extentLogs.pass("IsElementDisplayed", "Element exists on '" + CommonVariables.getDriver().getTitle() + "' page.");
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
							.info("Info. Element '" + by + "' displayed on '" + CommonVariables.getDriver().getTitle() + "' page.");
				} else {
					CommonVariables.TestClassLog.get()
							.info("Info. Element '" + by + "' displayed on '" + CommonVariables.getDriver().getTitle() + "' page.");
				}
				return true;
			} else {
				extentLogs.fail("IsElementDisplayed", "Element not displayed on '" + CommonVariables.getDriver().getTitle() + "' page.");
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
							.info("Info. Element '" + by + "' not displayed on '" + CommonVariables.getDriver().getTitle() + "' page.");
				} else {
					CommonVariables.TestClassLog.get()
							.info("Info. Element '" + by + "' not displayed on '" + CommonVariables.getDriver().getTitle() + "' page.");
				}
				return false;
			}
		} catch (NullPointerException e) {
			extentLogs.error("IsElementDisplayed",
					"Element not displayed on " + CommonVariables.getDriver().getTitle() + "' page due to exception - " + e.getMessage());
			throw e;
		} catch (NoSuchElementException e) {
			extentLogs.error("IsElementDisplayed",
					"Element not displayed on " + CommonVariables.getDriver().getTitle() + "' page due to exception - " + e.getMessage());
			throw e;
		} catch (Exception e) {
			extentLogs.error("IsElementDisplayed",
					"Element not displayed on " + CommonVariables.getDriver().getTitle() + "' page due to exception - " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Gets the U rl.
	 *
	 * @author Cigniti
	 * @return the string
	 * @description : Get URL
	 */
	public String GetURl() {
		try {
			WebdriverWaitForPage();
			extentLogs.pass("Get URL", "Successfully navigate to url - '" + CommonVariables.getDriver().getCurrentUrl() + "'.");
			return CommonVariables.getDriver().getCurrentUrl();
		} catch (StaleElementReferenceException e2) {
			extentLogs.error("Get URL", "caught 'StaleElementReferenceException' exception while getting '"
					+ CommonVariables.getDriver().getTitle() + "' page.");
			CommonVariables.TestCaseLog.get()
					.error("Info: caught 'StaleElementReferenceException' exception while getting '" + CommonVariables.getDriver().getTitle()
							+ "' page title");
			return "";
		} catch (org.openqa.selenium.UnhandledAlertException e) {
			extentLogs.error("Get URL",
					"caught 'UnhandledAlertException' exception while getting '" + CommonVariables.getDriver().getTitle() + "' page.");
			CommonVariables.TestCaseLog.get().error("Info: caught 'UnhandledAlertException' exception while getting '"
					+ CommonVariables.getDriver().getTitle() + "' page title");
			return "";
		}
	}

	/**
	 * Navigate back.
	 *
	 * @author Cigniti
	 * @description : Navigate Back
	 */
	public void NavigateBack() {
		try {
			CommonVariables.getDriver().navigate().back();
			extentLogs.pass("Navigate Back", "Successfully navigate back to page - '" + CommonVariables.getDriver().getTitle() + "' page.");
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
			}
		} catch (org.openqa.selenium.UnhandledAlertException e) {
			extentLogs.error("Navigate Back", "caught 'UnhandledAlertException' exception while navigating back from '"
					+ CommonVariables.getDriver().getTitle() + "' page.");
			CommonVariables.TestCaseLog.get()
					.info("Info: caught 'UnhandledAlertException' exception while navigating back from '"
							+ CommonVariables.getDriver().getTitle() + "' page");
			AcceptAlert();
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			extentLogs.error("Navigate Back",
					"caught 'StaleElementReferenceException' exception while navigating back from '" + CommonVariables.getDriver().getTitle()
							+ "' page.");
			CommonVariables.TestCaseLog.get()
					.error("Info: caught 'StaleElementReferenceException' exception while navigating back from '"
							+ CommonVariables.getDriver().getTitle() + "' page");
			AcceptAlert();
		}
	}

	/**
	 * Refresh browser.
	 *
	 * @author Cigniti
	 * @description : Refresh browser
	 */
	public void RefreshBrowser() {
		try {
			CommonVariables.getDriver().navigate().refresh();
			extentLogs.pass("Refresh Browser",
					"Successfully refresh browser at page - '" + CommonVariables.getDriver().getTitle() + "' page.");
			try {
				Thread.sleep(60);
			} catch (InterruptedException e) {
				AcceptAlert();
			}
		} catch (org.openqa.selenium.UnhandledAlertException e) {
			extentLogs.error("Refresh Browser",
					"caught 'UnhandledAlertException' exception while refresh '" + CommonVariables.getDriver().getTitle() + "' page");
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get()
						.info("Info: caught 'UnhandledAlertException' exception while refresh '" + CommonVariables.getDriver().getTitle()
								+ "' page");
			} else {
				CommonVariables.TestClassLog.get()
						.info("Info: caught 'UnhandledAlertException' exception while refresh '" + CommonVariables.getDriver().getTitle()
								+ "' page");
			}
			AcceptAlert();
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			extentLogs.error("Refresh Browser", "caught 'StaleElementReferenceException' exception while refresh '"
					+ CommonVariables.getDriver().getTitle() + "' page.");
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get()
						.info("Info: caught 'StaleElementReferenceException' exception while refresh '"
								+ CommonVariables.getDriver().getTitle() + "' page");
			} else {
				CommonVariables.TestClassLog.get()
						.info("Info: caught 'StaleElementReferenceException' exception while refresh '"
								+ CommonVariables.getDriver().getTitle() + "' page");
			}
			AcceptAlert();
		} catch (org.openqa.selenium.UnsupportedCommandException e) {
			extentLogs.error("Refresh Browser",
					"caught 'UnsupportedCommandException' exception while refresh '" + CommonVariables.getDriver().getTitle() + "' page.");
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCaseLog.get()
						.info("Info: caught 'UnsupportedCommandException' exception while refresh '" + CommonVariables.getDriver().getTitle()
								+ "' page");
			} else {
				CommonVariables.TestClassLog.get()
						.info("Info: caught 'UnsupportedCommandException' exception while refresh '" + CommonVariables.getDriver().getTitle()
								+ "' page");
			}
			try {
				CommonVariables.getDriver().navigate().refresh();
			} catch (Exception e2) {
				extentLogs.error("Refresh Browser",
						"caught 'Exception' exception while refresh '" + CommonVariables.getDriver().getTitle() + "' page.");
				if (CommonVariables.TestCaseLog.get() != null) {
					CommonVariables.TestCaseLog.get()
							.info("Info: caught 'Exception' exception while refresh '" + CommonVariables.getDriver().getTitle() + "' page");
				} else {
					CommonVariables.TestClassLog.get()
							.info("Info: caught 'Exception' exception while refresh '" + CommonVariables.getDriver().getTitle() + "' page");
				}
				CommonVariables.getDriver().navigate().to(CommonVariables.getDriver().getCurrentUrl());
				try {
					Thread.sleep(60);
				} catch (InterruptedException e1) {
					AcceptAlert();
				}
			}
			try {
				Thread.sleep(60);
			} catch (InterruptedException e1) {
				AcceptAlert();
			}
		}
	}

	/**
	 * Gets the page title.
	 *
	 * @author Cigniti
	 * @return the string
	 * @description : get Page Title
	 */
	public String GetPageTitle() {
		try {
			return CommonVariables.getDriver().getTitle();
		} catch (org.openqa.selenium.UnhandledAlertException e) {
			extentLogs.error("Page Title",
					"caught 'UnhandledAlertException' exception while getting '" + CommonVariables.getDriver().getTitle() + "' page.");
			CommonVariables.TestCaseLog.get().info("Info: caught 'UnhandledAlertException' exception while getting '"
					+ CommonVariables.getDriver().getTitle() + "' page title");
			if (AcceptAlert()) {
				return CommonVariables.getDriver().getTitle();
			}
			return null;
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			extentLogs.error("Page Title", "caught 'StaleElementReferenceException' exception while getting '"
					+ CommonVariables.getDriver().getTitle() + "' page.");
			CommonVariables.TestCaseLog.get()
					.info("Info: caught 'StaleElementReferenceException' exception while getting '" + CommonVariables.getDriver().getTitle()
							+ "' page title");
			try {
				if (AcceptAlert()) {
					return CommonVariables.getDriver().getTitle();
				} else {
					return null;
				}
			} catch (WebDriverException ex) {
				return null;
			}
		} catch (org.openqa.selenium.TimeoutException e) {
			extentLogs.error("Page Title",
					"caught 'TimeoutException' exception while getting '" + CommonVariables.getDriver().getTitle() + "' page.");
			CommonVariables.TestCaseLog.get().info(
					"Info: caught 'TimeoutException' exception while getting '" + CommonVariables.getDriver().getTitle() + "' page title");
			return null;
		}
	}

	/**
	 * Find elements.
	 *
	 * @author Cigniti
	 * @param by
	 *            the by
	 * @param timeoutInSeconds
	 *            the timeout in seconds
	 * @return the list
	 * @description : Get collection webelements using Find Elements
	 */
	public List<WebElement> FindElements(By by, Optional<Long> timeoutInSeconds) {
		long timeout = timeoutInSeconds.isPresent() ? timeoutInSeconds.get() : -1;
		if (timeout == -1) {
			timeout = Long.parseLong(ConfigManager.getProperties().getProperty("globalTimeOut"));
		}
		List<WebElement> webElement;
		try {
			webElement = (new WebDriverWait(CommonVariables.getDriver(), timeout))
					.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
			extentLogs.pass("Find Elements", "Successfully find matched elements on '" + CommonVariables.getDriver().getTitle() + "' page.");
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info",
					"Info: Successfully find matched elements by '" + by + "' on '" + CommonVariables.getDriver().getTitle() + "' page.");
			return webElement;
		} catch (org.openqa.selenium.UnhandledAlertException e) {
			if (AcceptAlert()) {
				return FindElements(by, timeoutInSeconds);
			}
			extentLogs.error("Find Elements",
					"caught 'UnhandledAlertException' exception while finding an elements on '" + CommonVariables.getDriver().getTitle()
							+ "' page.");
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info",
					"Info: caught 'UnhandledAlertException' exception while finding '" + by + "' element on '"
							+ CommonVariables.getDriver().getTitle() + "' page");
			throw new UnhandledAlertException("");
		} catch (StaleElementReferenceException e2) {
			if (AcceptAlert()) {
				return FindElements(by, timeoutInSeconds);
			}
			extentLogs.error("Find Elements", "caught 'StaleElementReferenceException' exception while getting '"
					+ CommonVariables.getDriver().getTitle() + "' page title.");
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info",
					"Info: caught 'StaleElementReferenceException' exception while getting '" + CommonVariables.getDriver().getTitle()
							+ "' page title");
			throw new StaleElementReferenceException("");
		} catch (TimeoutException e3) {
			if (AcceptAlert()) {
				return FindElements(by, timeoutInSeconds);
			}
			extentLogs.error("Find Elements",
					"caught 'TimeoutException' exception while getting '" + CommonVariables.getDriver().getTitle() + "' page title.");
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info",
					"Info: caught 'TimeoutException' exception while getting '" + CommonVariables.getDriver().getTitle() + "' page title");
			throw new TimeoutException();
		} catch (NoSuchElementException e4) {
			if (AcceptAlert()) {
				return FindElements(by, timeoutInSeconds);
			}
			extentLogs.error("Find Elements",
					"caught 'NoSuchElement' exception while getting '" + CommonVariables.getDriver().getTitle() + "' page title.");
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info",
					"Info: caught 'NoSuchElement' exception while getting '" + CommonVariables.getDriver().getTitle() + "' page title");
			throw new NoSuchElementException("No such Element");
		}
	}

	/**
	 * Accept alert.
	 *
	 * @author Cigniti
	 * @return true, if successful
	 * @Description: Accept alert pop-ups
	 */
	public boolean AcceptAlert() {
		try {
			Alert alert = CommonVariables.getDriver().switchTo().alert();
			alert.accept();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Decline alert.
	 *
	 * @author Cigniti
	 * @return true, if successful
	 * @description : Decline Alert
	 */
	public boolean declineAlert() {
		try {
			Alert alert = CommonVariables.getDriver().switchTo().alert();
			alert.dismiss();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Wait for element visible.
	 *
	 * @author Cigniti
	 * @param by            the by
	 * @param waitTime            the wait time
	 * @param locatorName the locator name
	 * @description : Wait for Element Visible
	 */
	public void WaitForElementVisible(final By by, int waitTime, String locatorName) {
		wait = new WebDriverWait(CommonVariables.getDriver(), waitTime*3);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));			
			extentLogs.pass("Element Visible", locatorName+" -Element visibile at '" + CommonVariables.getDriver().getTitle() + "' page.");
		} catch (TimeoutException e) {
			extentLogs.error("Element Visible",	"caught 'TimeoutException' exception while wait for element  ( " + by.toString() + " ) visibility at '" + CommonVariables.getDriver().getTitle() + "' page.");
			if (CommonVariables.TestCaseLog.get() != null) {
				CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info",
						"Info: caught 'TimeoutException' exception while wait for element ( " + by.toString()
								+ " ) visibility at '" + CommonVariables.getDriver().getTitle() + "' page");
			} else {
				CommonVariables.TestClassLog.get()
						.info("Info: caught 'TimeoutException' exception while wait for element ( " + by.toString()
								+ " ) visibility at '" + CommonVariables.getDriver().getTitle() + "' page");
				extentLogs.fail("WaitForElementVisible : "+by, "Info: caught 'TimeoutException' exception while wait for element ( " + by.toString()
								+ " ) visibility at '" + CommonVariables.getDriver().getTitle() + "' page");;
			}
		}
	}

	/**
	 * Takes screen shot.
	 *
	 * @author Cigniti Description: capture screenshot
	 * @param driver
	 *            the driver
	 * @param fileName
	 *            the file name
	 */
	private void takesScreenShot(WebDriver driver, String fileName) {
		//WebDriver driverScreenShot = new Augmenter().augment(driver);
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Save screenshot.
	 *
	 * @author Cigniti
	 * @param ImgPath
	 *            the img path
	 * @return true, if successful
	 * @description : Save Screenshot
	 */
	public static boolean saveScreenshot(String ImgPath) {
		try {
			// This is the ultimate implementation. Right now, we are able to
			// get the joined images in chrome ONLY.
			if (GetDriverInfo().get("DriverName").contains("chrome")) {
				try {
					saveFullScreenShot(ImgPath);
					return true;
				} catch (Exception e) {
					System.out.println(
							"Getting exception while taking full screenshot. Exception message:" + e.getStackTrace());
				}
			}

			// This needs to be removed if we are able to get the joined images
			// in other browsers
			if (CommonVariables.getDriver() != null && !CommonVariables.getDriver().getWindowHandle().isEmpty()) {
				File screenshot = null;
				if (ConfigManager.getProperties().getProperty("seleniumGrid").trim().equalsIgnoreCase("true")) {
					//org.openqa.selenium.WebDriver augmentedDriver = new Augmenter().augment(CommonVariables.getDriver());
					System.out.println("Augmenter in save screenshot");
					try {
						screenshot = ((TakesScreenshot) CommonVariables.getDriver()).getScreenshotAs(OutputType.FILE);
					} catch (WebDriverException e) {
						e.printStackTrace();
					}
				} else if (ConfigManager.getProperties().getProperty("cloudExecution").trim()
						.equalsIgnoreCase("true")) {
					//org.openqa.selenium.WebDriver augmentedDriver = new Augmenter().augment(CommonVariables.getDriver());
					System.out.println("Augmenter in save screenshot2");
					try {
						screenshot = ((TakesScreenshot) CommonVariables.getDriver()).getScreenshotAs(OutputType.FILE);
					} catch (WebDriverException e) {
						e.printStackTrace();
					}
				} else if (CommonVariables.getDriver().getClass().toString().toLowerCase().contains("chromedriver")
						|| CommonVariables.getDriver().getClass().toString().toLowerCase().contains("safari")
						|| CommonVariables.getDriver().getClass().toString().toLowerCase().contains("firefox")
						|| CommonVariables.getDriver().getClass().toString().toLowerCase().contains("internet")
						|| CommonVariables.getDriver().getClass().toString().toLowerCase().contains("ie")
						|| CommonVariables.getDriver().getClass().toString().toLowerCase().contains("edge")
						|| CommonVariables.getDriver().getClass().toString().toLowerCase().contains("Microsoft")
						|| CommonVariables.getDriver().getClass().toString().toLowerCase().contains("opera")
						|| CommonVariables.getDriver().getClass().toString().toLowerCase().contains("PhantomJS")
						|| CommonVariables.getDriver().getClass().toString().toLowerCase().contains("remotewebdriver")) {
					screenshot = ((TakesScreenshot) CommonVariables.getDriver()).getScreenshotAs(OutputType.FILE);
				}
				else {
					screenshot = ((TakesScreenshot) CommonVariables.getDriver()).getScreenshotAs(OutputType.FILE);
				}
				File screenshotfile = new File(ImgPath);
				//System.out.println("ImgPath: "+ImgPath);
				try {
					FileUtils.copyFile(screenshot, screenshotfile);
					if (CommonVariables.getDriver().getClass().toString().toLowerCase().contains("internet")
							|| CommonVariables.getDriver().getClass().toString().toLowerCase().contains("ie")) {
						Thread.sleep(1);
					}
					return true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			System.out.println();
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * Save full screen shot.
	 *
	 * @author Cigniti
	 * @param ImgPath
	 *            the img path
	 * @description : Capture Full Screenshot
	 */
	public static void saveFullScreenShot(String ImgPath) {
		CaptureScreenShot fullscreenshot = new CaptureScreenShot();
		try {
			fullscreenshot.seleniumCaptureBrowserScreenShot(CommonVariables.getDriver(), ImgPath);
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * **************************************************************************************************************************
	 * Function Name : isElementEnabled() Description : Element is disabled or not.
	 *
	 * @param objLocator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * @param strLocatorName
	 *            : Meaningful name to the element (Ex:Login Button, UserName
	 *            Textbox etc..)
	 * @return true, if is element enabled
	 * @throws Throwable
	 *             the throwable
	 * @return: boolean value (True: if the element is enabled, false: if it not
	 *          enabled).
	 *          **************************************************************************************************************************
	 */
	public boolean isElementEnabled(WebElement objLocator, String strLocatorName) throws Throwable {
		Boolean blnFlag = false;
		try {
			if ((objLocator).isEnabled()) {
				blnFlag = true;
				extentLogs.pass("Web Locator Status", "Web locator is enabled for - '" + strLocatorName + "'.");
			}
			return blnFlag;
		} catch (NoSuchElementException e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
	}

	/**
	 * **************************************************************************************************************************
	 * Function Name : isElementDisabled() Description : Element is disabled or not.
	 *
	 * @param objLocator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * @param strLocatorName
	 *            : Meaningful name to the element (Ex:Login Button, UserName
	 *            Textbox etc..)
	 * @return true, if is element disabled
	 * @throws Throwable
	 *             the throwable
	 * @return: boolean value (True: if the element is enabled, false: if it not
	 *          enabled).
	 *          **************************************************************************************************************************
	 */
	public boolean isElementDisabled(WebElement objLocator, String strLocatorName) throws Throwable {
		Boolean blnFlag = false;
		try {
			if (!(objLocator).isEnabled()) {
				blnFlag = true;
				extentLogs.pass("Web Locator Status", "Web locator is disabled for - '" + strLocatorName + "'.");
			}
			return blnFlag;
		} catch (NoSuchElementException e) {
			gStrErrMsg = e.getMessage();
			return blnFlag;
		}
	}

	/**
	 * Checks if is element visible.
	 *
	 * @author Cigniti
	 * @param by
	 *            the by
	 * @return true, if successful
	 * @description : Is Element Visible in UI
	 */
	public boolean IsElementVisible(final By by) {
		try {
			wait = new WebDriverWait(CommonVariables.getDriver(), 1);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			
			if ((CommonVariables.getDriver().findElement(by).getSize().height == 0) && (CommonVariables.getDriver().findElement(by).getSize().width == 0)) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			try {
				if ((e.getClass().isInstance(new TimeoutException(""))
						|| getClass().isInstance(new ElementNotVisibleException("")))
						|| (e.getClass().isInstance(new NoSuchElementException("")))) {
					if (ScrollToElementVisible(by)) {
						wait.until(ExpectedConditions.visibilityOfElementLocated(by));
						if ((CommonVariables.getDriver().findElement(by).getSize().height == 0)
								&& (CommonVariables.getDriver().findElement(by).getSize().width == 0)) {
							return false;
						} else {
							return true;
						}
					} else {
						throw e;
					}
				} else {
					throw e;
				}
			} catch (Exception e1) {
				if (e1.getCause().toString().contains("NoSuchElementException")) {
					throw new NoSuchElementException("");
				} else if (e1.getCause().toString().contains("ElementNotVisibleException")) {
					throw new ElementNotVisibleException("");
				} else if (e1.getCause().toString().contains("TimeoutException")) {
					throw new TimeoutException();
				} else {
					throw e1;
				}
			}
		}
	}

	/**
	 * Checks if is element visible.
	 *
	 * @author Cigniti
	 * @param element
	 *            the element
	 * @return true, if successful
	 * @description : Is Element Visible in UI
	 */
	public boolean IsElementVisible(WebElement element) {
		try {
			wait = new WebDriverWait(CommonVariables.getDriver(), 1);
			wait.until(ExpectedConditions.visibilityOf(element));
			if ((element.getSize().height == 0) && (element.getSize().width == 0)) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			try {
				if ((e.getClass().isInstance(new TimeoutException(""))
						|| getClass().isInstance(new ElementNotVisibleException("")))
						|| (e.getClass().isInstance(new NoSuchElementException("")))) {
					if (ScrollToElementVisible(element)) {
						wait.until(ExpectedConditions.visibilityOf(element));
						if ((element.getSize().height == 0) && (element.getSize().width == 0)) {
							return false;
						} else {
							return true;
						}
					} else {
						throw e;
					}
				} else {
					throw e;
				}
			} catch (Exception e1) {
				if (e1.getCause().toString().contains("NoSuchElementException")) {
					throw new NoSuchElementException("");
				} else if (e1.getCause().toString().contains("ElementNotVisibleException")) {
					throw new ElementNotVisibleException("");
				} else if (e1.getCause().toString().contains("TimeoutException")) {
					throw new TimeoutException();
				} else {
					throw e1;
				}
			}
		}
	}

	/**
	 * Scroll to top.
	 *
	 * @author Cigniti
	 * @description: scroll to top of the page
	 */
	public void ScrollToTop() {
		if (GetDriverInfo().get("DriverType").trim().equalsIgnoreCase("desktop")) {
			try {
				JavascriptExecutor js = (JavascriptExecutor) CommonVariables.getDriver();
				js.executeScript("window.scrollTo(0,0);");
			} catch (Exception e) {
			}
		} else {
			try {
				JavascriptExecutor js = (JavascriptExecutor) CommonVariables.getDriver();
				js.executeScript("$('body').scrollTop(0);");
			} catch (Exception e) {
				System.out.println(
						"Failed to Swipe on Top on Non-Desktop device. Check 'ScrollToTop' method under action library");
			}
		}
	}

	/**
	 * Scroll to bottom.
	 *
	 * @author Cigniti
	 * @description: scroll page to bottom of the page
	 */
	public void ScrollToBottom() {
		if (GetDriverInfo().get("DriverType").trim().equalsIgnoreCase("desktop")) {
			try {
				JavascriptExecutor js = (JavascriptExecutor) CommonVariables.getDriver();
				js.executeScript("window.scrollTo(0,document.documentElement.scrollHeight);");
			} catch (Exception e) {
			}
		} else {
			JavascriptExecutor js = (JavascriptExecutor) CommonVariables.getDriver();
			int height_covered = 0, pageHeightLeft = 0;
			Long pageheight1 = (Long) js.executeScript("return window.innerHeight");
			Long maxPageHeight1 = (Long) js
					.executeScript("return Math.max(document.documentElement.scrollHeight, document.body.scrollHeight,"
							+ "document.documentElement.clientHeight, window.innerHeight)");
			float sections = (float) maxPageHeight1 / pageheight1;
			int numberOfRows = (int) Math.ceil(sections);
			int pageheight = pageheight1.intValue();
			int maxPageHeight = maxPageHeight1.intValue();
			for (int row = 0; row < numberOfRows; row++) {
				pageHeightLeft = maxPageHeight - height_covered;
				if ((pageHeightLeft < pageheight)) {
				} else {
					height_covered = height_covered + pageheight;
					js.executeScript("window.scrollTo(0," + height_covered + ")");
				}
			}
		}
	}

	/**
	 * Gets the element X path.
	 *
	 * @author Cigniti
	 * @param element
	 *            the element
	 * @return the element X path
	 * @description : Get Element Xpath
	 */
	public String getElementXPath(WebElement element) {
		try {
			String str = element.toString().split("->")[1].trim();
			if (str.contains("xpath")) {
				String str2 = str.split("xpath: ")[1].trim();
				str = str2.substring(0, str2.length() - 1);
			} else {
				String str2 = str.split(": ")[1].trim();
				str = str.split(": ")[0].trim() + "=" + str2.substring(0, str2.length() - 1);
			}
			return str;
		} catch (Exception e) {
			return "<failed to retrive xpath>";
		}
	}

	/**
	 * Kill window process.
	 *
	 * @author Cigniti
	 * @param processName
	 *            the process name
	 * @throws Exception
	 *             the exception
	 * @description : Kill window process
	 */
	public void KillWindowProcess(String processName) throws Exception {
		Platform p = Platform.getCurrent();
		if (!p.is(Platform.MAC) && !checkOS().contains("Linux")) {
			//System.out.println("Kill windows");
			String TASKLIST = "tasklist";
			String KILL = "taskkill /F /IM ";
			Process proc = Runtime.getRuntime().exec(TASKLIST);
			BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains(processName)) {
					Runtime.getRuntime().exec(KILL + processName);
				}
			}
		} else if(!checkOS().contains("Windows")){
			try {
				System.out.println("Kill mac/linux");
				Runtime.getRuntime().exec("killall " + processName);
				Runtime.getRuntime().exec("taskkill /F /IM " + processName);
			} catch (Exception e) {
				LOG.info("Error killing process in MAC/Linux");
				Runtime.getRuntime().exec("killall " + processName);
			}
		}
		else{
			try {
				System.out.println("Kill Linux");
				Runtime.getRuntime().exec("killall " + processName);
				//Runtime.getRuntime().exec("taskkill /F /IM " + processName);	
			}catch(Exception e){
				LOG.info("Error killing process in Linux");
			}
					
		}
		
	}
	
	/**
	 * Check.
	 *
	 * @param application the application
	 * @return true, if successful
	 */
	public boolean check(String application) {
	  	//TODO: MAKE THIS DO A SWITCH STATEMETN INSTEAD OF A 4 IF STATEMENTS.
	        if (checkOS().contains("Windows")) {
	            try {
	            	System.out.println("Windows");
	                String line;
	                Process p = Runtime.getRuntime().exec(System.getenv("windir")
	                        + "\\system32\\" + "tasklist.exe");
	                BufferedReader input = new BufferedReader(
	                        new InputStreamReader(p.getInputStream()));

	                while ((line = input.readLine()) != null) {
	                    if (line.contains(application)) { //Parsses the line
	                        return true;
	                    }
	                }
	                input.close();
	            }
	            catch (Exception err) {
	                LOG.error(err.toString());
	            }
	            return false;
	        }

	        else if (checkOS().contains("Linux")) {
	            try {
	            	System.out.println("Linux");
	                String line;
	                Process p = Runtime.getRuntime().exec("ps -e");
	                BufferedReader input = new BufferedReader(
	                        new InputStreamReader(p.getInputStream()));
	                while ((line = input.readLine()) != null) {
	                    if (line.contains(application)) {
	                        return true;
	                    }
	                }
	                input.close();
	            }
	            catch (Exception err) {
	            	LOG.error(err.toString());
	            }
	            return false;
	        }

	        else if (checkOS().contains("Mac OS")) {
	            try {
	            	System.out.println("Mac");
	                String line;
	                Process p = Runtime.getRuntime().exec("ps -e");
	                BufferedReader input = new BufferedReader(
	                        new InputStreamReader(p.getInputStream()));
	                while ((line = input.readLine()) != null) {
	                    if (line.contains(application)) {
	                        return true;
	                    }
	                }
	                input.close();
	            }
	            catch (Exception err) {
	            	LOG.error(err.toString());
	            }
	            return false;
	        }

	        else {
	            return false;
	        }
	    }

	    /**
    	 * Check OS.
    	 *
    	 * @return A string containing the name of the operating system.
    	 */
	    private String checkOS() {
	        String os;
	        os = System.getProperty("os.name");
	        return os;
	    }

	/**
	 * Select from drop down.
	 *
	 * @author Cigniti
	 * @param ddb
	 *            the ddb
	 * @param value
	 *            the value
	 * @param index
	 *            the index
	 * @description: Select values from drop down button using visible text
	 */
	public void SelectFromDropDown(WebElement ddb, String value, int index) {
		try {
			Select dropdown = new Select(ddb);
			dropdown.selectByVisibleText(value);
		} catch (Exception e) {
			try {
				JavascriptExecutor js = (JavascriptExecutor) CommonVariables.getDriver();
				js.executeScript("arguments[0].selectedIndex = arguments[1];", ddb, index);
				js.executeScript("$(arguments[0]).trigger('change');", ddb);
			} catch (Exception e5) {
			}
		}

	}

	/**
	 * Count element.
	 *
	 * @author Cigniti
	 * @param by
	 *            the by
	 * @param timeoutInSeconds
	 *            the timeout in seconds
	 * @param description
	 *            the description
	 * @return the integer
	 * @description: Elements counts
	 */
	public Integer CountElement(By by, Optional<Long> timeoutInSeconds, String description) {
		try {
			if (!description.equals("")) {
				extentLogs.info("Elements Count", "Get the count for " + description);
			}
			return FindElements(by, timeoutInSeconds).size();
		} catch (NoSuchElementException e) {
			extentLogs.error("Elements Count",
					"Unable to get the count of elements due to exception - " + e.getLocalizedMessage());
			return 0;
		} catch (Exception e) {
			extentLogs.error("Elements Count",
					"Unable to get the count of elements due to exception - " + e.getLocalizedMessage());
			return 0;
		}
	}

	/**
	 * Gets the element inner text.
	 *
	 * @author Cigniti
	 * @param by
	 *            the by
	 * @param description
	 *            the description
	 * @return the element inner text
	 * @throws Throwable
	 *             the throwable
	 * @description: get element inner text directly using locator value
	 */
	public String getElementInnerText(By by, String description) throws Throwable {
		try {
			if (!description.equals("")) {
				extentLogs.pass("Inner Text of Element", "Get text for " + description);
			}
			WebElement elem = FindElement(by, Optional.of(Long.parseLong("4")));
			JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
			String text = (String) js.executeScript("return arguments[0].innerHTML.toString();", elem);
			return text;

		} catch (NoSuchElementException e) {
			extentLogs.error("Inner Text of Element",
					"No such element found to get inner text due to execption - " + e.getLocalizedMessage());
			return "";
		} catch (Exception e) {
			CommonVariables.getDriver().findElement(by).getText();
			return "";
		}
	}

	/**
	 * Gets the element inner text.
	 *
	 * @author Cigniti
	 * @param elem
	 *            the elem
	 * @param description
	 *            the description
	 * @return the element inner text
	 * @description: get element inner text using WebElement
	 */
	public String getElementInnerText(WebElement elem, String description) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
			String text = (String) js.executeScript("return arguments[0].innerHTML.toString();", elem);
			if (!text.equals("")) {
				extentLogs.pass("Get inner text", "Inner Text of" + description + ":" + text);
				CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info", "Get inner text for " + description);
			}
			return text;
		} catch (NoSuchElementException e) {
			extentLogs.error("Get inner text", "No such element found to get element inner text.");
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "error", "No such element found");
			return "";
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Gets the element text.
	 *
	 * @author Cigniti
	 * @param by
	 *            the by
	 * @param description
	 *            the description
	 * @return the element text
	 * @throws Throwable
	 *             the throwable
	 * @description: get text between open and close tag
	 */
	public String getElementText(By by, String description) throws Throwable {
		try {
			WaitForElementVisible(by, 10,description);
			String text = FindElement(by, Optional.of(Long.parseLong("4"))).getText();
			if (!text.equals("")) {
				extentLogs.pass("Get text for " + description, "Element Text: " + text);
				CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info", "Get text for " + description);
			}
			return text;
		} catch (NoSuchElementException e) {
			extentLogs.error("Element Text",
					"No such element found to get element text due to exception - " + e.getLocalizedMessage());
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info", "No such element found XPATH: " + by);
			return "";
		} catch (NullPointerException e) {
			extentLogs.error("Element Text",
					"No such element found to get element text due to exception - " + e.getLocalizedMessage());
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info", "No such element found XPATH: " + by);
			return "";
		} catch (Exception e) {
			CommonVariables.getDriver().findElement(by).getText();
			extentLogs.error("Element Text",
					"No such element found to get element text due to exception - " + e.getLocalizedMessage());
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info", e.getLocalizedMessage() + " - " + by);
			return "";
		}
	}

	/**
	 * Gets the selected element text from drop down.
	 *
	 * @param elem
	 *            the elem
	 * @param description
	 *            the description
	 * @return the selected element text from drop down
	 */
	public String getSelectedElementTextFromDropDown(WebElement elem, String description) {
		try {
			String text = new Select(elem).getFirstSelectedOption().getText();
			if (!text.equals("")||!text.isEmpty()) {
				extentLogs.pass("Get selected text from dropdown", "Selected Text of " + description + ": " + text);
				CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info",
						"Get selected text from dropdown " + description);
			}
			return text;
		} catch (NoSuchElementException e) {
			extentLogs.error("Get selected text from dropdown ", "No such element found to get element inner text.");
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "error", "No such element found");
			return "";
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Compare values.
	 *
	 * @author Cigniti
	 * @param actual
	 *            the actual
	 * @param expected
	 *            the expected
	 * @param errorText
	 *            the error text
	 * @return the boolean
	 * @description: compare values for validation
	 */
	public Boolean compareValues(String actual, String expected, String errorText) {
		boolean flag = true;
		if (!actual.trim().equalsIgnoreCase(expected.trim())) {
			extentLogs.fail("Compare Text Values",
					"" + errorText + " did not match. Expected text is-" + expected + " and Actual text is-" + actual);
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "error",
					errorText + " did not match. Expected text is-" + expected + " and Actual text is-" + actual);
			flag = false;
		} else {
			extentLogs.pass("Compare Text Values",
					"Actual and Expected Value are matched for - "+errorText+" , expected and actual value is " + actual);
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info",
					"Actual and Expected Value are matched for - "+errorText+" , expected and actual value is " + actual);
			flag = true;
		}
		return flag;
	}

	/**
	 * Check string contains.
	 *
	 * @author Cigniti
	 * @param actual
	 *            the actual
	 * @param expected
	 *            the expected
	 * @param errorText
	 *            the error text
	 * @return the boolean
	 * @description: verify string contains for validation
	 */
	public Boolean checkStringContains(String actual, String expected, String errorText) {
		boolean flag = true;
		if (!actual.trim().contains(expected.trim())) {
			extentLogs.fail("Presence of Text", "" + errorText + " did not contain expected String. Expected string is-"
					+ expected + " and Actual string is-" + actual);
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "error",
					errorText + " did not contain expected String. Expected string is-" + expected
							+ " and Actual string is-" + actual);
			flag = false;
		} else {
			extentLogs.pass("Presence of Text",
					"Actual string contains the expected String, expected and actual string is " + expected);
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "PASS",
					"Actual string contains the expected String, expected and actual string is " + expected);
			flag = true;
		}
		return flag;
	}

	/**
	 * Gets the element attribute.
	 *
	 * @author Cigniti
	 * @param by
	 *            the by
	 * @param attribute
	 *            the attribute
	 * @param description
	 *            the description
	 * @return the element attribute
	 * @description: get element attribute value with description.
	 */
	public String getElementAttribute(By by, String attribute, String description) {
		try {			
			return CommonVariables.getDriver().findElement(by).getAttribute(attribute).toString();
			
		} catch (NoSuchElementException e) {
			extentLogs.error("Attribute Value",
					"No such element found to get attribute value due to exception - " + e.getLocalizedMessage());
			return "";
		} catch (Exception e) {
			CommonVariables.getDriver().findElement(by).getText();
			extentLogs.error("Attribute Value",
					"No such element found to get attribute value due to exception - " + e.getLocalizedMessage());
			return "";
		}
	}

	/**
	 * The Enum Mode.
	 *
	 * @author Cigniti
	 */
	public static enum Mode {

		/** The alpha. */
		ALPHA,
		/** The alphanumeric. */
		ALPHANUMERIC,
		/** The numeric. */
		NUMERIC
	}

	/**
	 * Generate random string.
	 *
	 * @author Cigniti
	 * @param length
	 *            the length
	 * @param mode
	 *            the mode
	 * @return the string
	 * @descriton: generate random number - alpa,num,alphnum
	 */
	public static String generateRandomString(int length, Mode mode) {
		StringBuffer buffer = new StringBuffer();
		String characters = "";
		Boolean isNumericOnly = false;
		String automationTextPrefix = "";
		switch (mode) {
		case ALPHA:
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
			break;

		case ALPHANUMERIC:
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
			break;

		case NUMERIC:
			isNumericOnly = true;
			characters = "123456789";
			break;
		}
		int charactersLength = characters.length();
		if (length >= 3 && !isNumericOnly) {
			length = length - 2;
			automationTextPrefix = "AT";
		}
		for (int i = 0; i < length; i++) {
			double index = Math.random() * charactersLength;
			buffer.append(characters.charAt((int) index));
		}
		if (isNumericOnly) {
			return buffer.toString();
		} else {
			return automationTextPrefix + buffer.toString();
		}
	}

	/**
	 * Checks if is text present on page.
	 *
	 * @author Cigniti
	 * @param txtValue
	 *            the txt value
	 * @return true, if is text present on page
	 * @description: verify text presence in UI
	 */
	public boolean isTextPresentOnPage(String txtValue) {
		boolean b = false;
		try {
			//String bodyText = driver.findElement(By.tagName("body")).getText();
			String bodyText = CommonVariables.CommonDriver.get().findElement(By.tagName("body")).getText();
			b = bodyText.contains(txtValue.trim());
			if (b) {
				extentLogs.pass("Presence of Text", "Info : '" + txtValue + "' is found on Page.");
				b = true;
			} else {
				b = false;
			}
		} catch (Exception e) {
			extentLogs.error("Presence of Text", "Exception in isTextPresentOnPage" + e.getMessage());
			b = false;
		}
		return b;
	}

	/**
	 * Gets the current data time.
	 *
	 * @author Cigniti
	 * @param dateformat
	 *            the dateformat
	 * @return the current data time
	 * @description e.g. for Jul 16 2014 03:32:06 AM format pass 'MMM dd yyyy
	 *              hh:mm:ss aa' as dateformat value.
	 */
	public String getCurrentDataTime(String dateformat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		try {
			return sdf.format(cal.getTime());
		} catch (Exception e) {
			e.getMessage();
			return "";
		}
	}

	/**
	 * Gets the date time from provided string.
	 *
	 * @author Cigniti
	 * @param dateValue
	 *            the date value
	 * @param format
	 *            the format
	 * @return the date time from provided string
	 */
	public Date getDateTimeFromProvidedString(String dateValue, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			Date date = formatter.parse(dateValue);
			return date;
		} catch (ParseException e) {
			e.getMessage();
			return null;
		}
	}

	/**
	 * Gets the date time from provided string.
	 *
	 * @author Cigniti
	 * @param date
	 *            the date
	 * @param dateformat
	 *            the dateformat
	 * @return the date time from provided string
	 * @description: get date in user's required format
	 */
	public String getDateTimeFromProvidedString(Date date, String dateformat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		try {
			return sdf.format(date);
		} catch (Exception e) {
			// AddToLog("CurrentTestCaseLog", "error","Failed to convert current
			// Date and Time in the provided format ("+dateformat+")");
			return "";
		}
	}

	/**
	 * Wait for element present.
	 *
	 * @author Cigniti
	 * @param by
	 *            the by
	 * @param timeout
	 *            the timeout
	 * @return the boolean
	 * @description: wait for element present
	 */
	public Boolean waitForElementPresent(By by, Integer timeout) {
		Boolean flag = false;
		for (Integer i = 1; i <= timeout; i++) {
			if (IsElementExist(by, Optional.of(Long.parseLong("0")))) {
				flag = true;
				break;
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
		return flag;
	}

	/**
	 * Wait for element not present.
	 *
	 * @author Cigniti
	 * @param by
	 *            the by
	 * @param timeout
	 *            the timeout
	 * @return the boolean
	 * @description: wait for element not present
	 */
	public Boolean waitForElementNotPresent(By by, Integer timeout) {
		Boolean flag = false;
		for (Integer i = 1; i <= timeout; i++) {
			if (!IsElementExist(by, Optional.of(Long.parseLong("1")))) {
				flag = true;
				break;
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
		}
		return flag;
	}

	/**
	 * Sets the focus by xpath.
	 *
	 * @author Cigniti
	 * @param xpath
	 *            the new focus by xpath
	 * @description: move to element directly using locator value
	 */
	public void setFocusByXpath(String xpath) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
			js.executeScript(
					"$(document.evaluate(\"" + xpath + "\", document, null, 9, null).singleNodeValue).focusin();");
		} catch (NullPointerException ex) {
			try {
				JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.getDriver());
				js.executeScript(
						"$(document.evaluate(\"" + xpath + "\", document, null, 9, null).singleNodeValue).focusin();");
			} catch (Exception e) {

			}
		}
	}

	/**
	 * Change string first char to capital.
	 *
	 * @author Cigniti
	 * @param str
	 *            the str
	 * @return the string
	 * @description: change first character of string to Capital Letter
	 */
	public static String ChangeStringFirstCharToCapital(String str) {
		StringBuilder b = new StringBuilder(str);
		int i = 0;
		do {
			b.replace(i, i + 1, b.substring(i, i + 1).toUpperCase());
			i = b.indexOf(" ", i) + 1;
		} while (i > 0 && i < b.length());
		return b.toString();
	}

	/**
	 * Gets the date in expected format.
	 *
	 * @author Cigniti
	 * @param format
	 *            the format
	 * @param dateInString
	 *            the date in string
	 * @param returnformat
	 *            the returnformat
	 * @return the date in expected format
	 * @description : get Date In Expected Format
	 */
	public String getDateInExpectedFormat(String format, String dateInString, String returnformat) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		SimpleDateFormat returnFormatter = new SimpleDateFormat(returnformat);
		Date date = null;
		try {
			date = formatter.parse(dateInString);
			// formatter = new SimpleDateFormat(returnformat);
			return returnFormatter.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Scroll to element visible.
	 *
	 * @author Cigniti
	 * @param elementBy
	 *            the element by
	 * @return true, if successful
	 * @description : Swipe To Element Visible
	 */
	public boolean ScrollToElementVisible(By elementBy) {
		try {
			WebElement elem = (new WebDriverWait(CommonVariables.getDriver(), 10))
					.until(ExpectedConditions.presenceOfElementLocated(elementBy));
			return ScrollToElementVisible(elem);
		} catch (Exception ex) {
			ex.getLocalizedMessage();
			return false;
		}
	}

	/**
	 * Scroll to element visible.
	 *
	 * @author Cigniti
	 * @param element
	 *            the element
	 * @return true, if successful
	 * @description : Scroll To Element Visible
	 */
	public boolean ScrollToElementVisible(WebElement element) {
		if (CommonVariables.PlatformName.get().equalsIgnoreCase("windows")) {
			try {
				ScrollToTop();
				Point p = element.getLocation();
				if (p.getX() == 0 && p.getY() == 0) {
					return false;
				} else {
					((JavascriptExecutor) CommonVariables.getDriver())
							.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 120) + ");");
					try {
						if (CommonVariables.getDriver().getClass().toString().toLowerCase().contains("internet")
								|| CommonVariables.getDriver().getClass().toString().toLowerCase().contains("ie")
								|| CommonVariables.getDriver().getClass().toString().toLowerCase().contains("edge")
								|| CommonVariables.getDriver().getClass().toString().toLowerCase().contains("safari")
								|| CommonVariables.getDriver().getClass().toString().toLowerCase().contains("opera")) {
							Thread.sleep(80);
						}
					} catch (Exception e) {
					}
					return true;
				}
			} catch (Exception ex) {
				throw ex;
			}
		} else {
			try {
				Dimension d = element.getSize();
				if (d.height != 0 && d.width != 0) {
					new Actions(CommonVariables.getDriver()).moveToElement(element).perform();
					return true;
				} else {
					JavascriptExecutor js = (JavascriptExecutor) CommonVariables.getDriver();
					int height_covered = 0, pageHeightLeft = 0;
					Long pageCurrentHeight = (Long) js.executeScript("return window.innerHeight");
					ScrollToTop();
					Long pageheight1 = (Long) js.executeScript("return window.innerHeight");
					Long maxPageHeight1 = (Long) js.executeScript(
							"return Math.max(document.documentElement.scrollHeight, document.body.scrollHeight,"
									+ "document.documentElement.clientHeight, window.innerHeight)");
					float sections = (float) maxPageHeight1 / pageheight1;
					int numberOfRows = (int) Math.ceil(sections);
					int pageheight = pageheight1.intValue();
					int maxPageHeight = maxPageHeight1.intValue();
					for (int row = 0; row < numberOfRows; row++) {
						pageHeightLeft = maxPageHeight - height_covered;
						if ((pageHeightLeft < pageheight)) {
							d = element.getSize();
							if (d.height == 0 && d.width == 0) {
								js.executeScript("window.scrollTo(0," + pageCurrentHeight + ")");
								return false;
							} else {
								new Actions(CommonVariables.getDriver()).moveToElement(element).perform();
								return true;
							}
						} else {
							d = element.getSize();
							if (!(d.height != 0 && d.width != 0)) {
								height_covered = height_covered + pageheight;
								js.executeScript("window.scrollTo(0," + height_covered + ")");
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
								}
							} else {
								new Actions(CommonVariables.getDriver()).moveToElement(element).perform();
								return true;
							}
						}
					}
					ScrollToTop();
					return false;
				}
			} catch (org.openqa.selenium.ElementNotVisibleException e) {
				throw new ElementNotVisibleException("");
			} catch (Exception ex) {
				throw ex;
			}
		}
	}

	/**
	 * Mouseover.
	 *
	 * @author Cigniti
	 * @param webelement
	 *            the webelement
	 * @description : Mouse Over
	 */
	public void Mouseover(WebElement webelement) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
			js.executeScript("$(arguments[0]).trigger('mouseover');", webelement);
		} catch (Exception e) {
			e.getLocalizedMessage();
		}
	}

	/**
	 * Gets the xpath from by.
	 *
	 * @param by
	 *            the by
	 * @return the string
	 */
	public String GetXpathFromBy(By by) {
		try {
			return by.toString().split("By.xpath: ")[1].trim();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Checks if is element display in current view.
	 *
	 * @author Cigniti
	 * @param xpath
	 *            the xpath
	 * @return true, if successful
	 * @description : Is Element Display In Current View
	 */
	public boolean IsElementDisplayInCurrentView(By xpath) {
		try {
			return IsElementDisplayInCurrentView(CommonVariables.getDriver().findElement(xpath));
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Checks if is element display in current view.
	 *
	 * @author Cigniti
	 * @param webElement
	 *            the web element
	 * @return true, if successful
	 * @description: verify visibility of an element in current open view in UI
	 */
	public boolean IsElementDisplayInCurrentView(WebElement webElement) {
		double browser_top_offset = 0.0;
		try {
			JavascriptExecutor js = (JavascriptExecutor) CommonVariables.getDriver();
			Long pageheight = (Long) js.executeScript("return $(window).height()");
			double elemPos = 0;
			try {
				Long posY = (Long) js.executeScript("return $(arguments[0]).offset().top - $(window).scrollTop()",
						webElement);
				elemPos = posY.doubleValue();
			} catch (ClassCastException cc) {
				elemPos = (Double) js.executeScript("return $(arguments[0]).offset().top - $(window).scrollTop()",
						webElement);
			}
			double pageheight1 = pageheight.doubleValue();
			if (elemPos >= browser_top_offset && elemPos < pageheight1)
				return true;
			else
				return false;
		} catch (Exception ex) {
			LOG.info("" + ex.getMessage());
			return false;
		}
	}

	/**
	 * Gets the cookies.
	 *
	 * @author Cigniti
	 * @return the cookies
	 * @description : get cookies
	 */
	public String[] getcookies() {
		String[] stringArray = null;
		try {
			Set<Cookie> cookie = CommonVariables.CommonDriver.get().manage().getCookies();
			Object[] cookie_text = cookie.toArray();
			stringArray = new String[cookie_text.length];
			for (int i = 0; i < cookie_text.length; i++) {
				stringArray[i] = cookie_text[i].toString();
			}
		} catch (Exception e) {
			LOG.info("Not able to get cookies for the selected page due to error message :" + e.getMessage());
		}
		return stringArray;
	}

	/**
	 * Gets the random number between.
	 *
	 * @param upper
	 *            upper limit
	 * @param lower
	 *            lower limit
	 * @return the random number between
	 */
	public Integer getRandomNumberBetween(Integer upper, Integer lower) {
		return (int) ((Math.random() * (upper - lower)) + lower);
	}

	/**
	 * Sets the download path for file.
	 *
	 * @param downloadpath
	 *            the new download path for file
	 */
	public void setDownloadPathForFile(String downloadpath) {
		try {
			String downloadFilepath = downloadpath;
			File fl = new File(downloadFilepath);
			if (!fl.exists()) {
				fl.mkdir();
			}
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_settings.popups", 0);
			prefs.put("download.default_directory", downloadFilepath);
			prefs.put("savefile.default_directory", downloadFilepath);
			chromeoptions.setExperimentalOption("prefs", prefs);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * Highlight web element.
	 *
	 * @author Cigniti
	 * @param elem
	 *            the elem
	 * @param durationInSeconds
	 *            the duration in seconds
	 * @description: hightlight WebElement
	 */
	public void HighlightWebElement(WebElement elem, int durationInSeconds) {
		try {
			Thread.sleep(durationInSeconds);
			durationInSeconds = durationInSeconds * 10;
			String original_style = elem.getAttribute("style");
			((JavascriptExecutor) CommonVariables.getDriver()).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", elem,
					"style", "border: 6px solid red; border-style: dashed;");
			if (durationInSeconds > 0) {
				Thread.sleep(durationInSeconds);
				((JavascriptExecutor) CommonVariables.getDriver()).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])",
						elem, "style", original_style);
			}
		} catch (Exception e) {

		}
	}

	/**
	 * Switch into frame.
	 *
	 * @author Cigniti
	 * @param iFrameID
	 *            the i frame ID
	 * @return true, if successful
	 * @description : Switch In to iFrame
	 */
	public boolean SwitchIntoFrame(WebElement iFrameID) {
		try {
			CommonVariables.getDriver().switchTo().frame(iFrameID);
			System.out.println("switch to frame sucessfully.");
			return true;
		} catch (Exception e) {
			System.out.println("Failed to switch to frame due to error :" + e.getMessage());
			return false;
		}
	}

	/**
	 * Switch outfrom frame.
	 *
	 * @author Cigniti
	 * @return the web driver
	 */
	public WebDriver SwitchOutfromFrame() {
		return CommonVariables.getDriver().switchTo().defaultContent();

	}

	/**
	 * Delete file.
	 *
	 * @author Cigniti
	 * @param file
	 *            the file
	 * @return the boolean
	 */
	public Boolean deleteFile(File file) {
		try {
			if (file.isDirectory()) {
				String fileList[] = file.list();
				if (fileList.length == 0) {
					extentLogs.info("Delete File", "Deleting Directory : " + file.getPath());
					file.delete();
					extentLogs.info("Delete File", "Deleted Directory : " + file.getPath());
				} else {
					int size = fileList.length;
					for (int i = 0; i < size; i++) {
						String fileName = fileList[i];
						extentLogs.info("Delete File", "File path : " + file.getPath() + " and name :" + fileName);
						String fullPath = file.getPath() + "/" + fileName;
						File fileOrFolder = new File(fullPath);
						extentLogs.info("Delete File", "File copied, full path is: " + fileOrFolder.getPath());
						deleteFile(fileOrFolder);
					}
				}
				return true;
			} else {
				extentLogs.info("Delete File", "Deleting file : " + file.getPath());
				file.delete();
				extentLogs.info("Delete File", "Deleted file : " + file.getPath());
				return true;
			}
		} catch (Exception e) {
			extentLogs.error("Delete File", "Deleted file : " + file.getPath());
			return false;
		}
	}

	/**
	 * Verify presence of file or folder at location.
	 *
	 * @param path
	 *            - Complete Path of the folder which suppose to contain file
	 * @param fileName
	 *            - Name of the file. It'll compare case
	 * @return True, if file found.
	 * @description: Verify the presence of a file at a particular location. File
	 *               name is not case sensitive and can provide complete/partial
	 *               name of the file.
	 */
	public Boolean verifyPresenceOfFileOrFolderAtLocation(String path, String fileName) {
		try {
			File file = new File(path);
			File[] listOfFiles = file.listFiles();
			if (listOfFiles.length != 0) {
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].getName().toLowerCase().contains(fileName.toLowerCase())) {
						return true;
					}
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * <b>Description:</b> Copies the entire src File to Dest.
	 *
	 * @param src
	 *            the src
	 * @param dest
	 *            the dest
	 * @throws Exception
	 *             the exception
	 */
	public void copyFile(File src, File dest) throws Exception {
		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
				System.out.println("Directory copied from " + src + " to " + dest);
			}
			String files[] = src.list();
			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				copyFile(srcFile, destFile);
			}
		} else {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
			System.out.println("File copied from " + src + " to " + dest);
		}
	}

	/**
	 * <b>Description:</b> Copies the entire src File to Dest.
	 *
	 * @param src
	 *            the src
	 * @param dest
	 *            the dest
	 * @throws Exception
	 *             the exception
	 */
	public static void staticCopyFile(File src, File dest) throws Exception {
		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
				System.out.println("Directory copied from " + src + " to " + dest);
			}

			String files[] = src.list();

			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				staticCopyFile(srcFile, destFile);
			}

		} else {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}

			in.close();
			out.close();
		}
	}

	/**
	 * Gets the time difference in min.
	 *
	 * @author Cigniti
	 * @param startTime
	 *            - Start Time as a Date class object
	 * @param endTime
	 *            - End Time as a Date class object
	 * @return the time difference in min
	 */
	public long getTimeDifferenceInMin(Date startTime, Date endTime) {
		long ageInMillis = endTime.getTime() - startTime.getTime();
		return ageInMillis / (1000 * 60);
	}

	/**
	 * Gets the IP of selenium grid node.
	 *
	 * @author Cigniti
	 * @param remotewebdriver
	 *            the remotewebdriver
	 * @return the string
	 * @description: get IP of selenium grid 'node'
	 */
	public synchronized String  GetIPOfSeleniumGridNode(RemoteWebDriver remotewebdriver) {
		String hostFound = null;
		try {
			
			HttpCommandExecutor commandexecutor = (HttpCommandExecutor)(remotewebdriver).getCommandExecutor();
			
			String hostName = commandexecutor.getAddressOfRemoteServer().getHost();
			int port = commandexecutor.getAddressOfRemoteServer().getPort();
			HttpHost host = new HttpHost(hostName, port);
			@SuppressWarnings("deprecation")
			DefaultHttpClient client = new DefaultHttpClient();
			URL sessionURL = new URL("http://" + hostName + ":" + port + "/grid/api/testsession?session="
					+ ((RemoteWebDriver) remotewebdriver).getSessionId());            
			BasicHttpEntityEnclosingRequest r = new BasicHttpEntityEnclosingRequest("POST",
					sessionURL.toExternalForm());
			System.out.println(sessionURL.toExternalForm().toString());
			HttpResponse response = client.execute(host, r);
			if(response.toString().contains("403")) {
				hostFound = hostName;
				System.out.println("Proxy");
			}
			else {
				JSONObject object = extractObject(response);
				URL myURL = new URL(object.getString("proxyId"));
				if ((myURL.getHost() != null) && (myURL.getPort() != -1)) {
					hostFound = myURL.getHost();
				}
			}
			
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hostFound;
	}


	/**
	 * Extract object.
	 *
	 * @author Cigniti
	 * @param resp
	 *            the resp
	 * @return the JSON object
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws JSONException
	 *             the JSON exception
	 */
	private static JSONObject extractObject(HttpResponse resp) throws IOException, JSONException {
		InputStream contents = resp.getEntity().getContent();
		StringWriter writer = new StringWriter();
		IOUtils.copy(contents, writer, "UTF8");
		JSONObject objToReturn = new JSONObject(writer.toString());
		return objToReturn;
	}

	/**
	 * Javascript click.
	 *
	 * @author Cigniti
	 * @param webElement
	 *            the web element
	 * @param time
	 *            the time
	 * @return true, if successful
	 * @description: WebElement click
	 */
	public boolean javascriptClick(WebElement webElement, long time) {
		boolean state = false;
		try {
			JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
			js.executeScript("arguments[0].click();", webElement);
			Thread.sleep(time);
			state = true;
			return true;
		} catch (NullPointerException ex) {
			return false;
		} catch (Exception e) {
			try {
				if (ScrollToElementVisible(By.xpath(getElementXPath(webElement)))) {
					JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
					js.executeScript("$(arguments[0]).click();", webElement);
					Thread.sleep(time);
					state = true;
					return true;
				} else {
					return false;
				}
			} catch (Exception ex) {

			}
			if (!state) {
				try {
					JavascriptExecutor js = (JavascriptExecutor) (CommonVariables.CommonDriver.get());
					js.executeScript("$(arguments[0]).focusin();", webElement);
					Thread.sleep(time);
					state = true;
				}

				catch (Exception e1) {
					state = false;
				}
			}
		}
		return state;
	}

	/**
	 * Gets the alert text.
	 *
	 * @author Cigniti
	 * @return the alert text
	 * @description: get alert
	 */
	public String getAlertText() {
		try {
			Alert alert = CommonVariables.getDriver().switchTo().alert();
			return alert.getText();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Gets the IP of selenium grid node.
	 *
	 * @author Cigniti
	 * @param remotewebdriver
	 *            the remotewebdriver
	 * @return the IP of selenium grid node
	 */
	public String getIPOfSeleniumGridNode(RemoteWebDriver remotewebdriver) {
		String hostFound = null;
		try {
			HttpCommandExecutor commandexecutor = (HttpCommandExecutor) remotewebdriver.getCommandExecutor();
			String hostName = commandexecutor.getAddressOfRemoteServer().getHost();
			int port = commandexecutor.getAddressOfRemoteServer().getPort();
			HttpHost host = new HttpHost(hostName, port);
			@SuppressWarnings("deprecation")
			DefaultHttpClient client = new DefaultHttpClient();
			URL sessionURL = new URL("http://" + hostName + ":" + port + "/grid/api/testsession?session="
					+ remotewebdriver.getSessionId());
			BasicHttpEntityEnclosingRequest r = new BasicHttpEntityEnclosingRequest("POST",
					sessionURL.toExternalForm());
			HttpResponse response = client.execute(host, r);
			JSONObject object = extractObject(response);
			URL myURL = new URL(object.getString("proxyId"));
			if ((myURL.getHost() != null) && (myURL.getPort() != -1)) {
				hostFound = myURL.getHost();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hostFound;
	}

	/**
	 * Gets the text from clipboard.
	 *
	 * @return Clipboard text
	 * @throws UnsupportedFlavorException
	 *             the unsupported flavor exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getTextFromClipboard() throws UnsupportedFlavorException, IOException {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		return (String) clipboard.getData(DataFlavor.stringFlavor);
	}

	/**
	 * Kill popup.
	 *
	 * @author Cigniti
	 * @param textInURL
	 *            the text in URL
	 * @description: kill unwanted pop in web
	 */
	public void killPopup(String textInURL) {
		try {
			String windowHandle = CommonVariables.getDriver().getWindowHandle();
			for (String window : CommonVariables.getDriver().getWindowHandles()) {
				WebDriver temp = CommonVariables.getDriver().switchTo().window(window);
				if (temp.getCurrentUrl().toLowerCase().contains(textInURL)) {
					temp.close();
				}
			}
			CommonVariables.getDriver().switchTo().window(windowHandle);
		} catch (Exception e) {
		}
	}

	/**
	 * Switch toi frame.
	 *
	 * @author Cigniti
	 * @param frame_element
	 *            the frame element
	 * @return the web driver
	 * @description: switch to iframe
	 */
	public static WebDriver SwitchToiFrame(WebElement frame_element) {
		CommonVariables.getDriver().switchTo().frame(frame_element);
		CommonVariables.setDriver((EventFiringWebDriver) CommonVariables.getDriver());
		return CommonVariables.getDriver();
	}

	/**
	 * Switch back fromi frame to maincontent.
	 *
	 * @author Cigniti
	 * @return the web driver
	 * @description: switch back to default content from iframe
	 */
	public static WebDriver SwitchBackFromiFrameToMaincontent() {
		CommonVariables.getDriver().switchTo().defaultContent();
		CommonVariables.setDriver((EventFiringWebDriver) CommonVariables.getDriver());
		return CommonVariables.getDriver();
	}

	/**
	 * Gets the current time zone.
	 *
	 * @author Cigniti
	 * @return the current time zone
	 * @description: get current time zone
	 */
	public static String getCurrentTimeZone() {
		String timeZone = null;
		// get Calendar instance
		Calendar now = Calendar.getInstance();
		// get current TimeZone using getTimeZone method of Calendar class
		TimeZone tZone = now.getTimeZone();
		// display current TimeZone using getDisplayName() method of TimeZone
		// class
		timeZone = tZone.getDisplayName();
		return timeZone;
	}

	/**
	 * Robotic method.
	 *
	 * @author Cigniti
	 * @return the robot
	 * @throws AWTException
	 *             the AWT exception
	 * @description: Robot class should be use only when keyboard inputs are
	 *               required and all other Options like, ACtion Class, Webdriver
	 *               Send Keys.. are not working.
	 */
	public Robot roboticMethod() throws AWTException {
		Robot robot = new Robot();
		return robot;
	}

	/**
	 * Robotic tab click.
	 *
	 * @author Cigniti
	 * @param numberofClick
	 *            the numberof click
	 * @throws AWTException
	 *             the AWT exception
	 * @description: Maximum '6' times user can use tab click through robotium.
	 */
	public void roboticTabClick(int numberofClick) throws AWTException {
		switch (numberofClick) {
		case 1:
			roboticMethod().keyPress(KeyEvent.VK_TAB);			
			break;
		case 2:
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			break;
		case 3:
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			break;
		case 4:
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			break;
		case 5:
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			break;
		case 6:
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			roboticMethod().keyPress(KeyEvent.VK_TAB);
			break;
		default:
			System.out.println("Tab not clicked.");
			break;
		}
		
	}
	
	/**
	 * Robotic tab click release.
	 *
	 * @param numberofClick the numberof click
	 * @throws AWTException the AWT exception
	 */
	public void roboticTabClickRelease(int numberofClick) throws AWTException {
		switch (numberofClick) {
		case 1:
			roboticMethod().keyRelease(KeyEvent.VK_TAB);			
			break;
		case 2:
			roboticMethod().keyRelease(KeyEvent.VK_TAB);
			roboticMethod().keyRelease(KeyEvent.VK_TAB);
			break;
		case 3:
			roboticMethod().keyRelease(KeyEvent.VK_TAB);
			roboticMethod().keyRelease(KeyEvent.VK_TAB);
			roboticMethod().keyRelease(KeyEvent.VK_TAB);
			break;
		case 4:
			roboticMethod().keyRelease(KeyEvent.VK_TAB);
			roboticMethod().keyRelease(KeyEvent.VK_TAB);
			roboticMethod().keyRelease(KeyEvent.VK_TAB);
			roboticMethod().keyRelease(KeyEvent.VK_TAB);
			break;
		case 5:
			roboticMethod().keyRelease(KeyEvent.VK_TAB);
			roboticMethod().keyRelease(KeyEvent.VK_TAB);
			roboticMethod().keyRelease(KeyEvent.VK_TAB);
			roboticMethod().keyRelease(KeyEvent.VK_TAB);
			roboticMethod().keyRelease(KeyEvent.VK_TAB);
			break;
		case 6:
			roboticMethod().keyRelease(KeyEvent.VK_TAB);
			roboticMethod().keyRelease(KeyEvent.VK_TAB);
			roboticMethod().keyRelease(KeyEvent.VK_TAB);
			roboticMethod().keyRelease(KeyEvent.VK_TAB);
			roboticMethod().keyRelease(KeyEvent.VK_TAB);
			roboticMethod().keyRelease(KeyEvent.VK_TAB);
			break;
		default:
			System.out.println("Tab not released.");
			break;
		}
		
	}

	/**
	 * Action method.
	 *
	 * @author Cigniti
	 * @return the action
	 */
	public static Action actionMethod() {
		Action action = (Action) new Actions(CommonVariables.CommonDriver.get());
		return action;
	}

	/**
	 * Gets the current time.
	 *
	 * @return the string
	 */
	public String GetCurrentTime() {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a");
		String currenttime = df.format(Calendar.getInstance().getTime());
		return currenttime;
	}

	/**
	 * File separator.
	 *
	 * @author Cigniti
	 * @param filename
	 *            the filename
	 */
	public void fileSeparator(String filename) {
		try {
			String workingDirectory = System.getProperty("user.dir").replace("\\", "/");
			String absoluteFilePath = "";
			String osname = System.getProperty("os.name");
			String fileSeparator = File.separator;
			if (osname.contains("windows")) {
				fileSeparator = "/";
			} else {
				if (osname.contains("linux") || osname.contains("Unix") || osname.contains("Mac")) {
					fileSeparator = "/";
				}
			}
			absoluteFilePath = workingDirectory + fileSeparator + filename;
			System.out.println("Final filepath : " + absoluteFilePath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fluent wait.
	 *
	 * @author Cigniti
	 * @param waitTime
	 *            the wait time
	 * @param pollingCycle
	 *            the polling cycle
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void fluentWait(int waitTime, int pollingCycle) {
		try {
			FluentWait wait = new FluentWait(CommonVariables.getDriver());
			wait.withTimeout(waitTime, TimeUnit.SECONDS);
			wait.pollingEvery(pollingCycle, TimeUnit.SECONDS);
			wait.ignoring(NoSuchElementException.class);
		} catch (Exception e) {
			System.out.println("unable to perform an action for fluent wait due to execption - " + e.getMessage());
		}
	}

	/**
	 * Wrap the Selenium run time exception with more debug info.
	 *
	 * @param page
	 *            the page
	 * @param e
	 *            the e
	 * @return the string
	 */
	protected String makeSeleniumExceptionMessage(Page page, Throwable e) {

		String result = "SeleniumException found on page " + page.getClass();
		String fullMessage = e.getMessage();
		return result + "\n" + fullMessage.substring(0, fullMessage.indexOf("\n"));
	}

	/**
	 * Gets the files.
	 *
	 * @author Cigniti
	 * @param folderPath
	 *            the folder path
	 * @return the files
	 */
	public static File[] getFiles(String folderPath) {
		File arrayFiles[] = null;
		List<File> listFiles = new ArrayList<File>();
		traverseFolder(new File(folderPath), listFiles);
		// get the size of listFiles to initialize the size of array
		arrayFiles = new File[listFiles.size()];
		arrayFiles = listFiles.toArray(arrayFiles);

		return arrayFiles;
	}

	/**
	 * Traverse folder.
	 *
	 * @author Cigniti
	 * @param file
	 *            the file
	 * @param listFiles
	 *            the list files
	 */
	private static void traverseFolder(File file, List<File> listFiles) {
		// if current file is folder, look for files inside it.
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File aFile : files) {
				// recursive call
				traverseFolder(aFile, listFiles);
			}
		} else // add the file to list
		{
			listFiles.add(file);
		}
	}

	/**
	 * Robot mouse over.
	 * 
	 * @author Cigniti
	 * @param by
	 *            the by
	 * @throws Exception
	 *             the exception
	 */
	public void robotMouseOver(By by) throws Exception {
		try {
			Robot robot = new Robot();
			robot.delay(1500);
			Point point = CommonVariables.getDriver().findElement(by).getLocation();
			int xcord = point.getX();
			int ycord = point.getY();
			robot.mouseMove(xcord + 40, ycord + 120); // move mouse point to
														// specific location
			robot.delay(1500); // delay is to make code wait for mentioned
								// milliseconds before executing next step
			/*
			 * robot.mousePress(InputEvent.BUTTON1_DOWN_MASK); // press left click
			 * robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK); // release left click
			 * robot.delay(1500); robot.keyPress(KeyEvent.VK_DOWN); // press keyboard arrow
			 * key to select Save radio button Thread.sleep(2000);
			 * robot.keyPress(KeyEvent.VK_ENTER); Thread.sleep(3000);
			 */
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Action mouse over.
	 *
	 * @param element the element
	 * @throws Exception the exception
	 */
	public void actionMouseOver(WebElement element) throws Exception {
		try {
			Actions actionObj = new Actions(CommonVariables.getDriver());
			actionObj.moveToElement(element).perform();			
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Wait for text.
	 *
	 * @param by
	 *            the by
	 * @param locator
	 *            the locator
	 * @param secs
	 *            the secs
	 * @return true, if successful
	 * @throws Throwable
	 *             the throwable
	 */
	public boolean waitForText(By by, String locator, int secs) throws Throwable {
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(CommonVariables.getDriver(), 60);
			ScrollToElementVisible(by);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(by, locator));
			for (int i = 0; i < secs / 2; i++) {
				List<WebElement> elements = CommonVariables.getDriver().findElements(by);
				if (elements.size() > 0) {
					status = true;
					return status;
				} else {
					CommonVariables.getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
				}
			}
		} catch (Exception e) {

			return status;
		}
		return status;
	}

	/**
	 * Close all other windows.
	 * 
	 * @author Cigniti
	 * @param driver
	 *            the driver
	 * @param openWindowHandle
	 *            the open window handle
	 * @return true, if successful
	 */
	public boolean closeAllOtherWindows(WebDriver driver, String openWindowHandle) {
		Set<String> allWindowHandles = driver.getWindowHandles();
		for (String currentWindowHandle : allWindowHandles) {
			if (!currentWindowHandle.equals(openWindowHandle)) {
				driver.switchTo().window(currentWindowHandle);
				driver.close();
			}
		}
		driver.switchTo().window(openWindowHandle);
		if (driver.getWindowHandles().size() == 1)
			return true;
		else
			return false;
	}

	/**
	 * Select value from drop down for edge browser.
	 * @author Cigniti
	 * @param elementBy
	 *            the element by
	 * @param selectionValue
	 *            the selection value
	 */
	public void selectListValueEdge(By elementBy, String selectionValue) {
		try {
			WebElement webElement = CommonVariables.getDriver().findElement(elementBy);
			webElement.click();
			webElement.sendKeys(selectionValue);
			webElement.sendKeys(Keys.ENTER);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Tab outon element.
	 *
	 * @param elementBy the element by
	 */
	public void tabOutonElement(By elementBy) {
		try {
			WebElement webElement = CommonVariables.getDriver().findElement(elementBy);
			webElement.click();
			webElement.sendKeys(Keys.TAB);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Scroll to top.
	 *
	 * @author Cigniti
	 * @param driver
	 *            the driver
	 */
	public void ScrollToTop(WebDriver driver) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,-1000)", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Format month name.
	 *
	 * @param monthCharValue
	 *            the month char value
	 * @return the string
	 */
	public String FormatMonthName(String monthCharValue) {
		switch (monthCharValue.trim().toUpperCase()) {
		case "JAN":
			return "January";
		case "JANUARY":
			return "Jan";
		case "FEB":
			return "February";
		case "FEBRUARY":
			return "Feb";
		case "MAR":
			return "March";
		case "MARCH":
			return "Mar";
		case "APR":
			return "April";
		case "APRIL":
			return "Apr";
		case "MAY":
			return "May";
		case "JUN":
			return "June";
		case "JUNE":
			return "Jun";
		case "JULY":
		case "JUL":
			return "July";
		case "AUGUST":
			return "Aug";
		case "AUG":
			return "August";
		case "SEP":
			return "September";
		case "SEPTEMBER":
		case "OCT":
			return "October";
		case "OCTOBER":
			return "Oct";
		case "NOV":
			return "November";
		case "NOVEMBER":
			return "Nov";
		case "DECEMBER":
			return "Dec";
		case "DEC":
			return "December";
		default:
			extentLogs.info("Numeric value of the Month", "Incorrect value specified :" + monthCharValue);
			return "";
		}
	}

	/**
	 * Random number for range.
	 * @author Cigniti
	 * @param lowerNumber the lower number
	 * @param higherNumber the higher number
	 * @return the int
	 */
	public static int randomNumberForRange(int lowerNumber, int higherNumber) {
		Random r = new Random();
		int randomNumber = r.nextInt(higherNumber - lowerNumber) + lowerNumber;
		return randomNumber;
	}
	
	/**
	 * Sets the browser stack local.
	 *
	 * @author Cigniti
	 * Sets the browser stack local.
	 */
	public void setBrowserStackLocal() {
	try {
		bsLocal = new Local();
		HashMap<String, String> bsLocalArgs = new HashMap<String, String>();
		bsLocalArgs.put("key", ConfigManager.getProperties().getProperty("browserStackKey"));
		bsLocalArgs.put("forcelocal", "true");
		if(checkOS().contains("Windows")) {
			bsLocalArgs.put("proxy-host", "172.29.6.147");
			bsLocalArgs.put("proxy-port","8080");			
		}
		Log.info("starting Browser stack local");
		bsLocal.start(bsLocalArgs);
		Thread.sleep(20);
		if(bsLocal.isRunning()) {
			LOG.info("Browser stack local is running");
		}			
		} catch (Exception e) {
			if(check("BrowserStackLocal.exe")) {
				try {
					KillWindowProcess("BrowserStackLocal.exe");
					setBrowserStackLocal();
				} catch (Exception e1) {
					Log.info("BS not started");
					e1.printStackTrace();
				}
			}
		}		  
	}
	
	/**
	 * Sets the proxy.
	 *
	 * @author Cigniti
	 * Sets the proxy.
	 */
	public void setProxy() {
		System.getProperties().put("https.proxyHost", ConfigManager.getProperties().getProperty("proxyHost"));
		System.getProperties().put("https.proxyPort", ConfigManager.getProperties().getProperty("proxyPort"));
		System.getProperties().put("https.proxyUser", ConfigManager.getProperties().getProperty("proxyUser"));
		System.getProperties().put("https.proxyPassword", ConfigManager.getProperties().getProperty("proxyPassword"));
	}

	/**
	 * Sets the browser mob proxy.
	 *
	 * @return the proxy
	 */
	public Proxy setBrowserMobProxy() {
		  server = new BrowserMobProxyServer();
		  server.start();
		  int port = server.getPort();
		  Proxy proxy = ClientUtil.createSeleniumProxy(server);
		  return proxy;
		  //server.stop();
	}
	
	/**
	 * Delete all cookies.
	 */
	public void deleteAllCookies(){
		Set<Cookie> cookie = CommonVariables.CommonDriver.get().manage().getCookies();
		for(Cookie c:cookie){
			CommonVariables.CommonDriver.get().manage().deleteCookieNamed(c.getName());
		}
	}
	
	public static String encryptXOR(String message, String key){       
		try {
		        if (message==null || key==null ) return null;       
		        char[] keys=key.toCharArray();
		        char[] mesg=message.toCharArray();           
		        int ml=mesg.length;
		        int kl=keys.length;
		        char[] newmsg=new char[ml];           
		        for (int i=0; i<ml; i++){
		       newmsg[i]=(char)(mesg[i]^keys[i%kl]);
		        }
		        mesg=null;
		        keys=null;
		        return new String(new BASE64Encoder().encodeBuffer(new String(newmsg).getBytes()));
		} catch (Exception e) {
		        e.printStackTrace();
		        return null;
		    }
		}
	
	public static String decryptXOR(String message, String key){
	    try {
	      if (message==null || key==null ) return null;         
	      BASE64Decoder decoder = new BASE64Decoder();
	      char[] keys=key.toCharArray();
	      char[] mesg=new String(decoder.decodeBuffer(message)).toCharArray();
	      int ml=mesg.length;
	      int kl=keys.length;
	      char[] newmsg=new char[ml];
	      for (int i=0; i<ml; i++){
	        newmsg[i]=(char)(mesg[i]^keys[i%kl]);
	      }
	      mesg=null; keys=null;
	      return new String(newmsg);
	    }
	    catch ( Exception e ) {
	      return null;
	        } 
	      }
	
	public boolean selectByValue(By locator, String value, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			WebDriverWait wait = new WebDriverWait(CommonVariables.getDriver(), 60);
			ScrollToElementVisible(locator);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			Select s = new Select(CommonVariables.getDriver().findElement(locator));
			s.selectByValue(value);;
			flag = true;
		} catch (Exception e) {
			extentLogs.error("Select", " Option at index " + value + " is Not selected from the DropDown" + locatorName
					+ " due to exception - " + e.getLocalizedMessage());
			flag = false;
		} finally {
			if (flag == false) {
				extentLogs.fail("Select",
						" Option at index " + value + " is not Selected from the DropDown" + locatorName);
			} else if (flag == true) {
				extentLogs.pass("Select", " Option at index " + value + " is Selected from the DropDown" + locatorName);
			}
		}
		return flag;
	}
	
	
	
}
package com.vv.test.automation.web.accelerators;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.mortbay.log.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;


import com.google.common.base.Optional;
import com.relevantcodes.extentreports.ExtentTest;
import com.vv.test.automation.logs.CustomLogName;
import com.vv.test.automation.logs.CustomLogs;
import com.vv.test.automation.logs.DetailedLogs;
import com.vv.test.automation.logs.ExtentLogName;
import com.vv.test.automation.reports.ExtentHTML;
import com.vv.test.automation.reports.ExtentManager;
import com.vv.test.automation.utilities.CommonVariables;
import com.vv.test.automation.utilities.ConfigManager;

import cucumber.api.testng.AbstractTestNGCucumberTests;

public class TestEngineWeb extends AbstractTestNGCucumberTests {
	public ActionsLibrary objCommonFunc;
	protected DateFormat dateFormat;
	protected String methodName;
	private String Rerun = "";
	private String APIReportPath = "";
	private String FailedAPIReportPath = "";
	private static long threadCount = 1;
	protected long threadId;
	protected Boolean isRunningViaTestNgXML = false;
	public static String emailAddress;
	public static String[] to;
	private static final Logger LOG = LoggerFactory.getLogger(TestEngineWeb.class);
	protected static String timestamp = "";
	public Properties config_prop;
	private ExtentTest etest;
	public String log;
	private ExtentManager extentManager;
	@SuppressWarnings("rawtypes")
	public static Map sysInfo = new HashMap();
	private int itr_cnt = 1;
	private static String reportPath = "";

	protected String rootPath = System.getProperty("user.dir").replace("\\", "/") + "/logs/Results/";
	public static String extentReporth;

	public static synchronized ExtentTest getExtentReportTest() {
		return CommonVariables.extentTest.get();
	}

	public static synchronized void setExtentReportTest(ExtentTest etest) {
		CommonVariables.extentTest.set(etest);
	}

	/**
	 * @author cigniti
	 * @param context
	 * @throws IOException
	 */
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite(ITestContext context) throws IOException {
		try {
			// System.out.println(System.getenv("JENKINS_HOME"));
			if (System.getenv("JENKINS_HOME") != null && System.getenv("JENKINS_HOME").length() > 1) {
				String buildNumber = System.getenv("BUILD_NUMBER");
				String JobName = System.getenv("JOB_NAME");
				CommonVariables.Jenkins_BuildNumber.set(buildNumber);
				CommonVariables.Jenkins_JobName.set(JobName);
			}
			LOG.info("Executing the test suite on " + context.getSuite().getXmlSuite().getTests().size()
					+ " number of threads.");
			LOG.info("Executing the test suite on  number of threads : "
					+ context.getSuite().getXmlSuite().getThreadCount() + " number of threads.");
			timestamp = new SimpleDateFormat("yyyy_MM_dd_hh_mm_sss_a").format(new Date());

			Properties config_prop = new Properties();
			try {
				FileReader reader;
				reader = new FileReader("config.properties");
				config_prop.load(reader);
			} catch (IOException e) {
				LOG.info("Failed to fetch/read 'config.properties' file.");
			}

			// code to get TestNG Groups
			String threadtestNGGroupNames = getTestNGGroups(context).substring(1, getTestNGGroups(context).length())
					.replace(":", ",");
			System.out.println(threadtestNGGroupNames);
			// code to read testNg failed xml, if exist.
			File src = null;
			File dest = null;
			try {
				src = new File(System.getProperty("user.dir").replace("\\", "/") + "/test-output/testng-failed.xml");
				BufferedReader in = new BufferedReader(new FileReader(System.getProperty("user.dir").replace("\\", "/")
						+ "/logs/CapturedJSErrors/lastRunResultFolder.txt"));
				String destString;
				while ((destString = in.readLine()) != null) {
					dest = new File(destString + "/testng-failed.xml");
				}
				ActionsLibrary.staticCopyFile(src, dest);
				in.close();
			} catch (Exception e1) {
				try {
					String timestamp = new SimpleDateFormat("yyyy_MM_dd").format(new Date());
					ActionsLibrary.staticCopyFile(src, new File(System.getProperty("user.dir").replace("\\", "/")
							+ "/logs/Failed-testng_xmls/test-failed_" + timestamp + "_" + System.nanoTime() + ".xml"));
				} catch (Exception e) {
					e.getLocalizedMessage();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author cigniti
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	@AfterSuite(alwaysRun = true, groups = { "smoke", "regression" })
	public void afterSuite() throws Exception {

		// code to replace extent report with logos
		ExtentHTML.createExtentHTML();

		// code to generate report through command prompt
		try {
			String osname = System.getProperty("os.name");
			if (System.getenv("JENKINS_HOME") == null) {
				if (osname.toLowerCase().contains("windows") & CommonVariables.extentReportPath != null) {
					String command = "cmd /c start " + System.getProperty("user.dir") + "/LaunchReport.bat " + "\""
							+ CommonVariables.extentReportPath;
					Runtime rt = Runtime.getRuntime();
					@SuppressWarnings("unused")
					Process pr = rt.exec(command);
				} else if (osname.toLowerCase().contains("mac") & CommonVariables.extentReportPath != null) {
					String[] mac_command = { "open", "/Applications/Google Chrome.app",
							CommonVariables.extentReportPath };
					Runtime rt = Runtime.getRuntime();
					@SuppressWarnings("unused")
					Process pr = rt.exec(mac_command);
				} else {
					if (CommonVariables.extentReportPath != null) {
						String[] linux_command = { "google-chrome", "/usr/bin/X11/google-chrome",
								CommonVariables.extentReportPath };
						Runtime rt = Runtime.getRuntime();
						@SuppressWarnings("unused")
						Process pr = rt.exec(linux_command);
					}

				}
			}
			LOG.info("***** HTML report Successfully Generated ***** !!!!");
		} catch (IOException e) {
			LOG.info("Failed to execute 'Launch Report' : " + e.getStackTrace());
		}

		try {
			PrintWriter writer = new PrintWriter(System.getProperty("user.dir").replace("\\", "/")
					+ "/logs/CapturedJSErrors/lastRunResultFolder.txt", "UTF-8");
			if (CommonVariables.extentReportPath != null) {
				writer.println(CommonVariables.extentReportPath.split("M_")[0] + "M");
				writer.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// code to kill all command prompt instances
		if (System.getProperty("os.name").toLowerCase().contains("windows") && System.getenv("JENKINS_HOME") == null) {
			String command = "cmd /c start " + "taskkill /F /IM cmd.exe";
			Runtime rt = Runtime.getRuntime();
			try {
				@SuppressWarnings("unused")
				Process pr = rt.exec(command);
				LOG.info("All command prompt instances are closed successfully.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// code for extent reporting
		extentManager.closeReport();
	}

	/**
	 * @author cigniti
	 * @param context
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@BeforeTest(alwaysRun = true, groups = { "smoke", "regression" })
	public void beforeTest(ITestContext context) throws IOException {
		String hostname = InetAddress.getLocalHost().getHostName();
		String hostAddress = InetAddress.getLocalHost().getHostAddress();
		this.threadId = Thread.currentThread().getId();
		CommonVariables.MachineHostName.set(hostname);
		CommonVariables.MachineHostAddress.set(hostAddress);
		LOG.info("Executing the test on thread id : " + threadId);
		try {
			Thread.sleep(1500 * threadCount);
			threadCount = threadCount + 1;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		CommonVariables.ResultSheet.set(new HashMap<String, String>());
		CommonVariables.TestMethodDescriptions.set(new HashMap<String, String>());
		// code to set detailed logs
		CommonVariables.DetailedLogs.set(new DetailedLogs());
		// code to set config properties
		ConfigManager.setProperties();
		// code to set instance variables for detail level logging.
		CommonVariables.CommonDriver.set(null);
		CommonVariables.TestCaseName.set("");
		CommonVariables.TotalTCCount.set(new Integer(0));
		CommonVariables.PassTCCount.set(new Integer(0));
		CommonVariables.FailTCCount.set(new Integer(0));
		CommonVariables.SkipTCCount.set(new Integer(0));
		CommonVariables.TestCasessHighLevelLog.set(new ArrayList<String>());
		CommonVariables.ScenariosHighLevelLog.set(new ArrayList<String>());
		CommonVariables.LastMethodName.set("");
		CommonVariables.TestClassResult.set("PASS");

		// code to get browser name from jenkins like, chrome, firefox etc
		final String browser;
		if (System.getenv("Browser") != null && (!System.getenv("Browser").isEmpty())) {
			browser = System.getenv("Browser");
		} else if (System.getProperty("Browser") != null && (!System.getProperty("Browser").isEmpty())) {
			browser = System.getProperty("Browser");
		} else {
			browser = null;
		}

		// code to get environment from jenkins like stag, qa, dev etc.
		final String Environment;
		if (System.getenv("Environment") != null && (!System.getenv("Environment").isEmpty())) {
			Environment = System.getenv("Environment");
		} else if (System.getProperty("Environment") != null && (!System.getProperty("Environment").isEmpty())) {
			Environment = System.getProperty("Environment");
		} else {
			Environment = null;
		}

		// code to get user region from jenkins like US, India etc.
		final String region;
		if (System.getenv("userRegion") != null && (!System.getenv("userRegion").isEmpty())) {
			region = System.getenv("userRegion");
		} else if (System.getProperty("userRegion") != null && (!System.getProperty("userRegion").isEmpty())) {
			region = System.getProperty("userRegion");
		} else {
			region = null;
		}
		// code to get boolean status of failed.xml i.e. ReRUN=true or false
		try {
			Rerun = System.getenv("RERUN").toString().toLowerCase();
		} catch (Exception e1) {
			e1.getMessage();
		}
		// code to set and get the testNG parameter values
		CommonVariables.browser.set(browser);
		try {
			if (context.getCurrentXmlTest().getTestParameters().size() > 0) {
				isRunningViaTestNgXML = true;
			}
		} catch (Exception ex) {
			ex.getMessage();
		}
		CommonVariables.ExecutionDate.set(GetCurrentTime().split(" ")[0]);
		CommonVariables.ExecutionStartTime.set(GetCurrentTime());
		try {
			if (context.getCurrentXmlTest().getTestParameters().size() > 0) {
				if (context.getCurrentXmlTest().getTestParameters().containsKey("seleniumGrid")) {
					CommonVariables.IsGridExecution
							.set(Boolean.parseBoolean(context.getCurrentXmlTest().getParameter("seleniumGrid").trim()));
				} else {
					CommonVariables.IsGridExecution
							.set(Boolean.parseBoolean(context.getCurrentXmlTest().getParameter("seleniumGrid").trim()));
				}
			} else {
				CommonVariables.IsGridExecution
						.set(Boolean.parseBoolean(ConfigManager.getProperties().getProperty("seleniumGrid").trim()));
			}
		} catch (Exception e) {
			CommonVariables.IsGridExecution.set(false);
		}
		try {
			if (context.getCurrentXmlTest().getTestParameters().size() > 0) {
				if (context.getCurrentXmlTest().getTestParameters().containsKey("localExecution")) {
					CommonVariables.IsLocalExecution.set(
							Boolean.parseBoolean(context.getCurrentXmlTest().getParameter("localExecution").trim()));
				} else {
					CommonVariables.IsLocalExecution.set(
							Boolean.parseBoolean(context.getCurrentXmlTest().getParameter("localExecution").trim()));
				}
			} else {
				CommonVariables.IsLocalExecution
						.set(Boolean.parseBoolean(ConfigManager.getProperties().getProperty("localExecution").trim()));
			}
		} catch (Exception e) {
			CommonVariables.IsLocalExecution.set(false);
		}
		try {
			if (context.getCurrentXmlTest().getTestParameters().size() > 0) {
				if (context.getCurrentXmlTest().getTestParameters().containsKey("cloudExecution")) {
					CommonVariables.IsCloudExecution.set(
							Boolean.parseBoolean(context.getCurrentXmlTest().getParameter("cloudExecution").trim()));
				} else {
					CommonVariables.IsCloudExecution.set(
							Boolean.parseBoolean(context.getCurrentXmlTest().getParameter("cloudExecution").trim()));
				}
			} else {
				CommonVariables.IsCloudExecution
						.set(Boolean.parseBoolean(ConfigManager.getProperties().getProperty("cloudExecution").trim()));
			}
		} catch (Exception e) {
			CommonVariables.IsCloudExecution.set(false);
		}
		Log.info("Running on Grid: " + CommonVariables.IsGridExecution.get());
		Log.info("Running on local: " + CommonVariables.IsLocalExecution.get());
		Log.info("Running on cloud: " + CommonVariables.IsCloudExecution.get());

		String DeviceType = "Unknown Device";

		try {
			String userRegion = null;
			try {
				if (Environment != null && (!Environment.isEmpty())) {
					CommonVariables.Environment.set(Environment);
				} else if (context.getCurrentXmlTest().getParameter("environment") != null
						&& (!context.getCurrentXmlTest().getParameter("environment").isEmpty())) {
					CommonVariables.Environment.set(context.getCurrentXmlTest().getParameter("environment"));
				} else {
					CommonVariables.Environment.set(ConfigManager.getProperties().getProperty("environment"));
				}
			} catch (Exception e) {
				LOG.info("Getting exception while getting environment type:" + e.getMessage());
				CommonVariables.Environment.set(ConfigManager.getProperties().getProperty("environment"));
			}
			// System.out.println(context.getCurrentXmlTest().getIncludedGroups().toString());
			LOG.info("Running on Environment : " + CommonVariables.Environment.get());
			try {
				if (region != null && (!region.isEmpty())) {
					CommonVariables.userRegion.set(region);
				} else if (context.getCurrentXmlTest().getParameter("userRegion") != null
						&& (!context.getCurrentXmlTest().getParameter("userRegion").isEmpty())) {
					CommonVariables.userRegion.set(context.getCurrentXmlTest().getParameter("userRegion"));
				} else {
					CommonVariables.userRegion.set(ConfigManager.getProperties().getProperty("userRegion"));
				}
			} catch (Exception e) {
				LOG.info("Getting exception while getting User Region:" + e.getMessage());
				CommonVariables.userRegion.set(ConfigManager.getProperties().getProperty("userRegion"));
			}
			LOG.info("Running for Region : " + CommonVariables.userRegion.get());

			try {
				if (browser != null && (!browser.isEmpty()) && (browser.contains("-"))) {
					ConfigManager.getProperties().setProperty("deviceType", browser.split("-")[1]);
					ConfigManager.getProperties().setProperty("platform", browser.split("-")[0]);
					CommonVariables.DeviceName.set(browser.split("-")[1]);
					CommonVariables.PlatformName.set(browser.split("-")[0]);
				} else if (context.getCurrentXmlTest().getParameter("platform") != null
						&& context.getCurrentXmlTest().getParameter("browser") != null
						&& context.getCurrentXmlTest().getParameter("browser_version") != null
						&& context.getCurrentXmlTest().getParameter("os") != null
						&& context.getCurrentXmlTest().getParameter("os_version") != null
						&& (!context.getCurrentXmlTest().getParameter("platform").isEmpty())
						&& (!context.getCurrentXmlTest().getParameter("browser_version").isEmpty())
						&& (!context.getCurrentXmlTest().getParameter("browser").isEmpty())
						&& (!context.getCurrentXmlTest().getParameter("os").isEmpty())
						&& (!context.getCurrentXmlTest().getParameter("os_version").isEmpty())) {
					CommonVariables.DeviceName.set(context.getCurrentXmlTest().getParameter("browser"));
					CommonVariables.Version.set(context.getCurrentXmlTest().getParameter("browser_version"));
					CommonVariables.PlatformName.set(context.getCurrentXmlTest().getParameter("platform"));
					CommonVariables.OS.set(context.getCurrentXmlTest().getParameter("os"));
					CommonVariables.OS_VERSION.set(context.getCurrentXmlTest().getParameter("os_version"));
				} else {
					CommonVariables.DeviceName.set(ConfigManager.getProperties().getProperty("deviceType"));
					CommonVariables.Version.set(ConfigManager.getProperties().getProperty("browser_version"));
					CommonVariables.PlatformName.set(ConfigManager.getProperties().getProperty("platform"));
					CommonVariables.OS.set(ConfigManager.getProperties().getProperty("os"));
					CommonVariables.OS_VERSION.set(ConfigManager.getProperties().getProperty("os_version"));
				}
			} catch (Exception e) {
				LOG.info("Getting exception while setting platform and browser values: " + e.getMessage());
				CommonVariables.DeviceName.set(ConfigManager.getProperties().getProperty("deviceType"));
				CommonVariables.PlatformName.set(ConfigManager.getProperties().getProperty("platform"));
			}

			if (context.getCurrentXmlTest().getIncludedGroups().toString() != null) {
				CommonVariables.testNGGroupNames.set(context.getCurrentXmlTest().getIncludedGroups().toString());
				System.out.println(CommonVariables.testNGGroupNames.get());
			}

			DeviceType = CommonVariables.DeviceName.get();
			LOG.info("Running on Device Type : " + DeviceType);
			LOG.info("Running on Platform : " + CommonVariables.PlatformName.get());
			if (Environment != null && browser != null && CommonVariables.IsGridExecution.get() == false) {
				ConfigManager.getProperties().setProperty("deviceType", browser.split("-")[1]);
				ConfigManager.getProperties().setProperty("platform", browser.split("-")[0]);
				ConfigManager.getProperties().setProperty("environment", Environment);
				String suppressCredential = ConfigManager.getProperties().getProperty("supressCredential") + "!@";
				if (CommonVariables.DeviceName.get().toLowerCase().contains("ie")
						|| CommonVariables.DeviceName.get().toLowerCase().contains("explorer")) {
					suppressCredential = "http://";
				}
				if (Environment.equalsIgnoreCase("Dev")) {
					String dev_url = ConfigManager.getProperties().getProperty("dev_url");
					ConfigManager.getProperties().setProperty("url", dev_url);
					ConfigManager.getProperties().setProperty("urlSuppressCredential", suppressCredential + dev_url);
				} else if (Environment.equalsIgnoreCase("QA")) {
					String qa_url = ConfigManager.getProperties().getProperty("qa_url");
					ConfigManager.getProperties().setProperty("url", qa_url);
					ConfigManager.getProperties().setProperty("urlSuppressCredential", suppressCredential + qa_url);
				} else if (Environment.equalsIgnoreCase("UAT")) {
					String uat_url = ConfigManager.getProperties().getProperty("uat_url");
					ConfigManager.getProperties().setProperty("url", uat_url);
					ConfigManager.getProperties().setProperty("urlSuppressCredential", suppressCredential + uat_url);
				} else if (Environment.equalsIgnoreCase("STAG")) {
					String stag_url = ConfigManager.getProperties().getProperty("stag_url");
					ConfigManager.getProperties().setProperty("url", stag_url);
					ConfigManager.getProperties().setProperty("urlSuppressCredential", suppressCredential + stag_url);
				} else if (Environment.equalsIgnoreCase("PROD")) {
					String prod_url = ConfigManager.getProperties().getProperty("prod_url");
					ConfigManager.getProperties().setProperty("url",
							ConfigManager.getProperties().getProperty("prod_url"));
					ConfigManager.getProperties().setProperty("urlSuppressCredential", prod_url);
					ConfigManager.getProperties().setProperty("urlSuppressCredential", "");
				} else if (Environment.equalsIgnoreCase("PREPROD")) {
					String preprod_url = ConfigManager.getProperties().getProperty("preprod_url");
					ConfigManager.getProperties().setProperty("url",
							ConfigManager.getProperties().getProperty("preprod_url"));
					ConfigManager.getProperties().setProperty("urlSuppressCredential", preprod_url);
				} else {
				}
			}
			if (context.getCurrentXmlTest().getParameter("appiumPort") == null) {
				CommonVariables.appiumPort.set(ConfigManager.getProperties().getProperty("appiumPort"));
			} else {
				CommonVariables.appiumPort.set(context.getCurrentXmlTest().getParameter("appiumPort"));
			}
			if (context.getCurrentXmlTest().getParameter("machineIP") == null) {
				CommonVariables.machineIP.set(ConfigManager.getProperties().getProperty("machineIP"));
			} else {
				CommonVariables.machineIP.set(context.getCurrentXmlTest().getParameter("machineIP"));
			}
		} catch (Exception e) {
			LOG.info("Getting exception in Before Test block." + e.getMessage());
			e.printStackTrace();
		}

		// code for extent reporting
		sysInfo.put("Machine IP Address", CommonVariables.MachineHostAddress.get());
		sysInfo.put("Environment", CommonVariables.Environment.get());
		sysInfo.put("Platform", CommonVariables.PlatformName.get());
		sysInfo.put("Region", CommonVariables.userRegion.get());
		sysInfo.put("Time Zone", ActionsLibrary.getCurrentTimeZone());
		sysInfo.put("Browser", CommonVariables.DeviceName.get());
		sysInfo.put("Execution Date and Time", CommonVariables.ExecutionStartTime.get());

		dateFormat = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
		Calendar.getInstance();
		// code to set report path
		String Report_Root_Path = rootPath + timestamp;
		reportPath = Report_Root_Path;
		CommonVariables.DetailedLogs.get().CreateFolder(Report_Root_Path);
		Report_Root_Path = rootPath + timestamp + "/" + DeviceType + "_" + Thread.currentThread().getId();
		CommonVariables.DetailedLogs.get().CreateFolder(Report_Root_Path);		
		CommonVariables.RootResultFolderPath.set(Report_Root_Path);
		
		CommonVariables.CurrentGlobalLog
				.set(CommonVariables.DetailedLogs.get().StartLogs("Global_Log", Report_Root_Path));
		CommonVariables.CurrentGlobalLog.get().info("***** Staring the Suite *****");

		File highlevellogfile = new File(Report_Root_Path + "/" + "HighLevelLog" + ".log");
		FileWriter highlevellogfw = new FileWriter(highlevellogfile.getAbsoluteFile());
		CommonVariables.HighLevelLog.set(new BufferedWriter(highlevellogfw));

		// code to generate intermediate results during automation execution
		try {
			createIntermediateTestResult("/" + DeviceType + "_" + Thread.currentThread().getId()
					+ "/intermediateResults-" + Thread.currentThread().getId(), ",");
		} catch (IOException e) {
			LOG.info("ERROR---> COULD NOT CREATE INTERMEDIATE RESULT FILE");
			e.printStackTrace();
		}

	}

	/**
	 * @author cigniti
	 * @throws IOException
	 */
	@AfterTest(alwaysRun = true, groups = { "smoke", "regression" })
	public void afterTest() throws IOException {
		if (CommonVariables.CommonDriver.get() != null) {
			try {
				CommonVariables.CommonDriver.get().close();
				CommonVariables.CommonDriver.get().quit();
			} catch (Exception e) {

			}
		}
		CommonVariables.ExecutionEndTime.set(GetCurrentTime());

		CommonVariables.CurrentGlobalLog.get().info("Stopping the Suite");
		try {
			CommonVariables.HighLevelLog.get().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		CommonVariables.ExecutionEndTime.set(GetCurrentTime());
		APIReportPath = CommonVariables.RootResultFolderPath.get() + "/../APINetworkTraffic.csv";
		FailedAPIReportPath = CommonVariables.RootResultFolderPath.get() + "/../FailedAPICalls.csv";
	}

	/**
	 * @author cigniti
	 * @throws IOException
	 */
	@BeforeClass(inheritGroups = true, alwaysRun = true, groups = { "smoke", "regression" })
	public void beforeClass() throws IOException {
		try {
			// code to set properties file
			ConfigManager.UpdateProperties();
			// code to set results
			CommonVariables.TestClassResult.set("PASS");
			String completeclassName = this.getClass().getName();
			LOG.info("Running the Test Class: " + completeclassName);
			String[] sArr = completeclassName.split("\\.");
			int last_array_item_index = sArr.length - 1;
			String className = sArr[last_array_item_index];
			String classLogName = className + "_" + Thread.currentThread().getId();
			CommonVariables.TestClassName.set(className);

			String Scenario_Report_Folder = CommonVariables.RootResultFolderPath.get() + "/"
					+ CommonVariables.TestClassName.get();
			CommonVariables.DetailedLogs.get().CreateFolder(Scenario_Report_Folder);
			CommonVariables.ScenarioResultFolderPath.set(Scenario_Report_Folder);

			String TestCases_Report_Folder = CommonVariables.ScenarioResultFolderPath.get() + "/" + "TestCases";
			CommonVariables.DetailedLogs.get().CreateFolder(TestCases_Report_Folder);
			String testCases_ScreenShot_Folder = CommonVariables.ScenarioResultFolderPath.get() + "/Screenshots";

			CommonVariables.DetailedLogs.get().CreateFolder(testCases_ScreenShot_Folder);
			CommonVariables.DetailedLogs.get().CreateFolder(Scenario_Report_Folder);
			// set screenshot path
			String ScreenShotPath = testCases_ScreenShot_Folder + "/" + CommonVariables.TestClassName.get() + "_"
					+ new SimpleDateFormat("yyyy_MM_dd_hh_mm_sss_a").format(new Date()) + ".jpg";
			CommonVariables.screenShotPath.set(ScreenShotPath);
			System.out.println("ScreenShotPath: " + ScreenShotPath);
			CommonVariables.TestClassLog
					.set(CommonVariables.DetailedLogs.get().StartLogs(classLogName, Scenario_Report_Folder));
			// set object of Action Library Class
			CommonVariables.setActionLib();
			objCommonFunc = CommonVariables.getActionLib();
			try {
				if (!ConfigManager.ArePropertiesSet.get()) {
					ConfigManager.setProperties();
				}
				ConfigManager.UpdateProperties();
			} catch (NullPointerException e) {
				LOG.info(
						"Failed to load and update ConfigManager.getProperties() in @BeforeClass method under BaseTestCase class.");
			}
			CustomLogs.addToLog(CustomLogName.CurrentTestCaseLog, "info", "hello");
			CustomLogs.addToLog(CustomLogName.CurrentTestClassLog, "info", "Staring the class");
			CommonVariables.TestClassLog.get().debug("In Before Class method inside TestEngineWeb.");
			CommonVariables.TestCaseResult.set("");
			CommonVariables.CommonDriver.set(null);
		} catch (Exception e) {
			CommonVariables.CommonDriver.set(null);
			LOG.info("Getting exception in @BeforeClass block of TestEngineWeb class. Exception info: \n"
					+ e.getMessage());
		}
		// code to set driver instance and open an application before every test
		// class
		if (this.getClass().getName().contains("Api")) {
			LOG.info("ApiTests Running");

		} else {
			if (CommonVariables.CommonDriver.get() == null) {
				objCommonFunc.locationServiceEnabled = false;
				try {
					WebDriver caseDriver = objCommonFunc.instantiateDriver((CommonVariables.DeviceName.get()));
					objCommonFunc.setDriver(new EventFiringWebDriver(CommonVariables.getDriver()));
					System.out.println("objCommonFunc driver: " + CommonVariables.getActionLib());
					if (CommonVariables.CommonDriver.get() != null) {

					} else {
						CommonVariables.setDriver(CommonVariables.getDriver());
						System.out.println("After instantiate since coomon driver is null setting common driver: "
								+ CommonVariables.getDriver());
					}

					objCommonFunc.OpenApplication(ConfigManager.getProperties().getProperty("url"));
				} catch (Exception ex) {
					LOG.info("Browser timeout Exception");
				}

			}
		}

	}

	/**
	 * @author cigniti
	 */
	@AfterClass(alwaysRun = true, groups = { "smoke", "regression" })
	public void afterClass() {
		try {
			CustomLogs.addToLog(CustomLogName.CurrentTestClassLog, "info", "Stopping the class");
			if (CommonVariables.TestClassResult.get().equalsIgnoreCase("PASS")) {
				CommonVariables.ScenariosHighLevelLog.get().add(CommonVariables.TestClassName.get() + ": PASS");
			} else {
				CommonVariables.ScenariosHighLevelLog.get().add(CommonVariables.TestClassName.get() + ": FAIL");
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
			if (CommonVariables.getDriver() == null || CommonVariables.getDriver().getWindowHandle() == null) {
			} else {
				CommonVariables.getActionLib().tearDown();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info(CommonVariables.TestCasessHighLevelLog.get().toString());
		CommonVariables.TestCasessHighLevelLog.get().clear();
	}

	/**
	 * @author cigniti
	 * @param method
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	@BeforeMethod(alwaysRun = true, groups = { "smoke", "regression" })
	public void beforeMethod(Method method) throws IOException {
		this.methodName = method.getName();
		CommonVariables.TestMethodName.set(this.methodName);
		String testName = method.getName();
		String testClassName = CommonVariables.TestClassName.get();
		String testcaseLogName = testClassName + "-" + testName + "_" + Thread.currentThread().getId();
		// code to generate logs before each test method
		if (CommonVariables.TestCaseName.get().equals(testName)) {
			if (CommonVariables.TestCaseLog.get() != null) {
				CommonVariables.TestCase_Data_Iterator.set(CommonVariables.TestCase_Data_Iterator.get() + 1);
				CommonVariables.TestCaseLog.get().info("***** Dataprovider Iteration No: "
						+ CommonVariables.TestCase_Data_Iterator.get() + " : *****");
			} else {
				CommonVariables.DataProviderIterator.set(1);
				CommonVariables.TestCaseName.set(testName);
				CommonVariables.TestCase_Data_Iterator.set(CommonVariables.TestCase_Data_Iterator.get() + 1);
				CommonVariables.TestCaseLog.set(CommonVariables.DetailedLogs.get().StartLogs(testcaseLogName,
						CommonVariables.ScenarioResultFolderPath.get() + "/" + "TestCases"));
				CommonVariables.TestCaseLog.get().info("***** Dataprovider Iteration No: "
						+ CommonVariables.TestCase_Data_Iterator.get() + " : *****");
			}
		} else {
			CommonVariables.TestCaseName.set(testName);
			CommonVariables.TestCase_Data_Iterator.set(1);
			CommonVariables.TestCaseLog.set(CommonVariables.DetailedLogs.get().StartLogs(testcaseLogName,
					CommonVariables.ScenarioResultFolderPath.get() + "/" + "TestCases"));
			CommonVariables.TestCaseLog.get()
					.info("Testcase executed on the Node IP: " + CommonVariables.machineIP.get());
			CommonVariables.TestCaseLog.get().info(
					"***** Dataprovider Iteration No: " + CommonVariables.TestCase_Data_Iterator.get() + " : *****");
		}
		CommonVariables.TCStartTime.set(GetCurrentTime());

		// code to get the Method Description
		String MethodDescription = "";
		java.lang.annotation.Annotation[] annotations = method.getAnnotations();
		for (java.lang.annotation.Annotation annotation : annotations) {
			if (annotation instanceof org.testng.annotations.Test) {
				org.testng.annotations.Test myAnnotation = (org.testng.annotations.Test) annotation;
				MethodDescription = myAnnotation.description().toString().replace(",", ";");
				LOG.info("Method Description : " + MethodDescription);
			}
		}
		// code to set driver instance and open application before each test
		// method
		if (CommonVariables.CommonDriver.get() == null) {
			objCommonFunc.locationServiceEnabled = false;
			if (testClassName.contains("Api")) {
				LOG.info("API tests running for feature : " + testClassName);
			} else {
				try {
					WebDriver caseDriver = objCommonFunc.instantiateDriver(CommonVariables.DeviceName.get());
					objCommonFunc.setDriver((EventFiringWebDriver) caseDriver);
					CommonVariables.setDriver(caseDriver);
					objCommonFunc.OpenApplication(ConfigManager.getProperties().getProperty("url"));
				} catch (Exception ex) {
					LOG.info("Browser timeout Exception");
				}

			}

		}
		// code for extent reporting
		extentManager = new ExtentManager();
		// extentManager.getTest(method.getName(), "Description : " +
		// MethodDescription);
		try {
			etest = extentManager.getTest(method.getName(), "Description : " + MethodDescription);
			etest.assignCategory("Groups : " + CommonVariables.testNGGroupNames.get());
			etest.assignAuthor("Developer : Cigniti Automation Team");
			etest.log(ExtentLogName.INFO, "Info", "Executing Test Case : ***** " + this.methodName + " *****");
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
		}
		CommonVariables.extentTest.set(etest);
		this.extentReporth = CommonVariables.extentReportPath;// changed

	}

	/**
	 * @author cigniti
	 * @param method
	 * @param result
	 * @param context
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	@AfterMethod(alwaysRun = true, groups = { "smoke", "regression" })
	public void afterMethod(Method method, ITestResult result) throws IOException {

		String MethodDescription = "";
		java.lang.annotation.Annotation[] annotations = method.getAnnotations();
		for (java.lang.annotation.Annotation annotation : annotations) {
			if (annotation instanceof org.testng.annotations.Test) {
				org.testng.annotations.Test myAnnotation = (org.testng.annotations.Test) annotation;
				MethodDescription = myAnnotation.description().toString().replace(",", ";");
				LOG.info("********* Method Description : " + MethodDescription + " *********");
			}
		}
		String testName = method.getName();
		Integer testResult = result.getStatus();

		// code for update intermediate results
		updateIntermediateTestResult("/" + CommonVariables.DeviceName.get() + "_" + Thread.currentThread().getId()
				+ "/intermediateResults-" + Thread.currentThread().getId(), ",", testName, testResult);

		CommonVariables.TestCaseName.set(testName);

		// code to get the list of data providers iteration.
		String DataParameters = "";
		try {
			String[] TestCasePArametersArray = (String[]) result.getParameters()[0];
			DataParameters = Arrays.toString(TestCasePArametersArray);
		} catch (java.lang.ClassCastException e) {
			try {
				Object[] arrDataObj = (Object[]) result.getParameters()[0];
				for (Object obj : arrDataObj) {
					DataParameters = DataParameters + obj.toString() + "||";
				}
			} catch (java.lang.ClassCastException e1) {
				DataParameters = Arrays.toString(result.getParameters());
			}
		} catch (Exception e) {
			DataParameters = Arrays.toString(result.getParameters());
		}
		if (DataParameters.equals("[]"))
			DataParameters = "";
		CommonVariables.TotalTCCount.set(CommonVariables.TotalTCCount.get() + 1);
		CommonVariables.TCEndTime.set(GetCurrentTime());
		String completeclassName = this.getClass().getName();
		String testcasename = CommonVariables.TestCaseName.get();
		String CompleteTCName = completeclassName + "." + testcasename;
		String testCases_ScreenShot_Folder = CommonVariables.ScenarioResultFolderPath.get() + "/" + "ScreenShots";

		// code to get the test method execution status
		String TestCaseResult = "";
		if (result.getStatus() == ITestResult.FAILURE) {
			TestCaseResult = "FAIL";
			CommonVariables.TestClassResult.set("FAIL");
			CommonVariables.FailTCCount.set(CommonVariables.FailTCCount.get() + 1);
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			TestCaseResult = "PASS";
			CommonVariables.TestClassResult.set("PASS");
			CommonVariables.PassTCCount.set(CommonVariables.PassTCCount.get() + 1);
		} else if (result.getStatus() == ITestResult.SKIP) {
			TestCaseResult = "SKIP";
			CommonVariables.TestClassResult.set("SKIP");
			CommonVariables.SkipTCCount.set(CommonVariables.SkipTCCount.get() + 1);
		} else {
			TestCaseResult = "UNKNOWN";
		}
		String TestDescriptionKey = CommonVariables.TestClassName.get() + "-" + CommonVariables.TestCaseName.get();
		if (CommonVariables.LastMethodName.get().equals(testName)) {
			itr_cnt++;
		} else {
			itr_cnt = 1;
		}
		String NewCompleteTCName = CompleteTCName + ">Itr" + itr_cnt;

		// code for extent reporting
		ExtentManager.closeTest(testName);
	}

	public Optional<Long> timeout(Integer time) {
		return Optional.of(Long.parseLong(String.valueOf(time)));
	}

	public String GetCurrentTime() {
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a");
		String currenttime = df.format(Calendar.getInstance().getTime());
		return currenttime;
	}

	/**
	 * @author cigniti
	 * @param fileName
	 * @param separator
	 * @throws IOException
	 */
	public void createIntermediateTestResult(String fileName, String separator) throws IOException {
		String filePath = reportPath + fileName + ".csv";
		FileWriter fw = new FileWriter(filePath);
		PrintWriter pw = new PrintWriter(fw);
		pw.print("MethodName");
		pw.print(separator);
		pw.print("TestResult");
		pw.flush();
		pw.close();
		fw.close();
	}

	/**
	 * @author cigniti
	 * @param fileName
	 * @param separator
	 * @param methodName
	 * @param testResult
	 */
	public void updateIntermediateTestResult(String fileName, String separator, String methodName, Integer testResult) {
		try {
			String filePath = reportPath + fileName + ".csv";
			FileWriter fw = new FileWriter(filePath, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println();
			pw.append(methodName);
			pw.append(separator);
			if (testResult == 1) {
				pw.append("PASS");
			} else if (testResult == 2) {
				pw.append("FAIL");
			} else if (testResult == 3) {
				pw.append("SKIP");
			} else if (testResult == 4) {
				pw.append("SUCCESS_PERCENTAGE_FAILURE");
			} else if (testResult == 16) {
				pw.append("STARTED");
			} else {
				pw.append("UNKNOWN");
			}
			pw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
		} catch (Exception e) {
		}
	}

	/**
	 * @author cigniti
	 * @param context
	 * @return
	 */
	public String getTestNGGroups(ITestContext context) {
		String sArrrGroups[] = context.getIncludedGroups();
		String groupName = "";
		int groupsCount = sArrrGroups.length;
		try {
			for (int i = 0; i < groupsCount; i++) {
				String sArrtemp[] = sArrrGroups[i].toString().split("\\.");
				for (int j = 0; j < sArrtemp.length; j++) {
					String name = sArrtemp[j].toString().trim().toLowerCase();
					if (name.equals("smoke")) {
						if (!groupName.contains("Smoke"))
							groupName = groupName + ":" + "Smoke";
					} else if (name.equals("regression")) {
						if (!groupName.contains("Regression"))
							groupName = groupName + ":" + "Regression";
					} else if (name.equals("mobile")) {
						if (!groupName.contains("Mobile"))
							groupName = groupName + ":" + "Mobile";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		groupName = ActionsLibrary.ChangeStringFirstCharToCapital(groupName);
		return groupName;
	}

	/**
	 * @author cigniti readTextFile
	 * @param filePath
	 */
	public List<String> readTextFile(String filePath) {
		List<String> list = new ArrayList<String>();
		String line = null;
		try {
			FileReader reader = new FileReader(filePath);
			BufferedReader bufferedReader = new BufferedReader(reader);

			while ((line = bufferedReader.readLine()) != null) {
				list.add(line);
			}
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @author cigniti
	 * @param location
	 */
	public void CreateFolder(String location) {
		new File(location).mkdir();
	}
}
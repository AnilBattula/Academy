package com.vv.test.automation.reports;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.vv.test.automation.utilities.CommonVariables;
import com.vv.test.automation.web.accelerators.TestEngineWeb;
public class ExtentManager extends TestEngineWeb {

	public static ExtentReports reporter;
	public static Map<Long, String> threadToExtentTestMap = new HashMap<Long, String>();
	public static Map<String, ExtentTest> nameToTestMap = new HashMap<String, ExtentTest>();
	private static String timestamp = new SimpleDateFormat("yyyy_MM_dd_hh_mm_sss_a").format(new Date());

	/**
	 * @author Cigniti
	 * @return
	 */
	private synchronized static ExtentReports getExtentReport() {
		if (reporter == null) {
			if(System.getenv("JENKINS_HOME")==null){				
				CommonVariables.extentReportPath=System.getProperty("user.dir").replace("\\", "/")+"/logs/Reports/local/ExtentReports"+timestamp+".html";
			}else{
				String buildNumber=System.getenv("BUILD_NUMBER");
				CommonVariables.extentReportPath=System.getProperty("user.dir").replace("\\", "/")+"/logs/Reports/jenkins/ExtentReports"+buildNumber+".html";
			}
			reporter = new ExtentReports(CommonVariables.extentReportPath, true, DisplayOrder.NEWEST_FIRST);			
			File extentReportConfigFile = new File(System.getProperty("user.dir")+"/lib/config.xml");
			reporter.loadConfig(extentReportConfigFile);
			reporter.addSystemInfo(sysInfo);			
		}
		return reporter;
	}

	/**
	 * @author Cigniti
	 * @param testName
	 * @param testDescription
	 * @return
	 */
	public synchronized static ExtentTest getTest(String testName, String testDescription) {

		if (!nameToTestMap.containsKey(testName)) {
			Long threadID = Thread.currentThread().getId();
			ExtentTest test = getExtentReport().startTest(testName, testDescription);
			nameToTestMap.put(testName, test);
			threadToExtentTestMap.put(threadID, testName);
		}
		return nameToTestMap.get(testName);
	}

	public synchronized static ExtentTest getTest(String testName) {
		return getTest(testName, "");
	}

	/*
	 * At any given instance there will be only one test running in a thread. We
	 * are already maintaining a thread to extentest map. This method should be
	 * used after some part of the code has already called getTest with proper
	 * testcase name and/or description.
	 *
	 * Reason: This method is not for creating test but getting an existing test
	 * reporter. sometime you are in a utility function and want to log
	 * something from there. Utility function or similar code sections are not
	 * bound to a test hence we cannot get a reporter via test name, unless we
	 * pass test name in all method calls. Which will be an overhead and a rigid
	 * design. However, there is one common association which is the thread ID.
	 * When testng executes a test it puts it launches a new thread, assuming
	 * test level threading, all tests will have a unique thread id hence call
	 * to this function will return the right extent test to use
	 */
	public synchronized static ExtentTest getTest() {
		Long threadID = Thread.currentThread().getId();

		if (threadToExtentTestMap.containsKey(threadID)) {
			String testName = threadToExtentTestMap.get(threadID);
			return nameToTestMap.get(testName);
		}
		return null;
	}

	public synchronized static void closeTest(String testName) {

		if (!testName.isEmpty()) {
			ExtentTest test = getTest(testName);
			getExtentReport().endTest(test);
			getExtentReport().flush();
		}
	}

	public synchronized static void closeTest(ExtentTest test) {
		if (test != null) {
			getExtentReport().endTest(test);
		}
	}

	public synchronized static void closeTest()  {
		ExtentTest test = getTest();
		closeTest(test);
	}

	public synchronized static void closeReport() {
		if (reporter != null &CommonVariables.CommonDriver.get()!=null) {
			reporter.flush();
			reporter.close();
		}
	}
}
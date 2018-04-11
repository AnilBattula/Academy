package com.vv.test.automation.executor;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.testng.annotations.Test;

import com.vv.test.automation.fileutils.ExcelReader;

public class TestDriver {

	static FileWriter fos;
	private static String outputFileName = System.getProperty("user.dir").replace("\\", "/")+ "/testng_xmls/runtime.xml";
	public static String testDriverSheet= System.getProperty("user.dir").replace("\\", "/")+ "/testdata/Test_Driver.xlsx";
	public static ExcelReader xls;
	static final String browserConfigSheetName = "browserConfiguration";
	static final String allTestsSheetName = "allTests";

	/**
	 * @author Cigniti
	 * @param sheetName
	 * @throws IOException
	 */
	synchronized public static void createXml(String sheetName) throws IOException {
		xls = new ExcelReader(testDriverSheet);
		Properties config_prop = new Properties();
		try{
			FileReader reader;
			reader = new FileReader("config.properties");
			config_prop.load(reader);
		}
		catch (IOException e) {
			System.out.println("Failed to fetch/read 'config.properties' file.");
		}
		try {
			if (new File(outputFileName).exists()) {
				System.out.println("Test suite exists :- "+ outputFileName);
				new File(outputFileName).delete();
			}
			fos = new FileWriter(outputFileName);
			fos.write("<?xml version='1.0' encoding='UTF-8'?>\n");
			fos.write("<!DOCTYPE suite SYSTEM 'http://testng.org/testng-1.0.dtd'>\n");
			fos.write("<suite name='Princess-Automation' parallel='tests' thread-count='1' preserve-order='true' configfailurepolicy='continue' junit='false' skipfailedinvocationcounts='false' data-provider-thread-count='10' group-by-instances='false' allow-return-values='false'>\n\n");
			fos.write("<listeners>\n");
			fos.write("\t<listener class-name=\"com.princessCruise.web.automation.listeners.ExecutionListener\" />\n");
			fos.write("</listeners>\n");
			int startRow = 1;
			for (int rNum = startRow; rNum <= ExcelReader.getRowCount(sheetName); rNum++) {
				String browser = xls.getCellData(sheetName, "Browser", rNum);
				if ((!browser.isEmpty()) && (xls.getCellData(sheetName, "RunMode", rNum).equalsIgnoreCase("Y"))) {
					HashMap<String, String> browseConfigData = getBrowserDetails(browserConfigSheetName, browser);
					fos.write("\t<test name='Test-"+ browseConfigData.get("deviceType")+ "' junit='false' skipfailedinvocationcounts='false' preserve-order='true' group-by-instances='false' allow-return-values='false'>\n");
					fos.write("\t\t\t<parameter name=\"automationName\" value=\""+ browseConfigData.get("automationName") + "\"></parameter>\n");
					fos.write("\t\t\t<parameter name=\"platform\" value=\""+ browseConfigData.get("platform") + "\"></parameter>\n");
					fos.write("\t\t\t<parameter name=\"browser\" value=\""+ browseConfigData.get("deviceType") + "\"></parameter>\n");	
					fos.write("\t\t\t<parameter name=\"Version\" value=\""+ browseConfigData.get("browser_version") + "\"></parameter>\n");
					fos.write("\t\t\t<parameter name=\"environment\" value=\""+ browseConfigData.get("environment")  + "\"></parameter>\n");
					fos.write("\t\t\t<parameter name=\"userRegion\" value=\""+ config_prop.getProperty("userRegion") + "\"></parameter>\n");
					fos.write("\t\t\t<parameter name=\"seleniumGrid\" value=\""+ config_prop.getProperty("seleniumGrid")  + "\"></parameter>\n");
					fos.write("\t\t\t<parameter name=\"cloudExecution\" value=\""+ config_prop.getProperty("cloudExecution") + "\"></parameter>\n");
					fos.write("\t\t\t<parameter name=\"emailReport\" value=\""+ config_prop.getProperty("emailReport") + "\"></parameter>\n");
					fos.write("\t\t<groups>\n");
					fos.write("\t\t\t<run>\n");
					fos.write("\t\t\t<include name=\"smoke\"/>\n");
					fos.write("\t\t\t<include name=\"regression\"/>\n");
					fos.write("\t\t\t</run>\n");
					fos.write("\t\t</groups>\n");
					fos.write("\t\t <classes>\n");
					if(config_prop.getProperty("localExecution").toString().equalsIgnoreCase("true")
							&& config_prop.getProperty("IsKeywordDrivenRequired").toString().equalsIgnoreCase("true")){
						List<String> allClasses = allClassNames(allTestsSheetName,"classname",browser);
						for(int l =0; l<allClasses.size(); l++){
							fos.write("\t\t\t<class name=\"" + allClasses.get(l) +"\" />\n");
						}
					}
					fos.write("\t\t</classes>\n");
					fos.write("\t\t</test>\n");
					fos.write("\n");
				}
			}
			fos.write("</suite>");
			fos.flush();
			if (new File(outputFileName).exists()) {
				System.out.println("Test suite successfully generated :- " + outputFileName);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @author Cigniti
	 * @param allTestsSheetName
	 * @param className
	 * @param browser
	 * @return
	 */
	public static List<String> allClassNames(String allTestsSheetName, String className, String browser){
		List<String> methods = new ArrayList<String>();
		int methodNum = 2;
		do{
			String methodName = xls.getCellData(allTestsSheetName, "classname", methodNum);
			if(!(methodName.isEmpty())&&(xls.getCellData(allTestsSheetName, "Runmode", methodNum).equalsIgnoreCase("Y"))){					
				methods.add(methodName);
			}
			methodNum++;
		}while(!(xls.getCellData(allTestsSheetName, "classname", methodNum).isEmpty()));
		return methods;
	}

	/**
	 * @author Cigniti
	 * @param browserConfigSheetName
	 * @param browser
	 * @return
	 */
	public static HashMap<String, String> getBrowserDetails(String browserConfigSheetName, String browser){
		HashMap<String, String> browserDetails = new HashMap<String, String>();
		int startRow = 1;
		for(int i = startRow; i<ExcelReader.getRowCount(browserConfigSheetName); i++){
			String browserShown = xls.getCellData(browserConfigSheetName, 0, i);
			if((!(browserShown.isEmpty()) && browserShown.equalsIgnoreCase(browser))){
				int colCount = xls.getColumnCount(browserConfigSheetName, i);
				for(int j = 0; j<colCount; j++){
					String key = xls.getCellData(browserConfigSheetName, j, i+1);
					String value = xls.getCellData(browserConfigSheetName, j, i+2);
					browserDetails.put(key, value);
				}
			}
		}
		return browserDetails;
	}

	/**
	 * @author Cigniti
	 * @param args
	 * @throws Exception
	 */
	@Test(groups = "regression",description="TestDriver")
	public void runTestDriver() throws Exception
	{
		try{
			createXml("Browsers");	
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
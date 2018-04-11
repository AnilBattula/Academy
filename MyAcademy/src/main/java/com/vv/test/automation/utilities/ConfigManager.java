package com.vv.test.automation.utilities;

import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import org.testng.Assert;

public class ConfigManager {
	public static ThreadLocal<Properties> commonProp = new ThreadLocal<Properties>();
	public static ThreadLocal<Boolean> ArePropertiesSet = new ThreadLocal<Boolean>();
	public static ThreadLocal<Boolean> ArePropertiesUpdated = new ThreadLocal<Boolean>();
	protected static String propName = "config.properties";

	/**
	 * @author Cigniti
	 * @description: set properties
	 */
	public static void setProperties() {
		try {
			commonProp.set(new Properties());
			FileReader reader;
			reader = new FileReader(propName);
			commonProp.get().load(reader);
			ArePropertiesSet.set(true);
			ArePropertiesUpdated.set(false);
		} catch (IOException e) {
			e.printStackTrace();
			ArePropertiesSet.set(false);
			ArePropertiesUpdated.set(false);
		}
	}

	/**
	 * @author Cigniti
	 * @description: load properties
	 * @return
	 */
	public static Properties getProperties() {
		return commonProp.get();
	}

	/**
	 * @author Cigniti
	 * @description: update properties
	 */
	public static void UpdateProperties() {
		try {
			if (!ArePropertiesSet.get()) {
				setProperties();
			}
			String env = CommonVariables.Environment.get();
			String runningEnv = "";
			boolean isGridExecution = CommonVariables.IsGridExecution.get();
			boolean isCloudExecution = CommonVariables.IsCloudExecution.get();
			boolean isLocalExecution = CommonVariables.IsLocalExecution.get();
			
			try {
				runningEnv = InetAddress.getLocalHost().getHostName();
				if (runningEnv != null && (!runningEnv.isEmpty())) {
					commonProp.get().setProperty("gridHubIP", commonProp.get().getProperty("gridHubIP"));
				}
			} catch (UnknownHostException e) {
				System.out.println("Failed to get host Name:" + e.getLocalizedMessage());
			}
			if (env != null && !(env.isEmpty())) {
				String url = "";
				String suppressUrl = "";
				String suppressCredential = "";				
				if (env.equalsIgnoreCase("Dev")) {
					String dev_url = getProperties().getProperty("dev_url").trim();
					url = dev_url;
					suppressUrl = suppressCredential + dev_url;
				} else if (env.equalsIgnoreCase("UAT")) {
					String uat_url = getProperties().getProperty("uat_url").trim();
					url = uat_url;
					suppressUrl = suppressCredential + uat_url;
				} else if (env.equalsIgnoreCase("QA")) {
					String qa_url = getProperties().getProperty("qa_url").trim();
					url = qa_url;
					suppressUrl = suppressCredential + qa_url;
				} else if (env.equalsIgnoreCase("preprod")) {
					String preprod_url = getProperties().getProperty("preprod_url").trim();
					url = preprod_url;
					suppressUrl = suppressCredential + preprod_url;
				} else if (env.equalsIgnoreCase("STAG")) {
					String stag_url = getProperties().getProperty("stag_url").trim();
					url = stag_url;
					suppressUrl = suppressCredential + stag_url;
				} else if (env.equalsIgnoreCase("PROD")) {
					url = getProperties().getProperty("prod_url").trim();
					suppressUrl = url;
				} else {
					System.out.println("Unknown Environment : " + env);
					Assert.fail("Unknown Environment :" + env);
				}
				url = url.trim();
				commonProp.get().setProperty("userRegion", CommonVariables.userRegion.get());
				commonProp.get().setProperty("machineIP", CommonVariables.machineIP.get());
				switch (env.trim().toLowerCase()) {
				case "prod":
				case "production":
					commonProp.get().setProperty("environment", "Prod");
					commonProp.get().setProperty("dbServerName", commonProp.get().getProperty("dbServerName"));
					commonProp.get().setProperty("dbUserName", commonProp.get().getProperty("dbUserName"));
					commonProp.get().setProperty("dbPassword", commonProp.get().getProperty("dbPassword"));
					commonProp.get().setProperty("urlSuppressCredential", suppressUrl);
					commonProp.get().setProperty("url", url);
					commonProp.get().setProperty("seleniumGrid", String.valueOf(isGridExecution));
					commonProp.get().setProperty("cloudExecution", String.valueOf(isCloudExecution));
					commonProp.get().setProperty("localExecution", String.valueOf(isLocalExecution));
					commonProp.get().setProperty("deviceType", CommonVariables.DeviceName.get());
					commonProp.get().setProperty("platform", CommonVariables.PlatformName.get());
					commonProp.get().setProperty("appiumPort", CommonVariables.appiumPort.get());
					commonProp.get().setProperty("os", CommonVariables.OS.get());
					commonProp.get().setProperty("os_version", CommonVariables.OS_VERSION.get());
					break;
				case "preprod":
				case "preproduction":
				case "pre-prod":
					commonProp.get().setProperty("environment", "preprod");
					commonProp.get().setProperty("dbServerName", commonProp.get().getProperty("dbServerName"));
					commonProp.get().setProperty("dbUserName", commonProp.get().getProperty("dbUserName"));
					commonProp.get().setProperty("dbPassword", commonProp.get().getProperty("dbPassword"));
					commonProp.get().setProperty("urlSuppressCredential", suppressUrl);
					commonProp.get().setProperty("url", url);
					commonProp.get().setProperty("seleniumGrid", String.valueOf(isGridExecution));
					commonProp.get().setProperty("cloudExecution", String.valueOf(isCloudExecution));
					commonProp.get().setProperty("localExecution", String.valueOf(isLocalExecution));
					commonProp.get().setProperty("deviceType", CommonVariables.DeviceName.get());
					commonProp.get().setProperty("platform", CommonVariables.PlatformName.get());
					commonProp.get().setProperty("appiumPort", CommonVariables.appiumPort.get());
					commonProp.get().setProperty("os", CommonVariables.OS.get());
					commonProp.get().setProperty("os_version", CommonVariables.OS_VERSION.get());
					break;
				case "dev":
					commonProp.get().setProperty("environment", "DEV");
					commonProp.get().setProperty("dbServerName", commonProp.get().getProperty("dbServerName"));
					commonProp.get().setProperty("dbUserName", commonProp.get().getProperty("dbUserName"));
					commonProp.get().setProperty("dbPassword", commonProp.get().getProperty("dbPassword"));
					commonProp.get().setProperty("urlSuppressCredential", suppressUrl);
					commonProp.get().setProperty("url", url);
					commonProp.get().setProperty("seleniumGrid", String.valueOf(isGridExecution));
					commonProp.get().setProperty("cloudExecution", String.valueOf(isCloudExecution));
					commonProp.get().setProperty("localExecution", String.valueOf(isLocalExecution));
					commonProp.get().setProperty("deviceType", CommonVariables.DeviceName.get());
					commonProp.get().setProperty("platform", CommonVariables.PlatformName.get());
					commonProp.get().setProperty("appiumPort", CommonVariables.appiumPort.get());
					commonProp.get().setProperty("os", CommonVariables.OS.get());
					commonProp.get().setProperty("os_version", CommonVariables.OS_VERSION.get());
					break;
				case "qa":
					commonProp.get().setProperty("environment", "QA");
					commonProp.get().setProperty("dbServerName", commonProp.get().getProperty("dbServerName"));
					commonProp.get().setProperty("dbUserName", commonProp.get().getProperty("dbUserName"));
					commonProp.get().setProperty("dbPassword", commonProp.get().getProperty("dbPassword"));
					commonProp.get().setProperty("urlSuppressCredential", suppressUrl);
					commonProp.get().setProperty("url", url);
					commonProp.get().setProperty("seleniumGrid", String.valueOf(isGridExecution));
					commonProp.get().setProperty("cloudExecution", String.valueOf(isCloudExecution));
					commonProp.get().setProperty("localExecution", String.valueOf(isLocalExecution));
					commonProp.get().setProperty("deviceType", CommonVariables.DeviceName.get());
					commonProp.get().setProperty("platform", CommonVariables.PlatformName.get());
					commonProp.get().setProperty("appiumPort", CommonVariables.appiumPort.get());
					commonProp.get().setProperty("os", CommonVariables.OS.get());
					commonProp.get().setProperty("os_version", CommonVariables.OS_VERSION.get());
					break;
				case "stag":
					commonProp.get().setProperty("environment", "STAG");
					commonProp.get().setProperty("dbServerName", commonProp.get().getProperty("dbServerName"));
					commonProp.get().setProperty("dbUserName", commonProp.get().getProperty("dbUserName"));
					commonProp.get().setProperty("dbPassword", commonProp.get().getProperty("dbPassword"));
					commonProp.get().setProperty("urlSuppressCredential", suppressUrl);
					commonProp.get().setProperty("url", url);
					commonProp.get().setProperty("seleniumGrid", String.valueOf(isGridExecution));
					commonProp.get().setProperty("cloudExecution", String.valueOf(isCloudExecution));
					commonProp.get().setProperty("localExecution", String.valueOf(isLocalExecution));
					commonProp.get().setProperty("deviceType", CommonVariables.DeviceName.get());
					commonProp.get().setProperty("platform", CommonVariables.PlatformName.get());
					commonProp.get().setProperty("appiumPort", CommonVariables.appiumPort.get());
					commonProp.get().setProperty("os", CommonVariables.OS.get());
					commonProp.get().setProperty("os_version", CommonVariables.OS_VERSION.get());
					break;
				default:
					System.out.println(
							"***** Running Test cases based on default values set in 'Config.Properties' file. *****");
					break;
				}
			} else {
				System.out.println(
						"Unable to find Environment type. It should be set at every Thread level under '@BeforeTest' method against 'CommonVariables.Environment' variable.");
				Assert.fail(
						"Unable to find Environment type. It should be set at every Thread level under '@BeforeTest' method against 'CommonVariables.Environment' variable.");
			}
			ArePropertiesUpdated.set(true);
		} catch (Exception e) {
			e.getLocalizedMessage();
		}
	}
}
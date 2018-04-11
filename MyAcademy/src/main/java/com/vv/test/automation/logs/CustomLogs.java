package com.vv.test.automation.logs;

import com.vv.test.automation.utilities.CommonVariables;

public class CustomLogs {

	/**
	 * @author Cigniti
	 * @param LogLevel
	 * @param LogMessage
	 */
	private static void logGlobalLogs(String LogLevel, String LogMessage) {
		if(LogLevel.equalsIgnoreCase("debug")){
			CommonVariables.CurrentGlobalLog.get().debug(LogMessage);
		}else if(LogLevel.equalsIgnoreCase("info")){
			CommonVariables.CurrentGlobalLog.get().info(LogMessage);
		}else if(LogLevel.equalsIgnoreCase("error")){
			CommonVariables.CurrentGlobalLog.get().error(LogMessage);
		}else if(LogLevel.equalsIgnoreCase("screenshot")){
		}
	}

	/**
	 * @author Cigniti
	 * @param LogLevel
	 * @param LogMessage
	 */
	private static void logTestClassLogs(String LogLevel, String LogMessage) {
		if (LogLevel.equalsIgnoreCase("debug")) {
			CommonVariables.TestClassLog.get().debug(LogMessage);
		} else if (LogLevel.equalsIgnoreCase("info")) {
			CommonVariables.TestClassLog.get().info(LogMessage);
		} else if (LogLevel.equalsIgnoreCase("error")) {
			CommonVariables.TestClassLog.get().error(LogMessage);
		} else if (LogLevel.equalsIgnoreCase("screenshot")) {
		}
	}

	/**
	 * @author Cigniti
	 * @param LogName
	 * @param logLevel
	 * @param logMessage
	 */
	public static void addToLog(String LogName, String logLevel, String logMessage){

		if(LogName =="TestClassLog" && CommonVariables.TestClassLog.get()!=null)
		{
			logTestClassLogs(logLevel, logMessage);
		}else if(LogName =="CurrentGlobalLog" && CommonVariables.CurrentGlobalLog.get()!=null)
		{
			logGlobalLogs(logLevel, logMessage);
		}else if(LogName =="TestCaseLog" && CommonVariables.TestCaseLog.get()!=null)
		{
			logTestCaseLogs(logLevel, logMessage);
		}
	}

	/**
	 * @author Cigniti
	 * @param logName
	 * @param LogLevel
	 * @param LogMessage
	 */
	public static void addToLog(CustomLogName logName, String LogLevel, String LogMessage){
		switch (logName) {
		case CurrentTestClassLog:
			if(CommonVariables.TestClassLog.get()!=null)
			{
				logTestClassLogs(LogLevel, LogMessage);
			}
		case CurrentGlobalLog:
			if(CommonVariables.CurrentGlobalLog.get()!=null)
			{
				logGlobalLogs(LogLevel, LogMessage);
			}

		case CurrentTestCaseLog:
			if(CommonVariables.TestCaseLog.get()!=null)
			{
				logTestCaseLogs(LogLevel, LogMessage);
			}

		default:
			break;
		}	
	}

	/**
	 * @author Cigniti
	 * @param LogLevel
	 * @param LogMessage
	 */
	private static void logTestCaseLogs(String LogLevel, String LogMessage) {
		if(LogLevel.equalsIgnoreCase("debug")){
			CommonVariables.TestCaseLog.get().debug(LogMessage);
		}else if(LogLevel.equalsIgnoreCase("info")){
			CommonVariables.TestCaseLog.get().info(LogMessage);
		}else if(LogLevel.equalsIgnoreCase("error")){
		}
	}
	
	/**
	 * @author Cigniti
	 * @param e
	 * @param LogName
	 * @param LogLevel
	 * @param LogMessage
	 */
	public void addToLog(Exception e, String LogName, String LogLevel, String LogMessage) {
		String ExceptionName = e.getClass().toString();
		String fullClassName = e.getStackTrace()[0].getClassName();
		String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
		String methodName = e.getStackTrace()[0].getMethodName();
		int lineNumber = e.getStackTrace()[0].getLineNumber();
		String ErrorMessage = ExceptionName + " occurred in the class: " + fullClassName + " and the method name is: "
				+ methodName + " at the line number: " + lineNumber + " :: Custom Message: " + LogMessage;
		this.addToLog(LogName, LogLevel, LogMessage);
	}

}

package com.vv.test.automation.logs;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.mortbay.log.Log;

import com.vv.test.automation.utilities.CommonVariables;


public class DetailedLogs {
	
	public static Logger APP_LOGS=null;

	/**
	 * @author Cigniti
	 * @throws IOException
	 */
	public void StartLogs() throws IOException{
		String timestamp = new SimpleDateFormat("yyyy_MM_dd_hh_mm_sss'.log'").format(new Date());
		System.setProperty("logfile.name",System.getProperty("user.dir")+"/logs/TestLog.log");
		APP_LOGS = Logger.getLogger("DetailedLogs");
		PatternLayout layout = new PatternLayout("%d{dd/MM/yyyy HH:mm:ss} %-5p %c %x - %m%n");
		FileAppender appender = new FileAppender(layout,System.getProperty("user.dir")+"/logs/DetailedLog_"+ timestamp,false);
		APP_LOGS.addAppender(appender);
	}

	/**
	 * @author Cigniti
	 * @param LogName
	 * @param location
	 * @return
	 * @throws IOException
	 */
	public Logger StartLogs(String LogName, String location) throws IOException{
		try{
			Logger logger = Logger.getLogger(LogName);
			PatternLayout layout = new PatternLayout("%d{dd/MM/yyyy HH:mm:ss}\t%-5p\t%c\t%x\t%m%n");
			CommonVariables.TestCaseLogPath.set("");
			FileAppender appender = new FileAppender(layout,location.trim() + "/" + LogName + ".log",false);
			CommonVariables.TestCaseLogPath.set(location + "/" + LogName + ".log");
			logger.addAppender(appender);
			return logger;
		}catch(Exception e){
			Log.info("Error StartLogs");;
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @author Cigniti
	 * @param location
	 */
	public void CreateFolder(String location){
		new File(location).mkdir();
	}
}
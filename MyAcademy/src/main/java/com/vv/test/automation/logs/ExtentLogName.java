package com.vv.test.automation.logs;

import com.relevantcodes.extentreports.LogStatus;

public interface ExtentLogName {
	
	LogStatus PASS = LogStatus.PASS;
    LogStatus FAIL = LogStatus.FAIL;
    LogStatus SKIP = LogStatus.SKIP;
    LogStatus INFO = LogStatus.INFO;
    LogStatus WARNING = LogStatus.WARNING;
    LogStatus UNKNOWN = LogStatus.UNKNOWN;
    LogStatus FATAL=LogStatus.FATAL;
    LogStatus ERROR=LogStatus.ERROR;

}

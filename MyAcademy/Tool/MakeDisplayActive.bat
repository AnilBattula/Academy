::Code to Make the display active even when system is locked
POWERCFG -REQUESTSOVERRIDE PROCESS IEDriverServer.exe Display System
POWERCFG -REQUESTSOVERRIDE PROCESS chromedriver.exe Display System
POWERCFG -REQUESTSOVERRIDE PROCESS firefox.exe Display System

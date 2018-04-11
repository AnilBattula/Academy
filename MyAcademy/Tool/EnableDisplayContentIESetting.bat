@echo off
set "regKeyExists=1"
reg query "HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Internet Settings\Zones\3" /v "1609" || (
echo Display Mixed Content Setting Key doesn't exist!!!!
set "regKeyExists=0"
) 

IF %regKeyExists%==0 (
   echo Hence Adding New Registry Key
   REG ADD "HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Internet Settings\Zones\3" /v "1609" /t "REG_DWORD" /d "0" /f
)  ELSE (
	echo Verifying If Display Mixed Content Setting Is Disabled
)


FOR /F "skip=2tokens=2*" %%A IN ('reg.exe QUERY "HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Internet Settings\Zones\3" /v "1609"') DO SET "DFMT=%%B"
:hexadecimal
::ECHO New variable DFMT = %DFMT%
SET "myVal=0x3"
:hexadecimal

if %DFMT% ==%myVal% (echo Display Mixed Content Setting Is Disabled, hence modifying registry to enable it.
	echo Y|REG DELETE "HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Internet Settings\Zones\3" /v "1609"
	REG ADD "HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Internet Settings\Zones\3" /v "1609" /t "REG_DWORD" /d "0" /f
	) else (
	echo Display Mixed Content Setting Is Enabled by default in IE.	
)

echo Pre-Requisite of Enabling Display Mixed Content Setting in IE browser is enabled

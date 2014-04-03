mvn -version
dir
cd Appzone_mvn
dir
mvn dependency:copy-dependencies
icacls ExePath\IEDriverServer.exe /grant Everyone:(GE)
mvn -PLoginSuite test -Dbrowser=IE -DscreenResolution=ABC -Denvironment=https://my-qa.enterpriseappzone.com
mvn -version
dir
cd Appzone_mvn
dir
mvn dependency:copy-dependencies
mvn compile
mvn -PLoginSuite test -Dbrowser=FireFox -DscreenResolution=ABC -Denvironment=my-qa.enterpriseappzone.com
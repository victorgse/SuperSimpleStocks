# SuperSimpleStocks
Solution for "Super Simple Stocks" technical exercise

Maven is used to compile the project. The following additional profiles have been enabled: jacoco (code coverage analysis), pitest (mutation testing), and sonar (sonarqube code quality analysis).

This maven command can be used to run all tests and compile everything into a jar (enabling the jacoco, pitest, and sonar profiles is optional):
mvn clean verify -Pjacoco,pitest,sonar

Jacoco publishes its report under /target/site/jacoco/index.html
Pitest publishes its report under /target/pit-reports/.../index.html
Sonarqube will publish its report to your local sonar server which should be running at the default location

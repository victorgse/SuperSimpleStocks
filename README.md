# Super Simple Stocks
Solution to the awesome "Super Simple Stocks" technical exercise

Maven is used to compile the project. The following additional profiles have been enabled: jacoco (code coverage analysis), pitest (mutation testing), and sonar (sonarqube code quality analysis).

Jacoco publishes its report under /target/site/jacoco/index.html

Pitest publishes its report under /target/pit-reports/.../index.html

Sonarqube publishes its report to your local sonar server which should be running at its default address

This maven command can be used to run all tests and package up everything into a jar (enabling the jacoco, pitest, and sonar profiles is optional):

mvn clean verify -Pjacoco,pitest,sonar

To install the app (so that you would be able to execute the jar later on), run this command instead:

mvn clean install

After, that you would be able to run the jar with this command:

java -jar super-simple-stocks-1.0-SNAPSHOT.jar

The program logs its output to the console, as well as to the super-simple-stocks.log file, which lives at the project's root. (There might be an exception in the program's log after running the program. This is due to the app being asked to compute the P/E ratio of a company that didn't pay out any dividends and division to zero being impossible - this exception is intentionally printed to the program's output to facilitate the possibility for debugging.)


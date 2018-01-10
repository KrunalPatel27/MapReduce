**MapReduce Implementation for Test Coverage**

**email address: kpate236@uic.edu**
**First Name: Krunal, Last Name: Patel**

**Link: https://youtu.be/0UYzarpr15c**


Make sure your in this directory: Asteroid_Game_Junit_Project-master\Asteroids-Testing\Asteroids-Testing\Asteroids

To create Jacoco Coverage Reports

	run command: ```gradle test --tests package.classname.methodname jacocoTestReport```
	this command will create individual test coverage reports in build/reprots/jacoco
	I just copied these XML testReport files into a seperate directory called IndividualCoverageReport/XML to Parse later

To parse XML report files
	run command: `gradle createTxt`
	this will output parsed XML to IndividualCoverageReport/txt directory
	This file contains the line number and class assocaited with it (the name of the txt file is testMethodName)

To Run the MapReduce
	create JAR using: 'gradle jar' 



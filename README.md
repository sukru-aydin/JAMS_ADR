# JAMS-ADR Project

In this repository, UI Search Functionality case studies are performed by following Cucumber BBD Framework.  

## Instructions
    Test Scenarios are prepared by;

Gamze Bektaş

Project Manager

gamze.bektas@nmq.digital.com

    Test Scenarios are automated by;

Şükrü Aydın

Specialist Software Developer

sukru.aydin@nmq.digital.com



### Build Tool
```Maven```

### Dependencies
```selenium-java```
```webdrivermanager```
```cucumber-java```
```cucumber-junit```
```reporting-plugin```
```javafaker```

### Plugins
```maven-surefire-plugin```

### System Requirements
```Java 8 + SDK```

### Nice to Know
1 - For running tests scenarios;

        - go to CukesRunner.java class (src > test > java > com > jams > runners > CukesRunner)
        - specify tags section in CucumberOptions
                * @ui for UI tests
                * @all for all tests
        - run CukesRunner.java class
    
2 - Cucumber BDD Framework has 2 layers;

        - Business Layer --> src > test > resources > features
        - Implementation Layer --> src > > test > java > com > jams > step_defs
3 - Automated UI case on <ins>...</ins>.

4 - <ins>Relevant credentials</ins> are located in configuration.properties file.

5 - Followed  <ins>Page Object Model</ins> & <ins>Singleton Design Pattern.</ins>

6 - Achieved <ins>Data Driven Testing</ins> by configuration.properties and Cucumber Expressions.

7 - Implemented <ins>Gherkin Language</ins> in feature files.

8 - Utilized <ins>Hooks</ins> class for taking screenshots if a test fails.



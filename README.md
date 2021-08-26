### Description
This application leverages Java, Cucumber, and Selenium 
to interact with the [Computer Database](http://computer-database.herokuapp.com/computers) website.
The Cucumber feature files (written in Gherkin) run through different scenarios related to 
creating, reading, updating, and deleting computers within the database via a headless Chrome browser. 

Upon completion, a report is generated that captures not only the outcome of each
test/scenario, but it's runtime as well. 

### System Requirements
- Gradle 7
- Java 11+
- Chrome 
- OSX

### Execution 
To run the automation test suite, execute the following command from the project's root directory: 

    gradle test -Dheadless=true

Changing the **headless** value to **false**, will cause a browser to 
open for each of the feature's scenarios. By default, the application does 
**NOT** run in headless mode.

Location of test report:

    PROJECT_DIR/build/reports/tests/test/index.html

### Test Cases
Manual testcases can be found under the [docs](docs) section. 

### Features
The Cucumber feature files for the various CRUD test cases: 
- [`CREATE` CreateComputer.feature](src/test/resources/features/CreateComputer.feature)
- [`READ` RetrieveComputer.feature](src/test/resources/features/RetrieveComputer.feature)
- [`UPDATE` UpdateComputer.feature](src/test/resources/features/UpdateComputer.feature)
- [`DELETE` DeleteComputer.feature](src/test/resources/features/DeleteComputer.feature)

Step Definitions can be found [here](src/test/java/computers/TestSteps.java). 

### Outstanding Items
- Adding ability to run features in parallel
- Support for other browsers and operating systems
- Adding logic to store source and/or screenshot on test case failure
- Updating creation logic to create unique (to a given run) datasets
- Better state logic for cleaning up test data on errors or completion
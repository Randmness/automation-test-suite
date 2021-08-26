### Description
This application [Computer Database](http://computer-database.herokuapp.com/computers) application. 

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

Upon completion, a report is generated at:

    PROJECT_DIR/build/reports/tests/test/index.html

### Test Cases
The manual testcases can be found under the [docs](docs) section. 

### Features
The Cucumber features for the various CRUD test cases: 
- [`CREATE` CreateComputer.feature](src/test/resources/CreateComputer.feature)
- [`READ` RetrieveComputer.feature](src/test/resources/RetrieveComputer.feature)
- [`UPDATE` UpdateComputer.feature](src/test/resources/UpdateComputer.feature)
- [`DELETE` DeleteComputer.feature](src/test/resources/DeleteComputer.feature)

### Outstanding Items
- Adding ability to run features in parallel
- Support for other browsers and operating systems
- Adding logic to store source and/or screenshot on test case failure
- Updating creation logic to create unique (to a given run) datasets
- Better state logic for cleaning up test data on errors or completion
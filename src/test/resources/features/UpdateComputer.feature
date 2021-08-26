@smoke
Feature: Updating existing company

  Scenario: Successfully updating entry
    #TODO Add logic to generate unique company name and delete accordingly
    Given Navigate to page "http://computer-database.herokuapp.com/computers"
    And Computer entry already exists
      | computerName  | introduced  | discontinued  | company |
      | featureTest_UPDATE   | 2001-01-01  | 2002-02-02    | Canon   |
    When User enters filter criteria "featureTest_UPDATE"
    And User clicks the "Filter by name" button
    And User clicks the "featureTest_UPDATE" entry
    And Computer entry fields are entered
      | computerName  | introduced  | discontinued  | company |
      | featureTest_UPDATE2   | 2002-01-01  | 2003-02-02    | Canon   |
    And User clicks the "Save this computer" button
    Then User will be taken to the root page
    And Message will appear "Done! Computer featureTest_UPDATE2 has been updated".

  Scenario: Canceling an attempt to update entry
    #TODO Add logic to generate unique company name and delete accordingly
    Given Navigate to page "http://computer-database.herokuapp.com/computers"
    And Computer entry already exists
      | computerName  | introduced  | discontinued  | company |
      | featureTest_UPDATE   | 2001-01-01  | 2002-02-02    | Canon   |
    When User enters filter criteria "featureTest_UPDATE"
    And User clicks the "Filter by name" button
    And User clicks the "featureTest_UPDATE" entry
    And User clicks the "Cancel" button
    Then User will be taken to the root page
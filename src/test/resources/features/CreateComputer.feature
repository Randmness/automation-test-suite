@smoke
Feature: Creating computer entries

  Scenario: Successfully creating entry
    Given Navigate to page "http://computer-database.herokuapp.com/computers"
    When User clicks the "Add a new computer" button
    And Computer entry fields are entered
      | computerName  | introduced  | discontinued  | company |
      | featureTest_CREATE   | 2001-01-01  | 2002-02-02    | Canon   |
    And User clicks the "Create this computer" button
    Then User will be taken to the root page
    And Message will appear "Done! Computer featureTest_CREATE has been created".

  Scenario: Attempt to create company fails for invalid data
    Given Navigate to page "http://computer-database.herokuapp.com/computers"
    When User clicks the "Add a new computer" button
    And Computer entry fields are entered
      | computerName  | introduced  | discontinued  | company |
      | [blank]       | 2001-00-01  | 2002-00-02    | Canon   |
    And User clicks the "Create this computer" button
    Then User will see elements marked for invalid data.
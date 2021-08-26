@smoke
Feature: Creating computer entries

  Scenario: Successfully creating entry
    #TODO Add logic to generate unique company name and delete accordingly
    Given Navigate to Computers database
    When User clicks button with id "add"
    And Computer entry fields are entered
      | computerName  | introduced  | discontinued  | company |
      | featureTest_CREATE   | 2001-01-01  | 2002-02-02    | Canon   |
    And User clicks Create this Computer button
    Then User will be taken to the root page
    And Message will appear "Done! Computer featureTest_CREATE has been created".

  Scenario: Attempt to create company fails for invalid data
    #TODO Add logic to generate unique company name and delete accordingly
    Given Navigate to Computers database
    When User clicks button with id "add"
    And Computer entry fields are entered
      | computerName  | introduced  | discontinued  | company |
      | [blank]       | 2001-00-01  | 2002-00-02    | Canon   |
    And User clicks Create this Computer button
    Then User will see elements marked for invalid data.
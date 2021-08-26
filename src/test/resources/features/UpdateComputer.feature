@smoke
Feature: Updating existing company

  Scenario: Successfully updating entry
    Given Navigate to page "http://computer-database.herokuapp.com/computers"
    And Computer entry already exists
      | computerName  | introduced  | discontinued  | company |
      | TEST-Feature-UPDATE   | 2001-01-01  | 2002-02-02    | Canon   |
    When User enters filter criteria "TEST-Feature-UPDATE"
    And User clicks the "Filter by name" button
    And User clicks the "TEST-Feature-UPDATE" entry
    And Computer entry fields are entered
      | computerName  | introduced  | discontinued  | company |
      | TEST-Feature-UPDATE2   | 2002-01-01  | 2003-02-02    | Canon   |
    And User clicks the "Save this computer" button
    Then User will be taken to the root page
    And Message will appear "Done! Computer TEST-Feature-UPDATE2 has been updated".

  Scenario: Attempting to update an existing entry with invalid data
    Given Navigate to page "http://computer-database.herokuapp.com/computers"
    And Computer entry already exists
      | computerName  | introduced  | discontinued  | company |
      | TEST-Feature-UPDATE   | 2001-01-01  | 2002-02-02    | Canon   |
    When User enters filter criteria "TEST-Feature-UPDATE"
    And User clicks the "Filter by name" button
    And User clicks the "TEST-Feature-UPDATE" entry
    And Computer entry fields are entered
      | computerName  | introduced  | discontinued  | company |
      | [blank]       | 2001-00-01  | 2002-00-02    | Canon   |
    And User clicks the "Save this computer" button
    Then User will see elements marked for invalid data.

  Scenario: Canceling an attempt to update entry
    Given Navigate to page "http://computer-database.herokuapp.com/computers"
    And Computer entry already exists
      | computerName  | introduced  | discontinued  | company |
      | TEST-Feature-UPDATE   | 2001-01-01  | 2002-02-02    | Canon   |
    When User enters filter criteria "TEST-Feature-UPDATE"
    And User clicks the "Filter by name" button
    And User clicks the "TEST-Feature-UPDATE" entry
    And User clicks the "Cancel" button
    Then User will be taken to the root page
@read
Feature: Initial navigation to the home/initial page.

  Scenario: Retrieve all entries from database
    Given Navigate to Computers database
    Then Application shows current entries.

  Scenario: Retrieve a previously entered entry
    Given Navigate to Computers database
    And Computer entry already exists
      | computerName  | introduced  | discontinued  | company |
      | featureTest_READ   | 2001-01-01  | 2002-02-02    | Canon   |
    When User enters filter criteria "featureTest_READ"
    And User clicks Filer By Name button
    Then Results table should show matching computer entry.
      | computerName  | introduced  | discontinued  | company |
      | featureTest_READ   | 01 Jan 2001  | 02 Feb 2002    | Canon   |

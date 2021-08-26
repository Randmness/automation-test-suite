@read
Feature: Initial navigation to the home/initial page.

  Scenario: Retrieve all entries from database
    Given Navigate to Computers database
    Then Application shows current entries.

  Scenario: Retrieve a previously entered entry
    Given Navigate to Computers database
    And Computer entry already exists
      | computerName  | introduced  | discontinued  | company |
      | featureTest   | 2001-01-01  | 2002-02-02    | Canon   |
    Then Application shows current entries.

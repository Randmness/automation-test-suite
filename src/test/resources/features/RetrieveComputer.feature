@smoke
Feature: Retrieving all and individual entries

  Scenario: Retrieve all entries from database
    Given Navigate to page "http://computer-database.herokuapp.com/computers"
    Then Application shows "computers found"

  Scenario: Check pagination of current entries
    Given Navigate to page "http://computer-database.herokuapp.com/computers"
    Then All pages going forward will show at most 10 entries
    And All pages going back will show at most 10 entries.

  Scenario: Retrieve a previously entered entry
    Given Navigate to page "http://computer-database.herokuapp.com/computers"
    And Computer entry already exists
      | computerName  | introduced  | discontinued  | company |
      | featureTest_READ   | 2001-01-01  | 2002-02-02    | Canon   |
    When User enters filter criteria "featureTest_READ"
    And User clicks the "Filter by name" button
    Then Results table should show matching computer entry.
      | computerName  | introduced  | discontinued  | company |
      | featureTest_READ   | 01 Jan 2001  | 02 Feb 2002    | Canon   |

  Scenario: Attempt to retrieve nonexistent entry
    Given Navigate to page "http://computer-database.herokuapp.com/computers"
    When User enters filter criteria "John Snow's Pokedex"
    And User clicks the "Filter by name" button
    Then Application shows "No computers found"
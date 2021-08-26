@smoke
Feature: Deleting computer

  Scenario: Delete existing computer
    #TODO Add logic to generate unique company name and delete accordingly
    Given Navigate to page "http://computer-database.herokuapp.com/computers"
    And Computer entry already exists
      | computerName  | introduced  | discontinued  | company |
      | featureTest_DEL   | 2001-01-01  | 2002-02-02    | Canon   |
    When User enters filter criteria "featureTest_DEL"
    And User clicks the "Filter by name" button
    And User clicks the "featureTest_DEL" entry
    And User clicks the "Delete this computer" button
    Then User will be taken to the root page
    And Message will appear "Done! Computer has been deleted".
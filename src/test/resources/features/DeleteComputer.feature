@smoke
Feature: Deleting computer

  Scenario: Delete existing computer
    #TODO Add logic to generate unique company name and delete accordingly
    Given Navigate to Computers database
    And Computer entry already exists
      | computerName  | introduced  | discontinued  | company |
      | featureTest_DEL   | 2001-01-01  | 2002-02-02    | Canon   |
    When User enters filter criteria "featureTest_DEL"
    And User clicks button with id "searchsubmit"
    And User clicks the "featureTest_DEL" entry
    And User clicks the Delete this Computer button
    Then User will be taken to the root page
    And Message will appear "Done! Computer has been deleted".
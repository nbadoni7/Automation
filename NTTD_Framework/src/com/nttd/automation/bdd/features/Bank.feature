Feature: OCBC Site
  Feature to Test OCBC Public Site
  

  @RegressionPack
  Scenario: Find a ATM Near By
    Given I Enter the Pincode
    And I select Branch/ATM from the dropdown
    When I click on FindUs Button
    Then I validate of the search results show Branches/ATM for the mentioned pincode

  @RegressionPack
  Scenario: Find a Branch Near Us
    Given I Enter the Pincode
    And I select Branch/ATM from the dropdown
    When I click on FindUs Button
    Then I validate of the search results show Branches/ATM for the mentioned pincode
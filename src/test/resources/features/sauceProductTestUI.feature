Feature: Product display on catalogue

  Background:
    Given I navigate to the saucedemo login page
    When I enter the username "standard_user"
    And I enter the password "secret_sauce"
    And I click the login button

  Scenario: 01 - Validate if  all the images displayed on product catalogue page are different

    When I should see the product page with below items
      | Sauce Labs Backpack               |
      | Sauce Labs Bike Light             |
      | Sauce Labs Bolt T-Shirt           |
      | Sauce Labs Fleece Jacket          |
      | Sauce Labs Onesie                 |
      | Sauce Labs Onesie                 |
      | Test.allTheThings() T-Shirt (Red) |

    Then I Validate all displayed images are different

  Scenario: 02 - Validate if sauce Product selected on Product Page image matches with the images displayed on product inventory page
    When I should see the product page with below items
      | Sauce Labs Backpack               |
      | Sauce Labs Bike Light             |
      | Sauce Labs Bolt T-Shirt           |
      | Sauce Labs Fleece Jacket          |
      | Sauce Labs Onesie                 |
      | Sauce Labs Onesie                 |
      | Test.allTheThings() T-Shirt (Red) |
    Then I Validate all displayed images are different
    When I click on the Sauce Products
      | Sauce Labs Onesie   |
      | Sauce Labs Backpack |
    Then I navigated to inventory item page
    Then I should see the Same Sauce Product selected on Product Page
      | Sauce Labs Onesie |



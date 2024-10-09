Feature: Dog API image test based on breed and sub-breed

  Scenario: 01-Validate if all images are displayed successfully
    Given I send a GET request to  breed "hound" and subBreed "afghan"
    Then  the response status code should be "200"
    And   the response should contain status "success"
    Then the response should contain image URLs for breed "hound-afghan"


  Scenario: 02-Validate if all images are displayed successfully
    Given I send a GET request to  breed "poodle" and subBreed "toy"
    Then  the response status code should be "200"
    And   the response should contain status "success"
    Then the response should contain image URLs for breed "poodle-toy"
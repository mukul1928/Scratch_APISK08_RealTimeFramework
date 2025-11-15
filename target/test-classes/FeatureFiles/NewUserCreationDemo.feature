@sanity
Feature: Creation of New User Demo

  Scenario Outline: Creation of new User Data
    Given user starts sending API requests
    When user enters the payload data
    And user submits the payload with an endpoint as "<endpoint>"
    Then validate user gets created and corrsponding status code is "<status_code>"

    Examples: 
      | endpoint  | status_code |
      | api/users |         201 |

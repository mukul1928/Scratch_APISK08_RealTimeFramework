@regression

Feature: Creation of New User with Token

  Scenario Outline: New User Creation feature
    Given user hits the site of Go rest with URI
    And user validates himself with auth token
    And user passes the payload with all details
    When user hits the server with "<endpoint>"
    Then validate a new user is created with status code as "<status_code>"
    Then validate the details in response body

    Examples: 
      | endpoint        | status_code |
      | public/v2/users |         201 |

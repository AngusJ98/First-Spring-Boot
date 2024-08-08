Feature: ActorController
  Scenario: An actor is read by ID
    Given an actor exists with ID 42
    When a GET request is made to /actors/42
    Then an ActorResponse is returned
    And the status code is 200
  Scenario: An actor is created
    Given a valid ActorInput request body
    When a POST request is made to the actors collection
    Then an ActorResponse is returned
    And the status code is 201
  Scenario: An actor that doesn't exist is read by ID
    Given an actor does not exist with ID 5000
    When a GET request is made to /actors/5000
    Then an empty response is returned
    And the status code is 404
  Scenario: An actor is deleted
    Given an actor exists with ID 14
    When a DELETE request is made to the actors collection with ID 14
    Then a blank response is returned
    And the status code of the DELETE request is 204
  Scenario: An actor is patched
    Given an actor exists with ID 45
    And a valid ActorInput request body
    When a PATCH request is made to the collection with ID 45
    Then an ActorResponse is returned
    And the status code is 200
  Scenario: An actor is put
    Given an actor exists with ID 50
    When a PUT request is made to the collection with ID 50
    Then an ActorResponse is returned
    And the status code is 200
  Scenario: An actor that doesn't exist is deleted
    Given an actor does not exist with ID 5500
    When a DELETE request is made to the actors collection with ID 5500
    Then an empty response is returned
    And the status code is 404
  Scenario: An actor that doesn't exist is put
    Given an actor does not exist with ID 455
    When a PUT request is made to the collection with ID 455
    Then an empty response is returned
    And the status code is 404
  Scenario: An actor is put
    Given an invalid ActorInput
    And an actor exists with ID 99
    When a PUT request is made to the collection with ID 99
    #Then an empty response is returned
    And the status code is 200
Feature: FilmController
  Scenario: An film is read by ID
    Given an film exists with ID 42
    When a GET request is made to /films/42
    Then an FilmResponse is returned
    And the status code for the film is 200
  Scenario: An film is created
    Given a valid FilmInput request body
    When a POST request is made to the films collection
    Then an FilmResponse is returned
    And the status code for the film is 201
  Scenario: An film that doesn't exist is read by ID
    Given an film does not exist with ID 5000
    When a GET request is made to /films/5000
    Then an empty film response is returned
    And the status code for the film is 404
@run-this
Feature: REST - Find Books Feature
    Background:
        Given the base url is set to "https://bookcart.azurewebsites.net/"
        And the primary HTTP request is "GET"
        And the header "Content-type", "application/json" is added

    Scenario: Validate that a user may find a book by name
        Given the endpoint is set to "/api/Book"
        And the user sends the request
        When the user searches for a book titled "Soul of the Sword"
        Then the id to that book must be found aswell

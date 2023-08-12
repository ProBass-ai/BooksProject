
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

  @run-this
  Scenario Outline: Validate that a user may get similar books
    Given the endpoint is set to "/api/Book"
    And the user sends the request
    And the user has the id for a book titled "Soul of the Sword"
    And the endpoint is set to "/api/Book/GetSimilarBooks/" with "book_id" id
    When the user sends the request
    Then a book titled "<book title>" is present in the book list
    And the book price is "<book price>"
    And the book author is "<book author>"
    And the book category is "<book catergory>"

    Examples:
      | book title           | book price | book author        | book catergory |
      | A Dance with Dragons | 412.00     | George R.R. Martin | Fantasy        |





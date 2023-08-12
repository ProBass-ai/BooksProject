package steps;

import dto.BookDTO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utils.RestHelper;
import utils.Services;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FindBooksSteps {

    private Services services;
    private Response response;
    private int bookId;
    private BookDTO book;
    private Map<String, String> headers = new HashMap<>();


    @Given("the base url is set to {string}")
    public void theBaseUrlIsSetTo(String baseURL){
        services = new Services(baseURL);
    }

    @Given("the primary HTTP request is {string}")
    public void thePrimaryHTTPRequestIs(String httpMethod) {
        services.setHTTPMethod(httpMethod);
    }

    @Given("the endpoint is set to {string}")
    public void theEndpointIsSetTo(String endPoint) {
        services.setEndpoint(endPoint);
    }

    @Given("the header {string}, {string} is added")
    public void theHeaderIsAdded(String key, String value) {
        headers.put(key, value);
    }

    @When("the user sends the request")
    public void theUserSendsTheRequest() {
        services.addHeaders(headers);
        services.build();
        response = services.send();
    }

    @When("the user searches for a book titled {string}")
    public void theUserSearchesForABookTitled(String bookTitle) {
        BookDTO[] books = RestHelper.convertJsonToObject(response.asString(), BookDTO[].class);
        for (BookDTO book: books) {
            if (book.getTitle().equals(bookTitle)){
                this.book = book;
            }
        }
    }

    @Given("the user has the id for a book titled {string}")
    public void theUserHasTheIdForABookTitled(String bookTitle){
        theUserSearchesForABookTitled(bookTitle);
        bookId = book.getBookId();
    }

    @Then("the id to that book must be found aswell")
    public void theIdToThatBookMustBeFoundAswell() {
        assertNotNull(this.book);
        assertNotNull(this.book.getBookId());
    }

    @Given("the endpoint is set to {string} with {string} id")
    public void theEndpointIsSetToWithId(String endpoint, String id) {
        theEndpointIsSetTo(endpoint + id);
    }

    @Then("a book titled {string} is present in the book list")
    public void aBookTitledIsPresentInTheBookList(String bookTitle) {
        BookDTO[] books = RestHelper.convertJsonToObject(response.asString(), BookDTO[].class);
        for (BookDTO book: books) {
            if (book.getTitle().equals(bookTitle)){
                this.book = book;
            }
        }
        assertEquals(bookTitle, book.getTitle());
    }

    @Then("the book price is {string}")
    public void theBookPriceIs(String bookPrice) {
        assertEquals(Integer.parseInt(bookPrice), book.getPrice());
    }

    @Then("the book author is {string}")
    public void theBookAuthorIs(String bookAuthor) {
        assertEquals(bookAuthor, book.getAuthor());
    }

    @Then("the book category is {string}")
    public void theBookCategoryIs(String bookAuthor) {
        assertEquals(bookAuthor, book.getCategory());
    }
}

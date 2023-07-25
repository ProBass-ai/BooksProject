package steps;

import dto.BookDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utils.RestHelper;
import utils.Services;

import java.util.HashMap;
import java.util.Map;

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

    @Then("the id to that book must be found aswell")
    public void theIdToThatBookMustBeFoundAswell() {
        assertNotNull(this.book);
        assertNotNull(this.book.getBookId());
    }
}

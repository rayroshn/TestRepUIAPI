package stepsDefinitions;

import com.google.gson.Gson;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class dogBreedApiTestSteps {

    private Playwright playwright;
    private APIRequestContext request;
    private APIResponse response;
    String baseUri = "https://dog.ceo/api/breed/";

    @Before
    public void setup() {
        playwright = Playwright.create();
        request = playwright.request().newContext();

    }

    @Given("I send a GET request to  breed {string} and subBreed {string}")
    public void i_send_a_get_request_to_breed_and_sub_breed(String breedName, String subBreedName) {
        String subBreedEndPoint = breedName + "/" + subBreedName + "/images";
        String URL = baseUri + subBreedEndPoint;
        response = request.get(URL);
    }

   /* @Given("I send a GET request to {string , string}")
    public void iSendAGETRequestTo(String breedName ,String subBreedName) {
        String subBreedEndPoint = breedName+ "/" +subBreedName+ "/images";
        String URL=baseUri+subBreedEndPoint;
        response = request.get(URL);
    }*/

    /* @Then("the response status code should be {int}")
     public void theResponseStatusCodeShouldBe(int statusCode) {
         Assert.assertEquals(response.status(), statusCode, "Status code should be " + statusCode);
     }
 */
    @Then("the response status code should be {string}")
    public void the_response_status_code_should_be(String statusCode) {
        System.out.println("response.status() ::" + response.status());
        int status = response.status();
        //assertEquals(response.status(), Integer.parseInt(statusCode), "Status code should be " + statusCode);
        assert status == Integer.parseInt(statusCode);
    }

    @Then("the response should contain status {string}")
    public void theResponseShouldContainStatus(String statusText) {
        //String responseReturned = response.text();
        byte[] body = response.body();
        String responseBody = new String(body);
        Gson gson = new Gson();
        Map<String, Object> jsonResponse = gson.fromJson(responseBody, Map.class);
        String status = (String) jsonResponse.get("status");
        System.out.println("GSON ::" + status);
        assert status.equals(statusText);
    }

    @Then("the response should contain image URLs for breed {string}")
    public void theResponseShouldContainImageURLsForBreed(String breed) {
        //String responseBody = response.text();
        byte[] body = response.body();
        String responseBody = new String(body);
        Gson gson = new Gson();
        Map<String, Object> jsonResponse = gson.fromJson(responseBody, Map.class);
        //List
        List<String> subBreedUrls = (List<String>) jsonResponse.get("message");
        System.out.println("subBreedUrls :::" + subBreedUrls.size());

        //Set
        Set<String> subBreedUrlsFilterSet = subBreedUrls.stream().filter(b -> b!=null).collect(Collectors.toSet());
        System.out.println("subBreedUrlsFilterSet"+subBreedUrlsFilterSet.size());

        // To check if duplicate images
        assert subBreedUrls.size() == subBreedUrlsFilterSet.size();
        for (String imageUrls : subBreedUrls) {
            System.out.println("Message URLS :::" + imageUrls);
            String[] split = imageUrls.split("/");
            for (int i = 0; i < split.length; i++) {
                assert split[4].equals(breed);
            }

        }

    }

    @After
    public void tearDown() {
        request.dispose();
        playwright.close();
    }

}

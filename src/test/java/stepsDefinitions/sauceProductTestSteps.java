package stepsDefinitions;

import com.microsoft.playwright.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertTrue;

public class sauceProductTestSteps {
    private Playwright playwright;
    private Browser browser;
    private Page page;

    List<ElementHandle> imageElements;
    String src;

    @Given("I navigate to the saucedemo login page")
    public void iNavigateToTheSaucedemoLoginPage() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
        page.navigate("https://www.saucedemo.com/");
    }

    @When("I enter the username {string}")
    public void iEnterTheUsername(String username) {
        page.locator("[data-test='username']").click();
        page.locator("[data-test='username']").fill(username);
    }

    @When("I enter the password {string}")
    public void iEnterThePassword(String password) {
        page.locator("[data-test='password']").click();
        page.locator("[data-test='password']").fill(password);
    }

    @When("I click the login button")
    public void iClickTheLoginButton() {
        page.locator("[data-test='login-button']").click();
    }

    @When("I should see the product page with below items")
    public void iShouldSeeTheProductPageWithBelowItems(DataTable itemProducts) {

        List<String> product = itemProducts.asList(String.class);

            for (String item : product) {
                // assertTrue(page.locator("a img[alt='Sauce Labs Backpack']").isVisible());
                String list = String.format("a img[alt='%s']", item);
                // HashSet to  duplicate names
                page.waitForTimeout(1000); // Wait for 1 seconds

                assertTrue(page.locator(list).isVisible());

            }

        }
    @Then("I Validate all displayed images are different")
    public void iValidateAllDisplayedImagesAreDifferent() {
         imageElements = page.querySelectorAll("a img");
        Set<String> imageSet = new HashSet<>();
        for (ElementHandle element : imageElements) {
             src = element.getAttribute("alt");
            if (src != null) {
                imageSet.add(src);
            }
        }
        Assert.assertEquals(imageSet.size(), imageElements.size(), "All images displayed are unique.");
    }

    @Then("I navigated to inventory item page")
    public void INavigatedToInventoryItemPage()
    {
        // Wait for navigation to complete and assert
        page.getByText("Back to products").isVisible();
        Assert.assertTrue(page.getByText("Back to products").isVisible());
        //String expectedUrl = "https://www.saucedemo.com/inventory-item";
       // String actualUrl = page.url();
       // Assert.assertEquals(actualUrl, expectedUrl, "User should be navigated to the new URL.");


    }

    @When("I click on the Sauce Products")
    public void iClickOnTheSauceProducts(List<String>  itemProducts) throws InterruptedException {

        for (String item : itemProducts) {
            String list = String.format("a img[alt='%s']", item);
             for(ElementHandle e : imageElements) {
                 if(e.getAttribute("alt").equalsIgnoreCase(list))
                 assertTrue(page.locator(list).isVisible());
                 page.locator(list).click();
             break;
             }
           break;
        }

    }

    @Then("I should see the Same Sauce Product selected on Product Page")
    public void iShouldSeeTheSameSauceProductSelectedOnProductPage(List<String> validateProduct) {

        for (ElementHandle element : imageElements) {
            src = element.getAttribute("alt");
            if (src .equalsIgnoreCase(validateProduct.get(0).toString())){
                System.out.println(("Same Image disp"+src));
                break;

            }
        }

        browser.close();
        playwright.close();
    }

    public static class MyStepdefs {

        @Given("I send a get request to {string}")
        public void iSendAGetRequestTo(String arg0) {
        }
    }
}

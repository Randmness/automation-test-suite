package computers;

import computers.support.Button;
import computers.support.Computer;
import computers.support.Constants;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestSteps {
    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver",
                Paths.get("src/test/resources/drivers/mac/chromedriver").toString());

        if (driver == null) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            driver = new ChromeDriver(options);
        }

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @After
    public void tearDown(Scenario scenario) {
        //TODO attempt cleanup of entries
        if (driver!=null) {
            driver.close();
            driver.quit();
        }
    }

    @DataTableType(replaceWithEmptyString = "[blank]")
    public Computer authorEntry(Map<String, String> entry) {
        return new Computer(
                entry.get("computerName"),
                entry.get("introduced"),
                entry.get("discontinued"),
                entry.get("company"));
    }

    @Given("Navigate to page {string}")
    public void navigateToPage(String page) {
        driver.navigate().to(page);
    }

    @Then("All pages going forward will show at most {int} entries")
    public void allPagesGoingForwardResultTablesShowAtMost(int expectedCount) {
        allPagesInDirectionResultTablesShowAtMost(expectedCount, Button.NEXT);
    }

    @Then("All pages going back will show at most {int} entries.")
    public void allPagesGoingBackResultTablesShowAtMost(int expectedCount) {
        allPagesInDirectionResultTablesShowAtMost(expectedCount, Button.PREVIOUS);
    }

    private void allPagesInDirectionResultTablesShowAtMost(int expectedCount, Button direction) {
        resultsTableShowsAtMost(expectedCount);
        boolean isPresent = driver.findElements(direction.getMatch()).size() > 0;
        while (isPresent) {
            driver.findElement(direction.getMatch()).click();
            resultsTableShowsAtMost(expectedCount);
            isPresent = driver.findElements(direction.getMatch()).size() > 0;
        }
    }

    private void resultsTableShowsAtMost(int expectedCount) {
        assertThat("Expected at most " + expectedCount + " entries",
                expectedCount >= driver.findElements(By.xpath(Constants.RESULT_ROW_XPATH)).size());
    }

    @Given("Computer entry already exists")
    public void computerEntryAlreadyExists(List<Computer> computers) throws Exception {
        for (Computer computer: computers) {
            navigateToPage(Constants.NEW_COMPUTER_URL);
            computerEntryFieldsAreEntered(computer);
            driver.findElement(Button.SAVE_THIS_COMPUTER.getMatch()).click();
        }
    }

    @When("Computer entry fields are entered")
    public void computerEntryFieldsAreEntered(List<Computer> computers) {
        computerEntryFieldsAreEntered(computers.get(0));
    }

    private void computerEntryFieldsAreEntered(Computer computer) {
        setTextFields(Constants.COMPUTER_NAME_FIELD_ID, computer.getComputerName());
        setTextFields(Constants.INTRODUCED_NAME_FIELD_ID, computer.getIntroduced());
        setTextFields(Constants.DISCONTINUED_NAME_FIELD_ID, computer.getDiscontinuedDate());
        new Select(driver.findElement(By.xpath(Constants.COMPANY_FIELD_XPATH)))
            .selectByVisibleText(computer.getCompany());
    }

    private void setTextFields(String id, String value) {
        driver.findElement(By.id(id)).clear();
        driver.findElement(By.id(id)).sendKeys(value);
    }

    @When("User clicks button with id {string}")
    public void userClicksButtonWithId(String type, String id) {
        driver.findElement(By.id(id)).click();
    }

    @When("User clicks the {string} button")
    public void userClicksButton(String buttonLabel) {
        driver.findElement(Button.retrieveButtonByLabel(buttonLabel).getMatch()).click();
    }

    @When("User enters filter criteria {string}")
    public void userEntersFilterCriteria(String filter) {
        driver.findElement(By.id(Constants.SEARCH_FIELD_ID)).sendKeys(filter);
    }

    @When("User clicks the {string} entry")
    public void userClicksFeatureEntry(String featureName) {
        driver.findElement(By.xpath(Constants.COMPUTER_NAME_RESULT_XPATH)).click();
    }

    @Then("Application shows {string}")
    public void applicationShowsCurrentEntries(String message) {
        assertThat(driver.findElement(By.xpath(Constants.PAGE_HEADER_XPATH)).getText(),
                endsWith(message));
    }

    @Then("User will be taken to the root page")
    public void applicationShowsRootPage() {
        assertThat(driver.getCurrentUrl(), endsWith("/computers"));
    }

    @Then("Message will appear {string}.")
    public void deletedMessageWillAppear(String message) {
        assertThat(driver.findElement(By.xpath(Constants.MESSAGE_XPATH)).getText(),
                startsWith(message));
    }

    @Then("Results table should show matching computer entry.")
    public void resultsTableShouldShowMatchingComputerEntry(List<Computer> computers) {
        Computer computer = computers.get(0);
        assertNotEquals(driver.findElement(By.xpath(Constants.PAGE_HEADER_XPATH)).getText(),
                "No computers found");
        assertEquals(computer.getComputerName(),
                driver.findElement(By.xpath(Constants.COMPUTER_NAME_RESULT_XPATH)).getText());
        assertEquals(computer.getIntroduced(),
                driver.findElement(By.xpath(Constants.INTRODUCED_RESULT_XPATH)).getText());
        assertEquals(computer.getDiscontinuedDate(),
                driver.findElement(By.xpath(Constants.DISCONTINUED_RESULT_XPATH)).getText());
        assertEquals(computer.getCompany(),
                driver.findElement(By.xpath(Constants.COMPANY_SELECT_RESULT_XPATH)).getText());
    }

    @Then("User will see elements marked for invalid data.")
    public void userWillSeeInvalidDataErrors() {
        assertEquals(Constants.CLEARFIX_ERROR,
                driver.findElement(By.xpath(Constants.COMPUTER_NAME_FIELD_XPATH)).getAttribute(Constants.CLASS_ATTR));
        assertEquals(Constants.CLEARFIX_ERROR,
                driver.findElement(By.xpath(Constants.INTRODUCED_FIELD_XPATH)).getAttribute(Constants.CLASS_ATTR));
        assertEquals(Constants.CLEARFIX_ERROR,
                driver.findElement(By.xpath(Constants.DISCONTINUED_FIELD_XPATH)).getAttribute(Constants.CLASS_ATTR));
    }
}

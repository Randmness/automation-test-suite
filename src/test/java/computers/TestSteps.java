package computers;

import computers.support.Computer;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
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
    public void tearDown() {
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

    @Given("Computer entry already exists")
    public void computerEntryAlreadyExists(List<Computer> computers) throws Exception {
        for (Computer computer: computers) {
            navigateToPage("http://computer-database.herokuapp.com/computers/new");
            computerEntryFieldsAreEntered(computer);
            userClicksButton("Save this computer");
        }
    }

    @When("Computer entry fields are entered")
    public void computerEntryFieldsAreEntered(List<Computer> computers) {
        computerEntryFieldsAreEntered(computers.get(0));
    }

    private void computerEntryFieldsAreEntered(Computer computer) {
        setTextFields("name", computer.getComputerName());
        setTextFields("introduced", computer.getIntroduced());
        setTextFields("discontinued", computer.getDiscontinuedDate());
        Select select = new Select(driver.findElement(By.xpath("/html/body/section/form/fieldset/div[4]/div/select")));
        select.selectByVisibleText(computer.getCompany());
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
    public void userClicksButton(String button) {
        switch (button) {
            case "Add a new computer":
                driver.findElement(By.id("add")).click();
                break;
            case "Delete this computer":
                driver.findElement(By.xpath("/html/body/section/form/input")).click();
                break;
            case "Cancel":
                driver.findElement(By.xpath("/html/body/section/form/div/a")).click();
                break;
            case "Filter by name":
                driver.findElement(By.id("searchsubmit")).click();
                break;
            case "Save this computer":
            case "Create this computer":
                driver.findElement(By.xpath("/html/body/section/form/div/input[@type='submit']")).click();
                break;
            default:
                throw new IllegalArgumentException("Unsupported button: "+ button);
        }
    }

    @When("User enters filter criteria {string}")
    public void userEntersFilterCriteria(String filter) {
        driver.findElement(By.id("searchbox")).sendKeys(filter);
    }

    @When("User clicks the {string} entry")
    public void userClicksFeatureEntry(String featureName) {
        driver.findElement(By.xpath("/html/body/section/table/tbody/tr/td[1]")).click();
    }

    @Then("Application shows current entries.")
    public void applicationShowsCurrentEntries() {
        String expectedMessage = " computers found";
        String actualMessage = driver.findElement(By.xpath("/html/body/section/h1")).getText();
        assertThat(actualMessage, endsWith(expectedMessage));
    }

    @Then("User will be taken to the root page")
    public void applicationShowsRootPage() {
        assertThat(driver.getCurrentUrl(), endsWith("/computers"));
    }

    @Then("Message will appear {string}.")
    public void deletedMessageWillAppear(String message) {
        assertThat(driver.findElement(By.xpath("/html/body/section/div[1]")).getText(),
                startsWith(message));
    }

    @Then("Results table should show matching computer entry.")
    public void resultsTableShouldShowMatchingComputerEntry(List<Computer> computers) {
        Computer computer = computers.get(0);
        String expectedMessage = "No computers found";
        String actualMessage = driver.findElement(By.xpath("/html/body/section/h1")).getText();
        assertNotEquals(actualMessage, expectedMessage);
        assertEquals(computer.getComputerName(),
                driver.findElement(By.xpath("/html/body/section/table/tbody/tr/td[1]/a")).getText());
        assertEquals(computer.getIntroduced(),
                driver.findElement(By.xpath("/html/body/section/table/tbody/tr/td[2]")).getText());
        assertEquals(computer.getDiscontinuedDate(),
                driver.findElement(By.xpath("/html/body/section/table/tbody/tr/td[3]")).getText());
        assertEquals(computer.getCompany(),
                driver.findElement(By.xpath("/html/body/section/table/tbody/tr/td[4]")).getText());
    }

    @Then("User will see elements marked for invalid data.")
    public void userWillSeeInvalidDataErrors() {

        assertEquals("clearfix error",
                driver.findElement(By.xpath("/html/body/section/form/fieldset/div[1]")).getAttribute("class"));
        assertEquals("clearfix error",
                driver.findElement(By.xpath("/html/body/section/form/fieldset/div[2]")).getAttribute("class"));
        assertEquals("clearfix error",
                driver.findElement(By.xpath("/html/body/section/form/fieldset/div[3]")).getAttribute("class"));
    }
}

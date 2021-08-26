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

    @Given("Navigate to Computers database")
    public void navigateToComputersDatabase() {
        driver.navigate().to("http://computer-database.herokuapp.com/computers");
    }

    @Given("Navigate to add a computer")
    public void navigateToAddComputer() {
        driver.navigate().to("http://computer-database.herokuapp.com/computers/new");
    }

    @Given("Computer entry already exists")
    public void computerEntryAlreadyExists(List<Computer> computers) {
        for (Computer computer: computers) {
            navigateToAddComputer();
            computerEntryFieldsAreEntered(computer);
            userClicksCreateThisComputerButton();
        }
    }

    @When("Computer entry fields are entered")
    public void computerEntryFieldsAreEntered(List<Computer> computers) {
        computerEntryFieldsAreEntered(computers.get(0));
    }

    public void computerEntryFieldsAreEntered(Computer computer) {
        driver.findElement(By.id("name")).sendKeys(computer.getComputerName());
        driver.findElement(By.id("introduced")).sendKeys(computer.getIntroduced());
        driver.findElement(By.id("discontinued")).sendKeys(computer.getDiscontinuedDate());
        Select select = new Select(driver.findElement(By.xpath("/html/body/section/form/fieldset/div[4]/div/select")));
        select.selectByVisibleText(computer.getCompany());
    }

    @When("User clicks Create this Computer button")
    public void userClicksCreateThisComputerButton() {
        driver.findElement(By.xpath("/html/body/section/form/div/input")).click();
    }

    @When("User clicks button with id {string}")
    public void userClicksButtonWithId(String id) {
        driver.findElement(By.id(id)).click();
    }

    @When("User clicks the Delete this Computer button")
    public void userClicksDeleteThisComputerButton() {
        driver.findElement(By.xpath("/html/body/section/form/input")).click();
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

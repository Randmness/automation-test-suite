package computers;

import computers.support.Computer;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

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

    @DataTableType
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
        navigateToAddComputer();
        computerEntryFieldsAreEntered(computers);
    }

    @When("Computer entry fields are entered")
    public void computerEntryFieldsAreEntered(List<Computer> computers) {
        Computer computer = computers.get(0);
        driver.findElement(By.id("name")).sendKeys(computer.getComputerName());
        driver.findElement(By.id("introduced")).sendKeys(computer.getIntroduced());
        driver.findElement(By.id("discontinued")).sendKeys(computer.getDiscontinuedDate());
        Select select = new Select(driver.findElement(By.xpath("/html/body/section/form/fieldset/div[4]/div/select")));
        select.selectByVisibleText(computer.getCompany());
        userClicksCreateThisComputerButton();
    }

    @When("User clicks Create this Computer button")
    public void userClicksCreateThisComputerButton() {
        driver.findElement(By.xpath("/html/body/section/form/div/input")).click();
    }

    @Then("Application shows current entries.")
    public void applicationShowsCurrentEntries() {
        String expectedMessage = " computers found";
        String actualMessage = driver.findElement(By.xpath("/html/body/section/h1")).getText();

        assertThat(actualMessage, endsWith(expectedMessage));
    }
}

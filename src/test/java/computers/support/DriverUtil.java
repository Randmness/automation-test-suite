package computers.support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class DriverUtil {

    /**
     * Initializes driver in with configurable headless mode
     *
     * @return  Webdriver
     */
    public static WebDriver initializeDriver() {
        ChromeOptions options = new ChromeOptions();
        if (Boolean.parseBoolean(System.getProperty("headless", "true"))) {
            options.addArguments("--headless");
        };

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Constants.TIMEOUT_SECONDS, TimeUnit.SECONDS);
        return driver;
    }

    /**
     * Closes initialized driver
     *
     * @param driver
     */
    public static void closeDriver(WebDriver driver) {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

    private DriverUtil() {};
}

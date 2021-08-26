package computers.support;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestRunFinished;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.logging.Logger;

public class TestEventHandlerPlugin implements ConcurrentEventListener {
    private static final String COMPANY_NAME_PREFIX = "TEST-Feature-";
    private static Logger LOG = Logger.getLogger(TestEventHandlerPlugin.class.getName());

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestRunFinished.class, teardown);
    }

    /**
     * Event handler that is triggered after the tests run to clean up test data.
     */
    private EventHandler<TestRunFinished> teardown = event -> {
        LOG.info("Attempting to cleanup test entries companies starting with: " + COMPANY_NAME_PREFIX);
        WebDriver driver = null;

        try {
            driver = DriverUtil.initializeDriver();

            driver.navigate().to(Constants.TEST_COMPUTER_FILTER);
            boolean isPresent = driver.findElements(By.xpath(Constants.COMPUTER_NAME_RESULT_XPATH)).size() > 0;
            while (isPresent) {
                driver.findElement(By.xpath(Constants.COMPUTER_NAME_RESULT_XPATH)).click();
                driver.findElement(Button.DELETE_COMPUTER.getMatch()).click();
                driver.navigate().to(Constants.TEST_COMPUTER_FILTER);
                isPresent = driver.findElements(By.xpath(Constants.COMPUTER_NAME_RESULT_XPATH)).size() > 0;
            }

            LOG.info("Successfully cleaned up test entries.");
        } catch (Exception e) {
            LOG.severe("Issue occurred attempting to delete test entries: " + e.getMessage());
        } finally {
            DriverUtil.closeDriver(driver);
        }
    };
}


package test;

import org.example.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OpenLinkTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Initialize the WebDriver (adjust based on your WebDriver setup)
        driver = WebDriverManager.getDriver();
    }

    @AfterEach
    public void tearDown() {
        // Quit the WebDriver after each test
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testOpenLink() {
        // Open the link
        driver.get("https://developer.mozilla.org/en-US/");
        System.out.println("Opened the link successfully.");

        // Verify that the page title contains "MDN Web Docs"
        String expectedTitle = "MDN Web Docs";
        String actualTitle = driver.getTitle();
        assertEquals(expectedTitle, actualTitle, "The page title does not match the expected title.");
    }
}


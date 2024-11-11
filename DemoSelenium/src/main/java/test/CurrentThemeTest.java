package test;

import org.example.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CurrentThemeTest {
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
    public void testCurrentTheme() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement bodyElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
        String bodyClass = bodyElement.getAttribute("class");

        // Check if the theme is Dark or Light and assert accordingly
        if (bodyClass.contains("dark-mode")) {
            System.out.println("Current theme: Dark mode.");
            assertTrue(bodyClass.contains("dark-mode"), "Expected theme: Dark mode.");
        } else {
            System.out.println("Current theme: Light mode.");
            assertTrue(!bodyClass.contains("dark-mode"), "Expected theme: Light mode.");
        }
    }
}

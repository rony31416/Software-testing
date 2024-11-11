//package test;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//
//public class ToggleThemeTest {
//    private WebDriver driver;
//
//    public ToggleThemeTest(WebDriver driver) {
//        this.driver = driver;
//    }
//
//    public void execute() {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//
//        // Locate and click the theme toggle button
//        WebElement themeToggleButton = wait.until(ExpectedConditions.elementToBeClickable(
//                By.cssSelector(".theme-switcher-menu .theme-switcher-menu")));
//        themeToggleButton.click();
//
//        // Wait for and select the "Dark" theme option
//        WebElement darkThemeOption = wait.until(ExpectedConditions.elementToBeClickable(
//                By.cssSelector("ul.themes-menu button .icon-theme-dark")));
//        darkThemeOption.click();
//        System.out.println("Changed to Dark mode.");
//
//        // Optional: wait briefly to let the theme apply
//        try {
//            Thread.sleep(3000); // Adjust sleep time as necessary
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}


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

public class ToggleThemeTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Initialize the WebDriver (adjust based on your WebDriver setup)
        driver = WebDriverManager.getDriver();
        driver.get("https://developer.mozilla.org/en-US/"); // Navigate to your website before the test
    }

    @AfterEach
    public void tearDown() {
        // Quit the WebDriver after each test
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testToggleToDarkMode() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Locate and click the theme toggle button
        WebElement themeToggleButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".theme-switcher-menu .theme-switcher-menu")));
        themeToggleButton.click();

        // Wait for and select the "Dark" theme option
        WebElement darkThemeOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("ul.themes-menu button .icon-theme-dark")));
        darkThemeOption.click();
        System.out.println("Changed to Dark mode.");

        // Optional: wait briefly to let the theme apply
        try {
            Thread.sleep(10000); // Adjust sleep time as necessary
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify that the theme has been set to Dark mode by checking a class or attribute
        WebElement bodyElement = driver.findElement(By.tagName("body"));
        String bodyClass = bodyElement.getAttribute("class");

        assertTrue(bodyClass.contains("dark-mode"), "Expected theme to be Dark mode.");
    }
}

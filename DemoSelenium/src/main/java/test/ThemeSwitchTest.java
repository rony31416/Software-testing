package test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.example.WebDriverManager;
import java.time.Duration;

public class ThemeSwitchTest{

    private static final String URL = "https://developer.mozilla.org/en-US/docs/Web/WebDriver";

    public static void main(String[] args) {
        ThemeSwitchTest test = new ThemeSwitchTest();
        test.runThemeSwitchTests();
    }

    private void runThemeSwitchTests() {
        // Create a WebDriver instance
        WebDriver driver = WebDriverManager.getDriver();

        // List of theme transitions to test
        String[][] themeTransitions = {
                {"os-default", "dark"}, // Test case 1: OS Default to Dark
                {"dark", "os-default"}, // Test case 2: Dark to OS Default
                {"os-default", "light"}, // Test case 3: OS Default to Light
                {"light", "os-default"}, // Test case 4: Light to OS Default
                {"dark", "light"}, // Test case 5: Dark to Light
                {"light", "dark"} // Test case 6: Light to Dark
        };

        // Execute each transition test
        for (String[] transition : themeTransitions) {
            runThemeSwitchTest(driver, transition[0], transition[1]);
        }

        // Quit the WebDriver after tests are done
        WebDriverManager.quitDriver();
    }

    private void runThemeSwitchTest(WebDriver driver, String fromTheme, String toTheme) {
        // Navigate to the URL
        driver.get(URL);

        // Switch to the initial theme
        switchToTheme(driver, fromTheme);

        // Show notification for the initial theme
        showNotification(driver, fromTheme);

        // Wait and verify the initial theme is applied
        verifyTheme(driver, fromTheme);

        // Switch to the target theme
        switchToTheme(driver, toTheme);

        // Show notification for the target theme
        showNotification(driver, toTheme);

        // Wait and verify the target theme is applied
        verifyTheme(driver, toTheme);

        System.out.println("Test case: Switched from " + fromTheme + " to " + toTheme + " passed.\n");
    }

    private void switchToTheme(WebDriver driver, String theme) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement themeSwitcher = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".theme-switcher-menu")));
        themeSwitcher.click();

        String themeSelector;
        switch (theme) {
            case "light":
                themeSelector = ".icon-theme-light";
                break;
            case "dark":
                themeSelector = ".icon-theme-dark";
                break;
            case "os-default":
                themeSelector = ".icon-theme-os-default";
                break;
            default:
                throw new IllegalArgumentException("Invalid theme: " + theme);
        }

        WebElement themeButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(themeSelector)));
        themeButton.click();

        // Pause to let theme apply
        try {
            Thread.sleep(4000); // Increased delay to make each change more noticeable
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void showNotification(WebDriver driver, String theme) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // JavaScript to create a notification banner
        String script = "let notification = document.createElement('div');" +
                "notification.innerText = 'Theme changed to: " + theme + "';" +
                "notification.style.position = 'fixed';" +
                "notification.style.top = '0';" +
                "notification.style.width = '100%';" +
                "notification.style.backgroundColor = 'green';" +
                "notification.style.color = 'white';" +
                "notification.style.padding = '10px';" +
                "notification.style.textAlign = 'center';" +
                "notification.style.zIndex = '10000';" +
                "document.body.appendChild(notification);" +
                "setTimeout(() => notification.remove(), 3000);"; // Remove after 3 seconds

        // Execute the JavaScript in the browser
        js.executeScript(script);

        // Console log for debugging
        System.out.println("Notification shown for theme: " + theme);
    }

    private void verifyTheme(WebDriver driver, String expectedTheme) {
        String currentClass = driver.findElement(By.tagName("html")).getAttribute("class");
        System.out.println("Current class: " + currentClass); // Debug log to see the current class
        assert currentClass.contains(expectedTheme) : expectedTheme + " theme was not applied. Current class: " + currentClass;
        System.out.println(expectedTheme + " theme applied successfully.");
    }
}

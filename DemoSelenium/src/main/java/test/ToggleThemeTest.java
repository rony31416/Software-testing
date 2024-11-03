package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ToggleThemeTest {
    private WebDriver driver;

    public ToggleThemeTest(WebDriver driver) {
        this.driver = driver;
    }

    public void execute() {
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
            Thread.sleep(3000); // Adjust sleep time as necessary
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

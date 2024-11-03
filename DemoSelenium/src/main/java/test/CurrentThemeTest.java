package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CurrentThemeTest {
    private WebDriver driver;

    public CurrentThemeTest(WebDriver driver) {
        this.driver = driver;
    }

    public void execute() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement bodyElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
        String bodyClass = bodyElement.getAttribute("class");

        if (bodyClass.contains("dark-mode")) {
            System.out.println("Current theme: Dark mode.");
        } else {
            System.out.println("Current theme: Light mode.");
        }
    }
}

package test;

import org.openqa.selenium.WebDriver;

public class OpenLinkTest {
    private WebDriver driver;

    public OpenLinkTest(WebDriver driver) {
        this.driver = driver;
    }

    public void execute() {
        driver.get("https://developer.mozilla.org/en-US/");
        System.out.println("Opened the link successfully.");
    }
}

package org.example;

import org.openqa.selenium.WebDriver;
import test.CurrentThemeTest;
import test.OpenLinkTest;
import test.ToggleThemeTest;
import test.VerifyThemeChangeTest;

public class Main {
    public static void main(String[] args) {
        WebDriver driver = WebDriverManager.getDriver();

        try {
            new OpenLinkTest(driver).execute(); // Test 1: Open link
           new CurrentThemeTest(driver).execute(); // Test 2: Check current theme
          new ToggleThemeTest(driver).execute(); // Test 3: Change theme to Dark
          new VerifyThemeChangeTest(driver).execute(); // Test 4: Verify theme change

            // Additional tests can be added here

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            WebDriverManager.quitDriver();
        }
    }
}

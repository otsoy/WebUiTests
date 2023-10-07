package org.otsoi.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.otsoi.enums.DriverType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Optional;


public class WebDriverManager {
    private WebDriver driver;
    private static DriverType driverType;

    public WebDriverManager() {
        driverType = DriverType.valueOf(Optional.ofNullable(System.getProperty("driverType")).orElse(DriverType.CHROME.toString()));
    }

    public WebDriver getDriver() {
        if(driver == null) driver = createDriver();
        return driver;
    }

    private WebDriver createDriver() {
        switch (driverType) {
            case FIREFOX -> driver = new FirefoxDriver();
            case CHROME -> {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");
                driver = new ChromeDriver();
            }
        }
        return driver;
    }

    public void closeDriver() {
        driver.close();
        driver.quit();
    }
}
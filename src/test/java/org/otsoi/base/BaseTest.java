package org.otsoi.base;

import com.github.javafaker.Faker;
import com.google.common.io.Files;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.otsoi.pageObjects.pages.MainPage;
import org.otsoi.readers.ConfigReader;
import org.otsoi.webdriver.WebDriverManager;

import java.io.File;
import java.io.IOException;

public abstract class BaseTest {
    protected WebDriver driver;
    protected Faker faker;
    private final WebDriverManager webDriverManager;
    private final ConfigReader configReader;


    public BaseTest(){
        webDriverManager = new WebDriverManager();
        configReader = new ConfigReader();
        faker = new Faker();
    }

    @BeforeEach
    public void initEach(){
        driver = webDriverManager.getDriver();
    }

    @AfterEach
    public void afterEach() {
        takeScreenshot();
        if(null != driver) {
            this.webDriverManager.closeDriver();
        }

    }

    @Attachment(type = "image/png")
    public byte[] takeScreenshot(){
        try {
            File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            return Files.toByteArray(screen);
        } catch (IOException e) {
            return null;
        }
    }
    @Step
    public MainPage openMainPage(){
        String url =this.configReader.getApplicationUrl();
        this.driver.get(url);
        return new MainPage(driver);
    }
}

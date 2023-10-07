package org.otsoi.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.otsoi.readers.ConfigReader;

public abstract class BasePageObject {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected ConfigReader configReader;

    public BasePageObject(WebDriver driver) {
        this.driver = driver;
        configReader = new ConfigReader();
        wait = new WebDriverWait(driver, configReader.getTimeout(), configReader.getPolling());
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, (int)configReader.getTimeout().toSeconds()), this);
    }
}

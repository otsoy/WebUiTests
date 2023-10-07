package org.otsoi.base;

import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.otsoi.readers.ConfigReader;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class BasePage extends BasePageObject{
    private String url;
    public BasePage(WebDriver driver, String url) {
        super(driver);
        this.url = url;
    }
    public String getUrl(){
        return URI.create(this.configReader.getApplicationUrl())
                .resolve(url).toString();
    }
    @Step
    public BasePage waitPageLoad(){
       this.wait.until(driver -> driver.getCurrentUrl().contains(getUrl()));
       return this;
    }
}

package org.otsoi.pageObjects.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.otsoi.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.otsoi.entities.Result;
import org.otsoi.enums.InputFieldActions;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MainPage extends BasePage {
    @FindBy(xpath= ".//*[contains(@id, 'min-span')]//input")
    private WebElement minValue;
    @FindBy(xpath= ".//*[contains(@id, 'max-span')]//input")
    private WebElement maxValue;
    @FindBy(xpath = ".//*[@value='Generate']")
    private WebElement generateBtn;
    @FindBy(xpath = ".//*[contains(@id, 'result')]")
    private WebElement resultError;
    private final By resultElementLoc =By.xpath(".//*[contains(@id, 'result')]//span");

    public MainPage(WebDriver driver) {
        super(driver, "");
        this.driver.switchTo().frame(this.driver.findElement(By.xpath(".//*[@id='homepage-generator']/iframe")));
    }

    @Step
    public void setMinValue(String value) {
        this.minValue.clear();
        this.minValue.sendKeys(value);
    }
    @Step
    public void pressKeyInMin(InputFieldActions action) {
        switch(action){
            case UP ->  this.minValue.sendKeys(Keys.UP);
            case DOWN ->  this.minValue.sendKeys(Keys.DOWN);
            case ENTER ->  this.minValue.sendKeys(Keys.ENTER);

        }
    }
    @Step
    public void setMaxValue(String value) {
        this.maxValue.clear();
        this.maxValue.sendKeys(value);
    }
    @Step
    public void pressKeyInMax(InputFieldActions action) {
        switch(action){
            case UP ->  this.maxValue.sendKeys(Keys.UP);
            case DOWN ->  this.maxValue.sendKeys(Keys.DOWN);
            case ENTER ->  this.minValue.sendKeys(Keys.ENTER);
        }
    }

    public String getInputMaxValue() {
        return this.maxValue.getText().isEmpty() ? this.maxValue.getAttribute("value") : this.maxValue.getText() ;
    }

    public String getInputMinValue() {
        return this.minValue.getText().isEmpty() ? this.minValue.getAttribute("value") : this.minValue.getText() ;
    }
    @Step
    public void pressGenerate() {
        this.generateBtn.click();
    }

    public Result getResult(){
        this.wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(resultElementLoc));
        List<WebElement> resultRows =  this.driver.findElements(resultElementLoc);

        int value = Integer.parseInt(resultRows.get(0).getText());
        String[] metaData = resultRows.get(1).getText().split("\n");
        int min = parseMinFromResult(metaData[0]);
        int max = parseMaxFromResult(metaData[0]);
        ZonedDateTime date = parseDateFromResult(metaData[1]);

        return new Result( value, min, max, date);
    }
    public String getErrorText() {
        this.wait.until((ExpectedCondition<Boolean>) driver -> !this.resultError.getText().isEmpty());
        return this.resultError.getText();
    }
    private int parseMinFromResult(String result){
        String min = result.split("Min: ")[1].split(", Max:")[0];
        return Integer.parseInt(min);
    }

    private int parseMaxFromResult(String result){
        String max = result.split("Max: ")[1];
        return Integer.parseInt(max);
    }
    private ZonedDateTime parseDateFromResult(String result){
        final DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        return ZonedDateTime.parse(result, formatter);
    }
}

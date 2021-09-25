package com.alex.study.tests;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class BaseClass {

    protected WebDriver webDriver;
    protected WebDriverWait webDriverWait;
    protected Actions actions;

    @BeforeEach
    protected void before() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-notifications");
        webDriver = new ChromeDriver(chromeOptions);
        webDriver.manage().window().maximize();
        webDriverWait = new WebDriverWait(webDriver, 30, 1000);
        actions = new Actions(webDriver);
        webDriver.get("https://www.rgs.ru/");
    }

    @AfterEach
    protected void after() {
        webDriver.quit();
    }

    protected void switchToNewWindow(By byPath) {
        final Set<String> oldWindowsSet = webDriver.getWindowHandles();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(byPath)).click();

        String newWindow = (new WebDriverWait(webDriver, 10))
                .until(new ExpectedCondition<>() {
                    @NullableDecl
                    @Override
                    public String apply(@NullableDecl WebDriver driver) {
                        Assertions.assertNotNull(driver, "Driver equals null");
                        Set<String> newWindowsSet = driver.getWindowHandles();
                        newWindowsSet.removeAll(oldWindowsSet);
                        return newWindowsSet.size() > 0 ? newWindowsSet.iterator().next() : null;
                    }
                });
        webDriver.switchTo().window(newWindow);
    }

    protected void scrollToElementJs(WebElement element) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void waitUtilElementToBeClickable(WebElement element) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void waitUtilElementToBeVisible(By locator) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitUtilElementToBeVisible(WebElement element) {
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void fillInputField(WebElement element, String value) {
        scrollToElementJs(element);
        waitUtilElementToBeClickable(element);
        element.click();
        element.clear();
        element.sendKeys(value);
        boolean checkFlag = webDriverWait.until(ExpectedConditions.attributeContains(element, "value", value));
        Assertions.assertTrue(checkFlag, "Поле было заполнено некорректно");
    }

    protected void checkErrorMessageAtField(WebElement element, String errorMessage) {
        element = element.findElement(By.xpath("./..//span"));
        Assertions.assertEquals(errorMessage, element.getText(), "Проверка ошибки у поля не была пройдена");
    }

    protected void closeFrame(By byFrame,By byButtonCloseFrame) {
        if (checkPresentElement(byFrame) != null) {
            WebElement frame = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(byFrame));
            webDriver.switchTo().frame(frame);
            WebElement buttonFrameStockClose = webDriver.findElement(byButtonCloseFrame);
            actions.moveToElement(buttonFrameStockClose).click().build().perform();
            webDriver.switchTo().defaultContent();
        }
    }

    protected WebElement checkPresentElement(By byFrame) {
        webDriverWait.withTimeout(Duration.ofSeconds(5)).pollingEvery(Duration.ofMillis(100));
        WebElement element = null;

        try {
            element = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(byFrame));
        } catch (TimeoutException ignore) {
        }

        return element;
    }


}

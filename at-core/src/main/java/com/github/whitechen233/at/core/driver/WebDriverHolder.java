package com.github.whitechen233.at.core.driver;

import java.time.Duration;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class WebDriverHolder {

    private static final WebDriverHolder HOLDER = new WebDriverHolder();

    private final ThreadLocal<WebDriver> driverThreadLocal;

    private Wait<WebDriver> wait;

    private WebDriverHolder() {
        driverThreadLocal = new ThreadLocal<>();
    }

    public static WebDriverHolder getInstance() {
        return HOLDER;
    }

    public WebDriver getWebDriver() {
        return Optional.ofNullable(driverThreadLocal.get()).orElseGet(() -> {
            WebDriver webDriver = WebDriverCreator.createChromeDriver();
            driverThreadLocal.set(webDriver);
            wait = new FluentWait<>(WebDriverHolder.getInstance().getWebDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class);
            return webDriver;
        });
    }

    public WebElement findElement(String element) {
        return wait.until(driver -> driver.findElement(By.cssSelector(element)));
    }

    public void open(String url) {
        getWebDriver().get(url);
    }

    public void enterValue(String ele, String value) {
        findElement(ele).sendKeys(value);
    }

    public void click(String ele) {
        findElement(ele).click();
    }

    public void quit() {
        getWebDriver().quit();
        driverThreadLocal.remove();
    }
}

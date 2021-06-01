package com.github.whitechen233.at.core.driver;

import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WebDriverHolder {

    private final ThreadLocal<WebDriver> driverThreadLocal;

    private static final  WebDriverHolder HOLDER = new WebDriverHolder();

    private WebDriverHolder() {
        driverThreadLocal = new ThreadLocal<>();
    }

    public static WebDriverHolder getInstance() {
        return HOLDER;
    }

    public WebDriver getWebDriver() {
        return Optional.ofNullable(this.driverThreadLocal.get()).orElseGet(() -> {
            WebDriver webDriver = WebDriverCreator.createChromeDriver();
            this.driverThreadLocal.set(webDriver);
            return webDriver;
        });
    }

    public void open(String url) {
        getWebDriver().get(url);
    }

    public void enterValue(String ele, String value) {
        getWebDriver().findElement(By.cssSelector(ele)).sendKeys(value);
    }

    public void click(String ele) {
        getWebDriver().findElement(By.cssSelector(ele)).click();
    }

    public void quit() {
        getWebDriver().quit();
        this.driverThreadLocal.remove();
    }
}

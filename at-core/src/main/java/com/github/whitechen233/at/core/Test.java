package com.github.whitechen233.at.core;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.whitechen233.at.core.driver.WebDriverHolder;

public class Test {

    public static void main(String[] args) throws Exception {

        WebDriverHolder holder = WebDriverHolder.getInstance();

        holder.open("http://172.29.49.128/afe-web");

        List<WebElement> elements = holder.getWebDriver().findElements(By.className("messager-window"));
        if (!elements.isEmpty()) {
            elements.get(0).getText();
        }

        holder.enterValue("#dps-login-username", "white");
        holder.enterValue("#dps-login-password", "Admin.1324");
        holder.enterValue("#dps-login-captcha", "9999");

        holder.click("#dps-login-submit");

        TimeUnit.SECONDS.sleep(10);
        holder.quit();
    }
}

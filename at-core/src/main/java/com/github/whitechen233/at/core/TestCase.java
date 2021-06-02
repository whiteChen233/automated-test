package com.github.whitechen233.at.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.whitechen233.at.core.driver.WebDriverHolder;
import com.github.whitechen233.at.core.step.OpenPageStep;
import com.github.whitechen233.at.core.step.Step;
import com.github.whitechen233.at.core.step.WaitStep;

import lombok.Getter;

@Getter
public class TestCase {

    private final String name;

    private final List<Step> steps;

    public TestCase(String name) {
        this.name = name;
        steps = new ArrayList<>();
    }

    public TestCase next(Step step) {
        this.steps.add(step);
        return this;
    }

    public TestCase open(String url) {
        return next(new OpenPageStep(url));
    }

    public TestCase wait(long timeout, TimeUnit unit) {
        return next(new WaitStep(timeout, unit));
    }

    public void execute() {
        this.getSteps().forEach(Step::execute);
    }

    public void endTest() {
        WebDriverHolder.getInstance().quit();
    }

    public boolean hasError(String message) {
        List<WebElement> elements = WebDriverHolder.getInstance().getWebDriver()
            .findElements(By.className("messager-window"));
        return elements.stream().anyMatch(i -> i.getText().equals(message));
    }
}

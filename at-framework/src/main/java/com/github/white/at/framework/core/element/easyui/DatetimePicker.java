package com.github.white.at.framework.core.element.easyui;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.ObjIntConsumer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.white.at.framework.core.driver.WebDriverHolder;

public class DatetimePicker {

    private final String element;

    public DatetimePicker(String element) {
        this.element = element;
    }

    public void setDatetime(LocalDateTime ldt) {
        int year = ldt.getYear();
        int month = ldt.getMonthValue();
        int day = ldt.getDayOfMonth();
        int hour = ldt.getHour();
        int minute = ldt.getMinute();

        openPicker();
        flushPicker().findElement(By.cssSelector("table thead tr:first-child th:nth-child(2)")).click();
        flushPicker().findElement(By.cssSelector("table thead tr:first-child th:nth-child(2)")).click();

        boolean exit = true;
        while (exit) {
            List<WebElement> wes = flushPicker().findElements(By.cssSelector("table tr td .year"));
            int min = Integer.parseInt(wes.get(0).getText());
            int max = Integer.parseInt(wes.get(wes.size() - 1).getText());
            if (Math.max(min, year) == Math.min(year, max)) {
                exit = false;
                continue;
            }
            if (max < year) {
                flushPicker().findElement(By.cssSelector("table thead tr th.next")).click();
            }
            if (min > year) {
                flushPicker().findElement(By.cssSelector("table thead tr th.prev")).click();
            }
        }

        ObjIntConsumer<String> fun = (cssSelector, expected) -> flushPicker()
            .findElements(By.cssSelector(cssSelector)).stream()
            .filter(i -> Integer.parseInt(i.getText().replaceAll("[^\\d]", "")) == expected)
            .findFirst().ifPresent(WebElement::click);

        fun.accept("table tbody tr td .year", year);
        fun.accept("table tbody tr td .month", month);
        fun.accept("table tbody tr td.day:not(.old,.new)", day);
        fun.accept("table tbody tr td .hour", hour);
        fun.accept("table tbody tr td .minute", minute);
    }

    private void openPicker() {
        WebDriverHolder.getInstance().findElement(element + " input").click();
    }

    private WebElement flushPicker() {
        return WebDriverHolder.getInstance().findElement(".datetimepicker div[style='display: block;']");
    }

}

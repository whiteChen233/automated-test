package com.github.whitechen233.at.core;

import com.github.whitechen233.at.core.driver.WebDriverHolder;

public class EnterValue implements Step {

    private String element;

    private String value;

    @Override
    public void execute() {
        WebDriverHolder driverHolder = WebDriverHolder.getInstance();
        driverHolder.enterValue(element, value);
    }
}

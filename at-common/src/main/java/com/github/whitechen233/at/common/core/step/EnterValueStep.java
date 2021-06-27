package com.github.whitechen233.at.common.core.step;

import com.github.whitechen233.at.common.core.driver.WebDriverHolder;

public class EnterValueStep implements Step {

    private final String element;

    private final String value;

    public EnterValueStep(String element, String value) {
        this.element = element;
        this.value = value;
    }

    @Override
    public void execute() {
        WebDriverHolder driverHolder = WebDriverHolder.getInstance();
        driverHolder.enterValue(element, value);
    }
}

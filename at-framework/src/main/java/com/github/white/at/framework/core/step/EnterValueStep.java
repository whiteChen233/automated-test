package com.github.white.at.framework.core.step;

import com.github.white.at.framework.core.driver.WebDriverHolder;

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

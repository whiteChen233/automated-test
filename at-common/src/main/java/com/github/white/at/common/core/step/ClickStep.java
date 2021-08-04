package com.github.white.at.common.core.step;

import com.github.white.at.common.core.driver.WebDriverHolder;

public class ClickStep implements Step {

    private final String element;

    public ClickStep(String element) {
        this.element = element;
    }

    @Override
    public void execute() {
        WebDriverHolder.getInstance().click(element);
    }
}

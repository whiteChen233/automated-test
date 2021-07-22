package com.github.whitechen233.at.common.core.step;

import com.github.whitechen233.at.common.core.driver.WebDriverHolder;

public class OpenPageStep implements Step {

    private final String url;

    public OpenPageStep(String url) {
        this.url = url;
    }

    @Override
    public void execute() {
        WebDriverHolder.getInstance().open(url);
    }
}
package com.github.white.at.framework.core.step;

import com.github.white.at.framework.core.driver.WebDriverHolder;

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

package com.github.whitechen233.at.core.driver;

/**
 * The enum Web driver enum.
 */
public enum WebDriverEnum {

    /**
     * Chrome web driver enum.
     */
    CHROME("drivers/win32/chromedriver.exe")

    ;

    private final String driverPath;

    WebDriverEnum(String driverPath) {
        this.driverPath = driverPath;
    }

    public String getDriverPath() {
        return this.driverPath;
    }
}

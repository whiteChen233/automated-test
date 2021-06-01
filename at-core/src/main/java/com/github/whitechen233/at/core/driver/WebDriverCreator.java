package com.github.whitechen233.at.core.driver;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.springframework.core.io.ClassPathResource;

public class WebDriverCreator {

    public static ChromeDriver createChromeDriver() {
        ChromeDriver cd = null;
        try {
            ClassPathResource cpr = new ClassPathResource(WebDriverEnum.CHROME.getDriverPath());

            ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(cpr.getFile())
                .usingAnyFreePort()
                .build();

            LoggingPreferences logPrefs = new LoggingPreferences();
            logPrefs.enable(LogType.PERFORMANCE, Level.ALL);

            ChromeOptions options = new ChromeOptions()
                .addArguments("--disable-gpu")
                .addArguments("blink-settings=imagesEnabled=false")
                .addArguments("--no-sandbox");

            options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

            ChromeDriver.class.getConstructor(ChromeDriverService.class, ChromeOptions.class)
                .newInstance(service, options);
            cd = new ChromeDriver(service, options);
            cd.manage().window().maximize();
            cd.manage().deleteAllCookies();

            cd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        } catch (Exception e) {

        }

        return Optional.ofNullable(cd).orElseGet(() -> {
            ChromeDriver tmp = new ChromeDriver();
            return tmp;
        });
    }
}

package com.github.whitechen233.at.common.core.driver;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
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


        ChromeOptions options = new ChromeOptions()
            .addArguments("--disable-gpu")
            .addArguments("blink-settings=imagesEnabled=false")
            .addArguments("--no-sandbox");

        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

        Function<ChromeOptions, ChromeDriver> fun = ops -> {
            try {
                ClassPathResource cpr = new ClassPathResource(WebDriverEnum.CHROME.getDriverPath());
                ChromeDriverService service = new ChromeDriverService.Builder()
                    .usingDriverExecutable(cpr.getFile())
                    .usingAnyFreePort()
                    .build();

                ChromeDriver cd = new ChromeDriver(service, ops);
                cd.manage().window().maximize();
                cd.manage().deleteAllCookies();

                cd.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

                return cd;
            } catch (Exception e) {

            }
            return null;
        };

        return Optional.ofNullable(fun.apply(options)).orElseGet(() -> {
            System
                .setProperty("webdriver.chrome.driver", "src\\main\\java\\resources\\drivers\\win32\\chromedriver.exe");
            return new ChromeDriver(options);
        });
    }
}

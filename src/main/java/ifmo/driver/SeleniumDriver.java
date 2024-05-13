package ifmo.driver;

import org.openqa.selenium.WebDriver;

import java.util.List;

public abstract class SeleniumDriver {

    protected WebDriver driver;

    public static List<SeleniumDriver> getDrivers() {
//        return List.of(new SeleniumFirefoxDriver());
        return List.of(new SeleniumFirefoxDriver(), new SeleniumChromeDriver());
    }

    public final WebDriver getDriver() {
        return driver;
    }

    public abstract void setup();

    public abstract void close();
}

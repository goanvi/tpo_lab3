package ifmo.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class SeleniumFirefoxDriver extends SeleniumDriver {

//    public static final String FIREFOX_BINARY = "/usr/bin/firefox";

    public SeleniumFirefoxDriver() {
        setup();
    }

    @Override
    public void setup() {
//        var options = new FirefoxOptions();
//        options.setBinary(FIREFOX_BINARY);

        WebDriverManager.firefoxdriver().setup();

//        driver = new FirefoxDriver(options);
        driver = new FirefoxDriver();
        driver.manage().window().setSize(new Dimension(1024, 768));
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Override
    public void close() {
        driver.close();
    }
}

import ifmo.CustomParameterResolver;
import ifmo.driver.SeleniumDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Duration;
import java.util.stream.Stream;

@ExtendWith(CustomParameterResolver.class)
public abstract class PageTestBase {

    static Stream<Arguments> allDrivers() {
        return SeleniumDriver.getDrivers().stream().map(SeleniumDriver::getDriver).map(Arguments::of);
    }

    @BeforeEach
    public void prepareContext(WebDriver driver) {
        driver.get("https://github.com/");
        preparePages(driver);
    }

    protected abstract void preparePages(WebDriver driver);

    @AfterEach
    public void quitDriver(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://github.com/logout");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Sign out']")));
        driver.findElement(By.xpath("//input[@value='Sign out']")).click();
        wait.until(ExpectedConditions.urlToBe("https://github.com/"));
        driver.quit();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("allDrivers")
    @Retention(RetentionPolicy.RUNTIME)
    protected @interface TestWithAllDrivers {
    }


}

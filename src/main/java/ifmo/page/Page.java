package ifmo.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Page {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(getDriver(), Duration.ofSeconds(60));
        PageFactory.initElements(driver, this);
    }

    protected final WebDriver getDriver() {
        return driver;
    }

    protected final void goToUrl(String url) {
        driver.get(url);
    }

    public final String currentUrl() {
        return driver.getCurrentUrl();
    }

    protected final Duration getWaitTimeout() {
        return driver.manage().timeouts().getImplicitWaitTimeout();
    }

    protected final void refreshPage() {
        driver.get(driver.getCurrentUrl());
    }

    protected final Optional<WebElement> findOptionalElement(By by) {
        try {
            return Optional.of(driver.findElement(by));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public final WebDriverWait getWait() {
        return wait;
    }

}

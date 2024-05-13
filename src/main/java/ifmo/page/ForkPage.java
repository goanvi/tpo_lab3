package ifmo.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ForkPage extends Page {
    public static final By FORK_NAME_FIELD_BY = By.xpath("//input[@data-testid='repository-name-input']");
    public static final By AVAILABLE_FORK_NAME_TITLE_BY = By.xpath("//span[@class='Box-sc-g0xbh4-0 lbunpI']");
    public static final By SUCCESS_FORK_BUTTON_BY = By.xpath("//button[contains(@class, 'NFBxW') and @type='submit']");


    public ForkPage(WebDriver driver) {
        super(driver);
    }

    public void createFork(String forkName) {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(FORK_NAME_FIELD_BY));
        WebElement titleField = getDriver().findElement(FORK_NAME_FIELD_BY);
        titleField.clear();
        titleField.sendKeys(forkName);
        getWait().until(ExpectedConditions.textToBePresentInElementLocated(AVAILABLE_FORK_NAME_TITLE_BY, forkName + " is available."));
        getDriver().findElement(SUCCESS_FORK_BUTTON_BY).click();
        getWait().until(ExpectedConditions.urlContains(forkName));

    }
}

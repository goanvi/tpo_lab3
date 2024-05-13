package ifmo.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NewRepositoryPage extends Page {
    public static final By AVAILABLE_REPOSITORY_NAME_TITLE_BY = By.xpath("//span[@class='Box-sc-g0xbh4-0 lbunpI']");
    public static final By CREATE_REPOSITORY_BUTTON_BY = By.xpath("//button[contains(@class, 'ezWhJE') and @type='submit']");

    public NewRepositoryPage(WebDriver driver) {
        super(driver);
    }

    public void endSuccessCreateRepository(String repositoryName) {
        getWait().until(ExpectedConditions.textToBePresentInElementLocated(AVAILABLE_REPOSITORY_NAME_TITLE_BY, repositoryName + " is available."));
        getDriver().findElement(CREATE_REPOSITORY_BUTTON_BY).click();
        getWait().until(ExpectedConditions.urlContains(repositoryName));
    }
}

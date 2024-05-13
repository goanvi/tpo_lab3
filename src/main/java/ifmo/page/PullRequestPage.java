package ifmo.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PullRequestPage extends Page {

    public static final By CREATE_BUTTON_AFTER_COMPARING_BY = By.xpath("//button[contains(@data-hydro-click, 'pull_request') and contains(@class,'js-details-target')]");
    public static final By PULL_REQUEST_FIELD_BY = By.xpath("//input[@id='pull_request_title']");
    public static final By SUCCESS_CREATE_PULL_REQUEST_BUTTON_BY = By.xpath("//button[contains(@class, 'hx_create-pr-button')]");

    public PullRequestPage(WebDriver driver) {
        super(driver);
    }

    public void endSuccessCreatePullRequest(String pullRequestName) {
        getWait().until(ExpectedConditions.elementToBeClickable(CREATE_BUTTON_AFTER_COMPARING_BY));
        getDriver().findElement(CREATE_BUTTON_AFTER_COMPARING_BY).click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(PULL_REQUEST_FIELD_BY));
        getDriver().findElement(PULL_REQUEST_FIELD_BY).sendKeys(pullRequestName);
        getDriver().findElement(SUCCESS_CREATE_PULL_REQUEST_BUTTON_BY).click();
    }
}

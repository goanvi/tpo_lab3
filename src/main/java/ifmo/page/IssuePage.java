package ifmo.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class IssuePage extends Page{
    public static final By ISSUE_NAME_FIELD_BY = By.xpath("//input[@id='issue_title']");
    public static final By SUBMIT_NEW_ISSUE_BUTTON_BY = By.xpath("//button[contains(text(), 'Submit new issue') and contains(@class, 'ml-2')]");

    public IssuePage(WebDriver driver) {
        super(driver);
    }

    public void createIssue(String issueName) {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(ISSUE_NAME_FIELD_BY));
        getDriver().findElement(ISSUE_NAME_FIELD_BY).sendKeys(issueName);
        getDriver().findElement(SUBMIT_NEW_ISSUE_BUTTON_BY).click();
        getWait().until(ExpectedConditions.not(ExpectedConditions.urlContains("new")));
    }
}

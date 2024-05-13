package ifmo.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BranchPage extends Page {
    public static final By CREATE_NEW_BRANCH_BUTTON_BY = By.xpath("//button[contains(@class, 'QUSMI')]");
    public static final By BRANCH_NAME_FIELD_BY = By.xpath("//input[not(contains(@placeholder,'.')) and contains(@class, 'cDLBls')]");
    public static final By SUCCESS_CREATE_BRANCH_BUTTON_BY = By.xpath("//button[@tabindex='-1']");

    public BranchPage(WebDriver driver) {
        super(driver);
    }

    public void createBranch(String branchName) {
        getWait().until(ExpectedConditions.elementToBeClickable(CREATE_NEW_BRANCH_BUTTON_BY));
        getDriver().findElement(CREATE_NEW_BRANCH_BUTTON_BY).click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(BRANCH_NAME_FIELD_BY));
        getDriver().findElement(BRANCH_NAME_FIELD_BY).sendKeys(branchName);
        getDriver().findElement(SUCCESS_CREATE_BRANCH_BUTTON_BY).click();
    }
}

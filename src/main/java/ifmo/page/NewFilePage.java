package ifmo.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NewFilePage extends Page{
    public static final By NEW_FILE_FIELD_BY = By.xpath("//input[@aria-label='File name']");
    public static final By COMMIT_BUTTON_BY = By.xpath("//button[@data-hotkey='Mod+s']");
    public static final By COMMIT_NAME_FIELD_BY = By.xpath("//button[@data-hotkey='Mod+Enter' and contains(@class, 'NFBxW')]");
    public static final By SUCCESS_COMMIT_BUTTON_BY = By.xpath("//button[@data-hotkey='Mod+Enter' and contains(@class, 'NFBxW')]");

    public NewFilePage(WebDriver driver) {
        super(driver);
    }

    public void createNewFile(String fileName) {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(NEW_FILE_FIELD_BY));
        getDriver().findElement(NEW_FILE_FIELD_BY).sendKeys(fileName);
        getDriver().findElement(COMMIT_BUTTON_BY).click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(COMMIT_NAME_FIELD_BY));
        getDriver().findElement(SUCCESS_COMMIT_BUTTON_BY).click();
    }
}

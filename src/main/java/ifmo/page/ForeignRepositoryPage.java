package ifmo.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ForeignRepositoryPage extends Page {

    LoggedInHomePage loggedInHomePage;

    @FindBy(xpath = "//button[@aria-label='Add file' and @data-component='IconButton']")
    WebElement addFileButton;

    @FindBy(xpath = "//a[@data-tab-item='i1issues-tab']")
    WebElement issuesTab;

    @FindBy(xpath = "//a[@data-tab-item='i2pull-requests-tab']")
    WebElement pullRequestTab;

    @FindBy(xpath = "//a[@id='fork-button']")
    WebElement forkButton;


    public ForeignRepositoryPage(WebDriver driver, LoggedInHomePage loggedInHomePage) {
        super(driver);
        this.loggedInHomePage = loggedInHomePage;
    }

    public void goTo(String repository, String user) {
        loggedInHomePage.searchRepositoryWithUserFilter(repository, user);
    }

    public void addFile() {
        addFileButton.click();
        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@tabindex='0']")));
        getDriver().findElement(By.xpath("//a[@tabindex='0']")).click();
        getWait().until(ExpectedConditions.urlContains("new"));
    }

    public void createIssue(String issueName) {
        issuesTab.click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-hotkey='c']")));
        getDriver().findElement(By.xpath("//a[@data-hotkey='c']")).click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='issue_title']")));
        getDriver().findElement(By.xpath("//input[@id='issue_title']")).sendKeys(issueName);
        getDriver().findElement(By.xpath("//button[contains(text(), 'Submit new issue') and contains(@class, 'ml-2')]")).click();
        getWait().until(ExpectedConditions.not(ExpectedConditions.urlContains("new")));
    }

    public void createPullRequest() {
        pullRequestTab.click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-hotkey='c']")));
        getDriver().findElement(By.xpath("//a[@data-hotkey='c']")).click();
        getWait().until(ExpectedConditions.urlContains("compare"));
    }

    public void createFork(String forkName) {
        forkButton.click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid='repository-name-input']")));
        WebElement titleField = getDriver().findElement(By.xpath("//input[@data-testid='repository-name-input']"));
        titleField.clear();
        titleField.sendKeys(forkName);
        getWait().until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//span[@class='Box-sc-g0xbh4-0 lbunpI']"), forkName + " is available."));
        getDriver().findElement(By.xpath("//button[contains(@class, 'NFBxW') and @type='submit']")).click();
        getWait().until(ExpectedConditions.urlContains(forkName));

    }

}

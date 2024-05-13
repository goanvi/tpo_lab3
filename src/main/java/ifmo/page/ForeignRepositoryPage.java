package ifmo.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ForeignRepositoryPage extends Page {

    public static final By CREATE_NEW_FILE_BUTTON_BY = By.xpath("//a[@tabindex='0']");
    public static final By NEW_ISSUE_BUTTON_BY = By.xpath("//a[@data-hotkey='c']");
    public static final By NEW_PULL_REQUEST_BUTTON_BY = By.xpath("//a[@data-hotkey='c']");
    LoggedInHomePage loggedInHomePage;

    @FindBy(xpath = "//button[@aria-label='Add file' and @data-component='IconButton']")
    WebElement addFileButton;

    @FindBy(xpath = "//a[@data-tab-item='i1issues-tab']")
    WebElement issuesTab;

    @FindBy(xpath = "//a[@data-tab-item='i2pull-requests-tab']")
    WebElement pullRequestTab;

    @FindBy(xpath = "//a[@id='fork-button']")
    WebElement forkButton;

    private final IssuePage issuePage;
    private final ForkPage forkPage;


    public ForeignRepositoryPage(WebDriver driver, LoggedInHomePage loggedInHomePage, IssuePage issuePage, ForkPage forkPage) {
        super(driver);
        this.loggedInHomePage = loggedInHomePage;
        this.issuePage = issuePage;
        this.forkPage = forkPage;
    }

    public void goTo(String repository, String user) {
        loggedInHomePage.searchRepositoryWithUserFilter(repository, user);
    }

    public void addFile() {
        addFileButton.click();
        getWait().until(ExpectedConditions.elementToBeClickable(CREATE_NEW_FILE_BUTTON_BY));
        getDriver().findElement(CREATE_NEW_FILE_BUTTON_BY).click();
        getWait().until(ExpectedConditions.urlContains("new"));
    }

    public void createIssue(String issueName) {
        issuesTab.click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(NEW_ISSUE_BUTTON_BY));
        getDriver().findElement(NEW_ISSUE_BUTTON_BY).click();
        issuePage.createIssue(issueName);
    }

    public void createPullRequest() {
        pullRequestTab.click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(NEW_PULL_REQUEST_BUTTON_BY));
        getDriver().findElement(NEW_PULL_REQUEST_BUTTON_BY).click();
        getWait().until(ExpectedConditions.urlContains("compare"));
    }

    public void createFork(String forkName) {
        forkButton.click();
        forkPage.createFork(forkName);
    }

}

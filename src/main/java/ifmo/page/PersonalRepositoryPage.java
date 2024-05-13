package ifmo.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PersonalRepositoryPage extends Page {

    public static final By SETTINGS_TAB_BY = By.xpath("//a[@id='settings-tab']");
    public static final By REMOVE_REPOSITORY_BUTTON_BY = By.xpath("//button[@data-show-dialog-id='repo-delete-menu-dialog']");
    public static final By REMOVE_REPOSITORY_BUTTON_2_BY = By.xpath("//button[@data-test-selector='repo-delete-proceed-button' and @data-next-stage='2']");
    public static final By REMOVE_REPOSITORY_BUTTON_3_BY = By.xpath("//button[@data-test-selector='repo-delete-proceed-button' and @data-next-stage='3']");
    public static final By REMOVE_REPOSITORY_VERIFICATION_FIELD_BY = By.xpath("//input[@id='verification_field']");
    public static final By REMOVE_REPOSITORY_BUTTON_LAST_BY = By.xpath("//button[@data-test-selector='repo-delete-proceed-button']");
    public static final By PULL_REQUEST_TAB_BY = By.xpath("//a[@id='pull-requests-tab']");
    public static final By SEARCH_BUTTON_BY = By.xpath("//button[@data-hotkey='s,/']");
    public static final By NEW_REPOSITORY_BUTTON_BY = By.xpath("//a[contains(@class, 'ml-2') and @href='/new']");
    public static final By REPOSITORY_NAME_FIELD_BY = By.xpath("//input[@data-testid='repository-name-input']");
    public static final By BRANCHES_LINK_BY = By.xpath("//a[contains(@aria-label, 'Branches')]");
    public static final By NEW_FILE_BUTTON_BY = By.xpath("//button[@aria-label='Add file']");
    public static final By CREATE_NEW_FILE_BUTTON_BY = By.xpath("//a[@tabindex='0']");
    public static final By NEW_PULL_REQUEST_BUTTON_BY = By.xpath("//a[contains(@data-ga-click,'New pull request')]");
    private final LoggedInHomePage loggedInHomePage;
    private final BranchPage branchPage;
    private final NewFilePage newFilePage;
    private final PullRequestPage pullRequestPage;
    private final NewRepositoryPage newRepositoryPage;
    private WebElement newRepoButton;


    public PersonalRepositoryPage(WebDriver driver, LoggedInHomePage loggedInHomePage, BranchPage branchPage, NewFilePage newFilePage, PullRequestPage pullRequestPage, NewRepositoryPage newRepositoryPage) {
        super(driver);
        this.loggedInHomePage = loggedInHomePage;
        this.branchPage = branchPage;
        this.newFilePage = newFilePage;
        this.pullRequestPage = pullRequestPage;
        this.newRepositoryPage = newRepositoryPage;
    }

    public void openRepositoryPage() {
        loggedInHomePage.getWait().until(ExpectedConditions.visibilityOfElementLocated(SEARCH_BUTTON_BY));
        loggedInHomePage.getDriver().get("https://github.com/gnchr?tab=repositories");
        newRepoButton = getDriver().findElement(NEW_REPOSITORY_BUTTON_BY);
    }

    public void startCreateRepository(String repositoryName) {
        newRepoButton.click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(REPOSITORY_NAME_FIELD_BY));
        WebElement titleField = getDriver().findElement(REPOSITORY_NAME_FIELD_BY);
        titleField.clear();
        titleField.sendKeys(repositoryName);
    }

    public void endSuccessCreateRepository(String repositoryName) {
        newRepositoryPage.endSuccessCreateRepository(repositoryName);
    }

    public void createBranch(String branchName) {
        getDriver().findElement(BRANCHES_LINK_BY).click();
        branchPage.createBranch(branchName);
    }

    public void createNewFile(String fileName) {
        getWait().until(ExpectedConditions.elementToBeClickable(NEW_FILE_BUTTON_BY));
        getDriver().findElement(NEW_FILE_BUTTON_BY).click();
        getWait().until(ExpectedConditions.elementToBeClickable(CREATE_NEW_FILE_BUTTON_BY));
        getDriver().findElement(CREATE_NEW_FILE_BUTTON_BY).click();
        newFilePage.createNewFile(fileName);
    }

    public void startCreatePullRequest() {
        getWait().until(ExpectedConditions.elementToBeClickable(PULL_REQUEST_TAB_BY));
        getDriver().findElement(PULL_REQUEST_TAB_BY).click();
        getWait().until(ExpectedConditions.elementToBeClickable(NEW_PULL_REQUEST_BUTTON_BY));
        getDriver().findElement(NEW_PULL_REQUEST_BUTTON_BY).click();
    }

    public void endSuccessCreatePullRequest(String pullRequestName) {
        pullRequestPage.endSuccessCreatePullRequest(pullRequestName);
    }

    public void removeRepository(String repositoryName) {
        getWait().until(ExpectedConditions.elementToBeClickable(SETTINGS_TAB_BY));
        getDriver().findElement(SETTINGS_TAB_BY).click();
        getWait().until(ExpectedConditions.elementToBeClickable(REMOVE_REPOSITORY_BUTTON_BY));
        getDriver().findElement(REMOVE_REPOSITORY_BUTTON_BY).click();
        getWait().until(ExpectedConditions.elementToBeClickable(REMOVE_REPOSITORY_BUTTON_2_BY));
        getDriver().findElement(REMOVE_REPOSITORY_BUTTON_2_BY).click();
        getWait().until(ExpectedConditions.elementToBeClickable(REMOVE_REPOSITORY_BUTTON_3_BY));
        getDriver().findElement(REMOVE_REPOSITORY_BUTTON_3_BY).click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(REMOVE_REPOSITORY_VERIFICATION_FIELD_BY));
        getDriver().findElement(REMOVE_REPOSITORY_VERIFICATION_FIELD_BY).sendKeys("gnchr/" + repositoryName);
        getDriver().findElement(REMOVE_REPOSITORY_BUTTON_LAST_BY).click();
        getWait().until(ExpectedConditions.urlContains("repositories"));
    }


}

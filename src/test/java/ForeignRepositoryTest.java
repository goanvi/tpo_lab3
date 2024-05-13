import ifmo.page.*;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ForeignRepositoryTest extends PageTestBase {
    static final By NEW_FILE_ERROR_TITLE_BY = By.xpath("//h3[contains(@class,'eRjmst')]");
    static final By CLOSE_ISSUE_BUTTON_BY = By.xpath("//button[@name='comment_and_close']");
    static final By ISSUE_TITLE_BY = By.xpath("//bdi[contains(@class,'js-issue-title')]");
    static final By PULL_REQUEST_BUTTON_BY = By.xpath("//button[contains(@data-ga-click,'request')]");
    static final By FORK_BUTTON_BY = By.xpath("//button[@data-test-selector='repo-delete-proceed-button']");
    static final By CREATE_FORK_ERROR_TITLE_BY = By.xpath("//div[contains(@class,'eVJSSH')]");
    static String user = "goanvi";
    static String repository = "tpo";
    static String issueName = "test issue";
    static String forkName = "test-fork";
    HomePage homePage;
    LoginPage loginPage;
    LoggedInHomePage loggedInHomePage;
    PersonalRepositoryPage personalRepositoryPage;
    ForeignRepositoryPage foreignRepository;
    BranchPage branchPage;
    NewFilePage newFilePage;
    PullRequestPage pullRequestPage;
    NewRepositoryPage newRepositoryPage;
    IssuePage issuePage;
    ForkPage forkPage;

    @BeforeEach
    void goToRepository() {
        foreignRepository.goTo(repository, user);
    }

    @TestWithAllDrivers
    void createNewFileTest(WebDriver driver) {
        foreignRepository.addFile();
        assertEquals(
                "You need to fork this repository to propose changes.",
                driver.findElement(NEW_FILE_ERROR_TITLE_BY).getText().trim()
        );
    }

    @TestWithAllDrivers
    void createIssueTest(WebDriver driver) {
        foreignRepository.createIssue(issueName);
        assertEquals(
                issueName,
                driver.findElement(ISSUE_TITLE_BY).getText().trim()
        );
        driver.findElement(CLOSE_ISSUE_BUTTON_BY).click();
    }

    @TestWithAllDrivers
    void createPullRequestBeforeForkTest(WebDriver driver) {
        foreignRepository.createPullRequest();
        assertEquals(
                "true",
                driver
                        .findElement(PULL_REQUEST_BUTTON_BY)
                        .getAttribute("disabled")
                        .trim()
        );
    }

    @TestWithAllDrivers
    void createForkTest(WebDriver driver) {
        foreignRepository.createFork(forkName);
        assertEquals(
                "https://github.com/gnchr/" + forkName,
                foreignRepository.currentUrl()
        );
        personalRepositoryPage.removeRepository(forkName);
    }

    @TestWithAllDrivers
    void createExistingForkTest(WebDriver driver) {
        foreignRepository.createFork(forkName);
        driver.get("https://github.com/");
        loggedInHomePage.searchRepositoryWithUserFilter(repository, user);
        driver.findElement(FORK_BUTTON_BY).click();
        foreignRepository.getWait().until(ExpectedConditions.visibilityOfElementLocated(CREATE_FORK_ERROR_TITLE_BY));
        assertEquals(
                "No available destinations to fork this repository.",
                driver.findElement(CREATE_FORK_ERROR_TITLE_BY).getText().trim()
        );
        personalRepositoryPage.removeRepository(forkName);
    }


    @Override
    protected void preparePages(WebDriver driver) {
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver, homePage);
        loggedInHomePage = new LoggedInHomePage(driver, loginPage);
        branchPage = new BranchPage(driver);
        newFilePage = new NewFilePage(driver);
        pullRequestPage = new PullRequestPage(driver);
        newRepositoryPage = new NewRepositoryPage(driver);
        issuePage = new IssuePage(driver);
        forkPage = new ForkPage(driver);
        personalRepositoryPage = new PersonalRepositoryPage(driver, loggedInHomePage, branchPage, newFilePage, pullRequestPage, newRepositoryPage);
        foreignRepository = new ForeignRepositoryPage(driver, loggedInHomePage, issuePage, forkPage);
    }
}

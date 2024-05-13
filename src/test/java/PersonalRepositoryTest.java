import ifmo.page.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class PersonalRepositoryTest extends PageTestBase {
    public static final By BAD_REPOSITORY_NAME_ERROR_BY = By.xpath("//span[@id='RepoNameInput-message']");
    public static final By PAGE_NOT_FOUND_ERROR_BY = By.xpath("//img[contains(@alt,'404')]");
    public static final By ACTIVE_BRANCHES_TITLE_BY = By.xpath("//h2[@id='Active']");
    public static final By BRANCH_NAMES_BY = By.xpath("//div[contains(@class,'hWGdnT')]");
    public static final By REMOVE_BRANCH_BUTTONS_BY = By.xpath("//button[contains(@aria-label,'Delete')]");
    public static final By CREATE_BRANCH_ERROR_TITLE_BY = By.xpath("//div[contains(text(),'Sorry')]");
    public static final By FILE_NAME_TITLES_BY = By.xpath("//a[contains(@aria-label, '(File)')]");
    public static final By ERROR_TITLE_BY = By.xpath("//div[contains(@class,'dFVtaX')]");
    public static final By CREATE_NEW_FILE_ERROR_SPAN_BY = By.xpath("//div[contains(text(),'There')]");
    public static final By CREATE_PULL_REQUEST_ERROR_TITLE_BY = By.xpath("//h3[contains(text(),'There')]");
    public static final By PULL_REQUEST_TITLE_BY = By.xpath("//bdi[contains(@class, 'markdown-title')]");
    public static final By CLOSE_PULL_REQUEST_BUTTON_BY = By.xpath("//button[@name='comment_and_close']");
    public static final By CREATE_PULL_REQUEST_ERROR_DIV_BY = By.xpath("//div[@class='js-flash-alert']");
    static String repositoryName = "test-repository";
    static String branchName = "test-branch";
    static String user = "goanvi";
    static String repository = "tpo";
    static String fileName = "test_file";
    static String pullRequestTitle = "test-pr";
    HomePage homePage;
    LoginPage loginPage;
    LoggedInHomePage loggedInHomePage;
    PersonalRepositoryPage personalRepositoryPage;
    ForeignRepositoryPage foreignRepositoryPage;
    BranchPage branchPage;
    NewFilePage newFilePage;
    PullRequestPage pullRequestPage;
    NewRepositoryPage newRepositoryPage;
    IssuePage issuePage;
    ForkPage forkPage;

    @TestWithAllDrivers
    void createRepositoryTest(WebDriver driver) {
        personalRepositoryPage.openRepositoryPage();
        personalRepositoryPage.startCreateRepository(repositoryName);
        personalRepositoryPage.endSuccessCreateRepository(repositoryName);
        assertEquals(
                "https://github.com/gnchr/" + repositoryName,
                personalRepositoryPage.currentUrl()
        );
        personalRepositoryPage.removeRepository(repositoryName);
    }

    @TestWithAllDrivers
    void createExistingRepositoryTest(WebDriver driver) {
        personalRepositoryPage.openRepositoryPage();
        personalRepositoryPage.startCreateRepository(repositoryName);
        personalRepositoryPage.endSuccessCreateRepository(repositoryName);
        personalRepositoryPage.openRepositoryPage();
        personalRepositoryPage.startCreateRepository(repositoryName);
        personalRepositoryPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(BAD_REPOSITORY_NAME_ERROR_BY));
        assertEquals(
                "The repository " + repositoryName + " already exists on this account.",
                driver.findElement(BAD_REPOSITORY_NAME_ERROR_BY).getText().trim()
        );
        driver.get("https://github.com/gnchr/" + repositoryName);
        personalRepositoryPage.removeRepository(repositoryName);
    }

    //Не проверял
    @TestWithAllDrivers
    void removeRepositoryTest(WebDriver driver) {
        personalRepositoryPage.openRepositoryPage();
        personalRepositoryPage.startCreateRepository(repositoryName);
        personalRepositoryPage.endSuccessCreateRepository(repositoryName);
        personalRepositoryPage.removeRepository(repositoryName);
        driver.get("https://github.com/gnchr/" + repositoryName);
        personalRepositoryPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(PAGE_NOT_FOUND_ERROR_BY));
        assertNotNull(driver.findElement(PAGE_NOT_FOUND_ERROR_BY));
    }

    @TestWithAllDrivers
    void createBranchTest(WebDriver driver) {
        foreignRepositoryPage.goTo(repository, user);
        foreignRepositoryPage.createFork(repositoryName);
        personalRepositoryPage.createBranch(branchName);
        personalRepositoryPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(ACTIVE_BRANCHES_TITLE_BY));
        assertEquals(
                branchName,
                driver.findElements(BRANCH_NAMES_BY).get(1).getText().trim());
        driver.findElements(REMOVE_BRANCH_BUTTONS_BY).get(1).click();
        personalRepositoryPage.removeRepository(repositoryName);
    }

    //Не проверил
    @TestWithAllDrivers
    void createExistingBranchTest(WebDriver driver) {
        foreignRepositoryPage.goTo(repository, user);
        foreignRepositoryPage.createFork(repositoryName);
        personalRepositoryPage.createBranch("master");
        personalRepositoryPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(ERROR_TITLE_BY));
        assertEquals(
                "Sorry, that branch already exists.",
                driver.findElement(CREATE_BRANCH_ERROR_TITLE_BY).getText().trim());
        driver.get("https://github.com/gnchr/" + repositoryName);
        personalRepositoryPage.removeRepository(repositoryName);
    }

    //Не проверил
    @TestWithAllDrivers
    void createNewFileTest(WebDriver driver) {
        foreignRepositoryPage.goTo(repository, user);
        foreignRepositoryPage.createFork(repositoryName);
        personalRepositoryPage.createNewFile(fileName);
        personalRepositoryPage.getWait().until(ExpectedConditions.not(ExpectedConditions.urlContains("new")));
        List<WebElement> files = driver.findElements(FILE_NAME_TITLES_BY);
        WebElement file = files.stream().filter(elem -> elem.getAttribute("title").equals(fileName)).findAny().orElse(null);
        Assertions.assertNotNull(file, "file " + fileName + " exist");
        personalRepositoryPage.removeRepository(repositoryName);
    }

    //Не проверил
    @TestWithAllDrivers
    void createIncorrectNewFileTest(WebDriver driver) {
        foreignRepositoryPage.goTo(repository, user);
        foreignRepositoryPage.createFork(repositoryName);
        personalRepositoryPage.createNewFile(" ");
        personalRepositoryPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(ERROR_TITLE_BY));
        assertEquals(
                "There was an error committing your changes:",
                driver.findElement(CREATE_NEW_FILE_ERROR_SPAN_BY).getText().trim());
        driver.get("https://github.com/gnchr/" + repositoryName);
        personalRepositoryPage.removeRepository(repositoryName);
    }

    //Не проверил
    @TestWithAllDrivers
    void createPullRequestWithoutChangesTest(WebDriver driver) {
        foreignRepositoryPage.goTo(repository, user);
        foreignRepositoryPage.createFork(repositoryName);
        personalRepositoryPage.startCreatePullRequest();
        foreignRepositoryPage.getWait().until(ExpectedConditions.urlContains("compare"));
        assertEquals(
                "There isn’t anything to compare.",
                driver.findElement(CREATE_PULL_REQUEST_ERROR_TITLE_BY).getText().trim()
        );
        driver.get("https://github.com/gnchr/" + repositoryName);
        personalRepositoryPage.removeRepository(repositoryName);
    }

    //Не проверил
    @TestWithAllDrivers
    void createPullRequestTest(WebDriver driver) {
        foreignRepositoryPage.goTo(repository, user);
        foreignRepositoryPage.createFork(repositoryName);
        personalRepositoryPage.startCreatePullRequest();
        personalRepositoryPage.endSuccessCreatePullRequest(pullRequestTitle);
        personalRepositoryPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(PULL_REQUEST_TITLE_BY));
        assertEquals(
                pullRequestTitle,
                driver.findElement(PULL_REQUEST_TITLE_BY).getText().trim()
        );
        driver.findElement(CLOSE_PULL_REQUEST_BUTTON_BY).click();
        driver.get("https://github.com/gnchr/" + repositoryName);
        personalRepositoryPage.removeRepository(repositoryName);
    }

    //Не проверил
    @TestWithAllDrivers
    void createIncorrectPullRequestTest(WebDriver driver) {
        foreignRepositoryPage.goTo(repository, user);
        foreignRepositoryPage.createFork(repositoryName);
        personalRepositoryPage.startCreatePullRequest();
        personalRepositoryPage.endSuccessCreatePullRequest(" ");
        personalRepositoryPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(CREATE_PULL_REQUEST_ERROR_DIV_BY));
        assertEquals(
                "Pull request creation failed. Validation failed: Title can't be blank",
                driver.findElement(CREATE_PULL_REQUEST_ERROR_TITLE_BY).getText().trim()
        );
        driver.get("https://github.com/gnchr/" + repositoryName);
        personalRepositoryPage.removeRepository(repositoryName);
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
        foreignRepositoryPage = new ForeignRepositoryPage(driver, loggedInHomePage, issuePage, forkPage);
    }
}

import ifmo.page.ForeignRepositoryPage;
import ifmo.page.HomePage;
import ifmo.page.LoggedInHomePage;
import ifmo.page.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ForeignRepositoryTest extends PageTestBase {
    static String user = "goanvi";
    static String repository = "tpo";
    static String issueName = "test issue";
    static String forkName = "test-fork";
    HomePage homePage;
    LoginPage loginPage;
    LoggedInHomePage loggedInHomePage;
    ForeignRepositoryPage foreignRepository;

    @BeforeEach
    void goToRepository() {
        foreignRepository.goTo(repository, user);
    }

    @TestWithAllDrivers
    void createNewFileTest(WebDriver driver) {
        foreignRepository.addFile();
        assertEquals(
                "You need to fork this repository to propose changes.",
                driver.findElement(By.xpath("//h3[contains(@class,'eRjmst')]")).getText().trim()
        );
    }

    @TestWithAllDrivers
    void createIssueTest(WebDriver driver) {
        foreignRepository.createIssue(issueName);
        assertEquals(
                issueName,
                driver.findElement(By.xpath("//bdi[contains(@class,'js-issue-title')]")).getText().trim()
        );
        driver.findElement(By.xpath("//button[@name='comment_and_close']")).click();
    }

    @TestWithAllDrivers
    void createPullRequestBeforeForkTest(WebDriver driver) {
        foreignRepository.createPullRequest();
        assertEquals(
                "true",
                driver
                        .findElement(By.xpath("//button[contains(@data-ga-click,'request')]"))
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
        driver.findElement(By.xpath("//a[@id='settings-tab']")).click();
        foreignRepository.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-show-dialog-id='repo-delete-menu-dialog']")));
        driver.findElement(By.xpath("//button[@data-show-dialog-id='repo-delete-menu-dialog']")).click();
        foreignRepository.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-test-selector='repo-delete-proceed-button' and @data-next-stage='2']")));
        driver.findElement(By.xpath("//button[@data-test-selector='repo-delete-proceed-button' and @data-next-stage='2']")).click();
        foreignRepository.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-test-selector='repo-delete-proceed-button' and @data-next-stage='3']")));
        driver.findElement(By.xpath("//button[@data-test-selector='repo-delete-proceed-button' and @data-next-stage='3']")).click();
        foreignRepository.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='verification_field']")));
        driver.findElement(By.xpath("//input[@id='verification_field']")).sendKeys("gnchr/" + forkName);
        driver.findElement(By.xpath("//button[@data-test-selector='repo-delete-proceed-button']")).click();
        foreignRepository.getWait().until(ExpectedConditions.urlContains("repositories"));
    }

    @TestWithAllDrivers
    void createExistingForkTest(WebDriver driver) {
        foreignRepository.createFork(forkName);
        driver.get("https://github.com/");
        loggedInHomePage.searchRepositoryWithUserFilter(repository, user);
        driver.findElement(By.xpath("//a[@id='fork-button']")).click();
        foreignRepository.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'eVJSSH')]")));
        assertEquals(
                "No available destinations to fork this repository.",
                driver.findElement(By.xpath("//div[contains(@class,'eVJSSH')]")).getText().trim()
        );
        driver.get("https://github.com/gnchr/" + forkName);
        foreignRepository.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='settings-tab']")));
        driver.findElement(By.xpath("//a[@id='settings-tab']")).click();
        foreignRepository.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-show-dialog-id='repo-delete-menu-dialog']")));
        driver.findElement(By.xpath("//button[@data-show-dialog-id='repo-delete-menu-dialog']")).click();
        foreignRepository.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-test-selector='repo-delete-proceed-button' and @data-next-stage='2']")));
        driver.findElement(By.xpath("//button[@data-test-selector='repo-delete-proceed-button' and @data-next-stage='2']")).click();
        foreignRepository.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-test-selector='repo-delete-proceed-button' and @data-next-stage='3']")));
        driver.findElement(By.xpath("//button[@data-test-selector='repo-delete-proceed-button' and @data-next-stage='3']")).click();
        foreignRepository.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='verification_field']")));
        driver.findElement(By.xpath("//input[@id='verification_field']")).sendKeys("gnchr/" + forkName);
        driver.findElement(By.xpath("//button[@data-test-selector='repo-delete-proceed-button']")).click();
        foreignRepository.getWait().until(ExpectedConditions.urlContains("repositories"));

    }

    @Override
    protected void preparePages(WebDriver driver) {
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver, homePage);
        loggedInHomePage = new LoggedInHomePage(driver, loginPage);
        foreignRepository = new ForeignRepositoryPage(driver, loggedInHomePage);
    }
}

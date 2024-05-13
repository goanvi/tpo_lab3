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

    @TestWithAllDrivers
    void createRepositoryTest(WebDriver driver) {
        personalRepositoryPage.openRepositoryPage();
        personalRepositoryPage.createRepository(repositoryName);
        assertEquals(
                "https://github.com/gnchr/" + repositoryName,
                personalRepositoryPage.currentUrl()
        );
        removeRepository(driver);
    }

    @TestWithAllDrivers
    void createExistingRepositoryTest(WebDriver driver) {
        personalRepositoryPage.openRepositoryPage();
        personalRepositoryPage.createRepository(repositoryName);
        personalRepositoryPage.openRepositoryPage();
        personalRepositoryPage.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@class, 'ml-2') and @href='/new']")));
        driver.findElement(By.xpath("//a[contains(@class, 'ml-2') and @href='/new']")).click();
        personalRepositoryPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid='repository-name-input']")));
        WebElement titleField = driver.findElement(By.xpath("//input[@data-testid='repository-name-input']"));
        titleField.clear();
        titleField.sendKeys(repositoryName);
        personalRepositoryPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='RepoNameInput-message']")));
        assertEquals(
                "The repository " + repositoryName + " already exists on this account.",
                driver.findElement(By.xpath("//span[@id='RepoNameInput-message']")).getText().trim()
        );
        driver.get("https://github.com/gnchr/" + repositoryName);
        removeRepository(driver);
    }

    //Не проверял
    @TestWithAllDrivers
    void removeRepositoryTest(WebDriver driver) {
        personalRepositoryPage.openRepositoryPage();
        personalRepositoryPage.createRepository(repositoryName);
        removeRepository(driver);
        driver.get("https://github.com/gnchr/" + repositoryName);
        personalRepositoryPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[contains(@alt,'404')]")));
        assertNotNull(driver.findElement(By.xpath("//img[contains(@alt,'404')]")));
    }

    @TestWithAllDrivers
    void createBranchTest(WebDriver driver) {
        foreignRepositoryPage.goTo(repository, user);
        foreignRepositoryPage.createFork(repositoryName);
        personalRepositoryPage.createBranch(branchName);
        personalRepositoryPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@id='Active']")));
        assertEquals(
                branchName,
                driver.findElements(By.xpath("//div[contains(@class,'hWGdnT')]")).get(1).getText().trim());
        driver.findElements(By.xpath("//button[contains(@aria-label,'Delete')]")).get(1).click();
        removeRepository(driver);
    }

    //Не проверил
    @TestWithAllDrivers
    void createExistingBranchTest(WebDriver driver) {
        foreignRepositoryPage.goTo(repository, user);
        foreignRepositoryPage.createFork(repositoryName);
        personalRepositoryPage.createBranch("master");
        personalRepositoryPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'dFVtaX')]")));
        assertEquals(
                "Sorry, that branch already exists.",
                driver.findElement(By.xpath("//div[contains(text(),'Sorry')]")).getText().trim());
        driver.get("https://github.com/gnchr/" + repositoryName);
        removeRepository(driver);
    }

    //Не проверил
    @TestWithAllDrivers
    void createNewFileTest(WebDriver driver) {
        foreignRepositoryPage.goTo(repository, user);
        foreignRepositoryPage.createFork(repositoryName);
        personalRepositoryPage.createNewFile(fileName);
        personalRepositoryPage.getWait().until(ExpectedConditions.not(ExpectedConditions.urlContains("new")));
        List<WebElement> files = driver.findElements(By.xpath("//a[contains(@aria-label, '(File)')]"));
        WebElement file = files.stream().filter(elem -> elem.getAttribute("title").equals(fileName)).findAny().orElse(null);
        Assertions.assertNotNull(file, "file " + fileName + " exist");
        removeRepository(driver);
    }

    //Не проверил
    @TestWithAllDrivers
    void createIncorrectNewFileTest(WebDriver driver) {
        foreignRepositoryPage.goTo(repository, user);
        foreignRepositoryPage.createFork(repositoryName);
        personalRepositoryPage.createNewFile(" ");
        personalRepositoryPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'dFVtaX')]")));
        assertEquals(
                "There was an error committing your changes:",
                driver.findElement(By.xpath("//div[contains(text(),'There')]")).getText().trim());
        driver.get("https://github.com/gnchr/" + repositoryName);
        removeRepository(driver);
    }

    //Не проверил
    @TestWithAllDrivers
    void createPullRequestWithoutChangesTest(WebDriver driver) {
        foreignRepositoryPage.goTo(repository, user);
        foreignRepositoryPage.createFork(repositoryName);
        foreignRepositoryPage.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='pull-requests-tab']")));
        driver.findElement(By.xpath("//a[@id='pull-requests-tab']")).click();
        foreignRepositoryPage.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@data-ga-click,'New pull request')]")));
        driver.findElement(By.xpath("//a[contains(@data-ga-click,'New pull request')]")).click();
        foreignRepositoryPage.getWait().until(ExpectedConditions.urlContains("compare"));
        assertEquals(
                "There isn’t anything to compare.",
                driver.findElement(By.xpath("//h3[contains(text(),'There')]")).getText().trim()
        );
        driver.get("https://github.com/gnchr/" + repositoryName);
        removeRepository(driver);
    }

    //Не проверил
    @TestWithAllDrivers
    void createPullRequestTest(WebDriver driver) {
        foreignRepositoryPage.goTo(repository, user);
        foreignRepositoryPage.createFork(repositoryName);
        personalRepositoryPage.createPullRequest(pullRequestTitle);
        personalRepositoryPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//bdi[contains(@class, 'markdown-title')]")));
        assertEquals(
                pullRequestTitle,
                driver.findElement(By.xpath("//bdi[contains(@class, 'markdown-title')]")).getText().trim()
        );
        driver.findElement(By.xpath("//button[@name='comment_and_close']")).click();
        driver.get("https://github.com/gnchr/" + repositoryName);
        removeRepository(driver);
    }

    //Не проверил
    @TestWithAllDrivers
    void createIncorrectPullRequestTest(WebDriver driver) {
        foreignRepositoryPage.goTo(repository, user);
        foreignRepositoryPage.createFork(repositoryName);
        personalRepositoryPage.createPullRequest(" ");
        personalRepositoryPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='js-flash-alert']")));
        assertEquals(
                "Pull request creation failed. Validation failed: Title can't be blank",
                driver.findElement(By.xpath("//div[@class='js-flash-alert']")).getText().trim()
        );
        driver.get("https://github.com/gnchr/" + repositoryName);
        removeRepository(driver);
    }

    void removeRepository(WebDriver driver) {
        personalRepositoryPage.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='settings-tab']")));
        driver.findElement(By.xpath("//a[@id='settings-tab']")).click();
        personalRepositoryPage.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-show-dialog-id='repo-delete-menu-dialog']")));
        driver.findElement(By.xpath("//button[@data-show-dialog-id='repo-delete-menu-dialog']")).click();
        personalRepositoryPage.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-test-selector='repo-delete-proceed-button' and @data-next-stage='2']")));
        driver.findElement(By.xpath("//button[@data-test-selector='repo-delete-proceed-button' and @data-next-stage='2']")).click();
        personalRepositoryPage.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-test-selector='repo-delete-proceed-button' and @data-next-stage='3']")));
        driver.findElement(By.xpath("//button[@data-test-selector='repo-delete-proceed-button' and @data-next-stage='3']")).click();
        personalRepositoryPage.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='verification_field']")));
        driver.findElement(By.xpath("//input[@id='verification_field']")).sendKeys("gnchr/" + repositoryName);
        driver.findElement(By.xpath("//button[@data-test-selector='repo-delete-proceed-button']")).click();
        personalRepositoryPage.getWait().until(ExpectedConditions.urlContains("repositories"));
    }

    @Override
    protected void preparePages(WebDriver driver) {
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver, homePage);
        loggedInHomePage = new LoggedInHomePage(driver, loginPage);
        personalRepositoryPage = new PersonalRepositoryPage(driver, loggedInHomePage);
        foreignRepositoryPage = new ForeignRepositoryPage(driver, loggedInHomePage);
    }
}

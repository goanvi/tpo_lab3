package ifmo.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PersonalRepositoryPage extends Page {

    private final LoggedInHomePage loggedInHomePage;
    private WebElement newRepoButton;

    public PersonalRepositoryPage(WebDriver driver, LoggedInHomePage loggedInHomePage) {
        super(driver);
        this.loggedInHomePage = loggedInHomePage;
    }

    public void openRepositoryPage() {
        loggedInHomePage.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-hotkey='s,/']")));
        loggedInHomePage.getDriver().get("https://github.com/gnchr?tab=repositories");
        newRepoButton = getDriver().findElement(By.xpath("//a[contains(@class, 'ml-2') and @href='/new']"));
    }

    public void createRepository(String repositoryName) {
        newRepoButton.click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid='repository-name-input']")));
        WebElement titleField = getDriver().findElement(By.xpath("//input[@data-testid='repository-name-input']"));
        titleField.clear();
        titleField.sendKeys(repositoryName);
        getWait().until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//span[@class='Box-sc-g0xbh4-0 lbunpI']"), repositoryName + " is available."));
        getDriver().findElement(By.xpath("//button[contains(@class, 'ezWhJE') and @type='submit']")).click();
        getWait().until(ExpectedConditions.urlContains(repositoryName));
    }

    public void createBranch(String branchName) {
        getDriver().findElement(By.xpath("//a[contains(@aria-label, 'Branches')]")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'QUSMI')]")));
        getDriver().findElement(By.xpath("//button[contains(@class, 'QUSMI')]")).click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[not(contains(@placeholder,'.')) and contains(@class, 'cDLBls')]")));
        getDriver().findElement(By.xpath("//input[not(contains(@placeholder,'.')) and contains(@class, 'cDLBls')]")).sendKeys(branchName);
        getDriver().findElement(By.xpath("//button[@tabindex='-1']")).click();
    }

    public void createNewFile(String fileName) {
        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Add file']")));
        getDriver().findElement(By.xpath("//button[@aria-label='Add file']")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@tabindex='0']")));
        getDriver().findElement(By.xpath("//a[@tabindex='0']")).click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@aria-label='File name']")));
        getDriver().findElement(By.xpath("//input[@aria-label='File name']")).sendKeys(fileName);
        getDriver().findElement(By.xpath("//button[@data-hotkey='Mod+s']")).click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-hotkey='Mod+Enter' and contains(@class, 'NFBxW')]")));
        getDriver().findElement(By.xpath("//button[@data-hotkey='Mod+Enter' and contains(@class, 'NFBxW')]")).click();
    }

    public void createPullRequest(String pullRequestName) {
        getDriver().findElement(By.xpath("//a[@id='pull-requests-tab']")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@data-ga-click,'New pull request')]")));
        getDriver().findElement(By.xpath("//a[contains(@data-ga-click,'New pull request')]")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@data-hydro-click, 'pull_request') and contains(@class,'js-details-target')]")));
        getDriver().findElement(By.xpath("//button[contains(@data-hydro-click, 'pull_request') and contains(@class,'js-details-target')]")).click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='pull_request_title']")));
        getDriver().findElement(By.xpath("//input[@id='pull_request_title']")).sendKeys(pullRequestName);
        getDriver().findElement(By.xpath("//button[contains(@class, 'hx_create-pr-button')]")).click();
    }


}

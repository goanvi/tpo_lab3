package ifmo.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoggedInHomePage extends Page {

    @FindBy(xpath = "//button[@data-hotkey='s,/']")
    private WebElement searchButton;

    @FindBy(xpath = "//input[@name='query-builder-test']")
    private WebElement searchField;


    public LoggedInHomePage(WebDriver driver, LoginPage loginPage) {
        super(driver);
        loginPage.login(System.getenv("TPO_EMAIL"), System.getenv("TPO_PASSWORD"));
    }

    protected void clickSearchButton() {
        getWait().until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
    }

    protected void searchQuery(String query) {
        clickSearchButton();
        searchField.sendKeys(query);
        searchField.sendKeys(Keys.ENTER);
    }

    public void searchUser(String user) {
        searchQuery(user);
        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-testid='nav-item-users']")));
        getDriver().findElement(By.xpath("//a[@data-testid='nav-item-users']")).click();
        getWait().until(ExpectedConditions.urlContains("users"));
        getDriver().findElement(By.xpath("//a[@class='Link__StyledLink-sc-14289xe-0 cstVDi']")).click();
        getWait().until(ExpectedConditions.urlToBe("https://github.com/" + user));

    }

    public void searchRepositoryWithUserFilter(String repository, String user) {
        searchQuery(repository + " owner:" + user);
        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-testid='nav-item-repositories']")));
        getDriver().findElement(By.xpath("//a[@data-testid='nav-item-repositories']")).click();
        getWait().until(ExpectedConditions.urlContains("repositories"));
        getDriver().findElement(By.xpath("//a[@class='Link__StyledLink-sc-14289xe-0 cstVDi']")).click();
        getWait().until(ExpectedConditions.urlToBe("https://github.com/" + user + "/" + repository));
    }


}

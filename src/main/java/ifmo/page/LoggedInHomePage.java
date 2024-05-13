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

    public static final By NAV_USER_BUTTON_BY = By.xpath("//a[@data-testid='nav-item-users']");
    public static final By USER_LINK_BY = By.xpath("//a[@class='Link__StyledLink-sc-14289xe-0 cstVDi']");
    public static final By NAV_REPOSITORY_BUTTON_BY = By.xpath("//a[@data-testid='nav-item-repositories']");
    public static final By REPOSITORY_LINK_BY = By.xpath("//a[@class='Link__StyledLink-sc-14289xe-0 cstVDi']");
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
        getWait().until(ExpectedConditions.elementToBeClickable(NAV_USER_BUTTON_BY));
        getDriver().findElement(NAV_USER_BUTTON_BY).click();
        getWait().until(ExpectedConditions.urlContains("users"));
        getDriver().findElement(USER_LINK_BY).click();
        getWait().until(ExpectedConditions.urlToBe("https://github.com/" + user));

    }

    public void searchRepositoryWithUserFilter(String repository, String user) {
        searchQuery(repository + " owner:" + user);
        getWait().until(ExpectedConditions.elementToBeClickable(NAV_REPOSITORY_BUTTON_BY));
        getDriver().findElement(NAV_REPOSITORY_BUTTON_BY).click();
        getWait().until(ExpectedConditions.urlContains("repositories"));
        getDriver().findElement(REPOSITORY_LINK_BY).click();
        getWait().until(ExpectedConditions.urlToBe("https://github.com/" + user + "/" + repository));
    }


}

package ifmo.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HomePage extends Page {
    @FindBy(how = How.XPATH, using = "//a[@aria-label='Homepage']")
    private WebElement homeButton;

    @FindBy(xpath = "//a[@href = '/login' and not(contains(@class, 'f5'))]")
    private WebElement loginButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void clickHome() {
        homeButton.click();
    }

    public void openLoginPage() {
        loginButton.click();
    }
}
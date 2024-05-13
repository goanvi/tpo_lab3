package ifmo.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Optional;

public class LoginPage extends Page {
    public static final String CREDENTIALS_ERROR_XPATH = "//div[@role='alert' and contains(text(),'Incorrect username or password.')]";
    @FindBy(xpath = "//input[@name='login']")
    private WebElement emailField;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//input[@name='commit']")
    private WebElement loginButton;

    public LoginPage(WebDriver driver, HomePage homePage) {
        super(driver);
        homePage.openLoginPage();
    }

    protected void fillCredentials(String email, String password) {
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
    }

    protected void clickLoginButton() {
        loginButton.click();
    }

    public void login(String email, String password) {
        fillCredentials(email, password);
        clickLoginButton();
    }

    public boolean hasWrongCredentialsError() {
        Optional<WebElement> passwordError = findOptionalElement(By.xpath(CREDENTIALS_ERROR_XPATH));
        return passwordError.isPresent();
    }
}

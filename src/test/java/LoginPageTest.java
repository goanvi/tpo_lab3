import ifmo.page.HomePage;
import ifmo.page.LoginPage;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginPageTest extends PageTestBase {
    static String EMAIL = System.getenv("TPO_EMAIL");
    static String PASSWORD = System.getenv("TPO_PASSWORD");
    HomePage homePage;
    LoginPage loginPage;


    @TestWithAllDrivers
    void testCorrectCredentials(WebDriver driver) {
        loginPage.login(EMAIL, PASSWORD);
        assertEquals("https://github.com/", loginPage.currentUrl());
    }

    @TestWithAllDrivers
    void testInCorrectCredentials(WebDriver driver) {
        loginPage.login("EMAIL", "PASSWORD");
        assertTrue(loginPage.hasWrongCredentialsError());
    }


    @Override
    protected void preparePages(WebDriver driver) {
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver, homePage);
    }
}

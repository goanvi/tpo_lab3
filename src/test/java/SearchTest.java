import ifmo.page.HomePage;
import ifmo.page.LoggedInHomePage;
import ifmo.page.LoginPage;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchTest extends PageTestBase {
    static String user = "goanvi";
    static String repository = "tpo";
    HomePage homePage;
    LoginPage loginPage;
    LoggedInHomePage loggedInHomePage;

    @TestWithAllDrivers
    void searchUserTest(WebDriver driver) {
        loggedInHomePage.searchUser(user);
        assertEquals("https://github.com/" + user, loggedInHomePage.currentUrl());
    }

    @TestWithAllDrivers
    void searchRepositoryWithUserFilterTest(WebDriver driver) {
        loggedInHomePage.searchRepositoryWithUserFilter(repository, user);
        assertEquals("https://github.com/" + user + "/" + repository, loggedInHomePage.currentUrl());
    }

    @Override
    protected void preparePages(WebDriver driver) {
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver, homePage);
        loggedInHomePage = new LoggedInHomePage(driver, loginPage);
    }
}

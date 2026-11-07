package tests;

import base.BaseTest;
import pages.LoginPage;
import pages.SearchPage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {

    @Test
    public void verifyLinkedInSearch() {

        LoginPage loginPage = new LoginPage(driver);
        SearchPage searchPage = new SearchPage(driver);

        // Read LinkedIn credentials
        String email = base.Config.get("LINKEDIN_EMAIL");
        String password = base.Config.get("LINKEDIN_PASSWORD");

        Assert.assertTrue(
                email != null && !email.isBlank()
                        && password != null && !password.isBlank(),
                "LinkedIn credentials are not configured"
        );

        // Step 1: Open LinkedIn login page
        loginPage.openLoginPage();

        // Step 2: Enter valid credentials
        loginPage.enterUsername(email);
        loginPage.enterPassword(password);

        // Step 3: Click Sign in
        loginPage.clickLogin();

        // Step 4: Verify login
        Assert.assertTrue(
                loginPage.isLoginSuccessful(),
                "Login was not successful"
        );

        System.out.println(
                "LinkedIn login successful!"
        );

        // Step 5: Search for a keyword
        searchPage.searchForKeyword("software testing");

        // Step 6: Verify search results URL
        Assert.assertTrue(
                searchPage.isSearchResultsPageDisplayed(),
                "Search results page was not opened"
        );

        // Step 7: Verify visible search results
        int resultCount =
                searchPage.getVisibleSearchResultCount();

        Assert.assertTrue(
                resultCount > 0,
                "No visible search results found"
        );

        System.out.println(
                "Visible search results: " + resultCount
        );

        System.out.println(
                "Current URL: " + driver.getCurrentUrl()
        );

        System.out.println(
                "TC08 Passed: LinkedIn search works!"
        );
    }
}


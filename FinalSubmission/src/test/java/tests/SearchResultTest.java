package tests;

import base.BaseTest;
import pages.LoginPage;
import pages.SearchPage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchResultTest extends BaseTest {

    @Test
    public void verifyClickSearchResult() {

        LoginPage loginPage = new LoginPage(driver);
        SearchPage searchPage = new SearchPage(driver);

        String email = base.Config.get("LINKEDIN_EMAIL");
        String password = base.Config.get("LINKEDIN_PASSWORD");

        Assert.assertTrue(
                email != null && !email.isBlank()
                        && password != null && !password.isBlank(),
                "LinkedIn credentials are not configured"
        );

        loginPage.openLoginPage();
        loginPage.enterUsername(email);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        Assert.assertTrue(
                loginPage.isLoginSuccessful(),
                "LinkedIn login failed"
        );

        System.out.println("Login successful!");

        searchPage.searchForKeyword("software testing");

        Assert.assertTrue(
                searchPage.isSearchResultsPageDisplayed(),
                "Search results page did not open"
        );


        String expectedUrl = searchPage.clickFirstCompanyResult();

        Assert.assertNotNull(
                expectedUrl,
                "No company result was found"
        );

        Assert.assertTrue(
                searchPage.isCompanyPageOpened(expectedUrl),
                "Expected company page did not open"
        );

        System.out.println("TC09 Passed: Company page opened!");
        System.out.println("Current URL: " + driver.getCurrentUrl());

    }
}

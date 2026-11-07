package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginTest extends BaseTest {

    @Test
    public void verifyLoginPageLoads() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.openLoginPage();
        System.out.println("Email inputs found: " +
                driver.findElements(
                        org.openqa.selenium.By.cssSelector("input[type='email']")
                ).size());

        System.out.println("Password inputs found: " +
                driver.findElements(
                        org.openqa.selenium.By.cssSelector("input[type='password']")
                ).size());

        System.out.println("Iframes found: " +
                driver.findElements(
                        org.openqa.selenium.By.tagName("iframe")
                ).size());

        // Check which page Selenium actually opens
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page Title: " + driver.getTitle());

        Assert.assertTrue(
                loginPage.isLoginFormDisplayed(),
                "Login form fields are not displayed"
        );

        System.out.println(
                "LinkedIn login form displayed successfully!"
        );
    }

    @Test
    public void verifyEmptyLoginValidation() {

        LoginPage loginPage = new LoginPage(driver);

        // Open LinkedIn login page
        loginPage.openLoginPage();

        // Leave email and password fields empty
        // Click the visible Sign in button
        loginPage.clickLogin();

        // Verify LinkedIn displays the validation error
        Assert.assertTrue(
                loginPage.isEmptyLoginValidationDisplayed(),
                "Expected empty email validation message was not displayed"
        );

        // Verify user remains on the login page
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "User was unexpectedly redirected away from login page"
        );

        System.out.println(
                "TC02 Passed: Empty login form validation displayed!"
        );
    }

    @Test
    public void verifyPasswordVisibilityToggle() {

        LoginPage loginPage = new LoginPage(driver);

        // Open LinkedIn login page
        loginPage.openLoginPage();

        // Enter a dummy password
        loginPage.enterPassword("TestPassword123");

        // Verify password is initially hidden
        Assert.assertEquals(
                loginPage.getPasswordFieldType(),
                "password",
                "Password should initially be hidden"
        );

        // Click eye icon to show password
        loginPage.clickPasswordVisibilityIcon();

        // Verify password is visible
        Assert.assertEquals(
                loginPage.getPasswordFieldType(),
                "text",
                "Password should be visible after clicking eye icon"
        );
// Capture evidence while the password is visible
        try {
            java.nio.file.Path screenshotPath =
                    java.nio.file.Paths.get("test-screenshots", "TC03_Password_Visible.png");

            java.nio.file.Files.createDirectories(screenshotPath.getParent());

            java.nio.file.Files.copy(
                    ((org.openqa.selenium.TakesScreenshot) driver)
                            .getScreenshotAs(org.openqa.selenium.OutputType.FILE)
                            .toPath(),
                    screenshotPath,
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );

            System.out.println("TC03 visible password screenshot saved!");
        } catch (java.io.IOException e) {
            throw new RuntimeException("Unable to save TC03 screenshot", e);
        }
        // Click eye icon again to hide password
        loginPage.clickPasswordVisibilityIcon();

        // Verify password is hidden again
        Assert.assertEquals(
                loginPage.getPasswordFieldType(),
                "password",
                "Password should be hidden after clicking eye icon again"
        );

        System.out.println(
                "TC03 Passed: Password visibility toggle works!"
        );
    }



    @Test
    public void verifyInvalidEmailFormatValidation() {

        LoginPage loginPage = new LoginPage(driver);

        // Open LinkedIn login page
        loginPage.openLoginPage();

        // Enter invalid email
        loginPage.enterUsername("invalidemail");

        // Enter dummy password
        loginPage.enterPassword("TestPassword123");

        // Click Sign in
        loginPage.clickLogin();

        // Verify invalid email validation message
        Assert.assertTrue(
                loginPage.isInvalidEmailValidationDisplayed(),
                "Invalid email validation message was not displayed"
        );

        // Verify user remains on login page
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "User was unexpectedly redirected away from login page"
        );

        System.out.println(
                "TC04 Passed: Invalid email format validation works!"
        );
    }


    @Test
    public void verifyEmptyPasswordValidation() {

        LoginPage loginPage = new LoginPage(driver);

        // Open LinkedIn login page
        loginPage.openLoginPage();

        // Enter a valid email format
        loginPage.enterUsername("test@example.com");

        // Leave password empty and click Sign in
        loginPage.clickLogin();

        // Verify password validation message
        Assert.assertTrue(
                loginPage.isEmptyPasswordValidationDisplayed(),
                "Empty password validation message was not displayed"
        );

        // Verify user remains on login page
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "User was unexpectedly redirected away from login page"
        );

        System.out.println(
                "TC05 Passed: Empty password validation works!"
        );
    }




    @Test
    public void verifyInvalidLoginCredentials() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.openLoginPage();

        loginPage.enterUsername("invaliduser@example.com");
        loginPage.enterPassword("WrongPassword123!");

        loginPage.clickLogin();

        // Inspect LinkedIn's response
        System.out.println(
                "Current URL: " + driver.getCurrentUrl()
        );

        System.out.println(
                "Page Title: " + driver.getTitle()
        );

        System.out.println(
                "Visible page text:"
        );

        System.out.println(
                driver.findElement(By.tagName("body")).getText()
        );

        // Verify the error message
        Assert.assertTrue(
                loginPage.isInvalidCredentialsErrorDisplayed(),
                "Invalid credentials error message was not displayed"
        );

        System.out.println(
                "TC06 Passed: Invalid login credentials rejected!"
        );
    }

    @Test
    public void verifySuccessfulLogin() {

        LoginPage loginPage = new LoginPage(driver);

        // Read credentials from environment variables
        String email = base.Config.get("LINKEDIN_EMAIL");
        String password = base.Config.get("LINKEDIN_PASSWORD");

        Assert.assertTrue(
                email != null && !email.isBlank()
                        && password != null && !password.isBlank(),
                "LinkedIn credentials are not configured"
        );

        // Open LinkedIn login page
        loginPage.openLoginPage();

        // Enter valid credentials
        loginPage.enterUsername(email);
        loginPage.enterPassword(password);

        // Click Sign in
        loginPage.clickLogin();

        // Verify successful login
        Assert.assertTrue(
                loginPage.isLoginSuccessful(),
                "Successful LinkedIn login was not detected"
        );

        System.out.println(
                "TC07 Passed: LinkedIn login successful!"
        );

        System.out.println(
                "Current URL: " + driver.getCurrentUrl()
        );
    }

}
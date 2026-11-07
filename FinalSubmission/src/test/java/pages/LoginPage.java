package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By usernameField = By.cssSelector("input[type='email'][autocomplete~='username']");
    private final By passwordField = By.id("password");
    private final By loginButton =
            By.cssSelector("button[type='submit']");
    private final By errorMessage =
            By.cssSelector("#error-for-password, .alert");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(15));
    }

    public void openLoginPage() {
        driver.get("https://www.linkedin.com/login");
    }


    public void enterUsername(String username) {

        By emailLocator = By.cssSelector("input[type='email']");

        WebElement emailField = wait.until(driver ->
                driver.findElements(emailLocator)
                        .stream()
                        .filter(element ->
                                element.isDisplayed() &&
                                        element.isEnabled()
                        )
                        .findFirst()
                        .orElse(null)
        );

        emailField.clear();
        emailField.sendKeys(username);

        System.out.println("Email entered successfully!");
    }


    public void clickLogin() {

        By signInButtons = By.xpath(
                "//button[@type='button'][.//span[normalize-space()='Sign in']]"
        );

        WebElement visibleButton = wait.until(driver ->
                driver.findElements(signInButtons)
                        .stream()
                        .filter(button ->
                                button.isDisplayed() && button.isEnabled()
                        )
                        .findFirst()
                        .orElse(null)
        );

        visibleButton.click();

        System.out.println("Visible Sign in button clicked!");
    }



    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public boolean isErrorDisplayed() {
        return wait.until(ExpectedConditions
                        .visibilityOfElementLocated(errorMessage))
                .isDisplayed();
    }


    public boolean isLoginFormDisplayed() {

        By emailLocator = By.cssSelector("input[type='email']");
        By passwordLocator = By.cssSelector("input[type='password']");

        return wait.until(driver -> {

            boolean emailVisible = driver.findElements(emailLocator)
                    .stream()
                    .anyMatch(element -> element.isDisplayed());

            boolean passwordVisible = driver.findElements(passwordLocator)
                    .stream()
                    .anyMatch(element -> element.isDisplayed());

            return emailVisible && passwordVisible;
        });
    }


    public boolean isEmptyLoginValidationDisplayed() {

        By validationMessage = By.xpath(
                "//*[contains(normalize-space(text()), " +
                        "'Please enter an email address or phone number.')]"
        );

        return wait.until(driver ->
                driver.findElements(validationMessage)
                        .stream()
                        .anyMatch(element ->
                                element.isDisplayed()
                        )
        );
    }


    public WebElement getVisiblePasswordField() {

        return wait.until(driver ->
                driver.findElements(By.cssSelector("input[type='password']"))
                        .stream()
                        .filter(WebElement::isDisplayed)
                        .findFirst()
                        .orElse(null)
        );
    }

    public String getPasswordFieldType() {

        WebElement passwordField = wait.until(driver ->
                driver.findElements(
                                By.cssSelector("input[autocomplete='current-password']")
                        )
                        .stream()
                        .filter(WebElement::isDisplayed)
                        .findFirst()
                        .orElse(null)
        );

        return passwordField.getAttribute("type");
    }

    public void enterPassword(String password) {

        getVisiblePasswordField().sendKeys(password);
    }



    public void clickPasswordVisibilityIcon() {

        By visibilityButton = By.cssSelector(
                "button[aria-label='Show password'], " +
                        "button[aria-label='Hide password']"
        );

        WebElement button = wait.until(driver ->
                driver.findElements(visibilityButton)
                        .stream()
                        .filter(element ->
                                element.isDisplayed() &&
                                        element.isEnabled()
                        )
                        .findFirst()
                        .orElse(null)
        );

        button.click();

        System.out.println("Password visibility button clicked!");
    }

    public boolean isInvalidEmailValidationDisplayed() {

        By validationMessage = By.xpath(
                "//*[normalize-space(text())=" +
                        "'Please enter a valid email address.']"
        );

        return wait.until(driver ->
                driver.findElements(validationMessage)
                        .stream()
                        .anyMatch(element -> element.isDisplayed())
        );
    }

    public boolean isEmptyPasswordValidationDisplayed() {

        By validationMessage = By.xpath(
                "//*[normalize-space(text())='Please enter a password.']"
        );

        return wait.until(driver ->
                driver.findElements(validationMessage)
                        .stream()
                        .anyMatch(element -> element.isDisplayed())
        );
    }

    public boolean isInvalidCredentialsErrorDisplayed() {

        By errorMessage = By.xpath(
                "//*[normalize-space(text())='Wrong email or password.']"
        );

        return wait.until(driver ->
                driver.findElements(errorMessage)
                        .stream()
                        .anyMatch(element -> element.isDisplayed())
        );
    }

    public boolean isLoginSuccessful() {

        // LinkedIn can take a while after sign-in (loading spinner, redirects),
        // and may show a security check that the user must complete by hand.
        // Allow 90 seconds, and ignore elements replaced while the page redraws.
        WebDriverWait loginWait = new WebDriverWait(
                driver, Duration.ofSeconds(90)
        );
        loginWait.pollingEvery(Duration.ofMillis(500));
        loginWait.ignoring(StaleElementReferenceException.class);

        try {
            boolean loggedIn = loginWait.until(d -> {

                String currentUrl = d.getCurrentUrl();

                // Security check / verification page: keep waiting
                // so it can be completed manually in the browser window.
                if (currentUrl.contains("/checkpoint")
                        || currentUrl.contains("/challenge")) {
                    return false;
                }

                boolean authenticatedUrl =
                        currentUrl.contains("/feed")
                                || currentUrl.contains("/mynetwork")
                                || currentUrl.contains("/in/");

                boolean searchBoxVisible =
                        d.findElements(By.cssSelector(
                                "input[placeholder*='Search'], " +
                                        "input[aria-label*='Search']"
                        )).stream().anyMatch(this::isVisibleSafely);

                return authenticatedUrl || searchBoxVisible;
            });

            // Let the home feed finish rendering so later steps
            // don't grab elements that are about to be replaced.
            waitForPageToSettle();
            return loggedIn;

        } catch (TimeoutException e) {
            System.out.println(
                    "Login not detected. Current URL: "
                            + driver.getCurrentUrl()
            );
            return false;
        }
    }

    private boolean isVisibleSafely(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (StaleElementReferenceException e) {
            return false;
        }
    }

    private void waitForPageToSettle() {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(d -> "complete".equals(
                        ((JavascriptExecutor) d)
                                .executeScript("return document.readyState")));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}

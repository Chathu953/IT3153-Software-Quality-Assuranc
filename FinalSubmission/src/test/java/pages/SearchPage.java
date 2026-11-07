package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By searchLocator = By.cssSelector(
            "input[placeholder*='Search'], " +
                    "input[aria-label*='Search']"
    );

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(30)
        );
    }

    // Find a fresh, visible search box
    public WebElement getSearchBox() {

        return wait.until(d -> {
            try {
                return d.findElements(searchLocator)
                        .stream()
                        .filter(element ->
                                element.isDisplayed() &&
                                        element.isEnabled()
                        )
                        .findFirst()
                        .orElse(null);
            } catch (StaleElementReferenceException e) {
                return null;
            }
        });
    }

    // Search with automatic retry if element becomes stale
    public void searchForKeyword(String keyword) {

        wait.ignoring(StaleElementReferenceException.class)
                .until(d -> {

                    WebElement searchBox = getSearchBox();

                    searchBox.click();
                    searchBox.clear();
                    searchBox.sendKeys(keyword);
                    searchBox.sendKeys(Keys.ENTER);

                    return true;
                });

        System.out.println(
                "Search performed for: " + keyword
        );
    }

    // Verify search results page
    public boolean isSearchResultsPageDisplayed() {

        return wait.until(d ->
                d.getCurrentUrl().contains("/search/results/")
        );
    }

    // Count visible search results


    public int getVisibleSearchResultCount() {

        By resultLinks = By.cssSelector(
                "a[href*='/jobs/view/'], " +
                        "a[href*='/in/'], " +
                        "a[href*='/company/']"
        );

        return wait.until(d -> {

            int count = 0;

            for (WebElement element :
                    d.findElements(resultLinks)) {

                try {
                    if (element.isDisplayed()
                            && !element.getText().isBlank()) {
                        count++;
                    }
                } catch (
                        org.openqa.selenium.StaleElementReferenceException e
                ) {
                    // Ignore elements replaced during page updates
                }
            }

            return count > 0 ? count : null;
        });
    }


    public String clickFirstCompanyResult() {

        By companyLink = By.cssSelector(
                "main a[href*='/company/']"
        );

        return wait.until(d -> {

            try {
                if (!d.getCurrentUrl()
                        .contains("/search/results/")) {
                    return null;
                }

                for (WebElement link :
                        d.findElements(companyLink)) {

                    if (!link.isDisplayed()) {
                        continue;
                    }

                    String url = link.getAttribute("href");

                    if (url == null || url.isBlank()) {
                        continue;
                    }

                    if (url.contains("premium-nav-upsell")
                            || url.contains("premium_nav_upsell")) {
                        continue;
                    }

                    String companyName = link.getText().trim();

                    if (companyName.isBlank()) {
                        continue;
                    }

                    System.out.println(
                            "Selected company: " + companyName
                    );

                    System.out.println(
                            "Company URL: " + url
                    );

                    link.click();

                    return url;
                }

            } catch (StaleElementReferenceException e) {
                return null;
            }

            return null;
        });
    }




    public boolean isCompanyPageOpened(String expectedUrl) {

        if (expectedUrl == null || expectedUrl.isBlank()) {
            return false;
        }

        return wait.until(d -> {

            String actual = d.getCurrentUrl()
                    .split("\\?", 2)[0]
                    .replaceAll("/+$", "");

            String expected = expectedUrl
                    .split("\\?", 2)[0]
                    .replaceAll("/+$", "");

            return actual.equals(expected)
                    && actual.contains("/company/");
        });
    }
}

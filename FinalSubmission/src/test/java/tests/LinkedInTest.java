
package tests;
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LinkedInTest extends BaseTest {

    @Test
    public void verifyLinkedInPageTitle() {
        driver.get("https://www.linkedin.com/");

        String title = driver.getTitle();
        System.out.println("Page Title: " + title);

        Assert.assertTrue(
                title.contains("LinkedIn"),
                "LinkedIn page title was not found"
        );
    }
}


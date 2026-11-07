package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;

import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        try {
            if (driver != null) {

                String status;

                if (result.getStatus() == ITestResult.SUCCESS) {
                    status = "PASSED";
                } else if (result.getStatus() == ITestResult.FAILURE) {
                    status = "FAILED";
                } else {
                    status = "SKIPPED";
                }

                takeScreenshot(result.getName(), status);
            }

        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    private void takeScreenshot(String testName, String status) {

        try {
            Path folder = Path.of("test-screenshots", status);

            Files.createDirectories(folder);

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern(
                            "yyyyMMdd_HHmmss_SSS"));

            String safeName = testName.replaceAll(
                    "[^a-zA-Z0-9_-]", "_");

            String fileName = safeName + "_"
                    + timestamp + ".png";

            File source = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            Path destination = folder.resolve(fileName);

            Files.copy(
                    source.toPath(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );

            System.out.println(
                    "Screenshot saved: "
                            + destination.toAbsolutePath()
            );

        } catch (IOException | org.openqa.selenium.WebDriverException e) {
            System.err.println(
                    "Screenshot capture failed: " + e.getMessage()
            );
        }
    }
}

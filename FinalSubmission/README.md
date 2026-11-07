# LinkedIn Automation Testing

## 1. Project Overview

This project automates selected LinkedIn login and search functionalities using Selenium WebDriver, Java, and TestNG. It demonstrates automated functional testing through browser interactions, input validation, assertions, test execution, and screenshot capture.

The project follows the Page Object Model (POM) design pattern to separate test logic from webpage interactions, improving code organization, reusability, and maintainability.

The automation covers nine test cases related to login functionality, form validation, password visibility, user authentication, search functionality, and search result navigation.

## 2. Technologies Used

| Technology | Version / Purpose |
|---|---|
| Java | JDK 21 |
| Selenium WebDriver | 4.27.0 |
| TestNG | 7.10.2 |
| Apache Maven | Dependency management and test execution |
| IntelliJ IDEA | Development environment |
| Google Chrome | Browser for automated testing |
| Git and GitHub | Version control and source code management |

## 3. Project Structure

```text
LinkedInAutomationTesting/
│
├── src/
│   └── test/
│       └── java/
│           ├── base/
│           │   └── BaseTest.java
│           │
│           ├── pages/
│           │   ├── LoginPage.java
│           │   └── SearchPage.java
│           │
│           └── tests/
│               ├── LinkedInTest.java
│               ├── LoginTest.java
│               ├── SearchTest.java
│               └── SearchResultTest.java
│
├── test-screenshots/
│   ├── PASSED/
│   ├── FAILED/
│   └── TC03_Password_Visible.png
│
├── pom.xml
├── testng.xml
├── README.md
└── .gitignore
```

### Description of Main Components

- **BaseTest.java:** Handles browser initialization, test setup, browser cleanup, and automatic screenshot capture.
- **LoginPage.java:** Contains Selenium locators and reusable methods for LinkedIn login interactions.
- **SearchPage.java:** Contains methods for performing LinkedIn searches and interacting with search results.
- **LoginTest.java:** Contains automated login and form validation test cases.
- **SearchTest.java:** Contains the search functionality test.
- **SearchResultTest.java:** Contains the search result navigation test.
- **LinkedInTest.java:** Additional Java test class included in the source code.
- **pom.xml:** Defines Maven dependencies and project build configuration.
- **testng.xml:** Defines the TestNG test execution suite.
- **README.md:** Provides project documentation and execution instructions.

## 4. Automated Test Cases

| Test ID | Test Case Description |
|---|---|
| TC01 | Verify LinkedIn login page loads successfully |
| TC02 | Verify empty login form validation |
| TC03 | Verify password visibility toggle functionality |
| TC04 | Verify invalid email format validation |
| TC05 | Verify empty password validation |
| TC06 | Verify invalid login credentials |
| TC07 | Verify successful login using valid credentials |
| TC08 | Verify LinkedIn search functionality |
| TC09 | Verify navigation from search results |

The automated tests use TestNG assertions to compare actual application behavior against expected results.

## 5. Prerequisites

Before executing the automation tests, ensure the following software is available:

1. Java Development Kit (JDK) 21
2. Apache Maven
3. Google Chrome browser
4. IntelliJ IDEA (recommended)
5. Internet connection
6. An authorized LinkedIn account for authentication-related tests

Maven downloads the required project dependencies defined in `pom.xml`.

Selenium WebDriver manages browser automation. Selenium Manager can automatically resolve the required browser driver when supported by the local environment.

## 6. How to Configure and Execute the Tests

### Step 1: Download or Extract the Project

Clone the GitHub repository or extract the submitted ZIP file.

To clone the repository, execute:

```bash
git clone git remote add origin https://github.com/Chathu953/IT3153-Software-Quality-Assuranc.git

```

Open the extracted or cloned project directory in IntelliJ IDEA.

### Step 2: Configure Java and Maven

Ensure JDK 21 is installed and selected as the project SDK.

Open `pom.xml` in IntelliJ IDEA and allow Maven to load the project dependencies.

To verify the Java installation, execute:

```bash
java -version
```

To verify Maven, execute:

```bash
mvn -version
```

### Step 3: Configure LinkedIn Credentials

The project uses environment variables to securely access LinkedIn login credentials.

The following environment variables must be configured:

| Variable | Description |
|---|---|
| `LINKEDIN_EMAIL` | Email address of an authorized LinkedIn account |
| `LINKEDIN_PASSWORD` | Password associated with the account |

Actual LinkedIn credentials are not included in the project source code.

**Method 1: Configure Credentials in IntelliJ IDEA**

1. Open IntelliJ IDEA.
2. Select **Run → Edit Configurations**.
3. Create or select the TestNG run configuration associated with `testng.xml`.
4. Locate the **Environment variables** field.
5. Add the variables `LINKEDIN_EMAIL` and `LINKEDIN_PASSWORD` with the appropriate values.
6. Click **Apply** and **OK**.

**Method 2: Configure Credentials in Windows PowerShell**

Open PowerShell and enter:

```powershell
$env:LINKEDIN_EMAIL = "your_test_email"
$env:LINKEDIN_PASSWORD = "your_test_password"
```

Replace the placeholder values with credentials for an authorized LinkedIn account.

These PowerShell variables apply to the current terminal session.

**Security Note:** Do not hardcode actual credentials in Java files, commit them to GitHub, or include them in the submission ZIP file.

### Step 4: Execute the Tests

**Method 1: Run Using IntelliJ IDEA**

1. Open the project in IntelliJ IDEA.
2. Allow Maven dependencies to load.
3. Open `testng.xml`.
4. Right-click the file.
5. Select **Run**.
6. Observe the test execution results in the Run window.

Ensure the TestNG configuration has the required environment variables.

**Method 2: Run Using Maven**

Open a terminal in the project root directory and execute:

```bash
mvn clean test
```

This command compiles the test code and executes the tests configured through the Maven build.

If the Maven configuration does not automatically select `testng.xml`, the TestNG suite must be configured in the Maven Surefire Plugin.

### Step 5: Review Test Execution Results

After execution, the results can be reviewed through:

- IntelliJ IDEA TestNG Run window
- Maven terminal output
- Generated test reports under the `target` directory
- Screenshots captured during test execution

The results indicate which tests passed, failed, or were skipped.

## 7. Screenshot Capture

The automation framework includes automatic screenshot capture to provide evidence of test execution.

Screenshots are captured after test execution and organized according to the TestNG result.

The screenshot directory contains:

```text
test-screenshots/
├── PASSED/
├── FAILED/
└── TC03_Password_Visible.png
```

- **PASSED:** Contains screenshots captured after successfully executed tests.
- **FAILED:** Contains screenshots captured after failed tests.
- **TC03_Password_Visible.png:** Captures evidence of the password visibility toggle while the dummy test password is displayed.

Automatic screenshot filenames include timestamps to distinguish individual test executions.

The screenshots can be used as supporting evidence in the test report.

## 8. Test Execution Results

**Recorded execution date:** November 5, 2026

| Result Category | Count |
|---|---:|
| Total Test Cases | 9 |
| Passed | 8 |
| Failed | 1 |
| Skipped | 0 |
| Pass Rate | 88.89% |

### Failed Test Case: TC06

**Test Case:** Verify Invalid Login Credentials

**Observed Result:** The test encountered a timeout while waiting for the expected invalid-login error message.

LinkedIn's security verification mechanism, including CAPTCHA challenges, affected the expected validation flow during execution.

As a result, the expected error message was not detected within the configured waiting period.

This failure was recorded and documented rather than bypassing LinkedIn's security verification.

### Overall Result

Eight out of nine automated test cases passed during the recorded execution.

The results demonstrate that the implemented login validation, password visibility, successful authentication, search functionality, and search result navigation tests operated as expected in that execution.

## 9. Known Limitations

The following limitations should be considered when executing the automation tests:

1. LinkedIn may display CAPTCHA challenges or additional account verification.
2. Login-related tests require valid credentials for an authorized LinkedIn account.
3. LinkedIn's dynamic interface may change, affecting Selenium element locators.
4. Internet connectivity and browser performance may influence execution time.
5. Results may vary between different execution environments.
6. Security verification challenges may prevent some tests from reaching their expected validation messages.

The automation does not attempt to bypass LinkedIn's security mechanisms.

## 10. Security Considerations

The project follows these credential-handling practices:

- Actual LinkedIn credentials are accessed through environment variables.
- Real login credentials are not hardcoded in the Java test classes.
- Dummy credentials are used for negative testing and password visibility validation.
- Local development files and generated output are excluded from Git tracking where appropriate.
- Personal information should be hidden in submitted screenshots and screen recordings.

Users executing the tests should use their own authorized LinkedIn credentials.

## 11. Conclusion

This project demonstrates automated functional testing of LinkedIn login and search workflows using Selenium WebDriver, Java, TestNG, and Maven.

The implementation uses the Page Object Model design pattern, reusable page methods, TestNG assertions, automated screenshot capture, and organized test execution.

The recorded execution produced eight successful test cases out of nine, with one test affected by LinkedIn's security verification behavior.

Overall, the project demonstrates the application of automated software quality assurance techniques to a real-world web application.

## 12. Repository

**GitHub Repository:**

git remote add origin https://github.com/Chathu953/IT3153-Software-Quality-Assuranc.git

The repository contains the source code, Maven configuration, TestNG configuration, and project documentation.
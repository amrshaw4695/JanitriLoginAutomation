package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class LoginPage {

    public final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    //Locators for Login Page
    private final By userIDLoc = By.xpath("//input[@id='formEmail']");
    private final By passwordLoc = By.xpath("//input[@id='formPassword']");
    private final By loginLoc = By.xpath("//button[@class='login-button']");
    private final By passwordVisibilityToggle = By.xpath("//img[@class='passowrd-visible']");

    //Actions for Login Page
    public void enterUserID(String userid) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='formEmail']")));

        // Scroll into view just in case
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", emailField);

        // Click before typing
        emailField.click();
        emailField.clear();
        emailField.sendKeys(userid);
    }


    public void enterPassword(String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='formPassword']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", passwordField);

        // Click before typing
        passwordField.click();
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        WebElement btnLogin = driver.findElement(loginLoc);
        btnLogin.click();
    }


    // Function to checks
    public boolean isLoginButtonEnabled() {
        WebElement loginButton = driver.findElement(loginLoc);
        return loginButton.isEnabled();
    }

    public boolean isInvalidCredentialsPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // First check if login page is loaded
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formEmail")));

            // Check if notification prompt div appears — if yes, refresh
            try {
                WebElement notification = driver.findElement(
                        By.xpath("//p[contains(text(),'To proceed to the login page please allow')]")
                );
                if (notification.isDisplayed()) {
                    driver.navigate().refresh(); // Refresh and wait again
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formEmail")));
                }
            } catch (NoSuchElementException ignored) {
            }

            // Now wait for the error message
            WebElement errorDiv = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//div[@class='invalid-credential-div']")
                    )
            );

            return errorDiv.isDisplayed();
        } catch (Exception e) {
            System.out.println("❌ Could not detect invalid credentials div: " + e.getMessage());
            return false;
        }
    }

    public String getErrorMessageText() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement errorDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class, 'invalid-credential-div')]")
            ));
            return errorDiv.getText().trim();
        } catch (TimeoutException e) {
            return ""; // Or handle accordingly
        }
    }


    public boolean isPasswordMasked() {
        WebElement passwordField = driver.findElement(By.id("formPassword"));
        return passwordField.getAttribute("type").equals("password");
    }

    public boolean isPasswordUnmasked() {
        WebElement passwordField = driver.findElement(By.id("formPassword"));
        return passwordField.getAttribute("type").equals("text");
    }

    public void togglePasswordVisibility() {
        WebElement eyeIcon = driver.findElement(By.xpath("//img[@class='passowrd-visible']"));  // use correct class from HTML
        eyeIcon.click();
    }

    public boolean isCaptionVisible() {
        WebElement title = driver.findElement(By.xpath("//p[contains(text(),'Your Pregnancy and Newborn Monitoring Partner')]"));
        return title.isDisplayed();
    }

    public boolean isUserIdFieldVisible() {
        return driver.findElement(By.id("formEmail")).isDisplayed();
    }

    public boolean isPasswordFieldVisible() {
        return driver.findElement(By.id("formPassword")).isDisplayed();
    }

    public boolean isEyeIconVisible() {
        return driver.findElement(By.xpath("//img[@class='passowrd-visible']")).isDisplayed();
    }


    public boolean isLogoDisplayed() {
        WebElement logo = driver.findElement(By.xpath("//img[@class='login-janitri-logo']"));
        return logo.isDisplayed();
    }
}

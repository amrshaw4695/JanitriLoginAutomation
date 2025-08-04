package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // === Locators ===
    private final By userIDLoc = By.id("formEmail");
    private final By passwordLoc = By.id("formPassword");
    private final By loginButtonLoc = By.xpath("//button[@class='login-button']");
    private final By passwordEyeIconLoc = By.xpath("//img[@class='passowrd-visible']");
    private final By invalidCredLoc = By.xpath("//div[@class='invalid-credential-div']");
    private final By captionLoc = By.xpath("//p[contains(text(),'Your Pregnancy and Newborn Monitoring Partner')]");
    private final By logoLoc = By.xpath("//img[@class='login-janitri-logo']");
    private final By notificationText = By.xpath("//p[contains(text(),'To proceed to the login page please allow')]");

    // === Constructor ===
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // === Actions ===

    public void enterUserID(String userId) {
        WebElement userField = wait.until(ExpectedConditions.elementToBeClickable(userIDLoc));
        userField.clear();
        userField.sendKeys(userId);
    }

    public void enterPassword(String password) {
        WebElement pwdField = wait.until(ExpectedConditions.elementToBeClickable(passwordLoc));
        pwdField.clear();
        pwdField.sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButtonLoc)).click();
    }

    public void togglePasswordVisibility() {
        driver.findElement(passwordEyeIconLoc).click();
    }

    // === Validations ===

    public boolean isLoginButtonEnabled() {
        return driver.findElement(loginButtonLoc).isEnabled();
    }

    public boolean isInvalidCredentialsPresent() {
        handleNotificationIfPresent();
        try {
            WebElement errorDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(invalidCredLoc));
            return errorDiv.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getErrorMessageText() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement errorDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class, 'invalid-credential-div')]")
            ));
            return errorDiv.getText().trim();
        } catch (TimeoutException e) {
            return ""; // No error appeared
        }
    }


    public boolean isPasswordMasked() {
        return driver.findElement(passwordLoc).getAttribute("type").equals("password");
    }

    public boolean isPasswordUnmasked() {
        return driver.findElement(passwordLoc).getAttribute("type").equals("text");
    }

    public boolean isUserIdFieldVisible() {
        return driver.findElement(userIDLoc).isDisplayed();
    }

    public boolean isPasswordFieldVisible() {
        return driver.findElement(passwordLoc).isDisplayed();
    }

    public boolean isEyeIconVisible() {
        return driver.findElement(passwordEyeIconLoc).isDisplayed();
    }

    public boolean isCaptionVisible() {
        return driver.findElement(captionLoc).isDisplayed();
    }

    public boolean isLogoDisplayed() {
        return driver.findElement(logoLoc).isDisplayed();
    }

    // === Private Helper ===

    private void handleNotificationIfPresent() {
        try {
            if (driver.findElement(notificationText).isDisplayed()) {
                driver.navigate().refresh();
                wait.until(ExpectedConditions.visibilityOfElementLocated(userIDLoc));
            }
        } catch (NoSuchElementException ignored) {
        }
    }
}

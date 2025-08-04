package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseTest {

    @Test(priority = 1)
    void testLoginButtonEnabledWhenFieldsAreEmpty() throws Exception {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUserID("");
        loginPage.enterPassword("");
        loginPage.clickLogin();

        assertTrue(loginPage.isLoginButtonEnabled(),
                "Login button should be enabled even when fields are blank but should throw Invalid credential on Login");
    }

    @Test(priority = 2)
    void testInvalidLoginWithEmptyFields_ShowsErrorMessage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUserID("");
        loginPage.enterPassword("");
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isInvalidCredentialsPresent(),
                "Error message should appear when logging in with empty fields");
    }


    @Test(priority = 3)
    void testLoginBehaviourWhenInvalidCredentials() throws Exception {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUserID("wrongUser");
        loginPage.enterPassword("wrongPassword");
        loginPage.clickLogin();

        String errorText = loginPage.getErrorMessageText();
        System.out.println("Error message shown: " + errorText);
        Assert.assertTrue(errorText.toLowerCase().contains("invalid"), "Expected an error on invalid login.");
    }

    @Test(priority = 4)
    public void testPasswordVisibilityToggle() {
        LoginPage loginPage = new LoginPage(driver);

        // Step 1: Enter any password (to make sure the field is active)
        loginPage.enterPassword("Shaw");

        // Step 2: Verify it's masked
        Assert.assertTrue(loginPage.isPasswordMasked(), "Password should be masked initially");

        // Step 3: Click the toggle icon
        loginPage.togglePasswordVisibility();

        // Step 4: Verify it's unmasked
        Assert.assertTrue(loginPage.isPasswordUnmasked(), "Password should be visible after toggling");
    }

    @Test(priority = 5)
    public void testLoginWithOnlyUserID() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUserID("amrita");
        loginPage.clickLogin();

        // Assert
        assertTrue(loginPage.isInvalidCredentialsPresent(), "Login should fail with only UserID");
    }

    @Test(priority = 6)
    public void testLoginWithOnlyPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterPassword("Shaw");
        loginPage.clickLogin();

        // Assert
        assertTrue(loginPage.isInvalidCredentialsPresent(), "Login should fail with only Password");
    }

    @Test(priority = 7)
    public void testUIElementsPresence() {
        LoginPage loginPage = new LoginPage(driver);

        Assert.assertTrue(loginPage.isCaptionVisible(), "Page caption should be visible");
        Assert.assertTrue(loginPage.isUserIdFieldVisible(), "User ID input field should be visible");
        Assert.assertTrue(loginPage.isPasswordFieldVisible(), "Password input field should be visible");
        Assert.assertTrue(loginPage.isEyeIconVisible(), "Eye icon for password toggle should be visible");

    }

    @Test(priority = 8)
    public void testPageTitleVerification() {
        String expectedTitle = "Janitri";
        String actualTitle = driver.getTitle();

        Assert.assertEquals(actualTitle, expectedTitle, "Page title should match expected title");
    }

    @Test(priority = 9)
    public void testLogoPresence() {
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isLogoDisplayed(), "Janitri logo should be visible on the login page");

    }

}



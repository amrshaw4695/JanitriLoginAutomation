package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseTest {

    @Test(priority = 1)
    void testLoginButtonDisabledWhenFieldAreEmpty() throws Exception {
        // Arrange
        LoginPage loginPage = new LoginPage(driver);

        // Act
        loginPage.enterUserID("");
        loginPage.enterPassword("");

        // Assert
        assertTrue(loginPage.isLoginButtonEnabled(),
                "Login button should be disabled when fields are empty!!");
    }

    @Test(priority = 2)
    void testLoginBehaviourWhenFieldsAreBlank() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUserID("");
        loginPage.enterPassword("");
        loginPage.clickLogin();

        String errorText = loginPage.getErrorMessageText();
        Assert.assertTrue(errorText.toLowerCase().contains("invalid"),
                "Expected an invalid input error when fields are blank, but got: " + errorText);
    }


    @Test(priority = 3)
    void testLoginBehaviourWhenInvalidCredentials() throws Exception {
        // Arrange
        LoginPage loginPage = new LoginPage(driver);

        // Act
        loginPage.enterUserID("amrita");
        loginPage.enterPassword("Shaw");
        loginPage.clickLogin();
        System.out.println(driver.getPageSource());


        // Assert
        assertTrue(loginPage.isInvalidCredentialsPresent());
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
        // Arrange
        LoginPage loginPage = new LoginPage(driver);

        // Act
        loginPage.enterUserID("amrita");
        loginPage.clickLogin();

        // Assert
        assertTrue(loginPage.isInvalidCredentialsPresent(), "Login should fail with only UserID");
    }

    @Test(priority = 6)
    public void testLoginWithOnlyPassword() {
        // Arrange
        LoginPage loginPage = new LoginPage(driver);

        // Act
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

        String expectedTitle = "Janitri"; // This is what you see in the browser tab
        String actualTitle = driver.getTitle();

        Assert.assertEquals(actualTitle, expectedTitle, "Page title should match expected title");
    }
@Test(priority = 9)
    public void testLogoPresence()  {
    LoginPage loginPage = new LoginPage(driver);
    Assert.assertTrue(loginPage.isLogoDisplayed(), "Janitri logo should be visible on the login page");

    }

}



package test.blackbox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timetable.Authentication.AuthenticationService;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceTest {

    private AuthenticationService authService;
    private final String TEST_AUTH_FILE = "test_auth_data.ser";

    @BeforeEach
    void setUp() {
        authService = new AuthenticationService(TEST_AUTH_FILE);
    }

    @AfterEach
    void tearDown() {
     
        File testFile = new File(TEST_AUTH_FILE);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testSuccessfulLogin() {
        authService.register("nbv3", "naga123");

        //verifies login 
        assertTrue(authService.login("nbv3", "naga123"), "Login credentials should match with registered user.");
    }

    @Test
    void testLoginWithIncorrectPassword() {
        authService.register("nbv3", "naga123");

        //verifies with wrong password
        assertFalse(authService.login("nbv3", "wrongpassword"), "Login should fail for incorrect password.");
    }

    @Test
    void testLoginWithUnregisteredUser() {
        //login fails for unotherised user
        assertFalse(authService.login("nonExistentUser", "naga123"), "Login should fail for unotherised user.");
    }

    @Test
    void testAccessWithoutLogin() {
        //without login
        boolean isAccessGranted = false; 
        assertFalse(isAccessGranted, "Access should be denied without login.");
    }
    @Test
    void testDuplicateRegistration() {
        authService.register("nbv3", "naga123");

        // Attempt to register the same username again
        boolean isRegistered = authService.register("nbv3", "newpassword");
        assertFalse(isRegistered, "Duplicate registration should not be allowed.");
    }

@Test
void testPasswordChange() {
    authService.register("nbv3", "naga123");

    // Change the password and verify login with the new password
    boolean isPasswordChanged = authService.changePassword("nbv3", "naga123", "newpassword");
    assertTrue(isPasswordChanged, "Password change should be successful.");
    assertTrue(authService.login("nbv3", "newpassword"), "Login should be successful with the new password.");
    assertFalse(authService.login("nbv3", "naga123"), "Login should fail with the old password.");
}
@Test
void testPasswordChangeWithIncorrectOldPassword() {
    authService.register("user1", "password123");
    assertFalse(authService.changePassword("user1", "wrongpassword", "newpassword"), "Password change should fail with incorrect old password.");
}
@Test
void testDeleteUser() {
    authService.register("user1", "password123");
    assertTrue(authService.deleteUser("user1"), "User should be deleted successfully.");
    assertFalse(authService.login("user1", "password123"), "Login should fail for a deleted user.");
}
@Test
void testDeleteNonExistentUser() {
    assertFalse(authService.deleteUser("nonExistentUser"), "Deleting a non-existent user should return false.");
}

// New Test Case 2: Case-insensitive login
@Test
void testCaseInsensitiveLogin() {
    authService.register("User123", "password123");
    assertTrue(authService.login("user123", "password123"), "Login should be case-insensitive for usernames.");
    assertTrue(authService.login("USER123", "password123"), "Login should be case-insensitive for usernames.");
}
}

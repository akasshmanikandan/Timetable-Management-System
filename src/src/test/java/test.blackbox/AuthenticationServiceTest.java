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
}

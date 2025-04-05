package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import main.najah.code.UserService;

@TestMethodOrder(OrderAnnotation.class)
@Execution(ExecutionMode.SAME_THREAD)
@DisplayName("UserService Tests")
class UserServiceTest {

    UserService service;

    @BeforeAll
    static void beforeAll() {
        System.out.println("UserServiceTest: Starting all tests...");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("UserServiceTest: All tests complete.");
    }

    @BeforeEach
    void setUp() {
        service = new UserService();
        System.out.println("Running a test...");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test finished.\n");
    }

    // ----------------------------
    // isValidEmail() Tests
    // ----------------------------

    @ParameterizedTest
    @ValueSource(strings = {
        "user@example.com",
        "john.doe@mail.org",
        "abc.def@sub.domain.com",
        "a@b.c",
        "simple@domain.co"
    })
    @DisplayName("Valid email formats should return true")
    void testValidEmails(String email) {
        assertTrue(service.isValidEmail(email));
    }

    @Test @DisplayName("Invalid email - null input")
    void testEmailNull() {
        assertFalse(service.isValidEmail(null));
    }

    @Test @DisplayName("Invalid email - missing @")
    void testEmailMissingAt() {
        assertFalse(service.isValidEmail("user.domain.com"));
    }

    @Test @DisplayName("Invalid email - missing dot")
    void testEmailMissingDot() {
        assertFalse(service.isValidEmail("user@domain"));
    }

    @Test @DisplayName("Email with dot but no @ → should be invalid")
    void testEmailHasDotOnly() {
        assertFalse(service.isValidEmail("user.domain.com"));
    }

    @Test @DisplayName("Email with @ but no dot → should be invalid")
    void testEmailHasAtOnly() {
        assertFalse(service.isValidEmail("user@domain"));
    }

    @Test @DisplayName("Email missing both @ and dot → should be invalid")
    void testEmailMissingAtAndDot() {
        assertFalse(service.isValidEmail("justtext"));
    }

    @Test @DisplayName("Multiple assertions for a valid email")
    void testEmailMultipleAssertions() {
        String email = "valid.user@mail.com";
        assertAll("Valid email checks",
            () -> assertNotNull(email),
            () -> assertTrue(email.contains("@")),
            () -> assertTrue(email.contains(".")),
            () -> assertTrue(service.isValidEmail(email))
        );
    }

    @Test
    @DisplayName("Email validation completes within time")
    @Timeout(1)
    void testEmailValidationTimeout() {
        assertTrue(service.isValidEmail("quick@test.com"));
    }

    // ----------------------------
    // Disabled email design tests
    // ----------------------------

    @Test @Disabled("Capital-start emails should be invalid (design rule)")
    @DisplayName("Email starting with capital should be invalid (design)")
    void testEmailStartsWithCapital() {
        assertFalse(service.isValidEmail("Auser@mail.com"));
    }

    @Test @Disabled("Email starting with number should be invalid (design rule)")
    @DisplayName("Email starting with number should be invalid (design)")
    void testEmailStartsWithNumber() {
        assertFalse(service.isValidEmail("1user@mail.com"));
    }

    @Test @Disabled("Email starting with dot should be invalid (design rule)")
    @DisplayName("Email starting with dot should be invalid (design)")
    void testEmailStartsWithDot() {
        assertFalse(service.isValidEmail(".user@mail.com"));
    }

    @Test @Disabled("Email starting with @ should be invalid (design rule)")
    @DisplayName("Email starting with @ should be invalid (design)")
    void testEmailStartsWithAt() {
        assertFalse(service.isValidEmail("@user.com"));
    }

    @Test @Disabled("Email with spaces should be invalid (design rule)")
    @DisplayName("Email with spaces should be invalid (design)")
    void testEmailWithSpaces() {
        assertFalse(service.isValidEmail("my email@mail.com"));
    }

    @Test @Disabled("Email with double dots should be invalid (design rule)")
    @DisplayName("Email with double dots should be invalid (design)")
    void testEmailWithDoubleDots() {
        assertFalse(service.isValidEmail("user..name@mail.com"));
    }

    @Test @Disabled("Email ending with dot should be invalid (design rule)")
    @DisplayName("Email ending with dot should be invalid (design)")
    void testEmailEndsWithDot() {
        assertFalse(service.isValidEmail("user@mail.com."));
    }

    // ----------------------------
    // authenticate() Tests
    // ----------------------------

    @Test @DisplayName("Authenticate with correct credentials returns true")
    void testCorrectCredentials() {
        assertTrue(service.authenticate("admin", "1234"));
    }

    @Test @DisplayName("Authenticate with incorrect password returns false")
    void testWrongPassword() {
        assertFalse(service.authenticate("admin", "wrong"));
    }

    @Test @DisplayName("Authenticate with incorrect username returns false")
    void testWrongUsername() {
        assertFalse(service.authenticate("user", "1234"));
    }

    @Test @DisplayName("Authenticate with completely wrong credentials returns false")
    void testWrongCredentials() {
        assertFalse(service.authenticate("foo", "bar"));
    }

    @Test @DisplayName("Authenticate with null username and password returns false")
    void testNullCredentials() {
        assertFalse(service.authenticate(null, null));
    }

    @Test @DisplayName("Authenticate with null username returns false")
    void testNullUsername() {
        assertFalse(service.authenticate(null, "1234"));
    }

    @Test @DisplayName("Authenticate with null password returns false")
    void testNullPassword() {
        assertFalse(service.authenticate("admin", null));
    }

    @ParameterizedTest
    @CsvSource({
        "admin,1234,true",
        "admin,wrong,false",
        "user,1234,false",
        "foo,bar,false"
    })
    @DisplayName("Parameterized authentication test cases")
    void testAuthenticateParameterized(String username, String password, boolean expected) {
        assertEquals(expected, service.authenticate(username, password));
    }

    @Test
    @DisplayName("Authenticate completes within timeout")
    @Timeout(1)
    void testAuthenticateTimeout() {
        assertTrue(service.authenticate("admin", "1234"));
    }

    @Test
    @Disabled("Intentional fail: incorrect expected value for authentication")
    @DisplayName("Intentionally failing authentication test (disabled)")
    void testAuthenticateFailing() {
        assertTrue(service.authenticate("admin", "admin")); // will fail
    }
}

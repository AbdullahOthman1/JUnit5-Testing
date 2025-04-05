package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.najah.code.Calculator;

@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Calculator Tests")
public class CalculatorTest {

    Calculator calc;

    @BeforeEach
    void setUp() {
        calc = new Calculator();
    }

    @Test
    @Order(1)
    @Disabled("This test fails because add() accepts empty input, but we want to disallow it. Would need source code change.")
    @DisplayName("Calling add() with no parameters should not be allowed (design preference)")
    void testAddNoParametersInvalid() {
        assertThrows(IllegalArgumentException.class, () -> calc.add());
    }

    @Test
    @Order(2)
    @Disabled("This test fails because add() allows only one parameter, but we want to disallow it. Would need source code change.")
    @DisplayName("Calling add() with one parameter should not be allowed (design preference)")
    void testAddOneParameterInvalid() {
        assertThrows(IllegalArgumentException.class, () -> calc.add(5));
    }

    @Test
    @Order(3)
    @DisplayName("Add two integers")
    void testAddTwoParameters() {
        assertEquals(9, calc.add(4, 5));
    }

    @Test
    @Order(4)
    @DisplayName("Add multiple integers")
    void testAddMultipleParameters() {
        assertEquals(15, calc.add(1, 2, 3, 4, 5));
    }

    @Test
    @Order(5)
    @DisplayName("Add all negative numbers")
    void testAddAllNegative() {
        assertEquals(-15, calc.add(-5, -3, -7));
    }

    @Test
    @Order(6)
    @DisplayName("Sum is negative when negative numbers dominate")
    void testAddNegativesDominate() {
        assertTrue(calc.add(-10, 5) < 0);
    }

    @Test
    @Order(7)
    @DisplayName("Sum is positive when positive numbers dominate")
    void testAddPositivesDominate() {
        assertTrue(calc.add(10, -2, -3) > 0);
    }

    @Test
    @Order(8)
    @DisplayName("Add all zeros")
    void testAddAllZeros() {
        assertEquals(0, calc.add(0, 0, 0));
    }

    @ParameterizedTest(name = "Sum of {0} and {1} should be {2}")
    @CsvSource({
        "1, 2, 3",
        "-1, -1, -2",
        "5, -2, 3",
        "0, 0, 0"
    })
    @Order(9)
    @DisplayName("Parameterized test for addition")
    void testAddParameterized(int a, int b, int expected) {
        assertEquals(expected, calc.add(a, b));
    }

    @Test
    @Order(10)
    @Timeout(1)
    @DisplayName("Add with timeout")
    void testAddWithTimeout() {
        assertEquals(6, calc.add(1, 2, 3));
    }

    @Test
    @Order(11)
    @Disabled("This test fails because the expected result is wrong. Fix: expect 6, not 5")
    @DisplayName("Intentionally failing test - should be fixed")
    void testAddFailing() {
        assertEquals(5, calc.add(1, 2, 3)); // Incorrect on purpose
    }
    
    @Test
    @Order(12)
    @DisplayName("Add with integer overflow (MAX + 1)")
    void testAddIntegerOverflowPositive() {
        int result = calc.add(Integer.MAX_VALUE, 1);
        assertEquals(Integer.MIN_VALUE, result, "Expected overflow to Integer.MIN_VALUE");
    }

    @Test
    @Order(13)
    @DisplayName("Add with integer underflow (MIN - 1)")
    void testAddIntegerOverflowNegative() {
        int result = calc.add(Integer.MIN_VALUE, -1);
        assertEquals(Integer.MAX_VALUE, result, "Expected underflow to Integer.MAX_VALUE");
    }

    @Test
    @Order(14)
    @DisplayName("Add max and -1 within range")
    void testAddMaxAndMinusOne() {
        int result = calc.add(Integer.MAX_VALUE, -1);
        assertEquals(2147483646, result);
    }
    
    // ----------------------
    // DIVIDE METHOD TESTS
    // ----------------------

    @Test
    @Order(15)
    @DisplayName("Divide two positive integers")
    void testDividePositive() {
        assertEquals(5, calc.divide(10, 2));
    }

    @Test
    @Order(16)
    @DisplayName("Divide returns floored result")
    void testDivideFloor() {
        assertEquals(3, calc.divide(7, 2));
    }

    @Test
    @Order(17)
    @DisplayName("Divide with negative numerator")
    void testDivideNegativeNumerator() {
        assertEquals(-2, calc.divide(-6, 3));
    }

    @Test
    @Order(18)
    @DisplayName("Divide with negative denominator")
    void testDivideNegativeDenominator() {
        assertEquals(-3, calc.divide(9, -3));
    }

    @Test
    @Order(19)
    @DisplayName("Divide with both negative values")
    void testDivideBothNegative() {
        assertEquals(4, calc.divide(-8, -2));
    }

    @Test
    @Order(20)
    @DisplayName("Divide zero by number")
    void testDivideZeroNumerator() {
        assertEquals(0, calc.divide(0, 100));
    }

    @Test
    @Order(21)
    @DisplayName("Divide by zero should throw ArithmeticException")
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> calc.divide(5, 0));
    }

    @Test
    @Order(22)
    @DisplayName("Divide using Integer.MAX_VALUE")
    void testDivideMaxInt() {
        assertEquals(1, calc.divide(Integer.MAX_VALUE, Integer.MAX_VALUE));
    }

    @Test
    @Order(23)
    @DisplayName("Divide using Integer.MIN_VALUE")
    void testDivideMinInt() {
        assertEquals(1, calc.divide(Integer.MIN_VALUE, Integer.MIN_VALUE));
    }

    @ParameterizedTest(name = "{0} / {1} = {2}")
    @CsvSource({
        "100, 10, 10",
        "-9, 3, -3",
        "12, -4, -3"
    })
    @Order(24)
    @DisplayName("Parameterized divide tests")
    void testDivideParameterized(int a, int b, int expected) {
        assertEquals(expected, calc.divide(a, b));
    }

    @Test
    @Order(25)
    @Timeout(1)
    @DisplayName("Divide with timeout")
    void testDivideWithTimeout() {
        assertEquals(2, calc.divide(100, 50));
    }

    @Test
    @Order(26)
    @Disabled("This test can't compile because Java doesn't allow calling divide with one parameter. Included for logical completeness.")
    @DisplayName("Divide with one parameter should not be allowed (design preference)")
    void testDivideOneParameter() {
        // This won't compile, so the test is intentionally disabled.
        // assertEquals(5, calc.divide(5));
    }
    
    // ----------------------
    // FACTORIAL METHOD TESTS
    // ----------------------

    @Test
    @Order(27)
    @DisplayName("Factorial of 0 is 1")
    void testFactorialZero() {
        assertEquals(1, calc.factorial(0));
    }

    @Test
    @Order(28)
    @DisplayName("Factorial of 1 is 1")
    void testFactorialOne() {
        assertEquals(1, calc.factorial(1));
    }

    @Test
    @Order(29)
    @DisplayName("Factorial of 5 with multiple assertions")
    void testFactorialFive() {
        int result = calc.factorial(5);
        
        assertAll("Factorial of 5 checks",
            () -> assertEquals(120, result, "Correct factorial value"),
            () -> assertTrue(result > 0, "Result should be positive"),
            () -> assertTrue(result < Integer.MAX_VALUE, "Result should not overflow")
        );
    }


    @Test
    @Order(30)
    @DisplayName("Factorial of 10 is 3628800")
    void testFactorialTen() {
        assertEquals(3628800, calc.factorial(10));
    }

    @Test
    @Order(31)
    @DisplayName("Factorial of 12 is within int limit")
    void testFactorialTwelve() {
        assertEquals(479001600, calc.factorial(12));
    }

    @Test
    @Order(32)
    @DisplayName("Factorial of 13 causes overflow (result wraps)")
    void testFactorialOverflow() {
        int result = calc.factorial(13);
        assertNotEquals(6227020800L, result, "Expected overflow for int type");
    }

    @Test
    @Order(33)
    @DisplayName("Factorial with negative input throws exception")
    void testFactorialNegativeInput() {
        assertThrows(IllegalArgumentException.class, () -> calc.factorial(-3));
    }

    @Test
    @Order(34)
    @DisplayName("Factorial of large number overflows")
    void testFactorialLargeInput() {
        int result = calc.factorial(20); // Overflows
        assertTrue(result < 0 || result == 0, "Expected overflow behavior");
    }

    @ParameterizedTest(name = "Factorial of {0} should be {1}")
    @CsvSource({
        "0, 1",
        "1, 1",
        "2, 2",
        "3, 6",
        "4, 24",
        "5, 120"
    })
    @Order(35)
    @DisplayName("Parameterized test for factorial correctness")
    void testFactorialParameterized(int input, int expected) {
        assertEquals(expected, calc.factorial(input));
    }

    @Test
    @Order(36)
    @Disabled("Java does not allow calling factorial with more than one argument. This test is logically invalid.")
    @DisplayName("Factorial with multiple arguments should not be allowed (design preference)")
    void testFactorialMultipleArguments() {
        // This won't compile: factorial(3, 4)
        // calc.factorial(3, 4);
    }

}

package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.najah.code.Product;

@DisplayName("Product Tests")
public class ProductTest {

    Product p;

    @Test
    @DisplayName("Valid product constructor sets name and price correctly")
    void testValidConstructor() {
        p = new Product("Book", 10.0);
        assertAll("Valid product",
            () -> assertEquals("Book", p.getName()),
            () -> assertEquals(10.0, p.getPrice()),
            () -> assertEquals(0.0, p.getDiscount())
        );
    }

    @Test
    @Disabled("Price 0 should be disallowed (design choice). Source code currently allows it.")
    @DisplayName("Creating product with price = 0 should be invalid (design preference)")
    void testZeroPriceInvalid() {
        new Product("Water", 0.0);
    }

    @Test
    @DisplayName("Negative price should throw IllegalArgumentException")
    void testNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> new Product("Gold", -5.0));
    }

    @Test
    @Disabled("Empty name should be disallowed (design choice). Source code currently allows it.")
    @DisplayName("Empty name should be invalid (design preference)")
    void testEmptyNameInvalid() {
        new Product("", 10.0);
    }

    @Test
    @Disabled("Null name should be disallowed (design choice). Source code currently allows it.")
    @DisplayName("Null name should be invalid (design preference)")
    void testNullNameInvalid() {
        new Product(null, 15.0);
    }
    // ----------------------
    // applyDiscount() tests
    // ----------------------

    @Test
    @DisplayName("Apply valid discount of 10%")
    void testValidDiscount() {
        p = new Product("Laptop", 1000.0);
        p.applyDiscount(10.0);
        assertEquals(10.0, p.getDiscount());
    }

    @Test
    @DisplayName("Apply 0% discount")
    void testZeroPercentDiscount() {
        p = new Product("Pen", 2.0);
        p.applyDiscount(0.0);
        assertEquals(0.0, p.getDiscount());
    }

    @Test
    @DisplayName("Apply 50% discount")
    void testMaxAllowedDiscount() {
        p = new Product("Phone", 800.0);
        p.applyDiscount(50.0);
        assertEquals(50.0, p.getDiscount());
    }

    @Test
    @DisplayName("Apply negative discount should throw exception")
    void testNegativeDiscount() {
        p = new Product("TV", 1000.0);
        assertThrows(IllegalArgumentException.class, () -> p.applyDiscount(-5.0));
    }

    @Test
    @DisplayName("Apply discount over 50% should throw exception")
    void testOverMaxDiscount() {
        p = new Product("Camera", 600.0);
        assertThrows(IllegalArgumentException.class, () -> p.applyDiscount(60.0));
    }

    @Test
    @DisplayName("Apply 25% discount and check discount + final price")
    void testApplyDiscountMultipleAssertions() {
        p = new Product("Shoes", 200.0);
        p.applyDiscount(25.0);

        assertAll("Discounted product",
            () -> assertEquals(25.0, p.getDiscount()),
            () -> assertEquals(150.0, p.getFinalPrice())
        );
    }

    // ----------------------
    // getFinalPrice() tests
    // ----------------------

    @Test
    @DisplayName("Final price without discount is original price")
    void testFinalPriceNoDiscount() {
        p = new Product("Item", 100.0);
        assertEquals(100.0, p.getFinalPrice());
    }

    @Test
    @DisplayName("Final price after 50% discount")
    void testFinalPriceHalfOff() {
        p = new Product("Dress", 80.0);
        p.applyDiscount(50.0);
        assertEquals(40.0, p.getFinalPrice());
    }

    @Test
    @DisplayName("Final price after 25% discount")
    void testFinalPrice25Percent() {
        p = new Product("Jacket", 100.0);
        p.applyDiscount(25.0);
        assertEquals(75.0, p.getFinalPrice());
    }

    @Test
    @DisplayName("Final price after 0% discount")
    void testFinalPriceZeroPercent() {
        p = new Product("Mouse", 99.9);
        p.applyDiscount(0.0);
        assertEquals(99.9, p.getFinalPrice());
    }

    @Test
    @DisplayName("Final price with small decimal discount")
    void testFinalPriceTinyDiscount() {
        p = new Product("Notebook", 100.0);
        p.applyDiscount(0.1);
        assertEquals(99.9, p.getFinalPrice(), 0.01); // small delta for precision
    }

    @Test
    @DisplayName("getFinalPrice() completes within timeout")
    @Timeout(1) // seconds
    void testFinalPriceWithTimeout() {
        p = new Product("Watch", 250.0);
        p.applyDiscount(20.0);
        assertEquals(200.0, p.getFinalPrice());
    }
    
    @ParameterizedTest(name = "Price: {0}, Discount: {1}, Expected Final: {2}")
    @CsvSource({
        "100.0, 10.0, 90.0",
        "200.0, 50.0, 100.0",
        "150.0, 0.0, 150.0",
        "80.0, 25.0, 60.0"
    })
    @DisplayName("Parameterized test for getFinalPrice() with various discounts")
    void testFinalPriceParameterized(double price, double discount, double expected) {
        p = new Product("Test", price);
        p.applyDiscount(discount);
        assertEquals(expected, p.getFinalPrice(), 0.01); // small delta for floating-point rounding
    }
}

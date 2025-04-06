package main.najah.test;

import main.najah.code.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Recipe Class Tests")
@Execution(ExecutionMode.CONCURRENT)
public class RecipeTest {

    Recipe recipe;

    @BeforeEach
    void setUp() {
        recipe = new Recipe();
    }

    @Test
    @DisplayName("Set and get name")
    void testNameGetterSetter() {
        recipe.setName("Caramel");
        assertEquals("Caramel", recipe.getName());
    }

    @Test
    @DisplayName("Set name to null should keep previous value")
    void testSetNameNullKeepsPrevious() {
        recipe.setName("Latte");
        recipe.setName(null);
        assertEquals("Latte", recipe.getName());
    }

    @Test
    @DisplayName("Price setter accepts valid number")
    void testValidPriceSet() throws RecipeException {
        recipe.setPrice("20");
        assertEquals(20, recipe.getPrice());
    }

    @Test
    @DisplayName("Price setter throws exception for negative")
    void testNegativePrice() {
        assertThrows(RecipeException.class, () -> recipe.setPrice("-10"));
    }

    @Test
    @DisplayName("Price setter throws exception for non-numeric")
    void testNonNumericPrice() {
        assertThrows(RecipeException.class, () -> recipe.setPrice("abc"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"10", "0", "100"})
    @DisplayName("Valid values for amtMilk")
    void testValidAmtMilk(String milk) throws RecipeException {
        recipe.setAmtMilk(milk);
        assertEquals(Integer.parseInt(milk), recipe.getAmtMilk());
    }

    @Test
    @DisplayName("Invalid amtSugar throws exception")
    void testInvalidAmtSugar() {
        assertThrows(RecipeException.class, () -> recipe.setAmtSugar("-5"));
        assertThrows(RecipeException.class, () -> recipe.setAmtSugar("sugar"));
    }

    @Test
    @DisplayName("Test equals() with same name")
    void testEqualsSameName() {
        Recipe r1 = new Recipe();
        Recipe r2 = new Recipe();
        r1.setName("Espresso");
        r2.setName("Espresso");
        assertEquals(r1, r2);
    }

    @Test
    @DisplayName("Test equals() with different names returns false")
    void testEqualsDifferentName() {
        Recipe r1 = new Recipe();
        Recipe r2 = new Recipe();
        r1.setName("Mocha");
        r2.setName("Latte");
        assertNotEquals(r1, r2);
    }

    @Test
    @DisplayName("Test equals() with null and different type")
    void testEqualsWithNullAndOtherType() {
        assertNotEquals(recipe, null);
        assertNotEquals(recipe, "not a recipe");
    }

    @Test
    @DisplayName("Test toString() returns name")
    void testToString() {
        recipe.setName("Americano");
        assertEquals("Americano", recipe.toString());
    }

    @Test
    @Timeout(1)
    @DisplayName("Timeout test for setting all fields")
    void testTimeoutSetAllFields() throws RecipeException {
        recipe.setAmtMilk("1");
        recipe.setAmtCoffee("1");
        recipe.setAmtSugar("1");
        recipe.setAmtChocolate("1");
        recipe.setPrice("5");
    }

    @ParameterizedTest
    @CsvSource({
        "5, 1, 1, 1, 1",
        "0, 0, 0, 0, 0",
        "10, 2, 3, 1, 4"
    })
    @DisplayName("Parameterized test for valid complete recipe")
    void testParameterizedCompleteRecipe(String price, String coffee, String milk, String sugar, String chocolate) throws RecipeException {
        recipe.setPrice(price);
        recipe.setAmtCoffee(coffee);
        recipe.setAmtMilk(milk);
        recipe.setAmtSugar(sugar);
        recipe.setAmtChocolate(chocolate);

        assertAll("Recipe fields",
            () -> assertEquals(Integer.parseInt(price), recipe.getPrice()),
            () -> assertEquals(Integer.parseInt(coffee), recipe.getAmtCoffee()),
            () -> assertEquals(Integer.parseInt(milk), recipe.getAmtMilk()),
            () -> assertEquals(Integer.parseInt(sugar), recipe.getAmtSugar()),
            () -> assertEquals(Integer.parseInt(chocolate), recipe.getAmtChocolate())
        );
    }

    @Test
    @DisplayName("Invalid inputs for multiple ingredients throw exceptions")
    void testMultipleInvalidInputs() {
        assertAll("Invalid ingredient inputs",
            () -> assertThrows(RecipeException.class, () -> recipe.setAmtMilk("-1")),
            () -> assertThrows(RecipeException.class, () -> recipe.setAmtSugar("abc")),
            () -> assertThrows(RecipeException.class, () -> recipe.setAmtChocolate("-3")),
            () -> assertThrows(RecipeException.class, () -> recipe.setAmtCoffee("xyz"))
        );
    }

    @Test
    @DisplayName("Test hashCode is consistent with name")
    void testHashCodeMatchesName() {
        Recipe r1 = new Recipe();
        Recipe r2 = new Recipe();
        r1.setName("Hazelnut");
        r2.setName("Hazelnut");
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    @DisplayName("Test hashCode with null name")
    void testHashCodeNullName() {
        Recipe r = new Recipe();
        r.setName(null); // ignored internally
        assertDoesNotThrow(() -> r.hashCode());
    }
} 

package main.najah.test;

import main.najah.code.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@TestMethodOrder(OrderAnnotation.class)
@Execution(ExecutionMode.CONCURRENT)
@DisplayName("RecipeBook Full Test")
public class RecipeBookTest {

    RecipeBook book;
    Recipe recipe;

    @BeforeEach
    void setUp() {
        book = new RecipeBook();
        recipe = new Recipe();
        recipe.setName("Mocha");
    }

    // ---------------------
    // addRecipe tests
    // ---------------------

    @Test
    @DisplayName("Add valid recipe returns true")
    void testAddValidRecipe() {
        assertTrue(book.addRecipe(recipe));
    }

    @Test
    @DisplayName("Adding same recipe twice returns false")
    void testAddDuplicateRecipe() {
        book.addRecipe(recipe);
        assertFalse(book.addRecipe(recipe));
    }

    @Test
    @DisplayName("Add 5 recipes — last one should fail")
    void testAddMoreThanFourRecipes() {
        for (int i = 1; i <= 4; i++) {
            Recipe r = new Recipe();
            r.setName("Recipe" + i);
            assertTrue(book.addRecipe(r));
        }

        Recipe r5 = new Recipe();
        r5.setName("Overflow");
        assertFalse(book.addRecipe(r5));
    }

    @Test
    @DisplayName("Add different object with same name should return false")
    void testAddRecipeSameNameDifferentObject() {
        Recipe r1 = new Recipe();
        r1.setName("Choco");

        Recipe r2 = new Recipe();
        r2.setName("Choco");

        assertTrue(book.addRecipe(r1));
        assertFalse(book.addRecipe(r2));
    }

    @Test
    @DisplayName("Adding null recipe should throw NullPointerException")
    void testAddNullRecipe() {
        assertThrows(NullPointerException.class, () -> book.addRecipe(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Espresso", "Latte", "Cappuccino", "Americano"})
    @DisplayName("Add multiple valid recipes with different names")
    void testAddMultipleNamedRecipes(String name) {
        Recipe r = new Recipe();
        r.setName(name);
        assertTrue(book.addRecipe(r));
    }

    // ---------------------
    // deleteRecipe tests
    // ---------------------

    @Test
    @DisplayName("Delete recipe at valid index returns recipe name")
    void testDeleteValidRecipe() {
        recipe.setName("Latte");
        book.addRecipe(recipe);
        assertEquals("Latte", book.deleteRecipe(0));
    }

    @Test
    @DisplayName("Delete from empty index returns null")
    void testDeleteFromEmptySlot() {
        assertNull(book.deleteRecipe(1));
    }

    @Test
    @DisplayName("Delete same index twice: first returns name, second returns null")
    void testDeleteSameIndexTwice() {
        recipe.setName("Mocha");
        book.addRecipe(recipe);
        assertEquals("Mocha", book.deleteRecipe(0));
        assertNull(book.deleteRecipe(0));
    }

    @Test
    @DisplayName("Deleting out of bounds should throw exception")
    void testDeleteOutOfBounds() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> book.deleteRecipe(5));
    }

    // ---------------------
    // editRecipe tests
    // ---------------------

    @Test
    @DisplayName("Edit a recipe at valid index updates recipe and returns old name")
    void testEditValidRecipe() {
        Recipe r1 = new Recipe();
        r1.setName("Cappuccino");
        book.addRecipe(r1);

        Recipe r2 = new Recipe();
        r2.setName("NewCappuccino");

        assertEquals("Cappuccino", book.editRecipe(0, r2));
    }

    @Test
    @DisplayName("Edit empty index returns null")
    void testEditEmptySlot() {
        Recipe r = new Recipe();
        r.setName("Vanilla");
        assertNull(book.editRecipe(1, r));
    }

    @Test
    @DisplayName("Edit with out-of-bounds index throws exception")
    void testEditOutOfBounds() {
        Recipe r = new Recipe();
        r.setName("Hazelnut");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> book.editRecipe(5, r));
    }

    // ---------------------
    // getRecipes tests
    // ---------------------

    @Test
    @DisplayName("Retrieve all recipes after creation (should be all null)")
    void testGetRecipesInitiallyEmpty() {
        Recipe[] recipes = book.getRecipes();
        for (Recipe r : recipes) {
            assertNull(r);
        }
    }

    @Test
    @DisplayName("Retrieve recipes after add – multiple assertions on recipe")
    void testGetRecipesAfterAdd() {
        book.addRecipe(recipe);
        Recipe[] recipes = book.getRecipes();
        Recipe r = recipes[0];

        assertAll("Check added recipe properties",
            () -> assertNotNull(r),
            () -> assertEquals("Mocha", r.getName()),
            () -> assertEquals(0, r.getAmtMilk()),
            () -> assertEquals(0, r.getAmtSugar()),
            () -> assertEquals(0, r.getAmtChocolate()),
            () -> assertEquals(0, r.getAmtCoffee())
        );
    }



    // ---------------------
    // timeout test
    // ---------------------

    @Test
    @Timeout(1)
    @DisplayName("Quick timeout test on recipe retrieval")
    void testTimeoutGetRecipes() {
        book.getRecipes();
    }


    
}

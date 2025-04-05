# ☕ JUnit 5 Testing Suite - Software Testing Assignment

## 📚 Overview
This project demonstrates **JUnit 5-based unit testing** for a set of utility and domain-specific Java classes. The goal is to build robust test coverage for various modules with strong validation of edge cases and clean test design.

### 🔧 Source Classes (in `main.najah.code`)
- `Calculator.java` - Simple math utilities (add, divide, factorial)
- `Product.java` - Represents a product with price and discount logic
- `UserService.java` - Handles email validation and authentication
- `Recipe.java` - Represents a coffee recipe (name, price, and ingredients)
- `RecipeBook.java` - Stores up to 4 `Recipe` objects with methods to add, delete, and edit recipes
- `RecipeException.java` - Custom exception class for recipe validation

---

## ✅ JUnit 5 Testing Highlights
This project uses **JUnit 5** as the testing framework and includes the following:

- ✅ `@Test`, `@BeforeEach`, `@AfterEach`, `@BeforeAll`, `@AfterAll` for test lifecycle
- ✅ `@DisplayName` for human-readable test titles
- ✅ `@ParameterizedTest` with `@ValueSource` and `@CsvSource` for multiple input values
- ✅ `@Timeout` to ensure quick execution
- ✅ `assertAll()` for grouped assertions
- ✅ `@Disabled` to document design-only constraints
- ✅ `@Execution(CONCURRENT)` for parallel test execution
- ✅ Manual and automated testing of exception paths

---

## 🚀 How to Run
1. Open the project in IntelliJ or Eclipse
2. Right-click on the `main.najah.test` package → Run All Tests With Coverage
3. Inspect visual results and view class-by-class test metrics

---

## 📦 Extras
- `AllTests.java` can be configured to run everything via a test suite
- Tests use clean naming and structure
- Separate test classes for each source class

---

## 👨‍💻 Author
- Abdullah S. Othman
- Software Testing And Quality Assurance - Assignment 2

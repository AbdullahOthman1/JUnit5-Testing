package main.najah.test;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ CalculatorTest.class, ProductTest.class, RecipeBookTest.class, UserServiceTest.class })
public class AllTests {

}
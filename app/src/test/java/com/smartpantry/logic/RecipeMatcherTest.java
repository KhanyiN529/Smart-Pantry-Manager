package com.smartpantry.logic;
import com.smartpantry.data.*;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

public class RecipeMatcherTest {
    private Recipe recipe(String json) { return new Recipe("Test", json, "Prepare"); }
    private Ingredient stock(String name, double qty, String unit) { return new Ingredient(name, qty, unit, ""); }
    private String need(String name, double qty, String unit) {
        return "{\"name\":\""+name+"\",\"quantity\":"+qty+",\"unit\":\""+unit+"\"}";
    }
    @Test public void missingIngredientIsExcluded() {
        assertFalse(RecipeMatcher.matches(recipe("["+need("egg",2,"pcs")+","+need("bread",2,"slices")+"]"), Arrays.asList(stock("egg",2,"pcs"))));
    }
    @Test public void convertsMassAndCombinesEntries() {
        assertTrue(RecipeMatcher.matches(recipe("["+need("rice",750,"g")+"]"), Arrays.asList(stock("rice",0.5,"kg"),stock("rice",250,"g"))));
    }
    @Test public void convertsVolume() {
        assertTrue(RecipeMatcher.matches(recipe("["+need("milk",500,"ml")+"]"), Arrays.asList(stock("milk",0.5,"l"))));
    }
    @Test public void incompatibleDimensionsAreExcluded() {
        assertFalse(RecipeMatcher.matches(recipe("["+need("rice",100,"g")+"]"), Arrays.asList(stock("rice",100,"ml"))));
    }
    @Test public void pluralAndCaseAliasesMatch() {
        assertTrue(RecipeMatcher.matches(recipe("["+need("tomato",2,"pcs")+"]"), Arrays.asList(stock(" TOMATOES ",2,"pieces"))));
    }
    @Test public void insufficientQuantityIsExcluded() {
        assertFalse(RecipeMatcher.matches(recipe("["+need("egg",2,"pcs")+"]"), Arrays.asList(stock("egg",1.99,"pcs"))));
    }
    @Test public void repeatedRequirementsConsumeCombinedQuantity() {
        assertFalse(RecipeMatcher.matches(recipe("["+need("egg",2,"pcs")+","+need("egg",2,"pcs")+"]"), Arrays.asList(stock("egg",3,"pcs"))));
    }
    @Test public void expiredStockIsExcluded() {
        assertFalse(RecipeMatcher.matches(recipe("["+need("egg",2,"pcs")+"]"), Arrays.asList(new Ingredient("egg",2,"pcs","2000-01-01"))));
    }
    @Test public void malformedAndEmptyRecipesFailClosed() {
        assertFalse(RecipeMatcher.matches(recipe("[]"), Arrays.asList(stock("egg",2,"pcs"))));
        assertFalse(RecipeMatcher.matches(recipe("invalid"), Arrays.asList(stock("egg",2,"pcs"))));
    }
    @Test public void invalidQuantitiesAreIgnored() {
        assertFalse(RecipeMatcher.matches(recipe("["+need("egg",2,"pcs")+"]"), Arrays.asList(stock("egg",Double.NaN,"pcs"))));
    }
}

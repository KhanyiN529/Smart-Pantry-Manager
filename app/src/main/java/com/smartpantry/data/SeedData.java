package com.smartpantry.data;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SeedData {
    public static void ensureSeeded(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        db.runInTransaction(() -> {
        if (db.recipeDao().count() > 0) return;

        List<Recipe> recipes = createRecipes();
        for (Recipe r : recipes) {
            db.recipeDao().insert(r);
        }
        });
    }

    private static List<Recipe> createRecipes() {
        List<Recipe> list = new ArrayList<>();
        try {
            list.add(new Recipe("Egg Sandwich", buildIngredients(
                    item("egg", 2, "pcs"),
                    item("bread", 2, "slices"),
                    item("butter", 10, "g"),
                    item("cheese", 30, "g")
            ), "1. Fry the eggs until set.\n2. Toast the bread and spread butter.\n3. Add eggs and cheese, then sandwich together."));

            list.add(new Recipe("Tomato Pasta", buildIngredients(
                    item("pasta", 200, "g"),
                    item("tomato", 2, "pcs"),
                    item("garlic", 2, "cloves"),
                    item("olive oil", 15, "ml")
            ), "1. Boil pasta until tender.\n2. Fry garlic in olive oil.\n3. Add chopped tomato and simmer.\n4. Toss pasta into sauce and serve."));

            list.add(new Recipe("Veggie Omelette", buildIngredients(
                    item("egg", 3, "pcs"),
                    item("onion", 1, "pcs"),
                    item("pepper", 1, "pcs"),
                    item("spinach", 50, "g")
            ), "1. Chop the vegetables finely.\n2. Whisk the eggs.\n3. Fry the vegetables, add eggs, and cook until set."));

            list.add(new Recipe("Greek Salad", buildIngredients(
                    item("tomato", 2, "pcs"),
                    item("cucumber", 1, "pcs"),
                    item("onion", 1, "pcs"),
                    item("feta", 50, "g"),
                    item("olive oil", 15, "ml")
            ), "1. Slice the vegetables.\n2. Add feta.\n3. Dress with olive oil and serve chilled."));

            list.add(new Recipe("Chicken Stir Fry", buildIngredients(
                    item("chicken", 200, "g"),
                    item("rice", 150, "g"),
                    item("broccoli", 1, "head"),
                    item("soy sauce", 20, "ml")
            ), "1. Cook rice.\n2. Fry chicken until golden.\n3. Add broccoli and soy sauce.\n4. Toss together and serve."));

            list.add(new Recipe("Bean Quesadilla", buildIngredients(
                    item("tortilla", 2, "pcs"),
                    item("black beans", 150, "g"),
                    item("cheese", 60, "g"),
                    item("onion", 1, "pcs")
            ), "1. Warm tortillas.\n2. Fill with beans, onion and cheese.\n3. Fold and toast until crisp."));

            list.add(new Recipe("Tomato Soup", buildIngredients(
                    item("tomato", 4, "pcs"),
                    item("onion", 1, "pcs"),
                    item("garlic", 2, "cloves"),
                    item("stock", 300, "ml")
            ), "1. Simmer tomato, onion and garlic.\n2. Blend until smooth.\n3. Add stock and heat through."));

            list.add(new Recipe("Pesto Pasta", buildIngredients(
                    item("pasta", 200, "g"),
                    item("basil", 20, "g"),
                    item("parmesan", 40, "g"),
                    item("olive oil", 15, "ml")
            ), "1. Cook pasta.\n2. Blend basil, parmesan and oil into pesto.\n3. Toss with hot pasta and serve."));

            list.add(new Recipe("Chickpea Curry", buildIngredients(
                    item("chickpeas", 200, "g"),
                    item("coconut milk", 200, "ml"),
                    item("onion", 1, "pcs"),
                    item("curry powder", 10, "g")
            ), "1. Cook onion until soft.\n2. Add curry powder, chickpeas and coconut milk.\n3. Simmer until thick and fragrant."));

            list.add(new Recipe("Avocado Toast", buildIngredients(
                    item("bread", 2, "slices"),
                    item("avocado", 1, "pcs"),
                    item("lemon", 1, "pcs"),
                    item("chili flakes", 5, "g")
            ), "1. Toast bread.\n2. Mash avocado with lemon.\n3. Spread onto toast and season."));

            list.add(new Recipe("Potato Hash", buildIngredients(
                    item("potato", 2, "pcs"),
                    item("onion", 1, "pcs"),
                    item("pepper", 1, "pcs"),
                    item("olive oil", 15, "ml")
            ), "1. Dice potatoes and cook until golden.\n2. Add onion and pepper.\n3. Fry until crisp and serve."));

            list.add(new Recipe("Mushroom Risotto", buildIngredients(
                    item("rice", 200, "g"),
                    item("mushroom", 200, "g"),
                    item("stock", 500, "ml"),
                    item("parmesan", 30, "g")
            ), "1. Cook rice slowly with stock.\n2. Add mushrooms and stir.\n3. Finish with parmesan before serving."));

            list.add(new Recipe("Caesar Wrap", buildIngredients(
                    item("tortilla", 2, "pcs"),
                    item("chicken", 150, "g"),
                    item("lettuce", 1, "head"),
                    item("caesar dressing", 30, "ml")
            ), "1. Fill tortilla with lettuce and chicken.\n2. Add dressing.\n3. Fold and serve."));

            list.add(new Recipe("Lentil Soup", buildIngredients(
                    item("lentils", 150, "g"),
                    item("carrot", 2, "pcs"),
                    item("onion", 1, "pcs"),
                    item("stock", 400, "ml")
            ), "1. Sweat onion and carrot.\n2. Add lentils and stock.\n3. Simmer until soft and creamy."));

            list.add(new Recipe("Rice Bowl", buildIngredients(
                    item("rice", 150, "g"),
                    item("chickpeas", 150, "g"),
                    item("cucumber", 1, "pcs"),
                    item("tomato", 1, "pcs")
            ), "1. Cook rice.\n2. Add chickpeas and chopped vegetables.\n3. Toss lightly and serve."));

            list.add(new Recipe("Banana Oat Pancakes", buildIngredients(
                    item("banana", 2, "pcs"),
                    item("oats", 100, "g"),
                    item("egg", 2, "pcs"),
                    item("milk", 100, "ml")
            ), "1. Blend oats and banana.\n2. Mix in egg and milk.\n3. Cook in a pan until golden."));

            list.add(new Recipe("Cheese Toasties", buildIngredients(
                    item("bread", 2, "slices"),
                    item("cheese", 80, "g"),
                    item("butter", 10, "g"),
                    item("tomato", 1, "pcs")
            ), "1. Butter outside of bread.\n2. Add cheese and tomato.\n3. Grill until melted and crisp."));

            list.add(new Recipe("Chicken Noodle Soup", buildIngredients(
                    item("chicken", 150, "g"),
                    item("noodles", 150, "g"),
                    item("carrot", 2, "pcs"),
                    item("stock", 400, "ml")
            ), "1. Simmer chicken and carrot in stock.\n2. Add noodles and cook until tender.\n3. Serve warm."));

            list.add(new Recipe("Peanut Noodle Salad", buildIngredients(
                    item("noodles", 150, "g"),
                    item("peanut butter", 30, "g"),
                    item("cucumber", 1, "pcs"),
                    item("carrot", 2, "pcs")
            ), "1. Cook noodles and cool them.\n2. Mix peanut butter with a little water.\n3. Toss with vegetables and noodles."));

            list.add(new Recipe("Vegetable Curry", buildIngredients(
                    item("potato", 2, "pcs"),
                    item("spinach", 100, "g"),
                    item("onion", 1, "pcs"),
                    item("coconut milk", 200, "ml")
            ), "1. Fry onion and potato.\n2. Add coconut milk and spinach.\n3. Simmer until potatoes are cooked."));

            list.add(new Recipe("Tomato Basil Bruschetta", buildIngredients(
                    item("bread", 4, "slices"),
                    item("tomato", 2, "pcs"),
                    item("basil", 10, "g"),
                    item("olive oil", 15, "ml")
            ), "1. Toast bread.\n2. Mix diced tomato and basil with olive oil.\n3. Spoon over toast and serve."));

            list.add(new Recipe("Chicken Rice Soup", buildIngredients(
                    item("chicken", 150, "g"),
                    item("rice", 150, "g"),
                    item("celery", 2, "stalks"),
                    item("stock", 500, "ml")
            ), "1. Simmer celery and chicken in stock.\n2. Add rice and cook until tender.\n3. Serve hot."));
        } catch (Exception e) {
            throw new IllegalStateException("Invalid bundled recipe data", e);
        }
        return list;
    }

    private static String buildIngredients(JSONObject... items) {
        JSONArray arr = new JSONArray();
        for (JSONObject item: items) {
            arr.put(item);
        }
        return arr.toString();
    }

    private static JSONObject item(String name, double quantity, String unit) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("name", name);
            obj.put("quantity", quantity);
            obj.put("unit", unit);
        } catch (Exception e) {
            throw new IllegalStateException("Invalid bundled recipe data", e);
        }
        return obj;
    }
}

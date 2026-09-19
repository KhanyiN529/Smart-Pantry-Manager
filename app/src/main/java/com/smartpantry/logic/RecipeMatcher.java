package com.smartpantry.logic;

import com.smartpantry.data.Ingredient;
import com.smartpantry.data.Recipe;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/** Strict matching: quantities are combined only within compatible measurement dimensions. */
public final class RecipeMatcher {
    private RecipeMatcher() { }

    public static String normalizeName(String value) {
        if (value == null) return "";
        String name = value.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", " ");
        // Explicit aliases avoid corrupting words such as cheese, rice and couscous.
        String[][] aliases = {{"tomatoes","tomato"},{"potatoes","potato"},{"eggs","egg"},
            {"onions","onion"},{"peppers","pepper"},{"mushrooms","mushroom"},
            {"carrots","carrot"},{"bananas","banana"},{"avocados","avocado"},
            {"lemons","lemon"},{"cucumbers","cucumber"},{"tortillas","tortilla"},
            {"chickpeas","chickpea"},{"lentils","lentil"},{"black beans","black bean"}};
        for (String[] alias : aliases) if (name.equals(alias[0])) return alias[1];
        return name;
    }

    private static String unit(String raw) {
        if (raw == null) return "";
        switch (raw.trim().toLowerCase(Locale.ROOT)) {
            case "g": case "gram": case "grams": return "g";
            case "kg": case "kilogram": case "kilograms": return "kg";
            case "ml": case "millilitre": case "millilitres": case "milliliter": case "milliliters": return "ml";
            case "l": case "litre": case "litres": case "liter": case "liters": return "l";
            case "pc": case "pcs": case "piece": case "pieces": case "each": return "pcs";
            case "slice": case "slices": return "slice";
            case "clove": case "cloves": return "clove";
            case "head": case "heads": return "head";
            case "stalk": case "stalks": return "stalk";
            default: return "";
        }
    }

    public static boolean supportedUnit(String value) { return !unit(value).isEmpty(); }

    private static boolean add(Map<String, Double> amounts, String name, double quantity, String rawUnit) {
        String u = unit(rawUnit);
        String n = normalizeName(name);
        if (n.isEmpty() || u.isEmpty() || (Double.isNaN(quantity) || Double.isInfinite(quantity)) || quantity <= 0) return false;
        if (u.equals("kg")) { quantity *= 1000; u = "g"; }
        if (u.equals("l")) { quantity *= 1000; u = "ml"; }
        String key = n + "|" + u;
        double total = amounts.getOrDefault(key, 0.0) + quantity;
        if ((Double.isNaN(total) || Double.isInfinite(total))) return false;
        amounts.put(key, total);
        return true;
    }

    public static boolean matches(Recipe recipe, List<Ingredient> pantry) {
        Map<String, Double> stock = new HashMap<>();
        Map<String, Double> required = new HashMap<>();
        for (Ingredient ingredient : pantry) {
            if (ingredient.expiry != null && !ingredient.expiry.isEmpty()) {
                try { if (LocalDate.parse(ingredient.expiry).isBefore(LocalDate.now())) continue; }
                catch (RuntimeException invalidDate) { continue; }
            }
            add(stock, ingredient.name, ingredient.quantity, ingredient.unit);
        }
        try {
            JSONArray ingredients = new JSONArray(recipe.ingredientsJson);
            if (ingredients.length() == 0) return false;
            for (int i = 0; i < ingredients.length(); i++) {
                JSONObject entry = ingredients.getJSONObject(i);
                if (!add(required, entry.getString("name"), entry.getDouble("quantity"), entry.getString("unit"))) return false;
            }
            for (Map.Entry<String, Double> entry : required.entrySet()) {
                if (stock.getOrDefault(entry.getKey(), 0.0) < entry.getValue()) return false;
            }
            return true;
        } catch (Exception malformedRecipe) { return false; }
    }
}

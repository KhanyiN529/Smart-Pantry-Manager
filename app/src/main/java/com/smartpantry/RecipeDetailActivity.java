package com.smartpantry;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.smartpantry.data.AppDatabase;
import com.smartpantry.data.Recipe;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        int id = getIntent().getIntExtra("recipe_id", -1);
        TextView txtName = findViewById(R.id.txtName);
        TextView txtIngredients = findViewById(R.id.txtIngredients);
        TextView txtSteps = findViewById(R.id.txtSteps);

        txtName.setText("Recipe unavailable");
        if (id != -1) {
            Recipe selectedRecipe = AppDatabase.getInstance(this).recipeDao().findById(id);

            if (selectedRecipe != null) {
                txtName.setText(selectedRecipe.name);
                txtIngredients.setText(formatIngredients(selectedRecipe.ingredientsJson));
                txtSteps.setText(selectedRecipe.steps.replace("\n", "\n\n"));
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() { finish(); return true; }

    private String formatIngredients(String json) {
        try {
            JSONArray arr = new JSONArray(json);
            List<String> lines = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject item = arr.getJSONObject(i);
                String name = item.optString("name", "");
                double qty = item.optDouble("quantity", 1);
                String unit = item.optString("unit", "");
                String line = "• " + name + " - " + qty + " " + unit;
                lines.add(line.trim());
            }
            return android.text.TextUtils.join("\n", lines);
        } catch (Exception e) {
            return json;
        }
    }
}

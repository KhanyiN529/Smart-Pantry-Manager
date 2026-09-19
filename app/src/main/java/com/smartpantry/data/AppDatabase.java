package com.smartpantry.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Ingredient.class, Recipe.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract IngredientDao ingredientDao();
    public abstract RecipeDao recipeDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "smartpantry.db")
                    .allowMainThreadQueries() // for simplicity in this assignment
                    .build();
        }
        return INSTANCE;
    }
}

package com.smartpantry.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM recipes ORDER BY name")
    List<Recipe> getAll();

    @Query("SELECT * FROM recipes WHERE id = :id LIMIT 1")
    Recipe findById(int id);

    @Insert
    void insert(Recipe recipe);

    @Query("SELECT COUNT(*) FROM recipes")
    int count();
}

package com.smartpantry.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface IngredientDao {
    @Query("SELECT * FROM ingredients ORDER BY name")
    List<Ingredient> getAll();

    @Insert
    void insert(Ingredient ingredient);

    @Update
    void update(Ingredient ingredient);

    @Delete
    void delete(Ingredient ingredient);

    @Query("SELECT * FROM ingredients WHERE name = :name LIMIT 1")
    Ingredient findByName(String name);

    @Query("SELECT * FROM ingredients WHERE id = :id LIMIT 1")
    Ingredient findById(int id);
}

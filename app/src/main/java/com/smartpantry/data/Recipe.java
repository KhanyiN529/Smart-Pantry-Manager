package com.smartpantry.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipes")
public class Recipe {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String ingredientsJson; // simple JSON array of {name,quantity,unit}
    public String steps;

    public Recipe(String name, String ingredientsJson, String steps) {
        this.name = name;
        this.ingredientsJson = ingredientsJson;
        this.steps = steps;
    }
}

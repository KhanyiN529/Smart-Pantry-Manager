package com.smartpantry.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredients")
public class Ingredient {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public double quantity;
    public String unit;
    public String expiry; // ISO date string, optional

    public Ingredient(String name, double quantity, String unit, String expiry) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiry = expiry;
    }
}

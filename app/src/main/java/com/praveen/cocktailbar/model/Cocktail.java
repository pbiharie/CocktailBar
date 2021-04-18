package com.praveen.cocktailbar.model;

import java.util.Hashtable;
import java.util.Optional;

public class Cocktail {
    private int id;
    private String cocktailName;
    private String drinkThumbnailURL;
    private String category;
    private String drinkType;
    private String glass;
    private String instruction;
    private Hashtable<String, String> ingredientAndMeasurement = new Hashtable<>();


    public Cocktail(int id, String cocktailName, String drinkThumbnailURL) {
        this.id = id;
        this.cocktailName = cocktailName;
        this.drinkThumbnailURL = drinkThumbnailURL;
    }

    public Cocktail(int id, String cocktailName, String drinkThumbnailURL, String category, String drinkType, String glass, String instruction, Hashtable<String, String> ingredientAndMeasurement) {
        this.id = id;
        this.cocktailName = cocktailName;
        this.drinkThumbnailURL = drinkThumbnailURL;
        this.category = category;
        this.drinkType = drinkType;
        this.glass = glass;
        this.instruction = instruction;
        this.ingredientAndMeasurement = ingredientAndMeasurement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCocktailName() {
        return cocktailName;
    }

    public void setCocktailName(String cocktailName) {
        this.cocktailName = cocktailName;
    }

    public String getDrinkThumbnailURL() {
        return drinkThumbnailURL;
    }

    public void setDrinkThumbnailURL(String drinkThumbnailURL) {
        this.drinkThumbnailURL = drinkThumbnailURL;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDrinkType() {
        return drinkType;
    }

    public void setDrinkType(String drinkType) {
        this.drinkType = drinkType;
    }

    public String getGlass() {
        return glass;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Hashtable<String, String> getIngredientAndMeasurement() {
        return ingredientAndMeasurement;
    }

    public void setIngredientAndMeasurement(Hashtable<String, String> ingredientAndMeasurement) {
        this.ingredientAndMeasurement = ingredientAndMeasurement;
    }
}

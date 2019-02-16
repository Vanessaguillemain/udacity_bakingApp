package com.example.android.bakingapp.model;

import com.example.android.bakingapp.R;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by vanessa on 16/02/2019.
 */

public class Recipe {
    private int id;
    private String name;
    @SerializedName("ingredients")
    private ArrayList<RecipeIngredient> recipeIngredients;
    @SerializedName("steps")
    private ArrayList<RecipeStep> recipeSteps;
    private String serving;
    private String image;
    private int imageTest;

    public Recipe(int id, String name, int imageTest) {
        this.id = id;
        this.name = name;
        this.imageTest = imageTest;
    }
    public Recipe(int id, String name, ArrayList<RecipeIngredient> recipeIngredients, ArrayList<RecipeStep> recipeSteps, String serving, String image) {
        this.id = id;
        this.name = name;
        this.recipeIngredients = recipeIngredients;
        this.recipeSteps = recipeSteps;
        this.serving = serving;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public ArrayList<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(ArrayList<RecipeIngredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public ArrayList<RecipeStep> getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(ArrayList<RecipeStep> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    public String getServing() {
        return serving;
    }

    public void setServing(String serving) {
        this.serving = serving;
    }

    public int getImageTest() {
        return R.drawable.image1;
    }

    public void setImageTest(int imageTest) {
        this.imageTest = imageTest;
    }


    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", recipeIngredients=" + recipeIngredients +
                ", recipeSteps=" + recipeSteps +
                ", serving='" + serving + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}

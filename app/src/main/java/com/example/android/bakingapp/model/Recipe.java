package com.example.android.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.bakingapp.R;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by vanessa on 16/02/2019.
 */

public class Recipe implements Parcelable{
    private int id;
    private String name;
    @SerializedName("ingredients")
    private ArrayList<RecipeIngredient> recipeIngredients;
    @SerializedName("steps")
    private ArrayList<RecipeStep> recipeSteps;
    private int servings;
    private String image;
    private int imageTest;

    public Recipe() {
        new Recipe(0,"", null, null, 0, "");
    }

    public Recipe(int id, String name, int imageTest) {
        this.id = id;
        this.name = name;
        this.imageTest = imageTest;
    }
    public Recipe(int id, String name, ArrayList<RecipeIngredient> recipeIngredients, ArrayList<RecipeStep> recipeSteps, int servings, String image) {
        this.id = id;
        this.name = name;
        this.recipeIngredients = recipeIngredients;
        this.recipeSteps = recipeSteps;
        this.servings = servings;
        this.image = image;
    }
    private Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        servings = in.readInt();
        image = in.readString();
        recipeIngredients = in.readArrayList(RecipeIngredient.class.getClassLoader());
        recipeSteps = in.readArrayList(RecipeStep.class.getClassLoader());
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        out.writeInt(servings);
        out.writeString(image);
        out.writeList(recipeIngredients);
        out.writeList(recipeSteps);
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

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
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
                ", servings='" + servings + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

}

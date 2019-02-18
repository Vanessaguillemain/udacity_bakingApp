package com.example.android.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vanessa on 16/02/2019.
 */

public class RecipeIngredient implements Parcelable {

    private double quantity;
    private String measure;
    private String ingredient;

    public RecipeIngredient(double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    private RecipeIngredient(Parcel in) {
        quantity = in.readDouble();
        measure = in.readString();
        ingredient = in.readString();
    }

    public static final Creator<RecipeIngredient> CREATOR = new Creator<RecipeIngredient>() {
        @Override
        public RecipeIngredient createFromParcel(Parcel in) {
            return new RecipeIngredient(in);
        }

        @Override
        public RecipeIngredient[] newArray(int size) {
            return new RecipeIngredient[size];
        }
    };

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeDouble(quantity);
        out.writeString(measure);
        out.writeString(ingredient);
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getFullStringIngredient() {
        return "- "+ quantity + " " + measure + " " + ingredient;
    }

    @Override
    public String toString() {
        return "RecipeIngredient{" +
                "quantity=" + quantity +
                ", measure='" + measure + '\'' +
                ", ingredient='" + ingredient + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }


}

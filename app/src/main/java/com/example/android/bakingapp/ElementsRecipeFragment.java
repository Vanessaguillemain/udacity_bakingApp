package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.RecipeIngredient;
import com.example.android.bakingapp.model.RecipeStep;

import java.util.ArrayList;

/**
 * Created by vanessa on 16/02/2019.
 */

public class ElementsRecipeFragment extends Fragment {

    private Recipe currentRecipe;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment
     */
    public ElementsRecipeFragment() {
    }

    /**
     * Inflates the fragment layout file and sets the correct resource for the image to display
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the Android-Me fragment layout
        View rootView = inflater.inflate(R.layout.fragment_list_elements, container, false);

        // Get a reference to the ImageView in the fragment layout
        TextView textViewIngr = (TextView) rootView.findViewById(R.id.tvIngredients);
        TextView textViewDesc = (TextView) rootView.findViewById(R.id.tvDescription);

        if(currentRecipe != null) {
            ArrayList<RecipeIngredient> ingredients = currentRecipe.getRecipeIngredients();
            ArrayList<RecipeStep> steps = currentRecipe.getRecipeSteps();

            textViewIngr.setText("Ingredients =" + ingredients.get(0));
            textViewDesc.setText("steps 1 =" + steps.get(0));

        }

        // Return the rootView
        return rootView;
    }

    public Recipe getCurrentRecipe() {
        return currentRecipe;
    }

    public void setCurrentRecipe(Recipe currentRecipe) {
        this.currentRecipe = currentRecipe;
    }
}

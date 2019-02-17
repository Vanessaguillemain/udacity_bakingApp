package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.RecipeIngredient;
import com.example.android.bakingapp.model.RecipeStep;

import java.util.ArrayList;

/**
 * Created by vanessa on 17/02/2019.
 */

public class PlayStepFragment extends Fragment {

    private Recipe currentRecipe;
    private int currentStep;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment
     */
    public PlayStepFragment() {
    }

    /**
     * Inflates the fragment layout file and sets the correct resource for the image to display
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the Android-Me fragment layout
        View rootView = inflater.inflate(R.layout.fragment_play_step, container, false);

        TextView textViewDescription = (TextView) rootView.findViewById(R.id.tvDescription);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.ivPlayer);

        if(currentRecipe != null) {
            ArrayList<RecipeStep> steps = currentRecipe.getRecipeSteps();
            textViewDescription.setText("Descriptions =" + steps.get(currentStep).getDescription());
        }
        // Return the root view
        return rootView;
    }

    public Recipe getCurrentRecipe() {
        return currentRecipe;
    }

    public void setCurrentRecipe(Recipe currentRecipe) {
        this.currentRecipe = currentRecipe;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }
}

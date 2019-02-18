package com.example.android.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.RecipeIngredient;
import com.example.android.bakingapp.model.RecipeStep;
import com.example.android.bakingapp.utils.Utils;

import java.util.ArrayList;

/**
 * Created by vanessa on 16/02/2019.
 */

public class RecipeDetailFragment extends Fragment {

    private Recipe currentRecipe;
    RecipeDetailAdapter mAdapter;
    GridView gridView;

    public int getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    private int currentStep = -1;

    // Define a new interface OnRecipeStepClickListener that triggers a callback in the host activity
    OnRecipeStepClickListener mCallback;

    // OnRecipeStepClickListener interface, calls a method in the host activity named onRecipeStepSelected
    public interface OnRecipeStepClickListener {
        void onRecipeStepSelected(int position, View view);
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment
     */
    public RecipeDetailFragment() {
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnRecipeStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnRecipeStepClickListener");
        }
    }

    /**
     * Inflates the fragment layout file and sets the correct resource for the image to display
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the Android-Me fragment layout
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        TextView textViewIngr = rootView.findViewById(R.id.tvIngredients);
        gridView = rootView.findViewById(R.id.steps_grid_view);
        if(savedInstanceState != null) {
            currentRecipe = savedInstanceState.getParcelable(Utils.BUNDLE_KEY_RECIPE);
            currentStep = savedInstanceState.getInt(Utils.BUNDLE_KEY_STEP_INDEX);
        }

        if(currentRecipe != null) {
            ArrayList<RecipeIngredient> ingredients = currentRecipe.getRecipeIngredients();
            ArrayList<RecipeStep> steps = currentRecipe.getRecipeSteps();
            //TODO
            textViewIngr.setText(getIngredientsString(ingredients));

            // Create the adapter
            // This adapter takes in the context and an ArrayList of ALL the image resources to display
            mAdapter = new RecipeDetailAdapter(getContext(), steps, currentStep);

            // Set the adapter on the GridView
            gridView.setAdapter(mAdapter);

            // Set a click listener on the gridView and trigger the callback onRecipeStepSelected when an item is clicked
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // Trigger the callback method and pass in the position that was clicked
                    mCallback.onRecipeStepSelected(position, view);
                }
            });
        }
        // Return the root view
        return rootView;
    }

    private String getIngredientsString(ArrayList<RecipeIngredient> ingredients) {
        String result ="";
        for(RecipeIngredient ingredient : ingredients){
            result += ingredient.getFullStringIngredient() + "\n";
        }
        return result;
    }

    public void setCurrentRecipe(Recipe currentRecipe) {
        this.currentRecipe = currentRecipe;
    }

    public RecipeDetailAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * Save the current state of this fragment
     */
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putParcelable(Utils.BUNDLE_KEY_RECIPE, currentRecipe);
        currentState.putInt(Utils.BUNDLE_KEY_STEP_INDEX, currentStep);
    }
}

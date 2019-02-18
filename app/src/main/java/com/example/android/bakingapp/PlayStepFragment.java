package com.example.android.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.RecipeStep;
import com.example.android.bakingapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vanessa on 17/02/2019.
 */

public class PlayStepFragment extends Fragment {

    private Recipe currentRecipe;
    private List<RecipeStep> recipeSteps;
    private int currentStep;
    private int stepsSize;
    private Button mBtnBefore;
    private Button mBtnAfter;
    private TextView mTextViewDescription;
    private boolean buttonsActivated ;

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

        if(savedInstanceState != null) {
            currentRecipe = savedInstanceState.getParcelable(Utils.BUNDLE_KEY_RECIPE);
            currentStep = savedInstanceState.getInt(Utils.BUNDLE_KEY_STEP_INDEX);
        }
        recipeSteps = currentRecipe.getRecipeSteps();
        stepsSize = recipeSteps.size();

        mTextViewDescription = (TextView) rootView.findViewById(R.id.tvDescription);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.ivPlayer);
        mBtnBefore = (Button) rootView.findViewById(R.id.btnBefore);
        mBtnAfter = (Button) rootView.findViewById(R.id.btnAfter);
        initButtonsState();
        if(buttonsActivated) {
            setButtons();
            mBtnBefore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadStepBefore();
                }
            });

            mBtnAfter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadStepAfter();
                }
            });
        } else {
            mBtnBefore.setVisibility(View.GONE);
            mBtnAfter.setVisibility(View.GONE);

        }
        if(currentRecipe != null) {
            recipeSteps = currentRecipe.getRecipeSteps();
            stepsSize = recipeSteps.size();
            if (currentStep >= 0) {
                mTextViewDescription.setText(recipeSteps.get(currentStep).getDescription());
            }
        }
        // Return the root view
        return rootView;
    }
    private void loadStepBefore() {
        currentStep = currentStep -1;
        mTextViewDescription.setText(recipeSteps.get(currentStep).getDescription());
        setButtons();
    }
    private void loadStepAfter() {
        currentStep = currentStep +1;
        mTextViewDescription.setText(recipeSteps.get(currentStep).getDescription());
        setButtons();
    }

    public Recipe getCurrentRecipe() {
        return currentRecipe;
    }

    public void setCurrentRecipe(Recipe currentRecipe) {
        this.currentRecipe = currentRecipe;
    }

    public void initButtonsState() {
        buttonsActivated = getResources().getBoolean(R.bool.buttons_visibility);
    }
    public int getCurrentStep() {
        return currentStep;
    }

    private void setButtons(){
        if(currentStep == 0) {
            mBtnBefore.setEnabled(false);
            mBtnAfter.setEnabled(true);
        } else if (currentStep == stepsSize-1){
            mBtnBefore.setEnabled(true);
            mBtnAfter.setEnabled(false);
        } else {
            mBtnBefore.setEnabled(true);
            mBtnAfter.setEnabled(true);
        }
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
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

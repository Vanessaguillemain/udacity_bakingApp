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

import java.util.ArrayList;

/**
 * Created by vanessa on 17/02/2019.
 */

public class PlayStepFragment extends Fragment {

    private Recipe currentRecipe;
    private int currentStep;
    //TODO temporaore
    private Context mContext;
    private Button mBtnBefore;
    private Button mBtnAfter;
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

        TextView textViewDescription = (TextView) rootView.findViewById(R.id.tvDescription);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.ivPlayer);
        mBtnBefore = (Button) rootView.findViewById(R.id.btnBefore);
        mBtnAfter = (Button) rootView.findViewById(R.id.btnAfter);
        initButtonsState();
        if(buttonsActivated) {
            setButtons();
            mBtnBefore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "before", Toast.LENGTH_SHORT).show();
                }
            });

            mBtnAfter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "after", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            mBtnBefore.setVisibility(View.GONE);
            mBtnAfter.setVisibility(View.GONE);

        }
        if(currentRecipe != null) {
            ArrayList<RecipeStep> steps = currentRecipe.getRecipeSteps();
            textViewDescription.setText(steps.get(currentStep).getDescription());
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
        } else if (currentStep == currentRecipe.getRecipeSteps().size()-1){
            mBtnBefore.setEnabled(true);
            mBtnAfter.setEnabled(false);
        } else {
            mBtnBefore.setEnabled(true);
            mBtnAfter.setEnabled(true);
        }
    }

    //TODO temporaore
    public Context getmContext() {
        return mContext;
    }
    //TODO temporaore
    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

}

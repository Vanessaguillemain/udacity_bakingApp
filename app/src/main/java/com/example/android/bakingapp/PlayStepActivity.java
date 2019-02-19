package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utils.Utils;

public class PlayStepActivity extends AppCompatActivity {

    private Recipe mRecipe;
    private int mCurrentStep;
    //private PlayStepFragment mPlayStepFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_step);

        if(savedInstanceState == null) {
            mRecipe = getIntent().getExtras().getParcelable(Utils.BUNDLE_KEY_RECIPE);
            mCurrentStep = getIntent().getExtras().getInt(Utils.BUNDLE_KEY_STEP_INDEX);
            this.setTitle(mRecipe.getName());

            // Create a new Fragment
            PlayStepFragment mPlayStepFragment = new PlayStepFragment();
            mPlayStepFragment.setCurrentRecipe(mRecipe);
            //mPlayStepFragment.setContext(this);
            mPlayStepFragment.setCurrentStep(mCurrentStep);

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.play_step_container, mPlayStepFragment)
                    .commit();
        } else {
            //TODO avant ce code, est-ce que les valeurs sont nulles ou pas?
            mRecipe = savedInstanceState.getParcelable(Utils.BUNDLE_KEY_RECIPE);
            mCurrentStep = savedInstanceState.getInt(Utils.BUNDLE_KEY_STEP_INDEX);
            this.setTitle(mRecipe.getName());
        }

    }

    /**
     * Save the current state of this activity
     */

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelable(Utils.BUNDLE_KEY_RECIPE, mRecipe);
        currentState.putInt(Utils.BUNDLE_KEY_STEP_INDEX, mCurrentStep);
    }
}

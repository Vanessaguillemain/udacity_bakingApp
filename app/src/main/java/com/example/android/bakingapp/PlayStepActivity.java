package com.example.android.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utils.Utils;

public class PlayStepActivity extends AppCompatActivity {

    private Recipe mRecipe;
    private int mCurrentStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_step);

        if(savedInstanceState == null) {
            mRecipe = getIntent().getExtras().getParcelable(Utils.BUNDLE_KEY_RECIPE);
            mCurrentStep = getIntent().getExtras().getInt(Utils.BUNDLE_KEY_STEP_INDEX);
            this.setTitle(mRecipe.getName());

            // Create a new Fragment
            PlayStepFragment playStepFragment = new PlayStepFragment();
            playStepFragment.setCurrentRecipe(mRecipe);
            playStepFragment.setCurrentStep(mCurrentStep);

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.play_step_container, playStepFragment)
                    .commit();

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

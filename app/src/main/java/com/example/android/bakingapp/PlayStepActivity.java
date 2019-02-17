package com.example.android.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utils.Utils;

public class PlayStepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_step);

        Recipe recipe = getIntent().getExtras().getParcelable(Utils.BUNDLE_KEY_RECIPE);
        int index = getIntent().getExtras().getInt(Utils.BUNDLE_KEY_STEP_INDEX);
        int indexTitle = index+1;
        this.setTitle(recipe.getName()+ " - step " + indexTitle);

        // Create a new Fragment
        PlayStepFragment playStepFragment = new PlayStepFragment();
        playStepFragment.setCurrentRecipe(recipe);
        playStepFragment.setCurrentStep(index);
        //TODO temporaire
        playStepFragment.setmContext(this);

        // Add the fragment to its container using a FragmentManager and a Transaction
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.play_step_container, playStepFragment)
                .commit();

    }
}
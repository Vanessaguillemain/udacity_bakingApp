package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utils.Utils;

import butterknife.ButterKnife;

/**
 * Created by vanessa on 16/02/2019.
 */

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnRecipeStepClickListener {

    private Recipe mRecipe;
    private boolean mTwoPane;
    private int mCurrentStep = -1;
    RecipeDetailFragment recipeDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if(savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable(Utils.BUNDLE_KEY_RECIPE);
            mCurrentStep = savedInstanceState.getInt(Utils.BUNDLE_KEY_STEP_INDEX);

        } else {
            mRecipe = getIntent().getExtras().getParcelable(Utils.BUNDLE_KEY_RECIPE);
            mCurrentStep = -1;
        }
        this.setTitle(mRecipe.getName());

        if (findViewById(R.id.play_step_container) != null) {
            mTwoPane = true;
        }else {
            mTwoPane = false;
        }
        if(savedInstanceState == null) {
            // Create a new Fragment
            recipeDetailFragment = new RecipeDetailFragment();
            recipeDetailFragment.setCurrentRecipe(mRecipe);
            recipeDetailFragment.setCurrentStep(mCurrentStep);

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.list_elements_container, recipeDetailFragment)
                    .commit();

            if (findViewById(R.id.play_step_container) != null) {
                // This LinearLayout will only initially exist in the two-pane tablet case
                mTwoPane = true;
                // Create a new Fragment
                PlayStepFragment playStepFragment = new PlayStepFragment();
                playStepFragment.setCurrentRecipe(mRecipe);
                //playStepFragment.setContext(this);//TODO
                playStepFragment.setCurrentStep(mCurrentStep);

                fragmentManager.beginTransaction()
                        .add(R.id.play_step_container, playStepFragment)
                        .commit();

            } else {
                mTwoPane = false;
            }
        } else {
            // Create a new Fragment for replacing
            recipeDetailFragment = new RecipeDetailFragment();
            recipeDetailFragment.setCurrentRecipe(mRecipe);
            recipeDetailFragment.setCurrentStep(mCurrentStep);

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.list_elements_container, recipeDetailFragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRecipeStepSelected(int position, View view) {

        if(mTwoPane) {
            // Create a new Fragment for replacing
            recipeDetailFragment = new RecipeDetailFragment();
            recipeDetailFragment.setCurrentRecipe(mRecipe);
            recipeDetailFragment.setCurrentStep(position);

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.list_elements_container, recipeDetailFragment)
                    .commit();

            // Create a new Fragment for replacing
            PlayStepFragment playStepFragment = new PlayStepFragment();
            playStepFragment.setCurrentRecipe(mRecipe);
            //playStepFragment.setContext(this);//TODO
            playStepFragment.setCurrentStep(position);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.play_step_container, playStepFragment)
                    .commit();

        } else {
            Bundle b = new Bundle();
            b.putInt(Utils.BUNDLE_KEY_STEP_INDEX, position);
            b.putParcelable(Utils.BUNDLE_KEY_RECIPE, mRecipe);

            final Intent intent = new Intent(this, PlayStepActivity.class);
            intent.putExtras(b);
            startActivity(intent);
        }
        mCurrentStep = position;
    }

    /**
     * Save the current state of this Activity
     */
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelable(Utils.BUNDLE_KEY_RECIPE, mRecipe);
        currentState.putInt(Utils.BUNDLE_KEY_STEP_INDEX, mCurrentStep);
    }
}

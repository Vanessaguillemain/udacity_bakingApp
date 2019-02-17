package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utils.Utils;

import butterknife.ButterKnife;

/**
 * Created by vanessa on 16/02/2019.
 */

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnImageClickListener{

    private Recipe mRecipe;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mRecipe = getIntent().getExtras().getParcelable(Utils.BUNDLE_KEY_RECIPE);
        this.setTitle(mRecipe.getName());

        // Create a new head BodyPartFragment
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setCurrentRecipe(mRecipe);

        // Add the fragment to its container using a FragmentManager and a Transaction
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.list_elements_container, recipeDetailFragment)
                .commit();

        if(findViewById(R.id.play_step_container) != null) {
            // This LinearLayout will only initially exist in the two-pane tablet case
            mTwoPane = true;

            // Create a new Fragment
            PlayStepFragment playStepFragment = new PlayStepFragment();
            playStepFragment.setCurrentRecipe(mRecipe);
            playStepFragment.setCurrentStep(0);
            //playStepFragment.desactivateButtons();

            //TODO temporaire
            playStepFragment.setmContext(this);

            fragmentManager.beginTransaction()
                    .add(R.id.play_step_container, playStepFragment)
                    .commit();

        } else {
            mTwoPane =  false;
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
    public void onImageSelected(int position) {
        // Create a Toast that displays the position that was clicked
        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();

        if(mTwoPane) {
            // Create a new Fragment
            PlayStepFragment playStepFragment = new PlayStepFragment();
            playStepFragment.setCurrentRecipe(mRecipe);
            playStepFragment.setCurrentStep(position);
            //TODO temporaire
            playStepFragment.setmContext(this);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.play_step_container, playStepFragment)
                    .commit();

        } else {
            // TODO (3) Put this information in a Bundle and attach it to an Intent that will launch an AndroidMeActivity
            Bundle b = new Bundle();
            b.putInt(Utils.BUNDLE_KEY_STEP_INDEX, position);
            b.putParcelable(Utils.BUNDLE_KEY_RECIPE, mRecipe);

            final Intent intent = new Intent(this, PlayStepActivity.class);
            intent.putExtras(b);
            startActivity(intent);
        }

    }
}

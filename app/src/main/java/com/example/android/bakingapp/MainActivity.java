package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.bakingapp.json.JsonRecipeUtils;
import com.example.android.bakingapp.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String BUNDLE_KEY_RECIPE_TAB = "recipe_tab";

    @BindView(R.id.recyclerview) RecyclerView mRecyclerView;
    Recipe[] mRecipeTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            mRecipeTab = (Recipe[]) savedInstanceState.getParcelableArray(BUNDLE_KEY_RECIPE_TAB);
        } else {
            mRecipeTab = JsonRecipeUtils.getRecipesFromJsonWithGson(this);
        }
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        RecipeListAdapter myAdapter = new RecipeListAdapter(MainActivity.this, mRecipeTab);
        mRecyclerView.setAdapter(myAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArray(BUNDLE_KEY_RECIPE_TAB, mRecipeTab);
    }
}

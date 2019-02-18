package com.example.android.bakingapp;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.bakingapp.json.JsonRecipeUtils;
import com.example.android.bakingapp.model.Recipe;

import java.util.Arrays;

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
            //mRecipeTab = (Recipe[]) savedInstanceState.getParcelableArray(BUNDLE_KEY_RECIPE_TAB);
            Parcelable[] a = savedInstanceState.getParcelableArray(BUNDLE_KEY_RECIPE_TAB);
            mRecipeTab = Arrays.copyOf(a, a.length, Recipe[].class);

        } else {
            mRecipeTab = JsonRecipeUtils.getRecipesFromJsonWithGson(this);
        }
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        String s = getResources().getString(R.string.nb_span);
        Integer span = Integer.parseInt(s);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainActivity.this, span);
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

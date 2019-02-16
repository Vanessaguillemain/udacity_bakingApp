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

    @BindView(R.id.recyclerview) RecyclerView mRecyclerView;
    Recipe[] mRecipeTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mRecipeTab = JsonRecipeUtils.getRecipesFromJsonWithGson(this);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        RecipeListAdapter myAdapter = new RecipeListAdapter(MainActivity.this, mRecipeTab);
        mRecyclerView.setAdapter(myAdapter);

    }

}

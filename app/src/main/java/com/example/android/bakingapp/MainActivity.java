package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.bakingapp.json.JsonRecipeUtils;
import com.example.android.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview) RecyclerView mRecyclerView;
    List<Recipe> mRecipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Recipe[] recipeTab = JsonRecipeUtils.getRecipesFromJsonWithGson(this);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mRecipeList = new ArrayList<>();
        mRecipeList.add(new Recipe(1, "Recette 1", R.drawable.image1));
        mRecipeList.add(new Recipe(1, "Recette 2", R.drawable.image1));
        mRecipeList.add(new Recipe(1, "Recette 3", R.drawable.image1));
        mRecipeList.add(new Recipe(1, "Recette 4", R.drawable.image1));

        RecipeListAdapter myAdapter = new RecipeListAdapter(MainActivity.this, mRecipeList);
        mRecyclerView.setAdapter(myAdapter);

    }
}

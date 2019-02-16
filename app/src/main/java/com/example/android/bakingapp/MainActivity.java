package com.example.android.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<Recipe> mRecipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mRecipeList = new ArrayList<>();
        mRecipeList.add(new Recipe(1, "Recette 1", R.drawable.image1));
        mRecipeList.add(new Recipe(1, "Recette 2", R.drawable.image1));
        mRecipeList.add(new Recipe(1, "Recette 3", R.drawable.image1));
        mRecipeList.add(new Recipe(1, "Recette 4", R.drawable.image1));

        RecipeAdapter myAdapter = new RecipeAdapter(MainActivity.this, mRecipeList);
        mRecyclerView.setAdapter(myAdapter);

    }
}

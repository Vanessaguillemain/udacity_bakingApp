package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vanessa on 16/02/2019.
 */

public class RecipeActivity  extends AppCompatActivity {
    @BindView((R.id.ivImage)) ImageView mFlower;
    @BindView((R.id.tvDescription)) TextView mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);
        ButterKnife.bind(this);

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            mFlower.setImageResource(mBundle.getInt("Image"));
            mDescription.setText(mBundle.getString("Name"));
        }
    }
}

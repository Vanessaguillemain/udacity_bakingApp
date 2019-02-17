package com.example.android.bakingapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.bakingapp.model.RecipeStep;

import java.util.List;

/**
 * Created by vanessa on 16/02/2019.
 */

public class RecipeDetailAdapter extends BaseAdapter {

    // Keeps track of the context and list of recipe steps to display
    private Context mContext;
    private List<RecipeStep> mRecipeSteps;

    /**
     * Constructor method
     * @param steps The list of steps to display
     */
    public RecipeDetailAdapter(Context context, List<RecipeStep> steps) {
        mContext = context;
        mRecipeSteps = steps;
    }

    /**
     * Returns the number of items the adapter will display
     */
    @Override
    public int getCount() {
        return mRecipeSteps.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * Creates a new ImageView for each item referenced by the adapter
     */
    public View getView(final int position, View convertView, ViewGroup parent) {

        TextView textViewStep;

        if (convertView == null) {
            textViewStep = new TextView(mContext);
        } else {
            textViewStep = (TextView) convertView;
        }
        String s = mRecipeSteps.get(position).getShortDescription();
        textViewStep.setText(s);
        textViewStep.setBackgroundResource(R.drawable.back);

        return textViewStep;
    }
}

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
    private int mCurrentStep;
    private View mCurrentView;
    private boolean mSomethingSelected = false;

    /**
     * Constructor method
     * @param steps The list of steps to display
     */
    public RecipeDetailAdapter(Context context, List<RecipeStep> steps, int currentStep) {
        mContext = context;
        mRecipeSteps = steps;
        mCurrentStep = currentStep;
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

    public View getCurrentView() {
        return mCurrentView;
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
        if(mCurrentStep != 0 && mCurrentStep == position) {
        //if(mSomethingSelected && mCurrentStep == position) {
            textViewStep.setBackgroundResource(R.color.colorAccent);
            mCurrentView = textViewStep;
        } else {
            textViewStep.setBackgroundResource(R.drawable.back);
        }

        return textViewStep;
    }

    public boolean isSomethingSelected() {
        return mSomethingSelected;
    }

    public void setSomethingSelected(boolean mSomethingSelected) {
        this.mSomethingSelected = mSomethingSelected;
    }
}

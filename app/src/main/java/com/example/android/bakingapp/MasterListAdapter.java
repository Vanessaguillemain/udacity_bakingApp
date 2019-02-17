package com.example.android.bakingapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.model.RecipeStep;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vanessa on 16/02/2019.
 */

public class MasterListAdapter extends BaseAdapter {
    // Keeps track of the context and list of images to display
    private Context mContext;
    private List<Integer> mImageIds;
    private List<RecipeStep> mRecipeSteps;

    /**
     * Constructor method
     * @param imageIds The list of images to display
     */
    public MasterListAdapter(Context context, List<RecipeStep> steps) {
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
       /* ImageView imageView;
        if (convertView == null) {
            // If the view is not recycled, this creates a new ImageView to hold an image
            imageView = new ImageView(mContext);
            // Define the layout parameters
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        // Set the image resource and return the newly created ImageView
        imageView.setImageResource(mImageIds.get(position));
        return imageView;*/


        TextView textViewStep;

        if (convertView == null) {
            textViewStep = new TextView(mContext);

        } else {
            textViewStep = (TextView) convertView;
        }
        String s = mRecipeSteps.get(position).getShortDescription();
        textViewStep.setText(s);
        textViewStep.setBackgroundResource(R.drawable.back);

        textViewStep.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(v.getContext(), "Text #" + (position), Toast.LENGTH_SHORT).show();
            }
        });

        return textViewStep;

    }


}

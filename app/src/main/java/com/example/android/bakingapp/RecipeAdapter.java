package com.example.android.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.model.Recipe;

import java.util.List;

/**
 * Created by vanessa on 16/02/2019.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder >{

    private Context mContext;
    private List<Recipe> mRecipeList;

    RecipeAdapter(Context context, List<Recipe> recipeList) {
        mContext = context;
        mRecipeList = recipeList;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_row, parent, false);
        return new RecipeViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.mImage.setImageResource(mRecipeList.get(position).getImage());
        holder.mTitle.setText(mRecipeList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }
}

class RecipeViewHolder extends RecyclerView.ViewHolder {
    ImageView mImage;
    TextView mTitle;
    RecipeViewHolder(View itemView) {
        super(itemView);
         mImage = itemView.findViewById(R.id.ivImage);
         mTitle = itemView.findViewById(R.id.tvTitle);
    }
}

package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
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

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListViewHolder>{

    private Context mContext;
    private List<Recipe> mRecipeList;


    RecipeListAdapter(Context context, List<Recipe> recipeList) {
        mContext = context;
        mRecipeList = recipeList;
    }

    @Override
    public RecipeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_row, parent, false);
        return new RecipeListViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final RecipeListViewHolder holder, int position) {
        holder.mImage.setImageResource(mRecipeList.get(position).getImageTest());
        holder.mTitle.setText(mRecipeList.get(position).getName());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(mContext, RecipeActivity.class);
                mIntent.putExtra("Name", mRecipeList.get(holder.getAdapterPosition()).getName());
                mIntent.putExtra("Id", mRecipeList.get(holder.getAdapterPosition()).getId());
                mIntent.putExtra("Image", mRecipeList.get(holder.getAdapterPosition()).getImage());
                mContext.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }
}

class RecipeListViewHolder extends RecyclerView.ViewHolder {

    ImageView mImage;
    TextView mTitle;
    CardView mCardView;

    RecipeListViewHolder(View itemView) {
        super(itemView);
        mImage = itemView.findViewById(R.id.ivImage);
        mTitle = itemView.findViewById(R.id.tvTitle);
        mCardView = itemView.findViewById(R.id.cardview);
    }
}

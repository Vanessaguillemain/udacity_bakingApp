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
import com.squareup.picasso.Picasso;

import butterknife.BindView;

/**
 * Created by vanessa on 16/02/2019.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListViewHolder>{

    private Context mContext;
    private Recipe[] mRecipeList;

    RecipeListAdapter(Context context, Recipe[] recipeList) {
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
        Recipe currentRecipe = mRecipeList[position];
        String urlImage = currentRecipe.getImage();

        if(urlImage.isEmpty()) {
            holder.mImage.setImageResource(mRecipeList[position].getImageTest());
        } else {
            Picasso.with(mContext).load(urlImage).into(holder.mImage);
        }

        holder.mTitle.setText(currentRecipe.getName());

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(mContext, RecipeActivity.class);
                mIntent.putExtra("recipe", mRecipeList[holder.getAdapterPosition()]);
                mContext.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipeList.length;
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

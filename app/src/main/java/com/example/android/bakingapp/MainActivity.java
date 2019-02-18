package com.example.android.bakingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.bakingapp.json.JsonRecipeUtils;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utils.NetworkUtils;
import com.example.android.bakingapp.utils.RecipesQueryTask;
import com.example.android.bakingapp.utils.TaskInformer;
import com.example.android.bakingapp.utils.Utils;

import java.net.URL;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TaskInformer {

    private static final String BUNDLE_KEY_RECIPE_TAB = "recipe_tab";

    @BindView(R.id.recyclerview) RecyclerView mRecyclerView;
    @BindView(R.id.tv_error_message_display) TextView mErrorMessageDisplay;

    Recipe[] mRecipeTab;
    private boolean internetAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (savedInstanceState != null){
            //mRecipeTab = (Recipe[]) savedInstanceState.getParcelableArray(BUNDLE_KEY_RECIPE_TAB);
            Parcelable[] a = savedInstanceState.getParcelableArray(BUNDLE_KEY_RECIPE_TAB);
            mRecipeTab = Arrays.copyOf(a, a.length, Recipe[].class);
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);
            populateViews();
        } else {
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);
            //recherche web
            makeRecipesSearchQuery(Utils.PATH_JSON_FILE_WEB);
            //recherche asset
            //mRecipeTab = JsonRecipeUtils.getRecipesFromJsonAssetWithGson(this);


        }

    }

    private void populateViews() {
        String s = getResources().getString(R.string.nb_span);
        Integer span = Integer.parseInt(s);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainActivity.this, span);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        RecipeListAdapter myAdapter = new RecipeListAdapter(MainActivity.this, mRecipeTab);
        mRecyclerView.setAdapter(myAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArray(BUNDLE_KEY_RECIPE_TAB, mRecipeTab);
    }

    /**
     * This method constructs the url needed (using {@link NetworkUtils})
     * and finally fires off an AsyncTask to perform the GET request {@link RecipesQueryTask}
     */
    private void makeRecipesSearchQuery(String urlString) {
        internetAvailable = isOnline(this);
        if(internetAvailable) {
            //TODO
            hideErrorMessageInternet();
            URL recipesSearchUrl = null;
            recipesSearchUrl = NetworkUtils.buildUrl(urlString);
            new RecipesQueryTask(this).execute(recipesSearchUrl);
        } else {
            //TODO
            showErrorMessageInternet();
        }
    }

    /**
     * This method will hide the error message and show the grid of Posters.
     */
    private void hideErrorMessageInternet() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the grid of Posters.
     */
    private void showErrorMessageInternet() {
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    /**
     * This methods checks if there is a connection
     * @param context the context that will be used to check CONNECTIVITY_SERVICE
     * @return true if connected, false if not
     */
    private boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm != null) {
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        }
        return false;
    }

    @Override
    public void onPreExecute() {
        //TODO
        //mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTaskDone(String jsonResult) {
        //TODO
        //mLoadingIndicator.setVisibility(View.INVISIBLE);
        // Here we will receive output only if the Activity is alive.
        // no need to add checks like if(!isFinishing()), it is checked
        // by the AsyncTask
        if (jsonResult != null && !jsonResult.equals("")) {
            mRecipeTab = JsonRecipeUtils.getRecipesFromStringWithGson(jsonResult);
            populateViews();
        }
    }
}

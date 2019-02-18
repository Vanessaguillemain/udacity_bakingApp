package com.example.android.bakingapp.json;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by vanessa on 16/02/2019.
 */

public class JsonRecipeUtils {

    public static Recipe[] getRecipesFromJsonAssetWithGson(Context context) {
        String json = loadJSONFromAsset(context);
        return getRecipesFromStringWithGson( json) ;
    }

    public static Recipe[] getRecipesFromStringWithGson(String json) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Recipe[] recipeTab = gson.fromJson(json, Recipe[].class);
        return recipeTab;
    }

    public static String loadJSONFromAsset(Context context) {
        String json = null;
        AssetManager assetManager = context.getAssets();
        try {
            //TODO
            InputStream is = assetManager.open(Utils.PATH_JSON_FILE_ASSET);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}

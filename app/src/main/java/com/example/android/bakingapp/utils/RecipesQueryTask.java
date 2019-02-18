package com.example.android.bakingapp.utils;

import android.os.AsyncTask;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;

/**
 * Created by vanessa on 18/02/2019.
 */

public class RecipesQueryTask extends AsyncTask<URL, Void, String> {
    // Hold weak reference to avoid memory leak in case of
    // end of activity for example when the user rotates
    private final WeakReference<TaskInformer> mCallBack;

    public RecipesQueryTask(TaskInformer callback) {
        this.mCallBack = new WeakReference<>(callback);
    }

    @Override
    protected String doInBackground(URL... urls) {
        String tmdbSearchResults = null;
        if(urls != null && urls.length>0) {
            URL searchUrl = urls[0];
            try {
                tmdbSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tmdbSearchResults;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Here we can't guarantee that Activity/Fragment who started this AsyncTask is alive
        // Make sure our caller is active
        final TaskInformer callBack = mCallBack.get();
        if(callBack != null) {
            callBack.onPreExecute();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        // Here we can't guarantee that Activity/Fragment who started this AsyncTask is alive
        // Make sure our caller is active
        final TaskInformer callBack = mCallBack.get();
        if(callBack != null) {
            callBack.onTaskDone(s);
        }
    }

}

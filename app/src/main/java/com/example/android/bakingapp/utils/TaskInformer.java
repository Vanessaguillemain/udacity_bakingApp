package com.example.android.bakingapp.utils;

/**
 * Created by vanessa on 18/02/2019.
 */

public interface TaskInformer {
    void onPreExecute();
    void onTaskDone(String jsonResult);
}

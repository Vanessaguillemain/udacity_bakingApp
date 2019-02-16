package com.example.android.bakingapp.model;

import com.example.android.bakingapp.R;

/**
 * Created by vanessa on 16/02/2019.
 */

public class Recipe {
    private int id;
    private String name;
    private int image;

    public Recipe(int id, String name, int image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getImage() {
        return R.drawable.image1;
    }
}

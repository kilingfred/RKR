package com.example.rkr.models;

import androidx.annotation.NonNull;

public class Categories {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // THIS IS THE IMPORTANT PART
    @NonNull
    @Override
    public String toString() {
        return name;  // So Spinner displays the name
    }
}

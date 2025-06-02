package com.example.rkr.models;

import com.google.gson.annotations.SerializedName;

public class Company {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("address")
    private String address;

    @SerializedName("email")
    private String email;

    @SerializedName("logo")
    private String logo;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getLogo() {
        return logo;
    }
}
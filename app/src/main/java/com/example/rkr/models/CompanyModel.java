package com.example.rkr.models;

import com.google.gson.annotations.SerializedName;

public class CompanyModel {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;
    
    @SerializedName("number")
    private String phone;

    @SerializedName("address")
    private String address;

    @SerializedName("link")
    private String link;

    @SerializedName("email")
    private String email;

    @SerializedName("logo")
    private String logo;

    @SerializedName("description")
    private String info;

    public String getInfo() {
        return info;
    }


    public String getLink() {
        return link;
    }

    public String getPhone() {
        return phone;
    }

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

    public CompanyModel(String name, String phone, String address, String link, String email, String logo, String info) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.link = link;
        this.email = email;
        this.logo = logo;
        this.info = info;
    }
}
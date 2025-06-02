package com.example.rkr.models;

public class CompanyRegisterModel {
    private UserRegisterModel user;
    private String name;
    private String phone;
    private String address;

    public CompanyRegisterModel(UserRegisterModel user, String name, String phone, String address) {
        this.user = user;
        this.address = address;
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public UserRegisterModel getUser() {
        return user;
    }
}

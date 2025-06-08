package com.example.rkr.models;

public class CompanyRegisterModel {
    private final UserModel user;
    private final String name;
    private final String number;
    private final String address;

    public CompanyRegisterModel(UserModel user, String name, String phone, String address) {
        this.user = user;
        this.address = address;
        this.name = name;
        this.number = phone;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return number;
    }

    public UserModel getUser() {
        return user;
    }
}

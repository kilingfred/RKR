package com.example.rkr.models;

import com.example.rkr.enums.UserTypes;

public class UserRegisterModel {
    private String login;
    private String password;
    private UserTypes type;
    private String email;
    private Integer id;

    public UserRegisterModel(String login, String password, UserTypes type, String email) {
        this.email = email;
        this.type = type;
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public UserTypes getType() {
        return type;
    }

}


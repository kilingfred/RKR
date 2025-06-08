package com.example.rkr.models;

import com.example.rkr.enums.UserTypes;

public class UserModel {
    private String login;
    private String password;
    private String type; // or UserTypes type;
    private String email;
    private Integer id;

    public UserModel(String login, String password, String type, String email) {
        this.email = email;
        this.type = type;
        this.login = login;
        this.password = password;
    }

    public UserModel(String login, String password) {
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

    public String getType() {
        return type;
    }

}


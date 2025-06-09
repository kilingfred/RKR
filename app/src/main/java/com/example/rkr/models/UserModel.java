package com.example.rkr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {
    @Expose
    @SerializedName("id")
    private Integer id;
    @Expose
    @SerializedName("type")
    private String type;
    @Expose
    @SerializedName("login")
    private String login;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("password")
    private String password;

    public UserModel(Integer id, String type, String login, String email, String password) {
        this.id = id;
        this.type = type;
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public UserModel(String type, String login, String email, String password) {
        this(null, type, login, email, password); // Default id to null, will be set by server
    }

    public UserModel(String login, String password) {
        this(null, null, login, null, password); // Default for minimal constructor
    }

    // Getters and setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
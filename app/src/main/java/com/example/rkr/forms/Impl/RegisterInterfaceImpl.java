package com.example.rkr.forms.Impl;

import com.example.rkr.forms.RegisterInterface;
import com.example.rkr.models.CompanyRegisterModel;
import com.example.rkr.models.UserRegisterModel;
import com.example.rkr.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;

public class RegisterInterfaceImpl {

    private final RegisterInterface registerApi;

    public RegisterInterfaceImpl() {
        registerApi = ApiClient.getRetrofit().create(RegisterInterface.class);
    }

    public void registerUser(UserRegisterModel model, Callback<Void> callback) {
        registerApi.registerUser(model).enqueue(callback);
    }


    public void registerCompany(CompanyRegisterModel model, Callback<Void> callback) {
        registerApi.registerCompany(model).enqueue(callback);
    }

    public boolean registerByGoogle() {
        // You can implement your own logic here, e.g. using Firebase
        return false;
    }
}

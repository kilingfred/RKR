package com.example.rkr.forms.Impl;

import com.example.rkr.api.RetrofitClient;
import com.example.rkr.forms.RegisterInterface;
import com.example.rkr.models.CompanyRegisterModel;
import com.example.rkr.models.UserModel;

import retrofit2.Call;

public class RegisterInterfaceImpl implements RegisterInterface {

    private final RegisterInterface registerApi;

    public RegisterInterfaceImpl() {
        registerApi = RetrofitClient.getRetrofitInstance().create(RegisterInterface.class);
    }

    @Override
    public Call<Void> registerUser(UserModel model) {
        return registerUser(model);
    }

    @Override
    public Call<Void> registerCompany(CompanyRegisterModel model) {
        return registerCompany(model);
    }


    public Call<RegisterInterface.LastIdResponse> getLastUserId() {
        return registerApi.getLastUserId();
    }

    public boolean registerByGoogle() {
        // You can implement your own logic here, e.g. using Firebase
        return false;
    }
}
package com.example.rkr.forms.Impl;

import com.example.rkr.forms.RegisterProductInterface;
import com.example.rkr.models.ProductModel;
import com.example.rkr.network.ApiClient;

import retrofit2.Call;

public class RegisterProductInterfaceImpl {
    private final RegisterProductInterface productInterface;

    public RegisterProductInterfaceImpl() {
        productInterface = ApiClient.getRetrofit().create(RegisterProductInterface.class);
    }

    public RegisterProductInterface getProductInterface() {
        return productInterface;
    }

    public Call<Void> register(ProductModel model) {
        return productInterface.register(model);
    }
}

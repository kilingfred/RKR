package com.example.rkr.forms.Impl;

import com.example.rkr.api.RetrofitClient;
import com.example.rkr.forms.RegisterProductInterface;
import com.example.rkr.models.Product;

import retrofit2.Call;

public class RegisterProductInterfaceImpl {
    private final RegisterProductInterface productInterface;

    public RegisterProductInterfaceImpl() {
        productInterface = RetrofitClient.getRetrofitInstance().create(RegisterProductInterface.class);
    }

    public RegisterProductInterface getProductInterface() {
        return productInterface;
    }

    public Call<Void> register(Product model) {
        return productInterface.register(model);
    }
}

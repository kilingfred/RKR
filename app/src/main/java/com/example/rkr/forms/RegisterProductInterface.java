package com.example.rkr.forms;

import com.example.rkr.models.Product;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterProductInterface {
    @POST("register/product")
    Call<Void> register(@Body Product model);
}

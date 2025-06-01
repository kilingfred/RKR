package com.example.rkr.forms;

import com.example.rkr.models.ProductModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterProductInterface {
    @POST
    Call<Void> register(@Body ProductModel model);
}

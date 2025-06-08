package com.example.rkr.forms;

import com.example.rkr.models.CompanyRegisterModel;
import com.example.rkr.models.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterInterface {

    @POST("/register/user")
    Call<Void> registerUser(@Body UserModel model);

    @POST("/register/company")
    Call<Void> registerCompany(@Body CompanyRegisterModel model);
}

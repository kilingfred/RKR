package com.example.rkr.forms;

import com.example.rkr.models.CompanyRegisterModel;
import com.example.rkr.models.UserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RegisterInterface {
    @POST("register_user")
    Call<Void> registerUser(@Body UserModel model);

    @POST("register_company")
    Call<Void> registerCompany(@Body CompanyRegisterModel model);

    @GET("last-user-id")
    Call<LastIdResponse> getLastUserId(); // New method

    // Define a simple response model for the last ID
    class LastIdResponse {
        private int lastId;

        public int getLastId() { return lastId; }
        public void setLastId(int lastId) { this.lastId = lastId; }
    }
}
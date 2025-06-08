package com.example.rkr.forms.Impl;

import com.example.rkr.forms.loginInterface;
import com.example.rkr.models.UserModel;
import com.example.rkr.views.RegisterActivity;
import com.example.rkr.api.ApiService;
import com.example.rkr.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class loginInterfaceImpl implements loginInterface {

    @Override
    public void loginUsingGoogle() {
        System.out.println("Вхід через Google ще не реалізовано.");
    }

    // Use a callback to handle async result
    public void loginWithCredentials(String username, String password, LoginCallback callback) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        apiService.loginUser(new UserModel(username, password)).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onLoginResult(true, response.body());
                } else {
                    callback.onLoginResult(false, null);
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                callback.onLoginResult(false, null);
            }
        });
    }

    @Override
    public void register(android.content.Context context) {
        android.content.Intent intent = new android.content.Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    // Callback interface for login result
    public interface LoginCallback {
        void onLoginResult(boolean success, UserModel user);
    }
}
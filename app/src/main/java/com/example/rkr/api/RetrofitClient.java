package com.example.rkr.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            // Додаємо GsonBuilder з .setLenient(true)
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://nat-cash.onrender.com/api/")
                    .addConverterFactory(GsonConverterFactory.create(gson)) // Використовуємо наш налаштований Gson
                    .build();
        }
        return retrofit;
    }
}
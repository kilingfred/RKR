package com.example.rkr.api;

import com.example.rkr.models.Company;
import com.example.rkr.models.Product;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface ApiService {
    @GET("products")
    Call<List<Product>> getProducts(); // Повертає список продуктів, оскільки API працює нормально

    @GET("companies")
    Call<List<Company>> getCompanies(); // Додано метод для отримання компаній
}
package com.example.rkr.api;

import com.example.rkr.models.Categories;
import com.example.rkr.models.CompanyModel;
import com.example.rkr.models.Product;
import com.example.rkr.models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("products")
    Call<List<Product>> getProducts(); // Повертає список продуктів, оскільки API працює нормально

    @GET("companies")
    Call<List<CompanyModel>> getCompanies(); // Додано метод для отримання компаній

    @POST("login")
    Call<UserModel> loginUser(@Body UserModel loginRequest);

    @GET("products/{id}")
    Call<Product> getProduct(@Path("id") int productId);

    @GET("categories")
    Call<List<Categories>> getCategories();

    @GET("products/by-manufacturer/{manufacturer}")
    Call<List<Product>> getCompanyProducts(@Path("manufacturer") String manufacturer);

    @POST("delete_product")
    Call<Void> deleteProduct(@Body Product product);

    @GET("company/{name}")
    Call<CompanyModel> getCompany(@Path("name") String name);
}
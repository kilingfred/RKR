package com.example.rkr.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rkr.BaseActivity;
import com.example.rkr.R;
import com.example.rkr.api.ApiService;
import com.example.rkr.api.RetrofitClient;
import com.example.rkr.models.CompanyModel;
import com.example.rkr.models.Product;
import com.example.rkr.adapters.ProductAdapter;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "CompanyActivity started", Toast.LENGTH_LONG).show();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_company);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = this.getIntent();
        String company = intent.getStringExtra("company");

        ImageView logo = findViewById(R.id.logo);
        TextView name = findViewById(R.id.company_name);
        TextView address = findViewById(R.id.address);
        TextView phone = findViewById(R.id.phone);
        TextView link = findViewById(R.id.link);
        TextView email = findViewById(R.id.email);
        TextView info = findViewById(R.id.company_info);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<CompanyModel> companyModelCall = apiService.getCompany(company);

        companyModelCall.enqueue(new Callback<CompanyModel>() {
            @Override
            public void onResponse(Call<CompanyModel> call, Response<CompanyModel> response) {
                if (!CompanyActivity.this.isFinishing() && !CompanyActivity.this.isDestroyed()) {
                    if (response.isSuccessful() && response.body() != null) {
                        name.setText(response.body().getName());
                        address.setText(response.body().getAddress());
                        phone.setText(response.body().getPhone());
                        link.setText(response.body().getLink());
                        email.setText(response.body().getEmail());
                        info.setText(response.body().getInfo());
                        Glide.with(CompanyActivity.this).load(response.body().getLogo()).into(logo);
                    } else {
                        Log.e("getCompanyError", "Company data is null or response not successful");
                    }
                }
            }

            @Override
            public void onFailure(Call<CompanyModel> call, Throwable t) {
                Log.e("getCompanyError","Couldn't set fields" + t.getMessage());
            }
        });

        RecyclerView recyclerView = findViewById(R.id.products);
        int spanCount = 3; // 3 items per row
        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        recyclerView.setLayoutManager(layoutManager);

        // Fetch products for this company
        Call<List<Product>> productsCall = apiService.getCompanyProducts(company);

        productsCall.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> productList = response.body();
                    ProductAdapter productAdapter = new ProductAdapter(productList);
                    productAdapter.setOnProductClickListener(product -> {
                        Intent intent = new Intent(CompanyActivity.this, ProductActivity.class);
                        intent.putExtra("product", new Gson().toJson(product));
                        startActivity(intent);
                    });
                    recyclerView.setAdapter(productAdapter);
                } else {
                    Log.e("CompanyActivity", "No products found or error in response");
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("CompanyActivity", "Failed to fetch products: " + t.getMessage());
            }
        });
    }
}
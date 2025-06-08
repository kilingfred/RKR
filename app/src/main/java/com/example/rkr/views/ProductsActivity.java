package com.example.rkr.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rkr.BaseActivity;
import com.example.rkr.R;
import com.example.rkr.adapters.ProductAdapter;
import com.example.rkr.api.ApiService;
import com.example.rkr.api.RetrofitClient;
import com.example.rkr.models.Product;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;

    private static final String TAG = "ProductsActivity"; // Додано тег для логування

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        adapter = new ProductAdapter(productList);
        recyclerView.setAdapter(adapter);

        // Set click listener
        adapter.setOnProductClickListener(product -> {
            Intent intent = new Intent(ProductsActivity.this, ProductActivity.class);
            intent.putExtra("product", new Gson().toJson(product)); // or use Parcelable/Serializable
            startActivity(intent);
        });

        loadProducts();
    }

    private void loadProducts() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<List<Product>> call = apiService.getProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "API Success: Response received.");
                    Log.d(TAG, "API data: " + new Gson().toJson(response.body()));
                    productList.clear();
                    List<Product> newList = new ArrayList<>(response.body());
                    adapter.updateProducts(newList);
                    Toast.makeText(ProductsActivity.this, "Продукти завантажено успішно!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "API Error: Response Code = " + response.code());
                    if (response.errorBody() != null) {
                        try {
                            String errorBodyString = response.errorBody().string();
                            Log.e(TAG, "API Error: Raw Response Body = " + errorBodyString);
                        } catch (IOException e) {
                            Log.e(TAG, "API Error: Could not read error body", e);
                        }
                    }
                    Toast.makeText(ProductsActivity.this, "Помилка завантаження продуктів: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, "Network/API Call Failure: " + t.getMessage(), t);
                // Спроба логувати більш детальні причини, якщо це MalformedJsonException
                if (t instanceof com.google.gson.stream.MalformedJsonException) {
                    Log.e(TAG, "Malformed JSON Exception: " + t.getMessage());
                    Toast.makeText(ProductsActivity.this, "Помилка формату даних: отримано недійсний JSON.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ProductsActivity.this, "Помилка мережі: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
package com.example.rkr.views;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rkr.R;
import com.example.rkr.adapters.ProductAdapter;
import com.example.rkr.api.ApiService;
import com.example.rkr.api.RetrofitClient;
import com.example.rkr.models.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends AppCompatActivity {

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
                    productList.clear();
                    productList.addAll(response.body());
                    adapter.updateProducts(productList);
                    Toast.makeText(ProductsActivity.this, "Продукти завантажено успішно!", Toast.LENGTH_SHORT).show();
                } else {
                    // Логування коду відповіді API
                    Log.e(TAG, "API Error: Response Code = " + response.code());

                    // Спроба отримати тіло помилки
                    if (response.errorBody() != null) {
                        try {
                            String errorBodyString = response.errorBody().string();
                            Log.e(TAG, "API Error: Response Body = " + errorBodyString);

                            // Спроба розпарсити тіло помилки як JSON, якщо це можливо
                            try {
                                Gson gson = new GsonBuilder().setLenient().create(); // Додаємо setLenient для гнучкості
                                Object errorJson = gson.fromJson(errorBodyString, Object.class);
                                Log.e(TAG, "API Error: Parsed Error JSON = " + errorJson.toString());
                            } catch (Exception jsonEx) {
                                Log.e(TAG, "API Error: Could not parse error body as JSON. Error: " + jsonEx.getMessage());
                            }

                        } catch (IOException e) {
                            Log.e(TAG, "API Error: Could not read error body", e);
                        }
                    } else {
                        Log.e(TAG, "API Error: Response was empty or not successful, and errorBody is null.");
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
}
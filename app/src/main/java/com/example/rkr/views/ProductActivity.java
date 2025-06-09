package com.example.rkr.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.rkr.BaseActivity;
import com.example.rkr.R;
import com.example.rkr.models.Product;
import com.example.rkr.api.ApiService;
import com.example.rkr.api.RetrofitClient;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends BaseActivity {

    private static final String TAG = "ProductActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = this.getIntent();
        String productJson = intent.getStringExtra("product");
        Product product = new Gson().fromJson(productJson, Product.class);

        String productName = product.getName();
        if (productName != null && productName.length() > 20) {
            productName = productName.substring(0, 20) + "...";
        }
        setHeader("Продукт " + productName);

        // Find views and set data
        ((TextView)findViewById(R.id.name)).setText(product.getName());
        ((TextView)findViewById(R.id.price)).setText(product.getPrice());
        ((TextView)findViewById(R.id.quantity)).setText(product.getQuantity());
        ((TextView)findViewById(R.id.bar_code)).setText(product.getBarCode());
        ((TextView)findViewById(R.id.product_category)).setText(product.getCategory());
        ((TextView)findViewById(R.id.product_manufacturer)).setText(product.getManufacturer());
        ImageView imageView = findViewById(R.id.image);
        Glide.with(this).load(product.getImageUrl()).into(imageView);

        Button gotoCompany = findViewById(R.id.viewCompany);
        Button back = findViewById(R.id.back);
        Button editButton = findViewById(R.id.edit);
        Button deleteButton = findViewById(R.id.delete);

        gotoCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ProductActivity.this, CompanyActivity.class);
                intent1.putExtra("company", product.getManufacturer());
                startActivity(intent1);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ProductActivity.this, ProductsActivity.class);
                startActivity(intent1);
            }
        });

        // Check ownership
        SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
        String userType = prefs.getString("userType", "");
        String companyName = prefs.getString("companyName", "");
        String login = prefs.getString("loggedInUsername", "");

        boolean isOwner = "company".equalsIgnoreCase(userType) && companyName.equals(product.getManufacturer());

        if (isOwner) {
            editButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
        } else {
            editButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
        }

        editButton.setOnClickListener(v -> {

        });
        deleteButton.setOnClickListener(v -> {
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            apiService.deleteProduct(product).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ProductActivity.this, "Товар видалено", Toast.LENGTH_SHORT).show();
                        finish(); // Or navigate to products list
                    } else {
                        Toast.makeText(ProductActivity.this, "Помилка при видаленні", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(ProductActivity.this, "Помилка мережі", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
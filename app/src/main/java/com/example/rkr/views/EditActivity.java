package com.example.rkr.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rkr.BaseActivity;
import com.example.rkr.R;
import com.example.rkr.api.ApiService;
import com.example.rkr.api.RetrofitClient;
import com.example.rkr.enums.QuantityItems;
import com.example.rkr.forms.Impl.RegisterInterfaceImpl;
import com.example.rkr.forms.Impl.RegisterProductInterfaceImpl;
import com.example.rkr.models.Categories;
import com.example.rkr.models.Product;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditActivity extends BaseActivity {

    RegisterProductInterfaceImpl registerInterface = new RegisterProductInterfaceImpl();
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);
        SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
        String userType = prefs.getString("userType", "");
        updateHeader(userType);
        Intent intent = getIntent();
        String productJson = intent.getStringExtra("product");
        Product product = new Gson().fromJson(productJson, Product.class);
        String name = product.getName();
        setHeader("Редагування " + name);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText product_name = findViewById(R.id.product_name);
        EditText quantity = findViewById(R.id.product_quantity);
        Spinner unit = findViewById(R.id.product_unit);
        Spinner category = findViewById(R.id.product_category);
        EditText price = findViewById(R.id.product_price);
        EditText url = findViewById(R.id.product_image_url);
        EditText barCode = findViewById(R.id.product_code);
        EditText manufacturer = findViewById(R.id.product_manufacturer);
        Button button_edit = findViewById(R.id.edit_button);
        Button button_cancel = findViewById(R.id.cancel_button);
        ArrayAdapter<QuantityItems> unitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, QuantityItems.values());
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit.setAdapter(unitAdapter);

        manufacturer.setText(product.getManufacturer());
        product_name.setText(product.getName());
        price.setText(product.getPrice());
        quantity.setText(product.getQuantity().subSequence(0, product.getQuantity().indexOf(" ")));
        unit.setSelection(QuantityItems.fromString(product.getQuantity().substring(product.getQuantity().indexOf(" ") + 1)).ordinal());
        barCode.setText(product.getBarCode());
        url.setText(product.getImageUrl());
        Call<List<Categories>> listCall = apiService.getCategories();
        listCall.enqueue(new Callback<List<Categories>>() {
            @Override
            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                if (response.body() != null && !response.body().isEmpty()) {
                    List<Categories> list = new ArrayList<>(response.body());
                    ArrayAdapter<Categories> categoryAdapter = new ArrayAdapter<>(EditActivity.this, android.R.layout.simple_spinner_item, list);
                    categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    category.setAdapter(categoryAdapter);
                } else {
                    Toast.makeText(EditActivity.this, "Categories are empty!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Categories>> call, Throwable t) {
                Log.e("RegisterProductActivity", "Error fetching categories: " + t.getMessage());
            }
        });

        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = product_name.getText().toString();
                String productPrice = price.getText().toString();
                String productQuantity = quantity.getText().toString() + unit.getSelectedItem().toString();
                String productUrl = url.getText().toString();
                String productCode = barCode.getText().toString();
                String productCategory = category.getSelectedItem() != null ? category.getSelectedItem().toString() : "";


                if (productName.isBlank()) {
                    Toast.makeText(EditActivity.this, "Name must be filled!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (productCode.isBlank()) {
                    Toast.makeText(EditActivity.this, "Bar_code must be filled!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (productPrice.isBlank()) {
                    Toast.makeText(EditActivity.this, "Price must be filled!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (productUrl.isBlank()) {
                    Toast.makeText(EditActivity.this, "URL must be filled!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (productQuantity.isBlank()) {
                    Toast.makeText(EditActivity.this, "Quantity must be filled!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Product product2 = new Product(product.getId(), productName, productPrice, productQuantity, productCategory, productCode, product.getManufacturer(), productUrl);
                registerInterface.edit(product2).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(EditActivity.this, "Product edited successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(EditActivity.this, "Edit failed: " + response.code() + " - " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(EditActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(EditActivity.this, ProductActivity.class);
                intent1.putExtra("product", new Gson().toJson(product));
                startActivity(intent1);
            }
        });

    }
}
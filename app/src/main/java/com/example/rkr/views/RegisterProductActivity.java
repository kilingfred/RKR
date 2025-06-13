package com.example.rkr.views;

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

import com.example.rkr.BaseActivity;
import com.example.rkr.R;
import com.example.rkr.api.ApiService;
import com.example.rkr.api.RetrofitClient;
import com.example.rkr.enums.QuantityItems;
import com.example.rkr.forms.Impl.RegisterInterfaceImpl;
import com.example.rkr.forms.Impl.RegisterProductInterfaceImpl;
import com.example.rkr.forms.RegisterInterface;
import com.example.rkr.models.Categories;
import com.example.rkr.models.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterProductActivity extends BaseActivity {

    private final RegisterProductInterfaceImpl productInterface = new RegisterProductInterfaceImpl();
    private final ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private final RegisterInterfaceImpl registerInterface = new RegisterInterfaceImpl(); // To fetch last ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_product);
        setHeader("Реєстрація продукту");

        SharedPreferences pref = getSharedPreferences("auth", MODE_PRIVATE);
        String company_name = pref.getString("companyName", "");
        EditText name = findViewById(R.id.product_name);
        EditText price = findViewById(R.id.product_price);
        EditText barCode = findViewById(R.id.product_code);
        Spinner category = findViewById(R.id.product_category);
        Spinner unit = findViewById(R.id.product_unit);
        EditText url = findViewById(R.id.product_image_url);
        EditText quantity = findViewById(R.id.product_quantity);
        EditText manufacturer = findViewById(R.id.product_manufacturer); // Added from XML
        manufacturer.setText(company_name);

        ArrayAdapter<QuantityItems> unitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, QuantityItems.values());
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit.setAdapter(unitAdapter);

        Call<List<Categories>> listCall = apiService.getCategories();
        listCall.enqueue(new Callback<List<Categories>>() {
            @Override
            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                if (response.body() != null && !response.body().isEmpty()) {
                    List<Categories> list = new ArrayList<>(response.body());
                    ArrayAdapter<Categories> categoryAdapter = new ArrayAdapter<>(RegisterProductActivity.this, android.R.layout.simple_spinner_item, list);
                    categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    category.setAdapter(categoryAdapter);
                } else {
                    Toast.makeText(RegisterProductActivity.this, "Categories are empty!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Categories>> call, Throwable t) {
                Log.e("RegisterProductActivity", "Error fetching categories: " + t.getMessage());
            }
        });

        Button apply = findViewById(R.id.register_button);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName = name.getText().toString();
                String productPrice = price.getText().toString();
                String productQuantity = quantity.getText().toString() + unit.getSelectedItem().toString();
                String productUrl = url.getText().toString();
                String productCode = barCode.getText().toString();
                String productCategory = category.getSelectedItem() != null ? category.getSelectedItem().toString() : "";
                String productManufacturer = manufacturer.getText().toString();

                if (productName.isBlank()) {
                    Toast.makeText(RegisterProductActivity.this, "Name must be filled!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (productCode.isBlank()) {
                    Toast.makeText(RegisterProductActivity.this, "Bar_code must be filled!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (productPrice.isBlank()) {
                    Toast.makeText(RegisterProductActivity.this, "Price must be filled!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (productUrl.isBlank()) {
                    Toast.makeText(RegisterProductActivity.this, "URL must be filled!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (productQuantity.isBlank()) {
                    Toast.makeText(RegisterProductActivity.this, "Quantity must be filled!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Fetch last ID and register product
                registerInterface.getLastUserId().enqueue(new Callback<RegisterInterface.LastIdResponse>() {
                    @Override
                    public void onResponse(Call<RegisterInterface.LastIdResponse> call, Response<RegisterInterface.LastIdResponse> response) {
                        if (response.isSuccessful()) {
                            int lastId = response.body().getLastId();
                            int newId = lastId + 1;

                            Product newModel = new Product(
                                    newId, productName, productPrice, productQuantity, productCategory, productCode, productManufacturer, productUrl
                            );
                            productInterface.register(newModel).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(RegisterProductActivity.this, "Product registered successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(RegisterProductActivity.this, "Registration failed: " + response.code() + " - " + response.message(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(RegisterProductActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            Toast.makeText(RegisterProductActivity.this, "Failed to fetch last ID: " + response.code() + " - " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterInterface.LastIdResponse> call, Throwable t) {
                        Toast.makeText(RegisterProductActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
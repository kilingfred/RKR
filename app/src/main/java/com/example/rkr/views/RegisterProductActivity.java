package com.example.rkr.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import com.example.rkr.BaseActivity;
import com.example.rkr.R;
import com.example.rkr.forms.Impl.RegisterProductInterfaceImpl;
import com.example.rkr.models.Product;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterProductActivity extends BaseActivity {

    RegisterProductInterfaceImpl productInterface = new RegisterProductInterfaceImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_product);
        setHeader("Реєстрація продукту");
        EditText name = findViewById(R.id.product_name);
        EditText price = findViewById(R.id.product_price);
        EditText bar_code = findViewById(R.id.product_code);
        Spinner category = findViewById(R.id.product_category);
        Spinner unit = findViewById(R.id.product_unit);
        EditText url = findViewById(R.id.product_image_url);
        EditText quantity = findViewById(R.id.product_quantity);



        Button apply = findViewById(R.id.register_button);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String product_name = name.getText().toString();
                String product_price = price.getText().toString();
                String product_quantity = quantity.getText().toString() + unit.getSelectedItem().toString();
                String product_url = url.getText().toString();
                String product_code = bar_code.getText().toString();
                String product_category = category.getSelectedItem().toString();

                if(product_name.isBlank()) {
                    Toast.makeText(RegisterProductActivity.this,"Name must be filled!", Toast.LENGTH_SHORT).show();return;
                }
                if(product_code.isBlank()) {
                    Toast.makeText(RegisterProductActivity.this,"Name must be filled!", Toast.LENGTH_SHORT).show();return;
                }
                if(product_price.isBlank()) {
                    Toast.makeText(RegisterProductActivity.this,"Name must be filled!", Toast.LENGTH_SHORT).show();return;
                }
                if(product_url.isBlank()) {
                    Toast.makeText(RegisterProductActivity.this,"Name must be filled!", Toast.LENGTH_SHORT).show();return;
                }
                if(product_quantity.isBlank()){
                    Toast.makeText(RegisterProductActivity.this,"Name must be filled!", Toast.LENGTH_SHORT).show();return;
                }

                Product new_model = new Product(product_name, product_price, product_quantity, product_category, product_code, product_url);

                productInterface.register(new_model).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(RegisterProductActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(RegisterProductActivity.this, "Registration failed: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(RegisterProductActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }
}
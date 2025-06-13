package com.example.rkr.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rkr.BaseActivity;
import com.example.rkr.R;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setHeader("Головне меню");
        Button productsButton = findViewById(R.id.productsButton);
        Button companiesButton = findViewById(R.id.companiesButton);

        TextView textView = findViewById(R.id.welcomeTextView);
        SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
        String name = prefs.getString("companyName", "Unknown");
        textView.setText("Ласкаво просимо " + name);

        productsButton.setOnClickListener(v -> {
            startActivity(new Intent(this, ProductsActivity.class));
        });

        companiesButton.setOnClickListener(v -> {
            startActivity(new Intent(this, CompaniesActivity.class));
        });
    }
}
package com.example.rkr;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rkr.adapters.CompanyAdapter;
import com.example.rkr.api.ApiService;
import com.example.rkr.api.RetrofitClient; // Важливо: переконайтеся, що імпорт правильний
import com.example.rkr.models.Company;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompaniesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CompanyAdapter adapter;
    private TextView titleTextView;
    private List<Company> companyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies);

        // Перевірка стану входу
        SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
        if (!isLoggedIn) {
            Toast.makeText(this, "Будь ласка, увійдіть", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Ініціалізація RecyclerView
        recyclerView = findViewById(R.id.companiesRecyclerView); // Переконайтеся, що id в activity_companies.xml правильний
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        companyList = new ArrayList<>();
        adapter = new CompanyAdapter(companyList);
        recyclerView.setAdapter(adapter);

        titleTextView = findViewById(R.id.titleTextView);
        titleTextView.setText(R.string.loading); // Можна встановити "Завантаження..."

        // Завантаження компаній
        loadCompanies();
    }

    private void loadCompanies() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Викликаємо getCompanies()
        Call<List<Company>> call = apiService.getCompanies();
        call.enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    companyList.clear();
                    companyList.addAll(response.body());
                    adapter.updateCompanies(companyList);
                    titleTextView.setText(R.string.companies); // Встановіть остаточний заголовок
                    Toast.makeText(CompaniesActivity.this, "Компанії завантажено успішно!", Toast.LENGTH_SHORT).show();
                } else {
                    titleTextView.setText(R.string.error_loading_data);
                    Toast.makeText(CompaniesActivity.this, "Помилка завантаження компаній: " + response.code(), Toast.LENGTH_SHORT).show();
                    try {
                        if (response.errorBody() != null) {
                            String error = response.errorBody().string();
                            Toast.makeText(CompaniesActivity.this, "Помилка API: " + error, Toast.LENGTH_LONG).show();
                            System.err.println("API Error Response: " + error);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable t) {
                titleTextView.setText(R.string.error_loading_data);
                Toast.makeText(CompaniesActivity.this, "Помилка мережі: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
package com.example.rkr.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rkr.BaseActivity;
import com.example.rkr.R;
import com.example.rkr.adapters.CompanyAdapter;
import com.example.rkr.api.ApiService;
import com.example.rkr.api.RetrofitClient; // Важливо: переконайтеся, що імпорт правильний
import com.example.rkr.models.CompanyModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompaniesActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private CompanyAdapter adapter;
    private TextView titleTextView;
    private List<CompanyModel> companyModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies);

        setHeader("Список компаній");
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

        companyModelList = new ArrayList<>();
        adapter = new CompanyAdapter(companyModelList);
        recyclerView.setAdapter(adapter);

        adapter.setOnCompanyClickListener(company -> {
            Intent intent = new Intent(CompaniesActivity.this, CompanyActivity.class);
            intent.putExtra("company", company.getName()); // or use Parcelable/Serializable
            startActivity(intent);
        });

        // Завантаження компаній
        loadCompanies();
    }

    private void loadCompanies() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Викликаємо getCompanies()
        Call<List<CompanyModel>> call = apiService.getCompanies();
        call.enqueue(new Callback<List<CompanyModel>>() {
            @Override
            public void onResponse(Call<List<CompanyModel>> call, Response<List<CompanyModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    companyModelList.clear();
                    companyModelList.addAll(response.body());
                    adapter.updateCompanies(companyModelList);
//                    titleTextView.setText(R.string.companies); // Встановіть остаточний заголовок
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
            public void onFailure(Call<List<CompanyModel>> call, Throwable t) {
                titleTextView.setText(R.string.error_loading_data);
                Toast.makeText(CompaniesActivity.this, "Помилка мережі: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
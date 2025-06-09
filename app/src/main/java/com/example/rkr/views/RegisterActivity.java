package com.example.rkr.views;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rkr.BaseActivity;
import com.example.rkr.R;
import com.example.rkr.enums.UserTypes;
import com.example.rkr.forms.Impl.RegisterInterfaceImpl;
import com.example.rkr.models.CompanyRegisterModel;
import com.example.rkr.models.UserModel;
import com.example.rkr.forms.RegisterInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {

    private final RegisterInterfaceImpl registerInterface = new RegisterInterfaceImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        setHeader("Реєстрація користувача");
        updateHeader(null);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner accountTypeSpinner = findViewById(R.id.account_type);
        ArrayAdapter<UserTypes> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, UserTypes.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypeSpinner.setAdapter(adapter);
        EditText loginEditText = findViewById(R.id.account_login);
        EditText emailEditText = findViewById(R.id.account_email);
        EditText passwordEditText = findViewById(R.id.account_password);
        EditText confirmPasswordEditText = findViewById(R.id.account_approve);
        EditText companyNameEditText = findViewById(R.id.company_name);
        EditText companyAddressEditText = findViewById(R.id.company_address);
        EditText companyPhoneEditText = findViewById(R.id.company_phone);
        View companyLayout = findViewById(R.id.company_layout);

        accountTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UserTypes selected = (UserTypes) parent.getItemAtPosition(position);
                companyLayout.setVisibility(selected == UserTypes.COMPANY ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Button register = findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userType = accountTypeSpinner.getSelectedItem().toString();
                String login = loginEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if (login.isBlank()) { Toast.makeText(RegisterActivity.this, "Login field must be filled!", Toast.LENGTH_SHORT).show(); return; }
                if (email.isBlank()) { Toast.makeText(RegisterActivity.this, "Email field must be filled!", Toast.LENGTH_SHORT).show(); return; }
                if (password.isBlank()) { Toast.makeText(RegisterActivity.this, "Password field must be filled!", Toast.LENGTH_SHORT).show(); return; }
                if (confirmPassword.isBlank()) { Toast.makeText(RegisterActivity.this, "Repeat Password field must be filled!", Toast.LENGTH_SHORT).show(); return; }
                if (password.length() < 6) { Toast.makeText(RegisterActivity.this, "Password must be at least 6 characters!", Toast.LENGTH_SHORT).show(); return; }
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, R.string.passwordCompareFail, Toast.LENGTH_SHORT).show();
                    return;
                }

                // Fetch the last ID and register
                registerInterface.getLastUserId().enqueue(new Callback<RegisterInterface.LastIdResponse>() {
                    @Override
                    public void onResponse(Call<RegisterInterface.LastIdResponse> call, Response<RegisterInterface.LastIdResponse> response) {
                        if (response.isSuccessful()) {
                            int lastId = response.body().getLastId();
                            int newId = lastId + 1;

                            if (userType.equals(UserTypes.COMPANY.getValue())) {
                                String companyName = companyNameEditText.getText().toString();
                                String companyAddress = companyAddressEditText.getText().toString();
                                String companyPhone = companyPhoneEditText.getText().toString();

                                if (companyName.isBlank() || companyAddress.isBlank() || companyPhone.isBlank()) {
                                    Toast.makeText(RegisterActivity.this, "All company fields must be filled!", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                CompanyRegisterModel companyModel = new CompanyRegisterModel(
                                        newId, userType, login, email, password, companyName, companyAddress, companyPhone
                                );
                                registerInterface.registerCompany(companyModel, new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(RegisterActivity.this, "Company registered successfully", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "Registration failed: " + response.code() + " - " + response.message(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(RegisterActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            } else {
                                UserModel userModel = new UserModel(newId, userType, login, email, password);
                                registerInterface.registerUser(userModel, new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "Registration failed: " + response.code() + " - " + response.message(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(RegisterActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "Failed to fetch last ID: " + response.code() + " - " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterInterface.LastIdResponse> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
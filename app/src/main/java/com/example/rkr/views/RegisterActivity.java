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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rkr.MainActivity;
import com.example.rkr.R;
import com.example.rkr.enums.UserTypes;
import com.example.rkr.forms.Impl.RegisterInterfaceImpl;
import com.example.rkr.forms.RegisterInterface;
import com.example.rkr.models.CompanyRegisterModel;
import com.example.rkr.models.UserRegisterModel;
import com.example.rkr.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends MainActivity {

    private final RegisterInterfaceImpl registerInterface = new RegisterInterfaceImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
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
                UserTypes userType = (UserTypes) accountTypeSpinner.getSelectedItem();
                String login = loginEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if(login.isBlank()) {Toast.makeText(RegisterActivity.this,"Login field must be filled!", Toast.LENGTH_SHORT).show();return;}
                if(email.isBlank()) {Toast.makeText(RegisterActivity.this,"Login field must be filled!", Toast.LENGTH_SHORT).show();return;}
                if(password.isBlank()) {Toast.makeText(RegisterActivity.this,"Login field must be filled!", Toast.LENGTH_SHORT).show();return;}
                if(confirmPassword.isBlank()) {Toast.makeText(RegisterActivity.this,"Login field must be filled!", Toast.LENGTH_SHORT).show();return;}

                if(password.length() < 6) {Toast.makeText(RegisterActivity.this,"Password must contain at least 6 characters!", Toast.LENGTH_SHORT).show();return;}

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, R.string.passwordCompareFail, Toast.LENGTH_SHORT).show();
                    return;
                }

                UserRegisterModel userModel;
                if (userType == UserTypes.COMPANY) {
                    String companyName = companyNameEditText.getText().toString();
                    String companyAddress = companyAddressEditText.getText().toString();
                    String companyPhone = companyPhoneEditText.getText().toString();

                    if(companyName.isBlank()) {Toast.makeText(RegisterActivity.this,"Login field must be filled!", Toast.LENGTH_SHORT).show();return;}
                    if(companyAddress.isBlank()) {Toast.makeText(RegisterActivity.this,"Login field must be filled!", Toast.LENGTH_SHORT).show();return;}
                    if(companyPhone.isBlank()) {Toast.makeText(RegisterActivity.this,"Login field must be filled!", Toast.LENGTH_SHORT).show();return;}

                    userModel = new UserRegisterModel(login, password, userType, email);
                    CompanyRegisterModel companyModel = new CompanyRegisterModel(userModel,companyName, companyAddress, companyPhone);
                    registerInterface.registerCompany(companyModel,new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Company registered successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Registration failed: " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                } else {
                    userModel = new UserRegisterModel(login, password, userType, email);
                    registerInterface.registerUser(userModel, new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Registration failed: " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}
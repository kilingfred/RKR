package com.example.rkr.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rkr.BaseActivity;
import com.example.rkr.R;
import com.example.rkr.forms.Impl.loginInterfaceImpl; // Переконайтеся, що імпорт правильний
import com.example.rkr.forms.loginInterface; // Переконайтеся, що імпорт правильний


public class MainActivity extends BaseActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private Button loginButton;
    private Button googleLoginButton; // Якщо ви плануєте реалізувати Google Login
    private TextView resultTextView;

    private loginInterface authService; // Використовуємо інтерфейс для логіки автентифікації

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Ваш XML-файл для екрану входу/реєстрації

        // Перевірка, чи користувач вже увійшов
        SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // Якщо користувач вже увійшов, переходимо на HomeActivity
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish(); // Завершуємо поточну активність, щоб користувач не міг повернутися назад
            return; // Виходимо з onCreate
        }

        // Ініціалізація компонентів UI
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.loginButton);
        googleLoginButton = findViewById(R.id.googleLoginButton); // Якщо є у розмітці
        resultTextView = findViewById(R.id.resultTextView);

        // Ініціалізація сервісу автентифікації
        authService = new loginInterfaceImpl();

        // Обробник натискання кнопки реєстрації
        registerButton.setOnClickListener(v -> {
            authService.register(MainActivity.this);
        });

        // Обробник натискання кнопки входу
        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Будь ласка, введіть ім'я користувача та пароль.", Toast.LENGTH_SHORT).show();
                return;
            }

            ((loginInterfaceImpl)authService).loginWithCredentials(username, password, (success, user) -> {
                runOnUiThread(() -> {
                    if (success) {
                        resultTextView.setText("Вхід успішний!");
                        Toast.makeText(MainActivity.this, "Вхід успішний!", Toast.LENGTH_SHORT).show();

                        // Зберегти стан входу та додаткову інформацію про користувача
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.putString("loggedInUsername", user.getLogin()); // Save login field
                        editor.putString("userType", user.getType() != null ? user.getType() : ""); // "COMPANY" або "CUSTOMER"
                        editor.putString("companyName", user.getLogin()); // Use login as companyName
                        editor.apply();

                        // Перехід на HomeActivity
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        resultTextView.setText("Вхід не вдався. Невірне ім'я користувача або пароль.");
                        Toast.makeText(MainActivity.this, "Невірне ім'я користувача або пароль.", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        });

        // Обробник натискання кнопки входу через Google (якщо реалізовано)
        googleLoginButton.setOnClickListener(v -> {
            // Тут ви можете викликати метод для входу через Google
            // authService.loginUsingGoogle(); // Це лише заглушка, реалізація потребує Google Sign-In SDK
            Toast.makeText(MainActivity.this, "Вхід через Google ще не реалізовано.", Toast.LENGTH_SHORT).show();
        });
    }
}
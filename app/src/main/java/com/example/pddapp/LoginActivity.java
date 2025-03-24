package com.example.pddapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pddapp.db.AppDatabase;
import com.example.pddapp.db.User;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnSignIn;
    private TextView tvSignUp;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        appDatabase = AppDatabase.getDatabase(this);

        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        btnSignIn = findViewById(R.id.signin_btn);
        tvSignUp = findViewById(R.id.signup);

        btnSignIn.setOnClickListener(v -> loginUser());
        tvSignUp.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            finish();
        });
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            showErrorDialog("Все поля должны быть заполнены");
            return;
        }

        new Thread(() -> {
            User user = appDatabase.userDao().getUser(email, password);
            runOnUiThread(() -> {
                if (user == null) {
                    showErrorDialog("Неверный email или пароль");
                } else {
                    StartActivity.saveUserId(LoginActivity.this, user.id);
                    Toast.makeText(this, "Авторизация прошла успешно!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            });
        }).start();
    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Ошибка")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}
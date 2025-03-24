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

public class SignUpActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etEmail, etPassword;
    private Button btnSignUp;
    private TextView tvSignIn;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);


        appDatabase = AppDatabase.getDatabase(this);

        etFirstName = findViewById(R.id.fname);
        etLastName = findViewById(R.id.lname);
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        btnSignUp = findViewById(R.id.signup_btn);
        tvSignIn = findViewById(R.id.signin);

        btnSignUp.setOnClickListener(v -> registerUser());
        tvSignIn.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void registerUser() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showErrorDialog("Все поля должны быть заполнены");
            return;
        }


        new Thread(() -> {
            User existingUser = appDatabase.userDao().getUserByEmail(email);
            if (existingUser != null) {
                runOnUiThread(() -> showErrorDialog("Пользователь с таким email уже существует"));
                return;
            }

            User newUser = new User(firstName, lastName, email, password);
            appDatabase.userDao().insert(newUser);

            runOnUiThread(() -> {
                Toast.makeText(this, "Регистрация прошла успешно!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
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
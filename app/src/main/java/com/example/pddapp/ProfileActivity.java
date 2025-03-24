package com.example.pddapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pddapp.db.AppDatabase;
import com.example.pddapp.db.User;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        int userId = StartActivity.getCurrentUserId(this);

        if (userId != -1) {
            // Получаем данные пользователя из базы данных
            new Thread(() -> {
                User user = AppDatabase.getDatabase(this).userDao().getUserById(userId);

                runOnUiThread(() -> {
                    if (user != null) {
                        TextView tvName = findViewById(R.id.name);
                        TextView tvEmail = findViewById(R.id.email);

                        tvName.setText(user.firstName + " " + user.lastName);
                        tvEmail.setText(user.email);
                    }
                });
            }).start();
        }

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
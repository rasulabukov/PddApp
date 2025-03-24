package com.example.pddapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rules);

        RecyclerView recyclerView = findViewById(R.id.rulesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> rulesList = Arrays.asList(
                "Правила дорожного движения",
                "Дорожные знаки",
                "Дорожная разметка",
                "Перечень неисправностей",
                "Основные положения по допуску",
                "Штрафы"
        );

        RulesAdapter adapter = new RulesAdapter(rulesList);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class RulesAdapter extends RecyclerView.Adapter<RulesAdapter.RulesViewHolder> {

        private List<String> rulesList;

        public RulesAdapter(List<String> rulesList) {
            this.rulesList = rulesList;
        }

        @Override
        public RulesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new RulesViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RulesViewHolder holder, int position) {
            holder.textView.setText(rulesList.get(position));
            holder.itemView.setOnClickListener(v -> showEmptyDialog());
        }

        @Override
        public int getItemCount() {
            return rulesList.size();
        }

        private void showEmptyDialog() {
            new AlertDialog.Builder(RulesActivity.this)
                    .setMessage("Пусто")
                    .setPositiveButton("OK", null)
                    .show();
        }

        class RulesViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public RulesViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(android.R.id.text1);
            }
        }
    }
}


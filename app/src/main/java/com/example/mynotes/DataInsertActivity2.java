package com.example.mynotes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mynotes.databinding.ActivityDataInsert2Binding;

public class DataInsertActivity2 extends AppCompatActivity {

    ActivityDataInsert2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataInsert2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String type = getIntent().getStringExtra("type");

        if ("update".equals(type)) {
            binding.tvScreenTitle.setText("Edit Note");
            binding.title.setText(getIntent().getStringExtra("title"));
            binding.disp.setText(getIntent().getStringExtra("disp"));
            int id = getIntent().getIntExtra("id", -1);
            binding.button.setText("Update Note");
            binding.button.setOnClickListener(v -> {
                if (validateInput()) {
                    Intent result = new Intent();
                    result.putExtra("title", binding.title.getText().toString().trim());
                    result.putExtra("disp", binding.disp.getText().toString().trim());
                    result.putExtra("id", id);
                    setResult(RESULT_OK, result);
                    finish();
                }
            });
        } else {
            binding.tvScreenTitle.setText("New Note");
            binding.button.setText("Save Note");
            binding.button.setOnClickListener(v -> {
                if (validateInput()) {
                    Intent result = new Intent();
                    result.putExtra("title", binding.title.getText().toString().trim());
                    result.putExtra("disp", binding.disp.getText().toString().trim());
                    setResult(RESULT_OK, result);
                    finish();
                }
            });
        }
    }

    private boolean validateInput() {
        String title = binding.title.getText().toString().trim();
        String disp = binding.disp.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            binding.title.setError("Title cannot be empty");
            binding.title.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(disp)) {
            binding.disp.setError("Note content cannot be empty");
            binding.disp.requestFocus();
            return false;
        }
        return true;
    }
}
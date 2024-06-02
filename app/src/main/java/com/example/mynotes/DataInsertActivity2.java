package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mynotes.databinding.ActivityDataInsert2Binding;

public class  DataInsertActivity2 extends AppCompatActivity {
    ActivityDataInsert2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDataInsert2Binding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        String type = getIntent().getStringExtra("type");
        if (type.equals("update")) {
            setTitle("Update");
          binding.title.setText(getIntent().getStringExtra("title"));
            binding.disp.setText(getIntent().getStringExtra("disp"));
            int id=getIntent().getIntExtra("id",0);
            binding.button.setText("update Note");
             binding.button.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent intent = new Intent();
                     intent.putExtra("title", binding.title.getText().toString());
                     intent.putExtra("disp", binding.disp.getText().toString());
                     intent.putExtra("id",id );
                     setResult(RESULT_OK, intent);
                     finish();
                 }

             });

        } else {

            setTitle("add mode");
            binding.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("title", binding.title.getText().toString());
                    intent.putExtra("disp", binding.disp.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });

        }
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(DataInsertActivity2.this,MainActivity.class));
        finish();
    }
}
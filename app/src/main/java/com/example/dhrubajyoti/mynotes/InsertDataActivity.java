package com.example.dhrubajyoti.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dhrubajyoti.mynotes.databinding.ActivityInsertDataBinding;

public class InsertDataActivity extends AppCompatActivity {
    ActivityInsertDataBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Add Note");

        if(getIntent().getStringExtra("type").equals("update")){
            setTitle("Update Note");
            binding.button.setText("Update Note");
            binding.title.setText(getIntent().getStringExtra("title"));
            binding.desc.setText(getIntent().getStringExtra("desc"));
        }

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent()
                        .putExtra("title", binding.title.getText().toString())
                        .putExtra("desc", binding.desc.getText().toString());
                if(getIntent().getStringExtra("type").equals("update")){
                    intent.putExtra("id", getIntent().getIntExtra("id", 0));
                }
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(InsertDataActivity.this, MainActivity.class));
    }
}
package com.example.streetfix;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button reportBtn, viewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reportBtn = findViewById(R.id.reportBtn);
        viewBtn = findViewById(R.id.viewBtn);

        reportBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ReportIssueActivity.class);
            startActivity(intent);
        });

        viewBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewReportsActivity.class);
            intent.putExtra("role", "user"); // user role
            startActivity(intent);
        });
    }
}
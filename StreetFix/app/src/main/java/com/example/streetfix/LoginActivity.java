package com.example.streetfix;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button userBtn, officerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userBtn = findViewById(R.id.userButton);
        officerBtn = findViewById(R.id.officerButton);

        userBtn.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, UserLoginActivity.class);
            startActivity(intent);
        });

        officerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, OfficerLoginActivity.class);
            startActivity(intent);
        });

    }
}
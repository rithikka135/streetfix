package com.example.streetfix;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class OfficerLoginActivity extends AppCompatActivity {

    EditText officerIdEdit, passwordEdit;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officer_login);

        officerIdEdit = findViewById(R.id.officerIdEdit);
        passwordEdit = findViewById(R.id.passwordEdit);
        loginBtn = findViewById(R.id.loginButton);

        loginBtn.setOnClickListener(v -> {
            String id = officerIdEdit.getText().toString();
            String pass = passwordEdit.getText().toString();

            if(id.equals("admin") && pass.equals("1234")){
                Intent intent = new Intent(OfficerLoginActivity.this, ViewReportsActivity.class);
                intent.putExtra("role", "officer"); // officer role
                startActivity(intent);
            } else {
                Toast.makeText(this,"Invalid Officer Login",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
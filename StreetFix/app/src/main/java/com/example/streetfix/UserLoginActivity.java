package com.example.streetfix;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UserLoginActivity extends AppCompatActivity {

    EditText emailEdit, passwordEdit;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        emailEdit = findViewById(R.id.emailEdit);
        passwordEdit = findViewById(R.id.passwordEdit);
        loginBtn = findViewById(R.id.loginButton);

        loginBtn.setOnClickListener(v -> {
            String email = emailEdit.getText().toString();
            String pass = passwordEdit.getText().toString();

            if(email.equals("user@gmail.com") && pass.equals("1234")){
                Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this,"Invalid User Login",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
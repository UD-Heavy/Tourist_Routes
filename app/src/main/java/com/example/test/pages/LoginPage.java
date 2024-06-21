package com.example.test.pages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;

public class LoginPage extends AppCompatActivity {

    private Button registrationButton;
    private Button loginButton;
    private Intent registrationPage;
    private SharedPreferences sharedPreferences;

    // обработка события при создании окна
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_page);
        init();

        // проверка, нужно ли создавать аккаунт
        sharedPreferences = getSharedPreferences("Account", Context.MODE_PRIVATE);

        if (sharedPreferences.getString("Acc", "123").equals("true")) {
            startActivity(new Intent(LoginPage.this, MainMenuPage.class));
        } else {
            registrationButton = findViewById(R.id.buttonRegister);
            loginButton = findViewById(R.id.buttonEnter);

            // обработка события при нажатии на кнопку
            registrationButton.setOnClickListener(v -> {
                startActivity(registrationPage);
            });

            loginButton.setOnClickListener(v -> {
                startActivity(new Intent(LoginPage.this, MainMenuPage.class));
            });
        }
    }

    private void init() {
        registrationPage = new Intent(LoginPage.this, RegistrationPage.class);
    }

}

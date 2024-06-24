package com.example.test.pages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;

public class LoginPage extends AppCompatActivity {

    private Button registrationButton;
    private Button loginButton;
    private Intent mainMenuPage;
    private SharedPreferences sharedPreferences;

    // обработка события при создании окна
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_page);
        init();

        if (sharedPreferences.getString("Acc", "123").equals("true")) {
            startActivity(mainMenuPage);
        }
    }

    private void init() {
        // проверка, нужно ли создавать аккаунт
        sharedPreferences = getSharedPreferences("Account", Context.MODE_PRIVATE);

        mainMenuPage = new Intent(LoginPage.this, MainMenuPage.class);

        registrationButton = findViewById(R.id.buttonRegister);
        loginButton = findViewById(R.id.buttonEnter);
    }

    public void onClickLogin(View v) {
        startActivity(mainMenuPage);
    }

    public void onClickRegistration(View v){
        startActivity(new Intent(LoginPage.this, RegistrationPage.class));
    }
}

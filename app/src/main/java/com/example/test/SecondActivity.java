package com.example.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    // обработка события при создании окна
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.register_window);

        // объявление объектов для работы
        Button button1 = findViewById(R.id.do_Register);

        // обработка события при нажатии на кнопку
        button1.setOnClickListener(v -> {
            finish();
        });
    }

}
package com.example.test;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // обработка события при создании окна
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // объявление объектов для работы
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.buttonRegister);
        Button button1 = findViewById(R.id.buttonEnter);

        // обработка события при нажатии на кнопку
        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });
        button1.setOnClickListener(v -> {
            //setContentView(R.layout.register_window);
            Intent intent1 = new Intent(MainActivity.this, MainWindow.class);
            startActivity(intent1);

        });
    }

}

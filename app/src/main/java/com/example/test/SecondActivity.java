package com.example.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {
    EditText logintext;
    EditText password_text;
    EditText email_text;
    EditText repeat_pass_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.register_window);
        Button button1 = findViewById(R.id.do_Register);
        logintext = (EditText) findViewById(R.id.login_text);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setContentView(R.layout.activity_main);
                if (TextUtils.isEmpty(logintext.getText().toString()))
                {
                    Toast.makeText(SecondActivity.this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    finish();
                    Toast.makeText(SecondActivity.this, "Пользователь зарегистрирован!", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // Переопределяем метод dispatchTouchEvent для скрытия клавиатуры при касании пустого места
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                v.clearFocus();
                InputMethodManager imm = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                }
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
        return super.dispatchTouchEvent(event);
    }

}
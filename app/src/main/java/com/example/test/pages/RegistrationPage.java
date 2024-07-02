package com.example.test.pages;

import static com.example.test.utils.RegistrationValidator.emailValidator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.test.R;
import com.example.test.exceptions.EmailNotCorrectException;
import com.example.test.models.User;


public class RegistrationPage extends AppCompatActivity {
    private EditText loginText;
    private EditText passwordText;
    private EditText emailText;
    private EditText passwordConfirmText;
    private Button registerButton;

    private String login;
    private String email;
    private String password;
    private String passwordConfirm;
    private SharedPreferences.Editor sp;


    // обработка события при создании окна
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.register_page);
        init();
    }

    // объявление объектов для работы
    private void init() {
        registerButton = findViewById(R.id.do_Register);
        loginText = findViewById(R.id.email_text);
        emailText = findViewById(R.id.email_text);
        passwordText = findViewById(R.id.password_text);
        passwordConfirmText = findViewById(R.id.repeat_pass_text);

        // проверка, нужно ли создавать аккаунт
        sp = getSharedPreferences("Account", Context.MODE_PRIVATE).edit();
    }

    // скрытие клавиатуры при нажатии на пустое место
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
                assert imm != null;
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public void onClickRegister(View v) {

        // берем данные из текстовых полей
        login = loginText.getText().toString().trim();
        email = emailText.getText().toString().trim();
        password = passwordText.getText().toString().trim();
        passwordConfirm = passwordConfirmText.getText().toString().trim();

        // есть ли пустые поля
        if (login.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty())
            Toast.makeText(RegistrationPage.this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
            // одинаковый пароль и подтверждение пароля
        else if (!TextUtils.equals(password, passwordConfirm))
            Toast.makeText(RegistrationPage.this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
            // создание аккаунта
        else {
            try {
                emailValidator(email); // валидация почты
                User user = new User(login, email, password);
                editSharedPreferences();
                startActivity(new Intent(RegistrationPage.this, MainMenuPage.class));
            } catch (EmailNotCorrectException e) {
                Toast.makeText(RegistrationPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void editSharedPreferences() {
        sp.putString("Acc", "true").commit();
        sp.putString("Login", login).commit();
        sp.putString("Email", email).commit();
    }

}
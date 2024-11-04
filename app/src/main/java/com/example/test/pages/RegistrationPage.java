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


/**
 * Класс, представляющий экран регистрации пользователя. Предоставляет функционал
 * для ввода данных о пользователе, проверки введённых данных и создания аккаунта.
 */
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


    /**
     * Инициализирует экран регистрации при его создании. Устанавливает макет,
     * включает поддержку Edge-to-Edge и вызывает метод {@link 'init} для инициализации полей.
     *
     * @param savedInstanceState объект {@link Bundle},
     * содержащий данные о предыдущем состоянии активности
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.register_page);
        init();
    }

    /**
     * Инициализирует элементы пользовательского интерфейса и создаёт
     * редактор для хранения данных об аккаунте пользователя в SharedPreferences.
     */
    private void init() {
        registerButton = findViewById(R.id.do_Register);
        loginText = findViewById(R.id.email_text);
        emailText = findViewById(R.id.email_text);
        passwordText = findViewById(R.id.password_text);
        passwordConfirmText = findViewById(R.id.repeat_pass_text);

        // проверка, нужно ли создавать аккаунт
        sp = getSharedPreferences("Account", Context.MODE_PRIVATE).edit();
    }

    /**
     * Обрабатывает касание экрана для скрытия клавиатуры при нажатии на пустую область.
     * Если фокус установлен на текстовом поле, то фокус снимается, и клавиатура скрывается.
     *
     * @param event объект {@link MotionEvent}, представляющий событие касания экрана
     * @return возвращает результат обработки события методом {@code super.dispatchTouchEvent(event)}
     */
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

    /**
     * Обрабатывает нажатие на кнопку регистрации. Проверяет введённые данные на корректность,
     * валидирует email, создаёт нового пользователя и сохраняет данные в SharedPreferences.
     *
     * @param v объект {@link View}, представляющий кнопку, на которую было нажато
     */
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

    /**
     * Сохраняет данные о пользователе в SharedPreferences, включая информацию о
     * том, что аккаунт создан, а также логин и email пользователя.
     */
    public void editSharedPreferences() {
        sp.putString("Acc", "true").commit();
        sp.putString("Login", login).commit();
        sp.putString("Email", email).commit();
    }

}
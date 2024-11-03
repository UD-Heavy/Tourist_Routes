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


/**
 * Класс, представляющий страницу входа в приложение. Предоставляет пользователю
 * возможность войти в аккаунт или зарегистрироваться. При успешной проверке учетной записи
 * автоматически переходит на главный экран.
 */
public class LoginPage extends AppCompatActivity {

    private Button registrationButton;
    private Button loginButton;
    private Intent mainMenuPage;
    private SharedPreferences sharedPreferences;

    /**
     * Вызывается при создании активности. Устанавливает макет, включает поддержку Edge-to-Edge
     * и инициализирует элементы интерфейса. Проверяет, зарегистрирован ли пользователь,
     * и при наличии аккаунта автоматически направляет его на главный экран.
     *
     * @param savedInstanceState объект {@link Bundle}, содержащий данные о предыдущем состоянии активности
     */
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


    /**
     * Инициализирует элементы интерфейса и переменные, используемые для переходов
     * на другие страницы. Проверяет наличие учетной записи в настройках приложения.
     */
    private void init() {
        // проверка, нужно ли создавать аккаунт
        sharedPreferences = getSharedPreferences("Account", Context.MODE_PRIVATE);

        mainMenuPage = new Intent(LoginPage.this, MainMenuPage.class);

        registrationButton = findViewById(R.id.buttonRegister);
        loginButton = findViewById(R.id.buttonEnter);
    }

    /**
     * Обработчик события нажатия на кнопку входа. Запускает главную страницу приложения.
     *
     * @param v объект {@link View}, представляющий нажатую кнопку
     */
    public void onClickLogin(View v) {
        startActivity(mainMenuPage);
    }

    /**
     * Обработчик события нажатия на кнопку регистрации. Перенаправляет пользователя
     * на страницу регистрации.
     *
     * @param v объект {@link View}, представляющий нажатую кнопку
     */
    public void onClickRegistration(View v){
        startActivity(new Intent(LoginPage.this, RegistrationPage.class));
    }
}

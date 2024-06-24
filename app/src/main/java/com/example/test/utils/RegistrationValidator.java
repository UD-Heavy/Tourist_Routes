package com.example.test.utils;

import androidx.annotation.NonNull;

import com.example.test.exceptions.EmailNotCorrectException;
import com.example.test.exceptions.UserNotFoundException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrationValidator {

    private static final String USERS_REF = "User";

    // проверка почты на валидность
    public static void emailValidator(String email) {
        String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        // Используем регулярное выражение для проверки email-адреса
        if (!email.matches(emailRegex))
            throw new EmailNotCorrectException("Электронная почта не корректна");

    }

    public static void checkIfUserExists(String email) throws UserNotFoundException {
        // Получаем ссылку на базу данных Firebase
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        // Получаем ссылку на коллекцию Users
        DatabaseReference usersRef = database.child(USERS_REF);

        // Проверяем, существует ли пользователь с данным email
        usersRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Пользователь найден, можем продолжить выполнение кода
                    throw new UserNotFoundException("User with this email not found.");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Обработка ошибок подключения к базе данных
                System.err.println("Failed to read user data from Firebase database");
            }
        });
    }
}
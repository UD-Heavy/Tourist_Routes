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

    // проверка почты на валидность
    public static void emailValidator(String email) {
        String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        // Используем регулярное выражение для проверки email-адреса
        if (!email.matches(emailRegex))
            throw new EmailNotCorrectException("Электронная почта не корректна");

    }

}
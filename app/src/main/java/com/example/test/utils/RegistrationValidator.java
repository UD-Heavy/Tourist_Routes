package com.example.test.utils;

import com.example.test.exceptions.EmailNotCorrectException;


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
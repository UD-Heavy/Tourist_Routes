package com.example.test.utils;

import com.example.test.exceptions.EmailNotCorrectException;

public class RegistrationValidator {

    // проверка, что в почте есть @
    public static void checkEmail(String email) {
        if (!email.contains("@"))
            throw new EmailNotCorrectException("Электронная почта не корректна");
    }
}

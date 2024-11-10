package com.example.test.exceptions;

/**
 * Исключение, указывающее на то, что пользователь не найден.
 * Обычно используется для ситуаций, когда требуется наличие пользователя,
 * но он отсутствует в системе.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Конструктор, принимающий сообщение об ошибке, которое описывает причину исключения.
     *
     * @param message сообщение об ошибке
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}

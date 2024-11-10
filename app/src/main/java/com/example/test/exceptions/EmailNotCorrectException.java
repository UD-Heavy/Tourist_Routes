package com.example.test.exceptions;

/**
 * Исключение, указывающее на то, что формат электронной почты некорректен.
 * Используется для валидации электронной почты при регистрации или входе в систему.
 */
public class EmailNotCorrectException extends RuntimeException{

    /**
     * Конструктор, принимающий сообщение об ошибке, которое описывает причину исключения.
     *
     * @param msg сообщение об ошибке
     */
    public EmailNotCorrectException(String msg){
        super(msg);
    }
}

package com.example.test.models;


/**
 * Класс, представляющий модель пользователя в приложении. Содержит
 * данные о пользователе, включая имя, электронную почту и пароль.
 */
public class User {

    private String name;
    private String email;
    private String password;

    /**
     * Конструктор по умолчанию. Создает пустого пользователя без начальных данных.
     */
    public User() {
    }

    /**
     * Конструктор, принимающий параметры для инициализации данных пользователя.
     *
     * @param name     имя
     * @param email    электронная почта
     * @param password пароль
     */
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * Получает имя пользователя.
     *
     * @return имя пользователя
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает имя пользователя.
     *
     * @param name новое имя пользователя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Получает электронную почту пользователя.
     *
     * @return электронная почта пользователя
     */
    public String getEmail() {
        return email;
    }

    /**
     * Устанавливает электронную почту пользователя.
     *
     * @param email новая электронная почта пользователя
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Получает пароль пользователя.
     *
     * @return пароль пользователя
     */
    public String getPassword() {
        return password;
    }

    /**
     * Устанавливает пароль пользователя.
     *
     * @param password новый пароль пользователя
     */
    public void setPassword(String password) {
        this.password = password;
    }
}

package com.example.rkr.forms;

public interface registerInterface {
    /**
     * Реєструє нового користувача з вказаним ім'ям та паролем.
     * @param username Ім'я користувача.
     * @param password Пароль користувача.
     * @return true, якщо реєстрація успішна, false в іншому випадку.
     */
    boolean register(String username, String password);

    /**
     * Аутентифікує користувача з вказаним ім'ям та паролем.
     * @param username Ім'я користувача.
     * @param password Пароль користувача.
     * @return true, якщо вхід успішний, false в іншому випадку.
     */
    boolean login(String username, String password);
}
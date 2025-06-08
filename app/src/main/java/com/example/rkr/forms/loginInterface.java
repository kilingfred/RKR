package com.example.rkr.forms;

import android.content.Context;

public interface loginInterface {
    /**
     * Ініціює вхід через Google-аутентифікацію.
     */
    void loginUsingGoogle();

    /**
     * Реєструє нового користувача з вказаним ім'ям та паролем.
     * @param context Ім'я користувача.
     * @return true, якщо реєстрація успішна, false в іншому випадку.
     */
    void register(Context context);

}
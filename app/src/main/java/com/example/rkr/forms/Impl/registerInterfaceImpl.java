package com.example.rkr.forms.Impl;

import com.example.rkr.forms.registerInterface;

import java.util.HashMap;
import java.util.Map;

public class registerInterfaceImpl implements registerInterface {

    // Імітація бази даних користувачів у пам'яті
    private static final Map<String, String> users = new HashMap<>();

    @Override
    public boolean register(String username, String password) {
        // Перевірка вхідних даних
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return false;
        }
        // Перевірка, чи користувач уже існує
        if (users.containsKey(username)) {
            return false;
        }
        // Реєстрація користувача
        users.put(username, password); // У реальному додатку пароль слід хешувати
        return true;
    }

    @Override
    public boolean login(String username, String password) {
        // Перевірка вхідних даних
        if (username == null || password == null) {
            return false;
        }
        // Перевірка облікових даних
        if (users.containsKey(username)) {
            return users.get(username).equals(password); // У реальному додатку порівнювати хеші
        }
        return false;
    }
}
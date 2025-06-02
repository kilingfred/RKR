package com.example.rkr.forms.Impl;

import com.example.rkr.forms.loginInterface;
import com.example.rkr.forms.registerInterface;

public class loginInterfaceImpl implements loginInterface {

    private final registerInterface registerService = new registerInterfaceImpl();

    @Override
    public void loginUsingGoogle() {
        System.out.println("Вхід через Google ще не реалізовано.");
    }

    @Override
    public boolean loginWithCredentials(String username, String password) {
        boolean result = registerService.login(username, password);
        if (result) {
            System.out.println("Вхід успішний!");
        } else {
            System.out.println("Вхід не вдався.");
        }
        return result;
    }

    @Override
    public boolean register(String username, String password) {
        return registerService.register(username, password);
    }
}
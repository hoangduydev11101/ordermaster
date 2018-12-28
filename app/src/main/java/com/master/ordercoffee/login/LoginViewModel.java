package com.master.ordercoffee.login;

public class LoginViewModel {
    private static final LoginViewModel ourInstance = new LoginViewModel();

    public static LoginViewModel getInstance() {
        return ourInstance;
    }
}

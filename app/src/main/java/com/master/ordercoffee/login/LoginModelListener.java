package com.master.ordercoffee.login;

import com.google.firebase.auth.FirebaseUser;

public abstract class LoginModelListener  {
    public void onGetSmsCodeSuccess() {}

    public void onGetSmsCodeFailed(String message) {}

    public void onVerifySmsSuccess(FirebaseUser firebaseUser) {}

    public void onVerirySmsFailed(Exception e) {}
}

package com.master.ordercoffee.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.master.ordercoffee.login.LoginActivity;
import com.master.ordercoffee.main.MainActivity;

public class NavigationService {

    public static void goIntoView(Context currentActivity, Class toActivity) {
        Intent intent = new Intent(currentActivity, toActivity);
        currentActivity.startActivity(intent);
        ((Activity)currentActivity).finish();
    }
}

package com.master.ordercoffee.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.master.ordercoffee.model.User;
import com.master.ordercoffee.utils.TextUtil;

public class UserService {

    public static String KEY_CACHE = "master-order";
    public static String KEY_USER = "current-user";

    public static void saveCurrentUser(Context context, User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_CACHE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (user != null) {
            String data = new Gson().toJson(user);
            if (!TextUtil.stringIsNullOrEmpty(data)) {
                editor.putString(KEY_USER, data);
                editor.apply();
            }
        }
    }

    public static User getCurrentUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_CACHE, Context.MODE_PRIVATE);
        String userStr = sharedPreferences.getString(KEY_USER, null);
        if (!TextUtil.stringIsNullOrEmpty(userStr)) {
            User user = new Gson().fromJson(userStr, User.class);
            if (user != null && !TextUtil.stringIsNullOrEmpty(user.id)) {
                return user;
            }
        }
        return null;
    }

    public static void clearCurrentUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_CACHE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USER);
    }

    public static boolean isUserAvailable(User user) {
        return user != null && !TextUtil.stringIsNullOrEmpty(user.id);
    }
}

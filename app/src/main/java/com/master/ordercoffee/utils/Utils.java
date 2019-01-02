package com.master.ordercoffee.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

public class Utils {

    private static Handler mUiHandler = new Handler(Looper.myLooper());

    public static void runOnUiThread (Runnable runnable) {
        if (mUiHandler != null && runnable != null) {
            mUiHandler.post(runnable);
        }
    }

    public static void runOnUiThread (Runnable runnable, long delayTimes) {
        if (mUiHandler != null && runnable != null) {
            mUiHandler.postDelayed(runnable, delayTimes);
        }
    }

    public static void removeRunable(Runnable runnable) {
        if (mUiHandler != null && runnable != null) {
            mUiHandler.removeCallbacks(runnable);
        }
    }

    private static PhoneNumberUtil phoneNumberUtil = null;
    public static boolean isPhoneCorrectFormat(String phone, Context context) {
        if (!TextUtil.stringIsNullOrEmpty(phone) || phone.length() < 5)
            return false;

        if (phoneNumberUtil == null)
            phoneNumberUtil = PhoneNumberUtil.createInstance(context);

        try {
            final Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(phone, "VN");
            String nationalNumber = String.valueOf(phoneNumber.getNationalNumber()); // = 902xxxx (origin is 092xxxx)
            if (nationalNumber.length() == 10) {
                return false;
            }

            return phoneNumberUtil.isValidNumber(phoneNumber);
        } catch (NumberParseException e) {
            e.printStackTrace();
        }

        return false;
    }

}

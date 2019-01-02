package com.master.ordercoffee.utils;

import android.content.Context;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

public class PhoneUtil {
    private static PhoneNumberUtil phoneNumberUtil = null;

    public static boolean isPhoneCorrectFormat(String phone, Context context) {
        if (TextUtil.stringIsNullOrEmpty(phone) || phone.length() < 5)
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

    public static String formatPhone(String phone) {
        if (phone.startsWith("0")) {
            phone = phone.replaceFirst("0", "+84");
        }
        return phone;
    }
}

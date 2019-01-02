package com.master.ordercoffee.utils;

public class TextUtil {
    public static boolean stringIsNullOrEmpty(String s) {
        return s == null || s.equals("null") || s.isEmpty();
    }
}

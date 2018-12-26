package com.master.ordercoffee.utils;

import android.os.SystemClock;

public class TimeUtil {
    public static long currentTimeStamp() {
        return System.currentTimeMillis();
    }

    public static long currentUpTime() {
        return SystemClock.uptimeMillis();
    }
}

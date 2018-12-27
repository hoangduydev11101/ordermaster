package com.master.ordercoffee.utils;

import android.os.Handler;
import android.os.Looper;

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

}

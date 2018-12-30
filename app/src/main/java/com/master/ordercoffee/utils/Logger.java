package com.master.ordercoffee.utils;

import android.util.Log;

public class Logger {

    private static final String TAG = Logger.class.getSimpleName();
    private static final String EMPTY = "";
    public static String MAIN_TAG = "Main";
    public static String FIREBASE_TAG = "FireBase";

    public static void log(String tag, String logstr) {
        Log.d(tag, logstr);
    }

    public static void logDebug (String mess) {
        Log.i("DEBUG", mess);
    }

    public static void logError (String mess) {
        Log.e("ERROR", mess);
    }


    /**
     * Send a VERBOSE log message.
     *
     * @param tag
     * @param format
     * @param args
     * @return
     */
    public static int v(String tag, String format, Object... args) {
        return Log.v(tag, format(format, args));
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param tag
     * @param msg
     * @param e
     * @return
     */
    public static int v(String tag, String msg, Throwable e) {
        return Log.v(tag, msg, e);
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param tag
     * @param format
     * @param e
     * @param args
     * @return
     */
    public static int v(String tag, String format, Throwable e, Object... args) {
        return Log.v(tag, format(format, args), e);
    }

    /**
     * Send a DEBUG log message.
     *
     * @param tag
     * @param format
     * @param args
     * @return
     */
    public static int d(String tag, String format, Object... args) {
        return Log.d(tag, format(format, args));
    }

    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param tag
     * @param msg
     * @param e
     * @return
     */
    public static int d(String tag, String msg, Throwable e) {
        return Log.d(tag, msg, e);
    }

    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param tag
     * @param format
     * @param e
     * @param args
     * @return
     */
    public static int d(String tag, String format, Throwable e, Object... args) {
        return Log.d(tag, format(format, args), e);
    }

    /**
     * Send a WARN log message.
     *
     * @param tag
     * @param format
     * @param args
     * @return
     */
    public static int w(String tag, String format, Object... args) {
        return Log.w(tag, format(format, args));
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param tag
     * @param msg
     * @param e
     * @return
     */
    public static int w(String tag, String msg, Throwable e) {
        return Log.w(tag, msg, e);
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param tag
     * @param format
     * @param e
     * @param args
     * @return
     */
    public static int w(String tag, String format, Throwable e, Object... args) {
        return Log.w(tag, format(format, args), e);
    }

    /**
     * Send a INFO log message.
     *
     * @param tag
     * @param format
     * @param args
     * @return
     */
    public static int i(String tag, String format, Object... args) {
        return Log.i(tag, format(format, args));
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param tag
     * @param msg
     * @param e
     * @return
     */
    public static int i(String tag, String msg, Throwable e) {
        return Log.i(tag, msg, e);
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param tag
     * @param format
     * @param e
     * @param args
     * @return
     */
    public static int i(String tag, String format, Throwable e, Object... args) {
        return Log.i(tag, format(format, args), e);
    }

    /**
     * Send a ERROR log message.
     *
     * @param tag
     * @param format
     * @param args
     * @return
     */
    public static int e(String tag, String format, Object... args) {
        return Log.e(tag, format(format, args));
    }

    /**
     * Send a ERROR log message and log the exception.
     *
     * @param tag
     * @param msg
     * @param e
     * @return
     */
    public static int e(String tag, String msg, Throwable e) {
        return Log.e(tag, msg, e);
    }

    /**
     * Send a ERROR log message and log the exception.
     *
     * @param tag
     * @param format
     * @param e
     * @param args
     * @return
     */
    public static int e(String tag, String format, Throwable e, Object... args) {
        return Log.e(tag, format(format, args), e);
    }

    private static String format(String format, Object... args) {
        try {
            return String.format(format == null ? EMPTY : format, args);
        } catch (RuntimeException e) {
            Logger.w(TAG, "format error. reason=%s, format=%s", e.getMessage(), format);
            return String.format(EMPTY, format);
        }

    }
}

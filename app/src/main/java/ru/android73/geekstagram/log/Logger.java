package ru.android73.geekstagram.log;

import android.util.Log;

import java.util.Locale;

public class Logger {

    static final String TAG = "Geekstagram";

    private static final boolean PRINT_FILE_NAME = true;

    private static void vt(String tag, String message) {
        StringBuilder sb = getStringBuilder(message);
        Log.v(tag, sb.toString());
    }

    private static void dt(String tag, String message) {
        StringBuilder sb = getStringBuilder(message);
        Log.d(tag, sb.toString());
    }

    private static void it(String tag, String message) {
        StringBuilder sb = getStringBuilder(message);
        Log.i(tag, sb.toString());
    }

    private static void wt(String tag, String message) {
        StringBuilder sb = getStringBuilder(message);
        Log.w(tag, sb.toString());
    }


    private static void et(String tag, String message) {
        StringBuilder sb = getStringBuilder(message);
        Log.e(tag, sb.toString());
    }


    private static void et(String tag, Throwable e) {
        Log.e(tag, "Exception: ", e);
    }

    public static void e(Throwable e) {
        et(TAG, e);
    }

    public static void v(String format, Object... args) {
        vt(TAG, String.format(Locale.getDefault(), format, args));
    }

    public static void d(String format, Object... args) {
        dt(TAG, String.format(Locale.getDefault(), format, args));
    }

    public static void i(String format, Object... args) {
        it(TAG, String.format(Locale.getDefault(), format, args));
    }

    public static void w(String format, Object... args) {
        wt(TAG, String.format(Locale.getDefault(), format, args));
    }

    public static void e(String format, Object... args) {
        et(TAG, String.format(Locale.getDefault(), format, args));
    }


    private static StringBuilder getStringBuilder(String message) {
        return new StringBuilder(100).append(getLocation()).append(' ').append(message);
    }

    private static String getLocation() {
        if (!PRINT_FILE_NAME) {
            return "";
        }

        final String logClassName = Logger.class.getName();
        final StackTraceElement[] traces = Thread.currentThread().getStackTrace();
        boolean found = false;

        for (StackTraceElement trace : traces) {
            if (found) {
                if (!trace.getClassName().startsWith(logClassName)) {
                    String className = trace.getFileName().substring(0, trace.getFileName().length() - 5);
                    return String.format(Locale.getDefault(), "[%s.%s:%d]", className, trace.getMethodName(), trace.getLineNumber());
                }
            } else if (trace.getClassName().startsWith(logClassName)) {
                found = true;
            }
        }
        return "[]";
    }

}

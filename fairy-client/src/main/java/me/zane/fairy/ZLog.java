package me.zane.fairy;

import android.util.Log;

/**
 * Created by Zane on 2017/10/27.
 * Email: zanebot96@gmail.com
 */

public final class ZLog {

    private static boolean DEBUG = true;
    private static String TAG = "Fairy-Client";

    private ZLog(){}

    public static void setDebug(boolean DEBUG) {
        ZLog.DEBUG = DEBUG;
    }

    public static void v(String message) {
        if (DEBUG) {
            Log.v(TAG, message);
        }
    }

    public static void d(String message) {
        if (DEBUG) {
            Log.d(TAG, message);
        }
    }

    public static void i(String message) {
        Log.i(TAG, message);
    }

    public static void w(String message) {
        Log.w(TAG, message);
    }

    public static void e(String message) {
        Log.e(TAG, message);
    }
}

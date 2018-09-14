package com.example.musicgamedome.Utils;

import android.util.Log;

/**
 * Created by Administrator on 2017-08-18 0018.
 */
public class LogUtils {

    public static final boolean DEBUG = true;

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }
}

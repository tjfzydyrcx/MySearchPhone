package com.example.my360windowmanager;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Administrator on 2017-08-07 0007.
 */
public class ToastUtils {
    private static boolean isShow = true;

    public static void show(Activity Activity, String str) {
        if (isShow) {
            Toast.makeText(Activity, str, Toast.LENGTH_SHORT).show();

        }
    }

    public static void cancel() {
        isShow = false;
    }
}

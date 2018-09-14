package com.example.my360windowmanager.Utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by Administrator on 2017-08-07 0007.
 * 屏幕的宽高
 */
public class ScreenSizeUtil {

    public static int getScreenWidth(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int screenWidth = metric.widthPixels;
        return screenWidth;
    }

    public static int getScreenHeight(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int screenHeight = metric.heightPixels;
        return screenHeight;
    }

}

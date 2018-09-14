package com.example.administrator.mysearchphone;

import android.app.Application;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by Administrator on 2017-09-18 0018.
 */
public class MyApplicton extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "aacf3b1ffb", true);


    }
}

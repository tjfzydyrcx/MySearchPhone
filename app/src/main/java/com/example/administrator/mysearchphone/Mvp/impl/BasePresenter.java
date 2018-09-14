package com.example.administrator.mysearchphone.Mvp.impl;

import android.content.Context;

/**
 * Created by Administrator on 2017-08-04 0004.
 */
public class BasePresenter {
    Context context;

    public void attach(Context context) {
        this.context = context;
    }

    public void onCreate() {
    }

    public void onPause() {
    }

    public void Resume() {
    }

    public void Destroy() {
        context = null;
    }
}

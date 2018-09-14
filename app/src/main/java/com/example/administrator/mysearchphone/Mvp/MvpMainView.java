package com.example.administrator.mysearchphone.Mvp;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Administrator on 2017-08-04 0004.
 */
public interface MvpMainView extends MvpLoadingView {
    void showToast(String msg);
    void updateView();
}

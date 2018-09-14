package com.example.mynews.Utils;

import android.app.Activity;

import com.example.mynews.weight.CommonProgressDialog;

/**
 * Created by Administrator on 2017-08-09 0009.
 * 弹窗工具类
 */
public class ProgressDialogUtils {
    private CommonProgressDialog dialog;
    private Activity activity;

    public ProgressDialogUtils(Activity activity) {
        this.activity = activity;
    }

    //显示
    public void ProgressDialogshow(String msg) {
        if (dialog == null) {
            dialog = new CommonProgressDialog(activity);
        }

        if (msg == null) {
            msg = "正在加载。。。";
        }
        dialog.setMessage(msg);
        if (!activity.isFinishing() && !dialog.isShowing()) {
            dialog.show();
        }
    }

    ;

    //关闭
    public void ProgressDialogclose() {
        if (dialog != null && !activity.isFinishing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

}

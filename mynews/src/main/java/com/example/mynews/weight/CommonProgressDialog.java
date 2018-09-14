package com.example.mynews.weight;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.example.mynews.R;

/**
 * Created by Administrator on 2017-08-09 0009.
 * 加载弹窗样式
 */
public class CommonProgressDialog extends Dialog {

    public CommonProgressDialog(Context context) {
        super(context, R.style.commonProgressDialog);
        setContentView(R.layout.commonprpgressdialog);
        //显示在屏幕中间
        getWindow().getAttributes().gravity = Gravity.CENTER;
    }

    /**
     * 设置加载消息的方法
     */
    public void setMessage(String s) {
        TextView tv = (TextView) this.findViewById(R.id.id_tv_loading);
        tv.setText(s);
    }

}

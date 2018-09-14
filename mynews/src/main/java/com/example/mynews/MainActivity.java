package com.example.mynews;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.mynews.Utils.ProgressDialogUtils;

public class MainActivity extends AppCompatActivity {
    ProgressDialogUtils utils;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            utils.ProgressDialogclose();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        utils=    new ProgressDialogUtils(this);
        utils.ProgressDialogshow("数据我来了哦");
       handler.sendMessageDelayed(new Message(),3000);

    }
}

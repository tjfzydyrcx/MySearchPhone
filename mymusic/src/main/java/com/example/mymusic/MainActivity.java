package com.example.mymusic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymusic.FIleinfo.FileInfo;
import com.example.mymusic.service.DownLoadService;

public class MainActivity extends AppCompatActivity {
    private EditText editpath;
    private Button btndown;
    private Button btnstop;
    private TextView textresult;
    public static ProgressBar progressbar;

    FileInfo fileinfo;
    Handler handle = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editpath = (EditText) findViewById(R.id.editpath);
        btndown = (Button) findViewById(R.id.btndown);
        btnstop = (Button) findViewById(R.id.btnstop);
        textresult = (TextView) findViewById(R.id.textresult);
        progressbar = (ProgressBar) findViewById(R.id.progressBar);
        progressbar.setMax(100);
        ButtonClickListener listener = new ButtonClickListener();
        btndown.setOnClickListener(listener);
        btnstop.setOnClickListener(listener);
        String apk = "http://112.74.96.87:8088/package/DrugSaleTreasure_o2o.apk";

        fileinfo = new FileInfo(0, apk/*"http://abv.cn/music/%E5%85%89%E8%BE%89%E5%B2%81%E6%9C%88.mp3"*/, "光辉岁月.mp3", 0, 0);
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownLoadService.ACCTON_UPDATE);
        registerReceiver(mReceiver, filter);
    }

    private final class ButtonClickListener implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btndown:

                    Intent intent = new Intent(MainActivity.this, DownLoadService.class);
                    intent.setAction(DownLoadService.ACTION_START);
                    intent.putExtra("fileInfo", fileinfo);
                    startService(intent);
                    btnstop.setEnabled(true);
                    btndown.setEnabled(false);
                    break;

                case R.id.btnstop:
                    btndown.setEnabled(true);
                    btnstop.setEnabled(false);
                    intent = new Intent(MainActivity.this, DownLoadService.class);
                    intent.setAction(DownLoadService.ACCTON_STOP);
                    intent.putExtra("fileInfo", fileinfo);
                    startService(intent);
                    break;
            }
        }
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownLoadService.ACCTON_UPDATE.equals(intent.getAction())) {
                final int finished = intent.getIntExtra("finished", 0);
             /*   handle.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 100);*/
                progressbar.setProgress(finished);
                textresult.setText(finished + "%");   //把下载的百分比显示到界面控件上
                if (progressbar.getProgress() == progressbar.getMax()) { //下载完成时提示
                    Toast.makeText(getApplicationContext(), "文件下载成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}

package com.example.mymusic.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.mymusic.FIleinfo.FileInfo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017-08-15 0015.
 */
public class DownLoadService extends Service {
    public final static String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/downloads/";
    public final static String ACTION_START = "ACTION_START";
    public final static String ACCTON_STOP = "ACCTON_STOP";
    public final static String ACCTON_UPDATE = "ACCTON_UPDATE";
    public final static int MSG_INIT = 0;
    private DownloadTask task = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 获得activity 的参数
        if (ACTION_START.equals(intent.getAction())) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            Log.i("test", "Start==" + fileInfo.toString());
            new InitThread(fileInfo).start();
        } else if (ACCTON_STOP.equals(intent.getAction())) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            Log.i("test", "Stop==" + fileInfo.toString());
            if (task != null) {
                task.isPause = true;
            }
        }

        intent.getData();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_INIT:
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    Log.i("test", "INIT==" + fileInfo.toString());

                    task = new DownloadTask(fileInfo, DownLoadService.this);
                    task.download();
                    break;
            }

        }
    };

    /*
    * 初始化线程
    *
    * */
    class InitThread extends Thread {
        FileInfo fileInfo = null;

        public InitThread(FileInfo fileInfo) {

            this.fileInfo = fileInfo;
        }

        public void run() {
            //连接网络文件
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            try {
                URL url = new URL(fileInfo.getUrl());

                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");

                int length = -1;
                if (conn.getResponseCode() == 200) {
                    // 获取文件长度
                    length = conn.getContentLength();
                }
                if (length <= 0) {
                    return;
                } else {

                    File dir = new File(DOWNLOAD_PATH);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    //在本地创建文件
                    File file = new File(dir, fileInfo.getFileName());
                    raf = new RandomAccessFile(file, "rwd");
                    // 设置文件长度
                    raf.setLength(length);
                    //设置文件长度
                    fileInfo.setLength(length);
                    //  发送消息
                    handler.obtainMessage(MSG_INIT, fileInfo).sendToTarget();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {

                    raf.close();
                    conn.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

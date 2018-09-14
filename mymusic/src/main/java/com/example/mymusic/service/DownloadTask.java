package com.example.mymusic.service;

import android.content.Context;
import android.content.Intent;

import com.example.mymusic.DbUtils.ThreadDAO;
import com.example.mymusic.DbUtils.ThreadDaAOImpl;
import com.example.mymusic.FIleinfo.FileInfo;
import com.example.mymusic.FIleinfo.Threadinfo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2017-08-15 0015.
 * 下载任务类
 */
public class DownloadTask {

    Context context;
    FileInfo fileInfo = null;
    private ThreadDAO mDao = null;
    private int mFinished = 0;
    public boolean isPause = false;

    public DownloadTask(FileInfo fileInfo, Context context) {
        this.fileInfo = fileInfo;
        this.context = context;
        mDao = new ThreadDaAOImpl(context);
    }

    public void download() {
        //读取数据库的线程信息
        List<Threadinfo> threadinfos = mDao.getThreads(fileInfo.getUrl());
        Threadinfo threadinfo = null;
        if (threadinfos.size() == 0) {
            //初始化线程类
            threadinfo = new Threadinfo(0, fileInfo.getUrl(), 0, fileInfo.getLength(), 0);
        } else {
            threadinfo = threadinfos.get(0);
        }
        //创建子线程开始下载
        new DownloadThread(threadinfo).start();
    }

    class DownloadThread extends Thread {
        private Threadinfo mThradinfo = null;

        public DownloadThread(Threadinfo mThradinfo) {
            this.mThradinfo = mThradinfo;
        }


        public void run() {
            //向数据库插入线程信息
            if (!mDao.isExists(mThradinfo.getUrl(), mThradinfo.getId())) {
                mDao.inisertThread(mThradinfo);
            }
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream input = null;
            try {
                URL url = new URL(mThradinfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();

                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                //  设置下载位置
                int start = mThradinfo.getStart() + mThradinfo.getFinished();
                conn.setRequestProperty("Range", "bytes=" + start + "-" + mThradinfo.getEnd());
                //文件的写入位置
                File file = new File(DownLoadService.DOWNLOAD_PATH, fileInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);
                Intent intent = new Intent(DownLoadService.ACCTON_UPDATE);
                mFinished += mThradinfo.getFinished();
                //开始下载
                if (conn.getResponseCode() == 206) {
                    // 读取数据
                    input = conn.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int len = -1;
                    //写入文件
                    while ((len = input.read(buffer)) != -1) {
                        raf.write(buffer, 0, len);
                        mFinished += len;
                        mThradinfo.setFinished(mFinished);
                        long jindu = mFinished * 100 / fileInfo.getLength();
                        intent.putExtra("finished", (int) jindu);
                        intent.putExtra("size", fileInfo.getLength());
                        context.sendBroadcast(intent);

                        // 在下载暂停时，保存下载进度
                        if (isPause) {
                            mDao.updateThread(mThradinfo.getUrl(), mThradinfo.getId(), mThradinfo.getFinished());
                            return;
                        }
                    }

                    mDao.deleteThread(mThradinfo.getUrl(), mThradinfo.getId());
                }
                //把下载进入发送广播给Activity


            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                try {
                    raf.close();
                    input.close();
                    conn.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }


        }
    }
}

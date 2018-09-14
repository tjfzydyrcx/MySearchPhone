package com.example.musicgamedome.Utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

import com.example.musicgamedome.R;

import java.io.IOException;

/**
 * 音乐播放类
 * Created by Administrator on 2017-08-22 0022.
 */
public class Myplay {
    //音效索引
    public final static int INDEX_STONE_ENTER = 0;
    public final static int INDEX_STONE_CANCEL = 1;
    public final static int INDEX_STONE_COIN = 2;

    // 音效的文件名称
    private final static String[] SONG_NAMES = {"enter.mp3", "cancel.mp3", "coin.mp3"};

    private static MediaPlayer[] mToneMediaPlay = new MediaPlayer[SONG_NAMES.length];

    private static MediaPlayer mMusicMediaPlayer;

    /**
     * 播放音效
     *
     * @param context
     * @param index
     */
    public static void playTone(Context context, int index) {
        //加载声音
        AssetManager assetManager = context.getAssets();
        if (mToneMediaPlay[index] == null) {
            mToneMediaPlay[index] = new MediaPlayer();
            try {
                AssetFileDescriptor fileDescriptor = assetManager.openFd(SONG_NAMES[index]);
                mToneMediaPlay[index].setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
                mToneMediaPlay[index].prepare();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mToneMediaPlay[index].start();
    }

    /**
     * 播放音效
     *
     * @param context
     */
    public static void stopTone(Context context, int index) {
        mToneMediaPlay[index].stop();
    }

    /**
     * 播放歌曲
     *
     * @param context
     * @param filename
     */
    public static void playSong(Context context, String filename) {
        if (mMusicMediaPlayer == null) {
            mMusicMediaPlayer = new MediaPlayer();
        }

        //强制重置
        mMusicMediaPlayer.reset();
        AssetManager assetManager = context.getAssets();
        try {
            AssetFileDescriptor fileDescriptor = assetManager.openFd(filename);
            mMusicMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            mMusicMediaPlayer.prepare();
            mMusicMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 停止歌曲
     *
     * @param context
     */
    public static void stopThesong(Context context) {
        if (mMusicMediaPlayer == null) {
            mMusicMediaPlayer = new MediaPlayer();
        }
        mMusicMediaPlayer.stop();

    }

}

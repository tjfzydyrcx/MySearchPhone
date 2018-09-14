package com.example.musicgamedome.Model;

/**
 * Created by Administrator on 2017-08-17 0017.
 */
public class Song {
    private String mSongName;//歌曲名
    private String mSongFileMame;//文件名
    private int mNameLength;//歌曲名长度
     //一个一个的字
    public char[] getNameCharacters() {
        return mSongName.toCharArray();
    }

    public String getSongFileMame() {
        return mSongFileMame;
    }

    public void setSongFileMame(String mSongFileMame) {
        this.mSongFileMame = mSongFileMame;
    }

    public int getNameLength() {
        return mNameLength;
    }


    public String getSongName() {
        return mSongName;
    }

    public void setSongName(String SongName) {
        this.mSongName = SongName;
        this.mNameLength = mSongName.length();
    }
}

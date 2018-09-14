package com.example.mymusic.DbUtils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mymusic.FIleinfo.Threadinfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-08-15 0015.
 */
public class ThreadDaAOImpl implements ThreadDAO {

    private DBHelper mhelper = null;

    public ThreadDaAOImpl(Context context) {
        mhelper = new DBHelper(context);
    }

    @Override
    public void inisertThread(Threadinfo threadinfo) {
        SQLiteDatabase db = mhelper.getWritableDatabase();
        db.execSQL("insert into thread_info(thread_id,url,start,end,finished) values(?,?,?,?,?)",
                new Object[]{threadinfo.getId(), threadinfo.getUrl(), threadinfo.getStart(), threadinfo.getEnd(), threadinfo.getFinished()});
        db.close();
    }

    @Override
    public void deleteThread(String url, int thread_id) {
        SQLiteDatabase db = mhelper.getWritableDatabase();
        db.execSQL("delete from thread_info where url= ? and thread_id = ?", new Object[]{url, thread_id});
        db.close();
    }

    @Override
    public void updateThread(String url, int thread_id, int finished) {
        SQLiteDatabase db = mhelper.getWritableDatabase();
        db.execSQL("update thread_info set finished = ? where url= ? and thread_id = ?", new Object[]{finished, url, thread_id});
        db.close();
    }

    @Override
    public List<Threadinfo> getThreads(String url) {
        List<Threadinfo> list = new ArrayList<>();
        SQLiteDatabase db = mhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url = ?", new String[]{url});
        while (cursor.moveToNext()) {
            Threadinfo threadinfo = new Threadinfo();
            threadinfo.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            threadinfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            threadinfo.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            threadinfo.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            threadinfo.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            list.add(threadinfo);

        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public boolean isExists(String url, int thread_id) {


        SQLiteDatabase db = mhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url = ? and thread_id = ?", new String[]{url, String.valueOf(thread_id)});
        boolean exists = cursor.moveToNext();
        cursor.close();
        db.close();

        return exists;
    }
}

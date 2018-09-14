package com.example.administrator.mysearchphone.widget;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by ${thinkPad} on 2017/9/16.
 */

public class CallRecordsUtils {


    private static final ArrayList<String> TODO = null;

    public String[] cltype = {"已接电话", "已拨电话", "未接来电"};
    private Context mContext;
    Cursor cursor;
    public static ArrayList<String> callLogs = new ArrayList<String>();

    public CallRecordsUtils(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 获取通话记录	包括type date cachedName number
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public ArrayList<String> getCallLogs(String phone) {

        String[] projection = {
                CallLog.Calls.DATE, // 日期
                CallLog.Calls.NUMBER, // 号码
                CallLog.Calls.TYPE, // 类型
                CallLog.Calls.CACHED_NAME, // 名字
                CallLog.Calls.DURATION  //通话时长
        };
        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
        Date date;
        try {
            ContentResolver cr = mContext.getContentResolver();
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return TODO;
            }
            //获取呼出的
//            cursor = cr.query(CallLog.Calls.CONTENT_URI, projection, "number=?", new String[]{"15011448851"}, CallLog.Calls.DEFAULT_SORT_ORDER);
            // 获取呼入的
            cursor = cr.query(CallLog.Calls.CONTENT_URI, null, "number=?", new String[]{phone}, null);
//            cursor = cr.query(CallLog.Calls.CONTENT_URI, projection, null, new String[]{"15011448851"}, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (cursor.moveToNext()) {
                    date = new Date(cursor.getLong(cursor
                            .getColumnIndex(CallLog.Calls.DATE)));
                    String number = cursor.getString(cursor
                            .getColumnIndex(CallLog.Calls.NUMBER));
                    int type = cursor.getInt(cursor
                            .getColumnIndex(CallLog.Calls.TYPE));
                    String cachedName = cursor.getString(cursor
                            .getColumnIndex(CallLog.Calls.CACHED_NAME));
                    long duratton = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DURATION));
                    Log.i("通话类型__________________", type + "==" + duratton);
                    String typePhone = "无";
                    if (type == 1) {
                        typePhone = cltype[0];
                    } else if (type == 2) {
                        typePhone = cltype[1];
                    } else {
                        typePhone = cltype[2];
                    }
                    String callLog = typePhone + ";" + sfd.format(date) + ";" + cachedName + ";" + number + "===" + duratton;
                    callLogs.add(callLog);
                    Collections.reverse(callLogs);
                }


                cursor.close();
            }
//            Log.i("通话时长__________________", callLogs.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return callLogs;
    }
}


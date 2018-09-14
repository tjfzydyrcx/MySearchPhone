package com.example.watermelondiarynew.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 李 on 2017/1/26.
 */
public class GetDate {

    public static StringBuilder getDate(){

        StringBuilder stringBuilder = new StringBuilder();
        Calendar now = Calendar.getInstance();
        stringBuilder.append(now.get(Calendar.YEAR) + "年");
        stringBuilder.append((int)(now.get(Calendar.MONTH) + 1)  + "月");
        stringBuilder.append(now.get(Calendar.DAY_OF_MONTH) + "日");
//        stringBuilder.append("  "+GetNowDate());
        return stringBuilder;
    }
    //当前时间 ==小时
    public static String GetNowDate() {
        String temp_str = "";
        Date dt = new Date();
        //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        temp_str = sdf.format(dt);
        return temp_str;
    } //当前时间 ==小时
}

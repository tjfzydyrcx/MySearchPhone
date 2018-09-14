package com.example.musicgamedome.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.musicgamedome.Activity.MainActivity;

/**
 * Created by Administrator on 2017-08-16 0016.
 */
public class Getlayout {



    public static View getView(Context context, int layoutId) {


        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflator.inflate(layoutId, null);
        return layout;
    }
}

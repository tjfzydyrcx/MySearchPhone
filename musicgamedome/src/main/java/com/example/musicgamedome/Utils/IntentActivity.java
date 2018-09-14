package com.example.musicgamedome.Utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2017-08-22 0022.
 */
public class IntentActivity {

    public static void startActivity(Context context, Class desti) {
        Intent i = new Intent(context, desti);
        context.startActivity(i);
    }
}

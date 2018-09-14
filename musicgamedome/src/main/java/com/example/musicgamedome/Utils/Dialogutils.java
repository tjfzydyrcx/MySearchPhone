package com.example.musicgamedome.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.musicgamedome.Model.IAlerDialogButtonListener;
import com.example.musicgamedome.R;

/**
 * Created by Administrator on 2017-08-22 0022.
 */
public class Dialogutils {
    private static AlertDialog mAlertDialog;

    public static void showDialog(final Context context, String msg, final IAlerDialogButtonListener listener) {
        View view = Getlayout.getView(context, R.layout.dialog_view);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_Transparent);
        ImageButton btnok = view.findViewById(R.id.btn_true);
        ImageButton btnno = view.findViewById(R.id.btn_false);
        TextView message = view.findViewById(R.id.text_dialog_message);
        message.setText(msg);
        builder.setView(view);
        mAlertDialog = builder.create();
        mAlertDialog.show();
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAlertDialog != null) {
                    mAlertDialog.cancel();
                }
                if (listener != null) {
                    listener.onClick();
                }
                Myplay.playTone(context, Myplay.INDEX_STONE_ENTER);
            }
        });
        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAlertDialog != null) {
                    mAlertDialog.cancel();
                }
                Myplay.playTone(context, Myplay.INDEX_STONE_CANCEL);

            }
        });

    }


}

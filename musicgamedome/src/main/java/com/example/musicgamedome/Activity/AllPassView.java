package com.example.musicgamedome.Activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.musicgamedome.R;
import com.example.musicgamedome.Utils.LogUtils;

/**
 * Created by Administrator on 2017-08-22 0022.
 * 通关界面
 */
public class AllPassView extends AppCompatActivity implements View.OnClickListener {
    protected <T extends View> T $(@IdRes int id) {
        return (T) findViewById(id);
    }

    private FrameLayout layout_bar_icon;
    ImageButton wechat, rank;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allpass_view);
        layout_bar_icon = $(R.id.layout_bar_icon);
        layout_bar_icon.setVisibility(View.GONE);
        wechat = $(R.id.allpass_wechat);
        rank = $(R.id.allpass_rank);
        wechat.setOnClickListener(this);
        rank.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.allpass_wechat:
                LogUtils.e("HH", "WECHAT");
                break;
            case R.id.allpass_rank:
                LogUtils.e("HH", "RANK");
                break;
        }
    }
}

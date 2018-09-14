package com.example.mycalendarexaple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.text1, R.id.text2})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text1:
                startActivity(new Intent(MainActivity.this, XiaomiActivity.class));
                break;
            case R.id.text2:
                startActivity(new Intent(MainActivity.this, DingdingActivity.class));
                break;
        }
    }
}

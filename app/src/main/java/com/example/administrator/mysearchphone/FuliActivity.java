package com.example.administrator.mysearchphone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Administrator on 2017-09-04 0004.
 */
public class FuliActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_fuli);
        tv = (TextView) findViewById(R.id.tv);
        tv.setText("wo laile ");
    }
}

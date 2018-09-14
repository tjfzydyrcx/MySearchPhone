package com.example.administrator.mysearchphone;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mysearchphone.Api.Apiservice;
import com.example.administrator.mysearchphone.Mvp.MvpMainView;
import com.example.administrator.mysearchphone.Mvp.impl.MainPresenter;
import com.example.administrator.mysearchphone.MyModel.Phone;
import com.example.administrator.mysearchphone.business.HttpUntil;
import com.example.administrator.mysearchphone.widget.CallRecordsUtils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.BuglyLog;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, MvpMainView {


    String str = "13522336184";
    String appKey = "83a63c15f10fd7f2";
    String mUrl = "http://api.jisuapi.com/shouji/";
    EditText input_phone;
    TextView result_phone, result_province, result_type, result_carrier, tv_night;
    Button btn_search;
    MainPresenter mainPresenter;
    ProgressDialog progressDialog;
    CallRecordsUtils callRecordsUtils;

    protected <T extends View> T $(@IdRes int id) {
        return (T) findViewById(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      /*  //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        Preferences.init(MainActivity.this);
        setContentView(R.layout.activity_main);
        CrashReport.setUserId(getApplicationContext(), "tianjainfei");

        callRecordsUtils = new CallRecordsUtils(this);
        input_phone = $(R.id.input_phone);
        btn_search = $(R.id.btn_search);
        tv_night = $(R.id.night);
        result_phone = $(R.id.result_phone);
        result_province = $(R.id.result_province);
        result_type = $(R.id.result_type);
        result_carrier = $(R.id.result_carrier);
        btn_search.setOnClickListener(this);
        mainPresenter = new MainPresenter(this);
        mainPresenter.attach(this);


    /* Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mUrl).addConverterFactory(GsonConverterFactory.create()).client(new OkHttpClient())
                .build();
        Apiservice repo = retrofit.create(Apiservice.class);
        Call<Phone> call = repo.logresult(appKey, str);
        call.enqueue(new Callback<Phone>() {
            @Override
            public void onResponse(Call<Phone> call, Response<Phone> response) {
                Log.e("shuzhi", response.body().getResult().toString());
            }

            @Override
            public void onFailure(Call<Phone> call, Throwable t) {

            }
        });*/


    }

    @Override
    public void onClick(View view) {
//        Toast.makeText(this, "点击了", Toast.LENGTH_SHORT).show();
        // CrashReport.testJavaCrash();
        mainPresenter.searchPhoneinfo(input_phone.getText().toString());
//        callRecordsUtils.callDuration();

      /*  ArrayList<String> list=callRecordsUtils.getCallLogs("15011448851");
            Log.e("ces",list.get(0)) ;*/
//        CallRecordsUtils.callDuration();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void updateView() {
        Phone phone = mainPresenter.getPhoneInfo();
        result_phone.setText("手机号：" + phone.getResult().getShouji());
        result_province.setText("手机所在省：" + phone.getResult().getProvince());
        result_type.setText("手机网络类型：" + phone.getResult().getCardtype());
        result_carrier.setText("归属运营商：" + phone.getResult().getCompany());
    }

    @Override
    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(this, "", "正在加载...", true, false);

        } else if (progressDialog.isShowing()) {
            progressDialog.setTitle("");
            progressDialog.setMessage("正在加载...");
        }
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public static String getBase64(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        Base64 encoder = new Base64();
        return encoder.encode(data);
    }
}

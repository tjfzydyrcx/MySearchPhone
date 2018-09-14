package com.example.administrator.mysearchphone.Api;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.administrator.mysearchphone.MyModel.Phone;

import java.io.IOException;

import okhttp3.Interceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017-08-04 0004.
 */
public class Myretrofit {
    String appKey = "83a63c15f10fd7f2";
    String mUrl = "http://api.jisuapi.com/shouji/query?appkey=" + appKey + "/";
    HttpResponse mhttpResponse;

    public interface HttpResponse {
        void onSuccess(Object object);

        void onFail(String error);
    }

    public Myretrofit(HttpResponse httpResponse) {
        this.mhttpResponse = httpResponse;
    }

    public void onCreate(String phone) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mUrl).addConverterFactory(GsonConverterFactory.create())
                .build();
        Apiservice repo = retrofit.create(Apiservice.class);
        Call<Phone> call = repo.logresult(appKey, "phone");
        call.enqueue(new Callback<Phone>() {
            @Override
            public void onResponse(Call<Phone> call, Response<Phone> response) {
                Log.e("shuzhi", response.body().getResult().toString());
            }

            @Override
            public void onFailure(Call<Phone> call, Throwable t) {

            }
        });
    }

    Interceptor interceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {


         okhttp3.Response response= chain.proceed( chain.request()) ;


            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" )
                    .build();
        }
    };

}

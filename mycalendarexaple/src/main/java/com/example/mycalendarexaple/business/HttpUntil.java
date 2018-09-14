package com.example.mycalendarexaple.business;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017-08-04 0004.
 */
public class HttpUntil {

    String mUrl;
    Map<String, String> mParam;
    HttpResponse mhttpResponse;
    private final OkHttpClient client = new OkHttpClient();

    Handler mhandler = new Handler(Looper.getMainLooper());

    public interface HttpResponse {
        void onSuccess(Object object);

        void onFail(String error);
    }

    public HttpUntil(HttpResponse httpResponse) {
        this.mhttpResponse = httpResponse;
    }

    public void sendPostHttp(String url, Map<String, String> param) {
        sendHttp(url, param, true);

    }

    public void sendGettHttp(String url, Map<String, String> param) {
        sendHttp(url, param, false);

    }

    private void sendHttp(String url, Map<String, String> param, boolean isPost) {
        mUrl = url;
        mParam = param;
        //编写请求逻辑
        run(isPost);

    }

    private void run(boolean isPost) {
        final Request request = createRequest(isPost);
        Call mcall = client.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (mhttpResponse != null) {
                    mhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mhttpResponse.onFail("请求错误");
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (mhttpResponse == null) return;
                mhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            try {
                                mhttpResponse.onSuccess(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.e(" code---  请求转换失败", response.toString());
                                mhttpResponse.onFail(" code---  请求转换失败" + response);
                            }
                        } else {
                            mhttpResponse.onFail(" code---  请求失败" + response);
                            Log.e(" code---  请求失败", response.toString());
                        }
                    }
                });
            }
        });
    }

    /*
    * 创建请求
    * */
    private Request createRequest(boolean isPost) {
        Request request;
        if (isPost) {
            MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder();
            requestBodyBuilder.setType(MultipartBody.FORM);
            Iterator<Map.Entry<String, String>> iterator = mParam.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                requestBodyBuilder.addFormDataPart(entry.getKey(), entry.getValue());
            }
            request = new Request.Builder().url(mUrl).post(requestBodyBuilder.build()).build();
        } else {
            String urlStr = mUrl + MapParamToString(mParam);
            Log.e("urlStr", urlStr);
            request = new Request.Builder().url(urlStr).get().build();
        }
        return request;
    }

    //拼接字符串，get 方式
    private String MapParamToString(Map<String, String> param) {
        StringBuilder stringbuilder = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = param.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            stringbuilder.append(entry.getKey() + "=" + entry.getValue());
        }
        String str = stringbuilder.toString().substring(0, stringbuilder.length());
        return str;
    }
}

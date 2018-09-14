package com.example.administrator.mysearchphone.Mvp.impl;

import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.administrator.mysearchphone.Mvp.MvpMainView;
import com.example.administrator.mysearchphone.MyModel.Phone;
import com.example.administrator.mysearchphone.business.HttpUntil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017-08-04 0004.
 */
public class MainPresenter extends BasePresenter {
    //极速数据网址  https://www.jisuapi.com
    String appKey = "83a63c15f10fd7f2";
    String mUrl = "http://api.jisuapi.com/shouji/query?appkey=" + appKey;
    MvpMainView mvpMainView;
    Phone mPhone;

    public Phone getPhoneInfo() {
        return mPhone;
    }

    public MainPresenter(MvpMainView mvpMainView) {
        this.mvpMainView = mvpMainView;

    }

    public void searchPhoneinfo(String phone) {
        if (phone.length() != 11) {
            mvpMainView.showToast("请输入正确的手机号");
            return;
        }
        mvpMainView.showLoading();
        //写上Http 处理的逻辑
        sendHttp(phone);
    }

    private void sendHttp(String phone) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("shouji", phone);
        HttpUntil httpUntil = new HttpUntil(new HttpUntil.HttpResponse() {
            @Override
            public void onSuccess(Object object) {
                String json = object.toString();
                Log.e("shuju", json);
                //自带的
                mPhone = parseModelWithOrgJson(json);
                //gson
//                mPhone = parseModelWithGson(json);
                //fastJson
//                mPhone = parseModelWithFastJson(json);
                mvpMainView.hideLoading();
                mvpMainView.updateView();
            }

            @Override
            public void onFail(String error) {
                mvpMainView.showToast(error);
                mvpMainView.hideLoading();

            }
        });
        httpUntil.sendGettHttp(mUrl, map);

    }

    private Phone parseModelWithOrgJson(String json) {
        Phone phone = new Phone();
        Phone.ResultBean resbean = new Phone.ResultBean();
        try {
            JSONObject jsb = new JSONObject(json);
            JSONObject result = jsb.getJSONObject("result");
            String value = result.getString("shouji");
            resbean.setShouji(value);
            value = result.getString("province");
            resbean.setProvince(value);
            value = result.getString("cardtype");
            resbean.setCardtype(value);
            value = result.getString("company");
            resbean.setCompany(value);
            phone.setResult(resbean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return phone;
    }

    private Phone parseModelWithGson(String json) {
        Gson gson = new Gson();
        Phone phone = gson.fromJson(json, Phone.class);
        return phone;
    }

    private Phone parseModelWithFastJson(String json) {
        Phone phone = JSON.parseObject(json, Phone.class);
        return phone;
    }
}
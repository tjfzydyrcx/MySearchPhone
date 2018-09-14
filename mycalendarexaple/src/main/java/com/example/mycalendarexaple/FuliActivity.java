package com.example.mycalendarexaple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.example.mycalendarexaple.Adapter.RefreshFootAdapter;
import com.example.mycalendarexaple.Api.Apiservice;
import com.example.mycalendarexaple.Model.Fuli;
import com.example.mycalendarexaple.business.HttpUntil;
import com.example.mycalendarexaple.weight.RecycleViewScrollListeners;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017-09-04 0004.
 */
public class FuliActivity extends AppCompatActivity implements RefreshFootAdapter.OnItemClickLitener {
    @BindView(R.id.fl_swipeRefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.fl_recyclerView)
    RecyclerView recyclerView;
    private List<Fuli> mTitles = null;
    int shu = 6, page = 1;
    private List<String> mImg = null;
    String mUrl = "http://gank.io/api/data/";
    RefreshFootAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_fuli);
        ButterKnife.bind(this);

        initdata();



    }

    private void initdata() {

        mTitles = new ArrayList<>();
        mImg = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mUrl).addConverterFactory(GsonConverterFactory.create()).client(new OkHttpClient())
                .build();
        Apiservice repo = retrofit.create(Apiservice.class);
        Call<Fuli> call = repo.logresult(shu, page);
        call.enqueue(new Callback<Fuli>() {
            @Override
            public void onResponse(Call<Fuli> call, Response<Fuli> response) {
                Log.e("shuzhi", response.body().getResults().size() + "");

                mTitles.add(response.body());
                for (Fuli mt : mTitles) {
                    for (int i = 0; i < mt.getResults().size(); i++) {
                        mImg.add(mt.getResults().get(i).getUrl());
                        Log.e("shuju", mImg.get(i) + "");
                        init();
                    }
                }
            }

            @Override
            public void onFailure(Call<Fuli> call, Throwable t) {

            }
        });


    }

    private void init() {
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RefreshFootAdapter(FuliActivity.this, mImg);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTitles.clear();
                mImg.clear();
                shu = 10;
                page = 1;
                initdata();
                swipeRefreshLayout.setRefreshing(false);
                adapter.notifyDataSetChanged();
            }
        });
        recyclerView.addOnScrollListener(new RecycleViewScrollListeners() {
            @Override
            public void onLoadMore() {
                mTitles.clear();
                page = ++page;
                initdata();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void sendHttp() {
        HttpUntil httpUntil = new HttpUntil(new HttpUntil.HttpResponse() {
            @Override
            public void onSuccess(Object object) {
                String json = object.toString();
                Log.e("shuju", json);
                Gson gs = new Gson();
                Fuli fuli = gs.fromJson(object.toString(), Fuli.class);
            }

            @Override
            public void onFail(String error) {
                Log.e("error", error);

            }
        });
        httpUntil.sendGettHttp(mUrl, new HashMap<String, String>());

    }


    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

}

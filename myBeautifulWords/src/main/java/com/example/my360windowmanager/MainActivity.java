package com.example.my360windowmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<TypeInterf> list;
    MyRecyclerAdapter madapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
       /* recyclerView = (RecyclerView) findViewById(R.id.myRecycler);

        madapter = new MyRecyclerAdapter(list, this);
        Log.e("SHUJU", list.size() + "=====");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(madapter);
        madapter.setOnItemClickLitener(new MyRecyclerAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtils.show(MainActivity.this, "wodele==" + position);

            }

            @Override
            public void onItemLongClick(View view, int position) {
                ToastUtils.show(MainActivity.this, "changwodele==" + position);
            }
        });*/
    }

    private void initData() {
        list = new ArrayList<>();
        Item_a item_a = null;
        Item_b item_b = null;
        for (int i = 0; i < 60; i++) {
            if (i % 3 == 0) {
                item_a = new Item_a();
                item_a.setText1("ItemA的第" + i + "个text1");
                item_a.setText2("ItemA的第" + i + "个text2");
                list.add(item_a);
            } else {
                item_b = new Item_b();
                item_b.setText1("ItemB的第" + i + "个text1");
                item_b.setText2("ItemB的第" + i + "个text2");
                list.add(item_b);
            }
        }
    }

}

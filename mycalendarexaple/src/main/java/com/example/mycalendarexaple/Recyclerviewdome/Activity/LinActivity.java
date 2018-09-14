package com.example.mycalendarexaple.Recyclerviewdome.Activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.example.mycalendarexaple.R;
import com.example.mycalendarexaple.Recyclerviewdome.Adapters.MyAdapter;
import com.example.mycalendarexaple.Recyclerviewdome.Network.MyOkhttp;
import com.example.mycalendarexaple.Recyclerviewdome.model.Meizi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-09-05 0005.
 */
public class LinActivity extends AppCompatActivity {
    @BindView(R.id.line_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.line_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.line_coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private int screenwidth;
    private List<Meizi> meizis;
    private int page = 1;
    private ItemTouchHelper itemTouchHelper;
    private MyAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private int lastVisibleItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initView();
        setListener();
        new GetDataTask().execute("http://gank.io/api/data/福利/10/1");
        //获取屏幕宽度
        WindowManager wm = (WindowManager) LinActivity.this
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        screenwidth = outMetrics.widthPixels;

    }


    private void initView() {
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
    }

    private void setListener() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                new GetDataTask().execute("http://gank.io/api/data/福利/10/1");

            }
        });

        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = 0, swipeFlags = 0;
                if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    //设置侧滑方向为从左到右和从右到左都可以
                    swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

                }
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                Collections.swap(meizis, from, to);
                mAdapter.notifyItemMoved(from, to);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                mAdapter.removeItem(viewHolder.getAdapterPosition());

            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(Color.WHITE);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                viewHolder.itemView.setAlpha(1 - Math.abs(dX) / screenwidth);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                0：当前屏幕停止滚动；1时：屏幕在滚动 且 用户仍在触碰或手指还在屏幕上；2时：随用户的操作，屏幕上产生的惯性滑动；
                //               滑动状态停止并且剩余两个item时自动加载
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 2 >= mLayoutManager.getItemCount()) {
                    new GetDataTask().execute("http://gank.io/api/data/福利/10/" + (++page));
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //                获取加载的最后一个可见视图在适配器的位置。
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();

            }

        });

    }

    class GetDataTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... params) {
            return MyOkhttp.get(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!TextUtils.isEmpty(s)) {
                JSONObject jsonObject;
                Gson gson = new Gson();
                String jsonData = null;
                try {
                    jsonObject = new JSONObject(s);
                    jsonData = jsonObject.getString("results");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (meizis == null || meizis.size() == 0) {
                    meizis = gson.fromJson(jsonData, new TypeToken<List<Meizi>>() {
                    }.getType());
                    Meizi pages = new Meizi();
                    pages.setPage(page);
                } else {
                    List<Meizi> more = gson.fromJson(jsonData, new TypeToken<List<Meizi>>() {
                    }.getType());
                    meizis.addAll(more);
                    Meizi pages = new Meizi();
                    pages.setPage(page);
                    meizis.add(pages);
                }
                if (mAdapter == null) {
                    mAdapter = new MyAdapter(meizis, LinActivity.this, recyclerView, coordinatorLayout);
                    recyclerView.setAdapter(mAdapter);
                    itemTouchHelper.attachToRecyclerView(recyclerView);
                    mAdapter.setOnItemClickLitener(new MyAdapter.OnItemClickLitener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Log.e("shuzi", meizis.size() + "==" + position);
                            popuwindow_image(view, position);
                        }

                        @Override
                        public void onItemLongClick(View view, int position) {

                        }
                    });
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    List<View> mViews = new ArrayList<>();

    private void popuwindow_image(View v, final int i) {

        final ViewPager viewPager;
        final PopupWindow popupWindow;

        View contentView = getLayoutInflater().from(getApplicationContext()).inflate(R.layout.pupuwindow_image, null);
        viewPager = (ViewPager) contentView.findViewById(R.id.pager);
        //imageView= (ImageView) contentView.findViewById(R.id.tishi_duihao);

        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        int height = this.getWindowManager().getDefaultDisplay().getHeight();
        int width = this.getWindowManager().getDefaultDisplay().getWidth();
        popupWindow.setHeight(height);
        popupWindow.setWidth(width);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("mengdd", "onTouch : ");
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });


        mViews.clear();

        for (int j = 0; j < meizis.size(); j++) {
            View image = LayoutInflater.from(getApplicationContext()).inflate(R.layout.image_photo, null);
            ImageView img = (ImageView) image.findViewById(R.id.image_photo);
            mViews.add(image);
            Matrix matrix = new Matrix();
            Glide.with(this)
                    .load(meizis.get(j).getUrl())
                    .centerCrop()
                    .thumbnail(1.0f)
                    .error(R.mipmap.ic_launcher)
                    .into(img);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
        }

        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setCurrentItem(i);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
               /* if (state==2){
                    new GetDataTask().execute("http://gank.io/api/data/福利/10/" + (++page));
                }*/
            }
        });

        // imageView.setImageBitmap(bitmap);

        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.mipmap.bg_photo));
        // 设置好参数之后再show
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, height / 5);

    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mViews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViews.get(position));
            return mViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            container.removeView(mViews.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            // TODO Auto-generated method stub
            return view == object;
        }


    }
}

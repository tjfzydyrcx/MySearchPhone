package com.example.musicgamedome.MyView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.example.musicgamedome.Model.IWordButtonClickListener;
import com.example.musicgamedome.Model.WordButton;
import com.example.musicgamedome.R;
import com.example.musicgamedome.Utils.Getlayout;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-08-16 0016.
 */
public class MyGridView extends GridView {
    public static int COUNTS_WORDS = 24;
    private ArrayList<WordButton> mArrayList = new ArrayList<>();
    private MyGridAdapter mGridAdapter = null;
    private Context mContext;
    private Animation mScaleAnim;
    private IWordButtonClickListener mIbuttClick;


    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        mGridAdapter = new MyGridAdapter();
        this.setAdapter(mGridAdapter);

    }

    public void updateData(ArrayList<WordButton> List) {
        mArrayList = List;
        //重新设置数据源
        setAdapter(mGridAdapter);
    }

    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    class MyGridAdapter extends BaseAdapter {
        public MyGridAdapter() {
        }

        @Override
        public int getCount() {
            return mArrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return mArrayList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final WordButton holder;

            if (view == null) {
                view = Getlayout.getView(mContext, R.layout.self_ui_fridview_item);
                holder = mArrayList.get(i);

                //加载动画
                mScaleAnim = AnimationUtils.loadAnimation(mContext, R.anim.scale);
                //加载动画延迟时间
                mScaleAnim.setStartOffset(i * 100);


                holder.mIndex = i;
                holder.mViewButton = view.findViewById(R.id.item_btn);
                view.setTag(holder);
            } else {
                holder = (WordButton) view.getTag();
            }

            holder.mViewButton.setText(holder.mWordString);

            //播放动画
            view.startAnimation(mScaleAnim);
            holder.mViewButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mIbuttClick.onWordButtonClick(holder);

                }
            });
            return view;
        }
    }

    /**
     * 注册监听接口
     *
     * @param Listener
     */
    public void registOnWordButtonClick(IWordButtonClickListener Listener) {
        this.mIbuttClick = Listener;
    }
}

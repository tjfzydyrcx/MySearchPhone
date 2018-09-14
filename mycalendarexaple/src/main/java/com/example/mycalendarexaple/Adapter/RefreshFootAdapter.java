package com.example.mycalendarexaple.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mycalendarexaple.Model.Fuli;
import com.example.mycalendarexaple.R;
import com.example.mycalendarexaple.weight.ShowMaxImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-09-04 0004.
 */
public class RefreshFootAdapter extends RecyclerView.Adapter<RefreshFootAdapter.ViewHolder> {
    List<String> imgs = null;
    Context mContext;

    public RefreshFootAdapter(Context context, List<String> imgs) {
        this.mContext = context;
        this.imgs = imgs;
    }


    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_fuli_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Glide.with(mContext).load(imgs.get(position)).centerCrop()
                .error(R.mipmap.ic_launcher)
                .into(holder.fruitImage);
        Log.e("url", imgs.get(position));
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return imgs.size();
    }

    //添加一个item
    public void addItem(String meizi, int position) {
        imgs.add(position, meizi);
        notifyItemInserted(position);
//        recyclerview.scrollToPosition(position);//recyclerview滚动到新加item处
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fruitImage;

        public ViewHolder(View view) {
            super(view);
            fruitImage = (ImageView) view.findViewById(R.id.img_view);

        }
    }

}

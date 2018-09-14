package com.example.mycalendarexaple.Recyclerviewdome.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mycalendarexaple.R;
import com.example.mycalendarexaple.Recyclerviewdome.model.Meizi;
import com.example.mycalendarexaple.Utils.SnackbarUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Administrator on 2017-09-05 0005.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements View.OnClickListener {
    List<Meizi> meizis;
    Context context;
    RecyclerView recyclerview;
    CoordinatorLayout coordinatorLayout;

    public MyAdapter(List<Meizi> meizis, Context context, RecyclerView recyclerview, CoordinatorLayout coordinatorLayout) {
        this.meizis = meizis;
        this.context = context;
        this.recyclerview = recyclerview;
        this.coordinatorLayout = coordinatorLayout;
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
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(
                context).inflate(R.layout.line_meizi_item, parent,
                false);
        MyViewHolder holder = new MyViewHolder(view);

        view.setOnClickListener(this);

        return holder;

    }

    @Override
    public void onBindViewHolder(final MyAdapter.MyViewHolder holder, final int position) {

        holder.tv.setText("图" + position);
        Picasso.with(context).load(meizis.get(position).getUrl()).into(holder.iv);
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("shuzi", meizis.get(position).getUrl() + "====" + holder.getLayoutPosition() + "");
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return meizis.size();
    }

    @Override
    public void onClick(View v) {
        int position = recyclerview.getChildAdapterPosition(v);
        SnackbarUtil.ShortSnackbar(coordinatorLayout, meizis.get(position).getUrl()+"点击第" + position + "个", SnackbarUtil.Info).show();

    }

    public void addItem(Meizi meizi, int position) {
        meizis.add(position, meizi);
        notifyItemInserted(position);
        recyclerview.scrollToPosition(position);
    }

    public void removeItem(final int position) {
        final Meizi removed = meizis.get(position);
        meizis.remove(position);
        notifyItemRemoved(position);
        SnackbarUtil.ShortSnackbar(coordinatorLayout, "你删除了第" + position + "个item", SnackbarUtil.Warning).setAction("撤销", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(removed, position);
                SnackbarUtil.ShortSnackbar(coordinatorLayout, "撤销了删除第" + position + "个item", SnackbarUtil.Confirm).show();
            }
        }).setActionTextColor(Color.WHITE).show();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private PhotoView iv;
        private TextView tv;

        public MyViewHolder(View view) {
            super(view);
            iv = (PhotoView) view.findViewById(R.id.line_item_iv);
            tv = (TextView) view.findViewById(R.id.line_item_tv);
        }
    }
}

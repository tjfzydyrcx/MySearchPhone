package com.example.my360windowmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017-08-07 0007.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<TypeInterf> list;
    private OnItemClickLitener mOnItemClickLitener;

    public MyRecyclerAdapter(List<TypeInterf> list, Context context) {
        this.list = list;
        this.context = context;
    }


    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }


    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View view = null;
        if (viewType == R.layout.item_a) {
            view = View.inflate(context, R.layout.item_a, null);
            holder = new ViewHodlera(view);
        } else {
            view = View.inflate(context, R.layout.item_b, null);
            holder = new ViewHodlerb(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHodlera) {
            Log.e("wde ==", ((Item_a) list.get(position)).getText1() + "===");
            ((ViewHodlera) holder).tv1.setText(((Item_a) list.get(position)).getText1());
            ((ViewHodlera) holder).tv2.setText(((Item_a) list.get(position)).getText2());
        } else {
            Log.e("wde ==", ((Item_b) list.get(position)).getText1() + "===");
            ((ViewHodlerb) holder).tv1.setText(((Item_b) list.get(position)).getText1());
            ((ViewHodlerb) holder).tv2.setText(((Item_b) list.get(position)).getText2());
        }
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
        return list.size();
    }

    class ViewHodlera extends RecyclerView.ViewHolder {
        TextView tv1;
        TextView tv2;

        public ViewHodlera(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.item_a_text1);
            tv2 = (TextView) itemView.findViewById(R.id.item_a_text2);
        }
    }

    class ViewHodlerb extends RecyclerView.ViewHolder {
        TextView tv2;
        TextView tv1;

        //ImageView imageView;
        public ViewHodlerb(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.item_b_text1);
            tv2 = (TextView) itemView.findViewById(R.id.item_b_text2);
        }

    }
}

package com.noplugins.keepfit.coachplatform.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.bean.ReturnTimeBean;
import com.noplugins.keepfit.coachplatform.bean.SelectTimeBean;

import java.util.List;

public class AddTimeAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {
    private List<ReturnTimeBean.RestTimeBean.DataBean> list;
    private Activity context;
    private static final int EMPTY_VIEW = 2;
    private static final int TYPE_YOUTANG = 1;

    public AddTimeAdapter(List<ReturnTimeBean.RestTimeBean.DataBean> mlist, Activity mcontext) {
        list = mlist;
        context = mcontext;

    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {
        YouYangViewHolder youYangViewHolder = new YouYangViewHolder(view, false);
        return youYangViewHolder;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        RecyclerView.ViewHolder holder = getViewHolderByViewType(viewType, parent);
        return holder;
    }

    RecyclerView.ViewHolder holder = null;

    private RecyclerView.ViewHolder getViewHolderByViewType(int viewType, ViewGroup parent) {
        View item_view = null;
        if (viewType == EMPTY_VIEW) {
            item_view = LayoutInflater.from(context).inflate(R.layout.add_time_empty, parent, false);
            holder = new EmptyViewHolder(item_view, false);
        } else if (viewType == TYPE_YOUTANG) {
            item_view = LayoutInflater.from(context).inflate(R.layout.add_time_view, parent, false);
            holder = new YouYangViewHolder(item_view, true);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder view_holder, int position, boolean isItem) {
        if (view_holder instanceof YouYangViewHolder) {
            YouYangViewHolder holder = (YouYangViewHolder) view_holder;
            holder.delete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.remove(position);
                    notifyDataSetChanged();
                }
            });
            if (null == list.get(position).getBegDate()) {
                holder.date_tv.setText("今天");
            } else {
                holder.date_tv.setText(list.get(position).getBegDate());
            }
            String begin_time = list.get(position).getBegTime().substring(0,list.get(position).getBegTime().length()-3);
            String end_time = list.get(position).getEndTime().substring(0,list.get(position).getEndTime().length()-3);
            holder.time_tv.setText(begin_time + ":" + end_time);
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public int getAdapterItemViewType(int position) {
        if (list.size() == 0) {
            return EMPTY_VIEW;
        } else {
            return TYPE_YOUTANG;
        }
    }


    @Override
    public int getAdapterItemCount() {
        return list.size() > 0 ? list.size() : 1;
    }


    public void setData(List<ReturnTimeBean.RestTimeBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public EmptyViewHolder(View item_view, boolean isItem) {
            super(item_view);
            if (isItem) {

            } else {
                this.view = item_view;
            }
        }
    }


    public class YouYangViewHolder extends RecyclerView.ViewHolder {
        public View view;
        ImageView delete_btn;
        TextView date_tv, time_tv;

        public YouYangViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                this.view = itemView;
                delete_btn = view.findViewById(R.id.delete_btn);
                date_tv = view.findViewById(R.id.date_tv);
                time_tv = view.findViewById(R.id.time_tv);
            }
        }
    }
}

package com.noplugins.keepfit.coachplatform.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.bean.ClassDateBean;
import com.noplugins.keepfit.coachplatform.bean.ScheduleBean;
import com.noplugins.keepfit.coachplatform.bean.SelectDateBean;
import com.noplugins.keepfit.coachplatform.fragment.ScheduleFragment;
import com.noplugins.keepfit.coachplatform.util.ui.MyListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class ClassAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {
    private static final int EMPTY_VIEW = 2;
    private static final int TYPE_YOUTANG = 1;
    private static final int WEIJIESHU_VIEW = 3;
    private static final int YIJIESHU_VIEW = 4;
    private List<Integer> weijieshu_status_map = new ArrayList<>();
    private List<Integer> yijieshu_status_map = new ArrayList<>();
    ScheduleFragment scheduleFragment;
    List<ClassDateBean> classDateBeans;

    public ClassAdapter(List<ClassDateBean> m_classDateBean, ScheduleFragment m_scheduleFragment) {
        classDateBeans = m_classDateBean;
        scheduleFragment = m_scheduleFragment;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {

        WeiJieShuViewHolder youYangViewHolder = new WeiJieShuViewHolder(view, false);
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
            item_view = LayoutInflater.from(scheduleFragment.getContext()).inflate(R.layout.select_date_empty_view, parent, false);
            holder = new EmptyViewHolder(item_view, false);
        } else if (viewType == WEIJIESHU_VIEW) {
            item_view = LayoutInflater.from(scheduleFragment.getContext()).inflate(R.layout.class_date_item, parent, false);
            holder = new WeiJieShuViewHolder(item_view, true);
        } else if (viewType == YIJIESHU_VIEW) {
            item_view = LayoutInflater.from(scheduleFragment.getContext()).inflate(R.layout.class_date_item, parent, false);
            holder = new YiJieShuViewHolder(item_view, true);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder view_holder, int position, boolean isItem) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position);
                }
            }
        });
        if(classDateBeans.size()>0){
            ClassDateBean classDateBean = classDateBeans.get(position);
            if (view_holder instanceof WeiJieShuViewHolder) {
                WeiJieShuViewHolder holder = (WeiJieShuViewHolder) view_holder;
                holder.status_tv.setText("未结束");
                if (classDateBean.getWeijieshu_list().size() > 0) {
                    holder.status_tv.setVisibility(View.VISIBLE);
                    WeijieshuTypeAdapter weijieshuTypeAdapter = new WeijieshuTypeAdapter(classDateBean.getWeijieshu_list(), scheduleFragment);
                    holder.listview.setAdapter(weijieshuTypeAdapter);
                } else {
                    holder.status_tv.setVisibility(View.GONE);
                }

            } else if (view_holder instanceof YiJieShuViewHolder) {
                YiJieShuViewHolder holder = (YiJieShuViewHolder) view_holder;
                holder.status_tv.setText("已结束");
                if (classDateBean.getYijieshu_list().size() > 0) {
                    holder.status_tv.setVisibility(View.VISIBLE);
                    YiJieShuTypeAdapter yiJieShuTypeAdapter = new YiJieShuTypeAdapter(classDateBean.getYijieshu_list(), scheduleFragment);
                    holder.listview.setAdapter(yiJieShuTypeAdapter);
                } else {
                    holder.status_tv.setVisibility(View.GONE);
                }
            }
        }



    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    int RETUEN_CODE;

    @Override
    public int getAdapterItemViewType(int position) {
        if (classDateBeans.size() == 0) {
            RETUEN_CODE = EMPTY_VIEW;
        } else {
            if (classDateBeans.get(position).getType().equals("未结束")) {
                RETUEN_CODE = WEIJIESHU_VIEW;
            } else {
                RETUEN_CODE = YIJIESHU_VIEW;
            }
        }
        return RETUEN_CODE;
    }


    @Override
    public int getAdapterItemCount() {
        return classDateBeans.size() > 0 ? classDateBeans.size() : 1;
    }


    public void setData(List<ClassDateBean> list) {
        this.classDateBeans = list;
        notifyDataSetChanged();
    }


    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public EmptyViewHolder(View item_view, boolean isItem) {
            super(item_view);
            if (isItem) {
                this.view = item_view;
            } else {

            }
        }
    }

    public class WeiJieShuViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView status_tv;
        public MyListView listview;

        public WeiJieShuViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                this.view = itemView;
                status_tv = view.findViewById(R.id.status_tv);
                listview = view.findViewById(R.id.listview);
            }
        }
    }

    public class YiJieShuViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView status_tv;
        public MyListView listview;

        public YiJieShuViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                this.view = itemView;
                status_tv = view.findViewById(R.id.status_tv);
                listview = view.findViewById(R.id.listview);
            }
        }
    }

}

package com.noplugins.keepfit.coachplatform.util.ui.courcetable;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.bean.SelectDateBean;
import com.noplugins.keepfit.coachplatform.util.screen.ScreenUtilsHelper;

import java.util.List;

public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.ViewHolder> {
    private Context context;
    private List<SelectDateBean> data;
    private int item_width;

    public ResourceAdapter(Context context, List<SelectDateBean> data, int mitem_width) {
        this.context = context;
        this.item_width = mitem_width;
        this.data = data;

    }

    @NonNull
    @Override
    public ResourceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.item_recyclerview, null));

    }

    @Override
    public void onBindViewHolder(@NonNull ResourceAdapter.ViewHolder holder, final int position) {
        Log.e("上方日期打印", data.get(position).getMonth() + "/" + data.get(position).getDate());
        holder.date_tv.setText(data.get(position).getMonth() + "/" + data.get(position).getDate());
        holder.week_tv.setText(data.get(position).getWeek_str());
        if (data.get(position).getWeek_str().equals("今天")) {
            holder.date_tv.setTextColor(context.getResources().getColor(R.color.color_lan));
            holder.week_tv.setTextColor(context.getResources().getColor(R.color.color_lan));
        } else {
            holder.date_tv.setTextColor(context.getResources().getColor(R.color.color_181818));
            holder.week_tv.setTextColor(context.getResources().getColor(R.color.color_181818));
        }
        LinearLayout.LayoutParams lLayoutlayoutParams = new LinearLayout.LayoutParams(item_width, ScreenUtilsHelper.dip2px(context, 74));
        holder.item_layout.setLayoutParams(lLayoutlayoutParams);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView week_tv, date_tv;
        private LinearLayout item_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            week_tv = itemView.findViewById(R.id.week_tv);
            date_tv = itemView.findViewById(R.id.date_tv);
            item_layout = itemView.findViewById(R.id.item_layout);

        }
    }

}

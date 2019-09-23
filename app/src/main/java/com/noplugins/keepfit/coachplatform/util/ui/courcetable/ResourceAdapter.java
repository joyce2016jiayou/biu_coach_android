package com.noplugins.keepfit.coachplatform.util.ui.courcetable;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.util.screen.ScreenUtilsHelper;

import java.util.List;

public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.ViewHolder> {
    private Context context;
    private List<TopDateEntity> data;
    private int item_width;

    public ResourceAdapter(Context context, List<TopDateEntity> data, int mitem_width) {
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

        holder.date_tv.setText(data.get(position).getDate_str());
        holder.week_tv.setText(data.get(position).getWeek_str());

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

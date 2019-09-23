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

public class TimeLinLeftAdapter extends RecyclerView.Adapter<TimeLinLeftAdapter.ViewHolder>{
    private Context context;
    private List<String> data;
    private int item_width;
    public TimeLinLeftAdapter(Context context, List<String> data, int mitem_width) {
        this.context = context;
        this.item_width = mitem_width;
        this.data = data;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview, parent, false);
//        return new ViewHolder(view);
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.left_item, null));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv_item.setText(data.get(position));

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("这里是点击每一行item的响应事件", "" + position + "");
//            }
//        });

        LinearLayout.LayoutParams lLayoutlayoutParams = new LinearLayout.LayoutParams(item_width, ScreenUtilsHelper.dip2px(context, 79));

        holder.time_layout.setLayoutParams(lLayoutlayoutParams);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_item;
        private LinearLayout time_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_item = itemView.findViewById(R.id.time_tv);
            time_layout = itemView.findViewById(R.id.time_layout);

        }
    }
}

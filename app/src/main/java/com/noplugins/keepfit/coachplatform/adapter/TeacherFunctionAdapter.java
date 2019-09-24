package com.noplugins.keepfit.coachplatform.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.bean.MineFunctionBean;
import com.noplugins.keepfit.coachplatform.bean.TagEntity;

import java.util.List;

public class TeacherFunctionAdapter extends BaseAdapter {
    private List<MineFunctionBean> strings;
    private LayoutInflater layoutInflater;
    private Context context;

    public TeacherFunctionAdapter(Context mcontext, List<MineFunctionBean> mstrings) {
        this.strings = mstrings;
        context = mcontext;
        layoutInflater = LayoutInflater.from(mcontext);
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public Object getItem(int position) {
        return strings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TeacherFunctionAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_mine_function, parent,false);
            holder = new TeacherFunctionAdapter.ViewHolder();
            holder.tag_value = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tag_layout = (LinearLayout) convertView.findViewById(R.id.ll_click);
            holder.iv_img = convertView.findViewById(R.id.iv_img);
            convertView.setTag(holder);
        } else {
            holder = (TeacherFunctionAdapter.ViewHolder) convertView.getTag();
        }
        holder.tag_value.setText(strings.get(position).getName());
        Glide.with(context)
                .load(strings.get(position).getDrawImg())
                .into(holder.iv_img);


        return convertView;
    }

    class ViewHolder {
        TextView tag_value;
        ImageView iv_img;
        LinearLayout tag_layout;
    }
}

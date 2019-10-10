package com.noplugins.keepfit.coachplatform.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.bean.GetDailryBean;

import java.util.List;

public class DailyTagAdapter extends BaseAdapter {
    private List<GetDailryBean.LableListBean> strings;
    private LayoutInflater layoutInflater;
    private Context context;

    public DailyTagAdapter(Context mcontext, List<GetDailryBean.LableListBean> mstrings) {
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.daily_tag_item, null);
            holder = new ViewHolder();
            holder.tag_value = (TextView) convertView.findViewById(R.id.tag_value);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tag_value.setText(strings.get(position).getName());
        if (strings.get(position).isCheck()) {
            holder.tag_value.setBackgroundResource(R.drawable.tag_select_bg);
            holder.tag_value.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            holder.tag_value.setBackgroundResource(R.drawable.tag_bg);
            holder.tag_value.setTextColor(context.getResources().getColor(R.color.color_4A4A4A));
        }

        return convertView;
    }

    class ViewHolder {
        TextView tag_value;
    }
}

package com.noplugins.keepfit.coachplatform.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.bean.TagEntity;

import java.util.List;

public class TeacherTagAdapter extends BaseAdapter {
    private List<TagEntity> strings;
    private LayoutInflater layoutInflater;
    private Context context;

    public TeacherTagAdapter(Context mcontext, List<TagEntity> mstrings) {
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
            convertView = layoutInflater.inflate(R.layout.mine_tag_item, null);
            holder = new ViewHolder();
            holder.tag_value = (TextView) convertView.findViewById(R.id.tag_value);
            holder.tag_layout = (LinearLayout) convertView.findViewById(R.id.tag_layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tag_value.setText(strings.get(position).getTag());


        return convertView;
    }

    class ViewHolder {
        TextView tag_value;
        LinearLayout tag_layout;
    }
}

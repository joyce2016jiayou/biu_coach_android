package com.noplugins.keepfit.coachplatform.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.noplugins.keepfit.coachplatform.R;
import org.greenrobot.eventbus.EventBus;
import rx.Subscription;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;

    private List<String> list;

    public TypeAdapter(List<String> mlist, Context context) {
        this.context = context;
        this.list = mlist;
        this.inflater = LayoutInflater.from(context);
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final TypeAdapter.viewHolder holder;
        if (convertView == null) {
            holder = new TypeAdapter.viewHolder();
            convertView = inflater.inflate(R.layout.select_type_item, null);
            holder.name_tv = (TextView) convertView.findViewById(R.id.name_tv);

            convertView.setTag(holder);
        } else {
            holder = (TypeAdapter.viewHolder) convertView.getTag();
        }
        holder.name_tv.setText(list.get(position));

        return convertView;
    }


    private class viewHolder {
        private TextView name_tv;
    }
}
